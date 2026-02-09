package net.sam.dcl.taglib.table;

/**
 * Stylable.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public interface Styleable {
  /**
   * Get style.
   *
   * @return style
   */
  public String getStyle();

  /**
   * Set style.
   *
   * @param style style
   */
  public void setStyle(String style);

  /**
   * Get style class.
   *
   * @return style class
   */
  public String getStyleClass();

  /**
   * Get style class.
   *
   * @param styleClass styleClass
   */
  public void setStyleClass(String styleClass);

  /**
   * Get style id.
   *
   * @return style id
   */
  public String getStyleId();

  /**
   * Set style id.
   *
   * @param styleId styleId
   */
  public void setStyleId(String styleId);


  public String getStyleClassValue();
}
