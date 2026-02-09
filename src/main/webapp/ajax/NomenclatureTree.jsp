<%@ page import="net.sam.dcl.dbo.DboCategory" %>
<%@ page import="net.sam.dcl.form.NomenclatureActionBean" %>
<%@ page import="net.sam.dcl.log.Log" %>
<%@ page import="net.sam.dcl.util.HibernateUtil" %>
<%@ page import="net.sam.dcl.util.StringUtil" %>
<%@ page import="org.hibernate.Hibernate" %>
<%@ page import="org.hibernate.StatelessSession" %>
<%@ page import="net.sam.dcl.controller.actions.SelectFromGridAction" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="net.sam.dcl.util.DAOUtils" %>
<%@ page import="net.sam.dcl.controller.ActionContext" %>
<%@ page import="net.sam.dcl.controller.IActionContext" %>
<%@ page import="java.sql.Types" %>
<%@ page import="java.util.*" %>
<%@ page import="net.sam.dcl.db.*" %>
<%!
	static public class Category extends DboCategory
	{
		Integer parentId;
		Integer matchCount;


		public Integer getParentId()
		{
			return parentId;
		}

		public void setParentId(Integer parentId)
		{
			this.parentId = parentId;
		}

		public Integer getMatchCount()
		{
			return matchCount;
		}

		public void setMatchCount(Integer matchCount)
		{
			this.matchCount = matchCount;
		}
	}

	private boolean getNodes2(StatelessSession hibSession, NomenclatureActionBean form, DboCategory parent, boolean selectMode)
					throws Exception
	{
		VDbConnection conn = null;
		try
		{
			conn = VDbConnectionManager.getVDbConnection();
			String sql = "select c.*, 0 as PRD_COUNT from dcl_category c order by parent_id, cat_name ";

			//if (parent != null && isFilter(form)){
			VParameter param = new VParameter();

			if (isFilter(form))
			{
				String show_blocked = null;
				if (selectMode)
				{
					show_blocked = "1";
				}
				sql = "select cc.* from dcl_get_tree(:filter_category,:filter_produce,:filter_type,:filter_params,:filter_add_params,:stf_id,:filter_cusCode, :filter_catalog_number,:show_blocked,:filter_common) cc where cc.IS_HAVE_PRODUCES is not null";
				param.add("show_blocked", show_blocked, Types.VARCHAR);
			}
			long savedTime = System.currentTimeMillis();

			VResultSet resultSet = DAOUtils.executeQuery(conn,
							sql,
							form,
							param);

			Map<Integer, Category> map = new LinkedHashMap();
			if (resultSet.next())
			{
				Category category = categoryFromResultSet(resultSet);
				map.put(category.getId(), category);
				while (resultSet.next())
				{
					category = categoryFromResultSet(resultSet);
					map.put(category.getId(), category);
				}
			}
			else
			{
				form.setRootCategory(new DboCategory(-2, null, "Ничего не найдено", new HashSet<DboCategory>(), null));
				return false;
			}

			for (Category category : map.values())
			{
				if (category.getParentId() != null)
				{
					DboCategory par = map.get(category.getParentId());
					if (par != null)
					{
						par.addChild(category);
					}
				}
				else
				{
					form.setRootCategory(category);
				}
			}
		}
		finally
		{
			if (conn != null) conn.close();
		}

		return true;
	}

	private Category categoryFromResultSet(VResultSet resultSet) throws VDbException
	{
		Category category = new Category();
		if (StringUtil.isEmpty(resultSet.getData("CAT_ID")))
		{
			category.setId(null);
		}
		else
		{
			category.setId(Integer.parseInt(resultSet.getData("CAT_ID")));
		}
		category.setName(resultSet.getData("CAT_NAME"));
		if (StringUtil.isEmpty(resultSet.getData("PARENT_ID")))
		{
			category.setParent(null);
		}
		else
		{
			category.setParentId(Integer.parseInt(resultSet.getData("PARENT_ID")));
		}
		category.setChildren(new LinkedHashSet<DboCategory>());
		category.setMatchCount(Integer.parseInt(resultSet.getData("PRD_COUNT")));
		return category;
	}

	private String getCategoryNameFormatted(String categoryName)
	{
		if (categoryName.contains("'"))
		{
			categoryName = categoryName.replace("'", "\\'");
		}
		return categoryName;
	}

	private boolean nodes2String(DboCategory category, NomenclatureActionBean form, String parentStr, StringBuffer res, boolean[] selectedSet)
	{
		if (category.getParent() == null)
		{
			res.append(parentStr + " = new WebFXTree('" + getCategoryNameFormatted(category.getName()) + "');\n");
			res.append(parentStr + ".setBehavior('classic');\n");
			res.append(parentStr + ".cat_id='" + category.getId() + "';\n");
			res.append("_wTH.selectedNode=null;\n");
			for (DboCategory childCategory : category.getChildren())
			{
				nodes2String(childCategory, form, parentStr, res, selectedSet);
			}
		}
		else
		{

			res.append("var tmp" + category.getId() + " = new WebFXTreeItem('");
			res.append(getCategoryNameFormatted(category.getName()));
			res.append("');\n");
			res.append("tmp" + category.getId() + ".cat_id=");
			res.append(category.getId());
			res.append(";\n");
			res.append(parentStr + ".add(tmp" + category.getId() + ");\n");
			if (isFilter(form))
			{
				res.append("tmp" + category.getId() + ".open=true;");
			}
			if (isFilter(form) && !selectedSet[0] && ((Category) category).getMatchCount() > 0)
			{
				res.append("_wTH.selectedNode=tmp" + category.getId() + ";\n");
				selectedSet[0] = true;
			}
			else
			{
				if (String.valueOf(category.getId()).equals(form.getCat_id()))
				{
					res.append("_wTH.selectedNode=tmp" + category.getId() + ";\n");
				}
			}
			for (DboCategory childCategory : category.getChildren())
			{
				nodes2String(childCategory, form, "tmp" + category.getId(), res, selectedSet);
			}
		}
		return true;
	}

	boolean isFilter(NomenclatureActionBean form)
	{
		return form.getIgnoreFilter() == null &&
						(form.getStf_id() != null ||
										!StringUtil.isEmpty(form.getFilter_add_params()) ||
										!StringUtil.isEmpty(form.getFilter_catalog_number()) ||
										!StringUtil.isEmpty(form.getFilter_cusCode()) ||
										!StringUtil.isEmpty(form.getFilter_params()) ||
										!StringUtil.isEmpty(form.getFilter_produce()) ||
										!StringUtil.isEmpty(form.getFilter_common()) ||
										!StringUtil.isEmpty(form.getFilter_type()));
	}
%>
<script type="text/javascript" language="JavaScript">
	<%
		StatelessSession hibSession = HibernateUtil.getSessionFactory().openStatelessSession();
		boolean[] selectedSet = {true};
		try {
			NomenclatureActionBean form = (NomenclatureActionBean) request.getSession().getAttribute("Nomenclature");
			StringBuffer res = new StringBuffer();

			if (form.getIgnoreFilter() != null){
				form.setRootCategory(null);
			}
			if (form.getRestoreFilter() != null){
				form.setRootCategory(null);
				form.setRestoreFilter(null);
			}
			if (form.getRootCategory() == null){
				getNodes2(hibSession, form, null,SelectFromGridAction.isSelectMode(request.getSession()));
				 selectedSet[0] = false;
			}
			nodes2String(form.getRootCategory(), form, "_wTH.tree", res,selectedSet);
			res.append("_wTH.onSelect = function(obj){loadGrid(obj)};");

			out.print(res.toString());
			form.setIgnoreFilter(null);
		} catch (Exception e) {
			Log.info(e.getMessage());
			Log.printExceptionStackTrace(e);
			throw e;
		} finally {
			hibSession.close();
		}
	%>

</script>
