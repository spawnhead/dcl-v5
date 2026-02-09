package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.dbo.DboProduce;

public class DeliveryRequest implements Serializable
{
  protected static Log log = LogFactory.getLog(DeliveryRequest.class);
  String is_new_doc;

  String dlr_id;
  String drp_id;

  User createUser = new User();
  User editUser = new User();
  User placeUser = new User();
  String usr_date_create;
  String usr_date_edit;
  String usr_date_place;

  String dlr_number;
  String dlr_date;
  String dlr_fair_trade;
  String dlr_need_deliver;
  String dlr_ord_not_form;
  String dlr_guarantee_repair;
  String dlr_include_in_spec;
  String dlr_wherefrom;
  String dlr_minsk;
  String dlr_comment;
  String dlr_place_request;
  String dlr_place_request_form;
  String dlr_annul;
  String dlr_executed;
  double count_rest_drp;

  boolean numDateOrdShowForFairTrade;

  List<DeliveryRequestProduce> produces = new ArrayList<DeliveryRequestProduce>();

  String printScale = "100";
  
  public DeliveryRequest()
  {
  }

  public DeliveryRequest(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public DeliveryRequest(String dlr_id, String drp_id)
  {
    this.dlr_id = dlr_id;
    this.drp_id = drp_id;
  }

  public String getDrp_id()
  {
    return drp_id;
  }

  public void setDrp_id(String drp_id)
  {
    this.drp_id = drp_id;
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

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getDlr_id()
  {
    return dlr_id;
  }

  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
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

  public String getDlr_date_ts()
  {
    return StringUtil.appDateString2dbDateString(dlr_date);
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

  public String getDlr_minsk()
  {
    return dlr_minsk;
  }

  public void setDlr_minsk(String dlr_minsk)
  {
    this.dlr_minsk = dlr_minsk;
  }

  public String getDlr_comment()
  {
    return dlr_comment;
  }

  public void setDlr_comment(String dlr_comment)
  {
    this.dlr_comment = dlr_comment;
  }

  public String getDlr_place_request()
  {
    return dlr_place_request;
  }

  public void setDlr_place_request(String dlr_place_request)
  {
    this.dlr_place_request = dlr_place_request;
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

  public double getCount_rest_drp()
  {
    return count_rest_drp;
  }

  public void setCount_rest_drp(double count_rest_drp)
  {
    this.count_rest_drp = count_rest_drp;
  }

  public String getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(String printScale)
  {
    this.printScale = printScale;
  }

  public boolean isNumDateOrdShowForFairTrade()
  {
    return numDateOrdShowForFairTrade;
  }

  public void setNumDateOrdShowForFairTrade(boolean numDateOrdShowForFairTrade)
  {
    this.numDateOrdShowForFairTrade = numDateOrdShowForFairTrade;
  }

  public List<DeliveryRequestProduce> getProduces()
  {
    return produces;
  }

  public void setProduces(List<DeliveryRequestProduce> produces)
  {
    this.produces = produces;
  }

  public void calculateInString()
  {
    for (int i = 0; i < produces.size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = produces.get(i);
      deliveryRequestProduce.setNumber(Integer.toString(i));
    }
  }

  public static DeliveryRequestProduce getEmptyProduce()
  {
    DeliveryRequestProduce deliveryRequestProduce = new DeliveryRequestProduce();
    deliveryRequestProduce.setDrp_id("");
    deliveryRequestProduce.setDlr_id("");
    deliveryRequestProduce.setNumber("");
    deliveryRequestProduce.setProduce(new DboProduce());
    deliveryRequestProduce.setDrp_count(0.0);
    deliveryRequestProduce.setDrp_price(0.0);
    deliveryRequestProduce.setStuffCategory(new StuffCategory());
    deliveryRequestProduce.setOrd_number("");
    deliveryRequestProduce.setOrd_date("");
    deliveryRequestProduce.setCustomer(new Contractor());
    deliveryRequestProduce.setPurpose(new Purpose());
    deliveryRequestProduce.setDrp_purpose("");
    deliveryRequestProduce.setDrp_occupied("");

    return deliveryRequestProduce;
  }

  public void calculate()
  {
    calculateInString();
  }

  public void setListParentIds()
  {
    for (DeliveryRequestProduce deliveryRequestProduce : produces)
    {
      deliveryRequestProduce.setDlr_id(getDlr_id());
    }
  }

  public void clear()
  {
    for (int i = 0; i < produces.size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = produces.get(i);

      if ( StringUtil.isEmpty(deliveryRequestProduce.getDrp_occupied()) )
      {
        produces.remove(i);
        i--;
      }
    }
  }

  public void setListIdsToNull()
  {
    for (DeliveryRequestProduce deliveryRequestProduce : produces)
    {
      deliveryRequestProduce.setDrp_id(null);
    }
  }

  public DeliveryRequestProduce findProduce(String number)
  {
    for (DeliveryRequestProduce deliveryRequestProduce : produces)
    {
      if (deliveryRequestProduce.getNumber().equalsIgnoreCase(number))
        return deliveryRequestProduce;
    }

    return null;
  }

  public void updateProduce(String number, DeliveryRequestProduce deliveryRequestProduceIn)
  {
    for (int i = 0; i < produces.size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = produces.get(i);

      if (deliveryRequestProduce.getNumber().equalsIgnoreCase(number))
      {
        produces.set(i, deliveryRequestProduceIn);
        return;
      }
    }
  }

  public void deleteProduce(String number)
  {
		for (int i = 0; i < produces.size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = produces.get(i);

      if (deliveryRequestProduce.getNumber().equalsIgnoreCase(number))
      {
        produces.remove(i);
				break;
			}
		}
	}

  public void insertProduce(DeliveryRequestProduce deliveryRequestProduce)
  {
		produces.add(produces.size(), deliveryRequestProduce);
  }
}
