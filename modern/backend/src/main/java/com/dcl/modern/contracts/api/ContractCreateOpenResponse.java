package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contracts/create/open response. N3a CONTRACTS §1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractCreateOpenResponse(
    ContractCreateDefaultsDto defaults,
    ContractCreateLookupsDto lookups,
    boolean canCreate
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ContractCreateDefaultsDto(
        String conNumber,
        String conDate,
        Boolean conReusable,
        String conFinalDate,
        LookupItemDto contractor,
        LookupItemDto currency,
        Boolean conFaxCopy,
        Boolean conOriginal,
        LookupItemDto seller,
        Boolean conAnnul,
        String conAnnulDate,
        String conComment,
        Boolean isNewDoc,
        String conExecuted
    ) {}

    public record ContractCreateLookupsDto(
        List<LookupItemDto> contractors,
        List<LookupItemDto> currencies,
        List<LookupItemDto> sellers
    ) {}
}
