package com.dcl.modern.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dev-only: environment status for Development dashboard.
 */
@RestController
@RequestMapping("/api/dev")
@Profile("dev")
public class DevStatusController {

    private final DevStatusService devStatusService;

    public DevStatusController(DevStatusService devStatusService) {
        this.devStatusService = devStatusService;
    }

    @GetMapping("/status")
    public ResponseEntity<DevStatusResponse> status() {
        return ResponseEntity.ok(devStatusService.getStatus());
    }
}
