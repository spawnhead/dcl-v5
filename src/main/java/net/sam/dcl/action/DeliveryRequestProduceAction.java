package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.form.DeliveryRequestProduceForm;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.config.Config;
import net.sam.dcl.dao.*;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryRequestProduceAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentProduceToBean(IActionContext context, DeliveryRequestProduce deliveryRequestProduce)
  {
    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();

    try
    {
      deliveryRequestProduce.setDrp_id(form.getDrp_id());
      deliveryRequestProduce.setDlr_id(form.getDlr_id());
      deliveryRequestProduce.setOpr_id(form.getOpr_id());
      deliveryRequestProduce.setAsm_id(form.getAsm_id());
      deliveryRequestProduce.setApr_id(form.getApr_id());
      deliveryRequestProduce.setOrd_date(StringUtil.appDateString2dbDateString(form.getOrd_date()));
      deliveryRequestProduce.setOrd_number(form.getOrd_number());
      deliveryRequestProduce.setOrdInfoAssemble(form.getOrdInfoAssemble());
      deliveryRequestProduce.setCustomerAssemble(form.getCustomerAssemble());
      deliveryRequestProduce.setSip_id(form.getSip_id());
      deliveryRequestProduce.setSpi_date(form.getSpi_date());
      deliveryRequestProduce.setSpi_number(form.getSpi_number());
      deliveryRequestProduce.setNumber(form.getNumber());
      if (null != form.getProduce().getId())
      {
        deliveryRequestProduce.setProduce(ProduceDAO.loadProduceWithUnit(form.getProduce().getId()));
      }
      if ( StringUtil.isEmpty(form.getDrp_price()) )
      {
        deliveryRequestProduce.setDrp_price(0.0);
      }
      else
      {
        deliveryRequestProduce.setDrp_price(StringUtil.appCurrencyString2double(form.getDrp_price()));
      }
      if ( StringUtil.isEmpty(form.getDrp_count()) )
      {
        deliveryRequestProduce.setDrp_count(0.0);
      }
      else
      {
        deliveryRequestProduce.setDrp_count(StringUtil.appCurrencyString2double(form.getDrp_count()));
      }
      if (!StringUtil.isEmpty(form.getStuffCategory().getId()))
      {
        deliveryRequestProduce.setStuffCategory(StuffCategoryDAO.load(context, form.getStuffCategory().getId()));
      }
      if (!StringUtil.isEmpty(form.getCustomer().getId()))
      {
        deliveryRequestProduce.setCustomer(ContractorDAO.load(context, form.getCustomer().getId()));
      }
      if (!StringUtil.isEmpty(form.getSpecification().getSpc_id()))
      {
        deliveryRequestProduce.setSpecification(SpecificationDAO.load(context, form.getSpecification().getSpc_id()));
        deliveryRequestProduce.getSpecification().setSpc_date(StringUtil.appDateString2dbDateString(deliveryRequestProduce.getSpecification().getSpc_date()));
        deliveryRequestProduce.setContract(ContractDAO.load(context, deliveryRequestProduce.getSpecification().getCon_id(), false));
      }
      if (!StringUtil.isEmpty(form.getPurpose().getId()))
      {
        deliveryRequestProduce.setPurpose(PurposeDAO.load(context, form.getPurpose().getId()));
      }
      deliveryRequestProduce.setDrp_purpose(form.getDrp_purpose());
      deliveryRequestProduce.setDlr_fair_trade(form.getDlr_fair_trade());
      deliveryRequestProduce.setDlr_need_deliver(form.getDlr_need_deliver());
      deliveryRequestProduce.setDlr_include_in_spec(form.getDlr_include_in_spec());
      deliveryRequestProduce.setDrp_occupied(form.getDrp_occupied());
      if (!StringUtil.isEmpty(form.getReceiveManager().getUsr_id()))
      {
        deliveryRequestProduce.setReceiveManager(UserDAO.load(context, form.getReceiveManager().getUsr_id()));
      }
      deliveryRequestProduce.setReceive_date_formatted(form.getReceive_date());
      deliveryRequestProduce.setDrp_max_extra(form.getDrp_max_extra());
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    StoreUtil.putSession(context.getRequest(), deliveryRequestProduce);
  }

  private void saveBeanToCurrentProduce(IActionContext context, DeliveryRequestProduce deliveryRequestProduce)
  {
    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();

    form.setDrp_id(deliveryRequestProduce.getDrp_id());
    form.setDlr_id(deliveryRequestProduce.getDlr_id());
    form.setOpr_id(deliveryRequestProduce.getOpr_id());
    form.setAsm_id(deliveryRequestProduce.getAsm_id());
    form.setApr_id(deliveryRequestProduce.getApr_id());
    form.setOrd_date(deliveryRequestProduce.getOrd_date());
    form.setOrd_number(deliveryRequestProduce.getOrd_number());
    form.setOrdInfoAssemble(deliveryRequestProduce.getOrdInfoAssemble());
    form.setCustomerAssemble(deliveryRequestProduce.getCustomerAssemble());
    form.setSip_id(deliveryRequestProduce.getSip_id());
    form.setSpi_date(deliveryRequestProduce.getSpi_date());
    form.setSpi_number(deliveryRequestProduce.getSpi_number());
    form.setNumber(deliveryRequestProduce.getNumber());
    form.setProduce(deliveryRequestProduce.getProduce());
    if ( deliveryRequestProduce.getDrp_price() == 0 )
    {
      form.setDrp_price("");
    }
    else
    {
      form.setDrp_price(StringUtil.double2appCurrencyString(deliveryRequestProduce.getDrp_price()));
    }
    if ( deliveryRequestProduce.getDrp_count() == 0 )
    {
      form.setDrp_count("");
    }
    else
    {
      form.setDrp_count(StringUtil.double2appCurrencyString(deliveryRequestProduce.getDrp_count()));
    }
    form.setStuffCategory(deliveryRequestProduce.getStuffCategory());
    form.setCustomer(deliveryRequestProduce.getCustomer());
    form.setContract(deliveryRequestProduce.getContract());
    form.setSpecification(deliveryRequestProduce.getSpecification());
    form.setPurpose(deliveryRequestProduce.getPurpose());
    form.setDrp_purpose(deliveryRequestProduce.getDrp_purpose());
    form.setDlr_fair_trade(deliveryRequestProduce.getDlr_fair_trade());
    form.setDlr_need_deliver(deliveryRequestProduce.getDlr_need_deliver());
    form.setDlr_include_in_spec(deliveryRequestProduce.getDlr_include_in_spec());
    form.setDrp_occupied(deliveryRequestProduce.getDrp_occupied());
    form.setReceiveManager(deliveryRequestProduce.getReceiveManager());
    form.setReceive_date(deliveryRequestProduce.getReceive_date_formatted());
    form.setDrp_max_extra(deliveryRequestProduce.getDrp_max_extra());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();
    DeliveryRequestProduce deliveryRequestProduce = (DeliveryRequestProduce) StoreUtil.getSession(context.getRequest(), DeliveryRequestProduce.class);
    if (!StringUtil.isEmpty(deliveryRequestProduce.getOpr_id()))
    {
      Order order = OrderDAO.loadByOprId(context, deliveryRequestProduce.getOpr_id());
      CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, order.getCurrency().getId(), StringUtil.appDateString2dbDateString(order.getOrd_date()));
      form.setCourse(StringUtil.double2appCurrencyString(currencyRate.getCrt_rate()));

      OrderProduce orderProduce = OrderProduceDAO.load(context, deliveryRequestProduce.getOpr_id());
      form.setOpr_price_netto(orderProduce.getOpr_price_netto_formatted());
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward insert(IActionContext context) throws Exception
  {
    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();
    DeliveryRequestProduce deliveryRequestProduce = new DeliveryRequestProduce();
    deliveryRequestProduce.setDlr_fair_trade(form.getDlr_fair_trade());
    deliveryRequestProduce.setDlr_include_in_spec(form.getDlr_include_in_spec());
    deliveryRequestProduce.setDrp_occupied("");
    StoreUtil.putSession(context.getRequest(), deliveryRequestProduce);
    //обнуляем поля формы
    saveBeanToCurrentProduce(context, deliveryRequestProduce);


    String defaultLoadId;
    if ( !"1".equals(form.getDlr_fair_trade()) )
    {
      if ( !StringUtil.isEmpty(form.getDlr_guarantee_repair()) )
      {
        defaultLoadId = Config.getString(Constants.defaultPurposeGuaranteeRepair);
      }
      else
      {
        defaultLoadId = Config.getString(Constants.defaultPurposeProduce);
      }
    }
    else
    {
      defaultLoadId = Config.getString(Constants.defaultPurposeFairTrade);
    }

    Purpose purpose = new Purpose();
    purpose.setId(defaultLoadId);
    PurposeDAO.load(context, purpose);
    form.setPurpose(purpose);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);

    DeliveryRequestProduce deliveryRequestProduce = deliveryRequest.findProduce(form.getNumber());
    deliveryRequestProduce.setDlr_fair_trade(form.getDlr_fair_trade());
    deliveryRequestProduce.setDlr_need_deliver(form.getDlr_need_deliver());
    deliveryRequestProduce.setDlr_include_in_spec(form.getDlr_include_in_spec());
    StoreUtil.putSession(context.getRequest(), deliveryRequestProduce);
    saveBeanToCurrentProduce(context, deliveryRequestProduce);

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward selectProduce(IActionContext context) throws Exception
  {
    DeliveryRequestProduce deliveryRequestProduce = (DeliveryRequestProduce) StoreUtil.getSession(context.getRequest(), DeliveryRequestProduce.class);
    saveCurrentProduceToBean(context, deliveryRequestProduce);
    return context.getMapping().findForward("selectProduce");
  }

  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception
  {
    DeliveryRequestProduce deliveryRequestProduce = (DeliveryRequestProduce) StoreUtil.getSession(context.getRequest(), DeliveryRequestProduce.class);
    saveBeanToCurrentProduce(context, deliveryRequestProduce);

    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();
		String nomenclature_id = SelectFromGridAction.getSelectedId(context);

    if ( !StringUtil.isEmpty(nomenclature_id) )
    {
      deliveryRequestProduce.setOpr_id(null); //выбираем товар, значит он больше не связан с любым другим документом (если был)
      deliveryRequestProduce.setAsm_id(null);
      deliveryRequestProduce.setApr_id(null);
      deliveryRequestProduce.setSip_id(null);
      form.setProduce(ProduceDAO.loadProduceWithUnit(new Integer(nomenclature_id)));
    }

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    DeliveryRequestProduceForm form = (DeliveryRequestProduceForm) context.getForm();
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    DeliveryRequestProduce deliveryRequestProduce = DeliveryRequest.getEmptyProduce();
    saveCurrentProduceToBean(context, deliveryRequestProduce);

    if (
         (
           (
             StringUtil.isEmpty(deliveryRequestProduce.getDlr_fair_trade()) &&
             StringUtil.isEmpty(form.getDlr_guarantee_repair())
           )
             ||
           !StringUtil.isEmpty(deliveryRequestProduce.getDlr_include_in_spec())
         )
           &&
         (
           Double.isNaN(deliveryRequestProduce.getDrp_price()) ||
           deliveryRequestProduce.getDrp_price() == 0.0 
         )
       )
    {
      StrutsUtil.addError(context, "errors.delivery_request.wrong.price", null);
      return input(context);
    }

    if (StringUtil.isEmpty(form.getNumber()))
    {
      deliveryRequest.insertProduce(deliveryRequestProduce);
    }
    else
    {
      deliveryRequest.updateProduce(form.getNumber(), deliveryRequestProduce);
    }
    return context.getMapping().findForward("back");
  }

}
