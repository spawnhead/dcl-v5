package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.Department;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.service.DeferredAttachmentService;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class UserForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(UserForm.class);

  String usr_id;
  String usr_code = "";
  String usr_login;
  String usr_passwd;
  String usr_passwd2;
  Department department = new Department();
  String usr_phone;
  String usr_block;
  String usr_respons_person;
  String usr_no_login_saved;
  String usr_no_login;
  String usr_chief_dep;
  String usr_fax;
  String usr_email;
  String usr_local_entry;
  String usr_internet_entry;

  HolderImplUsingList usergrid = new HolderImplUsingList();

  DeferredAttachmentService attachmentService = null;
  String attachmentId;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setUsr_respons_person("");
    setUsr_no_login("");
    setUsr_chief_dep("");
    setUsr_local_entry("");
    setUsr_internet_entry("");
    super.reset(mapping, request);
  }

  public String getUsr_id()
  {
    return usr_id;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public String getUsr_passwd()
  {
    return usr_passwd;
  }

  public void setUsr_passwd(String usr_passwd)
  {
    this.usr_passwd = usr_passwd;
  }

  public String getUsr_phone()
  {
    return usr_phone;
  }

  public void setUsr_phone(String usr_phone)
  {
    this.usr_phone = usr_phone;
  }

  public String getUsr_block()
  {
    return usr_block;
  }

  public void setUsr_block(String usr_block)
  {
    this.usr_block = usr_block;
  }

  public String getUsr_login()
  {
    return usr_login;
  }

  public void setUsr_login(String usr_login)
  {
    this.usr_login = usr_login;
  }

  public String getUsr_code()
  {
    return usr_code;
  }

  public void setUsr_code(String usr_code)
  {
    this.usr_code = usr_code;
  }

  public String getUsr_passwd2()
  {
    return usr_passwd2;
  }

  public void setUsr_passwd2(String usr_passwd2)
  {
    this.usr_passwd2 = usr_passwd2;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public String getUsr_respons_person()
  {
    return usr_respons_person;
  }

  public void setUsr_respons_person(String usr_respons_person)
  {
    this.usr_respons_person = usr_respons_person;
  }

  public String getUsr_no_login_saved()
  {
    return usr_no_login_saved;
  }

  public void setUsr_no_login_saved(String usr_no_login_saved)
  {
    this.usr_no_login_saved = usr_no_login_saved;
  }

  public String getUsr_no_login()
  {
    return usr_no_login;
  }

  public void setUsr_no_login(String usr_no_login)
  {
    this.usr_no_login = usr_no_login;
  }

  public String getUsr_chief_dep()
  {
    return usr_chief_dep;
  }

  public void setUsr_chief_dep(String usr_chief_dep)
  {
    this.usr_chief_dep = usr_chief_dep;
  }

  public String getUsr_fax()
  {
    return usr_fax;
  }

  public void setUsr_fax(String usr_fax)
  {
    this.usr_fax = usr_fax;
  }

  public String getUsr_email()
  {
    return usr_email;
  }

  public void setUsr_email(String usr_email)
  {
    this.usr_email = usr_email;
  }

  public String getUsr_local_entry()
  {
    return usr_local_entry;
  }

  public void setUsr_local_entry(String usr_local_entry)
  {
    this.usr_local_entry = usr_local_entry;
  }

  public String getUsr_internet_entry()
  {
    return usr_internet_entry;
  }

  public void setUsr_internet_entry(String usr_internet_entry)
  {
    this.usr_internet_entry = usr_internet_entry;
  }

  public HolderImplUsingList getUsergrid()
  {
    return usergrid;
  }

  public void setUsergrid(HolderImplUsingList usergrid)
  {
    this.usergrid = usergrid;
  }

  public DeferredAttachmentService getAttachmentService()
  {
    return attachmentService;
  }

  public String getOriginalFileName()
  {
    try
    {
      return attachmentService == null || attachmentService.list() == null || attachmentService.list().size() <= 0 ? "" :  attachmentService.list().get(0).getOriginalFileName();
    }
    catch (Exception ex)
    {
      log.error(ex);
    }

    return "";
  }

  public int getIdx()
  {
    try
    {
      return attachmentService == null || attachmentService.list() == null || attachmentService.list().size() <= 0 ? -1 :  attachmentService.list().get(0).getIdx();
    }
    catch (Exception ex)
    {
      log.error(ex);
    }

    return -1;
  }

  public void setAttachmentService(DeferredAttachmentService attachmentService)
  {
    this.attachmentService = attachmentService;
  }

  public String getAttachmentId()
  {
    return attachmentId;
  }

  public void setAttachmentId(String attachmentId)
  {
    this.attachmentId = attachmentId;
  }

  static public class UserLanguages
  {
    String usr_id;
    String usr_name;
    String usr_surname;
    String usr_position;
    String lng_id;
    String lng_name;

    public String getUsr_id()
    {
      return usr_id;
    }

    public void setUsr_id(String usr_id)
    {
      this.usr_id = usr_id;
    }

    public String getUsr_surname()
    {
      return usr_surname;
    }

    public void setUsr_surname(String usr_surname)
    {
      this.usr_surname = usr_surname;
    }

    public String getUsr_position()
    {
      return usr_position;
    }

    public void setUsr_position(String usr_position)
    {
      this.usr_position = usr_position;
    }

    public String getUsr_name()
    {
      return usr_name;
    }

    public void setUsr_name(String usr_name)
    {
      this.usr_name = usr_name;
    }

    public String getLng_id()
    {
      return lng_id;
    }

    public void setLng_id(String lng_id)
    {
      this.lng_id = lng_id;
    }

    public String getLng_name()
    {
      return lng_name;
    }

    public void setLng_name(String lng_name)
    {
      this.lng_name = lng_name;
    }
  }
}
