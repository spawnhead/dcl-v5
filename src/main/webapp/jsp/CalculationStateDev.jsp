<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%
  String ctx = request.getContextPath();
  String generateUrl = response.encodeURL(ctx + "/CalculationStateAction.do");
  String excelUrl = response.encodeURL(ctx + "/CalculationStateAction.do") + "?dispatch=generateExcel";
  String gridUrl = response.encodeURL(ctx + "/test/CalculationStateGridStandalone.jsp");
  String reportTypesUrl = response.encodeURL(ctx + "/CalcStateReportTypesListAction.do");
  String contractorsUrl = response.encodeURL(ctx + "/ContractorsListAction.do") + "?have_all=true";
  String sellersUrl = response.encodeURL(ctx + "/SellersListAction.do");
  String contractsUrl = response.encodeURL(ctx + "/ContractsDepFromContractorListAction.do");
  String currenciesUrl = response.encodeURL(ctx + "/CurrenciesListAction.do");
  String usersUrl = response.encodeURL(ctx + "/UsersListAction.do") + "?have_all=true";
  String depsUrl = response.encodeURL(ctx + "/DepartmentsListAction.do") + "?have_all=true";
  String sessionId = (session != null) ? session.getId() : null;
  if (sessionId != null && gridUrl.indexOf(";jsessionid=") == -1) {
    gridUrl = gridUrl + (gridUrl.indexOf('?') >= 0 ? "&" : "?") + "jsessionid=" + sessionId;
  }
%>
<style>
  #calc-state-dev-root { display: flex; flex-direction: column; height: calc(100vh - 180px); min-height: 500px; }
  .cs-toolbar { display: flex; flex-wrap: wrap; align-items: center; gap: 8px 12px; padding: 10px 12px; background: #fff; border: 1px solid #e3e6ea; border-radius: 8px; margin-bottom: 8px; }
  .cs-toolbar .cs-label { font-size: 12px; color: #667085; margin-right: 2px; }
  .cs-toolbar input[type="text"], .cs-toolbar select { padding: 5px 8px; border: 1px solid #d0d5dd; border-radius: 6px; font-size: 12px; min-width: 140px; background: #fff; }
  .cs-toolbar .cs-btn { padding: 6px 12px; border: 1px solid #b5b5b5; border-radius: 6px; background: #fff; color: #222; font-size: 12px; cursor: pointer; }
  .cs-toolbar .cs-btn:hover { filter: brightness(0.97); }
  .cs-toolbar .cs-btn-primary { background: #4a90e2; border-color: #4a90e2; color: #fff; }
  .cs-toolbar .cs-spacer { flex: 1; }
  .cs-toolbar .cs-status { font-size: 12px; color: #666; }
  #calc-state-grid-frame { flex: 1; min-height: 400px; width: 100%; border: 0; background: #fff; }
</style>

<div id="calc-state-dev-root">
  <iframe name="calcStateSubmitFrame" id="calcStateSubmitFrame" style="display:none;width:0;height:0;border:0"></iframe>
  <form id="calcStateFilterForm" class="cs-toolbar" method="post" action="<%= generateUrl %>" target="calcStateSubmitFrame">
    <input type="hidden" name="dispatch" value="generate"/>
    <span class="cs-label"><bean:message key="CalculationState.reportType"/></span>
    <input type="hidden" name="reportType.name"/>
    <select name="reportType.id" id="cs_reportType" title="Вид отчёта">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.contractor"/></span>
    <input type="hidden" name="contractorCalcState.name"/>
    <select name="contractorCalcState.id" id="cs_contractor" title="Контрагент">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.seller"/></span>
    <input type="hidden" name="sellerCalcState.name"/>
    <select name="sellerCalcState.id" id="cs_seller" title="Продавец">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.contract"/></span>
    <select name="contract.con_id" id="cs_contract" title="Договор">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.currency"/></span>
    <input type="hidden" name="currencyCalcState.name"/>
    <select name="currencyCalcState.id" id="cs_currency" title="Валюта">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.user"/></span>
    <input type="hidden" name="userCalcState.userFullName"/>
    <select name="userCalcState.usr_id" id="cs_user" title="Пользователь">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.department"/></span>
    <input type="hidden" name="departmentCalcState.name"/>
    <select name="departmentCalcState.id" id="cs_department" title="Отдел">
      <option value="">—</option>
    </select>
    <span class="cs-label"><bean:message key="CalculationState.not_include_if_earliest"/></span>
    <input type="checkbox" name="not_include_if_earliest" value="1" id="cs_not_include_earliest"/>
    <input type="text" name="earliest_doc_date" id="cs_earliest_date" placeholder="дд.мм.гггг" style="width:100px;"/>
    <label class="cs-label"><input type="checkbox" name="not_include_zero" value="1"/> <bean:message key="CalculationState.not_include_zero"/></label>
    <label class="cs-label"><input type="checkbox" name="include_all_specs" value="1"/> <bean:message key="CalculationState.include_all_specs"/></label>
    <label class="cs-label"><input type="checkbox" name="not_show_annul" value="1"/> <bean:message key="CalculationState.not_show_annul"/></label>
    <label class="cs-label"><input type="checkbox" name="notShowExpiredContractZeroBalance" value="1"/> <bean:message key="CalculationState.notShowExpiredContractZeroBalance"/></label>
    <label class="cs-label"><input type="checkbox" name="view_pay_cond" value="1"/> <bean:message key="CalculationState.view_pay_cond"/></label>
    <label class="cs-label"><input type="checkbox" name="view_delivery_cond" value="1"/> <bean:message key="CalculationState.view_delivery_cond"/></label>
    <label class="cs-label"><input type="checkbox" name="view_expiration" value="1"/> <bean:message key="CalculationState.view_expiration"/></label>
    <label class="cs-label"><input type="checkbox" name="view_complaint" value="1"/> <bean:message key="CalculationState.view_complaint"/></label>
    <label class="cs-label"><input type="checkbox" name="view_comment" value="1"/> <bean:message key="CalculationState.view_comment"/></label>
    <label class="cs-label"><input type="checkbox" name="view_manager" value="1"/> <bean:message key="CalculationState.view_manager"/></label>
    <label class="cs-label"><input type="checkbox" name="view_stuff_category" value="1"/> <bean:message key="CalculationState.view_stuff_category"/></label>
    <span class="cs-spacer"></span>
    <span class="cs-status" id="cs_status">Выберите вид отчёта и контрагента</span>
    <button type="button" class="cs-btn" id="cs_clear"><bean:message key="button.clearAll"/></button>
    <button type="submit" class="cs-btn cs-btn-primary" id="cs_generate" disabled><bean:message key="button.form"/></button>
    <button type="button" class="cs-btn" id="cs_excel" disabled><bean:message key="button.formExcel"/></button>
  </form>
  <iframe id="calc-state-grid-frame" name="calcStateGridFrame" src="<%= gridUrl %>"></iframe>
</div>
<script>
(function() {
  var gridUrl = '<%= gridUrl %>';
  var excelUrl = '<%= excelUrl %>';
  var listUrls = {
    reportType: '<%= reportTypesUrl %>',
    contractor: '<%= contractorsUrl %>',
    seller: '<%= sellersUrl %>',
    currency: '<%= currenciesUrl %>',
    user: '<%= usersUrl %>',
    department: '<%= depsUrl %>'
  };
  var contractsBaseUrl = '<%= contractsUrl %>';

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
    return fetch(url, { credentials: 'same-origin' }).then(function(r){ return r.text(); }).then(parseListHtml);
  }

  var displayNames = {
    reportType: 'reportType.name',
    contractor: 'contractorCalcState.name',
    seller: 'sellerCalcState.name',
    currency: 'currencyCalcState.name',
    user: 'userCalcState.userFullName',
    department: 'departmentCalcState.name'
  };

  function populateSelect(selId, items, autoSelectFirst) {
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
    if (autoSelectFirst && items && items.length > 0 && items[0].value) {
      sel.selectedIndex = 1;
      syncDisplayNames();
    }
    checkCanGenerate();
  }

  function syncDisplayNames() {
    ['reportType','contractor','seller','currency','user','department'].forEach(function(k) {
      var sel = document.getElementById('cs_' + (k === 'reportType' ? 'reportType' : k));
      var name = displayNames[k];
      var hid = document.querySelector('input[name="' + name + '"]');
      if (sel && hid) {
        var idx = sel.selectedIndex;
        hid.value = idx >= 0 && sel.options[idx] ? sel.options[idx].textContent : '';
      }
    });
  }

  function initDropdowns() {
    ['reportType','contractor','seller','currency','user','department'].forEach(function(k) {
      var selId = 'cs_' + (k === 'reportType' ? 'reportType' : k);
      var autoFirst = (k === 'reportType');
      fetchList(listUrls[k]).then(function(items) {
        populateSelect(selId, items, autoFirst);
      }).catch(function(){ checkCanGenerate(); });
    });
  }

  function loadContracts() {
    var ctrId = (document.getElementById('cs_contractor') || {}).value || '';
    if (!ctrId) {
      populateSelect('cs_contract', [], false);
      return;
    }
    var url = contractsBaseUrl + (contractsBaseUrl.indexOf('?') >= 0 ? '&' : '?') + 'ctr_id=' + encodeURIComponent(ctrId) + '&have_all=true&allCon=1';
    fetchList(url).then(function(items) { populateSelect('cs_contract', items, false); }).catch(function(){ checkCanGenerate(); });
  }

  function checkCanGenerate() {
    var rptEl = document.getElementById('cs_reportType');
    var ctrEl = document.getElementById('cs_contractor');
    var rpt = (rptEl || {}).value || '';
    var ctr = (ctrEl || {}).value || '';
    var ok = rpt.trim() !== '' && ctr.trim() !== '';
    var genBtn = document.getElementById('cs_generate');
    var excelBtn = document.getElementById('cs_excel');
    if (genBtn) genBtn.disabled = !ok;
    if (excelBtn) excelBtn.disabled = !ok;
  }

  function setStatus(txt, err) {
    var el = document.getElementById('cs_status');
    if (el) { el.textContent = txt || ''; el.style.color = err ? '#721c24' : '#666'; }
  }

  function onGenerateSubmit() {
    syncDisplayNames();
    setStatus('Формирование…');
  }

  function onGenerateDone() {
    var frame = document.getElementById('calc-state-grid-frame');
    if (frame) {
      frame.src = gridUrl + (gridUrl.indexOf('?') >= 0 ? '&' : '?') + 't=' + Date.now();
      setStatus('Результаты готовы');
    }
  }

  ['cs_reportType','cs_contractor','cs_seller','cs_contract','cs_currency','cs_user','cs_department'].forEach(function(id) {
    var el = document.getElementById(id);
    if (el) el.addEventListener('change', function() {
      syncDisplayNames();
      if (id === 'cs_contractor') loadContracts();
      checkCanGenerate();
    });
  });

  document.getElementById('cs_clear').onclick = function() {
    document.getElementById('calcStateFilterForm').reset();
    ['cs_reportType','cs_contractor','cs_seller','cs_contract','cs_currency','cs_user','cs_department'].forEach(function(id) {
      var s = document.getElementById(id);
      if (s) s.innerHTML = '<option value="">—</option>';
    });
    initDropdowns();
    loadContracts();
    checkCanGenerate();
    setStatus('Выберите вид отчёта и контрагента');
  };

  document.getElementById('calcStateFilterForm').onsubmit = onGenerateSubmit;
  document.getElementById('calcStateSubmitFrame').onload = onGenerateDone;
  document.getElementById('cs_excel').onclick = function() {
    var ifr = document.createElement('iframe');
    ifr.style.display = 'none';
    ifr.src = excelUrl;
    document.body.appendChild(ifr);
    setTimeout(function() { document.body.removeChild(ifr); }, 5000);
  };

  initDropdowns();
  loadContracts();
  checkCanGenerate();
})();
</script>
