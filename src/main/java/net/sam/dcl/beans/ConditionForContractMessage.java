package net.sam.dcl.beans;

import java.io.Serializable;

public class ConditionForContractMessage implements Serializable
{
  String cfc_id;
  String ctr_id;
  String msg;

  public ConditionForContractMessage()
  {
  }

  public String getCfc_id()
  {
    return cfc_id;
  }

  public void setCfc_id(String cfc_id)
  {
    this.cfc_id = cfc_id;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getMsg()
  {
    return msg;
  }

  public void setMsg(String msg)
  {
    this.msg = msg;
  }
}