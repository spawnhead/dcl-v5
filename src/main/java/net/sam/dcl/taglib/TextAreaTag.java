package net.sam.dcl.taglib;

import javax.servlet.jsp.JspException;

/**
 * User: Dima
 * Date: Feb 22, 2005
 * Time: 2:31:17 PM
 */
public class TextAreaTag extends org.apache.struts.taglib.html.TextareaTag
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

  @Override
  protected String renderTextareaElement() throws JspException
  {
    String ret = ControlCommentHelper.renderInputElement(super.renderTextareaElement(), this, pageContext, getProperty(), showHelp, "text-area-out");
    showHelp = true;
    return ret;
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
