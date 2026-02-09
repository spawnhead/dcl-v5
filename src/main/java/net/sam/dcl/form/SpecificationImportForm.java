package net.sam.dcl.form;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.SpecificationImportProduce;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class SpecificationImportForm extends BaseDispatchValidatorForm
{
  String is_new_doc;
  String number;

  String spi_id;
  String target;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String spi_number;
  String spi_date;
  String spi_comment;
  String spi_course;
  String spi_koeff;
  String spi_arrive;
  String spi_arrive_from;

  boolean formReadOnly = false;
  boolean currentUserManager = false;

	String recordNumberForRecalc;
	String valueForRecalc;

	HolderImplUsingList gridSpec = new HolderImplUsingList();

  public SpecificationImportForm() {
    User currentUser = UserUtil.getCurrentUser(ActionContext.threadInstance().getRequest());
    currentUserManager = currentUser.isManager();
  }

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setSpi_arrive_from("");
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

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getSpi_id()
  {
    return spi_id;
  }

  public void setSpi_id(String spi_id)
  {
    this.spi_id = spi_id;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String target)
  {
    this.target = target;
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

  public String getSpi_number()
  {
    return spi_number;
  }

  public void setSpi_number(String spi_number)
  {
    this.spi_number = spi_number;
  }

  public String getSpi_date()
  {
    return spi_date;
  }

  public void setSpi_date(String spi_date)
  {
    this.spi_date = spi_date;
  }

  public String getSpi_comment()
  {
    return spi_comment;
  }

  public void setSpi_comment(String spi_comment)
  {
    this.spi_comment = spi_comment;
  }

  public String getSpi_course()
  {
    return spi_course;
  }

  public void setSpi_course(String spi_course)
  {
    this.spi_course = spi_course;
  }

  public String getSpi_koeff()
  {
    return spi_koeff;
  }

  public void setSpi_koeff(String spi_koeff)
  {
    this.spi_koeff = spi_koeff;
  }

  public String getSpi_arrive()
  {
    return spi_arrive;
  }

  public void setSpi_arrive(String spi_arrive)
  {
    this.spi_arrive = spi_arrive;
  }

  public String getSpi_arrive_from()
  {
    return spi_arrive_from;
  }

  public void setSpi_arrive_from(String spi_arrive_from)
  {
    this.spi_arrive_from = spi_arrive_from;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public HolderImplUsingList getGridSpec()
  {
    return gridSpec;
  }

  public void setGridSpec(HolderImplUsingList gridSpec)
  {
    this.gridSpec = gridSpec;
  }

	public String getRecordNumberForRecalc() {
		return recordNumberForRecalc;
	}

	public void setRecordNumberForRecalc(String recordNumberForRecalc) {
		this.recordNumberForRecalc = recordNumberForRecalc;
	}

	public String getValueForRecalc() {
		return valueForRecalc;
	}

	public void setValueForRecalc(String valueForRecalc) {
		this.valueForRecalc = valueForRecalc;
	}

	public String getItogo() {
		return ((SpecificationImportProduce)gridSpec.getDataList().get(gridSpec.getDataList().size()-1)).getSip_cost_formatted();
	}

  public boolean isCurrentUserManager() {
    return currentUserManager;
  }
}
