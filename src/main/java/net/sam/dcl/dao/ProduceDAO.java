package net.sam.dcl.dao;

import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;
import org.hibernate.Session;
import org.hibernate.Hibernate;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ProduceDAO
{
  public static DboProduce loadProduce(Integer id)
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    DboProduce produce = (DboProduce) hibSession.get(DboProduce.class, id);
    hibSession.getTransaction().commit();
    return produce;
  }

  public static DboProduce loadProduceWithUnit(Integer id)
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    DboProduce produce = (DboProduce) hibSession.get(DboProduce.class, id);
    Hibernate.initialize(produce.getUnit());
    hibSession.getTransaction().commit();
    return produce;
  }

  public static DboProduce loadProduceFull(Integer id)
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    DboProduce produce = (DboProduce) hibSession.get(DboProduce.class, id);
    Hibernate.initialize(produce.getUnit());
    Hibernate.initialize(produce.getCatalogNumbers());
    hibSession.getTransaction().commit();
    return produce;
  }

  public static DboProduce loadProduceWithUnitInOneSession(Integer id, Session hibSession)
  {
    DboProduce produce = (DboProduce) hibSession.get(DboProduce.class, id);
    Hibernate.initialize(produce.getUnit());
    return produce;
  }

  public static boolean GetCanEditCustomCodeForSpcImport(IActionContext context, Integer id) throws Exception
  {
    ProduceInSpcImport produceInSpcImport = new ProduceInSpcImport(id);
    DAOUtils.load(context, "load-produce_in_spc_import", produceInSpcImport, null);
    return produceInSpcImport.getSpc_import_count_one() == 1;
  }

  public static class ProduceInSpcImport
  {
    Integer prd_id;
    Integer spc_import_count_one;

    private ProduceInSpcImport(Integer prd_id)
    {
      this.prd_id = prd_id;
    }

    public Integer getPrd_id()
    {
      return prd_id;
    }

    public void setPrd_id(Integer prd_id)
    {
      this.prd_id = prd_id;
    }

    public Integer getSpc_import_count_one()
    {
      return spc_import_count_one;
    }

    public void setSpc_import_count_one(Integer spc_import_count_one)
    {
      this.spc_import_count_one = spc_import_count_one;
    }
  }
}
