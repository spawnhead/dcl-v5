package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dbo.*;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * OrderProduce: pww
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderProduce implements Serializable
{
	protected static Log log = LogFactory.getLog(OrderProduce.class);
	String opr_id;
	String ord_id;
	String number;
	String opr_use_prev_number;
	DboProduce produce = new DboProduce();
	String opr_produce_name;
	String opr_catalog_num;
	double opr_count;
	double opr_count_executed;
	double opr_count_occupied;
	double opr_price_brutto;
	double opr_discount;
	double opr_price_netto;
	double opr_summ;
	String opr_occupied;
	String opr_comment;
	double drp_price;
	String drp_max_extra;
	String opr_parent_id;
	String opr_have_depend;

	Contractor contractor = new Contractor();
	Contract contract = new Contract();
	Specification specification = new Specification();

	List<ProductionTerm> productTerms = new ArrayList<ProductionTerm>();
	List<ReadyForShipping> readyForShippings = new ArrayList<ReadyForShipping>();

	String stf_id;
	String specificationNumbers;
	double prd_circulation180days;
	double rest_in_minsk;
	double restInLithuania;
	boolean is_itog;

	public OrderProduce()
	{
	}

	public OrderProduce(String opr_id)
	{
		this.opr_id = opr_id;
	}

	public OrderProduce(OrderProduce orderProduce)
	{
		opr_id = orderProduce.getOpr_id();
		ord_id = orderProduce.getOrd_id();
		number = orderProduce.getNumber();
		opr_use_prev_number = orderProduce.getOpr_use_prev_number();
		if (null == orderProduce.getProduce().getId())
		{
			produce = new DboProduce();
		}
		else
		{
			produce = ProduceDAO.loadProduceWithUnit(orderProduce.getProduce().getId());
		}
		opr_produce_name = orderProduce.getOpr_produce_name();
		opr_catalog_num = orderProduce.getOpr_catalog_num();
		opr_count = orderProduce.getOpr_count();
		opr_count_executed = orderProduce.getOpr_count_executed();
		opr_count_occupied = orderProduce.getOpr_count_occupied();
		opr_price_brutto = orderProduce.getOpr_price_brutto();
		opr_discount = orderProduce.getOpr_discount();
		opr_price_netto = orderProduce.getOpr_price_netto();
		opr_summ = orderProduce.getOpr_summ();
		opr_occupied = orderProduce.getOpr_occupied();
		opr_comment = orderProduce.getOpr_comment();
		drp_price = orderProduce.getDrp_price();
		drp_max_extra = orderProduce.getDrp_max_extra();
		opr_parent_id = orderProduce.getOpr_parent_id();
		opr_have_depend = orderProduce.getOpr_have_depend();

		contractor = new Contractor(orderProduce.getContractor());
		contract = new Contract(orderProduce.getContract());
		specification = new Specification(orderProduce.getSpecification());

		stf_id = orderProduce.getStf_id();
		specificationNumbers = orderProduce.getSpecificationNumbers();
		prd_circulation180days = orderProduce.getPrd_circulation180days();
		rest_in_minsk = orderProduce.getRest_in_minsk();
		restInLithuania = orderProduce.getRestInLithuania();
		is_itog = orderProduce.isIs_itog();
	}

	public String getOpr_id()
	{
		return opr_id;
	}

	public void setOpr_id(String opr_id)
	{
		this.opr_id = opr_id;
	}

	public String getOrd_id()
	{
		return ord_id;
	}

	public void setOrd_id(String ord_id)
	{
		this.ord_id = ord_id;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public DboProduce getProduce()
	{
		return produce;
	}

	public void setProduce(DboProduce produce)
	{
		this.produce = produce;
	}

	public String getOpr_produce_name()
	{
		return opr_produce_name;
	}

	public void setOpr_produce_name(String opr_produce_name)
	{
		this.opr_produce_name = opr_produce_name;
	}

	public String getOpr_catalog_num()
	{
		return opr_catalog_num;
	}

	public void setOpr_catalog_num(String opr_catalog_num)
	{
		this.opr_catalog_num = opr_catalog_num;
	}

	public double getOpr_price_brutto()
	{
		return opr_price_brutto;
	}

	public String getOpr_price_brutto_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_price_brutto);
	}

	public void setOpr_price_brutto(double opr_price_brutto)
	{
		this.opr_price_brutto = opr_price_brutto;
	}

	public double getOpr_count()
	{
		return opr_count;
	}

	public String getOpr_count_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_count);
	}

	public void setOpr_count(double opr_count)
	{
		this.opr_count = opr_count;
	}

	public double getOpr_count_executed()
	{
		return opr_count_executed;
	}

	public String getOpr_count_executed_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_count_executed);
	}

	public void setOpr_count_executed(double opr_count_executed)
	{
		this.opr_count_executed = opr_count_executed;
	}

	public double getOpr_count_occupied()
	{
		return opr_count_occupied;
	}

	public String getOpr_count_occupied_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_count_occupied);
	}

	public void setOpr_count_occupied(double opr_count_occupied)
	{
		this.opr_count_occupied = opr_count_occupied;
	}

	public double getOpr_discount()
	{
		return opr_discount;
	}

	public String getOpr_discount_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_discount);
	}

	public void setOpr_discount(double opr_discount)
	{
		this.opr_discount = opr_discount;
	}

	public double getOpr_price_netto()
	{
		return opr_price_netto;
	}

	public String getOpr_price_netto_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_price_netto);
	}

	public void setOpr_price_netto(double opr_price_netto)
	{
		this.opr_price_netto = opr_price_netto;
	}

	public double getOpr_summ()
	{
		return opr_summ;
	}

	public String getOpr_summ_formatted()
	{
		return StringUtil.double2appCurrencyString(opr_summ);
	}

	public void setOpr_summ(double opr_summ)
	{
		this.opr_summ = opr_summ;
	}

	public String getOpr_occupied()
	{
		return opr_occupied;
	}

	public void setOpr_occupied(String opr_occupied)
	{
		this.opr_occupied = opr_occupied;
	}

	public String getOpr_comment()
	{
		return opr_comment;
	}

	public void setOpr_comment(String opr_comment)
	{
		this.opr_comment = opr_comment;
	}

	public double getDrp_price()
	{
		return drp_price;
	}

	public String getDrp_price_formatted()
	{
		return StringUtil.double2appCurrencyString(drp_price);
	}

	public void setDrp_price(double drp_price)
	{
		this.drp_price = drp_price;
	}

	public String getDrp_max_extra()
	{
		return drp_max_extra;
	}

	public void setDrp_max_extra(String drp_max_extra)
	{
		this.drp_max_extra = drp_max_extra;
	}

	public String getOpr_parent_id()
	{
		return opr_parent_id;
	}

	public void setOpr_parent_id(String opr_parent_id)
	{
		this.opr_parent_id = opr_parent_id;
	}

	public String getOpr_have_depend()
	{
		return opr_have_depend;
	}

	public void setOpr_have_depend(String opr_have_depend)
	{
		this.opr_have_depend = opr_have_depend;
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public Contract getContract()
	{
		return contract;
	}

	public void setContract(Contract contract)
	{
		this.contract = contract;
	}

	public Specification getSpecification()
	{
		return specification;
	}

	public void setSpecification(Specification specification)
	{
		this.specification = specification;
	}

	public List<ProductionTerm> getProductTerms()
	{
		return productTerms;
	}

	public void setProductTerms(List<ProductionTerm> productTerms)
	{
		this.productTerms = productTerms;
	}

	public List<ReadyForShipping> getReadyForShippings()
	{
		return readyForShippings;
	}

	public void setReadyForShippings(List<ReadyForShipping> readyForShippings)
	{
		this.readyForShippings = readyForShippings;
	}

	public boolean isIs_itog()
	{
		return is_itog;
	}

	public void setIs_itog(boolean is_itog)
	{
		this.is_itog = is_itog;
	}

	public String getStf_id()
	{
		return stf_id;
	}

	public void setStf_id(String stf_id)
	{
		this.stf_id = stf_id;
	}

	public String getSpecificationNumbers()
	{
		return specificationNumbers;
	}

	public void setSpecificationNumbers(String specificationNumbers)
	{
		this.specificationNumbers = specificationNumbers;
	}

	public double getPrd_circulation180days()
	{
		return prd_circulation180days;
	}

	public void setPrd_circulation180days(double prd_circulation180days)
	{
		this.prd_circulation180days = prd_circulation180days;
	}

	public double getRest_in_minsk()
	{
		return rest_in_minsk;
	}

	public String getRestInMinskFormatted()
	{
		return StringUtil.double2appCurrencyString(getRest_in_minsk());
	}

	public void setRest_in_minsk(double rest_in_minsk)
	{
		this.rest_in_minsk = rest_in_minsk;
	}

	public double getRestInLithuania()
	{
		return restInLithuania;
	}

	public void setRestInLithuania(double restInLithuania)
	{
		this.restInLithuania = restInLithuania;
	}

	public String getOpr_use_prev_number()
	{
		return opr_use_prev_number;
	}

	public boolean isDependedPosition()
	{
		return !StringUtil.isEmpty(getOpr_use_prev_number());
	}

	public void setOpr_use_prev_number(String opr_use_prev_number)
	{
		this.opr_use_prev_number = opr_use_prev_number;
	}

	public String getCatalogNumberForStuffCategory()
	{
		if (null == produce.getId() || StringUtil.isEmpty(stf_id))
		{
			return "";
		}

		produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
		DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(stf_id));
		return catalogNumber == null ? null : catalogNumber.getNumber();
	}

	public String getProduceNameForLanguage(String lngCode)
	{
		if (null == produce.getId())
		{
			//старый документ - для печати нужно имя
			return getOpr_produce_name();
		}

		if ("RU".equalsIgnoreCase(lngCode))
		{
			return produce.getName();
		}

		IActionContext context = ActionContext.threadInstance();
		String defaultStr = "";
		try
		{
			defaultStr = StrutsUtil.getMessage(context, "OrderProduce.default_print_no_translate");
		}
		catch (Exception e)
		{
			log.error(e);
		}
		produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
		DboProduceLanguage produceLanguage = produce.getProduceLanguages().get(lngCode);
		return produceLanguage == null ? defaultStr : produceLanguage.getName();
	}

	public String getUnitNameForLanguage(String lngCode)
	{
		if (null == produce.getId())
		{
			return "";
		}

		IActionContext context = ActionContext.threadInstance();
		String defaultStr = "";
		try
		{
			defaultStr = StrutsUtil.getMessage(context, "OrderProduce.default_print_no_translate");
		}
		catch (Exception e)
		{
			log.error(e);
		}
		produce.setUnit((DboUnit) HibernateUtil.associateWithCurentSession(produce.getUnit()));
		DboUnitLanguage unitLanguage = produce.getUnit().getUnitLanguages().get(lngCode);
		return unitLanguage == null ? defaultStr : unitLanguage.getName();
	}

	public static void loadGridData(OrderProduce orderProduce, Order order)
	{
		if (!StringUtil.isEmpty(order.getOrd_date_conf_all()) || !StringUtil.isEmpty(order.getOrd_ready_for_deliv_date_all()))
		{
			try
			{
				if (!StringUtil.isEmpty(order.getOrd_date_conf_all()))
				{
					ProductionTerm productionTerm = new ProductionTerm();
					if (orderProduce.getProductTerms().size() > 0)
					{
						productionTerm = new ProductionTerm(orderProduce.getProductTerms().get(0));
						orderProduce.getProductTerms().clear();
					}
					productionTerm.setPtr_count(orderProduce.getOpr_count());
					productionTerm.setPtr_date(StringUtil.appDateString2dbDateString(order.getOrd_date_conf()));
					orderProduce.getProductTerms().add(productionTerm);
				}
				if (!StringUtil.isEmpty(order.getOrd_ready_for_deliv_date_all()))
				{
					ReadyForShipping readyForShipping = new ReadyForShipping();
					if (orderProduce.getReadyForShippings().size() > 0)
					{
						readyForShipping = new ReadyForShipping(orderProduce.getReadyForShippings().get(0));
						orderProduce.getReadyForShippings().clear();
					}
					readyForShipping.setRfs_count(orderProduce.getOpr_count());
					readyForShipping.setShippingDocType(order.getShippingDocType());
					readyForShipping.setRfs_number(order.getOrd_shp_doc_number());
					readyForShipping.setRfs_date(StringUtil.appDateString2dbDateString(order.getOrd_ready_for_deliv_date()));
					readyForShipping.setRfs_ship_from_stock(StringUtil.appDateString2dbDateString(order.getOrd_ship_from_stock()));
					readyForShipping.setRfs_arrive_in_lithuania(StringUtil.appDateString2dbDateString(order.getOrd_arrive_in_lithuania()));
					orderProduce.getReadyForShippings().add(readyForShipping);
				}
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}
}
