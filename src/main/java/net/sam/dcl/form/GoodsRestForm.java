package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class GoodsRestForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  String common1 = " * ";
  String common2 = " sum(lpc_count) as lpc_count, \n" +
          "  sum(lpc_count_free) as lpc_count_free, \n" +
          "  sum(less_3_month) as less_3_month, \n" +
          "  sum(month_3_6) as month_3_6, \n" +
          "  sum(month_6_9) as month_6_9, \n" +
          "  sum(month_9_12) as month_9_12, \n" +
          "  sum(more_12_month) as more_12_month, \n" +
          "  sum(lpc_count_free_to) as lpc_count_free_to, \n" +
          "  sum(less_3_month_to) as less_3_month_to, \n" +
          "  sum(month_3_6_to) as month_3_6_to, \n" +
          "  sum(month_6_9_to) as month_6_9_to, \n" +
          "  sum(month_9_12_to) as month_9_12_to, \n" +
          "  sum(more_12_month_to) as more_12_month_to, ";

  String select_list = "";
  String group_by = "";
  String order_by = "";

  String have_date_to;

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
  String view_prc_date;
  String view_prc_number;
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
  String lpc_id;
  String lpc_comment;
  String imageId;
  String departmentId;
  String departmentName;

  boolean canEditDepartment = false;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setView_department("");
    setView_order_for("");
    setView_1c_number("");
    setView_cost_one_by("");
    setView_price_list_by("");
    setView_prc_date("");
    setView_prc_number("");
    setView_lpc_count("");
    setView_usr_shipping("");
    setView_debt("");
    setView_purpose("");
    setView_comment("");
    setView_sums("");

    setGoods_on_storage("");
    setShipping_goods("");

    setHave_date_to("");

    setOnlyTotal("");
    setBy_user("");

    setOrder_by_date_receipt("");
    setOrder_by_name("");
    setOrder_by_stuff_category("");

    super.reset(mapping, request);
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getUserShow()
  {
    if (StringUtil.isEmpty(onlyTotal))
    {
      return "1";
    }

    if (!StringUtil.isEmpty(getUser().getUserFullName()) || !StringUtil.isEmpty(getBy_user()))
    {
      return "1";
    }
    else
    {
      return "";
    }
  }

  public String getStuffCategoryShow()
  {
    if (StringUtil.isEmpty(onlyTotal))
    {
      return "1";
    }

    if (!StringUtil.isEmpty(getStuffCategory().getName()))
    {
      return "1";
    }
    else
    {
      return "";
    }
  }

  public String getDepartmentShow()
  {
    if ((!StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(getDepartment().getName())) ||
            (StringUtil.isEmpty(onlyTotal) && !StringUtil.isEmpty(view_department))
            )
    {
      return "1";
    }
    else
    {
      return "";
    }
  }

  public boolean getSelect()
  {
    if (
            StringUtil.isEmpty(getUser().getUsr_id()) &&
                    StringUtil.isEmpty(getDepartment().getId()) &&
                    StringUtil.isEmpty(getStuffCategory().getId())
            )
    {
      return false;
    }

    return true;
  }

  public boolean isCanForm()
  {
    return canForm && getSelect();
  }

  public String getHave_date_to()
  {
    return have_date_to;
  }

  public void setHave_date_to(String have_date_to)
  {
    this.have_date_to = have_date_to;
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

  public String getTop_view_count()
  {
    if (!StringUtil.isEmpty(goods_on_storage))
    {
      int base_count = 4;
      if (!StringUtil.isEmpty(getStuffCategoryShow()))
      {
        base_count++;
      }
      if (!StringUtil.isEmpty(getDepartmentShow()))
      {
        base_count++;
      }
      if (!StringUtil.isEmpty(view_order_for))
      {
        base_count += 2;
      }
      if (!StringUtil.isEmpty(view_prc_date))
      {
        base_count++;
      }
      if (!StringUtil.isEmpty(view_prc_number))
      {
        base_count++;
      }
      if (!StringUtil.isEmpty(view_lpc_count))
      {
        base_count++;
      }
      return Integer.toString(base_count);
    }

    //для типа отчета "Отгруженные товары, не закрытые оплатами"
    if (!StringUtil.isEmpty(shipping_goods))
    {
      int base_count = 6;
      if (!StringUtil.isEmpty(getStuffCategoryShow()))
      {
        base_count++;
      }
      if (!StringUtil.isEmpty(getDepartmentShow()))
      {
        base_count++;
      }
      if (!StringUtil.isEmpty(view_usr_shipping))
      {
        base_count++;
      }
      return Integer.toString(base_count);
    }

    //default
    return "";
  }

  public String getGrid_width()
  {
    int width = 700;

    if (!StringUtil.isEmpty(have_date_to))
    {
      if (!StringUtil.isEmpty(view_sums))
      {
        width += 400;
      }
      else
      {
        width += 150;
      }
    }
    if (!StringUtil.isEmpty(view_department))
    {
      width += 100;
    }
    if (!StringUtil.isEmpty(view_order_for))
    {
      width += 200;
    }
    if (!StringUtil.isEmpty(view_prc_date))
    {
      width += 100;
    }
    if (!StringUtil.isEmpty(view_prc_number))
    {
      width += 100;
    }
    if (!StringUtil.isEmpty(view_lpc_count))
    {
      width += 50;
    }
    if (!StringUtil.isEmpty(view_usr_shipping))
    {
      width += 100;
    }
    if (!StringUtil.isEmpty(view_debt))
    {
      width += 100;
    }
    if (!StringUtil.isEmpty(view_purpose))
    {
      width += 100;
    }
    if (!StringUtil.isEmpty(view_comment))
    {
      width += 200;
    }
    if (!StringUtil.isEmpty(view_sums))
    {
      width += 250;
    }

    return "width:" + Integer.toString(width) + "px";
  }

  public String getCommon1()
  {
    return common1;
  }

  public void setCommon1(String common1)
  {
    this.common1 = common1;
  }

  public String getCommon2()
  {
    return common2;
  }

  public void setCommon2(String common2)
  {
    this.common2 = common2;
  }

  public String getSelect_list()
  {
    return select_list;
  }

  public void setSelect_list(String select_list)
  {
    this.select_list = select_list;
  }

  public String getGroup_by()
  {
    return group_by;
  }

  public void setGroup_by(String group_by)
  {
    this.group_by = group_by;
  }

  public String getOrder_by()
  {
    return order_by;
  }

  public void setOrder_by(String order_by)
  {
    this.order_by = order_by;
  }

  public String getLpc_id()
  {
    return lpc_id;
  }

  public void setLpc_id(String lpc_id)
  {
    this.lpc_id = lpc_id;
  }

  public String getLpc_comment()
  {
    return lpc_comment;
  }

  public void setLpc_comment(String lpc_comment)
  {
    this.lpc_comment = lpc_comment;
  }

  public String getImageId()
  {
    return imageId;
  }

  public void setImageId(String imageId)
  {
    this.imageId = imageId;
  }

  public String getDepartmentId()
  {
    return departmentId;
  }

  public void setDepartmentId(String departmentId)
  {
    this.departmentId = departmentId;
  }

  public String getDepartmentName()
  {
    return departmentName;
  }

  public void setDepartmentName(String departmentName)
  {
    this.departmentName = departmentName;
  }

  public boolean isCanEditDepartment()
  {
    return canEditDepartment;
  }

  public void setCanEditDepartment(boolean canEditDepartment)
  {
    this.canEditDepartment = canEditDepartment;
  }
}
