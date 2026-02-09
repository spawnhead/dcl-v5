package net.sam.dcl.action;

import net.sam.dcl.beans.Margin;
import net.sam.dcl.beans.MarginLine;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.util.StoreUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Development / Margin (AG Grid): JSON endpoint for current Margin session data.
 * Always returns JSON (either {data:..., view:..., meta:...} or {error:...}).
 */
public class MarginDevDataAction extends DBAction
{
  private static final Log log = LogFactory.getLog(MarginDevDataAction.class);

  public ActionForward execute(IActionContext context) throws Exception
  {
    HttpServletResponse resp = context.getResponse();
    resp.setContentType("application/json; charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter w = null;
    try
    {
      Margin margin = (Margin) StoreUtil.getSession(context.getRequest(), Margin.class);
      List lines = (margin != null) ? margin.getMarginLines() : null;
      int total = (lines != null) ? lines.size() : 0;

      int limit = parseLimit(context.getRequest().getParameter("limit"));
      int count = total;
      if (limit > 0 && count > limit)
      {
        count = limit;
      }

      String json = toJsonMargin(margin, lines, total, count);
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
      log.error("MarginDevData: failed: " + msg, e);

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
    String m = (message == null) ? "" : message;
    return "{\"error\":" + q(m) + "}";
  }

  private static String toJsonMargin(Margin margin, List lines, int total, int count)
  {
    StringBuilder sb = new StringBuilder(8192);
    sb.append("{\"data\":[");
    boolean first = true;
    for (int i = 0; i < count; i++)
    {
      Object o = (lines != null) ? lines.get(i) : null;
      if (!(o instanceof MarginLine))
      {
        continue;
      }
      MarginLine r = (MarginLine) o;
      if (!first) sb.append(',');
      first = false;
      sb.append(rowToJson(r));
    }
    sb.append("],\"view\":");
    sb.append(viewToJson(margin));
    sb.append(",\"meta\":{");
    sb.append("\"rowsTotal\":").append(total).append(',');
    sb.append("\"rowsReturned\":").append(count).append(',');
    sb.append("\"limited\":").append(count < total ? "true" : "false");
    sb.append("}}");
    return sb.toString();
  }

  private static String viewToJson(Margin margin)
  {
    if (margin == null)
    {
      return "{}";
    }
    StringBuilder sb = new StringBuilder(512);
    sb.append('{');
    sb.append("\"view_contractor\":").append(b(margin.getView_contractor())).append(',');
    sb.append("\"view_country\":").append(b(margin.getView_country())).append(',');
    sb.append("\"view_contract\":").append(b(margin.getView_contract())).append(',');
    sb.append("\"view_stuff_category\":").append(b(margin.getView_stuff_category())).append(',');
    sb.append("\"view_shipping\":").append(b(margin.getView_shipping())).append(',');
    sb.append("\"view_payment\":").append(b(margin.getView_payment())).append(',');
    sb.append("\"view_transport\":").append(b(margin.getView_transport())).append(',');
    sb.append("\"view_transport_sum\":").append(b(margin.getView_transport_sum())).append(',');
    sb.append("\"view_custom\":").append(b(margin.getView_custom())).append(',');
    sb.append("\"view_other_sum\":").append(b(margin.getView_other_sum())).append(',');
    sb.append("\"view_montage_sum\":").append(b(margin.getView_montage_sum())).append(',');
    sb.append("\"view_montage_time\":").append(b(margin.getView_montage_time())).append(',');
    sb.append("\"view_montage_cost\":").append(b(margin.getView_montage_cost())).append(',');
    sb.append("\"view_update_sum\":").append(b(margin.getView_update_sum())).append(',');
    sb.append("\"view_summ_zak\":").append(b(margin.getView_summ_zak())).append(',');
    sb.append("\"view_koeff\":").append(b(margin.getView_koeff())).append(',');
    sb.append("\"view_user\":").append(b(margin.getView_user())).append(',');
    sb.append("\"view_department\":").append(b(margin.getView_department()));
    sb.append('}');
    return sb.toString();
  }

  private static String rowToJson(MarginLine r)
  {
    StringBuilder sb = new StringBuilder(1024);
    sb.append('{');
    sb.append("\"ctr_name\":").append(q(r.getCtr_name())).append(',');
    sb.append("\"cut_name\":").append(q(r.getCut_name())).append(',');
    sb.append("\"con_number_formatted\":").append(q(r.getCon_number_formatted_exel())).append(',');
    sb.append("\"con_date_formatted\":").append(q(r.getCon_date_formatted())).append(',');
    sb.append("\"spc_number_formatted\":").append(q(r.getSpc_number_formatted_exel())).append(',');
    sb.append("\"spc_date_formatted\":").append(q(r.getSpc_date_formatted())).append(',');
    sb.append("\"spc_summ_formatted\":").append(q(r.getSpc_summ_formatted())).append(',');
    sb.append("\"cur_name\":").append(q(r.getCur_name())).append(',');
    sb.append("\"stf_name_show\":").append(q(r.getStf_name_show())).append(',');
    sb.append("\"shp_number_show\":").append(q(r.getShp_number_show())).append(',');
    sb.append("\"shp_date_show\":").append(q(r.getShp_date_show())).append(',');
    sb.append("\"pay_date_show\":").append(q(r.getPay_date_show())).append(',');
    sb.append("\"lps_summ_eur_formatted\":").append(q(r.getLps_summ_eur_formatted())).append(',');
    sb.append("\"lps_summ_formatted\":").append(q(r.getLps_summ_formatted())).append(',');
    sb.append("\"lps_sum_transport_formatted\":").append(q(r.getLps_sum_transport_formatted())).append(',');
    sb.append("\"lcc_transport_formatted\":").append(q(r.getLcc_transport_formatted())).append(',');
    sb.append("\"lps_custom_formatted\":").append(q(r.getLps_custom_formatted())).append(',');
    sb.append("\"lcc_charges_formatted\":").append(q(r.getLcc_charges_formatted())).append(',');
    sb.append("\"lcc_montage_formatted\":").append(q(r.getLcc_montage_formatted())).append(',');
    sb.append("\"lps_montage_time_formatted\":").append(q(r.getLps_montage_time_formatted())).append(',');
    sb.append("\"montage_cost_formatted\":").append(q(r.getMontage_cost_formatted())).append(',');
    sb.append("\"lcc_update_sum_formatted\":").append(q(r.getLcc_update_sum_formatted())).append(',');
    sb.append("\"summ_formatted\":").append(q(r.getSumm_formatted())).append(',');
    sb.append("\"summ_zak_formatted\":").append(q(r.getSumm_zak_formatted())).append(',');
    sb.append("\"margin_formatted\":").append(q(r.getMargin_formatted())).append(',');
    sb.append("\"koeff_formatted\":").append(q(r.getKoeff_formatted())).append(',');
    sb.append("\"usr_name_show\":").append(q(r.getUsr_name_show())).append(',');
    sb.append("\"dep_name_show\":").append(q(r.getDep_name_show())).append(',');
    sb.append("\"itogLine\":").append(r.isItogLine() ? "true" : "false").append(',');
    sb.append("\"spc_group_delivery\":").append(q(r.getSpc_group_delivery())).append(',');
    sb.append("\"haveUnblockedPrc\":").append(r.haveUnblockedPrc() ? "true" : "false");
    sb.append('}');
    return sb.toString();
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
          else
          {
            sb.append(c);
          }
      }
    }
    return sb.toString();
  }

  private static String b(String val)
  {
    return "1".equals(val) ? "true" : "false";
  }
}
