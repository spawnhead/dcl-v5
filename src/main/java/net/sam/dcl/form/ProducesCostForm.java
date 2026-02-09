package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProducesCostForm extends JournalForm
{
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();
  String prc_id;
  String number_1c;
  String block_in_filter;

  public String getPrc_id()
  {
    return prc_id;
  }

  public void setPrc_id(String prc_id)
  {
    this.prc_id = prc_id;
  }

  public String getNumber_1c()
  {
    return number_1c;
  }

  public void setNumber_1c(String number_1c)
  {
    this.number_1c = number_1c;
  }

  public String getBlock_in_filter()
  {
    return block_in_filter;
  }

  public void setBlock_in_filter(String block_in_filter)
  {
    this.block_in_filter = block_in_filter;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class ProduceCost
  {
    String prc_id;
    String prc_number;
    String prc_date;
    String prc_route;
    String prc_block;
    String usr_id_create;

    public String getPrc_id()
    {
      return prc_id;
    }

    public void setPrc_id(String prc_id)
    {
      this.prc_id = prc_id;
    }

    public String getPrc_number()
    {
      return prc_number;
    }

    public void setPrc_number(String prc_number)
    {
      this.prc_number = prc_number;
    }

    public String getPrc_date()
    {
      return prc_date;
    }

    public void setPrc_date(String prc_date)
    {
      this.prc_date = prc_date;
    }

    public String getPrc_date_date()
    {
      return StringUtil.dbDateString2appDateString(prc_date);
    }

    public String getPrc_route()
    {
      return prc_route;
    }

    public void setPrc_route(String prc_route)
    {
      this.prc_route = prc_route;
    }

    public String getPrc_block()
    {
      return prc_block;
    }

    public String getMsg_check_block()
    {
      if ("1".equals(prc_block))
        return "ask_unblock";
      else
        return "ask_block";
    }

    public void setPrc_block(String prc_block)
    {
      this.prc_block = prc_block;
    }

    public String getUsr_id_create()
    {
      return usr_id_create;
    }

    public void setUsr_id_create(String usr_id_create)
    {
      this.usr_id_create = usr_id_create;
    }
  }

}
