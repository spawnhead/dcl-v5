/*
 * $Header: /home/cvs/jakarta-struts/src/share/org/apache/struts/taglib/html/TextTag.java,v 1.2 2003/03/23 06:08:41 dgraham Exp $
 * $Revision: 1.2 $
 * $Date: 2003/03/23 06:08:41 $
 *
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


package net.sam.dcl.taglib;

import net.sam.dcl.config.Config;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseFieldTag;

import javax.servlet.jsp.JspException;


/**
 * Custom tag for input fields of type "text".
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2003/03/23 06:08:41 $
 */

public class DateTag extends BaseFieldTag
{
  private String css = Config.getString("date-tag.css");
  private String htmlName = null;
  private String afterSelect = "null";
  private String startField = "";
  private String endField = "";
  private boolean onlyHeader = false;
  protected boolean selectOnly = false;
  private boolean showHelp = true;
  private String enterDispatch = "";

  /**
   * Construct a new instance of this tag.
   */
  public DateTag()
  {
    super();
    this.type = "text";
  }

  public int doStartTag() throws JspException
  {
    // Create an appropriate "input" element based on our parameters
    StringBuffer results = new StringBuffer("<input type=\"");
    results.append(type);
    results.append("\" name=\"");
    // * @since Struts 1.1
    if (indexed)
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
    if (accept != null)
    {
      results.append(" accept=\"");
      results.append(accept);
      results.append("\"");
    }
    if (cols != null)
    {
      results.append(" size=\"");
      results.append(cols);
      results.append("\"");
    }
    if (tabindex != null)
    {
      results.append(" tabindex=\"");
      results.append(tabindex);
      results.append("\"");
    }

    if (selectOnly && !getReadonly())
    {
      results.append(" readonly");
    }

    results.append(" value=\"");
    if (value != null)
    {
      results.append(TagUtils.getInstance().filter(value));
    }
    else if (redisplay || !"password".equals(type))
    {
      Object value = TagUtils.getInstance().lookup(pageContext, name, property, null);
      if (value == null)
        value = "";
      results.append(TagUtils.getInstance().filter(value.toString()));
    }

    results.append("\"");

    String enterPressStr = net.sam.dcl.taglib.TagUtils.getEnterPressStr(getStyleClass(), getEnterDispatch());
    if ( !StringUtil.isEmpty(enterPressStr) )
    {
      results.append(" onkeydown=\"");
      results.append(enterPressStr);
      results.append("\"");
    }

    results.append(prepareEventHandlers());
    results.append(prepareStyles());
    results.append(getElementClose());
    String ret;
    if ( !"".equals(startField) && !"".equals(endField))
    {
      throw new JspException("You should set only one from startField and endField properties");
    }
    else if (!"".equals(startField))
    {
      endField = property;
    }
    else if (!"".equals(endField))
    {
      startField = property;
    }
    if (getReadonly())
    {
      ret = "<table cellSpacing=0 cellPadding=0 border=0 class='date-box'><tr><td>" +
              results.toString() + "&nbsp;</td>" +
              "<td valign=middle style='padding-top:1px;'><div id='" + nameForHtml + "Btn' class='select-btn_disabled' tabindex=-1 >" +
              "</td></tr></table>";
    }
    else
    {
      ret =
              "<table cellSpacing=0 cellPadding=0 border=0 class='date-box'><tr>" +
                      "<td valign=top>" + results.toString() + "</td><td>&nbsp;</td>" +
                      "<td valign=middle style='padding-top:1px;'><div id='" + nameForHtml + "Btn' class='select-btn_enabled' tabindex=-1 " +
                      "onclick = 'return __showCalendar(\"" + nameForHtml + "Frm\",\"" + nameForHtml + "\",\"" + nameForHtml + "Btn\",\"" + css + "\"," + afterSelect + ",\"" + startField + "\",\"" + endField + "\",\"" + onlyHeader + "\");'" +
                      "></td></tr></table>" +
                      "<iframe id='" + nameForHtml + "Frm' style='display:none;' FRAMEBORDER=0 SCROLLING=no class='frame-calendar' APPLICATION='yes' ></iframe>";

    }

    // Print this field to our output writer
    String res = ControlCommentHelper.renderInputElement(ret, this, pageContext, getProperty(), showHelp, null);
    TagUtils.getInstance().write(pageContext, res);
    // Continue processing this page
    startField = endField = "";
    onlyHeader = false;
    showHelp = true;
    enterDispatch = "";
    return (EVAL_BODY_TAG);
  }

  public void release()
  {
    super.release();
    css = null;
    htmlName = null;
    afterSelect = "null";
  }

  public String getCss()
  {
    return css;
  }

  public void setCss(String css)
  {
    this.css = css;
  }

  public String getHtmlName()
  {
    return htmlName;
  }

  public void setHtmlName(String htmlName)
  {
    this.htmlName = htmlName;
  }

  public String getAfterSelect()
  {
    return afterSelect;
  }

  public void setAfterSelect(String afterSelect)
  {
    this.afterSelect = afterSelect;
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

  public String getStartField()
  {
    return startField;
  }

  public void setStartField(String startField)
  {
    this.startField = startField;
  }

  public String getEndField()
  {
    return endField;
  }

  public void setEndField(String endField)
  {
    this.endField = endField;
  }

  public boolean isOnlyHeader()
  {
    return onlyHeader;
  }

  public void setOnlyHeader(boolean onlyHeader)
  {
    this.onlyHeader = onlyHeader;
  }

  public boolean isSelectOnly()
  {
    return selectOnly;
  }

  public void setSelectOnly(boolean selectOnly)
  {
    this.selectOnly = selectOnly;
  }

  public boolean isShowHelp()
  {
    return showHelp;
  }

  public void setShowHelp(boolean showHelp)
  {
    this.showHelp = showHelp;
  }

  public String getEnterDispatch()
  {
    return enterDispatch;
  }

  public void setEnterDispatch(String enterDispatch)
  {
    this.enterDispatch = enterDispatch;
  }
}
