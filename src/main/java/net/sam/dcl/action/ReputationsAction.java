package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.form.ReputationsForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.beans.Reputation;
import net.sam.dcl.dao.ReputationDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ReputationsAction extends DBAction implements IDispatchable
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    ReputationsForm form = (ReputationsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-reputations", ReputationsForm.Reputation.class, null, null);

    return context.getMapping().getInputForward();
  }

  public ActionForward set_default(IActionContext context) throws Exception
  {
    ReputationsForm form = (ReputationsForm) context.getForm();
    Reputation reputation = new Reputation(form.getRpt_id());
    if ("".equals(form.getSetDefault()))
    {
      ReputationDAO.setDefaultForCtc(context, reputation);
    }

    return execute(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("edit");
  }
}
