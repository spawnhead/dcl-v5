package com.dcl.modern.contractors.api;

/** POST /api/contractors/block body. CONTRACTS §4. */
public record ContractorBlockRequest(
    String ctrId,
    String block
) {}
