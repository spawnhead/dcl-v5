package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.beans.ContractorRequestType;
import net.sam.dcl.beans.Seller;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContractorRequestsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(ContractorRequestsForm.class);

  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

  String crq_id;
  ContractorRequestType requestType = new ContractorRequestType();
  String crq_ticket_number;
  String crq_equipment;
  String crq_deliver;

  //Добавить чекбокс "Акт не сдан". Если да, то отображать документы, у которых "Акт сдан в экономический отдел"=НЕТ
  String crqNotDeliver;
  String exclude_annul;

  Seller seller = new Seller();

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

  public String getCrq_ticket_number()
  {
    return crq_ticket_number;
  }

  public void setCrq_ticket_number(String crq_ticket_number)
  {
    this.crq_ticket_number = crq_ticket_number;
  }

  public String getCrq_equipment()
  {
    return crq_equipment;
  }

  public void setCrq_equipment(String crq_equipment)
  {
    this.crq_equipment = crq_equipment;
  }

  public String getCrq_deliver()
  {
    return crq_deliver;
  }

  public void setCrq_deliver(String crq_deliver)
  {
    this.crq_deliver = crq_deliver;
  }

  public String getCrqNotDeliver()
  {
    return crqNotDeliver;
  }

  public void setCrqNotDeliver(String crqNotDeliver)
  {
    this.crqNotDeliver = crqNotDeliver;
  }

  public String getExclude_annul()
  {
    return exclude_annul;
  }

  public void setExclude_annul(String exclude_annul)
  {
    this.exclude_annul = exclude_annul;
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
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
    String crq_ticket_number;
    String crq_receive_date;
    String crq_contractor;
    String crq_seller;
    String crq_request_type_id;
    String crq_equipment;
    String crq_serial_number;
    String crq_deliver;
    String crq_annul;
    int attach_idx;
    String crq_comment;

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

    public String getCrq_ticket_number()
    {
      return crq_ticket_number;
    }

    public void setCrq_ticket_number(String crq_ticket_number)
    {
      this.crq_ticket_number = crq_ticket_number;
    }

    public String getCrq_receive_date()
    {
      return crq_receive_date;
    }

    public void setCrq_receive_date(String crq_receive_date)
    {
      this.crq_receive_date = crq_receive_date;
    }

    public String getCrq_receive_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(crq_receive_date);
    }

    public String getCrq_contractor()
    {
      return crq_contractor;
    }

    public void setCrq_contractor(String crq_contractor)
    {
      this.crq_contractor = crq_contractor;
    }

    public String getCrq_seller()
    {
      return crq_seller;
    }

    public void setCrq_seller(String crq_seller)
    {
      this.crq_seller = crq_seller;
    }

    public String getCrq_equipment()
    {
      return crq_equipment;
    }

    public void setCrq_equipment(String crq_equipment)
    {
      this.crq_equipment = crq_equipment;
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

    public String getCrq_serial_number()
    {
      return crq_serial_number;
    }

    public void setCrq_serial_number(String crq_serial_number)
    {
      this.crq_serial_number = crq_serial_number;
    }

    public String getCrq_deliver()
    {
      return crq_deliver;
    }

    public void setCrq_deliver(String crq_deliver)
    {
      this.crq_deliver = crq_deliver;
    }

    public String getCrq_annul()
    {
      return crq_annul;
    }

    public void setCrq_annul(String crq_annul)
    {
      this.crq_annul = crq_annul;
    }

    public int getAttach_idx()
    {
      return attach_idx;
    }

    public void setAttach_idx(int attach_idx)
    {
      this.attach_idx = attach_idx;
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
  }

}