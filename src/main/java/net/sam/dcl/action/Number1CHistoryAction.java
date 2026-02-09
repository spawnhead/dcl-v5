package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.Number1CHistoryForm;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: shprotova
 * Date: 10/14/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Number1CHistoryAction extends DBAction implements IDispatchable {
  private DboProduce produce = null;

  public ActionForward show(IActionContext context) throws Exception {
    Number1CHistoryForm form = (Number1CHistoryForm) context.getForm();
    produce = (DboProduce) context.getRequest().getSession().getAttribute("produce");
    String description = ">>" + produce.getFullName();
    form.setNumber_description(description);
    form.setPrd_id(String.valueOf(produce.getId()));
    VParameter parameter = new VParameter();
    parameter.add("prd_id", String.valueOf(produce.getId()), Types.INTEGER);
    DAOUtils.fillGrid(context, form.getGrid(), "select_from_dcl_1c_number_history_for_grid", Number1CHistoryForm.Number1C.class, null, parameter);
    return context.getMapping().findForward("form");
  }
}
