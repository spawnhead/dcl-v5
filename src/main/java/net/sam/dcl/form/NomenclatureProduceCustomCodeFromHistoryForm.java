package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.dbo.DboCustomCode;

/**
 * Created by A.Shkrobova.
 * Date: 5/23/2015.
 */
public class NomenclatureProduceCustomCodeFromHistoryForm extends BaseForm {
  DboCustomCode customCode = new DboCustomCode();
  private String dateCreated;
  private String id;


  public DboCustomCode getCustomCode() {
    return customCode;
  }

  public void setCustomCode(DboCustomCode customCode) {
    this.customCode = customCode;
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
