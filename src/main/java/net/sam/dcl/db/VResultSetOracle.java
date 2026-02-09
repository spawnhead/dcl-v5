package net.sam.dcl.db;

import net.sam.dcl.db.*;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.RealDbConnection;
import net.sam.dcl.db.VDbException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VResultSetOracle extends net.sam.dcl.db.VResultSet {

	/**
	 * Constructs an VResulrSet object
	 */
	protected VResultSetOracle(String vresultSetID, VDbConnection vdbconn,
														 ResultSet rset,
														 java.sql.Statement stmt,
														 RealDbConnection conn) throws VDbException {
		super(vresultSetID, vdbconn, rset, stmt, conn);
	}


	/**
	 * Gets data from the result set.
	 * return string
	 * the first column is 1
	 */
	protected String getDataFromResultSet(int colNum) throws VDbException {
		//x.2) get data from result set
		try {
			switch (mColumnTypeArr[colNum - 1]) {

			case java.sql.Types.DECIMAL:
			case java.sql.Types.NUMERIC: {
				oracle.sql.NUMBER numVal = ((oracle.jdbc.driver.OracleResultSet) mRSet).getNUMBER(colNum);
				if (mRSet.wasNull() == true) {
					return "";
				} else {
					return numVal.stringValue();
				}
			}

			/*
							//oracle 9 and 7.x 8.x All types Date Time and Timestamp we recognize as Timestamp
							case java.sql.Types.DATE      :
							case java.sql.Types.TIME      :
							case java.sql.Types.TIMESTAMP : {
																								java.sql.Timestamp val  = mRSet.getTimestamp (colNum);
																								if(mRSet.wasNull()==true) return "";
																								else                      return TypeConverter.toString    (val);
																							}*/

			default:
				return super.getDataFromResultSet(colNum);
			}
		}
		catch (VDbException e) {
			throw e;
		}
		catch (SQLException e) {
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * for the certain database it will be need to correct a column's type.
	 * overloaded
	 * racle 9 and 7.x 8.x All types Date Time and Timestamp we recognize as Timestamp
	 */
	protected int correctType(int type) {
		switch (type) {
		case java.sql.Types.DATE:
		case java.sql.Types.TIME:
			return java.sql.Types.TIMESTAMP;

		default:
			return type;
		}
	}
}
