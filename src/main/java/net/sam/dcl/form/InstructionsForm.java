package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.InstructionType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InstructionsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(InstructionsForm.class);
  String showActive;
  InstructionType type = new InstructionType();
  String ins_concerning;

  PageableHolderImplUsingList gridInstructions = new PageableHolderImplUsingList();

  public String getShowActive()
  {
    return showActive;
  }

  public void setShowActive(String showActive)
  {
    this.showActive = showActive;
  }

  public InstructionType getType()
  {
    return type;
  }

  public void setType(InstructionType type)
  {
    this.type = type;
  }

  public String getIns_concerning()
  {
    return ins_concerning;
  }

  public void setIns_concerning(String ins_concerning)
  {
    this.ins_concerning = ins_concerning;
  }

  public PageableHolderImplUsingList getGridInstructions()
  {
    return gridInstructions;
  }

  public void setGridInstructions(PageableHolderImplUsingList gridInstructions)
  {
    this.gridInstructions = gridInstructions;
  }

  static public class Instruction
  {
    String ins_id;
    String ins_type;
    String ins_number;
    String ins_date_sign;
    String ins_date_from;
    String ins_date_to;
    String ins_concerning;
    String ins_inactive;
    int attach_idx;

    public String getIns_id()
    {
      return ins_id;
    }

    public void setIns_id(String ins_id)
    {
      this.ins_id = ins_id;
    }

    public String getIns_type()
    {
      return ins_type;
    }

    public void setIns_type(String ins_type)
    {
      this.ins_type = ins_type;
    }

    public String getIns_number()
    {
      return ins_number;
    }

    public void setIns_number(String ins_number)
    {
      this.ins_number = ins_number;
    }

    public String getIns_date_sign()
    {
      return ins_date_sign;
    }

    public String getIns_date_sign_formatted()
    {
      return StringUtil.dbDateString2appDateString(ins_date_sign);
    }

    public void setIns_date_sign(String ins_date_sign)
    {
      this.ins_date_sign = ins_date_sign;
    }

    public String getIns_date_from()
    {
      return ins_date_from;
    }

    public String getIns_date_from_formatted()
    {
      return StringUtil.dbDateString2appDateString(ins_date_from);
    }

    public void setIns_date_from(String ins_date_from)
    {
      this.ins_date_from = ins_date_from;
    }

    public String getIns_date_to()
    {
      return ins_date_to;
    }

    public String getIns_date_to_formatted()
    {
      return StringUtil.dbDateString2appDateString(ins_date_to);
    }

    public void setIns_date_to(String ins_date_to)
    {
      this.ins_date_to = ins_date_to;
    }

    public String getIns_concerning()
    {
      return ins_concerning;
    }

    public void setIns_concerning(String ins_concerning)
    {
      this.ins_concerning = ins_concerning;
    }

    public String getIns_inactive()
    {
      return ins_inactive;
    }

    public void setIns_inactive(String ins_inactive)
    {
      this.ins_inactive = ins_inactive;
    }

    public int getAttach_idx()
    {
      return attach_idx;
    }

    public void setAttach_idx(int attach_idx)
    {
      this.attach_idx = attach_idx;
    }
  }

}