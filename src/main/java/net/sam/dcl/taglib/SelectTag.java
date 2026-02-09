package net.sam.dcl.taglib;

import javax.servlet.jsp.JspException;

/**
 * User: Dima
 * Date: Jan 26, 2005
 * Time: 4:52:54 PM
 */
public class SelectTag extends org.apache.struts.taglib.html.SelectTag
{
  private String htmlName = null;

  /**
   * Create an appropriate select start element based on our parameters.
   *
   * @throws javax.servlet.jsp.JspException
   * @since Struts 1.1
   */
  protected String renderSelectStartElement() throws JspException
  {
    StringBuffer results = new StringBuffer("<select");

    results.append(" name=\"");
    // * @since Struts 1.1
    if (this.indexed)
    {
      prepareIndex(results, name);
    }
    String nameForHtml = property;
    if (htmlName != null && htmlName.length() != 0)
    {
      nameForHtml = htmlName;
    }
    results.append(nameForHtml);
    results.append("\"");
    if (accesskey != null)
    {
      results.append(" accesskey=\"");
      results.append(accesskey);
      results.append("\"");
    }
    if (multiple != null)
    {
      results.append(" multiple=\"multiple\"");
    }
    if (size != null)
    {
      results.append(" size=\"");
      results.append(size);
      results.append("\"");
    }
    if (tabindex != null)
    {
      results.append(" tabindex=\"");
      results.append(tabindex);
      results.append("\"");
    }
    if (getReadonly()) results.append(" disabled");
    results.append(prepareEventHandlers());
    results.append(prepareStyles());
    results.append(">");

    return results.toString();
  }

  public int doStartTag() throws JspException
  {
    int retVal = super.doStartTag();
    return retVal;
  }

  public void release()
  {
    super.release();
    htmlName = null;
  }

  public String getHtmlName()
  {
    return htmlName;
  }

  public void setHtmlName(String htmlName)
  {
    this.htmlName = htmlName;
  }

  private boolean readOnlySetted = false;

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
}
