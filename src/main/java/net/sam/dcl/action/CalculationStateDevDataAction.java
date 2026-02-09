package net.sam.dcl.action;

import net.sam.dcl.beans.CalculationState;
import net.sam.dcl.beans.CalculationStateLine;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Supplier;

/**
 * Development / Состояние расчетов (AG Grid): JSON endpoint for CalculationState session data.
 */
public class CalculationStateDevDataAction extends DBAction
{
  private static final Log log = LogFactory.getLog(CalculationStateDevDataAction.class);

  public ActionForward execute(IActionContext context) throws Exception
  {
    HttpServletResponse resp = context.getResponse();
    resp.setContentType("application/json; charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter w = null;
    try
    {
      CalculationState calcState = (CalculationState) StoreUtil.getSession(context.getRequest(), CalculationState.class);
      List lines = (calcState != null) ? calcState.getCalculationStateLines() : null;
      int total = (lines != null) ? lines.size() : 0;

      int limit = parseLimit(context.getRequest().getParameter("limit"));
      int count = total;
      if (limit > 0 && count > limit)
      {
        count = limit;
      }

      String json = toJson(calcState, lines, total, count);
      resp.setStatus(HttpServletResponse.SC_OK);
      w = resp.getWriter();
      w.write(json);
    }
    catch (Exception e)
    {
      String msg = e.getMessage();
      if (msg == null || msg.trim().isEmpty())
      {
        msg = e.getClass().getSimpleName();
      }
      log.error("CalculationStateDevData: failed: " + msg, e);

      resp.setStatus(HttpServletResponse.SC_OK);
      if (w == null)
      {
        w = resp.getWriter();
      }
      w.write(toJsonError(msg));
    }
    finally
    {
      if (w != null)
      {
        w.flush();
      }
    }
    return null;
  }

  private static int parseLimit(String value)
  {
    final int def = 200;
    final int max = 1000;
    if (value == null) return def;
    try
    {
      int v = Integer.parseInt(value.trim());
      if (v < 1) return def;
      return Math.min(v, max);
    }
    catch (Exception ignore)
    {
      return def;
    }
  }

  private static String toJsonError(String message)
  {
    return "{\"error\":" + q(message == null ? "" : message) + "}";
  }

  private static String toJson(CalculationState calcState, List lines, int total, int count)
  {
    StringBuilder sb = new StringBuilder(8192);
    sb.append("{\"data\":[");
    boolean first = true;
    for (int i = 0; i < count; i++)
    {
      Object o = (lines != null) ? lines.get(i) : null;
      if (!(o instanceof CalculationStateLine))
      {
        continue;
      }
      CalculationStateLine r = (CalculationStateLine) o;
      if (!first) sb.append(',');
      first = false;
      sb.append(rowToJson(r));
    }
    sb.append("],\"view\":");
    sb.append(viewToJson(calcState));
    sb.append(",\"meta\":{");
    sb.append("\"rowsTotal\":").append(total).append(',');
    sb.append("\"rowsReturned\":").append(count).append(',');
    sb.append("\"limited\":").append(count < total ? "true" : "false");
    sb.append("}}");
    return sb.toString();
  }

  private static String viewToJson(CalculationState c)
  {
    if (c == null) return "{}";
    StringBuilder sb = new StringBuilder(256);
    sb.append('{');
    sb.append("\"view_pay_cond\":").append(b(c.getView_pay_cond())).append(',');
    sb.append("\"view_delivery_cond\":").append(b(c.getView_delivery_cond())).append(',');
    sb.append("\"view_expiration\":").append(b(c.getView_expiration())).append(',');
    sb.append("\"view_complaint\":").append(b(c.getView_complaint())).append(',');
    sb.append("\"view_comment\":").append(b(c.getView_comment())).append(',');
    sb.append("\"view_manager\":").append(b(c.getView_manager())).append(',');
    sb.append("\"view_stuff_category\":").append(b(c.getView_stuff_category())).append(',');
    sb.append("\"isDebit\":").append(safeBool(() -> c.getIsDebit()) ? "true" : "false");
    sb.append('}');
    return sb.toString();
  }

  /** Safe getter for values that may use ActionContext (can throw if context not set). */
  private static String safeGet(Supplier<String> getter)
  {
    try
    {
      String s = getter.get();
      return s != null ? s : "";
    }
    catch (Exception e)
    {
      return "";
    }
  }

  private static boolean safeBool(Supplier<Boolean> getter)
  {
    try
    {
      Boolean b = getter.get();
      return Boolean.TRUE.equals(b);
    }
    catch (Exception e)
    {
      return false;
    }
  }

  private static String rowToJson(CalculationStateLine r)
  {
    StringBuilder sb = new StringBuilder(2048);
    sb.append('{');
    sb.append("\"con_number_date\":").append(q(stripHtml(safeGet(r::getCon_number_date)))).append(',');
    sb.append("\"spc_number_date\":").append(q(stripHtml(safeGet(r::getSpc_number_date)))).append(',');
    sb.append("\"spc_summ_formatted\":").append(q(safeGet(r::getSpc_summ_formatted))).append(',');
    sb.append("\"con_currency\":").append(q(safeGet(r::getCon_currency))).append(',');
    sb.append("\"spc_add_pay_cond\":").append(q(safeGet(r::getSpc_add_pay_condExcel))).append(',');
    sb.append("\"spc_delivery_cond\":").append(q(safeGet(r::getSpc_delivery_condExcel))).append(',');
    sb.append("\"spc_delivery_date_formatted\":").append(q(safeGet(r::getSpc_delivery_date_formatted))).append(',');
    sb.append("\"shp_expiration\":").append(q(safeGet(r::getShp_expiration_excel))).append(',');
    sb.append("\"pay_expiration_formatted\":").append(q(safeGet(r::getPay_expiration_excel))).append(',');
    sb.append("\"complaint\":").append(q(stripHtml(safeGet(r::getComplaint)))).append(',');
    sb.append("\"comment\":").append(q(stripHtml(safeGet(r::getComment)))).append(',');
    sb.append("\"shp_date_formatted\":").append(q(safeGet(r::getShp_date_formatted))).append(',');
    sb.append("\"shpNumberFormatted\":").append(q(safeGet(r::getShpNumberExcel))).append(',');
    sb.append("\"shp_summ_formatted\":").append(q(safeGet(r::getShp_summ_formatted))).append(',');
    sb.append("\"pay_date_formatted\":").append(q(safeGet(r::getPay_date_formatted))).append(',');
    sb.append("\"pay_summ_formatted\":").append(q(safeGet(r::getPay_summ_formatted))).append(',');
    sb.append("\"shp_saldo_formatted\":").append(q(safeGet(r::getShp_saldo_formatted))).append(',');
    sb.append("\"managersFormatted\":").append(q(safeGet(r::getManagersFormatted))).append(',');
    sb.append("\"stuff_categories\":").append(q(safeGet(r::getStuff_categories))).append(',');
    sb.append("\"itogLine\":").append(r.isItog_line()).append(',');
    sb.append("\"ctr_line\":").append(r.isCtr_line());
    sb.append('}');
    return sb.toString();
  }

  private static String stripHtml(String s)
  {
    if (s == null) return "";
    return s.replaceAll("<[^>]+>", "").replaceAll("&nbsp;", " ").trim();
  }

  private static String q(String s)
  {
    if (s == null) return "null";
    return "\"" + escapeJson(s) + "\"";
  }

  private static String escapeJson(String s)
  {
    if (s == null) return "";
    StringBuilder sb = new StringBuilder(s.length() + 16);
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      switch (c)
      {
        case '\\': sb.append("\\\\"); break;
        case '"': sb.append("\\\""); break;
        case '\n': sb.append("\\n"); break;
        case '\r': sb.append("\\r"); break;
        case '\t': sb.append("\\t"); break;
        default:
          if (c < 0x20)
          {
            sb.append("\\u");
            String hex = Integer.toHexString(c);
            for (int k = hex.length(); k < 4; k++) sb.append('0');
            sb.append(hex);
          }
          else sb.append(c);
      }
    }
    return sb.toString();
  }

  private static String b(String val)
  {
    return "1".equals(val) ? "true" : "false";
  }
}
