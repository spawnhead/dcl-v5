package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.OutgoingLetterForm;
import net.sam.dcl.service.AttachmentException;
import org.apache.struts.action.ActionForward;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OutgoingLetterAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(OutgoingLetterAction.class);
  final static String referencedTable = "DCL_OUTGOING_LETTER";

  private void saveCurrentFormToBean(IActionContext context)
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();

    OutgoingLetter outgoingLetter = (OutgoingLetter) StoreUtil.getSession(context.getRequest(), OutgoingLetter.class);

    outgoingLetter.setIs_new_doc(form.getIs_new_doc());

    outgoingLetter.setOtl_id(form.getOtl_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id())) 
      {
        outgoingLetter.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        outgoingLetter.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getContractor().getId()))
      {
        outgoingLetter.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
      }
      if (!StringUtil.isEmpty(form.getContactPerson().getCps_id()))
      {
        outgoingLetter.setContactPerson(ContactPersonDAO.load(context, form.getContactPerson().getCps_id()));
      }
      else
      {
        outgoingLetter.setContactPerson(new ContactPerson());
      }
	    if (!StringUtil.isEmpty(form.getSeller().getId()))
	    {
	      outgoingLetter.setSeller(SellerDAO.load(context, form.getSeller().getId()));
	    }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    outgoingLetter.setUsr_date_create(form.getUsr_date_create());
    outgoingLetter.setUsr_date_edit(form.getUsr_date_edit());
    outgoingLetter.setOtl_number(form.getOtl_number());
    outgoingLetter.setOtl_date(form.getOtl_date());

    outgoingLetter.setOtl_comment(form.getOtl_comment());

    StoreUtil.putSession(context.getRequest(), outgoingLetter);
  }

  private void getCurrentFormFromBean(IActionContext context, OutgoingLetter outgoingLetterIn)
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    OutgoingLetter outgoingLetter;
    if (null != outgoingLetterIn)
    {
      outgoingLetter = outgoingLetterIn;
    }
    else
    {
      outgoingLetter = (OutgoingLetter) StoreUtil.getSession(context.getRequest(), OutgoingLetter.class);
    }

    if (null != outgoingLetter)
    {
      form.setIs_new_doc(outgoingLetter.getIs_new_doc());

      form.setOtl_id(outgoingLetter.getOtl_id());
      form.setCreateUser(outgoingLetter.getCreateUser());
      form.setEditUser(outgoingLetter.getEditUser());
      form.setUsr_date_create(outgoingLetter.getUsr_date_create());
      form.setUsr_date_edit(outgoingLetter.getUsr_date_edit());
      form.setOtl_number(outgoingLetter.getOtl_number());
      form.setOtl_date(outgoingLetter.getOtl_date());
      form.setContractor(outgoingLetter.getContractor());
      form.setContactPerson(outgoingLetter.getContactPerson());
      form.setSeller(outgoingLetter.getSeller());

      form.setOtl_comment(outgoingLetter.getOtl_comment());
    }
  }

  public ActionForward newContractor(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());

    return context.getMapping().findForward("newContractor");
  }

  public ActionForward newContactPerson(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
    context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getContactPerson().getCps_id());
    return context.getMapping().findForward("newContactPerson");
  }

  public ActionForward retFromContractor(IActionContext context) throws Exception
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    getCurrentFormFromBean(context, null);

    String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
    if (!StringUtil.isEmpty(contractorId))
    {
      form.setContractor(ContractorDAO.load(context, contractorId));
    }
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

    String contactPersonId = (String) context.getRequest().getSession().getAttribute(ContactPerson.current_contact_person_id);
    if (!StringUtil.isEmpty(contactPersonId))
    {
      form.setContactPerson(ContactPersonDAO.load(context, contactPersonId));
    }
    context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, null);

    return show(context);
  }

  public ActionForward retFromAttach(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context, null);

    return show(context);
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();

    saveCurrentFormToBean(context);

    OutgoingLetter outgoingLetter = (OutgoingLetter) StoreUtil.getSession(context.getRequest(), OutgoingLetter.class);

    if (StringUtil.isEmpty(form.getOtl_id()))
    {
      User user = UserUtil.getCurrentUser(context.getRequest());
      form.setCreateUser(user);

      //form otl_number
      String date = StringUtil.date2appDateString(StringUtil.getCurrentDateTime());
      String year = date.substring(8);
      String month = date.substring(3, 5);
      form.setGen_num(CommonDAO.GetNumber(context, "get-num_outgoing_letter"));
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        DAOUtils.load(context, "user-code-load", null);
      }
      outgoingLetter.setOtl_number("BYM" + year + month + "/" + StringUtil.padWithLeadingZeros(form.getGen_num(), 4) + "-" + form.getUsr_code().toUpperCase());
      form.setOtl_number(outgoingLetter.getOtl_number());

      OutgoingLetterDAO.insert(context, outgoingLetter);
    }
    else
    {
      OutgoingLetterDAO.save(context, outgoingLetter);
    }
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    form.getAttachmentService().commit(Integer.parseInt(outgoingLetter.getOtl_id()));
    hibSession.getTransaction().commit();

    return true;
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    /*
     * Права Создать, смотреть: все
     * Редактировать: пользователь, который создал; админ
    * */
    User user = UserUtil.getCurrentUser(context.getRequest());
    if (
            user.isAdmin() ||
                    user.getUsr_id().equals(form.getCreateUser().getUsr_id()) ||
                    StringUtil.isEmpty(form.getCreateUser().getUsr_id()) //только что созданный документ
            )
    {
      form.setFormReadOnly(false);
    }
    else
    {
      form.setFormReadOnly(true);
    }

    form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());

    return context.getMapping().findForward("form");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();

    OutgoingLetter outgoingLetter = new OutgoingLetter();
    StoreUtil.putSession(context.getRequest(), outgoingLetter);
    //обнуляем поля формы
    getCurrentFormFromBean(context, outgoingLetter);

    form.setIs_new_doc("true");
    form.setOtl_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));

    form.setAttachmentService(
            DeferredAttachmentService.create(context.getRequest().getSession(),
				            referencedTable, context.getMapping().findForward("backFromAttach"), null));

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();

    OutgoingLetter outgoingLetter = OutgoingLetterDAO.load(context, form.getOtl_id());
    StoreUtil.putSession(context.getRequest(), outgoingLetter);
    getCurrentFormFromBean(context, outgoingLetter);

    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    form.setAttachmentService(
            DeferredAttachmentService.create(context.getRequest().getSession(),
				            referencedTable, Integer.parseInt(form.getOtl_id()),
				            context.getMapping().findForward("backFromAttach"), null));
    hibSession.getTransaction().commit();

    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    form.getAttachmentService().rollback();
    form.setAttachmentService(null);

    DeferredAttachmentService.removeLast(context.getRequest().getSession());
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    boolean retFromSave = saveCommon(context);

    if (retFromSave)
    {
      return back(context);
    }
    else
    {
      return show(context);
    }
  }

  public ActionForward deferredAttach(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();

    DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
    return context.getMapping().findForward("deferredAttach");
  }

  public ActionForward deleteAttachment(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    DeferredAttachmentService.DeferredAttachment attachment = form.getAttachmentService().find(Integer.parseInt(form.getAttachmentId()));
    form.getAttachmentService().delete(attachment);

    return show(context);
  }

  public ActionForward downloadAttachment(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    OutgoingLetterForm form = (OutgoingLetterForm) context.getForm();
    try
    {
      form.getAttachmentService().download(form.getAttachmentId(), context.getRequest(), context.getResponse());
      return null;
    }
    catch (AttachmentException e)
    {
      StrutsUtil.addError(context, e.getMessage(), e);
      return show(context);
    }
  }
}