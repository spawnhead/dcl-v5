package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.beans.DeliveryTermType;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.SpecificationPayment;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class SpecificationForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(SpecificationForm.class);
  public static String EnterImmediatelyId = "1";
  public static String EnterAfterPrepayId = "2";

  String is_new_doc;
  String old_number;
  String spc_id;
  String con_id;
  String spc_number;
  String spc_date;
  String spc_summ;
  String spc_summ_nds;
  String spc_executed;
  String spc_occupied;
  String spc_occupied_in_pay_shp;
  DeliveryTermType deliveryTerm = new DeliveryTermType();
  String spc_percent_or_sum_percent;
  String spc_percent_or_sum_sum;
  String spc_delivery_percent;
  String spc_delivery_sum;
  String spc_delivery_date;
  String spc_delivery_cond;

  String spc_add_pay_cond;
  String spc_fax_copy;
  String spc_original;
  String spc_montage;
  String spc_pay_after_montage;
  String spc_group_delivery;
  String spc_annul;
  String spc_annul_date;
  String spc_in_ctc;
  String spc_comment;
  User user = new User();
  String spc_letter1_date;
  String spc_letter2_date;
  String spc_letter3_date;
  String spc_complaint_in_court_date;
  String spc_additional_days_count;

  double payed_summ;
  String payed_date;

  String currencyName;
  String noRoundSum;
  String ndsRate;

  boolean formReadOnly = false;
  boolean readOnlyIfNotAdminForExecuted;
  boolean readOnlyIfNotAdminEconomistLawyerForExecuted;
  boolean showAttach;
  boolean showAttachFiles;

  HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
  DeferredAttachmentService attachmentService = null;
  String attachmentId;
  Integer copy_attachment_id;
  String copy_attachment_name;

  List<SpecificationPayment> specificationPayments = new ArrayList<SpecificationPayment>();

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setSpc_original("");
    setSpc_fax_copy("");
    setSpc_montage("");
    setSpc_pay_after_montage("");
    setSpc_percent_or_sum_percent("");
    setSpc_percent_or_sum_sum("");
    setSpc_annul("");
    if (!isReadOnlyGroupDelivery())
    {
      setSpc_group_delivery("");
    }

    super.reset(mapping, request);
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getOld_number()
  {
    return old_number;
  }

  public void setOld_number(String old_number)
  {
    this.old_number = old_number;
  }

  public String getSpc_id()
  {
    return spc_id;
  }

  public void setSpc_id(String spc_id)
  {
    this.spc_id = spc_id;
  }

  public String getCon_id()
  {
    return con_id;
  }

  public void setCon_id(String con_id)
  {
    this.con_id = con_id;
  }

  public String getSpc_number()
  {
    return spc_number;
  }

  public void setSpc_number(String spc_number)
  {
    this.spc_number = spc_number;
  }

  public String getSpc_date()
  {
    return spc_date;
  }

  public void setSpc_date(String spc_date)
  {
    this.spc_date = spc_date;
  }

  public String getSpc_summ()
  {
    return spc_summ;
  }

  public void setSpc_summ(String spc_summ)
  {
    this.spc_summ = spc_summ;
  }

  public String getSpc_summ_nds()
  {
    return spc_summ_nds;
  }

  public void setSpc_summ_nds(String spc_summ_nds)
  {
    this.spc_summ_nds = spc_summ_nds;
  }

  public String getSpc_executed()
  {
    return spc_executed;
  }

  public void setSpc_executed(String spc_executed)
  {
    this.spc_executed = spc_executed;
  }

  public String getSpc_occupied()
  {
    return spc_occupied;
  }

  public void setSpc_occupied(String spc_occupied)
  {
    this.spc_occupied = spc_occupied;
  }

  public boolean isReadOnlyIfOccupied()
  {
    return !StringUtil.isEmpty(getSpc_occupied()) | formReadOnly;
  }

  public String getSpc_occupied_in_pay_shp()
  {
    return spc_occupied_in_pay_shp;
  }

  public void setSpc_occupied_in_pay_shp(String spc_occupied_in_pay_shp)
  {
    this.spc_occupied_in_pay_shp = spc_occupied_in_pay_shp;
  }

  public DeliveryTermType getDeliveryTerm()
  {
    return deliveryTerm;
  }

  public void setDeliveryTerm(DeliveryTermType deliveryTerm)
  {
    this.deliveryTerm = deliveryTerm;
  }

  public String getSpc_percent_or_sum_percent()
  {
    return spc_percent_or_sum_percent;
  }

  public void setSpc_percent_or_sum_percent(String spc_percent_or_sum_percent)
  {
    this.spc_percent_or_sum_percent = spc_percent_or_sum_percent;
  }

  public String getSpc_percent_or_sum_sum()
  {
    return spc_percent_or_sum_sum;
  }

  public void setSpc_percent_or_sum_sum(String spc_percent_or_sum_sum)
  {
    this.spc_percent_or_sum_sum = spc_percent_or_sum_sum;
  }

  public String getSpc_delivery_percent()
  {
    return spc_delivery_percent;
  }

  public void setSpc_delivery_percent(String spc_delivery_percent)
  {
    this.spc_delivery_percent = spc_delivery_percent;
  }

  public String getSpc_delivery_sum()
  {
    return spc_delivery_sum;
  }

  public void setSpc_delivery_sum(String spc_delivery_sum)
  {
    this.spc_delivery_sum = spc_delivery_sum;
  }

  public String getSpc_delivery_date()
  {
    return spc_delivery_date;
  }

  public boolean isShowRemainder()
  {
    if (SpecificationForm.EnterAfterPrepayId.equals(getDeliveryTerm().getId()) && (!StringUtil.isEmpty(getSpc_percent_or_sum_percent()) || !StringUtil.isEmpty(getSpc_percent_or_sum_sum())) && StringUtil.isEmpty(getSpc_delivery_date()))
    {
      if ( StringUtil.appCurrencyString2double(spc_delivery_percent) == 0 && StringUtil.appCurrencyString2double(spc_delivery_sum) == 0 )
      {
        return false;  
      }
      
      if (!StringUtil.isEmpty(getSpc_percent_or_sum_percent()) && (StringUtil.appCurrencyString2double(spc_delivery_percent) <= (payed_summ / StringUtil.appCurrencyString2double(spc_summ) * 100)))
      {
        return true;
      }
      if (!StringUtil.isEmpty(getSpc_percent_or_sum_sum()) && StringUtil.appCurrencyString2double(spc_delivery_sum) <= payed_summ)
      {
        return true;
      }
    }

    return false;
  }

  public void setSpc_delivery_date(String spc_delivery_date)
  {
    this.spc_delivery_date = spc_delivery_date;
  }

  public String getSpc_delivery_cond()
  {
    return spc_delivery_cond;
  }

  public void setSpc_delivery_cond(String spc_delivery_cond)
  {
    this.spc_delivery_cond = spc_delivery_cond;
  }

  public String getSpc_add_pay_cond()
  {
    return spc_add_pay_cond;
  }

  public void setSpc_add_pay_cond(String spc_add_pay_cond)
  {
    this.spc_add_pay_cond = spc_add_pay_cond;
  }

  public String getSpc_fax_copy()
  {
    return spc_fax_copy;
  }

  public void setSpc_fax_copy(String spc_fax_copy)
  {
    this.spc_fax_copy = spc_fax_copy;
  }

  public String getSpc_original()
  {
    return spc_original;
  }

  public void setSpc_original(String spc_original)
  {
    this.spc_original = spc_original;
  }

  public String getSpc_montage()
  {
    return spc_montage;
  }

  public void setSpc_montage(String spc_montage)
  {
    this.spc_montage = spc_montage;
  }

  public String getSpc_pay_after_montage()
  {
    return spc_pay_after_montage;
  }

  public void setSpc_pay_after_montage(String spc_pay_after_montage)
  {
    this.spc_pay_after_montage = spc_pay_after_montage;
  }

  public String getSpc_group_delivery()
  {
    return spc_group_delivery;
  }

  public void setSpc_group_delivery(String spc_group_delivery)
  {
    this.spc_group_delivery = spc_group_delivery;
  }

  public String getSpc_annul()
  {
    return spc_annul;
  }

  public void setSpc_annul(String spc_annul)
  {
    this.spc_annul = spc_annul;
  }

  public String getSpc_annul_date()
  {
    return spc_annul_date;
  }

  public void setSpc_annul_date(String spc_annul_date)
  {
    this.spc_annul_date = spc_annul_date;
  }

  public String getSpc_in_ctc()
  {
    return spc_in_ctc;
  }

  public void setSpc_in_ctc(String spc_in_ctc)
  {
    this.spc_in_ctc = spc_in_ctc;
  }

  public String getSpc_comment()
  {
    return spc_comment;
  }

  public void setSpc_comment(String spc_comment)
  {
    this.spc_comment = spc_comment;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getSpc_letter1_date()
  {
    return spc_letter1_date;
  }

  public void setSpc_letter1_date(String spc_letter1_date)
  {
    this.spc_letter1_date = spc_letter1_date;
  }

  public String getSpc_letter2_date()
  {
    return spc_letter2_date;
  }

  public void setSpc_letter2_date(String spc_letter2_date)
  {
    this.spc_letter2_date = spc_letter2_date;
  }

  public String getSpc_letter3_date()
  {
    return spc_letter3_date;
  }

  public void setSpc_letter3_date(String spc_letter3_date)
  {
    this.spc_letter3_date = spc_letter3_date;
  }

  public String getSpc_complaint_in_court_date()
  {
    return spc_complaint_in_court_date;
  }

  public void setSpc_complaint_in_court_date(String spc_complaint_in_court_date)
  {
    this.spc_complaint_in_court_date = spc_complaint_in_court_date;
  }

  public String getSpc_additional_days_count()
  {
    return spc_additional_days_count;
  }

  public void setSpc_additional_days_count(String spc_additional_days_count)
  {
    this.spc_additional_days_count = spc_additional_days_count;
  }

  public double getPayed_summ()
  {
    return payed_summ;
  }

  public void setPayed_summ(double payed_summ)
  {
    this.payed_summ = payed_summ;
  }

  public String getPayed_date()
  {
    return payed_date;
  }

  public String getPayed_date_formatted()
  {
    if (!StringUtil.isEmpty(getPayed_date()))
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "Specification.payed_date") + " " + getPayed_date();
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return "";
  }

  public void setPayed_date(String payed_date)
  {
    this.payed_date = payed_date;
  }

  public String getCurrencyName()
  {
    return currencyName;
  }

  public void setCurrencyName(String currencyName)
  {
    this.currencyName = currencyName;
  }

  public String getNoRoundSum()
  {
    return noRoundSum;
  }

  public void setNoRoundSum(String noRoundSum)
  {
    this.noRoundSum = noRoundSum;
  }

  public String getNdsRate()
  {
    return ndsRate;
  }

  public void setNdsRate(String ndsRate)
  {
    this.ndsRate = ndsRate;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isReadOnlyGroupDelivery()
  {
    return !StringUtil.isEmpty(spc_in_ctc) | formReadOnly;
  }

  public boolean isReadOnlyIfNotAdminForExecuted()
  {
    return readOnlyIfNotAdminForExecuted;
  }

  public void setReadOnlyIfNotAdminForExecuted(boolean readOnlyIfNotAdminForExecuted)
  {
    this.readOnlyIfNotAdminForExecuted = readOnlyIfNotAdminForExecuted;
  }

  public boolean isReadOnlyIfNotAdminEconomistLawyerForExecuted()
  {
    return readOnlyIfNotAdminEconomistLawyerForExecuted;
  }

  public void setReadOnlyIfNotAdminEconomistLawyerForExecuted(boolean readOnlyIfNotAdminEconomistLawyerForExecuted)
  {
    this.readOnlyIfNotAdminEconomistLawyerForExecuted = readOnlyIfNotAdminEconomistLawyerForExecuted;
  }

  public boolean isReadOnlySaveButton()
  {
    return isReadOnlyGroupDelivery() & readOnlyIfNotAdminForExecuted & readOnlyIfNotAdminEconomistLawyerForExecuted & formReadOnly;
  }

  public boolean isShowAttach()
  {
    return showAttach;
  }

  public void setShowAttach(boolean showAttach)
  {
    this.showAttach = showAttach;
  }

  public boolean isShowAttachFiles()
  {
    return showAttachFiles;
  }

  public void setShowAttachFiles(boolean showAttachFiles)
  {
    this.showAttachFiles = showAttachFiles;
  }

  public HolderImplUsingList getAttachmentsGrid()
  {
    return attachmentsGrid;
  }

  public void setAttachmentsGrid(HolderImplUsingList attachmentsGrid)
  {
    this.attachmentsGrid = attachmentsGrid;
  }

  public DeferredAttachmentService getAttachmentService()
  {
    return attachmentService;
  }

  public void setAttachmentService(DeferredAttachmentService attachmentService)
  {
    this.attachmentService = attachmentService;
  }

  public String getAttachmentId()
  {
    return attachmentId;
  }

  public void setAttachmentId(String attachmentId)
  {
    this.attachmentId = attachmentId;
  }

  public Integer getCopy_attachment_id()
  {
    return copy_attachment_id;
  }

  public void setCopy_attachment_id(Integer copy_attachment_id)
  {
    this.copy_attachment_id = copy_attachment_id;
  }

  public String getCopy_attachment_name()
  {
    return copy_attachment_name;
  }

  public void setCopy_attachment_name(String copy_attachment_name)
  {
    this.copy_attachment_name = copy_attachment_name;
  }

  public List<SpecificationPayment> getSpecificationPayments()
  {
    return specificationPayments;
  }

  public void setSpecificationPayments(List<SpecificationPayment> specificationPayments)
  {
    this.specificationPayments = specificationPayments;
  }

  public int getSpecificationPaymentsCount()
  {
    return specificationPayments.size();
  }
}
