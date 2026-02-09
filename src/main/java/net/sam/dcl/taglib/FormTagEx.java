package net.sam.dcl.taglib;

import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.FormTag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * User: Dima
 * Date: Oct 13, 2004
 * Time: 2:08:44 PM
 */
public class FormTagEx extends FormTag
{
  private boolean readonly = false;

  public FormTagEx()
  {
    target = "_default";
  }

  public int doStartTag() throws JspException
  {
    if (action == null)
    {
      ActionMapping mapping = (ActionMapping) pageContext.getRequest().getAttribute(Globals.MAPPING_KEY);
      setAction(mapping.getPath());
    }
    int retVal = super.doStartTag();
    if (SelectFromGridAction.isSelectMode(pageContext.getSession()))
    {
      StringBuffer results = new StringBuffer();
      HttpServletResponse response =(HttpServletResponse) this.pageContext.getResponse();
      String actionUrl = SelectFromGridAction.getSelectModeSelectUrl(pageContext.getSession());
      results.append("<script LANGUAGE=\"JScript\" type=\"text/javascript\">");
      results.append("function __setSelect(obj,id,skip){\n");
      //results.append("alert(obj.tagName+'\\n'+event.srcElement.tagName+'\\n'+event.srcElement.onclick+'\\n'+obj.onclick);\n");
      //skip all objects that have defined onclick
      results.append("if(skip!=1 && event.srcElement.onclick!=null) return false;\n");
      results.append("var form = getForm(obj);\n");
      results.append("form.action = '");
      results.append(response.encodeURL(TagUtils.getInstance().getActionMappingURL(actionUrl, this.pageContext)));
      results.append("';\n");
      results.append("document.getElementById('" + SelectFromGridAction.SELECT_ID + "').value=id;\n");
      results.append("form.submit();\n");
      results.append("}");
      results.append("</script>");
      TagUtils.getInstance().write(pageContext, results.toString());
    }

    return retVal;    //To change body of overridden methods use File | Settings | File Templates.
  }

  public int doEndTag() throws JspException
  {
    StringBuffer results = new StringBuffer();
    results.append("<input type=hidden name='ctrl' value=''>");
    results.append("<input type=hidden name='do' value=''>");
    if (SelectFromGridAction.isSelectMode(pageContext.getSession()))
    {
      results.append("<input type=hidden name='" + SelectFromGridAction.SELECT_ID + "' value=''>");
      results.append("<div class=gridBottom>\n" +
              "\t<input type=button onclick='{  return __setSelect(this,\"\",1);}'  class=\"width80\" value=\"" +
              StrutsUtil.getMessage(pageContext, "button.cancel") + "\" >\n" +
              "</div>");
    }
    //TagUtils.getInstance().writePrevious(pageContext, results.toString());
    TagUtils.getInstance().write(pageContext, results.toString());
    int retVal = super.doEndTag();
    //mandatory
    action = null;
    return retVal;
  }

  public boolean getReadonly()
  {
    return readonly;
  }

  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }
}
