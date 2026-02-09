package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.*;
import net.sam.dcl.form.TimeboardForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.dao.*;
import org.apache.struts.action.ActionForward;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class TimeboardAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(TimeboardAction.class);

  private void saveCurrentFormToBean(IActionContext context)
  {
    TimeboardForm form = (TimeboardForm) context.getForm();

    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);

    timeboard.setIs_new_doc(form.getIs_new_doc());
    timeboard.setTmb_id(form.getTmb_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        timeboard.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        timeboard.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
      timeboard.setUsr_date_create(form.getUsr_date_create());
      timeboard.setUsr_date_edit(form.getUsr_date_edit());

      if (!StringUtil.isEmpty(form.getUser().getUsr_id()))
      {
        timeboard.setUser(UserDAO.load(context, form.getUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getCheckedUser().getUsr_id()))
      {
        timeboard.setCheckedUser(UserDAO.load(context, form.getCheckedUser().getUsr_id()));
      }
      timeboard.setTmb_date(form.getTmb_date());
      timeboard.setTmb_checked(form.getTmb_checked());
      timeboard.setTmb_checked_date(form.getTmb_checked_date());
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    StoreUtil.putSession(context.getRequest(), timeboard);
  }

  private void getCurrentFormFromBean(IActionContext context, Timeboard timeboardIn)
  {
    TimeboardForm form = (TimeboardForm) context.getForm();
    Timeboard timeboard;
    if (null != timeboardIn)
    {
      timeboard = timeboardIn;
    }
    else
    {
      timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    }

    if (null != timeboard)
    {
      form.setIs_new_doc(timeboard.getIs_new_doc());

      form.setTmb_id(timeboard.getTmb_id());
      form.setCreateUser(timeboard.getCreateUser());
      form.setEditUser(timeboard.getEditUser());
      form.setUsr_date_create(timeboard.getUsr_date_create());
      form.setUsr_date_edit(timeboard.getUsr_date_edit());
      form.setTmb_date(timeboard.getTmb_date());
      form.setUser(timeboard.getUser());
      form.setTmb_checked(timeboard.getTmb_checked());
      form.setTmb_checked_date(timeboard.getTmb_checked_date());
      form.setCheckedUser(timeboard.getCheckedUser());

      form.getGridWorks().setDataList(timeboard.getWorks());
    }
  }

  public ActionForward newWork(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("newWork");
  }

  public ActionForward cloneWork(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("cloneWork");
  }

  public ActionForward editWork(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("editWork");
  }

  public ActionForward deleteWork(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    TimeboardForm form = (TimeboardForm) context.getForm();
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    timeboard.deleteWork(form.getNumber());

    return retFromWorkOperation(context);
  }

  public ActionForward retFromWorkOperation(IActionContext context) throws Exception
  {
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    getCurrentFormFromBean(context, timeboard);

    return show(context);
  }

  public ActionForward selectContractorRequest(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    if ( !timeboard.haveSelected() )
    {
      StrutsUtil.addError(context, "error.timeboars.no_selected_items", null);
      return show(context);
    }

    return context.getMapping().findForward("selectContractorRequest");
  }

  public ActionForward retSelectContractorRequest(IActionContext context) throws Exception
  {
    String crqId = SelectFromGridAction.getSelectedId(context);
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    if (!StringUtil.isEmpty(crqId))
    {
      ContractorRequest contractorRequest = ContractorRequestDAO.load(context, crqId);
      timeboard.importFromContractorRequest(contractorRequest);
    }

    getCurrentFormFromBean(context, timeboard);
    return show(context);
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    TimeboardForm form = (TimeboardForm) context.getForm();
    String errMsg = "";

    saveCurrentFormToBean(context);

    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    Timeboard timeboardCheckDubl = new Timeboard();
    timeboardCheckDubl.setUser(new User(timeboard.getUser()));
    timeboardCheckDubl.setTmb_date(timeboard.getTmb_date());
    TimeboardDAO.loadByUsrIdTmbDate(context, timeboardCheckDubl);
    //нашли, и это не текущий док
    if ( !StringUtil.isEmpty(timeboardCheckDubl.getTmb_id()) && !timeboardCheckDubl.getTmb_id().equals(timeboard.getTmb_id()) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.timeboar.double_user_date");
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return false;
    }


    if (StringUtil.isEmpty(form.getTmb_id()))
    {
      TimeboardDAO.insert(context, timeboard);
    }
    else
    {
      TimeboardDAO.save(context, timeboard);
    }
    return true;
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final TimeboardForm form = (TimeboardForm) context.getForm();
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);

    saveCurrentFormToBean(context);
    timeboard.calculate();
    final int countItogRecord = timeboard.getCountItogRecord();
    form.getGridWorks().setDataList(timeboard.getWorks());
    form.setCountItogRecord(countItogRecord);

    context.getRequest().setAttribute("show-checker", new IShowChecker()
    {
      int size = form.getGridWorks().getDataList().size();

      public boolean check(ShowCheckerContext context)
      {
        if (context.getTable().getRecordCounter() >= size - countItogRecord + 1)
        {
          return false;
        }
        return true;
      }
    }
    );

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      int size = form.getGridWorks().getDataList().size();

      public String check(StyleClassCheckerContext context)
      {
        if (context.getTable().getRecordCounter() >= size - countItogRecord + 1)
        {
          return "bold-cell";
        }
        return "";
      }
    });

    context.getRequest().setAttribute("styleCheckerImage", new IStyleClassChecker()
    {
      int size = form.getGridWorks().getDataList().size();

      public String check(StyleClassCheckerContext context)
      {
        if (context.getTable().getRecordCounter() >= size - countItogRecord + 1)
        {
          return "bold-cell";
        }
        return "grid-image-without-border";
      }
    });

    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( user.isOnlyEconomist() || "1".equals(form.getTmb_checked_old()) )
    {
      form.setFormReadOnly(true);
    }
    else
    {
      form.setFormReadOnly(false);
    }

    //порядок важен
    form.setTmbCheckedReadOnly(form.isFormReadOnly());
    if ( user.isAdmin() )
    {
      form.setTmbCheckedReadOnly(false);
    }
    if ( user.isChiefDepartment() && StringUtil.isEmpty(form.getTmb_checked_old()) )
    {
      form.setTmbCheckedReadOnly(false);
    }
    if ( user.isOnlyStaffOfService() && !user.isChiefDepartment())
    {
      form.setTmbCheckedReadOnly(true);
    }

    final boolean editReadOnly = "1".equals(form.getTmb_checked_old());
    form.setEconomReadOnly(editReadOnly);
    context.getRequest().setAttribute("editReadOnly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return editReadOnly;
      }
    });

    return context.getMapping().findForward("form");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    TimeboardForm form = (TimeboardForm) context.getForm();

    Timeboard timeboard = new Timeboard();
    StoreUtil.putSession(context.getRequest(), timeboard);
    //обнуляем поля формы
    getCurrentFormFromBean(context, timeboard);

    form.setIs_new_doc("true");
    form.setUser(UserUtil.getCurrentUser(context.getRequest()));

    saveCurrentFormToBean(context);

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    TimeboardForm form = (TimeboardForm) context.getForm();

    Timeboard timeboard = TimeboardDAO.load(context, form.getTmb_id());
    StoreUtil.putSession(context.getRequest(), timeboard);
    getCurrentFormFromBean(context, timeboard);
    form.setTmb_checked_old(timeboard.getTmb_checked());

    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    boolean retFromSave = saveCommon(context);

		if (retFromSave)
    {
      return back(context);
    }
    else
    {
      return show(context);
    }
  }
}