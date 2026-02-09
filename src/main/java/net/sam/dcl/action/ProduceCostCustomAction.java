package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.beans.ProduceCost;
import net.sam.dcl.beans.ProduceCostCustom;
import net.sam.dcl.form.ProduceCostCustomForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostCustomAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentFormToBean(IActionContext context, ProduceCostCustom produceCostCustom)
  {
    ProduceCostCustomForm form = (ProduceCostCustomForm) context.getForm();

    produceCostCustom.setLpc_percent(form.getLpc_percent());
    produceCostCustom.setLpc_summ(StringUtil.appCurrencyString2double(form.getLpc_summ()));
    produceCostCustom.setLpc_summ_allocation(StringUtil.appCurrencyString2double(form.getLpc_summ_allocation()));
  }

  private void getCurrentFormFromBean(IActionContext context, ProduceCostCustom produceCostCustom)
  {
    ProduceCostCustomForm form = (ProduceCostCustomForm) context.getForm();

    form.setLpc_percent(produceCostCustom.getLpc_percent());
    form.setLpc_summ(StringUtil.double2appCurrencyString(produceCostCustom.getLpc_summ()));
    form.setLpc_summ_allocation(StringUtil.double2appCurrencyString(produceCostCustom.getLpc_summ_allocation()));
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ProduceCostCustomForm form = (ProduceCostCustomForm) context.getForm();
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    ProduceCostCustom produceCostCustom = produceCost.findProduceCostCustom(form.getLpc_percent(), null);
    getCurrentFormFromBean(context, produceCostCustom);

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    ProduceCostCustomForm form = (ProduceCostCustomForm) context.getForm();
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    ProduceCostCustom produceCostCustom = produceCost.getEmptyProduceCostCustom();
    saveCurrentFormToBean(context, produceCostCustom);

    produceCost.updateProduceCostCustom(form.getLpc_percent(), produceCostCustom);
    return context.getMapping().findForward("back");
  }

}
