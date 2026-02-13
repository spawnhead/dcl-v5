package com.dcl.modern.contracts.application;

import com.dcl.modern.contractors.domain.Contractor;
import com.dcl.modern.contractors.infrastructure.ContractorRepository;
import com.dcl.modern.contracts.api.*;
import com.dcl.modern.contracts.domain.ContractRow;
import com.dcl.modern.contracts.infrastructure.ContractListProvider;
import com.dcl.modern.contracts.infrastructure.ContractsFakeProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Contracts list use cases. Legacy: ContractsAction (input, filter, grid, cleanAll).
 * CONTRACTS: docs/screens/contracts/CONTRACTS.md.
 * TASK-0024: List reads from Postgres (ContractListProvider) so newly created contracts appear.
 */
@Service
public class ContractsService {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final List<SortItemDto> FIXED_SORT = List.of(
        new SortItemDto("con_reminder", "DESC"),
        new SortItemDto("con_date", "DESC"),
        new SortItemDto("con_number", "DESC")
    );

    private final ContractListProvider contractListProvider;
    private final ContractorRepository contractorRepository;

    public ContractsService(ContractListProvider contractListProvider, ContractorRepository contractorRepository) {
        this.contractListProvider = contractListProvider;
        this.contractorRepository = contractorRepository;
    }

    public ContractsLookupsResponse getLookups() {
        DefaultsDto defaults = new DefaultsDto(
            "",
            "",
            "",
            null,
            null,
            false,
            false,
            false,
            null,
            null,
            null
        );
        var contractors = contractorRepository.findAllByOrderByNameAsc().stream()
            .map(c -> new LookupItemDto(String.valueOf(c.getId()), c.getName()))
            .toList();
        ContractsLookupsResponse.LookupsDto lookups = new ContractsLookupsResponse.LookupsDto(
            contractors,
            ContractsFakeProvider.getUsersLookup(),
            ContractsFakeProvider.getSellersLookup()
        );
        return new ContractsLookupsResponse(defaults, lookups);
    }

    public ContractDataResponse getData(ContractDataRequest req) {
        int page = req.page() != null && req.page() >= 1 ? req.page() : 1;
        int pageSize = req.pageSize() != null && req.pageSize() >= 1 ? Math.min(100, req.pageSize()) : 15;
        String contractorId = req.contractor() != null ? req.contractor().id() : null;
        String contractorNameForFilter = resolveContractorName(contractorId);
        String userId = req.user() != null ? req.user().id() : null;
        String sellerId = req.seller() != null ? req.seller().id() : null;
        String conExecutedFilter = toConExecutedFilter(req.executed(), req.notExecuted());

        List<ContractRow> allRows = contractListProvider.loadAll();
        List<ContractRow> pageRows = ContractsFakeProvider.filterAndPage(
            allRows,
            req.number(),
            contractorId,
            contractorNameForFilter,
            ContractsFakeProvider.parseDate(req.dateBegin()),
            ContractsFakeProvider.parseDate(req.dateEnd()),
            req.sumMin(),
            req.sumMax(),
            userId,
            sellerId,
            conExecutedFilter,
            req.oridinalAbsent(),
            page,
            pageSize
        );
        long total = ContractsFakeProvider.countFiltered(
            allRows,
            req.number(),
            contractorId,
            contractorNameForFilter,
            ContractsFakeProvider.parseDate(req.dateBegin()),
            ContractsFakeProvider.parseDate(req.dateEnd()),
            req.sumMin(),
            req.sumMax(),
            userId,
            sellerId,
            conExecutedFilter,
            req.oridinalAbsent()
        );
        boolean hasNextPage = (long) page * pageSize < total;
        List<ContractRowDto> items = pageRows.stream().map(this::toDto).toList();
        return new ContractDataResponse(items, page, pageSize, hasNextPage, FIXED_SORT);
    }

    public ContractDataResponse getPage(PageRequestDto req) {
        if (req.filterState() == null) {
            return getData(new ContractDataRequest(null, null, "", "", null, null, null, null, null, null, null, 1, 15));
        }
        int current = req.currentPage() != null && req.currentPage() >= 1 ? req.currentPage() : 1;
        int pageSize = req.filterState().pageSize() != null ? Math.min(100, Math.max(1, req.filterState().pageSize())) : 15;
        int nextPage = "next".equalsIgnoreCase(req.direction()) ? current + 1 : "prev".equalsIgnoreCase(req.direction()) ? Math.max(1, current - 1) : current;
        ContractDataRequest dataReq = new ContractDataRequest(
            req.filterState().number(),
            req.filterState().contractor(),
            req.filterState().dateBegin(),
            req.filterState().dateEnd(),
            req.filterState().sumMin(),
            req.filterState().sumMax(),
            req.filterState().user(),
            req.filterState().seller(),
            req.filterState().executed(),
            req.filterState().notExecuted(),
            req.filterState().oridinalAbsent(),
            nextPage,
            pageSize
        );
        return getData(dataReq);
    }

    public CleanAllResponse cleanAll() {
        DefaultsDto defaults = new DefaultsDto("", "", "", null, null, false, false, false, null, null, null);
        ContractDataResponse grid = getData(new ContractDataRequest(null, null, "", "", null, null, null, null, null, null, null, 1, 15));
        return new CleanAllResponse(defaults, grid);
    }

    private String resolveContractorName(String contractorId) {
        if (contractorId == null || contractorId.isBlank()) return null;
        try {
            return contractorRepository.findById(Integer.parseInt(contractorId.trim()))
                .map(Contractor::getName)
                .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** executed=true && !notExecuted -> "1"; !executed && notExecuted=true -> "0"; else null. */
    private static String toConExecutedFilter(Boolean executed, Boolean notExecuted) {
        if (Boolean.TRUE.equals(executed) && !Boolean.TRUE.equals(notExecuted)) return "1";
        if (!Boolean.TRUE.equals(executed) && Boolean.TRUE.equals(notExecuted)) return "0";
        return null;
    }

    private ContractRowDto toDto(ContractRow r) {
        String conDate = r.conDate() != null ? r.conDate().format(DD_MM_YYYY) : "";
        return new ContractRowDto(
            r.conId(),
            r.conNumber(),
            conDate,
            r.conContractor(),
            r.conSumm(),
            r.conCurrency(),
            r.notes(),
            r.conExecuted(),
            r.conUser(),
            r.conReminder(),
            r.conAnnul(),
            r.attachIdx(),
            r.spcCount(),
            r.usrIdList(),
            r.depIdList()
        );
    }
}
