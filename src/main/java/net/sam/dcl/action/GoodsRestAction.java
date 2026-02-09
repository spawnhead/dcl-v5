package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.GoodsRestForm;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.AttachmentsService;
import org.apache.struts.action.ActionForward;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class GoodsRestAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    GoodsRestForm form = (GoodsRestForm) context.getForm();

    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);

    goodsRest.setDate_begin(form.getDate_begin());
    goodsRest.setHave_date_to(form.getHave_date_to());
    goodsRest.setDate_end(form.getDate_end());
    goodsRest.setUser(form.getUser());
    goodsRest.setDepartment(form.getDepartment());
    goodsRest.setStuffCategory(form.getStuffCategory());
    goodsRest.setPurpose(form.getPurpose());
    goodsRest.setOnlyTotal(form.getOnlyTotal());
    goodsRest.setBy_user(form.getBy_user());
    goodsRest.setView_department(form.getView_department());
    goodsRest.setView_order_for(form.getView_order_for());
    goodsRest.setView_1c_number(form.getView_1c_number());
    goodsRest.setView_cost_one_by(form.getView_cost_one_by());
    goodsRest.setView_price_list_by(form.getView_price_list_by());
    goodsRest.setView_prc_date(form.getView_prc_date());
    goodsRest.setView_prc_number(form.getView_prc_number());
    goodsRest.setView_lpc_count(form.getView_lpc_count());
    goodsRest.setView_usr_shipping(form.getView_usr_shipping());
    goodsRest.setView_debt(form.getView_debt());
    goodsRest.setView_purpose(form.getView_purpose());
    goodsRest.setView_comment(form.getView_comment());
    goodsRest.setView_sums(form.getView_sums());
    goodsRest.setOrder_by_name(form.getOrder_by_name());
    goodsRest.setOrder_by_stuff_category(form.getOrder_by_stuff_category());
    goodsRest.setOrder_by_date_receipt(form.getOrder_by_date_receipt());
    goodsRest.setGoods_on_storage(form.getGoods_on_storage());
    goodsRest.setShipping_goods(form.getShipping_goods());

    goodsRest.setGoodsRestLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), goodsRest);
  }

  private void getCurrentFormFromBean(IActionContext context)
  {
    GoodsRestForm form = (GoodsRestForm) context.getForm();

    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);

    form.setDate_begin(goodsRest.getDate_begin());
    form.setHave_date_to(goodsRest.getHave_date_to());
    form.setDate_end(goodsRest.getDate_end());
    form.setUser(goodsRest.getUser());
    form.setDepartment(goodsRest.getDepartment());
    form.setStuffCategory(goodsRest.getStuffCategory());
    form.setPurpose(goodsRest.getPurpose());
    form.setOnlyTotal(goodsRest.getOnlyTotal());
    form.setView_department(goodsRest.getView_department());
    form.setView_order_for(goodsRest.getView_order_for());
    form.setView_1c_number(goodsRest.getView_1c_number());
    form.setView_cost_one_by(goodsRest.getView_cost_one_by());
    form.setView_price_list_by(goodsRest.getView_price_list_by());
    form.setView_prc_date(goodsRest.getView_prc_date());
    form.setView_prc_number(goodsRest.getView_prc_number());
    form.setView_lpc_count(goodsRest.getView_lpc_count());
    form.setView_usr_shipping(goodsRest.getView_usr_shipping());
    form.setView_debt(goodsRest.getView_debt());
    form.setView_purpose(goodsRest.getView_purpose());
    form.setView_comment(goodsRest.getView_comment());
    form.setView_sums(goodsRest.getView_sums());
    form.setOrder_by_name(goodsRest.getOrder_by_name());
    form.setOrder_by_stuff_category(goodsRest.getOrder_by_stuff_category());
    form.setOrder_by_date_receipt(goodsRest.getOrder_by_date_receipt());
    form.setGoods_on_storage(goodsRest.getGoods_on_storage());
    form.setShipping_goods(goodsRest.getShipping_goods());

    form.getGrid().setDataList(goodsRest.getGoodsRestLines());
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    GoodsRestForm form = (GoodsRestForm) context.getForm();

    if (StringUtil.isEmpty(form.getDate_begin()))
    {
      return context.getMapping().getInputForward();
    }

    if (!StringUtil.isEmpty(form.getDate_end()))
    {
      Date dateBegin = StringUtil.getCurrentDateTime();
      Date dateEnd = StringUtil.getCurrentDateTime();
      try
      {
        dateBegin = StringUtil.appDateString2Date(form.getDate_begin());
        dateEnd = StringUtil.appDateString2Date(form.getDate_end());
      }
      catch (Exception e)
      {
        log.error(e.toString());
      }

      if (dateEnd.before(dateBegin))
      {
        StrutsUtil.addError(context, "error.goods_rest.incorrect_dates", null);
        return show(context);
      }
    }

    if (form.isCanForm())
    {
      form.setSelect_list(form.getCommon1());
      form.setGroup_by("");

      if (!StringUtil.isEmpty(form.getShipping_goods()))
      {
        form.setOrder_by("order by shp_date");
      }
      else
      {
        form.setOrder_by("order by prc_date");
      }
      if (!StringUtil.isEmpty(form.getOrder_by_name()))
      {
        if (!StringUtil.isEmpty(form.getShipping_goods()))
        {
          form.setOrder_by("order by produce_name_sort, shp_date");
        }
        else
        {
          form.setOrder_by("order by produce_name_sort, prc_date");
        }
      }
      if (!StringUtil.isEmpty(form.getOrder_by_stuff_category()))
      {
        form.setOrder_by("order by stf_name_sort, produce_name_sort");
      }

      if (StringUtil.isEmpty(form.getUser().getUsr_id()))
      {
        form.setUser(new User());
      }
      if (StringUtil.isEmpty(form.getDepartment().getId()))
      {
        form.setDepartment(new Department());
      }
      if (StringUtil.isEmpty(form.getStuffCategory().getId()))
      {
        form.setStuffCategory(new StuffCategory());
      }

      if (!"1".equalsIgnoreCase(form.getOnlyTotal())) //итог не отмечен
      {
      }
      else
      {
        form.setOrder_by("");

        String selectList = form.getCommon2();
        if (!StringUtil.isEmpty(form.getUser().getUserFullName()))
        {
          form.setGroup_by("group by usr_name");
          selectList += "usr_name";
        }
        if (!StringUtil.isEmpty(form.getDepartment().getName()))
        {
          form.setGroup_by("group by dep_name");
          selectList += "dep_name";
          if (!StringUtil.isEmpty(form.getBy_user()))
          {
            form.setGroup_by(form.getGroup_by() + ", usr_name");
            selectList += ", usr_name";
          }
        }
        if (!StringUtil.isEmpty(form.getStuffCategory().getName()))
        {
          form.setGroup_by("group by stf_name, stf_name_sort");
          selectList += "stf_name, stf_name_sort";
          if (!StringUtil.isEmpty(form.getOrder_by_stuff_category()))
          {
            form.setOrder_by("order by stf_name_sort");
          }
        }
        form.setSelect_list(selectList);
      }

      if (StringUtil.isEmpty(form.getShipping_goods()))
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-goods_rest", GoodsRestLine.class, null, null);
      }
      else
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-shipping_goods", GoodsRestLine.class, null, null);
      }
      saveCurrentFormToBean(context);
      GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);

      User user = UserUtil.getCurrentUser(context.getRequest());
      boolean forManager = false;
      User checkUser = null;
      boolean forChiefDepartment = false;
      Department checkDepartment = null;
      if (user.isManager() && !user.isAdmin() && !user.isEconomist())
      {
        forManager = true;
        checkUser = user;
        forChiefDepartment = true; //менеджер и нач.отдела - по своему отделу
        checkDepartment = user.getDepartment();
      }

      goodsRest.calculate(forManager, checkUser, forChiefDepartment, checkDepartment);
      StoreUtil.putSession(context.getRequest(), goodsRest);
      form.getGrid().setDataList(goodsRest.getGoodsRestLines());
    }

    return show(context);
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final GoodsRestForm form = (GoodsRestForm) context.getForm();

    context.getRequest().setAttribute("show-count-sum", new IShowChecker()
    {
      int size = form.getGrid().getDataList().size();

      public boolean check(ShowCheckerContext context)
      {
        return !(context.getTable().getRecordCounter() >= size - 1);
      }
    }
    );

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        GoodsRestLine goods_rest_line = (GoodsRestLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        if (goods_rest_line.isItogLine())
        {
          return "bold-cell";
        }
        return "";
      }
    });

    final User user = UserUtil.getCurrentUser(context.getRequest());
    //добавление И/ИЛИ редактирование комментариев - админ, экономист
    context.getRequest().setAttribute("commentEditReadOnly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return (!user.isAdmin() && !user.isEconomist());
      }
    });
    //редактировать Отдел - админ, экономист
    if ( (user.isAdmin() || user.isEconomist()) && StringUtil.isEmpty(form.getOnlyTotal()) )
    {
      form.setCanEditDepartment(true);
    }
    else
    {
      form.setCanEditDepartment(false);
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward generate(IActionContext context) throws Exception
  {
    GoodsRestForm form = (GoodsRestForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);
    Grid2Excel grid2Excel = new Grid2Excel("GoodsRest Report");
    grid2Excel.doGrid2Excel(context, goodsRest.getExcelTable());
    return null;
  }

  public ActionForward cleanAll(IActionContext context) throws Exception
  {
    GoodsRestForm form = (GoodsRestForm) context.getForm();
    form.setCanForm(false);
    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);
    goodsRest.cleanList();

    return filter(context);
  }

  public ActionForward changeType(IActionContext context) throws Exception
  {
    GoodsRestForm form = (GoodsRestForm) context.getForm();
    form.setCanForm(false);
    form.setGoods_on_storage("1");
    form.setOrder_by_date_receipt("1");
    form.getPurpose().setId(StrutsUtil.getMessage(context, "Purpose.all_id"));
    form.getPurpose().setName(StrutsUtil.getMessage(context, "Purpose.all"));
    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);
    goodsRest.cleanList();

    return filter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    GoodsRest goodsRest = new GoodsRest();
    StoreUtil.putSession(context.getRequest(), goodsRest);
    //обнуляем поля формы
    getCurrentFormFromBean(context);

    GoodsRestForm form = (GoodsRestForm) context.getForm();
    form.setCanForm(false);
    form.setView_prc_date("1");
    form.setView_sums("1");
    form.setGoods_on_storage("1");
    form.setOrder_by_date_receipt("1");
    form.getPurpose().setId(StrutsUtil.getMessage(context, "Purpose.all_id"));
    form.getPurpose().setName(StrutsUtil.getMessage(context, "Purpose.all"));
    form.setDate_begin(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));

    StoreUtil.putSession(context.getRequest(), goodsRest);
    saveCurrentFormToBean(context);

    return filter(context);
  }

  public ActionForward fromProduceMovement(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context);

    return show(context);
  }

  public ActionForward saveComment(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "update-lpc_comment", null);
    GoodsRestForm form = (GoodsRestForm) context.getForm();
    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);
    for ( GoodsRestLine goodsRestLine : goodsRest.getGoodsRestLines())
    {
      if (StringUtil.equal(goodsRestLine.getLpc_id(), form.getLpc_id()))
      {
        goodsRestLine.setLpc_comment(form.getLpc_comment());
        break;
      }
    }
    return context.getMapping().findForward("ajax");
  }

  public ActionForward saveDepartment(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "update-lpc_department", null);
    GoodsRestForm form = (GoodsRestForm) context.getForm();
    GoodsRest goodsRest = (GoodsRest) StoreUtil.getSession(context.getRequest(), GoodsRest.class);
    for (GoodsRestLine goodsRestLine : goodsRest.getGoodsRestLines())
    {
      if (StringUtil.equal(goodsRestLine.getLpc_id(), form.getLpc_id()))
      {
        goodsRestLine.setDep_name(form.getDepartmentName());
        break;
      }
    }
    return context.getMapping().findForward("ajax");
  }

  public ActionForward downloadImage(IActionContext context) throws Exception
  {
    AttachmentsService attachmentsService = new AttachmentsService(HibernateUtil.getSessionFactory().getCurrentSession());
    GoodsRestForm form = (GoodsRestForm) context.getForm();
    try
    {
      attachmentsService.download(form.getImageId(), context.getRequest(), context.getResponse());
    }
    catch (AttachmentException e)
    {
      log.error(e);
    }
    return null;
  }
}
