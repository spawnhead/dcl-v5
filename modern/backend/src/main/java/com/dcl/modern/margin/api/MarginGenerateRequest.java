package com.dcl.modern.margin.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

/**
 * Request body for building Margin session (CONTRACTS.md Margin Generate).
 * Legacy: URL-encoded form; modern: JSON. Field names match legacy for parity.
 */
@Schema(description = "Margin generate request (filters, options, view flags)")
public record MarginGenerateRequest(
    String date_begin,
    String date_end,
    LookupItemDto user,
    LookupItemDto department,
    LookupItemDto contractor,
    LookupItemDto stuffCategory,
    LookupItemDto route,
    Boolean user_aspect,
    Boolean department_aspect,
    Boolean contractor_aspect,
    Boolean stuff_category_aspect,
    Boolean route_aspect,
    Boolean onlyTotal,
    Boolean itog_by_spec,
    Boolean itog_by_user,
    Boolean itog_by_product,
    Boolean get_not_block,
    Map<String, Boolean> view
) {
    @Schema(description = "Selector item: id + display name")
    public record LookupItemDto(String id, String name) {}
}
