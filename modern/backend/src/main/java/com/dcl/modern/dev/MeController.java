package com.dcl.modern.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dev-only: current user (X-Dev-User / X-Dev-Roles).
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
    public ResponseEntity<CurrentUser> me() {
        return ResponseEntity.ok(currentUserProvider.getCurrentUser());
    }
}
