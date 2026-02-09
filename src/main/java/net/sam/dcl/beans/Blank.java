package net.sam.dcl.beans;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.util.StrutsUtil;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import com.lowagie.text.DocumentException;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class Blank implements Serializable
{
  final static public int topImg = 1;
  final static public int rightImg = 2;
  final static public int bottomImg = 3;
  final static public int leftImg = 4;

  String bln_id;
  Seller seller = new Seller();
  BlankType blankType = new BlankType();
  String bln_name;
  String bln_charset;
  Language language = new Language();
  String bln_preamble;
  String bln_note;
  String bln_usage;

  List<BlankImage> images = new ArrayList<BlankImage>();

  public Blank()
  {
  }

  public Blank(String bln_id)
  {
    this.bln_id = bln_id;
  }

  public Blank(Blank blank)
  {
    this.bln_id = blank.getBln_id();
    this.blankType = new BlankType(blank.getBlankType());
    this.seller = new Seller(blank.getSeller());
    this.bln_name = blank.getBln_name();
    this.bln_charset = blank.getBln_charset();
    this.language = new Language(blank.getLanguage());
    this.bln_preamble = blank.getBln_preamble();
    this.bln_note = blank.getBln_note();
    this.bln_usage = blank.getBln_usage();

    this.images = blank.getImages();
  }

  public String getBln_id()
  {
    return bln_id;
  }

  public void setBln_id(String bln_id)
  {
    this.bln_id = bln_id;
  }

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
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

  public List<BlankImage> getImages()
  {
    return images;
  }

  public void setImages(List<BlankImage> images)
  {
    this.images = images;
  }

  public void setListParentIds()
  {
    for (BlankImage blankImage : images)
    {
      blankImage.setBln_id(getBln_id());
    }
  }

  public void setListIdsToNull()
  {
    for (BlankImage blankImage : images)
    {
      blankImage.setBim_id(null);
    }
  }

  public String getImageNameByIdx(int imgNameIdx)
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      switch (imgNameIdx)
      {
        case topImg:
        {
          return StrutsUtil.getMessage(context, "Blank.img_top");
        }
        case rightImg:
        {
          return StrutsUtil.getMessage(context, "Blank.img_right");
        }
        case bottomImg:
        {
          return StrutsUtil.getMessage(context, "Blank.img_bottom");
        }
        case leftImg:
        {
          return StrutsUtil.getMessage(context, "Blank.img_left");
        }
      }
    }
    catch (Exception e)
    {
      return "";
    }

    return "";
  }

  public String getImage(int imgNameIdx) throws DocumentException
  {
    String imgName = getImageNameByIdx(imgNameIdx);

    String retImage = "";
    for (BlankImage blankImage : images)
    {
      if ( imgName.equals(blankImage.getBim_name()) )
      {
        return blankImage.getBim_image();
      }
    }

    if ( "".equals(retImage) )
    {
      throw new DocumentException("Не могу найти картинку по имени " + imgName);
    }

    return retImage;
  }
}