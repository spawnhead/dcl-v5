package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OrderPrintForm extends BaseDispatchValidatorForm
{
  String logoImage;
  Contractor contractor = new Contractor();
  ContactPerson contact_person = new ContactPerson();
  String concering;
  String number;
  String date;
  String preamble;
  String preamble2;
  IncoTerm priceCondition = new IncoTerm();
  String country;
  String payCondition;
  IncoTerm deliveryCondition = new IncoTerm();
  String deliveryAddress;
  String deliveryTerm;
  String additionalInfo;
  String additionalInfo2;
  String finalDate;
  User director = new User();
  User user = new User();
  List produces = new ArrayList();
  Map additionalRows = new LinkedHashMap();

  private User logist;
  private User directorRB;
  private User chiefDep;
	private User manager;
	private String logistSignature;
	private String directorRBSignature;
	private String chiefDepSignature;
	private String managerSignature;

  private boolean printDelivery;
  private IncoTerm deliveryType;
  private String deliveryCost;
  private String deliveryCostBy;
  private String importFlag;
  private Currency currency;
  private String countTotal;
  private int totalLinesCount;

  String show_unit;
  float printScale;

  public User getLogist()
  {
    return logist;
  }

  public User getDirectorRB()
  {
    return directorRB;
  }

  public User getChiefDep()
  {
    return chiefDep;
  }

  public void setLogist(User logist)
  {
    this.logist = logist;
  }

  public void setDirectorRB(User directorRB)
  {
    this.directorRB = directorRB;
  }

	public User getManager()
 {
   return manager;
 }

  public void setChiefDep(User chiefDep)
  {
    this.chiefDep = chiefDep;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

	public String getLogistSignature()
	{
		return logistSignature;
	}

	public boolean isLogistSignature()
	{
		return !StringUtil.isEmpty(getLogistSignature());
	}

	public void setLogistSignature(String logistSignature)
	{
		this.logistSignature = logistSignature;
	}

	public String getDirectorRBSignature()
	{
		return directorRBSignature;
	}

	public boolean isDirectorRBSignature()
	{
		return !StringUtil.isEmpty(getDirectorRBSignature());
	}

	public void setDirectorRBSignature(String directorRBSignature)
	{
		this.directorRBSignature = directorRBSignature;
	}

	public String getChiefDepSignature()
	{
		return chiefDepSignature;
	}

	public boolean isChiefDepSignature()
	{
		return !StringUtil.isEmpty(getChiefDepSignature());
	}

	public void setChiefDepSignature(String chiefDepSignature)
	{
		this.chiefDepSignature = chiefDepSignature;
	}

	public String getManagerSignature()
	{
		return managerSignature;
	}

	public boolean isManagerSignature()
	{
		return !StringUtil.isEmpty(getManagerSignature());
	}

	public void setManagerSignature(String managerSignature)
	{
		this.managerSignature = managerSignature;
	}

	public void setPrintDelivery(boolean printDelivery)
  {
    this.printDelivery = printDelivery;
  }

  public boolean isPrintDelivery()
  {
    return printDelivery;
  }

  public void setDeliveryType(IncoTerm deliveryType)
  {
    this.deliveryType = deliveryType;
  }

  public IncoTerm getDeliveryType()
  {
    return deliveryType;
  }

  public void setDeliveryCost(String deliveryPrice)
  {
    this.deliveryCost = deliveryPrice;
  }

  public String getDeliveryCost()
  {
    return deliveryCost;
  }

  public void setDeliveryCostBy(String deliveryCostBy)
  {
    this.deliveryCostBy = deliveryCostBy;
  }

  public String getDeliveryCostBy()
  {
    return deliveryCostBy;
  }

  public void setImportFlag(String importFlag)
  {
    this.importFlag = importFlag;
  }

  public String getImportFlag()
  {
    return importFlag;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCountTotal(String countTotal)
  {
    this.countTotal = countTotal;
  }

  public String getCountTotal()
  {
    return countTotal;
  }

  public void setTotalLinesCount(int totalLinesCount)
  {
    this.totalLinesCount = totalLinesCount;
  }

  public int getTotalLinesCount()
  {
    return totalLinesCount;
  }

  public String getShow_unit()
  {
    return show_unit;
  }

  public void setShow_unit(String show_unit)
  {
    this.show_unit = show_unit;
  }

	public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public ContactPerson getContact_person()
  {
    return contact_person;
  }

  public void setContact_person(ContactPerson contact_person)
  {
    this.contact_person = contact_person;
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

  public String getPreamble()
  {
    return preamble;
  }

  public void setPreamble(String preamble)
  {
    this.preamble = preamble;
  }

  public String getPreamble2()
  {
    return preamble2;
  }

  public void setPreamble2(String preamble2)
  {
    this.preamble2 = preamble2;
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

  public String getAdditionalInfo2()
  {
    return additionalInfo2;
  }

  public void setAdditionalInfo2(String additionalInfo2)
  {
    this.additionalInfo2 = additionalInfo2;
  }

  public String getFinalDate()
  {
    return finalDate;
  }

  public void setFinalDate(String finalDate)
  {
    this.finalDate = finalDate;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getLogoImage()
  {
    return logoImage;
  }

  public void setLogoImage(String logoImage)
  {
    this.logoImage = logoImage;
  }

  public List getProduces()
  {
    return produces;
  }

  public void setProduces(List produces)
  {
    this.produces = produces;
  }

  public Map getAdditionalRows()
  {
    return additionalRows;
  }

  public void setAdditionalRows(Map additionalRows)
  {
    this.additionalRows = additionalRows;
  }

  public User getDirector()
  {
    return director;
  }

  public void setDirector(User director)
  {
    this.director = director;
  }
}
