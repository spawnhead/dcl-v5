package net.sam.dcl.taglib;

import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Mar 26, 2005
 * Time: 4:12:05 PM
 */
public class ModalDialogTag extends TagSupport{
  String name;
  String width;
  String height;
  boolean doOpenWindow=false;
  public int doStartTag() throws JspException { 
    super.doStartTag();
    String out =
        "<script type=\"text/javascript\" language=\"JScript\">" +
        "function "+getDialogName(name)+"(url,callback){";
        if (!doOpenWindow){
          out+="var ret = showModalDialog(url,null,\"dialogWidth:"+width+"px;dialogHeight:"+height+"px;help:0;status:0;scroll:0\");";
        } else {
          out+="var ret = window.open(url,null,\"width="+width+"px,height="+height+"px,help=0,status=1,scroll=1,toolbar=1,location=1\");";
        }
    out+=
        //"alert(ret+','+callback);" +
        "if (ret && callback){" +
        "callback.call();" +
        "}" +
        "return false;" +
        "}" +
        "</script>";
    //TagUtils.getInstance().writePrevious(pageContext, out);
    TagUtils.getInstance().write(pageContext, out);
    return SKIP_BODY;
  }
  static public String getDialogName(String name){
    return "__showDialog"+StringUtil.getString(name);
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getWidth() {
    return width;
  }
  public void setWidth(String width) {
    this.width = width;
  }
  public String getHeight() {
    return height;
  }
  public void setHeight(String height) {
    this.height = height;
  }
  public boolean isDoOpenWindow() {
    return doOpenWindow;
  }
  public void setDoOpenWindow(boolean doOpenWindow) {
    this.doOpenWindow = doOpenWindow;
  }
}
