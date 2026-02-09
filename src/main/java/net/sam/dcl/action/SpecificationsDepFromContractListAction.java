package net.sam.dcl.action;

import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.Specification;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.dao.ContractDAO;
import net.sam.dcl.dao.SpecificationDAO;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.form.SpecificationsDepFromContractListForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class SpecificationsDepFromContractListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
		SpecificationsDepFromContractListForm form = (SpecificationsDepFromContractListForm) context.getForm();
		List<String> contractIds = new ArrayList<String>();
		if (!StringUtil.isEmpty(form.getCtr_id()) && form.getId().equals("-1")) {
			contractIds = DAOUtils.resultSet2StringList(DAOUtils.executeQuery(context, "select-contracts-ids-for-contractor", new VParameter("ctr_id", form.getCtr_id(), Types.INTEGER)));
		} else {
			if (!StringUtil.isEmpty(form.getId())) {
				contractIds.add(form.getId());
			}
		}

    List types = new ArrayList();
    if (!contractIds.isEmpty())
    {
			for (String contractId :contractIds) {
				Contract contract = ContractDAO.load(context, contractId, false);
				List<Specification> res;
				if (!form.isWithExecuted()) {
					res= SpecificationDAO.loadSpecificationsDepFromContract(context, contract);
				}else{
					res = DAOUtils.fillList(context, "select-specifications-for-contract-id-include-exec", contract, Specification.class, null, null);
				}
				for (Specification specification : res)
				{
					String value = StrutsUtil.getMessage(context, "msg.common.from", specification.getSpc_number(), StringUtil.dbDateString2appDateString(specification.getSpc_date()));
					if (!StringUtil.isEmpty(specification.getSpc_annul()))
					{
						value += " " + StrutsUtil.getMessage(context, "Specification.spcAnnul");
					}
					if (form.isWith_summ())
					{
						value = StrutsUtil.getMessage(context, "msg.common.sum", value, specification.getSpc_summ_formatted());
						value += " " + contract.getCurrency().getName();
					}
					types.add(new Record(specification.getSpc_id(), value, specification.getSpc_number(), specification.getSpc_annul()));
				}
			}
		}
    form.setList(types);
    return context.getMapping().getInputForward();
  }

  static public class Record
  {
    String id;
    String value;
    String number;
    String annul;

    public Record(String id, String value, String number, String annul)
    {
      this.id = id;
      this.value = value;
      this.number = number;
      this.annul = annul;
    }

    public String getId()
    {
      return id;
    }

    public String getValue()
    {
      return value;
    }

    public String getNumber()
    {
      return number;
    }

    public String getAnnul()
    {
      return annul;
    }

    public void setAnnul(String annul)
    {
      this.annul = annul;
    }
  }

}
