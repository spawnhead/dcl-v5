package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.db.VDbConnection;

import java.util.List;

import org.hibernate.Session;

public class SpecificationImportDAO
{
  public static SpecificationImport load(IActionContext context, String id) throws Exception
  {
    SpecificationImport specificationImport = new SpecificationImport(id);

    if (load(context, specificationImport))
    {
      specificationImport.setUsr_date_create(StringUtil.dbDateString2appDateString(specificationImport.getUsr_date_create()));
      specificationImport.setUsr_date_edit(StringUtil.dbDateString2appDateString(specificationImport.getUsr_date_edit()));

      specificationImport.setSpi_date(StringUtil.dbDateString2appDateString(specificationImport.getSpi_date()));

      loadProduce(context, specificationImport);
      return specificationImport;
    }
    throw new LoadException(specificationImport, id);
  }

  public static boolean load(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    if (DAOUtils.load(context, "specification_import-load", specificationImport, null))
    {
      if (!StringUtil.isEmpty(specificationImport.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, specificationImport.getCreateUser());
      }

      if (!StringUtil.isEmpty(specificationImport.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, specificationImport.getEditUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

public static SpecificationImport loadBySipId(IActionContext context, String id) throws Exception
  {
    SpecificationImport specificationImport = new SpecificationImport(null, id);

    if (loadBySipId(context, specificationImport))
    {
      specificationImport.setUsr_date_create(StringUtil.dbDateString2appDateString(specificationImport.getUsr_date_create()));
      specificationImport.setUsr_date_edit(StringUtil.dbDateString2appDateString(specificationImport.getUsr_date_edit()));

      specificationImport.setSpi_date(StringUtil.dbDateString2appDateString(specificationImport.getSpi_date()));

      return specificationImport;
    }
    throw new LoadException(specificationImport, id);
  }

  public static boolean loadBySipId(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    if (DAOUtils.load(context, "specification_import-load_by_sip_id", specificationImport, null))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadProduce(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if ( currentUser.isManager() )
    {
      specificationImport.setUsr_id(currentUser.getUsr_id());
      if (currentUser.isChiefDepartment())
      {
        specificationImport.setChief("1");
      }
    }
    List<SpecificationImportProduce> lst = DAOUtils.fillList(context, "select-specification_import_produces", specificationImport, SpecificationImportProduce.class, null, null);
    for ( SpecificationImportProduce specificationImportProduce : lst )
    {
      if (null != specificationImportProduce.getProduce().getId())
      {
        specificationImportProduce.setProduce(ProduceDAO.loadProduceWithUnitInOneSession(specificationImportProduce.getProduce().getId(), hibSession));
      }
    }
    hibSession.getTransaction().commit();
    specificationImport.setProduces(lst);
  }

  public static void insert(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    DAOUtils.load(context, "specification_import-insert", specificationImport, null);
    specificationImport.setListParentiIds();
    specificationImport.setListIdsToNull();
    saveProduce(context, specificationImport);
  }

  public static void save(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("specification_import-update"), specificationImport, null);
    specificationImport.setListParentiIds();
    saveProduce(context, specificationImport);
  }

  public static void saveProduce(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_specification_import_produces"), specificationImport, null);
    for (int i = 0; i < specificationImport.getProduces().size() - 1; i++)
    {
      SpecificationImportProduce specificationImportProduce = specificationImport.getProduces().get(i);
      SpecificationImportProduce specificationImportProduceFind = new SpecificationImportProduce();
      try
      {
        if (!StringUtil.isEmpty(specificationImportProduce.getSip_id()))
        {
          specificationImportProduceFind = SpecificationImportProduceDAO.load(context, specificationImportProduce.getSip_id());
        }
      }
      catch (Exception e)
      {
        //produce for produce cost deleted
      }

      if (StringUtil.isEmpty(specificationImportProduceFind.getSip_id()))
      {
        DAOUtils.update(conn, context.getSqlResource().get("insert_specification_import_produce"), specificationImportProduce, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("update_specification_import_produce"), specificationImportProduce, null);
      }
    }
  }

  public static void saveArrive(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("specification_import-update-arrive"), specificationImport, null);
  }
  public static void saveSentDate(IActionContext context, SpecificationImport specificationImport) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("specification_import-update-send_date"), specificationImport, null);
  }
}
