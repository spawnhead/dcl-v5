package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.service.helper.ProduceMovementHelper;
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
public class GoodsCirculation implements Serializable
{
	protected static Log log = LogFactory.getLog(GoodsCirculation.class);

	List<GoodsCirculationLine> goodsCirculationLines = new ArrayList<GoodsCirculationLine>();
	List<GoodsCirculationLine> goodsCirculationOutLines = new ArrayList<GoodsCirculationLine>();
	List<GoodsCirculationYear> goodsCirculationYears = new ArrayList<GoodsCirculationYear>();
	int currentLine = 0;

	String date_begin;
	String date_end;

	String by_quarter;
	String by_month;
	String produceName;
	String stf_id;
	String sln_id;
	String ctr_id;
	String by_contractor;

	public GoodsCirculation()
	{
	}

	public List<GoodsCirculationLine> getGoodsCirculationLines()
	{
		return goodsCirculationLines;
	}

	public void setGoodsCirculationLines(List<GoodsCirculationLine> goodsCirculationLines)
	{
		this.goodsCirculationLines = goodsCirculationLines;
	}

	public List<GoodsCirculationLine> getGoodsCirculationOutLines()
	{
		return goodsCirculationOutLines;
	}

	public void setGoodsCirculationOutLines(List<GoodsCirculationLine> goodsCirculationOutLines)
	{
		this.goodsCirculationOutLines = goodsCirculationOutLines;
	}

	public List<GoodsCirculationYear> getGoodsCirculationYears()
	{
		return goodsCirculationYears;
	}

	public int getCurrentLine()
	{
		return currentLine;
	}

	public void setCurrentLine(int currentLine)
	{
		this.currentLine = currentLine;
	}

	public String getDate_begin()
	{
		return date_begin;
	}

	public void setDate_begin(String date_begin)
	{
		this.date_begin = date_begin;
	}

	public String getDate_end()
	{
		return date_end;
	}

	public void setDate_end(String date_end)
	{
		this.date_end = date_end;
	}

	public String getBy_quarter()
	{
		return by_quarter;
	}

	public void setBy_quarter(String by_quarter)
	{
		this.by_quarter = by_quarter;
	}

	public boolean isByQuarter()
	{
		return !StringUtil.isEmpty(getBy_quarter());
	}

	public String getBy_month()
	{
		return by_month;
	}

	public void setBy_month(String by_month)
	{
		this.by_month = by_month;
	}

	public boolean isByMonth()
	{
		return !StringUtil.isEmpty(getBy_month());
	}

	public String getProduceName()
	{
		return produceName;
	}

	public void setProduceName(String produceName)
	{
		this.produceName = produceName;
	}

	public String getStf_id()
	{
		return stf_id;
	}

	public void setStf_id(String stf_id)
	{
		this.stf_id = stf_id;
	}

	public String getSln_id()
	{
		return sln_id;
	}

	public void setSln_id(String sln_id)
	{
		this.sln_id = sln_id;
	}

	public String getCtr_id()
	{
		return ctr_id;
	}

	public void setCtr_id(String ctr_id)
	{
		this.ctr_id = ctr_id;
	}

	public String getBy_contractor()
	{
		return by_contractor;
	}

	public boolean isByContractor()
	{
		return !StringUtil.isEmpty(getBy_contractor());
	}

	public void setBy_contractor(String by_contractor)
	{
		this.by_contractor = by_contractor;
	}

	// defines ordering based on first name
	class customCodeComparator implements Comparator<GoodsCirculationLine>
	{
		public int compare(GoodsCirculationLine goodsCirculationLine1, GoodsCirculationLine goodsCirculationLine2)
		{
			String cmpString1 = goodsCirculationLine1.getFullName() + goodsCirculationLine1.getPrd_id();
			String cmpString2 = goodsCirculationLine2.getFullName() + goodsCirculationLine2.getPrd_id();
			if (goodsCirculationLine1.isDivideByContractor())
			{
				cmpString1 += goodsCirculationLine1.getContractor();
				cmpString2 += goodsCirculationLine2.getContractor();
			}

			cmpString1 = cmpString1.toUpperCase();
			cmpString2 = cmpString2.toUpperCase();
			return cmpString1.compareTo(cmpString2);
		}
	}

	public void calculate()
	{
		IActionContext context = ActionContext.threadInstance();

		getGoodsCirculationOutLines().clear();
		if (getGoodsCirculationLines().size() == 0)
		{
			return;
		}

		boolean divideByContractor = isByContractor();
		for (GoodsCirculationLine goodsCirculationLine : getGoodsCirculationLines())
		{
			GoodsCirculationLine goodsCirculationOutLine = new GoodsCirculationLine(goodsCirculationLine, divideByContractor);
			int idx = getGoodsCirculationOutLines().indexOf(goodsCirculationOutLine);
			if (idx == -1)
			{
				goodsCirculationOutLine.cloneGoodsCirculationYears(getGoodsCirculationYears());
				getGoodsCirculationOutLines().add(goodsCirculationOutLine);
			}
			else
			{
				goodsCirculationOutLine = getGoodsCirculationOutLines().get(idx);
				goodsCirculationOutLine.setLps_count(goodsCirculationOutLine.getLps_count() + goodsCirculationLine.getLps_count());
			}

			Date dateShp;
			try
			{
				dateShp = StringUtil.dbDateString2Date(goodsCirculationLine.getShp_date());
				Calendar c1Shp = Calendar.getInstance();
				c1Shp.setTime(dateShp);
				int year = c1Shp.get(Calendar.YEAR);
				int month = c1Shp.get(Calendar.MARCH);
				int quarter = getQuarterByMonth(month);
				if (isByQuarter())
				{
					GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationOutLine.findGoodsCirculationQuarter(year, quarter);
					if (null != goodsCirculationQuarter)
					{
						goodsCirculationQuarter.setCount(goodsCirculationQuarter.getCount() + goodsCirculationLine.getLps_count());
						if (isByMonth())
						{
							GoodsCirculationMonth goodsCirculationMonth = goodsCirculationOutLine.findGoodsCirculationMonthInQuarter(year, quarter, month);
							if (null != goodsCirculationMonth)
							{
								goodsCirculationMonth.setCount(goodsCirculationMonth.getCount() + goodsCirculationLine.getLps_count());
							}
						}
					}
				}

				if (isByMonth() && !isByQuarter())
				{
					GoodsCirculationMonth goodsCirculationMonth = goodsCirculationOutLine.findGoodsCirculationMonth(year, month);
					if (null != goodsCirculationMonth)
					{
						goodsCirculationMonth.setCount(goodsCirculationMonth.getCount() + goodsCirculationLine.getLps_count());
					}
				}
			}
			catch (Exception e)
			{
				log.error(e);
			}
		}

		try
		{
			for (GoodsCirculationLine goodsCirculationLine : getGoodsCirculationOutLines())
			{
				DboProduce produce = new DboProduce();
				produce.setId(Integer.parseInt(goodsCirculationLine.getPrd_id()));
				ProduceMovement produceMovement = ProduceMovementHelper.formProduceMovementObject(context, produce, null);
				goodsCirculationLine.setRestInLithuania(produceMovement.getCountOrdRest() + produceMovement.getCountTransitRest());
				goodsCirculationLine.setOrdInProducer(produceMovement.getRestOrd());
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		getGoodsCirculationLines().clear();
		Collections.sort(getGoodsCirculationOutLines(), new customCodeComparator());
		if (divideByContractor)
		{
			GoodsCirculationLine goodsCirculationLineNew = null;
			String cmpString = "";
			if (getGoodsCirculationOutLines().size() > 0)
			{
				GoodsCirculationLine goodsCirculationLine = getGoodsCirculationOutLines().get(0);
				cmpString = goodsCirculationLine.getPrd_id();
				goodsCirculationLineNew = new GoodsCirculationLine();
				goodsCirculationLineNew.setItogLine(true);
				goodsCirculationLineNew.cloneGoodsCirculationYears(getGoodsCirculationYears());
			}
			int i = 0;
			while (i < getGoodsCirculationOutLines().size())
			{
				GoodsCirculationLine goodsCirculationLine = getGoodsCirculationOutLines().get(i);
				String cmpStringIn = goodsCirculationLine.getPrd_id();

				if (!cmpString.equals(cmpStringIn))
				{
					getGoodsCirculationOutLines().add(i, goodsCirculationLineNew);
					i++;
					cmpString = cmpStringIn;
					goodsCirculationLineNew = new GoodsCirculationLine();
					goodsCirculationLineNew.setItogLine(true);
					goodsCirculationLineNew.cloneGoodsCirculationYears(getGoodsCirculationYears());
				}

				if (StringUtil.isEmpty(goodsCirculationLineNew.getPrd_id()))
				{
					goodsCirculationLineNew.setPrd_id(goodsCirculationLine.getPrd_id());
					goodsCirculationLineNew.setProduce_name(goodsCirculationLine.getProduce_name());
					goodsCirculationLineNew.setPrd_type(goodsCirculationLine.getPrd_type());
					goodsCirculationLineNew.setPrd_params(goodsCirculationLine.getPrd_params());
					goodsCirculationLineNew.setPrd_add_params(goodsCirculationLine.getPrd_add_params());
				}
				goodsCirculationLineNew.calcGoodsCirculationCounts(goodsCirculationLine.getGoodsCirculationYears());

				goodsCirculationLineNew.setLps_count(goodsCirculationLineNew.getLps_count() + goodsCirculationLine.getLps_count());

				i++;
			}
			if (getGoodsCirculationOutLines().size() > 0)
			{
				getGoodsCirculationOutLines().add(i, goodsCirculationLineNew);
			}
		} //if ( !StringUtil.isEmpty(by_contractor) )
	}

	private int getQuarterByMonth(int month)
	{
		if (month < 3)
		{
			return 1;
		}
		else if (month < 6)
		{
			return 2;
		}
		else if (month < 9)
		{
			return 3;
		}
		else
		{
			return 4;
		}
	}

	public int getColCount()
	{
		int col = 0;
		for (GoodsCirculationYear goodsCirculationYear : getGoodsCirculationYears())
		{
			if (isByQuarter())
			{
				col += goodsCirculationYear.getGoodsCirculationYearQuarters().size();
				if (isByMonth())
				{
					for (GoodsCirculationQuarter goodsCirculationQuarter : goodsCirculationYear.getGoodsCirculationYearQuarters())
					{
						col += goodsCirculationQuarter.getGoodsCirculationQuarterMonths().size();
					}
				}
			}
			if (!isByQuarter() && isByMonth())
			{
				col += goodsCirculationYear.getGoodsCirculationYearMonths().size();
			}
		}

		return col;
	}

	public int getGridWidth()
	{
		int width = 1100;

		width += getColCount() * 30;

		return width;
	}

	public void calculatePeriods(String dateBeginStr, String dateEndStr)
	{
		getGoodsCirculationYears().clear();
		if (!isByMonth() && !isByQuarter())
			return;

		Date dateBegin;
		Date dateEnd;
		try
		{
			dateBegin = StringUtil.appDateString2Date(dateBeginStr);
			dateEnd = StringUtil.appDateString2Date(dateEndStr);
			Calendar c1Begin = Calendar.getInstance();
			c1Begin.setTime(dateBegin);
			Calendar c1End = Calendar.getInstance();
			c1End.setTime(dateEnd);
			//добавляем милисекунду, для того чтобы если задан один и тот же день, в цикл попало хоть один раз
			c1End.add(Calendar.MILLISECOND, 1);
			int curYear = -1;
			while (c1Begin.before(c1End))
			{
				int year = c1Begin.get(Calendar.YEAR);
				if (year != curYear)
				{
					getGoodsCirculationYears().add(new GoodsCirculationYear(year));
					curYear = year;
				}

				GoodsCirculationYear goodsCirculationYear = getGoodsCirculationYears().get(getGoodsCirculationYears().size() - 1);
				int month = c1Begin.get(Calendar.MONTH);
				int quarter = getQuarterByMonth(month);
				if (!goodsCirculationYear.findMonth(month))
				{
					goodsCirculationYear.getGoodsCirculationYearMonths().add(new GoodsCirculationMonth(month));
				}
				GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.findQuarter(quarter);
				if (goodsCirculationQuarter == null)
				{
					GoodsCirculationQuarter newQuarter = new GoodsCirculationQuarter(quarter);
					newQuarter.getGoodsCirculationQuarterMonths().add(new GoodsCirculationMonth(month));
					goodsCirculationYear.getGoodsCirculationYearQuarters().add(newQuarter);
				}
				else
				{
					if (!goodsCirculationQuarter.findMonth(month))
					{
						goodsCirculationQuarter.getGoodsCirculationQuarterMonths().add(new GoodsCirculationMonth(month));
					}
				}

				c1Begin.add(Calendar.MONTH, 1);
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	public void cleanList()
	{
		goodsCirculationLines.clear();
		goodsCirculationOutLines.clear();
		goodsCirculationYears.clear();
	}

	public String getQuarterCount(int rowIdx, int yearIdx, int quarterIdx)
	{
		GoodsCirculationLine goodsCirculationLine = getGoodsCirculationOutLines().get(rowIdx);
		GoodsCirculationYear goodsCirculationYear = goodsCirculationLine.getGoodsCirculationYears().get(yearIdx);
		GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(quarterIdx);
		return goodsCirculationQuarter.getCountFormatted();
	}

	public String getMonthForQuarterCount(int rowIdx, int yearIdx, int quarterIdx, int monthIdx)
	{
		GoodsCirculationLine goodsCirculationLine = getGoodsCirculationOutLines().get(rowIdx);
		GoodsCirculationYear goodsCirculationYear = goodsCirculationLine.getGoodsCirculationYears().get(yearIdx);
		GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(quarterIdx);
		GoodsCirculationMonth goodsCirculationMonth = goodsCirculationQuarter.getGoodsCirculationQuarterMonths().get(monthIdx);
		return goodsCirculationMonth.getCountFormatted();
	}

	public String getMonthCount(int rowIdx, int yearIdx, int monthIdx)
	{
		GoodsCirculationLine goodsCirculationLine = getGoodsCirculationOutLines().get(rowIdx);
		GoodsCirculationYear goodsCirculationYear = goodsCirculationLine.getGoodsCirculationYears().get(yearIdx);
		GoodsCirculationMonth goodsCirculationMonth = goodsCirculationYear.getGoodsCirculationYearMonths().get(monthIdx);
		return goodsCirculationMonth.getCountFormatted();
	}

	public double getQuarterCountDouble(int rowIdx, int yearIdx, int quarterIdx)
	{
		String countFormatted = getQuarterCount(rowIdx, yearIdx, quarterIdx);
		return StringUtil.appCurrencyString2double(countFormatted);
	}

	public double getMonthForQuarterCountDouble(int rowIdx, int yearIdx, int quarterIdx, int monthIdx)
	{
		String countFormatted = getMonthForQuarterCount(rowIdx, yearIdx, quarterIdx, monthIdx);
		return StringUtil.appCurrencyString2double(countFormatted);
	}

	public double getMonthCountDouble(int rowIdx, int yearIdx, int monthIdx)
	{
		String countFormatted = getMonthCount(rowIdx, yearIdx, monthIdx);
		return StringUtil.appCurrencyString2double(countFormatted);
	}

	public GoodsCirculationLine getEmptyLine()
	{
		GoodsCirculationLine goodsCirculationLine = new GoodsCirculationLine();

		goodsCirculationLine.setPrd_id("-1");
		goodsCirculationLine.setProduce_name("");

		return goodsCirculationLine;
	}

	public List getExcelTable()
	{
		List<Object> rows = new ArrayList<Object>();

		IActionContext context = ActionContext.threadInstance();

		List<Object> header = new ArrayList<Object>();
		try
		{
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.fullName"));
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.ctn_number"));
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.stuffCategory"));
			if (isByContractor())
			{
				header.add(StrutsUtil.getMessage(context, "GoodsCirculation.contractor"));
			}
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.unit"));
			for (GoodsCirculationYear goodsCirculationYear : getGoodsCirculationYears())
			{
				if (isByQuarter())
				{
					for (GoodsCirculationQuarter goodsCirculationQuarter : goodsCirculationYear.getGoodsCirculationYearQuarters())
					{
						if (isByMonth())
						{
							for (GoodsCirculationMonth goodsCirculationMonth : goodsCirculationQuarter.getGoodsCirculationQuarterMonths())
							{
								String yearMonth = Integer.toString(goodsCirculationYear.getYear()) + " " + goodsCirculationMonth.getMonthFormatted();
								header.add(yearMonth);
							}
						}

						String yearQuarter = Integer.toString(goodsCirculationYear.getYear()) + " " + goodsCirculationQuarter.getQuarterFormatted();
						header.add(yearQuarter);
					}
				}

				if (!isByQuarter() && isByMonth())
				{
					for (GoodsCirculationMonth goodsCirculationMonth : goodsCirculationYear.getGoodsCirculationYearMonths())
					{
						String yearMonth = Integer.toString(goodsCirculationYear.getYear()) + " " + goodsCirculationMonth.getMonthFormatted();
						header.add(yearMonth);
					}
				}
			}
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.totalCount"));
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.restInMinsk"));
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.restInLithuania"));
			header.add(StrutsUtil.getMessage(context, "GoodsCirculation.ordInProducer"));
		}
		catch (Exception e)
		{
			log.error(e);
		}

		rows.add(header);

		for (int i = 0; i < getGoodsCirculationOutLines().size(); i++)
		{
			GoodsCirculationLine goodsCirculationLine = getGoodsCirculationOutLines().get(i);

			List<Object> record = new ArrayList<Object>();
			record.add(goodsCirculationLine.getFullName());
			record.add(goodsCirculationLine.getCtn_number());
			record.add(goodsCirculationLine.getStf_name());
			if (isByContractor())
			{
				record.add(goodsCirculationLine.getContractor());
			}
			record.add(goodsCirculationLine.getUnit());

			for (int y = 0; y < getGoodsCirculationYears().size(); y++)
			{
				GoodsCirculationYear goodsCirculationYear = getGoodsCirculationYears().get(y);
				if (isByQuarter())
				{
					for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearQuarters().size(); j++)
					{
						if (isByMonth())
						{
							GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(j);
							for (int k = 0; k < goodsCirculationQuarter.getGoodsCirculationQuarterMonths().size(); k++)
							{
								GoodsCirculationMonth goodsCirculationMonth = goodsCirculationQuarter.getGoodsCirculationQuarterMonths().get(k);
								record.add(getMonthForQuarterCountDouble(i, y, j, k));
							}
						}
						record.add(getQuarterCountDouble(i, y, j));
					}
				}
				if (!isByQuarter() && isByMonth())
				{
					for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearMonths().size(); j++)
					{
						record.add(getMonthCountDouble(i, y, j));
					}
				}
			}
			record.add(goodsCirculationLine.getLps_count());
			record.add(goodsCirculationLine.getRest_in_minsk());
			record.add(goodsCirculationLine.getRestInLithuania());
			record.add(goodsCirculationLine.getOrdInProducer());
			rows.add(record);
		}

		return rows;
	}
}
