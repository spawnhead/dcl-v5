package net.sam.dcl.db;

import net.sam.dcl.util.TypeConverter;

import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

public class VResultSet
{
	protected String mVResultSetID;
	private net.sam.dcl.db.VDbConnection mVDbConn;
	protected java.sql.ResultSet mRSet;
	private RealDbConnection mConn;
	private java.sql.Statement mStmt;

	protected int[] mColumnTypeArr;

	private int mResultColumns;

	private int mPageSize;
	private long mRecCount;
	private int mPageRecCount;
	//  Sometime a user needs to set a page number which is not calculated
	//  from currenRecCount.
	private int mUserCurrPageNumber;

	private int mPageCursorPosition;
	private int mModeRun;
	private boolean reachEndOfResultSet = false;

	private boolean mPageRecBuffEmpty;
	//private boolean                         mGetDataFromBuff;
	private Vector mPageRecBuff;

	private boolean mEliminateLineBreaks;

	protected long mDbPoolInstanceNumber;
	//after closing functionality
	protected Vector mAfterCloseStatement;

	public static int PAGE_SIZE = 100;

	public static int MODE_RUN__SAVE_RECORDS_OF_CURRENT_PAGE = 0x0001;
	public static int MODE_RUN__GET_DATA_FROM_BUFF = 0x0002;

	/**
	 * Constructs an VResulrSet object
	 */
	protected VResultSet(String vresultSetID, VDbConnection vdbconn,
	                     ResultSet rset,
	                     java.sql.Statement stmt,
	                     RealDbConnection conn
	) throws VDbException
	{
		Init();
		mVResultSetID = vresultSetID;
		mVDbConn = vdbconn;
		mRSet = rset;
		mStmt = stmt;
		mConn = conn;
		mDbPoolInstanceNumber = mConn.getDbPoolInstanceNumber();
		try
		{
			mRSet.setFetchSize(PAGE_SIZE);
			readMetaData(mRSet.getMetaData());
		}
		catch (VDbException e)
		{
			throw e;
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * Inits this object.
	 */
	private void Init()
	{
		mRSet = null;
		//mRSetMeta           = null;
		mConn = null;
		mColumnTypeArr = null;
		mStmt = null;
		mPageSize = PAGE_SIZE;
		mModeRun = 0;
		mRecCount = 0;
		mPageRecCount = 0;
		mUserCurrPageNumber = -1;
		mPageCursorPosition = 0;

		mPageRecBuffEmpty = true;
		//mGetDataFromBuff    = false;
		mPageRecBuff = null;

		mResultColumns = 0;
		mDbPoolInstanceNumber = 0;
		mAfterCloseStatement = null;

		mEliminateLineBreaks = false;
	}

	/**
	 * Gets data.
	 * return string
	 */
	public synchronized String getData(String fieldName) throws VDbException
	{
		try
		{
			return getData(mRSet.findColumn(fieldName));
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * Gets data .
	 * return string
	 * the first column is 1
	 */
	public synchronized String getData(int colNum) throws VDbException
	{
		//x.1) get data from buffer
		if (isModeRun(MODE_RUN__GET_DATA_FROM_BUFF))
		{
			// VT$
			//return ((String[])(mPageRecBuff.elementAt(mPageCursorPosition-1)))[colNum-1];
			String val = ((String[]) (mPageRecBuff.elementAt(mPageCursorPosition - 1)))[colNum - 1];
			if (mEliminateLineBreaks)
			{
				val = eliminateLineBreaks(val);
			}
			return val;
		}
/*
   if(  mRSet.isAfterLast() ||
        mRSet.isBeforeFirst())
        throw new SQLException("The cursor position isn't into a work area","",-20002);
*/
		return getDataFromResultSet(colNum);
	}

	/**
	 * Gets data from the result set.
	 * return string
	 * the first column is 1
	 */
	protected String getDataFromResultSet(int colNum) throws VDbException
	{
		//x.2) get data from result set
		try
		{
			// Проверка на закрытый ResultSet
			if (mRSet == null || mRSet.isClosed())
			{
				throw new VDbException("The result set is closed");
			}
			switch (mColumnTypeArr[colNum - 1])
			{
				case java.sql.Types.TINYINT:
				{
					byte val = mRSet.getByte(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.SMALLINT:
				{
					short val = mRSet.getShort(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.INTEGER:
				{
					int val = mRSet.getInt(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.BIGINT:
				{
					long val = mRSet.getLong(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.REAL:
				{
					float val = mRSet.getFloat(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.DOUBLE:
				case java.sql.Types.FLOAT:
				{
					double val = mRSet.getDouble(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.DECIMAL:
				case java.sql.Types.NUMERIC:
				{
					java.math.BigDecimal val = mRSet.getBigDecimal(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.BIT:
				{
					boolean val = mRSet.getBoolean(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.DATE:
				{
					java.sql.Date val = mRSet.getDate(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.TIME:
				{
					java.sql.Time val = mRSet.getTime(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.TIMESTAMP:
				{
					java.sql.Timestamp val = mRSet.getTimestamp(colNum);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return TypeConverter.toString(val);
					}
				}
				case java.sql.Types.CHAR:
				case java.sql.Types.VARCHAR:
				case java.sql.Types.LONGVARCHAR:
				{
					String val = mRSet.getString(colNum);
					if (mEliminateLineBreaks) val = eliminateLineBreaks(val);
					if (mRSet.wasNull() == true)
					{
						return "";
					}
					else
					{
						return val;
					}
				}
				default:
					throw new VDbException("VResultSet > getData > Not supported type");
			}
		}
		catch (VDbException e)
		{
			throw e;
		}
		catch (SQLException e)
		{
			// Проверяем, не закрыт ли ResultSet
			String errorMsg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
			if (errorMsg.contains("result set is closed") || errorMsg.contains("resultset is closed"))
			{
				throw new VDbException("The result set is closed", mDbPoolInstanceNumber);
			}
			// Проверяем, не закрыт ли ResultSet через isClosed()
			if (mRSet != null)
			{
				try
				{
					if (mRSet.isClosed())
					{
						throw new VDbException("The result set is closed", mDbPoolInstanceNumber);
					}
				}
				catch (SQLException ignore)
				{
					// Если isClosed() выбрасывает исключение, значит ResultSet закрыт
					throw new VDbException("The result set is closed", mDbPoolInstanceNumber);
				}
			}
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * Gives the JDBC driver a hint as to the number of rows that should
	 * be fetched from the database when more rows are needed for this result
	 * set.
	 *
	 * @param rows the number of rows to fetch
	 * @throws java.sql.SQLException if a database access error occurs
	 */
	public synchronized void setFetchSize(int rows) throws VDbException
	{
		try
		{
			mRSet.setFetchSize(rows);
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * Sets size of page.
	 */
	public synchronized void setPageSize(int pageSize)
	{
		if (pageSize < 0) return;
		mPageSize = pageSize;
	}

	/**
	 * Go to a specified page from a current cursor position.
	 *
	 * @return true if the cursor is on the result set; false otherwise
	 */
	public synchronized boolean setPage(int pageNumber) throws VDbException
	{
		//x.1) check a page number
		if (pageNumber < 0) throw new VDbException("VResultSet > setPage > a page number is incorrect");
		//x.2)calculate a row number.
		int rowNumber = pageNumber * mPageSize - mPageSize;
		//x.3)
		if (rowNumber < 0) return true;

		//x.4)

		int i = 0;
		while (i < rowNumber && /*mRSet.*/next()) i++;

		if (i != rowNumber) return false;

		return true;
	}

	/**
	 * Just sets  a  current page number.
	 */
	public synchronized void setUserCurrPageNumber(int currPageNumber)
	{
		mUserCurrPageNumber = currPageNumber;
	}

	/**
	 * Go to a specified record from a current cursor position.
	 *
	 * @return true if the cursor is on the result set; false otherwise
	 */
	public synchronized boolean setRow(int rowNumber) throws VDbException
	{
		//x.1) check a page number
		if (rowNumber < 0) throw new VDbException("VResultSet > setRow > a rec number is incorrect");

		// Проверка на закрытый ResultSet перед началом
		if (mRSet == null || mRSet.isClosed())
		{
			reachEndOfResultSet = true;
			return false;
		}

		int i = 1;
		try
		{
			while (i < rowNumber && next())
			{
				// Проверка на закрытый ResultSet в цикле
				if (mRSet == null || mRSet.isClosed())
				{
					reachEndOfResultSet = true;
					return false;
				}
				i++;
			}
		}
		catch (VDbException e)
		{
			// Если ResultSet закрылся во время чтения, возвращаем false
			if (e.getMessage() != null && e.getMessage().toLowerCase().contains("result set is closed"))
			{
				reachEndOfResultSet = true;
				return false;
			}
			throw e;
		}

		if (i != rowNumber) return false;

		return true;
	}

	/**
	 * Moves the cursor down one row from its current position.
	 * A ResultSet cursor is initially positioned before the first row; the
	 * first call to next makes the first row the current row; the
	 * second call makes the second row the current row, and so on.
	 *
	 * @return true if the new current row is valid; false if there
	 *         are no more rows
	 * @throws java.sql.SQLException if a database access error occurs
	 */

	public synchronized boolean next() throws VDbException
	{
		boolean fl = false;
		if (reachEndOfResultSet) return false;
		// Проверка на закрытый ResultSet
		if (mRSet == null || mRSet.isClosed())
		{
			reachEndOfResultSet = true;
			return false;
		}
		try
		{
			//x.1)
			if (mModeRun == 0) fl = mRSet.next();

			//x.2)
			if (isModeRun(MODE_RUN__SAVE_RECORDS_OF_CURRENT_PAGE))
			{
				//x.x.1)
				if (mPageRecBuffEmpty == true) mPageRecBuff = new Vector(PAGE_SIZE);

				//x.x.2)reset buffer
				if (mPageRecCount == mPageSize)
				{
					mPageRecBuff.clear();
					mPageRecCount = 0;
				}
				//x.x.3
				if ((fl = mRSet.next()) == true)
				{

					//x.x.2)  adding record to buffer.
					String valArr[] = new String[mResultColumns];

					for (int i = 0; i < mResultColumns; i++) valArr[i] = this.getData(i + 1);

					mPageRecBuff.add(valArr);
					mPageRecBuffEmpty = false;
					mPageRecCount++;
				}
			}
			else if (isModeRun(MODE_RUN__GET_DATA_FROM_BUFF))
			{
				//x.x.1) check  there are any rows in the page buffer.
				if (mPageCursorPosition == mPageRecBuff.size())
				{
					resetModeRun(MODE_RUN__GET_DATA_FROM_BUFF);
					fl = mRSet.next();
				}
				else
				{
					mPageCursorPosition++;
					fl = true;
				}
			}
			if (!fl)
			{
				reachEndOfResultSet = true;
			}
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
		if (fl == true) mRecCount++;

		return fl;
	}

	/**
	 * Moves the cursor to the first row in current page.
	 *
	 * @return true if the cursor is on a valid row; false if
	 *         there are no rows in the result set
	 * @throws java.sql.SQLException if a database access error occurs or the
	 */
	public synchronized boolean firstRecInPage()
	{

		if (isModeRun(MODE_RUN__GET_DATA_FROM_BUFF)) return false;

		setModeRun(MODE_RUN__GET_DATA_FROM_BUFF);
		mPageCursorPosition = 0;

		if (mPageRecBuff != null) mRecCount = mRecCount - mPageRecBuff.size();

		return true;
	}

	/**
	 * closes this result set
	 */
	public synchronized void close()
	{
		//x.1) closing  a ResultSet
		try
		{
			if (mRSet != null) mRSet.close();
		}
		catch (SQLException e)
		{
		}
		finally
		{
			mRSet = null;
		}

		//x.2) closing  a Statement
		try
		{
			//if( mStmt != null)     mStmt.close();
			mConn.closeStmt(mStmt);
		}
		catch (SQLException e)
		{
		}
		finally
		{
			mStmt = null;
		}

		//x.3)remove this instanse of VResultSet from the storage of corresponding
		//    VDbConnection.
		mVDbConn.removeVResultSetFromStorage(mVResultSetID);

		//x.4) executing something.
		executeAfterClose();
	}

	/**
	 * Converters database metadata to the local format.
	 */
	protected void readMetaData(ResultSetMetaData metaResult) throws VDbException
	{
		try
		{
			mResultColumns = metaResult.getColumnCount();

			//x.1) make ConvertorData row and column amount
			mColumnTypeArr = new int[mResultColumns];

			for (int index = 1; index <= mResultColumns; index++)
			{
				mColumnTypeArr[index - 1] = correctType(metaResult.getColumnType(index));

				//  int     size       = metaResult.getColumnDisplaySize(index);
				//  int     type        = metaResult.getColumnType(index);
				//          size       =  metaResult.getPrecision(index);
				//         type=0;
				/*
							String  name              = ((oracle.jdbc.driver.OracleResultSetMetaData)metaResult). getColumnLabel(index);
											name              = metaResult.getColumnName (index);
												name            = metaResult.getSchemaName(index);
												name            = metaResult.getCatalogName(index);
												name            = ((oracle.jdbc.driver.OracleResultSetMetaData)metaResult).getTableName(index);

											name=null;*/
			}
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * Sets a run mode
	 */
	public synchronized void setModeRun(int modeRun)
	{
		if ((modeRun & MODE_RUN__SAVE_RECORDS_OF_CURRENT_PAGE) != 0)
		{
			mPageRecBuffEmpty = true;
		}

		mModeRun = mModeRun | modeRun;
	}

	/**
	 * Resets a run mode
	 */
	public synchronized void resetModeRun(int modeRun)
	{
		mModeRun = mModeRun & (~modeRun);
	}

	/**
	 * Checks a mode run
	 */
	public synchronized boolean isModeRun(int modeRun)
	{
		return (mModeRun & modeRun) != 0;
	}

	public synchronized int getResultColumns()
	{
		return mResultColumns;
	}

	public synchronized long getCurrPage()
	{
		if (mUserCurrPageNumber != -1)
		{
			return mUserCurrPageNumber;
		}
		else
		{
			return (mRecCount == 0) ? 0 : (mRecCount - 1) / mPageSize + 1;
		}
	}

	public synchronized long getRecCount()
	{
		return mRecCount;
	}

	public synchronized int getPageSize()
	{
		return mPageSize;
	}

	public synchronized String getColumnName(int colNum) throws VDbException
	{
		try
		{
			if (mRSet == null)
			{
				return null;
			}
			ResultSetMetaData meta = mRSet.getMetaData();
			String label = meta.getColumnLabel(colNum);
			// Предпочитаем алиасы, чтобы маппинг на бины работал корректно
			if (label != null && label.length() > 0)
			{
				return label;
			}
			return meta.getColumnName(colNum);
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	public synchronized String getColumnLabel(int colNum) throws VDbException
	{
		try
		{
			return mRSet == null ? null : mRSet.getMetaData().getColumnLabel(colNum);
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	public synchronized int getType(int colNum) throws VDbException
	{
		try
		{
			return correctType(mRSet.getMetaData().getColumnType(colNum));
		}
		catch (SQLException e)
		{
			throw new VDbException(e, mDbPoolInstanceNumber);
		}
	}

	/**
	 * for the certain database it will be need to correct a column's type.
	 * overloaded
	 */
	protected int correctType(int type)
	{
		return type;
	}

	/**
	 *
	 */
	public synchronized void addAfterCloseStatement(String statement)
	{
		if (mAfterCloseStatement == null) mAfterCloseStatement = new Vector(1);

		mAfterCloseStatement.add(statement);
	}

	/**
	 * execute some statement or other activity after result set closing
	 */
	protected void executeAfterClose()
	{
		if (mAfterCloseStatement == null) return;
		//starting thread
		new Thread()
		{
			public void run()
			{
				int size = mAfterCloseStatement.size();

				VDbConnection conn = null;
				try
				{
					conn = VDbConnectionManager.getVDbConnection();

					for (int i = 0; i < size; i++)
					{
						conn.execute(VDbConnectionManager.USERS__DEFAULT_SYS_USER,
										(String) mAfterCloseStatement.get(i));
					}
				}
				catch (Exception e)
				{
				}
				finally
				{
					if (conn != null) conn.close();
				}
			}
		}.start();
	}

	public void setEliminateLineBreaks(boolean mEliminateLineBreaks)
	{
		this.mEliminateLineBreaks = mEliminateLineBreaks;
	}

	public static String eliminateLineBreaks(String src)
	{
		return src == null ? null : src.replace('\n', ' ').replace('\r', ' ');
	}
}
