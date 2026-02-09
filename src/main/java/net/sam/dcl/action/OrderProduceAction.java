package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.OrderProduceForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dao.CurrencyRateDAO;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrderProduceAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentFormToBean(IActionContext context, OrderProduce orderProduce)
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();

    try
    {
      orderProduce.setNumber(form.getNumber());
      orderProduce.setOpr_use_prev_number(form.getOpr_use_prev_number());
      orderProduce.setOpr_id(form.getOpr_id());
      if (null != form.getProduce().getId())
      {
        orderProduce.setProduce(ProduceDAO.loadProduce(form.getProduce().getId()));
      }
      orderProduce.setOpr_produce_name(form.getOpr_produce_name());
      orderProduce.setOpr_catalog_num(form.getOpr_catalog_num());
      if ( StringUtil.isEmpty(form.getOpr_count()) )
      {
        orderProduce.setOpr_count(0.0);
      }
      else
      {
        orderProduce.setOpr_count(StringUtil.appCurrencyString2double(form.getOpr_count()));
      }
      if ( StringUtil.isEmpty(form.getOpr_count_executed()) )
      {
        orderProduce.setOpr_count_executed(0.0);
      }
      else
      {
        orderProduce.setOpr_count_executed(StringUtil.appCurrencyString2double(form.getOpr_count_executed()));
      }
      if ( StringUtil.isEmpty(form.getOpr_count_occupied()) )
      {
        orderProduce.setOpr_count_occupied(0.0);
      }
      else
      {
        orderProduce.setOpr_count_occupied(StringUtil.appCurrencyString2double(form.getOpr_count_occupied()));
      }
      if ( StringUtil.isEmpty(form.getOpr_price_brutto()) )
      {
        orderProduce.setOpr_price_brutto(0.0);
      }
      else
      {
        orderProduce.setOpr_price_brutto(StringUtil.appCurrencyString2double(form.getOpr_price_brutto()));
      }
      if ( StringUtil.isEmpty(form.getOpr_discount()) )
      {
        orderProduce.setOpr_discount(0.0);
      }
      else
      {
        orderProduce.setOpr_discount(StringUtil.appCurrencyString2double(form.getOpr_discount()));
      }
      if ( StringUtil.isEmpty(form.getOpr_price_netto()) )
      {
        orderProduce.setOpr_price_netto(0.0);
      }
      else
      {
        orderProduce.setOpr_price_netto(StringUtil.appCurrencyString2double(form.getOpr_price_netto()));
      }
      if ( StringUtil.isEmpty(form.getDrp_price()) )
      {
        orderProduce.setDrp_price(0.0);
      }
      else
      {
        orderProduce.setDrp_price(StringUtil.appCurrencyString2double(form.getDrp_price()));
      }
      orderProduce.setDrp_max_extra(form.getDrp_max_extra());
      orderProduce.setStf_id(form.getStf_id());
      orderProduce.setOpr_occupied(form.getOpr_occupied());
      orderProduce.setOpr_comment(form.getOpr_comment());

      orderProduce.setContractor(form.getContractorOpr());
      orderProduce.setContract(form.getContractOpr());
      orderProduce.setSpecification(form.getSpecificationOpr());
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    StoreUtil.putSession(context.getRequest(), orderProduce);
  }

  private void getCurrentFormFromBean(IActionContext context, OrderProduce orderProduce)
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();

    form.setNumber(orderProduce.getNumber());
    form.setOpr_use_prev_number(orderProduce.getOpr_use_prev_number());
    form.setOpr_id(orderProduce.getOpr_id());
    form.setProduce(orderProduce.getProduce());
    form.setOpr_produce_name(orderProduce.getOpr_produce_name());
    form.setOpr_catalog_num(orderProduce.getOpr_catalog_num());
    if ( orderProduce.getOpr_count() == 0 )
    {
      form.setOpr_count("");
    }
    else
    {
      form.setOpr_count(StringUtil.double2appCurrencyString(orderProduce.getOpr_count()));
    }
    if ( orderProduce.getOpr_count_executed() == 0 )
    {
      form.setOpr_count_executed("");
    }
    else
    {
      form.setOpr_count_executed(StringUtil.double2appCurrencyString(orderProduce.getOpr_count_executed()));
    }
    if ( orderProduce.getOpr_count_occupied() == 0 )
    {
      form.setOpr_count_occupied("");
    }
    else
    {
      form.setOpr_count_occupied(StringUtil.double2appCurrencyString(orderProduce.getOpr_count_occupied()));
    }
    if ( orderProduce.getOpr_price_brutto() == 0 )
    {
      form.setOpr_price_brutto("");
    }
    else
    {
      form.setOpr_price_brutto(StringUtil.double2appCurrencyString(orderProduce.getOpr_price_brutto()));
    }
    if ( orderProduce.getOpr_discount() == 0 )
    {
      form.setOpr_discount("");
    }
    else
    {
      form.setOpr_discount(StringUtil.double2appCurrencyString(orderProduce.getOpr_discount()));
    }
    form.setOpr_price_netto(StringUtil.double2appCurrencyString(orderProduce.getOpr_price_netto()));
    if ( orderProduce.getDrp_price() == 0 )
    {
      form.setDrp_price("");
      if ( form.isReadOnlyDrpPrice() )
      {
        form.setDrp_price(StringUtil.double2appCurrencyString(orderProduce.getDrp_price()));
      }
    }
    else
    {
      form.setDrp_price(StringUtil.double2appCurrencyString(orderProduce.getDrp_price()));
    }
    form.setDrp_max_extra(orderProduce.getDrp_max_extra());
    form.setStf_id(orderProduce.getStf_id());
    form.setOpr_occupied(orderProduce.getOpr_occupied());
    form.setOpr_comment(orderProduce.getOpr_comment());

    form.setContractorOpr(orderProduce.getContractor());
    form.setContractOpr(orderProduce.getContract());
    form.setSpecificationOpr(orderProduce.getSpecification());
  }

  private void initGrids(IActionContext context, OrderProduce orderProduce)
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    form.getGridProductTerm().getDataList().clear();
    form.getGridReadyForShipping().getDataList().clear();
    for ( ProductionTerm productionTerm : orderProduce.getProductTerms() )
    {
      form.getGridProductTerm().getDataList().add(new ProductionTerm(productionTerm));
    }
    for ( ReadyForShipping readyForShipping : orderProduce.getReadyForShippings() )
    {
      form.getGridReadyForShipping().getDataList().add(new ReadyForShipping(readyForShipping));
    }
  }

  private void saveGrids(IActionContext context, OrderProduce orderProduce)
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    orderProduce.getProductTerms().clear();
    orderProduce.getReadyForShippings().clear();
    for ( int i = 0; i < form.getGridProductTerm().getDataList().size(); i++ )
    {
      ProductionTerm productionTerm = (ProductionTerm)form.getGridProductTerm().getDataList().get(i);
      orderProduce.getProductTerms().add(new ProductionTerm(productionTerm));
    }
    for ( int i = 0; i < form.getGridReadyForShipping().getDataList().size(); i++ )
    {
      ReadyForShipping readyForShipping = (ReadyForShipping)form.getGridReadyForShipping().getDataList().get(i);
      orderProduce.getReadyForShippings().add(new ReadyForShipping(readyForShipping));
    }
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    final OrderProduceForm form = (OrderProduceForm) context.getForm();
    if ( !"1".equals(form.getOrd_block()) )
    {
      form.setFormReadOnly(false);
    }
    else
    {
      form.setFormReadOnly(true);
    }

    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if ( currentUser.isOnlyUserInLithuania() )
    {
      form.setFormReadOnly(true);
    }

    /**
     * Доступ к редактированию секции НАД секцией "Заполняется отделом логистики": админ, экономист, логистик, менеджер
     */
    if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic() || currentUser.isManager()) && !form.isFormReadOnly())
    {
      form.setReadOnlyIfNotLikeManager(false);
    }
    else
    {
      form.setReadOnlyIfNotLikeManager(true);
    }
    /**
     * Доступ к редактированию секции "Заполняется отделом логистики": админ, экономист, логистик
     */
    if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic()) && !form.isFormReadOnly())
    {
      form.setReadOnlyIfNotLikeLogist(false);
    }
    else
    {
      form.setReadOnlyIfNotLikeLogist(true);
    }
    /**
     * поле "Планируемая дата прибытия в Литву": админ, экономист, логистик, сотрудники в Литве
     */
    if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic() || currentUser.isUserInLithuania()) && !"1".equals(form.getOrd_block()))
    {
      form.setReadOnlyIfNotLikeLogistOrUIL(false);
    }
    else
    {
      form.setReadOnlyIfNotLikeLogistOrUIL(true);
    }

    context.getRequest().setAttribute("productionTermReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return (!StringUtil.isEmpty(form.getOrd_date_conf_all()) || form.isReadOnlyIfNotLikeLogist() || form.isFormReadOnly());
      }
    });

    context.getRequest().setAttribute("readyForShippingReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return !StringUtil.isEmpty(form.getOrd_ready_for_deliv_date_all()) || form.isReadOnlyIfNotLikeLogist() || form.isFormReadOnly();
      }
    });

    context.getRequest().setAttribute("rfsArriveInLithuaniaReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return !StringUtil.isEmpty(form.getOrd_ready_for_deliv_date_all()) || form.isReadOnlyIfNotLikeLogistOrUIL();
      }
    });

    context.getRequest().setAttribute("readonlyNotLikeLogist", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return form.isReadOnlyIfNotLikeLogist() || form.isFormReadOnly();
      }
    });

    for ( int i = 0; i < form.getGridProductTerm().getDataList().size(); i++ )
    {
      ProductionTerm productionTerm = (ProductionTerm)form.getGridProductTerm().getDataList().get(i);
      productionTerm.setNumberProductionTerm(Integer.toString(i));
    }
    for ( int i = 0; i < form.getGridReadyForShipping().getDataList().size(); i++ )
    {
      ReadyForShipping readyForShipping = (ReadyForShipping)form.getGridReadyForShipping().getDataList().get(i);
      readyForShipping.setNumberReadyForShipping(Integer.toString(i));
    }

    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, order.getCurrency().getId(), StringUtil.appDateString2dbDateString(order.getOrd_date()));
    form.setCourse(StringUtil.double2appCurrencyString(currencyRate.getCrt_rate()));

    return context.getMapping().getInputForward();
  }

  public ActionForward clone(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    form.setOrd_date_conf_all(order.getOrd_date_conf_all());
    form.setOrd_ready_for_deliv_date_all(order.getOrd_ready_for_deliv_date_all());

    OrderProduce orderProduce = new OrderProduce(order.findProduce(form.getNumber()));
    StoreUtil.putSession(context.getRequest(), orderProduce);
    getCurrentFormFromBean(context, orderProduce);

    orderProduce.setStf_id(form.getStf_id());
    orderProduce.setNumber("");
    orderProduce.setOpr_id("");
    orderProduce.setOpr_use_prev_number("");
    orderProduce.setOpr_parent_id("");
    orderProduce.setOpr_have_depend("");
    if ( !form.isCalculateNetto() )
    {
      orderProduce.setOpr_price_brutto(0.0);
      orderProduce.setOpr_discount(0.0);
    }
    else
    {
      orderProduce.setOpr_price_netto(0.0);
      if ( "1".equals(form.getOrd_discount_all()) )
      {
        orderProduce.setOpr_discount(0.0);
      }
    }
    OrderProduce.loadGridData(orderProduce, order);
    getCurrentFormFromBean(context, orderProduce);
    initGrids(context, orderProduce);

    form.setShowMsg(false);
    return input(context);
  }

  public ActionForward insert(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    form.setOrd_date_conf_all(order.getOrd_date_conf_all());
    form.setOrd_ready_for_deliv_date_all(order.getOrd_ready_for_deliv_date_all());
    
    OrderProduce orderProduce = new OrderProduce();
    orderProduce.setStf_id(form.getStf_id());
    orderProduce.setOpr_occupied("");
    StoreUtil.putSession(context.getRequest(), orderProduce);
    //обнуляем поля формы
    OrderProduce.loadGridData(orderProduce, order);
    getCurrentFormFromBean(context, orderProduce);
    initGrids(context, orderProduce);

    form.setShowMsg(false);
    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    //блокирован ли Заказ устанавливаем только при редактировании
    form.setOrd_block(order.getOrd_block());
    form.setOrd_date_conf_all(order.getOrd_date_conf_all());
    form.setOrd_ready_for_deliv_date_all(order.getOrd_ready_for_deliv_date_all());

    OrderProduce orderProduce = order.findProduce(form.getNumber());
    orderProduce.setStf_id(form.getStf_id());
    StoreUtil.putSession(context.getRequest(), orderProduce);
    OrderProduce.loadGridData(orderProduce, order);
    getCurrentFormFromBean(context, orderProduce);
    initGrids(context, orderProduce);

    form.setShowMsg(false);
    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward selectProduce(IActionContext context) throws Exception
  {
    OrderProduce orderProduce = (OrderProduce) StoreUtil.getSession(context.getRequest(), OrderProduce.class);
    saveCurrentFormToBean(context, orderProduce);
    return context.getMapping().findForward("selectProduce");
  }

  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception
  {
    OrderProduce orderProduce = (OrderProduce) StoreUtil.getSession(context.getRequest(), OrderProduce.class);
    getCurrentFormFromBean(context, orderProduce);

    OrderProduceForm form = (OrderProduceForm) context.getForm();
		String nomenclature_id = SelectFromGridAction.getSelectedId(context);

    if ( !StringUtil.isEmpty(nomenclature_id) )
    {
      form.setProduce(ProduceDAO.loadProduce(new Integer(nomenclature_id)));
    }

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    OrderProduce orderProduce = Order.getEmptyProduce();
    saveCurrentFormToBean(context, orderProduce);
    String errMsg = "";

    if ( orderProduce.getOpr_count_executed() > orderProduce.getOpr_count() )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_count", null);
    }

    if (
         !StringUtil.isEmpty(form.getOrd_all_include_in_spec()) &&
         StringUtil.isEmpty(form.getOrd_by_guaranty()) &&
         StringUtil.isEmpty(form.getDrp_price())
      )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.order_produce.empty_drp_price", null);
    }

    double countProduction = 0.0;
    double countReadyForShipping = 0.0;
    for ( int i = 0; i < form.getGridProductTerm().getDataList().size(); i++ )
    {
      ProductionTerm productionTerm = (ProductionTerm)form.getGridProductTerm().getDataList().get(i);
      countProduction += productionTerm.getPtr_count();

      if ( productionTerm.getPtr_comment().length() > 900 )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_length_production", null);
        break;
      }

      if ( StringUtil.isEmpty(form.getOrd_date_conf_all()) )
      {
        if ( ProductionTerm.errorDateFormat.equals(productionTerm.getPtr_date()) )
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_date_production", null);
          break;
        }
        if ( StringUtil.isEmpty(productionTerm.getPtr_date()) )
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_production_term", null);
          break;
        }
      }
    }

    for ( int i = 0; i < form.getGridReadyForShipping().getDataList().size(); i++ )
    {
      ReadyForShipping readyForShipping = (ReadyForShipping)form.getGridReadyForShipping().getDataList().get(i);
      countReadyForShipping += readyForShipping.getRfs_count();

      if ( readyForShipping.getRfs_number().length() > 60 || 
           readyForShipping.getRfs_gabarit().length() > 300 ||
           readyForShipping.getRfs_comment().length() > 900
         )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_length_ready_for_shp", null);
        break;
      }

      if ( StringUtil.isEmpty(form.getOrd_ready_for_deliv_date_all()) )
      {
        if ( ProductionTerm.errorDateFormat.equals(readyForShipping.getRfs_date()) )
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_date_ready_for_shp", null);
          break;
        }
        if ( StringUtil.isEmpty(readyForShipping.getRfs_date()) || StringUtil.isEmpty(readyForShipping.getShippingDocType().getId()) || StringUtil.isEmpty(readyForShipping.getRfs_number()) )
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_ready_for_shipping", null);
          break;
        }
      }
    }

    if ( StringUtil.isEmpty(form.getOrd_date_conf_all()) )
    {
      if ( countProduction > orderProduce.getOpr_count() )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_count_production", null);
      }
    }

    if ( StringUtil.isEmpty(form.getOrd_ready_for_deliv_date_all()) )
    {
      if ( countReadyForShipping > orderProduce.getOpr_count() )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.order_produce.incorrect_count_ready_for_shp", null);
      }
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return input(context);
    }

    if (StringUtil.isEmpty(form.getNumber()))
    {
      if ( order.haveProduce(orderProduce.getProduce().getId()) )
      {
        form.setShowMsg(true);
        return input(context);
      }
      saveGrids(context, orderProduce);
      order.insertProduce(orderProduce);
    }
    else
    {
      saveGrids(context, orderProduce);
      order.updateProduce(form.getNumber(), orderProduce);
    }
    return context.getMapping().findForward("back");
  }

  public ActionForward forceProcess(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    OrderProduce orderProduce = Order.getEmptyProduce();
    saveCurrentFormToBean(context, orderProduce);

    if (StringUtil.isEmpty(form.getNumber()))
    {
      saveGrids(context, orderProduce);
      order.insertProduce(orderProduce);
    }
    else
    {
      saveGrids(context, orderProduce);
      order.updateProduce(form.getNumber(), orderProduce);
    }
    return context.getMapping().findForward("back");
  }

  public ActionForward newProductTerm(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    ProductionTerm productionTerm = new ProductionTerm();
    form.getGridProductTerm().getDataList().add(productionTerm);
    return input(context);
  }

  public ActionForward deleteProductTerm(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    for ( int i = 0; i < form.getGridProductTerm().getDataList().size(); i++ )
    {
      ProductionTerm productionTerm = (ProductionTerm)form.getGridProductTerm().getDataList().get(i);
      if (productionTerm.getNumberProductionTerm().equals(form.getNumberProductionTerm()) )
      {
        form.getGridProductTerm().getDataList().remove(i);
        break;
      }
    }
    return input(context);
  }

  public ActionForward newReadyForShipping(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    ReadyForShipping readyForShipping = new ReadyForShipping();
    form.getGridReadyForShipping().getDataList().add(readyForShipping);
    return input(context);
  }

  public ActionForward deleteReadyForShipping(IActionContext context) throws Exception
  {
    OrderProduceForm form = (OrderProduceForm) context.getForm();
    for ( int i = 0; i < form.getGridReadyForShipping().getDataList().size(); i++ )
    {
      ReadyForShipping readyForShipping = (ReadyForShipping)form.getGridReadyForShipping().getDataList().get(i);
      if (readyForShipping.getNumberReadyForShipping().equals(form.getNumberReadyForShipping()) )
      {
        form.getGridReadyForShipping().getDataList().remove(i);
        break;
      }
    }
    return input(context);
  }

}
