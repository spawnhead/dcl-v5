package net.sam.dcl.controller.actions;

import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.processor.ActionProcessor;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Dima
 * Date: Sep 29, 2004
 * Time: 5:01:29 PM
 */
public class DBAction extends BaseAction
{
  public static final int rowsOnPage = Config.getNumber("page.record.count", 20);

  public void initActionContext(IActionContext context, ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, ActionProcessor actionProcessor) throws Exception
  {
    super.initActionContext(context, mapping, actionForm, request, response, actionProcessor);
    VDbConnection conn = VDbConnectionManager.getVDbConnection();
    context.setConnection(conn);
  }

  public void processFinally(IActionContext context)
  {
    super.processFinally(context);
    if (context.getConnection() != null) context.getConnection().close();
    context.setConnection(null);
  }
}
