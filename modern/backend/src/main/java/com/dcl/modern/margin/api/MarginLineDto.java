package com.dcl.modern.margin.api;

import com.dcl.modern.margin.domain.MarginLine;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "One row of the Margin report grid (CONTRACTS.md data[] item)")
public record MarginLineDto(
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
) {
    public static MarginLineDto from(MarginLine line) {
        return new MarginLineDto(
            line.ctr_name(), line.cut_name(), line.con_number_formatted(), line.con_date_formatted(),
            line.spc_number_formatted(), line.spc_date_formatted(), line.spc_summ_formatted(), line.cur_name(),
            line.stf_name_show(), line.shp_number_show(), line.shp_date_show(), line.pay_date_show(),
            line.lps_summ_eur_formatted(), line.lps_summ_formatted(), line.lps_sum_transport_formatted(), line.lcc_transport_formatted(),
            line.lps_custom_formatted(), line.lcc_charges_formatted(), line.lcc_montage_formatted(), line.lps_montage_time_formatted(),
            line.montage_cost_formatted(), line.lcc_update_sum_formatted(), line.summ_formatted(), line.summ_zak_formatted(),
            line.margin_formatted(), line.koeff_formatted(), line.usr_name_show(), line.dep_name_show(),
            line.itogLine(), line.spc_group_delivery(), line.haveUnblockedPrc()
        );
    }
}
