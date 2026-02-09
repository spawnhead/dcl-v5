package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.OrderPrintForm;
import net.sam.dcl.form.OrderForm;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.report.pdf.OrderPDF;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.dao.SpecificationDAO;
import net.sam.dcl.dao.ContractDAO;
import org.apache.struts.action.ActionForward;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrderPrintAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
		ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
		OrderPrintForm form = (OrderPrintForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale(order.getBlank().getLanguage().getLetterId()));
    setData(context, form, order, words);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
    OrderPDF pdfPage = new OrderPDF(out, order.getBlank(), form, words);
    pdfPage.setScale(form.getPrintScale());
    pdfPage.process();

		context.getResponse().setContentType("application/download");
		context.getResponse().setHeader("Content-disposition"," attachment;filename=\"Order.pdf\"");

		context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
    return null;
  }

  private void setData(IActionContext context, OrderPrintForm form, Order order, LocaledPropertyMessageResources words) throws Exception
  {
    form.setContractor(order.getContractor());
    form.setContact_person(order.getContact_person());
    form.setConcering(order.getOrd_concerning());
    form.setNumber(order.getOrd_number());
    boolean withS = false;
    if ( !StringUtil.isEmpty(order.getOrd_in_one_spec()) )
    {
      if (StringUtil.isEmpty(order.getSpecification().getSpc_id()))
      {
        withS = true;
      }
      else
      {
	      Contract contract = ContractDAO.load(context, order.getSpecification().getCon_id(), false);
	      Specification specification = SpecificationDAO.load(context, order.getSpecification().getSpc_id());
        if (!contract.isOriginal() || specification.isOriginal())
        {
          withS = true;
        }
      }
    }
    else
    {
      for (int i = 0; i < order.getProduces().size() - order.getCountItog(); i++)
      {
        OrderProduce orderProduce = order.getProduces().get(i);
        if (StringUtil.isEmpty(orderProduce.getContract().getCon_id()) || StringUtil.isEmpty(orderProduce.getSpecification().getSpc_id()))
        {
          withS = true;
          break;
        }
      }
    }

    if (!withS)
    {
      for (int i = 0; i < order.getProduces().size() - order.getCountItog(); i++)
      {
	      OrderProduce orderProduce = order.getProduces().get(i);

	      Contract contract = new Contract();
	      if (!StringUtil.isEmpty(orderProduce.getContract().getCon_id()))
		      contract = ContractDAO.load(context, orderProduce.getContract().getCon_id(), false);

	      Specification specification = new Specification();
	      if (!StringUtil.isEmpty(orderProduce.getSpecification().getSpc_id()))
		      specification = SpecificationDAO.load(context, orderProduce.getSpecification().getSpc_id());

        if (
             ( !StringUtil.isEmpty(contract.getCon_id()) && !contract.isOriginal() )
                ||
             ( !StringUtil.isEmpty(specification.getSpc_id()) && !specification.isOriginal() )
           )
        {
          withS = true;
          break;
        }
      }
    }

    if (withS)
    {
      form.setNumber(form.getNumber() + " S");
    }

    form.setDate(StringUtil.appDateString2format(order.getOrd_date(), "dd.MM.yyyy"));
    form.setPreamble(order.getOrd_preamble());
    form.setPreamble2(order.getBlank().getBln_preamble());
    form.setPrintDelivery(IncoTerm.CIP.equalsIgnoreCase(order.getDeliveryCondition().getName()) ||
                          IncoTerm.CPT.equalsIgnoreCase(order.getDeliveryCondition().getName()) ||
                          IncoTerm.CIP_2010.equalsIgnoreCase(order.getDeliveryCondition().getName()) ||
                          IncoTerm.CPT_2010.equalsIgnoreCase(order.getDeliveryCondition().getName()));
    form.setDeliveryCostBy(order.getOrd_delivery_cost_by());
    form.setDeliveryCost(StringUtil.double2appCurrencyString(order.getOrd_delivery_cost()));

	  List<OrderProduce> mergedProducesCopy = order.getMergedProduces(!StringUtil.isEmpty(order.getMerge_positions()), true, false);
    order.calculate(mergedProducesCopy, words);
    form.setProduces(OrderForm.processProduces(mergedProducesCopy));

    form.setDeliveryCondition(order.getDeliveryCondition());
    form.setDeliveryAddress(order.getOrd_addr());
    form.setPayCondition(order.getOrd_pay_condition());
    form.setDeliveryTerm(order.getOrd_delivery_term());
    form.setAdditionalInfo(order.getOrd_add_info());
    form.setAdditionalInfo2(order.getBlank().getBln_note());
    form.setDirector(order.getDirector());
    form.setLogist(order.getLogist());
    form.setDirectorRB(order.getDirector_rb());
    form.setChiefDep(order.getChief_dep());
    form.setManager(order.getManager());
    form.setImportFlag(order.getOrd_donot_calculate_netto());
    form.setCurrency(order.getCurrency());
    form.setCountTotal(order.getOrd_count_itog_flag());
    form.setTotalLinesCount(order.getCountItog());
    form.setShow_unit(order.getShow_unit());

    form.setPrintScale(StringUtil.appCurrencyString2double(order.getOrd_print_scale()).floatValue());
    form.setLogistSignature(order.getOrd_logist_signature());
    form.setDirectorRBSignature(order.getOrd_director_rb_signature());
    form.setChiefDepSignature(order.getOrd_chief_dep_signature());
    form.setManagerSignature(order.getOrd_manager_signature());
  }
}
