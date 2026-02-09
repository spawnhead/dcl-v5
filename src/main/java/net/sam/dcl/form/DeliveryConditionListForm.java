package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class DeliveryConditionListForm extends BaseForm {
  String name;
  String ids;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getIds() {
    return ids;
  }
  public void setIds(String ids) {
    this.ids = ids;
  }
}
