package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * POST /api/contracts/create/save body. N3a CONTRACTS §2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractCreateSaveRequest(
    String conNumber,
    String conDate,
    Boolean conReusable,
    String conFinalDate,
    LookupItemDto contractor,
    LookupItemDto currency,
    Boolean conFaxCopy,
    Boolean conOriginal,
    LookupItemDto seller,
    Boolean conAnnul,
    String conAnnulDate,
    String conComment,
    List<Object> specifications
) {}
