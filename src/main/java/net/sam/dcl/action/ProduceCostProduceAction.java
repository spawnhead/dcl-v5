package net.sam.dcl.action;

import net.sam.dcl.beans.Constants;
import net.sam.dcl.beans.ProduceCost;
import net.sam.dcl.beans.ProduceCostProduce;
import net.sam.dcl.beans.Purpose;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.dao.DepartmentDAO;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dao.PurposeDAO;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.form.ProduceCostProduceForm;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostProduceAction extends DBTransactionAction implements IDispatchable {

  private void saveCurrentFormToBean(IActionContext context, ProduceCostProduce produceCostProduce) {
    ProduceCostProduceForm form = (ProduceCostProduceForm) context.getForm();

    produceCostProduce.setNumber(form.getNumber());
    produceCostProduce.setLpc_id(form.getLpc_id());
    produceCostProduce.setPrc_id(form.getPrc_id());
    produceCostProduce.setPrc_date(form.getPrc_date());
    if (null != form.getProduce().getId()) {
      produceCostProduce.setProduce(ProduceDAO.loadProduceWithUnit(form.getProduce().getId()));
    }
    produceCostProduce.setLpc_produce_name(form.getLpc_produce_name());
    produceCostProduce.setStuffCategory(form.getStuffCategory());
    try {
      if (!StringUtil.isEmpty(form.getManager().getUsr_id())) {
        produceCostProduce.setManager(UserDAO.load(context, form.getManager().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getDepartment().getId())) {
        produceCostProduce.setDepartment(DepartmentDAO.load(context, form.getDepartment().getId()));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    produceCostProduce.setLpc_count(StringUtil.appCurrencyString2double(form.getLpc_count()));
    produceCostProduce.setLpc_cost_one_ltl_formatted(form.getLpc_cost_one_ltl());
    produceCostProduce.setLpc_cost_one_by_formatted(form.getLpc_cost_one_by());
    produceCostProduce.setLpc_price_list_by_formatted(form.getLpc_price_list_by());
    produceCostProduce.setLpc_weight_formatted(form.getLpc_weight());
    produceCostProduce.setLpc_summ(StringUtil.appCurrencyString2double(form.getLpc_summ()));
    produceCostProduce.setLpc_1c_number(form.getLpc_1c_number());
    produceCostProduce.setLpc_occupied(form.getLpc_occupied());
    produceCostProduce.setPurpose(form.getPurpose());
    produceCostProduce.setLpc_comment(form.getLpc_comment());
    produceCostProduce.setOpr_id(form.getOpr_id());
    produceCostProduce.setAsm_id(form.getAsm_id());
    produceCostProduce.setApr_id(form.getApr_id());
    produceCostProduce.setDrp_id(form.getDrp_id());
    produceCostProduce.setSip_id(form.getSip_id());

    produceCostProduce.setLpc_percent_dcl_1_4_formatted(form.getLpc_percent_dcl_1_4());
  }

  private void getCurrentFormFromBean(IActionContext context, ProduceCostProduce produceCostProduce) {
    ProduceCostProduceForm form = (ProduceCostProduceForm) context.getForm();

    form.setNumber(produceCostProduce.getNumber());
    form.setLpc_id(produceCostProduce.getLpc_id());
    form.setPrc_id(produceCostProduce.getPrc_id());
    form.setProduce(produceCostProduce.getProduce());
    form.setLpc_produce_name(produceCostProduce.getLpc_produce_name());
    form.setStuffCategory(produceCostProduce.getStuffCategory());
    form.setManager(produceCostProduce.getManager());
    form.setDepartment(produceCostProduce.getDepartment());
    form.setLpc_count(StringUtil.double2appCurrencyString(produceCostProduce.getLpc_count()));
    form.setLpc_cost_one_ltl(produceCostProduce.getLpc_cost_one_ltl_formatted());
    form.setLpc_cost_one_by(produceCostProduce.getLpc_cost_one_by_formatted());
    form.setLpc_price_list_by(produceCostProduce.getLpc_price_list_by_formatted());
    form.setLpc_weight(produceCostProduce.getLpc_weight_formatted());
    form.setLpc_summ(StringUtil.double2appCurrencyString(produceCostProduce.getLpc_summ()));
    form.setLpc_1c_number(produceCostProduce.getLpc_1c_number());
    form.setLpc_occupied(produceCostProduce.getLpc_occupied());
    form.setPurpose(produceCostProduce.getPurpose());
    form.setLpc_comment(produceCostProduce.getLpc_comment());
    form.setOpr_id(produceCostProduce.getOpr_id());
    form.setAsm_id(produceCostProduce.getAsm_id());
    form.setApr_id(produceCostProduce.getApr_id());
    form.setDrp_id(produceCostProduce.getDrp_id());
    form.setSip_id(produceCostProduce.getSip_id());

    form.setLpc_percent_dcl_1_4(produceCostProduce.getLpc_percent_dcl_1_4_formatted());
  }

  public ActionForward input(IActionContext context) throws Exception {
    return context.getMapping().getInputForward();
  }

  public ActionForward insert(IActionContext context) throws Exception {
    ProduceCostProduceForm form = (ProduceCostProduceForm) context.getForm();

    ProduceCostProduce produceCostProduce = new ProduceCostProduce();
    produceCostProduce.setPrc_id(form.getPrc_id());

    StoreUtil.putSession(context.getRequest(), produceCostProduce);
    //обнуляем поля формы
    getCurrentFormFromBean(context, produceCostProduce);

    String default_load_id = Config.getString(Constants.defaultPurposeProduce);
    Purpose default_purpose = new Purpose();
    default_purpose.setId(default_load_id);
    PurposeDAO.load(context, default_purpose);
    form.setPurpose(default_purpose);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception {
    ProduceCostProduceForm form = (ProduceCostProduceForm) context.getForm();
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);

    ProduceCostProduce produceCostProduce = produceCost.findProduceCostProduce(form.getNumber());
    StoreUtil.putSession(context.getRequest(), produceCostProduce);
    getCurrentFormFromBean(context, produceCostProduce);

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception {
    return context.getMapping().findForward("back");
  }

  public ActionForward selectProduce(IActionContext context) throws Exception {
    ProduceCostProduce produceCostProduce = (ProduceCostProduce) StoreUtil.getSession(context.getRequest(), ProduceCostProduce.class);
    saveCurrentFormToBean(context, produceCostProduce);
    return context.getMapping().findForward("selectProduce");
  }

  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception {
    ProduceCostProduce produceCostProduce = (ProduceCostProduce) StoreUtil.getSession(context.getRequest(), ProduceCostProduce.class);
    getCurrentFormFromBean(context, produceCostProduce);

    ProduceCostProduceForm form = (ProduceCostProduceForm) context.getForm();
    String nomenclature_id = SelectFromGridAction.getSelectedId(context);

    if (!StringUtil.isEmpty(nomenclature_id)) {
      produceCostProduce.setOpr_id(null);
      produceCostProduce.setAsm_id(null);
      produceCostProduce.setApr_id(null);
      produceCostProduce.setDrp_id(null);
      produceCostProduce.setSip_id(null);
      form.setProduce(ProduceDAO.loadProduceWithUnit(new Integer(nomenclature_id)));
    }

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception {
    ProduceCostProduceForm form = (ProduceCostProduceForm) context.getForm();
    ProduceCost prc = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    ProduceCostProduce produceCostProduce = ProduceCost.getEmptyProduceCostProduce();
    saveCurrentFormToBean(context, produceCostProduce);

    if (StringUtil.isEmpty(form.getNumber())) {
      prc.insertProduceCostProduce(produceCostProduce);
    } else {
      prc.updateProduceCostProduce(form.getNumber(), produceCostProduce);
    }
    return context.getMapping().findForward("back");
  }

}
