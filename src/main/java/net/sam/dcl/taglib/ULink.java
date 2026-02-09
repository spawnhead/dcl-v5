package net.sam.dcl.taglib;

import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.navigation.PermissionChecker;

/**
 * @author: DG
 * Date: Mar 27, 2005
 * Time: 1:32:45 PM
 */
public class ULink extends LinkTagEx
{
  public static final String LINK = "link";
  public static final String SUBMIT = "submit";
  public static final String FRAME = "frame";
  public static final String DIALOG = "dialog";
  public static final String CLOSE = "close";
  public static final String SCRIPT = "script";

  String type;
  String askUser;
  String onclickBeforeAsk;
  String dialog;
  String scriptUrl;
  String callback = "null";
  boolean disableControls = false;

  protected String calculateURL() throws JspException
  {
    String url = super.calculateURL();
    if (!StringUtil.isEmpty(scriptUrl))
    {
      VarStringParser parser = new VarStringParser(new CallbackImpl());
      try
      {
        String addToUrl = parser.parse(scriptUrl);
        url = StringUtil.addToURL(url, addToUrl);
      }
      catch (Exception e)
      {
      }
    }
    return url;
  }

  protected StringBuffer getOnClickAction(boolean showWait) throws JspException
  {
    StringBuffer results = new StringBuffer();
    if (!getReadonly())
    {
      checkActionForward();
      checkActionFromForm();
      String url = calculateURL();

      if (!PermissionChecker.checkURL(pageContext.getRequest(), url))
      {
        results.append("onclick='{ showCustomError(null,\"" + StrutsUtil.getMessage(pageContext, "error.access-denied") + "\", null, null); ");
      }
      else
      {
        String script = "";
        if (!type.equalsIgnoreCase(SCRIPT))
        {
          script = " if (lockForm()) return false; ";
        }

        if (!StringUtil.isEmpty(getOnclickBeforeAsk()))
        {
          script = getOnclickBeforeAsk() + "; ";
        }
        if (!StringUtil.isEmpty(askUser))
        {
          script += "if (!" + AskUserTag.getAskUserName(askUser) + "()){unlockForm();return false;}";
        }
        results.append("onclick='{" + (showWait ? " runProgress(); " : "") + script);
        if (!type.equalsIgnoreCase(LINK))
        {
          results.append(" var form=getForm(this); ");
        }

        if (!StringUtil.isEmpty(getOnclick()))
        {
          results.append("  " + getOnclick() + ";");
          setOnclick(null);
        }

        if (!type.equalsIgnoreCase(SCRIPT) && disableControls)
        {
          results.append(" disableFormControls(form,true); ");
        }

        if (type.equalsIgnoreCase(LINK))
        {
          results.append(" document.location=\"" + url + "\";");
        }
        else if (type.equalsIgnoreCase(SUBMIT))
        {
          results.append("var __savedAction=form.action; form.action=\"" + url + "\";form.submit();form.action=__savedAction;");
        }
        else if (type.equalsIgnoreCase(FRAME))
        {
          results.append(" submitInFrame(form,\"" + url + "\"," + callback + ");");
        }
        else if (type.equalsIgnoreCase(DIALOG))
        {
          results.append(" " + ModalDialogTag.getDialogName(dialog) + "(\"" + url + "\"," + callback + ");");
        }
        else if (type.equalsIgnoreCase(CLOSE))
        {
          results.append(" window.close(); ");
        }
        else if (type.equalsIgnoreCase(SCRIPT))
        {
          results.append("");
        }
        else
        {
          StrutsUtil.addError(pageContext, new EMessage("error.ubutton.type.wrong", null));
          throw new JspException(StrutsUtil.getMessage(pageContext, "error.ubutton.type.wrong"));
        }
      }
    }
    results.append(" return false;}' ");
    return results;
  }

  public int doStartTag() throws JspException
  {
    // Generate the opening anchor element


    StringBuffer results = new StringBuffer("<a href=''  ");
    results.append(getOnClickAction(true));
    if (getReadonly())
    {
      results.append(" disabled=\"true\"");
    }
    if (accesskey != null)
    {
      results.append(" accesskey=\"");
      results.append(accesskey);
      results.append("\"");
    }
    if (tabindex != null)
    {
      results.append(" tabindex=\"");
      results.append(tabindex);
      results.append("\"");
    }
    results.append(prepareStyles());
    results.append(prepareEventHandlers());
    results.append(">");
    // Print this element to our output writer
    TagUtils.getInstance().write(pageContext, results.toString());

    // Evaluate the body of this tag
    this.text = null;
    return (EVAL_BODY_TAG);
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getAskUser()
  {
    return askUser;
  }

  public void setAskUser(String askUser)
  {
    this.askUser = askUser;
  }

  public String getDialog()
  {
    return dialog;
  }

  public void setDialog(String dialog)
  {
    this.dialog = dialog;
  }

  public String getScriptUrl()
  {
    return scriptUrl;
  }

  public void setScriptUrl(String scriptUrl)
  {
    this.scriptUrl = scriptUrl;
  }

  public static class CallbackImpl implements VarStringParser.Callback
  {
    public String process(String var)
    {
      return "\"+form[\"" + var + "\"].value+\"";
    }

    public static void main(String[] args) throws Exception
    {
      CallbackImpl impl = new CallbackImpl();
      VarStringParser parser = new VarStringParser(impl);
      String res = parser.parse("test=${test}&name=${my name}");
      System.out.println(res);
      //System.out.println(impl.process("var"));
    }
  }

  public void release()
  {
    type = null;
    askUser = null;
    dialog = null;
    scriptUrl = null;
    text = null;
    super.release();
  }

  public boolean isDisableControls()
  {
    return disableControls;
  }

  public void setDisableControls(boolean disableControls)
  {
    this.disableControls = disableControls;
  }

  public String getCallback()
  {
    return callback;
  }

  public void setCallback(String callback)
  {
    this.callback = callback;
  }

  public String getOnclickBeforeAsk()
  {
    return onclickBeforeAsk;
  }

  public void setOnclickBeforeAsk(String onClickBeforeAsk)
  {
    this.onclickBeforeAsk = onClickBeforeAsk;
  }
}
