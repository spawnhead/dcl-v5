/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: Feb 20, 2003
 * Time: 9:51:01 AM
 * To change this template use Options | File Templates.
 */
package net.sam.dcl.test;


import net.sam.dcl.db.*;
import net.sam.dcl.log.Log;
import net.sam.dcl.util.DbReconnector;
import org.firebirdsql.jdbc.FBDriver;

import java.sql.Types;

public class OracleConnection{
  public static void main(String arg[]){
    try{
      init();
    } catch (Exception e){
      e.printStackTrace();  //To change body of catch statement use Options | File Templates.
    }
    try{
      VDbConnection conn = VDbConnectionManager.getVDbConnection();
      VParameter param = new VParameter("id","22",Types.VARCHAR);
      VResultSet resultSet = conn.executeQuery("select * from test_table where id=:id",param);
      while(resultSet.next()){
        System.out.println(resultSet.getData(1)+" "+resultSet.getData(2)+" "+resultSet.getData(3)+" ");
      }
    } catch (VDbException e){
      e.printStackTrace();  //To change body of catch statement use Options | File Templates.
    }
  }
  static private void init() throws Exception{

    try{

      VDbConnectionManager.initAnyDriver(FBDriver.class.getName(),
                                        null,
                                        "jdbc:firebirdsql://pc341/D:\\Projects\\Lintera\\Lintera.fdb",
                                        "SYSDBA",
                                        "masterkey");

      DbReconnector.initDbPool(2,4,2,4,1,1);
/*            String sql = new BufferedStringReader (new FileInputStream (Config.getString ("startup.sql"))).readToString ();
            VDbConnectionManager.executeSessionTuning (Parser.readSqlToArray (sql));
*/
    } catch (Exception e){
      Log.error("Error occured while initializing AuthFilter connections to DB: "+e.getMessage(), "AUF");
    }
  }


}
