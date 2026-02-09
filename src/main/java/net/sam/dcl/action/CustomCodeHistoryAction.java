package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.*;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CustomCodeHistoryAction extends DBAction
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    CustomCodeHistoryForm form = (CustomCodeHistoryForm) context.getForm();
    DAOUtils.load(context, "custom_code-load-by-code", null);
    DAOUtils.fillGrid(context, form.getGrid(), "select-custom_codes-history", CustomCodeHistoryForm.CustomCode.class, null, null);
    return context.getMapping().getInputForward();
  }

}
