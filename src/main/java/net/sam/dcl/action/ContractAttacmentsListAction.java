package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.StoreUtil;

import java.util.*;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class ContractAttacmentsListAction extends ListAction {
  public Object listExecute(IActionContext context) throws Exception {
    Map result = new LinkedHashMap<Integer,String>();
    DeferredAttachmentService contractAttachmentService = (DeferredAttachmentService) StoreUtil.getSession(context.getRequest(), ContractAction.CONTRACT_ATTACHMENT_SERVICE);
    List<DeferredAttachmentService.DeferredAttachment> attachmentList = contractAttachmentService.list();
    for (DeferredAttachmentService.DeferredAttachment attachment : attachmentList) {
      result.put(attachment.getIdx(),attachment.getOriginalFileName());
    }
    return result;
  }
}