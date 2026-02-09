package net.sam.dcl.taglib;

import net.sam.dcl.util.StringUtil;

import javax.servlet.jsp.JspException;


/**
 * User: Dima
 * Date: Feb 22, 2005
 * Time: 11:29:04 AM
 */
public class TextTag extends org.apache.struts.taglib.html.TextTag
{
  private boolean readOnlySetted = false;
  private boolean showHelp = true;
  private String enterDispatch = "";

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

  @Override
  protected String renderInputElement() throws JspException
  {
    String enterPressStr = TagUtils.getEnterPressStr(getStyleClass(), getEnterDispatch());
    if ( !StringUtil.isEmpty(enterPressStr) )
    {
      setOnkeydown(enterPressStr);
    }

    String superElementStr = super.renderInputElement();
    if ( superElementStr.indexOf("readonly=\"readonly\"") != -1 )
    {
      superElementStr = superElementStr.replace("readonly=\"readonly\"", "disabled readonly=\"readonly\"");  
    }
    String ret = ControlCommentHelper.renderInputElement(superElementStr, this, pageContext, getProperty(), showHelp, null);
    showHelp = true;
    enterDispatch = "";
    return ret;
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
