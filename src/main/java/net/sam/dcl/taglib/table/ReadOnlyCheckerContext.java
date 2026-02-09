package net.sam.dcl.taglib.table;



/**
 * Checker context.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ReadOnlyCheckerContext {
  /**
   * Bean.
   */
  Object bean;
  TableTag table;

  /**
   * Get bean.
   *
   * @return bean
   */
  public Object getBean() {
    return bean;
  }

  /**
   * Set bean.
   *
   * @param bean bean
   */
  public void setBean(Object bean) {
    this.bean = bean;
  }

  public TableTag getTable() {
    return table;
  }
  public void setTable(TableTag table) {
    this.table = table;
  }
}
