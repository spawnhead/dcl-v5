package net.sam.dcl.util;


import net.sam.dcl.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.*;
import java.util.*;

/**
 * User: Dima
 * Date: Apr 23, 2003
 * Time: 10:08:55 AM
 */
public class StringUtil
{
	protected static Log log = LogFactory.getLog(StringUtil.class);

	//От 0(воскресенье) до 6(суббота)
	public static final String[] daysOfWeek = {"Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};
	public static final char GROUPING_SEPARATOR = '.';
	public static final String GROUPING_SEPARATOR_FOR_REGEXP = "\\.";
	private static final List<MonthPair> monthList = Arrays.asList(
					new MonthPair("Январь", "января"),
					new MonthPair("Февраль", "февраля"),
					new MonthPair("Март", "марта"),
					new MonthPair("Апрель", "апреля"),
					new MonthPair("Май", "мая"),
					new MonthPair("Июнь", "июня"),
					new MonthPair("Июль", "июля"),
					new MonthPair("Август", "августа"),
					new MonthPair("Сентябрь", "сентября"),
					new MonthPair("Октябрь", "октября"),
					new MonthPair("Ноябрь", "ноября"),
					new MonthPair("Декабрь", "декабря")
	);

	static public String getString(String str)
	{
		return str == null ? "" : str;
	}

	static public boolean isEmpty(String src)
	{
		return src == null || src.length() == 0;
	}

	static public boolean equal(Object str1, Object str2)
	{
		return (str1 == null && str2 == null) || (str1 != null && str1.equals(str2));
	}

	static public boolean equal(String str1, String str2)
	{
		return getString(str1).equals(getString(str2));
	}

	static public String replace(String src, String what, String to)
	{
		if (src == null)
		{
			return null;
		}
		if (to == null)
		{
			return src;
		}
		if (what.length() == 0)
		{
			return src;
		}
		StringBuffer ret = new StringBuffer(src);
		int whatLen = what.length();
		int toLen = to.length();
		int idx = ret.toString().indexOf(what);

		while (idx != -1)
		{
			ret = ret.replace(idx, idx + whatLen, to);
			idx = ret.toString().indexOf(what, idx + toLen);
		}
		return ret.toString();
	}

	static public String[] splitWithAny(String src, String delimiters)
	{
		if (src != null && src.length() == 0)
		{
			return new String[]{""};
		}

		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(src, delimiters, false);
		while (tokenizer.hasMoreTokens())
		{
			list.add(tokenizer.nextToken());
		}
		String[] ret = new String[list.size()];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = list.get(i);
		}
		return ret;
	}

	static public boolean compareWithLeadingZeros(String one, String two)
	{
		return trimLeadingZeros(one).equals(trimLeadingZeros(two));
	}

	static public String trimLeadingZeros(String str)
	{
		int i = 0;
		for (; i < str.length() - 1; i++)
		{
			if (str.charAt(i) != '0')
			{
				break;
			}
		}
		return str.substring(i);
	}

	static public String padWithLeadingZeros(String str, int size)
	{

		return padWithLeadingSymbol(str, size, '0');
	}

	static public String padWithLeadingSymbol(String str, int size, char c)
	{
		StringBuilder ret = new StringBuilder();
		int strLen;
		strLen = str.length();
		for (int i = 0; i < size - strLen; i++)
		{
			ret.append(c);
		}
		ret.append(str);
		return ret.toString();
	}

	/*will work in '' strings*/
	static public String StringToJString(String s)
	{
		if (s == null)
		{
			return "''";
		}
		return "'" + filterForJS(s, true) + "'";
	}

	static public String StringToJString2(String s)
	{
		if (s == null)
		{
			return "\"\"";
		}
		return "\"" + filterForJS(s, false) + "\"";
	}

	public static String filterForJS(String value, boolean forSingleQuote)
	{
		if (value == null)
		{
			return (null);
		}
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuilder result = new StringBuilder(content.length + 50);

		for (char aContent : content)
		{
			switch (aContent)
			{
				case '\r':
					result.append("");
					break;
				case '\n':
					result.append("\\" + "n");
					break;
				case '\\':
					result.append("\\\\");
					break;
				case '\'':
					if (forSingleQuote) result.append("\\");
					result.append("'");
					break;
				case '"':
					if (forSingleQuote) result.append("&quot;");
					else result.append("\\" + "\"");
					break;
				default:
					result.append(aContent);
			}
		}

		return result.toString();
	}

	static public String callJFunc(String name, Object[] params)
	{
		String retStr = " " + name + "(";
		for (int i = 0; params != null && i < params.length; i++)
		{
			if (params[i] instanceof String)
			{
				retStr += StringToJString((String) params[i]);
			}
			else
			{
				retStr += String.valueOf(params[i]);
			}
			if (i != params.length - 1)
			{
				retStr += ",";
			}
		}
		return retStr + ")";
	}

	static public String onJClick(String name, Object[] params)
	{
		return " onclick=\"return " + callJFunc(name, params) + ";\" ";
	}

	static public String urlPair(String name, String value)
	{
		try
		{
			return name + "=" + URLEncoder.encode(value, "UTF8");
		}
		catch (UnsupportedEncodingException e)
		{
			return name + "=" + value;
		}

	}

	static public String addToURL(String url, String name, String value)
	{
		return addToURL(url, urlPair(name, value));
	}

	static public String addToURL(String url, String str)
	{
		String ch = "&";
		if (url != null && !url.contains("?")) ch = "?";
		return url + ch + str;
	}

	static public String ExceptionToJString(Throwable e)
	{
		if (e == null)
		{
			return "''";
		}
		String err;
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		err = StringUtil.StringToJString(writer.toString());

		try
		{
			writer.close();
		}
		catch (IOException e1)
		{
		}
		printWriter.close();
		return err;
	}

	public static Date getEndOfDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static String appDateString2dbTimestampString(String dateString)
	{
		if (isEmpty(dateString)) return null;
		try
		{
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.timestamp"));
			Date tmp = StringUtil.appDateString2Date(dateString);
			return dbFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong app date format", e);
		}

		return null;
	}

	public static String appDateString2dbDateString(String dateString)
	{
		if (isEmpty(dateString)) return null;
		try
		{
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
			Date tmp = StringUtil.appDateString2Date(dateString);
			return dbFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong app date format", e);
		}

		return null;
	}

	public static String appDateString2dbDateStringWithErr(String dateString) throws ParseException
	{
		if (isEmpty(dateString)) return null;
		SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
		Date tmp = StringUtil.appDateString2Date(dateString);
		return dbFormat.format(tmp);
	}

	public static String appDateString2appFullMonthYear(String dateString)
	{
		if (isEmpty(dateString)) return null;
		try
		{
			SimpleDateFormat appFullMonthYearFormat = new SimpleDateFormat("MMMM yyyy");
			Date tmp = StringUtil.appDateString2Date(dateString);
			return appFullMonthYearFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong app date format", e);
		}
		return null;
	}

	public static String appDateString2appFullDateFormat(String dateString)
	{
		if (isEmpty(dateString)) return null;
		try
		{
			SimpleDateFormat appFullMonthYearFormat = new SimpleDateFormat("dd MMMM yyyy");
			Date tmp = StringUtil.appDateString2Date(dateString);
			return convertMonthName(appFullMonthYearFormat.format(tmp));
		}
		catch (Exception e)
		{
			log.error("Wrong app date format", e);
		}

		return null;
	}

	private static String convertMonthName(String nameByMask)
	{
		for (MonthPair pair : monthList)
		{
			if (nameByMask.contains(pair.nameByMask))
			{
				nameByMask = nameByMask.replaceAll(pair.nameByMask, pair.correctName);
				return nameByMask;
			}
		}

		return nameByMask;
	}

	public static String getMonth(int month)
	{
		return monthList.get(month).nameByMask;
	}

	public static String appDateString2WeekDay(String dateString)
	{
		if (isEmpty(dateString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date.view"));
			Date tmp = appFormat.parse(dateString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tmp);
			//От 0(воскресенье) до 6(суббота)
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return daysOfWeek[dayOfWeek];
		}
		catch (Exception e)
		{
			log.error("Wrong app date format", e);
		}
		return null;
	}

	public static boolean checkTime(String timeString)
	{
		if (isEmpty(timeString)) return false;
		try
		{
			String[] timeArray = timeString.split(":");
			String hours = timeArray[0];
			String minutes = timeArray[1];
			return Integer.parseInt(hours) <= 24 && Integer.parseInt(minutes) <= 60;

		}
		catch (Exception e)
		{
			log.error("Wrong time format", e);
		}

		return false;
	}

	public static String dbTimestampString2appDateString(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date.view"));
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.timestamp"));
			Date tmp = dbFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String dbDateString2appDateString(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date.view"));
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
			Date tmp = dbFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String dbTimeString2appTimeString(String timeString)
	{
		if (isEmpty(timeString)) return null;
		try
		{
			//база возвращает с секундами HH:mm:ss - секунды просто отрезаем
			return timeString.substring(0, 5);
		}
		catch (Exception e)
		{
			log.error("Wrong db time format", e);
		}

		return null;
	}

	public static String dbDateString2appShotMonth(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat("MMM yyyy");
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
			Date tmp = dbFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String dbDateString2appFullMonth(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat("MMMM");
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
			Date tmp = dbFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String dbDateString2appFullMonthYear(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat("MMMM yyyy");
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
			Date tmp = dbFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String appFullMonthYearString2appDate(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFullMonthYearFormat = new SimpleDateFormat("MMMM yyyy");
			SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date.view"));
			Date tmp = appFullMonthYearFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong full month year format", e);
		}
		return null;
	}

	public static String dbDateString2WeekDay(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.date"));
			Date tmp = dbFormat.parse(timestampString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tmp);
			//От 0(воскресенье) до 6(суббота)
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return daysOfWeek[dayOfWeek];
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String dbDateTimeString2appDateTimeString(String timestampString)
	{
		if (isEmpty(timestampString)) return null;
		try
		{
			SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date_time.view"));
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.timestamp"));
			Date tmp = dbFormat.parse(timestampString);
			return appFormat.format(tmp);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String date2dbDateTimeString(Date dateTime)
	{
		try
		{
			SimpleDateFormat dbFormat = new SimpleDateFormat(Config.getString("format.db.timestamp"));
			return dbFormat.format(dateTime);
		}
		catch (Exception e)
		{
			log.error("Wrong db timestamp format", e);
		}
		return null;
	}

	public static String appDateString2format(String appDateStr, String formatStr) throws ParseException
	{
		if (isEmpty(appDateStr)) return null;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date tmp = StringUtil.appDateString2Date(appDateStr);
		return format.format(tmp);
	}

	public static String date2appDateString(Date date)
	{
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date.view"));
		return appFormat.format(date);
	}

	public static Date appDateString2Date(String strDate) throws ParseException
	{
		if (isEmpty(strDate)) return null;
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date"));
		return appFormat.parse(strDate);
	}

	public static Date appDateTimeString2Date(String strDate) throws ParseException
	{
		if (isEmpty(strDate)) return null;
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date_time.view"));
		return appFormat.parse(strDate);
	}

	public static Date timeString2Date(String strTime) throws ParseException
	{
		if (isEmpty(strTime)) return null;
		SimpleDateFormat appFormat = new SimpleDateFormat("HH:mm");
		return appFormat.parse(strTime);
	}

	public static Date dbDateString2Date(String stdDate) throws ParseException
	{
		if (isEmpty(stdDate)) return null;
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.db.date"));
		return appFormat.parse(stdDate);
	}

	public static String date2dbDateString(Date date) throws ParseException
	{
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date"));
		String appStringDate = appFormat.format(date);
		return appDateString2dbDateString(appStringDate);
	}

	public static String date2appDateTimeString(Date date)
	{
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.date-time"));
		return appFormat.format(date);
	}

	public static String date2appTimeString(Date date)
	{
		SimpleDateFormat appFormat = new SimpleDateFormat(Config.getString("format.app.time"));
		return appFormat.format(date);
	}

	public static String double2appCurrencyString(double value)
	{
		if (Double.isNaN(value))
		{
			return Config.getString("string.app.NaN");
		}

		Locale locale = new Locale("ru", "RU");
		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		nf.applyPattern("#,##0.00");
		DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
		sym.setGroupingSeparator(GROUPING_SEPARATOR);
		nf.setDecimalFormatSymbols(sym);
		return nf.format(value);
	}

	public static String doubleToString(Double value)
	{
		if (value != null)
		{
			return double2appCurrencyString(value);
		}

		return "";
	}

	public static String BigDecimal2appCurrencyString(BigDecimal value)
	{
		if (value == null)
		{
			return "";
		}

		Locale locale = new Locale("ru", "RU");
		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		nf.applyPattern("#,##0.00");
		DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
		sym.setGroupingSeparator(GROUPING_SEPARATOR);
		nf.setDecimalFormatSymbols(sym);
		return nf.format(value);
	}

	public static String doubleWithMinus2appCurrencyString(double value)
	{
		if (Double.isNaN(value))
		{
			return Config.getString("string.app.NaN");
		}

		Locale locale = new Locale("ru", "RU");
		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		nf.applyPattern("#,##0.00;#,##0.00");
		DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
		sym.setGroupingSeparator(GROUPING_SEPARATOR);
		nf.setDecimalFormatSymbols(sym);
		return nf.format(value);
	}

	public static String double2appCurrencyStringByMask(double value, String mask)
	{
		if (Double.isNaN(value))
		{
			return Config.getString("string.app.NaN");
		}

		Locale locale = new Locale("ru", "RU");
		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		nf.applyPattern(mask);
		DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
		sym.setGroupingSeparator(GROUPING_SEPARATOR);
		nf.setDecimalFormatSymbols(sym);
		return nf.format(value);
	}

	public static String double2appCurrencyStringWithoutDec(double value)
	{
		if (Double.isNaN(value))
		{
			return Config.getString("string.app.NaN");
		}

		Locale locale = new Locale("ru", "RU");
		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		nf.applyPattern("#,##0");
		DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
		sym.setGroupingSeparator(GROUPING_SEPARATOR);
		nf.setDecimalFormatSymbols(sym);
		return nf.format(value);
	}

	public static Double appCurrencyString2double(String value)
	{
		try
		{
			if (StringUtil.isEmpty(value))
			{
				return Double.NaN;
			}
			value = value.replaceAll(GROUPING_SEPARATOR_FOR_REGEXP, "");

			for (int i = 0; i < value.length(); i++)
			{
				if (!Character.isDigit(value.charAt(i)) && value.charAt(i) != ',' && value.charAt(i) != '-')
				{
					return Double.NaN;
				}
			}

			Locale locale = new Locale("ru", "RU");
			NumberFormat nf = NumberFormat.getInstance(locale);

			return nf.parse(value).doubleValue();
		}
		catch (Exception e)
		{
			log.error("Wrong curency format", e);
		}
		return Double.NaN;
	}

	public static Double stringToDouble(String s)
	{
		if (!isEmpty(s))
		{
			return appCurrencyString2double(s);
		}

		return null;
	}

	public static Double appCurrencyString2doubleSpecial(String value)
	{
		String valueLocal = value.trim().replaceAll("\\s+", ".").trim().replaceAll("\u00a0", ".");
		return appCurrencyString2double(valueLocal);
	}

	static public String stringArray2String(String[] arr)
	{
		return stringArray2String(arr, "", "");
	}

	static public String stringArray2String(String[] arr, String before, String after)
	{
		String res = "";
		int i = 0;
		if (arr.length > 0)
		{
			for (; i < arr.length - 1; i++)
			{
				res += before + arr[i] + after + ",";
			}
			res += before + arr[i] + after;
		}
		return res;
	}

	static public <T> String list2String(List<T> list, String before, String after, String delimeter)
	{
		String res = "";
		int i = 0;
		if (list.size() > 0)
		{
			for (; i < list.size() - 1; i++)
			{
				res += before + list.get(i) + after + delimeter;
			}
			res += before + list.get(i) + after;
		}
		return res;
	}

	static public HashMap<String, String> string2Map(String src, String delimeters)
	{
		HashMap<String, String> res = new HashMap<String, String>();
		String[] strings = splitWithAny(src, delimeters);

		for (String str : strings)
		{
			String checkStr = res.get(str);
			if (checkStr == null)
			{
				res.put(str, str);
			}
		}

		return res;
	}


	static public String deleteDoubleValueInString(String str, String delimeter)
	{
		List<String> list = new ArrayList<String>();
		HashMap<String, String> map = string2Map(str, delimeter);
		for (String strInMap : map.keySet())
		{
			list.add(map.get(strInMap));
		}

		return list2String(list, "", "", ", ");
	}

	static public double roundN(double val, int N)
	{
		if (Double.isNaN(val))
		{
			return val;
		}
		else
		{
			double multiplier = 1;
			for (int i = 0; i < N; i++)
				multiplier *= 10;
			return (Math.round(val * multiplier) / multiplier);
		}
	}

	static public Short parseShort(String str)
	{
		if (StringUtil.isEmpty(str))
		{
			return null;
		}
		return Short.parseShort(str);
	}

	static public String toString(Short val)
	{
		if (val == null)
		{
			return "";
		}
		return String.valueOf(val);
	}

	/**
	 * @param first  - начальная дата
	 * @param second - конечная дата
	 * @return - количество дней (second - first)
	 */
	static public long getDaysBetween(Date first, Date second)
	{
		double milliElapsed = second.getTime() - first.getTime();
		double daysElapsed = roundN(milliElapsed / 24F / 3600F / 1000F, 2);
		return Math.round(daysElapsed);
	}

	/**
	 * @param first  - начальная дата
	 * @param second - конечная дата
	 * @return - количество часов (second - first)
	 */
	static public double getHoursBetween(Date first, Date second)
	{
		double milliElapsed = second.getTime() - first.getTime();
		return roundN(milliElapsed / 3600F / 1000F, 2);
	}

	static public boolean isCharCyrillic(char ch)
	{
		return Character.isLetter(ch) && (ch == 0x0401 || (ch >= 0x0410 && ch <= 0x0451));
	}

	static public boolean isContainsCyrilicChars(String str)
	{
		for (int i = 0; str != null && i < str.length(); i++)
		{
			if (isCharCyrillic(str.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}

	static public boolean isContainsDigitChars(String str)
	{
		for (int i = 0; str != null && i < str.length(); i++)
		{
			if (Character.isDigit(str.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}

	static public boolean dbBooleanToJavaBoolean(String dbBoolean)
	{
		int intValue = Integer.parseInt(dbBoolean);
		return intValue == 1;
	}

	static public String javaBooleanToDBBoolean(boolean javaBoolean)
	{
		return javaBoolean ? "1" : "0";
	}

	private static class MonthPair
	{
		String nameByMask;
		String correctName;

		MonthPair(String nameByMask, String correctName)
		{
			this.nameByMask = nameByMask;
			this.correctName = correctName;
		}
	}

	public static String getStrSum(double sum, boolean needRound, String currencyName, LocaledPropertyMessageResources words)
	{
		if (needRound)
		{
			return NumberToWordsRussian.toWords(new BigDecimal(sum)) + " " + currencyName;
		}
		else
		{
			String tmpStrSum = StringUtil.double2appCurrencyString(sum);
			String decimalPartStr = tmpStrSum.substring(tmpStrSum.indexOf(',') + 1);

			return NumberToWordsRussian.toWords(new BigDecimal(sum)) + " " +
							(currencyName.equals("BYN") || currencyName.equals("BYR") ? words.getMessage("rep.CommercialProposal.belRouble") : currencyName) + " " +
							NumberToWordsRussian.toWords(new BigDecimal(decimalPartStr)) + " " +
							(currencyName.equals("BYN") || currencyName.equals("BYR") ? words.getMessage("rep.CommercialProposal.kopeck") : words.getMessage("rep.CommercialProposal.cents"));
		}
	}

	public static Date getCurrentDateTime()
	{
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
}
