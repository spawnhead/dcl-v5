package com.dcl.modern.orders.application;

import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.orders.api.LookupItemDto;
import com.dcl.modern.orders.api.OrderListResponse;
import com.dcl.modern.orders.api.OrderRowDto;
import com.dcl.modern.orders.domain.OrderRow;
import com.dcl.modern.orders.infrastructure.OrderListProvider;
import com.dcl.modern.orders.infrastructure.OrderLookupsRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Orders list use cases. Legacy: OrdersAction (input, filter, internalFilter, reload, block).
 * CONTRACTS: docs/screens/orders/CONTRACTS.md. Data: Postgres via OrderListProvider.
 */
@Service
public class OrdersService {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

    private final OrderListProvider listProvider;
    private final OrderLookupsRepository lookupsRepository;

    public OrdersService(OrderListProvider listProvider, OrderLookupsRepository lookupsRepository) {
        this.listProvider = listProvider;
        this.lookupsRepository = lookupsRepository;
    }

    /** List with filter/sort/pagination. Enriches rows with can_edit_clone, can_block per ROLE_MODEL. */
    public OrderListResponse list(OrderFilterParams params, Optional<CurrentUser> currentUser) {
        OrderListProvider.Result result = listProvider.list(params);

        boolean canBlock = currentUser.map(this::isAdmin).orElse(false);
        boolean onlyManager = currentUser.map(this::isOnlyManager).orElse(false);
        String userDepId = currentUser.map(CurrentUser::departmentId).orElse(null);

        List<OrderRowDto> items = result.items().stream()
            .map(row -> toDto(row, onlyManager, userDepId, canBlock))
            .toList();

        return new OrderListResponse(items, result.total(), result.page(), result.pageSize());
    }

    public List<LookupItemDto> getContractors(String filter, boolean haveAll) {
        return withAll(lookupsRepository.getContractors(), haveAll);
    }

    public List<LookupItemDto> getContractorsFor(String filter, boolean haveAll) {
        return withAll(lookupsRepository.getContractors(), haveAll);
    }

    public List<LookupItemDto> getUsers(String filter, boolean haveAll) {
        return withAll(lookupsRepository.getUsers(filter), haveAll);
    }

    public List<LookupItemDto> getDepartments(boolean haveAll) {
        return withAll(lookupsRepository.getDepartments(), haveAll);
    }

    public List<LookupItemDto> getStuffCategories(String filter, boolean haveAll) {
        return withAll(lookupsRepository.getStuffCategories(), haveAll);
    }

    public List<LookupItemDto> getSellers(boolean haveAll) {
        return withAll(lookupsRepository.getSellers(), haveAll);
    }

    public List<LookupItemDto> getContracts(String contractorId, boolean haveAll) {
        return withAll(lookupsRepository.getContracts(contractorId), haveAll);
    }

    public List<LookupItemDto> getSpecifications(String contractId, String contractorId, boolean haveAll) {
        return withAll(lookupsRepository.getSpecifications(contractId, contractorId), haveAll);
    }

    private static List<LookupItemDto> withAll(List<LookupItemDto> items, boolean haveAll) {
        if (!haveAll) return items;
        List<LookupItemDto> out = new ArrayList<>();
        out.add(new LookupItemDto("", "— Все —"));
        out.addAll(items);
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
