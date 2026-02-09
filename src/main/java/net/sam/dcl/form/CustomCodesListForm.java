package net.sam.dcl.form;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class CustomCodesListForm extends BaseForm
{
  protected static Log log = LogFactory.getLog(CustomCodesListForm.class);

  String filter;
  List list = new ArrayList();

  public List getList()
  {
    return list;
  }

  public void setList(List list)
  {
    this.list = list;
  }

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }

  static public class CustomCode
  {
    String cus_id;
    String cus_code;
    String cus_description;
    double cus_percent;
    String cus_block;

    public String getCus_id()
    {
      return cus_id;
    }

    public void setCus_id(String cus_id)
    {
      this.cus_id = cus_id;
    }

    public String getCus_code()
    {
      return cus_code;
    }

    public void setCus_code(String cus_code)
    {
      this.cus_code = cus_code;
    }

    public String getCus_description()
    {
      return cus_description;
    }

    public String getCusDescriptionFormatted()
    {
      String retStr = getCus_description();

      IActionContext context = ActionContext.threadInstance();
      try
      {
        if (!StringUtil.isEmpty(getCus_block()))
        {
          retStr = StrutsUtil.getMessage(context, "CustomCode.blocked") + " " + retStr;
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }

    public void setCus_description(String cus_description)
    {
      this.cus_description = cus_description;
    }

    public double getCus_percent()
    {
      return cus_percent;
    }

    public void setCus_percent(double cus_percent)
    {
      this.cus_percent = cus_percent;
    }

    public String getCus_block()
    {
      return cus_block;
    }

    public void setCus_block(String cus_block)
    {
      this.cus_block = cus_block;
    }

    public String getPercentFormatted()
    {
      String retStr = "";

      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StringUtil.double2appCurrencyString(getCus_percent()) + StrutsUtil.getMessage(context, "Common.percent");
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }
  }
}
