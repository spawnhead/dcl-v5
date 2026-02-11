package com.dcl.modern.contractors.api;

/**
 * POST /api/contractors/create/save 200. N3a1 CONTRACTS.
 * redirectTo includes path; client may append ?newContractorId=ctrId.
 */
public record ContractorCreateSaveResponse(String ctrId, String redirectTo, String returnTo) {}
