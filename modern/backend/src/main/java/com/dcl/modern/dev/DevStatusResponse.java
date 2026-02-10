package com.dcl.modern.dev;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DevStatusResponse(
    String appName,
    String version,
    List<String> activeProfiles,
    String javaVersion,
    DbStatus db,
    FlywayStatus flyway,
    String dataMode,
    String authMode
) {
    public record DbStatus(Boolean ok, String url, String now, String error) {}
    public record FlywayStatus(Boolean ok, Integer migrationsAppliedCount, String currentVersion, String error) {}
}
