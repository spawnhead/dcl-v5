package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletResponse;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColImageTag extends ColButtonTag
{
  String image;
  boolean enableOnClickAction = true;
  private boolean showWait = true;
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColImageTag.class);
  /**
   * Constructor.
   */
  private boolean tmpShow;
  private boolean tmpReadOnly;

  public ColImageTag()
  {
  }

  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException
  {
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    checkHideOnSelect();
    if (isHideOnSelect())
    {
      return SKIP_BODY;
    }

    StringBuilder results = new StringBuilder("<td " + tableTag.getTextForScrollableGrid() + TableUtils.getStyleString(this) + " align='" + align + "'");
    if (!StringUtil.isEmpty(width))
    {
      results.append(" width='").append(width).append("'");
    }
    results.append(">");
    tmpShow = isShow();
    tmpReadOnly = isReadOnly();
    if (tmpShow && !tmpReadOnly)
    {
      results.append("  <image class='grid-image' src='").append(response.encodeURL(image)).append("' tabindex=-1 style='cursor:pointer;'  ");
      if (isEnableOnClickAction())
      {
        results.append(getOnClickAction(showWait));
      }
      if (!StringUtil.isEmpty(getTooltipProperty()))
      {
        try
        {
          results.append(" title='").append(tableTag.getGridDataProperty(getTooltipProperty()).replaceAll("'", "\"")).append("' ");
        }
        catch (Exception e)
        {
          StrutsUtil.addError(pageContext, new EMessage(e));
          throw new JspException(e);
        }
      }
      else
      {
        results.append(" title='").append(StrutsUtil.getMessage(pageContext, getTooltip())).append("' ");
      }
    }
    else
    {
      results.append("&nbsp;");
    }
    TagUtils.getInstance().write(pageContext, results.toString());
    return SKIP_BODY;
  }

  public int doEndTag() throws JspException
  {
    action = null;
    if (!isHideOnSelect())
    {
      TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
      StringBuilder results = new StringBuilder();
      if (tmpShow && !tmpReadOnly)
      {
        results.append(" value=\"");
        if (!StringUtil.isEmpty(property))
        {
          try
          {
            results.append(tableTag.getGridDataProperty(property));
          }
          catch (Exception e)
          {
            StrutsUtil.addError(pageContext, new EMessage(e));
            throw new JspException(e);
          }
        }
        else
        {
          results.append(GetBodyContent());
        }
        results.append("\" >");
      }
      results.append("</td>\n");
      TagUtils.getInstance().write(pageContext, results.toString());
    }
    showWait = true;
    return EVAL_PAGE;
  }


  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
  }

  public String getImage()
  {
    return image;
  }

  public void setImage(String image)
  {
    this.image = image;
  }

  public boolean isEnableOnClickAction()
  {
    return enableOnClickAction;
  }

  public void setEnableOnClickAction(boolean enableOnClickAction)
  {
    this.enableOnClickAction = enableOnClickAction;
  }

  public boolean isShowWait()
  {
    return showWait;
  }

  public void setShowWait(boolean showWait)
  {
    this.showWait = showWait;
  }
}