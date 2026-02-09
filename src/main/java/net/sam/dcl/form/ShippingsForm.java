package net.sam.dcl.form;

import net.sam.dcl.beans.Seller;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShippingsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(ShippingsForm.class);
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();
  String shp_id;
  //for filter
  String block;
  // for block/unblock
  String shp_block;

  String closed_closed;
  String closed_open;
  String closed_all;

  Seller seller = new Seller();

  public String getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getBlock()
  {
    return block;
  }

  public void setBlock(String block)
  {
    this.block = block;
  }

  public String getShp_block()
  {
    return shp_block;
  }

  public void setShp_block(String shp_block)
  {
    this.shp_block = shp_block;
  }

  public String getClosed_closed()
  {
    return closed_closed;
  }

  public void setClosed_closed(String closed_closed)
  {
    this.closed_closed = closed_closed;
  }

  public String getClosed_open()
  {
    return closed_open;
  }

  public void setClosed_open(String closed_open)
  {
    this.closed_open = closed_open;
  }

  public String getClosed_all()
  {
    return closed_all;
  }

  public void setClosed_all(String closed_all)
  {
    this.closed_all = closed_all;
  }

  public String getClosed()
  {
    if ("1".equals(getClosed_all()))
    {
      return "0";
    }
    if ("1".equals(getClosed_closed()))
    {
      return "1";
    }
    if ("1".equals(getClosed_open()))
    {
      return "-1";
    }

    return "0";
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
  }

  static public class Shipping
  {
    String shp_id;
    String shp_number;
    String shp_date;
    String shp_date_expiration;
    String shp_contractor;
    String shp_block;
    String shp_currency;
    String shp_summ_plus_nds;
    String usr_id_create;
    int count_day;
    String shp_closed;

    String usr_id_list;
    String dep_id_list;
    String not_show_msg;

    public String getShp_id()
    {
      return shp_id;
    }

    public void setShp_id(String shp_id)
    {
      this.shp_id = shp_id;
    }

    public String getShp_number()
    {
      return shp_number;
    }

    public void setShp_number(String shp_number)
    {
      this.shp_number = shp_number;
    }

    public String getShp_date()
    {
      return shp_date;
    }

    public void setShp_date(String shp_date)
    {
      this.shp_date = shp_date;
    }

    public String getShp_date_date()
    {
      return StringUtil.dbDateString2appDateString(shp_date);
    }

    public String getShp_date_expiration()
    {
      return shp_date_expiration;
    }

    public void setShp_date_expiration(String shp_date_expiration)
    {
      this.shp_date_expiration = shp_date_expiration;
    }

    public String getShp_contractor()
    {
      return shp_contractor;
    }

    public void setShp_contractor(String shp_contractor)
    {
      this.shp_contractor = shp_contractor;
    }

    public String getShp_currency()
    {
      return shp_currency;
    }

    public void setShp_currency(String shp_currency)
    {
      this.shp_currency = shp_currency;
    }

    public String getShp_block()
    {
      return shp_block;
    }

    public String getMsg_check_block()
    {
      if ("1".equals(shp_block))
        return "ask_unblock";
      else
        return "ask_block";
    }

    public void setShp_block(String shp_block)
    {
      this.shp_block = shp_block;
    }

    public String getUsr_id_create()
    {
      return usr_id_create;
    }

    public void setUsr_id_create(String usr_id_create)
    {
      this.usr_id_create = usr_id_create;
    }

    public double getShp_summ_plus_nds()
    {
      return StringUtil.appCurrencyString2double(shp_summ_plus_nds);
    }

    public String getShp_summ_plus_nds_formatted()
    {
      return shp_summ_plus_nds;
    }

    public void setShp_summ_plus_nds_formatted(String shp_summ_plus_nds)
    {
      this.shp_summ_plus_nds = shp_summ_plus_nds;
    }

    public void setShp_summ_plus_nds(double shp_summ_plus_nds)
    {
      this.shp_summ_plus_nds = StringUtil.double2appCurrencyString(shp_summ_plus_nds);
    }

    public int getCount_day()
    {
      return count_day;
    }

    public void setCount_day(int count_day)
    {
      this.count_day = count_day;
    }

    public String getShp_closed()
    {
      return shp_closed;
    }

    public void setShp_closed(String shp_closed)
    {
      this.shp_closed = shp_closed;
    }

    public String getUsr_id_list()
    {
      return usr_id_list;
    }

    public void setUsr_id_list(String usr_id_list)
    {
      this.usr_id_list = usr_id_list;
    }

    public String getDep_id_list()
    {
      return dep_id_list;
    }

    public void setDep_id_list(String dep_id_list)
    {
      this.dep_id_list = dep_id_list;
    }

    public String getNot_show_msg()
    {
      return not_show_msg;
    }

    public void setNot_show_msg(String not_show_msg)
    {
      this.not_show_msg = not_show_msg;
    }

    public String getShp_expiration()
    {
      if ( "1".equals(not_show_msg) )
      {
        return "";
      }

      IActionContext context = ActionContext.threadInstance();

      try
      {
        if ( StringUtil.isEmpty(getShp_date_expiration()) )
        {
          return StrutsUtil.getMessage(context, "Shippings.shp_expiration_type3");
        }

        int expiration = count_day;
        if ( expiration < -3 )
        {
          return "";
        }
        else if ( expiration >= -3 && expiration <= 0 )
        {
          return StrutsUtil.getMessage(context, "Shippings.shp_expiration_type1", Integer.toString(-expiration));
        }
        else
        {
          return StrutsUtil.getMessage(context, "Shippings.shp_expiration_type2", Integer.toString(expiration));
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return "";
    }

  }
}
