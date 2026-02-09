<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<ctrl:form method="post">
Name1:<ctrl:text property="name1"/><br>
Name2:<ctrl:text property="name2"/><br>
Name3:<ctrl:text property="name3"/><br>
KP:<ctrl:text property="kp_id"/><ctrl:text property="kp_name"/>
	<ctrl:ubutton type="submit" dispatch="selectCP" >Select KP</ctrl:ubutton>
<br>
nomenclature:<ctrl:text property="nomenclature_id"/><ctrl:text property="nomenclature_name"/>
	<ctrl:ubutton type="submit" dispatch="select_nomenclature" >Select nomenclature</ctrl:ubutton>
<br>
<ctrl:serverList property="montageAdjustment.mad_machine_type" idName="montageAdjustment.mad_id" action="/MontageAdjustmentListAction"
                 scriptUrl="stf_id=99" selectOnly="true" style="width:225px;"/>
</ctrl:form>

