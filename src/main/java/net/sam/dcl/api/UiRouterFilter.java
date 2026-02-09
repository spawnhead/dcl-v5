package net.sam.dcl.api;

import net.sam.dcl.config.Config;
import net.sam.dcl.config.ConfigBootstrap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class UiRouterFilter implements Filter {

  public void init(FilterConfig filterConfig) throws ServletException {
    // no-op
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    ConfigBootstrap.ensureInitialized(request.getServletContext());

    String path = request.getServletPath();
    if (path == null) {
      chain.doFilter(req, res);
      return;
    }

    String screen = null;
    String route = null;
    if (path.contains("MarginDevAction")) {
      screen = "margin";
      route = "/ui/index.html#/margin";
    } else if (path.contains("CalculationStateDevAction")) {
      screen = "calc-state";
      route = "/ui/index.html#/calc-state";
    }

    if (screen == null) {
      chain.doFilter(req, res);
      return;
    }

    String override = request.getParameter("ui");
    if ("legacy".equalsIgnoreCase(override)) {
      chain.doFilter(req, res);
      return;
    }
    if ("new".equalsIgnoreCase(override)) {
      forward(request, response, route);
      return;
    }

    if (isScreenEnabled(screen)) {
      forward(request, response, route);
      return;
    }

    chain.doFilter(req, res);
  }

  public void destroy() {
    // no-op
  }

  private boolean isScreenEnabled(String screen) {
    String val = Config.getString("ui.pilot.screens");
    if (val == null) return false;
    String[] items = val.split(",");
    for (String it : items) {
      if (screen.equalsIgnoreCase(it.trim())) {
        return true;
      }
    }
    return false;
  }

  private void forward(HttpServletRequest req, HttpServletResponse res, String route) throws ServletException, IOException {
    String qs = req.getQueryString();
    if (qs != null && !qs.isEmpty() && qs.toLowerCase(Locale.ROOT).indexOf("ui=") < 0) {
      route = route + (route.indexOf('?') >= 0 ? "&" : "?") + qs;
    }
    req.getRequestDispatcher(route).forward(req, res);
  }
}
