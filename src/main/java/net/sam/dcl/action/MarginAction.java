package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.MarginForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.dao.DepartmentDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class MarginAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private static final Log log = LogFactory.getLog(MarginAction.class);

  private void saveFormToBean(IActionContext context)
  {
    MarginForm form = (MarginForm) context.getForm();

    Margin margin = (Margin) StoreUtil.getSession(context.getRequest(), Margin.class);

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

    margin.setMarginLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), margin);
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    final MarginForm form = (MarginForm) context.getForm();

    if (form.isCanForm())
    {
      form.setSelect_list(form.getCommon1());
      form.setGroup_by("");

      if ("1".equalsIgnoreCase(form.getGet_not_block())) //и не заблакированные
      {
        form.setCtc_block("");
      }
      else
      {
        form.setCtc_block("1");
      }

      if (!form.isCheckAspect()) //не выбрано - в разрезе
      {
        if (!"1".equalsIgnoreCase(form.getOnlyTotal())) //итог не отмечен
        {
          //default
        }
        else //итого отмечен, но в разрезе не отмечено
        {
          //отображать пока все поля
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
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) //group by user
          {
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

          if (!StringUtil.isEmpty(form.getDepartment().getName()) && !form.isShowForChiefDep()) //group by department
          {
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

          if (!StringUtil.isEmpty(form.getContractor().getName())) //group by contractor
          {
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

          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) //group by stuffCategory
          {
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

          if (!StringUtil.isEmpty(form.getRoute().getName())) //group by route
          {
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
      }
      else //выброно - в разрезе по - т.е. всегда есть групировка
      {
        //отображать пока все поля
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
        if (!"1".equalsIgnoreCase(form.getOnlyTotal())) //итог не отмечен и групируем по - стандартная выборка всех строк с звезданутой групировкой
        {
          String select = form.getCommon1();
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) //group by user
          {
            orderBy = " order by usr_name ";
          }
          if (!StringUtil.isEmpty(form.getDepartment().getName()) &&
              ("-1".equalsIgnoreCase(form.getUser().getUsr_id()) || StringUtil.isEmpty(form.getUser().getUserFullName()) )) //group by department
          {
            orderBy = " order by dep_name ";
          }
          if (!StringUtil.isEmpty(form.getContractor().getName())) //group by contractor
          {
            orderBy = " order by ctr_name ";
          }
          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) //group by stuffCategory
          {
            orderBy = " order by stf_name ";
          }
          if (!StringUtil.isEmpty(form.getRoute().getName())) //group by route
          {
            orderBy = " order by rut_name ";
          }

          if ("1".equals(form.getUser_aspect())) //пользователь в разрезе пользователя - group by user
          {
            orderBy += " ,usr_name ";
          }
          if ("1".equals(form.getDepartment_aspect())) //пользователь в разрезе отдела - group by Department
          {
            orderBy += " ,dep_name ";
          }
          if ("1".equals(form.getContractor_aspect())) //пользователь в разрезе контрактора - group by Contractor
          {
            orderBy += " ,ctr_name, cut_name ";
          }
          if ("1".equals(form.getStuff_category_aspect())) //пользователь в разрезе категорий - group by stuffCategory
          {
            orderBy += " ,stf_name ";
          }
          if ("1".equals(form.getRoute_aspect())) //пользователь в разрезе маршрутов - group by Route
          {
            orderBy += " ,rut_name ";
          }

          form.setSelect_list(select);
          form.setGroup_by(groupBy);
          form.setOrder_by(orderBy);
        }
        else //итог отмечен и нужно групировать по
        {
          String select = form.getCommon2();
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) //group by user
          {
            select += " usr_name ";
            groupBy = " group by usr_name ";
            orderBy = " order by usr_name ";
          }
          if (!StringUtil.isEmpty(form.getDepartment().getName()) &&
              ("-1".equalsIgnoreCase(form.getUser().getUsr_id()) || StringUtil.isEmpty(form.getUser().getUserFullName()) ) ) //group by department
          {
            select += " dep_name ";
            groupBy = " group by dep_name ";
            orderBy = " order by dep_name ";
          }
          if (!StringUtil.isEmpty(form.getContractor().getName())) //group by contractor
          {
            select += " ctr_name, cut_name ";
            groupBy = " group by ctr_name, cut_name ";
            orderBy = " order by ctr_name ";
          }
          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) //group by stuffCategory
          {
            select += " stf_name ";
            groupBy = " group by stf_name ";
            orderBy = " order by stf_name ";
          }
          if (!StringUtil.isEmpty(form.getRoute().getName())) //group by route
          {
            select += " rut_name ";
            groupBy = " group by rut_name ";
            orderBy = " order by rut_name ";
          }

          if ("1".equals(form.getUser_aspect())) //пользователь в разрезе пользователя - group by user
          {
            select += " ,usr_name ";
            groupBy += " ,usr_name ";
            orderBy += " ,usr_name ";
          }
          else
          {
            form.setView_user("");
          }
          if ("1".equals(form.getDepartment_aspect())) //пользователь в разрезе отдела - group by Department
          {
            select += " ,dep_name ";
            groupBy += " ,dep_name ";
            orderBy += " ,dep_name ";
          }
          else
          {
            form.setView_department("");
          }
          if ("1".equals(form.getContractor_aspect())) //пользователь в разрезе контрактора - group by Contractor
          {
            select += " ,ctr_name, cut_name ";
            groupBy += " ,ctr_name, cut_name ";
            orderBy += " ,ctr_name ";
          }
          else
          {
            form.setView_contractor("");
            form.setView_country("");
          }
          if ("1".equals(form.getStuff_category_aspect())) //пользователь в разрезе категорий - group by stuffCategory
          {
            select += " ,stf_name ";
            groupBy += " ,stf_name ";
            orderBy += " ,stf_name ";
          }
          else
          {
            form.setView_stuff_category("");
          }
          if ("1".equals(form.getRoute_aspect())) //пользователь в разрезе маршрутов - group by Route
          {
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
          if (!StringUtil.isEmpty(form.getUser().getUserFullName())) //group by user
          {
            form.setView_user("1");
          }
          if (!StringUtil.isEmpty(form.getDepartment().getName())) //group by department
          {
            form.setView_department("1");
          }
          if (!StringUtil.isEmpty(form.getContractor().getName())) //group by contractor
          {
            form.setView_contractor("1");
            form.setView_country("1");
          }
          if (!StringUtil.isEmpty(form.getStuffCategory().getName())) //group by stuffCategory
          {
            form.setView_stuff_category("1");
          }

        }
      }

      if (!"1".equalsIgnoreCase(form.getOnlyTotal())) //итог не отмечен
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-margin-common", MarginLine.class, null, null);
      }
      else
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-margin_proc-common", MarginLine.class, null, null);
      }
      saveFormToBean(context);
      Margin margin = (Margin) StoreUtil.getSession(context.getRequest(), Margin.class);
      margin.calculate();
      StoreUtil.putSession(context.getRequest(), margin);
      form.getGrid().setDataList(margin.getMarginLines());

      context.getRequest().setAttribute("show-spec-summ", new IShowChecker()
      {
        int size = form.getGrid().getDataList().size();

        public boolean check(ShowCheckerContext context)
        {
          return !(context.getTable().getRecordCounter() >= size );
        }
      }
      );

      context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
      {
        int size = form.getGrid().getDataList().size();

        public String check(StyleClassCheckerContext context)
        {
          MarginLine marginLine = (MarginLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
          if (marginLine.isItogLine())
          {
            return "bold-cell";
          }
          if (!StringUtil.isEmpty(marginLine.getSpc_group_delivery()))
          {
            if ( marginLine.haveUnblockedPrc() )
            {
              return "green-font-cell-pink";
            }
            return "cell-green";
          }
          if ( marginLine.haveUnblockedPrc() )
          {
            return "cell-pink";
          }
          return "";
        }
      });

    }

    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( !user.isAdmin() && !user.isEconomist() )
    {
      form.setView_other_sum("");
      form.setLogisticsReadOnly(true);
    }
    if (!StringUtil.isEmpty(form.getUser().getUsr_id()) && !"-1".equals(form.getUser().getUsr_id()))
    {
      form.setUser(UserDAO.load(context, form.getUser().getUsr_id()));
    }
    if (!StringUtil.isEmpty(form.getDepartment().getId()) && !"-1".equals(form.getDepartment().getId()))
    {
      form.setDepartment(DepartmentDAO.load(context, form.getDepartment().getId()));
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward generate(IActionContext context) throws Exception
  {
    MarginForm form = (MarginForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    Margin margin = (Margin) StoreUtil.getSession(context.getRequest(), Margin.class);
    Grid2Excel grid2Excel = new Grid2Excel("Margin Report");
    grid2Excel.doGrid2Excel(context, margin.getExcelTable());
    return null;
  }

  /**
   * Development / Margin (AG Grid): JSON endpoint for current Margin session data.
   * Returns HTTP 200 with either {data:..., view:..., meta:...} or {error:...}
   */
  public ActionForward data(IActionContext context) throws Exception
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
      log.error("MarginDev: dispatch=data failed: " + msg, e);

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

  public ActionForward cleanAll(IActionContext context) throws Exception
  {
    MarginForm form = (MarginForm) context.getForm();
    form.setCanForm(false);
    Margin margin = (Margin) StoreUtil.getSession(context.getRequest(), Margin.class);
    margin.cleanList();

    return filter(context);
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
    // Render mostly *_formatted/*_show fields to match legacy table output.
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

    // For row styling / rules:
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

  public ActionForward input(IActionContext context) throws Exception
  {
    MarginForm form = (MarginForm) context.getForm();
    form.setCanForm(false);

    // If opened from Development → Margin, keep submenu highlight even when URL is direct.
    try
    {
      String sp = context.getRequest().getServletPath();
      if (sp != null && sp.indexOf("MarginDevAction") >= 0)
      {
        context.getRequest().getSession().setAttribute(Outline.MENU_ID_NAME, "id.dev_margin");
      }
    }
    catch (Exception ignore)
    {
    }

    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( user.isManager() && !user.isAdmin() && !user.isEconomist() )
    {
      form.setReadOnlyForManager(true);
      form.setUser(user);
      if ( user.isChiefDepartment() )
      {
        form.setShowForChiefDep(true);
        form.setDepartment(user.getDepartment());
      }
      else
      {
        form.setShowForChiefDep(false);
      }
    }
    else
    {
      form.setReadOnlyForManager(false);
    }

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
    form.setView_montage_time("");
    form.setView_montage_cost("1");
    form.setView_update_sum("1");
    form.setView_summ_zak("1");
    form.setView_koeff("1");
    form.setView_user("1");
    form.setView_department("1");

    form.setGet_not_block("1");

    Margin margin = new Margin();
    StoreUtil.putSession(context.getRequest(), margin);
    saveFormToBean(context);

    return filter(context);
  }
}
