package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: 18.03.2006
 * Time: 14:33:08
 */
public class ShippingPosition
{
  protected static Log log = LogFactory.getLog(ShippingPosition.class);

  String number;

  String id;
  String shp_id;
  ProduceCostProduce produce = new ProduceCostProduce();
  StuffCategory stuffCategory = new StuffCategory();
  String count;
  String saleSumPlusNds;

  // calculated fields
  String purchaseSum;
  String transportSum;
  String customCharges;

  List<ManagerPercent> managers = new ArrayList<ManagerPercent>();
  String enterInUseDate;
  String montageTime;
  String montageOff;
  String montageOffCheckbox;
  String serialNumber;
  String yearOut;
  String lprId;

  String ctn_number;
  String prc_date;

  String itogStringText = "";
  boolean itogString = false;
  boolean showWarnInNameMain = false;

  public ShippingPosition()
  {
    managers.add(new ManagerPercent(100, new User()));
  }

  public ShippingPosition(String id)
  {
    this.id = id;
  }

  public ShippingPosition(ShippingPosition position)
  {
    this.number = position.getNumber();

    this.id = position.id;
    this.shp_id = position.shp_id;
    this.produce = new ProduceCostProduce(position.produce);
    this.stuffCategory = new StuffCategory(position.stuffCategory);
    this.count = position.count;
    this.saleSumPlusNds = position.saleSumPlusNds;
    this.purchaseSum = position.purchaseSum;
    this.transportSum = position.transportSum;
    this.customCharges = position.customCharges;
    for (ManagerPercent managerPercent : position.managers)
    {
      this.managers.add(new ManagerPercent(managerPercent));
    }
    this.enterInUseDate = position.enterInUseDate;
    this.montageTime = position.montageTime;
    this.montageOff = position.montageOff;
    this.montageOffCheckbox = position.montageOffCheckbox;
    this.serialNumber = position.serialNumber;
    this.yearOut = position.yearOut;
    this.lprId = position.lprId;

    this.ctn_number = position.ctn_number;
    this.prc_date = position.prc_date;

    this.itogStringText = position.getItogStringText();
    this.itogString = position.isItogString();

    this.showWarnInNameMain = position.isShowWarnInNameMain();
  }

  public void calculate()
  {
    setPurchaseSum(produce.getLpc_cost_one() * getCount());
    setTransportSum(produce.getLpc_sum_transport() / produce.getLpc_count() * getCount());
    setCustomCharges(produce.getLpc_custom() / produce.getLpc_count() * getCount());
  }

  public String getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public ProduceCostProduce getProduce()
  {
    return produce;
  }

  public String getProduceNameFormatted()
  {
    String resStr = "";
    if (isItogString())
      resStr += getItogStringText();
    else
    {
      resStr += produce.getProduce().getName();
      IActionContext context = ActionContext.threadInstance();
      try
      {
        resStr += StrutsUtil.getMessage(context, "ShippingProduces.warnInNameFlag", getNumber(), Boolean.toString(isShowWarnInNameMain()));
        resStr += StrutsUtil.getMessage(context, "ShippingProduces.warnInNomenclatureTemplate", getNumber());
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return resStr;
  }

  public String getFullNameProduce()
  {
    return produce.getProduce().getFullName();
  }

  public void setProduce(ProduceCostProduce produce)
  {
    this.produce = produce;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public String getCatalogNumberForStuffCategory()
  {
    if (null == getStuffCategory().getId() || null == getStuffCategory() || getStuffCategory().getId().isEmpty())
    {
      return "";
    }

    DboProduce produceInSession = (DboProduce) HibernateUtil.associateWithCurentSession(produce.getProduce());
    DboCatalogNumber catalogNumber = produceInSession.getCatalogNumbers().get(new Integer(getStuffCategory().getId()));
    return catalogNumber == null ? null : catalogNumber.getNumber();
  }

  public double getCount()
  {
    return StringUtil.appCurrencyString2double(count);
  }

  public void setCount(double count)
  {
    this.count = StringUtil.double2appCurrencyString(count);
  }

  public String getCountFormatted()
  {
    return count;
  }

  public void setCountFormatted(String count)
  {
    this.count = count;
  }

  public double getPurchaseSum()
  {
    return StringUtil.appCurrencyString2double(purchaseSum);
  }

  public void setPurchaseSum(double purchaseSum)
  {
    this.purchaseSum = StringUtil.double2appCurrencyString(purchaseSum);
  }

  public String getPurchaseSumFormatted()
  {
    return purchaseSum;
  }

  public void setPurchaseSumFormatted(String purchaseSum)
  {
    this.purchaseSum = purchaseSum;
  }

  public double getTransportSum()
  {
    return StringUtil.appCurrencyString2double(transportSum);
  }

  public void setTransportSum(double transportSum)
  {
    this.transportSum = StringUtil.double2appCurrencyString(transportSum);
  }

  public String getTransportSumFormatted()
  {
    return transportSum;
  }

  public void setTransportSumFormatted(String transportSum)
  {
    this.transportSum = transportSum;
  }

  public double getCustomCharges()
  {
    return StringUtil.appCurrencyString2double(customCharges);
  }

  public void setCustomCharges(double customCharges)
  {
    this.customCharges = StringUtil.double2appCurrencyString(customCharges);
  }

  public String getCustomChargesFormatted()
  {
    return customCharges;
  }

  public void setCustomChargesFormatted(String customCharges)
  {
    this.customCharges = customCharges;
  }

  public double getSaleSumPlusNds()
  {
    return StringUtil.appCurrencyString2doubleSpecial(saleSumPlusNds);
  }

  public void setSaleSumPlusNds(double saleSumPlusNds)
  {
    this.saleSumPlusNds = StringUtil.double2appCurrencyString(saleSumPlusNds);
  }

  public String getSaleSumPlusNdsFormatted()
  {
    return saleSumPlusNds;
  }

  public void setSaleSumPlusNdsFormatted(String saleSumPlusNds)
  {
    this.saleSumPlusNds = saleSumPlusNds;
  }

  public List<ManagerPercent> getManagers()
  {
    return managers;
  }

  public void setManagers(List<ManagerPercent> managers)
  {
    this.managers = managers;
  }

  public String getEnterInUseDate()
  {
    return enterInUseDate;
  }

  public String getEnterInUseDateTs()
  {
    return StringUtil.appDateString2dbDateString(enterInUseDate);
  }

  public void setEnterInUseDate(String enterInUseDate)
  {
    this.enterInUseDate = enterInUseDate;
  }

  public double getMontageTime()
  {
    return StringUtil.appCurrencyString2double(montageTime);
  }

  public void setMontageTime(double montageTime)
  {
    this.montageTime = StringUtil.double2appCurrencyStringByMask(montageTime, "##0.0");
  }

  public String getMontageTime_formatted()
  {
    return montageTime;
  }

  public void setMontageTime_formatted(String montageTime)
  {
    this.montageTime = montageTime;
  }

  public String getMontageOff()
  {
    return montageOff;
  }

  public void setMontageOff(String montageOff)
  {
    if (!"1".equals(montageOff))
      this.montageOff = "";
    else
      this.montageOff = montageOff;
  }

  public String getMontageOffCheckbox()
  {
    return montageOffCheckbox;
  }

  public void setMontageOffCheckbox(String montageOffCheckbox)
  {
    this.montageOffCheckbox = montageOffCheckbox;
  }

  public String getSerialNumber()
  {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber)
  {
    this.serialNumber = serialNumber;
  }

  public String getYearOut()
  {
    return yearOut;
  }

  public void setYearOut(String yearOut)
  {
    this.yearOut = yearOut;
  }

  public String getLprId()
  {
    return lprId;
  }

  public void setLprId(String lprId)
  {
    this.lprId = lprId;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getPrc_date()
  {
    return prc_date;
  }

  public String getPrc_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(prc_date);
  }

  public void setPrc_date(String prc_date)
  {
    this.prc_date = prc_date;
  }

  public String getItogStringText()
  {
    return itogStringText;
  }

  public void setItogStringText(String itogStringText)
  {
    this.itogStringText = itogStringText;
  }

  public boolean isItogString()
  {
    return itogString;
  }

  public void setItogString(boolean itogString)
  {
    this.itogString = itogString;
  }

  public boolean isShowWarnInNameMain()
  {
    return showWarnInNameMain;
  }

  public void setShowWarnInNameMain(boolean showWarnInNameMain)
  {
    this.showWarnInNameMain = showWarnInNameMain;
  }

  public void setListParentIds()
  {
    for (ManagerPercent managerPercent : getManagers())
    {
      managerPercent.setParentId(new Integer(id));
    }
  }

  public void setListIdsToNull()
  {
    for (ManagerPercent managerPercent : getManagers())
    {
      managerPercent.setId(null);
    }
  }

  public int getManagersCount()
  {
    return managers.size();
  }
}
