package net.sam.dcl.util;

/**
 * @author DG
 *         Date: 28-May-2007
 *         Time: 21:12:08
 */

import org.hibernate.*;
import org.hibernate.connection.DriverManagerConnectionProvider;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.EntityEntry;
import org.hibernate.cfg.*;

import java.io.Serializable;
import java.sql.*;

import net.sam.dcl.config.Config;
import net.sam.dcl.beans.User;

public class HibernateUtil
{
  private static SessionFactory sessionFactory;

  static public void init()
  {
    try
    {
      // Create the SessionFactory from hibernate.cfg.xml
      Configuration configuration = new AnnotationConfiguration().configure();
      configuration.setProperty("hibernate.connection.provider_class", DCLConnectionProvider.class.getName());
      configuration.setProperty("hibernate.connection.url", Config.getString("dbconnect.connection.url"));
      configuration.setProperty("hibernate.connection.driver_class", Config.getString("dbconnect.driver.class.name"));
      configuration.setProperty("hibernate.connection.username", Config.getString("dbconnect.user"));
      configuration.setProperty("hibernate.connection.password", Config.getString("dbconnect.pwd"));
      sessionFactory = configuration.buildSessionFactory();
    }
    catch (Throwable ex)
    {
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory()
  {
    return sessionFactory;
  }

  static public void done()
  {
    if (sessionFactory != null)
      sessionFactory.close();
  }

  static public Object associateWithCurentSession(Object obj)
  {
    Session hibSession = getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    if (!hibSession.contains(obj))
    {
      if (hibSession instanceof SessionImplementor)
      {
        try
        {
          hibSession.lock(obj, LockMode.NONE);
        }
        catch (NonUniqueObjectException e)
        {
          SessionImplementor si = (SessionImplementor) hibSession;
          Object entity = si.getPersistenceContext().unproxyAndReassociate(obj);
          //TODO: if object was an uninitialized proxy, this is inefficient,
          //      resulting in two SQL selects

          EntityEntry entry = si.getPersistenceContext().getEntry(entity);
          Serializable id;
          if (entry == null)
          {
            EntityPersister persister = si.getEntityPersister(null, entity);
            id = persister.getIdentifier(entity, hibSession.getEntityMode());
          }
          else
          {
            id = entry.getId();
          }
          return hibSession.load(obj.getClass(), id);
        }
      }
    }
    return obj;
  }

  static public class DCLConnectionProvider extends DriverManagerConnectionProvider
  {
    @Override
    public Connection getConnection() throws SQLException
    {
      Connection connection = super.getConnection();
      return setUserToConnecton(connection);
    }

    static public Connection setUserToConnecton(Connection connection) throws SQLException
    {
      String stmtString = "execute procedure set_context(?)";

      CallableStatement stmt = null;

      try
      {
        stmt = connection.prepareCall(stmtString);

        User threadUser = UserUtil.getThreadUser();
        if (threadUser == null)
        {
          stmt.setNull(1, Types.VARCHAR);
        }
        else
        {
          stmt.setString(1, threadUser.getUsr_id());
        }
        //execute
        stmt.execute();
        return connection;
      }
      finally
      {
        if (stmt != null) stmt.close();
      }
    }
  }
}
