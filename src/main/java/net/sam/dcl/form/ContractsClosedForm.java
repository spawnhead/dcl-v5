package net.sam.dcl.form;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractsClosedForm extends JournalForm
{
	protected static Log log = LogFactory.getLog(ContractsClosedForm.class);
	HolderImplUsingList grid = new HolderImplUsingList();
	String ctc_id;
	Contract contract = new Contract();
	String not_block;
	boolean cleanBlock = true;

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		if (isCleanBlock())
		{
			setNot_block("");
		}
		super.reset(mapping, request);
	}

	public String getCtc_id()
	{
		return ctc_id;
	}

	public void setCtc_id(String ctc_id)
	{
		this.ctc_id = ctc_id;
	}

	public Contract getContract()
	{
		return contract;
	}

	public void setContract(Contract contract)
	{
		this.contract = contract;
	}

	public HolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(HolderImplUsingList grid)
	{
		this.grid = grid;
	}

	public String getNot_block()
	{
		return not_block;
	}

	public void setNot_block(String not_block)
	{
		this.not_block = not_block;
	}

	public boolean isCleanBlock()
	{
		return cleanBlock;
	}

	public void setCleanBlock(boolean cleanBlock)
	{
		this.cleanBlock = cleanBlock;
	}

	public String getAdmin()
	{
		IActionContext context = ActionContext.threadInstance();
		User currentUser = UserUtil.getCurrentUser(context.getRequest());

		if (currentUser.isAdmin() || currentUser.isEconomist())
		{
			return "1";
		}
		return "";
	}

	static public class ContractClosed
	{
		String selectedContractId;
		String ctc_id;
		String ctc_number;
		String ctc_date;
		String ctc_contractor;
		String ctc_block;
		String usr_id_create;
		String con_number;
		String con_date;
		String show_warn;
		//String show_warn_dates;

		public String getSelectedContractId()
		{
			return selectedContractId;
		}

		public void setSelectedContractId(String selectedContractId)
		{
			this.selectedContractId = selectedContractId;
		}

		public String getCtc_id()
		{
			return ctc_id;
		}

		public void setCtc_id(String ctc_id)
		{
			this.ctc_id = ctc_id;
		}

		public String getCtc_number()
		{
			return ctc_number;
		}

		public void setCtc_number(String ctc_number)
		{
			this.ctc_number = ctc_number;
		}

		public String getCtc_date()
		{
			return ctc_date;
		}

		public void setCtc_date(String ctc_date)
		{
			this.ctc_date = ctc_date;
		}

		public String getCtc_date_date()
		{
			return StringUtil.dbDateString2appDateString(ctc_date);
		}

		public String getCtc_contractor()
		{
			return ctc_contractor;
		}

		public void setCtc_contractor(String ctc_contractor)
		{
			this.ctc_contractor = ctc_contractor;
		}

		public String getCtc_block()
		{
			return ctc_block;
		}

		public String getMsg_check_block()
		{
			if ("1".equals(ctc_block))
				return "ask_unblock";
			else
				return "ask_block";
		}

		public void setCtc_block(String ctc_block)
		{
			this.ctc_block = ctc_block;
		}

		public String getUsr_id_create()
		{
			return usr_id_create;
		}

		public void setUsr_id_create(String usr_id_create)
		{
			this.usr_id_create = usr_id_create;
		}

		public String getCon_number()
		{
			String retStr;

			String date = getCon_date();
			if (!date.contains("null"))
			{
				date = StringUtil.dbDateString2appDateString(date);
			}
			else
			{
				date = "";
			}

			if (StringUtil.isEmpty(con_number) && StringUtil.isEmpty(date))
			{
				retStr = "";
			}
			else
			{
				IActionContext context = ActionContext.threadInstance();
				retStr = "";
				try
				{
					retStr += StrutsUtil.getMessage(context, "msg.common.from", con_number, StringUtil.dbDateString2appDateString(getCon_date()));
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}

			return retStr;
		}

		public void setCon_number(String con_number)
		{
			this.con_number = con_number;
		}

		public String getCon_date()
		{
			return con_date;
		}

		public void setCon_date(String con_date)
		{
			this.con_date = con_date;
		}

		public String getShow_warn()
		{
			return show_warn;
		}

		public void setShow_warn(String show_warn)
		{
			this.show_warn = show_warn;
		}

		public boolean showWarn()
		{
			return !StringUtil.isEmpty(getShow_warn());
		}
	}

}
