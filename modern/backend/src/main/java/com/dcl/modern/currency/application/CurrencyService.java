package com.dcl.modern.currency.application;

import com.dcl.modern.currency.api.CurrencyCreateRequest;
import com.dcl.modern.currency.api.CurrencyResponse;
import com.dcl.modern.currency.domain.Currency;
import com.dcl.modern.currency.infrastructure.CurrencyRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurrencyService {
    private final CurrencyRepository repository;

    public CurrencyService(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CurrencyResponse create(CurrencyCreateRequest request) {
        Currency currency = new Currency(request.name(), toShort(request.noRound()), toShort(request.sortOrder()));
        Currency saved = repository.save(currency);
        return CurrencyResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<CurrencyResponse> list() {
        return repository.findAll().stream().map(CurrencyResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CurrencyResponse get(Integer id) {
        Currency currency = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found"));
        return CurrencyResponse.from(currency);
    }

    private static Short toShort(Integer value) {
        return value == null ? null : value.shortValue();
    }
}
