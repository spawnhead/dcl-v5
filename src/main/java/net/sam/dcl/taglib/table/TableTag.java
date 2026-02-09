package net.sam.dcl.taglib.table;

import net.sam.dcl.controller.processor.ActionProcessor;
import net.sam.dcl.controller.DispatchActionHelper;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.taglib.table.model.DataHolder;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.model.IGridResult;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.Getable;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.locking.LockedRecords;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.Globals;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Table Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class TableTag extends BodyTagSupport implements Styleable
{
  /**
   * Request attribute.
   */
  public static final String REQUEST_ATTRIBUTE_PAGE = "page";
  /**
   * Resources for
   */
  static ResourceBundle resources;
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(TableTag.class);
  /**
   * Pagable.
   */
  boolean pagable;
  /**
   * Group by.
   */
  String groupBy;

  String lookUpGroup;
  /**
   * Style.
   */
  String style;
  /**
   * Style class.
   */
  String styleClass = "grid";
  /**
   * Style id.
   */
  String styleId;
  /**
   * Property.
   */
  String property;
  /**
   * Property.
   */
  String name;
  /**
   * Key columns.
   */
  String key;
  /**
   * Parsed keys.
   */
  ArrayList keys;

  ArrayList hideColumnIfSelectMode = new ArrayList();
  int hideColumnCounterIfSelectMode = 0;

  boolean scrollableGrid = false;
  String height;
  String width = null;
  String result;

  int recordCounter = 1;
  int headerColumnCounter = 1;
  int rowColumnCounter = 1;

  String autoLockName = null;
  String autoLockReloadFunc = null;

  boolean expandableGroup = false;

  String showGroupsExpanded = null;

  boolean headerOnly = false;
  /**
   * Model for this tag.
   */
  protected DataHolder dataHolder;
  protected IGridResult gridResult;

  private String nothingWasFoundMsg = "msg.nothingWasFound";

  /**
   * Constructor.
   */
  public TableTag()
  {
  }

  void incrementRecordCount()
  {
    recordCounter++;
  }

  public int getRecordCounter()
  {
    return recordCounter;
  }

  public void setRecordCounter(int recordCounter)
  {
    this.recordCounter = recordCounter;
  }

  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException
  {
    unlockRecordsIfNeeded();
    if (isScrollableGrid() && StringUtil.isEmpty(height))
    {
      StrutsUtil.addError(pageContext, new EMessage("error.table.height", null));
      throw new JspException(StrutsUtil.getMessage(pageContext, "error.table.height"));
    }
    recordCounter = 1;
    String out = "";
    out += "<table ";
    if (isScrollableGrid())
    {
      out += " id='tableHeader" + getProperty() + "' ";
    }
    else
    {
      out += " id='tableBody" + getProperty() + "' isGroupBy=" + (StringUtil.isEmpty(getGroupBy()) ? "0" : "1");
    }
    out += " width=" + (isSettedWidth() ? getWidth() : "'100%'") + " cellSpacing=1 cellPadding=2 border=0 " + TableUtils.getStyleString(this) + " >";
    //Get property form Bean.
    if (name == null)
    {
      name = Constants.BEAN_KEY;
    }
    Object res = TagUtils.getInstance().lookup(pageContext, name, property, null);
    if (res == null)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.table.property", null));
      throw new JspException(StrutsUtil.getMessage(pageContext, "error.table.property"));
    }
    dataHolder = (DataHolder) res;
    if (!StringUtil.isEmpty(result))
    {
      res = TagUtils.getInstance().lookup(pageContext, name, result, null);
      if (res == null)
      {
        StrutsUtil.addError(pageContext, new EMessage("error.table.result", null));
        throw new JspException(StrutsUtil.getMessage(pageContext, "error.table.result"));
      }
      gridResult = (IGridResult) res;
    }
    if (dataHolder instanceof PageableDataHolder)
    {
      out += "<input type='hidden' name='" +
              getProperty() + "." + REQUEST_ATTRIBUTE_PAGE + "' " +
              "id='" +
              getProperty() + "." + REQUEST_ATTRIBUTE_PAGE + "' " +
              "value='" + ((PageableDataHolder) dataHolder).getPage() + "'" +
              " />";
    }
    if (!StringUtil.isEmpty(key))
    {
      out += "<input type=hidden name='" + getProperty() + ".pk' value='" + key + "'>";
    }
    out += "<tbody>";
    out += showPagination();
    out += "<tr>";
    //TagUtils.getInstance().writePrevious(pageContext, out);
    TagUtils.getInstance().write(pageContext, out);
    return EVAL_BODY_BUFFERED;
  }

  /**
   * Process the remainder of this page normally.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doEndTag() throws JspException
  {
    String out = getBodyContent().getString();

    if (isHeaderOnly())
    {
      out += "</table>";
    }
    else
    {
      if (recordCounter == 1)
      {
        String nothingWasFoundTranslation = StrutsUtil.getMessage(pageContext, nothingWasFoundMsg);
        if (isScrollableGrid())
        {
          out += "</table>";
          out += "<DIV style=\"width:" + (isSettedWidth() ? getWidth() : "100%") + ";overflow-y:scroll;height:" + getHeight() + ";\">";
          out += "<table width='100%' cellSpacing=1 cellPadding=2 border=0 " + TableUtils.getStyleString(this) + " >";
          out += "<tr ><td colspan=100 class=txt bgcolor=#eeeeee>" + nothingWasFoundTranslation + "</td></tr>";
          out += "</tbody></table>";
          out += "</div>";
        }
        else
        {
          out += "<tr ><td colspan=100 class=txt bgcolor=#eeeeee>" + nothingWasFoundTranslation + "</td></tr>";
          out += "</tbody></table>";
        }
      }
      else
      {
        out += "</tbody></table>";
        if (isScrollableGrid())
        {
          out += "</div>";
        }
      }
    }

    if (isScrollableGrid())
    {
      out += "<script language=\"JScript\" type=\"text/javascript\">\n" +
              "function set" + getProperty() + "HeaderWidths(){\n" +
              "recalcTableColumnWidth2('tableHeader" + getProperty() + "','tableBody" + getProperty() + "');" +
              "}\n";
      if (recordCounter != 1)
      {
        out += "document.getElementById('tableBody" + getProperty() + "').headersSetWidth=set" + getProperty() + "HeaderWidths;\n" +
                "document.getElementById('tableBody" + getProperty() + "').onresize=set" + getProperty() + "HeaderWidths;\n" +
                "initFunctions.push(set" + getProperty() + "HeaderWidths);\n";
      }
      out += "</script>";
    }
    TagUtils.getInstance().write(pageContext, out);
    recordCounter = 1;
    headerColumnCounter = 1;
    rowColumnCounter = 1;
    expandableGroup = false;
    if (!StringUtil.isEmpty(autoLockName))
    {
      ResponseCollectFilter.setNeedResponseCollect((HttpServletRequest) pageContext.getRequest());
    }
    nothingWasFoundMsg = "msg.nothingWasFound";
    showGroupsExpanded = null;
    dataHolder = null;
    gridResult = null;
    hideColumnIfSelectMode = new ArrayList();
    return EVAL_PAGE;
  }

  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
    dataHolder = null;
    recordCounter = 1;
    headerColumnCounter = 1;
    rowColumnCounter = 1;
    expandableGroup = false;
  }

  /**
   * Get grid data.
   *
   * @return grid data.
   */
  public DataHolder getGridData()
  {
    return dataHolder;
  }

  /**
   * Get pagable.
   *
   * @return pagable.
   */
  public boolean getPagable()
  {
    return pagable;
  }

  /**
   * Set pagable.
   *
   * @param pagable pagable
   */
  public void setPagable(boolean pagable)
  {
    this.pagable = pagable;
  }

  /**
   * Get group by.
   *
   * @return group by
   */
  public String getGroupBy()
  {
    return groupBy;
  }

  /**
   * Set group by.
   *
   * @param groupBy group by
   */
  public void setGroupBy(String groupBy)
  {
    this.groupBy = groupBy;
  }

  public String getLookUpGroup()
  {
    return lookUpGroup;
  }

  public void setLookUpGroup(String lookUpGroup)
  {
    this.lookUpGroup = lookUpGroup;
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
   * Get property.
   *
   * @return property
   */
  public String getProperty()
  {
    return property;
  }

  /**
   * set property
   *
   * @param property property
   */
  public void setProperty(String property)
  {
    this.property = property;
  }

  /**
   * Get name.
   *
   * @return name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Set name.
   *
   * @param name name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Show pagination.
   *
   * @throws javax.servlet.jsp.JspException JspException
   */
  public String showPagination() throws JspException
  {
    if (!(dataHolder instanceof PageableDataHolder))
    {
      return "";
    }
    StringBuffer out = new StringBuffer("");
    int currentPage = ((PageableDataHolder) dataHolder).getPage();

    out.append("\n\n<tr>");
    out.append("<td colspan='100' class=paginationTableHeader width='100%'> ");
    out.append("<table width='100%' class=paginationTable cellspacing=0><tr>");
    out.append("<td class=paginationTableFirst>");
    out.append(currentPage);
    out.append("</td>");
    out.append("<td class=paginationTableSecond>");

    boolean enable = false;
    if (currentPage > 1)
    {
      enable = true;
    }
    if (enable)
    {
      out.append("<input type=\"button\"   " +
              "onclick=\"" + getPrevPageAction() + "\" " +
              "value=\"" + StrutsUtil.getMessage(pageContext, "button.prev") + "\" " +
              "  name='" + getProperty() + ".prev' " +
              "/>");
    }
    enable = ((PageableDataHolder) dataHolder).hasNextPage();
    if (enable)
    {
      out.append("&nbsp;&nbsp;&nbsp;");
      out.append("<input type=\"button\" " +
              "onclick=\"" + getNextPageAction() + "\" " +
              "value=\"" + StrutsUtil.getMessage(pageContext, "button.next") + "\" " +
              " name='" + getProperty() + ".next'" +
              "/>");
    }
    out.append("&nbsp;");
    out.append("</td>");
    out.append("</tr>");
    out.append("</table>");
    out.append("</td>");
    out.append("</tr>");
    //}
    return out.toString();
  }

  /**
   * Get action on page click.
   *
   * @return script
   */
  public String getNextPageAction()
  {
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    String dispatch = pageContext.getRequest().getParameter(DispatchActionHelper.defaultParameter);
    ActionMapping mapping = (ActionMapping) pageContext.getRequest().getAttribute(Globals.MAPPING_KEY);
    String url = mapping.getPath();
    url = TagUtils.getInstance().getActionMappingURL(url, pageContext);
    if (!StringUtil.isEmpty(dispatch))
    {
      url = StringUtil.addToURL(url, DispatchActionHelper.defaultParameter, dispatch);
    }
    url = response.encodeURL(url);
    StringBuffer out = new StringBuffer();
    out.append("{var form = getForm(this);\n");
    out.append(" form.action='" + url + "';\n");
    out.append(" form.document.getElementById('" + ActionProcessor.CONTROL + "').value='" + getProperty() + "';");
    out.append(" form.document.getElementById('" + ActionProcessor.ACTION + "').value='" + PageableDataHolder.NEXT_PAGE + "';");
    out.append(" form.submit();}");
    return out.toString();
  }

  public String getPrevPageAction()
  {
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    String dispatch = pageContext.getRequest().getParameter(DispatchActionHelper.defaultParameter);
    ActionMapping mapping = (ActionMapping) pageContext.getRequest().getAttribute(Globals.MAPPING_KEY);
    String url = mapping.getPath();
    url = TagUtils.getInstance().getActionMappingURL(url, pageContext);
    if (!StringUtil.isEmpty(dispatch))
    {
      url = StringUtil.addToURL(url, DispatchActionHelper.defaultParameter, dispatch);
    }
    url = response.encodeURL(url);
    StringBuffer out = new StringBuffer();
    out.append("{var form = getForm(this);\n");
    out.append(" form.action='" + url + "';\n");
    out.append(" form.document.getElementById('" + ActionProcessor.CONTROL + "').value='" + getProperty() + "';");
    out.append(" form.document.getElementById('" + ActionProcessor.ACTION + "').value='" + PageableDataHolder.PREV_PAGE + "';");
    out.append(" form.submit();}");
    return out.toString();
  }

  public String getResult()
  {
    return result;
  }

  public void setResult(String result)
  {
    this.result = result;
  }

  /**
   * Get key columns.
   *
   * @return key
   */
  public String getKey()
  {
    return key;
  }

  /**
   * Get i key column
   *
   * @param i column
   * @return name
   */
  public String getKeyColumn(int i)
  {
    return (String) keys.get(i);
  }

  /**
   * Retunrns column count.
   *
   * @return column count
   */
  public int getKeyColumnCount()
  {
    if (keys == null)
    {
      return 0;
    }
    return keys.size();
  }

  /**
   * Set key columns.
   *
   * @param key key
   */
  public void setKey(String key)
  {
    this.key = key;
    if (key != null && !key.equals(""))
    {
      keys = new ArrayList();
      for (StringTokenizer tokenizer = new StringTokenizer(key, ","); tokenizer.hasMoreTokens();)
      {
        String token = tokenizer.nextToken();
        keys.add(token);
      }
    }
    else
    {
      keys = null;
    }
  }

  public String getKeyAsUrl() throws Exception
  {
    String result = "";
    for (int i = 0; i < keys.size(); i++)
    {
      String keyPropery = (String) keys.get(i);
      result += StringUtil.urlPair(keyPropery, getGridDataProperty(keyPropery));
      if (i != keys.size() - 1)
      {
        result += "&";
      }
    }
    return result;
  }

  public String getKeyAsString() throws Exception
  {
    String result = "";
    for (int i = 0; i < keys.size(); i++)
    {
      String keyPropery = (String) keys.get(i);
      result += getGridDataProperty(keyPropery);
      if (i != keys.size() - 1)
      {
        result += "#";
      }
    }
    return result + "";
  }

  public String getGridDataProperty(String property) throws Exception
  {
    Object obj = dataHolder.getCurrentRow();
    if (obj instanceof Getable)
    {
      return ((Getable) obj).get(property);
    }
    else
    {
      return BeanUtils.getProperty(obj, property);
    }
  }

  public IGridResult getGridResult()
  {
    return gridResult;
  }

  public String getStyleClassValue()
  {
    return getStyleClass();
  }

  public boolean isHeaderOnly()
  {
    return headerOnly;
  }

  public void setHeaderOnly(boolean headerOnly)
  {
    this.headerOnly = headerOnly;
  }

  public boolean isScrollableGrid()
  {
    return scrollableGrid;
  }

  public void setScrollableGrid(boolean scrollableGrid)
  {
    this.scrollableGrid = scrollableGrid;
  }

  public int getHeaderColumnCounter()
  {
    return headerColumnCounter;
  }

  public void incrementHeaderColumnCounter()
  {
    headerColumnCounter++;
  }

  public void setHeaderColumnCounter(int headerColumnCounter)
  {
    this.headerColumnCounter = headerColumnCounter;
  }

  public int getRowColumnCounter()
  {
    return rowColumnCounter;
  }

  public void incrementRowColumnCounter()
  {
    rowColumnCounter++;
  }

  public void setRowColumnCounter(int rowColumnCounter)
  {
    this.rowColumnCounter = rowColumnCounter;
  }

  public String getTextForScrollableGrid()
  {
    String str = "";
    if (isScrollableGrid())
    {
      if (getRowColumnCounter() < getHeaderColumnCounter())
      {
        str = " id='" + getProperty() + ".column" + getRowColumnCounter() + "' ";
        incrementRowColumnCounter();
      }
    }
    return str;
  }

  public String getHeight()
  {
    return height;
  }

  public void setHeight(String height)
  {
    this.height = height;
  }

  public boolean isSettedWidth()
  {
    return width != null;
  }

  public String getWidth()
  {
    return width;
  }

  public void setWidth(String width)
  {
    this.width = width;
  }

  public String getAutoLockName()
  {
    return autoLockName;
  }

  public void setAutoLockName(String autoLockName)
  {
    this.autoLockName = autoLockName;
  }

  public String getAutoLockReloadFunc()
  {
    return autoLockReloadFunc;
  }

  public void setAutoLockReloadFunc(String autoLockReloadFunc)
  {
    this.autoLockReloadFunc = autoLockReloadFunc;
  }

  public boolean isExpandableGroup()
  {
    return expandableGroup;
  }

  public void setExpandableGroup(boolean expandableGroup)
  {
    this.expandableGroup = expandableGroup;
  }

  public void setHideColumnIfSelectMode(boolean hide)
  {
    hideColumnIfSelectMode.add(hide ? "1" : "0");
  }

  public boolean isHideColumnIfSelectMode()
  {
    if (hideColumnCounterIfSelectMode < hideColumnIfSelectMode.size())
    {
      hideColumnCounterIfSelectMode++;
      return SelectFromGridAction.isSelectMode(pageContext.getSession()) && "1".equals(hideColumnIfSelectMode.get(hideColumnCounterIfSelectMode - 1));
    }
    else
    {
      LOGGER.error("Wrong column number for isHideColumnIfSelectMode" + rowColumnCounter + "," + hideColumnIfSelectMode.size());
    }
    return false;
  }

  public void resetHideColumnCounterIfSelectMode()
  {
    hideColumnCounterIfSelectMode = 0;
  }

  private void unlockRecordsIfNeeded() throws JspException
  {
    try
    {
      if (!StringUtil.isEmpty(getAutoLockName()))
      {
        LockedRecords.getLockedRecords().unlockWithTheSame(getAutoLockName(), pageContext.getSession().getId());
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.url.addkeys", e));
      throw new JspException(StrutsUtil.getMessage(pageContext, "error.url.addkeys"));
    }
  }

  public String getNothingWasFoundMsg()
  {
    return nothingWasFoundMsg;
  }

  public void setNothingWasFoundMsg(String nothingWasFoundMsg)
  {
    this.nothingWasFoundMsg = nothingWasFoundMsg;
  }

  public String getShowGroupsExpanded()
  {
    return showGroupsExpanded;
  }

  public void setShowGroupsExpanded(String showGroupsExpanded)
  {
    this.showGroupsExpanded = showGroupsExpanded;
  }
}