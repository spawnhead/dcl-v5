package net.sam.dcl.controller.actions;

import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.IProcessAfter;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.processor.ActionProcessor;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Dima
 * Date: Sep 29, 2004
 * Time: 5:01:29 PM
 */
public class HibernateAction extends DBAction implements IProcessBefore, IProcessAfter{

	protected static  final SessionFactory hibernateSessionFactory = HibernateUtil.getSessionFactory();
  public void initActionContext(IActionContext context, ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, ActionProcessor actionProcessor) throws Exception {
    super.initActionContext(context, mapping, actionForm, request, response, actionProcessor);
		//HibernateUtil.getSessionFactory().getCurrentSession().;
	}


	public ActionForward processBefore(IActionContext context) throws Exception {
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		return null;
	}

	public ActionForward processAfter(IActionContext context, ActionForward forward) throws Exception {
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		if (hibSession.getTransaction().isActive()){
			try {
				hibSession.getTransaction().commit();
				log.info("HibernateAction.hibSession.commit");
			} catch (HibernateException e) {
				StrutsUtil.addError(context,"error.hibernate.commit",e);
				throw e;
			}
		}
		hibSession.beginTransaction();
		log.info("HibernateAction.hibSession.beginTransaction");
		return forward;
	}

	public ActionForward processException(IActionContext context, ActionForward forward, Exception e) {
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		if (hibSession.getTransaction().isActive()){
			hibSession.getTransaction().rollback();
			hibSession.clear();
			log.info("HibernateAction.hibSession.rollback",e);
		}
		return super.processException(context, forward, e);
	}

}
