package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.db.VDbException;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.dbo.DboCustomCode;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.NomenclatureProduceCustomCodeFromHistoryForm;
import net.sam.dcl.form.NomenclatureProduceCustomCodeHistoryForm;
import net.sam.dcl.service.helper.NomenclatureProduceCustomCodeHistoryHelper;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;

import java.sql.Types;
import java.util.List;

/**
 * Created by A.Shkrobova.
 * Date: 5/23/2015.
 */
public class NomenclatureProduceCustomCodeFromHistoryAction extends DBAction implements IDispatchable
{
	private String id;
	private DboProduce produce;

	public ActionForward show(IActionContext context)
	{
		produce = (DboProduce) context.getRequest().getSession().getAttribute("produce");
		return context.getMapping().findForward("form");
	}

	public ActionForward save(IActionContext context)
	{
		NomenclatureProduceCustomCodeFromHistoryForm form = (NomenclatureProduceCustomCodeFromHistoryForm) context.getForm();
		if (id == null)
		{
			NomenclatureProduceCustomCodeHistoryHelper.insert(String.valueOf(produce.getId()), form.getCustomCode().getCode(), form.getDateCreated());
		}
		else
		{
			NomenclatureProduceCustomCodeHistoryHelper.update(id, form.getCustomCode().getCode(), form.getDateCreated());
		}

		updateProduce(context);
		id = null;
		return context.getMapping().findForward("back");
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		if (produce == null)
		{
			produce = (DboProduce) context.getRequest().getSession().getAttribute("produce");
		}
		id = context.getRequest().getParameter("id");
		NomenclatureProduceCustomCodeFromHistoryForm form = (NomenclatureProduceCustomCodeFromHistoryForm) context.getForm();
		NomenclatureProduceCustomCodeHistoryForm.CustomCode customCode = NomenclatureProduceCustomCodeHistoryHelper.loadCustomCodeForHistoryById(id);
		DboCustomCode dboCustomCode = new DboCustomCode();
		VParameter parameter = new VParameter();
		parameter.add("cus_code", customCode.getCus_code(), Types.VARCHAR);
		DAOUtils.load(context, "custom_code_load_light", dboCustomCode, parameter);
		form.setCustomCode(dboCustomCode);
		form.setDateCreated(customCode.getDate_created());
		return context.getMapping().findForward("form");
	}

	private void updateProduce(IActionContext context)
	{
		List<NomenclatureProduceCustomCodeHistoryForm.CustomCode> allCustomCodesFromHistory = NomenclatureProduceCustomCodeHistoryHelper.getAllCustomCodesFromHistory(String.valueOf(produce.getId()));
		if (allCustomCodesFromHistory.size() > 0)
		{
			String lastCustomCode = allCustomCodesFromHistory.get(0).getCus_code();
			if (!StringUtil.equal(produce.getCusCode(), lastCustomCode))
			{
				try
				{
					VParameter vParameter = new VParameter();
					vParameter.add("cus_code", lastCustomCode, Types.VARCHAR);
					vParameter.add("prd_id", String.valueOf(produce.getId()), Types.INTEGER);
					DAOUtils.executeQuery(context, "update_produce_code", vParameter);
				}
				catch (Exception e)
				{
					if (!(e instanceof VDbException && e.getMessage().equals("No resultset for sql")))
					{
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
}
