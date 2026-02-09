package net.sam.dcl.form;

import net.sam.dcl.beans.ManagerPercent;
import net.sam.dcl.beans.Shipping;
import net.sam.dcl.beans.ShippingPosition;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: 18.03.2006
 * Time: 19:47:03
 */
public class ShippingData
{
  protected static Log log = LogFactory.getLog(ShippingData.class);
  Shipping shipping = new Shipping();
  List<LeftRecord> leftOriginal = null;
  List<LeftRecord> leftIntermediate = new ArrayList<LeftRecord>();
  List<RightRecord> rightIntermediate = new ArrayList<RightRecord>();
  boolean block;

  /**
   * @return List of ShippingPosition
   */
  public List<ShippingPosition> findDeleted()
  {
    List<ShippingPosition> resList = new ArrayList<ShippingPosition>();
    for (int i = 0; i < shipping.getShippingPositions().size(); i++)
    {
      ShippingPosition position = shipping.getShippingPositions().get(i);
      RightRecord record = findInResult(position.getId());
      if (record == null)
      {
        resList.add(position);
      }
    }
    return resList;
  }

  /**
   * @return List of RightRecord
   */
  public List<RightRecord> findChanged()
  {
    List<RightRecord> resList = new ArrayList<RightRecord>();
    for (int i = 0; i < shipping.getShippingPositions().size(); i++)
    {
      ShippingPosition position = shipping.getShippingPositions().get(i);
      RightRecord record = findInResult(position.getId());
      boolean added = false;
      if (record != null &&
              (position.getSaleSumPlusNds() != record.getPosition().getSaleSumPlusNds() ||
                      !StringUtil.equal(position.getStuffCategory().getId(), record.getPosition().getStuffCategory().getId()) ||
                      !StringUtil.equal(position.getEnterInUseDate(), record.getPosition().getEnterInUseDate()) ||
                      !StringUtil.equal(position.getMontageTime_formatted(), record.getPosition().getMontageTime_formatted()) ||
                      !StringUtil.equal(position.getMontageOff(), record.getPosition().getMontageOff()) ||
                      !StringUtil.equal(position.getSerialNumber(), record.getPosition().getSerialNumber()) ||
                      !StringUtil.equal(position.getYearOut(), record.getPosition().getYearOut())
              )
              )
      {
        resList.add(record);
        added = true;
      }
      if (!added && record != null)
      {
        if (position.getManagers().size() != record.getPosition().getManagers().size())
        {
          resList.add(record);
        }
        else
        {
          for (int j = 0; j < position.getManagers().size(); j++)
          {
            ManagerPercent managerPercent = position.getManagers().get(j);
            ManagerPercent managerPercentRecord = record.getPosition().getManagers().get(j);
            if (!StringUtil.equal(managerPercent.getManager().getUsr_id(), managerPercentRecord.getManager().getUsr_id()) ||
                    !StringUtil.equal(managerPercent.getPercent(), managerPercentRecord.getPercent()))
            {
              resList.add(record);
              break;
            }
          }
        }
      }
    }
    return resList;
  }

  /**
   * @return List of RightRecord
   */
  public List<RightRecord> findAdded()
  {
    List<RightRecord> resList = new ArrayList<RightRecord>();
    for (RightRecord record : rightIntermediate)
    {
      if (record.getPosition().getId() == null)
      {
        resList.add(record);
      }
    }
    return resList;
  }

  public RightRecord findInResult(String lps_id)
  {
    for (RightRecord record : rightIntermediate)
    {
      if (StringUtil.equal(lps_id, record.getPosition().getId()))
      {
        return record;
      }
    }
    return null;
  }

  public ShippingPosition findInShipping(String lpc_id)
  {
    for (int i = 0; i < shipping.getShippingPositions().size(); i++)
    {
      ShippingPosition position = shipping.getShippingPositions().get(i);
      if (StringUtil.equal(lpc_id, position.getProduce().getLpc_id()))
      {
        return position;
      }
    }
    return null;
  }

  public Shipping getShipping()
  {
    return shipping;
  }

  public void setShipping(Shipping shipping)
  {
    this.shipping = shipping;
  }

  public List<RightRecord> getRightIntermediate()
  {
    return rightIntermediate;
  }

  public void setRightIntermediate(List<RightRecord> rightIntermediate)
  {
    this.rightIntermediate = rightIntermediate;
  }

  public List<LeftRecord> getLeftIntermediate()
  {
    return leftIntermediate;
  }

  public void setLeftIntermediate(List<LeftRecord> leftIntermediate)
  {
    this.leftIntermediate = leftIntermediate;
  }

  public List<LeftRecord> getLeftOriginal()
  {
    return leftOriginal;
  }

  public void setLeftOriginal(List<LeftRecord> leftOriginal)
  {
    this.leftOriginal = leftOriginal;
  }

  public static class LeftRecord
  {
    String prc_id;
    String prc_number;
    String prc_date;
    String lpc_id;
    String produce_name;
    String type;
    String params;
    String add_params;
    StuffCategory stuffCategory = new StuffCategory();
    double lpc_count;
    double free_count;
    String shipped_count = "";
    String selected_id;
    String rut_id;
    String lpc_1c_number;
    String ctn_number;
    String lpr_id;
    String cpr_ctr_name;
    String cpr_number;
    boolean stuffCategoryAlreadyFilled = false;

    public LeftRecord()
    {
    }

    public LeftRecord(LeftRecord leftRecord)
    {
      this.prc_id = leftRecord.prc_id;
      this.prc_number = leftRecord.prc_number;
      this.prc_date = leftRecord.prc_date;
      this.lpc_id = leftRecord.lpc_id;
      this.produce_name = leftRecord.produce_name;
      this.type = leftRecord.type;
      this.params = leftRecord.params;
      this.add_params = leftRecord.add_params;
      this.stuffCategory = new StuffCategory(leftRecord.stuffCategory);
      this.lpc_count = leftRecord.lpc_count;
      this.free_count = leftRecord.free_count;
      this.shipped_count = leftRecord.shipped_count;
      this.selected_id = leftRecord.selected_id;
      this.rut_id = leftRecord.rut_id;
      this.lpc_1c_number = leftRecord.lpc_1c_number;
      this.ctn_number = leftRecord.ctn_number;
      this.lpr_id = leftRecord.lpr_id;
      this.cpr_ctr_name = leftRecord.cpr_ctr_name;
      this.cpr_number = leftRecord.cpr_number;
      this.stuffCategoryAlreadyFilled = leftRecord.stuffCategoryAlreadyFilled;
    }

    public String getPrc_id()
    {
      return prc_id;
    }

    public void setPrc_id(String prc_id)
    {
      this.prc_id = prc_id;
    }

    public String getPrc_number()
    {
      return prc_number;
    }

    public String getPrc_number_date()
    {
      String retStr = "";

      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "msg.common.from", prc_number, StringUtil.dbDateString2appDateString(prc_date));
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }

    public void setPrc_number(String prc_number)
    {
      this.prc_number = prc_number;
    }

    public String getPrc_date()
    {
      return prc_date;
    }

    public void setPrc_date(String prc_date)
    {
      this.prc_date = prc_date;
    }

    public String getLpc_id()
    {
      return lpc_id;
    }

    public void setLpc_id(String lpc_id)
    {
      this.lpc_id = lpc_id;
    }

    public String getProduce_name()
    {
      String retStr = produce_name +
              (StringUtil.isEmpty(type) ? "" : " " + type) +
              (StringUtil.isEmpty(params) ? "" : " " + params) +
              (StringUtil.isEmpty(add_params) ? "" : " " + add_params);

      if ( !StringUtil.isEmpty(getCpr_number()) )
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          retStr += StrutsUtil.getMessage(context, "ShippingPositions.cpInfo", getCpr_ctr_name(), getCpr_number());
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      return retStr;
    }

    public void setProduce_name(String produce_name)
    {
      this.produce_name = produce_name;
    }

    public String getType()
    {
      return type;
    }

    public void setType(String type)
    {
      this.type = type;
    }

    public String getParams()
    {
      return params;
    }

    public void setParams(String params)
    {
      this.params = params;
    }

    public String getAdd_params()
    {
      return add_params;
    }

    public void setAdd_params(String add_params)
    {
      this.add_params = add_params;
    }

    public StuffCategory getStuffCategory()
    {
      return stuffCategory;
    }

    public void setStuffCategory(StuffCategory stuffCategory)
    {
      this.stuffCategory = stuffCategory;
    }

    public double getLpc_count()
    {
      return lpc_count;
    }

    public void setLpc_count(double lpc_count)
    {
      this.lpc_count = lpc_count;
    }

    public void setLpc_count_formatted(String lpc_count)
    {
      this.lpc_count = StringUtil.appCurrencyString2double(lpc_count);
    }

    public String getLpc_count_formatted()
    {
      return StringUtil.double2appCurrencyString(lpc_count);
    }

    public double getFree_count()
    {
      return free_count;
    }

    public void setFree_count(double free_count)
    {
      this.free_count = free_count;
    }

    public void setFree_count_formatted(String free_count)
    {
      this.free_count = StringUtil.appCurrencyString2double(free_count);
    }

    public String getFree_count_formatted()
    {
      return StringUtil.double2appCurrencyString(free_count);
    }

    public double getShipped_count()
    {
      return StringUtil.appCurrencyString2double(shipped_count);
    }

    public void setShipped_count(double shipped_count)
    {
      this.shipped_count = StringUtil.double2appCurrencyString(shipped_count);
    }

    public void setShipped_count_formatted(String shipped_count)
    {
      this.shipped_count = shipped_count;
    }

    public String getShipped_count_formatted()
    {
      return shipped_count;
    }

    public String getSelected_id()
    {
      return selected_id;
    }

    public void setSelected_id(String selected_id)
    {
      this.selected_id = selected_id;
    }

    public String getRut_id()
    {
      return rut_id;
    }

    public void setRut_id(String rut_id)
    {
      this.rut_id = rut_id;
    }

    public String getLpc_1c_number()
    {
      return lpc_1c_number;
    }

    public void setLpc_1c_number(String lpc_1c_number)
    {
      this.lpc_1c_number = lpc_1c_number;
    }

    public String getCtn_number()
    {
      return ctn_number;
    }

    public void setCtn_number(String ctn_number)
    {
      this.ctn_number = ctn_number;
    }

    public String getLpr_id()
    {
      return lpr_id;
    }

    public void setLpr_id(String lpr_id)
    {
      this.lpr_id = lpr_id;
    }

    public String getCpr_ctr_name()
    {
      return cpr_ctr_name;
    }

    public void setCpr_ctr_name(String cpr_ctr_name)
    {
      this.cpr_ctr_name = cpr_ctr_name;
    }

    public String getCpr_number()
    {
      return cpr_number;
    }

    public void setCpr_number(String cpr_number)
    {
      this.cpr_number = cpr_number;
    }

    public boolean isStuffCategoryAlreadyFilled()
    {
      return stuffCategoryAlreadyFilled;
    }

    public void setStuffCategoryAlreadyFilled(boolean stuffCategoryAlreadyFilled)
    {
      this.stuffCategoryAlreadyFilled = stuffCategoryAlreadyFilled;
    }
  }

  public static class RightRecord
  {
    int id = 0;
    ShippingPosition position = new ShippingPosition();
    String selected_id;
    String selected_manager_id;

    public int getId()
    {
      return id;
    }

    public void setId(int id)
    {
      this.id = id;
    }

    public ShippingPosition getPosition()
    {
      return position;
    }

    public void setPosition(ShippingPosition position)
    {
      this.position = position;
    }

    public String getSelected_id()
    {
      return selected_id;
    }

    public void setSelected_id(String selected_id)
    {
      this.selected_id = selected_id;
    }

    public String getSelected_manager_id()
    {
      return selected_manager_id;
    }

    public void setSelected_manager_id(String selected_manager_id)
    {
      this.selected_manager_id = selected_manager_id;
    }
  }

  public boolean isBlock()
  {
    return block;
  }

  public void setBlock(boolean block)
  {
    this.block = block;
  }
}
