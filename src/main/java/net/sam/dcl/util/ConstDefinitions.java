/**
 * Title:        SRM Project<p>
 * Description:  SRM: Supplier-Relationship-Management<p>
 * Copyright:    Copyright (c) A.K<p>
 * Company:      <p>
 * @author A.K
 * @version 1.0
 */
package net.sam.dcl.util;

/**
 * defines common project constants
 */
public final class ConstDefinitions {

	/**
	 * Diagnostics
	 */

	public final static String STATEMENT_TO_LOG = "log";
	public final static String AJAX_REQUEST_ID = "__AJAX_REQUEST_ID__";
	public final static String AJAX_RESPONSE = "__AJAX_RESPONSE__";


	/**
	 * Error definitions
	 */

	public final static String ERROR__UNKNOWN = "ERRUNKN";
	public final static String ERROR__UNIQUE = "ERRUNIQ";
	public final static String ERROR__INTEGRITY = "ERRINTEG";
	//the count of updated or deleted or inserted rows equals 0
	public final static String ERROR__COUNT_ROWS = "ERRCOUNT";
	//No more rows in result set
	public final static String ERROR__RSET_EMPTY = "ERREMPTY";
	//Connection problem
	public final static String ERROR__NOTDBCONNECTION = "ERRCONN";
	/**
	 * Other definitions
	 */

	public final static char ESPECIAL_SYMBOL = '|';
	public final static String ROW_DELIM = "\n";
	public final static String COL_DELIM = "|";
	public final static char INNER_PARAM_DELIM = ESPECIAL_SYMBOL;
	public final static char ERROR_DELIM = ESPECIAL_SYMBOL;
	public final static String VALUE_DELIM = "=";
	public final static String[] SECTION_SIGN = {"[", "]"};
	public final static String ERROR__BAD_SEARCH_CRITERIA = "ERRCRIT";

	public static int templateIdCP = 1;
	public static int templateIdCFC = 2;
	public static int templateIdOrder = 3;
	public static int templateIdNomenclature = 4;
}