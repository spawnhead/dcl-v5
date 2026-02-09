package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.form.FilesPathsForm;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

public class FilesPathsAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    FilesPathsForm form = (FilesPathsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridFilesPaths(), "select-files_paths", FilesPathsForm.FilesPath.class, null, null);

    return context.getMapping().getInputForward();
  }
}