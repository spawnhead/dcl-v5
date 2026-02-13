package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contractors/{ctrId}/edit/open response.
 * CONTRACTS: docs/screens/contractor_edit/CONTRACTS.md §1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorEditOpenResponse(
    String ctrId,
    boolean isNewDoc,
    String ctrName,
    String ctrFullName,
    LookupItemDto country,
    String ctrIndex,
    String ctrRegion,
    String ctrPlace,
    String ctrStreet,
    String ctrBuilding,
    String ctrAddInfo,
    String ctrAddress,
    String ctrPhone,
    String ctrFax,
    String ctrEmail,
    String ctrUnp,
    String ctrOkpo,
    String ctrBankProps,
    String ctrComment,
    LookupItemDto reputation,
    String ctrBlock,
    boolean formReadOnly,
    String usrDateCreate,
    String usrDateEdit,
    UserRefDto createUser,
    UserRefDto editUser,
    List<ContractorEditUserRowDto> gridUsers,
    List<ContractorEditAccountRowDto> gridAccounts,
    List<ContractorEditContactPersonRowDto> gridContactPersons,
    String activeTab,
    String returnTo,
    ContractorEditLookupsDto lookups,
    ContractorEditRoleFlagsDto roleFlags
) {
    public record UserRefDto(String usrId, String userFullName) {}
    public record ContractorEditUserRowDto(String number, UserRefDto user) {}
    public record ContractorEditAccountRowDto(String number, String accName, String accAccount, LookupItemDto currency) {}
    public record ContractorEditContactPersonRowDto(
        String number,
        String cpsId,
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
    public record ContractorEditLookupsDto(
        List<LookupItemDto> countries,
        List<LookupItemDto> reputations,
        List<LookupItemDto> users,
        List<LookupItemDto> currencies
    ) {}
    public record ContractorEditRoleFlagsDto(
        boolean adminRole,
        boolean readOnlyReputation,
        boolean readOnlyComment,
        boolean canDelete,
        boolean occupied
    ) {}
}
