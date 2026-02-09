package net.sam.dcl.dao;

import net.sam.dcl.beans.Reputation;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ReputationDAO
{
  public static Reputation load(IActionContext context, String id) throws Exception
  {
    Reputation reputation = new Reputation(id);
    if (load(context, reputation))
    {
      return reputation;
    }
    throw new LoadException(reputation, id);
  }

  public static boolean load(IActionContext context, Reputation reputation) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-reputation", reputation, null));
  }

  public static Reputation loadDefaultForCtc(IActionContext context) throws Exception
  {
    Reputation reputation = new Reputation();
    if (loadDefaultForCtc(context, reputation))
    {
      return reputation;
    }
    throw new LoadException(reputation, "dao-load-default-reputation");
  }

  public static boolean loadDefaultForCtc(IActionContext context, Reputation reputation) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-default-reputation", reputation, null));
  }

  public static void setDefaultForCtc(IActionContext context, Reputation reputation) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("reputation-update-set_default_for_ctc"), reputation, null);
  }
}
