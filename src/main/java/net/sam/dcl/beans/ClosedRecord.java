package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.ContractClosedDAO;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ClosedRecord implements Serializable
{
  protected static Log log = LogFactory.getLog(ClosedRecord.class);
  String number;

  String lcc_id;
  String ctc_id;
  double lcc_charges;
  double lcc_montage;
  double lcc_transport;
  double lcc_update_sum;
  double sum_out_nds_eur;
  Contractor contractor = new Contractor();
  Contract contract = new Contract();
  Specification specification = new Specification();
  double pay_summ;
  double shp_summ;
  String have_unblocked_prc;

  Date ctcDate;
  Date maxDate;

  String selectedContractId;

  List<Payment> payments = new ArrayList<Payment>();
  List<Shipping> shippings = new ArrayList<Shipping>();
  List<PayShip> listPayShip = new ArrayList<PayShip>();
  String managers = "";
  String products = "";

  List<Payment> deletedPayments = new ArrayList<Payment>();
  List<Shipping> deletedShipping = new ArrayList<Shipping>();
  String deletedPayIds = "";
  String deletedShpIds = "";
  String deletedPayIdsPerm = "";
  String deletedShpIdsPerm = "";
  String deletedPayIdsFinal;
  String deletedShpIdsFinal;

  public ClosedRecord()
  {
  }

  public ClosedRecord(ClosedRecord closedRecord)
  {
    lcc_id = closedRecord.getLcc_id();
    ctc_id = closedRecord.getCtc_id();
    number = closedRecord.getNumber();
    lcc_charges = closedRecord.getLcc_charges();
    lcc_montage = closedRecord.getLcc_montage();
    lcc_transport = closedRecord.getLcc_transport();
    lcc_update_sum = closedRecord.getLcc_update_sum();
    sum_out_nds_eur = closedRecord.getSum_out_nds_eur();
    contractor = new Contractor(closedRecord.getContractor());
    contract = new Contract(closedRecord.getContract());
    specification = new Specification(closedRecord.getSpecification());
    pay_summ = closedRecord.getPay_summ();
    shp_summ = closedRecord.getShp_summ();
    have_unblocked_prc = closedRecord.getHave_unblocked_prc();

	  ctcDate = closedRecord.getCtcDate();
	  maxDate = closedRecord.getMaxDate();

    selectedContractId = closedRecord.getSelectedContractId();

    payments = closedRecord.getPayments();
    shippings = closedRecord.getShippings();
    managers = closedRecord.getManagers();
    products = closedRecord.getProducts();

    deletedPayments = closedRecord.getDeletedPayments();
    deletedShipping = closedRecord.getDeletedShipping();

    deletedPayIds = closedRecord.getDeletedPayIds();
    deletedShpIds = closedRecord.getDeletedShpIds();
    deletedPayIdsPerm = closedRecord.getDeletedPayIdsPerm();
    deletedShpIdsPerm = closedRecord.getDeletedShpIdsPerm();
    deletedPayIdsFinal = closedRecord.getDeletedPayIdsFinal();
    deletedShpIdsFinal = closedRecord.getDeletedShpIdsFinal();
  }

  public String getLcc_id()
  {
    return lcc_id;
  }

  public void setLcc_id(String lcc_id)
  {
    this.lcc_id = lcc_id;
  }

  public String getCtc_id()
  {
    return ctc_id;
  }

  public void setCtc_id(String ctc_id)
  {
    this.ctc_id = ctc_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public double getLcc_charges()
  {
    return lcc_charges;
  }

  public String getLcc_charges_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_charges);
  }

  public void setLcc_charges(double lcc_charges)
  {
    this.lcc_charges = lcc_charges;
  }

  public double getLcc_montage()
  {
    return lcc_montage;
  }

  public String getLcc_montage_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_montage);
  }

  public void setLcc_montage(double lcc_montage)
  {
    this.lcc_montage = lcc_montage;
  }

  public double getLcc_transport()
  {
    return lcc_transport;
  }

  public String getLcc_transport_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_transport);
  }

  public void setLcc_transport(double lcc_transport)
  {
    this.lcc_transport = lcc_transport;
  }

  public double getLcc_update_sum()
  {
    return lcc_update_sum;
  }

  public String getLcc_update_sum_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_update_sum);
  }

  public void setLcc_update_sum(double lcc_update_sum)
  {
    this.lcc_update_sum = lcc_update_sum;
  }

  public double getSum_out_nds_eur()
  {
    return sum_out_nds_eur;
  }

  public String getSum_out_nds_eur_formatted()
  {
    return StringUtil.double2appCurrencyString(sum_out_nds_eur);
  }

  public void setSum_out_nds_eur(double sum_out_nds_eur)
  {
    this.sum_out_nds_eur = sum_out_nds_eur;
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

  public String getContract_number_formatted()
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( contract.isProject() )
      {
        return contract.getCon_number() + StrutsUtil.getMessage(context, "ClosedRecords.project");
      }

      if ( contract.isCopy() )
      {
        return contract.getCon_number() + StrutsUtil.getMessage(context, "ClosedRecords.copy");
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return contract.getCon_number();
  }

  public Specification getSpecification()
  {
    return specification;
  }

  public void setSpecification(Specification specification)
  {
    this.specification = specification;
  }

  public double getPay_summ()
  {
    return pay_summ;
  }

  public void setPay_summ(double pay_summ)
  {
    this.pay_summ = pay_summ;
  }

  public double getShp_summ()
  {
    return shp_summ;
  }

  public void setShp_summ(double shp_summ)
  {
    this.shp_summ = shp_summ;
  }

  public String getHave_unblocked_prc()
  {
    return have_unblocked_prc;
  }

  public boolean haveUnblockedPrc()
  {
    return !StringUtil.isEmpty(getHave_unblocked_prc());  
  }

  public void setHave_unblocked_prc(String have_unblocked_prc)
  {
    this.have_unblocked_prc = have_unblocked_prc;
  }

	public Date getCtcDate()
	{
		return ctcDate;
	}

	public void setCtcDate(Date ctcDate)
	{
		this.ctcDate = ctcDate;
	}

	public Date getMaxDate()
	{
		return maxDate;
	}

	public void setMaxDate(Date maxDate)
	{
		this.maxDate = maxDate;
	}

	public String getSelectedContractId()
  {
    return selectedContractId;
  }

  public void setSelectedContractId(String selectedContractId)
  {
    this.selectedContractId = selectedContractId;
  }

  public String getSpecification_number_formatted()
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (getSpecification().isProject())
      {
        return getSpecification().getSpc_number() + StrutsUtil.getMessage(context, "ClosedRecords.project");
      }

      if ( getSpecification().isCopy() )
      {
        return getSpecification().getSpc_number() + StrutsUtil.getMessage(context, "ClosedRecords.copy");
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return specification.getSpc_number();
  }

  public boolean haveCopyOrProject()
  {
    return (
              getSpecification().isProject() ||
              getSpecification().isCopy() ||
              getContract().isProject() ||
              getContract().isCopy()
           );
  }

	public boolean getShowWarnDates()
	{
		return getMaxDate().after(getCtcDate());
	}

	public String getWarnDates()
	{
		String retStr = "";
		if (getShowWarnDates())
		{
			IActionContext context = ActionContext.threadInstance();
			retStr = "";
			try
			{
				retStr += StrutsUtil.getMessage(context, "ClosedRecords.warnDates");
			}
			catch (Exception e)
			{
				log.error(e);
			}
		}

		return retStr;
	}

  public List<Payment> getPayments()
  {
    return payments;
  }

  public void setPayments(List<Payment> payments)
  {
    this.payments = payments;
  }

  public List<Shipping> getShippings()
  {
    return shippings;
  }

  public void setShippings(List<Shipping> shippings)
  {
    this.shippings = shippings;
  }

  public boolean haveCopyOfShipping()
  {
    for ( Shipping shipping : shippings )
    {
      if (!shipping.isOriginal())
        return true;
    }
    return false;
  }

  public List getListPayShip()
  {
    return listPayShip;
  }

  public String getManagers()
  {
    return managers;
  }

  public void setManagers(String managers)
  {
    this.managers = managers;
  }

  public String getProducts()
  {
    return products;
  }

  public void setProducts(String products)
  {
    this.products = products;
  }

  public List<Payment> getDeletedPayments()
  {
    return deletedPayments;
  }

  public void setDeletedPayments(List<Payment> deletedPayments)
  {
    this.deletedPayments = deletedPayments;
  }

  public List<Shipping> getDeletedShipping()
  {
    return deletedShipping;
  }

  public void setDeletedShipping(List<Shipping> deletedShipping)
  {
    this.deletedShipping = deletedShipping;
  }

  public String getDeletedPayIds()
  {
    return deletedPayIds;
  }

  public void setDeletedPayIds(String deletedPayIds)
  {
    this.deletedPayIds = deletedPayIds;
  }

  public String getDeletedShpIds()
  {
    return deletedShpIds;
  }

  public void setDeletedShpIds(String deletedShpIds)
  {
    this.deletedShpIds = deletedShpIds;
  }

  public String getDeletedPayIdsPerm()
  {
    return deletedPayIdsPerm;
  }

  public void setDeletedPayIdsPerm(String deletedPayIdsPerm)
  {
    this.deletedPayIdsPerm = deletedPayIdsPerm;
  }

  public String getDeletedShpIdsPerm()
  {
    return deletedShpIdsPerm;
  }

  public void setDeletedShpIdsPerm(String deletedShpIdsPerm)
  {
    this.deletedShpIdsPerm = deletedShpIdsPerm;
  }

  public String getDeletedPayIdsFinal()
  {
    return deletedPayIdsFinal;
  }

  public void setDeletedPayIdsFinal(String deletedPayIdsFinal)
  {
    this.deletedPayIdsFinal = deletedPayIdsFinal;
  }

  public String getDeletedShpIdsFinal()
  {
    return deletedShpIdsFinal;
  }

  public void setDeletedShpIdsFinal(String deletedShpIdsFinal)
  {
    this.deletedShpIdsFinal = deletedShpIdsFinal;
  }

  public void formRecList() throws ParseException
  {
    double sumShip = 0.0;
    double sumPay = 0.0;
    listPayShip.clear();
    managers = "";
    products = "";
	  Date maxDate = null;

    //получаем Транспорт Минск-Клиент, EUR из отгрузок
    setLcc_transport(0.0);
    for ( Shipping shipping : shippings )
    {
      setLcc_transport(getLcc_transport() + shipping.getShp_summ_transport());
    }
  
    int listLen = payments.size();
    if ( listLen < shippings.size() )
    {
      listLen = shippings.size();
    }

    int i;
    for (i = 0; i < listLen; i++ )
    {
      PayShip payShip = new PayShip();
      payShip.setId(String.valueOf(i));

      if ( i < shippings.size() )
      {
        Shipping shipping = shippings.get(i);
        shipping.setId(String.valueOf(i));

	      if (null == maxDate)
		      maxDate = shipping.getShpDate();
	      if (maxDate.before(shipping.getShpDate()))
		      maxDate = shipping.getShpDate();

        payShip.setShp_date(shipping.getShp_date());
        payShip.setShp_number(shipping.getShp_number());
        payShip.setShp_original(shipping.getShp_original());
        sumShip += StringUtil.roundN(shipping.getShp_summ_plus_nds(), 2);
        payShip.setShp_summ(StringUtil.double2appCurrencyString(shipping.getShp_summ_plus_nds()));

        for ( int j = 0; j < shipping.getManagers().size(); j++ )
        {
          String manager = (String) shipping.getManagers().get(j);

          if (!getManagers().contains(manager)) //не нашли манагера - добавляем
          {
            if (StringUtil.isEmpty(getManagers()))
            {
              setManagers(getManagers() + " " + manager);
            }
            else
            {
              setManagers(getManagers() + ", " + manager);
            }
          }
        }
        for ( int j = 0; j < shipping.getProducts().size(); j++ )
        {
          String product = (String) shipping.getProducts().get(j);

          if (!getProducts().contains(product)) //не нашли производителя - добавляем
          {
            if (StringUtil.isEmpty(getProducts()))
            {
              setProducts(getProducts() + " " + product);
            }
            else
            {
              setProducts(getProducts() + ", " + product);
            }
          }
        }
      }

      if ( i < payments.size() )
      {
        Payment payment = payments.get(i);
        payment.setId(String.valueOf(i));

	      if (null == maxDate)
		      maxDate = payment.getPayDate();
	      if (maxDate.before(payment.getPayDate()))
		      maxDate = payment.getPayDate();

        payShip.setPay_date(StringUtil.dbDateString2appDateString(payment.getPay_date()));
        sumPay += StringUtil.roundN(payment.getPay_summ(), 2);
        payShip.setPay_summ(StringUtil.double2appCurrencyString(payment.getPay_summ()));
      }

      listPayShip.add(listPayShip.size(), payShip);
    }

	  setMaxDate(maxDate);

    i++;
    PayShip payShip = new PayShip();
    payShip.setId(String.valueOf(i));
    IActionContext context = ActionContext.threadInstance();
    try
    {
      payShip.setShp_date(StrutsUtil.getMessage(context, "ClosedRecordList.total"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    payShip.setShp_summ(StringUtil.double2appCurrencyString(sumShip));
    payShip.setPay_summ(StringUtil.double2appCurrencyString(sumPay));
    listPayShip.add(listPayShip.size(), payShip);

    setShp_summ(StringUtil.roundN(sumShip, 2));
    setPay_summ(StringUtil.roundN(sumPay, 2));

    deletedShpIds = "";
    for (i = 0; i < deletedShipping.size(); i++)
    {
      Shipping shipping = deletedShipping.get(i);
      deletedShpIds += shipping.getShp_id() + ";";
    }
    deletedPayIds = "";
    for ( i = 0; i < deletedPayments.size(); i++)
    {
      Payment payment = deletedPayments.get(i);
      deletedPayIds += payment.getPay_id() + ";";
    }
    setDeletedShpIdsFinal(deletedShpIdsPerm + deletedShpIds);
    setDeletedPayIdsFinal(deletedPayIdsPerm + deletedPayIds);
    try
    {
      //получаем обновленную сумму Сумма без НДС, EUR
      ContractClosedDAO.loadSumEUROutNDSPart(context, this);
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }

  public void deleteSelected(List<PayShip> payShipDeleteIn)
  {
    for ( PayShip payShipDelete : payShipDeleteIn )
    {
      if (!StringUtil.isEmpty(payShipDelete.getSelected_shipping()))
      {
        //удаляем из отгрузок
        deleteFromShippingById(payShipDelete.getId());
      }
      if (!StringUtil.isEmpty(payShipDelete.getSelected_payment()))
      {
        //удаляем из оплат
        deleteFromPaymentById(payShipDelete.getId());
      }
    }
  }

  public void deleteFromShippingById(String id)
  {
    for ( int i = 0; i < shippings.size(); i++)
    {
      Shipping shipping = shippings.get(i);
      if ( shipping.getId().equals(id) )
      {
        deletedShipping.add(deletedShipping.size(), shipping);
        shippings.remove(i);
      }
    }
  }

  public void deleteFromPaymentById(String id)
  {
    for ( int i = 0; i < payments.size(); i++)
    {
      Payment payment = payments.get(i);
      if ( payment.getId().equals(id) )
      {
        deletedPayments.add(deletedPayments.size(), payment);
        payments.remove(i);
      }
    }
  }

  public void restoreDeleted()
  {
    for ( Shipping shipping : deletedShipping )
    {
      shippings.add(shippings.size(), shipping);
    }
    for ( Payment payment : deletedPayments )
    {
      payments.add(payments.size(), payment);
    }
  }

  public void deletePermanentShipping(List<Shipping> delShipping)
  {
    deletedShpIdsPerm = "";
    for ( Shipping shipping : delShipping )
    {
      deleteFromShippingByShpId(shipping.getShp_id());
      deletedShpIdsPerm += shipping.getShp_id() + ";";
    }
  }

  public void deleteFromShippingByShpId(String shp_id)
  {
    for ( int i = 0; i < shippings.size(); i++)
    {
      Shipping shipping = shippings.get(i);
      if ( shipping.getShp_id().equals(shp_id) )
      {
        shippings.remove(i);
      }
    }
  }

  public void deletePermanentPayments(List<Payment> delPayments)
  {
    deletedPayIdsPerm = "";
    for ( Payment payment : delPayments )
    {
      deleteFromPaymentByPayId(payment.getPay_id());
      deletedPayIdsPerm += payment.getPay_id() + ";";
    }
  }

  public void deleteFromPaymentByPayId(String pay_id)
  {
    for ( int i = 0; i < payments.size(); i++)
    {
      Payment payment = payments.get(i);
      if ( payment.getPay_id().equals(pay_id) )
      {
        payments.remove(i);
      }
    }
  }

  public boolean isCorrectLine()
  {
    if ( ( !StringUtil.isEmpty(getSpecification().getSpc_group_delivery()) &&
           getSpecification().getSpc_summ() >= getPay_summ() &&
           getSpecification().getSpc_summ() >= getShp_summ() &&
           getPay_summ() != getShp_summ() 
         )
         ||
         ( !StringUtil.isEmpty(getSpecification().getSpc_group_delivery()) &&
           getPay_summ() == 0 &&
           getShp_summ() == 0
         )   
       )
      {
        return false;
      }

    return true;
  }

  static public class PayShip
  {
    String id;
    String selected_shipping;
    String shp_date;
    String shp_number;
    String shp_summ;
    String shp_original;
    String selected_payment;
    String pay_date;
    String pay_summ;

    public String getId()
    {
      return id;
    }

    public void setId(String id)
    {
      this.id = id;
    }

    public String getSelected_shipping()
    {
      return selected_shipping;
    }

    public void setSelected_shipping(String selected_shipping)
    {
      this.selected_shipping = selected_shipping;
    }

    public String getShp_date()
    {
      return shp_date;
    }

    public void setShp_date(String shp_date)
    {
      this.shp_date = shp_date;
    }

    public String getShp_number()
    {
      return shp_number;
    }

    public void setShp_number(String shp_number)
    {
      this.shp_number = shp_number;
    }

    public String getShp_original()
    {
      return shp_original;
    }

    public void setShp_original(String shp_original)
    {
      this.shp_original = shp_original;
    }

    public String getShippingNumberWithOriginal()
    {
      if (StringUtil.isEmpty(getShp_number()))
        return "";

      String retStr = getShp_number();
      IActionContext context = ActionContext.threadInstance();
      try
      {
        if ( StringUtil.isEmpty(getShp_original()) )
        {
          return retStr += StrutsUtil.getMessage(context, "Shipping.copy");
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }

    public String getShp_summ()
    {
      return shp_summ;
    }

    public void setShp_summ(String shp_summ)
    {
      this.shp_summ = shp_summ;
    }

    public String getSelected_payment()
    {
      return selected_payment;
    }

    public void setSelected_payment(String selected_payment)
    {
      this.selected_payment = selected_payment;
    }

    public String getPay_date()
    {
      return pay_date;
    }

    public void setPay_date(String pay_date)
    {
      this.pay_date = pay_date;
    }

    public String getPay_summ()
    {
      return pay_summ;
    }

    public void setPay_summ(String pay_summ)
    {
      this.pay_summ = pay_summ;
    }
  }

  static public class PermanentDeletedRecord
  {
    String spc_id;
    List<Payment> deletedPayments = new ArrayList<Payment>();
    List<Shipping> deletedShipping = new ArrayList<Shipping>();

    public String getSpc_id()
    {
      return spc_id;
    }

    public void setSpc_id(String spc_id)
    {
      this.spc_id = spc_id;
    }

    public List<Payment> getDeletedPayments()
    {
      return deletedPayments;
    }

    public void setDeletedPayments(List<Payment> deletedPayments)
    {
      this.deletedPayments = deletedPayments;
    }

    public List<Shipping> getDeletedShipping()
    {
      return deletedShipping;
    }

    public void setDeletedShipping(List<Shipping> deletedShipping)
    {
      this.deletedShipping = deletedShipping;
    }

    public void fillDeleteShipping(List<Shipping> deletedShippingIn)
    {
      for ( Shipping shipping : deletedShippingIn)
      {
        deletedShipping.add(deletedShipping.size(), shipping);
      }
    }

    public void fillDeletePayments(List<Payment> deletedPaymentsIn)
    {
      for ( Payment payment : deletedPaymentsIn )
      {
        deletedPayments.add(deletedPayments.size(), payment);
      }
    }
  }

  static public class PermanentDeleted
  {
    List<PermanentDeletedRecord> deletedRecords = new ArrayList<PermanentDeletedRecord>();

    public List<PermanentDeletedRecord> getDeletedRecords()
    {
      return deletedRecords;
    }

    public void setDeletedRecords(List<PermanentDeletedRecord> deletedRecords)
    {
      this.deletedRecords = deletedRecords;
    }

    public PermanentDeletedRecord getDeleteRecordBySpcId(String spc_id)
    {
      for ( PermanentDeletedRecord permanentDeletedRecord : deletedRecords )
      {
        if ( permanentDeletedRecord.getSpc_id().equals(spc_id) )
        {
          return permanentDeletedRecord;
        }
      }

      return new PermanentDeletedRecord();
    }
  }
}
