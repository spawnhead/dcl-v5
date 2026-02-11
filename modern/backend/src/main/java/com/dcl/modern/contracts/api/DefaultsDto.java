package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Default filter values. CONTRACTS §1.1. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DefaultsDto(
    String number,
    String dateBegin,
    String dateEnd,
    Double sumMin,
    Double sumMax,
    Boolean executed,
    Boolean notExecuted,
    Boolean oridinalAbsent,
    LookupItemDto contractor,
    LookupItemDto user,
    LookupItemDto seller
) {}
