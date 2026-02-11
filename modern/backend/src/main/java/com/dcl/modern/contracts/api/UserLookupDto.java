package com.dcl.modern.contracts.api;

/** User lookup for spec form. userFullName per contract_spec_create payloads. */
public record UserLookupDto(String id, String userFullName) {}
