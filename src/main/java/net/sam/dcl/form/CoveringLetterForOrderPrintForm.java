package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CoveringLetterForOrderPrintForm extends BaseDispatchValidatorForm
{
  String date;
  Order order;
	List produces = new ArrayList();
	int totalLinesCount;
  User manager = new User();
  List<Page> pages = new ArrayList<Page>();
  List<ContractorNumbers> contractorNumbers = new ArrayList<ContractorNumbers>();
  String number;
  String ord_by_guaranty;
  boolean hasEmptySpec = false;
  float printScale;

  public Order getOrder()
  {
    return order;
  }

  public void setOrder(Order order)
  {
    this.order = order;
  }

	public List getProduces()
	{
		return produces;
	}

	public void setProduces(List produces)
	{
		this.produces = produces;
	}

	public int getTotalLinesCount()
	{
		return totalLinesCount;
	}

	public void setTotalLinesCount(int totalLinesCount)
	{
		this.totalLinesCount = totalLinesCount;
	}

	public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public List<Page> getPages()
  {
    return pages;
  }

  public void setPages(List<Page> pages)
  {
    this.pages = pages;
  }

  public void addPage(Page page)
  {
    pages.add(page);
  }

  public List<ContractorNumbers> getContractorNumbers()
  {
    return contractorNumbers;
  }

  public void setContractorNumbers(List<ContractorNumbers> contractorNumbers)
  {
    this.contractorNumbers = contractorNumbers;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getOrd_by_guaranty()
  {
    return ord_by_guaranty;
  }

  public void setOrd_by_guaranty(String ord_by_guaranty)
  {
    this.ord_by_guaranty = ord_by_guaranty;
  }

  public boolean isHasEmptySpec()
  {
    return hasEmptySpec;
  }

  public void setHasEmptySpec(boolean hasEmptySpec)
  {
    this.hasEmptySpec = hasEmptySpec;
  }

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }

  static public class Page
  {
    String positions;

    String purchaserName;
    String contractNumber;
    String contractDate;
    String conOriginal;
    String specificationNumber;
    String specificationDate;
    String spcOriginal;
    String seller;
    String payCondition;
    Currency currency = new Currency();
    double summ;
    List<Specification.Payment> payments = new ArrayList<Specification.Payment>();
    double rate;


    public String getPurchaserName()
    {
      return purchaserName;
    }

    public void setPurchaserName(String purchaserName)
    {
      this.purchaserName = purchaserName;
    }

    public String getContractNumber()
    {
      return contractNumber;
    }

    public void setContractNumber(String contractNumber)
    {
      this.contractNumber = contractNumber;
    }

    public String getContractDate()
    {
      return contractDate;
    }

    public void setContractDate(String contractDate)
    {
      this.contractDate = contractDate;
    }

    private String getConOriginal()
    {
      return conOriginal;
    }

	  public boolean isConOriginal()
	  {
	    //1 - "Оригинал"
	    return "1".equals(getConOriginal());
	  }

	  public boolean isConCopy()
	  {
	    //0 - "Факсовая и т.п. копия"
	    return "0".equals(getConOriginal());
	  }

	  public boolean isConProject()
	  {
	    //пусто - "Проект"
	    return StringUtil.isEmpty(getConOriginal());
	  }

    public void setConOriginal(String conOriginal)
    {
      this.conOriginal = conOriginal;
    }

    public String getSpecificationNumber()
    {
      return specificationNumber;
    }

    public void setSpecificationNumber(String specificationNumber)
    {
      this.specificationNumber = specificationNumber;
    }

    public String getSpecificationDate()
    {
      return specificationDate;
    }

    public void setSpecificationDate(String specificationDate)
    {
      this.specificationDate = specificationDate;
    }

    private String getSpcOriginal()
    {
      return spcOriginal;
    }

	  public boolean isSpcOriginal()
	  {
	    //1 - "Оригинал"
	    return "1".equals(getSpcOriginal());
	  }

	  public boolean isSpcCopy()
	  {
	    //0 - "Факсовая и т.п. копия"
	    return "0".equals(getSpcOriginal());
	  }

	  public boolean isSpcProject()
	  {
	    //пусто - "Проект"
	    return StringUtil.isEmpty(getSpcOriginal());
	  }

    public void setSpcOriginal(String spcOriginal)
    {
      this.spcOriginal = spcOriginal;
    }

    public String getSeller()
    {
      return seller;
    }

    public void setSeller(String seller)
    {
      this.seller = seller;
    }

    public String getPayCondition()
    {
      return payCondition;
    }

    public void setPayCondition(String payCondition)
    {
      this.payCondition = payCondition;
    }

    public Currency getCurrency()
    {
      return currency;
    }

    public void setCurrency(Currency currency)
    {
      this.currency = currency;
    }

    public double getSumm()
    {
      return summ;
    }

    public void setSumm(double summ)
    {
      this.summ = summ;
    }

    public List<Specification.Payment> getPayments()
    {
      return payments;
    }

    public void setPayments(List<Specification.Payment> payments)
    {
      this.payments = payments;
    }

    public void addPayment(String date, double summ)
    {
      payments.add(new Specification.Payment(date, summ));
    }

    public double getRate()
    {
      return rate;
    }

    public void setRate(double rate)
    {
      this.rate = rate;
    }

    public String getPositions()
    {
      return positions;
    }

    public void setPositions(String positions)
    {
      this.positions = positions;
    }
  }

  static public class ContractorNumbers
  {
    String contractor;
    String numbersAndCounts = "";

    public ContractorNumbers()
    {
    }

    public ContractorNumbers(String date)
    {
      this.contractor = date;
    }

    public String getContractor()
    {
      return contractor;
    }

    public void setContractor(String contractor)
    {
      this.contractor = contractor;
    }

    public String getNumbersAndCounts()
    {
      return numbersAndCounts;
    }

    public String getNumbersAndCountsFormatted()
    {
      if ( !StringUtil.isEmpty(numbersAndCounts) )
      {
        return numbersAndCounts.substring(0, numbersAndCounts.length() - 2);
      }

      return numbersAndCounts;
    }

    public void setNumbersAndCounts(String numbersAndCounts)
    {
      this.numbersAndCounts = numbersAndCounts;
    }

    public boolean equals(Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ContractorNumbers that = (ContractorNumbers) o;

	    return !(contractor != null ? !contractor.equals(that.contractor) : that.contractor != null);
    }

    public int hashCode()
    {
      int result;
      result = (contractor != null ? contractor.hashCode() : 0);
      return result;
    }
  }

}
