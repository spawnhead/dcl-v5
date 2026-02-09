<%@ page import="net.sam.dcl.beans.ReportDelimiterConsts,
                 net.sam.dcl.locking.LockedRecords,
                 net.sam.dcl.locking.LockedRecordsProcessor,
                 net.sam.dcl.locking.SyncObject"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%
  final JspWriter o = out;
  LockedRecords lockedRecords = LockedRecords.getLockedRecords();
  lockedRecords.iterate(new LockedRecordsProcessor()
  {
    public void process(SyncObject syncObject) throws Exception
    {
      o.println(syncObject.getName() + ":" + syncObject.getId() + ":" + syncObject.getOwner() + ReportDelimiterConsts.html_separator);
    }
  });
%>

