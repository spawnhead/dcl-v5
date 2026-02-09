package net.sam.dcl.action;

import net.sam.dcl.beans.CustomCode;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.dao.CustomCodeDAO;
import net.sam.dcl.form.CustomCodesForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CustomCodesAction extends DBAction implements IDispatchable, IFormAutoSave
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    CustomCodesForm form = (CustomCodesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-custom_codes", CustomCodesForm.CustomCode.class, null, null);

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !(currentUser.isAdmin() || currentUser.isDeclarant());
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward block(IActionContext context) throws Exception
  {
    CustomCodesForm form = (CustomCodesForm) context.getForm();
    CustomCode customCode = new CustomCode();
    customCode.setCode(form.getCus_code());
    if ("1".equals(form.getBlock()))
    {
      customCode.setBlock("");
    }
    else
    {
      customCode.setBlock("1");
    }
    CustomCodeDAO.saveBlock(context, customCode);

    return execute(context);
  }

  public ActionForward checkLaw240(IActionContext context) throws Exception
  {
    CustomCodesForm form = (CustomCodesForm) context.getForm();
    CustomCode customCode = new CustomCode();
    customCode.setCode(form.getCus_code());
    if ("1".equals(form.getCus_law_240_flag()))
    {
      customCode.setLaw240Flag("");
    }
    else
    {
      customCode.setLaw240Flag("1");
    }
    CustomCodeDAO.saveLaw240Flag(context, customCode);

    return execute(context);
  }
}
