package net.sam.dcl.form;

import net.sam.dcl.beans.ContractorRequestType;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.dao.ContractorRequestDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CurrentWorksForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(CurrentWorksForm.class);

  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

  String crq_id;
  ContractorRequestType requestType = new ContractorRequestType();
  String crq_equipment;

  public String getCrq_id()
  {
    return crq_id;
  }

  public void setCrq_id(String crq_id)
  {
    this.crq_id = crq_id;
  }

  public ContractorRequestType getRequestType()
  {
    return requestType;
  }

  public void setRequestType(ContractorRequestType requestType)
  {
    this.requestType = requestType;
  }

  public String getCrq_equipment()
  {
    return crq_equipment;
  }

  public void setCrq_equipment(String crq_equipment)
  {
    this.crq_equipment = crq_equipment;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class ContractorRequest
  {
    String crq_id;
    String crq_number;
    String crq_create_date;
    String crq_equipment;
    String stf_name;
    String crq_request_type_id;
    String crq_contractor;
    String con_number;
    String con_date;
    String crq_comment;

    String ord_id;
    String ord_number;
    String ord_date;
    String manager;
    double ord_sum;
    String ord_sent_to_prod_date;
    String executed_dates;

    boolean additionalDataReceived = false;
    String spi_send_dates;
    String cost_produces_dates;
    String ship_numbers_dates;

    public String getCrq_id()
    {
      return crq_id;
    }

    public void setCrq_id(String crq_id)
    {
      this.crq_id = crq_id;
    }

    public String getCrq_number()
    {
      return crq_number;
    }

    public void setCrq_number(String crq_number)
    {
      this.crq_number = crq_number;
    }

    public String getCrq_create_date()
    {
      return crq_create_date;
    }

    public String getCrqCreateDateFormatted()
    {
      return StringUtil.dbDateTimeString2appDateTimeString(getCrq_create_date());
    }

    public void setCrq_create_date(String crq_create_date)
    {
      this.crq_create_date = crq_create_date;
    }

    public String getCrq_contractor()
    {
      return crq_contractor;
    }

    public void setCrq_contractor(String crq_contractor)
    {
      this.crq_contractor = crq_contractor;
    }

    public String getCrq_equipment()
    {
      return crq_equipment;
    }

    public void setCrq_equipment(String crq_equipment)
    {
      this.crq_equipment = crq_equipment;
    }

    public String getStf_name()
    {
      return stf_name;
    }

    public void setStf_name(String stf_name)
    {
      this.stf_name = stf_name;
    }

    public String getCrq_request_type_id()
    {
      return crq_request_type_id;
    }

    public String getCrq_request_type()
    {
      return net.sam.dcl.beans.ContractorRequest.getRequestTypeName(crq_request_type_id, true);
    }

    public void setCrq_request_type_id(String crq_request_type_id)
    {
      this.crq_request_type_id = crq_request_type_id;
    }

    public String getCon_number()
    {
      return con_number;
    }

    public void setCon_number(String con_number)
    {
      this.con_number = con_number;
    }

    public String getCon_date()
    {
      return con_date;
    }

    public void setCon_date(String con_date)
    {
      this.con_date = con_date;
    }

    public String getConNumberDateFormatted()
    {
      if (StringUtil.isEmpty(getCon_number()))
        return "";

      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "msg.common.from", getCon_number(), StringUtil.dbDateString2appDateString(getCon_date()));
      }
      catch (Exception e)
      {
        log.error(e);
      }
      return "";
    }

    public String getCrq_comment()
    {
      return crq_comment;
    }

    public boolean isHasComment()
    {
      return !StringUtil.isEmpty(getCrq_comment());
    }

    public void setCrq_comment(String crq_comment)
    {
      this.crq_comment = crq_comment;
    }

    public String getOrd_id()
    {
      return ord_id;
    }

    public void setOrd_id(String ord_id)
    {
      this.ord_id = ord_id;
    }

    public String getOrd_number()
    {
      return ord_number;
    }

    public void setOrd_number(String ord_number)
    {
      this.ord_number = ord_number;
    }

    public String getOrd_date()
    {
      return ord_date;
    }

    public String getOrdDateFormatted()
    {
      return StringUtil.dbDateString2appDateString(getOrd_date());
    }

    public void setOrd_date(String ord_date)
    {
      this.ord_date = ord_date;
    }

    public String getManager()
    {
      return manager;
    }

    public void setManager(String manager)
    {
      this.manager = manager;
    }

    public double getOrd_sum()
    {
      return ord_sum;
    }

    public String getOrdSumFormatted()
    {
      if (StringUtil.isEmpty(getOrd_id()))
        return "";

      return StringUtil.double2appCurrencyString(getOrd_sum());
    }

    public void setOrd_sum(double ord_sum)
    {
      this.ord_sum = ord_sum;
    }

    public String getOrd_sent_to_prod_date()
    {
      return ord_sent_to_prod_date;
    }

    public String getOrdSentToProdDateFormatted()
    {
      return StringUtil.dbDateString2appDateString(getOrd_sent_to_prod_date());
    }

    public void setOrd_sent_to_prod_date(String ord_sent_to_prod_date)
    {
      this.ord_sent_to_prod_date = ord_sent_to_prod_date;
    }

    public String getExecuted_dates()
    {
      return executed_dates;
    }

    public String getExecutedDatesFormatted()
    {
      if (StringUtil.isEmpty(getExecuted_dates()))
        return "";

      return getExecuted_dates().substring(0, getExecuted_dates().length() - 2);
    }

    public void setExecuted_dates(String executed_dates)
    {
      this.executed_dates = executed_dates;
    }

    public boolean isAdditionalDataReceived()
    {
      return additionalDataReceived;
    }

    public void setAdditionalDataReceived(boolean additionalDataReceived)
    {
      this.additionalDataReceived = additionalDataReceived;
    }

    public String getSpi_send_dates()
    {
      return spi_send_dates;
    }

    public String getSpiSendDatesFormatted()
    {
      if (!isAdditionalDataReceived())
        ReceiveAdditionalData();

      if (StringUtil.isEmpty(getSpi_send_dates()))
        return "";

      return getSpi_send_dates().substring(0, getSpi_send_dates().length() - 2);
    }

    public void setSpi_send_dates(String spi_send_dates)
    {
      this.spi_send_dates = spi_send_dates;
    }

    public String getCost_produces_dates()
    {
      return cost_produces_dates;
    }

    public String getCostProducesDatesFormatted()
    {
      if (!isAdditionalDataReceived())
        ReceiveAdditionalData();

      if (StringUtil.isEmpty(getCost_produces_dates()))
        return "";

      String resStr = StringUtil.deleteDoubleValueInString(getCost_produces_dates(), "|");

      return resStr.substring(0, resStr.length() - 2);
    }

    public void setCost_produces_dates(String cost_produces_dates)
    {
      this.cost_produces_dates = cost_produces_dates;
    }

    public String getShip_numbers_dates()
    {
      return ship_numbers_dates;
    }

    public String getShipNumbersDatesFormatted()
    {
      if (!isAdditionalDataReceived())
        ReceiveAdditionalData();

      if (StringUtil.isEmpty(getShip_numbers_dates()))
        return "";

      String resStr = getShip_numbers_dates();

      IActionContext context = ActionContext.threadInstance();
      try
      {
        String from = " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " ";
        resStr = StringUtil.deleteDoubleValueInString(resStr, "|");
        resStr = resStr.replaceAll(Constants.replacementFromString, from);
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return resStr.substring(0, resStr.length() - 2);
    }

    public void setShip_numbers_dates(String ship_numbers_dates)
    {
      this.ship_numbers_dates = ship_numbers_dates;
    }

    public void ReceiveAdditionalData()
    {
      if (StringUtil.isEmpty(getOrd_id()))
        return;

      if (!isAdditionalDataReceived())
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          ContractorRequestDAO.loadAdditionalInformationForCurrentWork(context, this);
        }
        catch (Exception e)
        {
          log.error(e);
        }
        setAdditionalDataReceived(true);
      }
    }
  }

}