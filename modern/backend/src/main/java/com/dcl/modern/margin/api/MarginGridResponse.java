package com.dcl.modern.margin.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Margin grid data response (CONTRACTS.md Margin Grid Data)")
public record MarginGridResponse(
    List<MarginLineDto> data,
    ViewFlagsDto view,
    MarginMetaDto meta
) {}
