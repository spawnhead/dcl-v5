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

import net.sam.dcl.config.Config;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.table.model.IGridResult;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletResponse;


/**
 * Custom tag for input fields of type "text".
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2003/03/23 06:08:41 $
 */

public class ColDateTag extends ColBaseResultTag
{
  private String css = Config.getString("date-tag.css");
  private String afterSelect = "null";
  private String afterSelectParams = "null";
  protected boolean selectOnly = false;
  private boolean onlyHeader = false;


  /**
   * Construct a new instance of this tag.
   */
  public ColDateTag()
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
    if (!StringUtil.isEmpty(width))
    {
      results.append(" width='" + width + "'");
    }
    results.append(" >");
    if (!isShow())
    {
      results.append("&nbsp;</td>");
      TagUtils.getInstance().write(pageContext, results.toString());
      return EVAL_BODY_BUFFERED;
    }
    IGridResult gridResult = null;
    String nameForHtml;
    StringBuffer inputText = new StringBuffer();
    try
    {
      gridResult = getGridResult(tableTag);
      inputText.append("<input type='text' ");
      nameForHtml = calcNameForTag(tableTag);
      if (!StringUtil.isEmpty(nameForHtml))
      {
        inputText.append("name='" + nameForHtml + "' ");
      }
      Object value = calcValue(gridResult, tableTag);
      inputText.append(" value='" + TagUtils.getInstance().filter(value.toString()) + "' class='grid-col-date' ");
      if (isReadOnly()) inputText.append("disabled ");
      if (selectOnly && !isReadOnly())
      {
        inputText.append(" readonly");
      }
      inputText.append(">");
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage(e));
      throw new JspException(e);
    }

    String ret;

    if (isReadOnly())
    {
      ret = "<table cellSpacing=0 cellPadding=0 border=0 class='grid-col-date'><tr><td>" +
              inputText.toString() + "</td><td style='padding-left:0px;padding:0px;width:2px'>&nbsp;</td>" +
              "<td valign=middle style='padding-top:1px;'><div id='" + nameForHtml + "Btn' class='select-btn_disabled' tabindex=-1 >" +
              "</td></tr></table>";
    }
    else
    {
      ret = "<iframe id='" + nameForHtml + "Frm' style='display:none;' FRAMEBORDER=0 SCROLLING=no class='frame-calendar' APPLICATION='yes' ></iframe>" +
              "<table cellSpacing=0 cellPadding=0 border=0 class='grid-col-date'><tr>" +
              "<td valign=top>" + inputText.toString() + "</td><td 'padding-left:0px;padding:0px;width:2px'>&nbsp;</td>" +
              "<td valign=middle style='padding-top:1px;'><div id='" + nameForHtml + "Btn' class='select-btn_enabled' tabindex=-1 style='cursor:pointer;' " +
              "onclick = 'return __showCalendar(\"" + nameForHtml + "Frm\",\"" + nameForHtml + "\",\"" + nameForHtml + "Btn\",\"" + css + "\"," + afterSelect + ",\"\",\"\",\"" + onlyHeader + "\"," + afterSelectParams + ");'" +
              "></td></tr></table>";

    }
    results.append(ret);
    results.append("</td>");
    TagUtils.getInstance().write(pageContext, results.toString());
    afterSelect = "null";
    afterSelectParams = "null";

    return SKIP_BODY;

  }

  public void release()
  {

    super.release();
    css = null;
    afterSelect = "null";
    afterSelectParams = "null";
  }

  public String getCss()
  {
    return css;
  }

  public void setCss(String css)
  {
    this.css = css;
  }

  public String getAfterSelect()
  {
    return afterSelect;
  }

  public void setAfterSelect(String afterSelect)
  {
    this.afterSelect = afterSelect;
  }

  public String getAfterSelectParams()
  {
    return afterSelectParams;
  }

  public void setAfterSelectParams(String afterSelectParams)
  {
    this.afterSelectParams = afterSelectParams;
  }

  public boolean isSelectOnly()
  {
    return selectOnly;
  }

  public void setSelectOnly(boolean selectOnly)
  {
    this.selectOnly = selectOnly;
  }

  public boolean isOnlyHeader()
  {
    return onlyHeader;
  }

  public void setOnlyHeader(boolean onlyHeader)
  {
    this.onlyHeader = onlyHeader;
  }
}