package com.dcl.modern.contracts.api;

import java.util.List;

/** POST /api/contracts/data and /api/contracts/page response. CONTRACTS §1.2. */
public record ContractDataResponse(
    List<ContractRowDto> items,
    int page,
    int pageSize,
    boolean hasNextPage,
    List<SortItemDto> sort
) {}
