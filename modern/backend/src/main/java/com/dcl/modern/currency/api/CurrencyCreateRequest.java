package com.dcl.modern.currency.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CurrencyCreateRequest(
    @NotBlank @Size(max = 10) String name,
    Integer noRound,
    Integer sortOrder
) {
}
