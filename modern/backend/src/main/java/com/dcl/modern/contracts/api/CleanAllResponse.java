package com.dcl.modern.contracts.api;

/** POST /api/contracts/cleanAll response. CONTRACTS §1.4. */
public record CleanAllResponse(
    DefaultsDto defaults,
    ContractDataResponse grid
) {}
