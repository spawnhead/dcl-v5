package com.dcl.modern.orders.api;

import java.util.List;

/**
 * GET /api/orders response. CONTRACTS §1.2.
 */
public record OrderListResponse(
    List<OrderRowDto> items,
    long total,
    int page,
    int pageSize
) {}
