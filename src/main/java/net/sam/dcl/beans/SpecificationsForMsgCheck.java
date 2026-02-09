package net.sam.dcl.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecificationsForMsgCheck implements Serializable
{
  List<String> selectedIds = new ArrayList<String>();

  public List<String> getSelectedIds()
  {
    return selectedIds;
  }

  public void setSelectedIds(List<String> selectedIds)
  {
    this.selectedIds = selectedIds;
  }
  
  public boolean isInSelectedList(String checkId)
  {
    for (String id : getSelectedIds())
    {
      if (id.equals(checkId))
        return true;
    }

    return false;
  }
}