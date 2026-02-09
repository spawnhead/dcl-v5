package com.dcl.modern.currency.api;

import com.dcl.modern.currency.domain.Currency;

public record CurrencyResponse(
    Integer id,
    String name,
    Integer noRound,
    Integer sortOrder
) {
    public static CurrencyResponse from(Currency currency) {
        return new CurrencyResponse(
            currency.getId(),
            currency.getName(),
            currency.getNoRound(),
            currency.getSortOrder()
        );
    }
}
