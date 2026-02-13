package com.dcl.modern.commercialproposals.api;

/** Grid row for CP list. */
public record CpRowDto(
    String cprId,
    String cprNumber,
    String cprDate,
    String cprContractor,
    String cprSumFormatted,
    String cprCurrency,
    String cprStfName,
    String reservedState,
    String cprBlock,
    String cprUser,
    String cprDepartment,
    String cprCheckPrice
) {}
