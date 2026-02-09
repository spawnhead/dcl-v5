package net.sam.dcl.action;

import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.AssemblePositionsForm;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.util.*;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dao.OrderDAO;
import net.sam.dcl.beans.Order;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class AssemblePositionsAction extends ImportPositionsAction implements IDispatchable
{
  public ActionForward show(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("form");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    AssemblePositionsForm form = (AssemblePositionsForm) context.getForm();

    //0 - сборка, 1 - разборка
    //Для расборки обнуляем Продукт (производитель), чтобы показывались все
    if ( "1".equals(form.getAsm_type()) )
    {
      form.setStf_id(null);  
    }

    form.getGridLeft().getDataList().clear();
    form.getGridRight().getDataList().clear();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    if (data.getLeftOriginal() == null)
    {
      Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
      hibSession.beginTransaction();
      data.setLeftOriginal(DAOUtils.fillList(context, "order-positions-available-for-assemble", context.getForm(), ImportData.LeftRecord.class, null, null));
      for (int i = 0; i < data.getLeftOriginal().size(); i++)
      {
        ImportData.LeftRecord leftRecord = data.getLeftOriginal().get(i);
        ImportData.RightRecord rightRecord = findRightRecordByDocId(data.getRightIntermediate(), leftRecord.getDoc_id(), false);
        if ( null == rightRecord )
        {
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
    AssemblePositionsForm form = (AssemblePositionsForm) context.getForm();

    //0 - сборка, 1 - разборка
    if ( "1".equals(form.getAsm_type()) )
    {
      int selected_count = 0;
      for (int i = 0; i < form.getGridLeft().getDataList().size(); i++)
      {
        ImportData.LeftRecord leftRecord = (ImportData.LeftRecord) form.getGridLeft().getDataList().get(i);
        if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
        {
          selected_count++;
          if ( selected_count > 1 )
          {
            StrutsUtil.addError(context, "errors.assemble_positions.select_one_record", null);
            return show(context);
          }
        }
      }
    }

    for (int i = 0; i < form.getGridLeft().getDataList().size(); i++)
    {
      ImportData.LeftRecord leftRecord = (ImportData.LeftRecord) form.getGridLeft().getDataList().get(i);
      if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
      {
        //0 - сборка, 1 - разборка
        if ( "0".equals(form.getAsm_type()) )
        {
          ImportData.RightRecord rightRecord = new ImportData.RightRecord();
          rightRecord.getPosition().setDoc_id(leftRecord.getDoc_id());
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
          data.getLeftIntermediate().remove(leftRecord);
          i--;
        }

        //0 - сборка, 1 - разборка
        if ( "1".equals(form.getAsm_type()) )
        {
          ImportData.RightRecord rightRecord;
          if ( data.getRightIntermediate().size() > 0 )
          {
            rightRecord = data.getRightIntermediate().get(0);
            deleteRightRecord(context, data, rightRecord);
          }

          rightRecord = new ImportData.RightRecord();
          rightRecord.getPosition().setDoc_id(leftRecord.getDoc_id());
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
          data.getLeftIntermediate().remove(leftRecord);
          break;
        }
      } //if (!StringUtil.isEmpty(leftRecord.getSelected_id()))
    } //for
    form.getGridLeft().setDataList(data.getLeftIntermediate());
    form.getGridRight().setDataList(data.getRightIntermediate());
    return show(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    AssemblePositionsForm form = (AssemblePositionsForm) context.getForm();
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
    ImportData.LeftRecord leftRecord = findLeftRecordByDocId(data.getLeftOriginal(), rightRecord.getPosition().getDoc_id(), false);
    if (leftRecord != null)
    {
      leftRecord = new ImportData.LeftRecord(leftRecord);
    }
    else
    {
      leftRecord = new ImportData.LeftRecord();
      leftRecord.setDoc_id(rightRecord.getPosition().getDoc_id());
      leftRecord.setProduce(rightRecord.getPosition().getProduce());
      Order order = OrderDAO.loadByOprId(context, rightRecord.getPosition().getDoc_id());
      leftRecord.setId(order.getOrd_id());
      leftRecord.setDate(StringUtil.appDateString2dbTimestampString(order.getOrd_date()));
      leftRecord.setNumber(order.getOrd_number());
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

    int idx = findLeftRecordByParentDocId(data.getLeftOriginal(), leftRecord.getId());
    data.getLeftIntermediate().add(idx, leftRecord);
    data.getRightIntermediate().remove(rightRecord);
  }

  public ActionForward save(IActionContext context) throws Exception
  {
    AssemblePositionsForm form = (AssemblePositionsForm) context.getForm();
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    boolean hasError = false;
    for (int i = 0; i < data.getRightIntermediate().size(); i++)
    {
      ImportData.RightRecord record = data.getRightIntermediate().get(i);
      if (Double.isNaN(record.getPosition().getCount()))
      {
        StrutsUtil.addError(context, "errors.assemble_positions.wrong.count", null);
        hasError = true;
        continue;
      }
      //0 - сборка, 1 - разборка
      if ( "0".equals(form.getAsm_type()) )
      {
        if ( record.getPosition().getCount() <= 0 )
        {
          StrutsUtil.addError(context, "errors.assemble_positions.wrong.count_minus", null);
          hasError = true;
        }
      }
    }
    if (hasError)
    {
      return show(context);
    }
    return context.getMapping().findForward("back");
  }
}
