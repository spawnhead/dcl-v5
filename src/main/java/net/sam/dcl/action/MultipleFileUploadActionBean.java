/*************************************************************************
 (C) COPYRIGHT International Business Machines Corp. 2012-2014
 All Rights Reserved

 US Government Users Restricted Rights - Use, duplication or
 disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 **************************************************************************/
package net.sam.dcl.action;

import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.service.DeferredAttachmentService;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandra Shkrobova
 * Date: 10/30/13
 */

public class MultipleFileUploadActionBean extends ActionBean {
  private String sessionId;

  public ActionForward input() {
    return mapping.findForward("form");
  }

  public ActionForward save() {
    return getDeferredAttachmentService(request).getBackFromUploadForward();
  }

  public ActionForward back() {
    getDeferredAttachmentService(request).deleteNotPersisted();
    return save();
  }

  private DeferredAttachmentService getDeferredAttachmentService(HttpServletRequest request) {
    return DeferredAttachmentService.get(request.getSession());
  }

  public String getSessionId() {
    if (sessionId == null) {
      sessionId = request.getSession().getId();
    }
    return sessionId;
  }
}
