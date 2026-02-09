package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ReputationsForm extends BaseForm
{
  String setDefault;
  String rpt_id;
  HolderImplUsingList grid = new HolderImplUsingList();

  public String getSetDefault()
  {
    return setDefault;
  }

  public void setSetDefault(String setDefault)
  {
    this.setDefault = setDefault;
  }

  public String getRpt_id()
  {
    return rpt_id;
  }

  public void setRpt_id(String rpt_id)
  {
    this.rpt_id = rpt_id;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class Reputation
  {
    String rpt_id;
    String rpt_level;
    String rpt_description;
    String rpt_default_in_ctc;


    public String getRpt_id()
    {
      return rpt_id;
    }

    public void setRpt_id(String rpt_id)
    {
      this.rpt_id = rpt_id;
    }

    public String getRpt_level()
    {
      return rpt_level;
    }

    public void setRpt_level(String rpt_level)
    {
      this.rpt_level = rpt_level;
    }

    public String getRpt_description()
    {
      return rpt_description;
    }

    public void setRpt_description(String rpt_description)
    {
      this.rpt_description = rpt_description;
    }

    public String getRpt_default_in_ctc()
    {
      return rpt_default_in_ctc;
    }

    public void setRpt_default_in_ctc(String rpt_default_in_ctc)
    {
      this.rpt_default_in_ctc = rpt_default_in_ctc;
    }
  }
}
