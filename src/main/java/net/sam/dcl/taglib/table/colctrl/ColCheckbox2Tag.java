package net.sam.dcl.taglib.table.colctrl;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;

import javax.servlet.jsp.JspException;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColCheckbox2Tag extends ColBaseTag
{
  String property;
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColCheckbox2Tag.class);

  /**
   * Constructor.
   */
  public ColCheckbox2Tag()
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
    checkHideOnSelect();
    if (isHideOnSelect())
    {
      return SKIP_BODY;
    }
    StringBuffer result = new StringBuffer();
    result.append("<td" + tableTag.getTextForScrollableGrid() + TableUtils.getStyleString(this) + " align='" + align + "' >");
    if (isShow())
    {
      try
      {
        result.append("<input type='checkbox' " +
                "name='" + getProperty() + "' " +
                "value='" + tableTag.getGridDataProperty(property) + "' class='grid-checkbox' ");
        if (isReadOnly()) result.append("disabled ");
        result.append(">");
      }
      catch (Exception e)
      {
        StrutsUtil.addError(pageContext, new EMessage(e));
        throw new JspException(e);
      }
    }
    else
    {
      result.append("&nbsp;");
    }
    result.append("</td>");
    TagUtils.getInstance().write(pageContext, result.toString());
    return SKIP_BODY;

  }

  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
  }

  public String getProperty()
  {
    return property;
  }

  public void setProperty(String property)
  {
    this.property = property;
  }
}