package net.sam.dcl.action;

import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.form.ProducesForAssembleMinskForm;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProducesForAssembleMinskAction extends ImportPositionsAction implements IDispatchable
{
  public ActionForward show(IActionContext context) throws Exception
  {
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    recalcLeftWithFilter(form, data);
    return context.getMapping().findForward("form");
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    form.setShowGroupsExpanded("1");
    form.setCleanRight(false);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);

    form.getGridLeft().getDataList().clear();
    if ( form.isCleanRight() )
    {
      form.getGridRight().getDataList().clear();
    }
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    data.getLeftIntermediate().clear();
    data.setLeftOriginal(DAOUtils.fillList(context, "produce_cost-positions-available-for-commercial_proposal", context.getForm(), ImportData.LeftRecord.class, null, null));
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
      //в бронь включяем количество из текущего дока.
      leftRecord.setReserved(leftRecord.getReserved() + rightProduceCount);

      if ( leftRecord.getCount() > 0.0 )
      {
        leftRecord.setReserved(leftRecord.getReserved());
        leftRecord.setCount(leftRecord.getCount()); 

        data.getLeftIntermediate().add(new ImportData.LeftRecord(leftRecord));
      }
    }
    hibSession.getTransaction().commit();

    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    form.setRoute(RouteDAO.load(context, Config.getString(Constants.routeProducesForAssembleMinsk)));
    setDefaultPrcMinDate(context);
    form.setCleanRight(true);
    return internalFilter(context);
  }

  public ActionForward clear(IActionContext context) throws Exception
  {
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    form.setName_filter("");
    form.setCtn_number("");
    form.setNumber_1c("");
    setDefaultPrcMinDate(context);
    form.setCleanRight(false);
    return internalFilter(context);
  }

  private void setDefaultPrcMinDate(IActionContext context)
  {
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    if ( Config.haveNumber(Constants.dayCountDeductShippingPositions) )
    {
      int dayCount = Config.getNumber(Constants.dayCountDeductShippingPositions, 180);
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, -dayCount);
      form.setPrc_date_min(StringUtil.date2appDateString(calendar.getTime()));
    }
    else
    {
      form.setPrc_date_min("");
    }
  }

  private void recalcLeftWithFilter(ProducesForAssembleMinskForm form, ImportData data)
  {
    List<ImportData.LeftRecord> res = new ArrayList<ImportData.LeftRecord>();
    for (int i = 0; i < data.getLeftIntermediate().size(); i++)
    {
      ImportData.LeftRecord leftRecord = data.getLeftIntermediate().get(i);
      if (isSatisfyFilter(form, leftRecord))
      {
        res.add(leftRecord);
      }

      if ( leftRecord.getCount() > leftRecord.getReserved() )
      {
        leftRecord.setShipped_count(leftRecord.getCount() - leftRecord.getReserved());
      }
      else
      {
        leftRecord.setShipped_count(0);
      }
    }
    form.getGridLeft().setDataList(res);
  }

  protected boolean isSatisfyFilter(ProducesForAssembleMinskForm form, ImportData.LeftRecord record)
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
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    for (int i = 0; i < form.getGridLeft().getDataList().size(); i++)
    {
      ImportData.LeftRecord leftRecord = (ImportData.LeftRecord) form.getGridLeft().getDataList().get(i);
      if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
      {
        if (Double.isNaN(leftRecord.getShipped_count()))
        {
          StrutsUtil.addError(context, "errors.double_for", StrutsUtil.getMessage(context, "ProducesForAssembleMinsk.shipped_count1"), leftRecord.getProduce().getName(), null);
          continue;
        }
        if (leftRecord.getShipped_count() > (leftRecord.getCount() - leftRecord.getReserved()) || leftRecord.getShipped_count() <= 0)
        {
          StrutsUtil.addError(context, "errors.ProducesForAssembleMinsk.wrong.count", leftRecord.getProduce().getName(), null);
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
    ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    for (int i = data.getRightIntermediate().size() - 1; i >= 0; i--)
    {
      ImportData.RightRecord rightRecord = data.getRightIntermediate().get(i);
      if (!StringUtil.isEmpty(rightRecord.getSelected_id()))
      {
        deleteRightRecord(data, rightRecord);
      }
    }
    form.getGridLeft().setDataList(data.getLeftIntermediate());
    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  private void deleteRightRecord(ImportData data, ImportData.RightRecord rightRecord) throws Exception
  {
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
      }
      else
      {
        leftRecord = new ImportData.LeftRecord();
        leftRecord.setCount(rightRecord.getPosition().getCount());
        leftRecord.setDoc_id(rightRecord.getPosition().getDoc_id());
        leftRecord.setProduce(rightRecord.getPosition().getProduce());
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

  public ActionForward ajaxReservedInfoGrid(IActionContext context) throws Exception
  {
    String lpcId = context.getRequest().getParameter("lpcId");
    if (!StringUtil.isEmpty(lpcId) && !"-1".equals(lpcId))
    {
      ProducesForAssembleMinskForm form = (ProducesForAssembleMinskForm) context.getForm();
      form.getReservedInfoLines().setDataList(ReservedInfoDAO.loadInfo(context, form.getCpr_id(), lpcId));
    }
    return context.getMapping().findForward("ajaxReservedInfoGrid");
  }
}