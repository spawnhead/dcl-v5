package com.dcl.modern.contracts.api;

/**
 * POST /api/contracts/create/save 200 response. N3a CONTRACTS §2.
 */
public record ContractCreateSaveResponse(String conId, String redirectTo) {}
