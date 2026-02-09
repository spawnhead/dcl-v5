package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.UnitForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.db.VDbConnection;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UnitAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    context.getRequest().setAttribute("readOnlyChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return true;
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    UnitForm form = (UnitForm) context.getForm();
    form.setUnt_id("");
		form.setIs_acc_for_contract("1");
    getNamesList(context);
    return input(context);
  }

  protected void getNamesList(IActionContext context) throws Exception
  {
    UnitForm form = (UnitForm) context.getForm();
    form.getUnitgrid().getDataList().clear();
    DAOUtils.fillGrid(context, form.getUnitgrid(), "select-unit-languages", UnitForm.UnitLanguages.class, null, null);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
  	getNamesList(context);
		UnitForm form = (UnitForm) context.getForm();

		VResultSet vResultSet = DAOUtils.executeQuery(context, "select_unit_for_cpr_acceptable", new VParameter("unt_id", form.getUnt_id(), 4));
		vResultSet.next();
		String isCprAcceptable = vResultSet.getData("is_acceptable_for_cpr");
		form.setIs_acc_for_contract(isCprAcceptable);
    return input(context);
  }

  public boolean checkSave(IActionContext context) throws Exception
  {
    boolean ret = true;

    UnitForm form = (UnitForm) context.getForm();
    //обязателен для заполнения только русский
    for ( int i = 0; i < form.getUnitgrid().getDataList().size(); i++ )
    {
      UnitForm.UnitLanguages ul = (UnitForm.UnitLanguages) form.getUnitgrid().getDataList().get(i);
      if ( ul.getLng_id().equals(StrutsUtil.getMessage(context, "user.default_lng_id")) )
      {
        if ( "".equals(ul.getUnt_name()) )
        {
          StrutsUtil.addError(context, "error.unit.no_name", ul.getLng_name(), null);
          return false;
        }

        if ( ul.getUnt_name().length() > 150 )
        {
          StrutsUtil.addError(context, "error.unit.long_name", null);
          return false;
        }
      }
    }

    return ret;
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    if (!checkSave(context))
    {
      return input(context);
    }

    UnitForm form = (UnitForm) context.getForm();
		if (StringUtil.isEmpty(form.getUnt_id())) {
			DAOUtils.load(context, "unit-insert", new VParameter("is_acceptable_for_cpr", form.getIs_acc_for_contract(), 1));
		} else {
			VParameter parameter = new VParameter();
			parameter.add("is_acceptable_for_cpr", form.getIs_acc_for_contract(), 1);
			parameter.add("unt_id", form.getUnt_id(), 4);
			DAOUtils.executeQuery(context, "unit-update", parameter);
		}

    VDbConnection conn = context.getConnection();
    for ( int i = 0; i < form.getUnitgrid().getDataList().size(); i++ )
    {
      UnitForm.UnitLanguages ul = (UnitForm.UnitLanguages) form.getUnitgrid().getDataList().get(i);
      if ( StringUtil.isEmpty(ul.getUnt_id()) )
      {
        ul.setUnt_id(form.getUnt_id());
        DAOUtils.update(conn, context.getSqlResource().get("insert_unit_language"), ul, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("update_unit_language"), ul, null);
      }
    }
    conn.commit();

    return context.getMapping().findForward("back");
  }
}
