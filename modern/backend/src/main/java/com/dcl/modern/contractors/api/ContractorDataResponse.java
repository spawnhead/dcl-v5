package com.dcl.modern.contractors.api;

import java.util.List;

/** POST /api/contractors/data and /api/contractors/page response. CONTRACTS §2. */
public record ContractorDataResponse(
    List<ContractorRowDto> items,
    int page,
    int pageSize,
    boolean hasNextPage
) {}
