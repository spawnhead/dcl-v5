package net.sam.dcl.taglib.table;

import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.taglib.table.model.DataHolder;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletResponse;

/**
 * Row.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class RowTag extends BodyTagSupport implements Styleable
{
  protected String imageClose = Config.getString("row-tag.closed-image");
  protected String imageOpen = Config.getString("row-tag.opened-image");

  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(RowTag.class);
  /**
   * Style.
   */
  String style;
  /**
   * Style class.
   */
  String styleClass = "grid-row";
  /**
   * Style id.
   */
  String styleId;

  String styleClassCheckerId;
  String rowParserId;
  String enableSelection = null;
  String selectionCallback = null;
	String enableHighlight = null;
  /**
   * Model for this tag.
   */
  protected DataHolder dataHolder;
  /**
   * Parent tag.
   */
  protected TableTag tableTag;

  protected String currGroup = null;
  protected StringBuffer extraRowAfter = null;
  protected StringBuffer extraRowBefore = null;
  protected int rowParserResult = IRowParser.EVAL_ROW_INCLUDE;
  protected IRowParser rowParser = null;

  protected String curExpandState = "1";

  /**
   * Constructor.
   */
  public RowTag()
  {
  }

  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException
  {
    tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    if (tableTag == null)
    {
      return SKIP_BODY;
    }
    curExpandState = "1";
    dataHolder = tableTag.getGridData();
    extraRowAfter = new StringBuffer();
    extraRowBefore = new StringBuffer();
    TagUtils.getInstance().write(pageContext, "</tr>");
    if (!dataHolder.next()) return SKIP_BODY;
    if (tableTag.isScrollableGrid())
    {
      TagUtils.getInstance().write(pageContext, "" +
              "</table>" +
              "<DIV style=\"width:" + (tableTag.isSettedWidth() ? tableTag.getWidth() : "100%") + ";overflow-y:scroll;height:" + tableTag.getHeight() + ";\">" +
              "<table id='tableBody" + tableTag.getProperty() + "' isGroupBy=" + (StringUtil.isEmpty(
              tableTag.getGroupBy()) ? "0" : "1") + " width='100%' cellSpacing=1 cellPadding=2 border=0 " + TableUtils.getStyleString(
              tableTag) + " >");
    }

    if (!StringUtil.isEmpty(getRowParserId()))
    {
      rowParser = (IRowParser) pageContext.getRequest().getAttribute(getRowParserId());
      try
      {
        if (rowParser != null)
        {
          extraRowAfter = new StringBuffer();
          extraRowBefore = new StringBuffer();
          rowParserResult = rowParser.parse(new RowParserContext(dataHolder.getCurrentRow(), extraRowAfter, extraRowBefore, this));
        }
      }
      catch (Exception e)
      {
        throw new JspException(e);
      }
    }
    pageContext.getRequest().setAttribute("record", dataHolder.getCurrentRow());
    pageContext.getRequest().setAttribute("counter", tableTag.getRecordCounter());
    pageContext.getRequest().setAttribute("lastRecord", !tableTag.getGridData().hasNext());
    return EVAL_BODY_BUFFERED;
  }

  private void unlockRecordIfNeeded() throws JspException
  {
    try
    {
      if (!StringUtil.isEmpty(tableTag.getAutoLockName()))
      {
        LockedRecords.getLockedRecords().unlock(
                new SyncObject(tableTag.getAutoLockName(),
                        tableTag.getKeyAsString(),
                        pageContext.getSession().getId()));
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.url.addkeys", e));
      throw new JspException(StrutsUtil.getMessage(pageContext, "error.url.addkeys"));
    }
  }

  public int doAfterBody() throws JspException
  {
    // Render the output from this iteration to the output stream
    try
    {
      String out = "";
      if (extraRowBefore.length() > 0)
      {
        out += "<tr " + TableUtils.getStyleString(this, tableTag) + " >";
        out += extraRowBefore;
        out += "</tr>";
        tableTag.incrementRecordCount();
      }
      if (rowParserResult != IRowParser.SKIP_ROW)
      {
        if (tableTag.getGroupBy() != null)
        {
          out += showGroup();
        }
        if (bodyContent != null)
        {
          out += "<tr " + TableUtils.getStyleString(this, tableTag);
          if (SelectFromGridAction.isSelectMode(pageContext.getSession()))
          {
            out += " style='cursor: pointer;' onmouseover='__rowOnMouseOver(this)'  onmouseout='__rowOnMouseOut(this)'";
            out += " onclick=\"return __setSelect(this,'" + tableTag.getKeyAsString() + "',0)\" ";
          }
          else if (!StringUtil.isEmpty(enableSelection))
          {
            out += "onmouseover='{if (" + enableSelection + "()) __rowOnMouseOver(this)}'  onmouseout='{if (" + enableSelection + "()) __rowOnMouseOut(this)}'";
            out += " onclick=\"{if (" + enableSelection + "()) " + selectionCallback + "(this,'" + tableTag.getKeyAsString() + "')}\" ";
          }
          else if (!StringUtil.isEmpty(enableHighlight))
          {
            out += "onmouseover='{__rowOnMouseOverHighlight(this)}'  onmouseout='{__rowOnMouseOutHighlight(this)}'";
          }
          out += " >";
          out += bodyContent.getString();
          out += "</tr>";
          tableTag.incrementRecordCount();
        }
      }
      if (bodyContent != null)
      {
        bodyContent.clearBody();
      }
      if (extraRowAfter.length() > 0)
      {
        out += "<tr " + TableUtils.getStyleString(this, tableTag) + " >";
        out += extraRowAfter;
        out += "</tr>";
        tableTag.incrementRecordCount();
      }
      TagUtils.getInstance().writePrevious(pageContext, out);
      tableTag.resetHideColumnCounterIfSelectMode();
      if (rowParserResult == IRowParser.EVAL_ROW_AGAIN ||
              (rowParserResult != IRowParser.EVAL_ROW_AGAIN && dataHolder.next()))
      {
        if (rowParser != null)
        {
          extraRowAfter = new StringBuffer();
          extraRowBefore = new StringBuffer();
          rowParserResult = rowParser.parse(new RowParserContext(dataHolder.getCurrentRow(), extraRowAfter, extraRowBefore, this));
        }
        pageContext.getRequest().setAttribute("record", dataHolder.getCurrentRow());
        pageContext.getRequest().setAttribute("counter", tableTag.getRecordCounter());
        pageContext.getRequest().setAttribute("lastRecord", !tableTag.getGridData().hasNext());
        //unlockRecordIfNeeded();
        return EVAL_BODY_BUFFERED;
      }
      else
      {
        return SKIP_BODY;
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage(e));
      throw new JspException(e);
    }
  }

  public int doEndTag() throws JspException
  {
    currGroup = null;
    curExpandState = "1";
    enableSelection = selectionCallback = enableHighlight = null;
    dataHolder = null;
    return super.doEndTag();
  }


  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
    dataHolder = null;
    tableTag = null;
    style = null;
    styleClass = null;
    styleId = null;
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

  public String getStyleClassCheckerId()
  {
    return styleClassCheckerId;
  }

  public void setStyleClassCheckerId(String styleClassCheckerId)
  {
    this.styleClassCheckerId = styleClassCheckerId;
  }

  public String getRowParserId()
  {
    return rowParserId;
  }

  public void setRowParserId(String rowParserId)
  {
    this.rowParserId = rowParserId;
  }

  public String getImageClose()
  {
    return imageClose;
  }

  public void setImageClose(String imageClose)
  {
    this.imageClose = imageClose;
  }

  public String getSelectionCallback()
  {
    return selectionCallback;
  }

  public void setSelectionCallback(String selectionCallback)
  {
    this.selectionCallback = selectionCallback;
  }

  public String getEnableSelection()
  {
    return enableSelection;
  }

  public void setEnableSelection(String enableSelection)
  {
    this.enableSelection = enableSelection;
  }

	public String getEnableHighlight()
	{
		return enableHighlight;
	}

	public void setEnableHighlight(String enableHighlight)
	{
		this.enableHighlight = enableHighlight;
	}

	protected String showGroup() throws Exception
  {
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    String out = "";
    String val = tableTag.getGridDataProperty(tableTag.getGroupBy());
    if (!val.equals(currGroup))
    {
      currGroup = val;
      String tmpName = "expandclosed_" + tableTag.getProperty() + "_" + tableTag.getKeyAsString();
      out = "<tr expandgroup='" + tmpName + "'><td colspan=100 " + TableUtils.getStyleString(this) + ">";
      if (tableTag.isExpandableGroup())
      {
        if (StringUtil.isEmpty(tableTag.showGroupsExpanded))
        {
          curExpandState = pageContext.getRequest().getParameter(tmpName);
          if (curExpandState == null)
          {
            curExpandState = "1";
          }
        }
        else
        {
          curExpandState = "1".equals(tableTag.showGroupsExpanded) ? "0" : "1";
        }
        out += "<input type=hidden name='" + tmpName + "' value='" + curExpandState + "'>";
        out += "<img name='' src='" + ("1".equals(curExpandState) ? response.encodeURL(imageClose) : response.encodeURL(
                imageOpen)) + "' onclick='{expandGrid(this,\"" + tmpName + "\"); return false;}' style='cursor:hand'/>&nbsp;";
      }
      out += tableTag.getLookUpGroup() != null ? tableTag.getGridDataProperty(tableTag.getLookUpGroup()) : currGroup;
      out += "</td></tr>";
    }
    return out;
  }

  public String getStyleClassValue()
  {
    String addClass = "";
    if ( !SelectFromGridAction.isSelectMode(pageContext.getSession()))
    {
      addClass = "even";
      if (tableTag.getRecordCounter() % 2 != 0)
      {
        addClass = "not-even";
      }
    }

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
      return checker.check(context) + " " + addClass;
    }
    return getStyleClass() + " " + addClass;
  }

}