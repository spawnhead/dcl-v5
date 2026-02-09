package net.sam.dcl.taglib.table.colctrl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.table.model.IGridResult;
import net.sam.dcl.taglib.FormTagEx;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.controller.actions.SelectFromGridAction;

import javax.servlet.jsp.JspException;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColCheckbox3Tag extends ColLinkTag
{
  String resultProperty;
  private boolean showWait = true;
  private boolean hiddenCtrl = false;
  String result;
  boolean useIndexAsPK = false;
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColCheckbox3Tag.class);

  /**
   * Constructor.
   */
  public ColCheckbox3Tag()
  {
  }

  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */

  public int doStartTag() throws JspException
  {
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    FormTagEx formTag = (FormTagEx) findAncestorWithClass(this, FormTagEx.class);
    checkHideOnSelect();
    if (isHideOnSelect())
    {
      return SKIP_BODY;
    }
    StringBuffer results = new StringBuffer("<td " + tableTag.getTextForScrollableGrid() + TableUtils.getStyleString(this) + " align='" + align + "'");
    if (!StringUtil.isEmpty(width))
    {
      results.append(" width='").append(width).append("'");
    }
    results.append(" >");
    if (!isShow())
    {
      results.append("&nbsp;</td>");
      TagUtils.getInstance().write(pageContext, results.toString());
      return SKIP_BODY;
    }
    try
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
      results.append("<input type='checkbox' ");
      if (!StringUtil.isEmpty(result))
      {
        results.append("name='").append(result).append(".row(").append(getKey(tableTag)).append(")");
        if (!StringUtil.isEmpty(resultProperty))
        {
          results.append(".").append(getResultProperty());
        }
        results.append("' ");
      }
      if (!StringUtil.isEmpty(type))
      {
        checkActionFromForm();
        results.append("").append(getOnClickAction(showWait));
      }
      String checkboxStyleClass = "grid-checkbox";
      //только для "полосатых" форм
      if (
           null != formTag && !StringUtil.isEmpty(formTag.getStyleId()) && 
           formTag.getStyleId().equals("striped-form") && !SelectFromGridAction.isSelectMode(pageContext.getSession())
         )
      {
        checkboxStyleClass += (tableTag.getRecordCounter() % 2 != 0) ? "-not-even" : "-even";
      }
      results.append(" value='").append(getKey(tableTag)).append("' class='").append(checkboxStyleClass).append("' ");
      if (StringUtil.isEmpty(property))
      {
        Object obj = gridResult.checkRow(getKey(tableTag));
        if (obj != null)
        {
          if (!StringUtil.isEmpty(resultProperty))
          {
            if (getKey(tableTag).equals(BeanUtils.getProperty(obj, getResultProperty())))
            {
              results.append(" CHECKED ");
            }
          }
          else
          {
            if (getKey(tableTag).equals(obj))
            {
              results.append(" CHECKED ");
            }
          }
        }
      }
      else
      {
        String val = tableTag.getGridDataProperty(property);
        if (!Boolean.FALSE.toString().equals(val) && !StringUtil.isEmpty(val))
        {
          results.append(" CHECKED ");
        }
      }
      if (isReadOnly()) results.append("disabled ");
      if (!StringUtil.isEmpty(getOnclick()))
      {
        results.append(" onclick=\"");
        results.append(getOnclick());
        results.append("\" ");
      }
      results.append(">");

      if ( isHiddenCtrl() )
      {
        String nameCtrl = result + ".row(" + getKey(tableTag) + ")." + getResultProperty().replaceAll("Checkbox", "");
        results.append("<input type='hidden' name='").append(nameCtrl).append("' value='").append(getKey(tableTag)).append("'>");
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage(e));
      throw new JspException(e);
    }
    TagUtils.getInstance().write(pageContext, results.toString());
    return EVAL_BODY_BUFFERED;
  }

  public int doEndTag() throws JspException
  {
    if (!isHideOnSelect())
    {
      StringBuffer result = GetBodyContent();
      result.append("</td>");
      TagUtils.getInstance().write(pageContext, result.toString());
    }
    bodyContent = null;
    showWait = true;
    hiddenCtrl = false;
    return EVAL_PAGE;
  }

  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
  }

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

  public String getOnclick()
  {
    return onclick;
  }

  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }

  public boolean isShowWait()
  {
    return showWait;
  }

  public void setShowWait(boolean showWait)
  {
    this.showWait = showWait;
  }

  public boolean isHiddenCtrl()
  {
    return hiddenCtrl;
  }

  public void setHiddenCtrl(boolean hiddenCtrl)
  {
    this.hiddenCtrl = hiddenCtrl;
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
}