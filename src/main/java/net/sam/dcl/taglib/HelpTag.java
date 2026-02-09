package net.sam.dcl.taglib;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseFieldTag;

import javax.servlet.jsp.JspException;

public class HelpTag extends BaseFieldTag
{
  private boolean showHelp = true;
  private String htmlName = null;

  /**
   * Construct a new instance of this tag.
   */
  public HelpTag()
  {
    super();
  }

  public int doStartTag() throws JspException
  {
    String ret = "&nbsp;";
    // Print this field to our output writer
    String res = ControlCommentHelper.renderInputElement(ret, this, pageContext, getHtmlName(), showHelp, null);
    TagUtils.getInstance().write(pageContext, res);
    // Continue processing this page
    showHelp = true;

    return (EVAL_BODY_TAG);
  }

  public void release()
  {
    super.release();
  }

  public boolean isShowHelp()
  {
    return showHelp;
  }

  public void setShowHelp(boolean showHelp)
  {
    this.showHelp = showHelp;
  }

  public String getHtmlName()
  {
    return htmlName;
  }

  public void setHtmlName(String htmlName)
  {
    this.htmlName = htmlName;
  }
}