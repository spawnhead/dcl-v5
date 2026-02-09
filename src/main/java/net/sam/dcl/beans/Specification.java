package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.SpecificationForm;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class Specification implements Serializable
{
  protected static Log log = LogFactory.getLog(Specification.class);
  String spc_id;
  String con_id;
  String spc_number;
  String spc_date;
  double spc_summ;
  double spc_nds_rate;
  double spc_summ_nds;
  String spc_executed;
  String spc_occupied;
  String spc_occupied_in_pay_shp;
  double spc_summ_nr;
  DeliveryTermType deliveryTerm = new DeliveryTermType();
  String spc_percent_or_sum;
  double spc_delivery_percent;
  double spc_delivery_sum;
  String spc_delivery_date;
  String spc_delivery_cond;
  String spc_add_pay_cond;
  String spc_original;
  String spc_montage;
  String spc_pay_after_montage;
  String spc_group_delivery;
  String spc_annul;
  String spc_annul_date;
  String spc_in_ctc;
  String spc_comment;
  String attachmentsCount;
  User user = new User();
  String spc_letter1_date;
  String spc_letter2_date;
  String spc_letter3_date;
  String spc_complaint_in_court_date;
  String spc_additional_days_count;

  double payed_summ;
  String payed_date;
	String shp_id;
  double shipped_summ;

  String currencyName;
  String pay_id;
  List<Payment> notClosedPayments = new ArrayList<Payment>();
  List<Payment> payments = new ArrayList<Payment>();

  DeferredAttachmentService attachmentService = null;

  List<SpecificationPayment> specificationPayments = new ArrayList<SpecificationPayment>();

  boolean spcFromCpr = false;

  public Specification()
  {
  }

  public Specification(String spc_id)
  {
    this.spc_id = spc_id;
  }

  public Specification(Specification specification)
  {
    spc_id = specification.getSpc_id();
    con_id = specification.getCon_id();
    spc_number = specification.getSpc_number();
    spc_date = specification.getSpc_date();
    spc_summ = specification.getSpc_summ();
    spc_nds_rate = specification.getSpc_nds_rate();
    spc_summ_nds = specification.getSpc_summ_nds();
    spc_executed = specification.getSpc_executed();
    spc_occupied = specification.getSpc_occupied();
    spc_summ_nr = specification.getSpc_summ_nr();
    deliveryTerm = specification.getDeliveryTerm();
    spc_percent_or_sum = specification.getSpc_percent_or_sum();
    spc_delivery_percent = specification.getSpc_delivery_percent();
    spc_delivery_sum = specification.getSpc_delivery_sum();
    spc_delivery_date = specification.getSpc_delivery_date();
    spc_delivery_cond = specification.getSpc_delivery_cond();
    spc_add_pay_cond = specification.getSpc_add_pay_cond();
    spc_original = specification.getSpc_original();
    spc_montage = specification.getSpc_montage();
    spc_pay_after_montage = specification.getSpc_pay_after_montage();
    spc_group_delivery = specification.getSpc_group_delivery();
    spc_annul = specification.getSpc_annul();
    spc_annul_date = specification.getSpc_annul_date();
    spc_in_ctc = specification.getSpc_in_ctc();
    spc_comment = specification.getSpc_comment();
    attachmentsCount = specification.getAttachmentsCount();
    user = new User(specification.getUser());
    spc_letter1_date = specification.getSpc_letter1_date();
    spc_letter2_date = specification.getSpc_letter2_date();
    spc_letter3_date = specification.getSpc_letter3_date();
    spc_complaint_in_court_date = specification.getSpc_complaint_in_court_date();
    spc_additional_days_count = specification.getSpc_additional_days_count();

    payed_summ = specification.getPayed_summ();
    payed_date = specification.getPayed_date();
    shp_id = specification.getShp_id();
    shipped_summ = specification.getShipped_summ();

    attachmentService = specification.getAttachmentService();
    notClosedPayments = specification.getNotClosedPayments();
    payments = specification.getPayments();
    specificationPayments = specification.getSpecificationPayments();

    spcFromCpr = specification.isSpcFromCpr();
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

  public String getSpc_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_date);
  }

  public void setSpc_date(String spc_date)
  {
    this.spc_date = spc_date;
  }

  public double getSpc_summ()
  {
    return spc_summ;
  }

  public String getSpc_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(spc_summ);
  }

  public void setSpc_summ(double spc_summ)
  {
    this.spc_summ = spc_summ;
  }

  public double getSpc_summ_nds()
  {
    return spc_summ_nds;
  }

  public double getSpc_nds_rate()
  {
    return spc_nds_rate;
  }

  public String getSpc_nds_rate_formatted()
  {
    if (Double.isNaN(spc_nds_rate) && 0 == spc_summ_nds && 0 == spc_summ )
    {
      spc_nds_rate = 0;
    }
    return StringUtil.double2appCurrencyString(spc_nds_rate);
  }

  public void setSpc_nds_rate(double spc_nds_rate)
  {
    this.spc_nds_rate = spc_nds_rate;
  }

  public String getSpc_summ_nds_formatted()
  {
    return StringUtil.double2appCurrencyString(spc_summ_nds);
  }

  public void setSpc_summ_nds(double spc_summ_nds)
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

  public String getSpc_occupied_in_pay_shp()
  {
    return spc_occupied_in_pay_shp;
  }

  public void setSpc_occupied_in_pay_shp(String spc_occupied_in_pay_shp)
  {
    this.spc_occupied_in_pay_shp = spc_occupied_in_pay_shp;
  }

  public double getSpc_summ_nr()
  {
    return spc_summ_nr;
  }

  public String getSpc_summ_nr_formatted()
  {
    return StringUtil.double2appCurrencyString(spc_summ_nr);
  }

  public DeliveryTermType getDeliveryTerm()
  {
    return deliveryTerm;
  }

  public void setDeliveryTerm(DeliveryTermType deliveryTerm)
  {
    this.deliveryTerm = deliveryTerm;
  }

  public void setDeliveryTermNameById(String id)
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( SpecificationForm.EnterAfterPrepayId.equals(id) )
      {
        getDeliveryTerm().setName(StrutsUtil.getMessage(context, "DeliveryTerm.EnterAfterPrepay"));
      }
      else
      {
        getDeliveryTerm().setName(StrutsUtil.getMessage(context, "DeliveryTerm.EnterImmediately"));  
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }

  public boolean isPercentSelected()
  {
    return "0".equals(getSpc_percent_or_sum());
  }

  public boolean isSumSelected()
  {
    return "1".equals(getSpc_percent_or_sum());  
  }

  public String getSpc_percent_or_sum()
  {
    return spc_percent_or_sum;
  }

  public void setSpc_percent_or_sum(String spc_percent_or_sum)
  {
    this.spc_percent_or_sum = spc_percent_or_sum;
  }

  public double getSpc_delivery_percent()
  {
    return spc_delivery_percent;
  }

  public String getSpc_delivery_percent_formatted()
  {
    return StringUtil.double2appCurrencyString(spc_delivery_percent);
  }

  public void setSpc_delivery_percent(double spc_delivery_percent)
  {
    this.spc_delivery_percent = spc_delivery_percent;
  }

  public double getSpc_delivery_sum()
  {
    return spc_delivery_sum;
  }

  public String getSpc_delivery_sum_formatted()
  {
    return StringUtil.double2appCurrencyString(spc_delivery_sum);
  }

  public void setSpc_delivery_sum(double spc_delivery_sum)
  {
    this.spc_delivery_sum = spc_delivery_sum;
  }

  public String getSpc_delivery_date()
  {
    return spc_delivery_date;
  }

  public String getSpc_delivery_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_delivery_date);
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

  public void setSpc_summ_nr(double spc_summ_nr)
  {
    this.spc_summ_nr = spc_summ_nr;
  }

  public String getSpc_add_pay_cond()
  {
    return spc_add_pay_cond;
  }

  public void setSpc_add_pay_cond(String spc_add_pay_cond)
  {
    this.spc_add_pay_cond = spc_add_pay_cond;
  }

  public String getSpc_original()
  {
    return spc_original;
  }

  public void setSpc_original(String spc_original)
  {
    this.spc_original = spc_original;
  }

  public boolean isOriginal()
  {
    //1 - "Оригинал"
    return "1".equals(getSpc_original());
  }

  public boolean isCopy()
  {
    //0 - "Факсовая и т.п. копия"
    return "0".equals(getSpc_original());
  }

  public boolean isProject()
  {
    //пусто - "Проект"
    return StringUtil.isEmpty(getSpc_original());
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

  public String getSpc_annul_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_annul_date);
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

  public String getAttachmentsCount()
  {
    return attachmentsCount;
  }

  public void setAttachmentsCount(String attachmentsCount)
  {
    this.attachmentsCount = attachmentsCount;
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

  public String getSpc_letter1_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_letter1_date);
  }

  public void setSpc_letter1_date(String spc_letter1_date)
  {
    this.spc_letter1_date = spc_letter1_date;
  }

  public String getSpc_letter2_date()
  {
    return spc_letter2_date;
  }

  public String getSpc_letter2_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_letter2_date);
  }

  public void setSpc_letter2_date(String spc_letter2_date)
  {
    this.spc_letter2_date = spc_letter2_date;
  }

  public String getSpc_letter3_date()
  {
    return spc_letter3_date;
  }

  public String getSpc_letter3_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_letter3_date);
  }

  public void setSpc_letter3_date(String spc_letter3_date)
  {
    this.spc_letter3_date = spc_letter3_date;
  }

  public String getSpc_complaint_in_court_date()
  {
    return spc_complaint_in_court_date;
  }

  public String getSpc_complaint_in_court_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_complaint_in_court_date);
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

  public String getSpc_remainder()
  {
    if ( SpecificationForm.EnterAfterPrepayId.equals(getDeliveryTerm().getId()) && !StringUtil.isEmpty(getSpc_percent_or_sum()) && StringUtil.isEmpty(getSpc_delivery_date()) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        // 0 - Процент, 1 - Сумма
        if ( isPercentSelected() && (getSpc_delivery_percent() <= (getPayed_summ() / getSpc_summ() * 100)) )
        {
          return StrutsUtil.getMessage(context, "Specifications.spc_remainder_txt");
        }
        if ( isSumSelected() && getSpc_delivery_sum() <= getPayed_summ() )
        {
          return StrutsUtil.getMessage(context, "Specifications.spc_remainder_txt");
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return "";
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
    return StringUtil.dbDateString2appDateString(payed_date);
  }

  public void setPayed_date(String payed_date)
  {
    this.payed_date = payed_date;
  }

	public String getShp_id()
	{
		return shp_id;
	}

	public void setShp_id(String shp_id)
	{
		this.shp_id = shp_id;
	}

	public double getShipped_summ()
  {
    return shipped_summ;
  }

  public void setShipped_summ(double shipped_summ)
  {
    this.shipped_summ = shipped_summ;
  }

  public String getCurrencyName()
  {
    return currencyName;
  }

  public void setCurrencyName(String currencyName)
  {
    this.currencyName = currencyName;
  }

  public String getPay_id()
  {
    return pay_id;
  }

  public void setPay_id(String pay_id)
  {
    this.pay_id = pay_id;
  }

  public List<Payment> getNotClosedPayments()
  {
    return notClosedPayments;
  }

  public void setNotClosedPayments(List<Payment> notClosedPayments)
  {
    this.notClosedPayments = notClosedPayments;
  }

  public List<Payment> getPayments()
  {
    return payments;
  }

  public void setPayments(List<Payment> payments)
  {
    this.payments = payments;
  }

  public DeferredAttachmentService getAttachmentService()
  {
    return attachmentService;
  }

  public void setAttachmentService(DeferredAttachmentService attachmentService)
  {
    this.attachmentService = attachmentService;
  }

  public List<SpecificationPayment> getSpecificationPayments()
  {
    return specificationPayments;
  }

  public void setSpecificationPayments(List<SpecificationPayment> specificationPayments)
  {
    this.specificationPayments = specificationPayments;
  }

  public boolean isSpcFromCpr()
  {
    return spcFromCpr;
  }

  public void setSpcFromCpr(boolean spcFromCpr)
  {
    this.spcFromCpr = spcFromCpr;
  }

  public void calculateNDSRate()
  {
    if (spc_summ - spc_summ_nds == 0)
    {
      setSpc_nds_rate(Double.NaN);
    }
    else
    {
      setSpc_nds_rate(StringUtil.roundN(spc_summ / (spc_summ - spc_summ_nds) * 100 - 100, 2));
    }
  }

  public void calculatePredPayItog()
  {
    double predPayItog = 0.0;

    for (Payment payment : notClosedPayments)
    {
      predPayItog += payment.getSumm();
    }

    Payment newPayment = new Payment("", StringUtil.roundN(predPayItog, 2));
    notClosedPayments.add(newPayment);
  }

  public double calculatePredPayRate()
  {
    double predPayRate = 0.0;

    if ( notClosedPayments.size() != 0 )
    {
      //get itog string
      Payment payment = notClosedPayments.get(notClosedPayments.size() - 1);

      if ( spc_summ != 0.0 )
      {
        predPayRate = StringUtil.roundN(payment.getSumm() / spc_summ * 100, 2);
      }
    }

    return predPayRate;
  }

  private String checkDatesInLists(String appListDate, String dbPayDate)
  {
    String retStr = "";

    Date dateInList;
    Date paymentDate;
    IActionContext context = ActionContext.threadInstance();
    try
    {
      dateInList = StringUtil.appDateString2Date(appListDate);
      paymentDate = StringUtil.dbDateString2Date(dbPayDate);
      if ( dateInList.before(paymentDate) )
      {
        long day = StringUtil.getDaysBetween(dateInList, paymentDate);
        retStr += " " + StrutsUtil.getMessage(context, "Specification.delay", Long.toString(day));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  private String checkDatesInListsWithNow(String appListDate) throws Exception
  {
    Date paymentDate = StringUtil.getCurrentDateTime();
    return checkDatesInLists(appListDate, StringUtil.date2dbDateString(paymentDate));
  }

  public void calculatePaymentsDescription(List<SpecificationPayment> specificationPayments)
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( payments.size() > 0 )
      {
        double sumInList = 0;
        for ( SpecificationPayment specificationPayment : specificationPayments )
        {
          specificationPayment.setDescription("");
          specificationPayment.setProcessed(false);
          sumInList += specificationPayment.getSpp_sum();

          double paymentSum = 0;
          for ( Payment payment : payments )
          {
            paymentSum += payment.getSumm();
            String description;
            if ( paymentSum >= sumInList )
            {
              description = StrutsUtil.getMessage(context, "Specification.executed", StringUtil.dbDateString2appDateString(payment.getDate()));
              if ( !StringUtil.isEmpty(specificationPayment.getSpp_date()) )
              {
                description += checkDatesInLists(specificationPayment.getSppDateFormatted(), payment.getDate());
              }
              specificationPayment.setDescription(description);
              specificationPayment.setProcessed(true);
              break;
            }
          }
        }

        sumInList = 0;
        for ( SpecificationPayment specificationPayment : specificationPayments )
        {
          if ( specificationPayment.isProcessed() ) //считаем сумму обработанных
          {
            sumInList += specificationPayment.getSpp_sum();
          }
          if ( !specificationPayment.isProcessed() ) //первая не обработанная - и последняя
          {
            double paymentSum = 0;
            for ( Payment payment : payments )
            {
              paymentSum += payment.getSumm();
            }

            String description;
            if ( paymentSum > sumInList )
            {
              description = StrutsUtil.getMessage(context, "Specification.executed_part");
              if ( !StringUtil.isEmpty(specificationPayment.getSpp_date()) )
              {
                description += checkDatesInListsWithNow(specificationPayment.getSppDateFormatted());
              }
              specificationPayment.setDescription(description);
            }

            break;
          }
        }
      }
      else
      {
        for ( SpecificationPayment specificationPayment : specificationPayments )
        {
          if ( !StringUtil.isEmpty(specificationPayment.getSpp_date()) )
          {
            specificationPayment.setDescription(checkDatesInListsWithNow(specificationPayment.getSppDateFormatted()));
          }
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    calculateLastSums(specificationPayments);
  }

  private void calculateLastSums(List<SpecificationPayment> specificationPayments)
  {
    if (specificationPayments.size() == 0)
    {
      return;  
    }

    double sum = 0;
    double percent = 0;

    for (int i = 0; i < specificationPayments.size() - 1; i++ )
    {
      sum += specificationPayments.get(i).getSpp_sum();
      percent += specificationPayments.get(i).getSpp_percent();
    }

    specificationPayments.get(specificationPayments.size() - 1).setSpp_percent(StringUtil.roundN(100 - percent, 5));
    specificationPayments.get(specificationPayments.size() - 1).setSpp_sum(getSpc_summ() - sum);
  }

  public String getNumberWithDate()
  {
    if ( StringUtil.isEmpty(getSpc_number()) )
    {
      return "";
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      return StrutsUtil.getMessage(context, "msg.common.from", getSpc_number(), getSpc_date_formatted());
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
  }

  public String getAnnulStr()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( !StringUtil.isEmpty(getSpc_annul()) )
      {
        retStr += " " + StrutsUtil.getMessage(context, "Specification.spcAnnul");
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return  retStr;
  }

  public void setListParentIds()
  {
    for ( SpecificationPayment specificationPayment : getSpecificationPayments() )
    {
      specificationPayment.setSpc_id(getSpc_id());
    }
  }

  static public class Payment
  {
    String date;
    double summ;

    public Payment()
    {
    }

    public Payment(String date, double summ)
    {
      this.date = date;
      this.summ = summ;
    }

    public String getDate()
    {
      return date;
    }

    public void setDate(String date)
    {
      this.date = date;
    }

    public double getSumm()
    {
      return summ;
    }

    public void setSumm(double summ)
    {
      this.summ = summ;
    }
  }
}
