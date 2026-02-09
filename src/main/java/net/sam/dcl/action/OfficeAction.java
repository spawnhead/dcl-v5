package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.dao.MessageDAO;
import net.sam.dcl.form.OfficeForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionForward;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OfficeAction extends DBAction implements IDispatchable, IFormAutoSave
{
  public ActionForward input(IActionContext context) throws Exception
  {
    OfficeForm form = (OfficeForm) context.getForm();

    User user = UserUtil.getCurrentUser(context.getRequest());
    form.setUser(user);
    form.setReadOnlyForManager(true);
    if (user.isChiefDepartment())
    {
      form.setShowForChiefDep(true);
      form.setDepartment(user.getDepartment());
    }
    else
    {
      form.setShowForChiefDep(false);
    }
    if (user.isAdmin() || user.isEconomist())
    {
      form.setReadOnlyForManager(false);
    }
    form.setRefreshTimeout(Integer.valueOf(user.getUserSetting(Constants.userRefreshPeriod).getUst_value()) * 60 * 1000);

    setVisuallyAttributes(context);

    return context.getMapping().getInputForward();
  }

  private void setVisuallyAttributes(IActionContext context)
  {
    final OfficeForm form = (OfficeForm) context.getForm();
    final User user = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("deleteChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !user.getUsr_id().equals(form.getUser().getUsr_id());
      }
    });
  }

  private ActionForward refreshMessagesGrid(IActionContext context, String userId) throws Exception
  {
    OfficeForm form = (OfficeForm) context.getForm();
    if (!StringUtil.isEmpty(userId))
    {
      form.getUser().setUsr_id(userId);

      List<PaymentMessage> paymentMessages = DAOUtils.fillList(context, "select-payment_messages", form, PaymentMessage.class, null, null);
      for (PaymentMessage paymentMessage : paymentMessages)
      {
        Message message = new Message();
        message.setType(Message.paymentType);
        message.setId(paymentMessage.getPms_id());
        message.setDate(paymentMessage.getPmsCreateDateFormatted());
        message.setMessage(paymentMessage.getPmsMessageFormatted());
        message.setSum(paymentMessage.getPmsSumFormatted());
        message.setShortInfo(paymentMessage.getCurrency().getName());
        message.setContractor(paymentMessage.getContractor());
        message.setComment(paymentMessage.getPmsPercentFormatted());
        form.getGridMessages().getDataList().add(message);
      }

      List<Message> messages = DAOUtils.fillList(context, "select-information_messages", form, Message.class, null, null);
      for (Message message : messages)
      {
        Message newMessage = new Message(message);
        newMessage.setType(Message.informationType);
        newMessage.setDate(message.getDateFormatted());
        form.getGridMessages().getDataList().add(newMessage);
      }

      messages = DAOUtils.fillList(context, "select-contract_messages", form, Message.class, null, null);
      for (Message message : messages)
      {
        Message newMessage = new Message(message);
        newMessage.setType(Message.contractType);
        newMessage.setDate(message.getDateFormatted());
        form.getGridMessages().getDataList().add(newMessage);
      }

      messages = DAOUtils.fillList(context, "select-condition_for_contract_messages", form, Message.class, null, null);
      for (Message message : messages)
      {
        Message newMessage = new Message(message);
        newMessage.setType(Message.conditionForContractType);
        newMessage.setDate(message.getDateFormatted());
        form.getGridMessages().getDataList().add(newMessage);
      }

      messages = DAOUtils.fillList(context, "select-order_messages", form, Message.class, null, null);
      for (Message message : messages)
      {
        Message newMessage = new Message(message);
        newMessage.setType(Message.orderType);
        newMessage.setDate(message.getDateFormatted());
        newMessage.setContractor(message.getContractor());
        form.getGridMessages().getDataList().add(newMessage);
      }
    }

    Collections.sort(form.getGridMessages().getDataList(), new CustomerComparator());

    setVisuallyAttributes(context);

    return context.getMapping().findForward("ajaxMessagesGrid");
  }

  class CustomerComparator implements Comparator<Message>
  {
    public int compare(Message message1, Message message2)
    {
      if (StringUtil.isEmpty(message1.getDate())) return -1;
      if (StringUtil.isEmpty(message2.getDate())) return 1;
      try
      {
        return -StringUtil.appDateTimeString2Date(message1.getDate()).compareTo(StringUtil.appDateTimeString2Date(message2.getDate()));
      }
      catch (Exception ex)
      {
        log.error(ex);
      }

      return -1;
    }
  }


  public ActionForward ajaxMessagesGrid(IActionContext context) throws Exception
  {
    return refreshMessagesGrid(context, context.getRequest().getParameter("userId"));
  }

  public ActionForward ajaxRemoveMessages(IActionContext context) throws Exception
  {
    String uniqueId = context.getRequest().getParameter("uniqueId");
    String[] strings = uniqueId.split("_");
    String id = strings[0];
    String type = strings[1];
    if (!StringUtil.isEmpty(uniqueId))
    {
	    if (Message.informationType.equals(type))
     {
       Message message = new Message(id);
       MessageDAO.deleteInformationMessage(context, message);
     }
      if (Message.paymentType.equals(type))
      {
        PaymentMessage paymentMessage = new PaymentMessage(id);
        MessageDAO.deletePaymentMessage(context, paymentMessage);
      }
      if (Message.contractType.equals(type))
      {
        Message message = new Message(id);
        MessageDAO.deleteContractMessage(context, message);
      }
      if (Message.conditionForContractType.equals(type))
      {
        Message message = new Message(id);
        MessageDAO.deleteConditionForContractMessage(context, message);
      }
      if (Message.orderType.equals(type))
      {
        Message message = new Message(id);
        MessageDAO.deleteOrderMessage(context, message);
      }
    }

    return refreshMessagesGrid(context, context.getRequest().getParameter("userId"));
  }

  public ActionForward ajaxEconomistMessages(IActionContext context) throws Exception
  {
    User user = UserUtil.getCurrentUser(context.getRequest());
    String userId = context.getRequest().getParameter("userId");
    if (!StringUtil.isEmpty(userId) && user.isEconomist())
    {
      String resStr = "";
      List<UserLink> userLinkList = DAOUtils.fillList(context, "select-user_links", user, UserLink.class, null, null);

      if (userLinkList.size() > 0)
      {
        StringBuilder builder = new StringBuilder();
        //builder.append(" <img src='/dcl/images/lintera_important.jpg;jsessionid=" + context.getRequest().getSession().getId() + "' onclick='showControlComment(document.getElementById(\"warnInHeaderId\"), true);' style=\"cursor:pointer;\"> ");
        
        builder.append(" <div id='warnInHeaderId' ");
        builder.append(" __comment='");
        int i = 0;
        for(UserLink userLink : userLinkList)
        {
          builder.append("<a href=\""+ userLink.getUrl() +";jsessionid=" + context.getRequest().getSession().getId() + userLink.getParameters() + "\" onclick=\"setCorrectMenuId(" + i + ")\">" + userLink.getText() + "</a>");
          i++;
        }
        builder.append("'> ");

        i = 0;
        for(UserLink userLink : userLinkList)
        {
          builder.append(" <input id=\"menuIdx" + i + "\" type=\"hidden\" value=\"" + userLink.getMenuId() + "\"> ");
          i++;
        }
        builder.append("</div>");

        resStr = builder.toString();
      }

      StrutsUtil.setAjaxResponse(context, resStr, false);
    }
    else
    {
      StrutsUtil.setAjaxResponse(context, "", false);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxSetCorrectMenuId(IActionContext context) throws Exception
  {
    String menuId = context.getRequest().getParameter("menuId");
    if (!StringUtil.isEmpty(menuId))
    {
      context.getRequest().getSession().setAttribute(Outline.MENU_ID_NAME, "id.condition_for_contract");
    }

    return context.getMapping().findForward("ajax");
  }
}