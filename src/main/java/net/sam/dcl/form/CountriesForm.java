package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.util.StringUtil;

public class CountriesForm extends BaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();
  boolean showEdit = false;
  boolean showMerge = false;

  Integer mergeTargetId;
  GridResult<String> countriesSelectedIds;

  public CountriesForm()
  {
    resetSelectedIds();
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public boolean isShowEdit()
  {
    return showEdit;
  }

  public void setShowEdit(boolean showEdit)
  {
    this.showEdit = showEdit;
  }

  public boolean isShowMerge()
  {
    return showMerge;
  }

  public void setShowMerge(boolean showMerge)
  {
    this.showMerge = showMerge;
  }

  public Integer getMergeTargetId()
  {
    return mergeTargetId;
  }

  public void setMergeTargetId(Integer mergeTargetId)
  {
    this.mergeTargetId = mergeTargetId;
  }

  public GridResult<String> getCountriesSelectedIds()
  {
    return countriesSelectedIds;
  }

  public void setCountriesSelectedIds(GridResult<String> countriesSelectedIds)
  {
    this.countriesSelectedIds = countriesSelectedIds;
  }

  public void resetSelectedIds()
  {
    setCountriesSelectedIds(new GridResult<String>(String.class));
  }

  static public class Country
  {
    String cut_id;
    String cut_name;
    String occupied;

    public String getCut_id()
    {
      return cut_id;
    }

    public void setCut_id(String cut_id)
    {
      this.cut_id = cut_id;
    }

    public String getCut_name()
    {
      return cut_name;
    }

    public void setCut_name(String cut_name)
    {
      this.cut_name = cut_name;
    }

    public boolean isOccupiedB()
    {
      return !StringUtil.isEmpty((occupied));
    }

    public String getOccupied()
    {
      return occupied;
    }

    public void setOccupied(String occupied)
    {
      this.occupied = occupied;
    }
  }
}