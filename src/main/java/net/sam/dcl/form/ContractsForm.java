package net.sam.dcl.form;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.Seller;
import net.sam.dcl.beans.ReportDelimiterConsts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContractsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(ContractsForm.class);
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();
  String con_id;
  String executed;
  String not_executed;
  String con_executed;
  String oridinal_absent;

  Seller seller = new Seller();

  public String getCon_id()
  {
    return con_id;
  }

  public void setCon_id(String con_id)
  {
    this.con_id = con_id;
  }

  public String getExecuted()
  {
    return executed;
  }

  public void setExecuted(String executed)
  {
    this.executed = executed;
  }

  public String getNot_executed()
  {
    return not_executed;
  }

  public void setNot_executed(String not_executed)
  {
    this.not_executed = not_executed;
  }

  public String getCon_executed()
  {
    if (
          ("1".equals(getExecuted()) && "1".equals(getNot_executed())) ||
          (!"1".equals(getExecuted()) && !"1".equals(getNot_executed()))
       )
    {
      return null;
    }
    else if ("1".equals(getExecuted()))
    {
      return "1";
    }
    else
    {
      return "0";
    }
  }

  public void setCon_executed(String con_executed)
  {
    this.con_executed = con_executed;
  }

  public String getOridinal_absent()
  {
    return oridinal_absent;
  }

  public void setOridinal_absent(String oridinal_absent)
  {
    this.oridinal_absent = oridinal_absent;
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public boolean isAdmin()
  {
    IActionContext context = ActionContext.threadInstance();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());

    return currentUser.isAdmin();
  }

  static public class Contract
  {
    String con_id;
    String con_number;
    String con_date;
    double con_summ;
    String con_contractor;
    String con_currency;
    String con_executed;
    String con_user;
    String con_reminder;
    String con_annul;
    String con_reusable;
    String con_final_date;
    String con_original;
    String spc_numbers_no_original;
    String no_delivery_date;
    String incorrect_final_date;
    int attach_idx;
    int spc_count = 0;
    int day_before_final = 0;

    String usr_id_list;
    String dep_id_list;

    public String getCon_id()
    {
      return con_id;
    }

    public void setCon_id(String con_id)
    {
      this.con_id = con_id;
    }

    public String getCon_number()
    {
      return con_number;
    }

    public void setCon_number(String con_number)
    {
      this.con_number = con_number;
    }

    public String getCon_date()
    {
      return con_date;
    }

    public void setCon_date(String con_date)
    {
      this.con_date = con_date;
    }

    public String getCon_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(con_date);
    }

    public double getCon_summ()
    {
      return con_summ;
    }

    public String getCon_summ_formatted()
    {
      return StringUtil.double2appCurrencyString(con_summ);
    }

    public void setCon_summ(double con_summ)
    {
      this.con_summ = con_summ;
    }

    public String getCon_contractor()
    {
      return con_contractor;
    }

    public void setCon_contractor(String con_contractor)
    {
      this.con_contractor = con_contractor;
    }

    public String getCon_currency()
    {
      return con_currency;
    }

    public void setCon_currency(String con_currency)
    {
      this.con_currency = con_currency;
    }

    public String getCon_executed()
    {
      return con_executed;
    }

    public void setCon_executed(String con_executed)
    {
      this.con_executed = con_executed;
    }

    public String getCon_user()
    {
      return con_user;
    }

    public void setCon_user(String con_user)
    {
      this.con_user = con_user;
    }

    public String getCon_reminder()
    {
      return con_reminder;
    }

    public String getCon_reminder_formatted()
    {
      String retStr = "";
      IActionContext context = ActionContext.threadInstance();
      try
      {
        if ( isNoDeliveryDate() )
        {
         retStr += StrutsUtil.getMessage(context, "Contracts.noDeliveryDate");
        }
        if ( isIncorrectFinalDate() && getDay_before_final() > 0 && getDay_before_final() <= 35 )
        {
         retStr += (StringUtil.isEmpty(retStr) ? "" : ReportDelimiterConsts.html_separator) + StrutsUtil.getMessage(context, "Contracts.incorrectFinalDate0_35", Integer.toString(getDay_before_final()));
        }
        if ( isIncorrectFinalDate() && getDay_before_final() <= 0 )
        {
         retStr += (StringUtil.isEmpty(retStr) ? "" : ReportDelimiterConsts.html_separator) + StrutsUtil.getMessage(context, "Contracts.incorrectFinalDateLess0");
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }

    public void setCon_reminder(String con_reminder)
    {
      this.con_reminder = con_reminder;
    }

    public String getCon_annul()
    {
      return con_annul;
    }

    public boolean isAnnul()
    {
      return !StringUtil.isEmpty(getCon_annul());
    }

    public void setCon_annul(String con_annul)
    {
      this.con_annul = con_annul;
    }

    public String getCon_reusable()
    {
      return con_reusable;
    }

    public boolean isReusable()
    {
      return !StringUtil.isEmpty(getCon_reusable());
    }

    public void setCon_reusable(String con_reusable)
    {
      this.con_reusable = con_reusable;
    }

    public String getCon_final_date()
    {
      return con_final_date;
    }

    public String getCon_final_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(con_final_date);
    }

    public void setCon_final_date(String con_final_date)
    {
      this.con_final_date = con_final_date;
    }

    public String getCon_original()
    {
      return con_original;
    }

    public void setCon_original(String con_original)
    {
      this.con_original = con_original;
    }

    public String getSpc_numbers_no_original()
    {
      return spc_numbers_no_original;
    }

    public void setSpc_numbers_no_original(String spc_numbers_no_original)
    {
      this.spc_numbers_no_original = spc_numbers_no_original;
    }

    public String getNo_delivery_date()
    {
      return no_delivery_date;
    }

    public boolean isNoDeliveryDate()
    {
      return !StringUtil.isEmpty(getNo_delivery_date());
    }

    public void setNo_delivery_date(String no_delivery_date)
    {
      this.no_delivery_date = no_delivery_date;
    }

    public String getIncorrect_final_date()
    {
      return incorrect_final_date;
    }

    public boolean isIncorrectFinalDate()
    {
      return !StringUtil.isEmpty(getIncorrect_final_date());
    }

    public void setIncorrect_final_date(String incorrect_final_date)
    {
      this.incorrect_final_date = incorrect_final_date;
    }

    public String getNotes()
    {
      String retStr = "";
      IActionContext context = ActionContext.threadInstance();
      try
      {
        if ( isReusable() )
        {
          retStr += StrutsUtil.getMessage(context, "Contract.conReusableFinalDate", getCon_final_date_formatted());
        }
        else if (!StringUtil.isEmpty(getCon_final_date()) && getDay_before_final() <= 35 )
        {
          retStr += StrutsUtil.getMessage(context, "Contract.conFinalDate", getCon_final_date_formatted());
        }
        if ( !"1".equals(getCon_original()) )
        {
          retStr += (StringUtil.isEmpty(retStr) ? "" : ReportDelimiterConsts.html_separator) + StrutsUtil.getMessage(context, "Contracts.noConOriginal");
        }
        String spcNumbersNoOriginal = getSpc_numbers_no_original();
        if ( !StringUtil.isEmpty(spcNumbersNoOriginal) )
        {
          spcNumbersNoOriginal = spcNumbersNoOriginal.substring(0, spcNumbersNoOriginal.length() - 2);
          retStr += (StringUtil.isEmpty(retStr) ? "" : ReportDelimiterConsts.html_separator) +  StrutsUtil.getMessage(context, "Contracts.spcNumbersNoOriginal", spcNumbersNoOriginal);
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }

    public int getAttach_idx()
    {
      return attach_idx;
    }

    public void setAttach_idx(int attach_idx)
    {
      this.attach_idx = attach_idx;
    }

    public int getSpc_count()
    {
      return spc_count;
    }

    public void setSpc_count(int spc_count)
    {
      this.spc_count = spc_count;
    }

    public boolean haveSpec()
    {
      return (spc_count > 0);
    }

    public int getDay_before_final()
    {
      return day_before_final;
    }

    public void setDay_before_final(int day_before_final)
    {
      this.day_before_final = day_before_final;
    }

    public String getUsr_id_list()
    {
      return usr_id_list;
    }

    public void setUsr_id_list(String usr_id_list)
    {
      this.usr_id_list = usr_id_list;
    }

    public String getDep_id_list()
    {
      return dep_id_list;
    }

    public void setDep_id_list(String dep_id_list)
    {
      this.dep_id_list = dep_id_list;
    }
  }

}
