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


package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.taglib.ULink;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.table.model.IGridResult;
import org.apache.struts.taglib.TagUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.jsp.JspException;
import java.net.MalformedURLException;


/**
 * Custom tag for input fields of type "text".
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2003/03/23 06:08:41 $
 */

public class ColServerListTag extends ColBaseResultTag
{
  protected String other = "";
  protected String action = null;
  protected String height = "100";
  protected String listWidth = "null";
  protected String callback = "null";
  protected String idName = null;
  protected boolean selectOnly = false;
  protected String scriptUrl;
  protected String filter = "";
  protected String data = "null";

  /**
   * Construct a new instance of this tag.
   */
  public ColServerListTag()
  {
    super();
  }

  public int doStartTag() throws JspException
  {
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    checkHideOnSelect();
    if (isHideOnSelect())
    {
      return SKIP_BODY;
    }
    StringBuffer results = new StringBuffer("<td " + tableTag.getTextForScrollableGrid() + TableUtils.getStyleString(this) + " align='" + align + "'");
    results.append(" >");
    if (!isShow())
    {
      results.append("&nbsp;</td>");
      TagUtils.getInstance().write(pageContext, results.toString());
      return EVAL_BODY_BUFFERED;
    }
    IGridResult gridResult;
    String hiddenText = "";
    String name;
    String resIdName;
    StringBuffer inputText = new StringBuffer();
    try
    {
      gridResult = getGridResult(tableTag);
      inputText.append("<input type='text' ");
      name = calcNameForTag(tableTag);
      if (!StringUtil.isEmpty(name))
      {
        inputText.append("name='").append(name).append("' ");
      }
      Object value = calcValue(gridResult, tableTag);
      inputText.append(" value='").append(TagUtils.getInstance().filter(value.toString())).append("' class='grid-col-server-list' ");
      if (isReadOnly()) inputText.append("disabled ");
      if (selectOnly && !isReadOnly())
      {
        inputText.append(" readonly");
      }
      inputText.append(" ").append(other).append(" ");
      if (!StringUtil.isEmpty(filter))
      {
        inputText.append(" onpropertychange='return __filterList(\"").append(name).append("Frm\",\"").append(name).append("\",\"").append(name).append("Btn\");'");
      }
      inputText.append(" onkeydown='return __keydownList(\"").append(name).append("Frm\",\"").append(name).append("\",\"").append(name).append("Btn\", \"").append(filter).append("\", \"\");'");
      inputText.append(">");


      Object idValue;
      resIdName = calcIdNameForTag(tableTag);
      if (!StringUtil.isEmpty(idName))
      {
        idValue = calcIdValue(gridResult, tableTag);
        if (idValue == null)
          idValue = "";
        hiddenText = "<input type=hidden name='" + resIdName + "' value='" + TagUtils.getInstance().filter(idValue.toString()) + "' >";
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage(e));
      throw new JspException(e);
    }


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
    String ret = hiddenText;

    if (isReadOnly())
    {
      ret += "<table cellSpacing=0 cellPadding=0 border=0 class='grid-col-server-list'><tr><td>" +
             inputText.toString() + "</td><td style='padding-left:0px;padding:0px;width:2px'>&nbsp;</td>" +
             "<td valign=middle style='padding-top:1px;'><div id='" + name + "Btn' class='select-btn_disabled' tabindex=-1 >" +
             "</td></tr></table>";
    }
    else
    {
      ret += "<iframe id='" + name + "Frm' name='" + name + "Frm' style='display:none;' FRAMEBORDER=0 SCROLLING=no  class='frame-list' APPLICATION='yes' ></iframe>\n" +
             "<table cellSpacing=0 cellPadding=0 border=0 class='grid-col-server-list'><tr>\n" +
             "<td valign=top>" + inputText.toString() + "</td><td style='padding-left:0px;padding:0px;width:2px'>&nbsp;</td>\n" +
             "<td valign=middle style='padding-top:1px;'><div id='" + name + "Btn'  class='select-btn_enabled' tabindex=-1 style='cursor:pointer' \n" +
             "onclick='{var form=getForm(this); return __showList2(\"" + url + "\",\"" + name + "\",\"" + (idName == null ? "" : resIdName) + "\",\"" + name + "Frm\",\"" + name + "Btn\"," + callback + "," + height + ",\"" + filter + "\"" + "," + getData() + "," + listWidth + ");}'></td></tr></table>";
    }
    results.append(ret);
    results.append("</td>");
    TagUtils.getInstance().write(pageContext, results.toString());
    return SKIP_BODY;

  }

  protected String calcIdNameForTag(TableTag tableTag) throws Exception
  {
    String res = "";
    if (!StringUtil.isEmpty(result))
    {
      res = result + ".row(" + getKey(tableTag) + ")";
      if (!StringUtil.isEmpty(idName))
      {
        res += "." + getIdName();
      }
    }
    return res;
  }

  protected Object calcIdValue(IGridResult gridResult, TableTag tableTag) throws Exception
  {
    Object value;
    if (StringUtil.isEmpty(property))
    {
      Object obj = gridResult.checkRow(getKey(tableTag));
      value = BeanUtils.getProperty(obj, getIdName());
    }
    else
    {
      value = tableTag.getGridDataProperty(idName);
    }
    return value;
  }

  public void release()
  {
    super.release();
    other = "";
    action = null;
    height = "60";
    callback = "null";
    idName = null;
    filter = null;
    setData("null");
    listWidth = "null";
  }

  public boolean isSelectOnly()
  {
    return selectOnly;
  }

  public void setSelectOnly(boolean selectOnly)
  {
    this.selectOnly = selectOnly;
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

  public String getData()
  {
    return data;
  }

  public void setData(String data)
  {
    this.data = data;
  }

  public String getListWidth()
  {
    return listWidth;
  }

  public void setListWidth(String listWidth)
  {
    this.listWidth = listWidth;
  }
}
