package net.sam.dcl.dao;

import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VParameter;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 21.05.2010
 * Time: 17:20:30
 * To change this template use File | Settings | File Templates.
 */
public class EveryDayTaskDAO
{
  protected static org.apache.commons.logging.Log log = LogFactory.getLog(EveryDayTaskDAO.class);

  public static void closeReservedInCommercialProposal() throws Exception
  {
    VDbConnection conn = null;
    try
    {
      log.info("Start CloseReservedInCommercialProposal task");

      conn = VDbConnectionManager.getVDbConnection();
      String sql = "alter trigger dcl_commercial_proposal_bu0 inactive";
      DAOUtils.update(conn, sql, null, new VParameter());

      sql = "execute procedure dcl_close_reserved_in_cpr";
      DAOUtils.update(conn, sql, null, new VParameter());

      sql = "alter trigger dcl_commercial_proposal_bu0 active";
      DAOUtils.update(conn, sql, null, new VParameter());

      log.info("Finish CloseReservedInCommercialProposal task");
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      if (conn != null) conn.close();
    }
  }

  public static void deleteUserMessagesIfStorePeriodExpired() {
    try {
      log.info("Start checkUserMessagesOnDelete task");
      DAOUtils.update(VDbConnectionManager.getVDbConnection(), "execute procedure checkUserMessagesOnDelete", null, new VParameter());
    } catch (Exception e) {
      log.info("Finish checkUserMessagesOnDelete task with error", e);
    }

    log.info("Finish checkUserMessagesOnDelete task");
  }
}
