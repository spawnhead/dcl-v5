package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ReadyForShipping implements Serializable
{
  public static String errorDateFormat = "Error Date Format";

  String numberReadyForShipping;

  String rfs_id;
  String opr_id;
  ShippingDocType shippingDocType = new ShippingDocType();
  String rfs_number;
  double rfs_count;
  String rfs_date;
  String rfs_ship_from_stock;
  String rfs_arrive_in_lithuania;
  double rfs_weight;
  String rfs_gabarit;
  String rfs_comment;

  public ReadyForShipping()
  {
  }

  public ReadyForShipping(ReadyForShipping productionTerm)
  {
    numberReadyForShipping = productionTerm.getNumberReadyForShipping();

    rfs_id = productionTerm.getRfs_id();
    opr_id = productionTerm.getOpr_id();
    shippingDocType = new ShippingDocType(productionTerm.getShippingDocType());
    rfs_number = productionTerm.getRfs_number();
    rfs_count = productionTerm.getRfs_count();
    rfs_date = productionTerm.getRfs_date();
    rfs_ship_from_stock = productionTerm.getRfs_ship_from_stock();
    rfs_arrive_in_lithuania = productionTerm.getRfs_arrive_in_lithuania();
    rfs_weight = productionTerm.getRfs_weight();
    rfs_gabarit = productionTerm.getRfs_gabarit();
    rfs_comment = productionTerm.getRfs_comment();
  }

  public String getNumberReadyForShipping()
  {
    return numberReadyForShipping;
  }

  public void setNumberReadyForShipping(String numberReadyForShipping)
  {
    this.numberReadyForShipping = numberReadyForShipping;
  }

  public String getRfs_id()
  {
    return rfs_id;
  }

  public void setRfs_id(String rfs_id)
  {
    this.rfs_id = rfs_id;
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public ShippingDocType getShippingDocType()
  {
    return shippingDocType;
  }

  public void setShippingDocType(ShippingDocType shippingDocType)
  {
    this.shippingDocType = shippingDocType;
  }

  public String getRfs_number()
  {
    return rfs_number;
  }

  public void setRfs_number(String rfs_number)
  {
    this.rfs_number = rfs_number;
  }

  public double getRfs_count()
  {
    return rfs_count;
  }

  public String getRfs_count_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(getRfs_count(), "#,##0.000");
  }

  public void setRfs_count(double rfs_count)
  {
    this.rfs_count = rfs_count;
  }

  public void setRfs_count_formatted(String rfs_count)
  {
    this.rfs_count = StringUtil.appCurrencyString2double(rfs_count);
  }

  public String getRfs_date()
  {
    return rfs_date;
  }

  public String getRfs_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(getRfs_date());
  }

  public void setRfs_date(String rfs_date)
  {
    this.rfs_date = rfs_date;
  }

  public void setRfs_date_formatted(String rfs_date)
  {
    this.rfs_date = StringUtil.appDateString2dbDateString(rfs_date);
    //проверка на ошибку перевода даты из app в db формат
    if ( StringUtil.isEmpty(this.rfs_date) && !StringUtil.isEmpty(rfs_date) )
    {
      this.rfs_date = errorDateFormat;
    }
  }

  public String getRfs_ship_from_stock()
  {
    return rfs_ship_from_stock;
  }

  public String getRfs_ship_from_stock_formatted()
  {
    return StringUtil.dbDateString2appDateString(getRfs_ship_from_stock());
  }

  public void setRfs_ship_from_stock(String rfs_ship_from_stock)
  {
    this.rfs_ship_from_stock = rfs_ship_from_stock;
  }

  public void setRfs_ship_from_stock_formatted(String rfs_ship_from_stock)
  {
    this.rfs_ship_from_stock = StringUtil.appDateString2dbDateString(rfs_ship_from_stock);
    //проверка на ошибку перевода даты из app в db формат
    if ( StringUtil.isEmpty(this.rfs_ship_from_stock) && !StringUtil.isEmpty(rfs_ship_from_stock) )
    {
      this.rfs_ship_from_stock = errorDateFormat;
    }
  }

  public String getRfs_arrive_in_lithuania()
  {
    return rfs_arrive_in_lithuania;
  }

  public String getRfs_arrive_in_lithuania_formatted()
  {
    return StringUtil.dbDateString2appDateString(getRfs_arrive_in_lithuania());
  }

  public void setRfs_arrive_in_lithuania(String rfs_arrive_in_lithuania)
  {
    this.rfs_arrive_in_lithuania = rfs_arrive_in_lithuania;
  }

  public void setRfs_arrive_in_lithuania_formatted(String rfs_arrive_in_lithuania)
  {
    this.rfs_arrive_in_lithuania = StringUtil.appDateString2dbDateString(rfs_arrive_in_lithuania);
    //проверка на ошибку перевода даты из app в db формат
    if ( StringUtil.isEmpty(this.rfs_arrive_in_lithuania) && !StringUtil.isEmpty(rfs_arrive_in_lithuania) )
    {
      this.rfs_arrive_in_lithuania = errorDateFormat;
    }
  }

  public double getRfs_weight()
  {
    return rfs_weight;
  }

  public String getRfs_weight_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(getRfs_weight(), "#,##0.000");
  }

  public void setRfs_weight(double rfs_weight)
  {
    this.rfs_weight = rfs_weight;
  }

  public void setRfs_weight_formatted(String rfs_weight)
  {
    this.rfs_weight = StringUtil.appCurrencyString2double(rfs_weight);
  }

  public String getRfs_gabarit()
  {
    return rfs_gabarit;
  }

  public void setRfs_gabarit(String rfs_gabarit)
  {
    this.rfs_gabarit = rfs_gabarit;
  }

  public String getRfs_comment()
  {
    return rfs_comment;
  }

  public void setRfs_comment(String rfs_comment)
  {
    this.rfs_comment = rfs_comment;
  }
}
