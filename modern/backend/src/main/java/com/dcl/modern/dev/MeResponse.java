package com.dcl.modern.dev;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DEV_BYPASS: /api/me contract — id, name, roles, department, chiefDepartment, authMode.
 */
public record MeResponse(
    String id,
    String name,
    List<String> roles,
    DepartmentDto department,
    @JsonProperty("chiefDepartment") boolean chiefDepartment,
    String authMode
) {
    public record DepartmentDto(String id, String name) {}

    public static MeResponse from(CurrentUser u, String authMode) {
        return new MeResponse(
            u.id(),
            u.name(),
            u.roles(),
            new DepartmentDto(u.departmentId(), u.departmentName()),
            u.chiefDepartment(),
            authMode
        );
    }
}
