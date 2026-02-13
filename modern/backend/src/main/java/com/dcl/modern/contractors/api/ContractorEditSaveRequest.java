package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * PUT /api/contractors/{ctrId}/edit/save body.
 * CONTRACTS: docs/screens/contractor_edit/CONTRACTS.md §2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorEditSaveRequest(
    String ctrId,
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
    String ctrBankProps,
    String ctrComment,
    LookupItemDto reputation,
    List<ContractorEditOpenResponse.ContractorEditUserRowDto> gridUsers,
    List<ContractorEditOpenResponse.ContractorEditAccountRowDto> gridAccounts,
    List<ContractorEditContactPersonSaveDto> gridContactPersons,
    String returnTo
) {
    public record ContractorEditContactPersonSaveDto(
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
}
