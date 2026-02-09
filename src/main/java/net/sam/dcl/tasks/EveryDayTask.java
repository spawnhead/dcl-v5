package net.sam.dcl.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import net.sam.dcl.dao.EveryDayTaskDAO;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;

/**
 * User: dgena
 * Date: 18.05.2010
 * Time: 20:15:37
 */
public class EveryDayTask implements StatefulJob
{
  protected static Log log = LogFactory.getLog(EveryDayTask.class);

  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
  {
    log.info("Task started");

    try
    {
      EveryDayTaskDAO.closeReservedInCommercialProposal();
    }
    catch (Exception e)
    {
      log.error(e);
    }

    EveryDayTaskDAO.deleteUserMessagesIfStorePeriodExpired();

    log.info("Task finished");
  }
}
