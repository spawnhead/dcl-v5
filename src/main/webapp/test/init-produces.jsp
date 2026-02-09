<%@ page import="net.sam.dcl.dbo.DboCategory" %>
<%@ page import="net.sam.dcl.dbo.DboProduce" %>
<%@ page import="net.sam.dcl.util.HibernateUtil" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="java.util.List" %>
<%
	Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
	hibSession.beginTransaction();

	List<DboCategory> categories = hibSession.createQuery("from DboCategory").list();
	for (DboCategory category : categories) {
		for (int i = 0; i < 50; i++){
			DboProduce tmp = new DboProduce();
			tmp.setName("Name"+category.getName()+"."+i);
			tmp.setType("type"+category.getName()+"."+i);
			tmp.setParams("param"+category.getName()+"."+i);
			tmp.setAddParams("add param"+category.getName()+"."+i);
			category.addProduce(tmp);
		}
		//hibSession.update(category);
	}


	hibSession.getTransaction().commit();

%>