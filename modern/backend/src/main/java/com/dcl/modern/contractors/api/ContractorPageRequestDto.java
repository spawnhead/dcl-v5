package com.dcl.modern.contractors.api;

/** POST /api/contractors/page body. CONTRACTS §3. */
public record ContractorPageRequestDto(
    String direction,
    Integer currentPage,
    ContractorDataRequest filterState
) {}
