package com.dcl.modern.margin.domain;

/**
 * Column visibility flags for Margin grid.
 * Legacy: Margin session view_* toggles; CONTRACTS.md view schema.
 */
public record ViewFlags(
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
    public static ViewFlags allVisible() {
        return new ViewFlags(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }
}
