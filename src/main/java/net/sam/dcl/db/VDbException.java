package net.sam.dcl.db;

import java.sql.SQLException;

/**
 *
 */
public class VDbException extends SQLException
{
  public long mDbPoolInstanceNumber;
  public short mModuleStatus = net.sam.dcl.db.VDbConnectionManager.MODULE_STATUS__UNDEFINED;
  public VDbExecutionContext executionContext = null;

  public VDbException(SQLException e, long dbPoolInstanceNumber)
  {

    super(e.getMessage(), e.getSQLState(), e.getErrorCode());
    super.setNextException(e.getNextException());

    mDbPoolInstanceNumber = dbPoolInstanceNumber;
  }

  public VDbException(SQLException e, long dbPoolInstanceNumber, VDbExecutionContext executionContext)
  {
    super(e.getMessage(), e.getSQLState(), e.getErrorCode());
    super.setNextException(e.getNextException());
    this.executionContext = executionContext;

    mDbPoolInstanceNumber = dbPoolInstanceNumber;
  }

  public VDbException(String message)
  {
    super(message);
    System.out.println("[" + message + "]");
  }

  /**
   *
   */
  public VDbException(String message, int vendorCode, short moduleStatus)
  {
    super(message, "", vendorCode);
    mModuleStatus = moduleStatus;
  }
}
