package net.sam.dcl.taglib.table.colctrl;

import org.apache.log4j.Logger;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColDeleteTag extends ColImageTag
{
  /**
   * Reference to logger.
   */
  static Logger LOGGER = Logger.getLogger(ColDeleteTag.class);

  /**
   * Constructor.
   */
  public ColDeleteTag()
  {
    type = LINK;
    image = "images/sub.gif";
  }

  /**
   * Release any acquired resources.
   */
  public void release()
  {
    super.release();
  }
}