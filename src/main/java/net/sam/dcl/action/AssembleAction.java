package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.AssembleForm;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class AssembleAction extends DBTransactionAction implements IDispatchable
{
  protected static Log log = LogFactory.getLog(AssembleAction.class);

  private void saveCurrentFormToBean(IActionContext context)
  {
    AssembleForm form = (AssembleForm) context.getForm();

    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);

    assemble.setIs_new_doc(form.getIs_new_doc());

    assemble.setAsm_id(form.getAsm_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        assemble.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        assemble.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getStuffCategory().getId()))
      {
        assemble.setStuffCategory(StuffCategoryDAO.load(context, form.getStuffCategory().getId()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    assemble.setUsr_date_create(form.getUsr_date_create());
    assemble.setUsr_date_edit(form.getUsr_date_edit());
    assemble.setAsm_number(form.getAsm_number());
    assemble.setAsm_date(form.getAsm_date());
    assemble.setAsm_block(form.getAsm_block());
    assemble.setAsm_count(Integer.parseInt(form.getAsm_count()));
    assemble.setProduce(form.getProduce());
    assemble.setOpr_id(form.getOpr_id());
    //0 - сборка, 1 - разборка
    if ( "1".equals(form.getAsm_type_assemble()) )
    {
      assemble.setAsm_type("0");
    }
    if ( "1".equals(form.getAsm_type_disassemble()) )
    {
      assemble.setAsm_type("1");
    }
    assemble.setAsm_occupied(form.getAsm_occupied());

    StoreUtil.putSession(context.getRequest(), assemble);
  }

  private void getCurrentFormFromBean(IActionContext context, Assemble assembleIn)
  {
    AssembleForm form = (AssembleForm) context.getForm();
    Assemble assemble;
    if (null != assembleIn)
    {
      assemble = assembleIn;
    }
    else
    {
      assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    }

    if (null != assemble)
    {
      form.setIs_new_doc(assemble.getIs_new_doc());

      form.setAsm_id(assemble.getAsm_id());
      form.setCreateUser(assemble.getCreateUser());
      form.setEditUser(assemble.getEditUser());
      form.setUsr_date_create(assemble.getUsr_date_create());
      form.setUsr_date_edit(assemble.getUsr_date_edit());
      form.setAsm_number(assemble.getAsm_number());
      form.setAsm_date(assemble.getAsm_date());
      form.setAsm_block(assemble.getAsm_block());
      form.setAsm_count(Integer.toString(assemble.getAsm_count()));
      form.setProduce(assemble.getProduce());
      form.setStuffCategory(assemble.getStuffCategory());
      form.setOpr_id(assemble.getOpr_id());
      if ( assemble.isAssemble() )
      {
        form.setAsm_type_assemble("1");
      }
      else
      {
        form.setAsm_type_assemble("");
      }
      if ( assemble.isDisassemble() )
      {
        form.setAsm_type_disassemble("1");
      }
      else
      {
        form.setAsm_type_disassemble("");
      }
      form.setAsm_occupied(assemble.getAsm_occupied());

      form.getGridAsmDisasm().setDataList(assemble.getAsmOrDisasm());
      form.getGridSpec().setDataList(assemble.getProduces());
    }
  }

  public ActionForward deleteProduce(IActionContext context) throws Exception
  {
    AssembleForm form = (AssembleForm) context.getForm();
    saveCurrentFormToBean(context);
    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    assemble.deleteProduce(form.getNumber());
    getCurrentFormFromBean(context, null);

    return show(context);
  }

  public ActionForward retFromProduceOperation(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context, null);

    return show(context);
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    AssembleForm form = (AssembleForm) context.getForm();

    saveCurrentFormToBean(context);

    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    if ( "1".equals(form.getAsm_type_assemble()) )
    {
      /*
      при сохранении документа проверять для каждой строки 2-й таблицы условие
      N>=k*m, где
        m - количество указанное в строке
        k - количество комплектов
        N - количество в заказе
      Если хотя бы одна строка не отвечает условию, то выводить сообщение "Количество сборок превышает допустимое"
      */
      for (int i = 0; i < assemble.getProduces().size(); i++)
      {
        AssembleProduce assembleProduce = assemble.getProduces().get(i);

        if ( !StringUtil.isEmpty(assembleProduce.getOpr_id()) )
        {
          OrderProduce orderProduce = OrderProduceDAO.load(context, assembleProduce.getOpr_id());
          if ( assembleProduce.getApr_count() * assemble.getAsm_count() > orderProduce.getOpr_count_executed() )
          {
            StrutsUtil.addError(context, "errors.assemble.incorrect_count_asm", assembleProduce.getProduce().getName(), null);
            return false;
          }
        }
      }
    }

    //Разборка
    if ( "1".equals(form.getAsm_type_disassemble()) )
    {
      for (int i = 0; i < assemble.getProduces().size(); i++)
      {
        AssembleProduce assembleProduce = assemble.getProduces().get(i);

        if ( assembleProduce.getApr_count() <= 0 )
        {
          StrutsUtil.addError(context, "errors.assemble.incorrect_count_disasm", assembleProduce.getProduce().getName(), null);
          return false;
        }
      }
    }

    saveCurrentFormToBean(context);

    //0 - сборка, 1 - разборка
    if ( "1".equals(form.getAsm_type_assemble()) )
    {
      assemble.setAsm_type("0");
    }
    if ( "1".equals(form.getAsm_type_disassemble()) )
    {
      assemble.setAsm_type("1");
    }
    if ( !"1".equals(form.getAsm_type_assemble()) && !"1".equals(form.getAsm_type_disassemble()) )
    {
      StrutsUtil.addError(context, "errors.assemble.select_type", null);
      return false;
    }

    if (StringUtil.isEmpty(form.getAsm_id()))
    {
      User user = UserUtil.getCurrentUser(context.getRequest());
      form.setCreateUser(user);
      //form asm_number
      String year = form.getAsm_date().substring(8);
      String month = form.getAsm_date().substring(3, 5);
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        DAOUtils.load(context, "user-code-load", null);
      }
      form.setGen_num(CommonDAO.GetNumber(context, "get-num_assemble"));
      assemble.setAsm_number("BYM" + year + month + "/" + StringUtil.padWithLeadingZeros(form.getGen_num(), 4));
      assemble.setAsm_number(assemble.getAsm_number() + "-" + form.getUsr_code().toUpperCase());

      AssembleDAO.insert(context, assemble);
    }
    else
    {
      AssembleDAO.save(context, assemble);
    }

    return true;
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final AssembleForm form = (AssembleForm) context.getForm();

    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    assemble.calculate();
    form.getGridAsmDisasm().setDataList(assemble.getAsmOrDisasm());
    form.getGridSpec().setDataList(assemble.getProduces());

    User user = UserUtil.getCurrentUser(context.getRequest());
    /* создают и редактируют менагеры, экономист
       просмотр - декларант
       админ, ессно, может всё
       После блокировки:
       read-only могут админ, менагер, экономист, декларант
    */
    if ( "1".equals(assemble.getAsm_block()) || ( !user.isAdmin() && !user.isEconomist()  && !user.isManager() )  )
    {
      form.setFormReadOnly(true);
    }
    else
    {
      form.setFormReadOnly(false);
    }

    if ( assemble.isAssemble() )
    {
      form.setAsm_type_assemble("1");
    }
    else
    {
      form.setAsm_type_assemble("");
    }
    if ( assemble.isDisassemble() )
    {
      form.setAsm_type_disassemble("1");
    }
    else
    {
      form.setAsm_type_disassemble("");
    }

    context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        AssembleProduce deliveryRequestProduce = (AssembleProduce) form.getGridSpec().getDataList().get(context.getTable().getRecordCounter() - 1);
	      return !(StringUtil.isEmpty(deliveryRequestProduce.getApr_occupied()) && !form.isFormReadOnly());
      }
    });

    return context.getMapping().findForward("form");
  }

  public ActionForward reloadWithClean(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    assemble.clear();
    StoreUtil.putSession(context.getRequest(), assemble);
    getCurrentFormFromBean(context, null);
    return show(context);
  }

  public ActionForward reloadWithCleanProduce(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    assemble.clearProduce();
    StoreUtil.putSession(context.getRequest(), assemble);
    getCurrentFormFromBean(context, null);
    return show(context);
  }

  public ActionForward backFromSelect(IActionContext context) throws Exception
  {
    ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);

    if ( assemble.isAssemble() )
    {
      for (int i = 0; i < assemble.getProduces().size(); i++)
      {
        AssembleProduce assembleProduce = assemble.getProduces().get(i);
        if ( StringUtil.isEmpty(assembleProduce.getOpr_id()) )
        {
          continue;
        }

        ImportData.RightRecord rightRecord = data.findRightRecordByDocId(assembleProduce.getOpr_id());
        if ( null != rightRecord ) //нашли - значит эту запись отредактировали
        {
          rightRecord.setModified(true);
          assembleProduce.setApr_count(rightRecord.getPosition().getCount());
        }
        else //не нашли - значит эту запись удалили - удаляем из списка
        {
          assemble.getProduces().remove(i);
          i--;
        }
      }

      for (int i = 0; i < data.getRightIntermediate().size(); i++)
      {
        ImportData.RightRecord record = data.getRightIntermediate().get(i);
        if ( !record.isModified() ) //не измененная, а новая запись - добавляем в список товаров
        {
          AssembleProduce assembleProduce = new AssembleProduce();
          assembleProduce.setAsm_id(assemble.getAsm_id());
          assembleProduce.setOpr_id(record.getPosition().getDoc_id());
          assembleProduce.setApr_count(record.getPosition().getCount());

          assembleProduce.setProduce(ProduceDAO.loadProduceWithUnit(record.getPosition().getProduce().getId()));

          Order order = OrderDAO.loadByOprId(context, record.getPosition().getDoc_id());
          assembleProduce.setStf_id(order.getStuffCategory().getId());
          assembleProduce.setStf_name(order.getStuffCategory().getName());
          assembleProduce.setCtn_number(assembleProduce.getCatalogNumberForStuffCategory());

          assembleProduce.setNumber(Integer.toString(assemble.getProduces().size()));
          assemble.getProduces().add(assembleProduce);
        }
      }
    }

    if ( assemble.isDisassemble() )
    {
      if ( data.getRightIntermediate().size() > 0 )
      {
        ImportData.RightRecord record = data.getRightIntermediate().get(0);
        assemble.setOpr_id(record.getPosition().getDoc_id());
        assemble.setProduce(ProduceDAO.loadProduceWithUnit(record.getPosition().getProduce().getId()));

        Order order = OrderDAO.loadByOprId(context, record.getPosition().getDoc_id());
        assemble.setStuffCategory(order.getStuffCategory());

        assemble.fillFirstTable();
      }
      else
      {
        assemble.setOpr_id(null);
        assemble.setProduce(new DboProduce());
        assemble.setStuffCategory(new StuffCategory());

        assemble.getAsmOrDisasm().clear();
      }
    }

    getCurrentFormFromBean(context, null);
    return show(context);
  }

  public ActionForward selectFromOrder(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    ImportData importData = new ImportData();
    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    if ( assemble.isAssemble() )
    {
      for (int i = 0; i < assemble.getProduces().size(); i++)
      {
        AssembleProduce assembleProduce = assemble.getProduces().get(i);
        if ( !StringUtil.isEmpty(assembleProduce.getOpr_id()) ) //только те, что импортированы из заказа
        {
          ImportPosition position = new ImportPosition();
          position.setDoc_id(assembleProduce.getOpr_id());
          position.setCount(assembleProduce.getApr_count());
          position.setProduce(assembleProduce.getProduce());
          ImportData.RightRecord rightRecord = new ImportData.RightRecord();
          rightRecord.setId(i);
          rightRecord.setPosition(new ImportPosition(position));
          importData.getRightIntermediate().add(rightRecord);
        }
      }
    }

    if ( assemble.isDisassemble() )
    {
      if ( !StringUtil.isEmpty(assemble.getOpr_id()) ) //только те, что импортированы из заказа
      {
        ImportPosition position = new ImportPosition();
        position.setDoc_id(assemble.getOpr_id());
        position.setProduce(assemble.getProduce());
        ImportData.RightRecord rightRecord = new ImportData.RightRecord();
        rightRecord.setId(0);
        rightRecord.setPosition(new ImportPosition(position));
        importData.getRightIntermediate().add(rightRecord);
      }
    }

    StoreUtil.putSession(context.getRequest(), importData);

    return context.getMapping().findForward("selectFromOrder");
  }

  public ActionForward selectProduce(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);
    return context.getMapping().findForward("selectProduce");
  }

  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception
  {
    AssembleForm form = (AssembleForm) context.getForm();
		String nomenclatureId = SelectFromGridAction.getSelectedId(context);

    Assemble assemble = (Assemble) StoreUtil.getSession(context.getRequest(), Assemble.class);
    if ( assemble.isAssemble() && !StringUtil.isEmpty(nomenclatureId) )
    {
      form.setOpr_id(null); //выбираем товар, значит он больше не связан с Заказом (если был)
      form.setProduce(ProduceDAO.loadProduceWithUnit(new Integer(nomenclatureId)));
    }
    saveCurrentFormToBean(context);
    if ( assemble.isAssemble() && !StringUtil.isEmpty(nomenclatureId) )
    {
      assemble.fillFirstTable();
    }
    if ( assemble.isDisassemble() && !StringUtil.isEmpty(nomenclatureId) )
    {
      if (assemble.findProduceById(Integer.parseInt(nomenclatureId)))
      {
        StrutsUtil.addError(context, "errors.assemble.double_produce", null);
        return show(context);
      }
      AssembleProduce assembleProduce = new AssembleProduce();
      assembleProduce.setAsm_id(assemble.getAsm_id());

      assembleProduce.setProduce(ProduceDAO.loadProduceWithUnit(Integer.parseInt(nomenclatureId)));

      assembleProduce.setStf_id(assemble.getStuffCategory().getId());
      assembleProduce.setNumber(Integer.toString(assemble.getProduces().size()));
      assemble.getProduces().add(assembleProduce);
    }

    return show(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    AssembleForm form = (AssembleForm) context.getForm();

    Assemble assemble = new Assemble();
    StoreUtil.putSession(context.getRequest(), assemble);
    //обнуляем поля формы
    getCurrentFormFromBean(context, assemble);

    form.setAsm_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    form.setIs_new_doc("true");
    form.setAsm_block("");
    form.setAsm_type_assemble("");
    form.setAsm_type_disassemble("");

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    AssembleForm form = (AssembleForm) context.getForm();

    Assemble assemble = AssembleDAO.load(context, form.getAsm_id());
    assemble.fillFirstTable();
    StoreUtil.putSession(context.getRequest(), assemble);
    getCurrentFormFromBean(context, assemble);

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
