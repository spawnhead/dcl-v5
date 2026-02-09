package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.ShippingDocTypesForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ShippingDocTypesAction extends DBAction
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    final ShippingDocTypesForm form = (ShippingDocTypesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-shipping_doc_types", ShippingDocTypesForm.ShippingDocType.class, null, null);
		context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
		{
			public boolean check(ShowCheckerContext context)
			{
				ShippingDocTypesForm.ShippingDocType docType = (ShippingDocTypesForm.ShippingDocType) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (docType.isOccupiedB())
				{
					return false;
				}
				return true;
			}
		}
		);

    return context.getMapping().getInputForward();
  }

}