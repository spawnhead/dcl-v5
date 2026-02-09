package net.sam.dcl.navigation;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class ControlComment implements Serializable
{
  String fcm_id;
  String fcm_key;
  String fcm_value;

  public ControlComment()
  {
  }

  public ControlComment(String fcm_key)
  {
    this.fcm_key = fcm_key;
  }

  public ControlComment(String fcm_id, String fcm_key, String fcm_value)
  {
    this.fcm_id = fcm_id;
    this.fcm_key = fcm_key;
    this.fcm_value = fcm_value;
  }

  public String getFcm_id()
  {
    return fcm_id;
  }

  public void setFcm_id(String fcm_id)
  {
    this.fcm_id = fcm_id;
  }

  public String getFcm_key()
  {
    return fcm_key;
  }

  public void setFcm_key(String fcm_key)
  {
    this.fcm_key = fcm_key;
  }

  public String getFcm_value()
  {
    return fcm_value;
  }

  public void setFcm_value(String fcm_value)
  {
    this.fcm_value = fcm_value;
  }
}