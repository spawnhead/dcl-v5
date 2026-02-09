package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class TimeboardsForm extends JournalForm
{
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();
  String tmb_id;
  String tmb_date;

  public String getTmb_id()
  {
    return tmb_id;
  }

  public void setTmb_id(String tmb_id)
  {
    this.tmb_id = tmb_id;
  }

  public String getTmb_date()
  {
    return tmb_date;
  }

  public String getTmb_date_ts()
  {
    return StringUtil.appDateString2dbDateString(StringUtil.appFullMonthYearString2appDate(tmb_date));
  }

  public void setTmb_date(String tmb_date)
  {
    this.tmb_date = tmb_date;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class Timeboard
  {
    String tmb_id;
    String tmb_date;
    String tmb_user;
    String tmb_checked;

    public String getTmb_id()
    {
      return tmb_id;
    }

    public void setTmb_id(String tmb_id)
    {
      this.tmb_id = tmb_id;
    }

    public String getTmb_date()
    {
      return tmb_date;
    }

    public void setTmb_date(String tmb_date)
    {
      this.tmb_date = tmb_date;
    }

    public String getTmb_date_formatted()
    {
      return StringUtil.dbDateString2appFullMonthYear(tmb_date);
    }

    public String getTmb_user()
    {
      return tmb_user;
    }

    public void setTmb_user(String tmb_user)
    {
      this.tmb_user = tmb_user;
    }

    public String getTmb_checked()
    {
      return tmb_checked;
    }

    public void setTmb_checked(String tmb_checked)
    {
      this.tmb_checked = tmb_checked;
    }
  }

}