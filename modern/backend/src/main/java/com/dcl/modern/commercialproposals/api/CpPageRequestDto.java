package com.dcl.modern.commercialproposals.api;

/** POST /api/commercial-proposals/page body. */
public record CpPageRequestDto(
    String direction,
    Integer currentPage,
    CpDataRequest filterState
) {}
