<%@ page import="net.sam.dcl.dbo.DboCategory" %>
<%@ page import="net.sam.dcl.dbo.DboProduce" %>
<%@ page import="net.sam.dcl.util.HibernateUtil" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="java.util.List" %>
<%
	Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
	hibSession.beginTransaction();

	DboCategory category = new DboCategory("1");

	DboCategory childLevel1 = new DboCategory("1.1");

	childLevel1.addChild(new DboCategory("1.1.1"));
	childLevel1.addChild(new DboCategory("1.1.2"));
	childLevel1.addChild(new DboCategory("1.1.3"));
	category.addChild(childLevel1);

	childLevel1 = new DboCategory("1.2");

	childLevel1.addChild(new DboCategory("1.2.1"));
	childLevel1.addChild(new DboCategory("1.2.2"));
	childLevel1.addChild(new DboCategory("1.2.3"));
	category.addChild(childLevel1);

	hibSession.save(category);


	category = new DboCategory("2");

	childLevel1 = new DboCategory("2.1");

	childLevel1.addChild(new DboCategory("2.1.1"));
	childLevel1.addChild(new DboCategory("2.1.2"));
	childLevel1.addChild(new DboCategory("2.1.3"));
	category.addChild(childLevel1);

	childLevel1 = new DboCategory("2.2");

	childLevel1.addChild(new DboCategory("2.2.1"));
	childLevel1.addChild(new DboCategory("2.2.2"));
	childLevel1.addChild(new DboCategory("2.2.3"));
	category.addChild(childLevel1);

	hibSession.save(category);
	Query query = hibSession.createQuery("from DboCategory");
	List<DboProduce> list = query.list();


	hibSession.getTransaction().commit();

%>