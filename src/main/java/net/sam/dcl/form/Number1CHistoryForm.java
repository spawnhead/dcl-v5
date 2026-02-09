package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * Created with IntelliJ IDEA.
 * User: shprotova
 * Date: 10/29/13
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Number1CHistoryForm extends BaseForm {
  private String number_description;
  private HolderImplUsingList grid = new HolderImplUsingList();
  private String prd_id;

  public String getNumber_description() {
    return number_description;
  }

  public void setNumber_description(String number_description) {
    this.number_description = number_description;
  }

  public HolderImplUsingList getGrid() {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid) {
    this.grid = grid;
  }

  static public class Number1C {
    String id;
    String number_1c;
    String date_created;

    public String getNumber_1c() {
      return number_1c;
    }

    public void setNumber_1c(String number_1c) {
      this.number_1c = number_1c;
    }

    public String getDate_created() {
      return StringUtil.dbTimestampString2appDateString(date_created);
    }

    public void setDate_created(String date_created) {
      this.date_created = date_created;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

      }

  public String getPrd_id() {
    return prd_id;
  }

  public void setPrd_id(String prd_id) {
    this.prd_id = prd_id;
  }
}
