package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.NomenclatureProduceCustomCodeHistoryForm;
import net.sam.dcl.form.Number1CHistoryForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

import java.sql.Types;

/**
 * Created by A.Shkrobova.
 * Date: 5/23/2015.
 */

public class NomenclatureProduceCustomCodeHistoryAction extends DBAction implements IDispatchable {
	private DboProduce produce = null;

	public ActionForward show(IActionContext context) throws Exception {
		NomenclatureProduceCustomCodeHistoryForm form = (NomenclatureProduceCustomCodeHistoryForm) context.getForm();
		produce = (DboProduce) context.getRequest().getSession().getAttribute("produce");
		form.setProduceDescription(formDescription(context));
		form.setPrd_id(String.valueOf(produce.getId()));
		VParameter parameter = new VParameter();
		parameter.add("prd_id", String.valueOf(produce.getId()), Types.INTEGER);
		DAOUtils.fillGrid(context, form.getGrid(), "select_all_custom_codes_for_product", NomenclatureProduceCustomCodeHistoryForm.CustomCode.class, null, parameter);
		return context.getMapping().findForward("form");
	}

	private String formDescription(IActionContext context) throws Exception {
		String description = "";
		if (produce != null) {
			if (!StringUtil.isEmpty(produce.getFullName())) {
				description = String.format("%s", produce.getFullName());
			}

			if (!StringUtil.isEmpty(produce.getType())) {
				description = description + String.format(" %s", produce.getType());
			}

			if (!StringUtil.isEmpty(produce.getParams())) {
				description = description + String.format(" %s", produce.getParams());
			}

			if (!StringUtil.isEmpty(produce.getAddParams())) {
				description = description + String.format(" %s", produce.getAddParams());
			}

			if (!StringUtil.isEmpty(produce.getCatalogNumbersAsString())) {
				description = description + String.format(" %s: %s", StrutsUtil.getMessage(context, "NomenclatureProduceCustomCodeHistory.catalogNumber"), produce.getCatalogNumbersAsString());
			}

			return description;
		}

		return "";
	}
}
