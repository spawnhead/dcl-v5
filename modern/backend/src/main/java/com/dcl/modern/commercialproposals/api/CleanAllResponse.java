package com.dcl.modern.commercialproposals.api;

/** POST /api/commercial-proposals/cleanAll response. */
public record CleanAllResponse(
    CpLookupsResponse.DefaultsDto defaults,
    CpDataResponse grid
) {}
