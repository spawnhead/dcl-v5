package com.dcl.modern.orders.api;

import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import com.dcl.modern.orders.application.OrderEditService;
import com.dcl.modern.orders.application.OrderFilterParams;
import com.dcl.modern.orders.application.OrdersService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for Orders list (N2). Legacy: OrdersAction (input, filter, grid, block).
 * CONTRACTS: docs/screens/orders/CONTRACTS.md. SNAPSHOT: editCloneChecker, blockChecker.
 */
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService service;
    private final OrderEditService editService;

    @Autowired(required = false)
    private CurrentUserProvider currentUserProvider;

    public OrdersController(OrdersService service, OrderEditService editService) {
        this.service = service;
        this.editService = editService;
    }

    private Optional<CurrentUser> currentUser() {
        return currentUserProvider != null ? Optional.of(currentUserProvider.getCurrentUser()) : Optional.empty();
    }

    @GetMapping
    public OrderListResponse list(
        @RequestParam(required = false) String number,
        @RequestParam(required = false) String date_begin,
        @RequestParam(required = false) String date_end,
        @RequestParam(required = false) String contractor_id,
        @RequestParam(required = false) String contractor_for_id,
        @RequestParam(required = false) String user_id,
        @RequestParam(required = false) String department_id,
        @RequestParam(required = false) String stuff_category_id,
        @RequestParam(required = false) String contract_number,
        @RequestParam(required = false) String specification_number,
        @RequestParam(required = false) String seller_for_who_id,
        @RequestParam(required = false) BigDecimal sum_min,
        @RequestParam(required = false) BigDecimal sum_max,
        @RequestParam(required = false) Boolean executed,
        @RequestParam(required = false) Boolean not_executed,
        @RequestParam(required = false) Boolean ord_ready_for_deliv,
        @RequestParam(required = false) Boolean ord_annul_not_show,
        @RequestParam(required = false) Boolean state_a,
        @RequestParam(required = false) Boolean state_3,
        @RequestParam(required = false) Boolean state_b,
        @RequestParam(required = false) Boolean state_exclamation,
        @RequestParam(required = false) Boolean state_c,
        @RequestParam(required = false) String ord_num_conf,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "25") int pageSize,
        @RequestParam(required = false) String order_by
    ) {
        OrderFilterParams params = new OrderFilterParams(
            number, date_begin, date_end,
            contractor_id, contractor_for_id, user_id, department_id, stuff_category_id,
            contract_number, specification_number, seller_for_who_id,
            sum_min, sum_max,
            executed, not_executed, ord_ready_for_deliv, ord_annul_not_show,
            state_a, state_3, state_b, state_exclamation, state_c,
            ord_num_conf,
            page, pageSize,
            order_by
        );
        return service.list(params, currentUser());
    }

    @GetMapping("/lookups/contractors")
    public List<LookupItemDto> getContractors(
        @RequestParam(required = false) String filter,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getContractors(filter, have_all);
    }

    @GetMapping("/lookups/users")
    public List<LookupItemDto> getUsers(
        @RequestParam(required = false) String filter,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getUsers(filter, have_all);
    }

    @GetMapping("/lookups/departments")
    public List<LookupItemDto> getDepartments(
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getDepartments(have_all);
    }

    @GetMapping("/lookups/stuff-categories")
    public List<LookupItemDto> getStuffCategories(
        @RequestParam(required = false) String filter,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getStuffCategories(filter, have_all);
    }

    @GetMapping("/lookups/sellers")
    public List<LookupItemDto> getSellers(
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getSellers(have_all);
    }

    @GetMapping("/lookups/contracts")
    public List<LookupItemDto> getContracts(
        @RequestParam(required = false) String contractor_id,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getContracts(contractor_id, have_all);
    }

    @GetMapping("/lookups/specifications")
    public List<LookupItemDto> getSpecifications(
        @RequestParam(required = false) String contract_id,
        @RequestParam(required = false) String contractor_id,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getSpecifications(contract_id, contractor_id, have_all);
    }

    /** Order edit open. Legacy: OrderAction input (new) / edit (existing). CONTRACTS: docs/screens/order_edit. */
    @GetMapping("/edit/open")
    public OrderEditOpenResponse editOpen(@RequestParam(required = false) Integer ordId) {
        return editService.open(Optional.ofNullable(ordId));
    }

    /** Order save (create). Legacy: OrderAction process, is_new_doc=true. */
    @PostMapping("/edit/save")
    public OrderEditSaveResponse editSaveCreate(@RequestBody OrderEditSaveRequest request) {
        if (!request.isNewDoc()) {
            throw new IllegalArgumentException("Use PUT for update");
        }
        return editService.save(request);
    }

    /** Order save (update). Legacy: OrderAction process, is_new_doc=empty. */
    @PutMapping("/edit/save")
    public OrderEditSaveResponse editSaveUpdate(@RequestBody OrderEditSaveRequest request) {
        if (request.isNewDoc()) {
            throw new IllegalArgumentException("Use POST for create");
        }
        return editService.save(request);
    }
}
