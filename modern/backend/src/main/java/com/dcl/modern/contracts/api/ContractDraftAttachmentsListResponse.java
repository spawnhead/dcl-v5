package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contracts/draft/attachments response. N3a3 CONTRACTS.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractDraftAttachmentsListResponse(List<ContractDraftAttachmentItemDto> items) {}
