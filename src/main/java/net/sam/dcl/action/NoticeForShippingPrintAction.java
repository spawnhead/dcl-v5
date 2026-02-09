package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.form.NoticeForShippingPrintForm;
import net.sam.dcl.beans.Shipping;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.report.pdf.NoticeForShippingPDF;
import net.sam.dcl.dao.BlankDAO;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class NoticeForShippingPrintAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
		ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		NoticeForShippingPrintForm form = (NoticeForShippingPrintForm) context.getForm();
    Shipping shipping = (Shipping) StoreUtil.getSession(context.getRequest(), Shipping.class);

    setData(form, shipping);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
    NoticeForShippingPDF pdfPage = new NoticeForShippingPDF(out, BlankDAO.load(context, Config.getString(Constants.letterHeadNoticeForShippingName)), form);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition"," attachment;filename=\"NoticeForShipping.pdf\"");
		context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    
    return null;
  }

  private void setData(NoticeForShippingPrintForm form, Shipping shipping) throws Exception
  {
    form.setShp_id(shipping.getShp_id());
    form.setShp_number(shipping.getShp_number());
    form.setShp_date(shipping.getShp_date());
    form.setContractor(shipping.getContractor());
    form.setContractorWhere(shipping.getContractorWhere());
    form.setContractWhere(shipping.getContractWhere());
    form.setShp_notice_date(shipping.getShp_notice_date());
    form.setProduces(shipping.getShippingPositions());

    form.setPrintScale(StringUtil.appCurrencyString2double(shipping.getNoticeScale()).floatValue());
  }
}
