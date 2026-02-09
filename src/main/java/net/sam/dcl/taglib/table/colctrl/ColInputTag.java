package net.sam.dcl.taglib.table.colctrl;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.table.model.IGridResult;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;

import javax.servlet.jsp.JspException;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColInputTag extends ColBaseResultTag
{
  private String textAllign = "left";

  static Logger LOGGER = Logger.getLogger(ColInputTag.class);

  /**
   * Constructor.
   */
  public ColInputTag()
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
      return EVAL_BODY_BUFFERED;
    }
    try
    {
      IGridResult gridResult = getGridResult(tableTag);
      results.append("<input type='text' ");
      results.append(" style='text-align:").append(textAllign).append("' ");
      String name = calcNameForTag(tableTag);
      if (!StringUtil.isEmpty(name))
      {
        results.append("name='").append(name).append("' ");
      }
      Object value = calcValue(gridResult, tableTag);
      results.append(" value='").append(TagUtils.getInstance().filter(value.toString())).append("' class='grid-input' ");
      if (isReadOnly()) results.append("disabled ");
      if (!StringUtil.isEmpty(getOnchange()))
      {
        results.append(" onchange='").append(getOnchange()).append("(this,\"").append(getKey(tableTag)).append("\")' ");
      }
      if (!StringUtil.isEmpty(getOnfocus()))
      {
        results.append(" onfocus='").append(getOnfocus()).append("(this,\"").append(getKey(tableTag)).append("\")' ");
      }
      if (!StringUtil.isEmpty(getOnblur()))
      {
        results.append(" onblur='").append(getOnblur()).append("(this,\"").append(getKey(tableTag)).append("\")' ");
      }
      results.append(">");
      results.append("</td>");
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage(e));
      throw new JspException(e);
    }
    TagUtils.getInstance().write(pageContext, results.toString());
    return SKIP_BODY;
  }

  /**
   * Release any acquired resources.
   */
  public void release()
  {
    textAllign = "left";
    super.release();
  }

  public String getTextAllign()
  {
    return textAllign;
  }

  public void setTextAllign(String textAllign)
  {
    this.textAllign = textAllign;
  }
}