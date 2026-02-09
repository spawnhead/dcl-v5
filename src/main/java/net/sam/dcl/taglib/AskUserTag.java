package net.sam.dcl.taglib;

import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;

/**
 * @author: DG
 * Date: Mar 26, 2005
 * Time: 4:12:05 PM
 */
public class AskUserTag extends TagSupport
{
  String name;
  String width = "null";
  String height = "null";
  String key;
  String arg0;
  String arg1;
  String arg2;
  String arg3;
  boolean scriptArg0 = false;
  boolean scriptArg1 = false;
  boolean scriptArg2 = false;
  boolean scriptArg3 = false;

  boolean showOkCancel = false;

  public int doStartTag() throws JspException
  {
    super.doStartTag();
    String tArg0 = arg0;
    if (scriptArg0) tArg0 = "'+arg0+'";
    String tArg1 = arg1;
    if (scriptArg1) tArg1 = "'+arg1+'";
    String tArg2 = arg2;
    if (scriptArg2) tArg2 = "'+arg2+'";
    String tArg3 = arg3;
    if (scriptArg3) tArg3 = "'+arg3+'";
    String msg = StrutsUtil.getMessage(pageContext, key, tArg0, tArg1, tArg2, tArg3);
    String out =
            "<script type=\"text/javascript\" language=\"JScript\">" +
                    "function " + getAskUserName(name) + "(arg0,arg1,arg2,arg3){" +
                    " var ret = isUserAgree('" + msg + "'," + showOkCancel + "," + width + "," + height + "); " +
                    " stopProgress(); " +
                    " return ret; " +
                    "}" +
                    "</script>";
    TagUtils.getInstance().write(pageContext, out);
    return SKIP_BODY;
  }

  static public String getAskUserName(String name)
  {
    return "__askUser" + StringUtil.getString(name);
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getWidth()
  {
    return width;
  }

  public void setWidth(String width)
  {
    this.width = width;
  }

  public String getHeight()
  {
    return height;
  }

  public void setHeight(String height)
  {
    this.height = height;
  }

  public boolean isShowOkCancel()
  {
    return showOkCancel;
  }

  public void setShowOkCancel(boolean showOkCancel)
  {
    this.showOkCancel = showOkCancel;
  }

  public String getKey()
  {
    return key;
  }

  public void setKey(String key)
  {
    this.key = key;
  }

  public String getArg0()
  {
    return arg0;
  }

  public void setArg0(String arg0)
  {
    this.arg0 = arg0;
  }

  public String getArg1()
  {
    return arg1;
  }

  public void setArg1(String arg1)
  {
    this.arg1 = arg1;
  }

  public String getArg2()
  {
    return arg2;
  }

  public void setArg2(String arg2)
  {
    this.arg2 = arg2;
  }

  public String getArg3()
  {
    return arg3;
  }

  public void setArg3(String arg3)
  {
    this.arg3 = arg3;
  }

  public boolean isScriptArg0()
  {
    return scriptArg0;
  }

  public void setScriptArg0(boolean scriptArg0)
  {
    this.scriptArg0 = scriptArg0;
  }

  public boolean isScriptArg1()
  {
    return scriptArg1;
  }

  public void setScriptArg1(boolean scriptArg1)
  {
    this.scriptArg1 = scriptArg1;
  }

  public boolean isScriptArg2()
  {
    return scriptArg2;
  }

  public void setScriptArg2(boolean scriptArg2)
  {
    this.scriptArg2 = scriptArg2;
  }

  public boolean isScriptArg3()
  {
    return scriptArg3;
  }

  public void setScriptArg3(boolean scriptArg3)
  {
    this.scriptArg3 = scriptArg3;
  }
}
