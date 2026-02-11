package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/** POST /api/contracts/data body. CONTRACTS §1.2. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractDataRequest(
    String number,
    LookupItemDto contractor,
    String dateBegin,
    String dateEnd,
    Double sumMin,
    Double sumMax,
    LookupItemDto user,
    LookupItemDto seller,
    Boolean executed,
    Boolean notExecuted,
    Boolean oridinalAbsent,
    Integer page,
    Integer pageSize
) {}
