package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

/**
 * Created with IntelliJ IDEA.
 * User: shprotova
 * Date: 10/31/13
 * Time: 12:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class Number1CFromHistoryForm extends BaseForm {
  private String productName;
  private String number1C;
  private String dateCreated;
  private String id;


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getNumber1C() {
    return number1C;
  }

  public void setNumber1C(String number1C) {
    this.number1C = number1C;
  }

  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
