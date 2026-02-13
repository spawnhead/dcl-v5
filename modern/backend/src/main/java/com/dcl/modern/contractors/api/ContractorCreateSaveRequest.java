package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * POST /api/contractors/create/save body. N3a1 CONTRACTS.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorCreateSaveRequest(
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
    List<ContractorCreateOpenResponse.ContractorUserRowDto> gridUsers,
    List<ContractorCreateOpenResponse.ContractorAccountRowDto> gridAccounts,
    List<ContractorCreateOpenResponse.ContractorContactPersonRowDto> gridContactPersons,
    String ctrBankProps,
    String ctrComment,
    String returnTo
) {}
