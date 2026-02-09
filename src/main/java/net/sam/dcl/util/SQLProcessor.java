package net.sam.dcl.util;


import net.sam.dcl.db.SQLStmtNormalizer;
import net.sam.dcl.db.VParameter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: Dima
 * Date: Oct 14, 2004
 * Time: 3:45:15 PM
 */
public class SQLProcessor {

  public static void addParams(String sql, VParameter params, Object obj) throws Exception {
    Pattern p = Pattern.compile("'[^']*'");
    String[] ar = p.split(sql);
    for (int i = 0; i < ar.length; i++) {
      addParamsSimple(ar[i], params, obj);
    }
  }
/*  public static String replaceMacros(String sql,Object obj) throws Exception {
    if (obj == null) {
      return sql;
    }
    Map map = PropertyUtils.describe(obj);
    Properties prop = new Properties();
    for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
      String key = (String) iterator.next();
      if (map.get(key)!= null){
        prop.put(key,map.get(key).toString());
      }
    }
    sql = SQLStmtNormalizer.replaceMacros(sql,prop);
    return sql;
  }*/
  public static String replaceMacros(String sql,Object obj) throws Exception {
    if (obj == null) {
      return sql;
    }
    sql = SQLStmtNormalizer.replaceMacros2(sql,obj);
    return sql;
  }
  private static boolean isPagingParam(String param) {
    return param != null && (param.startsWith("_")
        || "pageSizePlusOne".equalsIgnoreCase(param)
        || "offset".equalsIgnoreCase(param)
        || "pageSize".equalsIgnoreCase(param));
  }

  private static void addParamsSimple(String sql, VParameter params, Object obj) throws Exception {
    Pattern p = Pattern.compile("[&:]([\\w\\.]+)\\b");
    Matcher m = p.matcher(sql);
    while (m.find()) {
      String param = m.group(1);
      if (!isExists(params, param)) {
        if (isPagingParam(param)) {
          params.add(param, null, Types.VARCHAR);
          continue;
        }
        try {
          String val = BeanUtils.getProperty(obj, param);
          params.add(param, val, Types.VARCHAR);
        } catch (Exception e) {
          if (isPagingParam(param)) {
            params.add(param, null, Types.VARCHAR);
            continue;
          }
          throw e;
        }
      }
    }
  }
  private static final boolean isExists(VParameter params, String param) {
    VParameter.Element[] elements = params.getParamElements();
    for (int i = 0; i < elements.length; i++) {
      if (param.equalsIgnoreCase(elements[i].mParamName)) {
        return true;
      }
    }
    return false;
  }
  public static void checkRegExp(String sql) throws Exception {
    Pattern p = Pattern.compile("'[^']*'");
    Matcher m = p.matcher(sql);
    int counter = 0;
    //System.out.println(m.replaceAll("'%"));
    String[] ar = p.split(sql);
    for (int i = 0; i < ar.length; i++) {
      String s = ar[i];
      System.out.println("[" + s + "]");
    }
/*    while (m.find()) {
      String param1 = m.group(1);
      String param2 = m.group(2);
//      String param3 = m.group(3);
      //String param4 = m.group(4);
//      System.out.println("["+param1+"]");
      System.out.println("["+param1+"]["+param2+"]");
      //System.out.println("["+param1+"]["+param2+"]["+param3+"]");
      //System.out.println("["+param1+"]["+param2+"]["+param3+"]["+param4+"]");
    }*/
  }
  public static void main(String[] args) {
    VParameter param = new VParameter();
    //test t = new test();
    try {
      //addParams(" ':test '&test1.test ' :test2  :test22' &test3  ':test4 '", param, t);
    } catch (Exception e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }
//  public static void load(VResultSet resultSet, Object obj) throws Exception {
//    int columnCount = resultSet.getResultColumns();
//    for (int i = 0; i < columnCount; i++) {
//      String column = resultSet.getColumnName(i + 1);
//      BeanUtils.setProperty(obj, column, resultSet.getData(i + 1));
//      BeanUtils.setProperty(obj, column.toLowerCase(), resultSet.getData(i + 1));
//    }
//  }
  /*public static void load(VResultSet resultSet, Object obj) throws Exception {
    DAOUtils.load(resultSet,obj);
  } */
//  public static void load(VResultSet resultSet, Object obj) throws Exception {
//    Map props = BeanUtils.describe(obj);
//    Iterator iterator = props.keySet().iterator();
//    while (iterator.hasNext()) {
//      String key = (String) iterator.next();
//      try {
//        String value = resultSet.getData(key.toUpperCase());
//        BeanUtils.setProperty(obj, key,value);
//      } catch (VDbException e) {}
//    }
//
//  }

}

