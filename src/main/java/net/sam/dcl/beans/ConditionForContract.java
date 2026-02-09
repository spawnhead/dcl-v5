package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.StuffCategoryDAO;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ConditionForContract implements Serializable
{
	protected static Log log = LogFactory.getLog(ConditionForContract.class);

	String is_new_doc;

	String cfc_id;
	User createUser = new User();
	User editUser = new User();
	User placeUser = new User();
	User executeUser = new User();
	User annulUser = new User();
	String usr_date_create;
	String usr_date_edit;
	String usr_date_place;
	String usr_date_execute;
	String usr_date_annul;
	String cfc_place;
	String cfc_execute;
	Seller seller = new Seller();
	Contractor contractor = new Contractor();
	String cfc_doc_type;
	String cfc_con_number_txt;
	String cfc_con_date;
	Currency currency = new Currency();
	Contract contract = new Contract();
	String spc_numbers;
	String cfc_spc_numbers;
	String cfc_spc_number_txt;
	String cfc_spc_date;
	String cfc_pay_cond;
	String cfc_custom_point;
	String cfc_delivery_cond;
	String cfc_guarantee_cond;
	String cfc_montage_cond;
	String cfc_date_con_to;
	String cfc_count_delivery;
	ContactPerson contactPersonSign = new ContactPerson();
	ContactPerson contactPerson = new ContactPerson();
	String cfc_delivery_count;
	PurchasePurpose purchasePurpose = new PurchasePurpose();
	String cfc_need_invoice;
	String cfc_comment;
	String cfc_check_price;
	String cfc_check_price_date;
	String usr_id_check_price;
	String cfc_annul;
	String cfc_annul_date;

	List<ConditionForContractProduce> produces = new ArrayList<ConditionForContractProduce>();

	boolean have_itog = false;

	String printScale = "100";

	public ConditionForContract()
	{
	}

	public ConditionForContract(String cfc_id)
	{
		this.cfc_id = cfc_id;
	}

	public String getIs_new_doc()
	{
		return is_new_doc;
	}

	public void setIs_new_doc(String is_new_doc)
	{
		this.is_new_doc = is_new_doc;
	}

	public String getCfc_id()
	{
		return cfc_id;
	}

	public void setCfc_id(String cfc_id)
	{
		this.cfc_id = cfc_id;
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

	public User getPlaceUser()
	{
		return placeUser;
	}

	public void setPlaceUser(User placeUser)
	{
		this.placeUser = placeUser;
	}

	public User getExecuteUser()
	{
		return executeUser;
	}

	public void setExecuteUser(User executeUser)
	{
		this.executeUser = executeUser;
	}

	public User getAnnulUser()
	{
		return annulUser;
	}

	public void setAnnulUser(User annulUser)
	{
		this.annulUser = annulUser;
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

	public String getUsr_date_place()
	{
		return usr_date_place;
	}

	public void setUsr_date_place(String usr_date_place)
	{
		this.usr_date_place = usr_date_place;
	}

	public String getUsr_date_execute()
	{
		return usr_date_execute;
	}

	public void setUsr_date_execute(String usr_date_execute)
	{
		this.usr_date_execute = usr_date_execute;
	}

	public String getUsr_date_annul()
	{
		return usr_date_annul;
	}

	public void setUsr_date_annul(String usr_date_annul)
	{
		this.usr_date_annul = usr_date_annul;
	}

	public String getCfc_place()
	{
		return cfc_place;
	}

	public void setCfc_place(String cfc_place)
	{
		this.cfc_place = cfc_place;
	}

	public boolean isCfcPlace()
	{
		return !StringUtil.isEmpty(getCfc_place());
	}

	public String getCfc_execute()
	{
		return cfc_execute;
	}

	public void setCfc_execute(String cfc_execute)
	{
		this.cfc_execute = cfc_execute;
	}

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public String getCfc_doc_type()
	{
		return cfc_doc_type;
	}

	public void setCfc_doc_type(String cfc_doc_type)
	{
		this.cfc_doc_type = cfc_doc_type;
	}

	public String getCfc_con_number_txt()
	{
		return cfc_con_number_txt;
	}

	public void setCfc_con_number_txt(String cfc_con_number_txt)
	{
		this.cfc_con_number_txt = cfc_con_number_txt;
	}

	public String getCfc_con_date()
	{
		return cfc_con_date;
	}

	public String getCfc_con_date_ts()
	{
		return StringUtil.appDateString2dbDateString(cfc_con_date);
	}

	public void setCfc_con_date(String cfc_con_date)
	{
		this.cfc_con_date = cfc_con_date;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Contract getContract()
	{
		return contract;
	}

	public void setContract(Contract contract)
	{
		this.contract = contract;
	}

	public String getSpc_numbers()
	{
		return spc_numbers;
	}

	public void setSpc_numbers(String spc_numbers)
	{
		this.spc_numbers = spc_numbers;
	}

	public String getCfc_spc_numbers()
	{
		return cfc_spc_numbers;
	}

	public void setCfc_spc_numbers(String cfc_spc_numbers)
	{
		this.cfc_spc_numbers = cfc_spc_numbers;
	}

	public String getCfc_spc_number_txt()
	{
		return cfc_spc_number_txt;
	}

	public void setCfc_spc_number_txt(String cfc_spc_number_txt)
	{
		this.cfc_spc_number_txt = cfc_spc_number_txt;
	}

	public String getCfc_spc_date()
	{
		return cfc_spc_date;
	}

	public String getCfc_spc_date_ts()
	{
		return StringUtil.appDateString2dbDateString(cfc_spc_date);
	}

	public void setCfc_spc_date(String cfc_spc_date)
	{
		this.cfc_spc_date = cfc_spc_date;
	}

	public String getCfc_pay_cond()
	{
		return cfc_pay_cond;
	}

	public void setCfc_pay_cond(String cfc_pay_cond)
	{
		this.cfc_pay_cond = cfc_pay_cond;
	}

	public String getCfc_custom_point()
	{
		return cfc_custom_point;
	}

	public void setCfc_custom_point(String cfc_custom_point)
	{
		this.cfc_custom_point = cfc_custom_point;
	}

	public String getCfc_delivery_cond()
	{
		return cfc_delivery_cond;
	}

	public void setCfc_delivery_cond(String cfc_delivery_cond)
	{
		this.cfc_delivery_cond = cfc_delivery_cond;
	}

	public String getCfc_guarantee_cond()
	{
		return cfc_guarantee_cond;
	}

	public void setCfc_guarantee_cond(String cfc_guarantee_cond)
	{
		this.cfc_guarantee_cond = cfc_guarantee_cond;
	}

	public String getCfc_montage_cond()
	{
		return cfc_montage_cond;
	}

	public void setCfc_montage_cond(String cfc_montage_cond)
	{
		this.cfc_montage_cond = cfc_montage_cond;
	}

	public String getCfc_date_con_to()
	{
		return cfc_date_con_to;
	}

	public String getCfc_date_con_to_ts()
	{
		return StringUtil.appDateString2dbDateString(cfc_date_con_to);
	}

	public void setCfc_date_con_to(String cfc_date_con_to)
	{
		this.cfc_date_con_to = cfc_date_con_to;
	}

	public String getCfc_count_delivery()
	{
		return cfc_count_delivery;
	}

	public void setCfc_count_delivery(String cfc_count_delivery)
	{
		this.cfc_count_delivery = cfc_count_delivery;
	}

	public ContactPerson getContactPersonSign()
	{
		return contactPersonSign;
	}

	public void setContactPersonSign(ContactPerson contactPersonSign)
	{
		this.contactPersonSign = contactPersonSign;
	}

	public ContactPerson getContactPerson()
	{
		return contactPerson;
	}

	public void setContactPerson(ContactPerson contactPerson)
	{
		this.contactPerson = contactPerson;
	}

	public String getCfc_delivery_count()
	{
		return cfc_delivery_count;
	}

	public void setCfc_delivery_count(String cfc_delivery_count)
	{
		this.cfc_delivery_count = cfc_delivery_count;
	}

	public PurchasePurpose getPurchasePurpose()
	{
		return purchasePurpose;
	}

	public void setPurchasePurpose(PurchasePurpose purchasePurpose)
	{
		this.purchasePurpose = purchasePurpose;
	}

	public String getCfc_need_invoice()
	{
		return cfc_need_invoice;
	}

	public boolean isNeedInvoice()
	{
		return !StringUtil.isEmpty(getCfc_need_invoice());
	}

	public void setCfc_need_invoice(String cfc_need_invoice)
	{
		this.cfc_need_invoice = cfc_need_invoice;
	}

	public String getCfc_comment()
	{
		return cfc_comment;
	}

	public void setCfc_comment(String cfc_comment)
	{
		this.cfc_comment = cfc_comment;
	}

	public String getCfc_check_price()
	{
		return cfc_check_price;
	}

	public void setCfc_check_price(String cfc_check_price)
	{
		this.cfc_check_price = cfc_check_price;
	}

	public boolean isCheckedPrices()
 {
   return !StringUtil.isEmpty(getCfc_check_price());
 }

	public String getCfc_check_price_date()
	{
		return cfc_check_price_date;
	}

	public void setCfc_check_price_date(String cfc_check_price_date)
	{
		this.cfc_check_price_date = cfc_check_price_date;
	}

	public String getUsr_id_check_price()
	{
		return usr_id_check_price;
	}

	public void setUsr_id_check_price(String usr_id_check_price)
	{
		this.usr_id_check_price = usr_id_check_price;
	}

	public String getCfc_annul()
	{
		return cfc_annul;
	}

	public void setCfc_annul(String cfc_annul)
	{
		this.cfc_annul = cfc_annul;
	}

	public String getCfc_annul_date()
	{
		return cfc_annul_date;
	}

	public void setCfc_annul_date(String cfc_annul_date)
	{
		this.cfc_annul_date = cfc_annul_date;
	}

	public List<ConditionForContractProduce> getProduces()
	{
		return produces;
	}

	public void setProduces(List<ConditionForContractProduce> produces)
	{
		this.produces = produces;
	}

	public String getPrintScale()
	{
		return printScale;
	}

	public void setPrintScale(String printScale)
	{
		this.printScale = printScale;
	}

	public void calculateInString(boolean roundToInt)
	{
		int roundDegree = 2;
		if (roundToInt)
		{
			roundDegree = 0;
		}
		for (int i = 0; i < produces.size(); i++)
		{
			ConditionForContractProduce conditionForContractProduce = produces.get(i);
			conditionForContractProduce.setNumber(Integer.toString(i + 1));
			conditionForContractProduce.setRoundToInt(roundToInt);

			double cppCost;
			double cppNDS;
			double cppNDSCost;

			if (roundToInt)
			{
				conditionForContractProduce.setCcp_price(Math.round(conditionForContractProduce.getCcp_price()));
			}
			cppCost = conditionForContractProduce.getCcp_price() * conditionForContractProduce.getCcp_count();
			cppCost = StringUtil.roundN(cppCost, roundDegree);
			conditionForContractProduce.setCcp_cost(cppCost);
			if (Constants.techServiceSellerId.equals(getSeller().getId()))
			{
				cppNDS = cppCost * conditionForContractProduce.getCcp_nds_rate() / 100;
				cppNDS = StringUtil.roundN(cppNDS, roundDegree);
				conditionForContractProduce.setCcp_nds(cppNDS);
				cppNDSCost = cppCost + cppNDS;
				cppNDSCost = StringUtil.roundN(cppNDSCost, roundDegree);
				conditionForContractProduce.setCcp_nds_cost(cppNDSCost);
			}
		}
	}

	public void calculate()
	{
		if (have_itog)
		{
			produces.remove(produces.size() - 1);
		}

		boolean roundToInt = getCurrency().isNeedRound() && !StringUtil.isEmpty(getCurrency().getId());
		calculateInString(roundToInt);

		double cppCost = 0;
		double cppNDS = 0;
		double cppNDSCost = 0;
		for (ConditionForContractProduce conditionForContractProduce : produces)
		{
			cppCost += conditionForContractProduce.getCcp_cost();
			cppNDS += conditionForContractProduce.getCcp_nds();
			cppNDSCost += conditionForContractProduce.getCcp_nds_cost();
		}

		int roundDegree = 2;
		if (roundToInt)
		{
			roundDegree = 0;
		}
		cppCost = StringUtil.roundN(cppCost, roundDegree);
		cppNDS = StringUtil.roundN(cppNDS, roundDegree);
		cppNDSCost = StringUtil.roundN(cppNDSCost, roundDegree);

		ConditionForContractProduce conditionForContractProduce = getEmptyConditionForContractProduce();
		conditionForContractProduce.setItogLine(true);
		IActionContext context = ActionContext.threadInstance();
		try
		{
			conditionForContractProduce.setItogStr(StrutsUtil.getMessage(context, "ConditionForContract.total"));
		}
		catch (Exception e)
		{
			log.error(e);
		}
		conditionForContractProduce.setCcp_cost(cppCost);
		conditionForContractProduce.setCcp_nds(cppNDS);
		conditionForContractProduce.setCcp_nds_cost(cppNDSCost);
		conditionForContractProduce.setRoundToInt(roundToInt);

		produces.add(conditionForContractProduce);

		have_itog = true;
	}

	public void importFromCP(CommercialProposal commercialProposal)
	{
		if (have_itog)
		{
			produces.remove(produces.size() - 1);
		}

		commercialProposal.calculate();
		List cprProduces = commercialProposal.getProduces();

		for (int i = 0; i < cprProduces.size() - commercialProposal.getCountItogRecord(); i++)
		{
			CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) cprProduces.get(i);
			ConditionForContractProduce conditionForContractProduce = getEmptyConditionForContractProduce();
			conditionForContractProduce.setCpr_id(commercialProposal.getCpr_id());
			conditionForContractProduce.setCpr_number(commercialProposal.getCpr_number());
			conditionForContractProduce.setCpr_date(StringUtil.appDateString2dbDateString(commercialProposal.getCpr_date()));
			conditionForContractProduce.setCfc_id(cfc_id);
			//по старому
			if (!StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
			{
				if (!StringUtil.isEmpty(commercialProposalProduce.getLpr_catalog_num())) // только если каталожный номер заполнен
				{
					List<DboProduce> listDboProduces = DboProduce.findByCatalogNumber(commercialProposalProduce.getLpr_catalog_num());
					if (listDboProduces.size() != 0)
					{
						conditionForContractProduce.setProduce(listDboProduces.get(0));
						Iterator<DboCatalogNumber> iter = conditionForContractProduce.getProduce().getCatalogNumbers().values().iterator();
						if (iter.hasNext())
						{
							DboCatalogNumber catalogNumber = iter.next();
							IActionContext context = ActionContext.threadInstance();
							try
							{
								conditionForContractProduce.setStuffCategory(StuffCategoryDAO.load(context, catalogNumber.getStuffCategory().getId().toString()));
							}
							catch (Exception e)
							{
								log.error(e);
							}
						}
					}
				}
			}
			else
			{
				conditionForContractProduce.setProduce(commercialProposalProduce.getProduce());
				conditionForContractProduce.setStuffCategory(commercialProposalProduce.getStuffCategory());
			}
			conditionForContractProduce.setCcp_count(commercialProposalProduce.getLpr_count());

      /*
       *значение поля "Цены указаны на условиях" в КП	заполнение колонки "Цена" (округление до целого)
       *EXW	не заполняется
       *FCA	= колонка "Прод. цена + Уп + Трансп + Монтаж + Обучен" * "Курс пересчёта"
       *CIP	= колонка "Прод. цена + Уп + Трансп + Монтаж + Обучен" * "Курс пересчёта"
       *CPT	= колонка "Прод. цена + Уп + Трансп + Монтаж + Обучен" * "Курс пересчёта"
       *DDU	= колонка "Прод. цена + Уп + Трансп + Монтаж + Обучен" * "Курс пересчёта"
       *DDP	= колонка "Прод. цена + Уп + Трансп + Монтаж + Обучен + Тамож. расходы" * "Курс пересчёта"				
      */
			if (commercialProposal.getPriceCondition().getName().equalsIgnoreCase(IncoTerm.EXW) || commercialProposal.getPriceCondition().getName().equalsIgnoreCase(IncoTerm.EXW_2010))
			{
				conditionForContractProduce.setCcp_price(0.0);
			}
			else if (commercialProposal.getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP) || commercialProposal.getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010))
			{
				conditionForContractProduce.setCcp_price(commercialProposalProduce.getSale_price_parking_trans_custom_print());
			}
			else
			{
				conditionForContractProduce.setCcp_price(commercialProposalProduce.getSale_price_parking_trans_print());
			}
			conditionForContractProduce.setCcp_nds_rate(commercialProposal.getCpr_nds());

			produces.add(conditionForContractProduce);
		}

		have_itog = false;
	}

	static public ConditionForContractProduce getEmptyConditionForContractProduce()
	{
		ConditionForContractProduce conditionForContractProduce = new ConditionForContractProduce();
		conditionForContractProduce.setCcp_id("");
		conditionForContractProduce.setCfc_id("");
		conditionForContractProduce.setNumber("");
		conditionForContractProduce.setCcp_price(0.0);
		conditionForContractProduce.setCcp_count(0.0);
		conditionForContractProduce.setCcp_cost(0.0);
		conditionForContractProduce.setCcp_nds_rate(0.0);
		conditionForContractProduce.setCcp_nds(0.0);
		conditionForContractProduce.setCcp_nds_cost(0.0);

		return conditionForContractProduce;
	}

	public void setListParentIds()
	{
		for (ConditionForContractProduce conditionForContractProduce : produces)
		{
			conditionForContractProduce.setCfc_id(getCfc_id());
		}
	}

	public void setListIdsToNull()
	{
		for (ConditionForContractProduce conditionForContractProduce : produces)
		{
			conditionForContractProduce.setCcp_id(null);
		}
	}

	public ConditionForContractProduce findConditionForContractProduce(String number)
	{
		for (ConditionForContractProduce conditionForContractProduce : produces)
		{
			if (conditionForContractProduce.getNumber().equalsIgnoreCase(number))
				return conditionForContractProduce;
		}

		return null;
	}

	public void updateConditionForContractProduce(String number, ConditionForContractProduce conditionForContractProduceIn)
	{
		for (int i = 0; i < produces.size(); i++)
		{
			ConditionForContractProduce conditionForContractProduce = produces.get(i);

			if (conditionForContractProduce.getNumber().equalsIgnoreCase(number))
			{
				produces.set(i, conditionForContractProduceIn);
				return;
			}
		}
	}

	public void deleteConditionForContractProduce(String number)
	{
		for (int i = 0; i < produces.size(); i++)
		{
			ConditionForContractProduce conditionForContractProduce = produces.get(i);

			if (conditionForContractProduce.getNumber().equalsIgnoreCase(number))
				produces.remove(i);
		}
	}

	public void insertConditionForContractProduce(ConditionForContractProduce conditionForContractProduce)
	{
		produces.add(produces.size() - 1, conditionForContractProduce);
	}

	public String getCfcSpecificationsNumbersFormatted(List<ConditionForContract> condContracts)
	{
		if (condContracts.size() == 0) return "";

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < condContracts.size(); i++)
		{
			ConditionForContract conditionForContract = condContracts.get(i);
			if (StringUtil.isEmpty(conditionForContract.getCfc_execute()))
			{
				result.append(conditionForContract.getCfc_spc_number_txt());
			}
			else
			{
				result.append("<span style=\"color:green\"><b>");
				result.append(conditionForContract.getCfc_spc_number_txt());
				result.append("</b></span>");
			}
			if (i != condContracts.size() - 1)
			{
				result.append(", ");
			}
		}
		return result.toString();
	}
}
