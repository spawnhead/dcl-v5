package com.dcl.modern.commercialproposals.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/** POST /api/commercial-proposals/data body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CpDataRequest(
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
    Boolean cprProposalDeclined,
    Integer page,
    Integer pageSize
) {}
