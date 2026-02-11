package com.dcl.modern.contracts.api;

import com.dcl.modern.contracts.application.ContractCreateService;
import com.dcl.modern.contracts.application.ContractCreateService.ContractCreateValidationException;
import com.dcl.modern.contracts.application.ContractDraftAttachmentsService;
import com.dcl.modern.contracts.application.ContractDraftSpecService;
import com.dcl.modern.contracts.application.ContractsService;
import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST API for Contracts list (N3) and contract create (N3a). Legacy: ContractsAction, ContractAction.
 * CONTRACTS: docs/screens/contracts/CONTRACTS.md, docs/screens/contract_create/CONTRACTS.md.
 */
@RestController
@RequestMapping("/api/contracts")
public class ContractsController {

    private static final String SESSION_DRAFT_ATTACHMENTS = "dcl.draft.attachments";

    private final ContractsService service;
    private final ContractCreateService createService;
    private final ContractDraftSpecService draftSpecService;
    private final ContractDraftAttachmentsService draftAttachmentsService;

    @Autowired(required = false)
    private CurrentUserProvider currentUserProvider;

    public ContractsController(ContractsService service, ContractCreateService createService,
            ContractDraftSpecService draftSpecService, ContractDraftAttachmentsService draftAttachmentsService) {
        this.service = service;
        this.createService = createService;
        this.draftSpecService = draftSpecService;
        this.draftAttachmentsService = draftAttachmentsService;
    }

    private boolean canCreate() {
        if (currentUserProvider == null) return true;
        CurrentUser u = currentUserProvider.getCurrentUser();
        if (u == null || u.roles() == null) return true;
        List<String> createRoles = List.of("admin", "economist", "lawyer");
        return u.roles().stream().anyMatch(createRoles::contains);
    }

    @GetMapping("/create/open")
    public ContractCreateOpenResponse getCreateOpen() {
        return createService.open(canCreate());
    }

    @PostMapping("/create/save")
    public ResponseEntity<ContractCreateSaveResponse> postCreateSave(@RequestBody ContractCreateSaveRequest request) {
        ContractCreateSaveResponse saved = createService.save(request);
        return ResponseEntity.ok(saved);
    }

    @ExceptionHandler(ContractCreateValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(ContractCreateValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getBody());
    }

    @GetMapping("/lookups")
    public ContractsLookupsResponse getLookups() {
        return service.getLookups();
    }

    @PostMapping("/data")
    public ContractDataResponse postData(@RequestBody ContractDataRequest request) {
        return service.getData(request != null ? request : new ContractDataRequest(null, null, "", "", null, null, null, null, null, null, null, 1, 15));
    }

    @PostMapping("/page")
    public ContractDataResponse postPage(@RequestBody PageRequestDto request) {
        return service.getPage(request != null ? request : new PageRequestDto("next", 1, null));
    }

    @PostMapping("/cleanAll")
    public CleanAllResponse postCleanAll() {
        return service.cleanAll();
    }

    @GetMapping("/draft/specifications/new/open")
    public SpecCreateOpenResponse getDraftSpecOpen(@RequestParam(required = false) String currencyName) {
        return draftSpecService.open(currencyName);
    }

    @PostMapping("/draft/specifications/save")
    public ResponseEntity<SpecCreateSaveResponse> postDraftSpecSave(@RequestBody SpecCreateSaveRequest request) {
        SpecCreateSaveResponse saved = draftSpecService.save(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/draft/attachments")
    public ContractDraftAttachmentsListResponse getDraftAttachments(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<ContractDraftAttachmentItemDto> stored = (List<ContractDraftAttachmentItemDto>) session.getAttribute(SESSION_DRAFT_ATTACHMENTS);
        if (stored == null) stored = new ArrayList<>();
        return draftAttachmentsService.list(stored);
    }

    @PostMapping("/draft/attachments/upload")
    public ResponseEntity<ContractDraftAttachmentItemDto> postDraftAttachmentsUpload(
            @RequestParam("file") MultipartFile file,
            HttpSession session) {
        @SuppressWarnings("unchecked")
        List<ContractDraftAttachmentItemDto> stored = (List<ContractDraftAttachmentItemDto>) session.getAttribute(SESSION_DRAFT_ATTACHMENTS);
        if (stored == null) {
            stored = new ArrayList<>();
            session.setAttribute(SESSION_DRAFT_ATTACHMENTS, stored);
        }
        String name = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
        ContractDraftAttachmentItemDto item = draftAttachmentsService.upload(name, stored);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/draft/attachments/{id}")
    public ResponseEntity<Void> deleteDraftAttachment(@PathVariable String id, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<ContractDraftAttachmentItemDto> stored = (List<ContractDraftAttachmentItemDto>) session.getAttribute(SESSION_DRAFT_ATTACHMENTS);
        if (stored != null) {
            draftAttachmentsService.delete(id, stored);
        }
        return ResponseEntity.noContent().build();
    }
}
