package net.sam.dcl.action;

import net.sam.dcl.beans.PaySum;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.PaySumForm;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.beans.Payment;
import net.sam.dcl.dao.ContractDAO;
import net.sam.dcl.dao.SpecificationDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class PaySumAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentFormToBean(IActionContext context, PaySum paySum)
  {
    PaySumForm form = (PaySumForm) context.getForm();

    paySum.setNumber(form.getNumber());
    paySum.setLps_summ(StringUtil.appCurrencyString2double(form.getLps_summ()));
    paySum.setContract(form.getContract());
    paySum.setSpecification(form.getSpecification());
    paySum.setLps_occupied(form.getLps_occupied());

    try
    {
      if (!StringUtil.isEmpty(form.getContract().getCon_id()))
      {
        paySum.setContract(ContractDAO.load(context, form.getContract().getCon_id(), false));
      }

      getSpcSumNR(context, form.getSpecification().getSpc_id());
    }
    catch (Exception e)
    {
      StrutsUtil.addError(context, e);
    }
  }

  private void getCurrentFormFromBean(IActionContext context, PaySum paySum)
  {
    PaySumForm form = (PaySumForm) context.getForm();

    form.setNumber(paySum.getNumber());
    form.setLps_summ(StringUtil.double2appCurrencyString(paySum.getLps_summ()));
    form.setContract(paySum.getContract());
    form.setSpecification(paySum.getSpecification());
    form.setLps_occupied(paySum.getLps_occupied());
  }

  private void getSpcSumNR(IActionContext context, String spcId)
  {
    PaySumForm form = (PaySumForm) context.getForm();
    form.getSpecification().setSpc_id(spcId);
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);

    try
    {
      if (!StringUtil.isEmpty(form.getSpecification().getSpc_id()))
      {
        form.setSpecification(SpecificationDAO.loadForPayment(context, form.getSpecification().getSpc_id(), payment.getPay_id()));
        form.getSpecification().setSpc_summ_nr(form.getSpecification().getSpc_summ_nr() - payment.getAllocatedDocSum(form.getSpecification().getSpc_id()));
        form.getSpecification().setSpc_summ_nr(StringUtil.roundN(form.getSpecification().getSpc_summ_nr(), 2));
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(context, e);
    }
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    PaySumForm form = (PaySumForm) context.getForm();
    getSpcSumNR(context, form.getSpecification().getSpc_id());

    return context.getMapping().findForward("form");
  }

  public ActionForward ajaxChangeSpecification(IActionContext context) throws Exception
  {
    PaySumForm form = (PaySumForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    String specificationId = context.getRequest().getParameter("specificationId");
    if ( !StringUtil.isEmpty(specificationId) )
    {
      getSpcSumNR(context, specificationId);
      if ( payment.getPay_summ_nr() >= form.getSpecification().getSpc_summ_nr() )
      {
        form.setLps_summ(form.getSpecification().getSpc_summ_nr_formatted());
      }
      else
      {
        form.setLps_summ(form.getPay_summ_nr());
      }

      String resultMsg = form.getLps_summ() + "|" +
                         form.getSpecification().getSpc_summ_nr_formatted();

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
    }

    return context.getMapping().findForward("ajax");
  }

  private void setPaymentData(IActionContext context)
  {
    PaySumForm form = (PaySumForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    form.setCtr_id(payment.getContractor().getId());
    form.setCtr_name(payment.getContractor().getName());
    form.setCur_id(payment.getCurrency().getId());
    form.setPay_summ_nr(StringUtil.double2appCurrencyString(payment.getPay_summ_nr()));
  }

  public ActionForward insert(IActionContext context) throws Exception
  {
    PaySum paySum = new PaySum();
    //обнуляем остальные поля формы
    getCurrentFormFromBean(context, paySum);

    setPaymentData(context);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    PaySumForm form = (PaySumForm) context.getForm();
    setPaymentData(context);

    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    PaySum paySum = payment.findPaySum(form.getNumber());
    getCurrentFormFromBean(context, paySum);

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  private boolean checkSumm(IActionContext context) throws Exception
  {
    PaySumForm form = (PaySumForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    double paySum = payment.getPay_summ();
    double sum = StringUtil.appCurrencyString2double(form.getLps_summ());
    double paySumNR = StringUtil.appCurrencyString2double(form.getPay_summ_nr());
    double spcSumNR = form.getSpecification().getSpc_summ_nr();

    if ( sum > 0 && paySumNR < 0 )
    {
      StrutsUtil.addError(context, "error.pay_summ.check_summ1", null);
      return false;
    }

    if ( sum < 0 && paySumNR > 0 )
    {
      StrutsUtil.addError(context, "error.pay_summ.check_summ2", null);
      return false;
    }

    if ( paySum > 0 && sum > paySumNR )
    {
      StrutsUtil.addError(context, "error.pay_summ.check_summ3", null);
      return false;
    }

    if ( paySum < 0 && sum < paySumNR )
    {
      StrutsUtil.addError(context, "error.pay_summ.check_summ4", null);
      return false;
    }

    if ( paySum > 0 && sum > spcSumNR )
    {
      StrutsUtil.addError(context, "error.pay_summ.check_summ5", null);
      return false;
    }

    return true;
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    PaySumForm form = (PaySumForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    PaySum paySum = payment.getEmptyPaySum();

    getSpcSumNR(context, form.getSpecification().getSpc_id());

    if ( !checkSumm(context) )
    {
      return input(context);
    }

    saveCurrentFormToBean(context, paySum);

    if (StringUtil.isEmpty(form.getNumber()))
    {
      payment.insertPaySum(paySum);
    }
    else
    {
      payment.updatePaySum(form.getNumber(), paySum);
    }
    return context.getMapping().findForward("back");
  }

}
