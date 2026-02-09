package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

public class CustomCode implements Serializable
{
  protected static Log log = LogFactory.getLog(CustomCode.class);

  String id;
  String code;
  String law240Flag;
  String block;
  double custom_percent;
  String date;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  public CustomCode()
  {
  }

  public CustomCode(String code)
  {
    this.code = code;
  }

  public CustomCode(String id, String date)
  {
    this.id = id;
    this.date = date;
  }

  public CustomCode(String id, String code, double custom_percent)
  {
    this.id = id;
    this.code = code;
    this.custom_percent = custom_percent;
  }

  public CustomCode(String id, String code, String date)
  {
    this.id = id;
    this.code = code;
    this.date = date;
  }

  public CustomCode(CustomCode customCode)
  {
    id = customCode.getId();
    code = customCode.getCode();
    law240Flag = customCode.getLaw240Flag();
    block = customCode.getBlock();
    date = customCode.getDate();
    custom_percent = customCode.getCustom_percent();

    createUser = new User(customCode.getCreateUser());
    editUser = new User(customCode.getEditUser());
    usr_date_create = customCode.getUsr_date_create();
    usr_date_edit = customCode.getUsr_date_edit();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getLaw240Flag()
  {
    return law240Flag;
  }

  public void setLaw240Flag(String law240Flag)
  {
    this.law240Flag = law240Flag;
  }

  public String getBlock()
  {
    return block;
  }

  public void setBlock(String block)
  {
    this.block = block;
  }

  public double getCustom_percent()
  {
    return custom_percent;
  }

  public String getCustom_percent_formatted()
  {
    if (StringUtil.isEmpty(getId()))
      custom_percent = Double.NaN;
    return StringUtil.double2appCurrencyString(getCustom_percent());
  }

  public void setCustom_percent(double custom_percent)
  {
    this.custom_percent = custom_percent;
  }

  public void setCustom_percent_formatted(String custom_percent_formatted)
  {
    setCustom_percent(StringUtil.appCurrencyString2double(custom_percent_formatted));
  }

  public String getPercentFormatted()
  {
    String retStr = "";

    if (StringUtil.isEmpty(getId()))
      return retStr;

    IActionContext context = ActionContext.threadInstance();
    try
    {
      retStr = getCustom_percent_formatted() + StrutsUtil.getMessage(context, "Common.percent");
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
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
}
