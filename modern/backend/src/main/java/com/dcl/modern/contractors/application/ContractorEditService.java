package com.dcl.modern.contractors.application;

import com.dcl.modern.contractors.api.*;
import com.dcl.modern.contractors.domain.Contractor;
import com.dcl.modern.contractors.domain.ContactPerson;
import com.dcl.modern.contractors.domain.User;
import com.dcl.modern.contractors.infrastructure.ContactPersonRepository;
import com.dcl.modern.contractors.infrastructure.ContractorRepository;
import com.dcl.modern.contractors.infrastructure.ReputationRepository;
import com.dcl.modern.contractors.infrastructure.UserRepository;
import com.dcl.modern.contracts.infrastructure.ContractRepository;
import com.dcl.modern.country.infrastructure.CountryRepository;
import com.dcl.modern.currency.infrastructure.CurrencyRepository;
import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Contractor edit. Legacy: ContractorAction#edit, editCommon, process.
 * CONTRACTS: docs/screens/contractor_edit/CONTRACTS.md.
 * All data from Postgres (no fake).
 */
@Service
public class ContractorEditService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final ReputationRepository reputationRepository;
    private final UserRepository userRepository;
    private final ContractorRepository contractorRepository;
    private final ContactPersonRepository contactPersonRepository;
    private final ContractRepository contractRepository;

    private final CurrentUserProvider currentUserProvider;

    public ContractorEditService(CountryRepository countryRepository,
            CurrencyRepository currencyRepository,
            ReputationRepository reputationRepository,
            UserRepository userRepository,
            ContractorRepository contractorRepository,
            ContactPersonRepository contactPersonRepository,
            ContractRepository contractRepository,
            CurrentUserProvider currentUserProvider) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.reputationRepository = reputationRepository;
        this.userRepository = userRepository;
        this.contractorRepository = contractorRepository;
        this.contactPersonRepository = contactPersonRepository;
        this.contractRepository = contractRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Transactional(readOnly = true)
    public ContractorEditOpenResponse open(String ctrIdParam, String returnTo, String tab) {
        int ctrId = parseCtrId(ctrIdParam);
        Contractor c = contractorRepository.findById(ctrId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Контрагент не найден"));

        List<ContactPerson> contactPersons = contactPersonRepository.findByContractorIdOrderByIdAsc(ctrId);
        List<ContractorEditOpenResponse.ContractorEditContactPersonRowDto> gridContactPersons = contactPersons.stream()
            .map(cp -> toEditContactPersonRow(cp, contactPersons.indexOf(cp) + 1))
            .toList();

        var countries = countryRepository.findAll().stream()
            .map(cu -> new LookupItemDto(String.valueOf(cu.getId()), cu.getName()))
            .toList();
        var reputations = reputationRepository.findAll().stream()
            .map(r -> new LookupItemDto(String.valueOf(r.getId()), r.getDescription()))
            .toList();
        var users = userRepository.findAll().stream()
            .map(u -> new LookupItemDto(String.valueOf(u.getId()), u.getDisplayName()))
            .toList();
        var currencies = currencyRepository.findAll().stream()
            .map(cu -> new LookupItemDto(String.valueOf(cu.getId()), cu.getName()))
            .toList();
        var lookups = new ContractorEditOpenResponse.ContractorEditLookupsDto(countries, reputations, users, currencies);

        boolean formReadOnly = c.getBlock() != null && c.getBlock() == 1;
        boolean occupied = contractRepository.existsByContractorId(ctrId);
        boolean adminRole = isAdmin();
        boolean readOnlyReputation = isReadOnlyReputation();
        boolean readOnlyComment = isReadOnlyComment();
        boolean canDelete = adminRole && !occupied;
        var roleFlags = new ContractorEditOpenResponse.ContractorEditRoleFlagsDto(
            adminRole, readOnlyReputation, readOnlyComment, canDelete, occupied);

        String usrDateCreate = c.getCreateDate() != null ? c.getCreateDate().format(DATE_FMT) : "";
        String usrDateEdit = c.getEditDate() != null ? c.getEditDate().format(DATE_FMT) : "";
        var createUser = toUserRef(c.getCreatedBy());
        var editUser = toUserRef(c.getEditedBy());

        List<ContractorEditOpenResponse.ContractorEditUserRowDto> gridUsers = defaultGridUsers();
        List<ContractorEditOpenResponse.ContractorEditAccountRowDto> gridAccounts = defaultGridAccounts();

        String countryName = countryRepository.findById(c.getCountryId()).map(cu -> cu.getName()).orElse("");
        String repDesc = c.getReputationId() != null
            ? reputationRepository.findById(c.getReputationId()).map(r -> r.getDescription()).orElse("")
            : "";

        String activeTab = resolveActiveTab(tab);
        String returnToVal = returnTo != null && !returnTo.isBlank() ? returnTo : "contractors";

        return new ContractorEditOpenResponse(
            String.valueOf(c.getId()),
            false,
            c.getName() != null ? c.getName() : "",
            c.getFullName() != null ? c.getFullName() : "",
            new LookupItemDto(String.valueOf(c.getCountryId()), countryName),
            c.getIndex() != null ? c.getIndex() : "",
            c.getRegion() != null ? c.getRegion() : "",
            c.getPlace() != null ? c.getPlace() : "",
            c.getStreet() != null ? c.getStreet() : "",
            c.getBuilding() != null ? c.getBuilding() : "",
            c.getAddInfo() != null ? c.getAddInfo() : "",
            c.buildAddress(),
            c.getPhone() != null ? c.getPhone() : "",
            c.getFax() != null ? c.getFax() : "",
            c.getEmail() != null ? c.getEmail() : "",
            c.getUnp() != null ? c.getUnp() : "",
            c.getOkpo() != null ? c.getOkpo() : "",
            c.getBankProps() != null ? c.getBankProps() : "",
            c.getComment() != null ? c.getComment() : "",
            c.getReputationId() != null ? new LookupItemDto(String.valueOf(c.getReputationId()), repDesc) : null,
            c.getBlock() != null && c.getBlock() == 1 ? "1" : "0",
            formReadOnly,
            usrDateCreate,
            usrDateEdit,
            createUser,
            editUser,
            gridUsers,
            gridAccounts,
            gridContactPersons,
            activeTab,
            returnToVal,
            lookups,
            roleFlags
        );
    }

    @Transactional
    public ContractorEditSaveResponse save(String ctrIdParam, ContractorEditSaveRequest req) {
        int ctrId = parseCtrId(ctrIdParam);
        Contractor c = contractorRepository.findById(ctrId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Контрагент не найден"));

        if (req.ctrName() == null || req.ctrName().isBlank()) {
            throw new ContractorCreateService.ValidationException("ctrName", "Введите наименование контрагента");
        }
        Integer countryId = parseId(req.country());
        if (countryId == null) {
            throw new ContractorCreateService.ValidationException("country", "Выберите страну");
        }
        Integer reputationId = parseId(req.reputation());
        if (reputationId == null) {
            throw new ContractorCreateService.ValidationException("reputation", "Выберите репутацию");
        }
        String unp = (req.ctrUnp() != null && !req.ctrUnp().isBlank()) ? req.ctrUnp().trim() : null;
        if (unp != null && unp.length() < 6) {
            throw new ContractorCreateService.ValidationException("ctrUnp", "УНП не менее 6 символов");
        }
        if (unp != null) {
            Optional<Contractor> other = contractorRepository.findByUnp(unp);
            if (other.isPresent() && !other.get().getId().equals(ctrId)) {
                throw new ContractorCreateService.ValidationException("ctrUnp", "Контрагент с таким УНП уже существует");
            }
        }

        Integer userId = (currentUserProvider != null && currentUserProvider.getCurrentUser() != null)
            ? parseUserId(currentUserProvider.getCurrentUser().id())
            : 1;
        if (userId == null) userId = 1;

        c.setName(req.ctrName());
        c.setFullName(req.ctrFullName());
        c.setCountryId(countryId);
        c.setReputationId(reputationId);
        c.setEmail(req.ctrEmail());
        c.setIndex(req.ctrIndex());
        c.setRegion(req.ctrRegion());
        c.setPlace(req.ctrPlace());
        c.setStreet(req.ctrStreet());
        c.setBuilding(req.ctrBuilding());
        c.setAddInfo(req.ctrAddInfo());
        c.setPhone(req.ctrPhone());
        c.setFax(req.ctrFax());
        c.setOkpo(req.ctrOkpo());
        c.setUnp(unp);
        c.setBankProps(req.ctrBankProps());
        c.setComment(req.ctrComment());
        LocalDateTime now = LocalDateTime.now();
        c.setEditDate(now);
        c.setEditedBy(userId);
        contractorRepository.save(c);

        contactPersonRepository.deleteByContractorId(ctrId);
        if (req.gridContactPersons() != null) {
            for (ContractorEditSaveRequest.ContractorEditContactPersonSaveDto dto : req.gridContactPersons()) {
                Short fire = "1".equals(dto != null ? dto.cpsFire() : null) ? (short) 1 : (short) 0;
                Short block = "1".equals(dto != null ? dto.cpsBlock() : null) ? (short) 1 : (short) 0;
                ContactPerson cp = new ContactPerson(
                    ctrId,
                    dto != null && dto.cpsName() != null ? dto.cpsName() : "",
                    dto != null ? dto.cpsPosition() : null,
                    dto != null ? dto.cpsOnReason() : null,
                    dto != null ? dto.cpsPhone() : null,
                    dto != null ? dto.cpsMobPhone() : null,
                    dto != null ? dto.cpsFax() : null,
                    dto != null ? dto.cpsEmail() : null,
                    dto != null ? dto.cpsContractComment() : null,
                    fire,
                    block
                );
                contactPersonRepository.save(cp);
            }
        }

        String rt = req.returnTo() != null ? req.returnTo().trim() : "contractors";
        String redirectTo = "contract".equals(rt) ? "/contracts/new" : "/contractors";
        return new ContractorEditSaveResponse(String.valueOf(ctrId), rt, redirectTo);
    }

    private static int parseCtrId(String ctrId) {
        if (ctrId == null || ctrId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ctrId required");
        }
        try {
            return Integer.parseInt(ctrId.trim());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ctrId");
        }
    }

    private static Integer parseUserId(String id) {
        if (id == null || id.isBlank()) return null;
        try {
            return Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private ContractorEditOpenResponse.ContractorEditContactPersonRowDto toEditContactPersonRow(ContactPerson cp, int number) {
        return new ContractorEditOpenResponse.ContractorEditContactPersonRowDto(
            String.valueOf(number),
            String.valueOf(cp.getId()),
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

    private ContractorEditOpenResponse.UserRefDto toUserRef(Integer usrId) {
        if (usrId == null) return new ContractorEditOpenResponse.UserRefDto("", "");
        Optional<User> u = userRepository.findById(usrId);
        return u.map(user -> new ContractorEditOpenResponse.UserRefDto(String.valueOf(user.getId()), user.getDisplayName()))
            .orElseGet(() -> new ContractorEditOpenResponse.UserRefDto(String.valueOf(usrId), ""));
    }

    private List<ContractorEditOpenResponse.ContractorEditUserRowDto> defaultGridUsers() {
        List<ContractorEditOpenResponse.ContractorEditUserRowDto> list = new ArrayList<>();
        userRepository.findAll().stream().limit(1).findFirst().ifPresent(u ->
            list.add(new ContractorEditOpenResponse.ContractorEditUserRowDto("1",
                new ContractorEditOpenResponse.UserRefDto(String.valueOf(u.getId()), u.getDisplayName()))));
        return list;
    }

    private List<ContractorEditOpenResponse.ContractorEditAccountRowDto> defaultGridAccounts() {
        return List.of(
            new ContractorEditOpenResponse.ContractorEditAccountRowDto("1", "Расчетный счет (BYN)", "", new LookupItemDto("", "")),
            new ContractorEditOpenResponse.ContractorEditAccountRowDto("2", "Расчетный счет (USD)", "", new LookupItemDto("", "")),
            new ContractorEditOpenResponse.ContractorEditAccountRowDto("3", "Валютный счет", "", new LookupItemDto("", ""))
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

    private boolean isAdmin() {
        if (currentUserProvider == null) return true;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.roles() == null) return true;
        return u.roles().stream().anyMatch("admin"::equals);
    }

    private boolean isReadOnlyReputation() {
        if (currentUserProvider == null) return false;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.roles() == null) return false;
        return u.roles().stream().anyMatch(r -> "manager".equals(r) || "manager_chief".equals(r));
    }

    private boolean isReadOnlyComment() {
        return isReadOnlyReputation();
    }

    private static String resolveActiveTab(String tab) {
        if (tab == null || tab.isBlank()) return "mainPanel";
        if ("contactPersons".equals(tab)) return "contactPersonsContractor";
        return tab;
    }
}
