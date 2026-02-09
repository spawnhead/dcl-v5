package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.HibernateUtil;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class AssembleProduce implements Serializable
{
  protected static Log log = LogFactory.getLog(AssembleProduce.class);
  String apr_id;
  String asm_id;
  String number;
  DboProduce produce = new DboProduce();
  double apr_count;
  String opr_id;
  String apr_occupied;

  String stf_id;
  String stf_name;
  String ctn_number;

  public AssembleProduce()
  {
  }

  public AssembleProduce(String apr_id)
  {
    this.apr_id = apr_id;
  }

  public AssembleProduce(AssembleProduce assembleProduce)
  {
    apr_id = assembleProduce.getApr_id();
    asm_id = assembleProduce.getAsm_id();
    number = assembleProduce.getNumber();
    produce = assembleProduce.getProduce();
    apr_count = assembleProduce.getApr_count();
    opr_id = assembleProduce.getOpr_id();
    apr_occupied = assembleProduce.getApr_occupied();

    stf_id = assembleProduce.getStf_id();
    stf_name = assembleProduce.getStf_name();
    ctn_number = assembleProduce.getCtn_number();
  }

  public String getApr_id()
  {
    return apr_id;
  }

  public void setApr_id(String apr_id)
  {
    this.apr_id = apr_id;
  }

  public String getAsm_id()
  {
    return asm_id;
  }

  public void setAsm_id(String asm_id)
  {
    this.asm_id = asm_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public double getApr_count()
  {
    return apr_count;
  }

  public String getApr_count_formatted()
  {
    return StringUtil.double2appCurrencyString(apr_count);
  }

  public void setApr_count(double apr_count)
  {
    this.apr_count = apr_count;
  }

  public void setApr_count_formatted(String apr_count)
  {
    this.apr_count = StringUtil.appCurrencyString2double(apr_count);
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public String getApr_occupied()
  {
    return apr_occupied;
  }

  public void setApr_occupied(String apr_occupied)
  {
    this.apr_occupied = apr_occupied;
  }

  public String getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(String stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getCatalogNumberForStuffCategory()
  {
    if ( null == produce.getId() || StringUtil.isEmpty(getStf_id()) )
    {
      return "";
    }

		produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
		DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(getStf_id()));
		return catalogNumber == null ? null : catalogNumber.getNumber();
	}
}
