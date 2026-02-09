<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page import="java.lang.management.RuntimeMXBean" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="net.sam.dcl.config.Config" %>
<%@ page import="net.sam.dcl.db.VDbConnection" %>
<%@ page import="net.sam.dcl.db.VDbConnectionManager" %>
<%@ page import="net.sam.dcl.db.VResultSet" %>

<%!
  private String formatDuration(long ms)
  {
    long seconds = ms / 1000;
    long minutes = seconds / 60;
    long hours = minutes / 60;
    long days = hours / 24;
    seconds = seconds % 60;
    minutes = minutes % 60;
    hours = hours % 24;
    StringBuilder sb = new StringBuilder();
    if (days > 0) sb.append(days).append("d ");
    if (hours > 0 || days > 0) sb.append(hours).append("h ");
    if (minutes > 0 || hours > 0 || days > 0) sb.append(minutes).append("m ");
    sb.append(seconds).append("s");
    return sb.toString().trim();
  }
%>

<%
  String javaVersion = System.getProperty("java.version");
  String javaVendor = System.getProperty("java.vendor");
  String javaHome = System.getProperty("java.home");
  String jvmName = System.getProperty("java.vm.name");
  String jvmVersion = System.getProperty("java.vm.version");
  String osName = System.getProperty("os.name");
  String osVersion = System.getProperty("os.version");
  String osArch = System.getProperty("os.arch");
  String fileEncoding = System.getProperty("file.encoding");
  String userDir = System.getProperty("user.dir");
  String classPath = System.getProperty("java.class.path");
  String libPath = System.getProperty("java.library.path");

  RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
  long uptimeMs = runtimeMxBean.getUptime();
  String uptime = formatDuration(uptimeMs);

  Runtime runtime = Runtime.getRuntime();
  long maxMemory = runtime.maxMemory();
  long totalMemory = runtime.totalMemory();
  long freeMemory = runtime.freeMemory();

  String serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
  String hostName = "";
  try
  {
    hostName = InetAddress.getLocalHost().getHostName();
  }
  catch (Exception e)
  {
    hostName = "unknown";
  }

  String dbExpectedVersion = "";
  try
  {
    dbExpectedVersion = Config.getString("db.version");
  }
  catch (Exception e)
  {
    dbExpectedVersion = "";
  }

  String dbEngineVersion = "";
  VDbConnection conn = null;
  VResultSet rs = null;
  try
  {
    conn = VDbConnectionManager.getVDbConnection();
    rs = conn.executeQuery(null, "select rdb$get_context('SYSTEM','ENGINE_VERSION') as engine_version from rdb$database");
    if (rs.next())
    {
      dbEngineVersion = rs.getData("engine_version");
    }
  }
  catch (Exception e)
  {
    dbEngineVersion = "";
  }
  finally
  {
    if (rs != null) rs.close();
    if (conn != null) conn.close();
  }
%>

<h2>Dev zone</h2>

<table border="0" cellpadding="4" cellspacing="0">
  <tr><td><b>Server time</b></td><td><%=serverTime%></td></tr>
  <tr><td><b>Host</b></td><td><%=hostName%></td></tr>
  <tr><td><b>Context path</b></td><td><%=request.getContextPath()%></td></tr>
  <tr><td><b>App root</b></td><td><%=application.getRealPath("/")%></td></tr>
  <tr><td><b>Working dir</b></td><td><%=userDir%></td></tr>
</table>

<br/>

<table border="0" cellpadding="4" cellspacing="0">
  <tr><td><b>Java</b></td><td><%=javaVersion%> (<%=javaVendor%>)</td></tr>
  <tr><td><b>JVM</b></td><td><%=jvmName%> <%=jvmVersion%></td></tr>
  <tr><td><b>JAVA_HOME</b></td><td><%=javaHome%></td></tr>
  <tr><td><b>File encoding</b></td><td><%=fileEncoding%></td></tr>
  <tr><td><b>OS</b></td><td><%=osName%> <%=osVersion%> (<%=osArch%>)</td></tr>
  <tr><td><b>Uptime</b></td><td><%=uptime%></td></tr>
  <tr><td><b>Memory</b></td><td>max=<%=maxMemory%>, total=<%=totalMemory%>, free=<%=freeMemory%></td></tr>
</table>

<br/>

<table border="0" cellpadding="4" cellspacing="0">
  <tr><td><b>Firebird engine</b></td><td><%=dbEngineVersion%></td></tr>
  <tr><td><b>DB expected</b></td><td><%=dbExpectedVersion%></td></tr>
  <tr><td><b>Java library path</b></td><td><%=libPath%></td></tr>
  <tr><td><b>Classpath</b></td><td><%=classPath%></td></tr>
</table>

<br/>

<h3>Фреймворки и библиотеки (кандидаты на замену в скобках)</h3>
<p>Список основных технологий приложения и гипотетических вариантов замены. Подробнее: <code>docs/Frameworks_and_Libraries_Replacement_Candidates.md</code>.</p>
<ul>
  <li><b>MVC / Web:</b> Struts 1.x (Spring MVC, Jakarta MVC)</li>
  <li><b>Шаблоны:</b> Struts Tiles (Apache Tiles, Thymeleaf, SPA)</li>
  <li><b>Валидация:</b> Struts Validator (Bean Validation JSR 380)</li>
  <li><b>Безопасность:</b> DefenderFilter + LoginAction (Spring Security)</li>
  <li><b>ORM:</b> Hibernate 5.x (Hibernate 6.x, EclipseLink)</li>
  <li><b>SQL-слой:</b> sql-resources.xml + DAOUtils (JPA Criteria, QueryDSL, jOOQ, MyBatis)</li>
  <li><b>Логирование:</b> Commons Logging (SLF4J + Logback)</li>
  <li><b>Утилиты:</b> Commons BeanUtils / Collections (MapStruct, Stream API, Guava)</li>
  <li><b>Файлы:</b> Commons FileUpload / IO (Servlet Part API, java.nio.file)</li>
  <li><b>PDF:</b> iText 2.x (iText 7, OpenPDF)</li>
  <li><b>Excel:</b> Apache POI (миграция на POI 5.x, Commons CSV)</li>
  <li><b>Планировщик:</b> Quartz 2.x (builder API, Spring @Scheduled)</li>
  <li><b>SOAP:</b> Apache Axis 1.x (CXF, Spring WS)</li>
  <li><b>Фронтенд:</b> Prototype.js (vanilla JS, jQuery)</li>
</ul>