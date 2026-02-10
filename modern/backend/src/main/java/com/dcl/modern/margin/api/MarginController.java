package com.dcl.modern.margin.api;

import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import com.dcl.modern.margin.application.MarginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for Margin report (Отчеты → Маржа).
 * Legacy: MarginAction (generate, cleanAll, generateExcel), MarginDevDataAction (grid), *ListAction (lookups).
 * CONTRACTS: docs/screens/margin/CONTRACTS.md.
 * ROLE_MODEL: optional hook to current user/roles (dev bypass or future auth); use for access/manager read-only when implemented.
 */
@RestController
@RequestMapping("/api/margin")
public class MarginController {

    private final MarginService service;

    @Autowired(required = false)
    private CurrentUserProvider currentUserProvider;

    public MarginController(MarginService service) {
        this.service = service;
    }

    /** Current user when available (dev profile or future auth). For role-based behavior per ROLE_MODEL. */
    protected Optional<CurrentUser> currentUser() {
        return currentUserProvider != null ? Optional.of(currentUserProvider.getCurrentUser()) : Optional.empty();
    }

    @Operation(summary = "Get grid data", description = "CONTRACTS Margin Grid Data; limit 50|100|200|500|1000, default 200")
    @ApiResponse(responseCode = "200", description = "data + view + meta")
    @GetMapping("/data")
    public MarginGridResponse getData(
        @RequestParam(defaultValue = "200") int limit
    ) {
        var result = service.getData(limit);
        return new MarginGridResponse(
            result.data().stream().map(MarginLineDto::from).toList(),
            ViewFlagsDto.from(result.view()),
            new MarginMetaDto(result.rowsTotal(), result.rowsReturned(), result.limited())
        );
    }

    @Operation(summary = "Build Margin session", description = "CONTRACTS Margin Generate; accepts filters/options/view")
    @PostMapping("/generate")
    public ResponseEntity<Void> generate(@RequestBody(required = false) MarginGenerateRequest request) {
        service.generate(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Clear session", description = "CONTRACTS Margin Clear All")
    @PostMapping("/cleanAll")
    public ResponseEntity<Void> cleanAll() {
        service.cleanAll();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Download Excel", description = "CONTRACTS Margin Excel Export")
    @GetMapping(value = "/export/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] bytes = service.exportExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=margin_export.xlsx");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }

    @Operation(summary = "Users lookup", description = "CONTRACTS Users Lookup; query filter, have_all")
    @GetMapping("/lookups/users")
    public List<LookupItemResponse> getUsers(
        @RequestParam(required = false) String filter,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getUsers(filter, have_all).stream()
            .map(i -> new LookupItemResponse(i.id(), i.name()))
            .toList();
    }

    @Operation(summary = "Departments lookup", description = "CONTRACTS Departments Lookup")
    @GetMapping("/lookups/departments")
    public List<LookupItemResponse> getDepartments(
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getDepartments(have_all).stream()
            .map(i -> new LookupItemResponse(i.id(), i.name()))
            .toList();
    }

    @Operation(summary = "Contractors lookup", description = "CONTRACTS Contractors Lookup")
    @GetMapping("/lookups/contractors")
    public List<LookupItemResponse> getContractors(
        @RequestParam(required = false) String filter,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getContractors(filter, have_all).stream()
            .map(i -> new LookupItemResponse(i.id(), i.name()))
            .toList();
    }

    @Operation(summary = "Stuff categories lookup", description = "CONTRACTS Stuff Categories Lookup")
    @GetMapping("/lookups/stuff-categories")
    public List<LookupItemResponse> getStuffCategories(
        @RequestParam(required = false) String filter,
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getStuffCategories(filter, have_all).stream()
            .map(i -> new LookupItemResponse(i.id(), i.name()))
            .toList();
    }

    @Operation(summary = "Routes lookup", description = "CONTRACTS Routes Lookup")
    @GetMapping("/lookups/routes")
    public List<LookupItemResponse> getRoutes(
        @RequestParam(defaultValue = "true") boolean have_all
    ) {
        return service.getRoutes(have_all).stream()
            .map(i -> new LookupItemResponse(i.id(), i.name()))
            .toList();
    }
}
