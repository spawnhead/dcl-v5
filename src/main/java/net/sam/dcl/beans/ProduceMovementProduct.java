package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ProduceMovementProduct implements Serializable
{
	protected static Log log = LogFactory.getLog(ProduceMovementProduct.class);

	boolean showLegend;
	boolean showLegendInput;
	boolean showLegendOrder;
	boolean showLegendTransit;

	int prd_id;
	int stf_id;
	String ord_id;
	String asm_id;
	String dlr_id;
	String spi_id;
	String prc_id;
	String shp_id;
	double trn_produce_count;
	double prc_produce_count;
	double shp_produce_count;
	DboCatalogNumber catalogNumber = new DboCatalogNumber();
	List<Order> order = new ArrayList<Order>();
	List<Transit> transit = new ArrayList<Transit>();
	List<ProduceCost> produceCost = new ArrayList<ProduceCost>();
	List<Shipping> shipping = new ArrayList<Shipping>();
	List<ProduceResultListLine> resultList = new ArrayList<ProduceResultListLine>();
	List<ProduceResultListLine> resultListChain = new ArrayList<ProduceResultListLine>();

	double countOrd;
	double countOrdExecuted;
	double countOrdRest;
	double restOrd;
	double countTransit;
	double countTransitRest;
	double countIn;
	double countOut;
	double rest;
	//эти линии аналогичны в 2-х списках, сохраняем чтобы не мучатся переформировывать второй раз
	ProduceResultListLine produceResultListLineFirst = new ProduceResultListLine();
	ProduceResultListLine produceResultListLineTotal1 = new ProduceResultListLine();
	ProduceResultListLine produceResultListLineTotal2 = new ProduceResultListLine();

	public ProduceMovementProduct()
	{
	}

	public ProduceMovementProduct(ProduceMovementProduct produceMovement)
	{
		prd_id = produceMovement.getPrd_id();
		stf_id = produceMovement.getStf_id();
		ord_id = produceMovement.getOrd_id();
		asm_id = produceMovement.getAsm_id();
		dlr_id = produceMovement.getDlr_id();
		spi_id = produceMovement.getSpi_id();
		prc_id = produceMovement.getPrc_id();
		shp_id = produceMovement.getShp_id();
		trn_produce_count = produceMovement.getTrn_produce_count();
		prc_produce_count = produceMovement.getPrc_produce_count();
		shp_produce_count = produceMovement.getShp_produce_count();
		catalogNumber = produceMovement.getCatalogNumber();
		showLegend = produceMovement.isShowLegend();
		showLegendInput = produceMovement.isShowLegendInput();
		showLegendOrder = produceMovement.isShowLegendOrder();
		showLegendTransit = produceMovement.isShowLegendTransit();
		order = produceMovement.getOrder();
		transit = produceMovement.getTransit();
		produceCost = produceMovement.getProduceCost();
		shipping = produceMovement.getShipping();
	}

	public int getPrd_id()
	{
		return prd_id;
	}

	public void setPrd_id(int prd_id)
	{
		this.prd_id = prd_id;
	}

	public int getStf_id()
	{
		return stf_id;
	}

	public void setStf_id(int stf_id)
	{
		this.stf_id = stf_id;
	}

	public String getOrd_id()
	{
		return ord_id;
	}

	public void setOrd_id(String ord_id)
	{
		this.ord_id = ord_id;
	}

	public String getAsm_id()
	{
		return asm_id;
	}

	public void setAsm_id(String asm_id)
	{
		this.asm_id = asm_id;
	}

	public String getDlr_id()
	{
		return dlr_id;
	}

	public void setDlr_id(String dlr_id)
	{
		this.dlr_id = dlr_id;
	}

	public String getSpi_id()
	{
		return spi_id;
	}

	public void setSpi_id(String spi_id)
	{
		this.spi_id = spi_id;
	}

	public String getPrc_id()
	{
		return prc_id;
	}

	public void setPrc_id(String prc_id)
	{
		this.prc_id = prc_id;
	}

	public String getShp_id()
	{
		return shp_id;
	}

	public void setShp_id(String shp_id)
	{
		this.shp_id = shp_id;
	}

	public double getTrn_produce_count()
	{
		return trn_produce_count;
	}

	public void setTrn_produce_count(double trn_produce_count)
	{
		this.trn_produce_count = trn_produce_count;
	}

	public double getPrc_produce_count()
	{
		return prc_produce_count;
	}

	public void setPrc_produce_count(double prc_produce_count)
	{
		this.prc_produce_count = prc_produce_count;
	}

	public double getShp_produce_count()
	{
		return shp_produce_count;
	}

	public void setShp_produce_count(double shp_produce_count)
	{
		this.shp_produce_count = shp_produce_count;
	}

	public DboCatalogNumber getCatalogNumber()
	{
		return catalogNumber;
	}

	public void setCatalogNumber(DboCatalogNumber catalogNumber)
	{
		this.catalogNumber = catalogNumber;
		this.stf_id = catalogNumber.getStuffCategory().getId();
	}

	public boolean isShowLegend()
	{
		return showLegend;
	}

	public void setShowLegend(boolean showLegend)
	{
		this.showLegend = showLegend;
	}

	public boolean isShowLegendInput()
	{
		return showLegendInput;
	}

	public void setShowLegendInput(boolean showLegendInput)
	{
		this.showLegendInput = showLegendInput;
	}

	public boolean isShowLegendOrder()
	{
		return showLegendOrder;
	}

	public void setShowLegendOrder(boolean showLegendOrder)
	{
		this.showLegendOrder = showLegendOrder;
	}

	public boolean isShowLegendTransit()
	{
		return showLegendTransit;
	}

	public void setShowLegendTransit(boolean showLegendTransit)
	{
		this.showLegendTransit = showLegendTransit;
	}

	public List<Order> getOrder()
	{
		return order;
	}

	public void setOrder(List<Order> order)
	{
		this.order = order;
	}

	public List<Transit> getTransit()
	{
		return transit;
	}

	public void setTransit(List<Transit> transit)
	{
		this.transit = transit;
	}

	public List<ProduceCost> getProduceCost()
	{
		return produceCost;
	}

	public void setProduceCost(List<ProduceCost> produceCost)
	{
		this.produceCost = produceCost;
	}

	public List<Shipping> getShipping()
	{
		return shipping;
	}

	public void setShipping(List<Shipping> shipping)
	{
		this.shipping = shipping;
	}

	public List getResultList()
	{
		return resultList;
	}

	public List getResultListChain()
	{
		return resultListChain;
	}

	public double getCountOrd()
	{
		return countOrd;
	}

	public void setCountOrd(double countOrd)
	{
		this.countOrd = countOrd;
	}

	public double getCountOrdExecuted()
	{
		return countOrdExecuted;
	}

	public void setCountOrdExecuted(double countOrdExecuted)
	{
		this.countOrdExecuted = countOrdExecuted;
	}

	public double getCountOrdRest()
	{
		return countOrdRest;
	}

	public void setCountOrdRest(double countOrdRest)
	{
		this.countOrdRest = countOrdRest;
	}

	public double getRestOrd()
	{
		return restOrd;
	}

	public void setRestOrd(double restOrd)
	{
		this.restOrd = restOrd;
	}

	public double getCountTransit()
	{
		return countTransit;
	}

	public void setCountTransit(double countTransit)
	{
		this.countTransit = countTransit;
	}

	public double getCountTransitRest()
	{
		return countTransitRest;
	}

	public void setCountTransitRest(double countTransitRest)
	{
		this.countTransitRest = countTransitRest;
	}

	public double getCountIn()
	{
		return countIn;
	}

	public void setCountIn(double countIn)
	{
		this.countIn = countIn;
	}

	public double getCountOut()
	{
		return countOut;
	}

	public void setCountOut(double countOut)
	{
		this.countOut = countOut;
	}

	public double getRest()
	{
		return rest;
	}

	public void setRest(double rest)
	{
		this.rest = rest;
	}

	public List<ProduceMovementProduct.ProduceResultListLine> cloneProduceList(List<ProduceMovementProduct.ProduceResultListLine> lst)
	{
		List<ProduceMovementProduct.ProduceResultListLine> resList = new ArrayList<ProduceMovementProduct.ProduceResultListLine>();
		for (ProduceMovementProduct.ProduceResultListLine produceResultListLine : lst)
		{
			resList.add(new ProduceMovementProduct.ProduceResultListLine(produceResultListLine));
		}
		return resList;
	}

	private List<ProduceMovementProduct.ProduceResultListLine> mergeLines(List<ProduceMovementProduct.ProduceResultListLine> lstIn, boolean checkOrder)
	{
		//Обнуляем идентичные строки заказа
		for (int i = 0; i < lstIn.size(); i++)
		{
			ProduceMovementProduct.ProduceResultListLine produceResultListLine = lstIn.get(i);

			if (!produceResultListLine.isEmptyLine())
			{
				for (int j = i + 1; j < lstIn.size(); j++)
				{
					ProduceMovementProduct.ProduceResultListLine line = lstIn.get(j);
					if (produceResultListLine.getOrd_number().equals(line.getOrd_number()) && produceResultListLine.getOrd_date().equals(line.getOrd_date()) && produceResultListLine.getOpr_id().equals(line.getOpr_id()) && !produceResultListLine.getOpr_id().isEmpty())
					{
						line.setOrd_produce_cnt(0);
						line.setOrd_produce_cnt_executed(0);
						line.setOrd_produce_cnt_rest(0);
					}
				}
			}
		}

		// Объединяем одинаковые строки.
		for (int i = 0; i < lstIn.size(); i++)
		{
			ProduceMovementProduct.ProduceResultListLine produceResultListLine = lstIn.get(i);

			if (!produceResultListLine.isEmptyLine())
			{
				produceResultListLine.setPrc_1c_numbers_list("; " + produceResultListLine.getLpc_1c_number());

				for (int j = i + 1; j < lstIn.size(); j++)
				{
					ProduceMovementProduct.ProduceResultListLine line = lstIn.get(j);
					if (
									produceResultListLine.getOrd_number().equals(line.getOrd_number()) &&
													produceResultListLine.getOrd_date().equals(line.getOrd_date())
									)
					{
						//Если тот же документ, но другая строка.
						if (!produceResultListLine.getOpr_id().equals(line.getOpr_id()) || (produceResultListLine.getOpr_id().isEmpty() && produceResultListLine.getOrd_id().equals(line.getOrd_id())))
						{
							produceResultListLine.setOrd_produce_cnt(produceResultListLine.getOrd_produce_cnt() + line.getOrd_produce_cnt());
							produceResultListLine.setOrd_produce_cnt_executed(produceResultListLine.getOrd_produce_cnt_executed() + line.getOrd_produce_cnt_executed());
							produceResultListLine.setOrd_produce_cnt_rest(produceResultListLine.getOrd_produce_cnt_rest() + line.getOrd_produce_cnt_rest());
						}
						// ord_id не обнуляем, потому что с ним сравниваем;
						line.setOrd_number("");
						line.setOrd_date("");
						line.setOrd_executed_date("");
						line.setOrd_produce_cnt(0);
						line.setOrd_produce_cnt_executed(0);
						line.setOrd_produce_cnt_rest(0);
					}
					if (
									produceResultListLine.getTrn_number().equals(line.getTrn_number()) &&
													produceResultListLine.getTrn_date().equals(line.getTrn_date()) &&
													(!checkOrder || (produceResultListLine.getOrd_id().equals(line.getOrd_id())))
									)
					{
						//Если тот же документ, но другая строка.
						if (!produceResultListLine.getTrn_id().equals(line.getTrn_id()))
						{
							produceResultListLine.setTrn_produce_cnt(produceResultListLine.getTrn_produce_cnt() + line.getTrn_produce_cnt());
							produceResultListLine.setTrn_produce_cnt_rest(produceResultListLine.getTrn_produce_cnt_rest() + line.getTrn_produce_cnt_rest());
						}
						line.setTrn_number("");
						line.setTrn_date("");
						line.setTrn_produce_cnt(0);
						line.setTrn_produce_cnt_rest(0);
					}
					if (
									produceResultListLine.getPrc_number().equals(line.getPrc_number()) &&
													produceResultListLine.getPrc_date().equals(line.getPrc_date()) &&
													(!checkOrder || (produceResultListLine.getOrd_id().equals(line.getOrd_id())))
									)
					{
						//Если тот же документ, но другая строка.
						if (!produceResultListLine.getLpc_id().equals(line.getLpc_id()))
						{
							produceResultListLine.setPrc_produce_cnt(produceResultListLine.getPrc_produce_cnt() + line.getPrc_produce_cnt());
							produceResultListLine.setPrc_produce_cnt_rest(produceResultListLine.getPrc_produce_cnt_rest() + line.getPrc_produce_cnt_rest());
							if (!StringUtil.isEmpty(line.getLpc_1c_number()) && !produceResultListLine.getPrc_1c_numbers_list().contains("; " + line.getLpc_1c_number()))
							{
								produceResultListLine.setPrc_1c_numbers_list(produceResultListLine.getPrc_1c_numbers_list() + (StringUtil.isEmpty(produceResultListLine.getPrc_1c_numbers_list()) ? "" : "; ") + line.getLpc_1c_number());
							}
						}
						line.setPrc_number("");
						line.setPrc_date("");
						line.setPrc_1c_numbers_list("");
						line.setPrc_produce_cnt(0);
						line.setPrc_produce_cnt_rest(0);
						line.setLpc_1c_number("");
					}
					if (
									produceResultListLine.getShp_number().equals(line.getShp_number()) &&
													produceResultListLine.getShp_date().equals(line.getShp_date()) &&
													produceResultListLine.getShp_contractor().equals(line.getShp_contractor()) &&
													produceResultListLine.getPrc_id().equals(line.getPrc_id()) &&
													(!checkOrder || (produceResultListLine.getOrd_id().equals(line.getOrd_id())))
									)
					{
						produceResultListLine.setShp_produce_cnt(produceResultListLine.getShp_produce_cnt() + line.getShp_produce_cnt());
						line.setShp_number("");
						line.setShp_date("");
						line.setShp_contractor("");
						line.setShp_produce_cnt(0);
					}
				}
			}
		}

		return lstIn;
	}

	private List<ProduceMovementProduct.ProduceResultListLine> deleteEmptyLines(List<ProduceMovementProduct.ProduceResultListLine> lstIn)
	{
		// Удаляем пусты строки.
		int i = 0;
		while (i < lstIn.size())
		{
			ProduceMovementProduct.ProduceResultListLine produceResultListLine = lstIn.get(i);
			if (produceResultListLine.isEmptyLine())
			{
				lstIn.remove(produceResultListLine);
				continue;
			}

			i++;
		}

		return lstIn;
	}

	public void formRecList(List<ProduceMovementProduct.ProduceResultListLine> lstIn)
	{
		List<ProduceMovementProduct.ProduceResultListLine> lst = mergeLines(lstIn, false);

		int ordUp = 0;
		int trnUp = 0;
		int prcUp = 0;
		int shpUp = 0;
		for (ProduceMovementProduct.ProduceResultListLine produceResultListLine : lst)
		{
			produceResultListLine.setOrdUp(ordUp);
			produceResultListLine.setTrnUp(trnUp);
			produceResultListLine.setPrcUp(prcUp);
			produceResultListLine.setShpUp(shpUp);

			if (StringUtil.isEmpty(produceResultListLine.getOrd_number()))
				ordUp++;
			if (StringUtil.isEmpty(produceResultListLine.getTrn_number()))
				trnUp++;
			if (StringUtil.isEmpty(produceResultListLine.getPrc_number()))
				prcUp++;
			if (StringUtil.isEmpty(produceResultListLine.getShp_number()))
				shpUp++;
		}

		int i = 0;
		for (ProduceMovementProduct.ProduceResultListLine produceResultListLine : lst)
		{
			if (produceResultListLine.getOrdUp() != 0)
			{
				ProduceMovementProduct.ProduceResultListLine correctedLine = lst.get(i - produceResultListLine.getOrdUp());
				correctedLine.setOrd_id(produceResultListLine.getOrd_id());
				correctedLine.setOrd_number(produceResultListLine.getOrd_number());
				correctedLine.setOrd_date(produceResultListLine.getOrd_date());
				correctedLine.setOrd_executed_date(produceResultListLine.getOrd_executed_date());
				correctedLine.setOrd_produce_cnt(produceResultListLine.getOrd_produce_cnt());
				correctedLine.setOrd_produce_cnt_executed(produceResultListLine.getOrd_produce_cnt_executed());
				correctedLine.setOrd_produce_cnt_rest(produceResultListLine.getOrd_produce_cnt_rest());
				correctedLine.setOrd_annul(produceResultListLine.getOrd_annul());

				produceResultListLine.setOrd_id("");
				produceResultListLine.setOrd_number("");
				produceResultListLine.setOrd_date("");
				produceResultListLine.setOrd_executed_date("");
				produceResultListLine.setOrd_produce_cnt(0);
				produceResultListLine.setOrd_produce_cnt_executed(0);
				produceResultListLine.setOrd_produce_cnt_rest(0);
				produceResultListLine.setOrd_annul("");
			}

			if (produceResultListLine.getTrnUp() != 0)
			{
				ProduceMovementProduct.ProduceResultListLine correctedLine = lst.get(i - produceResultListLine.getTrnUp());
				correctedLine.setAsm_id(produceResultListLine.getAsm_id());
				correctedLine.setDlr_id(produceResultListLine.getDlr_id());
				correctedLine.setSpi_id(produceResultListLine.getSpi_id());
				correctedLine.setTrn_number(produceResultListLine.getTrn_number());
				correctedLine.setTrn_date(produceResultListLine.getTrn_date());
				correctedLine.setTrn_produce_cnt(produceResultListLine.getTrn_produce_cnt());
				correctedLine.setTrn_produce_cnt_rest(produceResultListLine.getTrn_produce_cnt_rest());

				produceResultListLine.setAsm_id("");
				produceResultListLine.setDlr_id("");
				produceResultListLine.setSpi_id("");
				produceResultListLine.setTrn_number("");
				produceResultListLine.setTrn_date("");
				produceResultListLine.setTrn_produce_cnt(0);
				produceResultListLine.setTrn_produce_cnt_rest(0);
			}

			if (produceResultListLine.getPrcUp() != 0)
			{
				ProduceMovementProduct.ProduceResultListLine correctedLine = lst.get(i - produceResultListLine.getPrcUp());
				correctedLine.setPrc_id(produceResultListLine.getPrc_id());
				correctedLine.setPrc_number(produceResultListLine.getPrc_number());
				correctedLine.setPrc_date(produceResultListLine.getPrc_date());
				correctedLine.setPrc_produce_cnt(produceResultListLine.getPrc_produce_cnt());
				correctedLine.setPrc_produce_cnt_rest(produceResultListLine.getPrc_produce_cnt_rest());
				correctedLine.setPrc_1c_numbers_list(produceResultListLine.getPrc_1c_numbers_list());
				correctedLine.setLpc_1c_number(produceResultListLine.getLpc_1c_number());

				produceResultListLine.setPrc_id("");
				produceResultListLine.setPrc_number("");
				produceResultListLine.setPrc_date("");
				produceResultListLine.setPrc_produce_cnt(0);
				produceResultListLine.setPrc_produce_cnt_rest(0);
				produceResultListLine.setPrc_1c_numbers_list("");
				produceResultListLine.setLpc_1c_number("");
			}

			if (produceResultListLine.getShpUp() != 0)
			{
				ProduceMovementProduct.ProduceResultListLine correctedLine = lst.get(i - produceResultListLine.getShpUp());
				correctedLine.setShp_id(produceResultListLine.getShp_id());
				correctedLine.setShp_number(produceResultListLine.getShp_number());
				correctedLine.setShp_date(produceResultListLine.getShp_date());
				correctedLine.setShp_produce_cnt(produceResultListLine.getShp_produce_cnt());
				correctedLine.setShp_contractor(produceResultListLine.getShp_contractor());

				produceResultListLine.setShp_id("");
				produceResultListLine.setShp_number("");
				produceResultListLine.setShp_date("");
				produceResultListLine.setShp_produce_cnt(0);
				produceResultListLine.setShp_contractor("");
			}

			i++;
		}

		lst = deleteEmptyLines(lst);

		showLegend = false;
		showLegendInput = false;
		showLegendOrder = false;
		showLegendTransit = false;

		double sumOrdProduceCount = 0.0;
		double sumOrdProduceCountExecuted = 0.0;
		double sumOrdProduceCountRest = 0.0;
		double sumTrnProduceCount = 0.0;
		double sumTrnProduceCountRest = 0.0;
		double sumShpProduceCount = 0.0;
		double sumPrcProduceCount = 0.0;

		produceResultListLineFirst.setId(String.valueOf(-1));
		produceResultListLineFirst.setOrd_date(catalogNumber.getStuffCategory().getName());
		produceResultListLineFirst.setPrc_date(catalogNumber.getNumber());
		produceResultListLineFirst.setProductLine(true);

		resultList.clear();
		resultList.add(produceResultListLineFirst);

		i = 0;
		for (ProduceMovementProduct.ProduceResultListLine produceResultListLine : lst)
		{
			produceResultListLine.setId(String.valueOf(i));

			//По аннулированным заказам количество не включать в "Итого", "Остаток", "ВСЕГО"
			if (StringUtil.isEmpty(produceResultListLine.getOrd_annul()))
			{
				sumOrdProduceCount += produceResultListLine.getOrd_produce_cnt();

				//Если заказ "Заблокирован" (исполнен) - п. 887
				if (!StringUtil.isEmpty(produceResultListLine.getOrd_executed_date()) && produceResultListLine.getOrd_produce_cnt() != produceResultListLine.getOrd_produce_cnt_executed())
				{
					sumOrdProduceCountExecuted += produceResultListLine.getOrd_produce_cnt();
					showLegendOrder = true;
				}
				else
				{
					sumOrdProduceCountExecuted += produceResultListLine.getOrd_produce_cnt_executed();
				}

				sumOrdProduceCountRest += produceResultListLine.getOrd_produce_cnt_rest();
			}

			sumTrnProduceCount += produceResultListLine.getTrn_produce_cnt();
			sumTrnProduceCountRest += produceResultListLine.getTrn_produce_cnt_rest();
			sumShpProduceCount += produceResultListLine.getShp_produce_cnt();
			if (produceResultListLine.getPrc_produce_cnt_rest() > 0)
			{
				showLegendInput = true;
			}
			if (produceResultListLine.getTrn_produce_cnt_rest() > 0)
			{
				showLegendTransit = true;
			}
			sumPrcProduceCount += produceResultListLine.getPrc_produce_cnt();

			resultList.add(produceResultListLine);
		}

		i++;
		produceResultListLineTotal1.setId(String.valueOf(i));
		IActionContext context = ActionContext.threadInstance();
		try
		{
			produceResultListLineTotal1.setOrd_date(StrutsUtil.getMessage(context, "ProduceMovementList.total"));
			produceResultListLineTotal1.setPrc_date(StrutsUtil.getMessage(context, "ProduceMovementList.total"));
		}
		catch (Exception e)
		{
			log.error(e);
		}
		countOrd = sumOrdProduceCount;
		countOrdExecuted = sumOrdProduceCountExecuted;
		countOrdRest = sumOrdProduceCountRest;
		countTransit = sumTrnProduceCount;
		countTransitRest = sumTrnProduceCountRest;
		countIn = sumPrcProduceCount;
		countOut = sumShpProduceCount;
		produceResultListLineTotal1.setOrd_produce_cnt(sumOrdProduceCount);
		produceResultListLineTotal1.setOrd_produce_cnt_executed(sumOrdProduceCountExecuted);
		produceResultListLineTotal1.setOrd_produce_cnt_rest(sumOrdProduceCountRest);
		produceResultListLineTotal1.setTrn_produce_cnt(sumTrnProduceCount);
		produceResultListLineTotal1.setTrn_produce_cnt_rest(sumTrnProduceCountRest);
		produceResultListLineTotal1.setPrc_produce_cnt(sumPrcProduceCount);
		produceResultListLineTotal1.setShp_produce_cnt(sumShpProduceCount);
		produceResultListLineTotal1.setItogLine(true);

		i++;
		produceResultListLineTotal2.setId(String.valueOf(i));
		try
		{
			produceResultListLineTotal2.setOrd_date(StrutsUtil.getMessage(context, "ProduceMovementList.rest"));
			produceResultListLineTotal2.setPrc_date(StrutsUtil.getMessage(context, "ProduceMovementList.rest"));
		}
		catch (Exception e)
		{
			log.error(e);
		}
		restOrd = sumOrdProduceCount - sumOrdProduceCountExecuted;
		rest = sumPrcProduceCount - sumShpProduceCount;
		produceResultListLineTotal2.setOrd_produce_cnt(restOrd);
		produceResultListLineTotal2.setPrc_produce_cnt(rest);
		produceResultListLineTotal2.setItogLine(true);

		resultList.add(produceResultListLineTotal1);
		resultList.add(produceResultListLineTotal2);

		showLegend = showLegendInput | showLegendOrder | showLegendTransit;
	}

	public void formRecListChain(List<ProduceMovementProduct.ProduceResultListLine> lstIn)
	{
		List<ProduceMovementProduct.ProduceResultListLine> lst = mergeLines(lstIn, true);

		lst = deleteEmptyLines(lst);

		int i = 0;

		resultListChain.clear();
		resultListChain.add(resultListChain.size(), produceResultListLineFirst);

		String checkOrdId = "-1";
		for (ProduceMovementProduct.ProduceResultListLine produceResultListLine : lst)
		{
			produceResultListLine.setId(String.valueOf(i));
			if (!StringUtil.isEmpty(produceResultListLine.getOrd_id()) && !produceResultListLine.getOrd_id().equals(checkOrdId))
			{
				produceResultListLine.setNewChain(true);
				checkOrdId = produceResultListLine.getOrd_id();
			}

			resultListChain.add(produceResultListLine);
		}

		resultListChain.add(produceResultListLineTotal1);
		resultListChain.add(produceResultListLineTotal2);
	}

	static public class ProduceResultListLine
	{
		String id;
		String opr_id;
		String ord_id;
		String ord_date;
		String ord_number;
		String ord_annul;
		String ord_executed_date;
		double ord_produce_cnt;
		double ord_produce_cnt_executed;
		double ord_produce_cnt_rest;

		String asm_id;
		String dlr_id;
		String spi_id;
		String trn_id;
		String trn_date;
		String trn_number;
		double trn_produce_cnt;
		double trn_produce_cnt_rest;
		String dlr_minsk;

		String lpc_id;
		String prc_id;
		String prc_date;
		String prc_number;
		double prc_produce_cnt;
		double prc_produce_cnt_rest;
		String lpc_1c_number;
		String prc_1c_numbers_list;

		String shp_id;
		String shp_date;
		String shp_number;
		String shp_contractor;
		double shp_produce_cnt;

		boolean productLine = false;
		boolean itogLine = false;
		boolean itogLineLarge = false;
		boolean newChain = false;

		int ordUp = 0;
		int trnUp = 0;
		int prcUp = 0;
		int shpUp = 0;

		public ProduceResultListLine()
		{
		}

		public ProduceResultListLine(ProduceResultListLine produceResultListLine)
		{
			id = produceResultListLine.getId();
			opr_id = produceResultListLine.getOpr_id();
			ord_id = produceResultListLine.getOrd_id();
			ord_date = produceResultListLine.getOrd_date();
			ord_number = produceResultListLine.getOrd_number();
			ord_executed_date = produceResultListLine.getOrd_executed_date();
			ord_annul = produceResultListLine.getOrd_annul();
			ord_produce_cnt = produceResultListLine.getOrd_produce_cnt();
			ord_produce_cnt_executed = produceResultListLine.getOrd_produce_cnt_executed();
			ord_produce_cnt_rest = produceResultListLine.getOrd_produce_cnt_rest();
			asm_id = produceResultListLine.getAsm_id();
			dlr_id = produceResultListLine.getDlr_id();
			spi_id = produceResultListLine.getSpi_id();
			trn_id = produceResultListLine.getTrn_id();
			trn_date = produceResultListLine.getTrn_date();
			trn_number = produceResultListLine.getTrn_number();
			trn_produce_cnt = produceResultListLine.getTrn_produce_cnt();
			trn_produce_cnt_rest = produceResultListLine.getTrn_produce_cnt_rest();
			dlr_minsk = produceResultListLine.getDlr_minsk();
			lpc_id = produceResultListLine.getLpc_id();
			prc_id = produceResultListLine.getPrc_id();
			prc_date = produceResultListLine.getPrc_date();
			prc_number = produceResultListLine.getPrc_number();
			prc_produce_cnt = produceResultListLine.getPrc_produce_cnt();
			prc_produce_cnt_rest = produceResultListLine.getPrc_produce_cnt_rest();
			lpc_1c_number = produceResultListLine.getLpc_1c_number();
			prc_1c_numbers_list = produceResultListLine.getPrc_1c_numbers_list();
			shp_id = produceResultListLine.getShp_id();
			shp_date = produceResultListLine.getShp_date();
			shp_number = produceResultListLine.getShp_number();
			shp_contractor = produceResultListLine.getShp_contractor();
			shp_produce_cnt = produceResultListLine.getShp_produce_cnt();
			itogLine = produceResultListLine.isItogLine();
			productLine = produceResultListLine.isProductLine();
			itogLineLarge = produceResultListLine.isItogLineLarge();
			newChain = produceResultListLine.isNewChain();

			int ordUp = produceResultListLine.getOrdUp();
			int trnUp = produceResultListLine.getTrnUp();
			int prcUp = produceResultListLine.getPrcUp();
			int shpUp = produceResultListLine.getShpUp();
		}

		public String getId()
		{
			return id;
		}

		public void setId(String id)
		{
			this.id = id;
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

		public String getOrd_date()
		{
			return ord_date;
		}

		public String getOrdDateFormatted()
		{
			if (isSpecialLine() || isProductLine()) return getOrd_date();
			return StringUtil.dbDateString2appDateString(getOrd_date());
		}

		public void setOrd_date(String ord_date)
		{
			this.ord_date = ord_date;
		}

		public String getOrd_number()
		{
			return ord_number;
		}

		public String getOrdNumberFormatted()
		{
			String resStr = getOrd_number();
			if (!StringUtil.isEmpty(getOrd_annul()))
			{
				IActionContext context = ActionContext.threadInstance();
				try
				{
					resStr += " " + StrutsUtil.getMessage(context, "ProduceMovementList.ord_annul");
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}

			return resStr;
		}

		public void setOrd_number(String ord_number)
		{
			this.ord_number = ord_number;
		}

		public String getOrd_executed_date()
		{
			return ord_executed_date;
		}

		public void setOrd_executed_date(String ord_executed_date)
		{
			this.ord_executed_date = ord_executed_date;
		}

		public String getOrd_annul()
		{
			return ord_annul;
		}

		public void setOrd_annul(String ord_annul)
		{
			this.ord_annul = ord_annul;
		}

		public double getOrd_produce_cnt()
		{
			return ord_produce_cnt;
		}

		public String getOrdProduceCountFormatted()
		{
			if (StringUtil.isEmpty(getOrd_number()) && !isSpecialLine()) return "";
			boolean annul = !StringUtil.isEmpty(getOrd_annul());
			return (annul ? "<s>" : "") + StringUtil.double2appCurrencyString(getOrd_produce_cnt()) + (annul ? "</s>" : "");
		}

		public void setOrd_produce_cnt(double ord_produce_cnt)
		{
			this.ord_produce_cnt = ord_produce_cnt;
		}

		public double getOrd_produce_cnt_executed()
		{
			return ord_produce_cnt_executed;
		}

		public String getOrdProduceCountExecutedFormatted()
		{
			if (StringUtil.isEmpty(getOrd_number()) && !isSpecialLine()) return "";
			IActionContext context = ActionContext.threadInstance();
			String retStr = "";
			try
			{
				//Если заказ "Заблокирован" (исполнен) - п. 887
				String restString = getOrd_produce_cnt_rest() > 0 ? StrutsUtil.getMessage(context, "Common.bold", StringUtil.double2appCurrencyString(getOrd_produce_cnt_rest())) : StringUtil.double2appCurrencyString(getOrd_produce_cnt_rest());
				if (!StringUtil.isEmpty(getOrd_executed_date()) && getOrd_produce_cnt() != getOrd_produce_cnt_executed())
				{
					double executed = getOrd_produce_cnt() - getOrd_produce_cnt_executed();
					retStr = StringUtil.double2appCurrencyString(getOrd_produce_cnt()) + "(" + StringUtil.double2appCurrencyString(executed) + ")" + " / " + restString;
				}
				else
					retStr = StringUtil.double2appCurrencyString(getOrd_produce_cnt_executed()) + " / " + restString;
			}
			catch (Exception e)
			{
				log.error(e);
			}

			return retStr;
		}

		public void setOrd_produce_cnt_executed(double ord_produce_cnt_executed)
		{
			this.ord_produce_cnt_executed = ord_produce_cnt_executed;
		}

		public double getOrd_produce_cnt_rest()
		{
			return ord_produce_cnt_rest;
		}

		public void setOrd_produce_cnt_rest(double ord_produce_cnt_rest)
		{
			this.ord_produce_cnt_rest = ord_produce_cnt_rest;
		}

		public String getAsm_id()
		{
			return asm_id;
		}

		public void setAsm_id(String asm_id)
		{
			this.asm_id = asm_id;
		}

		public String getDlr_id()
		{
			return dlr_id;
		}

		public void setDlr_id(String dlr_id)
		{
			this.dlr_id = dlr_id;
		}

		public String getSpi_id()
		{
			return spi_id;
		}

		public void setSpi_id(String spi_id)
		{
			this.spi_id = spi_id;
		}

		public String getTrn_id()
		{
			return trn_id;
		}

		public void setTrn_id(String trn_id)
		{
			this.trn_id = trn_id;
		}

		public String getTrn_date()
		{
			return trn_date;
		}

		public String getTrnDateFormatted()
		{
			return StringUtil.dbDateString2appDateString(getTrn_date());
		}

		public void setTrn_date(String trn_date)
		{
			this.trn_date = trn_date;
		}

		public String getTrn_number()
		{
			return trn_number;
		}

		public String getTrnNumberFormatted()
		{
			if (StringUtil.isEmpty(getTrn_number())) return "";
			IActionContext context = ActionContext.threadInstance();
			String retStr = "";
			try
			{
				String fromMinsk = StrutsUtil.getMessage(context, "transitDocName.FromMinsk");
				String boldFromMinsk = StrutsUtil.getMessage(context, "Common.bold", fromMinsk);
				if ("1".equals(dlr_minsk))
					retStr = Transit.calcName(getAsm_id(), getDlr_id(), getSpi_id()) + " " + boldFromMinsk + " " + getTrn_number();
				else
					retStr = Transit.calcName(getAsm_id(), getDlr_id(), getSpi_id()) + " " + getTrn_number();
			}
			catch (Exception e)
			{
				log.error(e);
			}
			return retStr;
		}

		public void setTrn_number(String trn_number)
		{
			this.trn_number = trn_number;
		}

		public double getTrn_produce_cnt()
		{
			return trn_produce_cnt;
		}

		public double getTrn_produce_cnt_rest()
		{
			return trn_produce_cnt_rest;
		}

		public void setTrn_produce_cnt_rest(double trn_produce_cnt_rest)
		{
			this.trn_produce_cnt_rest = trn_produce_cnt_rest;
		}

		public String getTrnProduceCountFormatted()
		{
			return StringUtil.double2appCurrencyString(getTrn_produce_cnt());
		}

		public String getTrnProduceCountAndRest()
		{
			if (StringUtil.isEmpty(getTrn_number()) && !isSpecialLine()) return "";

			String resStr = "";
			if (!StringUtil.isEmpty(getTrnProduceCountFormatted()))
			{
				resStr += getTrnProduceCountFormatted();
			}

			if (getTrn_produce_cnt_rest() > 0)
			{
				resStr += " <span style=><b>(" + getTrn_produce_cnt_rest() + ")</b></span>";
			}

			return resStr;
		}

		public void setTrn_produce_cnt(double trn_produce_cnt)
		{
			this.trn_produce_cnt = trn_produce_cnt;
		}

		public String getDlr_minsk()
		{
			return dlr_minsk;
		}

		public void setDlr_minsk(String dlr_minsk)
		{
			this.dlr_minsk = dlr_minsk;
		}

		public String getLpc_id()
		{
			return lpc_id;
		}

		public void setLpc_id(String lpc_id)
		{
			this.lpc_id = lpc_id;
		}

		public String getPrc_id()
		{
			return prc_id;
		}

		public void setPrc_id(String prc_id)
		{
			this.prc_id = prc_id;
		}

		public String getPrc_date()
		{
			return prc_date;
		}

		public String getPrcDateFormatted()
		{
			if (isSpecialLine() || isProductLine()) return getPrc_date();
			return StringUtil.dbDateString2appDateString(getPrc_date());
		}

		public void setPrc_date(String prc_date)
		{
			this.prc_date = prc_date;
		}

		public String getPrc_number()
		{
			return prc_number;
		}

		public void setPrc_number(String prc_number)
		{
			this.prc_number = prc_number;
		}

		public double getPrc_produce_cnt()
		{
			return prc_produce_cnt;
		}

		public String getPrcProduceCountFormatted()
		{
			return StringUtil.double2appCurrencyString(getPrc_produce_cnt());
		}

		public void setPrc_produce_cnt(double prc_produce_cnt)
		{
			this.prc_produce_cnt = prc_produce_cnt;
		}

		public double getPrc_produce_cnt_rest()
		{
			return prc_produce_cnt_rest;
		}

		public void setPrc_produce_cnt_rest(double prc_produce_cnt_rest)
		{
			this.prc_produce_cnt_rest = prc_produce_cnt_rest;
		}

		public String getLpc_1c_number()
		{
			return lpc_1c_number;
		}

		public void setLpc_1c_number(String lpc_1c_number)
		{
			this.lpc_1c_number = lpc_1c_number;
		}

		public String getPrc_1c_numbers_list()
		{
			return prc_1c_numbers_list;
		}

		public void setPrc_1c_numbers_list(String prc_1c_numbers_list)
		{
			this.prc_1c_numbers_list = prc_1c_numbers_list;
		}

		public String getPrcProduceCountAndRest()
		{
			if (StringUtil.isEmpty(getPrc_number()) && !isSpecialLine()) return "";

			String resStr = "";
			if (!StringUtil.isEmpty(getPrcProduceCountFormatted()))
			{
				resStr += getPrcProduceCountFormatted();
			}

			if (getPrc_produce_cnt_rest() > 0)
			{
				resStr += " <span style=><b>(" + getPrc_produce_cnt_rest() + (getPrc_1c_numbers_list().equals("; ") ? "" : getPrc_1c_numbers_list()) + ")</b></span>";
			}

			return resStr;
		}

		public String getShp_id()
		{
			return shp_id;
		}

		public void setShp_id(String shp_id)
		{
			this.shp_id = shp_id;
		}

		public String getShp_date()
		{
			return shp_date;
		}

		public String getShpDateFormatted()
		{
			return StringUtil.dbDateString2appDateString(getShp_date());
		}

		public void setShp_date(String shp_date)
		{
			this.shp_date = shp_date;
		}

		public String getShp_number()
		{
			return shp_number;
		}

		public void setShp_number(String shp_number)
		{
			this.shp_number = shp_number;
		}

		public String getShp_contractor()
		{
			return shp_contractor;
		}

		public void setShp_contractor(String shp_contractor)
		{
			this.shp_contractor = shp_contractor;
		}

		public double getShp_produce_cnt()
		{
			return shp_produce_cnt;
		}

		public String getShpProduceCountFormatted()
		{
			if (StringUtil.isEmpty(getShp_number()) && !isSpecialLine()) return "";
			return StringUtil.double2appCurrencyString(getShp_produce_cnt());
		}

		public void setShp_produce_cnt(double shp_produce_cnt)
		{
			this.shp_produce_cnt = shp_produce_cnt;
		}

		public boolean isProductLine()
		{
			return productLine;
		}

		public void setProductLine(boolean productLine)
		{
			this.productLine = productLine;
		}

		public boolean isItogLine()
		{
			return itogLine;
		}

		public void setItogLine(boolean itogLine)
		{
			this.itogLine = itogLine;
		}

		public boolean isItogLineLarge()
		{
			return itogLineLarge;
		}

		public void setItogLineLarge(boolean itogLineLarge)
		{
			this.itogLineLarge = itogLineLarge;
		}

		public boolean isNewChain()
		{
			return newChain;
		}

		public void setNewChain(boolean newChain)
		{
			this.newChain = newChain;
		}

		public boolean isSpecialLine()
		{
			return isItogLine() || isItogLineLarge();
		}

		public boolean isEmptyLine()
		{
			return StringUtil.isEmpty(getOrd_number()) && StringUtil.isEmpty(getTrn_number()) && StringUtil.isEmpty(getPrc_number()) && StringUtil.isEmpty(getShp_number());
		}

		public int getOrdUp()
		{
			return ordUp;
		}

		public void setOrdUp(int ordUp)
		{
			this.ordUp = ordUp;
		}

		public int getTrnUp()
		{
			return trnUp;
		}

		public void setTrnUp(int trnUp)
		{
			this.trnUp = trnUp;
		}

		public int getPrcUp()
		{
			return prcUp;
		}

		public void setPrcUp(int prcUp)
		{
			this.prcUp = prcUp;
		}

		public int getShpUp()
		{
			return shpUp;
		}

		public void setShpUp(int shpUp)
		{
			this.shpUp = shpUp;
		}
	}
}
