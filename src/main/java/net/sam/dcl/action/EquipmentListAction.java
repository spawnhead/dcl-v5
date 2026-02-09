package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.Equipment;
import net.sam.dcl.form.EquipmentListForm;
import net.sam.dcl.util.DAOUtils;

import java.util.List;

import org.apache.struts.action.ActionForward;

public class EquipmentListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    EquipmentListForm form = (EquipmentListForm) context.getForm();
    List res;
    res = DAOUtils.fillList(context, "select-equipment_dep_con_id", form, Equipment.class, null, null);
    form.setList(res);
    return context.getMapping().getInputForward();
  }
}