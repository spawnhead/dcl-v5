package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.InstructionForm;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.locking.LockedRecords;
import org.apache.struts.action.ActionForward;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class InstructionAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(InstructionAction.class);
  final static String INSTRUCTION_LOCK_NAME = "Instruction";
  final static String referencedTable = "DCL_INSTRUCTION";

  private void saveCurrentFormToBean(IActionContext context)
  {
    InstructionForm form = (InstructionForm) context.getForm();

    Instruction instruction = (Instruction) StoreUtil.getSession(context.getRequest(), Instruction.class);

    instruction.setIs_new_doc(form.getIs_new_doc());

    instruction.setIns_id(form.getIns_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        instruction.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        instruction.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getType().getId()))
      {
        instruction.setType(InstructionTypeDAO.load(context, form.getType().getId()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    instruction.setUsr_date_create(form.getUsr_date_create());
    instruction.setUsr_date_edit(form.getUsr_date_edit());
    instruction.setIns_number(form.getIns_number());
    instruction.setIns_date_sign(form.getIns_date_sign());
    instruction.setIns_date_from(form.getIns_date_from());
    instruction.setIns_date_to(form.getIns_date_to());
    instruction.setIns_concerning(form.getIns_concerning());

    StoreUtil.putSession(context.getRequest(), instruction);
  }

  private void getCurrentFormFromBean(IActionContext context, Instruction instructionIn)
  {
    InstructionForm form = (InstructionForm) context.getForm();
    Instruction instruction;
    if (null != instructionIn)
    {
      instruction = instructionIn;
    }
    else
    {
      instruction = (Instruction) StoreUtil.getSession(context.getRequest(), Instruction.class);
    }

    if (null != instruction)
    {
      form.setIs_new_doc(instruction.getIs_new_doc());

      form.setIns_id(instruction.getIns_id());
      form.setCreateUser(instruction.getCreateUser());
      form.setEditUser(instruction.getEditUser());
      form.setUsr_date_create(instruction.getUsr_date_create());
      form.setUsr_date_edit(instruction.getUsr_date_edit());
      form.setType(instruction.getType());
      form.setIns_number(instruction.getIns_number());
      form.setIns_date_sign(instruction.getIns_date_sign());
      form.setIns_date_from(instruction.getIns_date_from());
      form.setIns_date_to(instruction.getIns_date_to());
      form.setIns_concerning(instruction.getIns_concerning());
    }
  }

  public ActionForward editType(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    return context.getMapping().findForward("editType");
  }

  public ActionForward retFromAttach(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context, null);

    return show(context);
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    InstructionForm form = (InstructionForm) context.getForm();

    saveCurrentFormToBean(context);

    Instruction instruction = (Instruction) StoreUtil.getSession(context.getRequest(), Instruction.class);
    if (StringUtil.isEmpty(form.getIns_id()))
    {
      InstructionDAO.insert(context, instruction);
    }
    else
    {
      InstructionDAO.save(context, instruction);
    }
    
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    form.getAttachmentService().commit(Integer.parseInt(instruction.getIns_id()));
    hibSession.getTransaction().commit();

    return true;
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    InstructionForm form = (InstructionForm) context.getForm();
    /*
     * Права смотреть: все
     * Создать, редактировать: админ, юрист
    * */
    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( user.isAdmin() || user.isLawyer() )
    {
      form.setFormReadOnly(false);
    }
    else
    {
      form.setFormReadOnly(true);
    }

    if ( user.isAdmin() )
    {
      form.setShowForAdmin(true);
    }
    else
    {
      form.setShowForAdmin(false);
    }

    form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());

    return context.getMapping().findForward("form");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    InstructionForm form = (InstructionForm) context.getForm();

    Instruction instruction = new Instruction();
    StoreUtil.putSession(context.getRequest(), instruction);
    //обнуляем поля формы
    getCurrentFormFromBean(context, instruction);

    form.setIs_new_doc("true");

    form.setAttachmentService(
            DeferredAttachmentService.create(context.getRequest().getSession(),
				            referencedTable, context.getMapping().findForward("backFromAttach"), null));

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    InstructionForm form = (InstructionForm) context.getForm();

    Instruction instruction = InstructionDAO.load(context, form.getIns_id());
    StoreUtil.putSession(context.getRequest(), instruction);
    getCurrentFormFromBean(context, instruction);

    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( user.isAdmin() || user.isLawyer() ) //могут редактировать
    {
      SyncObject syncObjectForLock = new SyncObject(INSTRUCTION_LOCK_NAME, instruction.getIns_id(), context.getRequest().getSession().getId());
      SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
      if (!syncObjectForLock.equals(syncObjectCurrent))
      {
        StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
        StrutsUtil.addError(context, "error.record.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
        return context.getMapping().findForward("back");
      }
    }

    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    form.setAttachmentService(
            DeferredAttachmentService.create(context.getRequest().getSession(),
				            referencedTable, Integer.parseInt(form.getIns_id()),
				            context.getMapping().findForward("backFromAttach"), null));
    hibSession.getTransaction().commit();

    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    InstructionForm form = (InstructionForm) context.getForm();
    form.getAttachmentService().rollback();
    form.setAttachmentService(null);

    if (!StringUtil.isEmpty(form.getIns_id()))
    {
      User user = UserUtil.getCurrentUser(context.getRequest());
      if ( user.isAdmin() || user.isLawyer() ) //могут редактировать
      {
        LockedRecords.getLockedRecords().unlockWithTheSame(INSTRUCTION_LOCK_NAME, context.getRequest().getSession().getId());
      }
    }

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
    InstructionForm form = (InstructionForm) context.getForm();

    DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
    return context.getMapping().findForward("deferredAttach");
  }

  public ActionForward deleteAttachment(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    InstructionForm form = (InstructionForm) context.getForm();
    DeferredAttachmentService.DeferredAttachment attachment = form.getAttachmentService().find(Integer.parseInt(form.getAttachmentId()));
    form.getAttachmentService().delete(attachment);

    return show(context);
  }

  public ActionForward downloadAttachment(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    InstructionForm form = (InstructionForm) context.getForm();
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