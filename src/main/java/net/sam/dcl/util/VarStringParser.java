package net.sam.dcl.util;



/**
 * @author: DG
 * Date: Mar 27, 2005
 * Time: 1:49:22 PM
 */
public class VarStringParser {
  final static public String START_SYMBOL = "$(";
  final static public String END_SYMBOL = ")";

  public interface Callback {
    public String process(String var) throws Exception;
  }

  Callback callback = null;
  public VarStringParser(Callback callback) {
    this.callback = callback;
  }
  public String parse(String str) throws Exception {
		str = str.replaceAll("\\\\\\)","###");
		StringBuffer ret = new StringBuffer(str.length() * 2);
    int idx = str.indexOf(START_SYMBOL);
    int prevIdx = 0;
    while (idx != -1) {
      ret.append(str.substring(prevIdx, idx));
      prevIdx = idx;
      idx = str.indexOf(END_SYMBOL, idx) + END_SYMBOL.length();
      if (idx == 0) break;
      String param = str.substring(prevIdx + START_SYMBOL.length(), idx - END_SYMBOL.length());
      ret.append(callback.process(param));
      prevIdx = idx;
      idx = str.indexOf(START_SYMBOL, idx);
    }
    ret.append(str.substring(prevIdx));
    return ret.toString().replaceAll("###",")");
  }

}
