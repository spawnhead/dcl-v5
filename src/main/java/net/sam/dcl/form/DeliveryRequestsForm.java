package net.sam.dcl.form;

import net.sam.dcl.beans.Constants;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class DeliveryRequestsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(DeliveryRequestsForm.class);

  public static String inDirection = "in";
  public static String outDirection = "out";

  String direction;

  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

  String dlr_id;
  String dlr_fair_trade;
  String dlr_place_request;
  String unexecuted;
  String annul_exclude;
  String specification_numbers;

  public String getDirection()
  {
    return direction;
  }

  public void setDirection(String direction)
  {
    this.direction = direction;
  }

  public boolean isInDoc()
  {
    return DeliveryRequestsForm.inDirection.equals(direction);
  }

  public boolean isOutDoc()
  {
    return DeliveryRequestsForm.outDirection.equals(direction);
  }

  public String getDlr_id()
  {
    return dlr_id;
  }
	                                                                 
  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public String getDlr_fair_trade()
  {
    return dlr_fair_trade;
  }

  public void setDlr_fair_trade(String dlr_fair_trade)
  {
    this.dlr_fair_trade = dlr_fair_trade;
  }

  public short getDlr_fair_trade_short()
  {
    if ( StringUtil.isEmpty(dlr_fair_trade) )
    {
      return 0;
    }

    return Short.valueOf(dlr_fair_trade);
  }

  public String getDlr_place_request()
  {
    return dlr_place_request;
  }

  public void setDlr_place_request(String dlr_place_request)
  {
    this.dlr_place_request = dlr_place_request;
  }

  public String getUnexecuted()
  {
    return unexecuted;
  }

  public void setUnexecuted(String unexecuted)
  {
    this.unexecuted = unexecuted;
  }

  public String getAnnul_exclude()
  {
    return annul_exclude;
  }

  public void setAnnul_exclude(String annul_exclude)
  {
    this.annul_exclude = annul_exclude;
  }

  public String getSpecification_numbers()
  {
    return specification_numbers;
  }

  public void setSpecification_numbers(String specification_numbers)
  {
    this.specification_numbers = specification_numbers;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class DeliveryRequest
  {
    String dlr_id;
    String dlr_number;
    String dlr_date;
    String dlr_fair_trade;
    String dlr_place_request;
    String dlr_user;
    String specification_numbers;
    String customers;
    String contracts;
    String orders;
    String dlr_annul;
    String dlr_minsk;

    String usr_id;
    String dep_id;

    int produce_count;

    public String getDlr_id()
    {
      return dlr_id;
    }

    public void setDlr_id(String dlr_id)
    {
      this.dlr_id = dlr_id;
    }

    public String getDlr_number()
    {
      return dlr_number;
    }

    public void setDlr_number(String dlr_number)
    {
      this.dlr_number = dlr_number;
    }

    public String getDlr_date()
    {
      return dlr_date;
    }

    public String getDlr_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(dlr_date);
    }

    public void setDlr_date(String dlr_date)
    {
      this.dlr_date = dlr_date;
    }

    public String getDlr_fair_trade()
    {
      return dlr_fair_trade;
    }

    public void setDlr_fair_trade(String dlr_fair_trade)
    {
      this.dlr_fair_trade = dlr_fair_trade;
    }

    public String getDlr_place_request()
    {
      return dlr_place_request;
    }

    public void setDlr_place_request(String dlr_place_request)
    {
      this.dlr_place_request = dlr_place_request;
    }

    public String getDlr_user()
    {
      return dlr_user;
    }

    public void setDlr_user(String dlr_user)
    {
      this.dlr_user = dlr_user;
    }

    public String getSpecification_numbers()
    {
      return specification_numbers;
    }

    public void setSpecification_numbers(String specification_numbers)
    {
      this.specification_numbers = specification_numbers;
    }

    public String getCustomers()
    {
      return customers;
    }

    public void setCustomers(String customers)
    {
      this.customers = customers;
    }

    public String getContracts()
    {
      String resStr = contracts;
      IActionContext context = ActionContext.threadInstance();
      try
      {
        String from = " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " ";
        resStr = resStr.replaceAll(Constants.replacementFromString, from);
      }
      catch (Exception e)
      {
        log.error(e);
      }
      return resStr;
    }

    public void setContracts(String contracts)
    {
      this.contracts = contracts;
    }

    public String getOrders()
    {
      String resStr = orders;
      IActionContext context = ActionContext.threadInstance();
      try
      {
        String from = " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " ";
        resStr = resStr.replaceAll(Constants.replacementFromString, from);
      }
      catch (Exception e)
      {
        log.error(e);
      }
      return resStr;
    }

    public void setOrders(String orders)
    {
      this.orders = orders;
    }

    public String getDlr_annul()
    {
      return dlr_annul;
    }

    public void setDlr_annul(String dlr_annul)
    {
      this.dlr_annul = dlr_annul;
    }

    public String getDlr_minsk()
    {
      return dlr_minsk;
    }

    public void setDlr_minsk(String dlr_minsk)
    {
      this.dlr_minsk = dlr_minsk;
    }

    public String getUsr_id()
    {
      return usr_id;
    }

    public void setUsr_id(String usr_id)
    {
      this.usr_id = usr_id;
    }

    public String getDep_id()
    {
      return dep_id;
    }

    public void setDep_id(String dep_id)
    {
      this.dep_id = dep_id;
    }

    public int getProduce_count()
    {
      return produce_count;
    }

    public void setProduce_count(int produce_count)
    {
      this.produce_count = produce_count;
    }

    public String getMsg_check_block()
    {
      if ("1".equals(dlr_place_request))
        return "ask_unblock";
      else
        return "ask_block";
    }

    public String getFairTradeFormatted()
    {
      if ( "1".equals(dlr_fair_trade) )
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          String retStr = StrutsUtil.getMessage(context, "DeliveryRequests.dlr_fair_trade");
          //0 - to Minsk, 1 - from Minsk
          if ( "1".equals(dlr_minsk) )
          {
            retStr += " " + StrutsUtil.getMessage(context, "DeliveryRequests.dlrFromMinsk");
          }
          return retStr;
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      return "";
    }

    public boolean isPlaced()
    {
      return !StringUtil.isEmpty(getDlr_place_request());
    }
  }

}
