package net.sam.dcl.form;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.DispatchActionHelper;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CalculationStateForm extends ReportBaseForm
{
  protected static Log log = LogFactory.getLog(CalculationStateForm.class);
  HolderImplUsingList gridCalcState = new HolderImplUsingList();

  Contractor contractorCalcState = new Contractor();
  CalcStateReportType reportType = new CalcStateReportType();
  Currency currencyCalcState = new Currency();
  Contract contract = new Contract();
  User userCalcState = new User();
  Department departmentCalcState = new Department();

  String view_pay_cond;
  String view_delivery_cond;
  String view_expiration;
  String view_complaint;
  String view_comment;
  String view_manager;
  String view_stuff_category;

  String include_all_specs;
  String earliest_doc_date;
  String not_include_if_earliest;
  String not_include_zero;
  String not_show_annul;
  String notShowExpiredContractZeroBalance;
  Seller sellerCalcState = new Seller();

  boolean readonlyEditCtrButton = true;
  boolean userInLithuania;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
		if (!"showGrid".equals(DispatchActionHelper.getDispatchMethodName(mapping,request)))
    {
			setView_pay_cond("");
			setView_delivery_cond("");
			setView_expiration("");
			setView_complaint("");
			setView_comment("");
			setView_manager("");
			setView_stuff_category("");

			setInclude_all_specs("");
			setNot_include_if_earliest("");
			setNot_include_zero("");
			setNot_show_annul("");
			setNotShowExpiredContractZeroBalance("");
		}
    super.reset(mapping, request);
  }

  public HolderImplUsingList getGridCalcState()
  {
    return gridCalcState;
  }

  public void setGridCalcState(HolderImplUsingList grid)
  {
    this.gridCalcState = grid;
  }

  public boolean getContractorDisabled()
  {
    return !StringUtil.isEmpty(getContractorCalcState().getName());
  }

  public boolean getSelect()
  {
    return !StringUtil.isEmpty(getContractorCalcState().getId());
  }

  public boolean isCanForm()
  {
    return canForm && getSelect();
  }

  public boolean isReadonlyEditCtrButton()
  {
    return readonlyEditCtrButton;
  }

  public void setReadonlyEditCtrButton(boolean readonlyEditCtrButton)
  {
    this.readonlyEditCtrButton = readonlyEditCtrButton;
  }

  public boolean isUserInLithuania()
  {
    return userInLithuania;
  }

  public void setUserInLithuania(boolean userInLithuania)
  {
    this.userInLithuania = userInLithuania;
  }

  public Contractor getContractorCalcState()
  {
    return contractorCalcState;
  }

  public void setContractorCalcState(Contractor contractorCalcState)
  {
    this.contractorCalcState = contractorCalcState;
  }

  public CalcStateReportType getReportType()
  {
    return reportType;
  }

  public void setReportType(CalcStateReportType reportType)
  {
    if (reportType == null)
    {
      this.reportType = new CalcStateReportType();
    }
    else
    {
      this.reportType = reportType;
    }
  }

  public Currency getCurrencyCalcState()
  {
    return currencyCalcState;
  }

  public void setCurrencyCalcState(Currency currency)
  {
    this.currencyCalcState = currency;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public String getContractNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContract().getCon_number()) )
    {
      return "";
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      String retStr;
      retStr = getContract().getCon_number();
      if ( !StringUtil.isEmpty(getContract().getCon_date()) )
      {
        retStr += " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " " + getContract().getCon_date_formatted();
      }
      return retStr;
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
  }

  public User getUserCalcState()
  {
    return userCalcState;
  }

  public void setUserCalcState(User userCalcState)
  {
    this.userCalcState = userCalcState;
  }

  public Department getDepartmentCalcState()
  {
    return departmentCalcState;
  }

  public void setDepartmentCalcState(Department departmentCalcState)
  {
    this.departmentCalcState = departmentCalcState;
  }

  public String getView_pay_cond()
  {
    return view_pay_cond;
  }

  public void setView_pay_cond(String view_pay_cond)
  {
    this.view_pay_cond = view_pay_cond;
  }

  public String getView_delivery_cond()
  {
    return view_delivery_cond;
  }

  public void setView_delivery_cond(String view_delivery_cond)
  {
    this.view_delivery_cond = view_delivery_cond;
  }

  public String getView_expiration()
  {
    return view_expiration;
  }

  public void setView_expiration(String view_expiration)
  {
    this.view_expiration = view_expiration;
  }

  public String getView_complaint()
  {
    return view_complaint;
  }

  public void setView_complaint(String view_complaint)
  {
    this.view_complaint = view_complaint;
  }

  public String getView_comment()
  {
    return view_comment;
  }

  public void setView_comment(String view_comment)
  {
    this.view_comment = view_comment;
  }

  public String getView_manager()
  {
    return view_manager;
  }

  public void setView_manager(String view_manager)
  {
    this.view_manager = view_manager;
  }

  public String getView_stuff_category()
  {
    return view_stuff_category;
  }

  public void setView_stuff_category(String view_stuff_category)
  {
    this.view_stuff_category = view_stuff_category;
  }

  public boolean isShowConNumber()
  {
    return StringUtil.isEmpty(contract.getCon_id()) || "-1".equals(contract.getCon_id());
  }

  public String getTop_contract()
  {
    int baseCount = 2;

    if (isShowConNumber())
    {
      baseCount++;
    }
    if (StringUtil.isEmpty(currencyCalcState.getId()))
    {
      baseCount++;
    }
    if (!StringUtil.isEmpty(view_pay_cond))
    {
      baseCount++;
    }
    if (!StringUtil.isEmpty(view_delivery_cond) && StringUtil.isEmpty(getIsDebit()))
    {
      baseCount += 2;
    }

    return Integer.toString(baseCount);
  }

  public String getInclude_all_specs()
  {
    return include_all_specs;
  }

  public void setInclude_all_specs(String include_all_specs)
  {
    this.include_all_specs = include_all_specs;
  }

  public String getEarliest_doc_date()
  {
    return earliest_doc_date;
  }

  public String getEarliest_doc_date_ts()
  {
    return StringUtil.appDateString2dbDateString(earliest_doc_date);
  }

  public void setEarliest_doc_date(String earliest_doc_date)
  {
    this.earliest_doc_date = earliest_doc_date;
  }

  public String getNot_include_if_earliest()
  {
    return not_include_if_earliest;
  }

  public void setNot_include_if_earliest(String not_include_if_earliest)
  {
    this.not_include_if_earliest = not_include_if_earliest;
  }

  public String getNot_include_zero()
  {
    return not_include_zero;
  }

  public void setNot_include_zero(String not_include_zero)
  {
    this.not_include_zero = not_include_zero;
  }

  public String getNot_show_annul()
  {
    return not_show_annul;
  }

  public void setNot_show_annul(String not_show_annul)
  {
    this.not_show_annul = not_show_annul;
  }

	public String getNotShowExpiredContractZeroBalance()
	{
		return notShowExpiredContractZeroBalance;
	}

	public void setNotShowExpiredContractZeroBalance(String notShowExpiredContractZeroBalance)
	{
		this.notShowExpiredContractZeroBalance = notShowExpiredContractZeroBalance;
	}

	public Seller getSellerCalcState()
  {
    return sellerCalcState;
  }

  public void setSellerCalcState(Seller sellerCalcState)
  {
    this.sellerCalcState = sellerCalcState;
  }

  public String getIsDebit()
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (reportType == null || StringUtil.isEmpty(reportType.getId()))
      {
        return "";
      }
      if (reportType.getId().equals(StrutsUtil.getMessage(context, "report_type_list.calc_debit_id")) )
      {
        return "1";
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return "";
  }

  public boolean isSelectedCurrency()
  {
    return !StringUtil.isEmpty(currencyCalcState.getName());
  }
}
