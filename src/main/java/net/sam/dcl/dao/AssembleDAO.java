package net.sam.dcl.dao;

import net.sam.dcl.beans.Assemble;
import net.sam.dcl.beans.AssembleProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.db.VDbConnection;

import java.util.List;

import org.hibernate.Session;

public class AssembleDAO
{
  public static Assemble load(IActionContext context, String id) throws Exception
  {
    Assemble assemble = new Assemble(id);

    if (load(context, assemble))
    {
      assemble.setUsr_date_create(StringUtil.dbDateString2appDateString(assemble.getUsr_date_create()));
      assemble.setUsr_date_edit(StringUtil.dbDateString2appDateString(assemble.getUsr_date_edit()));

      assemble.setAsm_date(StringUtil.dbDateString2appDateString(assemble.getAsm_date()));

      loadProduce(context, assemble);
      return assemble;
    }
    throw new LoadException(assemble, id);
  }

  public static boolean load(IActionContext context, Assemble assemble) throws Exception
  {
    if (DAOUtils.load(context, "assemble-load", assemble, null))
    {
      if (!StringUtil.isEmpty(assemble.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, assemble.getCreateUser());
      }

      if (!StringUtil.isEmpty(assemble.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, assemble.getEditUser());
      }

      if (null != assemble.getProduce().getId())
      {
        assemble.setProduce(ProduceDAO.loadProduceWithUnit(assemble.getProduce().getId()));
      }

      if (!StringUtil.isEmpty(assemble.getStuffCategory().getId()))
      {
        StuffCategoryDAO.load(context, assemble.getStuffCategory());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static Assemble loadByAprId(IActionContext context, String id) throws Exception
  {
    Assemble assemble = new Assemble(null, id);

    if (loadByAprId(context, assemble))
    {
      assemble.setUsr_date_create(StringUtil.dbDateString2appDateString(assemble.getUsr_date_create()));
      assemble.setUsr_date_edit(StringUtil.dbDateString2appDateString(assemble.getUsr_date_edit()));

      assemble.setAsm_date(StringUtil.dbDateString2appDateString(assemble.getAsm_date()));

      return assemble;
    }
    throw new LoadException(assemble, id);
  }

  public static boolean loadByAprId(IActionContext context, Assemble assemble) throws Exception
  {
    if (DAOUtils.load(context, "assemble-load_by_apr_id", assemble, null))
    {
      if (!StringUtil.isEmpty(assemble.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, assemble.getCreateUser());
      }

      if (!StringUtil.isEmpty(assemble.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, assemble.getEditUser());
      }

      if (null != assemble.getProduce().getId())
      {
        assemble.setProduce(ProduceDAO.loadProduceWithUnit(assemble.getProduce().getId()));
      }

      if (!StringUtil.isEmpty(assemble.getStuffCategory().getId()))
      {
        StuffCategoryDAO.load(context, assemble.getStuffCategory());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadProduce(IActionContext context, Assemble assemble) throws Exception
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    List<AssembleProduce> lst = DAOUtils.fillList(context, "select-assemble_produces", assemble, AssembleProduce.class, null, null);
    for ( AssembleProduce assembleProduce : lst )
    {
      if ( null != assembleProduce.getProduce().getId() )
      {
        assembleProduce.setProduce(ProduceDAO.loadProduceWithUnitInOneSession(assembleProduce.getProduce().getId(), hibSession));
      }
    }
    hibSession.getTransaction().commit();
    assemble.setProduces(lst);
  }

  public static void insert(IActionContext context, Assemble assemble) throws Exception
  {
    DAOUtils.load(context, "assemble-insert", assemble, null);
    assemble.setListParentIds();
    assemble.setListIdsToNull();
    saveProduce(context, assemble);
  }

  public static void save(IActionContext context, Assemble assemble) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("assemble-update"), assemble, null);
    assemble.setListParentIds();
    saveProduce(context, assemble);
  }

  public static void saveProduce(IActionContext context, Assemble assemble) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_assemble_produces"), assemble, null);
    for (int i = 0; i < assemble.getProduces().size(); i++)
    {
      AssembleProduce assembleProduce = assemble.getProduces().get(i);
      AssembleProduce assembleProduceFind = new AssembleProduce();
      try
      {
        if ( !StringUtil.isEmpty(assembleProduce.getApr_id()) )
        {
          assembleProduceFind = AssembleProduceDAO.load(context, assembleProduce.getApr_id());
        }
      }
      catch (Exception e)
      {
        //delivery request produce deleted
      }
      if ( StringUtil.isEmpty(assembleProduceFind.getApr_id()) )
      {
        DAOUtils.update(conn, context.getSqlResource().get("insert_assemble_produce"), assembleProduce, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("update_assemble_produce"), assembleProduce, null);
      }
    }
  }
}
