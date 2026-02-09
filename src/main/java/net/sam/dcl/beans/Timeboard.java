package net.sam.dcl.beans;

import java.util.*;
import java.io.Serializable;
import java.lang.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Timeboard implements Serializable
{
  protected static Log log = LogFactory.getLog(Timeboard.class);

  String is_new_doc;
  String tmb_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  User user = new User();
  String tmb_date;
  String tmb_checked;
  String tmb_checked_date;
  User checkedUser = new User();

  List<TimeboardWork> works = new ArrayList<TimeboardWork>();
  int countItogRecord;

  public Timeboard()
  {
  }

  public Timeboard(String tmb_id)
  {
    this.tmb_id = tmb_id;
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getTmb_id()
  {
    return tmb_id;
  }

  public void setTmb_id(String tmb_id)
  {
    this.tmb_id = tmb_id;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public String getUsr_date_create()
  {
    return usr_date_create;
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public String getTmb_date()
  {
    return tmb_date;
  }

  public String getTmb_date_ts()
  {
    return StringUtil.appDateString2dbDateString(tmb_date);
  }

  public void setTmb_date(String tmb_date)
  {
    this.tmb_date = tmb_date;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getTmb_checked()
  {
    return tmb_checked;
  }

  public void setTmb_checked(String tmb_checked)
  {
    this.tmb_checked = tmb_checked;
  }

  public String getTmb_checked_date()
  {
    return tmb_checked_date;
  }

  public void setTmb_checked_date(String tmb_checked_date)
  {
    this.tmb_checked_date = tmb_checked_date;
  }

  public User getCheckedUser()
  {
    return checkedUser;
  }

  public void setCheckedUser(User checkedUser)
  {
    this.checkedUser = checkedUser;
  }

  public List<TimeboardWork> getWorks()
  {
    return works;
  }

  public void setWorks(List<TimeboardWork> works)
  {
    this.works = works;
  }

  public int getCountItogRecord()
  {
    return countItogRecord;
  }

  public void setCountItogRecord(int countItogRecord)
  {
    this.countItogRecord = countItogRecord;
  }

  public static TimeboardWork getEmptyWork()
  {
    TimeboardWork timeboardWork = new TimeboardWork();
    timeboardWork.setTbw_id("");
    timeboardWork.setTmb_id("");
    timeboardWork.setNumber("");
    timeboardWork.setTbw_date("");
    timeboardWork.setTbw_from("");
    timeboardWork.setTbw_to("");
    timeboardWork.setTbw_hours_update(0.0);
    timeboardWork.setTbw_comment("");

    return timeboardWork;
  }

  // defines ordering based on date
  class customCodeComparator implements Comparator<TimeboardWork>
  {
    public int compare(TimeboardWork timeboardWork1, TimeboardWork timeboardWork2)
    {
      if (StringUtil.isEmpty(timeboardWork1.getTbw_date())) return -1;
      if (StringUtil.isEmpty(timeboardWork2.getTbw_date())) return 1;
      int result;
      Date date1 = StringUtil.getCurrentDateTime();
      Date date2 = StringUtil.getCurrentDateTime();
      try
      {
        date1 = StringUtil.appDateString2Date(timeboardWork1.getTbw_date_formatted());
        date2 = StringUtil.appDateString2Date(timeboardWork2.getTbw_date_formatted());
      }
      catch (Exception e)
      {
        log.error(e.getMessage(), e);
      }
      result = date1.compareTo(date2);
      return result;
    }
  }

  public void calculate()
  {
    if ( countItogRecord > 0 )
    {
      for (int i = 0; i < countItogRecord; i++)
      {
        works.remove(works.size() - 1);
      }
    }
    countItogRecord = 0;

    Collections.sort(works, new customCodeComparator());

    double hoursAll = 0.0;
    double hoursAllCorrect = 0.0;
    double hoursTotal = 0.0;
    double hoursTotalCorrect = 0.0;

    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);

      //считаем данные в строках
      timeboardWork.getTbw_hours_all_formatted();
      timeboardWork.getTbw_hours_total_formatted();

      hoursAll += timeboardWork.getTbw_hours_all();
      hoursAllCorrect += timeboardWork.getTbw_hours_all_correct();
      hoursTotal += timeboardWork.getTbw_hours_total();
      hoursTotalCorrect += timeboardWork.getTbw_hours_total_correct();

      timeboardWork.setNumber((new Integer(i + 1)).toString());
    }

    IActionContext context = ActionContext.threadInstance();
    TimeboardWork timeboardWork = getEmptyWork();
    try
    {
      timeboardWork.setTbw_date(StrutsUtil.getMessage(context, "TimeboardWorks.itog"));
      timeboardWork.setTbw_hours_all_out(StringUtil.double2appCurrencyString(hoursAll) + " ( " + StringUtil.double2appCurrencyString(hoursAllCorrect) + " )");
      timeboardWork.setTbw_hours_total_out(StringUtil.double2appCurrencyString(hoursTotal) + " ( " + StringUtil.double2appCurrencyString(hoursTotalCorrect) + " )");
      timeboardWork.setItogLine(true);
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    works.add(timeboardWork);

    countItogRecord = 1;
  }

  public void setListParentIds()
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);

      timeboardWork.setTmb_id(getTmb_id());
    }
  }

  public void setListIdsToNull()
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);

      timeboardWork.setTbw_id(null);
    }
  }

  public TimeboardWork findWork(String number)
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);

      if (timeboardWork.getNumber().equalsIgnoreCase(number))
        return timeboardWork;
    }

    return null;
  }

  public void updateWork(String number, TimeboardWork timeboardWorkIn)
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);

      if (timeboardWork.getNumber().equalsIgnoreCase(number))
      {
        works.set(i, timeboardWorkIn);
        return;
      }
    }
  }

  public void deleteWork(String number)
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);

      if (timeboardWork.getNumber().equalsIgnoreCase(number))
      {
        works.remove(i);
        break;
      }
    }
  }

  public void insertWork(TimeboardWork timeboardWorkIn)
  {
    works.add(works.size() - countItogRecord, timeboardWorkIn);
  }

  public boolean haveSelected()
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);
      if ( !StringUtil.isEmpty(timeboardWork.getSelectLine()) )
      {
        return true;
      }
    }

    return false;
  }

  public void importFromContractorRequest(ContractorRequest contractorRequest)
  {
    for (int i = 0; i < works.size() - countItogRecord; i++)
    {
      TimeboardWork timeboardWork = works.get(i);
      if ( !StringUtil.isEmpty(timeboardWork.getSelectLine()) )
      {
        timeboardWork.setContractorRequest(contractorRequest);
        timeboardWork.setSelectLine("");
      }
    }
  }
}