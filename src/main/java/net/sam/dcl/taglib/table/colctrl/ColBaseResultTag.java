package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.model.IGridResult;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

/**
 * @author: DG
 * Date: 09.04.2006
 * Time: 12:03:06
 */
public class ColBaseResultTag extends ColBaseTag
{
  String resultProperty;
  String result;
  String property;
  boolean useIndexAsPK = false;

  String onchange = null;
  String onfocus = null;
  String onblur = null;

  public String getResultProperty()
  {
    return resultProperty;
  }

  public void setResultProperty(String resultProperty)
  {
    this.resultProperty = resultProperty;
  }

  public String getResult()
  {
    return result;
  }

  public void setResult(String result)
  {
    this.result = result;
  }

  public String getProperty()
  {
    return property;
  }

  public void setProperty(String property)
  {
    this.property = property;
  }

  public boolean isUseIndexAsPK()
  {
    return useIndexAsPK;
  }

  public void setUseIndexAsPK(boolean useIndexAsPK)
  {
    this.useIndexAsPK = useIndexAsPK;
  }

  protected String getKey(TableTag tableTag) throws Exception
  {
    if (useIndexAsPK)
    {
      return String.valueOf(tableTag.getRecordCounter() - 1);
    }
    else
    {
      return tableTag.getKeyAsString();
    }
  }

  public String getOnchange()
  {
    return onchange;
  }

  public void setOnchange(String onchange)
  {
    this.onchange = onchange;
  }

  public String getOnfocus()
  {
    return onfocus;
  }

  public void setOnfocus(String onfocus)
  {
    this.onfocus = onfocus;
  }

  public String getOnblur()
  {
    return onblur;
  }

  public void setOnblur(String onblur)
  {
    this.onblur = onblur;
  }

  protected Object calcValue(IGridResult gridResult, TableTag tableTag) throws Exception
  {
    Object value;
    if (StringUtil.isEmpty(property))
    {
      Object obj = gridResult.checkRow(getKey(tableTag));
      if (!StringUtil.isEmpty(resultProperty))
      {
        value = BeanUtils.getProperty(obj, getResultProperty());
      }
      else
      {
        value = obj;
      }
    }
    else
    {
      value = tableTag.getGridDataProperty(property);
    }
    return value == null ? "" : value;
  }

  protected IGridResult getGridResult(TableTag tableTag) throws JspException
  {
    IGridResult gridResult;
    if (StringUtil.isEmpty(result))
    {
      result = tableTag.getResult();
      gridResult = tableTag.getGridResult();
    }
    else
    {
      gridResult = (IGridResult) TagUtils.getInstance().lookup(pageContext, tableTag.getName(), result, null);
      if (gridResult == null)
      {
        StrutsUtil.addError(pageContext, new EMessage("error.table.result", null));
        throw new JspException(StrutsUtil.getMessage(pageContext, "error.table.result"));
      }
    }
    return gridResult;
  }

  protected String calcNameForTag(TableTag tableTag) throws Exception
  {
    String res;
    if (!StringUtil.isEmpty(result))
    {
      res = result + ".row(" + getKey(tableTag) + ")";
      if (!StringUtil.isEmpty(resultProperty))
      {
        res += "." + getResultProperty();
      }
    }
    else
    {
      res = property + "-" + getKey(tableTag);
    }
    return res;
  }

}
