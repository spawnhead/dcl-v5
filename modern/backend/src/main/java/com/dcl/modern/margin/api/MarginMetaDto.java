package com.dcl.modern.margin.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Grid meta: rowsTotal, rowsReturned, limited (CONTRACTS.md meta)")
public record MarginMetaDto(
    long rowsTotal,
    int rowsReturned,
    boolean limited
) {}
