package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.ContractorRequestPrintActForm;
import net.sam.dcl.form.ContractorRequestForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.report.pdf.ContractorRequestActPDF;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.dao.BlankDAO;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractorRequestPrintActAction extends DBTransactionAction
{
	public ActionForward execute(IActionContext context) throws Exception
	{
		ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		ContractorRequestPrintActForm form = (ContractorRequestPrintActForm) context.getForm();
		ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);
		setData(form, contractorRequest);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ContractorRequestActPDF actPdfPage = new ContractorRequestActPDF(out, form);
		actPdfPage.setScale(form.getPrintScale());
		actPdfPage.process();
		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition", " attachment;filename=\"ContractorRequestAct.pdf\"");
		context.getResponse().getOutputStream().write(out.toByteArray());
		out.close();
		return null;
	}

	private void setData(ContractorRequestPrintActForm form, ContractorRequest contractorRequest) throws Exception
	{
		form.setCrq_number(contractorRequest.getCrq_number());
		form.setCrq_ticket_number(contractorRequest.getCrq_ticket_number());
		form.setCrq_receive_date(contractorRequest.getCrq_receive_date());
		form.setContractor(contractorRequest.getContractor());
		form.setContactPerson(contractorRequest.getContactPerson());
		form.setContract(contractorRequest.getContract());
		form.setContractForWork(contractorRequest.getContractForWork());
		form.setCrq_equipment(contractorRequest.getCrq_equipment());
		form.setCtn_number(contractorRequest.getCtn_number());
		form.setStf_name(contractorRequest.getStf_name());
		form.setLps_serial_num(contractorRequest.getLps_serial_num());
		form.setLps_year_out(contractorRequest.getLps_year_out());

		IActionContext context = ActionContext.threadInstance();
		try
		{
			form.setCrq_seller(contractorRequest.getSeller().getName());
			form.setBlank(BlankDAO.load(context, Config.getString(Constants.commonBlankLight3)));
			//0 - ЗАО "Линтера", 1 - ИП "ЛинтераТехСервис", Прочие - печатаем имя Контрагента, иначе - друкие Продавцы
			if ("0".equals(contractorRequest.getSeller().getId()))
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.commonBlankLight1)));
			}
			if ("1".equals(contractorRequest.getSeller().getId()))
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.commonBlankLight2)));
			}
			if ("2".equals(contractorRequest.getSeller().getId()))
			{
				form.setCrq_seller(contractorRequest.getContractorOther().getFullname());
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.commonBlankLight3)));
			}
			if ("4".equals(contractorRequest.getSeller().getId()))
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankSmerkonaActPrint)));
			}
			if ("5".equals(contractorRequest.getSeller().getId()))
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankKropaActPrint)));
			}
			if ("6".equals(contractorRequest.getSeller().getId()))
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankLBTActPrint)));
			}
			if ("7".equals(contractorRequest.getSeller().getId()))
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankLATActPrint)));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		//после куска, что выше - иначе бланки неправильные.
		if (
						ContractorRequestForm.serviceId.equals(contractorRequest.getRequestType().getId()) ||
										ContractorRequestForm.guaranteeId.equals(contractorRequest.getRequestType().getId())
						)
		{
			try
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.defaultGuarantyServiceActBlank)));
			}
			catch (Exception e)
			{
				log.error(e);
			}
		}

		form.setManager(contractorRequest.getManager());
		form.setChief(contractorRequest.getChief());
		form.setSpecialist(contractorRequest.getSpecialist());
		form.setCrq_city(contractorRequest.getCrq_city());

		form.setStages(contractorRequest.getStages());

		form.setLps_enter_in_use_date(contractorRequest.getLps_enter_in_use_date());
		form.setShp_date(contractorRequest.getShp_date());
		form.setCon_number(contractorRequest.getCon_number());
		form.setCon_date(contractorRequest.getCon_date());
		form.setSpc_number(contractorRequest.getSpc_number());
		form.setSpc_date(contractorRequest.getSpc_date());
		form.setCon_seller_id(contractorRequest.getCon_seller_id());
		form.setCon_seller(contractorRequest.getCon_seller());

		form.setCrq_reclamation_date(contractorRequest.getCrq_reclamation_date());
		form.setCrq_lintera_request_date(contractorRequest.getCrq_lintera_request_date());
		form.setCrq_defect_act(contractorRequest.getCrq_defect_act());
		form.setCrq_reclamation_act(contractorRequest.getCrq_reclamation_act());
		form.setCrq_lintera_agreement_date(contractorRequest.getCrq_lintera_agreement_date());
		form.setVisitId(contractorRequest.getVisitId());
		if (form.isPrintSellerRequest() || form.isPrintSellerAgreement())
		{
			form.setContractorWhere(ContractorDAO.load(context, Config.getString(Constants.contractorLinteraRequestPrint)));
			form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankLinteraRequestPrint)));
			if ("4".equals(contractorRequest.getCon_seller_id())) //UAB "SMERKONA"
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankSmerkonaRequestPrint)));
			}
			if ("5".equals(contractorRequest.getCon_seller_id())) //ООО "Кропа Техсервис"
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankKropaRequestPrint)));
			}
			if ("6".equals(contractorRequest.getCon_seller_id())) //UAB "Linterа - baldu technologijos"
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankLBTRequestPrint)));
			}
			if ("7".equals(contractorRequest.getCon_seller_id())) //UAB “Lintera, automatika ir technika“
			{
				form.setBlank(BlankDAO.load(context, Config.getString(Constants.blankLATRequestPrint)));
			}
		}

		if (!ContractorRequestForm.pnpId.equals(contractorRequest.getRequestType().getId()) && !StringUtil.isEmpty(contractorRequest.getCrq_no_contract()))
		{
			form.setLps_serial_num(contractorRequest.getCrq_serial_num());
			form.setLps_year_out(contractorRequest.getCrq_year_out());
			form.setLps_enter_in_use_date(contractorRequest.getCrq_enter_in_use_date());
		}
		form.setCrq_operating_time(StringUtil.doubleToString(contractorRequest.getCrq_operating_time()));

		form.setService(ContractorRequestForm.serviceId.equals(contractorRequest.getRequestType().getId()));
		String printTMPTimeSheet = context.getRequest().getParameter("isPNPTimeSheet");
		if (!StringUtil.isEmpty(printTMPTimeSheet))
		{
			form.setPNPTimeSheet(Boolean.parseBoolean(printTMPTimeSheet));
			Integer index = 0;
			Cookie[] cookies = context.getRequest().getCookies();
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("index"))
				{
					index = Integer.parseInt(cookie.getValue());
					break;
				}
			}
			form.setVisitNumber(index);
		}
		else
		{
			form.setPNP(ContractorRequestForm.pnpId.equals(contractorRequest.getRequestType().getId()));
		}
		form.setPrintScale(StringUtil.appCurrencyString2double(contractorRequest.getActScale()).floatValue());
	}
}