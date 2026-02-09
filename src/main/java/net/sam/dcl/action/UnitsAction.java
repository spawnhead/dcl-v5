package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.UnitsForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.model.IRowProcessor;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UnitsAction extends DBAction
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    UnitsForm form = (UnitsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-units-with-contract-accept", UnitsForm.Unit.class, null, null);

    return show(context);
  }

	public ActionForward show(IActionContext context) throws Exception
	{
		context.getRequest().setAttribute("alwaysReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				return true;
			}
		});

		return context.getMapping().getInputForward();
	}
}
