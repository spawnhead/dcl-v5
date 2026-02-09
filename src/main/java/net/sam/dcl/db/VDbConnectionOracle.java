package net.sam.dcl.db;

import net.sam.dcl.db.*;
import net.sam.dcl.db.StringValueHolder;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.RealDbConnection;
import net.sam.dcl.db.VDbConnectionManager;

import java.sql.SQLException;
import java.sql.ResultSet;

public class VDbConnectionOracle extends net.sam.dcl.db.VDbConnection {

	public VDbConnectionOracle() {
		super();
	}

	/**
	 * Gets an out data
	 */
	protected void getOutData(int i, java.sql.CallableStatement stmt, VParameter.Element elem) throws VDbException {
		try {
			switch (elem.mParamType) {
			case java.sql.Types.DECIMAL:
			case java.sql.Types.NUMERIC: {
				oracle.sql.NUMBER numVal = ((oracle.jdbc.driver.OracleCallableStatement) stmt).getNUMBER(i);
				if (stmt.wasNull() == true) {
					((StringValueHolder) elem.mOutParamVal).val = "";
				} else {
					((StringValueHolder) elem.mOutParamVal).val = numVal.stringValue();
				}
				break;
			}
			/*
							//oracle 9 and 7.x 8.x All types Date Time and Timestamp we recognize as Timestamp
							case java.sql.Types.DATE      :
							case java.sql.Types.TIME      :
							case java.sql.Types.TIMESTAMP : {
																							java.sql.Timestamp val  = stmt.getTimestamp (i);
																							if(stmt.wasNull()==true) ((StringValueHolder)elem.mOutParamVal).val="";
																							else                     ((StringValueHolder)elem.mOutParamVal).val=TypeConverter.toString(val);
																							break;
																							}*/

			default:
				super.getOutData(i, stmt, elem);
			}
		}
		catch (VDbException e) {
			throw e;
		}
		catch (SQLException e) {
			throw new VDbException(e, mConn.getDbPoolInstanceNumber());
		}
	}

	/**
	 * Creates VResultSetOracle object
	 */
	protected VResultSet newVResultSet(String vresultSetID, VDbConnection vdbconn,
																		 ResultSet rset,
																		 java.sql.Statement stmt,
																		 RealDbConnection conn) throws VDbException {
		return new VResultSetOracle(vresultSetID, vdbconn, rset, stmt, conn);
	}

	/**
	 * Binds parameters
	 */
	protected void bindParam(java.sql.PreparedStatement stmt, VParameter.Element elem, int paramNum) throws VDbException {
		super.bindParam(stmt, elem, paramNum);

		//x.1) register a parameter
		/*
				if( elem.mParamOut == true ) super.bindParam(stmt ,elem ,paramNum);
				try
				{
					switch(elem.mParamType)
					{
						//oracle 9 and 7.x 8.x All types Date Time and Timestamp we recognize as Timestamp
						case java.sql.Types.DATE       :
						case java.sql.Types.TIME       :
						case java.sql.Types.TIMESTAMP  :
						{
							if(elem.mParamVal==null) stmt.setNull     (paramNum , java.sql.Types.TIMESTAMP);
							else                     stmt.setTimestamp(paramNum , TypeConverter.toTimestamp(elem.mParamVal));
							break ;
						}
						default : super.bindParam(stmt ,elem ,paramNum); break ;
					}
				}
				//catch(VDbException e){ throw e;}
				catch(SQLException e){ throw new VDbException(e,mConn.getDbPoolInstanceNumber());}
				*/
	}

	/**
	 * Executes an SQL INSERT, UPDATE or DELETE statement.
	 *
	 * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
	 *            statement that returns nothing
	 * @return either the row count for INSERT, UPDATE or DELETE or 0
	 *         for SQL statements that return nothing
	 * @throws java.sql.SQLException if a database access error occurs or
	 *                               the row count equals 0
	 */
	public synchronized int executeUpdateEx(String user, String sql) throws VDbException {
		int ret = super.executeUpdate(user, sql);
		if (ret == 0) {
			throw new VDbException
					("No rows have been updated, inserted or deleted",
					 -20001, VDbConnectionManager.getModuleStatus()
					);
		}
		return ret;
	}

	/**
	 * Executes an SQL INSERT, UPDATE or DELETE statement.
	 *
	 * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
	 *            statement that returns nothing
	 * @return either the row count for INSERT, UPDATE or DELETE or 0
	 *         for SQL statements that return nothing
	 * @throws java.sql.SQLException if a database access error occurs or
	 *                               the row count equals 0
	 */
	public synchronized int executeUpdateEx(String user, String sql, VParameter param) throws VDbException {
		int ret = super.executeUpdate(user, sql, param);
		if (ret == 0) {
			throw new VDbException
					("No rows have been updated, inserted or deleted",
					 -20001,
					 VDbConnectionManager.getModuleStatus()
					);
		}
		return ret;
	}

}
