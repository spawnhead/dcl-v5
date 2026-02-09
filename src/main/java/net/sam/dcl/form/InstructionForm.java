package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class InstructionForm extends BaseDispatchValidatorForm
{
  String is_new_doc;
  String ins_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  InstructionType type = new InstructionType();
  String ins_number;
  String ins_date_sign;
  String ins_date_from;
  String ins_date_to;
  String ins_concerning;

  boolean formReadOnly = false;
  boolean showForAdmin = false;

  HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
  DeferredAttachmentService attachmentService = null;
  String attachmentId;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
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

  public String getIns_id()
  {
    return ins_id;
  }

  public void setIns_id(String ins_id)
  {
    this.ins_id = ins_id;
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

  public InstructionType getType()
  {
    return type;
  }

  public void setType(InstructionType type)
  {
    this.type = type;
  }

  public String getIns_number()
  {
    return ins_number;
  }

  public void setIns_number(String ins_number)
  {
    this.ins_number = ins_number;
  }

  public String getIns_date_sign()
  {
    return ins_date_sign;
  }

  public void setIns_date_sign(String ins_date_sign)
  {
    this.ins_date_sign = ins_date_sign;
  }

  public String getIns_date_from()
  {
    return ins_date_from;
  }

  public void setIns_date_from(String ins_date_from)
  {
    this.ins_date_from = ins_date_from;
  }

  public String getIns_date_to()
  {
    return ins_date_to;
  }

  public void setIns_date_to(String ins_date_to)
  {
    this.ins_date_to = ins_date_to;
  }

  public String getIns_concerning()
  {
    return ins_concerning;
  }

  public void setIns_concerning(String ins_concerning)
  {
    this.ins_concerning = ins_concerning;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isShowForAdmin()
  {
    return showForAdmin;
  }

  public void setShowForAdmin(boolean showForAdmin)
  {
    this.showForAdmin = showForAdmin;
  }

  public HolderImplUsingList getAttachmentsGrid()
  {
    return attachmentsGrid;
  }

  public void setAttachmentsGrid(HolderImplUsingList attachmentsGrid)
  {
    this.attachmentsGrid = attachmentsGrid;
  }

  public DeferredAttachmentService getAttachmentService()
  {
    return attachmentService;
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
}