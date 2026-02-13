package com.dcl.modern.contractors.application;

import com.dcl.modern.contractors.api.*;
import com.dcl.modern.contractors.domain.Contractor;
import com.dcl.modern.contractors.domain.Department;
import com.dcl.modern.contractors.domain.User;
import com.dcl.modern.contractors.infrastructure.ContractorRepository;
import com.dcl.modern.contractors.infrastructure.DepartmentRepository;
import com.dcl.modern.contractors.infrastructure.UserRepository;
import com.dcl.modern.contracts.infrastructure.ContractRepository;
import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Contractors list use cases. Legacy: ContractorsAction (input, filter, page, block).
 * CONTRACTS: docs/screens/contractors/CONTRACTS.md.
 * Postgres-only: reads from ContractorRepository.
 */
@Service
public class ContractorListService {

    private static final int DEFAULT_PAGE_SIZE = 15;

    private final ContractorRepository contractorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ContractRepository contractRepository;

    @Autowired(required = false)
    private CurrentUserProvider currentUserProvider;

    public ContractorListService(ContractorRepository contractorRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            ContractRepository contractRepository) {
        this.contractorRepository = contractorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.contractRepository = contractRepository;
    }

    public ContractorLookupsResponse getLookups() {
        ContractorLookupsResponse.DefaultsDto defaults = new ContractorLookupsResponse.DefaultsDto(
            "", "", "", "", "", "", null, null);

        List<LookupItemDto> users = userRepository.findAll().stream()
            .map(u -> new LookupItemDto(
                String.valueOf(u.getId()),
                u.getDisplayName()))
            .toList();

        List<LookupItemDto> departments = departmentRepository.findAll().stream()
            .map(d -> new LookupItemDto(
                String.valueOf(d.getId()),
                d.getName()))
            .toList();

        ContractorLookupsResponse.LookupsDto lookups = new ContractorLookupsResponse.LookupsDto(users, departments);

        return new ContractorLookupsResponse(defaults, lookups, canCreate());
    }

    public ContractorDataResponse getData(ContractorDataRequest req) {
        if (req == null) req = new ContractorDataRequest(null, null, null, null, null, null, null, null, 1, DEFAULT_PAGE_SIZE);

        int page = req.page() != null && req.page() >= 1 ? req.page() : 1;
        int pageSize = req.pageSize() != null && req.pageSize() >= 1 ? Math.min(100, req.pageSize()) : DEFAULT_PAGE_SIZE;

        List<Contractor> all = contractorRepository.findAllByOrderByNameAsc();
        Predicate<Contractor> pred = buildPredicate(req);
        List<Contractor> filtered = all.stream().filter(pred).toList();

        int total = filtered.size();
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, total);
        List<Contractor> pageItems = from < total ? filtered.subList(from, to) : List.of();

        boolean hasNextPage = (long) page * pageSize < total;

        List<ContractorRowDto> items = pageItems.stream()
            .map(c -> toRow(c))
            .toList();

        return new ContractorDataResponse(items, page, pageSize, hasNextPage);
    }

    public ContractorDataResponse getPage(ContractorPageRequestDto req) {
        if (req == null || req.filterState() == null) {
            return getData(new ContractorDataRequest(null, null, null, null, null, null, null, null, 1, DEFAULT_PAGE_SIZE));
        }
        int current = req.currentPage() != null && req.currentPage() >= 1 ? req.currentPage() : 1;
        int pageSize = req.filterState().pageSize() != null ? Math.min(100, Math.max(1, req.filterState().pageSize())) : DEFAULT_PAGE_SIZE;
        int nextPage = "next".equalsIgnoreCase(req.direction()) ? current + 1
            : "prev".equalsIgnoreCase(req.direction()) ? Math.max(1, current - 1) : current;

        ContractorDataRequest dataReq = new ContractorDataRequest(
            req.filterState().ctrName(),
            req.filterState().ctrFullName(),
            req.filterState().ctrAccount(),
            req.filterState().ctrAddress(),
            req.filterState().ctrEmail(),
            req.filterState().ctrUnp(),
            req.filterState().user(),
            req.filterState().department(),
            nextPage,
            pageSize
        );
        return getData(dataReq);
    }

    public ContractorLookupsResponse cleanAll() {
        return getLookups(); // Resets defaults; UI will clear filters and refetch data
    }

    public void block(ContractorBlockRequest req) {
        if (req == null || req.ctrId() == null || req.ctrId().isBlank()) return;
        if (!isAdmin()) return; // BEHAVIOR_MATRIX: only admin can block

        try {
            int id = Integer.parseInt(req.ctrId().trim());
            Optional<Contractor> opt = contractorRepository.findById(id);
            if (opt.isEmpty()) return;

            Contractor c = opt.get();
            String blockVal = req.block() != null ? req.block().trim() : "";
            Short newBlock = "1".equals(blockVal) ? (short) 1 : (short) 0;
            c.setBlock(newBlock);
            contractorRepository.save(c);
        } catch (NumberFormatException ignored) {}
    }

    /**
     * Delete contractor. Admin-only, occupied=false. CONTRACTS §5 DELETE /api/contractors/{ctrId}.
     */
    public ResponseEntity<?> delete(String ctrId) {
        if (ctrId == null || ctrId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            int id = Integer.parseInt(ctrId.trim());
            if (contractRepository.existsByContractorId(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(java.util.Map.of("error", "Occupied"));
            }
            Optional<Contractor> opt = contractorRepository.findById(id);
            if (opt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            contractorRepository.delete(opt.get());
            return ResponseEntity.ok(java.util.Map.of("status", "OK"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Predicate<Contractor> buildPredicate(ContractorDataRequest req) {
        return c -> {
            if (req.ctrName() != null && !req.ctrName().isBlank()) {
                if (c.getName() == null || !c.getName().toUpperCase().contains(req.ctrName().toUpperCase()))
                    return false;
            }
            if (req.ctrFullName() != null && !req.ctrFullName().isBlank()) {
                if (c.getFullName() == null || !c.getFullName().toUpperCase().contains(req.ctrFullName().toUpperCase()))
                    return false;
            }
            if (req.ctrAddress() != null && !req.ctrAddress().isBlank()) {
                String addr = c.buildAddress();
                if (!addr.toUpperCase().contains(req.ctrAddress().toUpperCase()))
                    return false;
            }
            if (req.ctrEmail() != null && !req.ctrEmail().isBlank()) {
                if (c.getEmail() == null || !c.getEmail().toUpperCase().contains(req.ctrEmail().toUpperCase()))
                    return false;
            }
            if (req.ctrUnp() != null && !req.ctrUnp().isBlank()) {
                if (c.getUnp() == null || !c.getUnp().toUpperCase().contains(req.ctrUnp().toUpperCase()))
                    return false;
            }
            if (req.ctrAccount() != null && !req.ctrAccount().isBlank()) {
                if (c.getBankProps() == null || !c.getBankProps().toUpperCase().contains(req.ctrAccount().toUpperCase()))
                    return false;
            }
            if (req.user() != null && req.user().id() != null && !req.user().id().isBlank()) {
                try {
                    int uid = Integer.parseInt(req.user().id().trim());
                    if (c.getCreatedBy() == null || c.getCreatedBy() != uid) return false;
                } catch (NumberFormatException e) { return false; }
            }
            if (req.department() != null && req.department().id() != null && !req.department().id().isBlank()) {
                try {
                    int depId = Integer.parseInt(req.department().id().trim());
                    if (c.getCreatedBy() == null) return false;
                    Optional<User> u = userRepository.findById(c.getCreatedBy());
                    if (u.isEmpty() || u.get().getDepartmentId() == null || u.get().getDepartmentId() != depId)
                        return false;
                } catch (NumberFormatException e) { return false; }
            }
            return true;
        };
    }

    private ContractorRowDto toRow(Contractor c) {
        String ctrBlock = c.getBlock() != null && c.getBlock() == 1 ? "1" : "";
        boolean occupied = contractRepository.existsByContractorId(c.getId());

        return new ContractorRowDto(
            String.valueOf(c.getId()),
            c.getName() != null ? c.getName() : "",
            c.getFullName() != null ? c.getFullName() : "",
            c.buildAddress(),
            c.getPhone() != null ? c.getPhone() : "",
            c.getFax() != null ? c.getFax() : "",
            c.getEmail() != null ? c.getEmail() : "",
            c.getBankProps() != null ? c.getBankProps() : "",
            ctrBlock,
            occupied
        );
    }

    private boolean canCreate() {
        if (currentUserProvider == null) return true;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.roles() == null) return true;
        return u.roles().stream().anyMatch(r -> "admin".equals(r) || "economist".equals(r) || "lawyer".equals(r));
    }

    private boolean isAdmin() {
        if (currentUserProvider == null) return true;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.roles() == null) return true;
        return u.roles().stream().anyMatch("admin"::equals);
    }
}
