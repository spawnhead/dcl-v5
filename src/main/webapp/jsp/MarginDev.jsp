<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
  String ctx = request.getContextPath();
  String generateUrl = response.encodeURL(ctx + "/MarginDevAction.do");
  String excelUrl = response.encodeURL(ctx + "/MarginDevAction.do") + "?dispatch=generateExcel";
  String gridUrl = response.encodeURL(ctx + "/test/MarginGridStandalone.jsp");
  String usersUrl = response.encodeURL(ctx + "/UsersListAction.do") + "?have_all=true";
  String depsUrl = response.encodeURL(ctx + "/DepartmentsListAction.do") + "?have_all=true";
  String contractorsUrl = response.encodeURL(ctx + "/ContractorsListAction.do") + "?have_all=true";
  String stuffCatUrl = response.encodeURL(ctx + "/StuffCategoriesListAction.do") + "?have_all=true";
  String routesUrl = response.encodeURL(ctx + "/RoutesListAction.do") + "?have_all=true";
  String sessionId = (session != null) ? session.getId() : null;
  if (sessionId != null && gridUrl.indexOf(";jsessionid=") == -1) {
    gridUrl = gridUrl + (gridUrl.indexOf('?') >= 0 ? "&" : "?") + "jsessionid=" + sessionId;
  }
%>
<style>
  #margin-dev-root { display: flex; flex-direction: column; height: calc(100vh - 180px); min-height: 500px; }
  .mg-toolbar {
    display: flex; flex-wrap: wrap; align-items: center; gap: 8px 12px;
    padding: 10px 12px; background: #fff; border: 1px solid #e3e6ea; border-radius: 8px; margin-bottom: 8px;
  }
  .mg-toolbar .mg-label { font-size: 12px; color: #667085; margin-right: 2px; }
  .mg-toolbar input[type="text"], .mg-toolbar select {
    padding: 5px 8px; border: 1px solid #d0d5dd; border-radius: 6px; font-size: 12px;
    min-width: 140px; background: #fff;
  }
  .mg-toolbar select { cursor: pointer; }
  .mg-toolbar .mg-btn {
    padding: 6px 12px; border: 1px solid #b5b5b5; border-radius: 6px;
    background: #fff; color: #222; font-size: 12px; cursor: pointer;
  }
  .mg-toolbar .mg-btn:hover { filter: brightness(0.97); }
  .mg-toolbar .mg-btn-primary { background: #4a90e2; border-color: #4a90e2; color: #fff; }
  .mg-toolbar .mg-spacer { flex: 1; }
  .mg-toolbar .mg-status { font-size: 12px; color: #666; }
  #margin-dev-grid-frame { flex: 1; min-height: 400px; width: 100%; border: 0; background: #fff; }
</style>

<div id="margin-dev-root">
  <iframe name="marginSubmitFrame" id="marginSubmitFrame" style="display:none;width:0;height:0;border:0"></iframe>
  <form id="marginFilterForm" class="mg-toolbar" method="post" action="<%= generateUrl %>" target="marginSubmitFrame">
    <input type="hidden" name="dispatch" value="generate"/>
    <span class="mg-label"><bean:message key="Margin.date"/> с</span>
    <input type="text" name="date_begin" id="mg_date_begin" placeholder="дд.мм.гггг" title="Дата начала"/>
    <span class="mg-label">по</span>
    <input type="text" name="date_end" id="mg_date_end" placeholder="дд.мм.гггг" title="Дата окончания"/>
    <span class="mg-label"><bean:message key="Margin.user"/></span>
    <input type="hidden" name="user.userFullName"/>
    <select name="user.usr_id" id="mg_user" title="Пользователь">
      <option value="">—</option>
    </select>
    <span class="mg-label"><bean:message key="Margin.department"/></span>
    <input type="hidden" name="department.name"/>
    <select name="department.id" id="mg_department" title="Отдел">
      <option value="">—</option>
    </select>
    <span class="mg-label"><bean:message key="Margin.contractor"/></span>
    <input type="hidden" name="contractor.name"/>
    <select name="contractor.id" id="mg_contractor" title="Контрагент">
      <option value="">—</option>
    </select>
    <span class="mg-label"><bean:message key="Margin.stuffCategory"/></span>
    <input type="hidden" name="stuffCategory.name"/>
    <select name="stuffCategory.id" id="mg_stuffCategory" title="Категория товаров">
      <option value="">—</option>
    </select>
    <span class="mg-label"><bean:message key="Margin.route"/></span>
    <input type="hidden" name="route.name"/>
    <select name="route.id" id="mg_route" title="Маршрут">
      <option value="">—</option>
    </select>
    <label class="mg-label"><input type="checkbox" name="onlyTotal" value="1" id="mg_onlyTotal"/> <bean:message key="Margin.onlyTotal"/></label>
    <label class="mg-label"><input type="checkbox" name="itog_by_spec" value="1"/> <bean:message key="Margin.itog_by_spec"/></label>
    <label class="mg-label"><input type="checkbox" name="itog_by_user" value="1"/> <bean:message key="Margin.itog_by_user"/></label>
    <label class="mg-label"><input type="checkbox" name="itog_by_product" value="1"/> <bean:message key="Margin.itog_by_product"/></label>
    <label class="mg-label"><input type="checkbox" name="get_not_block" value="1"/> <bean:message key="Margin.get_not_block"/></label>
    <input type="hidden" name="view_contractor" value="1"/>
    <input type="hidden" name="view_contract" value="1"/>
    <input type="hidden" name="view_stuff_category" value="1"/>
    <input type="hidden" name="view_shipping" value="1"/>
    <input type="hidden" name="view_payment" value="1"/>
    <input type="hidden" name="view_user" value="1"/>
    <input type="hidden" name="view_department" value="1"/>
    <span class="mg-spacer"></span>
    <span class="mg-status" id="mg_status">Укажите даты и хотя бы один фильтр</span>
    <button type="button" class="mg-btn" id="mg_clear"><bean:message key="button.clearAll"/></button>
    <button type="submit" class="mg-btn mg-btn-primary" id="mg_generate" disabled><bean:message key="button.form"/></button>
    <button type="button" class="mg-btn" id="mg_excel" disabled><bean:message key="button.formExcel"/></button>
  </form>
  <iframe id="margin-dev-grid-frame" name="marginDevGridFrame" src="<%= gridUrl %>"></iframe>
</div>
<script>
(function() {
  var gridUrl = '<%= gridUrl %>';
  var excelUrl = '<%= excelUrl %>';
  var listUrls = {
    user: '<%= usersUrl %>',
    department: '<%= depsUrl %>',
    contractor: '<%= contractorsUrl %>',
    stuffCategory: '<%= stuffCatUrl %>',
    route: '<%= routesUrl %>'
  };

  function parseListHtml(html) {
    var doc = new DOMParser().parseFromString(html, 'text/html');
    var rows = Array.from(doc.querySelectorAll('tr'));
    var out = [];
    rows.forEach(function(tr) {
      var onclick = tr.getAttribute('onclick') || '';
      if (onclick.indexOf('ItemSelected') === -1) return;
      var re = /'((?:\\'|[^'])*)'/g, m, parts = [];
      while ((m = re.exec(onclick)) !== null) parts.push(m[1].replace(/\\'/g, "'"));
      var td = tr.querySelector('td');
      var label = td ? td.textContent.trim() : '';
      var value = parts[0] || label;
      var lbl = parts[1] || label || value;
      if (value || lbl) out.push({ value: String(value), label: String(lbl) });
    });
    return out;
  }

  function fetchList(url) {
    return fetch(url, { credentials: 'same-origin' })
      .then(function(r) { return r.text(); })
      .then(parseListHtml);
  }

  var displayNames = { user: 'user.userFullName', department: 'department.name', contractor: 'contractor.name', stuffCategory: 'stuffCategory.name', route: 'route.name' };

  function populateSelect(selId, items) {
    var sel = document.getElementById(selId);
    if (!sel) return;
    var first = sel.options[0];
    sel.innerHTML = '';
    if (first) sel.appendChild(first);
    (items || []).forEach(function(it) {
      var opt = document.createElement('option');
      opt.value = it.value || '';
      opt.textContent = it.label || it.value || '—';
      sel.appendChild(opt);
    });
  }

  function syncDisplayNames() {
    ['user','department','contractor','stuffCategory','route'].forEach(function(k) {
      var sel = document.getElementById('mg_' + k);
      var name = displayNames[k];
      var hid = document.querySelector('input[name="' + name + '"]');
      if (sel && hid) {
        var idx = sel.selectedIndex;
        hid.value = idx >= 0 && sel.options[idx] ? sel.options[idx].textContent : '';
      }
    });
  }

  function initDropdowns() {
    Object.keys(listUrls).forEach(function(k) {
      fetchList(listUrls[k]).then(function(items) {
        populateSelect('mg_' + k, items);
      }).catch(function() {});
    });
  }

  function checkCanGenerate() {
    var db = (document.getElementById('mg_date_begin') || {}).value || '';
    var de = (document.getElementById('mg_date_end') || {}).value || '';
    var u = (document.getElementById('mg_user') || {}).value || '';
    var d = (document.getElementById('mg_department') || {}).value || '';
    var c = (document.getElementById('mg_contractor') || {}).value || '';
    var s = (document.getElementById('mg_stuffCategory') || {}).value || '';
    var r = (document.getElementById('mg_route') || {}).value || '';
    var ok = db.trim() !== '' && de.trim() !== '' && (u || d || c || s || r);
    document.getElementById('mg_generate').disabled = !ok;
    document.getElementById('mg_excel').disabled = !ok;
  }

  function setStatus(txt, err) {
    var el = document.getElementById('mg_status');
    if (el) { el.textContent = txt || ''; el.style.color = err ? '#721c24' : '#666'; }
  }

  function onGenerateSubmit() {
    syncDisplayNames();
    setStatus('Формирование…');
  }

  function onGenerateDone() {
    var frame = document.getElementById('margin-dev-grid-frame');
    if (frame) {
      frame.src = gridUrl + (gridUrl.indexOf('?') >= 0 ? '&' : '?') + 't=' + Date.now();
      setStatus('Результаты готовы');
    }
  }

  ['mg_date_begin','mg_date_end','mg_user','mg_department','mg_contractor','mg_stuffCategory','mg_route'].forEach(function(id) {
    var el = document.getElementById(id);
    if (el) {
      el.addEventListener('change', function() { syncDisplayNames(); checkCanGenerate(); });
      if (id.indexOf('date') !== -1) el.addEventListener('input', checkCanGenerate);
    }
  });

  document.getElementById('mg_clear').onclick = function() {
    document.getElementById('marginFilterForm').reset();
    ['mg_user','mg_department','mg_contractor','mg_stuffCategory','mg_route'].forEach(function(id) {
      var s = document.getElementById(id);
      if (s) { s.innerHTML = '<option value="">—</option>'; }
    });
    initDropdowns();
    checkCanGenerate();
    setStatus('Укажите даты и хотя бы один фильтр');
  };

  document.getElementById('marginFilterForm').onsubmit = onGenerateSubmit;
  document.getElementById('marginSubmitFrame').onload = onGenerateDone;
  document.getElementById('mg_excel').onclick = function() {
    var ifr = document.createElement('iframe');
    ifr.style.display = 'none';
    ifr.src = excelUrl;
    document.body.appendChild(ifr);
    setTimeout(function() { document.body.removeChild(ifr); }, 5000);
  };

  initDropdowns();
  checkCanGenerate();
})();
</script>
