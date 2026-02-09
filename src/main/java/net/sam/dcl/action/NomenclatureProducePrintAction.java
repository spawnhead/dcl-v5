package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.report.pdf.TechnicalDescriptionPDF;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.dao.BlankDAO;
import net.sam.dcl.dbo.DboAttachment;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class NomenclatureProducePrintAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
    NomenclatureProduceActionBean produceActionBean = (NomenclatureProduceActionBean) StoreUtil.getSession(context.getRequest(), NomenclatureProduceActionBean.class);
    produceActionBean.setPrintTo(false);

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    User user = UserUtil.getCurrentUser(context.getRequest());
    DboAttachment imgAttachment = null;
    if ("1".equals(produceActionBean.getDoPrintTo()))
    {
      Session session = null;
      try
      {
        session = HibernateUtil.getSessionFactory().openSession();
        DeferredAttachmentService attachmentsService = DeferredAttachmentService.get(context.getRequest().getSession());
        imgAttachment = attachmentsService.find(produceActionBean.getSelectedImage());
      }
      finally
      {
        if (session != null) session.close();
        produceActionBean.setSelectedImage(null);
      }

    }
    TechnicalDescriptionPDF pdfPage = new TechnicalDescriptionPDF(out, BlankDAO.load(context, String.valueOf(produceActionBean.getBlank().getBln_id())), produceActionBean, user, imgAttachment);
    pdfPage.setScale(StringUtil.appCurrencyString2double(produceActionBean.getPrintScale()).floatValue());
    pdfPage.process();

    context.getResponse().setContentType("application/download");
    context.getResponse().setHeader("Content-disposition", " attachment;filename=\"NomenclatureProduce.pdf\"");

    context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }
}