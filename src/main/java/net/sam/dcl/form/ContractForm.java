package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractForm extends BaseDispatchValidatorForm
{
  String is_new_doc;
  String spc_number;
  String con_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String con_number;
  String con_date;
  String con_reusable;
  String con_final_date;
  Contractor contractor = new Contractor();
  Currency currency = new Currency();
  String con_executed;
  String con_occupied;
  String con_fax_copy;
  String con_original;

  Seller seller = new Seller();

  String con_annul;
  String con_annul_date;
  String con_comment;

  boolean readOnlyIfNotAdminEconomistLawyer;
  boolean readOnlyForAnnul;
  boolean formReadOnly = false;
  boolean showAttach;
  boolean showAttachFiles;

  HolderImplUsingList grid = new HolderImplUsingList();

  HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
  DeferredAttachmentService attachmentService = null;
  String attachmentId;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setCon_reusable("");
    setCon_original("");
    setCon_fax_copy("");
    setCon_annul("");
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

  public String getSpc_number()
  {
    return spc_number;
  }

  public void setSpc_number(String spc_number)
  {
    this.spc_number = spc_number;
  }

  public String getCon_id()
  {
    return con_id;
  }

  public void setCon_id(String con_id)
  {
    this.con_id = con_id;
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

  public String getCon_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(con_date);
  }

  public void setCon_date(String con_date)
  {
    this.con_date = con_date;
  }

  public void setCon_date_formatted(String con_date)
  {
    this.con_date = StringUtil.appDateString2dbDateString(con_date);
  }

  public String getCon_reusable()
  {
    return con_reusable;
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

  public void setCon_final_date_formatted(String con_final_date)
  {
    this.con_final_date = StringUtil.appDateString2dbDateString(con_final_date);
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public String getCon_executed()
  {
    return con_executed;
  }

  public void setCon_executed(String con_executed)
  {
    this.con_executed = con_executed;
  }

  public String getCon_occupied()
  {
    return con_occupied;
  }

  public void setCon_occupied(String con_occupied)
  {
    this.con_occupied = con_occupied;
  }

  public String getCon_fax_copy()
  {
    return con_fax_copy;
  }

  public void setCon_fax_copy(String con_fax_copy)
  {
    this.con_fax_copy = con_fax_copy;
  }

  public String getCon_original()
  {
    return con_original;
  }

  public void setCon_original(String con_original)
  {
    this.con_original = con_original;
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
  }

  public String getCon_annul()
  {
    return con_annul;
  }

  public void setCon_annul(String con_annul)
  {
    this.con_annul = con_annul;
  }

  public String getCon_annul_date()
  {
    return con_annul_date;
  }

  public String getConAnnulDateFormatted()
  {
    return StringUtil.dbDateString2appDateString(getCon_annul_date());
  }

  public void setCon_annul_date(String con_annul_date)
  {
    this.con_annul_date = con_annul_date;
  }

  public void setConAnnulDateFormatted(String con_annul_date)
  {
    this.con_annul_date = StringUtil.appDateString2dbDateString(con_annul_date);
  }

  public String getCon_comment()
  {
    return con_comment;
  }

  public void setCon_comment(String con_comment)
  {
    this.con_comment = con_comment;
  }

  public boolean isReadOnlyIfOccupied()
  {
    return !StringUtil.isEmpty(getCon_occupied()) | formReadOnly;
  }

  public boolean isReadOnlyIfNotAdminEconomistLawyer()
  {
    return readOnlyIfNotAdminEconomistLawyer | formReadOnly;
  }

  public void setReadOnlyIfNotAdminEconomistLawyer(boolean readOnlyIfNotAdminEconomistLawyer)
  {
    this.readOnlyIfNotAdminEconomistLawyer = readOnlyIfNotAdminEconomistLawyer;
  }

  public boolean isReadOnlyForAnnul()
  {
    return readOnlyForAnnul | formReadOnly;
  }

  public void setReadOnlyForAnnul(boolean readOnlyForAnnul)
  {
    this.readOnlyForAnnul = readOnlyForAnnul;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isShowAttach()
  {
    return showAttach;
  }

  public void setShowAttach(boolean showAttach)
  {
    this.showAttach = showAttach;
  }

  public boolean isShowAttachFiles()
  {
    return showAttachFiles;
  }

  public void setShowAttachFiles(boolean showAttachFiles)
  {
    this.showAttachFiles = showAttachFiles;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
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
