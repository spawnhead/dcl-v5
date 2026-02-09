package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.SettingsForm;
import org.apache.struts.action.ActionForward;

public class SettingsAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    final SettingsForm form = (SettingsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridSettings(), "select-settings", SettingsForm.Setting.class, null, null);

    return context.getMapping().getInputForward();
  }

}