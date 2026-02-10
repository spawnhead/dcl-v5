package com.dcl.modern.orders.application;

import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.orders.api.LookupItemDto;
import com.dcl.modern.orders.api.OrderListResponse;
import com.dcl.modern.orders.api.OrderRowDto;
import com.dcl.modern.orders.domain.OrderRow;
import com.dcl.modern.orders.infrastructure.OrderFilterFakeProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Orders list use cases. Legacy: OrdersAction (input, filter, internalFilter, reload, block).
 * CONTRACTS: docs/screens/orders/CONTRACTS.md. Data: fake deterministic until DCL_ORDER_FILTER in Postgres.
 */
@Service
public class OrdersService {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final List<OrderRow> ALL_ROWS = OrderFilterFakeProvider.buildRows();

    /** List with filter/sort/pagination. Enriches rows with can_edit_clone, can_block per ROLE_MODEL. */
    public OrderListResponse list(OrderFilterParams params, Optional<CurrentUser> currentUser) {
        int page = Math.max(1, params.page());
        int pageSize = Math.min(100, Math.max(1, params.pageSize()));
        String orderBy = params.order_by() != null && !params.order_by().isBlank()
            ? params.order_by()
            : OrderFilterParams.DEFAULT_ORDER_AFTER_FILTER;

        List<OrderRow> pageRows = OrderFilterFakeProvider.filterSortPage(
            ALL_ROWS,
            params.number(),
            params.date_begin(),
            params.date_end(),
            params.contractor_id(),
            params.contractor_for_id(),
            params.user_id(),
            params.department_id(),
            orderBy,
            page,
            pageSize
        );
        long total = OrderFilterFakeProvider.countFiltered(
            ALL_ROWS,
            params.number(),
            params.date_begin(),
            params.date_end(),
            params.contractor_id(),
            params.contractor_for_id(),
            params.user_id(),
            params.department_id()
        );

        boolean canBlock = currentUser.map(this::isAdmin).orElse(false);
        boolean onlyManager = currentUser.map(this::isOnlyManager).orElse(false);
        String userDepId = currentUser.map(CurrentUser::departmentId).orElse(null);

        List<OrderRowDto> items = pageRows.stream()
            .map(row -> toDto(row, onlyManager, userDepId, canBlock))
            .toList();

        return new OrderListResponse(items, total, page, pageSize);
    }

    public List<LookupItemDto> getContractors(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItemDto("5001", "ООО Контрагент-A (DEV)"),
            new LookupItemDto("5002", "ООО Контрагент-B (DEV)"),
            new LookupItemDto("5003", "ООО ТестКонтрагент (DEV)")
        ), filter, haveAll);
    }

    public List<LookupItemDto> getContractorsFor(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItemDto("5001", "Клиент 1"),
            new LookupItemDto("5002", "Клиент 2"),
            new LookupItemDto("5003", "Клиент 3")
        ), filter, haveAll);
    }

    public List<LookupItemDto> getUsers(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItemDto("1001", "dev_admin (DEV)"),
            new LookupItemDto("1002", "dev_manager (DEV)"),
            new LookupItemDto("1003", "dev_manager_chief (DEV)"),
            new LookupItemDto("1004", "dev_economist (DEV)")
        ), filter, haveAll);
    }

    public List<LookupItemDto> getDepartments(boolean haveAll) {
        List<LookupItemDto> out = new ArrayList<>();
        if (haveAll) {
            out.add(new LookupItemDto("", "— Все —"));
        }
        out.add(new LookupItemDto("2001", "Отдел продаж (DEV)"));
        out.add(new LookupItemDto("2002", "Экономический отдел (DEV)"));
        return out;
    }

    public List<LookupItemDto> getStuffCategories(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItemDto("4001", "Категория-A (DEV)"),
            new LookupItemDto("4002", "Категория-B (DEV)")
        ), filter, haveAll);
    }

    public List<LookupItemDto> getSellers(boolean haveAll) {
        List<LookupItemDto> out = new ArrayList<>();
        if (haveAll) {
            out.add(new LookupItemDto("", "— Все —"));
        }
        out.add(new LookupItemDto("6001", "Продавец 1 (DEV)"));
        out.add(new LookupItemDto("6002", "Продавец 2 (DEV)"));
        return out;
    }

    /** CONTRACTS: contracts by contractor (contractor_for in legacy). */
    public List<LookupItemDto> getContracts(String contractorId, boolean haveAll) {
        List<LookupItemDto> out = new ArrayList<>();
        if (haveAll) {
            out.add(new LookupItemDto("", "— Все —"));
        }
        if (contractorId != null && !contractorId.isBlank()) {
            out.add(new LookupItemDto("7001", "Дог.-" + contractorId + " (DEV)"));
            out.add(new LookupItemDto("7002", "Дог.-B (DEV)"));
        }
        return out;
    }

    /** CONTRACTS: specifications by contract + contractor. */
    public List<LookupItemDto> getSpecifications(String contractId, String contractorId, boolean haveAll) {
        List<LookupItemDto> out = new ArrayList<>();
        if (haveAll) {
            out.add(new LookupItemDto("", "— Все —"));
        }
        if (contractId != null && !contractId.isBlank()) {
            out.add(new LookupItemDto("8001", "Спец.-" + contractId + " (DEV)"));
            out.add(new LookupItemDto("8002", "Спец.-2 (DEV)"));
        }
        return out;
    }

    private static List<LookupItemDto> filterLookup(List<LookupItemDto> items, String filter, boolean haveAll) {
        List<LookupItemDto> out = new ArrayList<>();
        if (haveAll) {
            out.add(new LookupItemDto("", "— Все —"));
        }
        String f = filter != null ? filter.trim().toLowerCase(Locale.ROOT) : "";
        for (LookupItemDto item : items) {
            if (f.isEmpty() || item.name().toLowerCase(Locale.ROOT).contains(f)) {
                out.add(item);
            }
        }
        return out;
    }

    private boolean isAdmin(CurrentUser u) {
        return u.roles() != null && u.roles().contains("admin");
    }

    private boolean isOnlyManager(CurrentUser u) {
        if (u.roles() == null) return false;
        boolean manager = u.roles().contains("manager");
        boolean admin = u.roles().contains("admin");
        boolean economist = u.roles().contains("economist");
        return manager && !admin && !economist;
    }

    private OrderRowDto toDto(OrderRow row, boolean onlyManager, String userDepId, boolean canBlock) {
        boolean canEditClone = !onlyManager || (row.dep_id() != null && userDepId != null && row.dep_id().toString().equals(userDepId));
        return new OrderRowDto(
            row.ord_id(),
            row.ord_number(),
            row.ord_date() != null ? row.ord_date().format(ISO) : null,
            row.ord_contractor(),
            row.ord_contractor_for(),
            row.ord_summ(),
            row.ord_date_conf() != null ? row.ord_date_conf().format(ISO) : null,
            row.ord_sent_to_prod_date() != null ? row.ord_sent_to_prod_date().format(ISO) : null,
            row.ord_received_conf_date() != null ? row.ord_received_conf_date().format(ISO) : null,
            row.ord_conf_sent_date() != null ? row.ord_conf_sent_date().format(ISO) : null,
            row.ord_ready_for_deliv_date() != null ? row.ord_ready_for_deliv_date().format(ISO) : null,
            row.ord_ready_for_deliv(),
            row.ord_executed_date() != null ? row.ord_executed_date().format(ISO) : null,
            row.ord_user(),
            row.ord_department(),
            row.is_warn(),
            row.ord_block(),
            row.ord_annul(),
            row.ord_num_conf(),
            row.dep_id(),
            row.ord_link_to_spec(),
            row.ord_comment_flag(),
            row.have_empty_date_conf(),
            row.count_day_curr_minus_sent(),
            row.ord_ship_from_stock() != null ? row.ord_ship_from_stock().format(ISO) : null,
            row.ord_arrive_in_lithuania() != null ? row.ord_arrive_in_lithuania().format(ISO) : null,
            row.usr_id_create(),
            canEditClone,
            canBlock
        );
    }
}
