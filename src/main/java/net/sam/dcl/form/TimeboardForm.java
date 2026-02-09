package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class TimeboardForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(CommercialProposal.class);

  String is_new_doc;
  String number;
  String tmb_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String tmb_date;
  User user = new User();
  String tmb_checked;
  String tmb_checked_old;
  String tmb_checked_date;
  User checkedUser = new User();

  boolean formReadOnly;
  boolean tmbCheckedReadOnly;
  boolean economReadOnly;

  HolderImplUsingList gridWorks = new HolderImplUsingList();
  int countItogRecord;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( !isTmbCheckedReadOnly() )
    {
      setTmb_checked("");
    }
    
    super.reset(mapping, request);
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
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

  public String getTmb_date_formatted()
  {
    return StringUtil.appDateString2appFullMonthYear(tmb_date);
  }

  public String getCpr_date_ts()
  {
    return StringUtil.appDateString2dbDateString(tmb_date);
  }

  public void setTmb_date(String tmb_date)
  {
    this.tmb_date = tmb_date;
  }

  public void setTmb_date_formatted(String tmb_date)
  {
    this.tmb_date = StringUtil.appFullMonthYearString2appDate(tmb_date);
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

  public String getTmb_checked_old()
  {
    return tmb_checked_old;
  }

  public void setTmb_checked_old(String tmb_checked_old)
  {
    this.tmb_checked_old = tmb_checked_old;
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

  public HolderImplUsingList getGridWorks()
  {
    return gridWorks;
  }

  public void setGridWorks(HolderImplUsingList gridWorks)
  {
    this.gridWorks = gridWorks;
  }

  public int getCountItogRecord()
  {
    return countItogRecord;
  }

  public void setCountItogRecord(int countItogRecord)
  {
    this.countItogRecord = countItogRecord;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public boolean isSaveReadOnly()
  {
    return formReadOnly && tmbCheckedReadOnly && economReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isTmbCheckedReadOnly()
  {
    return tmbCheckedReadOnly;
  }

  public void setTmbCheckedReadOnly(boolean tmbCheckedReadOnly)
  {
    this.tmbCheckedReadOnly = tmbCheckedReadOnly;
  }

  public boolean isEconomReadOnly()
  {
    return economReadOnly;
  }

  public void setEconomReadOnly(boolean economReadOnly)
  {
    this.economReadOnly = economReadOnly;
  }

  public boolean isDateReadOnly()
  {
    return ((gridWorks.getDataList().size() - getCountItogRecord()) > 0) || formReadOnly;
  }

  public boolean isLinkReadOnly()
  {
    return (gridWorks.getDataList().size() - getCountItogRecord()) == 0 || formReadOnly;
  }

  public boolean isShowCheckedUser()
  {
    return !StringUtil.isEmpty(getTmb_checked());
  }
}