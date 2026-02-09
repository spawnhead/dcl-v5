package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.DeliveryRequestPrintForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.report.pdf.DeliveryRequestPDF;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.dao.UserDAO;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Order: pww
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryRequestPrintAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
		ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		DeliveryRequestPrintForm form = (DeliveryRequestPrintForm) context.getForm();
    DeliveryRequest order = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);

    setData(context, form, order);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DeliveryRequestPDF pdfPage = new DeliveryRequestPDF(out, form);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition"," attachment;filename=\"DeliveryRequest.pdf\"");

    context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }

  private void setData(IActionContext context, DeliveryRequestPrintForm form, DeliveryRequest deliveryRequest) throws Exception
  {
    form.setDlr_number(deliveryRequest.getDlr_number());
    if ( !StringUtil.isEmpty(deliveryRequest.getPlaceUser().getUsr_id()) )
    {
      form.setPlaceUser(UserDAO.load(context, deliveryRequest.getPlaceUser().getUsr_id()));
    }
    //некоторые пользователи не привязаны к отделу
    if ( !StringUtil.isEmpty(deliveryRequest.getPlaceUser().getDepartment().getId()) )
    {
      form.setChiefDep(UserDAO.loadUserByDepartNameChief(context, deliveryRequest.getPlaceUser().getDepartment()));
    }
    form.setUsr_date_place(deliveryRequest.getUsr_date_place());
		form.setDlr_minsk(deliveryRequest.getDlr_minsk());
    form.setDlr_wherefrom(deliveryRequest.getDlr_wherefrom());
    form.setDlr_comment(deliveryRequest.getDlr_comment());
		form.setDlr_place_request(deliveryRequest.getDlr_place_request());
		form.setDlr_include_in_spec(deliveryRequest.getDlr_include_in_spec());
		form.setDlr_need_deliver(deliveryRequest.getDlr_need_deliver());
		form.setDlr_ord_not_form(deliveryRequest.getDlr_ord_not_form());
		form.setNumDateOrdShowForFairTrade(deliveryRequest.isNumDateOrdShowForFairTrade());
    form.setProduces(deliveryRequest.getProduces());

    form.setPrintScale(StringUtil.appCurrencyString2double(deliveryRequest.getPrintScale()).floatValue());
  }
}
