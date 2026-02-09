package net.sam.dcl.db;

import net.sam.dcl.db.*;

import java.sql.Types;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * This class is intended for adduction to normal type of string SQL.
 *
 * @author A.M.
 * @version 1.0
 */
public class SQLStmtNormalizer {

	/**
	 * Create normal SQL and parameters for bind server of database i.e. occurs
	 * change string of name type ":ABC1" on question mark "?". For instance,
	 * line "SELECT * FROM Table WHERE ID = :ID" after calling the method
	 * SQLStmtNormalizer.normalizeSQL() will take the type "SELECT * FROM Table WHERE ID = ?".
	 *
	 * @param sql	 Typically this is a static SQL SELECT statement.
	 * @param param Vector of parameters.
	 * @return String of SQL.
	 * @throws SQLException If a database access error occurs.
	 */
	public static void normalizeSQL(VDbExecutionContext executionContext) throws VDbException {
		executionContext.setSql(normalizeSQL(executionContext.getSql(), executionContext.getParam()));
	}

	public static String normalizeSQL(String sql, VParameter param) throws VDbException {
		VParameter.Element[] elements = param.getParamElements();
		VParameter.Element element = null;
		String sqlNormal = new String("");
		String paramName = new String("");
		int sqlLength = sql.length();
		char ch;
		boolean isParamReseted = false;


		for (int i = 0; i < sqlLength;) {
			ch = sql.charAt(i++);

			if (ch == '\'') {
				while (true) {
					sqlNormal = sqlNormal + String.valueOf(ch);
					if (i >= sqlLength) break;
					ch = sql.charAt(i++);
					if (ch == '\'') {
						sqlNormal = sqlNormal + String.valueOf(ch);
						break;
					}
				}
			}
			//original
			//else if (ch == ':') {
			else if (ch == ':' || ch == '&') {
				if (i >= sqlLength) {
					sqlNormal = sqlNormal + String.valueOf(ch);
					break;
				}
				ch = sql.charAt(i++);

				if (ch == '=') {
					sqlNormal = sqlNormal + ":" + String.valueOf(ch);
				} else {
					if (!isParamReseted) {
						isParamReseted = true;
						param.reset();
					}
					sqlNormal = sqlNormal + "?";
					paramName = new String("");

					while (true) {
						//original
						//if ((" (){}[]!@#$%^&|\\/,.~'`;?=><\t\n\r,+-*:").indexOf(String.valueOf(ch)) != -1) {
						if ((" (){}[]!@#$%^&|\\/,~'`;?=><\t\n\r,+*:").indexOf(String.valueOf(ch)) != -1) {
							sqlNormal = sqlNormal + String.valueOf(ch);
							break;
						}
						paramName = paramName + String.valueOf(ch);
						if (i >= sqlLength) break;
						ch = sql.charAt(i++);
					}

					try {
						boolean equals = false;
						if (paramName.matches("(\\d)+")) {
							equals = true;
							element = elements[Integer.parseInt(paramName) - 1];
						} else {
						for (int j = 0; j < elements.length; j++) {
							element = elements[j];
								if (element.mParamName != null) {
							if (element.mParamName.equalsIgnoreCase(paramName)) {
								equals = !equals;
								break;
							}
						}
							}
						}
						if (!equals) throw new Exception();
					}
					catch (Exception e) {
						throw new VDbException(
								SQLStmtNormalizer.class + " -> " + "normalizeSQL()" + " -> " + "bad name of parameter [:" + paramName + "].");
					}
					if (element.mParamOut == true) {
						param.addOut((StringValueHolder) element.mOutParamVal, element.mParamType);
					} else {
						param.add(element.mParamVal, element.mParamType);
					}
				}
			} else {
				sqlNormal = sqlNormal + String.valueOf(ch);
			}
		}

//    System.out.println(sqlNormal);
//    System.exit(0);
		return (sqlNormal);
	}

	/**
	 * Create normal SQL and parameters for bind server of database i.e. occurs
	 * change string of name type "{field}" on question mark "?". For instance,
	 * line "SELECT {Field} FROM {Table} WHERE {Cond}" after calling the method
	 * SQLStmtNormalizer.normalizeSQL() will take the type
	 * "SELECT id FROM work WHERE id = 1".
	 *
	 * @param sql	Typically this is a static SQL SELECT statement.
	 * @param prop Properties of parameters.
	 * @return String of SQL.
	 */
	public static String replaceMacros(String sql, java.util.Properties prop) /*throws java.sql.SQLException*/ {
		String sqlNormal = new String("");
		String paramName = new String("");
		String paramValue = new String("");
		int sqlLength = sql.length();
		char ch;

		for (int i = 0; i < sqlLength;) {
			ch = sql.charAt(i++);

			if (ch == '\'') {
				while (true) {
					sqlNormal = sqlNormal + String.valueOf(ch);
					if (i >= sqlLength) break;
					ch = sql.charAt(i++);
					if (ch == '\'') {
						sqlNormal = sqlNormal + String.valueOf(ch);
						break;
					}
				}
			} else if (ch == '{') {
				while (true) {
					if (i >= sqlLength) {
						sqlNormal = sqlNormal + "{" + paramName;
						break;
					}
					ch = sql.charAt(i++);
					if (ch == '}') {
						paramValue = prop.getProperty(paramName.trim(), "");
						sqlNormal = sqlNormal + paramValue;
						paramName = new String("");
						break;
					}
					paramName = paramName + String.valueOf(ch);
				}
			} else {
				sqlNormal = sqlNormal + String.valueOf(ch);
			}
		}

		return (sqlNormal);
	}
	public static String replaceMacros2(String sql, Object obj) throws Exception {
		String sqlNormal = new String("");
		String paramName = new String("");
		String paramValue = new String("");
		int sqlLength = sql.length();
		char ch;

		for (int i = 0; i < sqlLength;) {
			ch = sql.charAt(i++);

			if (ch == '\'') {
				while (true) {
					sqlNormal = sqlNormal + String.valueOf(ch);
					if (i >= sqlLength) break;
					ch = sql.charAt(i++);
					if (ch == '\'') {
						sqlNormal = sqlNormal + String.valueOf(ch);
						break;
					}
				}
			} else if (ch == '{') {
				while (true) {
					if (i >= sqlLength) {
						sqlNormal = sqlNormal + "{" + paramName;
						break;
					}
					ch = sql.charAt(i++);
					if (ch == '}') {
						if (PropertyUtils.isReadable(obj,paramName.trim())){
							Object tmp = PropertyUtils.getProperty(obj,paramName.trim());
							paramValue = String.valueOf(tmp==null?"":tmp);
						} else {
							paramValue = "";
						}
						sqlNormal = sqlNormal + paramValue;
						paramName = new String("");
						break;
					}
					paramName = paramName + String.valueOf(ch);
				}
			} else {
				sqlNormal = sqlNormal + String.valueOf(ch);
			}
		}

		return (sqlNormal);
	}

	public static void main(String[] args) throws VDbException {
		VParameter param = new VParameter();
		//param.add("1",Types.NUMERIC);
		//param.add("2",Types.NUMERIC);
		//param.add("test","test",Types.VARCHAR);
		param.add("test1", Types.VARCHAR);
		param.addOut(new StringValueHolder(), Types.VARCHAR);
		param.add("2.33", Types.NUMERIC);
		param.add("test3", Types.DATE);
		param.add("test4", Types.TIME);
		param.add("test5", Types.TIMESTAMP);
		param.add("test6", Types.BIT);

		//String sqlNormal = new String("?''?");
		String sqlNormal = "'?'?'?'?'?''?'?'?'? ? ? ?";

		//String sqlNormal = new String(":1 :test :2 :1 ");
		//String sqlNormal = "0'a'1";

		//String ret = normalizeSQL(sqlNormal,param);
		String ret = VDbConnection.substituteParams(sqlNormal, param);

		System.out.println(ret);
	}

}
