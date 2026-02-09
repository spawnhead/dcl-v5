package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.SerialNumber;
import net.sam.dcl.form.SerialNumberListForm;
import net.sam.dcl.util.DAOUtils;

import java.util.List;

import org.apache.struts.action.ActionForward;

public class SerialNumberListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    SerialNumberListForm form = (SerialNumberListForm) context.getForm();
    List res;
    res = DAOUtils.fillList(context, "select-serial_number_dep_prd_id", form, SerialNumber.class, null, null);

    form.setList(res);
    return context.getMapping().getInputForward();
  }
}