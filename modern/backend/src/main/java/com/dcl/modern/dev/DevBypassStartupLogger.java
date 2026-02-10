package com.dcl.modern.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * DEV_BYPASS guardrails: log DEV_BYPASS_ENABLED=true|false at startup.
 */
@Component
@Profile("dev")
public class DevBypassStartupLogger {

    private static final Logger log = LoggerFactory.getLogger(DevBypassStartupLogger.class);

    private final Environment env;

    public DevBypassStartupLogger(Environment env) {
        this.env = env;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        boolean dev = java.util.Arrays.stream(env.getActiveProfiles()).anyMatch("dev"::equals);
        log.info("DEV_BYPASS_ENABLED={}", dev);
    }
}
