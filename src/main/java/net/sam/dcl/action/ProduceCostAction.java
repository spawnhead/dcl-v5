package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ProduceCostForm;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.form.ProduceCostPositionsForm;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.service.helper.Number1CHistoryHelper;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.util.*;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(ProduceCostAction.class);
  public static final String ORDER_POSITIONS_LOCK_NAME = "OrderPositions";
  public static final String ASSEMBLE_POSITIONS_LOCK_NAME = "AssemblePositions";
  public static final String DELIVERY_REQUEST_POSITIONS_LOCK_NAME = "DeliveryRequestPositions";
  public static final String SPECIFICATION_IMPORT_POSITIONS_LOCK_NAME = "SpecificationImportPositions";
  public static final String POSITIONS_LOCK_ID = "FULL TABLE";

  private void saveCurrentFormToBean(IActionContext context)
  {
    ProduceCostForm form = (ProduceCostForm) context.getForm();

    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);

    produceCost.setIs_new_doc(form.getIs_new_doc());

    produceCost.setPrc_id(form.getPrc_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        produceCost.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        produceCost.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    produceCost.setUsr_date_create(form.getUsr_date_create());
    produceCost.setUsr_date_edit(form.getUsr_date_edit());
    produceCost.setPrc_number(form.getPrc_number());
    produceCost.setPrc_date(form.getPrc_date());
    produceCost.setPrc_sum_transport(form.getPrc_sum_transport());
    produceCost.setPrc_weight(form.getPrc_weight());
    produceCost.setPrc_course_ltl_eur(form.getPrc_course_ltl_eur());
    produceCost.setPrc_block(form.getPrc_block());

    try
    {
      if (!StringUtil.isEmpty(form.getRoute().getId()))
      {
        produceCost.setRoute(RouteDAO.load(context, form.getRoute().getId()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    StoreUtil.putSession(context.getRequest(), produceCost);
  }

  private void getCurrentFormFromBean(IActionContext context, ProduceCost produceCostIn)
  {
    ProduceCostForm form = (ProduceCostForm) context.getForm();
    ProduceCost produceCost;
    if (null != produceCostIn)
    {
      produceCost = produceCostIn;
    }
    else
    {
      produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    }

    if (null != produceCost)
    {
      form.setIs_new_doc(produceCost.getIs_new_doc());

      form.setPrc_id(produceCost.getPrc_id());
      form.setCreateUser(produceCost.getCreateUser());
      form.setEditUser(produceCost.getEditUser());
      form.setUsr_date_create(produceCost.getUsr_date_create());
      form.setUsr_date_edit(produceCost.getUsr_date_edit());
      form.setPrc_number(produceCost.getPrc_number());
      form.setPrc_date(produceCost.getPrc_date());
      form.setRoute(produceCost.getRoute());
      form.setPrc_sum_transport(produceCost.getPrc_sum_transport());
      form.setPrc_weight(produceCost.getPrc_weight());
      form.setPrc_course_ltl_eur(produceCost.getPrc_course_ltl_eur());
      form.setPrc_block(produceCost.getPrc_block());

      form.getGridProduces().setDataList(produceCost.getProducesCost());
      form.getGridCustom().setDataList(produceCost.getCustomCodes());
    }
  }

  public ActionForward newProduce(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("newProduce");
  }

  public ActionForward editProduce(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("editProduce");
  }

  public ActionForward editCustom(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("editCustom");
  }

  public ActionForward deleteProduce(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    ProduceCostForm form = (ProduceCostForm) context.getForm();
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    produceCost.deleteProduceCostProduce(form.getNumber());

    return retFromProduceOperation(context);
  }

  public ActionForward importExcel(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("importExcel");
  }

  public ActionForward retFromProduceOperation(IActionContext context) throws Exception
  {
    return show(context);
  }

  public String getUnknownUserCodes(IActionContext context) throws Exception
  {
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    String ret_str = "";

    for (int i = 0; i < produceCost.getProducesCost().size() - produceCost.getCountItogRecord(); i++)
    {
      ProduceCostProduce produceCostProduce = produceCost.getProducesCost().get(i);

      if ( !UserDAO.loadUserByCode(context, produceCostProduce.getManager()) )
      {
        ret_str += " " + produceCostProduce.getManager().getUsr_code();
      }
    }

    return ret_str;
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    ProduceCostForm form = (ProduceCostForm) context.getForm();
    String errMsg = "";

    if ( "1".equals(form.getNeedRecalc()) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.produce_cost.need_recalc");
    }

    saveCurrentFormToBean(context);

    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);

    if ( produceCost.findEmptyUserCode() )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.produce_cost.codes_empty");
    }

    String unknownCodes = getUnknownUserCodes(context);
    if ( !StringUtil.isEmpty(unknownCodes) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.produce_cost.codes_unknown");
    }

    for (int i = 0; i < form.getGridProduces().getDataList().size() - produceCost.getCountItogRecord(); i++)
    {
      ProduceCostProduce produceCostProduce = produceCost.getProducesCost().get(i);
      if ( null == produceCostProduce.getProduce() || null == produceCostProduce.getProduce().getId() )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.produce_cost.null_produce");
        break;
      }

      int number1CMaxSize = Config.getNumber(Constants.lengthNumber1C, 12);
      if ( !StringUtil.isEmpty(produceCostProduce.getLpc_1c_number()) && produceCostProduce.getLpc_1c_number().length() > number1CMaxSize )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.produce_cost.to_long_1C_number", produceCostProduce.getLpc_1c_number());
        break;
      }
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return false;
    }

    if (StringUtil.isEmpty(form.getPrc_id()))
    {
      ProduceCostDAO.insert(context, produceCost);
    }
    else
    {
      ProduceCostDAO.save(context, produceCost);
    }

    return true;
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final ProduceCostForm form = (ProduceCostForm) context.getForm();

    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    produceCost.calculate(null);
    form.getGridProduces().setDataList(produceCost.getProducesCost());
    form.getGridCustom().setDataList(produceCost.getCustomCodes());

    final int countItogRecord = produceCost.getCountItogRecord();
    context.getRequest().setAttribute("show-checker", new IShowChecker()
    {
      int size = form.getGridProduces().getDataList().size();

      public boolean check(ShowCheckerContext context)
      {
        return context.getTable().getRecordCounter() < size - countItogRecord + 1;
      }
    }
    );

    if (StringUtil.isEmpty(form.getCreateUser().getUsr_id()) || //новый документ
       (!"1".equals(form.getPrc_block())))
    {
      form.setFormReadOnly(false);
    }
    else
    {
      form.setFormReadOnly(true);
    }

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        ProduceCostProduce produceCostProduce = (ProduceCostProduce) form.getGridProduces().getDataList().get(context.getTable().getRecordCounter() - 1);
        if (produceCostProduce.isItogString())
        {
          return "bold-cell";
        }
        if (null == produceCostProduce.getProduce() || null == produceCostProduce.getProduce().getId() )
        {
          return "red-font-cell";
        }
        return "";
      }
    });

    context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        ProduceCostProduce produceCostProduce = (ProduceCostProduce) form.getGridProduces().getDataList().get(ctx.getTable().getRecordCounter() - 1);
        if( StringUtil.isEmpty(produceCostProduce.getLpc_occupied()) && !form.isFormReadOnly() )
          return false;
        else
          return true;
      }
    });

    return context.getMapping().findForward("form");
  }

  public ActionForward reload(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    ProduceCostForm form = (ProduceCostForm) context.getForm();
    form.setNeedRecalc("");

    return show(context);
  }

  public ActionForward backFromSelect(IActionContext context) throws Exception
  {
    ProduceCostForm form = (ProduceCostForm) context.getForm();
    if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
    {
      LockedRecords.getLockedRecords().unlockWithTheSame(ORDER_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
    }
    if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
    {
      LockedRecords.getLockedRecords().unlockWithTheSame(ASSEMBLE_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
    }
    if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
    {
      LockedRecords.getLockedRecords().unlockWithTheSame(DELIVERY_REQUEST_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
    }
    if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
    {
      LockedRecords.getLockedRecords().unlockWithTheSame(SPECIFICATION_IMPORT_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
    }
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);

    for (int i = 0; i < produceCost.getProducesCost().size() - produceCost.getCountItogRecord(); i++)
    {
      ProduceCostProduce produceCostProduce = produceCost.getProducesCost().get(i);
      if ( !StringUtil.isEmpty(produceCostProduce.getLpc_occupied()) )
      {
        continue;
      }

      if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(produceCostProduce.getOpr_id()) )
      {
        continue;
      }
      if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(produceCostProduce.getApr_id()) && StringUtil.isEmpty(produceCostProduce.getAsm_id()) )
      {
        continue;
      }
      if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(produceCostProduce.getDrp_id()) )
      {
        continue;
      }
      if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(produceCostProduce.getSip_id()) )
      {
        continue;
      }

      ImportData.RightRecord rightRecord = null;
      if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
      {
        rightRecord = data.findRightRecordByDocIdIdx(produceCostProduce.getOpr_id(), i);
      }
      if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
      {
        if ( StringUtil.isEmpty(produceCostProduce.getApr_id()) )
        {
          rightRecord = data.findRightRecordByDocIdIdx(produceCostProduce.getAsm_id(), i);
        }
        else
        {
          rightRecord = data.findRightRecordByDocIdIdx(produceCostProduce.getApr_id(), i);
        }
      }
      if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
      {
        rightRecord = data.findRightRecordByDocIdIdx(produceCostProduce.getDrp_id(), i);
      }
      if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
      {
        rightRecord = data.findRightRecordByDocIdIdx(produceCostProduce.getSip_id(), i);
      }
      if ( null != rightRecord ) //нашли - значит эту запись отредактировали
      {
        rightRecord.setModified(true);
        produceCostProduce.setLpc_count(rightRecord.getPosition().getCount());
      }
      else //не нашли - значит эту запись удалили - удаляем из списка
      {
        produceCost.getProducesCost().remove(i);
        i--;
      }
    }

    for (int i = 0; i < data.getRightIntermediate().size(); i++)
    {
      ImportData.RightRecord record = data.getRightIntermediate().get(i);
      if ( !record.isModified() ) //не измененная, а новая запись - добавляем в список товаров
      {
        ProduceCostProduce produceCostProduce = new ProduceCostProduce();
        produceCostProduce.setPrc_id(produceCost.getPrc_id());
        produceCostProduce.setLpc_count(record.getPosition().getCount());

        produceCostProduce.setProduce(ProduceDAO.loadProduceWithUnit(record.getPosition().getProduce().getId()));

        if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
        {
          produceCostProduce.setOpr_id(record.getPosition().getDoc_id());
          produceCostProduce.setAsm_id(null);
          produceCostProduce.setApr_id(null);
          produceCostProduce.setDrp_id(null);
          produceCostProduce.setSip_id(null);
          Order order = OrderDAO.loadByOprId(context, record.getPosition().getDoc_id());
          produceCostProduce.setStuffCategory(order.getStuffCategory());
          produceCostProduce.setManager(order.getCreateUser());
          produceCostProduce.setDepartment(order.getCreateUser().getDepartment());

          Purpose defaultPurpose = new Purpose();
          defaultPurpose.setId(Config.getString(Constants.defaultPurposeProduce));
          PurposeDAO.load(context, defaultPurpose);
          produceCostProduce.setPurpose(defaultPurpose);
        }

        if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
        {
          produceCostProduce.setOpr_id(null);
          produceCostProduce.setDrp_id(null);
          produceCostProduce.setSip_id(null);
          if ( record.getPosition().isUseMainId() )
          {
            produceCostProduce.setAsm_id(record.getPosition().getId());
            produceCostProduce.setApr_id(null);

            Assemble assemble = AssembleDAO.load(context, produceCostProduce.getAsm_id());
            produceCostProduce.setStuffCategory(assemble.getStuffCategory());
          }
          else
          {
            produceCostProduce.setAsm_id(null);
            produceCostProduce.setApr_id(record.getPosition().getDoc_id());

            Assemble assemble = AssembleDAO.loadByAprId(context,  produceCostProduce.getApr_id());
            Order order = OrderDAO.loadByOprId(context, assemble.getOpr_id());
            produceCostProduce.setStuffCategory(assemble.getStuffCategory());
            produceCostProduce.setManager(order.getCreateUser());
            produceCostProduce.setDepartment(order.getCreateUser().getDepartment());
          }

          Purpose defaultPurpose = new Purpose();
          defaultPurpose.setId(Config.getString(Constants.defaultPurposeProduce));
          PurposeDAO.load(context, defaultPurpose);
          produceCostProduce.setPurpose(defaultPurpose);
        }

        if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
        {
          produceCostProduce.setOpr_id(null);
          produceCostProduce.setAsm_id(null);
          produceCostProduce.setApr_id(null);
          produceCostProduce.setDrp_id(record.getPosition().getDoc_id());
          produceCostProduce.setSip_id(null);
          DeliveryRequest deliveryRequest = DeliveryRequestDAO.loadByDrpId(context, record.getPosition().getDoc_id());
          DeliveryRequestProduce deliveryRequestProduce = DeliveryRequestProduceDAO.load(context, record.getPosition().getDoc_id());
          produceCostProduce.setStuffCategory(deliveryRequestProduce.getStuffCategory());
          produceCostProduce.setManager(deliveryRequest.getPlaceUser());
          produceCostProduce.setDepartment(deliveryRequest.getPlaceUser().getDepartment());
          produceCostProduce.setPurpose(deliveryRequestProduce.getPurpose());
        }

        if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
        {
          produceCostProduce.setOpr_id(null);
          produceCostProduce.setAsm_id(null);
          produceCostProduce.setDrp_id(null);
          produceCostProduce.setSip_id(record.getPosition().getDoc_id());
          SpecificationImportProduce specificationImportProduce = SpecificationImportProduceDAO.load(context, record.getPosition().getDoc_id());
          produceCostProduce.setPurpose(specificationImportProduce.getPurpose());
          if ( !StringUtil.isEmpty(specificationImportProduce.getDrp_id()) )
          {
            DeliveryRequest deliveryRequest = DeliveryRequestDAO.loadByDrpId(context, specificationImportProduce.getDrp_id());
            produceCostProduce.setStuffCategory(specificationImportProduce.getStuffCategory());
            produceCostProduce.setManager(deliveryRequest.getPlaceUser());
            produceCostProduce.setDepartment(deliveryRequest.getPlaceUser().getDepartment());
          }
          if ( !StringUtil.isEmpty(specificationImportProduce.getOpr_id()) )
          {
            Order order = OrderDAO.loadByOprId(context, specificationImportProduce.getOpr_id());
            produceCostProduce.setStuffCategory(order.getStuffCategory());
            produceCostProduce.setManager(order.getCreateUser());
            produceCostProduce.setDepartment(order.getCreateUser().getDepartment());
          }

          produceCostProduce.setLpc_weight(specificationImportProduce.getSip_weight());
          produceCostProduce.setLpc_summ(specificationImportProduce.getSip_cost());
          if ( StringUtil.isEmpty(specificationImportProduce.getDrp_max_extra()) )
          {
            produceCostProduce.setLpc_price_list_by(specificationImportProduce.getDrp_price());
          }
        }

        produceCostProduce.setLpc_occupied("");
        produceCostProduce.setNoRound(CurrencyDAO.loadByName(context, "BYN").getNo_round());
        produceCostProduce.setNumber(Integer.toString(produceCost.getProducesCost().size()));
				if (form.getTarget().equals(ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET)) {
					produceCostProduce.setLpc_1c_number(Number1CHistoryHelper.getLastActual1CNumberFromHistory(context, String.valueOf(produceCostProduce.getProduce().getId()), produceCost.getPrc_date()));
				}
				produceCost.insertProduceCostProduce(produceCostProduce);
      }
    }

    getCurrentFormFromBean(context, null);
    return show(context);
  }

  public ActionForward selectFromDocs(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    ProduceCostForm form = (ProduceCostForm) context.getForm();
    SyncObject syncObjectForLock = null;
    if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
    {
      syncObjectForLock = new SyncObject(ORDER_POSITIONS_LOCK_NAME, POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
    }
    if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
    {
      syncObjectForLock = new SyncObject(ASSEMBLE_POSITIONS_LOCK_NAME, POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
    }
    if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
    {
      syncObjectForLock = new SyncObject(DELIVERY_REQUEST_POSITIONS_LOCK_NAME, POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
    }
    if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
    {
      syncObjectForLock = new SyncObject(SPECIFICATION_IMPORT_POSITIONS_LOCK_NAME, POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
    }
    SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
    if (!syncObjectCurrent.equals(syncObjectForLock))
    {
      StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
      StrutsUtil.addError(context, "error.record.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
      return show(context);
    }

    ImportData importData = new ImportData();
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    for (int i = 0; i < produceCost.getProducesCost().size() - produceCost.getCountItogRecord(); i++)
    {
      ProduceCostProduce produceCostProduce = produceCost.getProducesCost().get(i);
      if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(produceCostProduce.getOpr_id()) ) //только те, что импортированы из заказа
        {
          if ( StringUtil.isEmpty(produceCostProduce.getLpc_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setDoc_id(produceCostProduce.getOpr_id());
            position.setCount(produceCostProduce.getLpc_count());
            position.setProduce(produceCostProduce.getProduce());
            position.setCtn_number(produceCostProduce.getCatalogNumberForStuffCategory());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }

      if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(produceCostProduce.getApr_id()) || !StringUtil.isEmpty(produceCostProduce.getAsm_id()) )
        {
          if ( StringUtil.isEmpty(produceCostProduce.getLpc_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setId(produceCostProduce.getAsm_id());
            position.setDoc_id(produceCostProduce.getApr_id());
            if ( StringUtil.isEmpty(produceCostProduce.getApr_id()) )
            {
              position.setUseMainId(true);
            }
            position.setCount(produceCostProduce.getLpc_count());
            position.setProduce(produceCostProduce.getProduce());
            position.setCtn_number(produceCostProduce.getCatalogNumberForStuffCategory());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }

      if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(produceCostProduce.getDrp_id()) ) //только те, что импортированы из заявки
        {
          if ( StringUtil.isEmpty(produceCostProduce.getLpc_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setDoc_id(produceCostProduce.getDrp_id());
            position.setCount(produceCostProduce.getLpc_count());
            position.setProduce(produceCostProduce.getProduce());
            position.setCtn_number(produceCostProduce.getCatalogNumberForStuffCategory());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }

      if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(produceCostProduce.getSip_id()) ) //только те, что импортированы из спецификации (импорт)
        {
          if ( StringUtil.isEmpty(produceCostProduce.getLpc_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setDoc_id(produceCostProduce.getSip_id());
            position.setCount(produceCostProduce.getLpc_count());
            position.setProduce(produceCostProduce.getProduce());
            position.setCtn_number(produceCostProduce.getCatalogNumberForStuffCategory());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }
    }
    StoreUtil.putSession(context.getRequest(), importData);

    return context.getMapping().findForward("selectFromDocs");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ProduceCostForm form = (ProduceCostForm) context.getForm();

    ProduceCost contract = new ProduceCost();
    StoreUtil.putSession(context.getRequest(), contract);
    //обнуляем поля формы
    getCurrentFormFromBean(context, contract);

    form.setPrc_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    form.setIs_new_doc("true");

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ProduceCostForm form = (ProduceCostForm) context.getForm();
    form.setNeedRecalc("");

    ProduceCost contract = ProduceCostDAO.load(context, form.getPrc_id());
    StoreUtil.putSession(context.getRequest(), contract);
    getCurrentFormFromBean(context, contract);

    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    boolean retFromSave = saveCommon(context);

    if (retFromSave)
    {
      return context.getMapping().findForward("back");
    }
    else
    {
      return show(context);
    }
  }
}
