package net.sam.dcl.dao;

import net.sam.dcl.beans.SpecificationImportProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class SpecificationImportProduceDAO
{
  public static SpecificationImportProduce load(IActionContext context, String id) throws Exception
  {
    SpecificationImportProduce specificationImportProduce = new SpecificationImportProduce(id);

    if (load(context, specificationImportProduce))
    {
      return specificationImportProduce;
    }
    throw new LoadException(specificationImportProduce, id);
  }

  public static boolean load(IActionContext context, SpecificationImportProduce specificationImportProduce) throws Exception
  {
    if (DAOUtils.load(context, "specification_import_produce-load", specificationImportProduce, null))
    {
      if (null != specificationImportProduce.getProduce().getId())
      {
        specificationImportProduce.setProduce(ProduceDAO.loadProduceWithUnit(specificationImportProduce.getProduce().getId()));
      }
      return true;
    }
    else
    {
      return false;
    }
  }
}
