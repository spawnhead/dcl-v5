package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.form.ActionForm;
import net.sam.dcl.beans.Action;
import net.sam.dcl.dao.ActionDAO;
import net.sam.dcl.navigation.ControlActions;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ActionAction extends DBAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    ActionForm form = (ActionForm) context.getForm();

    Action action = (Action) StoreUtil.getSession(context.getRequest(), Action.class);

    action.setAct_id(form.getAct_id());
    action.setAct_system_name(form.getAct_system_name());
    action.setAct_name(form.getAct_name());
    action.setAct_logging(form.getAct_logging());
    action.setAct_check_access(form.getAct_check_access());

    StoreUtil.putSession(context.getRequest(), action);
  }

  private void getCurrentFormFromBean(IActionContext context, Action actionIn)
  {
    ActionForm form = (ActionForm) context.getForm();
    Action action;
    if (null != actionIn)
    {
      action = actionIn;
    }
    else
    {
      action = (Action) StoreUtil.getSession(context.getRequest(), Action.class);
    }

    if (null != action)
    {
      form.setAct_id(action.getAct_id());
      form.setAct_system_name(action.getAct_system_name());
      form.setAct_name(action.getAct_name());
      form.setAct_logging(action.getAct_logging());
      form.setAct_check_access(action.getAct_check_access());
    }
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ActionForm form = (ActionForm) context.getForm();
    Action action = ActionDAO.load(context, form.getAct_id());

    StoreUtil.putSession(context.getRequest(), action);
    getCurrentFormFromBean(context, action);

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    Action action = (Action) StoreUtil.getSession(context.getRequest(), Action.class);
    ActionDAO.save(context, action);
    ControlActions actions = (ControlActions) StoreUtil.getApplication(context.getServletContext(), ControlActions.class);
    actions.putAction(action);
    return context.getMapping().findForward("back");
  }

}