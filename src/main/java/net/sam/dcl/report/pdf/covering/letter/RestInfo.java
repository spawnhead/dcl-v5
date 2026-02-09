/*************************************************************************
 (C) COPYRIGHT International Business Machines Corp. 2012-2014
 All Rights Reserved

 US Government Users Restricted Rights - Use, duplication or
 disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 **************************************************************************/
package net.sam.dcl.report.pdf.covering.letter;

import net.sam.dcl.util.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandra Shkrobova
 * Date: 10/8/14
 */
public class RestInfo
{
  private String date_created = "";
  private double quantity;
  private String contractor_name = "";
  private String specification_number = "";
  private String contract_number = "";

  public RestInfo()
  {
  }

  public String getDate_created()
  {
    return date_created;
  }

  public void setDate_created(String date_created)
  {
    this.date_created = StringUtil.dbTimestampString2appDateString(date_created);
  }

  public double getQuantity()
  {
    return quantity;
  }

	public String getQuantityFormatted()
	{
		return StringUtil.double2appCurrencyString(getQuantity());
	}

  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
  }

  public String getContractor_name()
  {
    return contractor_name;
  }

  public void setContractor_name(String contractor_name)
  {
    this.contractor_name = contractor_name;
  }

  public String getSpecification_number()
  {
    return specification_number;
  }

  public void setSpecification_number(String specification_number)
  {
    this.specification_number = specification_number;
  }

  public String getContract_number()
  {
    return contract_number;
  }

  public void setContract_number(String contract_number)
  {
    this.contract_number = contract_number;
  }

  public String getContractAndSpecification()
  {
    if (!contract_number.isEmpty() && !specification_number.isEmpty())
      return String.format("%s\n%s", contract_number, specification_number);
    return contract_number.isEmpty() ? specification_number : contract_number;
  }
}
