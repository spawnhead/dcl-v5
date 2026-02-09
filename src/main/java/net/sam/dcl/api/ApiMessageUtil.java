package net.sam.dcl.api;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class ApiMessageUtil {
  private static final String BUNDLE = "resources.application";

  private ApiMessageUtil() {}

  public static String get(String key) {
    if (key == null) return "";
    try {
      ResourceBundle rb = ResourceBundle.getBundle(BUNDLE);
      return rb.getString(key);
    } catch (MissingResourceException ex) {
      return key;
    }
  }
}
