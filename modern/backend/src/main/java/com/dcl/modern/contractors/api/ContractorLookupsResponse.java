package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/** GET /api/contractors/lookups response. CONTRACTS §1. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorLookupsResponse(
    DefaultsDto defaults,
    LookupsDto lookups,
    boolean canCreate
) {
    public record DefaultsDto(
        String ctrName,
        String ctrFullName,
        String ctrAccount,
        String ctrAddress,
        String ctrEmail,
        String ctrUnp,
        LookupItemDto user,
        LookupItemDto department
    ) {}

    public record LookupsDto(
        java.util.List<LookupItemDto> users,
        java.util.List<LookupItemDto> departments
    ) {}
}
