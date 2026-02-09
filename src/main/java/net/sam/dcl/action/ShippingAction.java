package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ShippingData;
import net.sam.dcl.form.ShippingForm;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.dbo.DboCatalogNumber;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ShippingAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(ShippingAction.class);
  public static final String SHIPPING_POSITIONS_LOCK_NAME = "ShippingPositions";
  public static final String SHIPPING_POSITIONS_LOCK_ID = "FULL TABLE";

  private void saveCurrentFormToBean(IActionContext context)
  {
    ShippingForm form = (ShippingForm) context.getForm();
    Shipping shipping = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class)).getShipping();

    shipping.setShp_id(form.getShp_id());
    shipping.setContractor(form.getContractor());
    shipping.setCurrency(form.getCurrency());
    shipping.setShp_date(form.getShp_date());
    shipping.setShp_date_expiration(form.getShp_date_expiration());
    shipping.setShp_number(form.getShp_number());
    shipping.setShp_summ_plus_nds(form.getShp_summ_plus_nds());
    shipping.setShp_summ_transport(form.getShp_summ_transport());
    shipping.setShp_sum_update(form.getShp_sum_update());
    shipping.setSpecification(form.getSpecification());
    shipping.setShp_letter1_date(form.getShp_letter1_date());
    shipping.setShp_letter2_date(form.getShp_letter2_date());
    shipping.setShp_letter3_date(form.getShp_letter3_date());
    shipping.setShp_complaint_in_court_date(form.getShp_complaint_in_court_date());
    shipping.setShp_comment(form.getShp_comment());
    shipping.setShp_montage(form.getShp_montage());
    shipping.setShp_serial_num_year_out(form.getShp_serial_num_year_out());
    shipping.setShp_closed(form.getShp_closed());
    shipping.setShp_block(form.getShp_block());
    shipping.setContractorWhere(form.getContractorWhere());
    shipping.setContractWhere(form.getContractWhere());
    shipping.setShp_notice_date(form.getShp_notice_date());
    shipping.setShp_original(form.getShp_original());

    shipping.setNoticeScale(form.getNoticeScale());
  }

  private void getCurrentFormFromBean(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();
    form.setShp_id(shipping.getShp_id());
    form.setContractor(shipping.getContractor());
    form.setCurrency(shipping.getCurrency());
    form.setShp_date(shipping.getShp_date());
    form.setShp_date_expiration(shipping.getShp_date_expiration());
    form.setShp_date_create(shipping.getShp_create_date());
    form.setShp_date_edit(shipping.getShp_edit_date());
    form.setShp_number(shipping.getShp_number());
    form.setShp_summ_plus_nds(shipping.getShp_summ_plus_nds());
    form.setShp_summ_transport(shipping.getShp_summ_transport());
    form.setShp_sum_update(shipping.getShp_sum_update());
    form.setSpecification(shipping.getSpecification());
    if (!StringUtil.isEmpty(shipping.getSpecification().getSpc_id()))
    {
      form.setContract(ContractDAO.load(context, shipping.getSpecification().getCon_id(), false));
    }
    else
    {
      form.setContract(new Contract());
    }
    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    form.setFormReadOnly("1".equals(shipping.getShp_block()) || !(currentUser.isAdmin() || currentUser.isEconomist()) || currentUser.isOnlyManager() || currentUser.isOnlyLawyer());
    form.setShp_letter1_date(shipping.getShp_letter1_date());
    form.setShp_letter2_date(shipping.getShp_letter2_date());
    form.setShp_letter3_date(shipping.getShp_letter3_date());
    form.setShp_complaint_in_court_date(shipping.getShp_complaint_in_court_date());
    form.setShp_comment(shipping.getShp_comment());
    form.setShp_montage(shipping.getShp_montage());
    form.setShp_montage_checkbox(shipping.getShp_montage());
    form.setShp_serial_num_year_out(shipping.getShp_serial_num_year_out());
    form.setShp_serial_num_year_out_checkbox(shipping.getShp_serial_num_year_out());
    form.setShp_block(shipping.getShp_block());
    form.setContractorWhere(shipping.getContractorWhere());
    form.setContractWhere(shipping.getContractWhere());
    form.setShp_notice_date(shipping.getShp_notice_date());
    form.setShp_original(shipping.getShp_original());

    form.setNoticeScale(shipping.getNoticeScale());
  }

  public ActionForward editShippingPositions(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    ShippingForm form = (ShippingForm) context.getForm();
    SyncObject syncObjectForLock = new SyncObject(SHIPPING_POSITIONS_LOCK_NAME, SHIPPING_POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
    SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
    if (!syncObjectForLock.equals(syncObjectCurrent))
    {
      StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
      StrutsUtil.addError(context, "error.record.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
      form.setNeedPrintNotice(false);
      return show(context);
    }

    return context.getMapping().findForward("editShippingPositions");
  }

  protected List<ShippingData.RightRecord> getResultList(IActionContext context) throws Exception
  {
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    ShippingForm form = (ShippingForm) context.getForm();
    List<ShippingData.RightRecord> res = new ArrayList<ShippingData.RightRecord>();
    double purchaseSum = 0;
    double transportSum = 0;
    double customSum = 0;
    double sum = 0;
    for (int i = 0; i < data.getRightIntermediate().size(); i++)
    {
      ShippingData.RightRecord record = data.getRightIntermediate().get(i);
      purchaseSum += record.getPosition().getPurchaseSum();
      transportSum += record.getPosition().getTransportSum();
      customSum += record.getPosition().getCustomCharges();
      sum += record.getPosition().getSaleSumPlusNds();
      res.add(record);
    }
    ShippingData.RightRecord record = new ShippingData.RightRecord();
    record.getPosition().setItogStringText(StrutsUtil.getMessage(context, "Shipping.itogo"));
    record.getPosition().setItogString(true);
    record.getPosition().setPurchaseSum(purchaseSum);
    record.getPosition().setTransportSum(transportSum);
    record.getPosition().setCustomCharges(customSum);
    record.getPosition().setSaleSumPlusNds(sum);
    res.add(record);

    record = new ShippingData.RightRecord();
    record.getPosition().setItogStringText(StrutsUtil.getMessage(context, "Shipping.itogo2"));
    record.getPosition().setItogString(true);
    record.getPosition().setTransportSumFormatted("");
    record.getPosition().setPurchaseSumFormatted("");
    record.getPosition().setCustomChargesFormatted("");
    record.getPosition().setSaleSumPlusNds(form.getShp_summ_plus_nds() - sum);
    res.add(record);

    return res;
  }

  public ActionForward printNotice(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    saveCurrentFormToBean(context);
    form.setNeedPrintNotice(true);
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    if (!StringUtil.isEmpty(data.getShipping().getContractorWhere().getId()))
    {
      data.getShipping().setContractorWhere(ContractorDAO.load(context, data.getShipping().getContractorWhere().getId()));
    }
    if (!StringUtil.isEmpty(data.getShipping().getContractWhere().getCon_id()))
    {
      data.getShipping().setContractWhere(ContractDAO.load(context, data.getShipping().getContractWhere().getCon_id(), false));
    }
    Shipping shipping = new Shipping(data.getShipping());
    for (int i = 0; i < form.getGrid().getDataList().size() - 2; i++)
    {
      ShippingData.RightRecord record = (ShippingData.RightRecord) form.getGrid().getDataList().get(i);
      ShippingPosition position = new ShippingPosition(record.getPosition());
      shipping.getShippingPositions().add(position);
    }
    //удаляем дубли по позициям (суммируем количество)
    int i = 0;
    while (i < shipping.getShippingPositions().size())
    {
      ShippingPosition position1 = shipping.getShippingPositions().get(i);

      int j = i + 1;
      while (j < shipping.getShippingPositions().size())
      {
        ShippingPosition position2 = shipping.getShippingPositions().get(j);
        if (position1.getProduce().getProduce().getId().equals(position2.getProduce().getProduce().getId()))
        {
          position1.setCount(position1.getCount() + position2.getCount());
          shipping.getShippingPositions().remove(j);
          j--;
        }

        j++;
      }

      i++;
    }
    shipping.setNoticeScale(form.getNoticeScale());
    StoreUtil.putSession(context.getRequest(), shipping);
    return show(context);
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final ShippingForm form = (ShippingForm) context.getForm();
    final ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();

    form.getGrid().setDataList(getResultList(context));
    form.setCreateUser(shipping.getCreateUser());
    form.setEditUser(shipping.getEditUser());
    form.setManager(shipping.getManager());
    if (!StringUtil.isEmpty(form.getContractWhere().getCon_id()))
    {
      form.setContractWhere(ContractDAO.load(context, form.getContractWhere().getCon_id(), false));
    }
    else
    {
      form.setContractWhere(new Contract());
    }

    setVisuallyAttributes(context);

    form.setShowBlockMsg(data.isBlock());

    if (form.isNeedPrintNotice())
    {
      form.setPrintNotice("true");
    }
    else
    {
      form.setPrintNotice("false");
    }
    form.setNeedPrintNotice(false);

    User currentUser = UserUtil.getCurrentUser(context.getRequest());

    //Доступ к редактированию секции "Письма": админ, юрист
    //У заблокированных незакрытых доков эти поля должны быть доступны.
    //Доступ к редактированию поля Комментарий: админ, юрист, экономист
    //Экономиста не было, по 1041 - права сделать как у юриста.
    if ((
            !"1".equals(shipping.getShp_block()) ||
                    ("1".equals(shipping.getShp_block()) && StringUtil.isEmpty(shipping.getShp_closed()))
    )
            && (currentUser.isAdmin() || currentUser.isLawyer() || currentUser.isEconomist())
            )
    {
      form.setReadOnlyComment(false);
      if (currentUser.isAdmin() || currentUser.isLawyer())
      {
        form.setReadOnlyIfNotAdmOrLawyer(false);
      }
      else
      {
        form.setReadOnlyIfNotAdmOrLawyer(true);
      }
    }
    else
    {
      form.setReadOnlyComment(true);
      form.setReadOnlyIfNotAdmOrLawyer(true);
    }

    //Доступ к чекбоксу "Требуется монтаж и наладка": админ, экономист
    if (!"1".equals(shipping.getShp_block()) && (currentUser.isAdmin() || currentUser.isEconomist()))
    {
      form.setReadOnlyIfNotAdmOrEconomist(false);
    }
    else
    {
      form.setReadOnlyIfNotAdmOrEconomist(true);
    }

    //отображение колонки "Сумма закупки"
    if (currentUser.isAdmin() || currentUser.isEconomist())
    {
      form.setShowPurchaseSum(true);
    }
    else
    {
      form.setShowPurchaseSum(false);
    }

    if (StringUtil.isEmpty(form.getSpecification().getSpc_id()))
    {
      form.setShp_date_expiration(form.getShp_date());
    }

    if (StringUtil.isEmpty(form.getContractorWhere().getId()))
    {
      form.setContractorWhere(ContractorDAO.load(context, Config.getString(Constants.contractorWhereNoticeForShipping)));
    }

    UpdateWarn(form, shipping);

    return context.getMapping().findForward("form");
  }

  private void UpdateWarn(ShippingForm form, Shipping shipping)
  {
    for (int i = 0; i < form.getGrid().getDataList().size(); i++)
    {
      ShippingData.RightRecord record = (ShippingData.RightRecord) form.getGrid().getDataList().get(i);
      record.getPosition().setNumber(Integer.toString(i));
      record.getPosition().setMontageOffCheckbox(record.getPosition().getMontageOff());
      // Если "Требуется монтаж и наладка"=ДА И (в табличной части есть позиции, у которых "Без монтажа"=НЕТ И в справочнике "Номенклатура" поле "Нормативы и тарифы монтажа и наладки (Тип оборудования)" пустое)
      // то выводим предупреждение
      DboCatalogNumber dboCatalogNumber = record.getPosition().getProduce().getDboCatalogNumberForStuffCategory();
      boolean showWarnInNameMain = !record.getPosition().isItogString() && !StringUtil.isEmpty(shipping.getShp_montage()) && (dboCatalogNumber == null || dboCatalogNumber.getMontageAdjustment() == null);
      record.getPosition().setShowWarnInNameMain(showWarnInNameMain);
    }
  }

  public ActionForward backFromEditPosition(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context);
    ShippingForm form = (ShippingForm) context.getForm();
    form.setNeedPrintNotice(false);
    form.setNeedPrintNotice(false);
    return show(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ShippingData data = new ShippingData();

    data.getShipping().setShp_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    data.getShipping().setClear_check(true);
    StoreUtil.putSession(context.getRequest(), data);

    getCurrentFormFromBean(context);

    ShippingForm form = (ShippingForm) context.getForm();
    if (!StringUtil.isEmpty(form.getSpecification().getSpc_montage()))
    {
      form.setShp_montage(form.getSpecification().getSpc_montage());
      form.setShp_montage_checkbox(form.getSpecification().getSpc_montage());
    }
    //Если "Требуется монтаж и наладка"=[V], то "Указать серийные №№ и годы выпуска"=[V] (но с возможностью эту галочку убрать)
    if (!StringUtil.isEmpty(form.getShp_montage_checkbox()))
    {
      form.setShp_serial_num_year_out("1");
      form.setShp_serial_num_year_out_checkbox("1");
    }
    form.setManager(new User());
    form.setShp_original("1");

    form.setNeedPrintNotice(false);
    form.setNeedPrintNotice(false);

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    ShippingData data = new ShippingData();
    data.setShipping(ShippingDAO.load(context, form.getShp_id()));
    data.getShipping().setClear_check(true);
    for (int i = 0; i < data.getShipping().getShippingPositions().size(); i++)
    {
      ShippingPosition position = data.getShipping().getShippingPositions().get(i);
      ShippingData.RightRecord rightRecord = new ShippingData.RightRecord();
      rightRecord.setId(i);
      rightRecord.setPosition(new ShippingPosition(position));
      data.getRightIntermediate().add(rightRecord);
    }
    StoreUtil.putSession(context.getRequest(), data);
    getCurrentFormFromBean(context);
    if (!StringUtil.isEmpty(form.getSpecification().getSpc_montage()))
    {
      form.setShp_montage(form.getSpecification().getSpc_montage());
      form.setShp_montage_checkbox(form.getSpecification().getSpc_montage());
    }
    form.setManager(new User());

    form.setNeedPrintNotice(false);
    form.setNeedPrintNotice(false);

    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    unlockRecords(context);
    return context.getMapping().findForward("back");
  }

  protected void unlockRecords(IActionContext context)
  {
    LockedRecords.getLockedRecords().unlockWithTheSame(SHIPPING_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
  }

  public ActionForward newContractor(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("newContractor");
  }

  public ActionForward retFromContractor(IActionContext context) throws Exception
  {
    Shipping shipping = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class)).getShipping();
    String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
    if (!StringUtil.isEmpty(contractorId))
    {
      shipping.setContractor(ContractorDAO.load(context, contractorId));
      shipping.setSpecification(new Specification());
    }
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);
    getCurrentFormFromBean(context);
    ShippingForm form = (ShippingForm) context.getForm();
    form.setNeedPrintNotice(false);
    form.setNeedPrintNotice(false);
    return show(context);
  }

  public ActionForward beforeSave(IActionContext context) throws Exception
  {
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    ShippingForm form = (ShippingForm) context.getForm();
    double sum = 0;
    boolean isFilledManagerAndStuffCategory = true;
    for (int i = 0; i < data.getRightIntermediate().size(); i++)
    {
      ShippingData.RightRecord record = data.getRightIntermediate().get(i);

      if (!StringUtil.isEmpty(form.getShp_montage()))
      {
        if (StringUtil.isEmpty(record.getPosition().getMontageOff()) && !StringUtil.isEmpty(record.getPosition().getEnterInUseDate()) && StringUtil.isEmpty(record.getPosition().getEnterInUseDateTs()))
        {
          StrutsUtil.addError(context, "error.shipping.incorrect_enter_date_field", StrutsUtil.getMessage(context, "ShippingProduces.enterInUseDate"), record.getPosition().getProduce().getProduce_name(), null);
          return show(context);
        }

        if (StringUtil.isEmpty(record.getPosition().getMontageOff()) && Double.isNaN(record.getPosition().getMontageTime()))
        {
          StrutsUtil.addError(context, "error.shipping.incorrect_montage_time_field", StrutsUtil.getMessage(context, "ShippingProduces.montageTime"), record.getPosition().getProduce().getProduce_name(), null);
          return show(context);
        }

        if (!StringUtil.isEmpty(record.getPosition().getSerialNumber()) && record.getPosition().getSerialNumber().length() > 100)
        {
          StrutsUtil.addError(context, "error.shipping.incorrect_serial_number_len", record.getPosition().getProduce().getProduce_name(), null);
          return show(context);
        }

        if (!StringUtil.isEmpty(record.getPosition().getYearOut()) && record.getPosition().getYearOut().length() > 30)
        {
          StrutsUtil.addError(context, "error.shipping.incorrect_year_out_len", record.getPosition().getProduce().getProduce_name(), null);
          return show(context);
        }

        if (!StringUtil.isEmpty(record.getPosition().getMontageOff()))
        {
          record.getPosition().setEnterInUseDate("");
          record.getPosition().setMontageTime(0.0);
        }
      }
      if (validateShippingPosition(context, record.getPosition()))
      {
        return show(context);
      }

      sum += record.getPosition().getSaleSumPlusNds();
      if (StringUtil.isEmpty(record.getPosition().getStuffCategory().getId()))
      {
        isFilledManagerAndStuffCategory = false;
      }
      else
      {
        for (ManagerPercent managerPercent : record.getPosition().getManagers())
        {
          if (StringUtil.isEmpty(managerPercent.getManager().getUsr_id()))
          {
            isFilledManagerAndStuffCategory = false;
            break;
          }
        }
      }
    }

    boolean wasBlock = !StringUtil.isEmpty(form.getShp_block());

    double sumForBlock = form.getShp_summ_plus_nds() - sum;
    sumForBlock = StringUtil.roundN(sumForBlock, 2);
    data.setBlock(isFilledManagerAndStuffCategory &&
            sumForBlock == 0 &&
            !StringUtil.isEmpty(form.getSpecification().getSpc_id()));

    if (!StringUtil.isEmpty(form.getShp_montage()))
    {
      boolean correctInLines = true;
      for (int i = 0; i < data.getRightIntermediate().size(); i++)
      {
        ShippingData.RightRecord record = data.getRightIntermediate().get(i);
        if (StringUtil.isEmpty(record.getPosition().getEnterInUseDate()) && record.getPosition().getMontageTime() == 0 && StringUtil.isEmpty(record.getPosition().getMontageOff()))
        {
          correctInLines = false;
        }
      }
      if (!wasBlock && data.isBlock() && !correctInLines)
      {
        data.setBlock(false);
      }
    }

    if (!wasBlock && data.isBlock())
    {
      return show(context);
    }
    else
    {
      return process(context);
    }
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    boolean retFromSave = saveCommon(context);

    if (retFromSave)
    {
      return back(context);
    }
    else
    {
      return show(context);
    }
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    String errMsg = "";

    Contract contract = new Contract();
    try
    {
      if (!StringUtil.isEmpty(form.getContract().getCon_id()))
      {
        contract = ContractDAO.load(context, form.getContract().getCon_id(), false);
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(context, e);
    }

    Date shpDate = StringUtil.getCurrentDateTime();
    try
    {
      shpDate = StringUtil.appDateString2Date(form.getShp_date());
    }
    catch (Exception e)
    {
      log.error(e.toString());
    }
    if (shpDate.after(StringUtil.getCurrentDateTime()))
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.shipping.date_in_future");
    }

    if (!StringUtil.isEmpty(form.getSpecification().getSpc_id()) &&
            !form.getCurrency().getId().equalsIgnoreCase(contract.getCurrency().getId()))
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.shipping.currency");
    }

    if (!form.isShowPayAfterMontage() && StringUtil.isEmpty(form.getShp_date_expiration()))
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.shipping.expiration_date");
    }

    Date shpLetter1Date = StringUtil.getCurrentDateTime();
    Date shpLetter2Date = StringUtil.getCurrentDateTime();
    Date shpLetter3Date = StringUtil.getCurrentDateTime();
    Date shpComplaintInCourtDate = StringUtil.getCurrentDateTime();
    try
    {
      shpLetter1Date = StringUtil.appDateString2Date(form.getShp_letter1_date());
      shpLetter2Date = StringUtil.appDateString2Date(form.getShp_letter2_date());
      shpLetter3Date = StringUtil.appDateString2Date(form.getShp_letter3_date());
      shpComplaintInCourtDate = StringUtil.appDateString2Date(form.getShp_complaint_in_court_date());
    }
    catch (Exception e)
    {
      log.error(e.toString());
    }

    if (!StringUtil.isEmpty(form.getShp_letter2_date()) && !StringUtil.isEmpty(form.getShp_letter1_date()))
    {
      if (shpLetter2Date.before(shpLetter1Date))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.shipping.incorrect_date_sequence1_2");
      }
    }
    if (!StringUtil.isEmpty(form.getShp_letter3_date()) && !StringUtil.isEmpty(form.getShp_letter1_date()))
    {
      if (shpLetter3Date.before(shpLetter1Date))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.shipping.incorrect_date_sequence1_3");
      }
    }
    if (!StringUtil.isEmpty(form.getShp_complaint_in_court_date()) && !StringUtil.isEmpty(form.getShp_letter1_date()))
    {
      if (shpComplaintInCourtDate.before(shpLetter1Date))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.shipping.incorrect_date_sequence1_4");
      }
    }
    if (!StringUtil.isEmpty(form.getShp_letter3_date()) && !StringUtil.isEmpty(form.getShp_letter2_date()))
    {
      if (shpLetter3Date.before(shpLetter2Date))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.shipping.incorrect_date_sequence2_1");
      }
    }
    if (!StringUtil.isEmpty(form.getShp_complaint_in_court_date()) && !StringUtil.isEmpty(form.getShp_letter2_date()))
    {
      if (shpComplaintInCourtDate.before(shpLetter2Date))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.shipping.incorrect_date_sequence2_2");
      }
    }
    if (!StringUtil.isEmpty(form.getShp_complaint_in_court_date()) && !StringUtil.isEmpty(form.getShp_letter3_date()))
    {
      if (shpComplaintInCourtDate.before(shpLetter3Date))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.shipping.incorrect_date_sequence3");
      }
    }

    saveCurrentFormToBean(context);
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();
    if (!StringUtil.isEmpty(shipping.getShp_id()))
    {
      String deletedPositionIds = "";
      List<ShippingPosition> deleted = data.findDeleted();
      for (ShippingPosition position : deleted)
      {
        if (!StringUtil.isEmpty(position.getId()))
        {
          deletedPositionIds += position.getId() + ";";
        }
      }
      shipping.setDeletedPositionIds(deletedPositionIds);
      if (!StringUtil.isEmpty(deletedPositionIds))
      {
        ShippingDAO.loadPositionsInContractorRequest(context, shipping);
        if (!StringUtil.isEmpty(shipping.getDeletedPositionOccupiedIds()))
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.shipping.contractorRequestOccupied");
        }
      }
    }

    if (!StringUtil.isEmpty(errMsg))
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return false;
    }

    if (StringUtil.isEmpty(shipping.getShp_id()))
    {
      ShippingDAO.insert(context, shipping);
      List<ShippingData.RightRecord> added = data.findAdded();
      for (ShippingData.RightRecord record : added)
      {
        record.getPosition().setShp_id(shipping.getShp_id());
        ShippingPositionDAO.insert(context, record.getPosition());
      }
    }
    else
    {
      ShippingDAO.save(context, shipping);
      List<ShippingPosition> deleted = data.findDeleted();
      for (ShippingPosition position : deleted)
      {
        ShippingPositionDAO.delete(context, position);
      }
      List<ShippingData.RightRecord> changed = data.findChanged();
      for (ShippingData.RightRecord record : changed)
      {
        ShippingPositionDAO.save(context, record.getPosition());
      }
      List<ShippingData.RightRecord> added = data.findAdded();
      for (ShippingData.RightRecord record : added)
      {
        record.getPosition().setShp_id(shipping.getShp_id());
        ShippingPositionDAO.insert(context, record.getPosition());
      }
    }

    if (data.isBlock())
    {
      ShippingDAO.setBlock(context, shipping);
    }

    return true;
  }

  private class ReadOnlyChecker implements IReadOnlyChecker
  {
    private ShippingData data;

    public ReadOnlyChecker(ShippingData data)
    {
      this.data = data;
    }

    public boolean check(ReadOnlyCheckerContext ctx) throws Exception
    {
      return "1".equals(data.getShipping().getShp_block());
    }
  }

  private class StuffCategoryReadOnlyChecker implements IReadOnlyChecker
  {
    private ShippingData data;

    public StuffCategoryReadOnlyChecker(ShippingData data)
    {
      this.data = data;
    }

    public boolean check(ReadOnlyCheckerContext ctx) throws Exception
    {
      ShippingData.RightRecord rightRecord = (ShippingData.RightRecord) ctx.getBean();
      return "1".equals(data.getShipping().getShp_block()) || !StringUtil.isEmpty(rightRecord.getPosition().getProduce().getStuffCategory().getId());
    }
  }

  private class ControlShowChecker implements IShowChecker
  {
    private ShippingData data;

    public ControlShowChecker(ShippingData data)
    {
      this.data = data;
    }

    public boolean check(ShowCheckerContext context)
    {
      return !(context.getTable().getRecordCounter() > data.getRightIntermediate().size());
    }
  }

  public ActionForward ajaxManagersGrid(IActionContext context) throws Exception
  {
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));

    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("gridReadOnly", "1".equals(data.getShipping().getShp_block()) || currentUser.isOnlyManager() || currentUser.isOnlyLawyer());

    return context.getMapping().findForward("ajaxManagersGrid");
  }

  public ActionForward ajaxAddToGrid(IActionContext context) throws Exception
  {
    final ShippingForm form = (ShippingForm) context.getForm();

    int counter = Integer.parseInt(context.getRequest().getParameter("counter"));
    ShippingData.RightRecord rec = (ShippingData.RightRecord) form.getGrid().getDataList().get(counter);
    rec.getPosition().getManagers().add(new ManagerPercent(100, new User()));

    return ajaxManagersGrid(context);
  }

  public ActionForward ajaxRemoveFromGrid(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();

    int id = Integer.parseInt(context.getRequest().getParameter("id"));
    int counter = Integer.parseInt(context.getRequest().getParameter("counter"));
    ShippingData.RightRecord rec = (ShippingData.RightRecord) form.getGrid().getDataList().get(counter);
    rec.getPosition().getManagers().remove(id);
    if (rec.getPosition().getManagers().size() == 1)
    {
      rec.getPosition().getManagers().get(0).setPercent(100);
    }

    return ajaxManagersGrid(context);
  }

  public static boolean validateShippingPosition(IActionContext context, ShippingPosition position) throws Exception
  {
    // если здесь тока одна запись то и нечего проверять
    if (position.getManagers().size() == 1)
    {
      return false;
    }

    Integer sum = 0;
    boolean hasError = false;
    for (ManagerPercent managerPercent : position.getManagers())
    {
      sum += managerPercent.getPercent();
      if (StringUtil.isEmpty(managerPercent.getManager().getUsr_id()))
      {
        hasError = true;
        StrutsUtil.addError(context, "error.shipping.emptyManager", position.getProduce().getProduce_name(), null);
      }
      if (managerPercent.getPercent() < 1 || managerPercent.getPercent() > 99)
      {
        hasError = true;
        StrutsUtil.addError(context, "error.shipping.invalidPercent", position.getProduce().getProduce_name(), null);
      }
    }

    if (sum != 100)
    {
      StrutsUtil.addError(context, "error.shipping.invalidSumPercent", position.getProduce().getProduce_name(), null);
      hasError = true;
    }
    return hasError;
  }

  private void setVisuallyAttributes(IActionContext context)
  {
    final ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));

    context.getRequest().setAttribute("style-checker-first-column", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        if (context.getTable().getRecordCounter() == data.getRightIntermediate().size() + 1)
        {
          return "bold-cell";
        }
        if (context.getTable().getRecordCounter() == data.getRightIntermediate().size() + 2)
        {
          return "selected-cell";
        }
        return "first-column";
      }
    });

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        if (context.getTable().getRecordCounter() == data.getRightIntermediate().size() + 1)
        {
          return "bold-cell";
        }
        if (context.getTable().getRecordCounter() == data.getRightIntermediate().size() + 2)
        {
          return "selected-cell";
        }
        return "";
      }
    });

    context.getRequest().setAttribute("readOnlyChecker", new ReadOnlyChecker(data));

    context.getRequest().setAttribute("stuffCategoryReadOnlyChecker", new StuffCategoryReadOnlyChecker(data));
    context.getRequest().setAttribute("controlShowChecker", new ControlShowChecker(data));
  }

  public ActionForward ajaxShippingsGrid(IActionContext context) throws Exception
  {
    setVisuallyAttributes(context);

    return context.getMapping().findForward("ajaxShippingsGrid");
  }

  public ActionForward ajaxChangeMontage(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();
    String montage = context.getRequest().getParameter("montage");
    form.setShp_montage(montage);
    form.setShp_montage_checkbox(montage);
    shipping.setShp_montage(montage);

    UpdateWarn(form, shipping);
    
    return ajaxShippingsGrid(context);
  }

  public ActionForward ajaxChangeSerialNumYearOut(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();
    String serialNumYearOut = context.getRequest().getParameter("serialNumYearOut");
    form.setShp_serial_num_year_out(serialNumYearOut);
    form.setShp_serial_num_year_out_checkbox(serialNumYearOut);
    shipping.setShp_serial_num_year_out(serialNumYearOut);

    return ajaxShippingsGrid(context);
  }

  public ActionForward ajaxMakeMine(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    String ids = context.getRequest().getParameter("ids");
    String managerId = context.getRequest().getParameter("managerId");

    if (!StringUtil.isEmpty(managerId) && !StringUtil.isEmpty(ids))
    {
      for (int i = 0; i < form.getGrid().getDataList().size(); i++)
      {
        ShippingData.RightRecord record = (ShippingData.RightRecord) form.getGrid().getDataList().get(i);
        String checkStr = "|" + i + "|";
        if (ids.contains(checkStr))
        {
          //чистим всех менеджеров и добавляем одного
          record.getPosition().getManagers().clear();
          record.getPosition().getManagers().add(new ManagerPercent(100, UserDAO.load(context, managerId)));
        }
      }
    }

    return ajaxShippingsGrid(context);
  }

  public ActionForward ajaxChangeSpecification(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();
    String specificationId = context.getRequest().getParameter("specificationId");
    String resultMsg = "";
    if (!StringUtil.isEmpty(specificationId))
    {
      shipping.getSpecification().setSpc_id(specificationId);
      SpecificationDAO.load(context, shipping.getSpecification());
      form.setSpecification(shipping.getSpecification());
      form.setContract(ContractDAO.load(context, shipping.getSpecification().getCon_id(), false));

      if (!StringUtil.isEmpty(form.getSpecification().getSpc_montage()))
      {
        shipping.setShp_montage(form.getSpecification().getSpc_montage());
        form.setShp_montage(shipping.getShp_montage());
        resultMsg = shipping.getShp_montage();
      }
    }
    resultMsg += "|" + form.isReadOnlyDateExpiration() + "|" +
            form.isReadOnlyIfSpecHaveMontage() + "|" +
            form.getSpecification().getSpc_add_pay_cond();

    StrutsUtil.setAjaxResponse(context, resultMsg, false);
    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxCheckBeforeSave(IActionContext context) throws Exception
  {
    ShippingForm form = (ShippingForm) context.getForm();
    ShippingData data = ((ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class));
    Shipping shipping = data.getShipping();
    String specificationId = context.getRequest().getParameter("specificationId");
    String shpSumPlusNdsFormatted = context.getRequest().getParameter("shpSumPlusNdsFormatted");
    String resultMsg = "";
    if (!StringUtil.isEmpty(specificationId))
    {
      shipping.getSpecification().setSpc_id(specificationId);
      SpecificationDAO.load(context, shipping.getSpecification());
	    form.setShp_summ_plus_nds_formatted(shpSumPlusNdsFormatted);
	    double sum = 0;
	    shipping.getSpecification().setShp_id("-1");
	    if (!StringUtil.isEmpty(shipping.getShp_id()))
		    shipping.getSpecification().setShp_id(shipping.getShp_id());
	    SpecificationDAO.loadShippedSum(context, shipping.getSpecification());
	    sum = shipping.getSpecification().getShipped_summ();
	    sum += form.getShp_summ_plus_nds();
	    sum = StringUtil.roundN(sum, 2);
	    if (sum > shipping.getSpecification().getSpc_summ())
		    resultMsg = "IncorrectShippingSum"; //не важно какой текст, главное что не пустой
    }

    StrutsUtil.setAjaxResponse(context, resultMsg, false);
    return context.getMapping().findForward("ajax");
  }
}
