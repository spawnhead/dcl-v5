<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%
  String ctx = request.getContextPath();
  String dataUrl = response.encodeURL(ctx + "/MarginDevData.do");
%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid.min.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-theme-alpine.min.css"/>
<style>
  .mg-grid-wrap { background: #fff; border: 1px solid #e3e6ea; border-radius: 10px; padding: 10px; }
  .mg-grid-toolbar { display:flex; align-items:center; flex-wrap:wrap; gap:8px 10px; padding: 8px 10px; background:#fff; border:1px solid #e3e6ea; border-radius:10px; margin-bottom:8px; }
  .mg-grid-toolbar .spacer { flex: 1 1 auto; }
  .mg-grid-toolbar .label { font-size: 12px; color: #667085; margin-right: 4px; }
  .mg-grid-toolbar input[type="text"] { padding: 6px 10px; border: 1px solid #d0d5dd; border-radius: 8px; font-size: 12px; min-width: 180px; }
  .mg-grid-toolbar select { padding: 6px 10px; border: 1px solid #d0d5dd; border-radius: 8px; font-size: 12px; background: #fff; }
  .mg-grid-toolbar .btn { border: 1px solid #b5b5b5; border-radius: 8px; background: #fff; color: #222; padding: 7px 12px; font-size: 12px; cursor: pointer; user-select: none; }
  .mg-grid-toolbar .btn:hover { filter: brightness(0.97); }
  .mg-grid-toolbar .btn:active { filter: brightness(0.93); }
  .mg-grid-toolbar .btn-secondary { background: #6c757d; border-color: #6c757d; color: #fff; }
  .mg-grid-toolbar .btn-ghost { background: #f8f9fa; border-color: #d0d5dd; }
  #mgGridStatus { font-size: 12px; color: #666; margin-left: 2px; }
  #mgGrid { width: 100%; min-height: 650px; height: calc(100vh - 420px); }
  #mgGrid.ag-theme-alpine { line-height: normal; }
  #mgGrid .ag-cell, #mgGrid .ag-cell-value, #mgGrid .ag-header-cell-text { line-height: 18px !important; }
  .mg-itog .ag-cell { font-weight: 700; }
  .mg-green .ag-cell { background: #e9f7ef; }
  .mg-pink .ag-cell { background: #fdecef; }
  .mg-green-pink .ag-cell { background: linear-gradient(90deg, #e9f7ef 0%, #fdecef 100%); }
</style>

<div class="mg-grid-wrap">
  <div class="mg-grid-toolbar">
    <span id="mgGridStatus">Загрузка…</span>
    <span class="spacer"></span>
    <span class="label">Грузить:</span>
    <select id="mgGridLimit" title="Сколько строк грузить с сервера">
      <option value="50">50</option>
      <option value="100">100</option>
      <option value="200" selected="selected">200</option>
      <option value="500">500</option>
      <option value="1000">1000</option>
    </select>
    <span class="label">Показывать:</span>
    <select id="mgGridPageSize" title="Сколько строк на странице">
      <option value="25">25</option>
      <option value="50" selected="selected">50</option>
      <option value="100">100</option>
      <option value="200">200</option>
    </select>
    <input type="text" id="mgGridQuick" placeholder="Поиск…" title="Быстрый поиск по всем колонкам"/>
    <button type="button" class="btn btn-ghost" id="mgGridRefresh">Обновить</button>
    <button type="button" class="btn btn-secondary" id="mgGridExportCsv">Экспорт CSV</button>
  </div>
  <div id="mgGrid" class="ag-theme-alpine"></div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid-community.min.js"></script>
<script>
(function() {
  var dataUrl = '<%= dataUrl %>';
  var statusEl = document.getElementById('mgGridStatus');
  var gridEl = document.getElementById('mgGrid');
  var limitEl = document.getElementById('mgGridLimit');
  var pageSizeEl = document.getElementById('mgGridPageSize');
  var quickEl = document.getElementById('mgGridQuick');
  var refreshBtn = document.getElementById('mgGridRefresh');
  var exportBtn = document.getElementById('mgGridExportCsv');

  var gridApi = null;
  var colApi = null;

  function setStatus(text, isError) {
    if (!statusEl) return;
    statusEl.textContent = text || '';
    statusEl.style.color = isError ? '#721c24' : '#666';
  }

  function buildUrl() {
    var lim = limitEl ? parseInt(limitEl.value, 10) : 200;
    if (!lim || lim < 1) lim = 200;
    return dataUrl + '&limit=' + encodeURIComponent(String(lim));
  }

  function applyView(view) {
    if (!colApi || !view) return;
    function v(k) { return !!view[k]; }
    colApi.setColumnVisible('ctr_name', v('view_contractor'));
    colApi.setColumnVisible('cut_name', v('view_country'));
    colApi.setColumnVisible('con_number_formatted', v('view_contract'));
    colApi.setColumnVisible('con_date_formatted', v('view_contract'));
    colApi.setColumnVisible('spc_number_formatted', v('view_contract'));
    colApi.setColumnVisible('spc_date_formatted', v('view_contract'));
    colApi.setColumnVisible('spc_summ_formatted', v('view_contract'));
    colApi.setColumnVisible('cur_name', v('view_contract'));
    colApi.setColumnVisible('stf_name_show', v('view_stuff_category'));
    colApi.setColumnVisible('shp_number_show', v('view_shipping'));
    colApi.setColumnVisible('shp_date_show', v('view_shipping'));
    colApi.setColumnVisible('pay_date_show', v('view_payment'));
    colApi.setColumnVisible('lps_sum_transport_formatted', v('view_transport'));
    colApi.setColumnVisible('lcc_transport_formatted', v('view_transport_sum'));
    colApi.setColumnVisible('lps_custom_formatted', v('view_custom'));
    colApi.setColumnVisible('lcc_charges_formatted', v('view_other_sum'));
    colApi.setColumnVisible('lcc_montage_formatted', v('view_montage_sum'));
    colApi.setColumnVisible('lps_montage_time_formatted', v('view_montage_time'));
    colApi.setColumnVisible('montage_cost_formatted', v('view_montage_cost'));
    colApi.setColumnVisible('lcc_update_sum_formatted', v('view_update_sum'));
    colApi.setColumnVisible('summ_zak_formatted', v('view_summ_zak'));
    colApi.setColumnVisible('koeff_formatted', v('view_koeff'));
    colApi.setColumnVisible('usr_name_show', v('view_user'));
    colApi.setColumnVisible('dep_name_show', v('view_department'));
  }

  function loadData() {
    setStatus('Загрузка…');
    fetch(buildUrl(), { credentials: 'same-origin' })
      .then(function(r) { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); })
      .then(function(json) {
        if (json.error) {
          setStatus('Ошибка API: ' + json.error, true);
          if (gridApi) gridApi.setRowData([]);
          return;
        }
        var rows = json.data || [];
        if (gridApi) gridApi.setRowData(rows);
        applyView(json.view || {});
        var meta = json.meta || {};
        var total = (typeof meta.rowsTotal === 'number') ? meta.rowsTotal : rows.length;
        var limited = meta && meta.limited ? ' (ограничено)' : '';
        setStatus('Результаты: ' + rows.length + ' строк из ' + total + limited);
      })
      .catch(function(err) {
        var msg = err && err.message ? err.message : String(err);
        setStatus('API недоступен (' + msg + ')', true);
        if (gridApi) gridApi.setRowData([]);
      });
  }

  var columnDefs = [
    { field: 'ctr_name', headerName: '<bean:message key="Margin.ctr_name"/>', minWidth: 160 },
    { field: 'cut_name', headerName: '<bean:message key="Margin.cut_name"/>', minWidth: 120 },
    { field: 'con_number_formatted', headerName: '<bean:message key="Margin.con_number"/>', minWidth: 120 },
    { field: 'con_date_formatted', headerName: '<bean:message key="Margin.con_date"/>', minWidth: 110 },
    { field: 'spc_number_formatted', headerName: '<bean:message key="Margin.spc_number"/>', minWidth: 140 },
    { field: 'spc_date_formatted', headerName: '<bean:message key="Margin.spc_date"/>', minWidth: 130 },
    { field: 'spc_summ_formatted', headerName: '<bean:message key="Margin.spc_summ"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'cur_name', headerName: '<bean:message key="Margin.cur_name"/>', minWidth: 80 },
    { field: 'stf_name_show', headerName: '<bean:message key="Margin.stf_name"/>', minWidth: 180 },
    { field: 'shp_number_show', headerName: '<bean:message key="Margin.shp_number"/>', minWidth: 120 },
    { field: 'shp_date_show', headerName: '<bean:message key="Margin.shp_date"/>', minWidth: 110 },
    { field: 'pay_date_show', headerName: '<bean:message key="Margin.pay_date"/>', minWidth: 110 },
    { field: 'lps_summ_eur_formatted', headerName: '<bean:message key="Margin.lps_summ_eur"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lps_summ_formatted', headerName: '<bean:message key="Margin.lps_summ"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lps_sum_transport_formatted', headerName: '<bean:message key="Margin.lps_sum_transport"/>', minWidth: 130, cellClass: 'ag-right-aligned-cell' },
    { field: 'lcc_transport_formatted', headerName: '<bean:message key="Margin.lcc_transport"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lps_custom_formatted', headerName: '<bean:message key="Margin.lps_custom"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lcc_charges_formatted', headerName: '<bean:message key="Margin.lcc_charges"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lcc_montage_formatted', headerName: '<bean:message key="Margin.lcc_montage"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lps_montage_time_formatted', headerName: '<bean:message key="Margin.lps_montage_time"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'montage_cost_formatted', headerName: '<bean:message key="Margin.montage_cost"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'lcc_update_sum_formatted', headerName: '<bean:message key="Margin.lcc_update_sum"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'summ_formatted', headerName: '<bean:message key="Margin.summ"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'summ_zak_formatted', headerName: '<bean:message key="Margin.summ_zak"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'margin_formatted', headerName: '<bean:message key="Margin.margin"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
    { field: 'koeff_formatted', headerName: '<bean:message key="Margin.koeff"/>', minWidth: 110, cellClass: 'ag-right-aligned-cell' },
    { field: 'usr_name_show', headerName: '<bean:message key="Margin.usr_name"/>', minWidth: 140 },
    { field: 'dep_name_show', headerName: '<bean:message key="Margin.dep_name"/>', minWidth: 140 }
  ];

  var gridOptions = {
    columnDefs: columnDefs,
    defaultColDef: { sortable: true, resizable: true, filter: true },
    pagination: true,
    paginationPageSize: 50,
    headerHeight: 28,
    rowHeight: 24,
    onGridReady: function(params) {
      gridApi = params.api;
      colApi = params.columnApi;
      try {
        var ps = pageSizeEl ? parseInt(pageSizeEl.value, 10) : 50;
        if (gridApi && gridApi.paginationSetPageSize && ps) gridApi.paginationSetPageSize(ps);
      } catch (e) {}
      loadData();
    },
    getRowClass: function(p) {
      if (!p || !p.data) return null;
      if (p.data.itogLine) return 'mg-itog';
      var hasGroup = p.data.spc_group_delivery && String(p.data.spc_group_delivery).trim() !== '';
      if (hasGroup) {
        if (p.data.haveUnblockedPrc) return 'mg-green-pink';
        return 'mg-green';
      }
      if (p.data.haveUnblockedPrc) return 'mg-pink';
      return null;
    }
  };

  new agGrid.Grid(gridEl, gridOptions);

  if (refreshBtn) refreshBtn.addEventListener('click', function(){ loadData(); });
  if (quickEl) quickEl.addEventListener('input', function(){ if (gridApi) gridApi.setQuickFilter(quickEl.value || ''); });
  if (pageSizeEl) pageSizeEl.addEventListener('change', function(){ try { var ps = parseInt(pageSizeEl.value, 10); if (gridApi && gridApi.paginationSetPageSize) gridApi.paginationSetPageSize(ps); } catch(e){} });
  if (exportBtn) exportBtn.addEventListener('click', function(){ try { if (gridApi && gridApi.exportDataAsCsv) gridApi.exportDataAsCsv({ fileName: 'margin.csv' }); } catch(e){} });
})();
</script>

