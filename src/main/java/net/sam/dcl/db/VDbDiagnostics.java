package net.sam.dcl.db;

import net.sam.dcl.db.*;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.RealDbConnection;
import net.sam.dcl.util.TypeConverter;


import java.sql.ResultSet;
import java.sql.SQLException;

public class VDbDiagnostics {
	private int mPoolAmount;
	private int mMaxAmountConnArr[] = null;
	private int mCurrAmountConnArr[] = null;
	private boolean mSqlTraceSet[] = null;

	private Object mConnPool[] = null;


	VDbDiagnostics(int poolAmount, Object[] connPool,
								 int[] maxAmountConnArr,
								 int[] currAmountConnArr,
								 boolean[] sqlTraceSet) {
		mPoolAmount = poolAmount;
		mConnPool = connPool;
		mMaxAmountConnArr = maxAmountConnArr;
		mCurrAmountConnArr = currAmountConnArr;
		mSqlTraceSet = sqlTraceSet;
	}

	/**
	 * gets pool ammount
	 */
	public int getPoolAmount() {
		return mPoolAmount;
	}

	/**
	 * gets pool name
	 */
	public String getPoolName(int poolNum) {
		switch (poolNum) {
		case net.sam.dcl.db.VDbConnectionManager.BUILTIN_CONN_TYPE__FOR_READ:
			return "For read and single write operation";
		case VDbConnectionManager.BUILTIN_CONN_TYPE__FOR_WRITE:
			return "For multiple write operation";
		default:
			return "Unknown";
		}
	}

	/**
	 * gets connection ammount
	 */
	public int getConnAmount(int poolNum) {
		return mCurrAmountConnArr[poolNum];
	}

	/**
	 * gets available connection ammount
	 */
	public int getAvailableConnAmount(int poolNum) {
		return mMaxAmountConnArr[poolNum];
	}

	/**
	 * gets count of connection using
	 */
	public int getConnUseCount(int poolNum, int connNum) {
		return ((RealDbConnection[]) (mConnPool[poolNum]))[connNum].getUseCount();
	}

	/**
	 * gets connection busy
	 */
	public boolean isBusy(int poolNum, int connNum) {
		return ((RealDbConnection[]) (mConnPool[poolNum]))[connNum].isBusy();
	}

	/**
	 *
	 */
	public boolean isTransaction(int poolNum, int connNum) {
		return ((RealDbConnection[]) (mConnPool[poolNum]))[connNum].isTransaction();
	}

	public long getTotalUsingNumber(int poolNum, int connNum) {
		return ((RealDbConnection[]) (mConnPool[poolNum]))[connNum].getTotalUsingNumber();
	}

	public String getOpenedTime(int poolNum, int connNum) {
		return TypeConverter.toFormatTS(TypeConverter.toString(((RealDbConnection[]) (mConnPool[poolNum]))[connNum].getOpenedTime()));
	}

	public String getLastUsingTime(int poolNum, int connNum) {
		return TypeConverter.toFormatTS(TypeConverter.toString(((RealDbConnection[]) (mConnPool[poolNum]))[connNum].getLastUsingTime()));
	}

	/**
	 * returns number of open cursors which have been opened in VDb components.
	 */
	public int getVDbOpenCursor(int poolNum, int connNum) {
		return ((RealDbConnection[]) (mConnPool[poolNum]))[connNum].getUseStmt();
	}

	/**
	 * returns number of open cursors which are took from Oracle.
	 */
	public int getOracleOpenCursor(int poolNum, int connNum) throws VDbException {
		RealDbConnection conn = ((RealDbConnection[]) (mConnPool[poolNum]))[connNum];

		if (conn == null) throw new VDbException("VDbDiagnostics > getOracleOpenCursor > conn is null");

		String sql = "select count(c.sid) from v$open_cursor c , v$session s where c.sid=s.sid and s.audsid=userenv('SESSIONID')";

		java.sql.Statement stmt = null;
		try {
			stmt = conn.createStatement();
		}
		catch (VDbException e) {
			//x.1) check if number of cursors exceeded
			if (e.getErrorCode() == 1000) {
				return -1;
			} else {
				throw e;
			}
		}

		try {
			VDbExecutionContext executionContext = new VDbExecutionContext(sql);
			ResultSet rset = conn.executeQuery(stmt, executionContext);
			rset.next();
			return rset.getInt(1);
		}
		catch (VDbException e) {
			throw e;
		}
		catch (SQLException e) {
			throw new VDbException(e, conn.getDbPoolInstanceNumber());
		}
		finally {
			try {
				conn.closeStmt(stmt);
			} catch (SQLException e) {
				throw new VDbException(e, conn.getDbPoolInstanceNumber());
			}
		}
	}

	/**
	 * gets value of SqlTrace parameter for specified connection type.
	 */

	public boolean getSqlTrace(int connType) {
		return mSqlTraceSet[connType];

		/*
				RealDbConnection conn = ((RealDbConnection[])(mConnPool[connType]))[0];

				if(conn  ==  null ) throw new VDbException("VDbDiagnostics > getSqlTrace > conn is null");

				String sql = "select VALUE from v$parameter where name = 'sql_trace'";

				java.sql.Statement stmt=null;
				try
				{
					 stmt = conn.createStatement();
				}

				catch(VDbException e)
				{
					//x.1) check if number of cursors exceeded
				 //if(e.getErrorCode()==1000)  return -1 ;
				 //else                        throw e   ;
				 throw e;
				}

				try
				{
					ResultSet   rset = conn.executeQuery(stmt,sql);
											rset.next();
					return rset.getBoolean(1);
				}
				catch ( VDbException  e ){ throw e; }
				catch ( SQLException  e ){ throw new VDbException(e,conn.getDbPoolInstanceNumber());}
				finally { try{ conn.closeStmt(stmt); }catch(SQLException e){ throw new VDbException(e,conn.getDbPoolInstanceNumber());}}
				*/
	}

	/**
	 * gets the value of the current driver type
	 *
	 * @return value from the following constants  :VDbConnectionManager.DRIVER_TYPE__...
	 */
	public short getCurrentDriverType() {
		return VDbConnectionManager.mDriverType;
	}

}
