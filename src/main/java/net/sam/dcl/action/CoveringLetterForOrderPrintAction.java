package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.CoveringLetterForOrderPrintForm;
import net.sam.dcl.form.OrderForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.report.pdf.CoveringLetterForOrderPDF;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.dao.ContractDAO;
import net.sam.dcl.dao.SpecificationDAO;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.dao.OrderProduceDAO;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CoveringLetterForOrderPrintAction extends DBTransactionAction
{
	LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

	public ActionForward execute(IActionContext context) throws Exception
	{
		ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		CoveringLetterForOrderPrintForm form = (CoveringLetterForOrderPrintForm) context.getForm();
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);

		setData(context, form, order);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CoveringLetterForOrderPDF pdfPage = new CoveringLetterForOrderPDF(out, form, words);
		pdfPage.setScale(form.getPrintScale());
		pdfPage.process();

		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition", " attachment;filename=\"CoveringLetterForOrder.pdf\"");

		context.getResponse().getOutputStream().write(out.toByteArray());
		out.close();
		return null;
	}

	private void setData(IActionContext context, CoveringLetterForOrderPrintForm form, Order order) throws Exception
	{
		form.setOrder(order);
		for (int i = 0; i < form.getOrder().getProduces().size() - form.getOrder().getCountItog(); i++)
		{
			OrderProduce produce = form.getOrder().getProduces().get(i);
			if (!produce.isDependedPosition())
				OrderProduceDAO.loadCirculationAndRest(context, produce);
		}

		boolean hasEmptySpec = false;

		List<OrderProduce> mergedLikeOrderProduces = order.getMergedProduces(!StringUtil.isEmpty(order.getMerge_positions()), true, false);
		List<OrderForm.OrderProduceForForm> ordMergedLikeOrderProduces = OrderForm.processProduces(mergedLikeOrderProduces);

		//всегда мержим позиции.
		List<OrderProduce> mergedForRestProduces = order.getMergedProduces(true, false, false);
		form.setProduces(OrderForm.processProduces(mergedForRestProduces));
		form.setTotalLinesCount(order.getCountItog());

		//Переписываем ViewNumber для конечной таблици позиций.
		for (int i = 0; i < form.getProduces().size() - form.getTotalLinesCount(); i++)
		{
			OrderForm.OrderProduceForForm produce = (OrderForm.OrderProduceForForm) form.getProduces().get(i);
			String viewNumber = "";
			for (OrderForm.OrderProduceForForm produceInOrder : ordMergedLikeOrderProduces)
			{
				if (
								null != produceInOrder.getProduce() &&
												null != produceInOrder.getProduce().getId() &&
												null != produce.getProduce().getId() &&
												produceInOrder.getProduce().getId().equals(produce.getProduce().getId())
								)
				{
					viewNumber += produceInOrder.getViewNumberFormatted() + ", ";
				}
			}
			if (!StringUtil.isEmpty(viewNumber))
			{
				viewNumber = viewNumber.substring(0, viewNumber.length() - 2);
			}
			produce.setViewNumber(viewNumber);
		}

		form.setDate(order.getOrd_date());
		if ("1".equals(order.getOrd_in_one_spec()))
		{
			CoveringLetterForOrderPrintForm.Page page = new CoveringLetterForOrderPrintForm.Page();
			page.setPositions(words.getMessage("rep.CoveringLetterForOrder.all"));
			setPageData(context, page, order, order.getContract());
			setPageData(context, page, order.getContractor_for(), order.getSpecification());
			form.addPage(page);
		}
		else
		{
			//Отдельный мерж с учетом спецификации.
			List<OrderProduce> mergedForSpcProduces = order.getMergedProduces(true, true, true);
			List<OrderForm.OrderProduceForForm> ordMergedForSpcProduces = OrderForm.processProduces(mergedForSpcProduces);

			List<SpecificationKey> list = new ArrayList<SpecificationKey>();
			List<CoveringLetterForOrderPrintForm.ContractorNumbers> listContractorNumbers = new ArrayList<CoveringLetterForOrderPrintForm.ContractorNumbers>();

			for (int i = 0; i < ordMergedForSpcProduces.size() - form.getTotalLinesCount(); i++)
			{
				OrderForm.OrderProduceForForm orderProduce = ordMergedForSpcProduces.get(i);
				SpecificationKey key = new SpecificationKey(orderProduce.getSpecification(), orderProduce.getContract(), orderProduce.getContractor());
				int idx = list.indexOf(key);
				if (idx == -1)
				{
					list.add(key);
				}
				else
				{
					key = list.get(idx);
				}
				key.addProduce(orderProduce);

				if (StringUtil.isEmpty(orderProduce.getSpecification().getSpc_id()))
				{
					CoveringLetterForOrderPrintForm.ContractorNumbers contractorNumbers = new CoveringLetterForOrderPrintForm.ContractorNumbers(orderProduce.getContractor().getName());
					idx = listContractorNumbers.indexOf(contractorNumbers);
					if (idx == -1)
					{
						listContractorNumbers.add(contractorNumbers);
					}
					else
					{
						contractorNumbers = listContractorNumbers.get(idx);
					}

					contractorNumbers.setNumbersAndCounts(
									contractorNumbers.getNumbersAndCounts() +
													(StringUtil.isEmpty(order.getMerge_positions())
																	? orderProduce.getViewNumberFormatted()
																	: getPositionMerged(ordMergedLikeOrderProduces, orderProduce.getOrderProduce())) +
													" (" +
													orderProduce.getOpr_count_formatted() +
													" " +
													orderProduce.getProduce().getUnit().getName() +
													")" +
													", "
					);
				}
			}
			form.setContractorNumbers(listContractorNumbers);

			for (SpecificationKey specificationKey : list)
			{
				CoveringLetterForOrderPrintForm.Page page = new CoveringLetterForOrderPrintForm.Page();
				setPageData(context, page, order, specificationKey.contract);
				setPageData(context, page, specificationKey.contractor, specificationKey.specification);

				StringBuilder positions = new StringBuilder();
				for (OrderForm.OrderProduceForForm orderProduce : specificationKey.produces)
				{
					String position = String.format("%s (%s %s)",
									StringUtil.isEmpty(order.getMerge_positions()) ? orderProduce.getViewNumberFormatted() : getPositionMerged(ordMergedLikeOrderProduces, orderProduce.getOrderProduce()),
									orderProduce.getOpr_count_formatted(),
									orderProduce.getUnitNameForLanguage("RU")) + ", ";
					positions.append(position);
					/* Пока закомментарим. По идее позиции должны быть уникальны и нужно выводить все. Оставим, если будет странный кейс.
					String positionDep = "/" + position;
					int indMain = positions.indexOf(position);
					int indDep = positions.indexOf(positionDep);
					if (indMain == -1 || (indMain > 0 && indDep > 0 && indMain == indDep + 1))
					{
						positions.append(position);
					}
					*/
				}
				if (positions.length() > 1)
				{
					positions.setLength(positions.length() - 2);
				}
				page.setPositions(positions.toString());
				if (StringUtil.isEmpty(specificationKey.specification.getSpc_id()))
				{
					hasEmptySpec = true;
				}
				else
				{
					form.addPage(page);
				}
			}
		}

		form.setNumber(order.getOrd_number());
		form.setOrd_by_guaranty(order.getOrd_by_guaranty());
		form.setHasEmptySpec(hasEmptySpec);
		form.setManager(order.getManager());

		form.setPrintScale(StringUtil.appCurrencyString2double(order.getOrd_letter_scale()).floatValue());
	}

	//Ищем номер позиции в заказе
	private String getPositionMerged(List<OrderForm.OrderProduceForForm> mergedLikeOrderProduces, OrderProduce produce)
	{
		int i = 0;
		for (OrderForm.OrderProduceForForm produceInOrder : mergedLikeOrderProduces)
		{
			if (produce.getProduce().getId().equals(produceInOrder.getProduce().getId()) &&
							produce.getOpr_price_netto() == produceInOrder.getOpr_price_netto())
			{
				return produceInOrder.getViewNumberFormatted();
			}
			i++;
		}

		return "";
	}

	private void setPageData(IActionContext context, CoveringLetterForOrderPrintForm.Page page, Order order, Contract contract) throws Exception
	{
		if (null != contract && !StringUtil.isEmpty(contract.getCon_id()))
		{
			Contract localContract = ContractDAO.load(context, contract.getCon_id(), false);
			page.setSeller(localContract.getSeller().getName());
		}
	}

	private void setPageData(IActionContext context, CoveringLetterForOrderPrintForm.Page page, Contractor contractor, Specification specification) throws Exception
	{
		if (!StringUtil.isEmpty(contractor.getId()))
		{
			ContractorDAO.load(context, contractor);
			page.setPurchaserName(contractor.getFullname());
		}
		if (!StringUtil.isEmpty(specification.getSpc_id()))
		{
			SpecificationDAO.load(context, specification);
			Contract contract = ContractDAO.load(context, specification.getCon_id(), false);
			page.setContractNumber(contract.getCon_number());
			page.setContractDate(contract.getCon_date_formatted());
			page.setConOriginal(contract.getCon_original());
			page.setSpecificationNumber(specification.getSpc_number());
			page.setSpecificationDate(specification.getSpc_date_formatted());
			page.setSpcOriginal(specification.getSpc_original());
			page.setPayCondition(specification.getSpc_add_pay_cond());
			page.setCurrency(contract.getCurrency());
			page.setSumm(specification.getSpc_summ());
			SpecificationDAO.loadNotClosedPayments(context, specification);
			page.setPayments(specification.getNotClosedPayments());
			specification.calculatePredPayItog();
			page.setRate(specification.calculatePredPayRate());
		}
	}

	static public class SpecificationKey
	{
		Specification specification;
		Contract contract;
		Contractor contractor;
		List<OrderForm.OrderProduceForForm> produces = new ArrayList<OrderForm.OrderProduceForForm>();

		public SpecificationKey(Specification specification, Contract contract, Contractor contractor)
		{
			this.specification = specification;
			this.contract = contract;
			this.contractor = contractor;
		}

		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			SpecificationKey that = (SpecificationKey) o;

			return !(specification.getSpc_id() != null ? !specification.getSpc_id().equals(that.specification.getSpc_id()) : that.specification.getSpc_id() != null);
		}

		public int hashCode()
		{
			int result;
			result = (specification.getSpc_id() != null ? specification.getSpc_id().hashCode() : 0);
			return result;
		}


		public void addProduce(OrderForm.OrderProduceForForm produce)
		{
			produces.add(produce);
		}
	}
}
