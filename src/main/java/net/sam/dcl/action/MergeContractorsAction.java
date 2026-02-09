package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.MergeContractorsForm;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.ForUpdateDependedDocuments;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class MergeContractorsAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward merge(IActionContext context) throws Exception
  {
    String oldId = (String)context.getRequest().getSession().getAttribute(Contractor.oldContractorId);
    String newId = (String)context.getRequest().getSession().getAttribute(Contractor.newContractorId);

    if ( StringUtil.isEmpty(oldId) || StringUtil.isEmpty(newId) )
    {
      return context.getMapping().findForward("back");
    }

    MergeContractorsForm form = (MergeContractorsForm) context.getForm();
    form.setOldId(oldId);
    form.setNewId(newId);

    form.setContractorLeft(ContractorDAO.load(context, oldId));
    form.setContractorRight(ContractorDAO.load(context, newId));

    form.setRightName("1");
    form.setRightFullName("1");
    form.setRightIndex("1");
    form.setRightRegion("1");
    form.setRightPlace("1");
    form.setRightStreet("1");
    form.setRightBuilding("1");
    form.setRightAddInfo("1");
    form.setRightPhone("1");
    form.setRightFax("1");
    form.setRightEMail("1");
    form.setRightBank("1");
    form.setRightUNP("1");
    form.setRightOKPO("1");
    form.setRightReputation("1");
    form.setRightCountry("1");
    form.setRightComment("1");

    context.getRequest().getSession().setAttribute(Contractor.oldContractorId, null);
    context.getRequest().getSession().setAttribute(Contractor.newContractorId, null);

    return context.getMapping().getInputForward();
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    MergeContractorsForm form = (MergeContractorsForm) context.getForm();

    Contractor contractor = ContractorDAO.load(context, form.getNewId());
    contractor.setName(!StringUtil.isEmpty(form.getLeftName()) ? form.getContractorLeft().getName() : form.getContractorRight().getName());
    contractor.setFullname(!StringUtil.isEmpty(form.getLeftFullName()) ? form.getContractorLeft().getFullname() : form.getContractorRight().getFullname());
    contractor.setIndex(!StringUtil.isEmpty(form.getLeftIndex()) ? form.getContractorLeft().getIndex() : form.getContractorRight().getIndex());
    contractor.setRegion(!StringUtil.isEmpty(form.getLeftRegion()) ? form.getContractorLeft().getRegion() : form.getContractorRight().getRegion());
    contractor.setPlace(!StringUtil.isEmpty(form.getLeftPlace()) ? form.getContractorLeft().getPlace() : form.getContractorRight().getPlace());
    contractor.setStreet(!StringUtil.isEmpty(form.getLeftStreet()) ? form.getContractorLeft().getStreet() : form.getContractorRight().getStreet());
    contractor.setBuilding(!StringUtil.isEmpty(form.getLeftBuilding()) ? form.getContractorLeft().getBuilding() : form.getContractorRight().getBuilding());
    contractor.setAddInfo(!StringUtil.isEmpty(form.getLeftAddInfo()) ? form.getContractorLeft().getAddInfo() : form.getContractorRight().getAddInfo());
    contractor.setPhone(!StringUtil.isEmpty(form.getLeftPhone()) ? form.getContractorLeft().getPhone() : form.getContractorRight().getPhone());
    contractor.setFax(!StringUtil.isEmpty(form.getLeftFax()) ? form.getContractorLeft().getFax() : form.getContractorRight().getFax());
    contractor.setEmail(!StringUtil.isEmpty(form.getLeftEMail()) ? form.getContractorLeft().getEmail() : form.getContractorRight().getEmail());
    contractor.setBank_props(!StringUtil.isEmpty(form.getLeftBank()) ? form.getContractorLeft().getBank_props() : form.getContractorRight().getBank_props());
    contractor.setUnp(!StringUtil.isEmpty(form.getLeftUNP()) ? form.getContractorLeft().getUnp() : form.getContractorRight().getUnp());
    contractor.setOkpo(!StringUtil.isEmpty(form.getLeftOKPO()) ? form.getContractorLeft().getOkpo() : form.getContractorRight().getOkpo());
    contractor.getReputation().setId(!StringUtil.isEmpty(form.getLeftReputation()) ? form.getContractorLeft().getReputation().getId() : form.getContractorRight().getReputation().getId());
    contractor.getCountry().setId(!StringUtil.isEmpty(form.getLeftCountry()) ? form.getContractorLeft().getCountry().getId() : form.getContractorRight().getCountry().getId());
	  contractor.setComment(!StringUtil.isEmpty(form.getLeftComment()) ? form.getContractorLeft().getComment() : form.getContractorRight().getComment());

    ContractorDAO.updateDependedDocs(context, new ForUpdateDependedDocuments(form.getOldId(), form.getNewId()));
    ContractorDAO.save(context, contractor);    

    return context.getMapping().findForward("back");
  }
}