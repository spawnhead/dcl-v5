package net.sam.dcl.db;

import net.sam.dcl.config.Config;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * This class shares restricted amount of real connectoins to a database
 * between many clients.
 */
public class VDbConnectionManager
{
  static final public short BUILTIN_CONN_TYPE__DEFAULT = -1;
  static final public short BUILTIN_CONN_TYPE__FOR_READ = 0;
  static final public short BUILTIN_CONN_TYPE__FOR_WRITE = 1;
  static final public short BUILTIN_CONN_TYPE__FOR_REPORT = 2;
  static final public short BUILTIN_CONN_TYPE__ADDITIONAL1 = 3;
  static final public short BUILTIN_CONN_TYPE__AMOUNT = 4;
  //one connection for additional purpose
  static public int MAXCONN_FOR_BUILTIN_CONN_TYPE__ADDITIONAL1 = 1;

  public static final short DRIVER_TYPE__UNDEFINED = 0;
  public static final short DRIVER_TYPE__ORACLE_OCI = 1;
  public static final short DRIVER_TYPE__ORACLE_THIN = 2;

  //for compatibility with previous version
  public static final short ORACLE_OCI = DRIVER_TYPE__ORACLE_OCI;
  public static final short ORACLE_THIN = DRIVER_TYPE__ORACLE_THIN;

  protected static short mDriverType = DRIVER_TYPE__UNDEFINED;

  public static final String USERS__DEFAULT_SYS_USER = "def_sys_user";


  private static String mConnectionUrl = null;
  private static String mUser = null;
  private static String mPassword = null;

  private static boolean mDbInitialized = false;
  private static boolean mCompleteInitialized = false;

  private static long mDbPoolInstanceNumber = 0;


  private static int mMaxAmountConnArr[] = null;
  private static int mCurrAmountConnArr[] = null;

  // for lock requet create
  private static boolean mLockRequestCreate[] = null;
  private static Integer mLockRequestCreateMutex[] = null;

  //for sql trace
  private static boolean mSqlTraceSet[] = null;
  //for connection tuning
  private static Vector mSessionTuningSQLStmtList = null;
  private static Vector mConnTypeSessionTuningSQLStmtList[] = new Vector[BUILTIN_CONN_TYPE__AMOUNT];

  private static Object mConnPool[] = null;

  private static String mVDbConnectionClassName = null;
  private static String mVDbMetaDataClassName = null;
  //private static        String  mLinguisticDefinition   = null;

  private static long mCurrentObjectID = 0;
  private static long mTimeOutWhenIOException = 1000;

  private static VDbConnectionStorage
          mVConnStorage = new VDbConnectionStorage();

  //module status constants
  static public final short MODULE_STATUS__UNDEFINED = 0;
  static public final short MODULE_STATUS__NOTINIT = 1;
  static public final short MODULE_STATUS__INIT = 2;
  static public final short MODULE_STATUS__NOTDBCONNECTION = 3;
  //this module status save the status of the entire VDB module.
  static private short mModuleStatus = MODULE_STATUS__NOTINIT;


  public static boolean traceEnabled = true;

  /**
   * Inits oracle driver.
   * if the oracleDriverType == ORACLE_OCI  then connectionString=<database name>
   * if the oracleDriverType == ORACLE_THIN then connectionString=<host>:<port>:<sid>
   */
  public static synchronized void initDbOracle(short oracleDriverType, String connectionString,
                                               String user, String password)
          throws VDbException
  {

    mDbInitialized = false;
    mCompleteInitialized = false;
    setModuleStatus(MODULE_STATUS__NOTINIT);

    //x.1)
    closeAllRealDbConnection();

    try
    {
      //x.2) Load the Oracle JDBC driver
      DriverManager.registerDriver((java.sql.Driver) (Class.forName("oracle.jdbc.driver.OracleDriver").newInstance()));
    }
    catch (Exception e)
    {
      throw new VDbException(e.getClass() + " " + e.getMessage());
    }

    mUser = user;
    mPassword = password;

    if (oracleDriverType == DRIVER_TYPE__ORACLE_OCI)
    {
      mConnectionUrl = "jdbc:oracle:oci8:@" + connectionString;
    }
    else if (oracleDriverType == DRIVER_TYPE__ORACLE_THIN)
    {
      mConnectionUrl = "jdbc:oracle:thin:@" + connectionString;
    }
    else
    {
      throw new VDbException("VDbConnectionManager > initDbOracle > bad oracle driver type");
    }

    mDriverType = oracleDriverType;

    mVDbConnectionClassName = "net.sam.dcl.db.VDbConnectionOracle";
    mVDbMetaDataClassName = "net.sam.dcl.db.VDbMetaDataOracle";

    mDbInitialized = true;
  }

  /**
   * Inits Microsoft SQLServer driver.
   *
   * @param connectionString String for connection SQL Server.
   * @param user             Name of user.
   * @param password         Password for connection.
   * @throws VDbException If class not found.
   */
  public static synchronized void initDbSQLServer(String connectionString,
                                                  String user,
                                                  String password)
          throws VDbException
  {
    mDbInitialized = false;
    mCompleteInitialized = false;
    setModuleStatus(MODULE_STATUS__NOTINIT);

    // Close all real connection.
    closeAllRealDbConnection();
    String driverClass = "sun.jdbc.odbc.JdbcOdbcDriver";

    try
    {
      // Load SQLServer driver.
      DriverManager.registerDriver((java.sql.Driver) (Class.forName(driverClass).newInstance()));
    }
    catch (Exception e)
    {
      throw new VDbException(e.getClass() + " " + e.getMessage());
    }

    mVDbConnectionClassName = "de.xcitec.c4s.db.VDbConnectionSQLServer";
    mConnectionUrl = "jdbc:odbc:" + connectionString;
    mUser = user;
    mPassword = password;
    mDbInitialized = true;
  }

  /**
   * Inits any driver.
   */
  public static synchronized void initAnyDriver(String driverClassName, String VDbConnectionClassName,
                                                String connectionUrl,
                                                String user, String password)
          throws VDbException
  {
    mDbInitialized = false;
    mCompleteInitialized = false;
    setModuleStatus(MODULE_STATUS__NOTINIT);

    //x.1)
    closeAllRealDbConnection();

    //x.2) Load the Oracle JDBC driver
    try
    {
      java.sql.Driver driver = (java.sql.Driver) (Class.forName(driverClassName).newInstance());
      DriverManager.registerDriver(driver);
      // Диагностика: логируем версию загруженного драйвера
      String driverVersion = driver.getClass().getPackage() != null ? 
          driver.getClass().getPackage().getImplementationVersion() : "unknown";
      System.out.println("[VDbConnectionManager] Loaded driver: " + driverClassName);
      System.out.println("[VDbConnectionManager] Driver version: " + driverVersion);
      System.out.println("[VDbConnectionManager] Driver location: " + 
          driver.getClass().getProtectionDomain().getCodeSource().getLocation());
      
    }
    catch (Exception e)
    {
      throw new VDbException(e.getClass() + " " + e.getMessage());
    }

    mUser = user;
    mPassword = password;

    mConnectionUrl = connectionUrl;

    mVDbConnectionClassName = VDbConnectionClassName;
    mDbInitialized = true;
  }

  /**
   * The method inits the pool of connections
   *
   * @param numInitialConnR
   * @param numMaxConnR       a max number of connections for reading.
   * @param numInitialConnW
   * @param numMaxConnW       a max number of connections for writing.
   * @param numInitialConnRep
   * @param numMaxConnRep     a max number of connections for reports and
   *                          other long operations .
   */
  public static synchronized void initDbPool(int numInitialConnR, int numMaxConnR,
                                             int numInitialConnW, int numMaxConnW,
                                             int numInitialConnRep, int numMaxConnRep) throws VDbException
  {

    //x.1) check the db initialization
    if (mDbInitialized == false)
      throw new VDbException("VDbConnectionManager > initDbPool > Database has not been initialized ");

    //x.1.1)
    closeAllRealDbConnection();

    mCompleteInitialized = false;

    mDbPoolInstanceNumber++;

    //x.2) check
    if (numInitialConnR < 0) numInitialConnR = 0;
    if (numMaxConnR < 0) numMaxConnR = 0;
    if (numInitialConnW < 0) numInitialConnW = 0;
    if (numMaxConnW < 0) numMaxConnW = 0;
    if (numInitialConnRep < 0) numInitialConnRep = 0;
    if (numMaxConnRep < 0) numMaxConnRep = 0;

    if (numInitialConnR > numMaxConnR) numInitialConnR = numMaxConnR;
    if (numInitialConnW > numMaxConnW) numInitialConnW = numMaxConnW;
    if (numInitialConnRep > numMaxConnRep) numInitialConnRep = numMaxConnRep;

    //x.3)
    mMaxAmountConnArr = new int[BUILTIN_CONN_TYPE__AMOUNT];

    mMaxAmountConnArr[BUILTIN_CONN_TYPE__FOR_READ] = numMaxConnR;
    mMaxAmountConnArr[BUILTIN_CONN_TYPE__FOR_WRITE] = numMaxConnW;
    mMaxAmountConnArr[BUILTIN_CONN_TYPE__FOR_REPORT] = numMaxConnRep;
    mMaxAmountConnArr[BUILTIN_CONN_TYPE__ADDITIONAL1] = MAXCONN_FOR_BUILTIN_CONN_TYPE__ADDITIONAL1;
    //x.4)

    mCurrAmountConnArr = new int[BUILTIN_CONN_TYPE__AMOUNT];

    //x.5)
    mConnPool = new Object[BUILTIN_CONN_TYPE__AMOUNT];

    mLockRequestCreate = new boolean[BUILTIN_CONN_TYPE__AMOUNT];
    mLockRequestCreateMutex = new Integer[BUILTIN_CONN_TYPE__AMOUNT];

    mSqlTraceSet = new boolean[BUILTIN_CONN_TYPE__AMOUNT];

    for (int i = 0; i < BUILTIN_CONN_TYPE__AMOUNT; i++)
    {
      mConnPool[i] = new net.sam.dcl.db.RealDbConnection[mMaxAmountConnArr[i]];
      mLockRequestCreate[i] = false;
      mLockRequestCreateMutex[i] = new Integer(0);

      mSqlTraceSet[i] = false;
    }

    //x.6) create initials connects
    for (int i = 0; i < numInitialConnR; i++)
    {
      createRealDbConnection(BUILTIN_CONN_TYPE__FOR_READ, false);
    }
    //x.7) create initial connects
    for (int i = 0; i < numInitialConnW; i++)
    {
      createRealDbConnection(BUILTIN_CONN_TYPE__FOR_WRITE, false);
    }
    //x.8) create initial connects
    for (int i = 0; i < numInitialConnRep; i++)
    {
      createRealDbConnection(BUILTIN_CONN_TYPE__FOR_REPORT, false);
    }


    mCompleteInitialized = true;
    setModuleStatus(MODULE_STATUS__INIT);
  }

  /**
   *Changes a linguistic sort sequence
   */
  /*
      public static synchronized void initDbSort(String linguisticDefinition ) throws VDbException
      {
         //x.1) Try intit sort all connection that had been opened.
         if( mCompleteInitialized == false ) throw new VDbException("VDbConnectionManager > initDbSort > not complete initialization");

         mLinguisticDefinition  =   linguisticDefinition ;

         for(short connType = 0  ;
                   connType < BUILTIN_CONN_TYPE__AMOUNT ; connType++ )
         {
           RealDbConnection connPool[] = (RealDbConnection[])(mConnPool[connType])  ;

           if( connPool == null )  continue ;

           synchronized( connPool)
           {
             for(int i =0 ; i<connPool.length ; i++)
             {
               if( connPool[i] != null ) { setDbSort(connPool[i]); }
             }
           }
         }
      }
      */
  /**
   * Tune connections executing SQL statemens
   */
  public static synchronized void executeSessionTuning(Vector sessionTuningSQLStmtList) throws VDbException
  {
    //x.1) Trying tune all connection that had been opened.
    if (mCompleteInitialized == false)
    {
      throw new VDbException("VDbConnectionManager > executeConnectionTuning > not complete initialization",
              0, mModuleStatus);
    }

    mSessionTuningSQLStmtList = sessionTuningSQLStmtList;

    for (short connType = 0;
         connType < BUILTIN_CONN_TYPE__AMOUNT; connType++)
    {
      RealDbConnection connPool[] = (RealDbConnection[]) (mConnPool[connType]);

      if (connPool == null) continue;

      synchronized (connPool)
      {
        for (int i = 0; i < connPool.length; i++)
        {
          if (connPool[i] != null)
          {
            executeSessionTuning(connPool[i],
                    mSessionTuningSQLStmtList);
          }
        }
      }
    }
  }

  /**
   * Tune connections executing SQL statemens for especial connection type.
   */
  public static synchronized void executeSessionTuning(short connType,
                                                       Vector sessionTuningSQLStmtList)
          throws VDbException
  {
    //x.1) Trying tune all connection that had been opened.
    if (mCompleteInitialized == false)
    {
      throw new VDbException("VDbConnectionManager > executeConnectionTuning > not complete initialization",
              0, mModuleStatus);
    }

    mConnTypeSessionTuningSQLStmtList[connType] = (Vector) sessionTuningSQLStmtList.clone();

    RealDbConnection connPool[] = (RealDbConnection[]) (mConnPool[connType]);

    if (connPool == null) return;

    synchronized (connPool)
    {
      for (int i = 0; i < connPool.length; i++)
      {
        if (connPool[i] != null)
        {
          executeSessionTuning(connPool[i],
                  mConnTypeSessionTuningSQLStmtList[connType]);
        }
      }
    }
  }

  /**
   * Set sql trace
   *
   * @param connType BUILTIN_CONN_TYPE__FOR_READ or BUILTIN_CONN_TYPE__FOR_WRITE
   * @param
   */
  public static synchronized void setSqlTrace(short connType, boolean set) throws VDbException
  {
    //x.1) Try intit sql trace all connection that had been opened.
    if (mCompleteInitialized == false)
    {
      throw new VDbException("VDbConnectionManager > setSqlTrace > not complete initialization",
              0, mModuleStatus);
    }

    mSqlTraceSet[connType] = set;

    RealDbConnection connPool[] = (RealDbConnection[]) (mConnPool[connType]);

    if (connPool == null) return;

    synchronized (connPool)
    {
      for (int i = 0; i < connPool.length; i++)
      {
        if (connPool[i] != null)
        {
          setSqlTrace(connPool[i], set);
        }
      }
    }
  }

  /**
   * Set sql trace for first opened connection
   */
  private static synchronized void setSqlTraceForFirstOpenedConn(short connType, RealDbConnection conn) throws VDbException
  {
    //x.1) Try intit sql trace for first opened  connection
    boolean set = mSqlTraceSet[connType];

    if (conn != null)
    {
      setSqlTrace(conn, set);
    }
  }

  /**
   * Gets the real data base connection from pool of connections.
   */

  static RealDbConnection getRealDbConnection(short connType) throws VDbException
  {
    boolean beginTransaction;

    if (connType == BUILTIN_CONN_TYPE__FOR_WRITE)
    {
      beginTransaction = true;
    }
    else
    {
      beginTransaction = false;
    }

    return getRealDbConnection(connType, beginTransaction);
  }

  /**
   * Gets the real data base connection from pool of connections.
   */
  static RealDbConnection getRealDbConnection(short connType, boolean beginTransaction) throws VDbException
  {

    // RealDbConnection conn        = null ;
    if (mCompleteInitialized == false)
    {
      throw new VDbException("VDbConnectionManager > getRealConnection > not complete initialization",
              0, mModuleStatus);
    }

    RealDbConnection connPool[] = (RealDbConnection[]) (mConnPool[connType]);

    int minIndex = 0;


    synchronized (connPool)
    {

      if (mCurrAmountConnArr[connType] == 0)
      {
        if (mMaxAmountConnArr[connType] != 0)
        {
          if (lockRequestCreate(connType) == true)
          {
            ;
          }
          else
          {
            return null;
          }
        }
        else
        {
          return null;
        }
      }
      else
      {
        boolean minBusy = connPool[minIndex].isBusy();
        int minUseCount = connPool[minIndex].getUseCount();
        boolean minBeginTransaction = connPool[minIndex].isTransaction();

        //x.1)
        for (int i = 1; i < mCurrAmountConnArr[connType]; i++)
        {

          boolean currBusy = connPool[i].isBusy();
          int currUseCount = connPool[i].getUseCount();
          boolean currBeginTransaction = connPool[i].isTransaction();

          if (currBeginTransaction == true) continue;


          if ((currBusy == false && minBusy == true))
          {
            minIndex = i;
            minBusy = currBusy;
            minUseCount = currUseCount;
            minBeginTransaction = currBeginTransaction;
          }
          else if (currBusy == minBusy)
          {
            if (currUseCount < minUseCount)
            {
              minIndex = i;
              minBusy = currBusy;
              minUseCount = currUseCount;
              minBeginTransaction = currBeginTransaction;
            }
          }
        }

        //x.2.1) if need return connection for write
        if (connType == BUILTIN_CONN_TYPE__FOR_WRITE)
        {
          /*                                                   //>=1
                         if(  ((minBeginTransaction == true) || (minUseCount >= 2))&&
                               (mCurrAmountConnArr[connType] < mMaxAmountConnArr[connType] ) )
                         {

                             if(lockRequestCreate(connType) == true );
                             else  if( minBeginTransaction == true ) return null;
                                   else
                                   {
                                     //Set begin of transaction
                                     connPool[minIndex].beginTransaction();
                                     connPool[minIndex].incUseCount();
                                     return connPool[minIndex];
                                   }
                         }
                         else  if( minBeginTransaction == true ) return null;
                               else
                                 {
                                   //Set begin of transaction
                                   connPool[minIndex].beginTransaction();
                                   connPool[minIndex].incUseCount();
                                   return connPool[minIndex];
                                 }
                         */
          if ((minBeginTransaction == true) || (minUseCount >= 1))
          {
            if (mCurrAmountConnArr[connType] < mMaxAmountConnArr[connType])
            {
              if (lockRequestCreate(connType) == true)
              {
                ;
              }
              else
              {
                return null;
              }
            }
            else
            {
              return null;
            }
          }
          else
          {
            //Set begin of transaction
            connPool[minIndex].beginTransaction();
            connPool[minIndex].incUseCount();
            return connPool[minIndex];
          }
        }
        //x.2.2) if need return connection for read
        else if (connType == BUILTIN_CONN_TYPE__FOR_READ)
        {

          if (((minBusy == true) || (minUseCount >= 2)) &&
                  (mCurrAmountConnArr[connType] < mMaxAmountConnArr[connType]))
          {

            if (lockRequestCreate(connType) == true)
            {
              ;
            }
            else
            {
              connPool[minIndex].incUseCount();
              return connPool[minIndex];
            }
          }
          else
          {
            connPool[minIndex].incUseCount();  /*System.out.println(min_index);*/
            return connPool[minIndex];
          }
        }
        //x.2.3) if need return connection for another purpose. FOR_REPORT or ADDITIONAL1
        else if (connType == BUILTIN_CONN_TYPE__FOR_REPORT ||
                connType == BUILTIN_CONN_TYPE__ADDITIONAL1)
        {
          //x.2.3.1) if we need exlusive connection for transaction.
          if (beginTransaction == true)
          {
            if ((minBeginTransaction == true) || (minUseCount >= 1))
            {
              if (mCurrAmountConnArr[connType] < mMaxAmountConnArr[connType])
              {
                if (lockRequestCreate(connType) == true)
                {
                  ;
                }
                else
                {
                  return null;
                }
              }
              else
              {
                return null;
              }
            }
            else
            {
              //Set begin of transaction
              connPool[minIndex].beginTransaction();
              connPool[minIndex].incUseCount();
              connPool[minIndex].setAutoCommit(false);
              return connPool[minIndex];
            }
          }
          //x.2.3.2) if we need a usual connection for reading data.
          else
          {
            if ((minBeginTransaction == true) || (minBusy == true) || (minUseCount >= 2))
            {
              if (mCurrAmountConnArr[connType] < mMaxAmountConnArr[connType])
              {
                if (lockRequestCreate(connType) == true)
                {
                  ;
                }
                else
                {
                  if (minBeginTransaction == true)
                  {
                    return null;
                  }
                  else
                  {
                    connPool[minIndex].incUseCount();
                    connPool[minIndex].setAutoCommit(true);
                    return connPool[minIndex];
                  }
                }
              }
              else
              {
                if (minBeginTransaction == true)
                {
                  return null;
                }
                else
                {
                  connPool[minIndex].incUseCount();
                  connPool[minIndex].setAutoCommit(true);
                  return connPool[minIndex];
                }
              }
            }
            else
            {
              connPool[minIndex].incUseCount();
              connPool[minIndex].setAutoCommit(true);
              return connPool[minIndex];
            }
          }

        }
      }
    }

    //x.2) create real connection
    /*
          boolean beginTransaction ;

          if( connType == BUILTIN_CONN_TYPE__FOR_WRITE ) beginTransaction = true ;
          else                                           beginTransaction = false;
          */
    RealDbConnection conn = null;
    try
    {
      conn = createRealDbConnection(connType, beginTransaction);
      conn.incUseCount();
    }
    finally
    {
      unLockRequestCreate(connType);
    }

    return conn;
  }

  /**
   * Gets data base connection from pool of the real connections.
   */
  private static synchronized RealDbConnection createRealDbConnection
          (short connType, boolean beginTransaction)
          throws VDbException
  {

    RealDbConnection conn = null;
    //x.1) create connection
    RealDbConnection connPool[] = (RealDbConnection[]) (mConnPool[connType]);

    switch (connType)
    {
      case BUILTIN_CONN_TYPE__FOR_READ:
      {

        conn = new RealDbConnection(mConnectionUrl, mUser, mPassword,
                RealDbConnection.RUN_MODE__AUTO_COMMIT,
                mDbPoolInstanceNumber);
        break;
      }
      case BUILTIN_CONN_TYPE__FOR_WRITE:
      {
        conn = new RealDbConnection(mConnectionUrl, mUser, mPassword,
                (short) 0,
                mDbPoolInstanceNumber);
        break;
      }
      case BUILTIN_CONN_TYPE__FOR_REPORT:
      case BUILTIN_CONN_TYPE__ADDITIONAL1:
      {
        short connRunMode = beginTransaction == true ? (short) 0 : RealDbConnection.RUN_MODE__AUTO_COMMIT;

        conn = new RealDbConnection(mConnectionUrl, mUser, mPassword,
                connRunMode,
                mDbPoolInstanceNumber);
        break;
      }
      default:
        throw new VDbException("RealDbConnectionManager > createRealDbConnectino > Not supported connection type");
    }

    //x.2) Add connection to the pool of connections
    synchronized (connPool)
    {
      if (beginTransaction == true) conn.beginTransaction();

      connPool[mCurrAmountConnArr[connType]] = conn;
      mCurrAmountConnArr[connType] = mCurrAmountConnArr[connType] + 1;
    }

    //x.3)tune connection
    executeSessionTuning(conn, mSessionTuningSQLStmtList);
    executeSessionTuning(conn, mConnTypeSessionTuningSQLStmtList[connType]);
    //set db sql trace
    setSqlTraceForFirstOpenedConn(connType, conn);

    //Tracer.action_trace (Tracer.APPSERVER_ACTION__CREATE_DBCONNECTION ,
    //                     DbConnectionManager.class             ,
    //                     "Create_DbConnection"                 ,
    //                     "connection created"                  );

    return conn;
  }

  /**
   * Creates a new virtual db connection
   */
  public static VDbConnection getVDbConnection() throws VDbException
  {
    return getVDbConnection(BUILTIN_CONN_TYPE__DEFAULT);
  }

  /**
   * Creates a new virtual db connection
   */
  public static VDbConnection getVDbConnection(short connType) throws VDbException
  {
    VDbConnection vconn = null;

    if (mVDbConnectionClassName == null)
    {
      vconn = new VDbConnection();
    }
    else
    {
      try
      {
        vconn = (VDbConnection) Class.forName(mVDbConnectionClassName).newInstance();
      }
      catch (Exception e)
      {
        throw new VDbException(e.getClass() + " " + e.getMessage());
      }
    }
    vconn.setID(createObjectID());
    //x.)
    vconn.setConnType(connType);
    //x.) put virtual connection to the storage
    mVConnStorage.put(vconn.getID(), vconn);
    return vconn;
  }

  /**
   * Closes all virtual database connections and cleaning the pool.
   */
  public static synchronized void closeAllRealDbConnection()
  {
    //x.1) Try close all connection that had been opened.
    if (mConnPool == null) return;


    for (short connType = 0;
         connType < BUILTIN_CONN_TYPE__AMOUNT; connType++)
    {
      RealDbConnection connPool[] = (RealDbConnection[]) (mConnPool[connType]);

      if (connPool == null) continue;

      synchronized (connPool)
      {
        for (int i = 0; i < connPool.length; i++)
        {
          try
          {
            if (connPool[i] != null) connPool[i].close();
          }
          catch (VDbException e)
          {
            //Tracer.action_trace (Tracer.EXCEPTION    ,
            //              DbConnectionManager.class  ,
            //              "Close_all_DbConnection"   ,
            //              e.getMessage()              );
          }
          finally
          {
            connPool[i] = null;
          }
        }
        mCurrAmountConnArr[connType] = 0;
      }
    }
    /*DG for avoid the memory leak*/
    mVConnStorage.clear();
    mVConnStorage = null;
  }

  /**
   * Returns the specified connection to the pool.
   */
  static void returnRealDbConnection(RealDbConnection conn)
  {
    if (conn != null)
    {
      if (conn.isTransaction())
      {
        try
        {
          conn.rollback();
        }
        catch (VDbException e)
        {
        }
        //conn.endTransaction ();
      }
      conn.decUseCount();
    }
  }

  /**
   * Locks a request for creating a new database connections.
   */
  private static boolean lockRequestCreate(short connType)
  {
    synchronized (mLockRequestCreateMutex[connType])
    {
      if (mLockRequestCreate[connType] == true)
      {
        return false;
      }
      else
      {
        mLockRequestCreate[connType] = true;
        return true;
      }
    }
  }

  /**
   * UnLocks a request for creating a new database connections.
   */
  private static void unLockRequestCreate(short connType)
  {
    synchronized (mLockRequestCreateMutex[connType])
    {
      mLockRequestCreate[connType] = false;
    }
  }

  /*
     private static void setDbSort(RealDbConnection conn ) throws VDbException
     {
       if( mLinguisticDefinition == null  ) return ;

       String sql = "ALTER SESSION SET NLS_SORT = " + mLinguisticDefinition;

       java.sql.Statement stmt = conn.createStatement();
       try
       {
         conn.execute(stmt,sql);
       }
       finally   { try {conn.closeStmt(stmt);}catch(SQLException e){ throw new VDbException(e,mDbPoolInstanceNumber); }}
     }
     */
  /**
   * tune the specified connection
   */
  private static void executeSessionTuning(RealDbConnection conn,
                                           Vector sessionTuningSQLStmtList) throws VDbException
  {
    if (sessionTuningSQLStmtList == null) return;

    java.sql.Statement stmt = conn.createStatement();
    try
    {
      for (int i = 0; i < sessionTuningSQLStmtList.size(); i++)
      {
        VDbExecutionContext executionContext = new VDbExecutionContext((String) sessionTuningSQLStmtList.get(i));
        conn.execute(USERS__DEFAULT_SYS_USER, stmt, executionContext);
      }
    }
    finally
    {
      try
      {
        conn.closeStmt(stmt);
      }
      catch (SQLException e)
      {
        throw new VDbException(e, mDbPoolInstanceNumber);
      }
    }
  }

  /**
   *
   */
  private static void setSqlTrace(RealDbConnection conn, boolean set) throws VDbException
  {
    if (Config.getBoolean("db.traceSQL", false))
    {
      String sql = "ALTER SESSION SET SQL_TRACE = " + set;

      java.sql.Statement stmt = conn.createStatement();
      try
      {
        VDbExecutionContext executionContext = new VDbExecutionContext(sql);
        conn.execute(USERS__DEFAULT_SYS_USER, stmt, executionContext);
      }
      finally
      {
        try
        {
          conn.closeStmt(stmt);
        }
        catch (SQLException e)
        {
          throw new VDbException(e, mDbPoolInstanceNumber);
        }
      }
    }
  }

  /**
   * Gets diagnostics info
   */

  public static VDbDiagnostics getDiagnostics()
  {
    return new VDbDiagnostics(BUILTIN_CONN_TYPE__AMOUNT, mConnPool,
            mMaxAmountConnArr,
            mCurrAmountConnArr,
            mSqlTraceSet);
  }

  /**
   * return database meta data.
   */
  public static DatabaseMetaData getMetaData() throws VDbException
  {
    RealDbConnection realDbConn = getRealDbConnection(BUILTIN_CONN_TYPE__FOR_READ);
    if (realDbConn == null) throw new VDbException("VDbConnectionManager > getMetaData >Can't get dbconnection");

    try
    {
      return realDbConn.getMetaData();
    }
    finally
    {
      returnRealDbConnection(realDbConn);
    }
  }

  /**
   * return calculated meta data .which needs for VDb users
   */

  public static VDbMetaData getVDbMetaData() throws VDbException
  {
    VDbMetaData vmeta;

    if (mVDbMetaDataClassName == null)
    {
      throw new VDbException("VDbConnectionManager > getVDbMetaData > VDbMetaData not implemented");
    }
    else
    {
      try
      {
        vmeta = (VDbMetaData) Class.forName(mVDbMetaDataClassName).newInstance();
      }
      catch (Exception e)
      {
        throw new VDbException(e.getClass() + " " + e.getMessage());
      }
    }

    return vmeta;
  }

  /**
   * Returns an unique ID of created objects.
   */
  protected final static synchronized String createObjectID()
  {
    return Long.toHexString(++mCurrentObjectID);
  }

  /**
   *
   */
  static void removeObjectFromStorage(String ID)
  {
    mVConnStorage.remove(ID);
  }

  /**
   *
   */
  public static void collectVDbConnection()
  {
    //x.1) copy storage to array
    Vector connArray = null;

    synchronized (mVConnStorage)
    {
      connArray = new Vector(mVConnStorage.size());

      for (java.util.Enumeration i = mVConnStorage.elements(); i.hasMoreElements();)
      {
        connArray.add(i.nextElement());
      }
    }

    //checking which activate objects are still usefull
    int size = connArray.size();

    for (int i = 0; i < size; i++)
    {
      final VDbConnection conn = (VDbConnection) connArray.get(i);
      if (conn.isUseful() == false)
      {
        new Thread()
        {
          public void run()
          {
            conn.close();
          }
        }.start();
      }
    }
    connArray = null;
  }

  /**
   *
   */
  public static synchronized long getDbPoolInstanceNumber()
  {
    return mDbPoolInstanceNumber;
  }

  /**
   * Set module status
   */
  public static synchronized void setModuleStatus(short moduleStatus)
  {
    mModuleStatus = moduleStatus;
  }

  /**
   * Get module status
   */
  public static synchronized short getModuleStatus()
  {
    return mModuleStatus;
  }

  /**
   * @return String
   */
  public static synchronized String getDbUser()
  {
    return mUser;
	}

}
