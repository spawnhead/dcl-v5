package net.sam.dcl.beans;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:53:34 PM
 */
public class IncoTerm implements Serializable
{

  public static String EXW = "EXW";
  public static String FCA = "FCA";
  public static String CIP = "CIP";
  public static String CPT = "CPT";
  public static String DDU = "DDU";
  public static String DDP = "DDP";

  public static String EXW_2010 = "EXW_2010";
  public static String FCA_2010 = "FCA_2010";
  public static String CIP_2010 = "CIP_2010";
  public static String CPT_2010 = "CPT_2010";
  public static String DAT_2010 = "DAT_2010";
  public static String DAP_2010 = "DAP_2010";
  public static String DDP_2010 = "DDP_2010";

  static public final Map<String, String[]> termDepeninces = new HashMap<String, String[]>();

  static
  {
    termDepeninces.put(IncoTerm.EXW, new String[]{IncoTerm.FCA, IncoTerm.CPT, IncoTerm.CIP, IncoTerm.DDU, IncoTerm.DDP});
    termDepeninces.put(IncoTerm.FCA, new String[]{IncoTerm.FCA});
    termDepeninces.put(IncoTerm.CPT, new String[]{IncoTerm.CPT});
    termDepeninces.put(IncoTerm.CIP, new String[]{IncoTerm.CIP});
    termDepeninces.put(IncoTerm.DDU, new String[]{IncoTerm.DDU});
    termDepeninces.put(IncoTerm.DDP, new String[]{IncoTerm.DDP});

    termDepeninces.put(IncoTerm.EXW_2010, new String[]{IncoTerm.FCA_2010, IncoTerm.CPT_2010, IncoTerm.CIP_2010, IncoTerm.DAT_2010, IncoTerm.DAP_2010, IncoTerm.DDP_2010});
    termDepeninces.put(IncoTerm.FCA_2010, new String[]{IncoTerm.FCA_2010});
    termDepeninces.put(IncoTerm.CPT_2010, new String[]{IncoTerm.CPT_2010});
    termDepeninces.put(IncoTerm.CIP_2010, new String[]{IncoTerm.CIP_2010});
    termDepeninces.put(IncoTerm.DAT_2010, new String[]{IncoTerm.DAT_2010});
    termDepeninces.put(IncoTerm.DAP_2010, new String[]{IncoTerm.DAP_2010});
    termDepeninces.put(IncoTerm.DDP_2010, new String[]{IncoTerm.DDP_2010});
  }

  String id;
  String name;
  String nameExtended;

  public IncoTerm()
  {
  }

  public IncoTerm(String id)
  {
    this.id = id;
  }

  public IncoTerm(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getNameExtended()
  {
    return nameExtended;
  }

  public void setNameExtended(String nameExtended)
  {
    this.nameExtended = nameExtended;
  }
}
