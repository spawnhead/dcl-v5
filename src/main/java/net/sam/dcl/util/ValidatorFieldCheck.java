package net.sam.dcl.util;

import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.FieldChecks;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Sep 22, 2005
 * Time: 7:13:31 PM
 */
public class ValidatorFieldCheck extends FieldChecks
{
  public static Object validateCurrency(Object bean,
                                        ValidatorAction va, Field field,
                                        ActionMessages errors,
                                        HttpServletRequest request)
  {
    Object result;
    String value;
    if (isString(bean))
    {
      value = (String) bean;
    }
    else
    {
      value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }

    if (GenericValidator.isBlankOrNull(value))
    {
      return Boolean.TRUE;
    }

    result = StringUtil.appCurrencyString2double(value);

    if (Double.isNaN((Double)result))
    {
      errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
    }

    return Double.isNaN((Double)result) ? Boolean.FALSE : result;
  }

  public static Object validateCurrencyWithSpaces(Object bean,
                                        ValidatorAction va, Field field,
                                        ActionMessages errors,
                                        HttpServletRequest request)
  {
    Object result;
    String value;
    if (isString(bean))
    {
      value = (String) bean;
    }
    else
    {
      value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }

    if (GenericValidator.isBlankOrNull(value))
    {
      return Boolean.TRUE;
    }

    result = StringUtil.appCurrencyString2doubleSpecial(value);

    if (Double.isNaN((Double)result))
    {
      errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
    }

    return Double.isNaN((Double)result) ? Boolean.FALSE : result;
  }

  public static boolean validateCurrencyRange(Object bean,
                                              ValidatorAction va, Field field,
                                              ActionMessages errors,
                                              HttpServletRequest request)
  {

    String value;
    if (isString(bean))
    {
      value = (String) bean;
    }
    else
    {
      value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }

    if (!GenericValidator.isBlankOrNull(value))
    {
      try
      {
        double doubleValue = StringUtil.appCurrencyString2double(value);
        double min = Double.parseDouble(field.getVarValue("min"));
        double max = Double.parseDouble(field.getVarValue("max"));

        if (!GenericValidator.isInRange(doubleValue, min, max))
        {
          errors.add(field.getKey(), Resources.getActionMessage(request, va, field));

          return false;
        }
      }
      catch (Exception e)
      {
        errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
        return false;
      }
    }

    return true;
  }

  public static boolean validateCurrencyMin(Object bean,
                                            ValidatorAction va, Field field,
                                            ActionMessages errors,
                                            HttpServletRequest request)
  {
    String value;
    if (isString(bean))
    {
      value = (String) bean;
    }
    else
    {
      value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }

    if (!GenericValidator.isBlankOrNull(value))
    {
      try
      {
        double doubleValue = StringUtil.appCurrencyString2double(value);
        double min = Double.parseDouble(field.getVarValue("min"));

        if (doubleValue < min)
        {
          errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
          return false;
        }
      }
      catch (Exception e)
      {
        errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
        return false;
      }
    }

    return true;
  }

  public static boolean validateCurrencyMax(Object bean,
                                            ValidatorAction va, Field field,
                                            ActionMessages errors,
                                            HttpServletRequest request)
  {
    String value;
    if (isString(bean))
    {
      value = (String) bean;
    }
    else
    {
      value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }

    if (!GenericValidator.isBlankOrNull(value))
    {
      try
      {
        double doubleValue = StringUtil.appCurrencyString2double(value);
        double max = Double.parseDouble(field.getVarValue("max"));

        if (doubleValue > max)
        {
          errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
          return false;
        }
      }
      catch (Exception e)
      {
        errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
        return false;
      }
    }

    return true;
  }

  public static boolean validateTime(Object bean,
                                     ValidatorAction va, Field field,
                                     ActionMessages errors,
                                     HttpServletRequest request)
  {
    boolean result;
    String value;
    if (isString(bean))
    {
      value = (String) bean;
    }
    else
    {
      value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }

    if (GenericValidator.isBlankOrNull(value))
    {
      return true;
    }

    result = StringUtil.checkTime(value);

    if (!result)
    {
      errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
    }

    return result;
  }
}
