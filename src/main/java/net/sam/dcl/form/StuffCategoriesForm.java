package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.beans.User;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class StuffCategoriesForm extends BaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();
  String stf_id;
  String showInMontage;
  Integer mergeTargetId;
  GridResult<String> stuffCategoriesSelectedIds;

  public StuffCategoriesForm()
  {
    resetSelectedIds();
  }

  public String getAdmin()
  {
    IActionContext context = ActionContext.threadInstance();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());

    if (currentUser.isAdmin())
    {
      return "1";
    }
    return "";
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(String stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getShowInMontage()
  {
    return showInMontage;
  }

  public void setShowInMontage(String showInMontage)
  {
    this.showInMontage = showInMontage;
  }

  public Integer getMergeTargetId()
  {
    return mergeTargetId;
  }

  public void setMergeTargetId(Integer mergeTargetId)
  {
    this.mergeTargetId = mergeTargetId;
  }

  public GridResult<String> getStuffCategoriesSelectedIds()
  {
    return stuffCategoriesSelectedIds;
  }

  public void setStuffCategoriesSelectedIds(GridResult<String> stuffCategoriesSelectedIds)
  {
    this.stuffCategoriesSelectedIds = stuffCategoriesSelectedIds;
  }

  public void resetSelectedIds()
  {
    setStuffCategoriesSelectedIds(new GridResult<String>(String.class));
  }

  static public class StuffCategory
  {
    String stf_id;
    String stf_name;
    String stf_show_in_montage;
    String stf_occupied;
    String stf_occupied_in_montage;

    public String getStf_id()
    {
      return stf_id;
    }

    public void setStf_id(String stf_id)
    {
      this.stf_id = stf_id;
    }

    public String getStf_name()
    {
      return stf_name;
    }

    public void setStf_name(String stf_name)
    {
      this.stf_name = stf_name;
    }

    public String getStf_show_in_montage()
    {
      return stf_show_in_montage;
    }

    public void setStf_show_in_montage(String stf_show_in_montage)
    {
      this.stf_show_in_montage = stf_show_in_montage;
    }

    public String getStf_occupied()
    {
      return stf_occupied;
    }

    public void setStf_occupied(String stf_occupied)
    {
      this.stf_occupied = stf_occupied;
    }

    public String getStf_occupied_in_montage()
    {
      return stf_occupied_in_montage;
    }

    public void setStf_occupied_in_montage(String stf_occupied_in_montage)
    {
      this.stf_occupied_in_montage = stf_occupied_in_montage;
    }

    public boolean isOccupied()
    {
      return !StringUtil.isEmpty(getStf_occupied());
    }

    public boolean isOccupiedInMontage()
    {
      return !StringUtil.isEmpty(getStf_occupied_in_montage());
    }

  }

}
