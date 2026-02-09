package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.dbo.DboProduce;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Assemble implements Serializable
{
  String is_new_doc;

  String asm_id;
  String apr_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String asm_date;
  String asm_number;
  String asm_block;
  int asm_count;
  DboProduce produce = new DboProduce();
  StuffCategory stuffCategory = new StuffCategory();
  String opr_id;
  String asm_type;
  String asm_occupied;

  List<AssembleProduce> asmOrDisasm = new ArrayList<AssembleProduce>();
  List<AssembleProduce> produces = new ArrayList<AssembleProduce>();

  //список заказов (новер + дата) откуда берутся товары для сборки, для зависимыд документов (к примеру "Заявка на доставку")
  String ordInfo;
  String ordInfoContractorFor;

  public Assemble()
  {
  }

  public Assemble(String asm_id)
  {
    this.asm_id = asm_id;
  }

  public Assemble(String asm_id, String apr_id)
  {
    this.asm_id = asm_id;
    this.apr_id = apr_id;
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

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getAsm_id()
  {
    return asm_id;
  }

  public void setAsm_id(String asm_id)
  {
    this.asm_id = asm_id;
  }

  public String getApr_id()
  {
    return apr_id;
  }

  public void setApr_id(String apr_id)
  {
    this.apr_id = apr_id;
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

  public String getAsm_date()
  {
    return asm_date;
  }

  public String getAsm_date_ts()
  {
    return StringUtil.appDateString2dbDateString(asm_date);
  }

  public void setAsm_date(String asm_date)
  {
    this.asm_date = asm_date;
  }

  public String getAsm_number()
  {
    return asm_number;
  }

  public void setAsm_number(String asm_number)
  {
    this.asm_number = asm_number;
  }

  public String getAsm_block()
  {
    return asm_block;
  }

  public void setAsm_block(String asm_block)
  {
    this.asm_block = asm_block;
  }

  public int getAsm_count()
  {
    return asm_count;
  }

  public void setAsm_count(int asm_count)
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

  public String getAsm_type()
  {
    return asm_type;
  }

  public void setAsm_type(String asm_type)
  {
    this.asm_type = asm_type;
  }

  public String getAsm_occupied()
  {
    return asm_occupied;
  }

  public void setAsm_occupied(String asm_occupied)
  {
    this.asm_occupied = asm_occupied;
  }

  public List<AssembleProduce> getAsmOrDisasm()
  {
    return asmOrDisasm;
  }

  public void setAsmOrDisasm(List<AssembleProduce> asmOrDisasm)
  {
    this.asmOrDisasm = asmOrDisasm;
  }

  public List<AssembleProduce> getProduces()
  {
    return produces;
  }

  public void setProduces(List<AssembleProduce> produces)
  {
    this.produces = produces;
  }

  public String getOrdInfo()
  {
    return ordInfo;
  }

  public void setOrdInfo(String ordInfo)
  {
    this.ordInfo = ordInfo;
  }

  public String getOrdInfoContractorFor()
  {
    return ordInfoContractorFor;
  }

  public void setOrdInfoContractorFor(String ordInfoContractorFor)
  {
    this.ordInfoContractorFor = ordInfoContractorFor;
  }

	//0 - сборка, 1 - разборка
	public Boolean isAssemble()
	{
		return "0".equals(getAsm_type());
	}

	//0 - сборка, 1 - разборка
	public Boolean isDisassemble()
	{
		return "1".equals(getAsm_type());
	}

  public void clear()
  {
    stuffCategory = new StuffCategory();
    produce = new DboProduce();
    asmOrDisasm.clear();
    produces.clear();
  }

  public void clearProduce()
  {
    produce = new DboProduce();
    asmOrDisasm.clear();
    produces.clear();
  }

  public void fillFirstTable()
  {
    asmOrDisasm.clear();
    AssembleProduce assembleProduce = getEmptyAssembleProduce();
    assembleProduce.setProduce(produce);
    assembleProduce.setStf_id(stuffCategory.getId());
    assembleProduce.setApr_count(1);
    asmOrDisasm.add(assembleProduce);
  }

  public void calculate()
  {
    calculateInString();
  }

  public void calculateInString()
  {
    for (int i = 0; i < produces.size(); i++)
    {
      AssembleProduce assembleProduce = produces.get(i);
      assembleProduce.setNumber(Integer.toString(i));
    }
  }

  public AssembleProduce getEmptyAssembleProduce()
  {
    AssembleProduce assembleProduce = new AssembleProduce();
    assembleProduce.setApr_id("");
    assembleProduce.setAsm_id("");
    assembleProduce.setNumber("");
    assembleProduce.setProduce(new DboProduce());
    assembleProduce.setApr_count(0.0);
    assembleProduce.setStf_id("");
    assembleProduce.setApr_occupied("");

    return assembleProduce;
  }

  public void deleteProduce(String number)
  {
		for (int i = 0; i < produces.size(); i++)
    {
      AssembleProduce assembleProduce = produces.get(i);

      if (assembleProduce.getNumber().equalsIgnoreCase(number))
      {
        produces.remove(i);
				break;
			}
		}
	}

  public void setListParentIds()
  {
    for (AssembleProduce assembleProduce : produces)
    {
      assembleProduce.setAsm_id(getAsm_id());
    }
  }

  public void setListIdsToNull()
  {
    for (AssembleProduce assembleProduce : produces)
    {
      assembleProduce.setApr_id(null);
    }
  }

  public boolean findProduceById(int id)
  {
    for (AssembleProduce assembleProduce : produces)
    {
      if ( id == assembleProduce.getProduce().getId() )
      {
        return true;
      }
    }

    return false;
  }
}
