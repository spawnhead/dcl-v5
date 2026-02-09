package net.sam.dcl.action;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.beans.User;
import net.sam.dcl.util.UserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * @author DG
 *         Date: 08-Jan-2008
 *         Time: 20:51:01
 */
public class GoodsRestLithuaniaActionBean extends AttachmentsActionBean
{
  protected static Log log = LogFactory.getLog(GoodsRestLithuaniaActionBean.class);
  boolean attachReadonly = false;

  public GoodsRestLithuaniaActionBean()
  {
    setReferencedTable("DCL_GOODS_IN_LITHUANIA");
    setReferencedID(0);
  }

  @Override
  public ActionForward show() throws Exception
  {
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    grid = new HolderImplUsingList();
    List<DboAttachment> attachmentList = attachmentsService.list(referencedTable, referencedID, Order.desc("createDate"));
    for (int idx = attachmentList.size() - 1; idx > 4; idx--)
    {
      attachmentsService.delete(attachmentList.get(idx));
      attachmentList.remove(idx);
    }
    grid.setDataList(attachmentList);

    final User user = UserUtil.getCurrentUser(request);
    request.setAttribute("readOnlyChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return user.isLogistic();
      }
    });
    setAttachReadonly(user.isLogistic());

    return internalShow();
  }

  public boolean isAttachReadonly()
  {
    return attachReadonly;
  }

  public void setAttachReadonly(boolean attachReadonly)
  {
    this.attachReadonly = attachReadonly;
  }
}