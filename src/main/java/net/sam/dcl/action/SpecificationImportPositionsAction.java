package net.sam.dcl.action;

import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.SpecificationImportPositionsForm;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.DeliveryRequest;
import net.sam.dcl.beans.Order;
import net.sam.dcl.dao.DeliveryRequestDAO;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dao.OrderDAO;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SpecificationImportPositionsAction extends ImportPositionsAction implements IDispatchable
{

  public ActionForward show(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("form");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    SpecificationImportPositionsForm form = (SpecificationImportPositionsForm) context.getForm();
    form.getGridLeft().getDataList().clear();
    form.getGridRight().getDataList().clear();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    if (data.getLeftOriginal() == null)
    {
      Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
      hibSession.beginTransaction();
      if ( SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
      {
        data.setLeftOriginal(DAOUtils.fillList(context, "order-positions-available-for-specification-import", context.getForm(), ImportData.LeftRecord.class, null, null));
      }
      if ( SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
      {
        data.setLeftOriginal(DAOUtils.fillList(context, "delivery_request-positions-available-for-specification-import", context.getForm(), ImportData.LeftRecord.class, null, null));
      }
      for (int i = 0; i < data.getLeftOriginal().size(); i++)
      {
        ImportData.LeftRecord leftRecord = data.getLeftOriginal().get(i);
        ImportData.RightRecord rightRecord;
        if ( StringUtil.isEmpty(leftRecord.getDoc_id()) )
        {
          rightRecord = findRightRecordByDocId(data.getRightIntermediate(), leftRecord.getId(), true);
        }
        else
        {
          rightRecord = findRightRecordByDocId(data.getRightIntermediate(), leftRecord.getDoc_id(), false);
        }
        if ( null != rightRecord )
        {
          leftRecord.setCount(leftRecord.getCount() - rightRecord.getPosition().getCount());
          if ( leftRecord.getCount() < 0 )
          {
            if ( SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
            {
              StrutsUtil.addError(context, "errors.specification_import_positions.wrong.count_ord", rightRecord.getPosition().getProduce().getName(), null);
            }
            if ( SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
            {
              StrutsUtil.addError(context, "errors.specification_import_positions.wrong.count_drp", rightRecord.getPosition().getProduce().getName(), null);
            }
            continue;
          }
        }

        if ( leftRecord.getCount() != 0.0 )
        {
          leftRecord.setShipped_count(leftRecord.getCount());
          leftRecord.setProduce(ProduceDAO.loadProduceWithUnitInOneSession(leftRecord.getProduce().getId(), hibSession));
          data.getLeftIntermediate().add(new ImportData.LeftRecord(leftRecord));
        }
      }
      hibSession.getTransaction().commit();
    }
    form.getGridLeft().setDataList(data.getLeftIntermediate());
    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  public ActionForward add(IActionContext context) throws Exception
  {
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    SpecificationImportPositionsForm form = (SpecificationImportPositionsForm) context.getForm();
    for (int i = 0; i < form.getGridLeft().getDataList().size(); i++)
    {
      ImportData.LeftRecord leftRecord = (ImportData.LeftRecord) form.getGridLeft().getDataList().get(i);
      if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
      {
        if (Double.isNaN(leftRecord.getShipped_count()))
        {
          StrutsUtil.addError(context, "errors.double_for", StrutsUtil.getMessage(context, "SpecificationImportPositions.shipped_count1"), leftRecord.getProduce().getName(), null);
          continue;
        }
        if (leftRecord.getShipped_count() > leftRecord.getCount() || leftRecord.getShipped_count() <= 0)
        {
          StrutsUtil.addError(context, "errors.specification_import_positions.wrong.shipping_count", null);
          continue;
        }

        ImportData.RightRecord rightRecord;
        if ( StringUtil.isEmpty(leftRecord.getDoc_id()) )
        {
          rightRecord = findRightRecordByDocId(data.getRightIntermediate(), leftRecord.getId(), true);
        }
        else
        {
          rightRecord = findRightRecordByDocId(data.getRightIntermediate(), leftRecord.getDoc_id(), false);
        }
        boolean newRecord = false;
        if ( null == rightRecord )
        {
          rightRecord = new ImportData.RightRecord();
          newRecord = true;
        }
        rightRecord.getPosition().setCount(rightRecord.getPosition().getCount() + leftRecord.getShipped_count() );
        rightRecord.getPosition().setDoc_id(leftRecord.getDoc_id());
        rightRecord.getPosition().setId(leftRecord.getId());
        if ( StringUtil.isEmpty(leftRecord.getDoc_id()) )
        {
          rightRecord.getPosition().setUseMainId(true);
        }
        if ( newRecord )
        {
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
        }

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
    SpecificationImportPositionsForm form = (SpecificationImportPositionsForm) context.getForm();
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
    SpecificationImportPositionsForm form = (SpecificationImportPositionsForm) context.getForm();
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

        if ( SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()) )
        {
          Order order = OrderDAO.loadByOprId(context, rightRecord.getPosition().getDoc_id());
          leftRecord.setId(order.getOrd_id());
          leftRecord.setDate(StringUtil.appDateString2dbTimestampString(order.getOrd_date()));
          leftRecord.setNumber(order.getOrd_number());
        }

        if ( SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) )
        {
          DeliveryRequest deliveryRequest = DeliveryRequestDAO.loadByDrpId(context, rightRecord.getPosition().getDoc_id());
          leftRecord.setId(deliveryRequest.getDlr_id());
          leftRecord.setDate(StringUtil.appDateString2dbTimestampString(deliveryRequest.getDlr_date()));
          leftRecord.setNumber(deliveryRequest.getDlr_number());
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
