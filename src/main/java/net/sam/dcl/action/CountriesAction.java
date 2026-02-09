package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IProcessAfter;
import net.sam.dcl.form.CountriesForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.ForUpdateDependedDocuments;
import net.sam.dcl.dao.CountryDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CountriesAction extends DBTransactionAction implements IDispatchable, IProcessAfter
{
  public ActionForward show(IActionContext context) throws Exception
  {
    final CountriesForm form = (CountriesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-countries", CountriesForm.Country.class, null, null);
		context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
		{
			public boolean check(ShowCheckerContext context)
			{
				CountriesForm.Country country = (CountriesForm.Country) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				return  !country.isOccupiedB();
			}
		}
		);

    CountriesForm formEdit = (CountriesForm) context.getForm();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    formEdit.setShowEdit(currentUser.isAdmin() || currentUser.isEconomist());
    formEdit.setShowMerge(currentUser.isAdmin());

    return context.getMapping().getInputForward();
  }

  public ActionForward processAfter(IActionContext context, ActionForward forward) throws Exception
  {
    CountriesForm form = (CountriesForm) context.getForm();
    form.resetSelectedIds();
    return super.processAfter(context, forward);
  }

  public ActionForward mergeCountries(IActionContext context) throws Exception
  {
    CountriesForm form = (CountriesForm) context.getForm();
    GridResult<String> selectedIds = form.getCountriesSelectedIds();
    if (selectedIds.getRecords().size() == 0)
    {
      StrutsUtil.addError(context, "error.countries.only_one_line", null);
      return show(context);
    }
    if (selectedIds.getRecords().size() > 1)
    {
      StrutsUtil.addError(context, "error.countries.too_many_lines", null);
      return show(context);
    }
    if (selectedIds.getRecords().size() > 0)
    {
      String idFirst = selectedIds.getRecordList().get(0);
      String idSecond = form.getMergeTargetId().toString();

      CountryDAO.updateDependedDocs(context, new ForUpdateDependedDocuments(idFirst, idSecond));
    }
    return show(context);
  }

}