package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CustomCodeHistoryForm extends BaseForm
{
  String cus_code;
  String cus_description;
  HolderImplUsingList grid = new HolderImplUsingList();

  public String getCus_code()
  {
    return cus_code;
  }

  public void setCus_code(String cus_code)
  {
    this.cus_code = cus_code;
  }

  public String getCus_description()
  {
    return cus_description;
  }

  public void setCus_description(String cus_description)
  {
    this.cus_description = cus_description;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class CustomCode
  {
    String cus_id;
    String cus_percent;
    String cus_instant;

    public String getCus_id()
    {
      return cus_id;
    }

    public void setCus_id(String cus_id)
    {
      this.cus_id = cus_id;
    }

    public String getCus_percent()
    {
      return cus_percent;
    }

    public void setCus_percent(String cus_percent)
    {
      this.cus_percent = cus_percent;
    }

    public String getCus_percent_formatted()
    {
      return StringUtil.double2appCurrencyString(Double.parseDouble(cus_percent));
    }

    public String getCus_instant()
    {
      return cus_instant;
    }

    public void setCus_instant(String cus_instant)
    {
      this.cus_instant = cus_instant;
    }

    public String getCus_instant_date()
    {
      return StringUtil.dbTimestampString2appDateString(cus_instant);
    }

  }


}
