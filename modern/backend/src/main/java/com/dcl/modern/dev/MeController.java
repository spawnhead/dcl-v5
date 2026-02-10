package com.dcl.modern.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dev-only: current user. DEV_BYPASS: returns id, name, roles, department, chiefDepartment, authMode.
 */
@RestController
@RequestMapping("/api")
@Profile("dev")
public class MeController {

    private final CurrentUserProvider currentUserProvider;

    public MeController(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me() {
        CurrentUser u = currentUserProvider.getCurrentUser();
        return ResponseEntity.ok(MeResponse.from(u, "DEV_BYPASS"));
    }
}
