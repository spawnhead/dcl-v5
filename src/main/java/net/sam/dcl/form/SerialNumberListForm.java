package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class SerialNumberListForm extends BaseForm
{
  String filter;
  String prd_id;

  List list = new ArrayList();

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }

  public String getPrd_id()
  {
    return prd_id;
  }

  public void setPrd_id(String prd_id)
  {
    this.prd_id = prd_id;
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