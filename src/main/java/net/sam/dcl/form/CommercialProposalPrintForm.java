package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.beans.Currency;

import java.util.*;

public class CommercialProposalPrintForm extends BaseDispatchValidatorForm
{
  boolean isInvoice = false;
  boolean isContract = false;

  String logoImage;
  Contractor contractor = new Contractor();
  Contractor consignee = new Contractor();
  ContactPerson contactPerson = new ContactPerson();
  String concering;
  String number;
  String date;
  String dateFullFormat;
  String preamble;
  IncoTerm priceCondition = new IncoTerm();
  String country;
  String payCondition;
  IncoTerm deliveryCondition = new IncoTerm();
  String ndsByString;
  String NDS;
  Currency currency = new Currency();
  String deliveryAddress;
  String deliveryTerm;
  String additionalInfo;
  String finalDate;
  PurchasePurpose purchasePurpose = new PurchasePurpose();
  User createUser = new User();
  User user = new User();
  User executor = new User();
  List<Produce> produces = new ArrayList<Produce>();
  Map<String, AddRow> additionalRows = new LinkedHashMap<String, AddRow>();

  int guarantyInMonth;
  double prepayPercent;
  double prepaySum;
  String delayDays;
  String delayDaysInWords;
  String finalDateFullFormat;
  double totalPrint;
  double ndsPrint;
  Contractor contractorSeller = new Contractor();
  ContactPerson contactPersonSeller = new ContactPerson();
  ContactPerson contactPersonCustomer = new ContactPerson();
  boolean assembleMinsk = false;
  boolean providerDelivery = false;
  String providerDeliveryAddress;
  String deliveryCountDay;
  String deliveryCountDayInWords;

  boolean printCatalogNumber = true;
  boolean printUnit = false;
  boolean printExecutor = false;
  boolean printFacsimile = false;
  boolean printPriceListBy = false;
  boolean hideFinalDate = false;
  boolean finalDateAbove = false;

  float printScale;

  static public class Produce
  {
    String name;
    String number;
    String unit;
    String cost;
    String count;
    String total;
    String nds;
    String costNDS;
    String priceListBy;
    String discountPercent;
    String number1C;

    public Produce()
    {
    }

    public Produce(String name,
                   String number,
                   String unit,
                   String cost,
                   String count,
                   String total,
                   String nds,
                   String costNDS,
                   String priceListBy,
                   String discountPercent)
    {
      this.name = name;
      this.number = number;
      this.unit = unit;
      this.cost = cost;
      this.count = count;
      this.total = total;
      this.nds = nds;
      this.costNDS = costNDS;
      this.priceListBy = priceListBy;
      this.discountPercent = discountPercent;
    }

    public String getName()
    {
      return name;
    }

    public void setName(String name)
    {
      this.name = name;
    }

    public String getNumber()
    {
      return number;
    }

    public void setNumber(String number)
    {
      this.number = number;
    }

    public String getUnit()
    {
      return unit;
    }

    public void setUnit(String unit)
    {
      this.unit = unit;
    }

    public String getCost()
    {
      return cost;
    }

    public void setCost(String cost)
    {
      this.cost = cost;
    }

    public String getCount()
    {
      return count;
    }

    public void setCount(String count)
    {
      this.count = count;
    }

    public String getTotal()
    {
      return total;
    }

    public void setTotal(String total)
    {
      this.total = total;
    }

    public String getNds()
    {
      return nds;
    }

    public void setNds(String nds)
    {
      this.nds = nds;
    }

    public String getCostNDS()
    {
      return costNDS;
    }

    public void setCostNDS(String costNDS)
    {
      this.costNDS = costNDS;
    }

    public String getPriceListBy()
    {
      return priceListBy;
    }

    public void setPriceListBy(String priceListBy)
    {
      this.priceListBy = priceListBy;
    }

    public String getDiscountPercent()
    {
      return discountPercent;
    }

    public void setDiscountPercent(String discountPercent)
    {
      this.discountPercent = discountPercent;
    }

    public String getNumber1C()
    {
      return number1C;
    }

    public void setNumber1C(String number1C)
    {
      this.number1C = number1C;
    }
  }

  static public class AddRow
  {
    String total;
    String nds;
    String cost_nds;

    public AddRow()
    {
    }

    public AddRow(String total, String nds, String cost_nds)
    {
      this.total = total;
      this.nds = nds;
      this.cost_nds = cost_nds;
    }

    public String getTotal()
    {
      return total;
    }

    public void setTotal(String total)
    {
      this.total = total;
    }

    public String getNds()
    {
      return nds;
    }

    public void setNds(String nds)
    {
      this.nds = nds;
    }

    public String getCost_nds()
    {
      return cost_nds;
    }

    public void setCost_nds(String cost_nds)
    {
      this.cost_nds = cost_nds;
    }
  }

  public boolean isInvoice()
  {
    return isInvoice;
  }

  public void setInvoice(boolean invoice)
  {
    isInvoice = invoice;
  }

  public boolean isContract()
  {
    return isContract;
  }

  public void setContract(boolean contract)
  {
    this.isContract = contract;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Contractor getConsignee()
  {
    return consignee;
  }

  public void setConsignee(Contractor consignee)
  {
    this.consignee = consignee;
  }

  public ContactPerson getContactPerson()
  {
    return contactPerson;
  }

  public void setContactPerson(ContactPerson contactPerson)
  {
    this.contactPerson = contactPerson;
  }

  public String getConcering()
  {
    return concering;
  }

  public void setConcering(String concering)
  {
    this.concering = concering;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public String getDateFullFormat()
  {
    return dateFullFormat;
  }

  public void setDateFullFormat(String dateFullFormat)
  {
    this.dateFullFormat = dateFullFormat;
  }

  public String getPreamble()
  {
    return preamble;
  }

  public void setPreamble(String preamble)
  {
    this.preamble = preamble;
  }

  public IncoTerm getPriceCondition()
  {
    return priceCondition;
  }

  public void setPriceCondition(IncoTerm priceCondition)
  {
    this.priceCondition = priceCondition;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String country)
  {
    this.country = country;
  }

  public String getPayCondition()
  {
    return payCondition;
  }

  public void setPayCondition(String payCondition)
  {
    this.payCondition = payCondition;
  }

  public IncoTerm getDeliveryCondition()
  {
    return deliveryCondition;
  }

  public void setDeliveryCondition(IncoTerm deliveryCondition)
  {
    this.deliveryCondition = deliveryCondition;
  }

  public String getNdsByString()
  {
    return ndsByString;
  }

  public void setNdsByString(String ndsByString)
  {
    this.ndsByString = ndsByString;
  }

  public String getNDS()
  {
    return NDS;
  }

  public void setNDS(String NDS)
  {
    this.NDS = NDS;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public String getDeliveryAddress()
  {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress)
  {
    this.deliveryAddress = deliveryAddress;
  }

  public String getDeliveryTerm()
  {
    return deliveryTerm;
  }

  public void setDeliveryTerm(String deliveryTerm)
  {
    this.deliveryTerm = deliveryTerm;
  }

  public String getAdditionalInfo()
  {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo)
  {
    this.additionalInfo = additionalInfo;
  }

  public String getFinalDate()
  {
    return finalDate;
  }

  public void setFinalDate(String finalDate)
  {
    this.finalDate = finalDate;
  }

  public PurchasePurpose getPurchasePurpose()
  {
    return purchasePurpose;
  }

  public void setPurchasePurpose(PurchasePurpose purchasePurpose)
  {
    this.purchasePurpose = purchasePurpose;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public User getExecutor()
  {
    return executor;
  }

  public void setExecutor(User executor)
  {
    this.executor = executor;
  }

  public String getLogoImage()
  {
    return logoImage;
  }

  public void setLogoImage(String logoImage)
  {
    this.logoImage = logoImage;
  }

  public List<Produce> getProduces()
  {
    return produces;
  }

  public void setProduces(List<Produce> produces)
  {
    this.produces = produces;
  }

  public Map<String, AddRow> getAdditionalRows()
  {
    return additionalRows;
  }

  public void setAdditionalRows(Map<String, AddRow> additionalRows)
  {
    this.additionalRows = additionalRows;
  }

  public int getGuarantyInMonth()
  {
    return guarantyInMonth;
  }

  public void setGuarantyInMonth(int guarantyInMonth)
  {
    this.guarantyInMonth = guarantyInMonth;
  }

  public double getPrepayPercent()
  {
    return prepayPercent;
  }

  public void setPrepayPercent(double prepayPercent)
  {
    this.prepayPercent = prepayPercent;
  }

  public double getPrepaySum()
  {
    return prepaySum;
  }

  public void setPrepaySum(double prepaySum)
  {
    this.prepaySum = prepaySum;
  }

  public String getDelayDays()
  {
    return delayDays;
  }

  public void setDelayDays(String delayDays)
  {
    this.delayDays = delayDays;
  }

  public String getDelayDaysInWords()
  {
    return delayDaysInWords;
  }

  public void setDelayDaysInWords(String delayDaysInWords)
  {
    this.delayDaysInWords = delayDaysInWords;
  }

  public String getFinalDateFullFormat()
  {
    return finalDateFullFormat;
  }

  public void setFinalDateFullFormat(String finalDateFullFormat)
  {
    this.finalDateFullFormat = finalDateFullFormat;
  }

  public double getTotalPrint()
  {
    return totalPrint;
  }

  public void setTotalPrint(double totalPrint)
  {
    this.totalPrint = totalPrint;
  }

  public double getNdsPrint()
  {
    return ndsPrint;
  }

  public void setNdsPrint(double ndsPrint)
  {
    this.ndsPrint = ndsPrint;
  }

  public Contractor getContractorSeller()
  {
    return contractorSeller;
  }

  public void setContractorSeller(Contractor contractorSeller)
  {
    this.contractorSeller = contractorSeller;
  }

  public ContactPerson getContactPersonSeller()
  {
    return contactPersonSeller;
  }

  public void setContactPersonSeller(ContactPerson contactPersonSeller)
  {
    this.contactPersonSeller = contactPersonSeller;
  }

  public ContactPerson getContactPersonCustomer()
  {
    return contactPersonCustomer;
  }

  public void setContactPersonCustomer(ContactPerson contactPersonCustomer)
  {
    this.contactPersonCustomer = contactPersonCustomer;
  }

  public boolean isAssembleMinsk()
  {
    return assembleMinsk;
  }

  public void setAssembleMinsk(boolean assembleMinsk)
  {
    this.assembleMinsk = assembleMinsk;
  }

  public boolean isProviderDelivery()
  {
    return providerDelivery;
  }

  public void setProviderDelivery(boolean providerDelivery)
  {
    this.providerDelivery = providerDelivery;
  }

  public String getProviderDeliveryAddress()
  {
    return providerDeliveryAddress;
  }

  public void setProviderDeliveryAddress(String providerDeliveryAddress)
  {
    this.providerDeliveryAddress = providerDeliveryAddress;
  }

  public String getDeliveryCountDay()
  {
    return deliveryCountDay;
  }

  public void setDeliveryCountDay(String deliveryCountDay)
  {
    this.deliveryCountDay = deliveryCountDay;
  }

  public String getDeliveryCountDayInWords()
  {
    return deliveryCountDayInWords;
  }

  public void setDeliveryCountDayInWords(String deliveryCountDayInWords)
  {
    this.deliveryCountDayInWords = deliveryCountDayInWords;
  }

  public boolean isPrintCatalogNumber()
  {
    return printCatalogNumber;
  }

  public void setPrintCatalogNumber(boolean printCatalogNumber)
  {
    this.printCatalogNumber = printCatalogNumber;
  }

  public boolean isPrintUnit()
  {
    return printUnit;
  }

  public void setPrintUnit(boolean printUnit)
  {
    this.printUnit = printUnit;
  }

  public boolean isPrintExecutor()
  {
    return printExecutor;
  }

  public void setPrintExecutor(boolean printExecutor)
  {
    this.printExecutor = printExecutor;
  }

  public boolean isPrintFacsimile()
  {
    return printFacsimile;
  }

  public void setPrintFacsimile(boolean printFacsimile)
  {
    this.printFacsimile = printFacsimile;
  }

  public boolean isPrintPriceListBy()
  {
    return printPriceListBy;
  }

  public void setPrintPriceListBy(boolean printPriceListBy)
  {
    this.printPriceListBy = printPriceListBy;
  }

  public boolean isHideFinalDate()
  {
    return hideFinalDate;
  }

  public void setHideFinalDate(boolean hideFinalDate)
  {
    this.hideFinalDate = hideFinalDate;
  }

  public boolean isFinalDateAbove()
  {
    return finalDateAbove;
  }

  public void setFinalDateAbove(boolean finalDateAbove)
  {
    this.finalDateAbove = finalDateAbove;
  }

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }
}
