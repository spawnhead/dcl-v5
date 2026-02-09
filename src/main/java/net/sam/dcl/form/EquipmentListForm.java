package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class EquipmentListForm extends BaseForm
{
  String filter;
  String con_id;
  String requestTypeId;

  List list = new ArrayList();

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }

  public String getCon_id()
  {
    return con_id;
  }

  public void setCon_id(String con_id)
  {
    this.con_id = con_id;
  }

  public String getRequestTypeId()
  {
    return requestTypeId;
  }

  public void setRequestTypeId(String requestTypeId)
  {
    this.requestTypeId = requestTypeId;
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