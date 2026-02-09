package net.sam.dcl.action;

import net.sam.dcl.beans.Contract;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.form.ContractsDepFromContractorListForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

import java.util.List;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class ContractsDepFromContractorListAction extends DBAction
{
	public ActionForward execute(IActionContext context) throws Exception
	{
		ContractsDepFromContractorListForm form = (ContractsDepFromContractorListForm) context.getForm();
		if (StringUtil.isEmpty(form.getCon_seller()))
		{
			form.setCon_seller(getFormattedSellersIds(context));
		}

		List<Contract> res;
		if ("true".equals(form.getIs_for_closed()))
		{
			res = DAOUtils.fillList(context, "select-contracts-for-contractor-id-for_cc", form, Contract.class, null, null);
		}
		else
		{
			res = DAOUtils.fillList(context, "select-contracts-for-contractor-id", form, Contract.class, null, null);
		}

		if (form.isHave_all())
		{
			Contract contract = new Contract(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
			res.add(0, contract);
		}

		form.setList(res);
		return context.getMapping().getInputForward();
	}

	// For correct work of db procedure dcl_contract_for_contractor seller's ids should be format as "id1;id2;"
	private String getFormattedSellersIds(IActionContext context) throws Exception
	{
		List<String> sellersIds = DAOUtils.resultSet2StringList(DAOUtils.executeQuery(context, "select_all_sellers_id", null, null));
		if (sellersIds != null)
		{
			String formattedSellersIds = sellersIds.get(0);
			sellersIds.remove(0);
			for (String sellerId : sellersIds)
			{
				formattedSellersIds = formattedSellersIds + ";";
				formattedSellersIds = String.format(formattedSellersIds + "%s", sellerId);
			}
			return formattedSellersIds;
		}

		return null;
	}
}
