package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * POST /api/contracts/create/save 400 validation response. N3a CONTRACTS §2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationErrorResponse(ErrorDetail error) {
    public record ErrorDetail(String code, List<FieldError> fields) {}
    public record FieldError(String name, String message) {}
}
