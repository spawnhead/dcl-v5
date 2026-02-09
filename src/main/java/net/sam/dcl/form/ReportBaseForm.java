package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ReportBaseForm extends BaseDispatchValidatorForm
{
  boolean canForm = false;

  String date_begin;
  String date_end;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    super.reset(mapping, request);
  }

  public boolean isCanForm()
  {
    return canForm;
  }

  public void setCanForm(boolean canForm)
  {
    this.canForm = canForm;
  }

  public String getDate_begin()
  {
    return date_begin;
  }

  public String getDate_begin_date()
  {
    return StringUtil.appDateString2dbDateString(date_begin);
  }

  public void setDate_begin(String date_begin)
  {
    this.date_begin = date_begin;
  }

  public String getDate_end_date()
  {
    return StringUtil.appDateString2dbDateString(date_end);
  }

  public String getDate_end()
  {
    return date_end;
  }

  public void setDate_end(String date_end)
  {
    this.date_end = date_end;
  }
}