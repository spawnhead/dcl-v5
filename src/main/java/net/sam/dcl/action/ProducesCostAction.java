package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.Route;
import net.sam.dcl.beans.ProduceCost;
import net.sam.dcl.beans.User;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.form.ProducesCostForm;
import net.sam.dcl.dao.ProduceCostDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProducesCostAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{

  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        ProducesCostForm form = (ProducesCostForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        ProducesCostForm form = (ProducesCostForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ProducesCostForm form = (ProducesCostForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    ProducesCostForm form = (ProducesCostForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-produce_cost", ProducesCostForm.ProduceCost.class, null, null);

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        ProducesCostForm.ProduceCost record = (ProducesCostForm.ProduceCost) ctx.getBean();
        if ( currentUser.isOnlyDeclarant() && !currentUser.getUsr_id().equals(record.getUsr_id_create()) )
        {
          return true;
        }

        if ( currentUser.isAdmin() || !"1".equals(record.getPrc_block()))
        {
          return false;
        }
        
        return true;
      }
    });

    context.getRequest().setAttribute("editChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        ProducesCostForm.ProduceCost record = (ProducesCostForm.ProduceCost) ctx.getBean();
        return ( currentUser.isOnlyDeclarant() && !currentUser.getUsr_id().equals(record.getUsr_id_create()) );
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward block(IActionContext context) throws Exception
  {
    ProducesCostForm form = (ProducesCostForm) context.getForm();
    ProduceCost produce_cost = new ProduceCost();
    produce_cost.setPrc_id(form.getPrc_id());
    if ("1".equals(form.getBlock()))
    {
      produce_cost.setPrc_block("");
    }
    else
    {
      produce_cost.setPrc_block("1");
    }
    ProduceCostDAO.saveBlock(context, produce_cost);

    return internalFilter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ProducesCostForm form = (ProducesCostForm) context.getForm();
    form.setRoute(new Route());
    form.setDate_begin("");
    form.setDate_end("");
    form.setNumber("");
    form.setNumber_1c("");
    form.setBlock_in_filter("");

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

}
