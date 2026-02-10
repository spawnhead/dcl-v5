package com.dcl.modern.margin.domain;

/**
 * One row of the Margin report grid.
 * Legacy: MarginDevDataAction / MarginLine JSON; CONTRACTS.md grid response data[].
 */
public record MarginLine(
    String ctr_name,
    String cut_name,
    String con_number_formatted,
    String con_date_formatted,
    String spc_number_formatted,
    String spc_date_formatted,
    String spc_summ_formatted,
    String cur_name,
    String stf_name_show,
    String shp_number_show,
    String shp_date_show,
    String pay_date_show,
    String lps_summ_eur_formatted,
    String lps_summ_formatted,
    String lps_sum_transport_formatted,
    String lcc_transport_formatted,
    String lps_custom_formatted,
    String lcc_charges_formatted,
    String lcc_montage_formatted,
    String lps_montage_time_formatted,
    String montage_cost_formatted,
    String lcc_update_sum_formatted,
    String summ_formatted,
    String summ_zak_formatted,
    String margin_formatted,
    String koeff_formatted,
    String usr_name_show,
    String dep_name_show,
    boolean itogLine,
    String spc_group_delivery,
    boolean haveUnblockedPrc
) {}
