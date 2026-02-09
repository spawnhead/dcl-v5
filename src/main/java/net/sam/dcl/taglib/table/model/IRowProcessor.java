/**
 * Created by IntelliJ IDEA.
 * User: paa
 * Date: Apr 11, 2005
 * Time: 10:51:12 AM
 * To change this template use Options | File Templates.
 */
package net.sam.dcl.taglib.table.model;

public interface IRowProcessor {
    boolean process(Object bean) throws Exception;
}
