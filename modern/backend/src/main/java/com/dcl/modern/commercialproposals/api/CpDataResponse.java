package com.dcl.modern.commercialproposals.api;

import java.util.List;

/** POST /api/commercial-proposals/data and /page response. */
public record CpDataResponse(
    List<CpRowDto> items,
    int page,
    int pageSize,
    boolean hasNextPage,
    List<SortItemDto> sort
) {}
