package com.dcl.modern.contracts.api;

import java.util.List;

/** GET /api/contracts/lookups response. CONTRACTS §1.1. */
public record ContractsLookupsResponse(
    DefaultsDto defaults,
    LookupsDto lookups
) {
    public record LookupsDto(
        List<LookupItemDto> contractors,
        List<LookupItemDto> users,
        List<LookupItemDto> sellers
    ) {}
}
