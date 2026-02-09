package net.sam.dcl.taglib.table.colctrl;

import org.apache.log4j.Logger;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColEditTag extends ColImageTag {
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColEditTag.class);
  /**
   * Constructor.
   */

  public ColEditTag() {
    type=DIALOG;
    image="images/opn.gif";
  }

  /**
   * Release any acquired resources.
   */
  public void release() {
    super.release();
  }
}