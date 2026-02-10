package com.dcl.modern.margin.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Single lookup option (id + name) for filter selectors")
public record LookupItemResponse(String id, String name) {}
