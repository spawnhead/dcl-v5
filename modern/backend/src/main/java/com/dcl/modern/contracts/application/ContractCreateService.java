package com.dcl.modern.contracts.application;

import com.dcl.modern.contracts.api.*;
import com.dcl.modern.contracts.infrastructure.ContractsFakeProvider;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * N3a Contract create. Legacy: ContractAction (input, show, process).
 * CONTRACTS: docs/screens/contract_create/CONTRACTS.md.
 */
@Service
public class ContractCreateService {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ContractCreateOpenResponse open(boolean canCreate) {
        ContractCreateOpenResponse.ContractCreateDefaultsDto defaults =
            new ContractCreateOpenResponse.ContractCreateDefaultsDto(
                "",
                "",
                false,
                "",
                null,
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
        ContractCreateOpenResponse.ContractCreateLookupsDto lookups =
            new ContractCreateOpenResponse.ContractCreateLookupsDto(
                ContractsFakeProvider.getContractorsLookupForCreate(),
                ContractsFakeProvider.getCurrenciesLookup(),
                ContractsFakeProvider.getSellersLookupForCreate()
            );
        return new ContractCreateOpenResponse(defaults, lookups, canCreate);
    }

    /**
     * Save new contract. Validates per CONTRACTS §2; FAKE_SEEDED returns success without DB insert.
     */
    public ContractCreateSaveResponse save(ContractCreateSaveRequest req) {
        List<ValidationErrorResponse.FieldError> errors = validate(req);
        if (!errors.isEmpty()) {
            throw new ContractCreateValidationException(new ValidationErrorResponse(
                new ValidationErrorResponse.ErrorDetail("VALIDATION_ERROR", errors)
            ));
        }
        String conId = "5001";
        return new ContractCreateSaveResponse(conId, "/contracts");
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
