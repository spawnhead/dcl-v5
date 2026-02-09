package net.sam.dcl.action;

import net.sam.dcl.beans.Seller;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.SellersListForm;
import org.apache.struts.action.ActionForward;

import java.util.List;

public class SellersListAction extends DBAction
{
	public ActionForward execute(IActionContext context) throws Exception
	{
		SellersListForm form = (SellersListForm) context.getForm();

		List res;
		if (form.isForOrder())
			res = DAOUtils.fillList(context, "select-sellers-for-order-dao", form, Seller.class, null, null);
		else
			res = DAOUtils.fillList(context, "select-sellers-dao", form, Seller.class, null, null);
		form.setList(res);

		if (form.isHave_all())
		{
			form.getList().add(0, new Seller(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all")));
		}

		return context.getMapping().getInputForward();
	}
}