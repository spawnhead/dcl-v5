package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CustomCodesForm extends BaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();
  String cus_code;
  String cus_law_240_flag;
  String block;

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getCus_code()
  {
    return cus_code;
  }

  public void setCus_code(String cus_code)
  {
    this.cus_code = cus_code;
  }

  public String getCus_law_240_flag()
  {
    return cus_law_240_flag;
  }

  public void setCus_law_240_flag(String cus_law_240_flag)
  {
    this.cus_law_240_flag = cus_law_240_flag;
  }

  public String getBlock()
  {
    return block;
  }

  public void setBlock(String block)
  {
    this.block = block;
  }

  static public class CustomCode
  {
    String cus_id;
    String cus_code;
    String cus_law_240_flag;
    String cus_description;
    double cus_percent;
    String cus_instant;
    String cus_block;

    public String getCus_id()
    {
      return cus_id;
    }

    public void setCus_id(String cus_id)
    {
      this.cus_id = cus_id;
    }

    public String getCus_code()
    {
      return cus_code;
    }

    public void setCus_code(String cus_code)
    {
      this.cus_code = cus_code;
    }

    public String getCus_law_240_flag()
    {
      return cus_law_240_flag;
    }

    public void setCus_law_240_flag(String cus_law_240_flag)
    {
      this.cus_law_240_flag = cus_law_240_flag;
    }

    public String getCus_description()
    {
      return cus_description;
    }

    public void setCus_description(String cus_description)
    {
      this.cus_description = cus_description;
    }

    public double getCus_percent()
    {
      return cus_percent;
    }

    public void setCus_percent(double cus_percent)
    {
      this.cus_percent = cus_percent;
    }

    public String getCus_percent_formatted()
    {
      return StringUtil.double2appCurrencyString(cus_percent);
    }

    public String getCus_instant()
    {
      return cus_instant;
    }

    public void setCus_instant(String cus_instant)
    {
      this.cus_instant = cus_instant;
    }

    public String getCus_block()
    {
      return cus_block;
    }

    public void setCus_block(String cus_block)
    {
      this.cus_block = cus_block;
    }

    public String getMsgCheckBlock()
    {
      if ("1".equals(getCus_block()))
        return "ask_unblock";
      else
        return "ask_block";
    }

  }
}
