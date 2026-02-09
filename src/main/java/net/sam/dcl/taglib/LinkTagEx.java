package net.sam.dcl.taglib;


import net.sam.dcl.controller.DispatchActionHelper;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.taglib.html.LinkTag;

import javax.servlet.jsp.JspException;

/**
 * User: Dima
 * Date: Oct 13, 2004
 * Time: 5:51:15 PM
 */
public class LinkTagEx extends LinkTag{
  public static final String FAKE_PARAM = "___FAKE_PARAM___";
  protected String actionForward = null;

  public String getActionForward() {
    return actionForward;
  }
  public void setActionForward(String actionForward) {
    this.actionForward = actionForward;
  }
  protected String dispatch =null;
  public String getDispatch() {
    return dispatch;
  }
  public void setDispatch(String dispatch) {
    this.dispatch = dispatch;
  }
/*  protected String paramValue = null;
  public String getParamValue() {
    return paramValue;
  }
  public void setParamValue(String paramValue) {
    this.paramValue = paramValue;
  }*/
  protected void checkActionForward(){
    if (actionForward != null) {
      ActionMapping mapping = (ActionMapping) pageContext.getRequest().getAttribute(Globals.MAPPING_KEY);
      ActionForward aForward = mapping.findForward(actionForward);
      if (aForward != null) {
        setAction(aForward.getPath());
      }
    }
  }
  protected void checkActionFromForm() {
    if (action == null && forward==null && page==null && href==null){
      FormTag formTag = (FormTag)findAncestorWithClass(this,FormTag.class);
      if (formTag!= null){
        setAction(formTag.getAction());
      }
    }
  }
/*  protected String calculateURL() throws JspException {
    if (paramId!= null && paramValue!=null ){
      pageContext.getRequest().setAttribute(FAKE_PARAM,paramValue);
      paramName = FAKE_PARAM;
    }
    String url = super.calculateURL();
    pageContext.getRequest().setAttribute(FAKE_PARAM,null);
    return url;
  }*/
  protected String calculateURL() throws JspException {
    return addDispatchToURL(super.calculateURL(),dispatch);
  }
  static public String addDispatchToURL(String url,String dispatch){
    if (dispatch != null){
      url = StringUtil.addToURL(url,DispatchActionHelper.defaultParameter,dispatch);
    }
    return url;
  }
  public int doEndTag() throws JspException {
    int retVal = super.doEndTag();
    //mandatory
    action = null;
    return retVal;
  }
  public void release() {
    super.release();
    actionForward = null;
    dispatch = null;
  }

  private boolean readOnlySetted = false;
  public void setReadonly(boolean readonly) {
    readOnlySetted = true;
    super.setReadonly(readonly);
  }
  public boolean getReadonly() {
    if (readOnlySetted) {
      return super.getReadonly();
    }
    FormTagEx form = (FormTagEx) findAncestorWithClass(this, FormTagEx.class);
    if (form != null) {
      return form.getReadonly();
    }
    return super.getReadonly();
  }
}
