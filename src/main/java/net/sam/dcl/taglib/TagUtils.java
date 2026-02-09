package net.sam.dcl.taglib;

import net.sam.dcl.util.StringUtil;

public class TagUtils
{
  public static String getEnterPressStr(String styleClass, String enterDispatch)
  {
    String res = "";
    String enterDispatchStr = "";
    if ( "filter".equals(styleClass) )
    {
      enterDispatchStr = "filter";
    }
    if ( !StringUtil.isEmpty(enterDispatch) )
    {
      enterDispatchStr = enterDispatch;
    }
    if ( !StringUtil.isEmpty(enterDispatchStr) )
    {
      res = "javascript:checkEnterPress(event.keyCode, '" + enterDispatchStr + "'); return true;";
    }

    return res;
  }
}
