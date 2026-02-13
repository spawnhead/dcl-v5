package com.dcl.modern.contracts.application;

import com.dcl.modern.contracts.api.*;
import com.dcl.modern.contracts.domain.Contract;
import com.dcl.modern.contracts.infrastructure.ContractRepository;
import com.dcl.modern.contracts.infrastructure.SellerRepository;
import com.dcl.modern.contractors.domain.Contractor;
import com.dcl.modern.contractors.infrastructure.ContractorRepository;
import com.dcl.modern.currency.domain.Currency;
import com.dcl.modern.currency.infrastructure.CurrencyRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * N3a Contract create. Legacy: ContractAction (input, show, process).
 * CONTRACTS: docs/screens/contract_create/CONTRACTS.md.
 * TASK-0020: ALL reads/writes from Postgres. newContractorId resolved via SELECT.
 */
@Service
public class ContractCreateService {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ContractorRepository contractorRepository;
    private final CurrencyRepository currencyRepository;
    private final SellerRepository sellerRepository;
    private final ContractRepository contractRepository;

    public ContractCreateService(ContractorRepository contractorRepository,
            CurrencyRepository currencyRepository,
            SellerRepository sellerRepository,
            ContractRepository contractRepository) {
        this.contractorRepository = contractorRepository;
        this.currencyRepository = currencyRepository;
        this.sellerRepository = sellerRepository;
        this.contractRepository = contractRepository;
    }

    @Transactional(readOnly = true)
    public ContractCreateOpenResponse open(boolean canCreate, String newContractorId) {
        var contractors = contractorRepository.findAllByOrderByNameAsc().stream()
            .map(c -> new LookupItemDto(String.valueOf(c.getId()), c.getName()))
            .toList();
        var currencies = currencyRepository.findAll().stream()
            .map(c -> new LookupItemDto(String.valueOf(c.getId()), c.getName()))
            .toList();
        var sellers = sellerRepository.findAll().stream()
            .map(s -> new LookupItemDto(String.valueOf(s.getId()), s.getName()))
            .toList();

        LookupItemDto selectedContractor = null;
        if (newContractorId != null && !newContractorId.isBlank()) {
            Integer ctrId = parseIdFromString(newContractorId);
            if (ctrId != null) {
                Optional<Contractor> found = contractorRepository.findById(ctrId);
                if (found.isPresent()) {
                    Contractor c = found.get();
                    selectedContractor = new LookupItemDto(String.valueOf(c.getId()), c.getName());
                }
            }
        }

        var defaults = new ContractCreateOpenResponse.ContractCreateDefaultsDto(
            "",
            "",
            false,
            "",
            selectedContractor,
            null,
            false,
            false,
            null,
            false,
            "",
            "",
            true,
            "0"
        );
        var lookups = new ContractCreateOpenResponse.ContractCreateLookupsDto(contractors, currencies, sellers);
        return new ContractCreateOpenResponse(defaults, lookups, canCreate);
    }

    @Transactional
    public ContractCreateSaveResponse save(ContractCreateSaveRequest req) {
        List<ValidationErrorResponse.FieldError> errors = validate(req);
        if (!errors.isEmpty()) {
            throw new ContractCreateValidationException(new ValidationErrorResponse(
                new ValidationErrorResponse.ErrorDetail("VALIDATION_ERROR", errors)
            ));
        }
        Integer contractorId = parseId(req.contractor());
        Integer currencyId = parseId(req.currency());
        Integer sellerId = parseId(req.seller());
        if (contractorId == null || currencyId == null || sellerId == null) {
            throw new ContractCreateValidationException(new ValidationErrorResponse(
                new ValidationErrorResponse.ErrorDetail("VALIDATION_ERROR",
                    List.of(new ValidationErrorResponse.FieldError("contractor", "Invalid contractor/currency/seller")))));
        }
        LocalDate conDate = parseDate(req.conDate());
        Short reusable = Boolean.TRUE.equals(req.conReusable()) ? (short) 1 : (short) 0;
        LocalDate finalDate = req.conFinalDate() != null && !req.conFinalDate().isBlank() ? parseDate(req.conFinalDate()) : null;
        Short original = Boolean.TRUE.equals(req.conOriginal()) ? (short) 1 : (short) 0;
        Short annul = Boolean.TRUE.equals(req.conAnnul()) ? (short) 1 : (short) 0;
        Integer userId = 1;
        var now = LocalDateTime.now();

        var contract = new Contract(
            req.conNumber().trim(),
            conDate,
            contractorId,
            currencyId,
            sellerId,
            reusable,
            finalDate,
            req.conComment(),
            original,
            annul,
            now,
            userId
        );
        Contract saved = contractRepository.save(contract);
        return new ContractCreateSaveResponse(String.valueOf(saved.getId()), "/contracts");
    }

    private static Integer parseId(LookupItemDto dto) {
        if (dto == null || dto.id() == null || dto.id().isBlank()) return null;
        return parseIdFromString(dto.id());
    }

    private static Integer parseIdFromString(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<ValidationErrorResponse.FieldError> validate(ContractCreateSaveRequest req) {
        List<ValidationErrorResponse.FieldError> errors = new ArrayList<>();
        if (req == null) {
            errors.add(new ValidationErrorResponse.FieldError("conNumber", "Введите \"Номер\" договора"));
            return errors;
        }
        if (req.conNumber() == null || req.conNumber().isBlank()) {
            errors.add(new ValidationErrorResponse.FieldError("conNumber", "Введите \"Номер\" договора"));
        } else if (req.conNumber().length() > 50) {
            errors.add(new ValidationErrorResponse.FieldError("conNumber", "Номер не более 50 символов"));
        }
        if (req.conDate() == null || req.conDate().isBlank()) {
            errors.add(new ValidationErrorResponse.FieldError("conDate", "Введите \"Дату\" договора"));
        } else if (!isValidDate(req.conDate())) {
            errors.add(new ValidationErrorResponse.FieldError("conDate", "Неверный формат даты (ДД.ММ.ГГГГ)"));
        }
        if (req.contractor() == null || req.contractor().id() == null || req.contractor().id().isBlank()) {
            errors.add(new ValidationErrorResponse.FieldError("contractor.id", "Выберите \"Контрагента\""));
        }
        if (req.currency() == null || req.currency().id() == null || req.currency().id().isBlank()) {
            errors.add(new ValidationErrorResponse.FieldError("currency.id", "Выберите \"Валюту\""));
        }
        if (req.seller() == null || req.seller().id() == null || req.seller().id().isBlank()) {
            errors.add(new ValidationErrorResponse.FieldError("seller.id", "Выберите \"Продавца\""));
        }
        if (req.conComment() != null && req.conComment().length() > 5000) {
            errors.add(new ValidationErrorResponse.FieldError("conComment", "Примечание не более 5000 символов"));
        }
        boolean reusable = Boolean.TRUE.equals(req.conReusable());
        boolean sellerIs1 = req.seller() != null && "1".equals(req.seller().id());
        if (!reusable && sellerIs1) {
            if (req.conFinalDate() == null || req.conFinalDate().isBlank()) {
                errors.add(new ValidationErrorResponse.FieldError("conFinalDate", "Поле \"Срок действия\" обязательно"));
            } else if (!isValidDate(req.conFinalDate())) {
                errors.add(new ValidationErrorResponse.FieldError("conFinalDate", "Неверный формат даты (ДД.ММ.ГГГГ)"));
            }
        }
        if (req.conAnnulDate() != null && !req.conAnnulDate().isBlank() && !isValidDate(req.conAnnulDate())) {
            errors.add(new ValidationErrorResponse.FieldError("conAnnulDate", "Неверный формат даты (ДД.ММ.ГГГГ)"));
        }
        return errors;
    }

    private static boolean isValidDate(String s) {
        if (s == null || s.isBlank()) return true;
        try {
            DD_MM_YYYY.parse(s.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static LocalDate parseDate(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalDate.parse(s.trim(), DD_MM_YYYY);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static final class ContractCreateValidationException extends RuntimeException {
        private final ValidationErrorResponse body;

        public ContractCreateValidationException(ValidationErrorResponse body) {
            super("Validation failed");
            this.body = body;
        }

        public ValidationErrorResponse getBody() {
            return body;
        }
    }
}
