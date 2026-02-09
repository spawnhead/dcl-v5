package net.sam.dcl.api;

import java.util.Locale;

public final class ApiJsonUtil {
  private ApiJsonUtil() {}

  public static int parseInt(String value, int def) {
    if (value == null) return def;
    try {
      return Integer.parseInt(value.trim());
    } catch (Exception ignore) {
      return def;
    }
  }

  public static boolean parseBool(String value) {
    if (value == null) return false;
    String v = value.trim().toLowerCase(Locale.ROOT);
    return "1".equals(v) || "true".equals(v) || "yes".equals(v) || "on".equals(v);
  }

  public static String q(String s) {
    if (s == null) return "null";
    return "\"" + escape(s) + "\"";
  }

  public static String escape(String s) {
    if (s == null) return "";
    StringBuilder sb = new StringBuilder(s.length() + 16);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '\\': sb.append("\\\\"); break;
        case '"': sb.append("\\\""); break;
        case '\n': sb.append("\\n"); break;
        case '\r': sb.append("\\r"); break;
        case '\t': sb.append("\\t"); break;
        default:
          if (c < 0x20) {
            sb.append("\\u");
            String hex = Integer.toHexString(c);
            for (int k = hex.length(); k < 4; k++) sb.append('0');
            sb.append(hex);
          } else {
            sb.append(c);
          }
      }
    }
    return sb.toString();
  }

  public static String error(String code, String message) {
    String c = code == null ? "" : code;
    String m = message == null ? "" : message;
    return "{\"error\":" + q(m) + ",\"code\":" + q(c) + "}";
  }
}
