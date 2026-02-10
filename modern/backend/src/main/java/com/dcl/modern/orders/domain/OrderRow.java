package com.dcl.modern.orders.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * One row of Orders list grid. DCL_ORDER_FILTER RETURNS shape.
 * Legacy: OrdersForm.Order, sql-resources select-orders.
 */
public record OrderRow(
    int ord_id,
    String ord_number,
    LocalDate ord_date,
    String ord_contractor,
    String ord_contractor_for,
    BigDecimal ord_summ,
    LocalDate ord_date_conf,
    LocalDate ord_sent_to_prod_date,
    LocalDate ord_received_conf_date,
    LocalDate ord_conf_sent_date,
    LocalDate ord_ready_for_deliv_date,
    int ord_ready_for_deliv,
    LocalDate ord_executed_date,
    String ord_user,
    String ord_department,
    String is_warn,
    String ord_block,
    int ord_annul,
    String ord_num_conf,
    Integer dep_id,
    int ord_link_to_spec,
    int ord_comment_flag,
    String have_empty_date_conf,
    Integer count_day_curr_minus_sent,
    LocalDate ord_ship_from_stock,
    LocalDate ord_arrive_in_lithuania,
    String usr_id_create
) {}
