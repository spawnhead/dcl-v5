/**
 * Title:        AM <p>
 * Description:  AM<p>
 * Copyright:    Copyright (c) A.K.<p>
 * Company:      <p>
 * @author A.K.
 * @version 1.0
 */
package net.sam.dcl.util;

import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VDbException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The class provides methods to reset old connections and create new connections
 * and then allocates the new connections into a  VDbConnectionManager
 */
public class DbReconnector
{

  static boolean  mBusy                          = false ;
  static Integer  mMutexBusy                     = new Integer(0);
  static long     mLastDbConnectionManagerNumber = 0;

  static int      mNumInitialConnR    ;
  static int      mNumMaxConnR        ;
  static int      mNumInitialConnW    ;
  static int      mNumMaxConnW        ;
  static int      mNumInitialConnRep  ;
  static int      mNumMaxConnRep      ;


  static final int RETCODE__TYP1 = 1;
  static final int RETCODE__TYP2 = 2;
  static final int RETCODE__TYP3 = 3;

  public static final Log log = LogFactory.getLog(DbReconnector.class);


  /**
   * check if there are appropriate errors.
   */
  public static boolean isError( int errorCode )
  {
    switch(errorCode)
    {
      //thin : could not establish the connection
      case (int)17002 : return true;
      //OCI operation time out
      case (int)12535 : return true;
      //OCI TNS :listener failed to start a dedicated server process(at the begin of connection start)
      case (int)12500 : return true;
      //OCI TNS:packet write failure(when the OracleService is stoped)
      case (int)12571 : return true;
      //ORA-01014 ORACLE shutdown in progress
      case (int)1014 : return true;
      //OCI ORACLE not avialable(when the OracleService is running but the OracleStart is stoped )
      case (int)1034 : return true;
      //OCI 1033 ORACLE initialization or shutdown in progress
      case (int)1033 : return true;
      //OCI connected failed
      case (int)12545 : return true;
      //OCI end of communication chanel
      case (int)3113 : return true;
      //OCI not connected to ORACLE
      case (int)3114  : return true;
      //ORA-01089: immediate shutdown in progress - no operations are permitted
      case (int) 1089 : return true;
      //ORA-01090: shutdown in progress - connection is not permitted
      case (int)1090  : return true;
      //ORA-01092 ORACLE instance terminated. Disconnection forced
      case (int)1092  : return true;
      //ORA-00028: session killed
      case (int) 28   : return true;
      //ORA-01012: not logged on
      case (int) 1012 : return true;


    }
    return false ;
  }

  // reconnection
  public static int reconnect(final long dbPoolInstanceNumber) //throws TWISTAppServerException
  {

    //x.1) accept demand only from that which involves current  db manager
    //
    if(VDbConnectionManager.getDbPoolInstanceNumber() != dbPoolInstanceNumber ) return RETCODE__TYP1;

    //x.2)check the reconnection begin
    synchronized( mMutexBusy )
    {
      if( mBusy ) return RETCODE__TYP2;
      mBusy = true;
    }

    //x.3) accept demand only from that which involves current  db manager
    //      the same line .important
    if(VDbConnectionManager.getDbPoolInstanceNumber()== dbPoolInstanceNumber )
    {
      new Thread()
      {
        public void run()
        {
          //x.2) start reconnection
          //DbConnectionManager newDbManager = null;

          log.info("Reconnection start");
          //set VDB Module status
          VDbConnectionManager.setModuleStatus
                            (VDbConnectionManager.MODULE_STATUS__NOTDBCONNECTION);

          try
          {
            initDbPool( mNumInitialConnR    , mNumMaxConnR  ,
                        mNumInitialConnW    , mNumMaxConnW  ,
                        mNumInitialConnRep  , mNumMaxConnRep );
          }
          catch(VDbException e) {  }


          synchronized( mMutexBusy ){ mBusy = false; }
        }
      }.start();
      return RETCODE__TYP3;
     }
     else
     {
      synchronized( mMutexBusy ){ mBusy = false; }

      return RETCODE__TYP1;
     }
  }

  public static synchronized void initDbPool  ( int      numInitialConnR ,
                                                int      numMaxConnR     ,
                                                int      numInitialConnW ,
                                                int      numMaxConnW     ,
                                                int      numInitialConnRep ,
                                                int      numMaxConnRep     )
                                                                 throws VDbException
  {

    mNumInitialConnR    = numInitialConnR ;
    mNumMaxConnR        = numMaxConnR     ;
    mNumInitialConnW    = numInitialConnW ;
    mNumMaxConnW        = numMaxConnW     ;
    mNumInitialConnRep  = numInitialConnRep ;
    mNumMaxConnRep      = numMaxConnRep     ;

    while( true )
    {
      try
      {
        VDbConnectionManager.initDbPool(mNumInitialConnR    ,  mNumMaxConnR   ,
                                        mNumInitialConnW    ,  mNumMaxConnW   ,
                                        mNumInitialConnRep  ,  mNumMaxConnRep  );
        break;
      }
      catch(VDbException e)
      {
        //Log.error( e.getMessage() , "DBR" );

        log.error(e.getMessage());

        if(isError(e.getErrorCode()) )
          {
            //Log.info("Attempt to connect again","DBR");
            log.info("Attempt to connect again");

            //set VDB Module status
            VDbConnectionManager.setModuleStatus
                            (VDbConnectionManager.MODULE_STATUS__NOTDBCONNECTION);

            try {Thread.sleep(5000);}catch(InterruptedException ie){}
            continue;
          }
        else                         throw e   ;
      }
    }
  }

  /**
   *
   */

   public static boolean isBusy()
   {
    return mBusy;
   }

  public static int  analyseException ( Throwable ex  )

  {
    int retCode = 0;

    if(ex instanceof VDbException)
    {
      VDbException e = (VDbException)ex;
      if(isError(e.getErrorCode()))
      {
        retCode = reconnect( e.mDbPoolInstanceNumber);

       // if(retCode==RETCODE__TYP1) { e.SQLErrorCode = ConstDefinitions.ERROR__NOTDBCONNECTION; e.message = e.message +" [1001]";}
       // if(retCode==RETCODE__TYP2) { e.SQLErrorCode = ConstDefinitions.ERROR__NOTDBCONNECTION; e.message = e.message +" [1002]";}
       // if(retCode==RETCODE__TYP3) { e.SQLErrorCode = ConstDefinitions.ERROR__NOTDBCONNECTION; e.message = e.message +" [1003]";}
      }
    }
    return retCode ;
  }

}
