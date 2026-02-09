package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

import java.util.List;

public class ReservedInfoDAO
{
  public static List<ReservedInfo> loadInfo(IActionContext context, String cprId, String lpcId) throws Exception
  {
    ReservedInfo reservedInfo = new ReservedInfo(cprId, lpcId);
    return DAOUtils.fillList(context, "load-reserved_info", reservedInfo, ReservedInfo.class, null, null);
  }
}