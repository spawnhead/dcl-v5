package net.sam.dcl.beans;

import net.sam.dcl.config.Config;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.SellerDAO;
import net.sam.dcl.form.SpecificationForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Contract implements Serializable
{
	protected static Log log = LogFactory.getLog(Contract.class);
	String is_new_doc;

	String con_id;

	User createUser = new User();
	User editUser = new User();
	String usr_date_create;
	String usr_date_edit;

	String con_number;
	String con_date;
	String con_reusable;
	String con_final_date;
	Contractor contractor = new Contractor();
	Currency currency = new Currency();
	double con_summ;
	String con_executed;
	String con_occupied;
	String con_original;
	Seller seller = new Seller();
	String con_annul;
	String con_annul_date;
	String con_comment;

	List<Specification> specifications = new ArrayList<Specification>();

	public Contract()
	{
	}

	public Contract(String con_id, String con_number)
	{
		this.con_id = con_id;
		this.con_number = con_number;
	}

	public Contract(String con_id)
	{
		this.con_id = con_id;
	}

	public Contract(Contract contract)
	{
		this.con_id = contract.getCon_id();
		this.createUser = new User(contract.getCreateUser());
		this.editUser = new User(contract.getEditUser());
		this.usr_date_create = contract.getUsr_date_create();
		this.usr_date_edit = contract.getUsr_date_edit();

		this.con_number = contract.getCon_number();
		this.con_date = contract.getCon_date();
		this.con_reusable = contract.getCon_reusable();
		this.con_final_date = contract.getCon_final_date();
		this.contractor = new Contractor(contract.getContractor());
		this.currency = new Currency(contract.getCurrency());
		this.con_summ = contract.getCon_summ();
		this.con_executed = contract.getCon_executed();
		this.con_occupied = contract.getCon_occupied();
		this.con_original = contract.getCon_original();
		this.seller = new Seller(contract.getSeller());
		this.con_annul = contract.getCon_annul();
		this.con_annul_date = contract.getCon_annul_date();
		this.con_comment = contract.getCon_comment();
	}

	public User getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(User createUser)
	{
		this.createUser = createUser;
	}

	public User getEditUser()
	{
		return editUser;
	}

	public void setEditUser(User editUser)
	{
		this.editUser = editUser;
	}

	public String getIs_new_doc()
	{
		return is_new_doc;
	}

	public void setIs_new_doc(String is_new_doc)
	{
		this.is_new_doc = is_new_doc;
	}

	public String getCon_id()
	{
		return con_id;
	}

	public void setCon_id(String con_id)
	{
		this.con_id = con_id;
	}

	public String getUsr_date_create()
	{
		return usr_date_create;
	}

	public void setUsr_date_create(String usr_date_create)
	{
		this.usr_date_create = usr_date_create;
	}

	public String getUsr_date_edit()
	{
		return usr_date_edit;
	}

	public void setUsr_date_edit(String usr_date_edit)
	{
		this.usr_date_edit = usr_date_edit;
	}

	public String getCon_number()
	{
		return con_number;
	}

	public void setCon_number(String con_number)
	{
		this.con_number = con_number;
	}

	public String getCon_date()
	{
		return con_date;
	}

	public String getCon_date_formatted()
	{
		return StringUtil.dbDateString2appDateString(con_date);
	}

	public void setCon_date(String con_date)
	{
		this.con_date = con_date;
	}

	public void setCon_date_formatted(String con_date)
	{
		this.con_date = StringUtil.appDateString2dbDateString(con_date);
	}

	public String getCon_reusable()
	{
		return con_reusable;
	}

	public void setCon_reusable(String con_reusable)
	{
		this.con_reusable = con_reusable;
	}

	public String getCon_final_date()
	{
		return con_final_date;
	}

	public String getCon_final_date_formatted()
	{
		return StringUtil.dbDateString2appDateString(con_final_date);
	}

	public void setCon_final_date(String con_final_date)
	{
		this.con_final_date = con_final_date;
	}

	public void setCon_final_date_formatted(String con_final_date)
	{
		this.con_final_date = StringUtil.appDateString2dbDateString(con_final_date);
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public double getCon_summ()
	{
		return con_summ;
	}

	public void setCon_summ(double con_summ)
	{
		this.con_summ = con_summ;
	}

	public String getCon_executed()
	{
		return con_executed;
	}

	public void setCon_executed(String con_executed)
	{
		this.con_executed = con_executed;
	}

	public String getCon_occupied()
	{
		return con_occupied;
	}

	public void setCon_occupied(String con_occupied)
	{
		this.con_occupied = con_occupied;
	}

	public String getCon_original()
	{
		return con_original;
	}

	public void setCon_original(String con_original)
	{
		this.con_original = con_original;
	}

	public boolean isOriginal()
	{
		//1 - "Оригинал"
		return "1".equals(getCon_original());
	}

	public boolean isCopy()
	{
		//0 - "Факсовая и т.п. копия"
		return "0".equals(getCon_original());
	}

	public boolean isProject()
	{
		//пусто - "Проект"
		return StringUtil.isEmpty(getCon_original());
	}

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public String getCon_annul()
	{
		return con_annul;
	}

	public void setCon_annul(String con_annul)
	{
		this.con_annul = con_annul;
	}

	public String getCon_annul_date()
	{
		return con_annul_date;
	}

	public String getConAnnulDateFormatted()
	{
		return StringUtil.dbDateString2appDateString(con_annul_date);
	}

	public void setCon_annul_date(String con_annul_date)
	{
		this.con_annul_date = con_annul_date;
	}

	public void setConAnnulDateFormatted(String con_annul_date)
	{
		this.con_annul_date = StringUtil.appDateString2dbDateString(con_annul_date);
	}

	public String getCon_comment()
	{
		return con_comment;
	}

	public void setCon_comment(String con_comment)
	{
		this.con_comment = con_comment;
	}

	public List<Specification> getSpecifications()
	{
		return specifications;
	}

	public void setSpecifications(List<Specification> specifications)
	{
		this.specifications = specifications;
	}

	public void calculateInString(List<Specification> lstSpecifications)
	{
		List<Specification> lstSpec = specifications;
		if (null != lstSpecifications)
		{
			lstSpec = lstSpecifications;
		}

		for (Specification specification : lstSpec)
		{
			double sum = StringUtil.roundN(specification.getSpc_summ(), 2);
			double sumNDS = StringUtil.roundN(specification.getSpc_summ_nds(), 2);
			if (sum - sumNDS == 0)
			{
				specification.setSpc_nds_rate(Double.NaN);
			}
			else
			{
				specification.setSpc_nds_rate(sum / (sum - sumNDS) * 100 - 100);
			}
		}
	}

	public Specification getEmptySpecification()
	{
		Specification specification = new Specification();
		specification.setSpc_id("");
		specification.setSpc_number("");
		specification.setSpc_date("");
		specification.setSpc_summ(0.0);
		specification.setSpc_summ_nds(0.0);
		specification.setSpc_executed("");
		specification.setAttachmentsCount("0");

		return specification;
	}

	public void calculate(List<Specification> lstSpecifications)
	{
		List<Specification> lstSpec = specifications;
		if (null != lstSpecifications)
		{
			lstSpec = lstSpecifications;
		}

		calculateInString(lstSpec);
	}

	public double calculateTotalSum()
	{
		double total = 0;

		for (Specification specification : specifications)
		{
			//Если у договора есть аннулированная спека, то её сумму не включать в общую сумму договора.
			if (StringUtil.isEmpty(specification.getSpc_annul()))
			{
				total += specification.getSpc_summ();
			}
		}

		return total;
	}

	public boolean findNotExecutedSpecification()
	{
		for (Specification specification : specifications)
		{
			if (!"1".equalsIgnoreCase(specification.getSpc_executed()))
				return true;
		}

		return false;
	}

	public void setListParentIds()
	{
		for (Specification specification : specifications)
		{
			specification.setCon_id(getCon_id());
		}
	}

	public void setListIdsToNull()
	{
		for (Specification specification : specifications)
		{
			specification.setSpc_id(null);
		}
	}

	public Specification findSpecification(String number)
	{
		for (Specification specification : specifications)
		{
			if (specification.getSpc_number().equalsIgnoreCase(number))
				return specification;
		}

		return null;
	}

	public void updateSpecification(String number, Specification specificationIn)
	{
		for (int i = 0; i < specifications.size(); i++)
		{
			Specification specification = specifications.get(i);

			if (specification.getSpc_number().equalsIgnoreCase(number))
			{
				specifications.set(i, specificationIn);
				return;
			}
		}
	}

	public void deleteSpecification(String number)
	{
		for (int i = 0; i < specifications.size(); i++)
		{
			Specification specification = specifications.get(i);

			if (specification.getSpc_number().equalsIgnoreCase(number))
				specifications.remove(i);
		}
	}

	public void insertSpecification(Specification specification)
	{
		specifications.add(specifications.size(), specification);
	}

	public String getNumberWithDate()
	{
		if (StringUtil.isEmpty(getCon_number()))
		{
			return "";
		}

		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (StrutsUtil.getMessage(context, "list.all_id").equals(getCon_id()))
			{
				return StrutsUtil.getMessage(context, "list.all");
			}

			return StrutsUtil.getMessage(context, "msg.common.from", getCon_number(), getCon_date_formatted());
		}
		catch (Exception e)
		{
			log.error(e);
		}
		return "";
	}

	public String getFullContractInfo()
	{
		String retStr = getNumberWithDate();
		if (!StringUtil.isEmpty(getCon_annul()))
		{
			retStr += getAnnulStr();
		}
		if (!StringUtil.isEmpty(getCon_reusable()))
		{
			retStr += getReusableStr();
		}
		return retStr;
	}

	public String getReusableStr()
	{
		String retStr = "";
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (!StringUtil.isEmpty(getCon_reusable()))
			{
				retStr += " " + StrutsUtil.getMessage(context, "Contract.conReusableFinalDate", getCon_final_date_formatted());
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
		return retStr;
	}

	public String getAnnulStr()
	{
		String retStr = "";
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (!StringUtil.isEmpty(getCon_annul()))
			{
				retStr += " " + StrutsUtil.getMessage(context, "Contract.conAnnul");
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
		return retStr;
	}

	public String getNumberWithDateAndReusable()
	{
		String retStr = getNumberWithDate();
		if (!StringUtil.isEmpty(getCon_reusable()))
		{
			retStr += getReusableStr();
		}
		return retStr;
	}

	/**
	 * Сделать так, чтобы нельзя было аннулировать договор, если у него есть хотя бы одна закрытая спека.
	 * Сделать так, чтобы нельзя было аннулировать договор, если у него есть хотя бы
	 * одна спека, к которой привязана отгрузка или оплата.
	 *
	 * @return true если может быть аннулирован договор
	 */
	public boolean isCanAnnul()
	{
		for (Specification specification : specifications)
		{
			if (!StringUtil.isEmpty(specification.getSpc_executed()) || !StringUtil.isEmpty(specification.getSpc_occupied_in_pay_shp()))
				return false;
		}

		return true;
	}

	public void importFromCP(CommercialProposal commercialProposal)
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			commercialProposal.calculate();
			setCon_number(commercialProposal.getCpr_number());
			setCon_date(StringUtil.appDateString2dbDateString(commercialProposal.getCpr_date()));
			setContractor(commercialProposal.getContractor());
			setCurrency(commercialProposal.getCurrency());
			if (commercialProposal.isAssembleMinskStore())
			{
				setSeller(SellerDAO.load(context, Config.getString(Constants.cpToContractImportAsmMinskSeller)));
			}

			Specification specification = new Specification();
			specification.setSpcFromCpr(true);
			specification.setUser(commercialProposal.getCreateUser());
			specification.setSpc_number("1");
			specification.setSpc_date(StringUtil.appDateString2dbDateString(commercialProposal.getCpr_date()));
			specification.setSpc_summ(commercialProposal.getTotalPrint());
			specification.setSpc_summ_nds(commercialProposal.getNdsPrint());
			specification.setAttachmentsCount("0");
			if (commercialProposal.getCpr_prepay_percent() == 0)
			{
				specification.setDeliveryTerm(new DeliveryTermType(SpecificationForm.EnterImmediatelyId));
				if (commercialProposal.isProviderDelivery())
				{
					Calendar calendar = Calendar.getInstance();
					Date contractDate = StringUtil.appDateString2Date(getCon_date_formatted());
					calendar.setTime(contractDate);
					calendar.add(Calendar.DATE, Integer.parseInt(commercialProposal.getCpr_delivery_count_day()));
					specification.setSpc_delivery_date(StringUtil.appDateString2dbDateString(StringUtil.date2appDateString(calendar.getTime())));
				}
				else
				{
					specification.setSpc_delivery_date(StringUtil.appDateString2dbDateString(commercialProposal.getCpr_final_date()));
				}
			}
			else
			{
				specification.setDeliveryTerm(new DeliveryTermType(SpecificationForm.EnterAfterPrepayId));
				if (commercialProposal.getCpr_prepay_percent() % 5 == 0)
				{
					specification.setSpc_percent_or_sum("0"); //процент
				}
				else
				{
					specification.setSpc_percent_or_sum("1"); //сумма
				}
				specification.setSpc_delivery_percent(commercialProposal.getCpr_prepay_percent());
				specification.setSpc_delivery_sum(commercialProposal.getCpr_prepay_sum());
			}

			LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));
			String deliveryCondition = "";
			String payCondition = "";
			String spcAdditionalDaysCount = "0";
			if (commercialProposal.isAssembleMinskStore())
			{
				if (commercialProposal.getCpr_prepay_percent() == 100)
				{
					if (commercialProposal.isProviderDelivery())
					{
						deliveryCondition = words.getMessage("rep.CommercialProposal.contractText2_100_providerDelivery", commercialProposal.getCpr_provider_delivery_address(), commercialProposal.getCpr_delivery_count_day(), commercialProposal.getDeliveryCountDayInWords());
						spcAdditionalDaysCount = commercialProposal.getCpr_delivery_count_day();
					}
					else
					{
						deliveryCondition = words.getMessage("rep.CommercialProposal.contractText2_100");
						spcAdditionalDaysCount = Config.getString(Constants.defaultSpcAdditionalDaysCount);
					}
					payCondition = words.getMessage("rep.CommercialProposal.contractText4_100", commercialProposal.getFinalDateFullFormat());
				}
				else if (commercialProposal.getCpr_prepay_percent() == 0)
				{
					if (commercialProposal.isProviderDelivery())
					{
						deliveryCondition = words.getMessage("rep.CommercialProposal.contractText2_0_providerDelivery", commercialProposal.getCpr_provider_delivery_address(), commercialProposal.getCpr_delivery_count_day(), commercialProposal.getDeliveryCountDayInWords());
					}
					else
					{
						deliveryCondition = words.getMessage("rep.CommercialProposal.contractText2_0", commercialProposal.getFinalDateFullFormat());
					}
					payCondition = words.getMessage("rep.CommercialProposal.contractText4_0", commercialProposal.getCpr_delay_days(), commercialProposal.getDelayDaysInWords());
				}
				else
				{
					if (commercialProposal.isProviderDelivery())
					{
						deliveryCondition = words.getMessage("rep.CommercialProposal.contractText2_2_providerDelivery", commercialProposal.getCpr_provider_delivery_address(), commercialProposal.getCpr_delivery_count_day(), commercialProposal.getDeliveryCountDayInWords());
						spcAdditionalDaysCount = commercialProposal.getCpr_delivery_count_day();
					}
					else
					{
						deliveryCondition = words.getMessage("rep.CommercialProposal.contractText2_2");
						spcAdditionalDaysCount = Config.getString(Constants.defaultSpcAdditionalDaysCount);
					}
					payCondition = words.getMessage("rep.CommercialProposal.contractText4_2", new Object[]{
									StringUtil.double2appCurrencyString(commercialProposal.getCpr_prepay_percent()),
									StringUtil.getStrSum(commercialProposal.getCpr_prepay_sum(), false, "BYN", words),
									commercialProposal.getFinalDateFullFormat(),
									StringUtil.double2appCurrencyString(100 - commercialProposal.getCpr_prepay_percent()),
									StringUtil.getStrSum(commercialProposal.getTotalPrint() - commercialProposal.getCpr_prepay_sum(), false, "BYN", words),
									commercialProposal.getCpr_delay_days(),
									commercialProposal.getDelayDaysInWords()});
				}
			}
			specification.setSpc_delivery_cond(deliveryCondition);
			specification.setSpc_add_pay_cond(payCondition);
			specification.setSpc_additional_days_count(spcAdditionalDaysCount);

			specification.getSpecificationPayments().clear();
			if (commercialProposal.getCpr_prepay_percent() == 100)
			{
				Calendar calendar = Calendar.getInstance();
				Date commercialProposalDate = StringUtil.appDateString2Date(commercialProposal.getCpr_date());
				calendar.setTime(commercialProposalDate);
				calendar.add(Calendar.DATE, 3); //"Дата" КП + 3 дня
				specification.getSpecificationPayments().add(new SpecificationPayment(commercialProposal.getCpr_prepay_percent(), commercialProposal.getCpr_prepay_sum(), specification.getCurrencyName(), StringUtil.appDateString2dbDateString(StringUtil.date2appDateString(calendar.getTime()))));
			}
			else if (commercialProposal.getCpr_prepay_percent() == 0)
			{
				specification.getSpecificationPayments().add(new SpecificationPayment(100, specification.getSpc_summ(), specification.getCurrencyName()));
			}
			else
			{
				specification.getSpecificationPayments().add(new SpecificationPayment(commercialProposal.getCpr_prepay_percent(), commercialProposal.getCpr_prepay_sum(), specification.getCurrencyName(), StringUtil.appDateString2dbDateString(commercialProposal.getCpr_final_date())));
				specification.getSpecificationPayments().add(new SpecificationPayment(100 - commercialProposal.getCpr_prepay_percent(), specification.getSpc_summ() - commercialProposal.getCpr_prepay_sum(), specification.getCurrencyName()));
			}

			getSpecifications().add(specification);
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}
}
