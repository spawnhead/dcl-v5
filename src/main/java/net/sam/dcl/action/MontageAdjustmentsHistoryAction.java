package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IProcessAfter;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.MontageAdjustmentsHistoryForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.User;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class MontageAdjustmentsHistoryAction extends DBTransactionAction implements IDispatchable, IProcessAfter
{
  public ActionForward show(IActionContext context) throws Exception
  {
    MontageAdjustmentsHistoryForm form = (MontageAdjustmentsHistoryForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-montage_adjustments_history", MontageAdjustmentsHistoryForm.MontageAdjustmentHistory.class, null, null);
    for ( int i = 0; i < form.getGrid().getDataList().size(); i++ )
    {
      MontageAdjustmentsHistoryForm.MontageAdjustmentHistory montageAdjustment = (MontageAdjustmentsHistoryForm.MontageAdjustmentHistory)form.getGrid().getDataList().get(i);

      montageAdjustment.setMad_mech_work_sum(StringUtil.roundN(montageAdjustment.getMad_mech_work_tariff() * (montageAdjustment.getMad_mech_work_rule_montage() + montageAdjustment.getMad_mech_work_rule_adjustment()), 1));
      montageAdjustment.setMad_mech_road_sum(StringUtil.roundN(montageAdjustment.getMad_mech_road_tariff() * montageAdjustment.getMad_mech_road_rule(), 1));
      montageAdjustment.setMad_mech_total(StringUtil.roundN(montageAdjustment.getMad_mech_work_sum() + montageAdjustment.getMad_mech_road_sum(), 1));

      montageAdjustment.setMad_el_work_sum(StringUtil.roundN(montageAdjustment.getMad_el_work_tariff() * (montageAdjustment.getMad_el_work_rule_montage() + montageAdjustment.getMad_el_work_rule_adjustment()), 1));
      montageAdjustment.setMad_el_road_sum(StringUtil.roundN(montageAdjustment.getMad_el_road_tariff() * montageAdjustment.getMad_el_road_rule(), 1));
      montageAdjustment.setMad_el_total(StringUtil.roundN(montageAdjustment.getMad_el_work_sum() + montageAdjustment.getMad_el_road_sum(), 1));
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

    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    MontageAdjustmentsHistoryForm form = (MontageAdjustmentsHistoryForm) context.getForm();
    form.setMad_id("");

    return show(context);
  }
}