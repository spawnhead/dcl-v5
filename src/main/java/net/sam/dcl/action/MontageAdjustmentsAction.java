package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IProcessAfter;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.MontageAdjustmentsForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.User;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class MontageAdjustmentsAction extends DBTransactionAction implements IDispatchable, IProcessAfter
{
  public ActionForward show(IActionContext context) throws Exception
  {
    MontageAdjustmentsForm form = (MontageAdjustmentsForm) context.getForm();
    if ( form.isShowTable() )
    {
      DAOUtils.fillGrid(context, form.getGrid(), "select-montage_adjustments", MontageAdjustmentsForm.MontageAdjustment.class, null, null);
      for ( int i = 0; i < form.getGrid().getDataList().size(); i++ )
      {
        MontageAdjustmentsForm.MontageAdjustment montageAdjustment = (MontageAdjustmentsForm.MontageAdjustment)form.getGrid().getDataList().get(i);
        montageAdjustment.setNumber(Integer.toString(i + 1));

        montageAdjustment.setMad_mech_work_sum(StringUtil.roundN(montageAdjustment.getMad_mech_work_tariff() * (montageAdjustment.getMad_mech_work_rule_montage() + montageAdjustment.getMad_mech_work_rule_adjustment()), 1));
        montageAdjustment.setMad_mech_road_sum(StringUtil.roundN(montageAdjustment.getMad_mech_road_tariff() * montageAdjustment.getMad_mech_road_rule(), 1));
        montageAdjustment.setMad_mech_total(StringUtil.roundN(montageAdjustment.getMad_mech_work_sum() + montageAdjustment.getMad_mech_road_sum(), 1));

        montageAdjustment.setMad_el_work_sum(StringUtil.roundN(montageAdjustment.getMad_el_work_tariff() * (montageAdjustment.getMad_el_work_rule_montage() + montageAdjustment.getMad_el_work_rule_adjustment()), 1));
        montageAdjustment.setMad_el_road_sum(StringUtil.roundN(montageAdjustment.getMad_el_road_tariff() * montageAdjustment.getMad_el_road_rule(), 1));
        montageAdjustment.setMad_el_total(StringUtil.roundN(montageAdjustment.getMad_el_work_sum() + montageAdjustment.getMad_el_road_sum(), 1));
      }
    }

    /***
     * Редактировать строки может Админ, Экономист
     */
    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("showEditChecker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        if ( currentUser.isAdmin() || currentUser.isEconomist() )
        {
          return true;
        }
        return false;
      }
    }
    );

    final MontageAdjustmentsForm formFinal = (MontageAdjustmentsForm) context.getForm();
    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        MontageAdjustmentsForm.MontageAdjustment montageAdjustment = (MontageAdjustmentsForm.MontageAdjustment) formFinal.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        if ("1".equals(montageAdjustment.getMad_annul()))
        {
          return "crossed-cell";
        }
        return "";
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    MontageAdjustmentsForm form = (MontageAdjustmentsForm) context.getForm();
    form.setStuffCategory(new StuffCategory());

    return show(context);
  }
}
