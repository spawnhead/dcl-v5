package net.sam.dcl.taglib;

import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

/**
 * @author: DG
 * Date: Mar 27, 2005
 * Time: 1:32:45 PM
 */
public class UButton extends ULink
{
  protected String textId;
  private boolean showHelp = false;
  private boolean showWait = true;

  public int doStartTag() throws JspException
  {


    return (EVAL_BODY_TAG);
  }

  /*  public int doAfterBody() throws JspException {
    return super.doAfterBody();    //To change body of overridden methods use File | Settings | File Templates.
  }*/
  public int doEndTag() throws JspException
  {
    StringBuffer results = new StringBuffer("<input type=button ");
    results.append(getOnClickAction(showWait));

    if (getReadonly())
    {
      results.append(" disabled=\"true\"");
    }
    if (accesskey != null)
    {
      results.append(" accesskey=\"");
      results.append(accesskey);
      results.append("\"");
    }
    if (tabindex != null)
    {
      results.append(" tabindex=\"");
      results.append(tabindex);
      results.append("\"");
    }
    results.append(prepareStyles());
    results.append(prepareEventHandlers());

    if (textId != null)
    {
      text = StrutsUtil.getMessage(pageContext, textId);
    }
    if (text != null)
      results.append(" value=\"" + text + "\" ");
    results.append(">");

    // Render the remainder to the output stream
    String res = ControlCommentHelper.renderInputElement(results.toString(), this, pageContext, getStyleId(), showHelp, null);
    TagUtils.getInstance().write(pageContext, res);

    //mandatory
    action = null;
    text = null;
    showHelp = false;
    showWait = true;
    // Evaluate the remainder of this page
    return (EVAL_PAGE);
  }

  public void release()
  {
    textId = null;
    super.release();
  }

  public String getTextId()
  {
    return textId;
  }

  public void setTextId(String textId)
  {
    this.textId = textId;
  }

  public boolean isShowHelp()
  {
    return showHelp;
  }

  public void setShowHelp(boolean showHelp)
  {
    this.showHelp = showHelp;
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
