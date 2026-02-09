package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SpecificationImportProduce implements Serializable
{
	final static public String lineSeparator = "----------";

	protected static Log log = LogFactory.getLog(SpecificationImportProduce.class);
	String id;

	String spi_id;
	String sip_id;
	String number;
	DboProduce produce = new DboProduce();
	double drp_price;
	double sip_price;
	boolean sip_price_calculated = true;
	double sip_count;
	double sip_cost;
	double sip_weight;
	StuffCategory stuffCategory = new StuffCategory();
	String drp_id;
	String opr_id;
	String ord_number;
	String ord_date;
	Contractor customer = new Contractor();
	int count_day;
	String con_number;
	String con_date;
	Currency currency = new Currency();
	String spc_number;
	String spc_date;
	String spc_delivery_date;
	String spc_add_pay_cond;
	double pay_persent;

	Purpose purpose = new Purpose();
	String sip_occupied;
	double sip_percent;
	String dlr_ord_not_form;
	String have_depend;
	String drp_max_extra;

	String customCode;
	BigDecimal customPercent;

	boolean itog;

	public SpecificationImportProduce()
	{
	}

	public SpecificationImportProduce(String sip_id)
	{
		this.sip_id = sip_id;
	}

	public SpecificationImportProduce(SpecificationImportProduce specificationImportProduce)
	{
		id = specificationImportProduce.getId();

		spi_id = specificationImportProduce.getSpi_id();
		sip_id = specificationImportProduce.getSip_id();
		number = specificationImportProduce.getNumber();
		produce = specificationImportProduce.getProduce();
		drp_price = specificationImportProduce.getDrp_price();
		sip_price = specificationImportProduce.getSip_price();
		sip_price_calculated = specificationImportProduce.isSip_price_calculated();
		sip_count = specificationImportProduce.getSip_count();
		sip_cost = specificationImportProduce.getSip_cost();
		sip_weight = specificationImportProduce.getSip_weight();
		stuffCategory = new StuffCategory(specificationImportProduce.getStuffCategory());
		drp_id = specificationImportProduce.getDrp_id();
		opr_id = specificationImportProduce.getOpr_id();
		ord_number = specificationImportProduce.getOrd_number();
		ord_date = specificationImportProduce.getOrd_date();
		customer = new Contractor(specificationImportProduce.getCustomer());
		count_day = specificationImportProduce.getCount_day();
		con_number = specificationImportProduce.getCon_number();
		con_date = specificationImportProduce.getCon_date();
		currency = specificationImportProduce.getCurrency();
		spc_number = specificationImportProduce.getSpc_number();
		spc_date = specificationImportProduce.getSpc_date();
		spc_delivery_date = specificationImportProduce.getSpc_delivery_date();
		spc_add_pay_cond = specificationImportProduce.getSpc_add_pay_cond();
		pay_persent = specificationImportProduce.getPay_persent();
		purpose = new Purpose(specificationImportProduce.getPurpose());
		sip_occupied = specificationImportProduce.getSip_occupied();
		sip_percent = specificationImportProduce.getSip_percent();
		dlr_ord_not_form = specificationImportProduce.getDlr_ord_not_form();
		have_depend = specificationImportProduce.getHave_depend();
		drp_max_extra = specificationImportProduce.getDrp_max_extra();
		itog = specificationImportProduce.isItog();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSpi_id()
	{
		return spi_id;
	}

	public void setSpi_id(String spi_id)
	{
		this.spi_id = spi_id;
	}

	public String getSip_id()
	{
		return sip_id;
	}

	public void setSip_id(String sip_id)
	{
		this.sip_id = sip_id;
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

	public double getSip_price()
	{
		return sip_price;
	}

	public String getSip_price_formatted()
	{
		return StringUtil.double2appCurrencyString(sip_price);
	}

	public void setSip_price(double sip_price)
	{
		this.sip_price = sip_price;
	}

	public void setSip_price_formatted(String sip_price)
	{
		this.sip_price = StringUtil.appCurrencyString2double(sip_price);
	}

	public boolean isSip_price_calculated()
	{
		return sip_price_calculated;
	}

	public void setSip_price_calculated(boolean sip_price_calculated)
	{
		this.sip_price_calculated = sip_price_calculated;
	}

	public double getSip_count()
	{
		return sip_count;
	}

	public String getSip_count_formatted()
	{
		return StringUtil.double2appCurrencyString(sip_count);
	}

	public void setSip_count(double sip_count)
	{
		this.sip_count = sip_count;
	}

	public double getSip_cost()
	{
		return sip_cost;
	}

	public String getSip_cost_formatted()
	{
		return StringUtil.double2appCurrencyString(sip_cost);
	}

	public void setSip_cost(double sip_cost)
	{
		this.sip_cost = sip_cost;
	}

	public double getSip_weight()
	{
		return sip_weight;
	}

	public String getSip_weight_formatted()
	{
		return StringUtil.double2appCurrencyStringByMask(sip_weight, "#,##0.000");
	}

	public void setSip_weight_formatted(String sip_weight)
	{
		this.sip_weight = StringUtil.appCurrencyString2double(sip_weight);
	}

	public void setSip_weight(double sip_weight)
	{
		this.sip_weight = sip_weight;
	}

	public StuffCategory getStuffCategory()
	{
		return stuffCategory;
	}

	public void setStuffCategory(StuffCategory stuffCategory)
	{
		this.stuffCategory = stuffCategory;
	}

	public String getOpr_id()
	{
		return opr_id;
	}

	public void setOpr_id(String opr_id)
	{
		this.opr_id = opr_id;
	}

	public String getDrp_id()
	{
		return drp_id;
	}

	public void setDrp_id(String drp_id)
	{
		this.drp_id = drp_id;
	}

	public String getOrd_number()
	{
		return ord_number;
	}

	public void setOrd_number(String ord_number)
	{
		this.ord_number = ord_number;
	}

	public String getOrd_date()
	{
		return ord_date;
	}

	public void setOrd_date(String ord_date)
	{
		this.ord_date = ord_date;
	}

	public String getOrd_number_date()
	{
		String retStr = "";

		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (!StringUtil.isEmpty(getDlr_ord_not_form()))
			{
				return StrutsUtil.getMessage(context, "SpecificationImportProduces.dlr_ord_not_form");
			}

			if (StringUtil.isEmpty(getOrd_number()) && StringUtil.isEmpty(getOrd_date()))
			{
				retStr = "";
			}
			else
			{
				retStr = StrutsUtil.getMessage(context, "msg.common.from", getOrd_number(), StringUtil.dbDateString2appDateString(getOrd_date()));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCustomer(Contractor customer)
	{
		this.customer = customer;
	}

	public int getCount_day()
	{
		return count_day;
	}

	public void setCount_day(int count_day)
	{
		this.count_day = count_day;
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
		return StringUtil.dbDateString2appDateString(getCon_date());
	}

	public void setCon_date(String con_date)
	{
		this.con_date = con_date;
	}

	public String getCon_number_date()
	{
		String retStr = "";

		IActionContext context = ActionContext.threadInstance();
		try
		{
			String number = getCon_number();
			String date = getCon_date_formatted();
			if (StringUtil.isEmpty(number) && StringUtil.isEmpty(date))
			{
				retStr = "";
			}
			else
			{
				retStr = StrutsUtil.getMessage(context, "msg.common.from", number, date);
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Contractor getCustomer()
	{
		return customer;
	}

	public String getSpc_number()
	{
		return spc_number;
	}

	public void setSpc_number(String spc_number)
	{
		this.spc_number = spc_number;
	}

	public String getSpc_date()
	{
		return spc_date;
	}

	public void setSpc_date(String spc_date)
	{
		this.spc_date = spc_date;
	}

	public String getSpc_number_date()
	{
		String retStr = "";

		IActionContext context = ActionContext.threadInstance();
		try
		{
			String number = getSpc_number();
			String date = getSpc_date();
			if (StringUtil.isEmpty(number) && StringUtil.isEmpty(date))
			{
				retStr = "";
			}
			else
			{
				retStr = StrutsUtil.getMessage(context, "msg.common.from", number, StringUtil.dbDateString2appDateString(date));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	public String getSpc_delivery_date()
	{
		return spc_delivery_date;
	}

	public void setSpc_delivery_date(String spc_delivery_date)
	{
		this.spc_delivery_date = spc_delivery_date;
	}

	public String getSpc_add_pay_cond()
	{
		return spc_add_pay_cond;
	}

	public void setSpc_add_pay_cond(String spc_add_pay_cond)
	{
		this.spc_add_pay_cond = spc_add_pay_cond;
	}

	public double getPay_persent()
	{
		return pay_persent;
	}

	public String getPay_persent_formatted()
	{
		return StringUtil.double2appCurrencyString(getPay_persent());
	}

	public void setPay_persent(double pay_persent)
	{
		this.pay_persent = pay_persent;
	}

	public String getExpiration()
	{
		if (StringUtil.isEmpty(getSpc_delivery_date()))
		{
			return "";
		}

		if (count_day < -3)
		{
			return "";
		}

		if (count_day <= 0)
		{
			return "<b>" + Integer.toString(count_day) + "</b>";
		}

		if (count_day > 0)
		{
			return "<span style=\"color:red\"><b>" + Integer.toString(count_day) + "</b></span>";
		}

		return Integer.toString(count_day);
	}

	public Purpose getPurpose()
	{
		return purpose;
	}

	public void setPurpose(Purpose purpose)
	{
		this.purpose = purpose;
	}

	public String getSip_occupied()
	{
		return sip_occupied;
	}

	public void setSip_occupied(String sip_occupied)
	{
		this.sip_occupied = sip_occupied;
	}

	public double getSip_percent()
	{
		return sip_percent;
	}

	public void setSip_percent(double sip_percent)
	{
		this.sip_percent = sip_percent;
	}

	public String getDlr_ord_not_form()
	{
		return dlr_ord_not_form;
	}

	public void setDlr_ord_not_form(String dlr_ord_not_form)
	{
		this.dlr_ord_not_form = dlr_ord_not_form;
	}

	public String getHave_depend()
	{
		return have_depend;
	}

	public void setHave_depend(String have_depend)
	{
		this.have_depend = have_depend;
	}

	public String getDrp_max_extra()
	{
		return drp_max_extra;
	}

	public String getDrp_max_extra_formatted()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (!StringUtil.isEmpty(drp_max_extra))
			{
				return StrutsUtil.getMessage(context, "SpecificationImportProduces.drp_max_extra");
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return "";
	}

	public void setDrp_max_extra(String drp_max_extra)
	{
		this.drp_max_extra = drp_max_extra;
	}

	public String getCustomCode()
	{
		return customCode;
	}

	public void setCustomCode(String customCode)
	{
		this.customCode = customCode;
	}

	public BigDecimal getCustomPercent()
	{
		return customPercent;
	}

	public void setCustomPercent(BigDecimal customPercent)
	{
		this.customPercent = customPercent;
	}

	public void setCustom_percent_formatted(String custom_percent)
	{
		this.customPercent = new BigDecimal(StringUtil.appCurrencyString2double(custom_percent));
	}

	public String getCustom_percent_formatted()
	{
		return StringUtil.BigDecimal2appCurrencyString(customPercent);
	}

	public String getFullComment()
	{
		String retStr = "";

		IActionContext context = ActionContext.threadInstance();

		String conNumberDate = getCon_number_date();
		String spcNumberDate = getSpc_number_date();
		String spcAddPayCondition = getSpc_add_pay_cond();
		String spcPayedPercent = getPay_persent_formatted();
		try
		{
			if (StringUtil.isEmpty(getCustomer().getName()))
			{
				return StrutsUtil.getMessage(context, "SpecificationImportProduces.toStock");
			}
			else
			{
				if (StringUtil.isEmpty(conNumberDate) && StringUtil.isEmpty(spcNumberDate))
				{
					retStr = StrutsUtil.getMessage(context, "SpecificationImportProduces.toStock");
				}
				else
				{
					retStr = conNumberDate + (!StringUtil.isEmpty(conNumberDate) && !StringUtil.isEmpty(spcNumberDate) ? ReportDelimiterConsts.html_separator : "") + spcNumberDate;
				}
			}

			retStr += ReportDelimiterConsts.html_separator + lineSeparator + ReportDelimiterConsts.html_separator + spcPayedPercent + " " + StrutsUtil.getMessage(context, "Common.percent");
			if (!StringUtil.isEmpty(spcAddPayCondition))
				retStr += ReportDelimiterConsts.html_separator + lineSeparator + ReportDelimiterConsts.html_separator + spcAddPayCondition;
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	public boolean isItog()
	{
		return itog;
	}

	public void setItog(boolean itog)
	{
		this.itog = itog;
	}

	public String getCatalogNumberForStuffCategory()
	{
		if (itog || null == produce || null == produce.getId() || null == getStuffCategory() || StringUtil.isEmpty(getStuffCategory().getId()))
		{
			return "";
		}

		produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
		DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(getStuffCategory().getId()));
		return catalogNumber == null ? null : catalogNumber.getNumber();
	}

	public String getProduceName()
	{
		String retStr = "";
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (itog)
			{
				retStr = StrutsUtil.getMessage(context, "SpecificationImportProduces.totalBig");
			}
			else
			{
				retStr = produce.getName();
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	public String getProduceNameFormatted()
	{
		String retStr = "";
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (!itog)
			{
				retStr = getProduceName();
				if (!StringUtil.isEmpty(getHave_depend()))
				{
					retStr = StrutsUtil.getMessage(context, "OrderProduces.oprHaveDepend") + retStr;
				}
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	public void calculateSipCost(double percent, double spi_course, double spi_koeff)
	{
		sip_percent = percent;
		if (spi_course != 0.0 && spi_koeff != 0.0)
		{
			sip_price = drp_price / spi_course / spi_koeff;
			double part = ((double) 100 + percent) / (double) 100;
			sip_price = sip_price / part;
			sip_price = StringUtil.roundN(sip_price, 2);
			sip_cost = sip_price * getSip_count();
			sip_cost = StringUtil.roundN(sip_cost, 2);
			setSip_price_calculated(true);
		}
	}

	public void calculateCountDay(String spi_date)
	{
		//если дата документа пустая - не имеет смысл считать
		if (StringUtil.isEmpty(spi_date))
		{
			spc_delivery_date = "";
			return;
		}

		try
		{
			Date spiDate = StringUtil.appDateString2Date(spi_date);
			Date spcDeliveryDate = StringUtil.appDateString2Date(getSpc_delivery_date());
			double days = StringUtil.getDaysBetween(spcDeliveryDate, spiDate);
			count_day = (int) days;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}
}
