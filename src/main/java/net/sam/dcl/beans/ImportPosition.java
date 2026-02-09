package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.dbo.DboProduce;

/**
 * @author: DG
 * Date: 18.03.2006
 * Time: 14:33:08
 */
public class ImportPosition
{
  String id;
  String doc_id;
  DboProduce produce = new DboProduce();
  String ctn_number;
  String count;
  boolean useMainId = false;

  public ImportPosition()
  {
    this.count = StringUtil.double2appCurrencyString(0.0);
  }

  public ImportPosition(String id)
  {
    this.id = id;
    this.count = StringUtil.double2appCurrencyString(0.0);
  }

  public ImportPosition(ImportPosition position)
  {
    this.id = position.id;
    this.doc_id = position.doc_id;
    this.produce = position.produce;
    this.ctn_number = position.ctn_number;
    this.count = position.count;
    this.useMainId = position.useMainId;
  }

  public String getDoc_id()
  {
    return doc_id;
  }

  public void setDoc_id(String doc_id)
  {
    this.doc_id = doc_id;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public double getCount()
  {
    return StringUtil.appCurrencyString2double(count);
  }

  public void setCount(double count)
  {
    this.count = StringUtil.double2appCurrencyString(count);
  }

  public String getCount_formatted()
  {
    return count;
  }

  public void setCount_formatted(String count)
  {
    this.count = count;
  }

  public boolean isUseMainId()
  {
    return useMainId;
  }

  public void setUseMainId(boolean useMainId)
  {
    this.useMainId = useMainId;
  }
}
