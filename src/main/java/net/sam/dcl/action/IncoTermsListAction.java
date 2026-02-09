package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.IncoTermsListForm;
import net.sam.dcl.beans.IncoTerm;

import org.apache.struts.action.ActionForward;

import java.util.List;

public class IncoTermsListAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    IncoTermsListForm form = (IncoTermsListForm) context.getForm();
    List<IncoTermsListForm.IncoTerm> list = DAOUtils.fillList(context, "select-inco_terms", form, IncoTermsListForm.IncoTerm.class, null, null);
    int i = 0;
    while ( i < list.size() )
    {
      IncoTermsListForm.IncoTerm incoTerm = list.get(i);
      if (
          !StringUtil.isEmpty(form.getForOrder()) &&
          ( IncoTerm.EXW_2010.equals(incoTerm.getTrm_name()) || IncoTerm.EXW.equals(incoTerm.getTrm_name()) )
         )
      {
        list.remove(i);
        continue;
      }
      i++;
    }

    i = 0;
    while ( i < list.size() )
    {
      IncoTermsListForm.IncoTerm incoTerm = list.get(i);
      if ( incoTerm.getTrm_name().indexOf("_2010") == -1 )
      {
        list.add(i ,new IncoTermsListForm.IncoTerm("-1", "<hr>", "<hr>"));
        break;
      }
      i++;
    }
    form.setList(list);
    return context.getMapping().getInputForward();
  }
}
