package com.dcl.modern.dev;

/**
 * Provides current user (dev bypass or future security context).
 */
public interface CurrentUserProvider {
    CurrentUser getCurrentUser();
}
