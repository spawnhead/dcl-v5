package net.sam.dcl.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContractorsPrint implements Serializable
{
  String printScale;
  List<String> selectedIds = new ArrayList<String>();

  public String getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(String printScale)
  {
    this.printScale = printScale;
  }

  public List<String> getSelectedIds()
  {
    return selectedIds;
  }

  public void setSelectedIds(List<String> selectedIds)
  {
    this.selectedIds = selectedIds;
  }
}