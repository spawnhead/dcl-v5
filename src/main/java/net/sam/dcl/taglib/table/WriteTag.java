package net.sam.dcl.taglib.table;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;

/**
 * Column.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class WriteTag extends BodyTagSupport  {
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(WriteTag.class);
  public WriteTag() {
  }
  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    if (tableTag == null) {
      return SKIP_BODY;
    }
    return EVAL_BODY_BUFFERED;
  }

  public int doEndTag() throws JspException {
    //TagUtils.getInstance().writePrevious(pageContext, bodyContent.getString());
    TagUtils.getInstance().write(pageContext, bodyContent.getString());

    return EVAL_PAGE;
  }
}