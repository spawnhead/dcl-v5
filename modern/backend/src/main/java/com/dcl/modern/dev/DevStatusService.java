package com.dcl.modern.dev;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Builds dev status (db, flyway, dataMode). Never throws — errors become status fields.
 */
@Service
public class DevStatusService {

    private final Environment env;
    private final DataSource dataSource;
    private final Flyway flyway;

    @Value("${spring.application.name:unknown}")
    private String appName;

    public DevStatusService(Environment env, DataSource dataSource, Flyway flyway) {
        this.env = env;
        this.dataSource = dataSource;
        this.flyway = flyway;
    }

    public DevStatusResponse getStatus() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles().length > 0 ? env.getActiveProfiles() : env.getDefaultProfiles());
        String javaVersion = System.getProperty("java.version");
        String version = readVersion();

        DevStatusResponse.DbStatus db = dbStatus();
        DevStatusResponse.FlywayStatus flywayStatus = flywayStatus();
        String dataMode = resolveDataMode(db.ok(), dataSource);
        String authMode = profiles.contains("dev") ? "DEV_BYPASS" : "TBD";

        return new DevStatusResponse(
            appName,
            version,
            profiles,
            javaVersion,
            db,
            flywayStatus,
            dataMode,
            authMode
        );
    }

    private DevStatusResponse.DbStatus dbStatus() {
        try (Connection c = dataSource.getConnection()) {
            String url = c.getMetaData().getURL();
            try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT now()")) {
                String now = rs.next() ? rs.getString(1) : null;
                return new DevStatusResponse.DbStatus(true, url, now, null);
            }
        } catch (Exception e) {
            return new DevStatusResponse.DbStatus(false, null, null, e.getMessage());
        }
    }

    private DevStatusResponse.FlywayStatus flywayStatus() {
        try {
            int count = flyway.info().applied().length;
            var current = flyway.info().current();
            String version = current != null ? current.getVersion().getVersion() : null;
            return new DevStatusResponse.FlywayStatus(true, count, version, null);
        } catch (Exception e) {
            return new DevStatusResponse.FlywayStatus(false, null, null, e.getMessage());
        }
    }

    private String resolveDataMode(boolean dbOk, DataSource ds) {
        if (!dbOk) return "EMPTY";
        try (Connection c = ds.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT 1 FROM dev_seed_marker LIMIT 1")) {
            if (rs.next()) return "FAKE_SEEDED";
        } catch (Exception ignored) {
            // table may not exist before dev seed
        }
        try (Connection c = ds.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM dcl_country")) {
            if (rs.next() && rs.getInt(1) == 0) return "EMPTY";
        } catch (Exception ignored) {
        }
        return "REAL";
    }

    private static String readVersion() {
        Package p = DevStatusService.class.getPackage();
        if (p != null && p.getImplementationVersion() != null) {
            return p.getImplementationVersion();
        }
        return "unknown";
    }
}
