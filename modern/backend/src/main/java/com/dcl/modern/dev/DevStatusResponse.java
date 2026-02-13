package com.dcl.modern.dev;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * DEV_DASHBOARD_SPEC: profile, javaVersion, serverTime, db (ok, product, version), flyway (ok, appliedMigrationsCount), dataMode.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DevStatusResponse(
    String profile,
    String javaVersion,
    String serverTime,
    DbStatus db,
    FlywayStatus flyway,
    String dataMode,
    String authMode,
    String seedDataset
) {
    public record DbStatus(Boolean ok, String product, String version, String url, String error) {}
    public record FlywayStatus(Boolean ok, Integer appliedMigrationsCount, String error) {}
}
