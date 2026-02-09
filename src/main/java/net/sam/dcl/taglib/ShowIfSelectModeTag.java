package net.sam.dcl.taglib;

import net.sam.dcl.controller.actions.SelectFromGridAction;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ShowIfSelectModeTag extends TagSupport {
	public int doStartTag() throws JspException {

		if (!SelectFromGridAction.isSelectMode(pageContext.getSession())) {
			return (SKIP_BODY);
		}

		// Continue processing this page
		return (EVAL_BODY_INCLUDE);
	}

}