package net.sam.dcl.action;

import net.sam.dcl.controller.actions.BaseAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.filters.RequestResponseSetupFilter;
import org.apache.struts.action.ActionForward;

/**
 * @author: DG
 * Date: Jan 27, 2006
 * Time: 3:29:37 PM
 */
public class ShowPrevResponse extends BaseAction {
  public ActionForward execute(IActionContext context) throws Exception {
    ResponseCollectFilter.RequestResult requestResult = (ResponseCollectFilter.RequestResult) StoreUtil.getSession(context.getRequest(), ResponseCollectFilter.RequestResult.class);
    if (requestResult == null) {
      // Нет сохраненного ответа (например, прямой заход) — возвращаем пустую страницу
      context.getResponse().setStatus(204);
      return null;
    }
    StringBuffer responseString = requestResult.toStringBuffer(RequestResponseSetupFilter.getResponseEncoding());
    int idx = responseString.indexOf(StrutsUtil.COMMENT_FOR_INSERTING_ERRORS);
    if (idx != -1){
      idx += StrutsUtil.COMMENT_FOR_INSERTING_ERRORS.length();
      SyncObject syncObjectCurrent = (SyncObject) context.getRequest().getAttribute("skip.processing.locking");
      StrutsUtil.FormatLockResult res =  StrutsUtil.formatLockError(syncObjectCurrent, context);
      responseString.insert(idx, "\n"+StrutsUtil.getError4JavaScript(context,"error.record.locked",res.userName,res.creationTime,res.creationElapsedTime));
    }
    context.getResponse().getWriter().print(responseString.toString());
    //requestResult.toResponse( context.getResponse());
    return null;
  }
}
