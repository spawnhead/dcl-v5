package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IProcessAfter;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.StuffCategoriesForm;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.ForUpdateDependedDocuments;
import net.sam.dcl.dao.StuffCategoryDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class StuffCategoriesAction extends DBTransactionAction implements IDispatchable, IProcessAfter
{
  public ActionForward show(IActionContext context) throws Exception
  {
    final StuffCategoriesForm form = (StuffCategoriesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-stuff_categories", StuffCategoriesForm.StuffCategory.class, null, null);

    context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        StuffCategoriesForm.StuffCategory stuffCategory = (StuffCategoriesForm.StuffCategory) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !stuffCategory.isOccupied();
      }
    }
    );

    /***
     * снять чекбокс, если в справочнике "Нормативы и тарифы монтажа" для
     * отмеченной позиции есть заполненная таблица, не может никто
     */
    context.getRequest().setAttribute("checkBoxReadOnlyChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        StuffCategoriesForm.StuffCategory stuffCategory = (StuffCategoriesForm.StuffCategory) form.getGrid().getDataList().get(ctx.getTable().getRecordCounter() - 1);
        return stuffCategory.isOccupiedInMontage();
      }
    });
    return context.getMapping().getInputForward();
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "stuff_category-delete", null);
    return show(context);
  }

  public ActionForward mergeStuffCategories(IActionContext context) throws Exception
  {
    StuffCategoriesForm form = (StuffCategoriesForm) context.getForm();
    GridResult<String> selectedIds = form.getStuffCategoriesSelectedIds();
    if (selectedIds.getRecords().size() == 0)
    {
      StrutsUtil.addError(context, "error.stuf_categoryes.only_one_stuff", null);
      return show(context);
    }
    if (selectedIds.getRecords().size() > 1)
    {
      StrutsUtil.addError(context, "error.stuf_categoryes.too_many_stuffs", null);
      return show(context);
    }
    if (selectedIds.getRecords().size() > 0)
    {
      String idFirst = selectedIds.getRecordList().get(0);
      String idSecond = form.getMergeTargetId().toString();
      StuffCategory stfFirst = StuffCategoryDAO.load(context, idFirst);
      StuffCategory stfSecond = StuffCategoryDAO.load(context, idSecond);
      if ( !stfFirst.getName().equals(stfSecond.getName()) )
      {
        StrutsUtil.addError(context, "error.stuf_categoryes.different_names", null);
        return show(context);
      }

      StuffCategoryDAO.updateDependedDocs(context, new ForUpdateDependedDocuments(idFirst, idSecond));
    }
    return show(context);
  }

  public ActionForward processAfter(IActionContext context, ActionForward forward) throws Exception
  {
    StuffCategoriesForm form = (StuffCategoriesForm) context.getForm();
    form.resetSelectedIds();
    return super.processAfter(context, forward);
  }

  public ActionForward showInMontage(IActionContext context) throws Exception
  {
    StuffCategoriesForm form = (StuffCategoriesForm) context.getForm();
    StuffCategory stuffCategory = new StuffCategory();
    stuffCategory.setId(form.getStf_id());
    if ("1".equals(form.getShowInMontage()))
    {
      stuffCategory.setShowInMontage("");
    }
    else
    {
      stuffCategory.setShowInMontage("1");
    }
    StuffCategoryDAO.saveShowInMontage(context, stuffCategory);

    return show(context);
  }

}
