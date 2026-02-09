package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.UsersListForm;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class UsersListAction extends ListAction
{
  private static void logDebug(String message, String data) {
    try {
      java.io.FileWriter fw = new java.io.FileWriter("z:\\projects\\dcl_v3\\.cursor\\debug.log", true);
      fw.write("{\"runId\":\"pre-fix\",\"hypothesisId\":\"H6\",\"location\":\"UsersListAction.java:24\",\"message\":" + j(message) + ",\"data\":" + data + ",\"timestamp\":" + System.currentTimeMillis() + "}\n");
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
    UsersListForm form = (UsersListForm) context.getForm();
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
      VResultSet resultSet = DAOUtils.executeQuery(context, "select-users-filter", null);
      try {
        while (resultSet.next())
        {
          types.put(resultSet.getData("usr_id"), resultSet.getData("usr_name"));
          count++;
        }
        logDebug("users list ok", "{\"have_all\":" + form.isHave_all() + ",\"attempt\":" + attempt + ",\"count\":" + count + "}");
        return types;
      } catch (Exception e) {
        logDebug("users list error", "{\"have_all\":" + form.isHave_all() + ",\"attempt\":" + attempt + ",\"count\":" + count + ",\"error\":" + j(e.getMessage()) + "}");
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
