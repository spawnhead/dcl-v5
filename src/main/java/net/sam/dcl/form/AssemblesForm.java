package net.sam.dcl.form;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class AssemblesForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(AssemblesForm.class);
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

  String asm_id;

  public String getAsm_id()
  {
    return asm_id;
  }

  public void setAsm_id(String asm_id)
  {
    this.asm_id = asm_id;
  }

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class Assemble
  {
    String asm_id;
    String asm_number;
    String asm_date;
    String asm_block;
    String asm_user;
    String asm_type;

    public String getAsm_id()
    {
      return asm_id;
    }

    public void setAsm_id(String asm_id)
    {
      this.asm_id = asm_id;
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

    public String getAsm_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(asm_date);
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

    public String getAsm_user()
    {
      return asm_user;
    }

    public void setAsm_user(String asm_user)
    {
      this.asm_user = asm_user;
    }

    public String getAsm_type()
    {
      return asm_type;
    }

    public void setAsm_type(String asm_type)
    {
      this.asm_type = asm_type;
    }

    public String getType()
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        //0 - сборка, 1 - разборка
        if ( "0".equals(getAsm_type()) )
        {
          return StrutsUtil.getMessage(context, "Assembles.type_asm");
        }
        if ( "1".equals(getAsm_type()) )
        {
          return StrutsUtil.getMessage(context, "Assembles.type_disasm");
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return "";
    }
  }
}
