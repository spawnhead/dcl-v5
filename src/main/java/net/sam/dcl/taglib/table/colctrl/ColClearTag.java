package net.sam.dcl.taglib.table.colctrl;

import org.apache.log4j.Logger;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColClearTag extends ColImageTag {
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColClearTag.class);
  /**
   * Constructor.
   */

  public ColClearTag() {
    type=DIALOG;
    image="images/del.gif";
  }

  /**
   * Release any acquired resources.
   */
  public void release() {
    super.release();
  }
}