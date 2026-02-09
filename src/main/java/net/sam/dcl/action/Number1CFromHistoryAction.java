package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.Number1CFromHistoryForm;
import net.sam.dcl.service.helper.Number1CHistoryHelper;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;

import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: shprotova
 * Date: 10/30/13
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Number1CFromHistoryAction extends DBAction implements IDispatchable {
  private String id;
  private DboProduce produce;

  public ActionForward create(IActionContext context) {
		produce = (DboProduce) context.getRequest().getSession().getAttribute("produce");
		Number1CFromHistoryForm form = (Number1CFromHistoryForm) context.getForm();
		form.setProductName(produce.getFullName());
		return context.getMapping().findForward("form");
	}

  public ActionForward edit(IActionContext context) throws Exception {
    if (produce == null) {
      produce = (DboProduce) context.getRequest().getSession().getAttribute("produce");
    }
    id = context.getRequest().getParameter("id");
    Number1CFromHistoryForm form = (Number1CFromHistoryForm) context.getForm();
    form.setProductName(produce.getFullName());
    VParameter parameter = new VParameter("ID", id, Types.INTEGER);
    VResultSet resultSet = DAOUtils.executeQuery(context.getConnection(), context.getSqlResource().get("select_from_dcl_1c_number_history_by_id"), null, parameter);
    resultSet.next();
    form.setNumber1C(resultSet.getData("NUMBER_1C"));
    form.setDateCreated(StringUtil.dbTimestampString2appDateString(resultSet.getData("DATE_CREATED")));
    return context.getMapping().findForward("form");
  }

  public ActionForward save(IActionContext context){
    Number1CFromHistoryForm form = (Number1CFromHistoryForm) context.getForm();
    if (id == null) {
      try {
        Number1CHistoryHelper.insert(context, String.valueOf(produce.getId()), form.getNumber1C(), form.getDateCreated());
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      Number1CHistoryHelper.update(context, id, form.getNumber1C(), form.getDateCreated());
    }

		id = null;
    return context.getMapping().findForward("back");
  }


}
