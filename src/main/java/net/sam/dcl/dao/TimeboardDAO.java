package net.sam.dcl.dao;

import net.sam.dcl.beans.Timeboard;
import net.sam.dcl.beans.TimeboardWork;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.controller.IActionContext;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class TimeboardDAO
{

  public static Timeboard load(IActionContext context, String id) throws Exception
  {
    Timeboard timeboard = new Timeboard(id);

    if (load(context, timeboard))
    {
      timeboard.setUsr_date_create(StringUtil.dbDateString2appDateString(timeboard.getUsr_date_create()));
      timeboard.setUsr_date_edit(StringUtil.dbDateString2appDateString(timeboard.getUsr_date_edit()));

      timeboard.setTmb_date(StringUtil.dbDateString2appDateString(timeboard.getTmb_date()));
      timeboard.setTmb_checked_date(StringUtil.dbDateString2appDateString(timeboard.getTmb_checked_date()));

      loadWorks(context, timeboard);
      return timeboard;
    }
    throw new LoadException(timeboard, id);
  }

  public static boolean load(IActionContext context, Timeboard timeboard) throws Exception
  {
    if (DAOUtils.load(context, "timeboard-load", timeboard, null))
    {
      if (!StringUtil.isEmpty(timeboard.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, timeboard.getCreateUser());
      }
      if (!StringUtil.isEmpty(timeboard.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, timeboard.getEditUser());
      }
      if (!StringUtil.isEmpty(timeboard.getUser().getUsr_id()))
      {
        UserDAO.load(context, timeboard.getUser());
      }
      if (!StringUtil.isEmpty(timeboard.getCheckedUser().getUsr_id()))
      {
        UserDAO.load(context, timeboard.getCheckedUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadByUsrIdTmbDate(IActionContext context, Timeboard timeboard) throws Exception
  {
    DAOUtils.load(context, "timeboard-load-by_usr_id_tmb_date", timeboard, null);
  }

  public static void loadWorks(IActionContext context, Timeboard timeboard) throws Exception
  {
    List<TimeboardWork> lst = DAOUtils.fillList(context, "select-timeboard_works", timeboard, TimeboardWork.class, null, null);
    timeboard.setWorks(lst);
  }

  public static void insert(IActionContext context, Timeboard timeboard) throws Exception
  {
    DAOUtils.load(context, "timeboard-insert", timeboard, null);
    timeboard.setListParentIds();
    timeboard.setListIdsToNull();
    saveWorks(context, timeboard);
  }

  public static void save(IActionContext context, Timeboard timeboard) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("timeboard-update"), timeboard, null);
    timeboard.setListParentIds();
    timeboard.setListIdsToNull();
    saveWorks(context, timeboard);
  }

  public static void saveWorks(IActionContext context, Timeboard timeboard) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_timeboard_works"), timeboard, null);
    for (int i = 0; i < timeboard.getWorks().size() - timeboard.getCountItogRecord(); i++)
    {
      TimeboardWork timeboardWork = timeboard.getWorks().get(i);
      DAOUtils.update(conn, context.getSqlResource().get("insert_timeboard_work"), timeboardWork, null);
    }
  }

  public static void saveChecked(IActionContext context, Timeboard timeboard) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("timeboard-update-checked"), timeboard, null);
  }
}