package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.taglib.LinkTagEx;
import net.sam.dcl.taglib.ULink;
import net.sam.dcl.taglib.AskUserTag;
import net.sam.dcl.taglib.ModalDialogTag;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.navigation.PermissionChecker;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.controller.actions.BaseAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.FormTag;

import javax.servlet.jsp.JspException;
import java.net.MalformedURLException;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColLinkTag extends ColCustomTag
{
  String action;
  String forward;
  String dispatch;
  String onclick;

  public static final String LINK = "link";
  public static final String SUBMIT = "submit";
  public static final String FRAME = "frame";
  public static final String DIALOG = "dialog";
  public static final String SCRIPT = "script";

  String type;
  String askUser;
  String dialog;
  String scriptUrl;
  String callback = "null";
  String addText;


  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColLinkTag.class);

  /**
   * Constructor.
   */
  public ColLinkTag()
  {
  }

  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
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
      results.append("&nbsp;");
      TagUtils.getInstance().write(pageContext, results.toString());
      return SKIP_BODY;
    }
    if (!SelectFromGridAction.isSelectMode(pageContext.getSession()))
    {
      String styleClass = getStyleClassValue();
      if (isReadOnly())
      {
        results.append(" <span ");
        if (StringUtil.isEmpty(styleClass))
        {
          results.append("class='grid-link-readonly' ");
        }
        else
        {
          results.append("class='");
          results.append(styleClass);
          results.append("' ");
        }
      }
      else
      {
        results.append(" <a href='' ");
        if (StringUtil.isEmpty(styleClass))
        {
          results.append("class='grid-link' ");
        }
        else
        {
          results.append("class='");
          results.append(styleClass);
          results.append("' ");
        }
        results.append(getOnClickAction(true));
      }
      results.append(">");
    }
    int retVal = EVAL_BODY_BUFFERED;
    if (!StringUtil.isEmpty(property))
    {
      try
      {
        String value = tableTag.getGridDataProperty(property);
        if (!StringUtil.isEmpty(value))
        {
          results.append(value);
        }
      }
      catch (Exception e)
      {
        StrutsUtil.addError(pageContext, new EMessage(e));
        throw new JspException(e);
      }
      retVal = SKIP_BODY;
    }
    TagUtils.getInstance().write(pageContext, results.toString());
    return retVal;
  }

  /**
   * Generate the required input tag.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doEndTag() throws JspException
  {
    action = null;
    if (!isHideOnSelect())
    {
      StringBuffer result = GetBodyContent();
      if (isShow())
      {
        if (!SelectFromGridAction.isSelectMode(pageContext.getSession()))
        {
          if (isReadOnly())
          {
            result.append("</span>");
          }
          else
          {
            result.append("</a>");
          }
          if (!StringUtil.isEmpty(getAddText()))
          {
            result.append(getAddText());
          }
        }
      }
      result.append("</td>");
      TagUtils.getInstance().write(pageContext, result.toString());
    }
    addText = null;
    return EVAL_PAGE;
  }

  StringBuffer getOnClickAction(boolean showWait) throws JspException
  {
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    StringBuffer results = new StringBuffer();
    checkActionFromForm();
    try
    {
      //temporary disable if case
      if (false &&
              !StringUtil.isEmpty(tableTag.getAutoLockName()) &&
              LockedRecords.getLockedRecords().isLockedByOther(
                      new SyncObject(tableTag.getAutoLockName(),
                              tableTag.getKeyAsString(),
                              pageContext.getSession().getId())))
      {
        results.append("onclick='{ showCustomError(null,\"" + StrutsUtil.getMessage(pageContext, "error.record.locked") + "\",null,null); ");
        if (!StringUtil.isEmpty(tableTag.getAutoLockReloadFunc()))
        {
          results.append(" " + tableTag.getAutoLockReloadFunc() + "(); ");
        }
        else
        {
          results.append(" document.location.reload(); ");
        }
      }
      else
      {
        String url = calculateURL();
        if (!PermissionChecker.checkURL(pageContext.getRequest(), url))
        {
          results.append("onclick='{ showCustomError(null,\"" + StrutsUtil.getMessage(pageContext, "error.access-denied") + "\",null,null); ");
        }
        else
        {
          String script = "";
          if (!type.equalsIgnoreCase(SCRIPT))
          {
            script += " if (lockForm()) return false; ";
          }

          if (!StringUtil.isEmpty(askUser))
          {
            int idxB = askUser.indexOf("(");
            int idxE = askUser.indexOf(")");
            String params = "";
            if (idxB != -1 && idxE != -1)
            {
              params = askUser.substring(idxB + 1, idxE);
              askUser = askUser.substring(0, idxB);
              VarStringParser parser = new VarStringParser(new CallbackImpl(tableTag));
              try
              {
                params = parser.parse(params);
              }
              catch (Exception e)
              {
              }
            }
            script = "if (!" + AskUserTag.getAskUserName(askUser) + "(" + params + ")){unlockForm();return false;}";
          }
          results.append("onclick='{" + (showWait ? " runProgress(); " : "") + script + " var form=getForm(this); ");
          if (!StringUtil.isEmpty(getOnclick()))
          {
            results.append("  " + getOnclick() + ";");
            setOnclick("");
          }

          if (!StringUtil.isEmpty(type))
          {
            if (type.equalsIgnoreCase(LINK))
            {
              results.append(" document.location=\"" + url + "\";");
            }
            else if (type.equalsIgnoreCase(SUBMIT))
            {
              results.append(" form.action=\"" + url + "\";form.submit();");
            }
            else if (type.equalsIgnoreCase(FRAME))
            {
              results.append(" submitInFrame(form,\"" + url + "\"," + callback + ");");
            }
            else if (type.equalsIgnoreCase(DIALOG))
            {
              results.append(" " + ModalDialogTag.getDialogName(dialog) + "(\"" + url + "\"," + callback + ");");
            }
            else if (type.equalsIgnoreCase(SCRIPT))
            {
              results.append("");
            }
          }
        }
      }
    }
    catch (JspException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.url.addkeys", e));
      throw new JspException(StrutsUtil.getMessage(pageContext, "error.url.addkeys"));
    }
    results.append(" return false;}' ");
    return results;
  }

  /**
   * Release any acquired resources.
   */
  protected void checkActionFromForm()
  {
    if (action == null && forward == null)
    {
      FormTag formTag = (FormTag) findAncestorWithClass(this, FormTag.class);
      if (formTag != null)
      {
        setAction(formTag.getAction());
      }
    }
  }

  public String calculateURL() throws JspException
  {
    String url = null;
    try
    {
      url = TagUtils.getInstance().computeURLWithCharEncoding(pageContext, forward, null,
              null, action, null, null, null, false, false);
      url = LinkTagEx.addDispatchToURL(url, dispatch);
    }
    catch (MalformedURLException e)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.wrong.url", e));
      throw new JspException(StrutsUtil.getMessage(pageContext, "wrong.url"));
    }
    TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
    if (tableTag == null)
    {
      throw new JspException("Can't find <table> Tag");
    }
    try
    {
      url = StringUtil.addToURL(url, tableTag.getKeyAsUrl());
      if (!StringUtil.isEmpty(tableTag.getAutoLockName()))
      {
        url = StringUtil.addToURL(url, BaseAction.PARAM_LOCK_NAME, tableTag.getAutoLockName());
        url = StringUtil.addToURL(url, BaseAction.PARAM_LOCK_ID, tableTag.getKeyAsString());
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(pageContext, new EMessage("error.url.addkeys", e));
      throw new JspException(StrutsUtil.getMessage(pageContext, "error.url.addkeys"));
    }
    if (!StringUtil.isEmpty(scriptUrl))
    {
      VarStringParser parser = new VarStringParser(new ULink.CallbackImpl());
      String addToUrl = null;
      try
      {
        addToUrl = parser.parse(scriptUrl);
      }
      catch (Exception e)
      {
      }
      url = StringUtil.addToURL(url, addToUrl);
    }
    return url;
  }

  public void release()
  {
    action = null;
    forward = null;
    dispatch = null;

    type = null;
    askUser = null;
    dialog = null;
    scriptUrl = null;
    addText = null;

    super.release();
  }

  public String getAction()
  {
    return action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  public String getForward()
  {
    return forward;
  }

  public void setForward(String forward)
  {
    this.forward = forward;
  }

  public String getDispatch()
  {
    return dispatch;
  }

  public void setDispatch(String dispatch)
  {
    this.dispatch = dispatch;
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

  public String getCallback()
  {
    return callback;
  }

  public void setCallback(String callback)
  {
    this.callback = callback;
  }

  public String getOnclick()
  {
    return onclick;
  }

  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }

  static public class CallbackImpl implements VarStringParser.Callback
  {
    TableTag tableTag;

    public CallbackImpl(TableTag tableTag)
    {
      this.tableTag = tableTag;
    }

    public String process(String var) throws Exception
    {
      String str = tableTag.getGridDataProperty(var);
      return StringUtil.StringToJString2(str);
    }
  }

  public String getAddText()
  {
    return addText;
  }

  public void setAddText(String addText)
  {
    this.addText = addText;
  }
}