package net.sam.dcl.taglib;

import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

import net.sam.dcl.util.StringUtil;

public class WriteTag extends org.apache.struts.taglib.bean.WriteTag
{
  boolean jsFilter = false;

  /**
   * Process the start tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException
  {
    // Look up the requested bean (if necessary)
    if (ignore)
    {
      if (TagUtils.getInstance().lookup(pageContext, name, scope) == null)
      {
        return (SKIP_BODY); // Nothing to output
      }
    }

    // Look up the requested property value
    Object value = TagUtils.getInstance().lookup(pageContext, name, property, scope);

    if (value == null)
    {
      return (SKIP_BODY); // Nothing to output
    }

    // Convert value to the String with some formatting
    String output = formatValue(value);

    // Print this property value to our output writer, suitably filtered
    if (filter)
    {
      TagUtils.getInstance().write(pageContext, TagUtils.getInstance().filter(output));
    }
    else
    {
      if (jsFilter)
      {
        TagUtils.getInstance().write(pageContext, StringUtil.filterForJS(output, true));
      }
      else
      {
        TagUtils.getInstance().write(pageContext, output);
      }
    }

    // Continue processing this page
    return (SKIP_BODY);
  }

  public boolean isJsFilter()
  {
    return jsFilter;
  }

  public void setJsFilter(boolean jsFilter)
  {
    if (jsFilter)
    {
      this.filter = false;
    }
    this.jsFilter = jsFilter;
  }

  public void setFilter(boolean filter)
  {
    if (filter)
    {
      this.jsFilter = false;
    }
    super.setFilter(filter);
  }
}