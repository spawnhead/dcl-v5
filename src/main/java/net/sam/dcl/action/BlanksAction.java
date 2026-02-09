package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.util.*;
import net.sam.dcl.form.BlanksForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class BlanksAction extends DBAction implements IDispatchable
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    BlanksForm form = (BlanksForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridBlanks(), "select-blanks", BlanksForm.Blank.class, null, null);

    form.setNeedPrintExample(false);
    return input(context);
  }

  public ActionForward printExample(IActionContext context) throws Exception
  {
    BlanksForm form = (BlanksForm) context.getForm();
    Blank blank = BlankDAO.load(context, form.getBln_id());
    if ( Constants.commercialProposalBlankType.equals(blank.getBlankType().getId()) )
    {
      CommercialProposal commercialProposal = CommercialProposalDAO.load(context, CommercialProposalDAO.getRandomId(context, form.getBln_id()));
      StoreUtil.putSession(context.getRequest(), commercialProposal);
      form.setPrintAction("CommercialProposalPrintAction");
    }
    if ( Constants.orderBlankType.equals(blank.getBlankType().getId()) )
    {
      Order order = OrderDAO.load(context, OrderDAO.getRandomId(context, form.getBln_id()), true, false);
      StoreUtil.putSession(context.getRequest(), order);
      form.setPrintAction("OrderPrintAction");
    }
    if ( Constants.commonBlankType.equals(blank.getBlankType().getId()) )
    {
      NomenclatureProduceActionBean produceActionBean = new NomenclatureProduceActionBean();
      produceActionBean.initFromActionContext(context);
      produceActionBean.processBefore();
      Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
      hibSession.beginTransaction();
      produceActionBean.setId(String.valueOf(hibSession.getNamedQuery("get-max-produce-id").list().get(0)));
      produceActionBean.edit();
      hibSession.getTransaction().commit();
      StoreUtil.putSession(context.getRequest(), produceActionBean);
      form.setPrintAction("NomenclatureProducePrintAction");
    }
    if ( Constants.commonLightBlankType.equals(blank.getBlankType().getId()) )
    {
      form.setPrintAction("ContractorRequestPrintActAction");
      ContractorRequest contractorRequest;
      try
      {
        contractorRequest = ContractorRequestDAO.load(context, ContractorRequestDAO.getRandomIdForAct(context, form.getBln_id()));
      }
      catch (DAOException ex)
      {
        StrutsUtil.addError(context, "error.blanks.no_data_for_print", null);
        return input(context);
      }
      StoreUtil.putSession(context.getRequest(), contractorRequest);
    }
    if ( Constants.letterRequestBlankType.equals(blank.getBlankType().getId()) )
    {
      form.setPrintAction("ContractorRequestPrintLetterRequestAction");
      ContractorRequest contractorRequest;
      try
      {
        contractorRequest = ContractorRequestDAO.load(context, ContractorRequestDAO.getRandomIdForLetter(context));
      }
      catch (DAOException ex)
      {
        StrutsUtil.addError(context, "error.blanks.no_data_for_print", null);
        return input(context);
      }
      StoreUtil.putSession(context.getRequest(), contractorRequest);
    }

    form.setNeedPrintExample(true);
    return input(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    final BlanksForm form = (BlanksForm) context.getForm();

    context.getRequest().setAttribute("editReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        BlanksForm.Blank blank = (BlanksForm.Blank) form.getGridBlanks().getDataList().get(context.getTable().getRecordCounter() - 1);
        if (StringUtil.isEmpty(blank.getBln_id()))
        {
          return true;
        }
        return false;
      }
    });

    if (form.isNeedPrintExample())
    {
      form.setPrintExample("true");
    }
    else
    {
      form.setPrintExample("false");
    }
    form.setNeedPrintExample(false);

    return context.getMapping().getInputForward();
  }
}