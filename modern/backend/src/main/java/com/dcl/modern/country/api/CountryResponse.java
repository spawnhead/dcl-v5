package com.dcl.modern.country.api;

import com.dcl.modern.country.domain.Country;
import java.time.LocalDateTime;

public record CountryResponse(
    Integer id,
    String name,
    LocalDateTime createdAt,
    Integer createdBy,
    LocalDateTime editedAt,
    Integer editedBy
) {
    public static CountryResponse from(Country country) {
        return new CountryResponse(
            country.getId(),
            country.getName(),
            country.getCreatedAt(),
            country.getCreatedBy(),
            country.getEditedAt(),
            country.getEditedBy()
        );
    }
}
