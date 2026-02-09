package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Message implements Serializable
{
  final static public String informationType = "informationType";
  final static public String paymentType = "paymentType";
  final static public String contractType = "contractType";
  final static public String conditionForContractType = "conditionForContractType"; 
  final static public String orderType = "orderType"; 

  String type;
  String id;
  String date;
  String message;
  String sum;
  String shortInfo;
  Contractor contractor = new Contractor();
  String comment;
	User user = new User();

  public Message()
  {
  }

  public Message(String Id)
  {
    this.id = Id;
  }

  public Message(String message, User user)
  {
    this.message = message;
    this.user = user;
  }

  public Message(Message message)
  {
    this.type = message.getType();
    this.id = message.getId();
    this.date = message.getDate();
    this.message = message.getMessage();  
    this.sum = message.getSum();
    this.shortInfo = message.getShortInfo();
    this.contractor = new Contractor(message.getContractor());
    this.comment = message.getComment();  
    this.user = new User(message.getUser());
  }

  public String getUniqueId()
  {
    return getId() + "_" + getType();
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDate()
  {
    return date;
  }

  public String getDateFormatted()
  {
    return StringUtil.dbDateTimeString2appDateTimeString(getDate());
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public String getSum()
  {
    return sum;
  }

  public void setSum(String sum)
  {
    this.sum = sum;
  }

  public String getShortInfo()
  {
    return shortInfo;
  }

  public void setShortInfo(String shortInfo)
  {
    this.shortInfo = shortInfo;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}