package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ProductionTerm implements Serializable
{
  public static String errorDateFormat = "Error Date Format";

  String numberProductionTerm;

  String ptr_id;
  String opr_id;
  double ptr_count;
  String ptr_date;
  String ptr_comment;

  public ProductionTerm()
  {
  }

  public ProductionTerm(ProductionTerm productionTerm)
  {
    numberProductionTerm = productionTerm.getNumberProductionTerm();

    ptr_id = productionTerm.getPtr_id();
    opr_id = productionTerm.getOpr_id();
    ptr_count = productionTerm.getPtr_count();
    ptr_date = productionTerm.getPtr_date();
    ptr_comment = productionTerm.getPtr_comment();
  }

  public String getPtr_id()
  {
    return ptr_id;
  }

  public void setPtr_id(String ptr_id)
  {
    this.ptr_id = ptr_id;
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public String getNumberProductionTerm()
  {
    return numberProductionTerm;
  }

  public void setNumberProductionTerm(String numberProductionTerm)
  {
    this.numberProductionTerm = numberProductionTerm;
  }

  public double getPtr_count()
  {
    return ptr_count;
  }

  public String getPtr_count_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(ptr_count, "#,##0.000");
  }

  public void setPtr_count(double ptr_count)
  {
    this.ptr_count = ptr_count;
  }

  public void setPtr_count_formatted(String ptr_count)
  {
    this.ptr_count = StringUtil.appCurrencyString2double(ptr_count);
  }

  public String getPtr_date()
  {
    return ptr_date;
  }

  public String getPtr_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(ptr_date);
  }

  public void setPtr_date(String ptr_date)
  {
    this.ptr_date = ptr_date;
  }

  public void setPtr_date_formatted(String ptr_date)
  {
    this.ptr_date = StringUtil.appDateString2dbDateString(ptr_date);
    //проверка на ошибку перевода даты из app в db формат
    if ( StringUtil.isEmpty(this.ptr_date) && !StringUtil.isEmpty(ptr_date) )
    {
      this.ptr_date = errorDateFormat;
    }
  }

  public String getPtr_comment()
  {
    return ptr_comment;
  }

  public void setPtr_comment(String ptr_comment)
  {
    this.ptr_comment = ptr_comment;
  }
}
