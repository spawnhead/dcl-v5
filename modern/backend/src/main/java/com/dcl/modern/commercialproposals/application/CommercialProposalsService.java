package com.dcl.modern.commercialproposals.application;

import com.dcl.modern.commercialproposals.api.*;
import com.dcl.modern.commercialproposals.domain.CommercialProposal;
import com.dcl.modern.commercialproposals.infrastructure.CommercialProposalRepository;
import com.dcl.modern.commercialproposals.infrastructure.CpCloneHelper;
import com.dcl.modern.commercialproposals.infrastructure.CpListProvider;
import com.dcl.modern.commercialproposals.infrastructure.CpLookupsRepository;
import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Commercial Proposals list use cases. Legacy: CommercialProposalsAction.
 * docs/screens/commercial_proposals/. Postgres-only.
 */
@Service
public class CommercialProposalsService {

    private static final int DEFAULT_PAGE_SIZE = 15;
    private static final List<SortItemDto> FIXED_SORT = List.of(
        new SortItemDto("cpr_date", "DESC"),
        new SortItemDto("cpr_number", "DESC")
    );

    private final CpListProvider cpListProvider;
    private final CpLookupsRepository cpLookupsRepository;
    private final CommercialProposalRepository commercialProposalRepository;
    private final CpCloneHelper cpCloneHelper;

    @Autowired(required = false)
    private CurrentUserProvider currentUserProvider;

    public CommercialProposalsService(CpListProvider cpListProvider,
            CpLookupsRepository cpLookupsRepository,
            CommercialProposalRepository commercialProposalRepository,
            CpCloneHelper cpCloneHelper) {
        this.cpListProvider = cpListProvider;
        this.cpLookupsRepository = cpLookupsRepository;
        this.commercialProposalRepository = commercialProposalRepository;
        this.cpCloneHelper = cpCloneHelper;
    }

    public CpLookupsResponse getLookups() {
        CpLookupsResponse.DefaultsDto defaults = new CpLookupsResponse.DefaultsDto(
            "", null, null, null, null, "", "", null, null, false, false);

        List<LookupItemDto> departments = cpLookupsRepository.getDepartments();
        List<LookupItemDto> contractors = cpLookupsRepository.getContractors();
        List<LookupItemDto> users = cpLookupsRepository.getUsers();
        List<LookupItemDto> stuffCategories = cpLookupsRepository.getStuffCategories();

        CpLookupsResponse.LookupsDto lookups = new CpLookupsResponse.LookupsDto(
            departments, contractors, users, stuffCategories);

        return new CpLookupsResponse(defaults, lookups);
    }

    public CpDataResponse getData(CpDataRequest req) {
        if (req == null) req = defaultRequest();

        int page = req.page() != null && req.page() >= 1 ? req.page() : 1;
        int pageSize = req.pageSize() != null && req.pageSize() >= 1 ? Math.min(100, req.pageSize()) : DEFAULT_PAGE_SIZE;

        CpListProvider.FilterParams params = toFilterParams(req);
        CpListProvider.Result result = cpListProvider.list(params, page, pageSize);

        boolean hasNextPage = (long) page * pageSize < result.total();

        return new CpDataResponse(result.items(), result.page(), result.pageSize(), hasNextPage, FIXED_SORT);
    }

    public CpDataResponse getPage(CpPageRequestDto req) {
        if (req == null || req.filterState() == null) {
            return getData(defaultRequest());
        }
        int current = req.currentPage() != null && req.currentPage() >= 1 ? req.currentPage() : 1;
        int pageSize = req.filterState().pageSize() != null ? Math.min(100, Math.max(1, req.filterState().pageSize())) : DEFAULT_PAGE_SIZE;
        int nextPage = "next".equalsIgnoreCase(req.direction()) ? current + 1
            : "prev".equalsIgnoreCase(req.direction()) ? Math.max(1, current - 1) : current;

        CpDataRequest dataReq = new CpDataRequest(
            req.filterState().cprNumber(),
            req.filterState().department(),
            req.filterState().contractor(),
            req.filterState().user(),
            req.filterState().stuffCategory(),
            req.filterState().cprDateFrom(),
            req.filterState().cprDateTo(),
            req.filterState().cprSumFrom(),
            req.filterState().cprSumTo(),
            req.filterState().cprProposalReceivedFlag(),
            req.filterState().cprProposalDeclined(),
            nextPage,
            pageSize
        );
        return getData(dataReq);
    }

    public CleanAllResponse cleanAll() {
        CpLookupsResponse lookups = getLookups();
        CpDataResponse grid = getData(defaultRequest());
        return new CleanAllResponse(lookups.defaults(), grid);
    }

    @Transactional
    public void block(CpBlockRequest req) {
        if (req == null || req.cprId() == null || req.cprId().isBlank()) return;
        if (!canBlock()) return;

        try {
            int id = Integer.parseInt(req.cprId().trim());
            Optional<CommercialProposal> opt = commercialProposalRepository.findById(id);
            if (opt.isEmpty()) return;

            CommercialProposal cp = opt.get();
            Short current = cp.getBlock();
            Short newBlock = (current != null && current == 1) ? (short) 0 : (short) 1;
            cp.setBlock(newBlock);
            cp.setEditDate(LocalDateTime.now());
            cp.setEditedBy(getCurrentUserId());
            commercialProposalRepository.save(cp);
        } catch (NumberFormatException ignored) {}
    }

    @Transactional
    public CpCloneResponse clone(CpCloneRequest req) {
        if (req == null || req.cprId() == null || req.cprId().isBlank()) {
            return new CpCloneResponse(null);
        }

        try {
            int srcId = Integer.parseInt(req.cprId().trim());
            if (commercialProposalRepository.findById(srcId).isEmpty()) {
                return new CpCloneResponse(null);
            }
            boolean oldVersion = "old".equalsIgnoreCase(req.mode());
            int usrId = getCurrentUserId();
            Integer newId = cpCloneHelper.clone(srcId, oldVersion, usrId);
            return new CpCloneResponse(newId != null ? String.valueOf(newId) : null);
        } catch (NumberFormatException e) {
            return new CpCloneResponse(null);
        }
    }

    private CpDataRequest defaultRequest() {
        return new CpDataRequest(
            "", null, null, null, null, "", "", null, null, false, false, 1, DEFAULT_PAGE_SIZE);
    }

    private CpListProvider.FilterParams toFilterParams(CpDataRequest req) {
        String depId = req.department() != null && req.department().id() != null && !req.department().id().isBlank() ? req.department().id().trim() : null;
        String ctrId = req.contractor() != null && req.contractor().id() != null && !req.contractor().id().isBlank() ? req.contractor().id().trim() : null;
        String usrId = req.user() != null && req.user().id() != null && !req.user().id().isBlank() ? req.user().id().trim() : null;
        String stfId = req.stuffCategory() != null && req.stuffCategory().id() != null && !req.stuffCategory().id().isBlank() ? req.stuffCategory().id().trim() : null;

        return new CpListProvider.FilterParams(
            req.cprNumber() != null ? req.cprNumber().trim() : null,
            depId, ctrId, usrId, stfId,
            CpListProvider.parseDate(req.cprDateFrom()),
            CpListProvider.parseDate(req.cprDateTo()),
            req.cprSumFrom(), req.cprSumTo(),
            req.cprProposalReceivedFlag(), req.cprProposalDeclined()
        );
    }

    private boolean canBlock() {
        if (currentUserProvider == null) return true;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.roles() == null) return true;
        return u.roles().stream().anyMatch(r -> "admin".equals(r) || "economist".equals(r));
    }

    private int getCurrentUserId() {
        if (currentUserProvider == null) return 1;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.id() == null || u.id().isBlank()) return 1;
        try {
            return Integer.parseInt(u.id().trim());
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
