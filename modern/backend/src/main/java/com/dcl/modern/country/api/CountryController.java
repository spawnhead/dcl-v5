package com.dcl.modern.country.api;

import com.dcl.modern.country.application.CountryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @PostMapping
    public CountryResponse create(@Valid @RequestBody CountryCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CountryResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public CountryResponse get(@PathVariable Integer id) {
        return service.get(id);
    }
}
