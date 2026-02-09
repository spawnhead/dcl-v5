package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.ContractorsForContractsClosedListForm;

import org.apache.struts.action.ActionForward;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class ContractorsForContractsClosedListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    ContractorsForContractsClosedListForm form = (ContractorsForContractsClosedListForm) context.getForm();
    form.setList(DAOUtils.fillList(context, "select-contractors-for_contracts_closed-filter", form, ContractorsForContractsClosedListForm.Contractor.class, null, null));
    return context.getMapping().getInputForward();
  }
}
