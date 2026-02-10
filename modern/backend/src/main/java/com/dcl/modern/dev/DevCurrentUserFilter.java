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
 * Dev-only: set current user from X-Dev-User and X-Dev-Roles headers.
 */
@Component
@Profile("dev")
@Order(-100)
public class DevCurrentUserFilter extends OncePerRequestFilter {

    private static final String DEFAULT_USER = "dev";
    private static final String DEFAULT_ROLES = "ADMIN,REPORTS,MARGIN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String user = request.getHeader("X-Dev-User");
        if (user == null || user.isBlank()) user = DEFAULT_USER;
        String rolesHeader = request.getHeader("X-Dev-Roles");
        List<String> roles = rolesHeader == null || rolesHeader.isBlank()
            ? Arrays.asList(DEFAULT_ROLES.split(","))
            : Arrays.stream(rolesHeader.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        if (roles.isEmpty()) roles = Collections.singletonList("USER");
        CurrentUser current = new CurrentUser("1", user, user, roles);
        DevCurrentUserHolder.set(current);
        try {
            filterChain.doFilter(request, response);
        } finally {
            DevCurrentUserHolder.clear();
        }
    }
}
