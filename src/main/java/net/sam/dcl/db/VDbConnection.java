/**
 * Title:        AM Project<p>
 * Description:  AM <p>
 * Copyright:    <p>
 * Company:      <p>
 * @author A.K.
 * @version 1.0
 */

package net.sam.dcl.db;

import net.sam.dcl.util.TypeConverter;
import net.sam.dcl.config.Config;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This class represents a virtual db connection for
 * AM system.
 */
public class VDbConnection
{
  //test
  // test
  //todo text
  // todo text
  /* test

    */
  /* todo */
  /*
    todo
  */

  protected RealDbConnection mConn;
  private boolean mBeginTransaction = false;
  private Hashtable mVResultSetStorage;
  private String mID;

  private long mCurrentVResultSetID;

  private static int stMaxAmountOfTriesGetConnection = 3;

  private long mOpeningTime;
  private boolean mUnlimitedTimeout = false;
  private static long stMaxTimeout = 30 * 60 * 1000; //30 minutes
  private boolean mStartClosing = false;
  private Integer mStartClosingMutex = new Integer(0);
  private short mConnType = VDbConnectionManager.BUILTIN_CONN_TYPE__DEFAULT;

  private String mConnectionUser;

  protected VDbConnection()
  {
    mOpeningTime = System.currentTimeMillis();
    Init();
  }

  /**
   * Inits an object.
   */
  private void Init()
  {
    mConn = null;
    mBeginTransaction = false;
    mVResultSetStorage = new Hashtable(10);
    mCurrentVResultSetID = 0;

    mConnectionUser = null;
  }

  /**
   * sets type of connection which will be used.
   */
  void setConnType(short connType)
  {
    mConnType = connType;
  }

  /**
   * gets a real db connection.
   */
  void getRealDbConnectionFromConnectionManager(boolean beginTransaction) throws VDbException
  {
    //x.1)
    resetRealDbConnection();

    //x.2) geting a real db connection.
    int countOfTries = 0;
    short connType;
    //x.3) switch   to the appropriate connection type.
    if (mConnType == VDbConnectionManager.BUILTIN_CONN_TYPE__DEFAULT)
    {
      if (beginTransaction == true)
      {
        connType = VDbConnectionManager.BUILTIN_CONN_TYPE__FOR_WRITE;
      }
      else
      {
        connType = VDbConnectionManager.BUILTIN_CONN_TYPE__FOR_READ;
      }
    }
    else
    {
      connType = mConnType;
    }

    while (true)
    {
      mConn = VDbConnectionManager.getRealDbConnection(connType, beginTransaction);

      if (mConn != null) break;

      countOfTries++;
      if (countOfTries == stMaxAmountOfTriesGetConnection)
      {
        throw new VDbException("VDbConnection > getRealDbConnectionFromConnectionManager >Can't get dbconnection " + connType);
      }
      else
      {
        try
        {
          Thread.currentThread().sleep(500);
        }
        catch (InterruptedException e)
        {
        }
      }
    }
  }

  /**
   * Begins a new transaction.
   */
  public synchronized void beginTransaction() throws VDbException
  {
    if (mStartClosing == true)
    {
      throw new VDbException("VDbConnection > beginTransaction > v.connection started closing");
    }
    if (mBeginTransaction == true)
    {
      throw new VDbException("VDbConnection > beginTransaction > The transaction is already began");
    }
    //x.1)
    getRealDbConnectionFromConnectionManager(true);

    //x.3)
    mBeginTransaction = true;
  }

  /**
   * The <code>execute</code> method executes a SQL statement and indicates the
   * form of the first result.
   *
   * @param sql any SQL statement
   * @return true if the next result is a VResultSet; false if it is
   *         an update count or there are no more results
   * @throws SQLException if a database access error occurs
   */
  public synchronized boolean execute(String sql) throws VDbException
  {
    return execute(mConnectionUser, sql);
  }

  public synchronized boolean execute(String user, String sql) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > execute > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(sql);
    java.sql.Statement stmt = mConn.createStatement();

    try
    {
      return mConn.execute(user, stmt, executionContext);
    }
    finally
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mConn.getDbPoolInstanceNumber());
      }
    }
  }

  /**
   * Executes a SQL statement that returns a VResultSet.
   *
   * @param sql typically this is a static SQL SELECT statement
   * @return a VResultSet that contains the data produced by the
   *         query; never null
   * @throws SQLException if a database access error occurs
   */

  public synchronized VResultSet executeQuery(String user, String sql) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > executeQuery > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(sql);
    java.sql.Statement stmt = mConn.createStatement();

    VResultSet vResultSet = null;
    ResultSet rset = null;

    //x.x.1)
    try
    {
      rset = mConn.executeQuery(stmt, executionContext);
    }
    catch (Exception e)
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException ex)
      {
        throw new VDbException(ex, mConn.getDbPoolInstanceNumber());
      }

      if (e instanceof VDbException)
      {
        throw (VDbException) e;
      }
      else
      {
        throw new VDbException(e.toString());
      }
    }
    //x.x.2)
    String vresultSetID = createVResultSetID();
    try
    {
      vResultSet = newVResultSet(vresultSetID, this, rset, stmt, mConn);
    }
    catch (Exception e)
    {
      try
      {
        rset.close();
      }
      catch (SQLException ex)
      {
        throw new VDbException(ex, mConn.getDbPoolInstanceNumber());
      }
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException ex)
      {
        throw new VDbException(ex, mConn.getDbPoolInstanceNumber());
      }

      if (e instanceof VDbException)
      {
        throw (VDbException) e;
      }
      else
      {
        throw new VDbException(e.toString());
      }
    }
    //x.x.3)
    try
    {
      mVResultSetStorage.put(vresultSetID, vResultSet);
    }
    catch (Exception e)
    {
      vResultSet.close();
      throw new VDbException(e.toString());
    }

    return vResultSet;
  }

  /**
   * Executes an SQL INSERT, UPDATE or DELETE statement.
   *
   * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
   *            statement that returns nothing
   * @return either the row count for INSERT, UPDATE or DELETE or 0
   *         for SQL statements that return nothing
   * @throws SQLException if a database access error occurs
   */
  public synchronized int executeUpdate(String sql) throws VDbException
  {
    return executeUpdate(mConnectionUser, sql);
  }

  public synchronized int executeUpdate(String user, String sql) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > executeUpdate > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(sql);
    java.sql.Statement stmt = mConn.createStatement();

    try
    {
      return mConn.executeUpdate(user, stmt, executionContext);
    }
    finally
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mConn.getDbPoolInstanceNumber());
      }
    }
  }

  /**
   * Executes an SQL INSERT, UPDATE or DELETE statement.
   *
   * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
   *            statement that returns nothing
   * @return either the row count for INSERT, UPDATE or DELETE or 0
   *         for SQL statements that return nothing
   * @throws SQLException if a database access error occurs or
   *                      the row count equals 0
   */
  public synchronized int executeUpdateEx(String sql) throws VDbException
  {
    return executeUpdateEx(mConnectionUser, sql);
  }

  public synchronized int executeUpdateEx(String user, String sql) throws VDbException
  {
    int ret = executeUpdate(user, sql);
    if (ret == 0) throw new VDbException("No rows have been updated, inserted or deleted");
    return ret;
  }

  /**
   * The <code>executeCall</code> method executes a callable statement and indicates the
   * form of the first result.
   *
   * @param sql
   * @return true if the next result is a VResultSet; false if it is
   *         an update count or there are no more results
   * @throws SQLException if a database access error occurs
   */
  public synchronized boolean executeCall(String sql) throws VDbException
  {
    return executeCall(mConnectionUser, sql);
  }

  public synchronized boolean executeCall(String user, String sql) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > executeCall > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(sql);
    java.sql.CallableStatement stmt = mConn.createCallableStatement(executionContext);

    try
    {
      return mConn.executeCall(user, stmt, executionContext);
    }
    //catch (SQLException e){ throw e;}
    finally
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mConn.getDbPoolInstanceNumber());
      }
    }
  }

  /**
   * The <code>execute</code> method executes a SQL statement and indicates the
   * form of the first result.
   *
   * @param sql any SQL statement
   * @return true if the next result is a VResultSet; false if it is
   *         an update count or there are no more results
   * @throws SQLException if a database access error occurs
   */
  public synchronized boolean execute(String sql, VParameter param) throws VDbException
  {
    return execute(mConnectionUser, sql, param);
  }

  public synchronized boolean executeP(String sql, VParameter vParameter) throws VDbException
  {
    return execute(mConnectionUser, sql, vParameter, false);
  }

  public synchronized boolean executeS(String sql, VParameter vParameter) throws VDbException
  {
    return execute(mConnectionUser, sql, vParameter, true);
  }

  public synchronized boolean execute(String user, String sql, VParameter vParameter) throws VDbException
  {
    return execute(user, sql, vParameter, Config.getBoolean("db.substituteParams", false));
  }

  protected synchronized boolean execute(String user, String sql, VParameter vParameter, boolean isSubstitute) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > execute > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(vParameter, sql);
    SQLStmtNormalizer.normalizeSQL(executionContext);
    if (isSubstitute)
    {
      substituteParams(executionContext);
    }

    java.sql.PreparedStatement stmt = mConn.createPreparedStatement(executionContext);
    try
    {
      bindParam(stmt, executionContext.getParam());
      return mConn.execute(user, stmt, executionContext);
    }
    //catch (SQLException e){ throw e;}
    finally
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mConn.getDbPoolInstanceNumber());
      }
    }
  }

  public synchronized VResultSet executeQueryS(String sql, VParameter vParameter) throws VDbException
  {
    return executeQuery(sql, vParameter, true);
  }

  public synchronized VResultSet executeQueryP(String sql, VParameter vParameter) throws VDbException
  {
    return executeQuery(sql, vParameter, false);
  }

  public synchronized VResultSet executeQuery(String sql, VParameter vParameter) throws VDbException
  {
    return executeQuery(sql, vParameter, Config.getBoolean("db.substituteParams", false));
  }

  /**
   * Executes a SQL statement that returns a VResultSet.
   *
   * @param sql typically this is a static SQL SELECT statement
   * @return a VResultSet that contains the data produced by the
   *         query; never null
   * @throws SQLException if a database access error occurs
   */

  protected synchronized VResultSet executeQuery(String sql, VParameter vParameter, boolean isSubstitute) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > executeQuery > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(vParameter, sql);
    SQLStmtNormalizer.normalizeSQL(executionContext);
    if (isSubstitute)
    {
      substituteParams(executionContext);
    }

    java.sql.PreparedStatement stmt = mConn.createPreparedStatement(executionContext);

    ResultSet rset;
    VResultSet vResultSet;

    //x.x.1)
    try
    {
      bindParam(stmt, executionContext.getParam());
      //bindParam(stmt  , param);
      rset = mConn.executeQuery(stmt, executionContext);
    }
    catch (Exception e)
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException ex)
      {
        throw new VDbException(ex, mConn.getDbPoolInstanceNumber());
      }

      if (e instanceof VDbException)
      {
        throw (VDbException) e;
      }
      else
      {
        throw new VDbException(e.toString());
      }
    }

    //x.x.2)
    String vresultSetID = createVResultSetID();
    try
    {
      vResultSet = newVResultSet(vresultSetID, this, rset, stmt, mConn);
    }
    catch (Exception e)
    {
      try
      {
        rset.close();
      }
      catch (SQLException ex)
      {
        throw new VDbException(ex, mConn.getDbPoolInstanceNumber());
      }
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException ex)
      {
        throw new VDbException(ex, mConn.getDbPoolInstanceNumber());
      }

      if (e instanceof VDbException)
      {
        throw (VDbException) e;
      }
      else
      {
        throw new VDbException(e.toString());
      }
    }
    //x.x.3)
    try
    {
      mVResultSetStorage.put(vresultSetID, vResultSet);
    }
    catch (Exception e)
    {
      if (vResultSet != null) vResultSet.close();

      throw new VDbException(e.toString());
    }

    return vResultSet;
  }

  /**
   * Executes an SQL INSERT, UPDATE or DELETE statement.
   *
   * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
   *            statement that returns nothing
   * @return either the row count for INSERT, UPDATE or DELETE or 0
   *         for SQL statements that return nothing
   * @throws SQLException if a database access error occurs
   */
  public synchronized int executeUpdate(String sql, VParameter param)
          throws VDbException
  {
    return executeUpdate(mConnectionUser, sql, param);
  }

  public synchronized int executeUpdateS(String sql, VParameter vParameter) throws VDbException
  {
    return executeUpdate(mConnectionUser, sql, vParameter, true);
  }

  public synchronized int executeUpdateP(String sql, VParameter vParameter) throws VDbException
  {
    return executeUpdate(mConnectionUser, sql, vParameter, false);
  }

  public synchronized int executeUpdate(String user, String sql, VParameter vParameter) throws VDbException
  {
    return executeUpdate(user, sql, vParameter, Config.getBoolean("db.substituteParams", false));
  }

  protected synchronized int executeUpdate(String user, String sql, VParameter vParameter, boolean isSubstitute) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > executeUpdate > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(vParameter, sql);
    SQLStmtNormalizer.normalizeSQL(executionContext);
    if (isSubstitute)
    {
      substituteParams(executionContext);
    }

    java.sql.PreparedStatement stmt = mConn.createPreparedStatement(executionContext);
    try
    {
      bindParam(stmt, executionContext.getParam());
      return mConn.executeUpdate(user, stmt, executionContext);
    }
    //catch (SQLException e){ throw e;}
    finally
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mConn.getDbPoolInstanceNumber());
      }
    }
  }

  /**
   * Executes an SQL INSERT, UPDATE or DELETE statement.
   *
   * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
   *            statement that returns nothing
   * @return either the row count for INSERT, UPDATE or DELETE or 0
   *         for SQL statements that return nothing
   * @throws SQLException if a database access error occurs or
   *                      the row count equals 0
   */
  public synchronized int executeUpdateEx(String sql, VParameter param) throws VDbException
  {
    return executeUpdateEx(mConnectionUser, sql, param);
  }

  public synchronized int executeUpdateEx(String user, String sql, VParameter param) throws VDbException
  {
    int ret = executeUpdate(user, sql, param);
    if (ret == 0) throw new VDbException("No rows have been updated, inserted or deleted");
    return ret;
  }

  /**
   * The <code>executeCall</code> method executes a callable statement and indicates the
   * form of the first result.
   *
   * @param sql
   * @return true if the next result is a VResultSet; false if it is
   *         an update count or there are no more results
   * @throws SQLException if a database access error occurs
   */
  public synchronized boolean executeCall(String sql, VParameter param) throws VDbException
  {
    return executeCall(mConnectionUser, sql, param);
  }

  public synchronized boolean executeCallS(String sql, VParameter vParameter) throws VDbException
  {
    return executeCall(mConnectionUser, sql, vParameter, true);
  }

  public synchronized boolean executeCallP(String sql, VParameter vParameter) throws VDbException
  {
    return executeCall(mConnectionUser, sql, vParameter, false);
  }

  public synchronized boolean executeCall(String user, String sql, VParameter vParameter) throws VDbException
  {
    return executeCall(user, sql, vParameter, Config.getBoolean("db.substituteParams", false));
  }

  protected synchronized boolean executeCall(String user, String sql, VParameter vParameter, boolean isSubstitute) throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > executeCall > v.connection started closing");
    if (mBeginTransaction == false)
    {
      getRealDbConnectionFromConnectionManager(false);
    }
    VDbExecutionContext executionContext = new VDbExecutionContext(vParameter, sql);
    SQLStmtNormalizer.normalizeSQL(executionContext);
    if (isSubstitute)
    {
      substituteParams(executionContext);
    }

    java.sql.CallableStatement stmt = mConn.createCallableStatement(executionContext);

    boolean fl;
    try
    {
      bindParam(stmt, executionContext.getParam());
      fl = mConn.executeCall(user, stmt, executionContext);
      getOutData(stmt, executionContext.getParam());
    }
    finally
    {
      try
      {
        mConn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mConn.getDbPoolInstanceNumber());
      }
    }

    return fl;
  }

  /**
   * Makes all changes made since the previous
   * commit/rollback permanent and releases any database locks
   * currently held by the VDbConnection.
   *
   * @throws SQLException if a database access error occurs
   */
  public synchronized void commit() throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > commit > v.connection started closing");
    if (mBeginTransaction == false)
      throw new VDbException("VDbConnection > commit > a transaction have not been started");

    mBeginTransaction = false;

    try
    {
      mConn.commit();
    }
    catch (SQLException e)
    {
      throw new VDbException(e, mConn.getDbPoolInstanceNumber());
    }
    finally
    {
      resetRealDbConnection();
    }
  }

  /**
   * Drops all changes made since the previous
   * commit/rollback and releases any database locks currently held
   * by this VDbConnection.
   *
   * @throws SQLException if a database access error occurs
   */
  public synchronized void rollback() throws VDbException
  {
    if (mStartClosing == true) throw new VDbException("VDbConnection > rollback > v.connection started closing");
    if (mBeginTransaction == false) return;
    //throw new VDbException("VDbConnection > rollback > a transaction have not been started");

    mBeginTransaction = false;
    try
    {
      mConn.rollback();
    }
    //catch(SQLException e) { throw e; }
    finally
    {
      resetRealDbConnection();
    }
  }

  /**
   * Resets the virtual db connection's state.
   */
  protected synchronized void reset()
  {
    //x.1) close all result sets

    Hashtable localVResultSetStorage = (Hashtable) mVResultSetStorage.clone();

    for (Enumeration e = localVResultSetStorage.elements(); e.hasMoreElements();)
    {
      VResultSet vrset = (VResultSet) e.nextElement();
      if (vrset != null) vrset.close();
    }
    mVResultSetStorage.clear();
    localVResultSetStorage.clear();

    //x.2) reset a real  db connection
    resetRealDbConnection();
  }

  /**
   *
   */
  void removeVResultSetFromStorage(String ID)
  {
    if (mVResultSetStorage != null) mVResultSetStorage.remove(ID);
  }

  /**
   * Resets the virtual db connection's state.
   */
  private void resetRealDbConnection()
  {
    //x.1) the real db connection
    if (mConn != null)
    {
      VDbConnectionManager.returnRealDbConnection(mConn);
      mConn = null;
    }
  }

  /**
   * closes the virtual db connection
   */
  public void close()
  {
    synchronized (mStartClosingMutex)
    {
      if (mStartClosing == false)
      {
        mStartClosing = true;
      }
      else
      {
        return;
      }
    }

    reset();

    VDbConnectionManager.removeObjectFromStorage(getID());
  }

  /**
   * Binds parameters
   */
  private void bindParam(PreparedStatement stmt, VParameter param) throws VDbException
  {
    VParameter.Element paramElements[] = param.getParamElements();

    for (int i = 1; i <= paramElements.length; i++)
    {
      bindParam(stmt, paramElements[i - 1], i);
    }
  }

  /**
   * Binds parameters
   */
  protected void bindParam(PreparedStatement stmt, VParameter.Element elem, int paramNum) throws VDbException
  {
    //x.1) register a parameter
    try
    {
      if (elem.mParamOut == true)
      {
        if (elem.mOutParamVal == null)
        {
          stmt.setNull(paramNum, elem.mParamType);
        }
        else
        {
          ((CallableStatement) stmt).registerOutParameter(paramNum, elem.mParamType);
        }
        return;
      }

      switch (elem.mParamType)
      {

        case Types.TINYINT:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setByte(paramNum, TypeConverter.toByte(elem.mParamVal));
          }
          break;
        }
        case Types.SMALLINT:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setShort(paramNum, TypeConverter.toShort(elem.mParamVal));
          }
          break;
        }
        case Types.INTEGER:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setInt(paramNum, TypeConverter.toInt(elem.mParamVal));
          }
          break;
        }
        case Types.BIGINT:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setLong(paramNum, TypeConverter.toLong(elem.mParamVal));
          }
          break;
        }
        case Types.REAL:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setFloat(paramNum, TypeConverter.toFloat(elem.mParamVal));
          }
          break;
        }
        case Types.DOUBLE:
        case Types.FLOAT:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setDouble(paramNum, TypeConverter.toDouble(elem.mParamVal));
          }
          break;
        }
        case Types.DECIMAL:
        case Types.NUMERIC:
        {

          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setBigDecimal(paramNum, TypeConverter.toBigDecimal(elem.mParamVal));
          }
          break;
        }
        case Types.BIT:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setBoolean(paramNum, TypeConverter.toBoolean(elem.mParamVal));
          }
          break;
        }
        case Types.DATE:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setDate(paramNum, TypeConverter.toDate(elem.mParamVal));
          }
          break;
        }
        case Types.TIME:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setTime(paramNum, TypeConverter.toTime(elem.mParamVal));
          }
          break;
        }
        case Types.TIMESTAMP:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setTimestamp(paramNum, TypeConverter.toTimestamp(elem.mParamVal));
          }
          break;
        }
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.LONGVARCHAR:
        {
          if (elem.mParamVal == null)
          {
            stmt.setNull(paramNum, elem.mParamType);
          }
          else
          {
            stmt.setString(paramNum, elem.mParamVal);
          }
          break;
        }
        default:
          throw new VDbException("VDbConnection > bindParam > Not supported type");
      }
    }
    catch (VDbException e)
    {
      throw e;
    }
    catch (SQLException e)
    {
      throw new VDbException(e, mConn.getDbPoolInstanceNumber());
    }
  }

  /**
   * Gets an out data
   */
  private void getOutData(CallableStatement stmt, VParameter param) throws VDbException
  {
    VParameter.Element paramElements[] = param.getParamElements();

    for (int i = 1; i <= paramElements.length; i++)
    {
      VParameter.Element elem = paramElements[i - 1];
      //x.1) register a parameter
      if (elem.mParamOut != true) continue;

      getOutData(i, stmt, elem);
    }
  }

  /**
   * Gets an out data
   */
  protected void getOutData(int i, CallableStatement stmt, VParameter.Element elem) throws VDbException
  {
    try
    {
      switch (elem.mParamType)
      {
        case Types.TINYINT:
        {
          byte val = stmt.getByte(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.SMALLINT:
        {
          short val = stmt.getShort(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.INTEGER:
        {
          int val = stmt.getInt(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.BIGINT:
        {
          long val = stmt.getLong(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.REAL:
        {
          float val = stmt.getFloat(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.DOUBLE:
        case Types.FLOAT:
        {
          double val = stmt.getDouble(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.DECIMAL:
        case Types.NUMERIC:
        {
          java.math.BigDecimal val = stmt.getBigDecimal(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.BIT:
        {
          boolean val = stmt.getBoolean(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.DATE:
        {
          Date val = stmt.getDate(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.TIME:
        {
          Time val = stmt.getTime(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.TIMESTAMP:
        {
          Timestamp val = stmt.getTimestamp(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = TypeConverter.toString(val);
          }
          break;
        }
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.LONGVARCHAR:
        {
          String val = stmt.getString(i);
          if (stmt.wasNull() == true)
          {
            ((StringValueHolder) elem.mOutParamVal).val = "";
          }
          else
          {
            ((StringValueHolder) elem.mOutParamVal).val = val;
          }
          break;
        }
        default:
          throw new VDbException("VDbConnection > getOutData > Not supported type");
      }
    }
    catch (VDbException e)
    {
      throw e;
    }
    catch (SQLException e)
    {
      throw new VDbException(e, mConn.getDbPoolInstanceNumber());
    }
  }

  /**
   * Creates VResultSetXXX object
   */
  protected VResultSet newVResultSet(String vresultSetID, VDbConnection vdbconn,
                                     ResultSet rset,
                                     Statement stmt,
                                     RealDbConnection conn
  ) throws VDbException
  {
    return new VResultSet(vresultSetID, vdbconn, rset, stmt, conn);
  }

  /**
   *
   */
  void setID(String ID)
  {
    mID = ID;
  }

  /**
   *
   */
  String getID()
  {
    return mID;
  }

  public void setUnlimitedTimeout()
  {
    mUnlimitedTimeout = true;
  }

  /**
   *
   */
  public boolean isUseful()
  {
    if (mUnlimitedTimeout == true) return true;

    if ((System.currentTimeMillis() - mOpeningTime) > stMaxTimeout)
    {
      return false;
    }
    else
    {
      return true;
    }
  }
  /**
   *
   */
  /**
   * Returns an unique ID of created ResultSet.
   */
  private final synchronized String createVResultSetID()
  {
    return Long.toHexString(++mCurrentVResultSetID);
  }

  protected static void substituteParams(VDbExecutionContext executionContext) throws VDbException
  {
    executionContext.setSql(substituteParams(executionContext.getSql(), executionContext.getParam()));
  }

  protected static String substituteParams(String sql, VParameter vParameter) throws VDbException
  {
    VParameter.Element[] elements = vParameter.getParamElements();
    vParameter.reset();

    StringBuffer res = new StringBuffer();
    int i = 0;
    int startIdx = 0;
    boolean inString = false;
    int[] args = new int[1];
    args[0] = 0;
    for (; i < sql.length(); i++)
    {
      if (sql.charAt(i) == '\'')
      {
        if (!inString)
        {
          res.append(substituteParam(sql.substring(startIdx, i), vParameter, elements, args));
          startIdx = i;
        }
        else
        {
          res.append(sql.substring(startIdx, i + 1));
          startIdx = i + 1;
        }

        inString = !inString;
      }
    }
    if (inString)
    {
      res.append(sql.substring(startIdx, sql.length()));
    }
    else
    {
      res.append(substituteParam(sql.substring(startIdx, sql.length()), vParameter, elements, args));
    }
    return res.toString();
  }

  protected static String substituteParam(String sql, VParameter param, VParameter.Element[] elements, int[] args) throws VDbException
  {

    VParameter.Element element = null;
    StringBuffer ret = new StringBuffer();
    int idx = sql.indexOf("?");
    int prevIdx = 0;
    while (idx != -1)
    {
      ret.append(sql.substring(prevIdx, idx));
      prevIdx = idx + 1;
      if (args[0] == elements.length)
      {
        throw new VDbException(VDbConnection.class + " -> " + "substituteParams()" + " -> " + "bad count of parameter.");
      }
      element = elements[args[0]];
      if (element.mParamOut)
      {
        ret.append('?');
        param.addOut((StringValueHolder) element.mOutParamVal, element.mParamType);
      }
      else
      {
        if (element.mParamVal == null)
        {
          // parameters with value null not substituted(for FireBird)
          ret.append('?');
          param.add(element.mParamVal, element.mParamType);
        }
        else
        {
          ret.append(convertParam(element));
        }
      }
      args[0]++;
      idx = sql.indexOf('?', prevIdx);
    }
    ret.append(sql.substring(prevIdx));
    return ret.toString();
  }

  static protected String quoteString(String in)
  {
    return in.replaceAll("\'", "\'\'");
  }

  static protected String convertParam(VParameter.Element elem) throws VDbException
  {
    if (elem.mParamVal == null) return "null";

    switch (elem.mParamType)
    {
      case Types.TINYINT:
      case Types.SMALLINT:
      case Types.INTEGER:
      case Types.BIGINT:
      case Types.REAL:
      case Types.DOUBLE:
      case Types.FLOAT:
      case Types.DECIMAL:
      case Types.NUMERIC:
      {
        return String.valueOf(elem.mParamVal);
      }
      case Types.BIT:
      {
        if ("true".equalsIgnoreCase(elem.mParamVal))
        {
          return "1";
        }
        else
        {
          return "0";
        }
      }
      case Types.DATE:
      {
        return String.valueOf("TO_DATE('" + quoteString(elem.mParamVal) + "','YYYY-MM-DD')");
      }
      case Types.TIME:
      {
        return String.valueOf("TO_DATE('" + quoteString(elem.mParamVal) + "','HH24:MI:SS')");
      }
      case Types.TIMESTAMP:
      {
        return String.valueOf("TO_TIMESTAMP('" + quoteString(elem.mParamVal) + "','YYYY-MM-DD HH24:MI:SS.FF9')");
      }
      case Types.CHAR:
      case Types.VARCHAR:
      case Types.LONGVARCHAR:
      {
        return "'" + quoteString(elem.mParamVal) + "'";
      }
      default:
        throw new VDbException("VDbConnection > convertParam > Not supported type");
    }
  }

  public boolean isTransactionStarted()
  {
    return mBeginTransaction;
  }

  public void setConnectionUser(String user)
  {
    mConnectionUser = user;
  }
}
