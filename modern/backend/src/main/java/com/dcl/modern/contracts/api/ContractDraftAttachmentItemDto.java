package com.dcl.modern.contracts.api;

/**
 * Single attachment in draft list. N3a3 CONTRACTS.
 */
public record ContractDraftAttachmentItemDto(String idx, String id, String originalFileName, String attCreateDate) {}
