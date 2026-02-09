package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.UsersForm;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.Department;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.dao.UserDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UsersAction extends DBAction implements IDispatchable
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    UsersForm form = (UsersForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-users", UsersForm.User.class, null, null);

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !currentUser.isAdmin();
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward clearFilter(IActionContext context) throws Exception
  {
    UsersForm form = (UsersForm) context.getForm();
    form.setDepartment(new Department());
    return execute(context);
  }

  public ActionForward block(IActionContext context) throws Exception
  {
    UsersForm form = (UsersForm) context.getForm();
    User user = new User();
    user.setUsr_id(form.getUsr_id());
    if ("1".equals(form.getBlock()))
    {
      user.setUsr_block("");
    }
    else
    {
      user.setUsr_block("1");
    }
    UserDAO.saveBlock(context, user);

    return execute(context);
  }


}
