package net.sam.dcl.taglib.table.model;



/**
 * DataHolder interface. Used by TableTag to show his data
 * @author DG
 */
public interface DataHolder {

  /**
   * Go to next.
   * @return next
   */
  public boolean next();
  /**
   * Go to next.
   * @return next
   */
  public boolean hasNext();

  /**
   * Get current Row object(bean).
   * @return current
   */
  public Object getCurrentRow();


}
