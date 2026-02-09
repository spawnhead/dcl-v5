package net.sam.dcl.api;

import net.sam.dcl.config.ConfigBootstrap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiDispatchServlet extends HttpServlet {
  private final LookupApiHandler lookupHandler = new LookupApiHandler();
  private final MarginApiHandler marginHandler = new MarginApiHandler();
  private final CalcStateApiHandler calcStateHandler = new CalcStateApiHandler();

  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ConfigBootstrap.ensureInitialized(getServletContext());

    String path = req.getPathInfo();
    if (path == null || "/".equals(path)) {
      notFound(resp);
      return;
    }

    try {
      if ("/health".equals(path) || "/_health".equals(path)) {
        resp.getWriter().write("{\"status\":\"ok\"}");
        return;
      }

      if (path.startsWith("/lookups")) {
        lookupHandler.handle(req, resp, getServletContext());
        return;
      }

      if ("/margin/data".equals(path)) {
        marginHandler.handleData(req, resp);
        return;
      }

      if ("/calc-state/data".equals(path)) {
        calcStateHandler.handleData(req, resp);
        return;
      }

      notFound(resp);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(ApiJsonUtil.error("ERROR", e.getMessage()));
    }
  }

  private void notFound(HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    resp.getWriter().write(ApiJsonUtil.error("NOT_FOUND", "unknown endpoint"));
  }
}
