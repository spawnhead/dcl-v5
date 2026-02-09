package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.TimeboardWork;
import net.sam.dcl.beans.Timeboard;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.ReportDelimiterConsts;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.TimeboardWorkForm;
import net.sam.dcl.dao.ContractorRequestDAO;
import org.apache.struts.action.ActionForward;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class TimeboardWorkAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentFormToBean(IActionContext context, TimeboardWork timeboardWork)
  {
    TimeboardWorkForm form = (TimeboardWorkForm) context.getForm();

    try
    {
      timeboardWork.setTbw_id(form.getTbw_id());
      timeboardWork.setTmb_id(form.getTmb_id());
      timeboardWork.setNumber(form.getNumber());
      timeboardWork.setTbw_date_formatted(form.getTbw_date());
      timeboardWork.setTbw_from(form.getTbw_from());
      timeboardWork.setTbw_to(form.getTbw_to());
      timeboardWork.setTbw_hours_update(form.getTbw_hours_update());
      timeboardWork.setTbw_comment(form.getTbw_comment());

      if ( !StringUtil.isEmpty(form.getCrq_id()) )
      {
        timeboardWork.setContractorRequest(ContractorRequestDAO.load(context, form.getCrq_id()));  
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    StoreUtil.putSession(context.getRequest(), timeboardWork);
  }

  private void getCurrentFormFromBean(IActionContext context, TimeboardWork timeboardWork)
  {
    TimeboardWorkForm form = (TimeboardWorkForm) context.getForm();

    form.setTbw_id(timeboardWork.getTbw_id());
    form.setTmb_id(timeboardWork.getTmb_id());
    form.setNumber(timeboardWork.getNumber());
    form.setTbw_date(timeboardWork.getTbw_date_formatted());
    form.setTbw_from(timeboardWork.getTbw_from());
    form.setTbw_to(timeboardWork.getTbw_to());
    form.setTbw_hours_update(timeboardWork.getTbw_hours_update());
    form.setTbw_comment(timeboardWork.getTbw_comment());

    form.setCrq_id(timeboardWork.getContractorRequest().getCrq_id());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    TimeboardWorkForm form = (TimeboardWorkForm) context.getForm();
    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( user.isOnlyEconomist())
    {
      form.setFormReadOnly(true);
    }
    else
    {
      form.setFormReadOnly(false);
    }
    if ( user.isAdmin() || user.isEconomist() || ( user.isStaffOfService() && user.isChiefDepartment() ) )
    {
      form.setHoursUpdateReadOnly(false);
    }
    else
    {
      form.setHoursUpdateReadOnly(true);
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward insert(IActionContext context) throws Exception
  {
    TimeboardWork timeboardWork = new TimeboardWork();
    StoreUtil.putSession(context.getRequest(), timeboardWork);
    //обнуляем поля формы
    getCurrentFormFromBean(context, timeboardWork);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    TimeboardWorkForm form = (TimeboardWorkForm) context.getForm();
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);

    TimeboardWork timeboardWork = timeboard.findWork(form.getNumber());
    StoreUtil.putSession(context.getRequest(), timeboardWork);
    getCurrentFormFromBean(context, timeboardWork);

    return input(context);
  }

  public ActionForward clone(IActionContext context) throws Exception
  {
    TimeboardWorkForm form = (TimeboardWorkForm) context.getForm();
    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);

    TimeboardWork timeboardWork = new TimeboardWork(timeboard.findWork(form.getNumber()));
    getCurrentFormFromBean(context, timeboardWork);
    form.setNumber("");

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    TimeboardWorkForm form = (TimeboardWorkForm) context.getForm();

    String errMsg = "";

    Date dateWork = StringUtil.appDateString2Date(form.getTbw_date());
    Date dateTimeboard = StringUtil.appDateString2Date(form.getTmb_date());
    Calendar calendarWork = Calendar.getInstance();
    Calendar calendarTimeboard = Calendar.getInstance();
    Calendar calendarTimeboardMonth = Calendar.getInstance();
    calendarWork.setTime(dateWork);
    calendarTimeboard.setTime(dateTimeboard);
    calendarTimeboardMonth.setTime(dateTimeboard);
    calendarTimeboardMonth.add(Calendar.MONTH, 1);
    calendarTimeboardMonth.add(Calendar.DATE, -1);
    if ( calendarWork.before(calendarTimeboard) || calendarWork.after(calendarTimeboardMonth) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.timeboar_work.incorrect_date");
    }

    Date timeFrom = StringUtil.timeString2Date(form.getTbw_from());
    Date timeTo = StringUtil.timeString2Date(form.getTbw_to());
    if ( timeFrom.after(timeTo) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.timeboar_work.incorrect_time");
    }

    Timeboard timeboard = (Timeboard) StoreUtil.getSession(context.getRequest(), Timeboard.class);
    TimeboardWork timeboardWork = Timeboard.getEmptyWork();
    saveCurrentFormToBean(context, timeboardWork);
    for (int i = 0; i < timeboard.getWorks().size() - timeboard.getCountItogRecord(); i++)
    {
      TimeboardWork timeboardWorkCheck = timeboard.getWorks().get(i);
      if ( !timeboardWorkCheck.getNumber().equals(timeboardWork.getNumber()) )
      {
        //за одну дату
        if ( timeboardWorkCheck.getTbw_date().equals(timeboardWork.getTbw_date()) )
        {
          Date timeFromCheck = StringUtil.timeString2Date(timeboardWorkCheck.getTbw_from());
          Date timeToCheck = StringUtil.timeString2Date(timeboardWorkCheck.getTbw_to());
          /* В случае, если происходят временные накладки (это когда временные интервалы за одну
             дату пересекаются - человек не может одновременно быть в разных местах и выполнять
             разную работу), выдавать сообщение:
             "В указанные день и время уже осуществлялась другая работа:
             <Дата> <День недели> с <c> по <по>
             <Вид работ>
             <Контрагент>
             <Оборудование>"
          */
          if (
               ( timeFromCheck.before(timeFrom) && timeToCheck.after(timeFrom) ) ||
               ( timeFromCheck.before(timeTo) && timeToCheck.after(timeTo) ) ||
               ( timeFrom.before(timeFromCheck) && timeTo.after(timeFromCheck) ) ||
               ( timeFrom.before(timeToCheck) && timeTo.after(timeToCheck) ) ||
               ( timeFrom.equals(timeFromCheck) && timeTo.equals(timeToCheck) )  
             )
          {
            errMsg = StrutsUtil.addDelimiter(errMsg);
            errMsg += StrutsUtil.getMessage(context, "error.timeboar_work.crossed_time_date", timeboardWorkCheck.getTbw_date_formatted(), timeboardWorkCheck.getTbw_day_of_week(), timeboardWorkCheck.getTbw_from(), timeboardWorkCheck.getTbw_to());
            if ( !StringUtil.isEmpty(timeboardWorkCheck.getContractorRequest().getCrq_id()) )
            {
              errMsg += ReportDelimiterConsts.html_separator + timeboardWorkCheck.getContractorRequest().getRequestType().getNameById() +
                        ReportDelimiterConsts.html_separator + timeboardWorkCheck.getContractorRequest().getContractor().getName() +
                        ReportDelimiterConsts.html_separator + timeboardWorkCheck.getContractorRequest().getCrq_equipment();
            }
          }
        }
      }
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return input(context);
    }

    if (StringUtil.isEmpty(form.getNumber()))
    {
      timeboard.insertWork(timeboardWork);
    }
    else
    {
      timeboard.updateWork(form.getNumber(), timeboardWork);
    }
    return context.getMapping().findForward("back");
  }

}