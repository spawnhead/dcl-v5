package com.dcl.modern.country.application;

import com.dcl.modern.country.api.CountryCreateRequest;
import com.dcl.modern.country.api.CountryResponse;
import com.dcl.modern.country.domain.Country;
import com.dcl.modern.country.infrastructure.CountryRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CountryService {
    private final CountryRepository repository;
    private final Clock clock;

    public CountryService(CountryRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public CountryResponse create(CountryCreateRequest request) {
        LocalDateTime now = LocalDateTime.now(clock);
        Country country = new Country(request.name(), now, request.userId(), now, request.userId());
        Country saved = repository.save(country);
        return CountryResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<CountryResponse> list() {
        return repository.findAll().stream().map(CountryResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CountryResponse get(Integer id) {
        Country country = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
        return CountryResponse.from(country);
    }
}
