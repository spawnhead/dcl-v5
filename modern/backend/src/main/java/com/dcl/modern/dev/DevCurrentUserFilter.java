package com.dcl.modern.dev;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Dev-only: set current user from X-Dev-* headers. DEV_BYPASS: X-Dev-User, X-Dev-Roles;
 * optional X-Dev-Department-Id, X-Dev-Department-Name, X-Dev-Chief-Department.
 */
@Component
@Profile("dev")
@Order(-100)
public class DevCurrentUserFilter extends OncePerRequestFilter {

    private static final String DEFAULT_USER = "dev";
    private static final String DEFAULT_ROLES = "admin";
    private static final String DEFAULT_DEPARTMENT_ID = "-1";
    private static final String DEFAULT_DEPARTMENT_NAME = "Все";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String user = headerOrDefault(request, "X-Dev-User", DEFAULT_USER);
        String rolesHeader = request.getHeader("X-Dev-Roles");
        List<String> roles = rolesHeader == null || rolesHeader.isBlank()
            ? Collections.singletonList(DEFAULT_ROLES)
            : Arrays.stream(rolesHeader.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        if (roles.isEmpty()) roles = Collections.singletonList("admin");
        String depId = headerOrDefault(request, "X-Dev-Department-Id", DEFAULT_DEPARTMENT_ID);
        String depName = headerOrDefault(request, "X-Dev-Department-Name", DEFAULT_DEPARTMENT_NAME);
        boolean chiefDep = "1".equals(headerOrDefault(request, "X-Dev-Chief-Department", "0"));
        CurrentUser current = new CurrentUser("1", user, user, roles, depId, depName, chiefDep);
        DevCurrentUserHolder.set(current);
        try {
            filterChain.doFilter(request, response);
        } finally {
            DevCurrentUserHolder.clear();
        }
    }

    private static String headerOrDefault(HttpServletRequest request, String name, String def) {
        String v = request.getHeader(name);
        return (v == null || v.isBlank()) ? def : v.trim();
    }
}
