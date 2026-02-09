package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

import java.util.List;

public class ProduceMovementDAO
{
  public static List loadProduceMovement(IActionContext context, ProduceMovementProduct produceMovementProduct) throws Exception
  {
    return DAOUtils.fillList(context, "select-produce_movement", produceMovementProduct, ProduceMovementProduct.ProduceResultListLine.class, null, null);
  }
}