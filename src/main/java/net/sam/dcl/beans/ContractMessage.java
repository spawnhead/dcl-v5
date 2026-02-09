package net.sam.dcl.beans;

import java.io.Serializable;

public class ContractMessage implements Serializable
{
  String con_id;
  String spc_id;
  String ctr_id;
  String msg;

  public ContractMessage()
  {
  }

  public String getCon_id()
  {
    return con_id;
  }

  public void setCon_id(String con_id)
  {
    this.con_id = con_id;
  }

  public String getSpc_id()
  {
    return spc_id;
  }

  public void setSpc_id(String spc_id)
  {
    this.spc_id = spc_id;
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