<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%
  // IMPORTANT: The main DCL layout loads legacy Prototype.js and other scripts that can conflict with AG Grid.
  // To keep the test grid stable inside the application layout, we render the working standalone page in an iframe.
  String standaloneUrl = request.getContextPath() + "/test/TestNewGridStandalone.jsp";
%>
<div class="test-new-grid-page">
  <p style="margin-bottom:8px;">
    test_newGrid — тест нового грида (AG Grid Community) в разделе Development.
    Данные грузятся из БД по API; при недоступности API показываются тестовые строки.
    <a href="<%= standaloneUrl %>" target="_blank" rel="noopener">Открыть в новой вкладке</a>
  </p>
  <iframe
    id="testNewGridFrame"
    src="<%= standaloneUrl %>"
    style="width:100%; height:calc(100vh - 220px); min-height:650px; border:0; background:#fff;"
    loading="lazy"
    referrerpolicy="no-referrer"
  ></iframe>
  <script>
  (function() {
    var f = document.getElementById('testNewGridFrame');
    function resize() {
      if (!f) return;
      var rect = f.getBoundingClientRect();
      var vh = window.innerHeight || document.documentElement.clientHeight || 0;
      var h = vh - rect.top - 16;
      if (h < 650) h = 650;
      f.style.height = h + 'px';
    }
    if (window.addEventListener) {
      window.addEventListener('resize', resize);
    }
    resize();
  })();
  </script>
</div>
