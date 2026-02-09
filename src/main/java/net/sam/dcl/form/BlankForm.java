package net.sam.dcl.form;

import net.sam.dcl.beans.Seller;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.Language;
import net.sam.dcl.beans.BlankType;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class BlankForm extends BaseDispatchValidatorForm
{
  String bln_id;
  BlankType blankType = new BlankType();
  String bln_name;
	Seller seller = new Seller();
  String bln_charset;
  Language language = new Language();
  String bln_preamble;
  String bln_note;
  String bln_usage;

  HolderImplUsingList gridImages = new HolderImplUsingList();

  public boolean isShowForBlankType()
  {
    return Constants.orderBlankType.equals(getBlankType().getId());
  }

  public String getBln_id()
  {
    return bln_id;
  }

  public void setBln_id(String bln_id)
  {
    this.bln_id = bln_id;
  }

  public BlankType getBlankType()
  {
    return blankType;
  }

  public void setBlankType(BlankType blankType)
  {
    this.blankType = blankType;
  }

  public String getBln_name()
  {
    return bln_name;
  }

  public void setBln_name(String bln_name)
  {
    this.bln_name = bln_name;
  }

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public String getBln_charset()
  {
    return bln_charset;
  }

  public void setBln_charset(String bln_charset)
  {
    this.bln_charset = bln_charset;
  }

  public Language getLanguage()
  {
    return language;
  }

  public void setLanguage(Language language)
  {
    this.language = language;
  }

  public String getBln_preamble()
  {
    return bln_preamble;
  }

  public void setBln_preamble(String bln_preamble)
  {
    this.bln_preamble = bln_preamble;
  }

  public String getBln_note()
  {
    return bln_note;
  }

  public void setBln_note(String bln_note)
  {
    this.bln_note = bln_note;
  }

  public String getBln_usage()
  {
    return bln_usage;
  }

  public void setBln_usage(String bln_usage)
  {
    this.bln_usage = bln_usage;
  }

  public HolderImplUsingList getGridImages()
  {
    return gridImages;
  }

  public void setGridImages(HolderImplUsingList gridImages)
  {
    this.gridImages = gridImages;
  }
}