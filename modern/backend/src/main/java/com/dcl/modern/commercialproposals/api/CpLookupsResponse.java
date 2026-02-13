package com.dcl.modern.commercialproposals.api;

import java.util.List;

/** GET /api/commercial-proposals/lookups response. */
public record CpLookupsResponse(
    DefaultsDto defaults,
    LookupsDto lookups
) {
    public record DefaultsDto(
        String cprNumber,
        LookupItemDto department,
        LookupItemDto contractor,
        LookupItemDto user,
        LookupItemDto stuffCategory,
        String cprDateFrom,
        String cprDateTo,
        Double cprSumFrom,
        Double cprSumTo,
        Boolean cprProposalReceivedFlag,
        Boolean cprProposalDeclined
    ) {}

    public record LookupsDto(
        List<LookupItemDto> departments,
        List<LookupItemDto> contractors,
        List<LookupItemDto> users,
        List<LookupItemDto> stuffCategories
    ) {}
}
