package net.sam.dcl.taglib;

import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

/**
 * User: Dima
 * Date: Feb 22, 2005
 * Time: 2:31:17 PM
 */
public class CheckboxTag extends org.apache.struts.taglib.html.CheckboxTag
{
  private boolean readOnlySetted = false;
  private boolean showHelp = true;

  public void setReadonly(boolean readonly)
  {
    readOnlySetted = true;
    super.setReadonly(readonly);
  }

  public boolean getReadonly()
  {
    if (readOnlySetted)
    {
      return super.getReadonly();
    }
    FormTagEx form = (FormTagEx) findAncestorWithClass(this, FormTagEx.class);
    if (form != null)
    {
      return form.getReadonly();
    }
    return super.getReadonly();
  }

  public int doStartTag() throws JspException
  {
    // Create an appropriate "input" element based on our parameters
    StringBuffer results = new StringBuffer("<input type=\"checkbox\"");
    results.append(" name=\"");
    if (indexed)
    {
      prepareIndex(results, name);
    }
    results.append(this.property);
    results.append("\"");

    results.append(" id=\"");
    results.append(this.property);
    results.append("\"");

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

    results.append(" value=\"");

    if (value == null)
    {
      results.append("on");
    }
    else
    {
      results.append(value);
    }

    results.append("\"");

    if (this.isChecked())
    {
      results.append(" checked=\"checked\"");
    }
    if (getReadonly())
    {
      results.append(" disabled=\"true\"");
    }
    results.append(prepareEventHandlers());
    results.append(prepareStyles());
    results.append(getElementClose());

    // Print this field to our output writer
    String res = ControlCommentHelper.renderInputElement(results.toString(), this, pageContext, getProperty(), showHelp, null);
    TagUtils.getInstance().write(pageContext, res);

    // Continue processing this page
    this.text = null;
    showHelp = true;
    return (EVAL_BODY_TAG);
  }

  public boolean isShowHelp()
  {
    return showHelp;
  }

  public void setShowHelp(boolean showHelp)
  {
    this.showHelp = showHelp;
  }
}
