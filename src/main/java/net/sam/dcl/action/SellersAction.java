package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.SellersForm;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SellersAction extends DBTransactionAction implements IDispatchable
{

	public ActionForward execute(IActionContext context) throws Exception
	{
		final SellersForm form = (SellersForm) context.getForm();
		DAOUtils.fillGrid(context, form.getGrid(), "select-sellers", SellersForm.Seller.class, null, null);

		context.getRequest().setAttribute("alwaysReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				return true;
			}
		});

		context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
		{
			public boolean check(ShowCheckerContext context)
			{
				SellersForm.Seller seller = (SellersForm.Seller) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (seller.isOccupied())
				{
					return false;
				}
				return true;
			}
		}
		);

		return context.getMapping().getInputForward();
	}

	public ActionForward delete(IActionContext context) throws Exception
	{
		DAOUtils.update(context, "seller-delete", null);
		return execute(context);
	}
}