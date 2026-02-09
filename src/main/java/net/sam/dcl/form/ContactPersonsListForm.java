package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class ContactPersonsListForm extends BaseForm
{
  String ctr_id;

  List list = new ArrayList();

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public List getList()
  {
    return list;
  }

  public void setList(List list)
  {
    this.list = list;
  }
}
