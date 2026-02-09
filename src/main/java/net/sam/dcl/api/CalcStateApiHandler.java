package net.sam.dcl.api;

import net.sam.dcl.beans.CalculationState;
import net.sam.dcl.beans.CalculationStateLine;
import net.sam.dcl.util.StoreUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CalcStateApiHandler {

  public void handleData(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    CalculationState calcState = (CalculationState) StoreUtil.getSession(req, CalculationState.class);
    List lines = (calcState != null) ? calcState.getCalculationStateLines() : null;
    int total = (lines != null) ? lines.size() : 0;

    int page = Math.max(1, ApiJsonUtil.parseInt(req.getParameter("page"), 1));
    int pageSize = Math.max(1, ApiJsonUtil.parseInt(req.getParameter("pageSize"), 50));
    int from = Math.max(0, (page - 1) * pageSize);
    int to = Math.min(total, from + pageSize);

    StringBuilder sb = new StringBuilder(8192);
    sb.append("{\"data\":[");
    boolean first = true;
    if (lines != null && from < to) {
      for (int i = from; i < to; i++) {
        Object o = lines.get(i);
        if (!(o instanceof CalculationStateLine)) {
          continue;
        }
        if (!first) sb.append(',');
        first = false;
        sb.append(rowToJson((CalculationStateLine) o));
      }
    }
    sb.append("],\"view\":");
    sb.append(viewToJson(calcState));
    sb.append(",\"meta\":{");
    sb.append("\"totalCount\":").append(total).append(',');
    sb.append("\"page\":").append(page).append(',');
    sb.append("\"pageSize\":").append(pageSize);
    sb.append("}}");

    resp.getWriter().write(sb.toString());
  }

  private static String viewToJson(CalculationState c) {
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
    sb.append("\"isDebit\":").append(safeIsDebit(c) ? "true" : "false");
    sb.append('}');
    return sb.toString();
  }

  private static String rowToJson(CalculationStateLine r) {
    StringBuilder sb = new StringBuilder(2048);
    sb.append('{');
    sb.append("\"con_number_date\":").append(ApiJsonUtil.q(stripHtml(safe(r.getCon_number_date())))).append(',');
    sb.append("\"spc_number_date\":").append(ApiJsonUtil.q(stripHtml(safe(r.getSpc_number_date())))).append(',');
    sb.append("\"spc_summ_formatted\":").append(ApiJsonUtil.q(safe(r.getSpc_summ_formatted()))).append(',');
    sb.append("\"con_currency\":").append(ApiJsonUtil.q(safe(r.getCon_currency()))).append(',');
    sb.append("\"spc_add_pay_cond\":").append(ApiJsonUtil.q(safe(r.getSpc_add_pay_condExcel()))).append(',');
    sb.append("\"spc_delivery_cond\":").append(ApiJsonUtil.q(safe(r.getSpc_delivery_condExcel()))).append(',');
    sb.append("\"spc_delivery_date_formatted\":").append(ApiJsonUtil.q(safe(r.getSpc_delivery_date_formatted()))).append(',');
    sb.append("\"shp_expiration\":").append(ApiJsonUtil.q(safe(r.getShp_expiration_excel()))).append(',');
    sb.append("\"pay_expiration_formatted\":").append(ApiJsonUtil.q(safe(r.getPay_expiration_excel()))).append(',');
    sb.append("\"complaint\":").append(ApiJsonUtil.q(stripHtml(safe(r.getComplaint())))).append(',');
    sb.append("\"comment\":").append(ApiJsonUtil.q(stripHtml(safe(r.getComment())))).append(',');
    sb.append("\"shp_date_formatted\":").append(ApiJsonUtil.q(safe(r.getShp_date_formatted()))).append(',');
    sb.append("\"shpNumberFormatted\":").append(ApiJsonUtil.q(safe(r.getShpNumberExcel()))).append(',');
    sb.append("\"shp_summ_formatted\":").append(ApiJsonUtil.q(safe(r.getShp_summ_formatted()))).append(',');
    sb.append("\"pay_date_formatted\":").append(ApiJsonUtil.q(safe(r.getPay_date_formatted()))).append(',');
    sb.append("\"pay_summ_formatted\":").append(ApiJsonUtil.q(safe(r.getPay_summ_formatted()))).append(',');
    sb.append("\"shp_saldo_formatted\":").append(ApiJsonUtil.q(safe(r.getShp_saldo_formatted()))).append(',');
    sb.append("\"managersFormatted\":").append(ApiJsonUtil.q(safe(r.getManagersFormatted()))).append(',');
    sb.append("\"stuff_categories\":").append(ApiJsonUtil.q(safe(r.getStuff_categories()))).append(',');
    sb.append("\"itogLine\":").append(r.isItog_line()).append(',');
    sb.append("\"ctr_line\":").append(r.isCtr_line());
    sb.append('}');
    return sb.toString();
  }

  private static String safe(String s) {
    return s == null ? "" : s;
  }

  private static String stripHtml(String s) {
    if (s == null) return "";
    return s.replaceAll("<[^>]+>", "").replaceAll("&nbsp;", " ").trim();
  }

  private static String b(String val) {
    return "1".equals(val) ? "true" : "false";
  }

  private static boolean safeIsDebit(CalculationState c) {
    try {
      return c.getIsDebit();
    } catch (Exception e) {
      return false;
    }
  }
}
