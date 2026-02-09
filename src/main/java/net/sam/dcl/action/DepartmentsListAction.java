package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.DepartmentsListForm;

import java.util.Map;
import java.util.LinkedHashMap;

public class DepartmentsListAction extends ListAction
{
  private static void logDebug(String message, String data) {
    try {
      java.io.FileWriter fw = new java.io.FileWriter("z:\\projects\\dcl_v3\\.cursor\\debug.log", true);
      fw.write("{\"runId\":\"pre-fix\",\"hypothesisId\":\"H6\",\"location\":\"DepartmentsListAction.java:18\",\"message\":" + j(message) + ",\"data\":" + data + ",\"timestamp\":" + System.currentTimeMillis() + "}\n");
      fw.close();
    } catch (Exception ignore) {
    }
  }

  private static String j(String s) {
    if (s == null) return "null";
    return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }

  public Object listExecute(IActionContext context) throws Exception
  {
    DepartmentsListForm form = (DepartmentsListForm) context.getForm();
    Map types = new LinkedHashMap();
    Map baseTypes = new LinkedHashMap();
    if ( form.isHave_all() )
    {
      baseTypes.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    int attempt = 0;
    while (attempt < 2) {
      attempt++;
      types.clear();
      types.putAll(baseTypes);
      int count = 0;
      VResultSet resultSet = DAOUtils.executeQuery(context, "select-departments", null);
      try {
        while (resultSet.next())
        {
          types.put(resultSet.getData("dep_id"), resultSet.getData("dep_name"));
          count++;
        }
        logDebug("departments list ok", "{\"have_all\":" + form.isHave_all() + ",\"attempt\":" + attempt + ",\"count\":" + count + "}");
        return types;
      } catch (Exception e) {
        logDebug("departments list error", "{\"have_all\":" + form.isHave_all() + ",\"attempt\":" + attempt + ",\"count\":" + count + ",\"error\":" + j(e.getMessage()) + "}");
        if (attempt < 2 && isResultSetClosed(e)) {
          continue;
        }
        throw e;
      }
    }
    return types;
  }

  private static boolean isResultSetClosed(Exception e) {
    String msg = e != null ? e.getMessage() : null;
    return msg != null && msg.toLowerCase().contains("result set is already closed");
  }
}
