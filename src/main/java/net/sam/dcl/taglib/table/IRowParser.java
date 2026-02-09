/**
 * Created by IntelliJ IDEA.
 * User: paa
 * Date: Apr 11, 2005
 * Time: 10:51:12 AM
 * To change this template use Options | File Templates.
 */
package net.sam.dcl.taglib.table;

public interface IRowParser {
    static public int SKIP_ROW = 0;
    static public int EVAL_ROW_INCLUDE = 1;
    static public int EVAL_ROW_AGAIN = 2;
    int parse(RowParserContext rpContext) throws Exception;
}
