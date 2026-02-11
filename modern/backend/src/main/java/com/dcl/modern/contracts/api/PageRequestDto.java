package com.dcl.modern.contracts.api;

/** POST /api/contracts/page body. CONTRACTS §1.3. */
public record PageRequestDto(
    String direction,
    Integer currentPage,
    ContractDataRequest filterState
) {}
