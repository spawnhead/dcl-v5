package net.sam.dcl.action;

import net.sam.dcl.beans.ProduceMovement;
import net.sam.dcl.beans.ProduceMovementProduct;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.ProduceMovementForm;
import net.sam.dcl.service.helper.ProduceMovementHelper;
import net.sam.dcl.taglib.table.IRowParser;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.RowParserContext;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceMovementAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    ProduceMovementForm form = (ProduceMovementForm) context.getForm();
    form.setDivide_into_chain("1");

    if ( null == form.getProduce().getId() )
    {
      return context.getMapping().findForward("back");  
    }
    DboProduce produce = ProduceDAO.loadProduce(form.getProduce().getId());
    ProduceMovement produceMovement = ProduceMovementHelper.formProduceMovementObject(context, produce, null);

    form.setShowLegend(produceMovement.isShowLegend());
    form.setShowLegendInput(produceMovement.isShowLegendInput());
    form.setShowLegendOrder(produceMovement.isShowLegendOrder());
    form.setShowLegendTransit(produceMovement.isShowLegendTransit());

    form.setProduce(produceMovement.getProduce());
    form.getGrid().setDataList(produceMovement.getResultListChain());

    StoreUtil.putSession(context.getRequest(), produceMovement);

    return show(context);
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final ProduceMovementForm form = (ProduceMovementForm) context.getForm();

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      int size = form.getGrid().getDataList().size();
      int newChainCount = 0;
      int counter = -1;
      String addClass = "cell-even";

      public String check(StyleClassCheckerContext context)
      {
        int counterCheck = context.getTable().getRecordCounter() - 1;
        if ( counterCheck < 0 ) //вообще-то это баг где-то внутри грида
        {
          return "";
        }

        ProduceMovementProduct.ProduceResultListLine produceResultListLine = (ProduceMovementProduct.ProduceResultListLine) form.getGrid().getDataList().get(counterCheck);
        if ( produceResultListLine.isProductLine() )
        {
          newChainCount = 0;
          return "bold-orange-cell";
        }
        if ( produceResultListLine.isItogLine() )
        {
          return "bold-cell";
        }
        if ( produceResultListLine.isItogLineLarge() )
        {
          return "bold-cell-dark";
        }

        if ( !StringUtil.isEmpty(form.getDivide_into_chain()) )
        {
          //новая цепочка и строка
          if (produceResultListLine.isNewChain() && context.getTable().getRecordCounter() != counter)
          {
            if (newChainCount % 2 != 0)
            {
              addClass = "cell-not-even";
            }
            else
            {
              addClass = "cell-even";
            }
            newChainCount++;
            counter = context.getTable().getRecordCounter();
          }
          return addClass;
        }

        return "";
      }
    });

    final String strStuffCategory = StrutsUtil.getMessage(context, "ProduceMovementList.stuffCategory");
    final String strCatNumber = StrutsUtil.getMessage(context, "ProduceMovementList.catNumber");
    context.getRequest().setAttribute("row-parser", new IRowParser()
    {
      boolean processed = false;

      public int parse(RowParserContext rpContext) throws Exception
      {
        ProduceMovementProduct.ProduceResultListLine produceResultListLine = (ProduceMovementProduct.ProduceResultListLine) rpContext.getBean();
        if ( produceResultListLine.isProductLine() )
        {
          String str = "<td colspan=100 class='orange-row'>" +
                       strStuffCategory + "&nbsp;"+
                       produceResultListLine.getOrd_date() + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                       strCatNumber + "&nbsp;"+
                       produceResultListLine.getPrc_date() +
                       "</td>";
          rpContext.getExtraRowBefore().append(str);
          return IRowParser.SKIP_ROW;
        }
        return IRowParser.EVAL_ROW_INCLUDE;
      }
    });

    return context.getMapping().findForward("form");
  }

  public ActionForward reload(IActionContext context) throws Exception
  {
    ProduceMovementForm form = (ProduceMovementForm) context.getForm();
    ProduceMovement produceMovement = (ProduceMovement) StoreUtil.getSession(context.getRequest(), ProduceMovement.class);
    if ( !StringUtil.isEmpty(form.getDivide_into_chain()) )
    {
      form.getGrid().setDataList(produceMovement.getResultListChain());
    }
    else
    {
      form.getGrid().setDataList(produceMovement.getResultList());
    }

    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }
}
