package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.form.OrderExecutedProducesForm;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrderExecutedProducesAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    OrderExecutedProducesForm form = (OrderExecutedProducesForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);

    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    OrderExecutedProduces orderExecutedProduces = new OrderExecutedProduces();
    if (order.isBlock() || currentUser.isManager())
      orderExecutedProduces.setDisableEdit("true");
    else
      orderExecutedProduces.setDisableEdit("");

    form.setDisableEdit(orderExecutedProduces.getDisableEdit());

    if (order.getOrderExecutedDate().size() == 0)
    {
      if (!StringUtil.isEmpty(order.getOrd_id())) //берем из базы
      {
        order.calculateOrderExecutedAndDateLines(context, order.getOrderExecutedByDates());
      }
      else
      {
        order.calculateOrderExecutedAndDateLines(); //формируем на основе списка продуктов
      }
    }

    orderExecutedProduces.getOrderExecutedDate().clear();
    for (OrderExecutedDateLine date : order.getOrderExecutedDate())
    {
      orderExecutedProduces.getOrderExecutedDate().add(new OrderExecutedDateLine(date));
    }
    orderExecutedProduces.getOrderExecuted().clear();
    for (OrderExecutedLine line : order.getOrderExecuted())
    {
      orderExecutedProduces.getOrderExecuted().add(new OrderExecutedLine(line));
    }
    orderExecutedProduces.checkAndFillLastColumn();
    orderExecutedProduces.setDeletedColumnFlag();

    StoreUtil.putSession(context.getRequest(), orderExecutedProduces);

    form.setOrd_id(order.getOrd_id());
    form.setOrd_number(order.getOrd_number());
    form.setOrd_date(order.getOrd_date());
    form.setColspan(orderExecutedProduces.getColspan());

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    OrderExecutedProducesForm form = (OrderExecutedProducesForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    OrderExecutedProduces orderExecutedProduces = StoreUtil.getOrderExecutedProduces(context.getRequest());

    order.getOrderExecutedDate().clear();
    for (OrderExecutedDateLine date : orderExecutedProduces.getOrderExecutedDate())
    {
       order.getOrderExecutedDate().add(new OrderExecutedDateLine(date));
    }
    order.getOrderExecuted().clear();
    for (OrderExecutedLine line : orderExecutedProduces.getOrderExecuted())
    {
       order.getOrderExecuted().add(new OrderExecutedLine(line));
    }

    return context.getMapping().findForward("back");
  }

  public ActionForward ajaxNewExecutedDateChanged(IActionContext context) throws Exception
  {
    OrderExecutedProducesForm form = (OrderExecutedProducesForm) context.getForm();
    OrderExecutedProduces orderExecutedProduces = StoreUtil.getOrderExecutedProduces(context.getRequest());
    String newExecutedDate = context.getRequest().getParameter("newExecutedDate");
    if (!StringUtil.isEmpty(newExecutedDate))
    {
      String resultMsg = orderExecutedProduces.addNewExecutedDate(newExecutedDate);

      //Локализуем по коду.
      if (!StringUtil.isEmpty(resultMsg))
      {
        resultMsg = StrutsUtil.getMessage(context, resultMsg);
      }

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
      orderExecutedProduces.checkAndFillLastColumn();
      form.setColspan(orderExecutedProduces.getColspan());
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxChangeValue(IActionContext context) throws Exception
  {
    OrderExecutedProducesForm form = (OrderExecutedProducesForm) context.getForm();
    OrderExecutedProduces orderExecutedProduces = StoreUtil.getOrderExecutedProduces(context.getRequest());
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    String forDate = context.getRequest().getParameter("forDate");
    String number = context.getRequest().getParameter("number");
    String newValue = context.getRequest().getParameter("newValue");
    if (!StringUtil.isEmpty(forDate) && !StringUtil.isEmpty(number) && !StringUtil.isEmpty(newValue))
    {
      String resultMsg = orderExecutedProduces.setNewValueForDate(forDate, number, newValue, order);
      //Локализуем по коду.
      if (!StringUtil.isEmpty(resultMsg))
      {
        resultMsg = StrutsUtil.getMessage(context, resultMsg);
      }

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxDeleteColumn(IActionContext context) throws Exception
  {
    OrderExecutedProducesForm form = (OrderExecutedProducesForm) context.getForm();
    OrderExecutedProduces orderExecutedProduces = StoreUtil.getOrderExecutedProduces(context.getRequest());
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    String columnIdx = context.getRequest().getParameter("columnIdx");
    if (!StringUtil.isEmpty(columnIdx))
    {
      orderExecutedProduces.deleteColumn(Integer.parseInt(columnIdx));
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxOrderExecutedProducesGrid(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("ajaxOrderExecutedProducesGrid");
  }
}