package com.dcl.modern.dev;

import java.util.List;

/**
 * Current user (dev bypass or future auth). ROLE_MODEL + DEV_BYPASS: id, name, roles, department, chiefDepartment.
 */
public record CurrentUser(
    String id,
    String username,
    String displayName,
    List<String> roles,
    String departmentId,
    String departmentName,
    boolean chiefDepartment
) {
    /** For JSON: "name" per DEV_BYPASS /api/me contract. */
    public String name() {
        return displayName != null ? displayName : username;
    }
}
