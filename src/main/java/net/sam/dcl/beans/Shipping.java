package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;
import java.lang.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Shipping implements Serializable
{
  String shp_id;
  String id;

  User createUser = new User();
  User editUser = new User();
  User manager = new User();
  String shp_create_date;
  String shp_edit_date;

  String shp_number;
  String shp_date;
  String shp_date_expiration;
  Contractor contractor = new Contractor();
  Specification specification = new Specification();
  Currency currency = new Currency();
  String shp_letter1_date;
  String shp_letter2_date;
  String shp_letter3_date;
  String shp_complaint_in_court_date;
  String shp_comment;
  String shp_montage;
  String shp_serial_num_year_out;
  String shp_closed;
  Contractor contractorWhere = new Contractor();
  Contract contractWhere = new Contract();
  String shp_notice_date;
  String shp_original;

  double shp_summ_plus_nds;
  double shp_summ_transport;
  double shp_sum_update;
  String shp_block;
  double shp_produce_count;

  boolean clear_check;

  List<ShippingPosition> shippingPositions = new ArrayList<ShippingPosition>();
  List managers = new ArrayList();
  List products = new ArrayList();

  String deletedPositionIds = "";
  String deletedPositionOccupiedIds = "";

  boolean processed;

  String noticeScale = "100";

  public Shipping()
  {
  }

  public Shipping(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public Shipping(Shipping shipping)
  {
    this.shp_id = shipping.getShp_id();
    this.id = shipping.getId();
    this.createUser = shipping.getCreateUser();
    this.editUser = shipping.getEditUser();
    this.manager = shipping.getManager();
    this.shp_create_date = shipping.getShp_create_date();
    this.shp_edit_date = shipping.getShp_edit_date();

    this.shp_number = shipping.getShp_number();
    this.shp_date = shipping.getShp_date();
    this.shp_date_expiration = shipping.getShp_date_expiration();
    this.contractor = shipping.getContractor();
    this.specification = shipping.getSpecification();
    this.currency = shipping.getCurrency();
    this.shp_letter1_date = shipping.getShp_letter1_date();
    this.shp_letter2_date = shipping.getShp_letter2_date();
    this.shp_letter3_date = shipping.getShp_letter3_date();
    this.shp_complaint_in_court_date = shipping.getShp_complaint_in_court_date();
    this.shp_comment = shipping.getShp_comment();
    this.shp_montage = shipping.getShp_montage();
    this.shp_serial_num_year_out = shipping.getShp_serial_num_year_out();
    this.shp_closed = shipping.getShp_closed();
    this.contractorWhere = shipping.getContractorWhere();
    this.contractWhere = shipping.getContractWhere();
    this.shp_notice_date = shipping.getShp_notice_date();
    this.shp_original = shipping.getShp_original();
    this.shp_summ_plus_nds = shipping.getShp_summ_plus_nds();
    this.shp_summ_transport = shipping.getShp_summ_transport();
    this.shp_sum_update = shipping.getShp_sum_update();
    this.shp_block = shipping.getShp_block();
    this.shp_produce_count = shipping.getShp_produce_count();
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

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public String getShp_create_date()
  {
    return shp_create_date;
  }

  public void setShp_create_date(String shp_create_date)
  {
    this.shp_create_date = shp_create_date;
  }

  public String getShp_edit_date()
  {
    return shp_edit_date;
  }

  public String getShp_edit_date_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_edit_date);
  }

  public void setShp_edit_date(String shp_edit_date)
  {
    this.shp_edit_date = shp_edit_date;
  }

  public String getShp_number()
  {
    return shp_number;
  }

  public void setShp_number(String shp_number)
  {
    this.shp_number = shp_number;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public Date getShpDate() throws ParseException
  {
    return StringUtil.appDateString2Date(getShp_date());
  }

  public String getShp_date_ts()
  {
    return StringUtil.appDateString2dbDateString(getShp_date());
  }

  public void setShp_date_ts(String dt)
  {
    shp_date = StringUtil.dbDateString2appDateString(dt);
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public String getShp_date_expiration()
  {
    return shp_date_expiration;
  }

  public String getShp_date_expiration_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_date_expiration);
  }

  public void setShp_date_expiration_ts(String dt)
  {
    shp_date_expiration = StringUtil.dbDateString2appDateString(dt);
  }

  public void setShp_date_expiration(String shp_date_expiration)
  {
    this.shp_date_expiration = shp_date_expiration;
  }

  public double getShp_summ_plus_nds()
  {
    return shp_summ_plus_nds;
  }

  public void setShp_summ_plus_nds(double shp_summ_plus_nds)
  {
    this.shp_summ_plus_nds = shp_summ_plus_nds;
  }

  public double getShp_summ_transport()
  {
    return shp_summ_transport;
  }

  public void setShp_summ_transport(double shp_summ_transport)
  {
    this.shp_summ_transport = shp_summ_transport;
  }

  public double getShp_sum_update()
  {
    return shp_sum_update;
  }

  public void setShp_sum_update(double shp_sum_update)
  {
    this.shp_sum_update = shp_sum_update;
  }

  public String getShp_block()
  {
    return shp_block;
  }

  public void setShp_block(String shp_block)
  {
    this.shp_block = shp_block;
  }

  public double getShp_produce_count()
  {
    return shp_produce_count;
  }

  public void setShp_produce_count(double shp_produce_count)
  {
    this.shp_produce_count = shp_produce_count;
  }

  public boolean getClear_check()
  {
    return clear_check;
  }

  public void setClear_check(boolean clear_check)
  {
    this.clear_check = clear_check;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Specification getSpecification()
  {
    return specification;
  }

  public void setSpecification(Specification specification)
  {
    this.specification = specification;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public String getShp_letter1_date()
  {
    return shp_letter1_date;
  }

  public String getShp_letter1_date_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_letter1_date);
  }

  public void setShp_letter1_date(String shp_letter1_date)
  {
    this.shp_letter1_date = shp_letter1_date;
  }

  public String getShp_letter2_date()
  {
    return shp_letter2_date;
  }

  public String getShp_letter2_date_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_letter2_date);
  }

  public void setShp_letter2_date(String shp_letter2_date)
  {
    this.shp_letter2_date = shp_letter2_date;
  }

  public String getShp_letter3_date()
  {
    return shp_letter3_date;
  }

  public String getShp_letter3_date_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_letter3_date);
  }

  public void setShp_letter3_date(String shp_letter3_date)
  {
    this.shp_letter3_date = shp_letter3_date;
  }

  public String getShp_complaint_in_court_date()
  {
    return shp_complaint_in_court_date;
  }

  public String getShp_complaint_in_court_date_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_complaint_in_court_date);
  }

  public void setShp_complaint_in_court_date(String shp_complaint_in_court_date)
  {
    this.shp_complaint_in_court_date = shp_complaint_in_court_date;
  }

  public String getShp_comment()
  {
    return shp_comment;
  }

  public void setShp_comment(String shp_comment)
  {
    this.shp_comment = shp_comment;
  }

  public String getShp_montage()
  {
    return shp_montage;
  }

  public void setShp_montage(String shp_montage)
  {
    this.shp_montage = shp_montage;
  }

  public String getShp_serial_num_year_out()
  {
    return shp_serial_num_year_out;
  }

  public void setShp_serial_num_year_out(String shp_serial_num_year_out)
  {
    this.shp_serial_num_year_out = shp_serial_num_year_out;
  }

  public String getShp_closed()
  {
    return shp_closed;
  }

  public void setShp_closed(String shp_closed)
  {
    this.shp_closed = shp_closed;
  }

  public Contractor getContractorWhere()
  {
    return contractorWhere;
  }

  public void setContractorWhere(Contractor contractorWhere)
  {
    this.contractorWhere = contractorWhere;
  }

  public Contract getContractWhere()
  {
    return contractWhere;
  }

  public void setContractWhere(Contract contractWhere)
  {
    this.contractWhere = contractWhere;
  }

  public String getShp_notice_date()
  {
    return shp_notice_date;
  }

  public String getShp_notice_date_ts()
  {
    return StringUtil.appDateString2dbDateString(shp_notice_date);
  }

  public void setShp_notice_date(String shp_notice_date)
  {
    this.shp_notice_date = shp_notice_date;
  }

  public String getShp_original()
  {
    return shp_original;
  }

  public void setShp_original(String shp_original)
  {
    this.shp_original = shp_original;
  }

  public boolean isOriginal()
  {
    return !StringUtil.isEmpty(getShp_original());
  }

  public List<ShippingPosition> getShippingPositions()
  {
    return shippingPositions;
  }

  public void setShippingPositions(List<ShippingPosition> shippingPositions)
  {
    this.shippingPositions = shippingPositions;
  }

  public List getManagers()
  {
    return managers;
  }

  public void setManagers(List managers)
  {
    this.managers = managers;
  }

  public List getProducts()
  {
    return products;
  }

  public void setProducts(List products)
  {
    this.products = products;
  }

  public String getDeletedPositionIds()
  {
    return deletedPositionIds;
  }

  public void setDeletedPositionIds(String deletedPositionIds)
  {
    this.deletedPositionIds = deletedPositionIds;
  }

  public String getDeletedPositionOccupiedIds()
  {
    return deletedPositionOccupiedIds;
  }

  public void setDeletedPositionOccupiedIds(String deletedPositionOccupiedIds)
  {
    this.deletedPositionOccupiedIds = deletedPositionOccupiedIds;
  }

  public boolean isProcessed()
  {
    return processed;
  }

  public void setProcessed(boolean processed)
  {
    this.processed = processed;
  }

  public String getNoticeScale()
  {
    return noticeScale;
  }

  public void setNoticeScale(String noticeScale)
  {
    this.noticeScale = noticeScale;
  }
}
