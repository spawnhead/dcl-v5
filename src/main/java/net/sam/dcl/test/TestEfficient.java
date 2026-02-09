package net.sam.dcl.test;

import java.util.*;

/**
 * @author: DG
 * Date: 13.03.2006
 * Time: 21:51:09
 */
public class TestEfficient {
  static final int size = 100000;
  public static void main(String[] args) throws IllegalAccessException, InstantiationException {

    testListInsertions();
    //testHash();
    //testList();

  }
  private static void testList() throws InstantiationException, IllegalAccessException {
    List list = new ArrayList();
    long bt = System.currentTimeMillis();
    for (int i = 0; i < size;i++){
      list.add(Record.class.newInstance());
    }
    System.out.println(System.currentTimeMillis()-bt);
    bt = System.currentTimeMillis();
    String var = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaabc";

    for (int i = 0; i < size;i++){
      Record rec = (Record) list.get(i);
      if (rec.getField0().indexOf(var)!=-1 ||
          rec.getField1().indexOf(var)!=-1 ||
          rec.getField2().indexOf(var)!=-1 ||
          rec.getField3().indexOf(var)!=-1 ||
          rec.getField4().indexOf(var)!=-1 ||
          rec.getField5().indexOf(var)!=-1 ||
          rec.getField6().indexOf(var)!=-1 ||
          rec.getField7().indexOf(var)!=-1 ||
          rec.getField8().indexOf(var)!=-1 ||
          rec.getField9().indexOf(var)!=-1
        ){
        rec.setField9("1");
      } else {
        rec.setField9("0");
      }
    }
    System.out.println(System.currentTimeMillis()-bt);
  }
  private static void testListInsertions() throws InstantiationException, IllegalAccessException {
    List list = new ArrayList();
    long bt = System.currentTimeMillis();
    for (int i = 0; i < size;i++){
      list.add(Record.class.newInstance());
    }
    System.out.println(System.currentTimeMillis()-bt);
    bt = System.currentTimeMillis();
    Random random = new Random();
    for (int i = 0; i < size;i++){
      int idx = random.nextInt(size)%size;
      list.remove(idx);
      list.add(idx,Record.class.newInstance());
    }
    System.out.println(System.currentTimeMillis()-bt);
    System.out.println(list.size());
  }
  private static void testHash() throws InstantiationException, IllegalAccessException {
    Map map = new HashMap();
    long bt = System.currentTimeMillis();
    Random random = new Random();
    for (int i = 0; i < size;i++){
      map.put(String.valueOf(random.nextInt(size)%size), Record.class.newInstance());
    }
    System.out.println(System.currentTimeMillis()-bt);
    bt = System.currentTimeMillis();
    String var = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaabc";
    int counter = 0;
    for (int i = 0; i < size*10;i++){
      Record rec = (Record) map.get(String.valueOf(random.nextInt(size)%size));
      if (rec != null && rec.getField9().indexOf("a")!=-1){
        counter++;
        if (rec.getField0().indexOf(var)!=-1 ||
            rec.getField1().indexOf(var)!=-1 ||
            rec.getField2().indexOf(var)!=-1 ||
            rec.getField3().indexOf(var)!=-1 ||
            rec.getField4().indexOf(var)!=-1 ||
            rec.getField5().indexOf(var)!=-1 ||
            rec.getField6().indexOf(var)!=-1 ||
            rec.getField7().indexOf(var)!=-1 ||
            rec.getField8().indexOf(var)!=-1 ||
            rec.getField9().indexOf(var)!=-1
          ){
          rec.setField9("1");
        } else {
          rec.setField9("0");
        }
      }
    }
    System.out.println(System.currentTimeMillis()-bt);
    System.out.println(counter+"-"+map.size());
  }
  public static class Record{
    String field0 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field4 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field5 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field6 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field7 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field8 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String field9 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    public String getField0() {
      return field0;
    }
    public void setField0(String field0) {
      this.field0 = field0;
    }
    public String getField1() {
      return field1;
    }
    public void setField1(String field1) {
      this.field1 = field1;
    }
    public String getField2() {
      return field2;
    }
    public void setField2(String field2) {
      this.field2 = field2;
    }
    public String getField3() {
      return field3;
    }
    public void setField3(String field3) {
      this.field3 = field3;
    }
    public String getField4() {
      return field4;
    }
    public void setField4(String field4) {
      this.field4 = field4;
    }
    public String getField5() {
      return field5;
    }
    public void setField5(String field5) {
      this.field5 = field5;
    }
    public String getField6() {
      return field6;
    }
    public void setField6(String field6) {
      this.field6 = field6;
    }
    public String getField7() {
      return field7;
    }
    public void setField7(String field7) {
      this.field7 = field7;
    }
    public String getField8() {
      return field8;
    }
    public void setField8(String field8) {
      this.field8 = field8;
    }
    public String getField9() {
      return field9;
    }
    public void setField9(String field9) {
      this.field9 = field9;
    }
  }
}
