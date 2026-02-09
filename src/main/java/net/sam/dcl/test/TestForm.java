/**
 * @author: DG
 * Date: Jul 30, 2005
 * Time: 5:41:54 PM
 */
package net.sam.dcl.test;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class TestForm extends BaseDispatchValidatorForm {
  String key;
  String name1;
  String name2;
  String name3;
  String name4;
  String value;

  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getName1() {
    return name1;
  }
  public void setName1(String name1) {
    this.name1 = name1;
  }
  public String getName2() {
    return name2;
  }
  public void setName2(String name2) {
    this.name2 = name2;
  }
  public String getName3() {
    return name3;
  }
  public void setName3(String name3) {
    this.name3 = name3;
  }
  public String getName4() {
    return name4;
  }
  public void setName4(String name4) {
    this.name4 = name4;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
}
