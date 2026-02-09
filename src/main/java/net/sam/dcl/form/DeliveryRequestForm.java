package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.DeliveryRequestProduce;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class DeliveryRequestForm extends BaseDispatchValidatorForm
{
  String direction;
  String fakeOne = "1";
  String print;
  boolean needPrint;

  String is_new_doc;
  String number;
  String gen_num;

  String dlr_id;
  String target;

  User createUser = new User();
  User editUser = new User();
  User placeUser = new User();
  String usr_date_create;
  String usr_date_edit;
  String usr_date_place;

  String usr_code;

  String dlr_number;
  String dlr_date;
  String dlr_fair_trade;
  String dlr_need_deliver;
  String dlr_ord_not_form;
  String dlr_guarantee_repair;
  String dlr_include_in_spec;
  String dlr_wherefrom;
  String dlr_comment;
  String dlr_place_request_save;
  String dlr_place_request_form;
  String dlr_annul;
  String dlr_executed;
  String dlr_executed_old;
  double count_rest_drp;

  boolean formReadOnly = false;
  boolean annulReadOnly = false;
  boolean placeRequestReadOnly = false;
  boolean executedReadOnly = false;

  HolderImplUsingList gridResult = new HolderImplUsingList();
	private double produceCount = Double.NaN;

  String printScale = "100";

	public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( !isFormReadOnly() && !isOutDoc() )
    {
      setDlr_fair_trade("");
    }
    if ( !isExecutedReadOnly() && !isInDoc() )
    {
      setDlr_executed("");
    }
    if ( !isFormReadOnly() )
    {
      setDlr_need_deliver("");
      setDlr_ord_not_form("");
      setDlr_guarantee_repair("");
      setDlr_include_in_spec("");
      setDlr_place_request_form("");
    }
    if ( !isAnnulReadOnly() )
    {
      setDlr_annul("");
    }
    if ( !isPlaceRequestReadOnly() )
    {
      setDlr_place_request_form("");
    }
		clearProduceCount();
		super.reset(mapping, request);
  }

  public String getDirection()
  {
    return direction;
  }

  public void setDirection(String direction)
  {
    this.direction = direction;
  }

  public String getFakeOne()
  {
    return fakeOne;
  }

  public void setFakeOne(String fakeOne)
  {
    this.fakeOne = fakeOne;
  }

  public boolean isInDoc()
  {
    return DeliveryRequestsForm.inDirection.equals(direction);
  }

  public boolean isOutDoc()
  {
    return DeliveryRequestsForm.outDirection.equals(direction);
  }

  public String getPrint()
  {
    return print;
  }

  public void setPrint(String print)
  {
    this.print = print;
  }

  public boolean isNeedPrint()
  {
    return needPrint;
  }

  public void setNeedPrint(boolean needPrint)
  {
    this.needPrint = needPrint;
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getGen_num()
  {
    return gen_num;
  }

  public void setGen_num(String gen_num)
  {
    this.gen_num = gen_num;
  }

  public String getDlr_id()
  {
    return dlr_id;
  }

  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String target)
  {
    this.target = target;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public User getPlaceUser()
  {
    return placeUser;
  }

  public void setPlaceUser(User placeUser)
  {
    this.placeUser = placeUser;
  }

  public String getUsr_date_create()
  {
    return usr_date_create;
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public String getUsr_date_place()
  {
    return usr_date_place;
  }

  public void setUsr_date_place(String usr_date_place)
  {
    this.usr_date_place = usr_date_place;
  }

  public String getUsr_code()
  {
    return usr_code;
  }

  public void setUsr_code(String usr_code)
  {
    this.usr_code = usr_code;
  }

  public String getDlr_number()
  {
    return dlr_number;
  }

  public void setDlr_number(String dlr_number)
  {
    this.dlr_number = dlr_number;
  }

  public String getDlr_date()
  {
    return dlr_date;
  }

  public void setDlr_date(String dlr_date)
  {
    this.dlr_date = dlr_date;
  }

  public String getDlr_fair_trade()
  {
    return dlr_fair_trade;
  }

  public void setDlr_fair_trade(String dlr_fair_trade)
  {
    this.dlr_fair_trade = dlr_fair_trade;
  }

  public String getDlr_need_deliver()
  {
    return dlr_need_deliver;
  }

  public void setDlr_need_deliver(String dlr_need_deliver)
  {
    this.dlr_need_deliver = dlr_need_deliver;
  }

  public String getDlr_ord_not_form()
  {
    return dlr_ord_not_form;
  }

  public void setDlr_ord_not_form(String dlr_ord_not_form)
  {
    this.dlr_ord_not_form = dlr_ord_not_form;
  }

  public String getDlr_guarantee_repair()
  {
    return dlr_guarantee_repair;
  }

  public void setDlr_guarantee_repair(String dlr_guarantee_repair)
  {
    this.dlr_guarantee_repair = dlr_guarantee_repair;
  }

  public String getDlr_include_in_spec()
  {
    return dlr_include_in_spec;
  }

  public void setDlr_include_in_spec(String dlr_include_in_spec)
  {
    this.dlr_include_in_spec = dlr_include_in_spec;
  }

  public String getDlr_wherefrom()
  {
    return dlr_wherefrom;
  }

  public void setDlr_wherefrom(String dlr_wherefrom)
  {
    this.dlr_wherefrom = dlr_wherefrom;
  }

  public String getDlr_comment()
  {
    return dlr_comment;
  }

  public void setDlr_comment(String dlr_comment)
  {
    this.dlr_comment = dlr_comment;
  }

  public String getDlr_place_request_save()
  {
    return dlr_place_request_save;
  }

  public void setDlr_place_request_save(String dlr_place_request_save)
  {
    this.dlr_place_request_save = dlr_place_request_save;
  }

  public String getDlr_place_request_form()
  {
    return dlr_place_request_form;
  }

  public void setDlr_place_request_form(String dlr_place_request_form)
  {
    this.dlr_place_request_form = dlr_place_request_form;
  }

  public String getDlr_annul()
  {
    return dlr_annul;
  }

  public void setDlr_annul(String dlr_annul)
  {
    this.dlr_annul = dlr_annul;
  }

  public String getDlr_executed()
  {
    return dlr_executed;
  }

  public void setDlr_executed(String dlr_executed)
  {
    this.dlr_executed = dlr_executed;
  }

  public String getDlr_executed_old()
  {
    return dlr_executed_old;
  }

  public void setDlr_executed_old(String dlr_executed_old)
  {
    this.dlr_executed_old = dlr_executed_old;
  }

  public double getCount_rest_drp()
  {
    return count_rest_drp;
  }

  public void setCount_rest_drp(double count_rest_drp)
  {
    this.count_rest_drp = count_rest_drp;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isAnnulReadOnly()
  {
    return annulReadOnly;
  }

  public void setAnnulReadOnly(boolean annulReadOnly)
  {
    this.annulReadOnly = annulReadOnly;
  }

  public boolean isPlaceRequestReadOnly()
  {
    return placeRequestReadOnly;
  }

  public void setPlaceRequestReadOnly(boolean placeRequestReadOnly)
  {
    this.placeRequestReadOnly = placeRequestReadOnly;
  }

  public boolean isExecutedReadOnly()
  {
    return executedReadOnly;
  }

  public void setExecutedReadOnly(boolean executedReadOnly)
  {
    this.executedReadOnly = executedReadOnly;
  }

  public boolean isSaveReadOnly()
  {
    if (getGridResult().getDataList().size() == 0)
      return true;

    IActionContext context = ActionContext.threadInstance();
    User user = UserUtil.getCurrentUser(context.getRequest());
    if ( (user.isAdmin() || (user.isDeclarant() && getCount_rest_drp() > 0)) && !isFormReadOnly() && !isPlaceRequestReadOnly())
    {
      return false;
    }
    return annulReadOnly & executedReadOnly & formReadOnly & isPlaceRequestReadOnly();
  }

  public boolean isNumDateOrdShowForFairTrade()
  {
    return !StringUtil.isEmpty(dlr_fair_trade) && isInDoc() && StringUtil.isEmpty(dlr_need_deliver) && StringUtil.isEmpty(dlr_ord_not_form);
  }

  public boolean isSpecImportHide()
  {
    return StringUtil.isEmpty(dlr_need_deliver) || StringUtil.isEmpty(dlr_fair_trade) || isOutDoc();
  }

  public boolean isOrderImportHide()
  {
    return ( !StringUtil.isEmpty(dlr_fair_trade) && isInDoc() && !StringUtil.isEmpty(dlr_need_deliver) ) ||
           ( !StringUtil.isEmpty(dlr_fair_trade) && isInDoc() && !StringUtil.isEmpty(dlr_ord_not_form) ) ||
           ( !StringUtil.isEmpty(dlr_fair_trade) && isOutDoc() ) ||
           ( StringUtil.isEmpty(dlr_fair_trade) && !StringUtil.isEmpty(dlr_ord_not_form) );
  }

  public boolean isAddHide()
  {
    return ( !StringUtil.isEmpty(dlr_fair_trade) && isInDoc() && !StringUtil.isEmpty(dlr_need_deliver) ) ||
           ( !StringUtil.isEmpty(dlr_fair_trade) && isInDoc() && StringUtil.isEmpty(dlr_ord_not_form) ) ||
           ( StringUtil.isEmpty(dlr_fair_trade) && StringUtil.isEmpty(dlr_ord_not_form) );
  }

  public boolean isShowAnnul()
  {
    return (isInDoc() && !StringUtil.isEmpty(dlr_place_request_form) && count_rest_drp > 0) || ( isOutDoc() && StringUtil.isEmpty(dlr_executed_old) );
  }

  public boolean isShowSpecification()
  {
    return ( StringUtil.isEmpty(dlr_fair_trade) && ((count_rest_drp == 0) || count_rest_drp < getProduceCount()) ) ||
           ( !StringUtil.isEmpty(dlr_fair_trade) && isInDoc() && !StringUtil.isEmpty(dlr_include_in_spec) && ((count_rest_drp == 0) || count_rest_drp < getProduceCount()) ) ;
  }
	
  public boolean isShowMaxExtra()
  {
    return isInDoc() && ( StringUtil.isEmpty(dlr_fair_trade) || ( !StringUtil.isEmpty(dlr_fair_trade) && !StringUtil.isEmpty(dlr_include_in_spec) ) );
  }

	public double getProduceCount()
  {
		if (Double.isNaN(produceCount))
    {
			produceCount = 0.0;
			for (int i = 0; i < gridResult.getDataList().size(); i++)
      {
				DeliveryRequestProduce deliveryRequestProduce = (DeliveryRequestProduce) gridResult.getDataList().get(i);
				produceCount += deliveryRequestProduce.getDrp_count();
			}
		}
		return produceCount;
	}

  public HolderImplUsingList getGridResult()
  {
    return gridResult;
  }

  public void setGridResult(HolderImplUsingList gridResult)
  {
    this.gridResult = gridResult;
  }

	public void clearProduceCount()
  {
		this.produceCount = Double.NaN;
	}

  public String getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(String printScale)
  {
    this.printScale = printScale;
  }
}
