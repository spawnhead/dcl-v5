package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.ContractorRequestPrintLetterRequestForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.report.pdf.ContractorRequestLetterRequestPDF;
import net.sam.dcl.filters.ResponseCollectFilter;
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
public class ContractorRequestPrintLetterRequestAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		ContractorRequestPrintLetterRequestForm form = (ContractorRequestPrintLetterRequestForm) context.getForm();

    ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);

    setData(context, form, contractorRequest);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
    ContractorRequestLetterRequestPDF pdfPage = new ContractorRequestLetterRequestPDF(out, form);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition"," attachment;filename=\"ContractorRequest.pdf\"");
		context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }

  private void setData(IActionContext context, ContractorRequestPrintLetterRequestForm form, ContractorRequest contractorRequest) throws Exception
  {
    form.setContractor(contractorRequest.getContractor());
    form.setContract(contractorRequest.getContract());
    form.setCrq_equipment(contractorRequest.getCrq_equipment());
    form.setCtn_number(contractorRequest.getCtn_number());
    form.setStf_name(contractorRequest.getStf_name());
    form.setBlank(BlankDAO.load(context, Config.getString(Constants.letterRequestBlank)));
    form.setChief(contractorRequest.getChief());
    form.setPrintScale(StringUtil.appCurrencyString2double(contractorRequest.getLetterScale()).floatValue());
  }
}