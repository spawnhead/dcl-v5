package com.dcl.modern.dev;

import java.util.List;

/**
 * Current user (dev bypass or future auth).
 */
public record CurrentUser(
    String id,
    String username,
    String displayName,
    List<String> roles
) {}
