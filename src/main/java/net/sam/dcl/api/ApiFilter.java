package net.sam.dcl.api;

import net.sam.dcl.config.ConfigBootstrap;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ApiFilter implements Filter {

  public void init(FilterConfig filterConfig) throws ServletException {
    // no-op
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    ConfigBootstrap.ensureInitialized(request.getServletContext());

    response.setContentType("application/json; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    User user = UserUtil.getCurrentUser(request);
    if (user == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(ApiJsonUtil.error("UNAUTHORIZED", "auth required"));
      return;
    }

    if (!isAuthorized(request, user)) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write(ApiJsonUtil.error("FORBIDDEN", "not allowed"));
      return;
    }

    if (isMutating(request.getMethod()) && !isCsrfValid(request)) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write(ApiJsonUtil.error("CSRF_INVALID", "csrf token invalid"));
      return;
    }

    chain.doFilter(req, res);
  }

  public void destroy() {
    // no-op
  }

  private static boolean isMutating(String method) {
    if (method == null) return false;
    return "POST".equalsIgnoreCase(method) ||
        "PUT".equalsIgnoreCase(method) ||
        "DELETE".equalsIgnoreCase(method) ||
        "PATCH".equalsIgnoreCase(method);
  }

  private static boolean isAuthorized(HttpServletRequest request, User user) {
    String uri = request.getRequestURI();
    if (uri == null) return false;
    if (uri.contains("/api/margin")) {
      return isMarginRole(user);
    }
    if (uri.contains("/api/calc-state")) {
      return isCalcStateRole(user);
    }
    if (uri.contains("/api/lookups") || uri.contains("/api/health") || uri.contains("/api/_health")) {
      return isMarginRole(user) || isCalcStateRole(user);
    }
    return false;
  }

  private static boolean isMarginRole(User user) {
    return user.isAdmin() || user.isEconomist() || user.isManager();
  }

  private static boolean isCalcStateRole(User user) {
    return user.isAdmin() || user.isEconomist() || user.isManager() || user.isLawyer() || user.isUserInLithuania();
  }

  private static boolean isCsrfValid(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) return false;
    String expected = (String) session.getAttribute("csrfToken");
    if (expected == null || expected.isEmpty()) {
      return true;
    }
    String header = request.getHeader("X-CSRF-Token");
    String param = request.getParameter("csrf");
    String actual = header != null ? header : param;
    return expected.equals(actual);
  }
}
