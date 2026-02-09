package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.dao.ProduceCostDAO;
import net.sam.dcl.dao.RouteDAO;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.dao.ProduceCostProduceDAO;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.form.ShippingData;
import net.sam.dcl.form.ShippingPositionsForm;
import net.sam.dcl.util.*;
import org.apache.struts.action.ActionForward;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ShippingPositionsAction extends DBAction implements IDispatchable
{
  public ActionForward show(IActionContext context) throws Exception
  {
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);
    recalcLeftWithFilter(form, data);
    return context.getMapping().findForward("form");
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    form.setShowGroupsExpanded("1");
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    if (StringUtil.isEmpty(form.getManager().getUsr_id()))
    {
      form.setManager(UserUtil.getCurrentUser(context.getRequest()));
    }
    UserDAO.load(context, form.getManager());
	  if (StringUtil.isEmpty(form.getContractorForPositions().getName()))
	  {
	    form.setContractorForPositions(new Contractor());
	  }
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);

    if (null == data.getLeftOriginal())
    {
      setDefaultPrcMinDate(context);
      data.setLeftOriginal(new ArrayList<ShippingData.LeftRecord>());
    }

    data.getLeftOriginal().clear();
    data.getLeftIntermediate().clear();
    data.setLeftOriginal(DAOUtils.fillList(context, "shipping-positions-available", context.getForm(), ShippingData.LeftRecord.class, null, null));
    for (int i = 0; i < data.getLeftOriginal().size(); i++)
    {
      ShippingData.LeftRecord leftRecord = data.getLeftOriginal().get(i);
      ShippingData.RightRecord rightRecord = findRightRecordByLpcId(data.getRightIntermediate(), leftRecord.getLpc_id());
      if (rightRecord == null)
      {
        leftRecord.setStuffCategoryAlreadyFilled(!StringUtil.isEmpty(leftRecord.getStuffCategory().getId()));
        data.getLeftIntermediate().add(new ShippingData.LeftRecord(leftRecord));
      }
      else
      {
        double restCount = leftRecord.getFree_count() - rightRecord.getPosition().getCount();
        if (restCount > 0)
        {
          leftRecord.setFree_count(restCount);
          leftRecord.setShipped_count(restCount);
          leftRecord.setStuffCategoryAlreadyFilled(!StringUtil.isEmpty(leftRecord.getStuffCategory().getId()));
          data.getLeftIntermediate().add(new ShippingData.LeftRecord(leftRecord));
        }
      }
    }

    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  private void recalcLeftWithFilter(ShippingPositionsForm form, ShippingData data)
  {
    List<ShippingData.LeftRecord> res = new ArrayList<ShippingData.LeftRecord>();
    for (int i = 0; i < data.getLeftIntermediate().size(); i++)
    {
      ShippingData.LeftRecord leftRecord = data.getLeftIntermediate().get(i);
      if (isSatisfyFilter(form, leftRecord))
      {
        res.add(leftRecord);
      }
    }
    form.getGridLeft().setDataList(res);
  }

  protected boolean isSatisfyFilter(ShippingPositionsForm form, ShippingData.LeftRecord record)
  {
    boolean result = true;
    if (!StringUtil.isEmpty(form.getName_filter()))
    {
      result = record.getProduce_name().toUpperCase().contains(form.getName_filter().toUpperCase());
    }
    if (result && StringUtil.isEmpty(record.getCtn_number()) && !StringUtil.isEmpty(form.getCtn_number()))
    {
      result = false;
    }
    if (result && !StringUtil.isEmpty(record.getCtn_number()) && !StringUtil.isEmpty(form.getCtn_number()))
    {
      result = record.getCtn_number().toUpperCase().contains(form.getCtn_number().toUpperCase());
    }
    return result;
  }

  public ActionForward clear(IActionContext context) throws Exception
  {
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    form.setName_filter("");
    form.setCtn_number("");
    form.setNumber_1c("");
    form.setContractorForPositions(new Contractor());
    setDefaultPrcMinDate(context);
    return internalFilter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    if (data.getRightIntermediate().size() != 0)
    {
      ShippingData.RightRecord record = data.getRightIntermediate().get(0);
      Route route = new Route();
      DAOUtils.load(context, "shipping_position-rut_id", route, new VParameter("prc_id", record.getPosition().getProduce().getPrc_id(), Types.NUMERIC));
      RouteDAO.load(context, route);
      form.setRoute(route);
    }
	  form.setContractorForPositions(data.getShipping().getContractor());
    return internalFilter(context);
  }

  private void setDefaultPrcMinDate(IActionContext context)
  {
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    if (Config.haveNumber(Constants.dayCountDeductShippingPositions))
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

  public ActionForward add(IActionContext context) throws Exception
  {
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    UserDAO.load(context, form.getManager());
    for (int i = 0; i < form.getGridLeft().getDataList().size(); i++)
    {
      ShippingData.LeftRecord leftRecord = (ShippingData.LeftRecord) form.getGridLeft().getDataList().get(i);
      if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
      {
        if (Double.isNaN(leftRecord.getShipped_count()))
        {
          StrutsUtil.addError(context, "errors.double_for", StrutsUtil.getMessage(context, "ShippingPositions.shipped_count1"), leftRecord.getProduce_name(), null);
          continue;
        }
        if (leftRecord.getShipped_count() > leftRecord.getFree_count() ||
                leftRecord.getShipped_count() <= 0)
        {
          StrutsUtil.addError(context, "errors.shipping_positions.wrong.shipping_count", null);
          continue;
        }

        ShippingData.RightRecord rightRecordCheck = findRightRecordByLpcId(data.getRightIntermediate(), leftRecord.getLpc_id());
        if (rightRecordCheck == null)
        {
          ShippingPosition shippingPosition = new ShippingPosition();
          shippingPosition.setProduce(ProduceCostProduceDAO.load(context, leftRecord.getLpc_id()));
          shippingPosition.setCount(leftRecord.getShipped_count());
          shippingPosition.calculate();
          shippingPosition.setStuffCategory(new StuffCategory(leftRecord.getStuffCategory()));
          shippingPosition.setCtn_number(leftRecord.getCtn_number());
          shippingPosition.setPrc_date(leftRecord.getPrc_date());
          shippingPosition.setLprId(leftRecord.getLpr_id());
          shippingPosition.setMontageTime(0.0);
          ShippingData.RightRecord rightRecord = new ShippingData.RightRecord();
          rightRecord.setPosition(shippingPosition);
          rightRecord.setId(data.getRightIntermediate().size());
          data.getRightIntermediate().add(rightRecord);
        }
        else
        {
          rightRecordCheck.getPosition().setCount(rightRecordCheck.getPosition().getCount() + leftRecord.getShipped_count());  
        }

        leftRecord.setFree_count(leftRecord.getFree_count() - leftRecord.getShipped_count());
        leftRecord.setShipped_count(leftRecord.getFree_count());
        leftRecord.setSelected_id("");
        if (leftRecord.getFree_count() == 0)
        {
          data.getLeftIntermediate().remove(leftRecord);
        }
      }
    }
    return show(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    UserDAO.load(context, form.getManager());
    for (int i = data.getRightIntermediate().size() - 1; i >= 0; i--)
    {
      ShippingData.RightRecord rightRecord = data.getRightIntermediate().get(i);
      if (!StringUtil.isEmpty(rightRecord.getSelected_id()))
      {
        deleteRightRecord(context, data, rightRecord);
      }
    }
    return show(context);
  }

  public ActionForward changeRoute(IActionContext context) throws Exception
  {
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    UserDAO.load(context, form.getManager());
    for (int i = data.getRightIntermediate().size() - 1; i >= 0; i--)
    {
      ShippingData.RightRecord rightRecord = data.getRightIntermediate().get(i);
      deleteRightRecord(context, data, rightRecord);
    }
    return internalFilter(context);
  }

  private void deleteRightRecord(IActionContext context, ShippingData data, ShippingData.RightRecord rightRecord) throws Exception
  {
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    ShippingData.LeftRecord leftRecord = findLeftRecordByLpcId(data.getLeftIntermediate(), rightRecord.getPosition().getProduce().getLpc_id());
    if (leftRecord != null)
    {
      leftRecord.setFree_count(leftRecord.getFree_count() + rightRecord.getPosition().getCount());
      leftRecord.setShipped_count(leftRecord.getFree_count());
    }
    else
    {
      leftRecord = findLeftRecordByLpcId(data.getLeftOriginal(), rightRecord.getPosition().getProduce().getLpc_id());
      if (leftRecord != null)
      {
        leftRecord = new ShippingData.LeftRecord(leftRecord);
        leftRecord.setFree_count(rightRecord.getPosition().getCount());
        leftRecord.setShipped_count(leftRecord.getFree_count());
        leftRecord.setStuffCategory(rightRecord.getPosition().getStuffCategory());
        int idx = findLeftRecordByPrcId(data.getLeftIntermediate(), leftRecord.getPrc_id());
        data.getLeftIntermediate().add(idx, leftRecord);
      }
      else
      {
        leftRecord = new ShippingData.LeftRecord();
        leftRecord.setFree_count(rightRecord.getPosition().getCount());
        leftRecord.setShipped_count(leftRecord.getFree_count());
        leftRecord.setLpc_id(rightRecord.getPosition().getProduce().getLpc_id());
        leftRecord.setProduce_name(rightRecord.getPosition().getProduce().getProduce().getName());
        ProduceCost prc = ProduceCostDAO.load(context, rightRecord.getPosition().getProduce().getPrc_id());
        leftRecord.setPrc_date(StringUtil.appDateString2dbTimestampString(prc.getPrc_date()));
        leftRecord.setPrc_id(prc.getPrc_id());
        leftRecord.setPrc_number(prc.getPrc_number());
        leftRecord.setStuffCategory(rightRecord.getPosition().getStuffCategory());
        //must be already filled
        int idx = findLeftRecordByPrcId(data.getLeftIntermediate(), leftRecord.getPrc_id());
        data.getLeftIntermediate().add(idx, leftRecord);
        leftRecord.setRut_id(form.getRoute().getId());
      }
    }
    data.getRightIntermediate().remove(rightRecord);
  }

  public ActionForward save(IActionContext context) throws Exception
  {
    ShippingData data = (ShippingData) StoreUtil.getSession(context.getRequest(), ShippingData.class);
    ShippingPositionsForm form = (ShippingPositionsForm) context.getForm();
    UserDAO.load(context, form.getManager());
    boolean hasError = false;
    for (int i = 0; i < data.getRightIntermediate().size(); i++)
    {
      ShippingData.RightRecord record = data.getRightIntermediate().get(i);
      if (Double.isNaN(record.getPosition().getSaleSumPlusNds()))
      {
        StrutsUtil.addError(context, "errors.shipping_positions.wrong.sum_plus_nds", null);
        hasError = true;
      }
    }
    if (hasError)
    {
      return show(context);
    }
    return context.getMapping().findForward("back");
  }

  ShippingData.LeftRecord findLeftRecordByLpcId(List<ShippingData.LeftRecord> leftlList, String lpc_id)
  {
    for (ShippingData.LeftRecord record : leftlList)
    {
      if (StringUtil.equal(lpc_id, record.getLpc_id()))
      {
        return record;
      }
    }
    return null;
  }

  ShippingData.RightRecord findRightRecordByLpcId(List<ShippingData.RightRecord> rightlList, String lpcId)
  {
    for (ShippingData.RightRecord record : rightlList)
    {
      if (StringUtil.equal(lpcId, record.getPosition().getProduce().getLpc_id()))
      {
        return record;
      }
    }
    return null;
  }

  int findLeftRecordByPrcId(List originalList, String prc_id)
  {
    for (int i = 0; i < originalList.size(); i++)
    {
      ShippingData.LeftRecord record = (ShippingData.LeftRecord) originalList.get(i);
      if (StringUtil.equal(prc_id, record.getPrc_id()))
      {
        return i;
      }
    }
    return originalList.size();
  }
}
