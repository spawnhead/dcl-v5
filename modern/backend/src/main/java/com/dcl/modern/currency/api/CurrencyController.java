package com.dcl.modern.currency.api;

import com.dcl.modern.currency.application.CurrencyService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for currency reference data.
 * Legacy: DCL_CURRENCY table; reference data used across ERM (no dedicated Struts Action in initial inventory).
 */
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @PostMapping
    public CurrencyResponse create(@Valid @RequestBody CurrencyCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CurrencyResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public CurrencyResponse get(@PathVariable Integer id) {
        return service.get(id);
    }
}
