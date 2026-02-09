package net.sam.dcl.api;

import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.XMLResource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class LookupApiHandler {
  private static final int CONTRACTOR_SEARCH_MIN_CHARS = 2;
  private static final int CONTRACTOR_SEARCH_MAX_LIMIT = 50;

  public void handle(HttpServletRequest req, HttpServletResponse resp, ServletContext ctx) throws Exception {
    String path = req.getPathInfo();
    if (path == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      resp.getWriter().write(ApiJsonUtil.error("NOT_FOUND", "unknown lookup"));
      return;
    }

    String type = path.replaceFirst("^/lookups/?", "");
    if (type.startsWith("/")) type = type.substring(1);

    if ("report-types".equals(type)) {
      writeReportTypes(resp);
      return;
    }

    if ("users".equals(type)) {
      writeFromQuery(resp, ctx, "select-users-filter", "usr_id", "usr_name", paramUsers(), includeAll(req));
      return;
    }
    if ("departments".equals(type)) {
      writeFromQuery(resp, ctx, "select-departments", "dep_id", "dep_name", null, includeAll(req));
      return;
    }
    if ("contractors".equals(type)) {
      writeFromQuery(resp, ctx, "select-contractors-filter", "ctr_id", "ctr_name", paramFilter(req), includeAll(req));
      return;
    }
    if ("contractors/search".equals(type)) {
      writeContractorsSearch(resp, ctx, req);
      return;
    }
    if ("stuff-categories".equals(type)) {
      writeFromQuery(resp, ctx, "select-stuff_categories-filter", "stf_id", "stf_name", paramStuffCategory(req), includeAll(req));
      return;
    }
    if ("routes".equals(type)) {
      writeFromQuery(resp, ctx, "select-routes", "rut_id", "rut_name", null, includeAll(req));
      return;
    }
    if ("currencies".equals(type)) {
      writeFromQuery(resp, ctx, "select-currencies", "cur_id", "cur_name", null, includeAll(req));
      return;
    }
    if ("sellers".equals(type)) {
      writeFromQuery(resp, ctx, "select-sellers", "sln_id", "sln_name", null, includeAll(req));
      return;
    }
    if ("contracts".equals(type)) {
      writeContracts(resp, ctx, req);
      return;
    }

    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    resp.getWriter().write(ApiJsonUtil.error("NOT_FOUND", "unknown lookup"));
  }

  private void writeReportTypes(HttpServletResponse resp) throws Exception {
    List<Item> items = new ArrayList<Item>();
    items.add(new Item(ApiMessageUtil.get("report_type_list.calc_in_common_id"),
        ApiMessageUtil.get("report_type_list.calc_in_common")));
    items.add(new Item(ApiMessageUtil.get("report_type_list.calc_debit_id"),
        ApiMessageUtil.get("report_type_list.calc_debit")));
    writeItems(resp, items);
  }

  private void writeContracts(HttpServletResponse resp, ServletContext ctx, HttpServletRequest req) throws Exception {
    String contractorId = req.getParameter("contractorId");
    VParameter param = new VParameter();
    param.add("ctr_id", contractorId, java.sql.Types.VARCHAR);
    param.add("cur_id", null, java.sql.Types.VARCHAR);
    param.add("filter", null, java.sql.Types.VARCHAR);
    param.add("con_seller", loadAllSellerIds(ctx), java.sql.Types.VARCHAR);
    param.add("allCon", "1", java.sql.Types.VARCHAR);
    param.add("onlyReusable", null, java.sql.Types.VARCHAR);
    param.add("conFinalDateAfterDb", null, java.sql.Types.VARCHAR);
    writeFromQuery(resp, ctx, "select-contracts-for-contractor-id", "con_id", "con_number", param, includeAll(req));
  }

  private void writeContractorsSearch(HttpServletResponse resp, ServletContext ctx, HttpServletRequest req) throws Exception {
    String q = req.getParameter("q");
    q = q != null ? q.trim() : "";
    if (q.length() < CONTRACTOR_SEARCH_MIN_CHARS) {
      writeSearchItems(resp, new ArrayList<Item>());
      return;
    }
    int limit = ApiJsonUtil.parseInt(req.getParameter("limit"), 20);
    if (limit <= 0) limit = 20;
    if (limit > CONTRACTOR_SEARCH_MAX_LIMIT) limit = CONTRACTOR_SEARCH_MAX_LIMIT;

    XMLResource sql = ApiDbUtil.sqlResource(ctx);
    if (sql == null) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(ApiJsonUtil.error("SQL_RESOURCE_MISSING", "sql resource not loaded"));
      return;
    }

    List<Item> items = new ArrayList<Item>();
    VDbConnection conn = null;
    VResultSet rs = null;
    try {
      VParameter param = new VParameter();
      param.add("filter", q, java.sql.Types.VARCHAR);
      conn = ApiDbUtil.getConnection();
      rs = DAOUtils.executeQuery(conn, sql.get("select-contractors-filter"), null, param);
      while (rs.next() && items.size() < limit) {
        String id = getSafe(rs, "ctr_id", 1);
        String label = getSafe(rs, "ctr_name", 2);
        if (id == null) id = "";
        if (label == null) label = id;
        items.add(new Item(id, label));
      }
    } finally {
      if (rs != null) try { rs.close(); } catch (Exception ignore) {}
      if (conn != null) conn.close();
    }
    writeSearchItems(resp, items);
  }

  private VParameter paramFilter(HttpServletRequest req) {
    VParameter param = new VParameter();
    param.add("filter", req.getParameter("filter"), java.sql.Types.VARCHAR);
    return param;
  }

  private VParameter paramUsers() {
    VParameter param = new VParameter();
    param.add("filter", null, java.sql.Types.VARCHAR);
    param.add("dep_id", null, java.sql.Types.VARCHAR);
    param.add("responsPersonInt", null, java.sql.Types.VARCHAR);
    return param;
  }

  private VParameter paramStuffCategory(HttpServletRequest req) {
    VParameter param = new VParameter();
    param.add("filter", req.getParameter("filter"), java.sql.Types.VARCHAR);
    param.add("montage", req.getParameter("montage"), java.sql.Types.VARCHAR);
    return param;
  }

  private boolean includeAll(HttpServletRequest req) {
    return ApiJsonUtil.parseBool(req.getParameter("includeAll"));
  }

  private void writeFromQuery(HttpServletResponse resp, ServletContext ctx, String sqlId, String idCol, String labelCol,
                              VParameter param, boolean includeAll) throws Exception {
    XMLResource sql = ApiDbUtil.sqlResource(ctx);
    if (sql == null) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(ApiJsonUtil.error("SQL_RESOURCE_MISSING", "sql resource not loaded"));
      return;
    }

    List<Item> items = new ArrayList<Item>();
    if (includeAll) {
      items.add(new Item(ApiMessageUtil.get("list.all_id"), ApiMessageUtil.get("list.all")));
    }

    VDbConnection conn = null;
    VResultSet rs = null;
    try {
      conn = ApiDbUtil.getConnection();
      List<Item> baseItems = new ArrayList<Item>(items);
      // Try up to 2 times if ResultSet gets closed by driver
      int attempts = 0;
      while (attempts < 2) {
        attempts++;
        try {
          rs = DAOUtils.executeQuery(conn, sql.get(sqlId), null, param);
          while (rs.next()) {
            String id = getSafe(rs, idCol, 1);
            String label = getSafe(rs, labelCol, 2);
            if (id == null) id = "";
            if (label == null) label = id;
            items.add(new Item(id, label));
          }
          break;
        } catch (Exception e) {
          if (isResultSetClosed(e) && attempts < 2) {
            if (rs != null) try { rs.close(); } catch (Exception ignore) {}
            items.clear();
            items.addAll(baseItems);
            continue;
          }
          throw e;
        }
      }
    } finally {
      if (rs != null) try { rs.close(); } catch (Exception ignore) {}
      if (conn != null) conn.close();
    }

    writeItems(resp, items);
  }

  private void writeItems(HttpServletResponse resp, List<Item> items) throws Exception {
    StringBuilder sb = new StringBuilder(4096);
    sb.append("{\"data\":[");
    boolean first = true;
    for (Item it : items) {
      if (!first) sb.append(',');
      first = false;
      sb.append("{\"id\":").append(ApiJsonUtil.q(it.id)).append(",\"label\":").append(ApiJsonUtil.q(it.label)).append("}");
    }
    sb.append("]}");
    resp.getWriter().write(sb.toString());
  }

  private void writeSearchItems(HttpServletResponse resp, List<Item> items) throws Exception {
    StringBuilder sb = new StringBuilder(2048);
    sb.append("{\"items\":[");
    boolean first = true;
    for (Item it : items) {
      if (!first) sb.append(',');
      first = false;
      sb.append("{\"id\":").append(ApiJsonUtil.q(it.id)).append(",\"label\":").append(ApiJsonUtil.q(it.label)).append("}");
    }
    sb.append("]}");
    resp.getWriter().write(sb.toString());
  }

  private String getSafe(VResultSet rs, String col, int idx) {
    try {
      String v = rs.getData(col);
      if (v != null) return v;
    } catch (Exception ignore) {}
    try {
      return rs.getData(idx);
    } catch (Exception ignore) {
      return null;
    }
  }

  private static boolean isResultSetClosed(Throwable e) {
    String msg = e != null ? e.getMessage() : null;
    return msg != null && msg.toLowerCase().contains("result set is closed");
  }

  private String loadAllSellerIds(ServletContext ctx) {
    VDbConnection conn = null;
    VResultSet rs = null;
    try {
      XMLResource sql = ApiDbUtil.sqlResource(ctx);
      if (sql == null) return null;
      conn = ApiDbUtil.getConnection();
      rs = DAOUtils.executeQuery(conn, sql.get("select_all_sellers_id"), null, null);
      StringBuilder sb = new StringBuilder();
      while (rs.next()) {
        String id = getSafe(rs, "sln_id", 1);
        if (id == null || id.trim().isEmpty()) continue;
        if (sb.length() > 0) sb.append(';');
        sb.append(id.trim());
      }
      return sb.length() > 0 ? sb.toString() : null;
    } catch (Exception e) {
      return null;
    } finally {
      if (rs != null) try { rs.close(); } catch (Exception ignore) {}
      if (conn != null) try { conn.close(); } catch (Exception ignore) {}
    }
  }

  private static class Item {
    final String id;
    final String label;

    Item(String id, String label) {
      this.id = id;
      this.label = label;
    }
  }
}
