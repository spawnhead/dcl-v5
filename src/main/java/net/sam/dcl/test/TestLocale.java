package net.sam.dcl.test;

import net.sam.dcl.util.StringUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import sun.io.Converters;

/**
 * @author: DG
 * Date: Sep 7, 2005
 * Time: 12:15:24 PM
 */
public class TestLocale {
  public static void main(String[] args) throws ParseException {
    System.out.println("System.getProperty(\"file.encoding\")=" + System.getProperty("file.encoding"));
    System.out.println("Converters.getDefaultEncodingName()=" + Converters.getDefaultEncodingName());

/*    double d = 2.5;
    String pFloat = "2.5";
    String cFloat = "2,5";
    //System.out.println(new Float(pFloat));

    Locale locale = new Locale("ru","RU");
    NumberFormat nf = NumberFormat.getInstance(locale);
    Number num = nf.parse(cFloat);*/

    /*System.out.println(StringUtil.double2appCurrencyString(1));
  System.out.println(StringUtil.double2appCurrencyString(1.1));
  System.out.println(StringUtil.double2appCurrencyString(1.11));
  System.out.println(StringUtil.double2appCurrencyString(1.111));
  System.out.println(StringUtil.double2appCurrencyString(1.115));
  System.out.println(StringUtil.double2appCurrencyString(111.10));
  System.out.println(StringUtil.double2appCurrencyString(1111.10));
  System.out.println(StringUtil.double2appCurrencyString(11111.10));
  System.out.println(StringUtil.double2appCurrencyString(111111.10));
  System.out.println(StringUtil.double2appCurrencyString(1111111.10));  */

    //System.out.println(StringUtil.appCurrencyString2double("12.345.678,90"));
   /* System.out.println(StringUtil.appCurrencyString2double(""));
   System.out.println(StringUtil.appCurrencyString2double("1"));
   System.out.println(StringUtil.appCurrencyString2double("1,1"));
   System.out.println(StringUtil.appCurrencyString2double("1,11"));
   System.out.println(StringUtil.appCurrencyString2double("1,111"));
   System.out.println(StringUtil.appCurrencyString2double("1,115"));
   System.out.println(StringUtil.appCurrencyString2double("111,10"));
   System.out.println(StringUtil.appCurrencyString2double("1111,10"));
   System.out.println(StringUtil.appCurrencyString2double("11111,10"));
   System.out.println(StringUtil.appCurrencyString2double("111.111,10"));
   System.out.println(StringUtil.appCurrencyString2double("1111.111,10"));*/

  }
}
