package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActionsForm extends BaseForm
{
  protected static Log log = LogFactory.getLog(ActionsForm.class);

  HolderImplUsingList gridActions = new HolderImplUsingList();

  public HolderImplUsingList getGridActions()
  {
    return gridActions;
  }

  public void setGridActions(HolderImplUsingList gridActions)
  {
    this.gridActions = gridActions;
  }

  static public class Action
  {
    String act_id;
    String act_system_name;
    String act_name;
    String act_logging;
    String act_check_access;

    public String getAct_id()
    {
      return act_id;
    }

    public void setAct_id(String act_id)
    {
      this.act_id = act_id;
    }

    public String getAct_system_name()
    {
      return act_system_name;
    }

    public void setAct_system_name(String act_system_name)
    {
      this.act_system_name = act_system_name;
    }

    public String getAct_name()
    {
      return act_name;
    }

    public void setAct_name(String act_name)
    {
      this.act_name = act_name;
    }

    public String getAct_logging()
    {
      return act_logging;
    }

    public void setAct_logging(String act_logging)
    {
      this.act_logging = act_logging;
    }

    public String getAct_check_access()
    {
      return act_check_access;
    }

    public void setAct_check_access(String act_check_access)
    {
      this.act_check_access = act_check_access;
    }
  }
}