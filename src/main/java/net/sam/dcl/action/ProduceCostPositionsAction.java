package net.sam.dcl.action;

import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.Order;
import net.sam.dcl.beans.DeliveryRequest;
import net.sam.dcl.beans.SpecificationImport;
import net.sam.dcl.beans.Assemble;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ProduceCostPositionsForm;
import net.sam.dcl.form.ImportData;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostPositionsAction extends ImportPositionsAction implements IDispatchable
{
  public ActionForward show(IActionContext context) throws Exception
  {
    ProduceCostPositionsForm form = (ProduceCostPositionsForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    recalcLeftWithFilter(form, data);
    return context.getMapping().findForward("form");
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ProduceCostPositionsForm form = (ProduceCostPositionsForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    if (data.getLeftOriginal() == null)
    {
      form.getGridLeft().getDataList().clear();
      form.getGridRight().getDataList().clear();

      Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
      hibSession.beginTransaction();
      if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
      {
        data.setLeftOriginal(DAOUtils.fillList(context, "order-positions-available-for-produce-cost", context.getForm(), ImportData.LeftRecord.class, null, null));
      }
      if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
      {
        data.setLeftOriginal(DAOUtils.fillList(context, "assemble-positions-available-for-produce-cost", context.getForm(), ImportData.LeftRecord.class, null, null));
      }
      if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
      {
        data.setLeftOriginal(DAOUtils.fillList(context, "delivery_request-positions-available-for-produce-cost", context.getForm(), ImportData.LeftRecord.class, null, null));
      }
      if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
      {
        data.setLeftOriginal(DAOUtils.fillList(context, "specification-import-positions-available-for-produce-cost", context.getForm(), ImportData.LeftRecord.class, null, null));
      }
      for (int i = 0; i < data.getLeftOriginal().size(); i++)
      {
        ImportData.LeftRecord leftRecord = data.getLeftOriginal().get(i);
        leftRecord.setProduce(ProduceDAO.loadProduceWithUnitInOneSession(leftRecord.getProduce().getId(), hibSession));
        double rightProduceCount;
        if ( StringUtil.isEmpty(leftRecord.getDoc_id()) )
        {
          rightProduceCount = getRighProduceCountByDocId(data.getRightIntermediate(), leftRecord.getId(), true);
        }
        else
        {
          rightProduceCount = getRighProduceCountByDocId(data.getRightIntermediate(), leftRecord.getDoc_id(), false);
        }
        leftRecord.setCount(leftRecord.getCount() - rightProduceCount);
        if ( leftRecord.getCount() < 0 )
        {
          if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
          {
            StrutsUtil.addError(context, "errors.produce_cost_positions.wrong.count_ord", leftRecord.getProduce().getName(), null);
          }
          if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
          {
            StrutsUtil.addError(context, "errors.produce_cost_positions.wrong.count_asm", leftRecord.getProduce().getName(), null);
          }
          if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
          {
            StrutsUtil.addError(context, "errors.produce_cost_positions.wrong.count_drp", leftRecord.getProduce().getName(), null);
          }
          if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
          {
            StrutsUtil.addError(context, "errors.produce_cost_positions.wrong.count_spi", leftRecord.getProduce().getName(), null);
          }
          continue;
        }

        if ( leftRecord.getCount() != 0.0 )
        {
          leftRecord.setShipped_count(leftRecord.getCount());
          data.getLeftIntermediate().add(new ImportData.LeftRecord(leftRecord));
        }
      }
      hibSession.getTransaction().commit();
    }
    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    return filter(context);
  }

  public ActionForward clear(IActionContext context) throws Exception
  {
    ProduceCostPositionsForm form = (ProduceCostPositionsForm) context.getForm();
    form.setName_filter("");
    form.setCtn_number("");
    return filter(context);
  }

  private void recalcLeftWithFilter(ProduceCostPositionsForm form, ImportData data)
  {
    List<ImportData.LeftRecord> res = new ArrayList<ImportData.LeftRecord>();
    for (int i = 0; i < data.getLeftIntermediate().size(); i++)
    {
      ImportData.LeftRecord leftRecord = data.getLeftIntermediate().get(i);
      if (isSatisfyFilter(form, leftRecord))
      {
        res.add(leftRecord);
      }
    }
    form.getGridLeft().setDataList(res);
  }

  protected boolean isSatisfyFilter(ProduceCostPositionsForm form, ImportData.LeftRecord record)
  {
    boolean result = true;
    if (!StringUtil.isEmpty(form.getName_filter()))
    {
      result = record.getProduce().getFullName().toUpperCase().indexOf(form.getName_filter().toUpperCase()) != -1;
    }
    if ( StringUtil.isEmpty(record.getCtn_number()) && !StringUtil.isEmpty(form.getCtn_number()) )
    {
      result = false;
    }
    if (result && !StringUtil.isEmpty(record.getCtn_number()) && !StringUtil.isEmpty(form.getCtn_number()))
    {
      result = record.getCtn_number().toUpperCase().indexOf(form.getCtn_number().toUpperCase()) != -1;
    }
    return result;
  }

  public ActionForward add(IActionContext context) throws Exception
  {
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    ProduceCostPositionsForm form = (ProduceCostPositionsForm) context.getForm();
    for (int i = 0; i < form.getGridLeft().getDataList().size(); i++)
    {
      ImportData.LeftRecord leftRecord = (ImportData.LeftRecord) form.getGridLeft().getDataList().get(i);
      if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
      {
        if (Double.isNaN(leftRecord.getShipped_count()))
        {
          StrutsUtil.addError(context, "errors.double_for", StrutsUtil.getMessage(context, "ProduceCostPositions.shipped_count1"), leftRecord.getProduce().getName(), null);
          continue;
        }
        if (leftRecord.getShipped_count() > leftRecord.getCount() || leftRecord.getShipped_count() <= 0)
        {
          StrutsUtil.addError(context, "errors.produce_cost_positions.wrong.count", leftRecord.getProduce().getName(), null);
          continue;
        }

        ImportData.RightRecord rightRecord = new ImportData.RightRecord();
        rightRecord.getPosition().setCount(rightRecord.getPosition().getCount() + leftRecord.getShipped_count() );
        rightRecord.getPosition().setDoc_id(leftRecord.getDoc_id());
        rightRecord.getPosition().setId(leftRecord.getId());
        rightRecord.getPosition().setCtn_number(leftRecord.getCtn_number());
        if ( StringUtil.isEmpty(leftRecord.getDoc_id()) )
        {
          rightRecord.getPosition().setUseMainId(true);
        }
        
        ImportData.RightRecord rightRecordForProduce = findRightRecordByPrdId(data.getRightIntermediate(), leftRecord.getProduce().getId());
        if ( null != rightRecordForProduce )  //потому как товар не может дублироваться в сесси Hibermate - ругается
        {
          rightRecord.getPosition().setProduce(rightRecordForProduce.getPosition().getProduce());
        }
        else
        {
          rightRecord.getPosition().setProduce(ProduceDAO.loadProduceWithUnit(leftRecord.getProduce().getId()));
        }
        rightRecord.setId(data.getRightIntermediate().size());
        data.getRightIntermediate().add(rightRecord);

        leftRecord.setSelected_id("");
        leftRecord.setCount(leftRecord.getCount() - leftRecord.getShipped_count());
        leftRecord.setShipped_count(0);
        if (leftRecord.getCount() == 0)
        {
          data.getLeftIntermediate().remove(leftRecord);
          i--;
        }
      }
    }
    form.getGridLeft().setDataList(data.getLeftIntermediate());
    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    ProduceCostPositionsForm form = (ProduceCostPositionsForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    for (int i = data.getRightIntermediate().size() - 1; i >= 0; i--)
    {
      ImportData.RightRecord rightRecord = data.getRightIntermediate().get(i);
      if (!StringUtil.isEmpty(rightRecord.getSelected_id()))
      {
        deleteRightRecord(context, data, rightRecord);
      }
    }
    form.getGridLeft().setDataList(data.getLeftIntermediate());
    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  private void deleteRightRecord(IActionContext context, ImportData data, ImportData.RightRecord rightRecord) throws Exception
  {
    ProduceCostPositionsForm form = (ProduceCostPositionsForm) context.getForm();
    ImportData.LeftRecord leftRecord;
    if ( rightRecord.getPosition().isUseMainId() )
    {
      leftRecord = findLeftRecordByDocId(data.getLeftIntermediate(), rightRecord.getPosition().getId(), true);
    }
    else
    {
      leftRecord = findLeftRecordByDocId(data.getLeftIntermediate(), rightRecord.getPosition().getDoc_id(), false);
    }
    if (leftRecord != null)
    {
      leftRecord.setCount(leftRecord.getCount() + rightRecord.getPosition().getCount());
      leftRecord.setShipped_count(leftRecord.getCount());
    }
    else
    {
      if ( rightRecord.getPosition().isUseMainId() )
      {
        leftRecord = findLeftRecordByDocId(data.getLeftOriginal(), rightRecord.getPosition().getId(), true);
      }
      else
      {
        leftRecord = findLeftRecordByDocId(data.getLeftOriginal(), rightRecord.getPosition().getDoc_id(), false);  
      }
      if (leftRecord != null)
      {
        leftRecord = new ImportData.LeftRecord(leftRecord);
        leftRecord.setCount(rightRecord.getPosition().getCount());
        leftRecord.setShipped_count(leftRecord.getCount());
      }
      else
      {
        leftRecord = new ImportData.LeftRecord();
        leftRecord.setCount(rightRecord.getPosition().getCount());
        leftRecord.setShipped_count(leftRecord.getCount());
        leftRecord.setDoc_id(rightRecord.getPosition().getDoc_id());
        leftRecord.setProduce(rightRecord.getPosition().getProduce());

        if ( ProduceCostPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
        {
          Order order = OrderDAO.loadByOprId(context, rightRecord.getPosition().getDoc_id());
          leftRecord.setId(order.getOrd_id());
          leftRecord.setDate(StringUtil.appDateString2dbTimestampString(order.getOrd_date()));
          leftRecord.setNumber(order.getOrd_number());
        }

        if ( ProduceCostPositionsForm.ASSEMBLE_TARGET.equals(form.getTarget()) )
        {
          Assemble assemble = AssembleDAO.loadByAprId(context, rightRecord.getPosition().getDoc_id());
          leftRecord.setId(assemble.getAsm_id());
          leftRecord.setDate(StringUtil.appDateString2dbTimestampString(assemble.getAsm_date()));
          leftRecord.setNumber(assemble.getAsm_number());
        }

        if ( ProduceCostPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
        {
          DeliveryRequest deliveryRequest = DeliveryRequestDAO.loadByDrpId(context, rightRecord.getPosition().getDoc_id());
          leftRecord.setId(deliveryRequest.getDlr_id());
          leftRecord.setDate(StringUtil.appDateString2dbTimestampString(deliveryRequest.getDlr_date()));
          leftRecord.setNumber(deliveryRequest.getDlr_number());
        }

        if ( ProduceCostPositionsForm.SPECIFICATION_IMPORT_TARGET.equals(form.getTarget()) )
        {
          SpecificationImport specificationImport = SpecificationImportDAO.loadBySipId(context, rightRecord.getPosition().getDoc_id());
          leftRecord.setId(specificationImport.getSpi_id());
          leftRecord.setDate(StringUtil.appDateString2dbTimestampString(specificationImport.getSpi_date()));
          leftRecord.setNumber(specificationImport.getSpi_number());
        }
      }

      ImportData.LeftRecord leftRecordForProduce = findLeftRecordByPrdId(data.getLeftIntermediate(), rightRecord.getPosition().getProduce().getId());
      if ( null != leftRecordForProduce )  //потому как товар не может дублироваться в сесси Hibermate - ругается
      {
        leftRecord.setProduce(leftRecordForProduce.getProduce());
      }
      else
      {
        leftRecord.setProduce(ProduceDAO.loadProduceWithUnit(rightRecord.getPosition().getProduce().getId()));
      }

      int idx = findLeftRecordByParentDocId(data.getLeftIntermediate(), leftRecord.getId());
      data.getLeftIntermediate().add(idx, leftRecord);
    }
    data.getRightIntermediate().remove(rightRecord);
  }

  public ActionForward save(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }
}
