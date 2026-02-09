package net.sam.dcl.action;

import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.hibernate.criterion.Order;

import java.util.List;

public class WoodworkWorkFilesActionBean extends AttachmentsActionBean
{
  protected static Log log = LogFactory.getLog(WoodworkWorkFilesActionBean.class);
  boolean attachReadonly = false;

  public WoodworkWorkFilesActionBean()
  {
    setReferencedTable("DCL_WOODWORK_WORK_FILES");
    setReferencedID(0);
  }

  @Override
  public ActionForward show() throws Exception
  {
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    grid = new HolderImplUsingList();
    List<DboAttachment> attachmentList = attachmentsService.list(referencedTable, referencedID, Order.desc("createDate"));
    grid.setDataList(attachmentList);

    return internalShow();
  }
}