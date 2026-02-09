package net.sam.dcl.dao;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.util.DAOUtils;

public class CommonDAO
{
  public static String GetNumber(IActionContext context, String sqlString) throws Exception
  {
    VDbConnection conn = null;
    try
    {
      GetNumber getNumber = new GetNumber();
      conn = VDbConnectionManager.getVDbConnection();
      DAOUtils.load(conn, context, sqlString, getNumber, null);
      return getNumber.getNum();
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      if (conn != null) conn.close();
    }
  }

  public static class GetNumber
  {
    String num;

    public GetNumber()
    {
    }

    public String getNum()
    {
      return num;
    }

    public void setNum(String num)
    {
      this.num = num;
    }
  }
}
