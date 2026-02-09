package net.sam.dcl.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Locale;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.text.MessageFormat;

/**
 * General purpose abstract class that describes an API for retrieving
 * Locale-sensitive messages from underlying resource locations of an
 * unspecified design, and optionally utilizing the <code>MessageFormat</code>
 * class to produce internationalized messages with parametric replacement.
 * <p/>
 * Calls to <code>getMessage()</code> variants without a <code>Locale</code>
 * argument are presumed to be requesting a message string in the default
 * <code>Locale</code> for this JVM.
 * <p/>
 * Calls to <code>getMessage()</code> with an unknown key, or an unknown
 * <code>Locale</code> will return <code>null</code> if the
 * <code>returnNull</code> property is set to <code>true</code>.  Otherwise,
 * a suitable error message will be returned instead.
 * <p/>
 * <strong>IMPLEMENTATION NOTE</strong> - Classes that extend this class
 * must be Serializable so that instances may be used in distributable
 * application server environments.
 *
 * @version $Revision: 1.23 $ $Date: 2004/03/14 06:23:51 $
 */
public class LocaledPropertyMessageResources implements Serializable
{

  ResourceBundle words = null;
  // ------------------------------------------------------------- Properties

  /**
   * Commons Logging instance.
   */
  protected static Log log = LogFactory.getLog(LocaledPropertyMessageResources.class);

  /**
   * The configuration parameter used to initialize this MessageResources.
   */
  protected String config = null;

  /**
   * The configuration parameter used to initialize this MessageResources.
   *
   * @return parameter used to initialize this MessageResources
   */
  public String getConfig()
  {
    return (this.config);
  }

  /**
   * The default Locale for our environment.
   */
  protected Locale defaultLocale;

  /**
   * The set of previously created MessageFormat objects, keyed by the
   * key computed in <code>messageKey()</code>.
   */
  protected HashMap formats = new HashMap();

  /**
   * Indicate is a <code>null</code> is returned instead of an error message string
   * when an unknown Locale or key is requested.
   */
  protected boolean returnNull = false;

  /**
   * Indicates that a <code>null</code> is returned instead of an error message string
   * if an unknown Locale or key is requested.
   *
   * @return true if null is returned if unknown key or locale is requested
   */
  public boolean getReturnNull()
  {
    return (this.returnNull);
  }

  /**
   * Indicates that a <code>null</code> is returned instead of an error message string
   * if an unknown Locale or key is requested.
   *
   * @param returnNull true Indicates that a <code>null</code> is returned
   *                   if an unknown Locale or key is requested.
   */
  public void setReturnNull(boolean returnNull)
  {
    this.returnNull = returnNull;
  }

  // ----------------------------------------------------------- Constructors

  /**
   * Construct a new MessageResources according to the specified parameters.
   *
   * @param config The configuration parameter for this MessageResources
   */
  public LocaledPropertyMessageResources(String config)
  {
    this(config, false, Locale.getDefault());
  }

  public LocaledPropertyMessageResources(String config, Locale locale)
  {
    this(config, false, locale);
  }

  /**
   * Construct a new MessageResources according to the specified parameters.
   *
   * @param config     The configuration parameter for this MessageResources
   * @param returnNull The returnNull property we should initialize with
   */
  public LocaledPropertyMessageResources(String config, boolean returnNull)
  {
    this(config, returnNull, Locale.getDefault());
  }

  public LocaledPropertyMessageResources(String config, boolean returnNull, Locale locale)
  {
    super();
    this.config = config;
    this.returnNull = returnNull;
    defaultLocale = locale;
    words = ResourceBundle.getBundle(config, defaultLocale);
  }

  // --------------------------------------------------------- Public Methods


  /**
   * Returns a text message after parametric replacement of the specified
   * parameter placeholders.
   *
   * @param key  The message key to look up
   * @param arg0 The replacement for placeholder {0} in the message
   */
  public String getMessage(String key, Object arg0)
  {
    return this.getMessage(key, new Object[]{arg0});
  }

  /**
   * Returns a text message after parametric replacement of the specified
   * parameter placeholders.
   *
   * @param key  The message key to look up
   * @param arg0 The replacement for placeholder {0} in the message
   * @param arg1 The replacement for placeholder {1} in the message
   */
  public String getMessage(String key, Object arg0, Object arg1)
  {

    return this.getMessage(key, new Object[]{arg0, arg1});
  }

  /**
   * Returns a text message after parametric replacement of the specified
   * parameter placeholders.
   *
   * @param key  The message key to look up
   * @param arg0 The replacement for placeholder {0} in the message
   * @param arg1 The replacement for placeholder {1} in the message
   * @param arg2 The replacement for placeholder {2} in the message
   */
  public String getMessage(String key, Object arg0, Object arg1, Object arg2)
  {

    return this.getMessage(key, new Object[]{arg0, arg1, arg2});
  }

  /**
   * Returns a text message after parametric replacement of the specified
   * parameter placeholders.
   *
   * @param key  The message key to look up
   * @param arg0 The replacement for placeholder {0} in the message
   * @param arg1 The replacement for placeholder {1} in the message
   * @param arg2 The replacement for placeholder {2} in the message
   * @param arg3 The replacement for placeholder {3} in the message
   */
  public String getMessage(
    String key,
    Object arg0,
    Object arg1,
    Object arg2,
    Object arg3)
  {

    return this.getMessage(key, new Object[]{arg0, arg1, arg2, arg3});
  }

  /**
   * Returns a text message for the specified key, for the default Locale.
   * A null string result will be returned by this method if no relevant
   * message resource is found for this key or Locale, if the
   * <code>returnNull</code> property is set.  Otherwise, an appropriate
   * error message will be returned.
   * <p/>
   * This method must be implemented by a concrete subclass.
   *
   * @param key The message key to look up
   */
  public String getMessage(String key)
  {
    return this.getMessage(key, null);
  }

  /**
   * Returns a text message after parametric replacement of the specified
   * parameter placeholders.  A null string result will be returned by
   * this method if no resource bundle has been configured.
   * <p/>
   * for the system default Locale
   *
   * @param key  The message key to look up
   * @param args An array of replacement parameters for placeholders
   */
  public String getMessage(String key, Object args[])
  {


    MessageFormat format = null;
    String formatKey = key;

    synchronized (formats)
    {
      format = (MessageFormat) formats.get(formatKey);
      if (format == null)
      {
        String formatString = words.getString(key);

        if (formatString == null)
        {
          return returnNull ? null : ("???" + formatKey + "???");
        }

        format = new MessageFormat(escape(formatString));
        format.setLocale(defaultLocale);
        formats.put(formatKey, format);
      }
    }

    return format.format(args);
  }


  /**
   * Return <code>true</code> if there is a defined message for the specified
   * key in the specified Locale.
   *
   * @param key The message key to look up
   */
  public boolean isPresent(String key)
  {

    String message = getMessage(key);

    if (message == null)
    {
      return false;
    }
    else
      if (message.startsWith("???") && message.endsWith("???"))
      {
        return false; // FIXME - Only valid for default implementation
      }
      else
      {
        return true;
      }
  }

  // ------------------------------------------------------ Protected Methods

  /**
   * Escape any single quote characters that are included in the specified
   * message string.
   *
   * @param string The string to be escaped
   */
  protected String escape(String string)
  {

    if ((string == null) || (string.indexOf('\'') < 0))
    {
      return string;
    }

    int n = string.length();
    StringBuffer sb = new StringBuffer(n);

    for (int i = 0; i < n; i++)
    {
      char ch = string.charAt(i);

      if (ch == '\'')
      {
        sb.append('\'');
      }

      sb.append(ch);
    }

    return sb.toString();
  }

  // --------------------------------------------------------- Static Methods


  /**
   * Log a message to the Writer that has been configured for our use.
   *
   * @param message The message to be logged
   */
  public void log(String message)
  {
    log.debug(message);
  }

  /**
   * Log a message and exception to the Writer that has been configured
   * for our use.
   *
   * @param message   The message to be logged
   * @param throwable The exception to be logged
   */
  public void log(String message, Throwable throwable)
  {
    log.debug(message, throwable);
  }
}
