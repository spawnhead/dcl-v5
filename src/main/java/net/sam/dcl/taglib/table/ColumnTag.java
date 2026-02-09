package net.sam.dcl.taglib.table;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.actions.SelectFromGridAction;

/**
 * Column.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColumnTag extends BodyTagSupport implements Styleable
{
  private String styleClassVertical = "table-header_vertical";
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColumnTag.class);
  /**
   * Width.
   */
  String width;
  /**
   * Title.
   */
  String title;
  /**
   * Align.
   */
  String align = "left";
  /**
   * Style.
   */
  String style;
  /**
   * Style class.
   */
  String styleClass = "table-header";
  /**
   * Style id.
   */
  String styleId;
  /**
   * Constructor.
   */
  String property;
  boolean hideOnSelectMode = false;
  boolean verticalOrientation = false;

  public ColumnTag()
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
    if (tableTag == null)
    {
      return SKIP_BODY;
    }
    tableTag.setHideColumnIfSelectMode(hideOnSelectMode);
    if (SelectFromGridAction.isSelectMode(pageContext.getSession()) && isHideOnSelectMode())
    {
      return SKIP_BODY;
    }

    String out = "";
    out += "<th " + TableUtils.getStyleString(this);
    if (getAlign() != null)
    {
      out += "align=\"" + getAlign() + "\" ";
    }
    if (!StringUtil.isEmpty(getWidth()))
    {
      out += "width=\"" + getWidth() + "\" ";
    }

    if (tableTag.isScrollableGrid())
    {
      out += " id=\"" + tableTag.getProperty() + ".header" + tableTag.getHeaderColumnCounter() + "\" ";
    }
    tableTag.incrementHeaderColumnCounter();

    out += ">";
    if (getTitle() != null)
    {
      out += getTitle();
    }
    out += "</th>\n";
    //TagUtils.getInstance().writePrevious(pageContext, out);
    TagUtils.getInstance().write(pageContext, out);
    title = null;
    hideOnSelectMode = false;
    verticalOrientation = false;
    return EVAL_BODY_BUFFERED;
  }

  /**
   * Should be empty.
   *
   * @return status
   * @throws javax.servlet.jsp.JspException JspException
   */
  public int doAfterBody() throws JspException
  {
    title = null;
    width = null;
    align = null;
    property = null;
    return EVAL_BODY_BUFFERED;
  }

  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
    title = null;
    width = null;
    align = null;
    property = null;
  }

  /**
   * Get title.
   *
   * @return title.
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Get width.
   *
   * @return width
   */
  public String getWidth()
  {
    return width;
  }

  /**
   * Get align.
   *
   * @return align
   */
  public String getAlign()
  {
    return align;
  }

  /**
   * Set width.
   *
   * @param width width
   */
  public void setWidth(String width)
  {
    this.width = width;
  }

  /**
   * Set title.
   *
   * @param title title
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * Set align.
   *
   * @param align align
   */
  public void setAlign(String align)
  {
    this.align = align;
  }

  /**
   * Get style.
   *
   * @return style
   */
  public String getStyle()
  {
    return style;
  }

  /**
   * Set style.
   *
   * @param style style
   */
  public void setStyle(String style)
  {
    this.style = style;
  }

  /**
   * Get style class.
   *
   * @return style class
   */
  public String getStyleClass()
  {
    if ( verticalOrientation )
    {
      return styleClassVertical;
    }
    
    return styleClass;
  }

  /**
   * Get style class.
   *
   * @param styleClass styleClass
   */
  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }

  /**
   * Get style id.
   *
   * @return style id
   */
  public String getStyleId()
  {
    return styleId;
  }

  /**
   * Set style id.
   *
   * @param styleId styleId
   */
  public void setStyleId(String styleId)
  {
    this.styleId = styleId;
  }

  public String getStyleClassValue()
  {
    return getStyleClass();
  }

  public boolean isHideOnSelectMode()
  {
    return hideOnSelectMode;
  }

  public void setHideOnSelectMode(boolean hideOnSelectMode)
  {
    this.hideOnSelectMode = hideOnSelectMode;
  }

  public boolean isVerticalOrientation()
  {
    return verticalOrientation;
  }

  public void setVerticalOrientation(boolean verticalOrientation)
  {
    this.verticalOrientation = verticalOrientation;
  }
}