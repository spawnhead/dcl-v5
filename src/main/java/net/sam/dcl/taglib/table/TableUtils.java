package net.sam.dcl.taglib.table;

/**
 * Utils.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class TableUtils
{
  public static String getStyleString(Styleable elem)
  {
    String res = "";
    if (elem.getStyle() != null)
    {
      res += " style=\"" + elem.getStyle() + "\" ";
    }
    if (elem.getStyleId() != null)
    {
      res += " id=\"" + elem.getStyleId() + "\" ";
    }
    if (elem.getStyleClass() != null || elem.getStyleClassValue() != null)
    {
      res += " class=\"" + elem.getStyleClassValue() + "\" ";
    }

    return res;
  }

  public static String getStyleString(RowTag rowTag, TableTag tableTag)
  {
    String res = getStyleString((Styleable) rowTag);
    if (tableTag.isExpandableGroup())
    {
      if ("1".equals(rowTag.curExpandState))
      {
        res += " style=\"display:none\" ";
      }
      else
      {
        res += " style=\"display:block\" ";
      }
    }
    return res;
  }
}
