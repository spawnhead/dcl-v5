package com.dcl.modern.contractors.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/** POST /api/contractors/data body. CONTRACTS §2. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorDataRequest(
    String ctrName,
    String ctrFullName,
    String ctrAccount,
    String ctrAddress,
    String ctrEmail,
    String ctrUnp,
    LookupItemDto user,
    LookupItemDto department,
    Integer page,
    Integer pageSize
) {}
