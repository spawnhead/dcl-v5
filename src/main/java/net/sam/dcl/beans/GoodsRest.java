package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.*;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.action.NomenclatureProduceActionBean;
import net.sam.dcl.dbo.DboAttachment;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class GoodsRest implements Serializable
{
  protected static Log log = LogFactory.getLog(GoodsRest.class);
  List<GoodsRestLine> goodsRestLines = new ArrayList<GoodsRestLine>();

  String date_begin;
  String have_date_to;
  String date_end;

  User user = new User();
  Department department = new Department();
  StuffCategory stuffCategory = new StuffCategory();
  Purpose purpose = new Purpose();
  String onlyTotal;
  String by_user;

  String view_department;
  String view_order_for;
  String view_1c_number;
  String view_cost_one_by;
  String view_price_list_by;
  String view_prc_number;
  String view_prc_date;
  String view_lpc_count;
  String view_usr_shipping;
  String view_debt;
  String view_purpose;
  String view_comment;
  String view_sums;

  String order_by_name;
  String order_by_stuff_category;
  String order_by_date_receipt;

  String goods_on_storage;
  String shipping_goods;

  public GoodsRest()
  {
  }

  public List<GoodsRestLine> getGoodsRestLines()
  {
    return goodsRestLines;
  }

  public void setGoodsRestLines(List<GoodsRestLine> goodsRestLines)
  {
    this.goodsRestLines = goodsRestLines;
  }

  public String getDate_begin()
  {
    return date_begin;
  }

  public void setDate_begin(String date_begin)
  {
    this.date_begin = date_begin;
  }

  public String getHave_date_to()
  {
    return have_date_to;
  }

  public void setHave_date_to(String have_date_to)
  {
    this.have_date_to = have_date_to;
  }

  public String getDate_end()
  {
    return date_end;
  }

  public void setDate_end(String date_end)
  {
    this.date_end = date_end;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public Purpose getPurpose()
  {
    return purpose;
  }

  public void setPurpose(Purpose purpose)
  {
    this.purpose = purpose;
  }

  public String getOnlyTotal()
  {
    return onlyTotal;
  }

  public void setOnlyTotal(String onlyTotal)
  {
    this.onlyTotal = onlyTotal;
  }

  public String getBy_user()
  {
    return by_user;
  }

  public void setBy_user(String by_user)
  {
    this.by_user = by_user;
  }

  public String getView_department()
  {
    return view_department;
  }

  public void setView_department(String view_department)
  {
    this.view_department = view_department;
  }

  public String getView_order_for()
  {
    return view_order_for;
  }

  public void setView_order_for(String view_order_for)
  {
    this.view_order_for = view_order_for;
  }

  public String getView_1c_number()
  {
    return view_1c_number;
  }

  public void setView_1c_number(String view_1c_number)
  {
    this.view_1c_number = view_1c_number;
  }

  public String getView_cost_one_by()
  {
    return view_cost_one_by;
  }

  public void setView_cost_one_by(String view_cost_one_by)
  {
    this.view_cost_one_by = view_cost_one_by;
  }

  public String getView_price_list_by()
  {
    return view_price_list_by;
  }

  public void setView_price_list_by(String view_price_list_by)
  {
    this.view_price_list_by = view_price_list_by;
  }

  public String getView_prc_date()
  {
    return view_prc_date;
  }

  public void setView_prc_date(String view_prc_date)
  {
    this.view_prc_date = view_prc_date;
  }

  public String getView_prc_number()
  {
    return view_prc_number;
  }

  public void setView_prc_number(String view_prc_number)
  {
    this.view_prc_number = view_prc_number;
  }

  public String getView_lpc_count()
  {
    return view_lpc_count;
  }

  public void setView_lpc_count(String view_lpc_count)
  {
    this.view_lpc_count = view_lpc_count;
  }

  public String getView_usr_shipping()
  {
    return view_usr_shipping;
  }

  public void setView_usr_shipping(String view_usr_shipping)
  {
    this.view_usr_shipping = view_usr_shipping;
  }

  public String getView_debt()
  {
    return view_debt;
  }

  public void setView_debt(String view_debt)
  {
    this.view_debt = view_debt;
  }

  public String getView_purpose()
  {
    return view_purpose;
  }

  public void setView_purpose(String view_purpose)
  {
    this.view_purpose = view_purpose;
  }

  public String getView_comment()
  {
    return view_comment;
  }

  public void setView_comment(String view_comment)
  {
    this.view_comment = view_comment;
  }

  public String getView_sums()
  {
    return view_sums;
  }

  public void setView_sums(String view_sums)
  {
    this.view_sums = view_sums;
  }

  public String getOrder_by_name()
  {
    return order_by_name;
  }

  public void setOrder_by_name(String order_by_name)
  {
    this.order_by_name = order_by_name;
  }

  public String getOrder_by_stuff_category()
  {
    return order_by_stuff_category;
  }

  public void setOrder_by_stuff_category(String order_by_stuff_category)
  {
    this.order_by_stuff_category = order_by_stuff_category;
  }

  public String getOrder_by_date_receipt()
  {
    return order_by_date_receipt;
  }

  public void setOrder_by_date_receipt(String order_by_date_receipt)
  {
    this.order_by_date_receipt = order_by_date_receipt;
  }

  public String getGoods_on_storage()
  {
    return goods_on_storage;
  }

  public void setGoods_on_storage(String goods_on_storage)
  {
    this.goods_on_storage = goods_on_storage;
  }

  public String getShipping_goods()
  {
    return shipping_goods;
  }

  public void setShipping_goods(String shipping_goods)
  {
    this.shipping_goods = shipping_goods;
  }

  public boolean getUserShow()
  {
    if (StringUtil.isEmpty(onlyTotal))
    {
      return true;
    }

    return !StringUtil.isEmpty(getUser().getUserFullName()) || !StringUtil.isEmpty(getBy_user());
  }

  public boolean getStuffCategoryShow()
  {
    if (StringUtil.isEmpty(onlyTotal))
    {
      return true;
    }

    return !StringUtil.isEmpty(getStuffCategory().getName());
  }

  public boolean getDepartmentShow()
  {
    if ((!StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(getDepartment().getName())) ||
            (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(view_department))
            )
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public void calculate(boolean forManager, User checkUser, boolean forChiefDepartment, Department checkDepartment)
  {
    if (goodsRestLines.size() == 0)
    {
      return;
    }

    double less3Month = 0.0;
    double month3_6 = 0.0;
    double month6_9 = 0.0;
    double month9_12 = 0.0;
    double more12_month = 0.0;
    double less3MonthTo = 0.0;
    double month3_6_To = 0.0;
    double month6_9_To = 0.0;
    double month9_12_To = 0.0;
    double more12_month_To = 0.0;
    double debtSum = 0.0;

    if ( forManager || forChiefDepartment )
    {
      for (GoodsRestLine goodsRestLine : goodsRestLines)
      {
        boolean setTo0 = false;
        //если начальник отдела, то сравниваем Отдел, и не обращаем внимание на пользователя, поэтому else if
        if ( forChiefDepartment && null != checkDepartment )
        {
          if ( !checkDepartment.getId().equals(goodsRestLine.getDep_id()) )
          {
            setTo0 = true;
          }
        }
        else if ( forManager && null != checkUser )
        {
          if ( !checkUser.getUsr_id().equals(goodsRestLine.getUsr_id()) )
          {
            setTo0 = true;
          }
        }

        if (setTo0)
        {
          goodsRestLine.setLess_3_month(0.0);
          goodsRestLine.setMonth_3_6(0.0);
          goodsRestLine.setMonth_6_9(0.0);
          goodsRestLine.setMonth_9_12(0.0);
          goodsRestLine.setMore_12_month(0.0);
          goodsRestLine.setLess_3_month_to(0.0);
          goodsRestLine.setMonth_3_6_to(0.0);
          goodsRestLine.setMonth_6_9_to(0.0);
          goodsRestLine.setMonth_9_12_to(0.0);
          goodsRestLine.setMore_12_month_to(0.0);
        }
      }
    }

    double allSum = 0.0;
    double allSumTo = 0.0;
    AttachmentsService attachmentsService = new AttachmentsService(HibernateUtil.getSessionFactory().getCurrentSession());

    for (GoodsRestLine goodsRestLine : goodsRestLines)
    {
      less3Month += goodsRestLine.getLess_3_month();
      month3_6 += goodsRestLine.getMonth_3_6();
      month6_9 += goodsRestLine.getMonth_6_9();
      month9_12 += goodsRestLine.getMonth_9_12();
      more12_month += goodsRestLine.getMore_12_month();
      less3MonthTo += goodsRestLine.getLess_3_month_to();
      month3_6_To += goodsRestLine.getMonth_3_6_to();
      month6_9_To += goodsRestLine.getMonth_6_9_to();
      month9_12_To += goodsRestLine.getMonth_9_12_to();
      more12_month_To += goodsRestLine.getMore_12_month_to();
      debtSum += goodsRestLine.getDebt_summ();
      //Если отмечен итог - никаких комментов и аттачей
      if ( StringUtil.isEmpty(onlyTotal) )
      {
        try
        {
          List<DboAttachment> attachments = attachmentsService.list(NomenclatureProduceActionBean.referencedTable, Integer.parseInt(goodsRestLine.getPrd_id()));
          goodsRestLine.setImagesIds(new ArrayList<Integer>());
          for (DboAttachment attachment : attachments)
          {
            String ext = attachment.getOriginalFileExtention();
            if (".GIF".equals(ext) || ".JPG".equals(ext))
            {
              goodsRestLine.getImagesIds().add(attachment.getId());
            }
          }
        }
        catch (Exception e)
        {
          log.error(e);
          throw new RuntimeException(e);
        }
      }
    }

    IActionContext context = ActionContext.threadInstance();

    GoodsRestLine goodsRestLine = getEmptyLine();
    try
    {
      if (StringUtil.isEmpty(onlyTotal))
      {
        goodsRestLine.setProduce_name(StrutsUtil.getMessage(context, "GoodsRest.itogo_sum"));
      }
      else
      {
        if (getUserShow() && StringUtil.isEmpty(getBy_user()))
        {
          goodsRestLine.setUsr_name(StrutsUtil.getMessage(context, "GoodsRest.itogo_sum"));
        }
        if (getDepartmentShow())
        {
          goodsRestLine.setDep_name(StrutsUtil.getMessage(context, "GoodsRest.itogo_sum"));
        }
        if (getStuffCategoryShow())
        {
          goodsRestLine.setStf_name(StrutsUtil.getMessage(context, "GoodsRest.itogo_sum"));
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    goodsRestLine.setLess_3_month(less3Month);
    goodsRestLine.setMonth_3_6(month3_6);
    goodsRestLine.setMonth_6_9(month6_9);
    goodsRestLine.setMonth_9_12(month9_12);
    goodsRestLine.setMore_12_month(more12_month);
    goodsRestLine.setLess_3_month_to(less3MonthTo);
    goodsRestLine.setMonth_3_6_to(month3_6_To);
    goodsRestLine.setMonth_6_9_to(month6_9_To);
    goodsRestLine.setMonth_9_12_to(month9_12_To);
    goodsRestLine.setMore_12_month_to(more12_month_To);
    goodsRestLine.setDebt_summ(debtSum);
    goodsRestLine.setItogLine(true);
    goodsRestLines.add(goodsRestLines.size(), goodsRestLine);

    allSum += less3Month;
    allSum += month3_6;
    allSum += month6_9;
    allSum += month9_12;
    allSum += more12_month;
    allSumTo += less3MonthTo;
    allSumTo += month3_6_To;
    allSumTo += month6_9_To;
    allSumTo += month9_12_To;
    allSumTo += more12_month_To;

    GoodsRestLine goodsRestLineAll = getEmptyLine();
    try
    {
      if (StringUtil.isEmpty(onlyTotal))
      {
        goodsRestLineAll.setProduce_name(StrutsUtil.getMessage(context, "GoodsRest.all_sum"));
      }
      else
      {
        if (getUserShow() && StringUtil.isEmpty(getBy_user()))
        {
          goodsRestLineAll.setUsr_name(StrutsUtil.getMessage(context, "GoodsRest.all_sum"));
        }
        if (getDepartmentShow())
        {
          goodsRestLineAll.setDep_name(StrutsUtil.getMessage(context, "GoodsRest.all_sum"));
        }
        if (getStuffCategoryShow())
        {
          goodsRestLineAll.setStf_name(StrutsUtil.getMessage(context, "GoodsRest.all_sum"));
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    goodsRestLineAll.setMore_12_month(allSum);
    goodsRestLineAll.setMore_12_month_to(allSumTo);
    goodsRestLineAll.setItogLine(true);
    goodsRestLineAll.setAllItog(true);
    goodsRestLines.add(goodsRestLines.size(), goodsRestLineAll);

    //если нужно разбивать по пользователям, и выбраны все подразделения
    //считаем итоги для каждого подразделения
    if ( !StringUtil.isEmpty(getBy_user()) && "-1".equals(getDepartment().getId()) && getGoodsRestLines().size() > 0 )
    {
      goodsRestLine = goodsRestLines.get(0);
      less3Month = 0.0;
      month3_6 = 0.0;
      month6_9 = 0.0;
      month9_12 = 0.0;
      more12_month = 0.0;
      less3MonthTo = 0.0;
      month3_6_To = 0.0;
      month6_9_To = 0.0;
      month9_12_To = 0.0;
      more12_month_To = 0.0;
      debtSum = 0.0;
      String depForCmp = goodsRestLine.getDep_name();
      int rememberIdx = -1;
      int i = 0;
      while (i < goodsRestLines.size())
      {
        goodsRestLine = goodsRestLines.get(i);
        if ( goodsRestLine.isItogLine() )
        {
          i++;
          continue;
        }

        if ( !depForCmp.equals(goodsRestLine.getDep_name()) )
        {
          GoodsRestLine goodsRestLineDep = getEmptyLine();
          goodsRestLineDep.setLess_3_month(less3Month);
          goodsRestLineDep.setMonth_3_6(month3_6);
          goodsRestLineDep.setMonth_6_9(month6_9);
          goodsRestLineDep.setMonth_9_12(month9_12);
          goodsRestLineDep.setMore_12_month(more12_month);
          goodsRestLineDep.setLess_3_month_to(less3MonthTo);
          goodsRestLineDep.setMonth_3_6_to(month3_6_To);
          goodsRestLineDep.setMonth_6_9_to(month6_9_To);
          goodsRestLineDep.setMonth_9_12_to(month9_12_To);
          goodsRestLineDep.setMore_12_month_to(more12_month_To);
          goodsRestLineDep.setDebt_summ(debtSum);
          goodsRestLineDep.setItogLine(true);
          try
          {
            goodsRestLineDep.setDep_name(StrutsUtil.getMessage(context, "GoodsRest.itogo_sum") + " " + depForCmp);
          }
          catch (Exception e)
          {
            log.error(e);
          }
          goodsRestLines.add(i, goodsRestLineDep);

          less3Month = 0.0;
          month3_6 = 0.0;
          month6_9 = 0.0;
          month9_12 = 0.0;
          more12_month = 0.0;
          less3MonthTo = 0.0;
          month3_6_To = 0.0;
          month6_9_To = 0.0;
          month9_12_To = 0.0;
          more12_month_To = 0.0;
          debtSum = 0.0;
          depForCmp = goodsRestLine.getDep_name();

          i++;
        }

        less3Month += goodsRestLine.getLess_3_month();
        month3_6 += goodsRestLine.getMonth_3_6();
        month6_9 += goodsRestLine.getMonth_6_9();
        month9_12 += goodsRestLine.getMonth_9_12();
        more12_month += goodsRestLine.getMore_12_month();
        less3MonthTo += goodsRestLine.getLess_3_month_to();
        month3_6_To += goodsRestLine.getMonth_3_6_to();
        month6_9_To += goodsRestLine.getMonth_6_9_to();
        month9_12_To += goodsRestLine.getMonth_9_12_to();
        more12_month_To += goodsRestLine.getMore_12_month_to();
        debtSum += goodsRestLine.getDebt_summ();

        i++;
        rememberIdx = i;
      }

      if ( rememberIdx != -1 )
      {
        GoodsRestLine goodsRestLineDep = getEmptyLine();
        goodsRestLineDep.setLess_3_month(less3Month);
        goodsRestLineDep.setMonth_3_6(month3_6);
        goodsRestLineDep.setMonth_6_9(month6_9);
        goodsRestLineDep.setMonth_9_12(month9_12);
        goodsRestLineDep.setMore_12_month(more12_month);
        goodsRestLineDep.setLess_3_month_to(less3MonthTo);
        goodsRestLineDep.setMonth_3_6_to(month3_6_To);
        goodsRestLineDep.setMonth_6_9_to(month6_9_To);
        goodsRestLineDep.setMonth_9_12_to(month9_12_To);
        goodsRestLineDep.setMore_12_month_to(more12_month_To);
        goodsRestLineDep.setDebt_summ(debtSum);
        goodsRestLineDep.setItogLine(true);
        try
        {
          goodsRestLineDep.setDep_name(StrutsUtil.getMessage(context, "GoodsRest.itogo_sum") + " " + depForCmp);
        }
        catch (Exception e)
        {
          log.error(e);
        }
        goodsRestLines.add(rememberIdx, goodsRestLineDep);
      }
    }
  }

  public void cleanList()
  {
    goodsRestLines.clear();
  }

  public GoodsRestLine getEmptyLine()
  {
    GoodsRestLine goodsRestLine = new GoodsRestLine();

    goodsRestLine.setPrd_id("-1");
    goodsRestLine.setProduce_name("");
    goodsRestLine.setStf_name("");
    goodsRestLine.setOrder_for("");
    goodsRestLine.setUsr_name("");
    goodsRestLine.setDep_name("");
    goodsRestLine.setLpc_count(0.0);
    goodsRestLine.setPrc_date("");
    goodsRestLine.setPrc_number("");
    goodsRestLine.setShp_date("");
    goodsRestLine.setCtr_name("");
    goodsRestLine.setUsr_shipping("");
    goodsRestLine.setLpc_comment("");

    return goodsRestLine;
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();
    try
    {
      if (StringUtil.isEmpty(onlyTotal))
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.produce_name"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.prd_type"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.prd_params"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.prd_add_params"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.ctn_number"));
      }
      if (getStuffCategoryShow())
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.stf_name"));
      }
      if (StringUtil.isEmpty(onlyTotal))
      {
        if (!StringUtil.isEmpty(goods_on_storage) && !StringUtil.isEmpty(view_order_for))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.order_for"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.con_spc"));
        }
      }
      if (getDepartmentShow())
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.dep_name"));
      }
      if (StringUtil.isEmpty(onlyTotal))
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.unit"));
        if (StringUtil.isEmpty(shipping_goods))
        {
          if (!StringUtil.isEmpty(view_prc_date))
          {
            header.add(StrutsUtil.getMessage(context, "GoodsRest.prc_date"));
          }
          if (!StringUtil.isEmpty(view_prc_number))
          {
            header.add(StrutsUtil.getMessage(context, "GoodsRest.prc_number"));
          }
        }
        else
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.shp_date"));
        }
        if (!StringUtil.isEmpty(shipping_goods))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.ctr_name"));
          if (!StringUtil.isEmpty(view_usr_shipping))
          {
            header.add(StrutsUtil.getMessage(context, "GoodsRest.usr_shipping"));
          }
        }
        if (!StringUtil.isEmpty(view_lpc_count))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_count"));
        }
        header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_count_free"));
      }
      if (StringUtil.isEmpty(shipping_goods))
      {
        if (!StringUtil.isEmpty(view_sums))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.less_3"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.month_3_6"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.month_6_9"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.month_9_12"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.more_12_month"));
        }
      }
      else
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.less_1"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.month_1_2"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.month_2_3"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.month_3_6"));
        header.add(StrutsUtil.getMessage(context, "GoodsRest.more_6_month"));
      }
      if (!StringUtil.isEmpty(getHave_date_to()))
      {
        if (StringUtil.isEmpty(onlyTotal))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_count_free"));
        }
        if (StringUtil.isEmpty(shipping_goods))
        {
          if (!StringUtil.isEmpty(view_sums))
          {
            header.add(StrutsUtil.getMessage(context, "GoodsRest.less_3"));
            header.add(StrutsUtil.getMessage(context, "GoodsRest.month_3_6"));
            header.add(StrutsUtil.getMessage(context, "GoodsRest.month_6_9"));
            header.add(StrutsUtil.getMessage(context, "GoodsRest.month_9_12"));
            header.add(StrutsUtil.getMessage(context, "GoodsRest.more_12_month"));
          }
        }
        else
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.less_1"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.month_1_2"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.month_2_3"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.month_3_6"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.more_6_month"));
        }
      }
      else
      {
        if (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(shipping_goods) && !StringUtil.isEmpty(view_debt))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.debt_summ"));
          header.add(StrutsUtil.getMessage(context, "GoodsRest.debt_currency"));
        }
      }
      if ((StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(shipping_goods) && !StringUtil.isEmpty(view_purpose)) ||
              (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(goods_on_storage) && "-1".equals(getPurpose().getId()))
              )
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.purpose"));
      }
      if ( StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(goods_on_storage) )
      {
        if (!StringUtil.isEmpty(view_1c_number))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_1c_number"));
        }
        if (!StringUtil.isEmpty(view_cost_one_by))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_cost_one_by"));
        }
        if (!StringUtil.isEmpty(view_price_list_by))
        {
          header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_price_list_by"));
        }
      }
      if (getUserShow())
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.usr_name"));
      }
      if (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(view_comment))
      {
        header.add(StrutsUtil.getMessage(context, "GoodsRest.lpc_comment"));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (int i = 0; i < goodsRestLines.size(); i++)
    {
      GoodsRestLine goodsRestLine = goodsRestLines.get(i);

      List<Object> record = new ArrayList<Object>();

      if (StringUtil.isEmpty(onlyTotal))
      {
        record.add(goodsRestLine.getProduce_name());
        record.add(goodsRestLine.getPrd_type());
        record.add(goodsRestLine.getPrd_params());
        record.add(goodsRestLine.getPrd_add_params());
        record.add(goodsRestLine.getCtn_number());
      }
      if (getStuffCategoryShow())
      {
        record.add(goodsRestLine.getStf_name());
      }
      if (StringUtil.isEmpty(onlyTotal))
      {
        if (!StringUtil.isEmpty(goods_on_storage) && !StringUtil.isEmpty(view_order_for))
        {
          record.add(goodsRestLine.getOrder_for());
          record.add(goodsRestLine.getCon_spc().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator));
        }
      }
      if (getDepartmentShow())
      {
        record.add(goodsRestLine.getDep_name());
      }
      if (StringUtil.isEmpty(onlyTotal))
      {
        record.add(goodsRestLine.getUnit());
        if (StringUtil.isEmpty(shipping_goods))
        {
          if (!StringUtil.isEmpty(view_prc_date))
          {
            record.add(goodsRestLine.getPrc_date_formatted());
          }
          if (!StringUtil.isEmpty(view_prc_number))
          {
            record.add(goodsRestLine.getPrc_number());
          }
        }
        else
        {
          record.add(goodsRestLine.getShp_date_formatted());
        }
        if (!StringUtil.isEmpty(shipping_goods))
        {
          record.add(goodsRestLine.getCtr_name());
          if (!StringUtil.isEmpty(view_usr_shipping))
          {
            record.add(goodsRestLine.getUsr_shipping());
          }
        }
        if (i < goodsRestLines.size() - 2)
        {
          if (!StringUtil.isEmpty(view_lpc_count))
          {
            record.add(goodsRestLine.getLpc_count());
          }
          record.add(goodsRestLine.getLpc_count_free());
        }
        else
        {
          if (!StringUtil.isEmpty(view_lpc_count))
          {
            record.add("");
          }
          record.add("");
        }
      }

      if (!StringUtil.isEmpty(shipping_goods) || !StringUtil.isEmpty(view_sums) )
      {
        if ( i == goodsRestLines.size() - 1 && goodsRestLine.getLess_3_month() == 0 )
        {
          record.add("");
        }
        else
        {
          record.add(goodsRestLine.getLess_3_month());
        }
        if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMonth_3_6() == 0 )
        {
          record.add("");
        }
        else
        {
          record.add(goodsRestLine.getMonth_3_6());
        }
        if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMonth_6_9() == 0 )
        {
          record.add("");
        }
        else
        {
          record.add(goodsRestLine.getMonth_6_9());
        }
        if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMonth_9_12() == 0 )
        {
          record.add("");
        }
        else
        {
          record.add(goodsRestLine.getMonth_9_12());
        }
        if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMore_12_month() == 0 )
        {
          record.add("");
        }
        else
        {
          record.add(goodsRestLine.getMore_12_month());
        }
      }

      if (!StringUtil.isEmpty(getHave_date_to()))
      {
        if (StringUtil.isEmpty(onlyTotal))
        {
          if (i < goodsRestLines.size() - 2)
          {
            record.add(goodsRestLine.getLpc_count_free_to());
          }
          else
          {
            record.add("");
          }
        }

        if (!StringUtil.isEmpty(shipping_goods) || !StringUtil.isEmpty(view_sums) )
        {
          if ( i == goodsRestLines.size() - 1 && goodsRestLine.getLess_3_month_to() == 0 )
          {
            record.add("");
          }
          else
          {
            record.add(goodsRestLine.getLess_3_month_to());
          }
          if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMonth_3_6_to() == 0 )
          {
            record.add("");
          }
          else
          {
            record.add(goodsRestLine.getMonth_3_6_to());
          }
          if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMonth_6_9_to() == 0 )
          {
            record.add("");
          }
          else
          {
            record.add(goodsRestLine.getMonth_6_9_to());
          }
          if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMonth_9_12_to() == 0 )
          {
            record.add("");
          }
          else
          {
            record.add(goodsRestLine.getMonth_9_12_to());
          }
          if ( i == goodsRestLines.size() - 1 && goodsRestLine.getMore_12_month_to() == 0 )
          {
            record.add("");
          }
          else
          {
            record.add(goodsRestLine.getMore_12_month_to());
          }
        }
      }
      else
      {
        if (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(shipping_goods) && !StringUtil.isEmpty(view_debt))
        {
          if (
                  (i < goodsRestLines.size() - 2 && goodsRestLine.getDebt_summ() == 0) ||
                  (i == goodsRestLines.size() - 1 && goodsRestLine.getDebt_summ() == 0)
             )
          {
            record.add("");
          }
          else
          {
            record.add(goodsRestLine.getDebt_summ());
          }
          record.add(goodsRestLine.getDebt_currency());
        }
      }
      if (
           (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(shipping_goods) && !StringUtil.isEmpty(view_purpose)) ||
           (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(goods_on_storage) && "-1".equals(getPurpose().getId()))
         )
      {
        record.add(goodsRestLine.getPurpose());
      }
      if (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(goods_on_storage))
      {
        if (!StringUtil.isEmpty(view_1c_number))
        {
          record.add(goodsRestLine.getLpc_1c_number());
        }
        if (!StringUtil.isEmpty(view_cost_one_by))
        {
          record.add(goodsRestLine.getLpc_cost_one_by());
        }
        if (!StringUtil.isEmpty(view_price_list_by))
        {
          record.add(goodsRestLine.getLpc_price_list_by());
        }
      }
      if (getUserShow())
      {
        record.add(goodsRestLine.getUsr_name());
      }
      if (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(view_comment))
      {
        record.add(goodsRestLine.getCommentExcel().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator));
      }

      rows.add(record);
    }

    return rows;
  }

}
