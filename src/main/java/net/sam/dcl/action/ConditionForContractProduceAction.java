package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.ConditionForContractProduce;
import net.sam.dcl.beans.ConditionForContract;
import net.sam.dcl.dao.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.form.ConditionForContractProduceForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ConditionForContractProduceAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentFormToBean(IActionContext context, ConditionForContractProduce conditionForContractProduce)
  {
    ConditionForContractProduceForm form = (ConditionForContractProduceForm) context.getForm();

    try
    {
      conditionForContractProduce.setCcp_id(form.getCcp_id());
      conditionForContractProduce.setCfc_id(form.getCfc_id());
      conditionForContractProduce.setNumber(form.getNumber());
      if (null != form.getProduce().getId())
      {
        conditionForContractProduce.setProduce(ProduceDAO.loadProduceWithUnit(form.getProduce().getId()));
      }
      if ( StringUtil.isEmpty(form.getCcp_price()) )
      {
        conditionForContractProduce.setCcp_price(0.0);
      }
      else
      {
        conditionForContractProduce.setCcp_price(StringUtil.appCurrencyString2double(form.getCcp_price()));
      }
      if ( StringUtil.isEmpty(form.getCcp_count()) )
      {
        conditionForContractProduce.setCcp_count(0.0);
      }
      else
      {
        conditionForContractProduce.setCcp_count(StringUtil.appCurrencyString2double(form.getCcp_count()));
      }
      if ( StringUtil.isEmpty(form.getCcp_nds_rate()) )
      {
        conditionForContractProduce.setCcp_nds_rate(0.0);
      }
      else
      {
        conditionForContractProduce.setCcp_nds_rate(StringUtil.appCurrencyString2double(form.getCcp_nds_rate()));
      }
      if (!StringUtil.isEmpty(form.getStuffCategory().getId()))
      {
        conditionForContractProduce.setStuffCategory(StuffCategoryDAO.load(context, form.getStuffCategory().getId()));
      }
      conditionForContractProduce.setCpr_id(form.getCpr_id());
      conditionForContractProduce.setCpr_number(form.getCpr_number());
      conditionForContractProduce.setCpr_date(form.getCpr_date());
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    StoreUtil.putSession(context.getRequest(), conditionForContractProduce);
  }

  private void getCurrentFormFromBean(IActionContext context, ConditionForContractProduce conditionForContractProduce)
  {
    ConditionForContractProduceForm form = (ConditionForContractProduceForm) context.getForm();

    form.setCcp_id(conditionForContractProduce.getCcp_id());
    form.setCfc_id(conditionForContractProduce.getCfc_id());
    form.setNumber(conditionForContractProduce.getNumber());
    form.setProduce(conditionForContractProduce.getProduce());
    if ( conditionForContractProduce.getCcp_price() == 0 )
    {
      form.setCcp_price("");
    }
    else
    {
      form.setCcp_price(StringUtil.double2appCurrencyString(conditionForContractProduce.getCcp_price()));
    }
    if ( conditionForContractProduce.getCcp_count() == 0 )
    {
      form.setCcp_count("");
    }
    else
    {
      form.setCcp_count(StringUtil.double2appCurrencyString(conditionForContractProduce.getCcp_count()));
    }
    if ( conditionForContractProduce.getCcp_nds_rate() == 0 )
    {
      form.setCcp_nds_rate("");
    }
    else
    {
      form.setCcp_nds_rate(StringUtil.double2appCurrencyString(conditionForContractProduce.getCcp_nds_rate()));
    }
    form.setStuffCategory(conditionForContractProduce.getStuffCategory());
    form.setCpr_id(conditionForContractProduce.getCpr_id());
    form.setCpr_number(conditionForContractProduce.getCpr_number());
    form.setCpr_date(conditionForContractProduce.getCpr_date());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward insert(IActionContext context) throws Exception
  {
    ConditionForContractProduce conditionForContractProduce = new ConditionForContractProduce();
    StoreUtil.putSession(context.getRequest(), conditionForContractProduce);
    //обнуляем поля формы
    getCurrentFormFromBean(context, conditionForContractProduce);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ConditionForContractProduceForm form = (ConditionForContractProduceForm) context.getForm();
    ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);

    ConditionForContractProduce conditionForContractProduce = conditionForContract.findConditionForContractProduce(form.getNumber());
    StoreUtil.putSession(context.getRequest(), conditionForContractProduce);
    getCurrentFormFromBean(context, conditionForContractProduce);

    return input(context);
  }

  public ActionForward clone(IActionContext context) throws Exception
  {
    ConditionForContractProduceForm form = (ConditionForContractProduceForm) context.getForm();
    ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);

    ConditionForContractProduce conditionForContractProduce = new ConditionForContractProduce(conditionForContract.findConditionForContractProduce(form.getNumber()));
    StoreUtil.putSession(context.getRequest(), conditionForContractProduce);
    getCurrentFormFromBean(context, conditionForContractProduce);
    form.setNumber("");

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward selectProduce(IActionContext context) throws Exception
  {
    ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) StoreUtil.getSession(context.getRequest(), ConditionForContractProduce.class);
    saveCurrentFormToBean(context, conditionForContractProduce);
    return context.getMapping().findForward("selectProduce");
  }

  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception
  {
    ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) StoreUtil.getSession(context.getRequest(), ConditionForContractProduce.class);
    getCurrentFormFromBean(context, conditionForContractProduce);

    ConditionForContractProduceForm form = (ConditionForContractProduceForm) context.getForm();
		String nomenclature_id = SelectFromGridAction.getSelectedId(context);

    if ( !StringUtil.isEmpty(nomenclature_id) )
    {
      //выбираем товар, значит он больше не связан с любым другим документом (если был)
      form.setProduce(ProduceDAO.loadProduceWithUnit(new Integer(nomenclature_id)));
    }

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    ConditionForContractProduceForm form = (ConditionForContractProduceForm) context.getForm();
    ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);
    ConditionForContractProduce conditionForContractProduce = ConditionForContract.getEmptyConditionForContractProduce();
    saveCurrentFormToBean(context, conditionForContractProduce);

    if (StringUtil.isEmpty(form.getNumber()))
    {
      conditionForContract.insertConditionForContractProduce(conditionForContractProduce);
    }
    else
    {
      conditionForContract.updateConditionForContractProduce(form.getNumber(), conditionForContractProduce);
    }
    return context.getMapping().findForward("back");
  }

}
