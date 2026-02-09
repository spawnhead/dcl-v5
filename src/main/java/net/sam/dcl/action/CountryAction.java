package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.form.CountryForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.beans.Country;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CountryAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    context.getRequest().getSession().setAttribute(Country.currentCountryId, null);

    CountryForm form = (CountryForm) context.getForm();
    form.setIs_new_doc("true");

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    CountryForm form = (CountryForm) context.getForm();
    DAOUtils.load(context, "country-load", null);
    if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
    {
      UserDAO.load(context, form.getCreateUser());
    }
    if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
    {
      UserDAO.load(context, form.getEditUser());
    }
    form.setUsr_date_create(StringUtil.dbDateString2appDateString(form.getUsr_date_create()));
    form.setUsr_date_edit(StringUtil.dbDateString2appDateString(form.getUsr_date_edit()));

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    CountryForm form = (CountryForm) context.getForm();
    if (StringUtil.isEmpty(form.getCut_id()))
    {
      DAOUtils.load(context, "country-insert", null);
    }
    else
    {
      DAOUtils.update(context, "country-update", null);
    }
    context.getRequest().getSession().setAttribute(Country.currentCountryId, form.getCut_id());

    return context.getMapping().findForward("back");
  }

	public ActionForward delete(IActionContext context) throws Exception
	{
		DAOUtils.update(context, "country-delete", null);
		return context.getMapping().findForward("back");
	}

}