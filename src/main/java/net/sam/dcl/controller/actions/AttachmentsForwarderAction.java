package net.sam.dcl.controller.actions;

import org.apache.struts.action.ActionForward;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;

/**
 * @author DG
 *         Date: 10-Jan-2008
 *         Time: 22:47:46
 */
public class AttachmentsForwarderAction extends BaseAction{
	private static final String ATTACHMENT_BACK="ATTACHMENT_BACK";
	private static final String REFERENCED_ID = "referencedID";
	private static final String REFERENCED_TABLE = "referencedTable";

	public ActionForward execute(IActionContext context) throws Exception {
		StoreUtil.putSession(context.getRequest(),context.getMapping().findForward("back"),ATTACHMENT_BACK);
		return createAttachUrl(context, context.getMapping().getInputForward(),context.getMapping().getParameter(),context.getRequest().getParameter(REFERENCED_ID));
	}

	static public ActionForward createAttachUrl(IActionContext context, ActionForward forward,String refTable, String refId) {
		String path = forward.getPath();
		path = StringUtil.addToURL(path, REFERENCED_TABLE,refTable);
		path = StringUtil.addToURL(path, REFERENCED_ID,refId);
		return new ActionForward(path,true);
	}

	public static void saveAttachmentsBack(IActionContext context){
		StoreUtil.putSession(context.getRequest(),context.getMapping().findForward("back"),ATTACHMENT_BACK);
	}
	public static ActionForward getAttachmentsBack(IActionContext context){
		ActionForward res = (ActionForward) StoreUtil.getSession(context.getRequest(), ATTACHMENT_BACK);
		StoreUtil.putSession(context.getRequest(),null,ATTACHMENT_BACK);
		return res;
	}
}
