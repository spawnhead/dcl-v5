package net.sam.dcl.test;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Action for test_newGrid page (Development). show → JSP; data → JSON for grid.
 */
public class TestNewGridAction extends DBAction implements IDispatchable {

  private static final Log log = LogFactory.getLog(TestNewGridAction.class);
  private static final String SQL_ID = "test-select-orders-by-date";
  private static final String DATE_FMT = "dd.MM.yyyy";

  public ActionForward show(IActionContext context) throws Exception {
    return context.getMapping().findForward("form");
  }

  /**
   * Returns grid data as JSON: { "data": [ ... ] } or { "error": "...", "data": [] } (always HTTP 200).
   *
   * Optional query param: ord_date=dd.MM.yyyy (filters orders by order date).
   */
  public ActionForward data(IActionContext context) throws Exception {
    HttpServletResponse resp = context.getResponse();
    resp.setContentType("application/json; charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter w = null;
    try {
      TestNewGridForm form = (TestNewGridForm) context.getForm();
      if (form != null) {
        // Use a dedicated UI parameter to avoid Struts/BeanUtils trying to populate
        // java.sql.Date directly from String (ConversionException).
        form.setOrd_date(parseOrdDate(context.getRequest().getParameter("ord_date_s")));
      }

      int limit = parseLimit(context.getRequest().getParameter("limit"));
      List<TestNewGridForm.OrderRow> list = DAOUtils.fillList(context, SQL_ID, form, TestNewGridForm.OrderRow.class, null, null);
      if (list != null && limit > 0 && list.size() > limit) {
        list = list.subList(0, limit);
      }
      String json = toJson(list);

      resp.setStatus(HttpServletResponse.SC_OK);
      w = resp.getWriter();
      w.write(json);
    } catch (Exception e) {
      String msg = e.getMessage();
      if (msg == null || msg.trim().isEmpty()) {
        msg = e.getClass().getSimpleName();
      }
      log.error("test_newGrid: dispatch=data failed: " + msg, e);

      // Ensure client-side fetch() doesn't see HTTP 500; return structured JSON error instead.
      resp.setStatus(HttpServletResponse.SC_OK);
      if (w == null) {
        w = resp.getWriter();
      }
      w.write(toJsonError(msg));
    } finally {
      if (w != null) {
        w.flush();
      }
    }
    return null;
  }

  private static String toJsonError(String message) {
    return "{\"error\":\"" + escapeJson(message) + "\",\"data\":[]}";
  }

  private static String toJson(List<TestNewGridForm.OrderRow> list) {
    StringBuilder sb = new StringBuilder();
    sb.append("{\"data\":[");
    for (int i = 0; i < list.size(); i++) {
      if (i > 0) sb.append(',');
      sb.append(rowToJson(list.get(i)));
    }
    sb.append("]}");
    return sb.toString();
  }

  private static String rowToJson(TestNewGridForm.OrderRow r) {
    return "{\"ord_id\":" + q(r.getOrd_id())
        + ",\"ord_number\":" + q(r.getOrd_number())
        + ",\"ord_date\":" + q(r.getOrd_date())
        + ",\"contractor_name\":" + q(r.getContractor_name())
        + ",\"contractor_for_name\":" + q(r.getContractor_for_name())
        + "}";
  }

  private static String q(String s) {
    return "\"" + escapeJson(s != null ? s : "") + "\"";
  }

  private static int parseLimit(String value) {
    final int def = 200;
    final int max = 500;
    if (value == null) return def;
    try {
      int v = Integer.parseInt(value.trim());
      if (v < 1) return def;
      return Math.min(v, max);
    } catch (Exception ignore) {
      return def;
    }
  }

  private static Date parseOrdDate(String ddMmYyyy) throws Exception {
    if (ddMmYyyy == null) return null;
    String s = ddMmYyyy.trim();
    if (s.isEmpty()) return null;
    // Calendar.js uses dd.MM.yyyy. Keep parsing strict to avoid surprises.
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
    sdf.setLenient(false);
    java.util.Date d = sdf.parse(s);
    return new Date(d.getTime());
  }

  private static String escapeJson(String s) {
    if (s == null) return "";
    StringBuilder sb = new StringBuilder(s.length() + 8);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '"':  sb.append("\\\""); break;
        case '\\': sb.append("\\\\"); break;
        case '\n': sb.append("\\n"); break;
        case '\r': sb.append("\\r"); break;
        case '\t': sb.append("\\t"); break;
        default:
          if (c < ' ') sb.append("\\u").append(String.format("%04x", (int) c));
          else sb.append(c);
      }
    }
    return sb.toString();
  }
}
