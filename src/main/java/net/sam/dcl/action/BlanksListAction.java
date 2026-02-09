package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.form.BlanksListForm;
import net.sam.dcl.util.DAOUtils;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class BlanksListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    BlanksListForm form = (BlanksListForm) context.getForm();
    List<Blank> res = DAOUtils.fillList(context, "select-blanks", form, Blank.class, null, null);
    Map types = new LinkedHashMap();
    for (Blank blank : res)
    {
      types.put(blank.getBln_id(), blank.getBln_name());
    }
    return types;
  }
}