package com.dcl.modern.contractors.api;

import com.dcl.modern.contractors.application.ContractorCreateService;
import com.dcl.modern.contractors.application.ContractorCreateService.ValidationException;
import com.dcl.modern.contractors.application.ContractorEditService;
import com.dcl.modern.contractors.application.ContractorListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contractors list (References) + N3a1 Contractor create.
 * Legacy: ContractorsAction, ContractorAction.
 * CONTRACTS: docs/screens/contractors/CONTRACTS.md, docs/screens/contractor_create/CONTRACTS.md.
 */
@RestController
@RequestMapping("/api/contractors")
public class ContractorsController {

    private final ContractorCreateService createService;
    private final ContractorEditService editService;
    private final ContractorListService listService;

    public ContractorsController(ContractorCreateService createService, ContractorEditService editService, ContractorListService listService) {
        this.createService = createService;
        this.editService = editService;
        this.listService = listService;
    }

    @GetMapping("/lookups")
    public ContractorLookupsResponse getLookups() {
        return listService.getLookups();
    }

    @PostMapping("/data")
    public ContractorDataResponse postData(@RequestBody ContractorDataRequest request) {
        return listService.getData(request);
    }

    @PostMapping("/page")
    public ContractorDataResponse postPage(@RequestBody ContractorPageRequestDto request) {
        return listService.getPage(request);
    }

    @PostMapping("/cleanAll")
    public ContractorLookupsResponse postCleanAll() {
        return listService.cleanAll();
    }

    @PostMapping("/block")
    public ResponseEntity<Void> postBlock(@RequestBody ContractorBlockRequest request) {
        listService.block(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ctrId}")
    public ResponseEntity<?> deleteContractor(@PathVariable String ctrId) {
        return listService.delete(ctrId);
    }

    @GetMapping("/create/open")
    public ContractorCreateOpenResponse getCreateOpen(
            @RequestParam(required = false) String returnTo,
            @RequestParam(required = false) Integer ctrId) {
        return createService.open(returnTo, ctrId);
    }

    @PostMapping("/create/save")
    public ResponseEntity<?> postCreateSave(@RequestBody ContractorCreateSaveRequest request) {
        try {
            return ResponseEntity.ok(createService.save(request));
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(ContractorsController.class)
                .warn("Contractor create/save failed", e);
            String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(java.util.Map.of("error", msg, "message", msg));
        }
    }

    @GetMapping("/{ctrId}/edit/open")
    public ContractorEditOpenResponse getEditOpen(
            @PathVariable String ctrId,
            @RequestParam(required = false) String returnTo,
            @RequestParam(required = false) String tab) {
        return editService.open(ctrId, returnTo, tab);
    }

    @PutMapping("/{ctrId}/edit/save")
    public ResponseEntity<?> putEditSave(@PathVariable String ctrId, @RequestBody ContractorEditSaveRequest request) {
        try {
            return ResponseEntity.ok(editService.save(ctrId, request));
        } catch (ValidationException e) {
            throw e;
        } catch (org.springframework.web.server.ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(ContractorsController.class).warn("Contractor edit/save failed", e);
            String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(java.util.Map.of("error", msg, "message", msg));
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidation(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(java.util.Map.of("errors", java.util.List.of(java.util.Map.of("field", ex.getField(), "message", ex.getMessage())), "activeTab", "mainPanel"));
    }
}
