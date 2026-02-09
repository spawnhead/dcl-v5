package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
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
public class DeliveryRequestAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(DeliveryRequestAction.class);

  private void saveCurrentFormToBean(IActionContext context)
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();

    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);

    deliveryRequest.setIs_new_doc(form.getIs_new_doc());

    deliveryRequest.setDlr_id(form.getDlr_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        deliveryRequest.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        deliveryRequest.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getPlaceUser().getUsr_id()))
      {
        deliveryRequest.setPlaceUser(UserDAO.load(context, form.getPlaceUser().getUsr_id()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    deliveryRequest.setUsr_date_create(form.getUsr_date_create());
    deliveryRequest.setUsr_date_edit(form.getUsr_date_edit());
    deliveryRequest.setUsr_date_place(form.getUsr_date_place());
    deliveryRequest.setDlr_number(form.getDlr_number());
    deliveryRequest.setDlr_date(form.getDlr_date());
    deliveryRequest.setDlr_fair_trade(form.getDlr_fair_trade());
    deliveryRequest.setDlr_need_deliver(form.getDlr_need_deliver());
    deliveryRequest.setDlr_ord_not_form(form.getDlr_ord_not_form());
    deliveryRequest.setDlr_guarantee_repair(form.getDlr_guarantee_repair());
    deliveryRequest.setDlr_include_in_spec(form.getDlr_include_in_spec());
    deliveryRequest.setDlr_wherefrom(form.getDlr_wherefrom());
    deliveryRequest.setDlr_comment(form.getDlr_comment());
    deliveryRequest.setDlr_place_request_form(form.getDlr_place_request_form());
    deliveryRequest.setDlr_annul(form.getDlr_annul());
    deliveryRequest.setDlr_executed(form.getDlr_executed());

    deliveryRequest.setNumDateOrdShowForFairTrade(form.isNumDateOrdShowForFairTrade());

    deliveryRequest.setPrintScale(form.getPrintScale());

    StoreUtil.putSession(context.getRequest(), deliveryRequest);
  }

  private void getCurrentFormFromBean(IActionContext context, DeliveryRequest delivery_request_in)
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    DeliveryRequest deliveryRequest;
    if (null != delivery_request_in)
    {
      deliveryRequest = delivery_request_in;
    }
    else
    {
      deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    }

    if (null != deliveryRequest)
    {
      form.setIs_new_doc(deliveryRequest.getIs_new_doc());

      form.setDlr_id(deliveryRequest.getDlr_id());
      form.setCreateUser(deliveryRequest.getCreateUser());
      form.setEditUser(deliveryRequest.getEditUser());
      form.setPlaceUser(deliveryRequest.getPlaceUser());
      form.setUsr_date_create(deliveryRequest.getUsr_date_create());
      form.setUsr_date_edit(deliveryRequest.getUsr_date_edit());
      form.setUsr_date_place(deliveryRequest.getUsr_date_place());
      form.setDlr_number(deliveryRequest.getDlr_number());
      form.setDlr_date(deliveryRequest.getDlr_date());
      form.setDlr_fair_trade(deliveryRequest.getDlr_fair_trade());
      form.setDlr_need_deliver(deliveryRequest.getDlr_need_deliver());
      form.setDlr_ord_not_form(deliveryRequest.getDlr_ord_not_form());
      form.setDlr_guarantee_repair(deliveryRequest.getDlr_guarantee_repair());
      form.setDlr_include_in_spec(deliveryRequest.getDlr_include_in_spec());
      form.setDlr_wherefrom(deliveryRequest.getDlr_wherefrom());
      form.setDlr_comment(deliveryRequest.getDlr_comment());
      form.setDlr_place_request_form(deliveryRequest.getDlr_place_request_form());
      form.setDlr_annul(deliveryRequest.getDlr_annul());
      form.setDlr_executed(deliveryRequest.getDlr_executed());

      //можем сохранить только из бина в форму, но не наоборот
      form.setCount_rest_drp(deliveryRequest.getCount_rest_drp());

      form.setPrintScale(deliveryRequest.getPrintScale());

      form.getGridResult().setDataList(deliveryRequest.getProduces());
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

  public ActionForward deleteProduce(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    deliveryRequest.deleteProduce(form.getNumber());

    return retFromProduceOperation(context);
  }

  public ActionForward retFromProduceOperation(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context, null);

    return show(context);
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    saveCurrentFormToBean(context);
    String errMsg = "";

    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    for (int i = 0; i < deliveryRequest.getProduces().size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = deliveryRequest.getProduces().get(i);
      if ( !StringUtil.isEmpty(deliveryRequestProduce.getReceive_date()) && DeliveryRequestProduce.errorDateFormat.equals(deliveryRequestProduce.getReceive_date()) )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "errors.delivery_request_positions.wrong.date_format", deliveryRequestProduce.getProduce().getName());
      }
    } //for

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return false;
    }

    saveCurrentFormToBean(context);

    User user = UserUtil.getCurrentUser(context.getRequest());

    //устанавливаем только если поменялось с пустого
    if ( "1".equals(form.getDlr_place_request_form()) && StringUtil.isEmpty(form.getDlr_place_request_save()) )
    {
      form.setPlaceUser(user);
      deliveryRequest.setDlr_place_request(form.getDlr_place_request_form());
    }

    //0 - to Minsk, 1 - from Minsk
    if ( form.isInDoc() )
    {
      deliveryRequest.setDlr_minsk("0");
    }
    else
    {
      deliveryRequest.setDlr_minsk("1");
    }

    //устанавливаем здесь, потому что его использует "user-code-load", а нужно и для секции edit
    form.setCreateUser(user);
    if (StringUtil.isEmpty(form.getDlr_id()))
    {
      //form dlr_number
      String year = form.getDlr_date().substring(8);
      String month = form.getDlr_date().substring(3, 5);
      if (!StringUtil.isEmpty(form.getPlaceUser().getUsr_id()))
      {
        DAOUtils.load(context, "user-code-load", null);
      }
      form.setGen_num(CommonDAO.GetNumber(context, "get-num_delivery_request"));
      deliveryRequest.setDlr_number("BYM" + year + month + "/" + StringUtil.padWithLeadingZeros(form.getGen_num(), 4));
      if (!StringUtil.isEmpty(form.getPlaceUser().getUsr_id()))
      {
        deliveryRequest.setDlr_number(deliveryRequest.getDlr_number() + "-" + form.getUsr_code().toUpperCase());
      }

      DeliveryRequestDAO.insert(context, deliveryRequest);
    }
    else
    {
      if ( !StringUtil.isEmpty(form.getPlaceUser().getUsr_id()) && deliveryRequest.getDlr_number().length() < 14 )
      {
        DAOUtils.load(context, "user-code-load", null);
        deliveryRequest.setDlr_number(deliveryRequest.getDlr_number() + "-" + form.getUsr_code().toUpperCase());
      }
      DeliveryRequestDAO.save(context, deliveryRequest);
    }

    return true;
  }

  public ActionForward print(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    saveCurrentFormToBean(context);
    form.setNeedPrint(true);
    return show(context);
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();

    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    deliveryRequest.calculate();
    form.getGridResult().setDataList(deliveryRequest.getProduces());

    final User user = UserUtil.getCurrentUser(context.getRequest());
    if (form.isNeedPrint())
    {
      form.setPrint("true");
    }
    else
    {
      form.setPrint("false");
      /* создают и редактируют менагеры, экономист
         просмотр - декларант
         админ, ессно, может всё
         После блокировки:
         read-only могут админ, менагер, экономист, декларант
      */
      if ( "1".equals(deliveryRequest.getDlr_place_request()) || ( !user.isAdmin() && !user.isManager() && !user.isEconomist() )  )
      {
        form.setFormReadOnly(true);
      }
      else
      {
        form.setFormReadOnly(false);
      }
    }
    form.setNeedPrint(false);

    // для НЕИСПОЛНЕННЫХ РАЗМЕЩЁННЫХ заявок
    // могут редактировать: админ, декларант
    // а также
    // доступным для менеджера и экономиста, если ни одна позиция из заявки не была импортирована
    // в другие документы (или, иначе говоря, заявка ПОЛНОСТЬЮ неисполнена)
    if ( ( (user.isAdmin() || user.isDeclarant()) && form.isShowAnnul() ) ||
         ( (user.isManager() || user.isEconomist()) && deliveryRequest.getCount_rest_drp() == form.getProduceCount()  )
       )
    {
      form.setAnnulReadOnly(false);
    }
    else
    {
      form.setAnnulReadOnly(true);
    }
    form.setPlaceRequestReadOnly(form.getGridResult().getDataList().size() == 0);
    if ( user.isAdmin() || user.isEconomist() || user.isManager() )
    {
      form.setExecutedReadOnly(false);
    }
    else
    {
      form.setExecutedReadOnly(true);
    }

    context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        DeliveryRequestProduce deliveryRequestProduce = (DeliveryRequestProduce) form.getGridResult().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !( StringUtil.isEmpty(deliveryRequestProduce.getDrp_occupied()) && !form.isFormReadOnly() );
      }
    });

    context.getRequest().setAttribute("receiveReadOnlyChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        return !( user.isAdmin() || (user.isDeclarant() && form.getCount_rest_drp() > 0) );
      }
    });

    //если из Минска - всегда Fair Trade
    if ( form.isOutDoc() )
    {
      form.setDlr_fair_trade("1");
    }

    return context.getMapping().findForward("form");
  }

  public ActionForward reload(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    form.setNeedPrint(false);
    saveCurrentFormToBean(context);
    return show(context);
  }

  public ActionForward reloadWithClean(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    form.setNeedPrint(false);
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    deliveryRequest.clear();
    StoreUtil.putSession(context.getRequest(), deliveryRequest);
    if ( !StringUtil.isEmpty(form.getDlr_need_deliver()) )
    {
      form.setDlr_ord_not_form("");
      form.setDlr_guarantee_repair("");
      form.setDlr_include_in_spec("");
    }
    saveCurrentFormToBean(context);
    return show(context);
  }

  public ActionForward backFromSelect(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    form.setNeedPrint(false);

    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);

    for (int i = 0; i < deliveryRequest.getProduces().size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = deliveryRequest.getProduces().get(i);
      if ( !StringUtil.isEmpty(deliveryRequestProduce.getDrp_occupied()) )
      {
        continue;
      }

      if ( DeliveryRequestPositionsForm.ORDER_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(deliveryRequestProduce.getOpr_id()) )
      {
        continue;
      }
      if ( DeliveryRequestPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(deliveryRequestProduce.getApr_id()) && StringUtil.isEmpty(deliveryRequestProduce.getAsm_id()) )
      {
        continue;
      }
      if ( DeliveryRequestPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(deliveryRequestProduce.getSip_id()) )
      {
        continue;
      }

      ImportData.RightRecord rightRecord = null;
      if ( DeliveryRequestPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
      {
        rightRecord = data.findRightRecordByDocId(deliveryRequestProduce.getOpr_id());
      }
      if ( DeliveryRequestPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
      {
        if ( StringUtil.isEmpty(deliveryRequestProduce.getApr_id()) )
        {
          rightRecord = data.findRightRecordByDocId(deliveryRequestProduce.getAsm_id());
        }
        else
        {
          rightRecord = data.findRightRecordByDocId(deliveryRequestProduce.getApr_id());
        }
      }
      if ( DeliveryRequestPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
      {
        rightRecord = data.findRightRecordByDocId(deliveryRequestProduce.getSip_id());
      }
      if ( null != rightRecord ) //нашли - значит эту запись отредактировали
      {
        rightRecord.setModified(true);
        deliveryRequestProduce.setDrp_count(rightRecord.getPosition().getCount());
      }
      else //не нашли - значит эту запись удалили - удаляем из списка
      {
        deliveryRequest.getProduces().remove(i);
        i--;
      }
    }

    for (int i = 0; i < data.getRightIntermediate().size(); i++)
    {
      ImportData.RightRecord record = data.getRightIntermediate().get(i);
      if ( !record.isModified() ) //не измененная, а новая запись - добавляем в список товаров
      {
        DeliveryRequestProduce deliveryRequestProduce = new DeliveryRequestProduce();
        deliveryRequestProduce.setDlr_id(deliveryRequest.getDlr_id());
        deliveryRequestProduce.setDrp_count(record.getPosition().getCount());

        deliveryRequestProduce.setProduce(ProduceDAO.loadProduceWithUnit(record.getPosition().getProduce().getId()));

        if ( DeliveryRequestPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
        {
          deliveryRequestProduce.setOpr_id(record.getPosition().getDoc_id());
          deliveryRequestProduce.setAsm_id(null);
          deliveryRequestProduce.setApr_id(null);
          deliveryRequestProduce.setSip_id(null);
          Order order = OrderDAO.loadByOprId(context, record.getPosition().getDoc_id());
          deliveryRequestProduce.setOrd_date(StringUtil.appDateString2dbDateString(order.getOrd_date()));
          deliveryRequestProduce.setOrd_number(order.getOrd_number());
          deliveryRequestProduce.setStuffCategory(order.getStuffCategory());
          OrderProduce orderProduce = OrderProduceDAO.load(context, record.getPosition().getDoc_id());
          if ( !StringUtil.isEmpty(order.getOrd_in_one_spec()) ) //клиент для всего Заказа - грузим из Заказа
          {
            deliveryRequestProduce.setCustomer(order.getContractor_for());
            deliveryRequestProduce.setSpecification(order.getSpecification());
            if (!StringUtil.isEmpty(orderProduce.getSpecification().getCon_id()))
            {
              deliveryRequestProduce.setContract(ContractDAO.load(context, order.getSpecification().getCon_id(), false));
            }
          }
          else //клиент для отдельной строки - грузим из строки Заказа
          {
            deliveryRequestProduce.setCustomer(orderProduce.getContractor());
            deliveryRequestProduce.setSpecification(orderProduce.getSpecification());
            if (!StringUtil.isEmpty(orderProduce.getSpecification().getCon_id()))
            {
              deliveryRequestProduce.setContract(ContractDAO.load(context, orderProduce.getSpecification().getCon_id(), false));
            }
          }
        }

        if ( DeliveryRequestPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
        {
          deliveryRequestProduce.setOpr_id(null);
          deliveryRequestProduce.setSip_id(null);
          deliveryRequestProduce.setDrp_have_depend("1");
          if ( record.getPosition().isUseMainId() )
          {
            deliveryRequestProduce.setAsm_id(record.getPosition().getId());
            deliveryRequestProduce.setApr_id(null);

            Assemble assemble = AssembleDAO.load(context, deliveryRequestProduce.getAsm_id());
            deliveryRequestProduce.setOrdInfoAssemble(assemble.getOrdInfo());
            deliveryRequestProduce.setCustomerAssemble(assemble.getOrdInfoContractorFor());
            deliveryRequestProduce.setStuffCategory(assemble.getStuffCategory());

            deliveryRequestProduce.setOrd_date("");
            deliveryRequestProduce.setOrd_number("");
          }
          else
          {
            deliveryRequestProduce.setAsm_id(null);
            deliveryRequestProduce.setApr_id(record.getPosition().getDoc_id());

            Assemble assemble = AssembleDAO.loadByAprId(context,  deliveryRequestProduce.getApr_id());
            Order order = OrderDAO.loadByOprId(context, assemble.getOpr_id());
            deliveryRequestProduce.setOrd_date(StringUtil.appDateString2dbDateString(order.getOrd_date()));
            deliveryRequestProduce.setOrd_number(order.getOrd_number());
            deliveryRequestProduce.setStuffCategory(assemble.getStuffCategory());
            OrderProduce orderProduce = OrderProduceDAO.load(context, assemble.getOpr_id());
            if ( !StringUtil.isEmpty(order.getOrd_in_one_spec()) ) //клиент для всего Заказа - грузим из Заказа
            {
              deliveryRequestProduce.setCustomer(order.getContractor_for());
              deliveryRequestProduce.setSpecification(order.getSpecification());
              if (!StringUtil.isEmpty(orderProduce.getSpecification().getCon_id()))
              {
                deliveryRequestProduce.setContract(ContractDAO.load(context, order.getSpecification().getCon_id(), false));
              }
            }
            else //клиент для отдельной строки - грузим из строки Заказа
            {
              deliveryRequestProduce.setCustomer(orderProduce.getContractor());
              deliveryRequestProduce.setSpecification(orderProduce.getSpecification());
              if (!StringUtil.isEmpty(orderProduce.getSpecification().getCon_id()))
              {
                deliveryRequestProduce.setContract(ContractDAO.load(context, orderProduce.getSpecification().getCon_id(), false));
              }
            }
          }
        }

        if ( DeliveryRequestPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
        {
          deliveryRequestProduce.setOpr_id(null);
          deliveryRequestProduce.setAsm_id(null);
          deliveryRequestProduce.setApr_id(null);
          deliveryRequestProduce.setSip_id(record.getPosition().getDoc_id());
          SpecificationImport specificationImport = SpecificationImportDAO.loadBySipId(context, record.getPosition().getDoc_id());
          deliveryRequestProduce.setSpi_date(specificationImport.getSpi_date_ts());
          deliveryRequestProduce.setSpi_number(specificationImport.getSpi_number());
          SpecificationImportProduce specificationImportProduce = SpecificationImportProduceDAO.load(context, record.getPosition().getDoc_id());
          deliveryRequestProduce.setStuffCategory(specificationImportProduce.getStuffCategory());
        }

        Purpose defaultPurpose = new Purpose();
        if ( StringUtil.isEmpty(deliveryRequest.getDlr_fair_trade()) )
        {
          if ( !StringUtil.isEmpty(deliveryRequest.getDlr_guarantee_repair()) )
          {
            defaultPurpose.setId(Config.getString(Constants.defaultPurposeGuaranteeRepair));
          }
          else
          {
            defaultPurpose.setId(Config.getString(Constants.defaultPurposeProduce));
          }
        }
        else
        {
          defaultPurpose.setId(Config.getString(Constants.defaultPurposeFairTrade));
        }
        PurposeDAO.load(context, defaultPurpose);
        deliveryRequestProduce.setPurpose(defaultPurpose);

        deliveryRequestProduce.setDrp_occupied("");

        deliveryRequestProduce.setNumber(Integer.toString(deliveryRequest.getProduces().size()));
        deliveryRequest.getProduces().add(deliveryRequestProduce);
      }
    }

    getCurrentFormFromBean(context, null);
    return show(context);
  }

  public ActionForward selectDocsForDeliveryRequest(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();

    ImportData importData = new ImportData();
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    for (int i = 0; i < deliveryRequest.getProduces().size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = deliveryRequest.getProduces().get(i);
      if ( DeliveryRequestPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(deliveryRequestProduce.getOpr_id()) ) //только те, что импортированы из заказа
        {
          if ( StringUtil.isEmpty(deliveryRequestProduce.getDrp_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setDoc_id(deliveryRequestProduce.getOpr_id());
            position.setCount(deliveryRequestProduce.getDrp_count());
            position.setProduce(deliveryRequestProduce.getProduce());
            position.setCtn_number(deliveryRequestProduce.getCatalogNumberForStuffCategory());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }

      if ( DeliveryRequestPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(deliveryRequestProduce.getApr_id()) || !StringUtil.isEmpty(deliveryRequestProduce.getAsm_id()) )
        {
          if ( StringUtil.isEmpty(deliveryRequestProduce.getDrp_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setId(deliveryRequestProduce.getAsm_id());
            position.setDoc_id(deliveryRequestProduce.getApr_id());
            if ( StringUtil.isEmpty(deliveryRequestProduce.getApr_id()) )
            {
              position.setUseMainId(true);
            }
            position.setCount(deliveryRequestProduce.getDrp_count());
            position.setProduce(deliveryRequestProduce.getProduce());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }

      if ( DeliveryRequestPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
      {
        if ( !StringUtil.isEmpty(deliveryRequestProduce.getSip_id()) ) //только те, что импортированы из заказа
        {
          if ( StringUtil.isEmpty(deliveryRequestProduce.getDrp_occupied()) )
          {
            ImportPosition position = new ImportPosition();
            position.setDoc_id(deliveryRequestProduce.getSip_id());
            position.setCount(deliveryRequestProduce.getDrp_count());
            position.setProduce(deliveryRequestProduce.getProduce());
            ImportData.RightRecord rightRecord = new ImportData.RightRecord();
            rightRecord.setId(i);
            rightRecord.setPosition(new ImportPosition(position));
            importData.getRightIntermediate().add(rightRecord);
          }
        }
      }
    }
    StoreUtil.putSession(context.getRequest(), importData);

    return context.getMapping().findForward("selectDocsForDeliveryRequest");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();

    DeliveryRequest deliveryRequest = new DeliveryRequest();
    StoreUtil.putSession(context.getRequest(), deliveryRequest);
    //обнуляем поля формы
    getCurrentFormFromBean(context, deliveryRequest);

    form.setDlr_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    form.setIs_new_doc("true");
    form.setDlr_fair_trade("");
    form.setDlr_need_deliver("");
    form.setDlr_ord_not_form("");
    form.setDlr_guarantee_repair("");
    form.setDlr_include_in_spec("");
    form.setDlr_place_request_save("");
    form.setDlr_place_request_form("");
    form.setDlr_annul("");
    form.setDlr_executed("");
    form.setDlr_executed_old("");
    form.setCount_rest_drp(0.0);
    form.setDlr_comment(StrutsUtil.getMessage(context, "DeliveryRequest.dlr_comment_default"));
    form.setNeedPrint(false);

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    form.setNeedPrint(false);

    DeliveryRequest deliveryRequest = DeliveryRequestDAO.load(context, form.getDlr_id());
    deliveryRequest.setDlr_place_request_form(deliveryRequest.getDlr_place_request());
    form.setDlr_place_request_save(deliveryRequest.getDlr_place_request());
    form.setDlr_executed_old(deliveryRequest.getDlr_executed());
    StoreUtil.putSession(context.getRequest(), deliveryRequest);
    getCurrentFormFromBean(context, deliveryRequest);

    return show(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "delivery_request-delete", null);
    return context.getMapping().findForward("back");
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
      DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
      form.setNeedPrint(false);
      return show(context);
    }
  }

  public ActionForward ajaxCheckSaveError(IActionContext context) throws Exception
  {
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    String resultMsg = "";

    if (
         (
           StringUtil.isEmpty(deliveryRequest.getDlr_fair_trade()) &&
           StringUtil.isEmpty(deliveryRequest.getDlr_guarantee_repair()) //"Запчасти для гарантийного ремонта" не отмечены - контролируем
         )
            ||
         (
           !StringUtil.isEmpty(deliveryRequest.getDlr_fair_trade()) &&
           !StringUtil.isEmpty(deliveryRequest.getDlr_include_in_spec())
         )
       )
    {
      for (int i = 0; i < deliveryRequest.getProduces().size(); i++)
      {
        DeliveryRequestProduce deliveryRequestProduce = deliveryRequest.getProduces().get(i);
        if ( deliveryRequestProduce.getDrp_price() == 0.0 )
        {
          resultMsg = StrutsUtil.addDelimiter(resultMsg);
          resultMsg += StrutsUtil.getMessage(context, "errors.delivery_request.wrong.price");
          break;
        }
      }
    }

    StrutsUtil.setAjaxResponse(context, resultMsg, false);
    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxCheckSave(IActionContext context) throws Exception
  {
    DeliveryRequestForm form = (DeliveryRequestForm) context.getForm();
    DeliveryRequest deliveryRequest = (DeliveryRequest) StoreUtil.getSession(context.getRequest(), DeliveryRequest.class);
    String resultMsg;

    // осуществлять проверку по следующей формуле:
    // если a/b/c>1,5, где
    // a - Продажная цена за единицу без НДС, бел.руб.
    // b - курс из справочника "валюты" валюты, указанной в графе "Валюта" на дату заказа
    // с - Цена нетто, указанная в заказе, из которого импортирована позиция
    // то выдавать сообщение
    if (
         (
           StringUtil.isEmpty(deliveryRequest.getDlr_fair_trade()) &&
           StringUtil.isEmpty(deliveryRequest.getDlr_guarantee_repair()) //"Запчасти для гарантийного ремонта" не отмечены - контролируем
         )
            ||
         (
           !StringUtil.isEmpty(deliveryRequest.getDlr_fair_trade()) &&
           !StringUtil.isEmpty(deliveryRequest.getDlr_include_in_spec())
         )
       )
    {
      double checkCoefficient = Config.getFloat(Constants.drpPriceCoefficient, 1.5f);

      for (int i = 0; i < form.getGridResult().getDataList().size(); i++)
      {
        DeliveryRequestProduce deliveryRequestProduce = (DeliveryRequestProduce) form.getGridResult().getDataList().get(i);
        if (!StringUtil.isEmpty(deliveryRequestProduce.getOpr_id()))
        {
          double drpPrice = deliveryRequestProduce.getDrp_price();

          double checkCourse;
          Order order = OrderDAO.loadByOprId(context, deliveryRequestProduce.getOpr_id());
          CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, order.getCurrency().getId(), StringUtil.appDateString2dbDateString(order.getOrd_date()));
          checkCourse = currencyRate.getCrt_rate();

          OrderProduce orderProduce = OrderProduceDAO.load(context, deliveryRequestProduce.getOpr_id());
          double oprPriceNetto = orderProduce.getOpr_price_netto();

          if ( checkCourse != 0 && oprPriceNetto != 0 )
          {
            if ( drpPrice / checkCourse / oprPriceNetto > checkCoefficient )
            {
              resultMsg = StrutsUtil.getMessage(context, "msg.delivery_request_produce.check_dlr_price");
              StrutsUtil.setAjaxResponse(context, resultMsg, false);
              return context.getMapping().findForward("ajax");
            }
          }
        }
      }
    }

    return context.getMapping().findForward("ajax");
  }

}
