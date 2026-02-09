package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.BlankDAO;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.form.CommercialProposalPrintForm;
import net.sam.dcl.report.pdf.CommercialProposalPDF;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.*;
import net.sam.dcl.dbo.DboAttachment;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CommercialProposalPrintAction extends DBTransactionAction
{
  final static String referencedTable = "DCL_USER";

  public ActionForward execute(IActionContext context) throws Exception
  {
    ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		CommercialProposalPrintForm form = (CommercialProposalPrintForm) context.getForm();

    CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
    setData(form, commercialProposal, context);
    DboAttachment imgFacsimile = null;
    if (form.isPrintFacsimile())
    {
      DeferredAttachmentService attachmentService =
              DeferredAttachmentService.create(context.getRequest().getSession(),
				              referencedTable, Integer.parseInt(form.getUser().getUsr_id()),
				              null, null);
      imgFacsimile = attachmentService.firstAttach();
    }
		ByteArrayOutputStream out = new ByteArrayOutputStream();
    Blank blank = commercialProposal.getBlank();
    try
    {
      if ( form.isInvoice() )
      {
        blank = BlankDAO.load(context, Config.getString(Constants.defaultCPInvoiceBlank));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    CommercialProposalPDF pdfPage = new CommercialProposalPDF(out, blank, form, imgFacsimile);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

		context.getResponse().setContentType("application/download");
    if ( form.isInvoice() )
    {
      context.getResponse().setHeader("Content-disposition"," attachment;filename=\"CommercialProposalInvoice.pdf\"");
    }
    else if ( form.isContract() )
    {
      context.getResponse().setHeader("Content-disposition"," attachment;filename=\"CommercialProposalContract.pdf\"");
    }
    else
    {
      context.getResponse().setHeader("Content-disposition"," attachment;filename=\"CommercialProposal.pdf\"");
    }
    context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }

  private void setData(CommercialProposalPrintForm form, CommercialProposal commercialProposal, IActionContext context) throws Exception
  {
    form.setAssembleMinsk(commercialProposal.isAssembleMinskStore());

    form.setContractor(commercialProposal.getContractor());
    form.setContactPerson(commercialProposal.getContactPerson());
    form.setDeliveryCondition(commercialProposal.getDeliveryCondition());
    form.setPriceCondition(commercialProposal.getPriceCondition());
    form.setLogoImage(commercialProposal.getCpr_img_name());
    form.setAdditionalInfo(commercialProposal.getCpr_add_info());
    if ( form.isInvoice() )
    {
      form.setConcering(form.isAssembleMinsk() ?
              StrutsUtil.getMessage(context, "CommercialProposal.cpr_concerning_invoice_assemble",
                                    commercialProposal.getCpr_number(),
                                    commercialProposal.getCpr_date()
              ) :
              commercialProposal.getCpr_concerning_invoice());
    }
    else
    {
      form.setConcering(commercialProposal.getCpr_concerning());
    }
    form.setCountry(commercialProposal.getCpr_country());
    form.setDate(commercialProposal.getCpr_date());
    form.setDateFullFormat(StringUtil.appDateString2appFullDateFormat(commercialProposal.getCpr_date()));
    form.setDeliveryAddress(commercialProposal.getCpr_delivery_address());
    if ( form.isInvoice() )
    {
      form.setPurchasePurpose(commercialProposal.getPurchasePurpose());
      if ( !StringUtil.isEmpty(commercialProposal.getCpr_can_edit_invoice()) )
      {
        form.setDeliveryTerm(commercialProposal.getCpr_delivery_term_invoice());
        form.setPayCondition(commercialProposal.getCpr_pay_condition_invoice());
        form.setFinalDate(commercialProposal.getCpr_final_date_invoice());
      }
      else
      {
        form.setDeliveryTerm(commercialProposal.getCpr_delivery_term() + " " + StrutsUtil.getMessage(context, "CommercialProposal.delivery_date_invoice"));
        form.setPayCondition(StrutsUtil.getMessage(context, "CommercialProposal.pay_cond_invoice"));
        form.setFinalDate(StrutsUtil.getMessage(context, "CommercialProposal.end_date_invoice"));
      }
    }
    else
    {
      form.setDeliveryTerm(commercialProposal.getCpr_delivery_term());
      form.setPayCondition(commercialProposal.getCpr_pay_condition());
      form.setFinalDate(commercialProposal.getCpr_final_date());
      User user = UserUtil.getCurrentUser(context.getRequest());
      form.setPrintFacsimile(!StringUtil.isEmpty(commercialProposal.getFacsimile_flag()) && user.getUsr_id().equals(commercialProposal.getUser().getUsr_id()));
    }
    form.setNumber(commercialProposal.getCpr_number());
    form.setPreamble(commercialProposal.getCpr_preamble());
    form.setCreateUser(commercialProposal.getCreateUser());
    form.setUser(commercialProposal.getUser());
    form.setExecutor(commercialProposal.getExecutor());
    form.setContactPerson(commercialProposal.getContactPerson());
    form.setNdsByString(commercialProposal.getCpr_nds_by_string());
    form.setNDS(commercialProposal.getCpr_nds_formatted());
    form.setCurrency(commercialProposal.getCurrency());
    form.setPrintUnit(!StringUtil.isEmpty(commercialProposal.getShow_unit()) && StringUtil.isEmpty(commercialProposal.getCpr_old_version()) && form.isInvoice());
    form.setPrintExecutor(!StringUtil.isEmpty(commercialProposal.getCpr_executor_flag()));
    form.setPrintPriceListBy(commercialProposal.isPrintPriceListBy());

    if ( form.isAssembleMinsk() )
    {
      form.setGuarantyInMonth(commercialProposal.getCpr_guaranty_in_month());
      form.setPrepayPercent(commercialProposal.getCpr_prepay_percent());
      form.setPrepaySum(commercialProposal.getCpr_prepay_sum());
      form.setFinalDateFullFormat(commercialProposal.getFinalDateFullFormat());
      form.setDelayDays(commercialProposal.getCpr_delay_days());
      form.setDelayDaysInWords(commercialProposal.getDelayDaysInWords());
      form.setTotalPrint(commercialProposal.getTotalPrint());
      form.setNdsPrint(commercialProposal.getNdsPrint());
      form.setContractorSeller(ContractorDAO.load(context, Config.getString(Constants.contractorLTS)));
      form.setContactPersonSeller(commercialProposal.getContactPersonSeller());
      form.setContactPersonCustomer(commercialProposal.getContactPersonCustomer());
      form.setPurchasePurpose(commercialProposal.getPurchasePurpose());
      if ( form.isInvoice() || form.isContract() )
      {
        form.setPrintUnit(true); //для Минска печатаем в любом случае для с-ф и контракта
      }
      form.setProviderDelivery(commercialProposal.isProviderDelivery());
      form.setProviderDeliveryAddress(commercialProposal.getCpr_provider_delivery_address());
      form.setDeliveryCountDay(commercialProposal.getCpr_delivery_count_day());
      form.setDeliveryCountDayInWords(commercialProposal.getDeliveryCountDayInWords());
      form.setConsignee(commercialProposal.getConsignee());
    }
    if (form.isInvoice())
    {
      form.setTotalPrint(commercialProposal.getTotalPrint());
    }

    boolean printCatalogNumber = false;
    for (int i = 0; i < commercialProposal.getProduces().size() - commercialProposal.getCountItogRecord(); i++)
    {
      CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
      CommercialProposalPrintForm.Produce produce;
      if (commercialProposal.incoTermCaseA())
      {
        produce = new CommercialProposalPrintForm.Produce(commercialProposalProduce.getProduceNamePrint(),
                                                                       commercialProposalProduce.getCatalog_num(),
                                                                       commercialProposalProduce.getUnit(),
                                                                       commercialProposalProduce.getSale_price_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getLpr_count_formatted(),
                                                                       commercialProposalProduce.getOutValueFormated(commercialProposal.getCurrency().isNeedRound()),
                                                                       "",
                                                                       "",
                                                                       commercialProposalProduce.getLpc_price_list_by_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getDiscount_percent_formatted());
      }
      else if (commercialProposal.incoTermCaseB())
      {
        produce = new CommercialProposalPrintForm.Produce(commercialProposalProduce.getProduceNamePrint(),
                                                                       commercialProposalProduce.getCatalog_num(),
                                                                       commercialProposalProduce.getUnit(),
                                                                       commercialProposalProduce.getSale_price_parking_trans_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getLpr_count_formatted(),
                                                                       commercialProposalProduce.getOutValueFormated(commercialProposal.getCurrency().isNeedRound()),
                                                                       "",
                                                                       "",
                                                                       commercialProposalProduce.getLpc_price_list_by_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getDiscount_percent_formatted());
      }
      else if (commercialProposal.incoTermCaseC())
      {
        produce = new CommercialProposalPrintForm.Produce(commercialProposalProduce.getProduceNamePrint(),
                                                                       commercialProposalProduce.getCatalog_num(),
                                                                       commercialProposalProduce.getUnit(),
                                                                       commercialProposalProduce.getSale_price_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getLpr_count_formatted(),
                                                                       commercialProposalProduce.getOutValueFormated(commercialProposal.getCurrency().isNeedRound()),
                                                                       "",
                                                                       "",
                                                                       commercialProposalProduce.getLpc_price_list_by_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getDiscount_percent_formatted());
      }
      else if (commercialProposal.incoTermCaseD())
      {
        produce = new CommercialProposalPrintForm.Produce(commercialProposalProduce.getProduceNamePrint(),
                                                                       commercialProposalProduce.getCatalog_num(),
                                                                       commercialProposalProduce.getUnit(),
                                                                       commercialProposalProduce.getSale_price_parking_trans_custom_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getLpr_count_formatted(),
                                                                       commercialProposalProduce.getOutValueFormated(commercialProposal.getCurrency().isNeedRound()),
                                                                       "",
                                                                       "",
                                                                       commercialProposalProduce.getLpc_price_list_by_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getDiscount_percent_formatted());
      }
      else if (commercialProposal.incoTermCaseE())
      {
        produce = new CommercialProposalPrintForm.Produce(commercialProposalProduce.getProduceNamePrint(),
                                                                       commercialProposalProduce.getCatalog_num(),
                                                                       commercialProposalProduce.getUnit(),
                                                                       commercialProposalProduce.getSale_price_parking_trans_custom_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getLpr_count_formatted(),
                                                                       commercialProposalProduce.getOutValueFormated(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getNds_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getCost_nds_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getLpc_price_list_by_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                       commercialProposalProduce.getDiscount_percent_formatted());
      }
      else
      {
        produce = new CommercialProposalPrintForm.Produce();  
      }
      produce.setNumber1C(commercialProposalProduce.getLpc_1c_number());
      form.getProduces().add(produce);

      /*
       * Печатная форма с-ф
       * Если колонка "каталожный номер" пустая для всех позиций, то сделать так, чтобы она не выводилась на печать
      * */
      if ( form.isInvoice() )
      {
        if ( !StringUtil.isEmpty(commercialProposalProduce.getCatalog_num()) )
        {
          printCatalogNumber = true;
        }
      }
    }

    /*
     * Печатная форма с-ф
     * Если колонка "каталожный номер" пустая для всех позиций, то сделать так, чтобы она не выводилась на печать
    * */
    if ( form.isInvoice() )
    {
      form.setPrintCatalogNumber(printCatalogNumber);
    }

    for (int i = commercialProposal.getProduces().size() + 1 - commercialProposal.getCountItogRecord(); i < commercialProposal.getProduces().size(); i++)
    {
      CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
      form.getAdditionalRows().put(commercialProposalProduce.getTotalText(),
                                   new CommercialProposalPrintForm.AddRow(
                                                                          commercialProposalProduce.getOutValueFormated(commercialProposal.getCurrency().isNeedRound()),
                                                                          commercialProposalProduce.getNds_print_formatted(commercialProposal.getCurrency().isNeedRound()),
                                                                          commercialProposalProduce.getCost_nds_print_formatted(commercialProposal.getCurrency().isNeedRound())
                                                                         )
                                  );
    }

    form.setHideFinalDate(form.isInvoice() && form.isAssembleMinsk() && commercialProposal.getCpr_prepay_percent() == 0);
    form.setFinalDateAbove(commercialProposal.isFinalDateAbove());

    form.setPrintScale(StringUtil.appCurrencyString2double(commercialProposal.getCpr_print_scale()).floatValue());
    if ( form.isContract() )
    {
      form.setPrintScale(StringUtil.appCurrencyString2double(commercialProposal.getCpr_contract_scale()).floatValue());
    }
    if ( form.isInvoice() )
    {
      form.setPrintScale(StringUtil.appCurrencyString2double(commercialProposal.getCpr_invoice_scale()).floatValue());
    }
  }
}
