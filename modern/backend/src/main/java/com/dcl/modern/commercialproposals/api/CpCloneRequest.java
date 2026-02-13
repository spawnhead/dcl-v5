package com.dcl.modern.commercialproposals.api;

/** POST /api/commercial-proposals/clone body. */
public record CpCloneRequest(String cprId, String mode) {}  // mode: "new" | "old"
