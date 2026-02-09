package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ShippingForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(ShippingForm.class);
  String printNotice;
  boolean needPrintNotice;

  String shp_id;

  User createUser = new User();
  User editUser = new User();
  User manager = new User();
  String shp_date_create;
  String shp_date_edit;

  String shp_number;
  String shp_date;
  String shp_date_expiration;
  Contractor contractor = new Contractor();
  Contract contract = new Contract();
  Specification specification = new Specification();
  Currency currency = new Currency();
  String shp_letter1_date;
  String shp_letter2_date;
  String shp_letter3_date;
  String shp_complaint_in_court_date;
  String shp_comment;
  String shp_montage;
  String shp_montage_checkbox;
  String shp_serial_num_year_out;
  String shp_serial_num_year_out_checkbox;
  String shp_closed;
  String shp_block;
  Contractor contractorWhere = new Contractor();
  Contract contractWhere = new Contract();
  String shp_notice_date;
  String shp_original;

  double shp_summ_plus_nds;
  double shp_summ_transport;
  double shp_sum_update;
  boolean formReadOnly;
  boolean saveReadOnly;
  boolean readOnlyIfNotAdmOrLawyer;
  boolean readOnlyIfNotAdmOrEconomist;
  boolean readOnlyComment;
  boolean showPurchaseSum;
  boolean showBlockMsg = false;

  HolderImplUsingList grid = new HolderImplUsingList();

  String noticeScale = "100";

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( !isReadOnlyIfSpecHaveMontage() )
    {
      setShp_montage_checkbox("");
    }

    if ( !isFormReadOnly() )
    {
      setShp_serial_num_year_out_checkbox("");
      setShp_original("");
    }

    super.reset(mapping, request);
  }

  public String getPrintNotice()
  {
    return printNotice;
  }

  public void setPrintNotice(String printNotice)
  {
    this.printNotice = printNotice;
  }

  public boolean isNeedPrintNotice()
  {
    return needPrintNotice;
  }

  public void setNeedPrintNotice(boolean needPrintNotice)
  {
    this.needPrintNotice = needPrintNotice;
  }

  public String getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

  public String getShp_date_create()
  {
    return shp_date_create;
  }

  public void setShp_date_create(String shp_date_create)
  {
    this.shp_date_create = shp_date_create;
  }

  public String getShp_date_edit()
  {
    return shp_date_edit;
  }

  public void setShp_date_edit(String shp_date_edit)
  {
    this.shp_date_edit = shp_date_edit;
  }

  public String getShp_number()
  {
    return shp_number;
  }

  public void setShp_number(String shp_number)
  {
    this.shp_number = shp_number;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public String getShp_date_expiration()
  {
    return shp_date_expiration;
  }

  public void setShp_date_expiration(String shp_date_expiration)
  {
    this.shp_date_expiration = shp_date_expiration;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public Specification getSpecification()
  {
    return specification;
  }

  public void setSpecification(Specification specification)
  {
    this.specification = specification;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public String getShp_letter1_date()
  {
    return shp_letter1_date;
  }

  public void setShp_letter1_date(String shp_letter1_date)
  {
    this.shp_letter1_date = shp_letter1_date;
  }

  public String getShp_letter2_date()
  {
    return shp_letter2_date;
  }

  public void setShp_letter2_date(String shp_letter2_date)
  {
    this.shp_letter2_date = shp_letter2_date;
  }

  public String getShp_letter3_date()
  {
    return shp_letter3_date;
  }

  public void setShp_letter3_date(String shp_letter3_date)
  {
    this.shp_letter3_date = shp_letter3_date;
  }

  public String getShp_complaint_in_court_date()
  {
    return shp_complaint_in_court_date;
  }

  public void setShp_complaint_in_court_date(String shp_complaint_in_court_date)
  {
    this.shp_complaint_in_court_date = shp_complaint_in_court_date;
  }

  public String getShp_comment()
  {
    return shp_comment;
  }

  public void setShp_comment(String shp_comment)
  {
    this.shp_comment = shp_comment;
  }

  public String getShp_montage()
  {
    return shp_montage;
  }

  public void setShp_montage(String shp_montage)
  {
    this.shp_montage = shp_montage;
  }

  public String getShp_montage_checkbox()
  {
    return shp_montage_checkbox;
  }

  public void setShp_montage_checkbox(String shp_montage_checkbox)
  {
    this.shp_montage_checkbox = shp_montage_checkbox;
  }

  public String getShp_serial_num_year_out()
  {
    return shp_serial_num_year_out;
  }

  public void setShp_serial_num_year_out(String shp_serial_num_year_out)
  {
    this.shp_serial_num_year_out = shp_serial_num_year_out;
  }

  public String getShp_serial_num_year_out_checkbox()
  {
    return shp_serial_num_year_out_checkbox;
  }

  public void setShp_serial_num_year_out_checkbox(String shp_serial_num_year_out_checkbox)
  {
    this.shp_serial_num_year_out_checkbox = shp_serial_num_year_out_checkbox;
  }

  public String getShp_closed()
  {
    return shp_closed;
  }

  public void setShp_closed(String shp_closed)
  {
    this.shp_closed = shp_closed;
  }

  public String getShp_block()
  {
    return shp_block;
  }

  public void setShp_block(String shp_block)
  {
    this.shp_block = shp_block;
  }

  public Contractor getContractorWhere()
  {
    return contractorWhere;
  }

  public void setContractorWhere(Contractor contractorWhere)
  {
    this.contractorWhere = contractorWhere;
  }

  public Contract getContractWhere()
  {
    return contractWhere;
  }

  public void setContractWhere(Contract contractWhere)
  {
    this.contractWhere = contractWhere;
  }

  public String getContractNumberWithDateWhere()
  {
    if ( StringUtil.isEmpty(getContractWhere().getCon_number()) )
    {
      return "";
    }

    return getContractWhere().getNumberWithDate();
  }

  public String getShp_notice_date()
  {
    return shp_notice_date;
  }

  public void setShp_notice_date(String shp_notice_date)
  {
    this.shp_notice_date = shp_notice_date;
  }

  public String getShp_original()
  {
    return shp_original;
  }

  public void setShp_original(String shp_original)
  {
    this.shp_original = shp_original;
  }

  public double getShp_summ_plus_nds()
  {
    return StringUtil.roundN(shp_summ_plus_nds, 2);
  }

  public String getShp_summ_plus_nds_formatted()
  {
    return StringUtil.double2appCurrencyString(shp_summ_plus_nds);
  }

  public void setShp_summ_plus_nds(double shp_summ_plus_nds)
  {
    this.shp_summ_plus_nds = shp_summ_plus_nds;
  }

  public void setShp_summ_plus_nds_formatted(String shp_summ_plus_nds)
  {
    this.shp_summ_plus_nds =  StringUtil.appCurrencyString2doubleSpecial(shp_summ_plus_nds);
  }

  public double getShp_summ_transport()
  {
    return shp_summ_transport;
  }

  public String getShp_summ_transport_formatted()
  {
    return StringUtil.double2appCurrencyString(getShp_summ_transport());
  }

  public void setShp_summ_transport(double shp_summ_transport)
  {
    this.shp_summ_transport = shp_summ_transport;
  }

  public void setShp_summ_transport_formatted(String shp_summ_transport)
  {
    this.shp_summ_transport = StringUtil.appCurrencyString2double(shp_summ_transport);
  }

  public double getShp_sum_update()
  {
    return shp_sum_update;
  }

  public String getShp_sum_update_formatted()
  {
    return StringUtil.double2appCurrencyString(getShp_sum_update());
  }

  public void setShp_sum_update(double shp_sum_update)
  {
    this.shp_sum_update = shp_sum_update;
  }

  public void setShp_sum_update_formatted(String shp_sum_update)
  {
    this.shp_sum_update = StringUtil.appCurrencyString2double(shp_sum_update);
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getNoticeScale()
  {
    return noticeScale;
  }

  public void setNoticeScale(String noticeScale)
  {
    this.noticeScale = noticeScale;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isSaveReadOnly()
  {
    return !(!formReadOnly || !readOnlyIfNotAdmOrLawyer || !readOnlyIfNotAdmOrEconomist || !readOnlyComment);
  }

  public boolean isReadOnlyIfNotAdmOrLawyer()
  {
    return readOnlyIfNotAdmOrLawyer;
  }

  public void setReadOnlyIfNotAdmOrLawyer(boolean readOnlyIfNotAdmOrLawyer)
  {
    this.readOnlyIfNotAdmOrLawyer = readOnlyIfNotAdmOrLawyer;
  }

  public boolean isReadOnlyIfNotAdmOrEconomist()
  {
    return readOnlyIfNotAdmOrEconomist;
  }

  public void setReadOnlyIfNotAdmOrEconomist(boolean readOnlyIfNotAdmOrEconomist)
  {
    this.readOnlyIfNotAdmOrEconomist = readOnlyIfNotAdmOrEconomist;
  }

  public boolean isReadOnlyIfNotAdmOrEconomistOrLawyer()
  {
    return readOnlyIfNotAdmOrEconomist && readOnlyIfNotAdmOrLawyer;
  }

  public boolean isReadOnlyIfSpecHaveMontage()
  {
    return !StringUtil.isEmpty(specification.getSpc_montage()) || readOnlyIfNotAdmOrEconomist;
  }

  public boolean isReadOnlyComment()
  {
    return readOnlyComment;
  }

  public void setReadOnlyComment(boolean readOnlyComment)
  {
    this.readOnlyComment = readOnlyComment;
  }

  public boolean isShowPurchaseSum()
  {
    return showPurchaseSum;
  }

  public void setShowPurchaseSum(boolean showPurchaseSum)
  {
    this.showPurchaseSum = showPurchaseSum;
  }

  public boolean isShowBlockMsg()
  {
    return showBlockMsg;
  }

  public void setShowBlockMsg(boolean showBlockMsg)
  {
    this.showBlockMsg = showBlockMsg;
  }

  public String getContractNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContract().getCon_number()) )
    {
      return "";
    }

    return getContract().getNumberWithDate();
  }

  public String getSpecificationNumberWithDate()
  {
    if ( StringUtil.isEmpty(getSpecification().getSpc_number()) )
    {
      return "";
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      return StrutsUtil.getMessage(context, "msg.common.from", getSpecification().getSpc_number(), getSpecification().getSpc_date_formatted());
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
  }

  public boolean isShowPayAfterMontage()
  {
    if ( !StringUtil.isEmpty(specification.getSpc_montage()) && !StringUtil.isEmpty(specification.getSpc_pay_after_montage()) )
    {
      for (int i = 0; i < grid.getDataList().size() - 2; i++)
      {
        ShippingData.RightRecord record = (ShippingData.RightRecord) grid.getDataList().get(i);
        if ( StringUtil.isEmpty(record.getPosition().getMontageOff()) && StringUtil.isEmpty(record.getPosition().getEnterInUseDate()) )
        {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isReadOnlyDateExpiration()
  {
    return ( isShowPayAfterMontage() || StringUtil.isEmpty(getSpecification().getSpc_id()) || formReadOnly);
  }
}
