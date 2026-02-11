package com.dcl.modern.contracts.application;

import com.dcl.modern.contracts.api.ContractDraftAttachmentItemDto;
import com.dcl.modern.contracts.api.ContractDraftAttachmentsListResponse;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * N3a3 Draft attachments. Session/deferred storage when con_id=null.
 * CONTRACTS: docs/screens/contract_attachments/CONTRACTS.md.
 * In-memory store keyed by session id (no HttpSession in service for testability; controller passes list).
 */
@Service
public class ContractDraftAttachmentsService {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;

    /** In-memory per "session" (controller will pass list from session or create). */
    public ContractDraftAttachmentsListResponse list(List<ContractDraftAttachmentItemDto> stored) {
        if (stored == null) stored = List.of();
        return new ContractDraftAttachmentsListResponse(new ArrayList<>(stored));
    }

    public ContractDraftAttachmentItemDto upload(String originalFileName, List<ContractDraftAttachmentItemDto> stored) {
        if (stored == null) stored = new ArrayList<>();
        String id = UUID.randomUUID().toString();
        String idx = String.valueOf(stored.size() + 1);
        String attCreateDate = ISO.format(Instant.now());
        ContractDraftAttachmentItemDto item = new ContractDraftAttachmentItemDto(idx, id, originalFileName, attCreateDate);
        stored.add(item);
        return item;
    }

    /** Removes by id and renumbers idx. Caller must put back the same list in session. */
    public void delete(String id, List<ContractDraftAttachmentItemDto> stored) {
        if (stored == null) return;
        stored.removeIf(a -> id.equals(a.id()));
        for (int i = 0; i < stored.size(); i++) {
            ContractDraftAttachmentItemDto a = stored.get(i);
            stored.set(i, new ContractDraftAttachmentItemDto(String.valueOf(i + 1), a.id(), a.originalFileName(), a.attCreateDate()));
        }
    }
}
