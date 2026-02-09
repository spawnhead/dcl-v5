package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.LanguagesForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class LanguagesAction extends DBAction
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    LanguagesForm form = (LanguagesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-languages", LanguagesForm.Language.class, null, null);

    return context.getMapping().getInputForward();
  }

}
