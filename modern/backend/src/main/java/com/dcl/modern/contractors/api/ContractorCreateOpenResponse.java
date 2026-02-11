package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contractors/create/open response. N3a1 CONTRACTS.
 * docs/screens/contractor_create/CONTRACTS.md.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorCreateOpenResponse(
    ContractorCreateDefaultsDto defaults,
    ContractorCreateLookupsDto lookups,
    String returnTo
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ContractorCreateDefaultsDto(
        String ctrName,
        String ctrFullName,
        LookupItemDto country,
        String ctrIndex,
        String ctrRegion,
        String ctrPlace,
        String ctrStreet,
        String ctrBuilding,
        String ctrAddInfo,
        String ctrPhone,
        String ctrFax,
        String ctrEmail,
        String ctrUnp,
        String ctrOkpo,
        LookupItemDto reputation,
        List<ContractorUserRowDto> gridUsers,
        List<ContractorAccountRowDto> gridAccounts,
        List<Object> gridContactPersons,
        String ctrBankProps,
        String ctrComment
    ) {}

    public record ContractorCreateLookupsDto(
        List<LookupItemDto> countries,
        List<LookupItemDto> reputations,
        List<UserLookupDto> users,
        List<LookupItemDto> currencies
    ) {}

    public record ContractorUserRowDto(String usrId, String userFullName) {}
    public record ContractorAccountRowDto(String accName, String accAccount, LookupItemDto currency) {}
}
