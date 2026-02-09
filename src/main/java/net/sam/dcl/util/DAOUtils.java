package net.sam.dcl.util;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.*;
import net.sam.dcl.taglib.table.model.IRowProcessor;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.config.Config;
import net.sam.dcl.log.Log;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: Apr 6, 2005
 * Time: 12:54:45 PM
 */
public class DAOUtils
{
  public final static int DEF_PAGE_SIZE = 15;

  static public boolean load(IActionContext context, String sqlId, VParameter param) throws Exception
  {
    return load(context.getConnection(), context.getSqlResource().get(sqlId), context.getForm(), param);
  }

  static public boolean load(IActionContext context, String sqlId, Object object, VParameter param) throws Exception
  {
    return load(context.getConnection(), context.getSqlResource().get(sqlId), object, param);
  }

  static public boolean load(VDbConnection conn, IActionContext context, String sqlId, Object object, VParameter param) throws Exception
  {
    return load(conn, context.getSqlResource().get(sqlId), object, param);
  }

  static public boolean load(VDbConnection conn, String sql, Object object, VParameter param) throws Exception
  {
    sql = SQLProcessor.replaceMacros(sql, object);
    if (param == null)
    {
      param = new VParameter();
    }
    SQLProcessor.addParams(sql, param, object);
    VResultSet resultSet = conn.executeQuery(sql, param);
    if (resultSet.next())
    {
      loadObject(resultSet, object);
      return true;
    }
    return false;
  }

  static public int update(IActionContext context, String sqlId, VParameter param) throws Exception
  {
    return update(context.getConnection(), context.getSqlResource().get(sqlId), context.getForm(), param);
  }

  static public int update(VDbConnection conn, String sql, Object object, VParameter param) throws Exception
  {
    sql = SQLProcessor.replaceMacros(sql, object);
    if (param == null) param = new VParameter();
    SQLProcessor.addParams(sql, param, object);
    return conn.executeUpdate(sql, param);
  }

  static public VResultSet executeQuery(IActionContext context, String sqlId, VParameter param) throws Exception
  {
    return executeQuery(context.getConnection(), context.getSqlResource().get(sqlId), context.getForm(), param);
  }

  static public VResultSet executeQuery(IActionContext context, String sqlId, Object object, VParameter param) throws Exception
  {
    return executeQuery(context.getConnection(), context.getSqlResource().get(sqlId), object, param);
  }

  static public VResultSet executeQuery(VDbConnection conn, String sql, Object object, VParameter param) throws Exception
  {
    sql = SQLProcessor.replaceMacros(sql, object);
    if (param == null)
    {
      param = new VParameter();
    }
    SQLProcessor.addParams(sql, param, object);

    long t0 = System.currentTimeMillis();
    VResultSet rs = conn.executeQuery(sql, param);
    if (Config.getNumber("log.sql.timing", 0) == 1)
    {
      long t1 = System.currentTimeMillis();
      String preview = sql != null && sql.length() > 120 ? sql.substring(0, 120) + "..." : (sql != null ? sql : "");
      Log.info("DAO executeQuery " + (t1 - t0) + " ms | " + preview.replace('\n', ' '), "DAOUtils");
    }
    return rs;
  }

  static public List<String> resultSet2StringList(VResultSet resultSet) throws VDbException
  {
    ArrayList list = new ArrayList();
    while (resultSet.next())
    {
      list.add(resultSet.getData(1));
    }
    return list;
  }

  static public List resultSet2List(VResultSet resultSet, Class clazz, IRowProcessor rowProcessor) throws Exception
  {
    return resultSet2List(resultSet, clazz, rowProcessor, 1, 0);
  }

  static public List resultSet2List(VResultSet resultSet, Class clazz, IRowProcessor rowProcessor, int startPage, int pageSize) throws Exception
  {
    if (pageSize != 0)
    {
      resultSet.setPageSize(pageSize);
      resultSet.setFetchSize(pageSize);
    }
    // Вызываем setRow() только если нужно начать не с первой строки
    int startRow = (startPage - 1) * pageSize + 1;
    if (startRow > 1)
    {
      resultSet.setRow(startRow);
    }

    List list = new ArrayList();
    int counter = 0;
    // if pageSize == 0 fetch all records and we'll start from first one
    try
    {
      while ((pageSize == 0 || counter++ < pageSize) && resultSet.next())
      {
        Object bean = clazz.newInstance();
        loadObject(resultSet, bean);
        if (rowProcessor != null)
        {
          if (rowProcessor.process(bean)) list.add(bean);
        }
        else
        {
          list.add(bean);
        }
      }
    }
    catch (VDbException e)
    {
      // Если ResultSet закрылся во время чтения, возвращаем уже прочитанные данные
      if (isResultSetClosed(e))
      {
        // Возвращаем частично прочитанные данные
        return list;
      }
      throw e;
    }
    return list;
  }

  public static void loadObject(VResultSet resultSet, Object obj) throws Exception
  {
    int columnCount = resultSet.getResultColumns();
    for (int i = 0; i < columnCount; i++)
    {
      String column = resultSet.getColumnLabel(i + 1);
      if (column == null || column.length() == 0)
      {
        column = resultSet.getColumnName(i + 1);
      }
      if (column == null || column.length() == 0) continue;
      if ("_p_first".equalsIgnoreCase(column) || "_p_skip".equalsIgnoreCase(column))
        continue;
      try
      {
        if (obj instanceof Setable)
        {
          ((Setable) obj).set(column, resultSet.getData(i + 1));
        }
        else
        {
          BeanUtils.setProperty(obj, column, resultSet.getData(i + 1));
          BeanUtils.setProperty(obj, column.toLowerCase(), resultSet.getData(i + 1));
        }
      }
      catch (Exception e)
      {
        if (e.getMessage() != null && (e.getMessage().contains("Unknown property") || e.getMessage().contains("No such property")))
          continue;
        throw e;
      }
    }
  }

  public static void fillGrid(HolderImplUsingList grid, String sql, Object filledObject, Class dataClass, IRowProcessor rowProcessor, VParameter param) throws Exception
  {
    VDbConnection conn = null;
    VResultSet resultSet = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      resultSet = DAOUtils.executeQuery(conn, sql, filledObject, param);
      grid.setDataList(DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor));
    }
    catch (VDbException e)
    {
      if (isResultSetClosed(e))
      {
        // повтор: иногда драйвер закрывает результат при долгих выборках
        if (resultSet != null) resultSet.close();
        if (conn != null)
        {
          conn.close();
          conn = VDbConnectionManager.getVDbConnection();
        }
        resultSet = DAOUtils.executeQuery(conn, sql, filledObject, param);
        grid.setDataList(DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor));
      }
      else
      {
        throw e;
      }
    }
    finally
    {
      // Явно закрываем ResultSet перед закрытием соединения
      if (resultSet != null)
      {
        try
        {
          resultSet.close();
        }
        catch (Exception e)
        {
          // Игнорируем ошибки при закрытии ResultSet
        }
      }
      if (conn != null)
      {
        conn.close();
      }
    }

  }

  public static void fillGrid(IActionContext context, HolderImplUsingList grid, String sqlId, Class dataClass, IRowProcessor rowProcessor, VParameter param) throws Exception
  {
    VResultSet resultSet = null;
    try
    {
      resultSet = DAOUtils.executeQuery(context, sqlId, param);
      grid.setDataList(DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor));
    }
    catch (VDbException e)
    {
      if (isResultSetClosed(e))
      {
        // повтор: иногда драйвер закрывает результат при долгих выборках
        if (resultSet != null) resultSet.close();
        resultSet = DAOUtils.executeQuery(context, sqlId, param);
        grid.setDataList(DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor));
      }
      else
      {
        throw e;
      }
    }
    finally
    {
      if (resultSet != null) resultSet.close();
    }
  }

  public static void fillGrid(IActionContext context, PageableHolderImplUsingList grid, String sqlId, Class dataClass, IRowProcessor rowProcessor, VParameter param) throws Exception
  {
    VResultSet resultSet = null;
    try
    {
      resultSet = DAOUtils.executeQuery(context, sqlId, param);
      int pageSize = Config.getNumber("table.pageSize", DEF_PAGE_SIZE);
      boolean serverSidePaging = "select-contractors".equals(sqlId);
      int startPage = serverSidePaging ? 1 : grid.getPage();
      grid.setDataList(DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor, startPage, pageSize));
      grid.setHasNextPage(resultSet.next());
    }
    catch (VDbException e)
    {
      if (isResultSetClosed(e))
      {
        if (resultSet != null) resultSet.close();
        resultSet = DAOUtils.executeQuery(context, sqlId, param);
        int pageSize = Config.getNumber("table.pageSize", DEF_PAGE_SIZE);
        boolean serverSidePaging = "select-contractors".equals(sqlId);
        int startPage = serverSidePaging ? 1 : grid.getPage();
        grid.setDataList(DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor, startPage, pageSize));
        grid.setHasNextPage(resultSet.next());
      }
      else
      {
        throw e;
      }
    }
    finally
    {
      if (resultSet != null) resultSet.close();
    }
  }

  private static boolean isResultSetClosed(VDbException e)
  {
    String msg = e != null ? e.getMessage() : null;
    return msg != null && msg.toLowerCase().contains("result set is closed");
  }

  public static <T> List<T> fillList(IActionContext context, String sqlId, Object filledObject, Class<T> dataClass, IRowProcessor rowProcessor, VParameter param) throws Exception
  {
    VResultSet resultSet = executeQuery(context.getConnection(), context.getSqlResource().get(sqlId), filledObject, param);
    return DAOUtils.resultSet2List(resultSet, dataClass, rowProcessor);
  }

}
