package net.sam.dcl.dao;

import net.sam.dcl.beans.AssembleProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class AssembleProduceDAO
{
  public static AssembleProduce load(IActionContext context, String id) throws Exception
  {
    AssembleProduce assembleProduce = new AssembleProduce(id);

    if (load(context, assembleProduce))
    {
      return assembleProduce;
    }
    throw new LoadException(assembleProduce, id);
  }

  public static boolean load(IActionContext context, AssembleProduce assembleProduce) throws Exception
  {
    if (DAOUtils.load(context, "assemble_produce-load", assembleProduce, null))
    {
      if ( null != assembleProduce.getProduce().getId() )
      {
        assembleProduce.setProduce(ProduceDAO.loadProduceWithUnit(assembleProduce.getProduce().getId()));
      }

      return true;
    }
    else
    {
      return false;
    }
  }
}
