package com.dcl.modern.dev;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import org.flywaydb.core.api.MigrationInfo;

/**
 * Builds dev status (db, flyway, dataMode). Never throws — errors become status fields.
 */
@Service
public class DevStatusService {

    private final Environment env;
    private final DataSource dataSource;
    private final Flyway flyway;

    public DevStatusService(Environment env, DataSource dataSource, Flyway flyway) {
        this.env = env;
        this.dataSource = dataSource;
        this.flyway = flyway;
    }

    public DevStatusResponse getStatus() {
        String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : (env.getDefaultProfiles().length > 0 ? env.getDefaultProfiles()[0] : "default");
        String javaVersion = System.getProperty("java.version");
        String serverTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now());

        DevStatusResponse.DbStatus db = dbStatus();
        DevStatusResponse.FlywayStatus flywayStatus = flywayStatus();
        String dataMode = resolveDataMode(db.ok(), dataSource);
        String authMode = profile.equals("dev") ? "DEV_BYPASS" : "TBD";
        String seedDataset = resolveSeedDataset(dataSource);

        return new DevStatusResponse(profile, javaVersion, serverTime, db, flywayStatus, dataMode, authMode, seedDataset);
    }

    private DevStatusResponse.DbStatus dbStatus() {
        try (Connection c = dataSource.getConnection()) {
            var meta = c.getMetaData();
            String product = meta.getDatabaseProductName();
            String version = meta.getDatabaseProductVersion();
            if (version != null && version.length() > 20) version = version.substring(0, 20);
            String url = meta.getURL();
            try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT now()")) {
                rs.next();
                return new DevStatusResponse.DbStatus(true, product, version, url, null);
            }
        } catch (Exception e) {
            return new DevStatusResponse.DbStatus(false, null, null, null, e.getMessage());
        }
    }

    private DevStatusResponse.FlywayStatus flywayStatus() {
        try {
            int count = flyway.info().applied().length;
            return new DevStatusResponse.FlywayStatus(true, count, null);
        } catch (Exception e) {
            return new DevStatusResponse.FlywayStatus(false, null, e.getMessage());
        }
    }

    /**
     * SEED_DATA_PLAN: FAKE_SEEDED = DCL_SETTING.STN_NAME='DEV_SEED_VERSION' and STN_VALUE like 'margin-%';
     * REAL = no marker but domain data present; EMPTY = no marker and no domain data.
     */
    private String resolveDataMode(boolean dbOk, DataSource ds) {
        if (!dbOk) return "EMPTY";
        try (Connection c = ds.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(
                 "SELECT stn_value FROM dcl_setting WHERE stn_name = 'DEV_SEED_VERSION' LIMIT 1")) {
            if (rs.next()) {
                String val = rs.getString(1);
                if (val != null && val.startsWith("margin-")) return "FAKE_SEEDED";
            }
        } catch (Exception ignored) {
            // table may not exist before V5
        }
        try (Connection c = ds.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM dcl_country")) {
            if (rs.next() && rs.getInt(1) == 0) return "EMPTY";
        } catch (Exception ignored) {
        }
        return "REAL";
    }

    /**
     * Seed dataset indicator for UI: last Flyway migration version (e.g. "V21")
     * or DEV_SEED_VERSION from dcl_setting, or "unknown".
     */
    private String resolveSeedDataset(DataSource ds) {
        try {
            MigrationInfo[] applied = flyway.info().applied();
            if (applied != null && applied.length > 0) {
                MigrationInfo last = applied[applied.length - 1];
                if (last != null && last.getVersion() != null) {
                    return "V" + last.getVersion().toString();
                }
            }
        } catch (Exception ignored) {
        }
        try (Connection c = ds.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(
                 "SELECT stn_value FROM dcl_setting WHERE stn_name = 'DEV_SEED_VERSION' LIMIT 1")) {
            if (rs.next()) {
                String val = rs.getString(1);
                if (val != null && !val.isBlank()) return "DEV_SEED_VERSION=" + val;
            }
        } catch (Exception ignored) {
        }
        return "unknown";
    }

}
