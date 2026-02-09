package net.sam.dcl.log;

import net.sam.dcl.config.Config;

import org.apache.commons.logging.*;
/**
 * Title:        SRM
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author ES
 * @version
 */

public class Log {

  private   static String           unknownModuleStr = Log.class.getName();
  private   static int              errorCounter = 0;


  public static void info (String message, String module){
    writeMessage (message, module, "I");
  }

  public static void info (String message){
    writeMessage (message, unknownModuleStr, "I");
  }

  public static void warning (String message, String module){
    writeMessage (message, module, "W");
  }

  public static void warning (String message){
    writeMessage (message, unknownModuleStr, "W");
  }

  public static void error (String message, String module){
    writeMessage (message, module, "E");
  }

  public static void error (String message){
    writeMessage (message, unknownModuleStr, "E");
  }

  private static synchronized void writeMessage (String message, String module, String type){
    org.apache.commons.logging.Log log = LogFactory.getLog("net.sam.dcl.jsp."+module);
    if(type.equalsIgnoreCase("E"))  {
      errorCounter++;
      if( Config.getNumber("log.error",1) == 0 ) {/*System.out.println("\n!!!!"+message+"!!!!\n");*/return;}
      log.error(message);
    }
    if(type.equalsIgnoreCase("W")){
      if( Config.getNumber("log.warning",1) == 0 ) {/*System.out.println("\n!!!!"+message+"!!!!\n");*/return;}
      log.warn(message);
    }
    if(type.equalsIgnoreCase("I")){
      if( Config.getNumber("log.info",1) == 0 ) {/*System.out.println("\n!!!!"+message+"!!!!\n");*/return;}
      log.info(message);
    }
  }

    public static synchronized void printExceptionStackTrace (Throwable exc){
      org.apache.commons.logging.Log log = LogFactory.getLog(Log.class);
        String isLogExc = Config.getString ("DEBUG.logexc");
        if (isLogExc != null && isLogExc.equals ("1")){
          log.error("",exc);
        }
    }


}