package com.dcl.modern.orders.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Single order row in list response. DCL_ORDER_FILTER RETURNS + can_edit_clone, can_block (from editCloneChecker, blockChecker).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderRowDto(
    int ord_id,
    String ord_number,
    String ord_date,
    String ord_contractor,
    String ord_contractor_for,
    BigDecimal ord_summ,
    String ord_date_conf,
    String ord_sent_to_prod_date,
    String ord_received_conf_date,
    String ord_conf_sent_date,
    String ord_ready_for_deliv_date,
    int ord_ready_for_deliv,
    String ord_executed_date,
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
    String ord_ship_from_stock,
    String ord_arrive_in_lithuania,
    String usr_id_create,
    Boolean can_edit_clone,
    Boolean can_block
) {}
