package com.dcl.modern.country.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CountryCreateRequest(
    @NotBlank @Size(max = 50) String name,
    @NotNull Integer userId
) {
}
