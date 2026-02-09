package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class CalculationState implements Serializable
{
	protected static Log log = LogFactory.getLog(CalculationState.class);

	List<CalculationStateLine> calculationStateLines = new ArrayList<CalculationStateLine>();

	Contractor contractor = new Contractor();
	CalcStateReportType reportType = new CalcStateReportType();
	Currency currency = new Currency();
	Contract contract = new Contract();
	User user = new User();
	Department department = new Department();

	String view_pay_cond;
	String view_delivery_cond;
	String view_expiration;
	String view_complaint;
	String view_comment;
	String view_manager;
	String view_stuff_category;

	String include_all_specs;
	String earliest_doc_date;
	String not_include_if_earliest;
	String not_include_zero;
	String not_show_annul;
	String notShowExpiredContractZeroBalance;
	Seller seller = new Seller();

	public CalculationState()
	{
	}

	public List getCalculationStateLines()
	{
		return calculationStateLines;
	}

	public void setCalculationStateLines(List<CalculationStateLine> calculationStateLines)
	{
		this.calculationStateLines = calculationStateLines;
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public CalcStateReportType getReportType()
	{
		return reportType;
	}

	public void setReportType(CalcStateReportType reportType)
	{
		this.reportType = reportType;
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

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public String getView_pay_cond()
	{
		return view_pay_cond;
	}

	public void setView_pay_cond(String view_pay_cond)
	{
		this.view_pay_cond = view_pay_cond;
	}

	public String getView_delivery_cond()
	{
		return view_delivery_cond;
	}

	public void setView_delivery_cond(String view_delivery_cond)
	{
		this.view_delivery_cond = view_delivery_cond;
	}

	public String getView_expiration()
	{
		return view_expiration;
	}

	public void setView_expiration(String view_expiration)
	{
		this.view_expiration = view_expiration;
	}

	public String getView_complaint()
	{
		return view_complaint;
	}

	public void setView_complaint(String view_complaint)
	{
		this.view_complaint = view_complaint;
	}

	public String getView_comment()
	{
		return view_comment;
	}

	public void setView_comment(String view_comment)
	{
		this.view_comment = view_comment;
	}

	public String getView_manager()
	{
		return view_manager;
	}

	public void setView_manager(String view_manager)
	{
		this.view_manager = view_manager;
	}

	public String getView_stuff_category()
	{
		return view_stuff_category;
	}

	public void setView_stuff_category(String view_stuff_category)
	{
		this.view_stuff_category = view_stuff_category;
	}

	public String getInclude_all_specs()
	{
		return include_all_specs;
	}

	public void setInclude_all_specs(String include_all_specs)
	{
		this.include_all_specs = include_all_specs;
	}

	public String getEarliest_doc_date()
	{
		return earliest_doc_date;
	}

	public void setEarliest_doc_date(String earliest_doc_date)
	{
		this.earliest_doc_date = earliest_doc_date;
	}

	public String getNot_include_if_earliest()
	{
		return not_include_if_earliest;
	}

	public void setNot_include_if_earliest(String not_include_if_earliest)
	{
		this.not_include_if_earliest = not_include_if_earliest;
	}

	public String getNot_include_zero()
	{
		return not_include_zero;
	}

	public void setNot_include_zero(String not_include_zero)
	{
		this.not_include_zero = not_include_zero;
	}

	public String getNot_show_annul()
	{
		return not_show_annul;
	}

	public void setNot_show_annul(String not_show_annul)
	{
		this.not_show_annul = not_show_annul;
	}

	public String getNotShowExpiredContractZeroBalance()
	{
		return notShowExpiredContractZeroBalance;
	}

	public void setNotShowExpiredContractZeroBalance(String notShowExpiredContractZeroBalance)
	{
		this.notShowExpiredContractZeroBalance = notShowExpiredContractZeroBalance;
	}

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public boolean getIsDebit()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (reportType.getId().equals(StrutsUtil.getMessage(context, "report_type_list.calc_debit_id")))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
		return false;
	}

	public boolean isSelectedCurrency()
	{
		return !StringUtil.isEmpty(currency.getName());
	}

	public void calculate()
	{
		IActionContext context = ActionContext.threadInstance();
		if (calculationStateLines.size() == 0)
		{
			return;
		}

		int keyId = 1;
		int rememberI = 0;
		int rememberEmptyCtr = -1;
		int countForDelete = 0;
		boolean notEmptyCtr = false;
		boolean firstCtr = true;
		Map<String, Double[]> currencySum = new HashMap<String, Double[]>();
		List<CurrencyKey> listAllBalance = new ArrayList<CurrencyKey>();
		for (int i = 0; i < calculationStateLines.size(); i++)
		{
			CalculationStateLine calculationStateLine = calculationStateLines.get(i);
			calculationStateLine.setKey_id(keyId);
			keyId++;
			calculationStateLine.setDebet(getIsDebit());

			if (calculationStateLine.isCtr_line() && !getIsDebit())
			{
				if (!firstCtr)
				{
					boolean captionAdded = false;
					for (String conCurrency : currencySum.keySet())
					{
						Double[] sums = currencySum.get(conCurrency);

						CalculationStateLine balanceLine = getEmptyCalculationStateLine();
						balanceLine.setKey_id(keyId);
						keyId++;

						if (!captionAdded)
						{
							try
							{
								balanceLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.commonBalance"));
								captionAdded = true;
							}
							catch (Exception e)
							{
								log.error(e);
							}
						}
						balanceLine.setItog_line(true);
						balanceLine.setBalanceLine(true);
						balanceLine.setCommonBalanceLine(true);
						balanceLine.setCon_currency(conCurrency);
						balanceLine.setNeedFormatCon(false);
						double sumA = sums[0];
						double sumB = sums[1];
						if (sumA > sumB)
						{
							balanceLine.setShp_summ(sumA - sumB);
						}
						else
						{
							balanceLine.setPay_summ(sumB - sumA);
						}

						if (isNeedAllBalance())
						{
							AddSumsToAllBalance(listAllBalance, balanceLine);
						}

						calculationStateLines.add(i, balanceLine);
						i++;
					}
				}

				currencySum.clear();
				firstCtr = false;
			}

			//выборка по всем для дебиторов
			if (calculationStateLine.isCtr_line() && getIsDebit())
			{
				if (!firstCtr)
				{
					boolean captionAdded = false;
					for (String conCurrency : currencySum.keySet())
					{
						Double[] sums = currencySum.get(conCurrency);
						CalculationStateLine balanceLine = getEmptyCalculationStateLine();
						balanceLine.setKey_id(keyId);
						keyId++;

						if (!captionAdded)
						{
							try
							{
								balanceLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.commonBalance"));
								captionAdded = true;
							}
							catch (Exception e)
							{
								log.error(e);
							}
						}
						balanceLine.setItog_line(true);
						balanceLine.setCon_currency(conCurrency);
						balanceLine.setShp_summ(sums[0]);
						balanceLine.setPay_summ(sums[1]);
						balanceLine.setShp_saldo(sums[2]);
						balanceLine.setNeedFormatCon(false);

						if (isNeedAllBalance())
						{
							AddSumsToAllBalance(listAllBalance, balanceLine);
						}

						calculationStateLines.add(i, balanceLine);
						i++;
					}
				}

				currencySum.clear();
				firstCtr = false;
			}

			if (calculationStateLine.isRest_doc_line() && !getIsDebit())
			{
				double sumA;
				double sumB;
				if (!StringUtil.isEmpty(calculationStateLine.getShp_currency()))
				{
					sumA = calculationStateLine.getShp_summ();
					Double[] sums = currencySum.get(calculationStateLine.getShp_currency());
					if (null != sums)
					{
						sumA += sums[0];
						sumB = sums[1];
					}
					else
					{
						sumB = 0.0;
					}
					currencySum.put(calculationStateLine.getShp_currency(), new Double[]{sumA, sumB});
				}

				if (!StringUtil.isEmpty(calculationStateLine.getPay_currency()))
				{
					Double[] sums = currencySum.get(calculationStateLine.getPay_currency());
					sumB = calculationStateLine.getPay_summ();
					if (null != sums)
					{
						sumA = sums[0];
						sumB += sums[1];
					}
					else
					{
						sumA = 0.0;
					}
					currencySum.put(calculationStateLine.getPay_currency(), new Double[]{sumA, sumB});
				}
			}

			if (calculationStateLine.isItog_line() && !getIsDebit())
			{
				double deviateShipping;
				double deviatePay;
				double balanceShipping = 0.0;
				double balancePay = 0.0;

				deviateShipping = calculationStateLine.getSpc_summ() - calculationStateLine.getShp_summ();
				deviatePay = calculationStateLine.getSpc_summ() - calculationStateLine.getPay_summ();
				if (calculationStateLine.getShp_summ() > calculationStateLine.getPay_summ())
				{
					balanceShipping = calculationStateLine.getShp_summ() - calculationStateLine.getPay_summ();
				}
				if (calculationStateLine.getShp_summ() <= calculationStateLine.getPay_summ())
				{
					balancePay = calculationStateLine.getPay_summ() - calculationStateLine.getShp_summ();
				}

				Double[] sums = currencySum.get(calculationStateLine.getCon_currency());
				double sumA = calculationStateLine.getShp_summ();
				double sumB = calculationStateLine.getPay_summ();
				if (null != sums)
				{
					sumA += sums[0];
					sumB += sums[1];
				}
				currencySum.put(calculationStateLine.getCon_currency(), new Double[]{sumA, sumB});

				try
				{
					calculationStateLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.total"));
				}
				catch (Exception e)
				{
					log.error(e);
				}
				calculationStateLine.setNeedFormatCon(false);
				calculationStateLines.set(i, calculationStateLine);

				CalculationStateLine deviationLine = getEmptyCalculationStateLine();
				deviationLine.setKey_id(keyId);
				keyId++;
				try
				{
					deviationLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.deviation"));
				}
				catch (Exception e)
				{
					log.error(e);
				}
				deviationLine.setItog_line(true);
				deviationLine.setDeviateLine(true);
				deviationLine.setNeedFormatCon(false);
				deviationLine.setShp_summ(deviateShipping);
				deviationLine.setPay_summ(deviatePay);
				calculationStateLines.add(i + 1, deviationLine);
				i++;

				CalculationStateLine balanceLine = getEmptyCalculationStateLine();
				balanceLine.setKey_id(keyId);
				keyId++;
				try
				{
					balanceLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.balance"));
				}
				catch (Exception e)
				{
					log.error(e);
				}
				balanceLine.setItog_line(true);
				balanceLine.setBalanceLine(true);
				balanceLine.setShp_summ(balanceShipping);
				balanceLine.setPay_summ(balancePay);
				balanceLine.setNeedFormatCon(false);
				calculationStateLines.add(i + 1, balanceLine);
				i++;
			}

			if (getIsDebit() && calculationStateLine.isCtr_line())
			{
				if (!firstCtr)
				{
					if (!notEmptyCtr && rememberEmptyCtr != -1) //удоляем пустого контрагента
					{
						calculationStateLines.remove(rememberEmptyCtr);
						i--;
					}
				}
				if (firstCtr)
				{
					firstCtr = false;
				}
				notEmptyCtr = false;
				rememberEmptyCtr = i;
			}
			if (getIsDebit() && !calculationStateLine.isCtr_line() && calculationStateLine.isCon_line())
			{
				//запоминаем позицию - если сальдо <= 0 то начиная с этой позиции нужно удалять строки
				rememberI = i;
				countForDelete = 0;
			}
			if (!calculationStateLine.isRest_doc_line())
			{
				countForDelete++;
			}

			if ((calculationStateLine.isItog_line() || calculationStateLine.isRest_doc_line()) && getIsDebit())
			{
				double balance = calculationStateLine.getShp_summ() - calculationStateLine.getPay_summ();
				if (balance <= 0) //удоляем все по спеце
				{
					for (int j = 0; j < countForDelete; j++)
					{
						calculationStateLines.remove(rememberI);
						i--;
					}
				}
				else
				{
					notEmptyCtr = true;
					calculationStateLine.setShp_saldo(balance);
					double sumA;
					double sumB;
					double sumC;
					if (!StringUtil.isEmpty(calculationStateLine.getCon_currency()))
					{
						sumA = calculationStateLine.getShp_summ();
						sumB = calculationStateLine.getPay_summ();
						sumC = calculationStateLine.getShp_saldo();
						Double[] sums = currencySum.get(calculationStateLine.getCon_currency());
						if (null != sums)
						{
							sumA += sums[0];
							sumB += sums[1];
							sumC += sums[2];
						}
						currencySum.put(calculationStateLine.getCon_currency(), new Double[]{sumA, sumB, sumC});
					}
				}
				countForDelete = 0;
			}

		} //for

		//Если отмечено "Не включать в отчет контракты с истёкшим сроком дейтсвия с нулевым сальдо" - удаляем такие записи.
		if (!getNotShowExpiredContractZeroBalance().isEmpty())
		{
			rememberI = 0;
			countForDelete = 0;
			for (int i = 0; i < calculationStateLines.size(); i++)
			{
				CalculationStateLine calculationStateLine = calculationStateLines.get(i);

				if (!StringUtil.isEmpty(calculationStateLine.getCon_final_date()) && calculationStateLine.getCon_day_before_final() <= 0)
				{
					rememberI = i;
				}
				if (rememberI != 0)
				{
					countForDelete++;
				}

				if (calculationStateLine.isBalanceLine() && !getIsDebit())
				{
					if (rememberI != 0 && calculationStateLine.getShp_summ() == 0 && calculationStateLine.getPay_summ() == 0)
					{
						for (int j = 0; j < countForDelete; j++)
						{
							calculationStateLines.remove(rememberI);
							i--;
						}
					}
					rememberI = 0;
					countForDelete = 0;
				}
			}//for (int i = 0; i < calculationStateLines.size(); i++)
		}

		if (!getIsDebit())
		{
			boolean captionAdded = false;
			for (String conCurrency : currencySum.keySet())
			{
				Double[] sums = currencySum.get(conCurrency);
				CalculationStateLine balanceLine = getEmptyCalculationStateLine();
				balanceLine.setKey_id(keyId);
				keyId++;

				if (!captionAdded)
				{
					try
					{
						balanceLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.commonBalance"));
						captionAdded = true;
					}
					catch (Exception e)
					{
						log.error(e);
					}
				}
				balanceLine.setItog_line(true);
				balanceLine.setBalanceLine(true);
				balanceLine.setCommonBalanceLine(true);
				balanceLine.setCon_currency(conCurrency);
				balanceLine.setNeedFormatCon(false);
				double sumA = sums[0];
				double sumB = sums[1];
				if (sumA > sumB)
				{
					balanceLine.setShp_summ(sumA - sumB);
				}
				else
				{
					balanceLine.setPay_summ(sumB - sumA);
				}

				if (isNeedAllBalance())
				{
					AddSumsToAllBalance(listAllBalance, balanceLine);
				}

				calculationStateLines.add(calculationStateLines.size(), balanceLine);
			}
		} //if (!getIsDebit())

		if (getIsDebit()) //последний клиет и он пустой
		{
			if (!firstCtr)
			{
				if (!notEmptyCtr && rememberEmptyCtr != -1) //удоляем пустого контрагента
				{
					calculationStateLines.remove(rememberEmptyCtr);
				}
			}
		} //if (getIsDebit())

		if (getIsDebit())
		{
			boolean captionAdded = false;
			for (String conCurrency : currencySum.keySet())
			{
				Double[] sums = currencySum.get(conCurrency);
				CalculationStateLine balanceLine = getEmptyCalculationStateLine();
				balanceLine.setKey_id(keyId);
				keyId++;

				if (!captionAdded)
				{
					try
					{
						balanceLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.commonBalance"));
						captionAdded = true;
					}
					catch (Exception e)
					{
						log.error(e);
					}
				}
				balanceLine.setItog_line(true);
				balanceLine.setCon_currency(conCurrency);
				balanceLine.setShp_summ(sums[0]);
				balanceLine.setPay_summ(sums[1]);
				balanceLine.setShp_saldo(sums[2]);
				balanceLine.setNeedFormatCon(false);

				if (isNeedAllBalance())
				{
					AddSumsToAllBalance(listAllBalance, balanceLine);
				}

				calculationStateLines.add(calculationStateLines.size(), balanceLine);
			}
		} //if (getIsDebit())

    /*
        Оплаты, привязанные к спеке, сортировать в хронологическом порядке. Вверху старые, внизу - новые
    */
		int firstStr = -1;
		int lastStr = -1;
		for (int i = 0; i < calculationStateLines.size(); i++)
		{
			CalculationStateLine calculationStateLine = calculationStateLines.get(i);
			//не пустой номер спеки - нашли первую строку
			if (!StringUtil.isEmpty(calculationStateLine.getSpc_number()))
			{
				firstStr = i;
				lastStr = -1;
			}

			//если нашли первую строку и встретили итоговую - берем на одну раньше итоговой
			if (firstStr != -1 && calculationStateLine.isItog_line())
			{
				lastStr = i - 1;
			}

			//если нашли первую строку и последнюю и последняя больше первой
			// то сортируем оплаты и сбрасываем счетчики
			if (firstStr != -1 && lastStr != -1)
			{
				if (lastStr > firstStr)
				{
					for (int l = firstStr; l <= lastStr; l++)
					{
						CalculationStateLine calculationStateLine1 = calculationStateLines.get(l);
						for (int m = lastStr; m >= l; m--)
						{
							CalculationStateLine calculationStateLine2 = calculationStateLines.get(m);
							Date payDate1 = StringUtil.getCurrentDateTime();
							Date payDate2 = StringUtil.getCurrentDateTime();
							try
							{
								if (!StringUtil.isEmpty(calculationStateLine1.getPay_date()))
								{
									payDate1 = StringUtil.appDateString2Date(calculationStateLine1.getPay_date_formatted());
								}
								if (!StringUtil.isEmpty(calculationStateLine2.getPay_date()))
								{
									payDate2 = StringUtil.appDateString2Date(calculationStateLine2.getPay_date_formatted());
								}
								if (payDate2.before(payDate1))
								{
									String payId = calculationStateLine1.getPay_id();
									String payCurrency = calculationStateLine1.getPay_currency();
									String payDate = calculationStateLine1.getPay_date();
									String payDateExpiration = calculationStateLine1.getPay_date_expiration();
									double paySum = calculationStateLine1.getPay_summ();
									String payClosed = calculationStateLine1.getPay_closed();
									String payBlock = calculationStateLine1.getPay_block();
									String payComment = calculationStateLine1.getPay_comment();

									calculationStateLine1.setPay_id(calculationStateLine2.getPay_id());
									calculationStateLine1.setPay_currency(calculationStateLine2.getPay_currency());
									calculationStateLine1.setPay_date(calculationStateLine2.getPay_date());
									calculationStateLine1.setPay_date_expiration(calculationStateLine2.getPay_date_expiration());
									calculationStateLine1.setPay_summ(calculationStateLine2.getPay_summ());
									calculationStateLine1.setPay_closed(calculationStateLine2.getPay_closed());
									calculationStateLine1.setPay_block(calculationStateLine2.getPay_block());
									calculationStateLine1.setPay_comment(calculationStateLine2.getPay_comment());

									calculationStateLine2.setPay_id(payId);
									calculationStateLine2.setPay_currency(payCurrency);
									calculationStateLine2.setPay_date(payDate);
									calculationStateLine2.setPay_date_expiration(payDateExpiration);
									calculationStateLine2.setPay_summ(paySum);
									calculationStateLine2.setPay_closed(payClosed);
									calculationStateLine2.setPay_block(payBlock);
									calculationStateLine2.setPay_comment(payComment);
								}
							}
							catch (Exception e)
							{
								log.error(e.toString());
							}
						}
					}
				}
				firstStr = -1;
				lastStr = -1;
			}
		} //for (int i = 0; i < calculationStateLines.size(); i++)


    /*
        Отгрузки, привязанные к спеке, сортировать в хронологическом порядке. Вверху старые, внизу - новые
    */
		firstStr = -1;
		lastStr = -1;
		for (int i = 0; i < calculationStateLines.size(); i++)
		{
			CalculationStateLine calculationStateLine = calculationStateLines.get(i);
			//не пустой номер спеки - нашли первую строку
			if (!StringUtil.isEmpty(calculationStateLine.getSpc_number()))
			{
				firstStr = i;
				lastStr = -1;
			}

			//если нашли первую строку и встретили итоговую - берем на одну раньше итоговой
			if (firstStr != -1 && calculationStateLine.isItog_line())
			{
				lastStr = i - 1;
			}

			//если нашли первую строку и последнюю и последняя больше первой
			// то сортируем оплаты и сбрасываем счетчики
			if (firstStr != -1 && lastStr != -1)
			{
				if (lastStr > firstStr)
				{
					for (int l = firstStr; l <= lastStr; l++)
					{
						CalculationStateLine calculationStateLine1 = calculationStateLines.get(l);
						for (int m = lastStr; m >= l; m--)
						{
							CalculationStateLine calculationStateLine2 = calculationStateLines.get(m);
							Date shpDate1 = StringUtil.getCurrentDateTime();
							Date shpDate2 = StringUtil.getCurrentDateTime();
							try
							{
								if (!StringUtil.isEmpty(calculationStateLine1.getShp_date()))
								{
									shpDate1 = StringUtil.appDateString2Date(calculationStateLine1.getShp_date_formatted());
								}
								if (!StringUtil.isEmpty(calculationStateLine2.getShp_date()))
								{
									shpDate2 = StringUtil.appDateString2Date(calculationStateLine2.getShp_date_formatted());
								}
								if (shpDate2.before(shpDate1))
								{
									String shpId = calculationStateLine1.getShp_id();
									String shpNumber = calculationStateLine1.getShp_number();
									String shpDate = calculationStateLine1.getShp_date();
									String shpDateExpiration = calculationStateLine1.getShp_date_expiration();
									double shpSum = calculationStateLine1.getShp_summ();
									String shpClosed = calculationStateLine1.getShp_closed();
									String shpComment = calculationStateLine1.getShp_comment();
									double shpSaldo = calculationStateLine1.getShp_saldo();
									String shpLetter1Date = calculationStateLine1.getShp_letter1_date();
									String shpLetter2Date = calculationStateLine1.getShp_letter2_date();
									String shpLetter3Date = calculationStateLine1.getShp_letter3_date();
									String shpComplaintInCourtDate = calculationStateLine1.getShp_complaint_in_court_date();
									String shpOriginal = calculationStateLine1.getShp_original();
									String usrIdListShp = calculationStateLine1.getUsr_id_list_shp();
									int shpNoAct = calculationStateLine1.getShp_no_act();
									String managers = calculationStateLine1.getManagers();
									String stuffCategories = calculationStateLine1.getStuff_categories();

									calculationStateLine1.setShp_id(calculationStateLine2.getShp_id());
									calculationStateLine1.setShp_number(calculationStateLine2.getShp_number());
									calculationStateLine1.setShp_date(calculationStateLine2.getShp_date());
									calculationStateLine1.setShp_date_expiration(calculationStateLine2.getShp_date_expiration());
									calculationStateLine1.setShp_summ(calculationStateLine2.getShp_summ());
									calculationStateLine1.setShp_closed(calculationStateLine2.getShp_closed());
									calculationStateLine1.setShp_comment(calculationStateLine2.getShp_comment());
									calculationStateLine1.setShp_saldo(calculationStateLine2.getShp_saldo());
									calculationStateLine1.setShp_letter1_date(calculationStateLine2.getShp_letter1_date());
									calculationStateLine1.setShp_letter2_date(calculationStateLine2.getShp_letter2_date());
									calculationStateLine1.setShp_letter3_date(calculationStateLine2.getShp_letter3_date());
									calculationStateLine1.setShp_complaint_in_court_date(calculationStateLine2.getShp_complaint_in_court_date());
									calculationStateLine1.setShp_original(calculationStateLine2.getShp_original());
									calculationStateLine1.setUsr_id_list_shp(calculationStateLine2.getUsr_id_list_shp());
									calculationStateLine1.setShp_no_act(calculationStateLine2.getShp_no_act());
									calculationStateLine1.setManagers(calculationStateLine2.getManagers());
									calculationStateLine1.setStuff_categories(calculationStateLine2.getStuff_categories());

									calculationStateLine2.setShp_id(shpId);
									calculationStateLine2.setShp_number(shpNumber);
									calculationStateLine2.setShp_date(shpDate);
									calculationStateLine2.setShp_date_expiration(shpDateExpiration);
									calculationStateLine2.setShp_summ(shpSum);
									calculationStateLine2.setShp_closed(shpClosed);
									calculationStateLine2.setShp_comment(shpComment);
									calculationStateLine2.setShp_saldo(shpSaldo);
									calculationStateLine2.setShp_letter1_date(shpLetter1Date);
									calculationStateLine2.setShp_letter2_date(shpLetter2Date);
									calculationStateLine2.setShp_letter3_date(shpLetter3Date);
									calculationStateLine2.setShp_complaint_in_court_date(shpComplaintInCourtDate);
									calculationStateLine2.setShp_original(shpOriginal);
									calculationStateLine2.setUsr_id_list_shp(usrIdListShp);
									calculationStateLine2.setShp_no_act(shpNoAct);
									calculationStateLine2.setManagers(managers);
									calculationStateLine2.setStuff_categories(stuffCategories);
								}
							}
							catch (Exception e)
							{
								log.error(e.toString());
							}
						}
					}
				}
				firstStr = -1;
				lastStr = -1;
			}
		} //for (int i = 0; i < calculationStateLines.size(); i++)

		if (isNeedAllBalance())
		{
			for (CurrencyKey currencyKey : listAllBalance)
			{
				CalculationStateLine balanceAllLine = getEmptyCalculationStateLine();
				balanceAllLine.setItog_line(true);
				if (!getIsDebit())
				{
					balanceAllLine.setBalanceLine(true);
					balanceAllLine.setCommonBalanceLine(true);
				}
				balanceAllLine.setNeedFormatCon(false);
				try
				{
					if (!getIsDebit())
					{
						balanceAllLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.balance_all_expand"));
					}
					else
					{
						balanceAllLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.balance_all"));
					}
				}
				catch (Exception e)
				{
					log.error(e);
				}
				balanceAllLine.setCon_currency(currencyKey.getCurrency());
				balanceAllLine.setShp_summ(currencyKey.getSumShp());
				balanceAllLine.setPay_summ(currencyKey.getSumPay());
				if (getIsDebit())
				{
					balanceAllLine.setShp_saldo(currencyKey.getSumBalance());
				}
				calculationStateLines.add(calculationStateLines.size(), balanceAllLine);

				if (!getIsDebit())
				{
					balanceAllLine = getEmptyCalculationStateLine();
					balanceAllLine.setItog_line(true);
					balanceAllLine.setBalanceLine(true);
					balanceAllLine.setCommonBalanceLine(true);
					balanceAllLine.setNeedFormatCon(false);
					try
					{
						balanceAllLine.setCon_number(StrutsUtil.getMessage(context, "CalculationState.balance_all_collapse"));
					}
					catch (Exception e)
					{
						log.error(e);
					}

					//=A-B, если А > B
					if (currencyKey.getSumShp() > currencyKey.getSumPay())
					{
						balanceAllLine.setShp_summ(currencyKey.getSumShp() - currencyKey.getSumPay());
					}
					else //=B-A, если А<B
					{
						balanceAllLine.setPay_summ(currencyKey.getSumPay() - currencyKey.getSumShp());
					}
					calculationStateLines.add(calculationStateLines.size(), balanceAllLine);
				}
			} //for
		}
	}

	private void AddSumsToAllBalance(List<CurrencyKey> listAllBalance, CalculationStateLine balanceLine)
	{
		CurrencyKey key = new CurrencyKey(balanceLine.getCon_currency());
		int idx = listAllBalance.indexOf(key);
		if (idx == -1)
		{
			listAllBalance.add(key);
		}
		else
		{
			key = listAllBalance.get(idx);
		}
		key.setSumShp(key.getSumShp() + balanceLine.getShp_summ());
		key.setSumPay(key.getSumPay() + balanceLine.getPay_summ());
		key.setSumBalance(key.getSumBalance() + balanceLine.getShp_saldo());
	}

	public static CalculationStateLine getEmptyCalculationStateLine()
	{
		CalculationStateLine calculationStateLine = new CalculationStateLine();
		calculationStateLine.setUsr_id_list_con("");
		calculationStateLine.setDep_id_list_con("");
		calculationStateLine.setUsr_id_list_shp("");

		return calculationStateLine;
	}

	public void cleanList()
	{
		calculationStateLines.clear();
	}

	public List getExcelTable(HSSFWorkbook wb)
	{
		IActionContext context = ActionContext.threadInstance();
		List<Object> rows = new ArrayList<Object>();

		List<Object> header = new ArrayList<Object>();
		try
		{
			header.add(StrutsUtil.getMessage(context, "CalculationState.con_number_date"));
			header.add(StrutsUtil.getMessage(context, "CalculationState.spc_number_date"));
			header.add(StrutsUtil.getMessage(context, "CalculationState.spc_summ"));
			if (!isSelectedCurrency())
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.con_currency"));
			}
			if ("1".equals(getView_pay_cond()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.spc_add_pay_cond"));
			}
			if (!getIsDebit() && "1".equals(getView_delivery_cond()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.spc_delivery_cond"));
				header.add(StrutsUtil.getMessage(context, "CalculationState.spc_delivery_date"));
			}
			if (!getIsDebit() && "1".equals(getView_expiration()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.shp_expiration"));
				header.add(StrutsUtil.getMessage(context, "CalculationState.pay_expiration"));
			}
			if (getIsDebit() && "1".equals(getView_expiration()))
			{
				header.add("");
			}
			if ("1".equals(getView_complaint()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.complaint"));
			}
			if ("1".equals(getView_comment()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.comment"));
			}
			header.add(StrutsUtil.getMessage(context, "CalculationState.shp_number"));
			header.add(StrutsUtil.getMessage(context, "CalculationState.shp_date"));
			header.add(StrutsUtil.getMessage(context, "CalculationState.shp_summ"));
			header.add(StrutsUtil.getMessage(context, "CalculationState.pay_date"));
			header.add(StrutsUtil.getMessage(context, "CalculationState.pay_summ"));
			if (getIsDebit())
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.shp_saldo"));
			}
			if ("1".equals(getView_manager()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.managers"));
			}
			if ("1".equals(getView_stuff_category()))
			{
				header.add(StrutsUtil.getMessage(context, "CalculationState.stuff_categories"));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
		rows.add(header);

		for (CalculationStateLine calculationStateLine : calculationStateLines)
		{
			List<Object> record = new ArrayList<Object>();

			record.add(calculationStateLine.getCon_number_date());
			record.add(calculationStateLine.getSpc_number_date());
			if ((calculationStateLine.isItog_line() || calculationStateLine.getSpc_summ() != 0) &&
							(!calculationStateLine.isDeviateLine() && !calculationStateLine.isBalanceLine() && !calculationStateLine.isCommonBalanceLine())
							)
			{
				record.add(calculationStateLine.getSpc_summ());
			}
			else
			{
				record.add("");
			}
			if (!isSelectedCurrency())
			{
				record.add(calculationStateLine.getCon_currency());
			}
			if ("1".equals(getView_pay_cond()))
			{
				record.add(calculationStateLine.getSpc_add_pay_condExcel());
			}
			if (!getIsDebit() && "1".equals(getView_delivery_cond()))
			{
				record.add(calculationStateLine.getSpc_delivery_condExcel());
				record.add(calculationStateLine.getSpc_delivery_date_formatted());
			}
			if (!getIsDebit() && "1".equals(getView_expiration()))
			{
				record.add(calculationStateLine.getShp_expiration_excel());
				record.add(calculationStateLine.getPay_expiration_excel_formatted());
			}
			if (getIsDebit() && "1".equals(getView_expiration()))
			{
				record.add(calculationStateLine.getShp_expiration_excel());
			}
			if ("1".equals(getView_complaint()))
			{
				String outStr = calculationStateLine.getComplaint().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
				outStr = outStr.replaceAll(",,", ",");
				record.add(outStr);
			}
			if ("1".equals(getView_comment()))
			{
				record.add(calculationStateLine.getCommentExcel().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator));
			}
			record.add(calculationStateLine.getShpNumberExcel());
			record.add(calculationStateLine.getShp_date_formatted());
			if (calculationStateLine.isItog_line() || calculationStateLine.getShp_summ() != 0)
			{
				if (calculationStateLine.isBalanceLine() ||
								(calculationStateLine.isDeviateLine() && calculationStateLine.getShp_summ() < 0))
				{
					record.add(new Grid2Excel.CellFormat(wb, calculationStateLine.getShp_summ(), HorizontalAlignment.RIGHT, HSSFFont.COLOR_RED, true));
				}
				else
				{
					record.add(calculationStateLine.getShp_summ());
				}
			}
			else
			{
				record.add("");
			}
			record.add(calculationStateLine.getPay_date_formatted());
			if (calculationStateLine.isItog_line() || calculationStateLine.getPay_summ() != 0)
			{
				if (calculationStateLine.isDeviateLine() && calculationStateLine.getPay_summ() < 0)
				{
					record.add(new Grid2Excel.CellFormat(wb, calculationStateLine.getPay_summ(), HorizontalAlignment.RIGHT, HSSFFont.COLOR_RED, true));
				}
				else if (calculationStateLine.isBalanceLine())
				{
					record.add(new Grid2Excel.CellFormat(wb, calculationStateLine.getPay_summ(), HorizontalAlignment.RIGHT, (short) 0xc /*blue*/, true));
				}
				else
				{
					record.add(calculationStateLine.getPay_summ());
				}
			}
			else
			{
				record.add("");
			}
			if (getIsDebit())
			{
				if (calculationStateLine.isCtr_line())
				{
					record.add("");
				}
				else
				{
					if (!calculationStateLine.isItog_line() && !calculationStateLine.isRest_doc_line())
					{
						record.add("");
					}
					else
					{
						record.add(calculationStateLine.getShp_saldo());
					}
				}
			}
			if ("1".equals(getView_manager()))
			{
				String tmpStr = calculationStateLine.getManagersFormatted();
				if (!StringUtil.isEmpty(tmpStr))
				{
					tmpStr = tmpStr.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
					tmpStr = tmpStr.substring(0, tmpStr.length() - 2);
				}
				record.add(tmpStr);
			}
			if ("1".equals(getView_stuff_category()))
			{
				String tmpStr = calculationStateLine.getStuff_categories();
				if (!StringUtil.isEmpty(tmpStr))
				{
					tmpStr = tmpStr.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
					tmpStr = tmpStr.substring(0, tmpStr.length() - 2);
				}
				record.add(tmpStr);
			}

			rows.add(record);
		}

		return rows;
	}

	public boolean isNeedAllBalance()
	{
		return "-1".equals(contractor.getId());
	}

	static public class CurrencyKey
	{
		String currency;
		double sumShp;
		double sumPay;
		double sumBalance;

		public CurrencyKey(String currency)
		{
			this.currency = currency;
		}

		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CurrencyKey that = (CurrencyKey) o;

			return !(currency != null ? !currency.equals(that.currency) : that.currency != null);
		}

		public int hashCode()
		{
			int result;
			result = (currency != null ? currency.hashCode() : 0);
			return result;
		}

		public String getCurrency()
		{
			return currency;
		}

		public void setCurrency(String currency)
		{
			this.currency = currency;
		}

		public double getSumShp()
		{
			return sumShp;
		}

		public void setSumShp(double sumShp)
		{
			this.sumShp = sumShp;
		}

		public double getSumPay()
		{
			return sumPay;
		}

		public void setSumPay(double sumPay)
		{
			this.sumPay = sumPay;
		}

		public double getSumBalance()
		{
			return sumBalance;
		}

		public void setSumBalance(double sumBalance)
		{
			this.sumBalance = sumBalance;
		}
	}
}
