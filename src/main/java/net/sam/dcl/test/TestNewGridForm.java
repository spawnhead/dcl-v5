package net.sam.dcl.test;

import net.sam.dcl.controller.BaseForm;

/**
 * Form for test_newGrid page (Development).
 *
 * Used as a lightweight params holder for SQL macros/params and as a DTO container.
 */
public class TestNewGridForm extends BaseForm {

  /**
   * Optional filter: order date (dd.MM.yyyy from UI, parsed to SQL DATE).
   */
  private java.sql.Date ord_date;

  public java.sql.Date getOrd_date() {
    return ord_date;
  }

  public void setOrd_date(java.sql.Date ord_date) {
    this.ord_date = ord_date;
  }

  /**
   * DTO for grid row; matches SQL "test-select-orders-by-date" columns.
   */
  public static class OrderRow {
    private String ord_id;
    private String ord_number;
    private String ord_date;
    private String contractor_name;
    private String contractor_for_name;

    public String getOrd_id() { return ord_id; }
    public void setOrd_id(String ord_id) { this.ord_id = ord_id; }
    public String getOrd_number() { return ord_number; }
    public void setOrd_number(String ord_number) { this.ord_number = ord_number; }
    public String getOrd_date() { return ord_date; }
    public void setOrd_date(String ord_date) { this.ord_date = ord_date; }
    public String getContractor_name() { return contractor_name; }
    public void setContractor_name(String contractor_name) { this.contractor_name = contractor_name; }
    public String getContractor_for_name() { return contractor_for_name; }
    public void setContractor_for_name(String contractor_for_name) { this.contractor_for_name = contractor_for_name; }
  }
}
