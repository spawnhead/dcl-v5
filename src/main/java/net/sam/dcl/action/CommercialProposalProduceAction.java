package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.CommercialProposalProduceForm;
import net.sam.dcl.beans.CommercialProposal;
import net.sam.dcl.beans.CommercialProposalProduce;
import net.sam.dcl.beans.CustomCode;
import net.sam.dcl.beans.Currency;
import net.sam.dcl.dao.CustomCodeDAO;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dao.StuffCategoryDAO;
import net.sam.dcl.dao.CurrencyDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CommercialProposalProduceAction extends DBTransactionAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context, CommercialProposalProduce commercialProposalProduce)
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();

    commercialProposalProduce.setNumber(form.getNumber());
    commercialProposalProduce.setLpr_id(form.getLpr_id());
    commercialProposalProduce.setLpr_produce_name(form.getLpr_produce_name());
    commercialProposalProduce.setLpr_catalog_num(form.getLpr_catalog_num());
    commercialProposalProduce.setLpr_price_brutto(StringUtil.appCurrencyString2double(form.getLpr_price_brutto()));
    commercialProposalProduce.setLpr_discount(StringUtil.appCurrencyString2double(form.getLpr_discount()));
    commercialProposalProduce.setLpr_price_netto(StringUtil.appCurrencyString2double(form.getLpr_price_netto()));
    commercialProposalProduce.setLpr_count(StringUtil.appCurrencyString2double(form.getLpr_count()));
    commercialProposalProduce.setCustomCode(form.getCustom_code());
    commercialProposalProduce.setLpr_coeficient(StringUtil.appCurrencyString2double(form.getLpr_coeficient()));
    commercialProposalProduce.setLpr_sale_price(StringUtil.appCurrencyString2double(form.getLpr_sale_price()));
    try
    {
      if (null != form.getProduce().getId())
      {
        commercialProposalProduce.setProduce(ProduceDAO.loadProduceFull(form.getProduce().getId()));
        commercialProposalProduce.setLpr_produce_name("");
        commercialProposalProduce.setLpr_catalog_num("");
      }
      if (!StringUtil.isEmpty(form.getStuffCategory().getId()))
      {
        commercialProposalProduce.setStuffCategory(StuffCategoryDAO.load(context, form.getStuffCategory().getId()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    commercialProposalProduce.setLpr_comment(form.getLpr_comment());

    StoreUtil.putSession(context.getRequest(), commercialProposalProduce);
  }

  private void getCurrentFormFromBean(IActionContext context, CommercialProposalProduce commercialProposalProduce)
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();

    form.setNumber(commercialProposalProduce.getNumber());
    form.setLpr_id(commercialProposalProduce.getLpr_id());
    form.setLpr_produce_name(commercialProposalProduce.getProduce_name());
    form.setLpr_catalog_num(commercialProposalProduce.getCatalog_num());
    form.setLpr_price_brutto(StringUtil.double2appCurrencyString(commercialProposalProduce.getLpr_price_brutto()));
    form.setLpr_discount(StringUtil.double2appCurrencyString(commercialProposalProduce.getLpr_discount()));
    form.setLpr_price_netto(StringUtil.double2appCurrencyString(commercialProposalProduce.getLpr_price_netto()));
    form.setLpr_count(StringUtil.double2appCurrencyString(commercialProposalProduce.getLpr_count()));
    form.setCustom_code(commercialProposalProduce.getCustomCode());
    form.setLpr_coeficient(commercialProposalProduce.getLpr_coeficient_formatted());
    form.setLpr_sale_price(StringUtil.double2appCurrencyString(commercialProposalProduce.getLpr_sale_price()));
    form.setProduce(commercialProposalProduce.getProduce());
    form.setLpr_comment(commercialProposalProduce.getLpr_comment());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();
    CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
    form.setCpr_date(commercialProposal.getCpr_date());
    form.setCpr_old_version(commercialProposal.getCpr_old_version());

    if ( form.isReverseCalc() )
    {
      Currency currency = CurrencyDAO.load(context, form.getCur_id());
      form.setCur_name(currency.getName());
    }
    else
    {
      form.setLpr_sale_price("");
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward insert(IActionContext context) throws Exception
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();

    CommercialProposalProduce commercialProposalProduce = new CommercialProposalProduce();
    StoreUtil.putSession(context.getRequest(), commercialProposalProduce);
    //обнуляем поля формы
    getCurrentFormFromBean(context, commercialProposalProduce);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();
    CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);

    CommercialProposalProduce commercialProposalProduce = commercialProposal.findProduce(form.getNumber());
    StoreUtil.putSession(context.getRequest(), commercialProposalProduce);
    getCurrentFormFromBean(context, commercialProposalProduce);
    form.setStuffCategory(commercialProposalProduce.getStuffCategory());
    form.setNumberBefore("");

    return input(context);
  }

  public ActionForward clone(IActionContext context) throws Exception
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();
    CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);

    CommercialProposalProduce commercialProposalProduce = new CommercialProposalProduce(commercialProposal.findProduce(form.getNumber()));
    if ( !form.isCalculateNetto() )
    {
      commercialProposalProduce.setLpr_price_brutto(0.0);
      commercialProposalProduce.setLpr_discount(0.0);
    }
    else
    {
      commercialProposalProduce.setLpr_price_netto(0.0);
    }
    //Признак нового товара - до того как заполним поля формы.
    StoreUtil.putSession(context.getRequest(), commercialProposalProduce);
    getCurrentFormFromBean(context, commercialProposalProduce);
    form.setNumber("");
    form.setNumberBefore("");
    form.setStuffCategory(commercialProposalProduce.getStuffCategory());

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward selectProduce(IActionContext context) throws Exception
  {
    CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) StoreUtil.getSession(context.getRequest(), CommercialProposalProduce.class);
    saveCurrentFormToBean(context, commercialProposalProduce);
    return context.getMapping().findForward("selectProduce");
  }

  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception
  {
    CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) StoreUtil.getSession(context.getRequest(), CommercialProposalProduce.class);
    getCurrentFormFromBean(context, commercialProposalProduce);

    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();
		String nomenclatureId = SelectFromGridAction.getSelectedId(context);

    if ( !StringUtil.isEmpty(nomenclatureId) )
    {
      form.setProduce(ProduceDAO.loadProduceWithUnit(new Integer(nomenclatureId)));
    }

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    CommercialProposalProduceForm form = (CommercialProposalProduceForm) context.getForm();
    String errMsg = "";

    if ( StringUtil.isEmpty(form.getCpr_old_version()) && StringUtil.isEmpty(form.getStuffCategory().getId()) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.commercial_produce.empty_stuff_category", null);
    }

    if ( StringUtil.isEmpty(form.getCpr_old_version()) && form.isEmptyProduce() )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.commercial_produce.empty_produce", null);
    }

    if ( !StringUtil.isEmpty(form.getCpr_old_version()) && StringUtil.isEmpty(form.getLpr_produce_name()) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.commercial_produce.empty_produce", null);
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return input(context);
    }

    CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
    CommercialProposalProduce commercialProposalProduce = CommercialProposal.getEmptyProduce();
    commercialProposalProduce.setCpr_date(commercialProposal.getCpr_date());
    saveCurrentFormToBean(context, commercialProposalProduce);
    if ( form.isReverseCalc() )
    {
      boolean fRound = commercialProposal.getCurrencyTable().isNeedRound();

      double price = CommercialProposalProduce.getRoundSum(StringUtil.appCurrencyString2double(form.getLpr_sale_price()), fRound);
      commercialProposalProduce.setLpr_sale_price(price);

      if ( commercialProposal.incoTermCaseB() )
      {
        double salePriceParkingTrans = CommercialProposalProduce.getRoundSum(price / commercialProposal.getCpr_course(), fRound);
        double saleCostParkingTrans = CommercialProposalProduce.getRoundSum(salePriceParkingTrans * commercialProposalProduce.getLpr_count(), fRound);
        commercialProposalProduce.setSale_cost_parking_trans(saleCostParkingTrans);
      }

      if ( commercialProposal.incoTermCaseD() || commercialProposal.incoTermCaseE() )
      {
        double salePriceParkingTransCustom = CommercialProposalProduce.getRoundSum(price / commercialProposal.getCpr_course(), fRound);
        commercialProposalProduce.setSale_price_parking_trans_custom(salePriceParkingTransCustom);
        double customPercent = commercialProposalProduce.getCustomPercent();
        customPercent = StringUtil.roundN(customPercent, 2);
        double salePriceParkingTrans = CommercialProposalProduce.getRoundSum(salePriceParkingTransCustom / ( 1 + customPercent / 100 + 0.0015 ), fRound);
        commercialProposalProduce.setSale_price_parking_trans(salePriceParkingTrans);
        double saleCostParkingTrans = CommercialProposalProduce.getRoundSum(salePriceParkingTrans * commercialProposalProduce.getLpr_count(), fRound);
        commercialProposalProduce.setSale_cost_parking_trans(saleCostParkingTrans);
      }
    }
    if (!StringUtil.isEmpty(form.getCustom_code().getCode()))
      commercialProposalProduce.setCustomCode(CustomCodeDAO.load(context, commercialProposalProduce.getCustomCode().getId(), commercialProposal.getCpr_date_tm()));
    else
      commercialProposalProduce.setCustomCode(new CustomCode(null, "", Double.NaN));

    if (StringUtil.isEmpty(form.getNumber()))
    {
      commercialProposal.insertProduce(form.getNumberBefore(), commercialProposalProduce);
    }
    else
    {
      commercialProposal.updateProduce(form.getNumber(), commercialProposalProduce);
    }

    return context.getMapping().findForward("back");
  }

}
