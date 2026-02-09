package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class AssembleForm extends BaseDispatchValidatorForm
{
  String is_new_doc;
  String gen_num;
  String number;

  String asm_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String usr_code;

  String asm_number;
  String asm_date;
  String asm_block;
  String asm_count;
  DboProduce produce = new DboProduce();
  StuffCategory stuffCategory = new StuffCategory();
  String opr_id;
  String asm_type_assemble;
  String asm_type_disassemble;
  String asm_occupied;

  boolean formReadOnly = false;

  HolderImplUsingList gridAsmDisasm = new HolderImplUsingList();
  HolderImplUsingList gridSpec = new HolderImplUsingList();

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( !isFormReadOnly() )
    {
      setAsm_type_assemble("");
      setAsm_type_disassemble("");
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

  public String getGen_num()
  {
    return gen_num;
  }

  public void setGen_num(String gen_num)
  {
    this.gen_num = gen_num;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getAsm_id()
  {
    return asm_id;
  }

  public void setAsm_id(String asm_id)
  {
    this.asm_id = asm_id;
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

  public String getUsr_code()
  {
    return usr_code;
  }

  public void setUsr_code(String usr_code)
  {
    this.usr_code = usr_code;
  }

  public String getAsm_number()
  {
    return asm_number;
  }

  public void setAsm_number(String asm_number)
  {
    this.asm_number = asm_number;
  }

  public String getAsm_date()
  {
    return asm_date;
  }

  public void setAsm_date(String asm_date)
  {
    this.asm_date = asm_date;
  }

  public String getAsm_block()
  {
    return asm_block;
  }

  public void setAsm_block(String asm_block)
  {
    this.asm_block = asm_block;
  }

  public String getAsm_count()
  {
    return asm_count;
  }

  public void setAsm_count(String asm_count)
  {
    this.asm_count = asm_count;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public String getAsm_type_assemble()
  {
    return asm_type_assemble;
  }

  public void setAsm_type_assemble(String asm_type_assemble)
  {
    this.asm_type_assemble = asm_type_assemble;
  }

  public String getAsm_type_disassemble()
  {
    return asm_type_disassemble;
  }

  public void setAsm_type_disassemble(String asm_type_disassemble)
  {
    this.asm_type_disassemble = asm_type_disassemble;
  }

  public String getAsm_occupied()
  {
    return asm_occupied;
  }

  public void setAsm_occupied(String asm_occupied)
  {
    this.asm_occupied = asm_occupied;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly || !StringUtil.isEmpty(asm_occupied);
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public HolderImplUsingList getGridAsmDisasm()
  {
    return gridAsmDisasm;
  }

  public void setGridAsmDisasm(HolderImplUsingList gridAsmDisasm)
  {
    this.gridAsmDisasm = gridAsmDisasm;
  }

  public HolderImplUsingList getGridSpec()
  {
    return gridSpec;
  }

  public void setGridSpec(HolderImplUsingList gridSpec)
  {
    this.gridSpec = gridSpec;
  }

  public String getAsmDisassembleCount()
  {
    return Integer.toString(gridAsmDisasm.getDataList().size()); 
  }
}
