package net.sam.dcl.taglib.table.model;

/**
 * Grid data sortable interface.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public interface DataHolderWithPk extends DataHolder {
  void setPk(String pk);
  Object getRowByPk(String pkValue) throws Exception;
}
