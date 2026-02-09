package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ContractClosed implements Serializable
{
  String is_new_doc;

  String ctc_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String ctc_date;
  String ctc_number;
  String ctc_block;

  String ctc_id_list;

  List<ClosedRecord> closedRecords = new ArrayList<ClosedRecord>();

  public ContractClosed()
  {
  }

  public ContractClosed(String ctc_id)
  {
    this.ctc_id = ctc_id;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getCtc_id()
  {
    return ctc_id;
  }

  public void setCtc_id(String ctc_id)
  {
    this.ctc_id = ctc_id;
  }

  public String getUsr_date_create()
  {
    return usr_date_create;
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public String getCtc_date()
  {
    return ctc_date;
  }

  public Date getCtcDate() throws ParseException
  {
	  return StringUtil.appDateString2Date(getCtc_date());
  }

  public String getCtc_date_ts()
  {
    return StringUtil.appDateString2dbDateString(getCtc_date());
  }

  public void setCtc_date(String ctc_date)
  {
    this.ctc_date = ctc_date;
  }

  public String getCtc_number()
  {
    return ctc_number;
  }

  public void setCtc_number(String ctc_number)
  {
    this.ctc_number = ctc_number;
  }

  public String getCtc_block()
  {
    return ctc_block;
  }

  public boolean isBlocked()
  {
    return !StringUtil.isEmpty(getCtc_block());  
  }

  public void setCtc_block(String ctc_block)
  {
    this.ctc_block = ctc_block;
  }

  public String getCtc_id_list()
  {
    return ctc_id_list;
  }

  public void setCtc_id_list(String ctc_id_list)
  {
    this.ctc_id_list = ctc_id_list;
  }

  public List<ClosedRecord> getClosedRecords()
  {
    return closedRecords;
  }

  public void setClosedRecords(List<ClosedRecord> closedRecords)
  {
    this.closedRecords = closedRecords;
  }

  public void calculateInString(List lstClosedRecIn)
  {
    List lstClosedRec = getClosedRecords();
    if (null != lstClosedRecIn)
    {
      lstClosedRec = lstClosedRecIn;
    }

    for (int i = 0; i < lstClosedRec.size(); i++)
    {
      ClosedRecord closedRecord = (ClosedRecord) lstClosedRec.get(i);

      closedRecord.setNumber(String.valueOf(i + 1));
    }
  }

  public ClosedRecord getEmptyClosedRecord()
  {
    ClosedRecord closedRecord = new ClosedRecord();
    closedRecord.setNumber("");
    closedRecord.setContractor(new Contractor());
    closedRecord.setContract(new Contract());
    closedRecord.setSpecification(new Specification());

    return closedRecord;
  }

  public void calculate(List lstClosedRecIn)
  {
    List lstClosedRec = getClosedRecords();
    if (null != lstClosedRecIn)
    {
      lstClosedRec = lstClosedRecIn;
    }

    calculateInString(lstClosedRec);
  }

  public void setListParentIds()
  {
    for (ClosedRecord closedRecord : getClosedRecords())
    {
      closedRecord.setCtc_id(getCtc_id());
    }
  }

  public void setListIdsToNull()
  {
    for (ClosedRecord closedRecord : getClosedRecords())
    {
      closedRecord.setLcc_id(null);
    }
  }

  public ClosedRecord findClosedRecord(String number)
  {
    for (ClosedRecord closedRecord : getClosedRecords())
    {
      if (closedRecord.getNumber().equalsIgnoreCase(number))
        return closedRecord;
    }

    return null;
  }

  public void updateClosedRecord(String number, ClosedRecord closedRecordIn)
  {
    for (int i = 0; i < getClosedRecords().size(); i++)
    {
      ClosedRecord closedRecord = getClosedRecords().get(i);

      if (closedRecord.getNumber().equalsIgnoreCase(number))
      {
        getClosedRecords().set(i, closedRecordIn);
        return;
      }
    }
  }

  public void deleteClosedRecord(String number) throws Exception
  {
    for (int i = 0; i < getClosedRecords().size(); i++)
    {
      ClosedRecord closedRecord = getClosedRecords().get(i);

      if (closedRecord.getNumber().equalsIgnoreCase(number))
      {
        getClosedRecords().remove(i);
        break;
      }
    }
  }


  public ClosedRecord findForDelete() throws Exception
  {
    for (ClosedRecord closedRecord : getClosedRecords())
    {
      if (!StringUtil.isEmpty(closedRecord.getSelectedContractId()))
      {
        return closedRecord;
      }
    }

    return null;
  }

}
