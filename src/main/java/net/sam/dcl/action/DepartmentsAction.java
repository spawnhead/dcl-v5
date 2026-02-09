package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.DepartmentsForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class DepartmentsAction extends DBAction  {

  public ActionForward execute(IActionContext context) throws Exception {
    DepartmentsForm form = (DepartmentsForm) context.getForm();
    DAOUtils.fillGrid(context,form.getGrid(),"select-departments",DepartmentsForm.Department.class,null,null);
    return context.getMapping().getInputForward();
  }

}
