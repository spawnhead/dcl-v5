package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.ShippingReportForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.dao.DepartmentDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ShippingReportAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private void saveFormToBean(IActionContext context)
  {
    ShippingReportForm form = (ShippingReportForm) context.getForm();

    ShippingReport shippingReport = (ShippingReport) StoreUtil.getSession(context.getRequest(), ShippingReport.class);

    shippingReport.setUser(form.getUser());
    shippingReport.setDepartment(form.getDepartment());
    shippingReport.setContractor(form.getContractor());

    shippingReport.setOnlyTotal(form.getOnlyTotal());
    shippingReport.setItog_by_shp(form.getItog_by_shp());
    shippingReport.setItog_by_user(form.getItog_by_user());
    shippingReport.setItog_by_product(form.getItog_by_product());
    shippingReport.setItog_by_produce(form.getItog_by_produce());
    shippingReport.setNot_include_zero(form.getNot_include_zero());
    shippingReport.setInclude_all(form.getInclude_all());
    shippingReport.setInclude_closed(form.getInclude_closed());
    shippingReport.setInclude_opened(form.getInclude_opened());

    shippingReport.setView_contractor(form.getView_contractor());
    shippingReport.setView_country(form.getView_country());
    shippingReport.setView_user_left(form.getView_user_left());
    shippingReport.setView_department_left(form.getView_department_left());
    shippingReport.setView_contract(form.getView_contract());
    shippingReport.setView_stuff_category(form.getView_stuff_category());
    shippingReport.setView_produce(form.getView_produce());
    shippingReport.setView_summ(form.getView_summ());
    shippingReport.setView_summ_without_nds(form.getView_summ_without_nds());
    shippingReport.setView_summ_eur(form.getView_summ_eur());
    shippingReport.setView_user(form.getView_user());
    shippingReport.setView_department(form.getView_department());
    shippingReport.setShowShpNumDate(form.isShowShpNumDate());

    shippingReport.setShippingReportLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), shippingReport);
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    final ShippingReportForm form = (ShippingReportForm) context.getForm();

    if (StringUtil.isEmpty(form.getDate_begin()) || StringUtil.isEmpty(form.getDate_end()))
    {
      return context.getMapping().getInputForward();
    }

    if (!StringUtil.isEmpty(form.getUser().getUsr_id()) && !"-1".equals(form.getUser().getUsr_id()))
    {
      form.setUser(UserDAO.load(context, form.getUser().getUsr_id()));
    }
    if (!StringUtil.isEmpty(form.getDepartment().getId()) && !"-1".equals(form.getDepartment().getId()))
    {
      form.setDepartment(DepartmentDAO.load(context, form.getDepartment().getId()));
    }

    if (form.isCanForm())
    {
      form.setView_user_left("");
      form.setView_department_left("");

      form.setShowShpNumDate(true);
      form.setSelect_list(ShippingReportForm.selectAll);
      form.setGroup_by("");
      form.setOrder_by(ShippingReportForm.orderByDefault);

      if (!"1".equalsIgnoreCase(form.getOnlyTotal())) //итог не отмечен
      {
        if ("1".equalsIgnoreCase(form.getItog_by_shp()))
        {
          form.setOrder_by(ShippingReportForm.orderByShp);
          if ("1".equalsIgnoreCase(form.getItog_by_user()))
          {
            form.setOrder_by(form.getOrder_by() + ShippingReportForm.orderByShpUser);
          }
          if ("1".equalsIgnoreCase(form.getItog_by_product()))
          {
            form.setOrder_by(form.getOrder_by() + ShippingReportForm.orderByShpStuff);
          }
          if ("1".equalsIgnoreCase(form.getItog_by_produce()))
          {
            form.setOrder_by(form.getOrder_by() + ShippingReportForm.orderByShpProduce);
          }
        }
      }

      if ("1".equalsIgnoreCase(form.getOnlyTotal())) //итог отмечен
      {
        form.setShowShpNumDate(false);
        form.setView_contractor("");
        form.setView_country("");
        form.setView_contract("");
        form.setView_stuff_category("");
        form.setView_produce("");
        form.setView_user("");
        form.setView_department("");

        form.setSelect_list(ShippingReportForm.selectOnlyItod);

        User user = UserUtil.getCurrentUser(context.getRequest());
        if ( "-1".equals(form.getContractor().getId()) || !StringUtil.isEmpty(form.getContractor().getName()) )
        {
          form.setView_contractor("1");
          form.setView_country("1");
          form.setSelect_list(form.getSelect_list() + ShippingReportForm.selectOnlyItodCtr);
          form.setGroup_by(ShippingReportForm.groupByOnlyItodCtr);
        }
        if ( "-1".equals(form.getUser().getUsr_id()) || !StringUtil.isEmpty(form.getUser().getUserFullName()) )
        {
          form.setView_user_left("1");
          form.setSelect_list(form.getSelect_list() + ShippingReportForm.selectOnlyItodUsr);
          if ( user.isChiefDepartment() )
          {
            form.setGroup_by(ShippingReportForm.groupByOnlyItodForChief);
          }
          else
          {
            form.setGroup_by(ShippingReportForm.groupByOnlyItodUsr);
          }
        }
        if ( "-1".equals(form.getDepartment().getId()) || !StringUtil.isEmpty(form.getDepartment().getName()) )
        {
          form.setView_department_left("1");
          if ( user.isChiefDepartment() )
          {
            form.setSelect_list(form.getSelect_list() + ShippingReportForm.selectOnlyItodDepForChief);
          }
          else
          {
            form.setSelect_list(form.getSelect_list() + ShippingReportForm.selectOnlyItodDep);
            form.setGroup_by(ShippingReportForm.groupByOnlyItodDep);
          }
        }
      }

      //если Формировать отчет по Контрагент
      //Не отображаем колонку Контрагент (за исключением случая, когда выбрано "Все")
      if ( !"-1".equals(form.getContractor().getId()) && !StringUtil.isEmpty(form.getContractor().getName()) )
      {
        form.setView_contractor("");
        form.setView_country("");
      }
      //если Формировать отчет по Пользователь
      //Не отображаем колонку Пользователь (за исключением случая, когда выбрано "Все")
      if ( !"-1".equals(form.getUser().getUsr_id()) && !StringUtil.isEmpty(form.getUser().getUserFullName()) )
      {
        form.setView_user("");
      }
      //если Формировать отчет по Отдел
      //Не отображаем колонку Отдел (за исключением случая, когда выбрано "Все")
      if ( !"-1".equals(form.getDepartment().getId()) && !StringUtil.isEmpty(form.getDepartment().getName()) )
      {
        form.setView_department("");
      }
      
      if (
           ( "1".equalsIgnoreCase(form.getItog_by_shp()) && "1".equalsIgnoreCase(form.getItog_by_user()) ) ||
           ( !"-1".equals(form.getDepartment().getId()) && !StringUtil.isEmpty(form.getDepartment().getName()) ) ||
           ( !"-1".equals(form.getUser().getUsr_id()) && !StringUtil.isEmpty(form.getUser().getUserFullName()) )
         )
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-shipping-report-for-user", ShippingReportLine.class, null, null);
      }
      else
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-shipping-report", ShippingReportLine.class, null, null);
      }

      saveFormToBean(context);
      ShippingReport shippingReport = (ShippingReport) StoreUtil.getSession(context.getRequest(), ShippingReport.class);
      shippingReport.calculate();
      form.setShowLegend(shippingReport.isShowLegend());
      StoreUtil.putSession(context.getRequest(), shippingReport);
      form.getGrid().setDataList(shippingReport.getShippingReportLines());
    }

    context.getRequest().setAttribute("show-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        ShippingReportLine shippingReportLine = (ShippingReportLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !shippingReportLine.isItogLine();
      }
    }
    );

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        ShippingReportLine shippingReportLine = (ShippingReportLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        if (shippingReportLine.isItogLine())
        {
          return "bold-cell";
        }
        if (!StringUtil.isEmpty(shippingReportLine.getShp_closed()))
        {
          return "cell-green";
        }
        return "";
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward generate(IActionContext context) throws Exception
  {
    ShippingReportForm form = (ShippingReportForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    ShippingReport shippingReport = (ShippingReport) StoreUtil.getSession(context.getRequest(), ShippingReport.class);
    Grid2Excel grid2Excel = new Grid2Excel("Shipping Report");
    grid2Excel.doGrid2Excel(context, shippingReport.getExcelTable());
    return null;
  }

  public ActionForward cleanAll(IActionContext context) throws Exception
  {
    ShippingReportForm form = (ShippingReportForm) context.getForm();
    form.setCanForm(false);
    ShippingReport shippingReport = (ShippingReport) StoreUtil.getSession(context.getRequest(), ShippingReport.class);
    shippingReport.cleanList();

    return filter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ShippingReportForm form = (ShippingReportForm) context.getForm();
    form.setCanForm(false);

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
    form.setView_produce("1");
    form.setView_summ("1");
    form.setView_summ_without_nds("1");
    form.setView_summ_eur("1");
    form.setView_user("1");
    form.setView_department("1");

    form.setNot_include_zero("1");
    form.setInclude_all("");
    form.setInclude_closed("");
    form.setInclude_opened("");

    ShippingReport shippingReport = new ShippingReport();
    StoreUtil.putSession(context.getRequest(), shippingReport);
    saveFormToBean(context);

    return filter(context);
  }
}
