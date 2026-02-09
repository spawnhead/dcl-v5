package net.sam.dcl.form;

import net.sam.dcl.beans.User;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ContractorsForm extends JournalForm
{
  String ctr_id_journal;
  String ctr_name_journal;
  String ctr_full_name_journal;
  String ctr_address_journal;
  String ctr_account_journal;
  String ctr_email_journal;
  String ctr_unp_journal;
  String pageSize;
  String pageSizePlusOne;
  String offset;
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();
  List<Contractor> gridExel = new ArrayList<Contractor>();

  List<String> selectedIds = new ArrayList<String>();

  String print;
  boolean needPrint;

  Integer mergeTargetId;

  String printScale = "100";
  
  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    resetSelectedIds();
    super.reset(mapping, request);
  }

  public ContractorsForm()
  {
    resetSelectedIds();
  }

  public String getCtr_id_journal()
  {
    return ctr_id_journal;
  }

  public void setCtr_id_journal(String ctr_id_journal)
  {
    this.ctr_id_journal = ctr_id_journal;
  }

  public String getCtr_name_journal()
  {
    return ctr_name_journal;
  }

  public void setCtr_name_journal(String ctr_name_journal)
  {
    this.ctr_name_journal = ctr_name_journal;
  }

  public String getCtr_full_name_journal()
  {
    return ctr_full_name_journal;
  }

  public void setCtr_full_name_journal(String ctr_full_name_journal)
  {
    this.ctr_full_name_journal = ctr_full_name_journal;
  }

  public String getCtr_address_journal()
  {
    return ctr_address_journal;
  }

  public void setCtr_address_journal(String ctr_address_journal)
  {
    this.ctr_address_journal = ctr_address_journal;
  }

  public String getCtr_account_journal()
  {
    return ctr_account_journal;
  }

  public void setCtr_account_journal(String ctr_account_journal)
  {
    this.ctr_account_journal = ctr_account_journal;
  }

  public String getCtr_email_journal()
  {
    return ctr_email_journal;
  }

  public void setCtr_email_journal(String ctr_email_journal)
  {
    this.ctr_email_journal = ctr_email_journal;
  }

  public String getCtr_unp_journal()
  {
    return ctr_unp_journal;
  }

  public void setCtr_unp_journal(String ctr_unp_journal)
  {
    this.ctr_unp_journal = ctr_unp_journal;
  }

  public String getAdmin()
  {
    IActionContext context = ActionContext.threadInstance();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());

    if (currentUser.isAdmin())
    {
      return "1";
    }
    return "";
  }

  public String getAdminOrOtherUserInMinsk()
  {
    IActionContext context = ActionContext.threadInstance();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());

    if (currentUser.isAdmin() || currentUser.isOtherUserInMinsk())
    {
      return "1";
    }
    return "";
  }

  /** Параметры пагинации для SQL (заполняются в экшене/биндинге). */
  public String getPageSize()
  {
    return pageSize;
  }

  public void setPageSize(String pageSize)
  {
    this.pageSize = pageSize;
  }

  public String getPageSizePlusOne()
  {
    return pageSizePlusOne;
  }

  public void setPageSizePlusOne(String pageSizePlusOne)
  {
    this.pageSizePlusOne = pageSizePlusOne;
  }

  public String getOffset()
  {
    return offset;
  }

  public void setOffset(String offset)
  {
    this.offset = offset;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public List<Contractor> getGridExel()
  {
    return gridExel;
  }

  public void setGridExel(List<Contractor> gridExel)
  {
    this.gridExel = gridExel;
  }

  public List<String> getSelectedIds()
  {
    return selectedIds;
  }

  public void setSelectedIds(List<String> selectedIds)
  {
    this.selectedIds = selectedIds;
  }

  public String getPrint()
  {
    return print;
  }

  public void setPrint(String print)
  {
    this.print = print;
  }

  public boolean isNeedPrint()
  {
    return needPrint;
  }

  public void setNeedPrint(boolean needPrint)
  {
    this.needPrint = needPrint;
  }

  public void recalculateSelectedIds()
  {
    for (int i = 0; i < getGrid().getDataList().size(); i++)
    {
      ContractorsForm.Contractor contractor = (ContractorsForm.Contractor) getGrid().getDataList().get(i);
      if (!StringUtil.isEmpty(contractor.getSelectedItem()) && !isInSelectedList(contractor.getCtr_id()))
        getSelectedIds().add(contractor.getCtr_id());
      if (StringUtil.isEmpty(contractor.getSelectedItem()) && isInSelectedList(contractor.getCtr_id()))
        getSelectedIds().remove(contractor.getCtr_id());
    }
  }

  public boolean isInSelectedList(String checkId)
  {
    for (String id : getSelectedIds())
    {
      if (checkId.equals(id))
        return true;
    }

    return false;
  }

  public Integer getMergeTargetId()
  {
    return mergeTargetId;
  }

  public void setMergeTargetId(Integer mergeTargetId)
  {
    this.mergeTargetId = mergeTargetId;
  }

  public List<String> getContractorsSelectedIds() throws Exception
  {
    List<String> contractorsSelectedIds = new ArrayList<String>();
    for (int i = 0; i < getGrid().getDataList().size(); i++)
    {
      ContractorsForm.Contractor contractor = (ContractorsForm.Contractor) getGrid().getDataList().get(i);
      if (!StringUtil.isEmpty(contractor.getSelectedItem()))
        contractorsSelectedIds.add(contractor.getCtr_id());
    }
    return contractorsSelectedIds;
  }

  public void resetSelectedIds()
  {
    for (int i = 0; i < getGrid().getDataList().size(); i++)
    {
      ContractorsForm.Contractor contractor = (ContractorsForm.Contractor) getGrid().getDataList().get(i);
      contractor.setSelectedItem("");
    }
  }

  public String getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(String printScale)
  {
    this.printScale = printScale;
  }

  static public class Contractor
  {
    String ctr_id;
    String ctr_name;
    String ctr_full_name;
    String ctr_index;
    String ctr_region;
    String ctr_place;
    String ctr_street;
    String ctr_building;
    String ctr_add_info;
    String ctr_phone;
    String ctr_fax;
    String ctr_email;
    String ctr_bank_props;
    String ctr_block;
    String ctr_occupied;

    String selectedItem;

    public String getCtr_id()
    {
      return ctr_id;
    }

    public void setCtr_id(String ctr_id)
    {
      this.ctr_id = ctr_id;
    }

    public String getCtr_name()
    {
      return ctr_name;
    }

    public void setCtr_name(String ctr_name)
    {
      this.ctr_name = ctr_name;
    }

    public String getCtr_full_name()
    {
      return ctr_full_name;
    }

    public void setCtr_full_name(String ctr_full_name)
    {
      this.ctr_full_name = ctr_full_name;
    }

    public String getCtr_address()
    {
      String retStr = "";
      retStr += StringUtil.isEmpty(getCtr_index()) ? "" : getCtr_index() + ", ";
      retStr += StringUtil.isEmpty(getCtr_region()) ? "" : getCtr_region() + ", ";
      retStr += StringUtil.isEmpty(getCtr_place()) ? "" : getCtr_place() + ", ";
      retStr += StringUtil.isEmpty(getCtr_street()) ? "" : getCtr_street() + ", ";
      retStr += StringUtil.isEmpty(getCtr_building()) ? "" : getCtr_building() + ", ";
      retStr += StringUtil.isEmpty(getCtr_add_info()) ? "" : getCtr_add_info();
      if (StringUtil.isEmpty(getCtr_add_info()) && !StringUtil.isEmpty(retStr))
      {
        retStr = retStr.substring(0, retStr.length() - 2);  
      }
      return retStr;
    }

    public String getCtr_index()
    {
      return ctr_index;
    }

    public void setCtr_index(String ctr_index)
    {
      this.ctr_index = ctr_index;
    }

    public String getCtr_region()
    {
      return ctr_region;
    }

    public void setCtr_region(String ctr_region)
    {
      this.ctr_region = ctr_region;
    }

    public String getCtr_place()
    {
      return ctr_place;
    }

    public void setCtr_place(String ctr_place)
    {
      this.ctr_place = ctr_place;
    }

    public String getCtr_street()
    {
      return ctr_street;
    }

    public void setCtr_street(String ctr_street)
    {
      this.ctr_street = ctr_street;
    }

    public String getCtr_building()
    {
      return ctr_building;
    }

    public void setCtr_building(String ctr_building)
    {
      this.ctr_building = ctr_building;
    }

    public String getCtr_add_info()
    {
      return ctr_add_info;
    }

    public void setCtr_add_info(String ctr_add_info)
    {
      this.ctr_add_info = ctr_add_info;
    }

    public String getCtr_phone()
    {
      return ctr_phone;
    }

    public void setCtr_phone(String ctr_phone)
    {
      this.ctr_phone = ctr_phone;
    }

    public String getCtr_fax()
    {
      return ctr_fax;
    }

    public void setCtr_fax(String ctr_fax)
    {
      this.ctr_fax = ctr_fax;
    }

    public String getCtr_email()
    {
      return ctr_email;
    }

    public void setCtr_email(String ctr_email)
    {
      this.ctr_email = ctr_email;
    }

    public String getCtr_bank_props()
    {
      return ctr_bank_props;
    }

    public void setCtr_bank_props(String ctr_bank_props)
    {
      this.ctr_bank_props = ctr_bank_props;
    }

    public String getCtr_block()
    {
      return ctr_block;
    }

    public void setCtr_block(String ctr_block)
    {
      this.ctr_block = ctr_block;
    }

    public String getCtr_occupied()
    {
      return ctr_occupied;
    }

    public void setCtr_occupied(String ctr_occupied)
    {
      this.ctr_occupied = ctr_occupied;
    }

    public boolean isOccupied()
    {
      return !(StringUtil.isEmpty(getCtr_occupied()));
    }

    public String getSelectedItem()
    {
      return selectedItem;
    }

    public void setSelectedItem(String selectedItem)
    {
      this.selectedItem = selectedItem;
    }

    public String getMsg_check_block()
    {
      if ("1".equals(ctr_block))
        return "ask_unblock";
      else
        return "ask_block";
    }
  }
}
