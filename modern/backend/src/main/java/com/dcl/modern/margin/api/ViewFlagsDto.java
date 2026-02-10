package com.dcl.modern.margin.api;

import com.dcl.modern.margin.domain.ViewFlags;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Column visibility flags for Margin grid (CONTRACTS.md view)")
public record ViewFlagsDto(
    boolean view_contractor,
    boolean view_country,
    boolean view_contract,
    boolean view_stuff_category,
    boolean view_shipping,
    boolean view_payment,
    boolean view_transport,
    boolean view_transport_sum,
    boolean view_custom,
    boolean view_other_sum,
    boolean view_montage_sum,
    boolean view_montage_time,
    boolean view_montage_cost,
    boolean view_update_sum,
    boolean view_summ_zak,
    boolean view_koeff,
    boolean view_user,
    boolean view_department
) {
    public static ViewFlagsDto from(ViewFlags v) {
        return new ViewFlagsDto(
            v.view_contractor(), v.view_country(), v.view_contract(), v.view_stuff_category(),
            v.view_shipping(), v.view_payment(), v.view_transport(), v.view_transport_sum(),
            v.view_custom(), v.view_other_sum(), v.view_montage_sum(), v.view_montage_time(),
            v.view_montage_cost(), v.view_update_sum(), v.view_summ_zak(), v.view_koeff(),
            v.view_user(), v.view_department()
        );
    }
}
