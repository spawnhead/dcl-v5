
/**
 * Title:        AM Project<p>
 * Description:  AM: <p>
 * Copyright:    <p>
 * Company:      <p>
 * @author A.K.
 * @version 1.0
 */
package net.sam.dcl.util;



/**
 * This class includes static functions which execute
 * a conversion of certain types to string and back.
 */
public class TypeConverter
{
 /**
  * Converts specified type to string
  */
 static public  String toString( byte val )
 {
  return Byte.toString(val);
 }

 /**
  * Converts specified type to string
  */
 static public  String toString(short val )
 {
  return Short.toString(val);
 }

 /**
  * Converts specified type to string
  */
 static public  String toString( int val )
 {
  return Integer.toString(val);
 }

 /**
  * Converts specified type to string
  */
 static public  String toString( long val )
 {
  return Long.toString(val);
 }

 /**
  * Converts specified type to string
  */
 static public  String toString( float val )
 {
  return Float.toString(val);
 }

 /**
  * Converts specified type to string
  */
 static public  String toString( double val )
 {
  return Double.toString(val);
 }
  /**
  * Converts specified type to string
  */
 static public  String toString( boolean val)
 {
  return new Boolean(val).toString();
 }

/**
  * Converts specified type to string
  */
 static public  String toString( java.sql.Date val)
 {
  return val==null ? null : val.toString();
 }

 /**
  * Converts specified type to string
  */
 static public  String toString( java.sql.Time val)
 {
  return val==null ? null : val.toString();
 }


 /**
  * Converts specified type to string
  */
 static public  String toString( java.sql.Timestamp val)
 {
  return val==null ? null : val.toString();
 }

 /**
  * Converts specified normal data string to  format string
  * @param val is a String in <code>yyyy-mm-dd hh:mm:ss.fffffffff</code> format
  * @return a String in <code>dd.mm.yyyy hh:mm:ss</code> format
  */

 static public String toFormatTS(String val)
 {
  if( val == null) return null;

  String date_s;
  String time_s;

  String year;
  String month;
  String day;
  String hour;
  String minute;
  String second;

  int firstDash;
  int secondDash;
  int dividingSpace;
  int firstColon = 0;
  int secondColon = 0;
  int period = 0;
  String formatError = "TypeConverter > toFormatTS > Timestamp format must be yyyy-mm-dd hh:mm:ss.fffffffff";




  // Split the string into date and time components
  val = val.trim();
  dividingSpace = val.indexOf(' ');
  if (dividingSpace > 0)
  {
      date_s = val.substring(0,dividingSpace);
      time_s = val.substring(dividingSpace+1);
  }
  else {throw new java.lang.IllegalArgumentException(formatError);}


  // Parse the date
  firstDash   = date_s.indexOf('-');
  secondDash  = date_s.indexOf('-', firstDash+1);

  // Parse the time
  if (time_s == null) throw new java.lang.IllegalArgumentException(formatError);
  firstColon  = time_s.indexOf(':');
  secondColon = time_s.indexOf(':', firstColon+1);
  period = time_s.indexOf('.', secondColon+1);

  // Convert the date
  if ((firstDash > 0) & (secondDash > 0) & (secondDash < date_s.length()-1))
  {
    year  = date_s.substring(0, firstDash);
    month = date_s.substring(firstDash+1  , secondDash);
    day   = date_s.substring(secondDash+1);
  }
  else { throw new java.lang.IllegalArgumentException(formatError);}

  // Convert the time; default missing nanos
  if ((firstColon > 0) & (secondColon > 0) &(secondColon < time_s.length()-1))
  {
    hour  = time_s.substring(0, firstColon);
    minute =time_s.substring(firstColon+1, secondColon);

    if ((period > 0) & (period < time_s.length()-1))
    {
      second  = time_s.substring(secondColon+1, period);
    }
    else if (period > 0) {  throw new java.lang.IllegalArgumentException(formatError);}
         else { second = time_s.substring(secondColon+1);}
  }
  else {  throw new java.lang.IllegalArgumentException();}

  return day+"."+month+"."+year+" "+ hour+":"+minute+":"+second;
 }

 /**
  * Converts specified normal data string to  format string
  * @param val is a String in <code>dd.mm.yyyy hh:mm:ss</code> format
  * @return a String in <code>yyyy-mm-dd hh:mm:ss.fffffffff</code> format
  */
 static public String toNormalTS(String val)
 {
  if( val == null ) return null;

  String date_s;
  String time_s;

  String year;
  String month;
  String day;
  String hour;
  String minute;
  String second;

  int firstDash;
  int secondDash;
  int dividingSpace;
  int firstColon = 0;
  int secondColon = 0;

  String formatError = "TypeConverter > toNormalTS > AM format must be dd.mm.yyyy hh:mm:ss";

  // Split the string into date and time components
  val = val.trim();
  dividingSpace = val.indexOf(' ');
  if (dividingSpace > 0)
  {
    date_s = val.substring(0,dividingSpace);
    time_s = val.substring(dividingSpace+1);
  }
  else {  throw new java.lang.IllegalArgumentException(formatError);}


  // Parse the date
  firstDash = date_s.indexOf('.');
  secondDash = date_s.indexOf('.', firstDash+1);

  // Parse the time
  if (time_s == null) throw new java.lang.IllegalArgumentException(formatError);
  firstColon = time_s.indexOf(':');
  secondColon = time_s.indexOf(':', firstColon+1);

  // Convert the date
  if ((firstDash > 0) & (secondDash > 0) & (secondDash < date_s.length()-1))
  {
    day   = date_s.substring(0, firstDash) ;
    month = date_s.substring(firstDash+1, secondDash);
    year  = date_s.substring(secondDash+1);
  }
  else { throw new java.lang.IllegalArgumentException(formatError);}

  // Convert the time; default missing nanos
  if ((firstColon > 0) & (secondColon > 0) &  (secondColon < time_s.length()-1))
  {
    hour    = time_s.substring(0, firstColon);
    minute  = time_s.substring(firstColon+1, secondColon);
    second  = time_s.substring(secondColon+1);
  }
  else {  throw new java.lang.IllegalArgumentException(formatError);}

  return year+"-"+ month +"-"+day+" "+hour+":"+minute+":"+second+"."+"000000000";
 }



 /**
  * Converts specified type to string
  */
 static public  String toString( java.math.BigDecimal val)
 {
  return val==null ? null : val.toString();
 }

  /**
  * Returns the byte value at the specified position.
  */
 static public  byte toByte(String val )
 {
  return Byte.valueOf(val).byteValue();
 }
 /**
  * Returns the short value
  */
 static public  short toShort(String val )
 {
  return Short.valueOf(val).shortValue();
 }
 /**
  * Returns the int value
  */
 static public  int toInt(String val )
 {
  return Integer.valueOf(val).intValue();
 }
 static public  int toInt(String val , int def )
 {
  if( val==null || val.equals(""))  return def ;
  else                              return toInt(val);
 }
 /**
  * Returns the long value
  */
 static public  long toLong(String val )
 {
  return Long.valueOf(val).longValue();
 }
 /**
  * Returns the float value
  */
 static public  float toFloat(String val )
 {
  return Float.valueOf(val).floatValue();
 }
 /**
  * Returns the double value
  */
 static public  double toDouble(String val )
 {
  return Double.valueOf(val).doubleValue();
 }
 /**
  * Returns the boolen value
  */
 static public  boolean toBoolean(String val )
 {
  return Boolean.valueOf(val).booleanValue();
 }
 /**
  * Returns the boolen value
  */
 static public  boolean toBoolean(String val , String trueValue )
 {
  if( val.equals(trueValue)) val = "true"   ;
  else                       val = "false"  ;

  return Boolean.valueOf(val).booleanValue();
 }
 /**
  * Returns the date value
  */
 static public  java.sql.Date toDate(String val )
 {
   return val == null ? null : java.sql.Date.valueOf(val);
 }

 /**
  * Returns the string of dd.mm.yyyy.
  */
 static public String toFormatDate(String val)
 {
   String strRes = toFormatTS(val);
   if(strRes == null || strRes.length()<10)
     return null;
   else
     return strRes.substring(0,10);
 }

 /**
  * Returns the time value
  */
 static public  java.sql.Time toTime(String val )
 {
  return val == null ? null : java.sql.Time.valueOf(val);
 }

  /**
  * Returns the timestamp value
  */
 static public  java.sql.Timestamp toTimestamp(String val )
 {
  return val == null ? null : java.sql.Timestamp.valueOf(val);
 }

  /**
  * Returns the bigdecimal value
  */
 static public  java.math.BigDecimal toBigDecimal(String val )
 {
  return val == null ? null : new java.math.BigDecimal(val);
 }

 static public boolean isNoEmpty( String val )
 {
  if(val == null || val.trim().equals("")) return false  ;
  else                                      return true   ;
 }
 /**
  * Replace char to String
  */
  static public String replaceCharToString(String targetStr , char ch , String str)
  {
    String result = "";
    int size = targetStr.length();
    int begPos = 0;
    int endPos = 0;

    for (int i=0; i<size;  i++)
    {
      if(targetStr.charAt(i)==ch)
      {
        result += targetStr.substring(begPos , endPos);
        result += str;
        begPos = endPos = i+1;
      }
      else
      {
        endPos ++;
      }
    }
    result += targetStr.substring(begPos , endPos);
    return result ;
  }

}
