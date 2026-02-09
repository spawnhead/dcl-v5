package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.form.GoodsCirculationForm;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class GoodsCirculationAction extends DBAction implements IDispatchable, IFormAutoSave
{
	public ActionForward filter(IActionContext context) throws Exception
	{
		GoodsCirculationForm form = (GoodsCirculationForm) context.getForm();
		//Устанавливаем дефолтную ширину, чтобы при старте не сжималось.
		form.setGridWith(1000);

		if (StringUtil.isEmpty(form.getDate_begin()))
		{
			return context.getMapping().getInputForward();
		}

		boolean incorrectDates = false;
		if (!StringUtil.isEmpty(form.getDate_end()))
		{
			Date dateBegin = StringUtil.getCurrentDateTime();
			Date dateEnd = StringUtil.getCurrentDateTime();
			try
			{
				dateBegin = StringUtil.appDateString2Date(form.getDate_begin());
				dateEnd = StringUtil.appDateString2Date(form.getDate_end());
			}
			catch (Exception e)
			{
				log.error(e);
			}
			if (dateEnd.before(dateBegin))
			{
				StrutsUtil.addError(context, "error.goods_rest.incorrect_dates", null);
				incorrectDates = true;
			}
		}

		if (form.isCanForm() && !incorrectDates)
		{
			GoodsCirculation goodsCirculation = (GoodsCirculation) StoreUtil.getSession(context.getRequest(), GoodsCirculation.class);
			goodsCirculation.setDate_begin(form.getDate_begin_date());
			goodsCirculation.setDate_end(form.getDate_end_date());
			goodsCirculation.setProduceName(form.getProduceName());
			goodsCirculation.setStf_id(form.getStuffCategory().getId());
			goodsCirculation.setSln_id(form.getSeller().getId());
			goodsCirculation.setCtr_id(form.getContractorGoodsCirculation().getId());
			List<GoodsCirculationLine> lst = DAOUtils.fillList(context, "select-goods_circulation", goodsCirculation, GoodsCirculationLine.class, null, null);
			if (!StringUtil.isEmpty(form.getIncludeWithNoCirculationAndWithRest()))
			{
				List<GoodsCirculationLine> lstOrder = DAOUtils.fillList(context, "select-goods_circulation_rest", goodsCirculation, GoodsCirculationLine.class, null, null);

				//Удалямем строки, которые уже выбраны в select-goods_circulation.
				for ( GoodsCirculationLine circulationLine : lst )
				{
					GoodsCirculationLine foundLine = null;
					do
					{
						if (foundLine != null)
							lstOrder.remove(foundLine);

						foundLine = null;
						for (GoodsCirculationLine circulationLineOrder : lstOrder)
						{
							if (circulationLine.getPrd_id().equals(circulationLineOrder.getPrd_id()))
							{
								foundLine = circulationLineOrder;
								break;
							}
						}

					} while (foundLine != null);
				}

				//Не добавляем строки с нулевыми остатками
				for ( GoodsCirculationLine circulationLine : lstOrder )
				{
					if (circulationLine.getRestInLithuania() != 0 || circulationLine.getRest_in_minsk() != 0 || circulationLine.getOrdInProducer() != 0)
						lst.add(circulationLine);
				}
			}
			goodsCirculation.setGoodsCirculationLines(lst);

			goodsCirculation.setBy_quarter(form.getBy_quarter());
			goodsCirculation.setBy_month(form.getBy_month());
			goodsCirculation.setBy_contractor(form.getBy_contractor());
			goodsCirculation.calculatePeriods(form.getDate_begin(), form.getDate_end());
			goodsCirculation.calculate();
			StoreUtil.putSession(context.getRequest(), goodsCirculation);
			form.getGrid().setDataList(goodsCirculation.getGoodsCirculationOutLines());
			form.setGridWith(goodsCirculation.getGridWidth());
		}

		return show(context);
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		final GoodsCirculationForm form = (GoodsCirculationForm) context.getForm();

		context.getRequest().setAttribute("show-count-sum", new IShowChecker()
		{
			int size = form.getGrid().getDataList().size();

			public boolean check(ShowCheckerContext context)
			{
				return context.getTable().getRecordCounter() < size - 1;
			}
		}
		);

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				GoodsCirculationLine goodsCirculationLine = (GoodsCirculationLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (goodsCirculationLine.isItogLine())
				{
					return "bold-cell";
				}
				return "";
			}
		});

		GoodsCirculation goodsCirculation = (GoodsCirculation) StoreUtil.getSession(context.getRequest(), GoodsCirculation.class);
		if (null != goodsCirculation)
			goodsCirculation.setCurrentLine(0);
		return context.getMapping().getInputForward();
	}

	public ActionForward generate(IActionContext context) throws Exception
	{
		GoodsCirculationForm form = (GoodsCirculationForm) context.getForm();
		form.setCanForm(true);

		return filter(context);
	}

	public ActionForward generateExcel(IActionContext context) throws Exception
	{
		GoodsCirculation goodsCirculation = (GoodsCirculation) StoreUtil.getSession(context.getRequest(), GoodsCirculation.class);
		Grid2Excel grid2Excel = new Grid2Excel("GoodsCirculation Report");
		grid2Excel.doGrid2Excel(context, goodsCirculation.getExcelTable());
		return null;
	}

	public ActionForward cleanAll(IActionContext context) throws Exception
	{
		GoodsCirculationForm form = (GoodsCirculationForm) context.getForm();
		form.setDate_begin("");
		form.setDate_end("");
		form.setCanForm(false);
		form.setProduceName("");
		form.setStuffCategory(new StuffCategory());
		form.setSeller(new Seller());
		form.setContractorGoodsCirculation(new Contractor());
		GoodsCirculation goodsCirculation = (GoodsCirculation) StoreUtil.getSession(context.getRequest(), GoodsCirculation.class);
		goodsCirculation.cleanList();
		form.getGrid().setDataList(goodsCirculation.getGoodsCirculationOutLines());
		form.setGridWith(goodsCirculation.getGridWidth());

		return filter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		GoodsCirculation goodsCirculation = new GoodsCirculation();
		StoreUtil.putSession(context.getRequest(), goodsCirculation);

		return cleanAll(context);
	}

	public ActionForward fromProduceMovement(IActionContext context) throws Exception
 {
   return show(context);
 }

}
