<%@ page import="net.sam.dcl.dbo.DboCategory" %>
<%@ page import="net.sam.dcl.dbo.DboProduce" %>
<%@ page import="net.sam.dcl.util.HibernateUtil" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="java.util.List" %>
<%
	Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
	hibSession.beginTransaction();

	Query query = hibSession.createQuery("from DboCategory");
	List<DboProduce> list = query.list();


	int i = 1;
	i++;
	int a = 1;
	hibSession.getTransaction().commit();
	hibSession = null;
	//hibSession.createSQLQuery("select * from DCL_GET_CHILD_CATEGORIES(null, '1') ").addEntity(DboCategory.class).list()
%>