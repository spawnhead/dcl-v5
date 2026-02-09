package net.sam.dcl.taglib.table.colctrl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.table.model.IGridResult;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.message.EMessage;

import javax.servlet.jsp.JspException;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColCheckboxTag extends ColBaseTag {
  String resultProperty;
  String result;
  String property;
  String onclick;

  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColCheckboxTag.class);

  /**
   * Constructor.
   */
  public ColCheckboxTag() {
  }
  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {
      TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
			checkHideOnSelect();
			if (isHideOnSelect()) {
				return SKIP_BODY;
			}
      try {
          IGridResult gridResult;
          if (StringUtil.isEmpty(result)){
              result = tableTag.getResult();
              gridResult = tableTag.getGridResult();
          } else {
              gridResult = (IGridResult) TagUtils.getInstance().lookup(pageContext, tableTag.getName(), result, null);
              if (gridResult == null) {
                StrutsUtil.addError(pageContext,new EMessage("error.table.result",null));
                throw new JspException(StrutsUtil.getMessage(pageContext,"error.table.result"));
              }
          }
          StringBuffer resultStr = new StringBuffer();
          resultStr.append("<td" + tableTag.getTextForScrollableGrid()+TableUtils.getStyleString(this) + " align='" + align + "'");
          if(!StringUtil.isEmpty(width)){
              resultStr.append(" width='"+width+"'");
          }
          resultStr.append(" >");
          if(isShow()){
              //Object bean = TagUtils.getInstance().lookup(this.pageContext, beanName, null);
              resultStr.append("<input type='checkbox' " +
                      "name='" + result + ".row(" + tableTag.getKeyAsString() + ")");
              if (!StringUtil.isEmpty(resultProperty)){
                  resultStr.append("."+getResultProperty());
              }
              resultStr.append("' value='" + tableTag.getKeyAsString() + "' class='grid-checkbox' ");
              if (!isReadOnly() && !StringUtil.isEmpty(onclick)){
                VarStringParser parser = new VarStringParser(new CallbackImpl(tableTag));
                try {
                  onclick = parser.parse(onclick);
                } catch (Exception e) {
                  StrutsUtil.addError(pageContext,new EMessage(e));
                  throw new JspException(e);
                }

                resultStr.append(" onclick='"+onclick+"' ");
              }
              if (StringUtil.isEmpty(property)){
                  Object obj = gridResult.checkRow(tableTag.getKeyAsString());
                  if (!StringUtil.isEmpty(resultProperty)){
                      if (tableTag.getKeyAsString().equals(BeanUtils.getProperty(obj,getResultProperty()))){
                          resultStr.append(" CHECKED ");
                      }
                  } else {
                      if (tableTag.getKeyAsString().equals(obj)){
                          resultStr.append(" CHECKED ");
                      }
                  }
              } else {
                  String val = tableTag.getGridDataProperty(property);
                  if ("true".equalsIgnoreCase(val) || "1".equals(val) ){
                      resultStr.append(" CHECKED ");
                  }
              }
              if(isReadOnly()) resultStr.append("disabled ");
              resultStr.append(">");
          }else{
              resultStr.append("&nbsp;");
          }
          resultStr.append("</td>");
          TagUtils.getInstance().write(pageContext, resultStr.toString());
      } catch (Exception e) {
        StrutsUtil.addError(pageContext,new EMessage(e));
        throw new JspException(e);
      }
      return SKIP_BODY;
  }
  /**
   * Release any acquired resources.
   */
  public void release() {
    super.release();
  }
  public String getResultProperty() {
    return resultProperty;
  }
  public void setResultProperty(String resultProperty) {
    this.resultProperty = resultProperty;
  }
  public String getResult() {
    return result;
  }
  public void setResult(String result) {
    this.result = result;
  }
  public String getProperty() {
    return property;
  }
  public void setProperty(String property) {
    this.property = property;
  }
  public String getOnclick() {
    return onclick;
  }
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  class CallbackImpl implements VarStringParser.Callback {
    TableTag tableTag;
    public CallbackImpl(TableTag tableTag) {
      this.tableTag = tableTag;
    }
    public String process(String var) throws Exception {
      return tableTag.getGridDataProperty(var);
    }
  }

}