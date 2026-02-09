package net.sam.dcl.filters;

import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.UserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: DG
 * Date: Aug 9, 2005
 * Time: 1:13:07 PM
 */

public class HibernateSessionFilter implements Filter
{
	protected static Log log = LogFactory.getLog(HibernateSessionFilter.class);
	FilterConfig config = null;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException
	{
		//ActionContext.threadInstance().clear();
		UserUtil.setThreadUser((HttpServletRequest) request);
		try
		{
			SessionFactory factory = HibernateUtil.getSessionFactory();
			org.hibernate.classic.Session hibSession = factory.openSession();

			ManagedSessionContext.bind(hibSession);
			assert hibSession == factory.getCurrentSession();
			log.info("HibernateSessionFilter.hibSession.beginTransaction");
			try
			{
				chain.doFilter(request, response);
				if (hibSession.getTransaction().isActive())
				{
					hibSession.getTransaction().commit();
					log.info("HibernateSessionFilter.hibSession.commit");
				}
			}
			catch (Throwable e)
			{
				if (hibSession.getTransaction().isActive())
				{
					log.info("HibernateSessionFilter.hibSession.rollback");
					hibSession.getTransaction().rollback();
				}
				Throwable checkingExc = e;
				while (checkingExc instanceof RuntimeException && checkingExc.getCause() != null)
				{
					checkingExc = checkingExc.getCause();
				}
				try
				{
					Class clazz = checkingExc.getClass().getClassLoader().loadClass("org.apache.catalina.connector.ClientAbortException");
					if (clazz.isAssignableFrom(checkingExc.getClass()))
					{
						return;
					}
				}
				catch (ClassNotFoundException e1)
				{
					// can't load eception class - check by name
					if ("org.apache.catalina.connector.ClientAbortException".equals(checkingExc.getClass().getName()))
					{
						return;
					}
				}

				//throw new RuntimeException(e);
				log.error("HibernateSessionFilter:exception", e);
				if (e instanceof ServletException)
				{
					log.error("HibernateSessionFilter:rootException", ((ServletException) e).getRootCause());
				}
				throw new RuntimeException(e);
			}
			finally
			{
				ManagedSessionContext.unbind(factory);
				if (hibSession.isOpen())
				{
					hibSession.close();
					log.info("HibernateSessionFilter.hibSession.close");
				}

			}
		}
		finally
		{
			UserUtil.clearThreadUser();
		}

	}

	public void init(FilterConfig config) throws ServletException
	{
		this.config = config;
	}

	public void destroy()
	{

	}
}
