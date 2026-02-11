package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * POST /api/contracts/draft/specifications/save 200. N3a2 CONTRACTS.
 * specification — row for client to add to grid (optional).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpecCreateSaveResponse(
    boolean success,
    String redirectTo,
    SpecRowDto specification
) {
    /** One row for specifications grid on contract form. */
    public record SpecRowDto(
        String spcNumber,
        String spcDate,
        String spcSummFormatted,
        String spcNdsRateFormatted,
        String spcSummNdsFormatted,
        String spcRemainder,
        String spcExecuted
    ) {}
}
