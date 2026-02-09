package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.taglib.table.*;
import net.sam.dcl.taglib.FormTagEx;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.message.EMessage;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;

/**
 * Base class.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColBaseTag extends BodyTagSupport implements Styleable
{
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColBaseTag.class);
  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
          MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

  /**
   * Style.
   */
  String style;
  /**
   * Style class.
   */
  String styleClass;
  /**
   * Style id.
   */
  String styleId;

  /**
   * Checker for editable.
   */
  String readonlyCheckerId;

  String showCheckerId;

  String styleClassCheckerId;

  String align = "left";

  String width;

  boolean readOnlyOnSelectMode = true;

  boolean hideOnSelect = false;

  /**
   * Constructor.
   */
  public ColBaseTag()
  {
  }

  /**
   * Release any acquired resources.
   */

  public void release()
  {
    this.style = null;
    this.styleClass = null;
    this.styleId = null;
    hideOnSelect = false;
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

  /**
   * Get editable checker id.
   * Id for getting checker from request.
   *
   * @return id id
   */
  public String getReadonlyCheckerId()
  {
    return readonlyCheckerId;
  }

  /**
   * Set editable checker id.
   *
   * @param readonlyCheckerId readonlyCheckerId
   */
  public void setReadonlyCheckerId(String readonlyCheckerId)
  {
    this.readonlyCheckerId = readonlyCheckerId;
  }

  public String getStyleClassCheckerId()
  {
    return styleClassCheckerId;
  }

  public void setStyleClassCheckerId(String styleClassCheckerId)
  {
    this.styleClassCheckerId = styleClassCheckerId;
  }

  public String getAlign()
  {
    return align;
  }

  public void setAlign(String align)
  {
    this.align = align;
  }

  public String getWidth()
  {
    return width;
  }

  public void setWidth(String width)
  {
    this.width = width;
  }

  public String getShowCheckerId()
  {
    return showCheckerId;
  }

  public void setShowCheckerId(String showCheckerId)
  {
    this.showCheckerId = showCheckerId;
  }

  /**
   * Get enabled.
   *
   * @return enabled
   */
  public boolean isReadOnly()
  {
    boolean readOnly = false;
    if (SelectFromGridAction.isSelectMode(pageContext.getSession()) && readOnlyOnSelectMode)
    {
      return true;
    }

    IReadOnlyChecker checker = null;
    if (!StringUtil.isEmpty(getReadonlyCheckerId()))
    {
      checker = (IReadOnlyChecker) pageContext.getRequest().getAttribute(getReadonlyCheckerId());
    }
    if (checker != null)
    {
      ReadOnlyCheckerContext context = new ReadOnlyCheckerContext();
      TableTag tag = (TableTag) findAncestorWithClass(this, TableTag.class);
      if (tag != null)
      {
        context.setBean(tag.getGridData().getCurrentRow());
        context.setTable(tag);
        try
        {
          readOnly = checker.check(context);
        }
        catch (Exception e)
        {
        }
      }
    }
    else
    {
      FormTagEx form = (FormTagEx) findAncestorWithClass(this, FormTagEx.class);
      if (form != null)
      {
        readOnly = form.getReadonly();
      }
    }
    return readOnly;
  }

  public boolean isShow()
  {
    IShowChecker checker = null;
    if (!StringUtil.isEmpty(getShowCheckerId()))
    {
      checker = (IShowChecker) pageContext.getRequest().getAttribute(getShowCheckerId());
    }
    ShowCheckerContext context = new ShowCheckerContext();
    TableTag tag = (TableTag) findAncestorWithClass(this, TableTag.class);
    if (checker != null && tag != null)
    {
      context.setBean(tag.getGridData().getCurrentRow());
      context.setTable(tag);
      return checker.check(context);
    }
    return true;
  }

  public String getStyleClassValue()
  {
    IStyleClassChecker checker = null;
    if (!StringUtil.isEmpty(getStyleClassCheckerId()))
    {
      checker = (IStyleClassChecker) pageContext.getRequest().getAttribute(getStyleClassCheckerId());
    }
    TableTag tag = (TableTag) findAncestorWithClass(this, TableTag.class);
    if (checker != null && tag != null)
    {
      StyleClassCheckerContext context = new StyleClassCheckerContext();
      context.setBean(tag.getGridData().getCurrentRow());
      context.setTable(tag);
      return checker.check(context);
    }
    return getStyleClass();
  }

  public boolean isReadOnlyOnSelectMode()
  {
    return readOnlyOnSelectMode;
  }

  public void setReadOnlyOnSelectMode(boolean readOnlyOnSelectMode)
  {
    this.readOnlyOnSelectMode = readOnlyOnSelectMode;
  }

  protected void checkHideOnSelect() throws JspException
  {
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    if (tableTag == null)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.table.not-defined", null));
      throw new JspException(StrutsUtil.getMessage(pageContext, "table.not-defined"));
    }
    hideOnSelect = tableTag.isHideColumnIfSelectMode();
  }

  public boolean isHideOnSelect()
  {
    return hideOnSelect;
  }


}