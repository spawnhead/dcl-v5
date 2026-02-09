package net.sam.dcl.api;

import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.Department;
import net.sam.dcl.beans.Margin;
import net.sam.dcl.beans.MarginLine;
import net.sam.dcl.beans.Route;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.User;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.MarginForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.XMLResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MarginApiHandler {

  /**
   * Parity with legacy MarginAction: load full dataset, run Margin.calculate() (itog lines, merge),
   * return the same list as the old UI. No paging — one request returns all rows like the legacy screen.
   */
  public void handleData(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    MarginForm form = buildForm(req);
    String error = validate(form);
    if (error != null) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(ApiJsonUtil.error("BAD_REQUEST", error));
      return;
    }

    User currentUser = UserUtil.getCurrentUser(req);
    applyUserDefaults(form, currentUser);
    applyFilterLogic(form);
    applyRoleRestrictions(form, currentUser);

    boolean onlyTotal = "1".equalsIgnoreCase(form.getOnlyTotal());
    String dataSqlId = onlyTotal ? "select-margin_proc-common" : "select-margin-common";

    XMLResource sql = ApiDbUtil.sqlResource(req.getServletContext());
    if (sql == null) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(ApiJsonUtil.error("SQL_RESOURCE_MISSING", "sql resource not loaded"));
      return;
    }

    VDbConnection conn = null;
    try {
      conn = ApiDbUtil.getConnection();
      List<MarginLine> rawRows = loadFullRows(conn, sql.get(dataSqlId), form);
      Margin margin = new Margin();
      copyFormToMargin(form, margin);
      margin.setMarginLines(rawRows);
      margin.calculate();
      List<MarginLine> rows = margin.getMarginLines();
      resp.getWriter().write(buildResponseFull(rows, form));
    } finally {
      if (conn != null) conn.close();
    }
  }

  private void copyFormToMargin(MarginForm form, Margin margin) {
    margin.setUser(form.getUser());
    margin.setDepartment(form.getDepartment());
    margin.setContractor(form.getContractor());
    margin.setStuffCategory(form.getStuffCategory());
    margin.setRoute(form.getRoute());
    margin.setUser_aspect(form.getUser_aspect());
    margin.setDepartment_aspect(form.getDepartment_aspect());
    margin.setContractor_aspect(form.getContractor_aspect());
    margin.setStuff_category_aspect(form.getStuff_category_aspect());
    margin.setRoute_aspect(form.getRoute_aspect());
    margin.setOnlyTotal(form.getOnlyTotal());
    margin.setItog_by_spec(form.getItog_by_spec());
    margin.setItog_by_user(form.getItog_by_user());
    margin.setItog_by_product(form.getItog_by_product());
    margin.setGet_not_block(form.getGet_not_block());
    margin.setView_contractor(form.getView_contractor());
    margin.setView_country(form.getView_country());
    margin.setView_contract(form.getView_contract());
    margin.setView_stuff_category(form.getView_stuff_category());
    margin.setView_shipping(form.getView_shipping());
    margin.setView_payment(form.getView_payment());
    margin.setView_transport(form.getView_transport());
    margin.setView_transport_sum(form.getView_transport_sum());
    margin.setView_custom(form.getView_custom());
    margin.setView_other_sum(form.getView_other_sum());
    margin.setView_montage_sum(form.getView_montage_sum());
    margin.setView_montage_time(form.getView_montage_time());
    margin.setView_montage_cost(form.getView_montage_cost());
    margin.setView_update_sum(form.getView_update_sum());
    margin.setView_summ_zak(form.getView_summ_zak());
    margin.setView_koeff(form.getView_koeff());
    margin.setView_user(form.getView_user());
    margin.setView_department(form.getView_department());
  }

  private List<MarginLine> loadFullRows(VDbConnection conn, String sql, MarginForm form) throws Exception {
    VResultSet rs = null;
    try {
      rs = DAOUtils.executeQuery(conn, sql, form, null);
      return DAOUtils.resultSet2List(rs, MarginLine.class, null);
    } finally {
      if (rs != null) try { rs.close(); } catch (Exception ignore) {}
    }
  }

  private MarginForm buildForm(HttpServletRequest req) {
    MarginForm form = new MarginForm();
    form.setCanForm(true);
    form.setDate_begin(param(req, "dateBegin", "date_begin"));
    form.setDate_end(param(req, "dateEnd", "date_end"));

    form.setOnlyTotal(param(req, "onlyTotal", "onlyTotal"));
    form.setItog_by_spec(param(req, "itogBySpec", "itog_by_spec"));
    form.setItog_by_user(param(req, "itogByUser", "itog_by_user"));
    form.setItog_by_product(param(req, "itogByProduct", "itog_by_product"));
    form.setGet_not_block(param(req, "getNotBlock", "get_not_block"));

    form.setUser_aspect(param(req, "user_aspect", "user_aspect"));
    form.setDepartment_aspect(param(req, "department_aspect", "department_aspect"));
    form.setContractor_aspect(param(req, "contractor_aspect", "contractor_aspect"));
    form.setStuff_category_aspect(param(req, "stuff_category_aspect", "stuff_category_aspect"));
    form.setRoute_aspect(param(req, "route_aspect", "route_aspect"));

    User user = new User();
    String userId = param(req, "userId", "user.usr_id");
    user.setUsr_id(userId);
    String userName = param(req, "userName", "user.userFullName");
    if (!StringUtil.isEmpty(userName)) user.setUserFullName(userName);
    if (!StringUtil.isEmpty(userId) && StringUtil.isEmpty(user.getUserFullName())) user.setUserFullName(userId);
    form.setUser(user);

    Department dep = new Department();
    String depId = param(req, "departmentId", "department.id");
    dep.setId(depId);
    String depName = param(req, "departmentName", "department.name");
    if (!StringUtil.isEmpty(depName)) dep.setName(depName);
    if (!StringUtil.isEmpty(depId) && StringUtil.isEmpty(dep.getName())) dep.setName(depId);
    form.setDepartment(dep);

    Contractor ctr = new Contractor();
    String ctrId = param(req, "contractorId", "contractor.id");
    ctr.setId(ctrId);
    String ctrName = param(req, "contractorName", "contractor.name");
    if (!StringUtil.isEmpty(ctrName)) ctr.setName(ctrName);
    if (!StringUtil.isEmpty(ctrId) && StringUtil.isEmpty(ctr.getName())) ctr.setName(ctrId);
    form.setContractor(ctr);

    StuffCategory stf = new StuffCategory();
    String stfId = param(req, "stuffCategoryId", "stuffCategory.id");
    stf.setId(stfId);
    String stfName = param(req, "stuffCategoryName", "stuffCategory.name");
    if (!StringUtil.isEmpty(stfName)) stf.setName(stfName);
    if (!StringUtil.isEmpty(stfId) && StringUtil.isEmpty(stf.getName())) stf.setName(stfId);
    form.setStuffCategory(stf);

    Route route = new Route();
    String routeId = param(req, "routeId", "route.id");
    route.setId(routeId);
    String routeName = param(req, "routeName", "route.name");
    if (!StringUtil.isEmpty(routeName)) route.setName(routeName);
    if (!StringUtil.isEmpty(routeId) && StringUtil.isEmpty(route.getName())) route.setName(routeId);
    form.setRoute(route);

    // view_* from request (parity: "Отображать колонки" checkboxes); default "1" if not sent
    form.setView_contractor(viewParam(req, "view_contractor"));
    form.setView_country(viewParam(req, "view_country"));
    form.setView_contract(viewParam(req, "view_contract"));
    form.setView_stuff_category(viewParam(req, "view_stuff_category"));
    form.setView_shipping(viewParam(req, "view_shipping"));
    form.setView_payment(viewParam(req, "view_payment"));
    form.setView_transport(viewParam(req, "view_transport"));
    form.setView_transport_sum(viewParam(req, "view_transport_sum"));
    form.setView_custom(viewParam(req, "view_custom"));
    form.setView_other_sum(viewParam(req, "view_other_sum"));
    form.setView_montage_sum(viewParam(req, "view_montage_sum"));
    form.setView_montage_time(StringUtil.isEmpty(param(req, "view_montage_time", "view_montage_time")) ? "" : param(req, "view_montage_time", "view_montage_time"));
    form.setView_montage_cost(viewParam(req, "view_montage_cost"));
    form.setView_update_sum(viewParam(req, "view_update_sum"));
    form.setView_summ_zak(viewParam(req, "view_summ_zak"));
    form.setView_koeff(viewParam(req, "view_koeff"));
    form.setView_user(viewParam(req, "view_user"));
    form.setView_department(viewParam(req, "view_department"));

    return form;
  }

  private String viewParam(HttpServletRequest req, String key) {
    String p = param(req, key, key);
    return !StringUtil.isEmpty(p) ? p : "1";
  }

  private void applyUserDefaults(MarginForm form, User user) {
    if (user == null) return;
    if (user.isManager() && !user.isAdmin() && !user.isEconomist()) {
      form.setReadOnlyForManager(true);
      form.setUser(user);
      if (user.isChiefDepartment()) {
        form.setShowForChiefDep(true);
        form.setDepartment(user.getDepartment());
      } else {
        form.setShowForChiefDep(false);
      }
    } else {
      form.setReadOnlyForManager(false);
    }
  }

  private void applyRoleRestrictions(MarginForm form, User user) {
    if (user == null) return;
    if (!user.isAdmin() && !user.isEconomist()) {
      form.setView_other_sum("");
      form.setLogisticsReadOnly(true);
    }
  }

  private String validate(MarginForm form) {
    if (StringUtil.isEmpty(form.getDate_begin()) || StringUtil.isEmpty(form.getDate_end())) {
      return "dateBegin/dateEnd required";
    }
    boolean hasFilter = !StringUtil.isEmpty(form.getUser().getUsr_id())
        || !StringUtil.isEmpty(form.getDepartment().getId())
        || !StringUtil.isEmpty(form.getContractor().getId())
        || !StringUtil.isEmpty(form.getStuffCategory().getId())
        || !StringUtil.isEmpty(form.getRoute().getId());
    if (!hasFilter) {
      return "at least one filter required";
    }
    return null;
  }

  private int loadCount(VDbConnection conn, String sql, MarginForm form) throws Exception {
    VResultSet rs = null;
    try {
      rs = DAOUtils.executeQuery(conn, sql, form, null);
      if (rs.next()) {
        String v = rs.getData("total_count");
        return ApiJsonUtil.parseInt(v, 0);
      }
      return 0;
    } finally {
      if (rs != null) try { rs.close(); } catch (Exception ignore) {}
    }
  }

  private List<MarginLine> loadRows(VDbConnection conn, String sql, MarginForm form, int pageSize, int offset) throws Exception {
    VResultSet rs = null;
    VParameter param = new VParameter();
    param.add("pageSize", Integer.toString(pageSize), Types.INTEGER);
    param.add("offset", Integer.toString(offset), Types.INTEGER);
    List<MarginLine> out = new ArrayList<MarginLine>();
    try {
      rs = DAOUtils.executeQuery(conn, sql, form, param);
      while (rs.next()) {
        MarginLine row = new MarginLine();
        DAOUtils.loadObject(rs, row);
        out.add(row);
      }
      return out;
    } finally {
      if (rs != null) try { rs.close(); } catch (Exception ignore) {}
    }
  }

  private String buildResponse(List<MarginLine> rows, int page, int pageSize, boolean hasMore, MarginForm form) {
    StringBuilder sb = new StringBuilder(8192);
    sb.append("{\"data\":[");
    boolean first = true;
    for (MarginLine r : rows) {
      if (!first) sb.append(',');
      first = false;
      sb.append(rowToJson(r));
    }
    sb.append("],\"view\":");
    sb.append(viewToJson(form));
    sb.append(",\"meta\":{");
    sb.append("\"page\":").append(page).append(',');
    sb.append("\"pageSize\":").append(pageSize).append(',');
    sb.append("\"hasMore\":").append(hasMore);
    sb.append("}}");
    return sb.toString();
  }

  /** Full report response (parity with legacy): data + view + meta.totalCount. */
  private String buildResponseFull(List<MarginLine> rows, MarginForm form) {
    StringBuilder sb = new StringBuilder(8192);
    sb.append("{\"data\":[");
    boolean first = true;
    for (MarginLine r : rows) {
      if (!first) sb.append(',');
      first = false;
      sb.append(rowToJson(r));
    }
    sb.append("],\"view\":");
    sb.append(viewToJson(form));
    sb.append(",\"meta\":{\"totalCount\":").append(rows.size()).append("}}");
    return sb.toString();
  }

  private void applyFilterLogic(MarginForm form) {
    if (form.isCanForm()) {
      form.setSelect_list(form.getCommon1());
      form.setGroup_by("");

      if ("1".equalsIgnoreCase(form.getGet_not_block())) {
        form.setCtc_block("");
      } else {
        form.setCtc_block("1");
      }

      if (!form.isCheckAspect()) {
        if (!"1".equalsIgnoreCase(form.getOnlyTotal())) {
          // default
        } else {
          form.setView_contractor("1");
          form.setView_country("1");
          form.setView_contract("1");
          form.setView_stuff_category("1");
          form.setView_shipping("1");
          form.setView_payment("1");
          form.setView_transport("1");
          form.setView_transport_sum("1");
          form.setView_custom("1");
          form.setView_other_sum("1");
          form.setView_montage_sum("1");
          form.setView_montage_time("1");
          form.setView_montage_cost("1");
          form.setView_update_sum("1");
          form.setView_summ_zak("1");
          form.setView_koeff("1");
          form.setView_user("1");
          form.setView_department("1");

          String select = form.getCommon2();
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) {
            select += " usr_name ";
            form.setSelect_list(select);
            form.setGroup_by(" group by usr_name ");

            form.setView_contractor("");
            form.setView_country("");
            form.setView_contract("");
            form.setView_shipping("");
            form.setView_payment("");
            form.setView_department("");
            form.setView_stuff_category("");
          }

          if (!StringUtil.isEmpty(form.getDepartment().getName()) && !form.isShowForChiefDep()) {
            select += " dep_name ";
            form.setSelect_list(select);
            form.setGroup_by(" group by dep_name ");

            form.setView_contractor("");
            form.setView_country("");
            form.setView_contract("");
            form.setView_shipping("");
            form.setView_payment("");
            form.setView_user("");
            form.setView_stuff_category("");
          }

          if (!StringUtil.isEmpty(form.getContractor().getName())) {
            select += " ctr_name, cut_name ";
            form.setSelect_list(select);
            form.setGroup_by(" group by ctr_name, cut_name ");

            form.setView_contract("");
            form.setView_shipping("");
            form.setView_payment("");
            form.setView_user("");
            form.setView_department("");
            form.setView_stuff_category("");
          }

          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) {
            select += " stf_name ";
            form.setSelect_list(select);
            form.setGroup_by(" group by stf_name ");

            form.setView_contract("");
            form.setView_shipping("");
            form.setView_payment("");
            form.setView_user("");
            form.setView_department("");
            form.setView_contractor("");
            form.setView_country("");
          }

          if (!StringUtil.isEmpty(form.getRoute().getName())) {
            select += " rut_name ";
            form.setSelect_list(select);
            form.setGroup_by(" group by rut_name ");

            form.setView_contract("");
            form.setView_shipping("");
            form.setView_payment("");
            form.setView_user("");
            form.setView_department("");
            form.setView_contractor("");
            form.setView_country("");
            form.setView_stuff_category("");
          }
        }
      } else {
        form.setView_contractor("1");
        form.setView_country("1");
        form.setView_contract("1");
        form.setView_stuff_category("1");
        form.setView_shipping("1");
        form.setView_payment("1");
        form.setView_transport("1");
        form.setView_transport_sum("1");
        form.setView_custom("1");
        form.setView_other_sum("1");
        form.setView_montage_sum("1");
        form.setView_montage_time("1");
        form.setView_montage_cost("1");
        form.setView_update_sum("1");
        form.setView_summ_zak("1");
        form.setView_koeff("1");
        form.setView_user("1");
        form.setView_department("1");

        String groupBy = "";
        String orderBy = "";
        if (!"1".equalsIgnoreCase(form.getOnlyTotal())) {
          String select = form.getCommon1();
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) {
            orderBy = " order by usr_name ";
          }
          if (!StringUtil.isEmpty(form.getDepartment().getName()) &&
              ("-1".equalsIgnoreCase(form.getUser().getUsr_id()) || StringUtil.isEmpty(form.getUser().getUserFullName()) )) {
            orderBy = " order by dep_name ";
          }
          if (!StringUtil.isEmpty(form.getContractor().getName())) {
            orderBy = " order by ctr_name ";
          }
          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) {
            orderBy = " order by stf_name ";
          }
          if (!StringUtil.isEmpty(form.getRoute().getName())) {
            orderBy = " order by rut_name ";
          }

          if ("1".equals(form.getUser_aspect())) {
            orderBy += " ,usr_name ";
          }
          if ("1".equals(form.getDepartment_aspect())) {
            orderBy += " ,dep_name ";
          }
          if ("1".equals(form.getContractor_aspect())) {
            orderBy += " ,ctr_name, cut_name ";
          }
          if ("1".equals(form.getStuff_category_aspect())) {
            orderBy += " ,stf_name ";
          }
          if ("1".equals(form.getRoute_aspect())) {
            orderBy += " ,rut_name ";
          }

          form.setSelect_list(select);
          form.setGroup_by(groupBy);
          form.setOrder_by(orderBy);
        } else {
          String select = form.getCommon2();
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) {
            select += " usr_name ";
            groupBy = " group by usr_name ";
            orderBy = " order by usr_name ";
          }
          if (!StringUtil.isEmpty(form.getDepartment().getName()) &&
              ("-1".equalsIgnoreCase(form.getUser().getUsr_id()) || StringUtil.isEmpty(form.getUser().getUserFullName()) ) ) {
            select += " dep_name ";
            groupBy = " group by dep_name ";
            orderBy = " order by dep_name ";
          }
          if (!StringUtil.isEmpty(form.getContractor().getName())) {
            select += " ctr_name, cut_name ";
            groupBy = " group by ctr_name, cut_name ";
            orderBy = " order by ctr_name ";
          }
          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) {
            select += " stf_name ";
            groupBy = " group by stf_name ";
            orderBy = " order by stf_name ";
          }
          if (!StringUtil.isEmpty(form.getRoute().getName())) {
            select += " rut_name ";
            groupBy = " group by rut_name ";
            orderBy = " order by rut_name ";
          }

          if ("1".equals(form.getUser_aspect())) {
            select += " ,usr_name ";
            groupBy += " ,usr_name ";
            orderBy += " ,usr_name ";
          } else {
            form.setView_user("");
          }
          if ("1".equals(form.getDepartment_aspect())) {
            select += " ,dep_name ";
            groupBy += " ,dep_name ";
            orderBy += " ,dep_name ";
          } else {
            form.setView_department("");
          }
          if ("1".equals(form.getContractor_aspect())) {
            select += " ,ctr_name, cut_name ";
            groupBy += " ,ctr_name, cut_name ";
            orderBy += " ,ctr_name ";
          } else {
            form.setView_contractor("");
            form.setView_country("");
          }
          if ("1".equals(form.getStuff_category_aspect())) {
            select += " ,stf_name ";
            groupBy += " ,stf_name ";
            orderBy += " ,stf_name ";
          } else {
            form.setView_stuff_category("");
          }
          if ("1".equals(form.getRoute_aspect())) {
            select += " ,rut_name ";
            groupBy += " ,rut_name ";
            orderBy += " ,rut_name ";
          }

          form.setSelect_list(select);
          form.setGroup_by(groupBy);
          form.setOrder_by(orderBy);

          form.setView_contract("");
          form.setView_shipping("");
          form.setView_payment("");
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) {
            form.setView_user("1");
          }
          if (!StringUtil.isEmpty(form.getDepartment().getName())) {
            form.setView_department("1");
          }
          if (!StringUtil.isEmpty(form.getContractor().getName())) {
            form.setView_contractor("1");
            form.setView_country("1");
          }
          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) {
            form.setView_stuff_category("1");
          }

        }
      }
    }
  }

  private static String param(HttpServletRequest req, String primary, String fallback) {
    String v = req.getParameter(primary);
    if (StringUtil.isEmpty(v)) {
      v = req.getParameter(fallback);
    }
    return v;
  }

  private static String viewToJson(MarginForm form) {
    if (form == null) return "{}";
    StringBuilder sb = new StringBuilder(512);
    sb.append('{');
    sb.append("\"view_contractor\":").append(b(form.getView_contractor())).append(',');
    sb.append("\"view_country\":").append(b(form.getView_country())).append(',');
    sb.append("\"view_contract\":").append(b(form.getView_contract())).append(',');
    sb.append("\"view_stuff_category\":").append(b(form.getView_stuff_category())).append(',');
    sb.append("\"view_shipping\":").append(b(form.getView_shipping())).append(',');
    sb.append("\"view_payment\":").append(b(form.getView_payment())).append(',');
    sb.append("\"view_transport\":").append(b(form.getView_transport())).append(',');
    sb.append("\"view_transport_sum\":").append(b(form.getView_transport_sum())).append(',');
    sb.append("\"view_custom\":").append(b(form.getView_custom())).append(',');
    sb.append("\"view_other_sum\":").append(b(form.getView_other_sum())).append(',');
    sb.append("\"view_montage_sum\":").append(b(form.getView_montage_sum())).append(',');
    sb.append("\"view_montage_time\":").append(b(form.getView_montage_time())).append(',');
    sb.append("\"view_montage_cost\":").append(b(form.getView_montage_cost())).append(',');
    sb.append("\"view_update_sum\":").append(b(form.getView_update_sum())).append(',');
    sb.append("\"view_summ_zak\":").append(b(form.getView_summ_zak())).append(',');
    sb.append("\"view_koeff\":").append(b(form.getView_koeff())).append(',');
    sb.append("\"view_user\":").append(b(form.getView_user())).append(',');
    sb.append("\"view_department\":").append(b(form.getView_department()));
    sb.append('}');
    return sb.toString();
  }

  private static String rowToJson(MarginLine r) {
    StringBuilder sb = new StringBuilder(1024);
    sb.append('{');
    sb.append("\"ctr_name\":").append(ApiJsonUtil.q(r.getCtr_name())).append(',');
    sb.append("\"cut_name\":").append(ApiJsonUtil.q(r.getCut_name())).append(',');
    sb.append("\"con_number_formatted\":").append(ApiJsonUtil.q(r.getCon_number_formatted_exel())).append(',');
    sb.append("\"con_date_formatted\":").append(ApiJsonUtil.q(r.getCon_date_formatted())).append(',');
    sb.append("\"spc_number_formatted\":").append(ApiJsonUtil.q(r.getSpc_number_formatted_exel())).append(',');
    sb.append("\"spc_date_formatted\":").append(ApiJsonUtil.q(r.getSpc_date_formatted())).append(',');
    sb.append("\"spc_summ_formatted\":").append(ApiJsonUtil.q(r.getSpc_summ_formatted())).append(',');
    sb.append("\"cur_name\":").append(ApiJsonUtil.q(r.getCur_name())).append(',');
    sb.append("\"stf_name_show\":").append(ApiJsonUtil.q(r.getStf_name_show())).append(',');
    sb.append("\"shp_number_show\":").append(ApiJsonUtil.q(r.getShp_number_show())).append(',');
    sb.append("\"shp_date_show\":").append(ApiJsonUtil.q(r.getShp_date_show())).append(',');
    sb.append("\"pay_date_show\":").append(ApiJsonUtil.q(r.getPay_date_show())).append(',');
    sb.append("\"lps_summ_eur_formatted\":").append(ApiJsonUtil.q(r.getLps_summ_eur_formatted())).append(',');
    sb.append("\"lps_summ_formatted\":").append(ApiJsonUtil.q(r.getLps_summ_formatted())).append(',');
    sb.append("\"lps_sum_transport_formatted\":").append(ApiJsonUtil.q(r.getLps_sum_transport_formatted())).append(',');
    sb.append("\"lcc_transport_formatted\":").append(ApiJsonUtil.q(r.getLcc_transport_formatted())).append(',');
    sb.append("\"lps_custom_formatted\":").append(ApiJsonUtil.q(r.getLps_custom_formatted())).append(',');
    sb.append("\"lcc_charges_formatted\":").append(ApiJsonUtil.q(r.getLcc_charges_formatted())).append(',');
    sb.append("\"lcc_montage_formatted\":").append(ApiJsonUtil.q(r.getLcc_montage_formatted())).append(',');
    sb.append("\"lps_montage_time_formatted\":").append(ApiJsonUtil.q(r.getLps_montage_time_formatted())).append(',');
    sb.append("\"montage_cost_formatted\":").append(ApiJsonUtil.q(r.getMontage_cost_formatted())).append(',');
    sb.append("\"lcc_update_sum_formatted\":").append(ApiJsonUtil.q(r.getLcc_update_sum_formatted())).append(',');
    sb.append("\"summ_formatted\":").append(ApiJsonUtil.q(r.getSumm_formatted())).append(',');
    sb.append("\"summ_zak_formatted\":").append(ApiJsonUtil.q(r.getSumm_zak_formatted())).append(',');
    sb.append("\"margin_formatted\":").append(ApiJsonUtil.q(r.getMargin_formatted())).append(',');
    sb.append("\"koeff_formatted\":").append(ApiJsonUtil.q(r.getKoeff_formatted())).append(',');
    sb.append("\"usr_name_show\":").append(ApiJsonUtil.q(r.getUsr_name_show())).append(',');
    sb.append("\"dep_name_show\":").append(ApiJsonUtil.q(r.getDep_name_show())).append(',');
    sb.append("\"itogLine\":").append(r.isItogLine() ? "true" : "false").append(',');
    sb.append("\"spc_group_delivery\":").append(ApiJsonUtil.q(r.getSpc_group_delivery())).append(',');
    sb.append("\"haveUnblockedPrc\":").append(r.haveUnblockedPrc() ? "true" : "false");
    sb.append('}');
    return sb.toString();
  }

  private static String b(String val) {
    return "1".equals(val) ? "true" : "false";
  }
}
