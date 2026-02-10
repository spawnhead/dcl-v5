package com.dcl.modern.dev;

/**
 * ThreadLocal holder for dev current user (set by DevCurrentUserFilter).
 */
public final class DevCurrentUserHolder {
    private static final ThreadLocal<CurrentUser> HOLDER = new ThreadLocal<>();

    public static void set(CurrentUser user) {
        HOLDER.set(user);
    }

    public static CurrentUser get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    private DevCurrentUserHolder() {}
}
