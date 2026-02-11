package com.dcl.modern.contractors.api;

import com.dcl.modern.contractors.application.ContractorCreateService;
import com.dcl.modern.contractors.application.ContractorCreateService.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * N3a1 Contractor create. Legacy: ContractorAddActionContract, ContractorAction.
 * CONTRACTS: docs/screens/contractor_create/CONTRACTS.md.
 */
@RestController
@RequestMapping("/api/contractors")
public class ContractorsController {

    private final ContractorCreateService createService;

    public ContractorsController(ContractorCreateService createService) {
        this.createService = createService;
    }

    @GetMapping("/create/open")
    public ContractorCreateOpenResponse getCreateOpen(@RequestParam(required = false) String returnTo) {
        return createService.open(returnTo);
    }

    @PostMapping("/create/save")
    public ResponseEntity<ContractorCreateSaveResponse> postCreateSave(@RequestBody ContractorCreateSaveRequest request) {
        return ResponseEntity.ok(createService.save(request));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidation(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(java.util.Map.of("error", java.util.Map.of("code", "VALIDATION_ERROR", "field", ex.getField(), "message", ex.getMessage())));
    }
}
