package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.form.ContactPersonsListForm;
import net.sam.dcl.dao.ContactPersonDAO;

import java.util.List;

import org.apache.struts.action.ActionForward;

public class ContactPersonsListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    ContactPersonsListForm form = (ContactPersonsListForm) context.getForm();
    Contractor contractor = new Contractor(form.getCtr_id());
    List res = ContactPersonDAO.loadContactPersonsForContractor(context, contractor);
    form.setList(res);
    return context.getMapping().getInputForward();
  }
}
