package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contractors/create/open response. N3a1 CONTRACTS.
 * docs/screens/contractor_create/CONTRACTS.md.
 * TASK-0013: tabs metadata per SNAPSHOT 5 tabs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorCreateOpenResponse(
    ContractorCreateDefaultsDto defaults,
    ContractorCreateLookupsDto lookups,
    List<TabMetaDto> tabs,
    String activeTab,
    String returnTo
) {
    /** Tab metadata for UI. SNAPSHOT §2. */
    public record TabMetaDto(String id, String label) {}
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
        List<ContractorContactPersonRowDto> gridContactPersons,
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
    /** Contact person row. SNAPSHOT §4.4 gridContactPersons. Persistence deferred (no dcl_contact_person migration yet). */
    public record ContractorContactPersonRowDto(
        String cpsName,
        String cpsPosition,
        String cpsOnReason,
        String cpsPhone,
        String cpsMobPhone,
        String cpsFax,
        String cpsEmail,
        String cpsContractComment,
        String cpsFire,
        String cpsBlock
    ) {}
}
