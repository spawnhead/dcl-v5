package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.ContractorsListForm;

import org.apache.struts.action.ActionForward;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class ContractorsListAction extends DBAction
{
  private static void logDebug(String message, String data) {
    try {
      java.io.FileWriter fw = new java.io.FileWriter("z:\\projects\\dcl_v3\\.cursor\\debug.log", true);
      fw.write("{\"runId\":\"pre-fix\",\"hypothesisId\":\"H6\",\"location\":\"ContractorsListAction.java:19\",\"message\":" + j(message) + ",\"data\":" + data + ",\"timestamp\":" + System.currentTimeMillis() + "}\n");
      fw.close();
    } catch (Exception ignore) {
    }
  }

  private static String j(String s) {
    if (s == null) return "null";
    return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }

  public ActionForward execute(IActionContext context) throws Exception {
    ContractorsListForm form = (ContractorsListForm) context.getForm();
    int attempt = 0;
    Exception lastError = null;
    while (attempt < 2) {
      attempt++;
      try {
        form.setList(DAOUtils.fillList(context, "select-contractors-filter", form, ContractorsListForm.Contractor.class, null, null));
        logDebug("contractors list ok", "{\"have_all\":" + form.isHave_all() + ",\"have_empty\":" + form.isHave_empty() + ",\"attempt\":" + attempt + ",\"count\":" + (form.getList() != null ? form.getList().size() : -1) + "}");
        lastError = null;
        break;
      } catch (Exception e) {
        lastError = e;
        logDebug("contractors list error", "{\"have_all\":" + form.isHave_all() + ",\"have_empty\":" + form.isHave_empty() + ",\"attempt\":" + attempt + ",\"error\":" + j(e.getMessage()) + "}");
        if (attempt < 2 && isResultSetClosed(e)) {
          continue;
        }
        throw e;
      }
    }
    if (lastError != null) {
      throw lastError;
    }
    if (form.getList() == null) {
      form.setList(new java.util.ArrayList());
    }
    if ( form.isHave_all() )
    {
      ContractorsListForm.Contractor contractor = new ContractorsListForm.Contractor();
      contractor.setCtr_id(StrutsUtil.getMessage(context, "list.all_id"));
      contractor.setCtr_name(StrutsUtil.getMessage(context, "list.all"));
      form.getList().add(0, contractor);
    }
    if ( form.isHave_empty() )
    {
      ContractorsListForm.Contractor contractor = new ContractorsListForm.Contractor();
      contractor.setCtr_id(StrutsUtil.getMessage(context, "list.empty_id"));
      contractor.setCtr_name(StrutsUtil.getMessage(context, "list.empty"));
      form.getList().add(0, contractor);
    }
    return context.getMapping().getInputForward();
  }

  private static boolean isResultSetClosed(Exception e) {
    String msg = e != null ? e.getMessage() : null;
    return msg != null && msg.toLowerCase().contains("result set is already closed");
  }
}
