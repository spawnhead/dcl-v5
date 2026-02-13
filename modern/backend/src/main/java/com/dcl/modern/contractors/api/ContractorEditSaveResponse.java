package com.dcl.modern.contractors.api;

/**
 * PUT /api/contractors/{ctrId}/edit/save response (200).
 * CONTRACTS: docs/screens/contractor_edit/CONTRACTS.md §2.
 */
public record ContractorEditSaveResponse(String ctrId, String returnTo, String redirectTo) {}
