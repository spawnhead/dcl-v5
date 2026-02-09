/*
 * $Header: /home/cvs/jakarta-struts/src/share/org/apache/struts/taglib/html/RewriteTag.java,v 1.20 2004/03/14 06:23:46 sraeburn Exp $
 * $Revision: 1.20 $
 * $Date: 2004/03/14 06:23:46 $
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sam.dcl.taglib;

import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.FormTag;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.controller.DispatchActionHelper;

/**
 * Generate a URL-encoded URI as a string.
 *
 * @version $Revision: 1.20 $ $Date: 2004/03/14 06:23:46 $
 */
public class RewriteTag extends org.apache.struts.taglib.html.RewriteTag {
  String scriptUrl;
  String dispatch;

  // --------------------------------------------------------- Public Methods


  /**
   * Render the appropriately encoded URI.
   *
   * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    // Generate the hyperlink URL
    Map params = TagUtils.getInstance().computeParameters
        (pageContext, paramId, paramName, paramProperty, paramScope,
            name, property, scope, transaction);

    String url = null;
    try {
      // Note that we're encoding the & character to &amp; in XHTML mode only,
      // otherwise the & is written as is to work in javascripts.
      if (action == null && forward == null && page == null && href == null) {
        FormTag formTag = (FormTag) findAncestorWithClass(this, FormTag.class);
        if (formTag != null) {
          setAction(formTag.getAction());
        }
      }
      url =
          TagUtils.getInstance().computeURLWithCharEncoding(pageContext,
              forward,
              href,
              page,
              action,
              module,
              params,
              anchor,
              false,
              this.isXhtml(),
              useLocalEncoding);

      if (!StringUtil.isEmpty(scriptUrl)) {
        VarStringParser parser = new VarStringParser(new ULink.CallbackImpl());
        try {
          String addToUrl = parser.parse(scriptUrl);
          url = StringUtil.addToURL(url, addToUrl);
        } catch (Exception e) {
        }
      }
      if (dispatch != null) {
        url = StringUtil.addToURL(url, DispatchActionHelper.defaultParameter, dispatch);
      }
    } catch (MalformedURLException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException
          (messages.getMessage("rewrite.url", e.toString()));
    }

    TagUtils.getInstance().write(pageContext, url);

    return (SKIP_BODY);

  }
  public String getScriptUrl() {
    return scriptUrl;
  }
  public void setScriptUrl(String scriptUrl) {
    this.scriptUrl = scriptUrl;
  }
  public String getDispatch() {
    return dispatch;
  }
  public void setDispatch(String dispatch) {
    this.dispatch = dispatch;
  }


}
