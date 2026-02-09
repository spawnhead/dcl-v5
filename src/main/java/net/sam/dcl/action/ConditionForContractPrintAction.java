package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.form.ConditionForContractPrintForm;
import net.sam.dcl.beans.ConditionForContract;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.report.pdf.ConditionForContractPDF;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ConditionForContractPrintAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
    ConditionForContractPrintForm form = (ConditionForContractPrintForm) context.getForm();
    ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);

    setData(form, conditionForContract);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ConditionForContractPDF pdfPage = new ConditionForContractPDF(out, form);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

    context.getResponse().setContentType("application/download");
    context.getResponse().setHeader("Content-disposition", " attachment;filename=\"ConditionForContract.pdf\"");

    context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }

  private void setData(ConditionForContractPrintForm form, ConditionForContract conditionForContract) throws Exception
  {
    form.setCreateUser(conditionForContract.getCreateUser());
    form.setEditUser(conditionForContract.getEditUser());
    form.setPlaceUser(conditionForContract.getPlaceUser());
    form.setExecuteUser(conditionForContract.getExecuteUser());
    form.setUsr_date_create(conditionForContract.getUsr_date_create());
    form.setUsr_date_edit(conditionForContract.getUsr_date_edit());
    form.setUsr_date_place(conditionForContract.getUsr_date_place());
    form.setUsr_date_execute(conditionForContract.getUsr_date_execute());
    form.setCfc_place(conditionForContract.getCfc_place());
    form.setCfc_execute(conditionForContract.getCfc_execute());
    form.setSeller(conditionForContract.getSeller());
    form.setCfc_doc_type(conditionForContract.getCfc_doc_type());
    form.setContractor(conditionForContract.getContractor());
    form.setContract(conditionForContract.getContract());
    form.setCurrency(conditionForContract.getCurrency());
    form.setContactPersonSign(conditionForContract.getContactPersonSign());
    form.setContactPerson(conditionForContract.getContactPerson());
    form.setCfc_con_number_txt(conditionForContract.getCfc_con_number_txt());
    form.setCfc_con_date(conditionForContract.getCfc_con_date());
    form.setSpc_numbers(conditionForContract.getSpc_numbers());
    form.setCfc_spc_number_txt(conditionForContract.getCfc_spc_number_txt());
    form.setCfc_spc_date(conditionForContract.getCfc_spc_date());
    form.setCfc_pay_cond(conditionForContract.getCfc_pay_cond());
    form.setCfc_custom_point(conditionForContract.getCfc_custom_point());
    form.setCfc_delivery_cond(conditionForContract.getCfc_delivery_cond());
    form.setCfc_guarantee_cond(conditionForContract.getCfc_guarantee_cond());
    form.setCfc_montage_cond(conditionForContract.getCfc_montage_cond());
    form.setCfc_date_con_to(conditionForContract.getCfc_date_con_to());
    form.setCfc_count_delivery(conditionForContract.getCfc_count_delivery());
    form.setPurchasePurpose(conditionForContract.getPurchasePurpose());

    form.setProduces(conditionForContract.getProduces());

    form.setPrintScale(StringUtil.appCurrencyString2double(conditionForContract.getPrintScale()).floatValue());
  }
}
