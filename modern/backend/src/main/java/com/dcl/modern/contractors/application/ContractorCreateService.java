package com.dcl.modern.contractors.application;

import com.dcl.modern.contractors.api.ContractorCreateOpenResponse;
import com.dcl.modern.contractors.api.ContractorCreateSaveRequest;
import com.dcl.modern.contractors.api.ContractorCreateSaveResponse;
import com.dcl.modern.contractors.api.LookupItemDto;
import com.dcl.modern.contractors.api.UserLookupDto;
import com.dcl.modern.contractors.domain.Contractor;
import com.dcl.modern.contractors.domain.ContactPerson;
import com.dcl.modern.contractors.infrastructure.ContactPersonRepository;
import com.dcl.modern.contractors.infrastructure.ContractorRepository;
import com.dcl.modern.contractors.infrastructure.ReputationRepository;
import com.dcl.modern.contractors.infrastructure.UserRepository;
import com.dcl.modern.country.infrastructure.CountryRepository;
import com.dcl.modern.currency.infrastructure.CurrencyRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * N3a1 Contractor create. Legacy: ContractorAction create, process.
 * CONTRACTS: docs/screens/contractor_create/CONTRACTS.md.
 * TASK-0020: ALL reads/writes from Postgres.
 */
@Service
public class ContractorCreateService {

    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final ReputationRepository reputationRepository;
    private final UserRepository userRepository;
    private final ContractorRepository contractorRepository;
    private final ContactPersonRepository contactPersonRepository;

    public ContractorCreateService(CountryRepository countryRepository,
            CurrencyRepository currencyRepository,
            ReputationRepository reputationRepository,
            UserRepository userRepository,
            ContractorRepository contractorRepository,
            ContactPersonRepository contactPersonRepository) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.reputationRepository = reputationRepository;
        this.userRepository = userRepository;
        this.contractorRepository = contractorRepository;
        this.contactPersonRepository = contactPersonRepository;
    }

    @Transactional(readOnly = true)
    public ContractorCreateOpenResponse open(String returnTo, Integer ctrId) {
        var countries = countryRepository.findAll().stream()
            .map(c -> new LookupItemDto(String.valueOf(c.getId()), c.getName()))
            .toList();
        var reputations = reputationRepository.findAll().stream()
            .map(r -> new LookupItemDto(String.valueOf(r.getId()), r.getDescription()))
            .toList();
        var users = userRepository.findAll().stream()
            .map(u -> new UserLookupDto(String.valueOf(u.getId()), u.getDisplayName()))
            .toList();
        var currencies = currencyRepository.findAll().stream()
            .map(c -> new LookupItemDto(String.valueOf(c.getId()), c.getName()))
            .toList();

        ContractorCreateOpenResponse.ContractorCreateDefaultsDto defaults;
        if (ctrId != null) {
            var opt = contractorRepository.findById(ctrId);
            if (opt.isPresent()) {
                var c = opt.get();
                var gridContactPersons = contactPersonRepository.findByContractorIdOrderByIdAsc(ctrId).stream()
                    .map(this::toContactPersonDto)
                    .toList();
                defaults = buildEditDefaults(c, gridContactPersons);
            } else {
                defaults = buildCreateDefaults(List.of());
            }
        } else {
            defaults = buildCreateDefaults(List.of());
        }
        var lookups = new ContractorCreateOpenResponse.ContractorCreateLookupsDto(countries, reputations, users, currencies);
        var tabs = List.of(
            new ContractorCreateOpenResponse.TabMetaDto("mainPanel", "Главная"),
            new ContractorCreateOpenResponse.TabMetaDto("usersContractor", "Курируют"),
            new ContractorCreateOpenResponse.TabMetaDto("accountsContractor", "Расчетные счета и банковские реквизиты"),
            new ContractorCreateOpenResponse.TabMetaDto("contactPersonsContractor", "Контактные лица"),
            new ContractorCreateOpenResponse.TabMetaDto("commentContractor", "Комментарии")
        );
        return new ContractorCreateOpenResponse(defaults, lookups, tabs, "mainPanel", returnTo != null ? returnTo : "contract");
    }

    @Transactional
    public ContractorCreateSaveResponse save(ContractorCreateSaveRequest req) {
        if (req == null || req.ctrName() == null || req.ctrName().isBlank()) {
            throw new ValidationException("ctrName", "Введите наименование контрагента");
        }
        Integer countryId = parseId(req.country());
        if (countryId == null) {
            throw new ValidationException("country", "Выберите страну");
        }
        Integer reputationId = parseId(req.reputation());
        Integer userId = 1; // dev default
        var now = LocalDateTime.now();
        String unp = (req.ctrUnp() != null && !req.ctrUnp().isBlank()) ? req.ctrUnp().trim() : null;
        var contractor = new Contractor(
            req.ctrName(),
            countryId,
            reputationId,
            req.ctrFullName(),
            req.ctrEmail(),
            req.ctrIndex(),
            req.ctrRegion(),
            req.ctrPlace(),
            req.ctrStreet(),
            req.ctrBuilding(),
            req.ctrAddInfo(),
            req.ctrPhone(),
            req.ctrFax(),
            req.ctrOkpo(),
            unp,
            req.ctrBankProps(),
            req.ctrComment(),
            now,
            userId
        );
        Contractor saved = contractorRepository.save(contractor);
        var contactPersons = req.gridContactPersons();
        if (contactPersons != null && !contactPersons.isEmpty()) {
            for (var cp : contactPersons) {
                Short fire = "1".equals(cp != null ? cp.cpsFire() : null) ? (short) 1 : (short) 0;
                Short block = "1".equals(cp != null ? cp.cpsBlock() : null) ? (short) 1 : (short) 0;
                var entity = new ContactPerson(
                    saved.getId(),
                    cp != null && cp.cpsName() != null ? cp.cpsName() : "",
                    cp != null ? cp.cpsPosition() : null,
                    cp != null ? cp.cpsOnReason() : null,
                    cp != null ? cp.cpsPhone() : null,
                    cp != null ? cp.cpsMobPhone() : null,
                    cp != null ? cp.cpsFax() : null,
                    cp != null ? cp.cpsEmail() : null,
                    cp != null ? cp.cpsContractComment() : null,
                    fire,
                    block
                );
                contactPersonRepository.save(entity);
            }
        }
        String rt = req.returnTo() != null ? req.returnTo().trim() : "contract";
        String redirectTo = "contractors".equals(rt) ? "/contractors" : "/contracts/new?newContractorId=" + saved.getId();
        return new ContractorCreateSaveResponse(String.valueOf(saved.getId()), redirectTo, rt);
    }

    private ContractorCreateOpenResponse.ContractorCreateDefaultsDto buildCreateDefaults(
            List<ContractorCreateOpenResponse.ContractorContactPersonRowDto> gridContactPersons) {
        var users = userRepository.findAll().stream().limit(1)
            .map(u -> new ContractorCreateOpenResponse.ContractorUserRowDto(String.valueOf(u.getId()), u.getDisplayName()))
            .toList();
        return new ContractorCreateOpenResponse.ContractorCreateDefaultsDto(
            "", "", null, "", "", "", "", "", "", "", "", "", "", "", null,
            users,
            List.of(
                new ContractorCreateOpenResponse.ContractorAccountRowDto("Счёт 1", "", null),
                new ContractorCreateOpenResponse.ContractorAccountRowDto("Счёт 2", "", null),
                new ContractorCreateOpenResponse.ContractorAccountRowDto("Счёт валютный", "", null)
            ),
            gridContactPersons != null ? gridContactPersons : List.of(),
            "", ""
        );
    }

    private ContractorCreateOpenResponse.ContractorCreateDefaultsDto buildEditDefaults(
            Contractor c, List<ContractorCreateOpenResponse.ContractorContactPersonRowDto> gridContactPersons) {
        var users = userRepository.findAll().stream().limit(1)
            .map(u -> new ContractorCreateOpenResponse.ContractorUserRowDto(String.valueOf(u.getId()), u.getDisplayName()))
            .toList();
        return new ContractorCreateOpenResponse.ContractorCreateDefaultsDto(
            c.getName() != null ? c.getName() : "",
            c.getFullName() != null ? c.getFullName() : "",
            new LookupItemDto(String.valueOf(c.getCountryId()), ""),
            c.getIndex() != null ? c.getIndex() : "",
            c.getRegion() != null ? c.getRegion() : "",
            c.getPlace() != null ? c.getPlace() : "",
            c.getStreet() != null ? c.getStreet() : "",
            c.getBuilding() != null ? c.getBuilding() : "",
            c.getAddInfo() != null ? c.getAddInfo() : "",
            c.getPhone() != null ? c.getPhone() : "",
            c.getFax() != null ? c.getFax() : "",
            c.getEmail() != null ? c.getEmail() : "",
            c.getUnp() != null ? c.getUnp() : "",
            c.getOkpo() != null ? c.getOkpo() : "",
            c.getReputationId() != null ? new LookupItemDto(String.valueOf(c.getReputationId()), "") : null,
            users,
            List.of(
                new ContractorCreateOpenResponse.ContractorAccountRowDto("Счёт 1", "", null),
                new ContractorCreateOpenResponse.ContractorAccountRowDto("Счёт 2", "", null),
                new ContractorCreateOpenResponse.ContractorAccountRowDto("Счёт валютный", "", null)
            ),
            gridContactPersons,
            c.getBankProps() != null ? c.getBankProps() : "",
            c.getComment() != null ? c.getComment() : ""
        );
    }

    private ContractorCreateOpenResponse.ContractorContactPersonRowDto toContactPersonDto(ContactPerson cp) {
        return new ContractorCreateOpenResponse.ContractorContactPersonRowDto(
            cp.getName() != null ? cp.getName() : "",
            cp.getPosition() != null ? cp.getPosition() : "",
            cp.getOnReason() != null ? cp.getOnReason() : "",
            cp.getPhone() != null ? cp.getPhone() : "",
            cp.getMobPhone() != null ? cp.getMobPhone() : "",
            cp.getFax() != null ? cp.getFax() : "",
            cp.getEmail() != null ? cp.getEmail() : "",
            cp.getContractComment() != null ? cp.getContractComment() : "",
            cp.getFire() != null && cp.getFire() == 1 ? "1" : "0",
            cp.getBlock() != null && cp.getBlock() == 1 ? "1" : "0"
        );
    }

    private static Integer parseId(LookupItemDto dto) {
        if (dto == null || dto.id() == null || dto.id().isBlank()) return null;
        try {
            return Integer.parseInt(dto.id().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static final class ValidationException extends RuntimeException {
        private final String field;
        private final String message;

        public ValidationException(String field, String message) {
            super(message);
            this.field = field;
            this.message = message;
        }
        public String getField() { return field; }
        @Override
        public String getMessage() { return message; }
    }
}
