package com.dcl.modern.orders.application;

import java.math.BigDecimal;

/**
 * Filter params for Orders list. Maps to DCL_ORDER_FILTER args + pagination/sort.
 * Legacy: OrdersForm → select-orders.
 */
public record OrderFilterParams(
    String number,
    String date_begin,
    String date_end,
    String contractor_id,
    String contractor_for_id,
    String user_id,
    String department_id,
    String stuff_category_id,
    String contract_number,
    String specification_number,
    String seller_for_who_id,
    BigDecimal sum_min,
    BigDecimal sum_max,
    Boolean executed,
    Boolean not_executed,
    Boolean ord_ready_for_deliv,
    Boolean ord_annul_not_show,
    Boolean state_a,
    Boolean state_3,
    Boolean state_b,
    Boolean state_exclamation,
    Boolean state_c,
    String ord_num_conf,
    int page,
    int pageSize,
    String order_by
) {
    /** Default sort after "Apply filter": ord_date descending. CONTRACTS. */
    public static final String DEFAULT_ORDER_AFTER_FILTER = "ord_date descending";
    /** Initial sort: ord_ready_for_deliv desc, ord_date desc, ord_number desc. ACCEPTANCE A.4 */
    public static final String DEFAULT_ORDER_INITIAL = "ord_ready_for_deliv desc, ord_date desc, ord_number desc";
}
