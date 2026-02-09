/**
 * Created by IntelliJ IDEA.
 * User: paa
 * Date: Apr 25, 2005
 * Time: 5:56:23 PM
 * To change this template use Options | File Templates.
 */
package net.sam.dcl.taglib.table;

public class RowParserContext {
    Object bean;
    StringBuffer extraRow;
    StringBuffer extraRowBefore;
    RowTag rowTag;

    public RowParserContext(Object bean, StringBuffer extraRow) {
        this.bean = bean;
        this.extraRow = extraRow;
    }

    public RowParserContext(Object bean, StringBuffer extraRow, RowTag rowTag) {
        this.bean = bean;
        this.extraRow = extraRow;
        this.rowTag = rowTag;
    }

    public RowParserContext(Object bean, StringBuffer extraRow, StringBuffer extraRowBefore, RowTag rowTag) {
        this.bean = bean;
        this.extraRow = extraRow;
        this.extraRowBefore = extraRowBefore;
        this.rowTag = rowTag;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public StringBuffer getExtraRow() {
        return extraRow;
    }

    public void setExtraRow(StringBuffer extraRow) {
        this.extraRow = extraRow;
    }

    public StringBuffer getExtraRowBefore() {
        return extraRowBefore;
    }

    public void setExtraRowBefore(StringBuffer extraRowBefore) {
       this.extraRowBefore = extraRowBefore;
    }

    public RowTag getRowTag() {
        return rowTag;
    }

    public void setRowTag(RowTag rowTag) {
        this.rowTag = rowTag;
    }
}
