package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class SpecificationsDepFromContractListForm extends BaseForm
{
  String id;
	String ctr_id;
  boolean with_summ;
	boolean withExecuted;
	List list;

	public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public boolean isWith_summ()
  {
    return with_summ;
  }

  public void setWith_summ(boolean with_summ)
  {
    this.with_summ = with_summ;
  }

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getCtr_id() {
		return ctr_id;
	}

	public void setCtr_id(String ctr_id) {
		this.ctr_id = ctr_id;
	}

	public boolean isWithExecuted() {
		return withExecuted;
	}

	public void setWithExecuted(boolean withExecuted) {
		this.withExecuted = withExecuted;
	}
}
