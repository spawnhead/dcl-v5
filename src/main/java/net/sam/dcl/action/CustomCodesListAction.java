package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

import net.sam.dcl.form.CustomCodesListForm;


import org.apache.struts.action.ActionForward;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class CustomCodesListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    CustomCodesListForm form = (CustomCodesListForm) context.getForm();
    form.setList(DAOUtils.fillList(context, "select-custom_codes-filter", form, CustomCodesListForm.CustomCode.class, null, null));
    return context.getMapping().getInputForward();
  }

}
