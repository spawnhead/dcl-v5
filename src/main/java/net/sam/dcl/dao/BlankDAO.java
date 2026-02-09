package net.sam.dcl.dao;

import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.BlankImage;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.controller.IActionContext;

import java.util.List;

public class BlankDAO
{

  public static Blank load(IActionContext context, String id) throws Exception
  {
    Blank blank = new Blank(id);

    if (load(context, blank))
    {
      loadImages(context, blank);

      return blank;
    }
    throw new LoadException(blank, id);
  }

  public static boolean load(IActionContext context, Blank blank) throws Exception
  {
    if (DAOUtils.load(context, "blank-load", blank, null))
    {
      if (!StringUtil.isEmpty(blank.getLanguage().getId()))
      {
        LanguageDAO.load(context, blank.getLanguage());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadImages(IActionContext context, Blank blank) throws Exception
  {
    List<BlankImage> lst = DAOUtils.fillList(context, "select-images", blank, BlankImage.class, null, null);
    blank.setImages(lst);
  }

  public static void insert(IActionContext context, Blank blank) throws Exception
  {
    DAOUtils.load(context, "blank-insert", blank, null);
    blank.setListParentIds();
    blank.setListIdsToNull();
    saveImages(context, blank);
  }

  public static void save(IActionContext context, Blank blank) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("blank-update"), blank, null);
    blank.setListParentIds();
    saveImages(context, blank);
  }

  public static void saveImages(IActionContext context, final Blank blank) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_images"), blank, null);
    for (int i = 0; i < blank.getImages().size(); i++)
    {
      BlankImage blankImage = blank.getImages().get(i);
      DAOUtils.update(conn, context.getSqlResource().get("image-insert"), blankImage, null);
    }
  }
}