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

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseFieldTag;

import javax.servlet.jsp.JspException;
import java.net.MalformedURLException;


/**
 * Custom tag for input fields of type "text".
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2003/03/23 06:08:41 $
 */

public class ServerListTag extends BaseFieldTag
{
  protected String htmlName = null;
  protected String other = "";
  protected String action = null;
  protected String height = "100";
  protected String callback = "null";
  protected String idName = null;
  protected boolean selectOnly = false;
  protected String scriptUrl;
  protected String filter = "";
  private boolean showHelp = true;
  private String enterDispatch = "";

  /**
   * Construct a new instance of this tag.
   */
  public ServerListTag()
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

    if (selectOnly && !getReadonly())
    {
      results.append(" readonly");
    }
    if (getReadonly())
    {
      results.append(" disabled");
    }
    results.append(" " + other + " ");
    if (!StringUtil.isEmpty(filter))
    {
      results.append(" onpropertychange='return __filterList(\"" + nameForHtml + "Frm\",\"" + nameForHtml + "\",\"" + nameForHtml + "Btn\");'");
    }
    String enterDispatchStr = "";
    if ( "filter".equals(getStyleClass()) )
    {
      enterDispatchStr = "filter";
    }
    if ( !StringUtil.isEmpty(getEnterDispatch()) )
    {
      enterDispatchStr = getEnterDispatch();
    }
    results.append(" onkeydown='return __keydownList(\"" + nameForHtml + "Frm\",\"" + nameForHtml + "\",\"" + nameForHtml + "Btn\", \"" + filter + "\", \"" + enterDispatchStr + "\");'");
    results.append(prepareEventHandlers());
    results.append(prepareStyles());
    results.append(getElementClose());

    String url;
    try
    {
      url = TagUtils.getInstance().computeURL(pageContext, null, null, null, action, null, null, null, false);
      if (!StringUtil.isEmpty(scriptUrl))
      {
        VarStringParser parser = new VarStringParser(new ULink.CallbackImpl());
        try
        {
          String addToUrl = parser.parse(scriptUrl);
          url = StringUtil.addToURL(url, addToUrl);
        }
        catch (Exception e)
        {
        }
      }
      if (!StringUtil.isEmpty(filter))
      {
        url = StringUtil.addToURL(url, filter + "=");
      }
    }
    catch (MalformedURLException e)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.wrong.url", e));
      throw new JspException(StrutsUtil.getMessage(pageContext, "wrong.url"));
    }
    String ret = "";
    Object idValue;
    if (!StringUtil.isEmpty(idName))
    {
      idValue = TagUtils.getInstance().lookup(pageContext, name, idName, null);
      if (idValue == null)
        idValue = "";
      ret += "<input type=hidden name='" + idName + "' value='" + TagUtils.getInstance().filter(idValue.toString()) + "' >";
    }
    if (getReadonly())
    {
      ret += "<table cellSpacing=0 cellPadding=0 border=0 class='server-list-box'><tr><td>" +
              results.toString() + "</td><td style='padding:0px;'>&nbsp;</td>" +
              "<td valign=middle style='padding-top:1px;'><div id='" + nameForHtml + "Btn'  class='select-btn_disabled' tabindex=-1 >" +
              "</td></tr></table>";
    }
    else
    {
      ret +=
              "<table cellSpacing=0 cellPadding=0 border=0 class='server-list-box'><tr>\n" +
                      "<td valign=top>" + results.toString() + "</td><td style='padding:0px;'>&nbsp;</td>\n" +
                      "<td valign=middle style='padding-top:1px;'><div id='" + nameForHtml + "Btn'  class='select-btn_enabled' tabindex=-1 \n" +
                      "onclick='{var form=getForm(this); return __showList2(\"" + url + "\",\"" + nameForHtml + "\",\"" + (idName == null ? "" : idName) + "\",\"" + nameForHtml + "Frm\",\"" + nameForHtml + "Btn\"," + callback + "," + height + ",\"" + filter + "\");}'></td></tr></table>" +
                      "<iframe id='" + nameForHtml + "Frm' name='" + nameForHtml + "Frm' style='display:none;' FRAMEBORDER=0 SCROLLING=no  class='frame-list' APPLICATION='yes' ></iframe>\n";
    }


    // Print this field to our output writer
    String res = ControlCommentHelper.renderInputElement(ret, this, pageContext, getProperty(), showHelp, null);
    TagUtils.getInstance().write(pageContext, res);
    showHelp = true;
    enterDispatch = "";
    // Continue processing this page
    return (EVAL_BODY_TAG);
  }

  public void release()
  {
    super.release();
    htmlName = null;
    other = "";
    action = null;
    height = "60";
    callback = "null";
    idName = null;
    filter = null;
  }

  public boolean isSelectOnly()
  {
    return selectOnly;
  }

  public void setSelectOnly(boolean selectOnly)
  {
    this.selectOnly = selectOnly;
  }

  public String getHtmlName()
  {
    return htmlName;
  }

  public void setHtmlName(String htmlName)
  {
    this.htmlName = htmlName;
  }

  public String getOther()
  {
    return other;
  }

  public void setOther(String other)
  {
    this.other = other;
  }

  public String getAction()
  {
    return action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  public String getHeight()
  {
    return height;
  }

  public void setHeight(String height)
  {
    this.height = height;
  }

  public String getCallback()
  {
    return callback;
  }

  public void setCallback(String callback)
  {
    this.callback = callback;
  }

  public String getIdName()
  {
    return idName;
  }

  public void setIdName(String idName)
  {
    this.idName = idName;
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

  public String getScriptUrl()
  {
    return scriptUrl;
  }

  public void setScriptUrl(String scriptUrl)
  {
    this.scriptUrl = scriptUrl;
  }

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
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
