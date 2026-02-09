package net.sam.dcl.action;

import net.sam.dcl.beans.Department;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.UserSetting;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.form.UserForm;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.*;
import org.apache.struts.action.ActionForward;
import org.hibernate.classic.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UserAction extends DBTransactionAction implements IDispatchable
{
  final static String referencedTable = "DCL_USER";

  private void saveCurrentFormToBean(IActionContext context)
  {
    UserForm form = (UserForm) context.getForm();
    User user = (User) StoreUtil.getSession(context.getRequest(), "stored-user");
    if ( user == null )
    {
      user = new User();
    }

    user.setUsr_id(form.getUsr_id());
    user.setUsr_code(form.getUsr_code());
    user.setUsr_login(form.getUsr_login());
    user.setUsr_passwd(form.getUsr_passwd());
    user.setUsr_passwd2(form.getUsr_passwd2());
    user.setUsr_phone(form.getUsr_phone());
    user.setDepartment(form.getDepartment());
    user.setUsr_block(form.getUsr_block());
    user.setUsr_respons_person(form.getUsr_respons_person());
    user.setUsr_no_login(form.getUsr_no_login());
    user.setUsr_chief_dep(form.getUsr_chief_dep());
    user.setUsr_fax(form.getUsr_fax());
    user.setUsr_email(form.getUsr_email());
    user.setUsr_local_entry(form.getUsr_local_entry());
    user.setUsr_internet_entry(form.getUsr_internet_entry());

    StoreUtil.putSession(context.getRequest(), user, "stored-user");
  }

  private void getCurrentFormFromBean(IActionContext context)
  {
    UserForm form = (UserForm) context.getForm();
    User user = (User) StoreUtil.getSession(context.getRequest(), "stored-user");

    form.setUsr_id(user.getUsr_id());
    form.setUsr_code(user.getUsr_code());
    form.setUsr_login(user.getUsr_login());
    form.setUsr_passwd(user.getUsr_passwd());
    form.setUsr_passwd2(user.getUsr_passwd2());
    form.setUsr_phone(user.getUsr_phone());
    form.setDepartment(user.getDepartment());
    form.setUsr_block(user.getUsr_block());
    form.setUsr_respons_person(user.getUsr_respons_person());
    form.setUsr_no_login(user.getUsr_no_login());
    form.setUsr_chief_dep(user.getUsr_chief_dep());
    form.setUsr_fax(user.getUsr_fax());
    form.setUsr_email(user.getUsr_email());
    form.setUsr_local_entry(user.getUsr_local_entry());
    form.setUsr_internet_entry(user.getUsr_internet_entry());
  }

  protected void getNamesList(IActionContext context) throws Exception
  {
    UserForm form = (UserForm) context.getForm();
    DAOUtils.fillGrid(context, form.getUsergrid(), "select-user-languages", UserForm.UserLanguages.class, null, null);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    context.getRequest().setAttribute("readOnlyChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return true;
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    UserForm form = (UserForm) context.getForm();
    form.setUsr_id("");
    form.setUsr_code("");
    form.setUsr_login("");
    form.setUsr_passwd("");
    form.setUsr_passwd2("");
    form.setDepartment(new Department());
    form.setUsr_phone("");
    form.setUsr_block("");
    form.setUsr_respons_person("");
    form.setUsr_no_login("");
    form.setUsr_chief_dep("");
    form.setUsr_fax("");
    form.setUsr_email("");
    form.setUsr_local_entry("1");
    form.setUsr_internet_entry("1");

    saveCurrentFormToBean(context);
    getNamesList(context);

    form.setAttachmentService(
            DeferredAttachmentService.create(context.getRequest().getSession(),
				            referencedTable, context.getMapping().findForward("backFromAttach"), null));

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    UserForm form = (UserForm) context.getForm();
    DAOUtils.load(context, "user-load", null);
    saveCurrentFormToBean(context);
    getNamesList(context);
    form.setUsr_no_login_saved(form.getUsr_no_login());

    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    form.setAttachmentService(
            DeferredAttachmentService.create(context.getRequest().getSession(),
				            referencedTable, Integer.parseInt(form.getUsr_id()),
				            context.getMapping().findForward("backFromAttach"), null));
    hibSession.getTransaction().commit();

    return input(context);
  }

  public boolean checkSave(IActionContext context) throws Exception
  {
    boolean ret = true;

    UserForm form = (UserForm) context.getForm();
    //ответственная персона - проверяем, чтобы все поля для языков были заполнены
    if ( "1".equals(form.getUsr_respons_person()) )
    {
      String msg = "";
      for ( int i = 0; i < form.getUsergrid().getDataList().size(); i++ )
      {
        UserForm.UserLanguages ul = (UserForm.UserLanguages) form.getUsergrid().getDataList().get(i);
        if ("".equals(ul.getUsr_name()) || "".equals(ul.getUsr_surname()) || "".equals(ul.getUsr_position()) )
        {
          msg += StrutsUtil.getMessage(context, "error.user.no_name_surname_lng", ul.getLng_name());
          ret = false;
        }

        if ( ul.getUsr_name().length() > 20 )
        {
          msg += StrutsUtil.getMessage(context, "error.user.long_name_lng", ul.getLng_name());
          ret = false;
        }
        if ( ul.getUsr_surname().length() > 20 )
        {
          msg += StrutsUtil.getMessage(context, "error.user.long_surname_lng", ul.getLng_name());
          ret = false;
        }
        if ( ul.getUsr_surname().length() > 60 )
        {
          msg += StrutsUtil.getMessage(context, "error.user.long_position_lng", ul.getLng_name());
          ret = false;
        }
      }
      if ( !ret )
      {
        StrutsUtil.addError(context, "common.msg", msg, null);
        return false;
      }
    }
    else //обязателен для заполнения только русский
    {
      for ( int i = 0; i < form.getUsergrid().getDataList().size(); i++ )
      {
        UserForm.UserLanguages ul = (UserForm.UserLanguages) form.getUsergrid().getDataList().get(i);
        if ( ul.getLng_id().equals(StrutsUtil.getMessage(context, "user.default_lng_id")) )
        {
          if ( "".equals(ul.getUsr_name()) || "".equals(ul.getUsr_surname()) )
          {
            StrutsUtil.addError(context, "error.user.no_name_surname", ul.getLng_name(), null);
            return false;
          }

          if ( ul.getUsr_name().length() > 20 )
          {
            StrutsUtil.addError(context, "error.user.long_name", null);
            return false;
          }
          if ( ul.getUsr_surname().length() > 20 )
          {
            StrutsUtil.addError(context, "error.user.long_surname", null);
            return false;
          }
        }
      }
    }

    if ( form.getUsr_code().length() > 3 )
    {
      StrutsUtil.addError(context, "error.user.code_greater", null);
      return false;
    }
    if ( form.getUsr_login().length() > 8 )
    {
      StrutsUtil.addError(context, "error.user.login_greater", null);
      return false;
    }
    if ( form.getUsr_passwd().length() > 64 )
    {
      StrutsUtil.addError(context, "error.user.passwd_greater", null);
      return false;
    }
    //no login не стоит - обязательные поля: usr_code, usr_login, usr_passwd, usr_passwd2
    if ( !"1".equals(form.getUsr_no_login()) )
    {
      if ( "".equals(form.getUsr_code()) || "".equals(form.getUsr_login()) ||
           "".equals(form.getUsr_passwd()) || "".equals(form.getUsr_passwd2()) )
      {
        StrutsUtil.addError(context, "error.user.required_fields", null);
        return false;
      }

      if ( form.getUsr_code().length() != 3 )
      {
        StrutsUtil.addError(context, "error.user.code_not_equal", null);
        return false;
      }

      if ( form.getUsr_passwd().length() < 4 )
      {
        StrutsUtil.addError(context, "error.user.passwd_less", null);
        return false;
      }
    }

    return ret;
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    if (!checkSave(context))
    {
      getCurrentFormFromBean(context);
      return input(context);
    }

    VDbConnection conn = context.getConnection();
    UserForm form = (UserForm) context.getForm();
    if (StringUtil.isEmpty(form.getUsr_id()))
    {
      DAOUtils.load(context, "user-insert", null);

      UserSetting userSetting = new UserSetting();
      userSetting.setUst_name(Constants.userRefreshPeriod);
      userSetting.setUst_description(StrutsUtil.getMessage(context, "user.userRefreshPeriod"));
      userSetting.setUst_value("5");
      userSetting.setUst_type("0");
      userSetting.setUsr_id(form.getUsr_id());
      DAOUtils.update(conn, context.getSqlResource().get("user_setting-insert"), userSetting, null);

      userSetting = new UserSetting();
      userSetting.setUst_name(Constants.userShowPaymentByAllDep);
      userSetting.setUst_description(StrutsUtil.getMessage(context, "user.userShowPaymentByAllDep"));
      userSetting.setUst_value("");
      userSetting.setUst_type("2");
      userSetting.setUsr_id(form.getUsr_id());
      DAOUtils.update(conn, context.getSqlResource().get("user_setting-insert"), userSetting, null);
    }
    else
    {
      DAOUtils.update(context, "user-update", null);
      //Если для пользователя выставлено "Не позволен Логин", то автоматически "лишать его всех ролей"
      if ( !StringUtil.isEmpty(form.getUsr_no_login()) && StringUtil.isEmpty(form.getUsr_no_login_saved()) )
      {
        DAOUtils.update(context, "delete-roles-for-user", null);
      }
    }

    for ( int i = 0; i < form.getUsergrid().getDataList().size(); i++ )
    {
      UserForm.UserLanguages userLanguage = (UserForm.UserLanguages) form.getUsergrid().getDataList().get(i);
      if ( StringUtil.isEmpty(userLanguage.getUsr_id()) )
      {
        userLanguage.setUsr_id(form.getUsr_id());
        DAOUtils.update(conn, context.getSqlResource().get("insert_user_language"), userLanguage, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("update_user_language"), userLanguage, null);
      }
    }
    conn.commit();

    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    form.getAttachmentService().commit(Integer.parseInt(form.getUsr_id()));
    hibSession.getTransaction().commit();

    DeferredAttachmentService.removeLast(context.getRequest().getSession());
    return context.getMapping().findForward("back");
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    UserForm form = (UserForm) context.getForm();
    form.getAttachmentService().rollback();
    form.setAttachmentService(null);

    DeferredAttachmentService.removeLast(context.getRequest().getSession());
    return context.getMapping().findForward("back");
  }

  public ActionForward retFromAttach(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context);
    UserForm form = (UserForm) context.getForm();
    form.getAttachmentService().deleteToLast();

    return input(context);
  }

  public ActionForward deferredAttach(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    UserForm form = (UserForm) context.getForm();

    DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
    return context.getMapping().findForward("deferredAttach");
  }

  public ActionForward downloadAttachment(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    UserForm form = (UserForm) context.getForm();
    try
    {
      form.getAttachmentService().download(form.getAttachmentId(), context.getRequest(), context.getResponse());
      return null;
    }
    catch (AttachmentException e)
    {
      StrutsUtil.addError(context, e.getMessage(), e);
      return input(context);
    }
  }
}
