package net.sam.dcl.taglib;

import net.sam.dcl.beans.User;
import net.sam.dcl.navigation.ControlComment;
import net.sam.dcl.navigation.ControlComments;
import net.sam.dcl.util.StoreUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;

/**
 * User: dg
 * Date: 30.07.2009
 * Time: 11:00:37
 */
public class ControlCommentHelper
{
  static public String renderInputElement(String res, BodyTagSupport tag, PageContext pageContext, String property, boolean showHelp, String additionalStyles) throws JspException
  {
    if (!showHelp)
    {
      return res;
    }
    FormTagEx form = (FormTagEx) TagSupport.findAncestorWithClass(tag, FormTagEx.class);
    String beanName = "";
    if (form != null)
    {
      beanName = form.getBeanName() + ".";
    }
    User user = (User) StoreUtil.getSession((HttpServletRequest) pageContext.getRequest(), User.class);
    StringBuilder builder = new StringBuilder().append("<div ");
    try
    {
      ControlComments comments = (ControlComments) StoreUtil.getApplication(pageContext.getServletContext(), ControlComments.class);
      ControlComment comment = comments.getComment(beanName + property);

      if (null != comment)
      {
        builder.append("class='control-out control-wc ");
        builder.append(additionalStyles);
        builder.append(" '");
      }
      else
      {
        builder.append("class='control-out control-nc ");
        builder.append(additionalStyles);
        builder.append(" '");
      }
      builder.append(" ");
      if (user != null && user.isAdmin())
      {
        builder.append("onclick='editControlComment");
        builder.append("(this,\"").append(beanName).append(property);
        if (null != comment)
        {
          builder.append("\",true)'");
        }
        else
        {
          builder.append("\",false)'");
        }
      }
      else
      {
        if (null != comment)
        {
          builder.append("onclick='showControlComment(this)'");
        }
      }
      builder.append(" __comment='");
      builder.append((null == comment || comment.getFcm_value() == null) ? "" : comment.getFcm_value());
      builder.append("'");

    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }

    builder.append(">").append(res).append("</div>");
    return builder.toString();
  }
}
