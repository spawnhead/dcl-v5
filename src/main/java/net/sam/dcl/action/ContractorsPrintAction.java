package net.sam.dcl.action;

import net.sam.dcl.beans.ContractorsPrint;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.form.ContractorsPrintForm;
import net.sam.dcl.report.pdf.ContractorsPDF;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.dao.ContractorDAO;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractorsPrintAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
		ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		ContractorsPrintForm form = (ContractorsPrintForm) context.getForm();
    setData(context, form);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
    ContractorsPDF pdfPage = new ContractorsPDF(out, form);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition"," attachment;filename=\"Contractors.pdf\"");

		context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }

  private void setData(IActionContext context, ContractorsPrintForm form) throws Exception
  {
    ContractorsPrint contractorsPrint = (ContractorsPrint) StoreUtil.getSession(context.getRequest(), ContractorsPrint.class);
    form.setPrintScale(StringUtil.appCurrencyString2double(contractorsPrint.getPrintScale()).floatValue());

    List<Contractor> contractors = new ArrayList<Contractor>();
    for (String ctrId : contractorsPrint.getSelectedIds())
    {
      Contractor contractor = ContractorDAO.load(context, ctrId);
      contractors.add(contractor);
    }
    form.setContractors(contractors);
  }
}