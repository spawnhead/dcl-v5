package net.sam.dcl.action;

import net.sam.dcl.beans.DeliveryRequest;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.dao.DeliveryRequestDAO;
import net.sam.dcl.form.DeliveryRequestsForm;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryRequestsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        DeliveryRequestsForm form = (DeliveryRequestsForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        DeliveryRequestsForm form = (DeliveryRequestsForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    final DeliveryRequestsForm form = (DeliveryRequestsForm) context.getForm();
    if ( DeliveryRequestsForm.inDirection.equals(form.getDirection()) )
    {
      DAOUtils.fillGrid(context, form.getGrid(), "select-delivery_requests_in", DeliveryRequestsForm.DeliveryRequest.class, null, null);
    }
    else
    {
      DAOUtils.fillGrid(context, form.getGrid(), "select-delivery_requests_out", DeliveryRequestsForm.DeliveryRequest.class, null, null);
    }
    
    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx)
      {
        return !currentUser.isAdmin();
      }
    });

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        DeliveryRequestsForm.DeliveryRequest deliveryRequest = (DeliveryRequestsForm.DeliveryRequest) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        if ("1".equals(deliveryRequest.getDlr_annul()))
        {
          return "crossed-cell";
        }
        return "";
      }
    });

    // менеджер - тока свой отдел
    context.getRequest().setAttribute("editChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        DeliveryRequestsForm.DeliveryRequest record = (DeliveryRequestsForm.DeliveryRequest) ctx.getBean();
        return currentUser.isOnlyManager() &&
                !currentUser.getDepartment().getId().equals(record.getDep_id());
      }
    });

    context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        DeliveryRequestsForm.DeliveryRequest record = (DeliveryRequestsForm.DeliveryRequest) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !record.isPlaced() && record.getProduce_count() <= 0;
      }
    }
    );

    return context.getMapping().getInputForward();
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    DeliveryRequestsForm form = (DeliveryRequestsForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward block(IActionContext context) throws Exception
  {
    DeliveryRequestsForm form = (DeliveryRequestsForm) context.getForm();
    DeliveryRequest deliveryRequest = new DeliveryRequest();
    deliveryRequest.setDlr_id(form.getDlr_id());
    if ("1".equals(form.getDlr_place_request()))
    {
      deliveryRequest.setDlr_place_request("");
    }
    else
    {
      deliveryRequest.setDlr_place_request("1");
    }
    DeliveryRequestDAO.savePlaceRequest(context, deliveryRequest);

    return internalFilter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    DeliveryRequestsForm form = (DeliveryRequestsForm) context.getForm();
    form.setDlr_id("");
    form.setNumber("");
    form.setUser(new User());
    form.setDlr_fair_trade("");
    form.setDlr_place_request("");
    form.setUnexecuted("1");
    form.setAnnul_exclude("1");
    form.setSpecification_numbers("");

    if ( Config.haveNumber(Constants.dayCountDeductDeliveryRequests) )
    {
      int dayCount = Config.getNumber(Constants.dayCountDeductDeliveryRequests, 10);
      if (dayCount > 0)
      {
        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.add(Calendar.DATE, -dayCount);
        form.setDate_begin(StringUtil.date2appDateString(calendarBegin.getTime()));
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.add(Calendar.DATE, 1);
        form.setDate_end(StringUtil.date2appDateString(calendarEnd.getTime()));
      }
      else
      {
        form.setDate_begin("");
        form.setDate_end("");
      }
    }
    else
    {
      form.setDate_begin("");
      form.setDate_end("");
    }

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

}
