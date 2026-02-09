package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class BlankImage implements Serializable
{
  String number;
  String bim_id;
  String bln_id;
  String bim_name;
  String bim_image;

  public BlankImage()
  {
  }

  public BlankImage(String bim_name)
  {
    this.bim_name = bim_name;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getBim_id()
  {
    return bim_id;
  }

  public void setBim_id(String bim_id)
  {
    this.bim_id = bim_id;
  }

  public String getBln_id()
  {
    return bln_id;
  }

  public void setBln_id(String bln_id)
  {
    this.bln_id = bln_id;
  }

  public String getBim_name()
  {
    return bim_name;
  }

  public void setBim_name(String bim_name)
  {
    this.bim_name = bim_name;
  }

  public String getBim_image()
  {
    return bim_image;
  }

  public void setBim_image(String bim_image)
  {
    this.bim_image = bim_image;
  }
}