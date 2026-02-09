package net.sam.dcl.dao;

import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.MontageAdjustment;
import net.sam.dcl.beans.ForUpdateDependedDocuments;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class StuffCategoryDAO
{

  public static StuffCategory load(IActionContext context, String id) throws Exception
  {
    StuffCategory stuffCategory = new StuffCategory(id);
    if (load(context, stuffCategory))
    {
      return stuffCategory;
    }
    throw new LoadException(stuffCategory, id);
  }

  public static boolean load(IActionContext context, StuffCategory stuffCategory) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-stuff_category", stuffCategory, null));
  }

  public static boolean loadByName(IActionContext context, StuffCategory stuffCategory) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-stuff_category_by_name", stuffCategory, null));
  }

  public static void updateDependedDocs(IActionContext context, ForUpdateDependedDocuments updateDependedDocuments) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete_duplicates_for_ctn_before_update_stf_deps"), updateDependedDocuments, null);
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("stuff_category-update_depended_docs"), updateDependedDocuments, null);
  }

  public static void saveShowInMontage(IActionContext context, StuffCategory stuffCategory) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("stuff_category-update_show_in_montage"), stuffCategory, null);
  }

  public static List<MontageAdjustment> loadMontageAdjustments(IActionContext context, StuffCategory stuffCategory) throws Exception
  {
    return DAOUtils.fillList(context, "select-montage_adjustments-dao", stuffCategory, MontageAdjustment.class, null, null);
  }
}
