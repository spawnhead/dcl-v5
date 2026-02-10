package com.dcl.modern.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Dev profile: resolve current user from DevCurrentUserHolder (set by filter).
 */
@Component
@Profile("dev")
public class DevCurrentUserProvider implements CurrentUserProvider {
    @Override
    public CurrentUser getCurrentUser() {
        CurrentUser u = DevCurrentUserHolder.get();
        return u != null ? u : new CurrentUser("0", "anonymous", "Anonymous", java.util.List.of("USER"));
    }
}
