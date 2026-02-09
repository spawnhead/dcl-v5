package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.User;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class DeliveryRequestPrintForm extends BaseDispatchValidatorForm
{
  String dlr_number;
  User placeUser = new User();
  User chiefDep = new User();
  String usr_date_place;
  String dlr_wherefrom;
  String dlr_minsk;
  String dlr_comment;
  String dlr_place_request;
  String dlr_include_in_spec;
  String dlr_need_deliver;
  String dlr_ord_not_form;
  boolean numDateOrdShowForFairTrade;

  List produces = new ArrayList();

  float printScale;

  public String getDlr_number()
  {
    return dlr_number;
  }

  public void setDlr_number(String dlr_number)
  {
    this.dlr_number = dlr_number;
  }

  public User getPlaceUser()
  {
    return placeUser;
  }

  public void setPlaceUser(User placeUser)
  {
    this.placeUser = placeUser;
  }

  public User getChiefDep()
  {
    return chiefDep;
  }

  public void setChiefDep(User chiefDep)
  {
    this.chiefDep = chiefDep;
  }

  public String getUsr_date_place()
  {
    return usr_date_place;
  }

  public void setUsr_date_place(String usr_date_place)
  {
    this.usr_date_place = usr_date_place;
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

  public String getDlr_include_in_spec()
  {
    return dlr_include_in_spec;
  }

  public void setDlr_include_in_spec(String dlr_include_in_spec)
  {
    this.dlr_include_in_spec = dlr_include_in_spec;
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

  public boolean isNumDateOrdShowForFairTrade()
  {
    return numDateOrdShowForFairTrade;
  }

  public void setNumDateOrdShowForFairTrade(boolean numDateOrdShowForFairTrade)
  {
    this.numDateOrdShowForFairTrade = numDateOrdShowForFairTrade;
  }

  public List getProduces()
  {
    return produces;
  }

  public void setProduces(List produces)
  {
    this.produces = produces;
  }

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }
}
