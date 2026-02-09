<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%
  String ctx = request.getContextPath();
  String dataUrl = response.encodeURL(ctx + "/CalculationStateDevData.do");
  String sessionId = (session != null) ? session.getId() : null;
  if (sessionId != null && dataUrl.indexOf(";jsessionid=") == -1) {
    int q = dataUrl.indexOf('?');
    dataUrl = (q >= 0) ? dataUrl.substring(0, q) + ";jsessionid=" + sessionId + dataUrl.substring(q) : dataUrl + ";jsessionid=" + sessionId;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Состояние расчетов — Grid</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid.min.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-theme-alpine.min.css"/>
  <style>
    * { box-sizing: border-box; }
    html, body { height: 100%; margin: 0; }
    body { padding: 10px; font-family: Tahoma, Arial, sans-serif; background: #f5f5f5; height: 100vh; display: flex; flex-direction: column; overflow: hidden; }
    .toolbar { display: flex; align-items: center; flex-wrap: wrap; gap: 8px 10px; padding: 8px 10px; background: #fff; border: 1px solid #e3e6ea; border-radius: 10px; margin-bottom: 8px; }
    .toolbar .spacer { flex: 1 1 auto; }
    .toolbar .label { font-size: 12px; color: #667085; margin-right: 4px; }
    .toolbar select { padding: 6px 10px; border: 1px solid #d0d5dd; border-radius: 8px; font-size: 12px; background: #fff; }
    .btn { border: 1px solid #b5b5b5; border-radius: 8px; background: #fff; color: #222; padding: 7px 12px; font-size: 12px; cursor: pointer; }
    .btn:hover { filter: brightness(0.97); }
    .btn-secondary { background: #6c757d; border-color: #6c757d; color: #fff; }
    .btn-ghost { background: #f8f9fa; border-color: #d0d5dd; }
    #status { font-size: 12px; color: #666; }
    #grid { flex: 1 1 auto; min-height: 260px; width: 100%; }
    #grid.ag-theme-alpine { line-height: normal; }
    #grid .ag-cell, #grid .ag-header-cell-text { line-height: 18px !important; }
    .cs-itog .ag-cell { font-weight: 700; }
    .cs-ctr .ag-cell { font-weight: 600; }
  </style>
</head>
<body>
  <div class="toolbar">
    <span id="status">Загрузка…</span>
    <span class="spacer"></span>
    <span class="label">Грузить:</span>
    <select id="limit">
      <option value="50">50</option>
      <option value="100">100</option>
      <option value="200" selected>200</option>
      <option value="500">500</option>
      <option value="1000">1000</option>
    </select>
    <span class="label">На странице:</span>
    <select id="pageSize">
      <option value="25">25</option>
      <option value="50" selected>50</option>
      <option value="100">100</option>
    </select>
    <input type="text" id="quick" placeholder="Поиск…" style="padding:6px 10px;border:1px solid #d0d5dd;border-radius:8px;min-width:120px;"/>
    <button type="button" class="btn btn-ghost" id="refresh">Обновить</button>
    <button type="button" class="btn btn-secondary" id="exportCsv">Экспорт CSV</button>
  </div>
  <div id="grid" class="ag-theme-alpine"></div>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid-community.min.js"></script>
  <script>
  (function() {
    var dataUrl = '<%= dataUrl %>';
    var statusEl = document.getElementById('status');
    var gridEl = document.getElementById('grid');
    var gridApi = null, colApi = null;

    function setStatus(text, isError) { if (statusEl) { statusEl.textContent = text || ''; statusEl.style.color = isError ? '#721c24' : '#666'; } }

    function loadData() {
      setStatus('Загрузка…');
      var lim = parseInt(document.getElementById('limit').value, 10) || 200;
      fetch(dataUrl + '&limit=' + lim, { credentials: 'same-origin' })
        .then(function(r) {
          if (!r.ok) throw new Error('HTTP ' + r.status);
          var ct = (r.headers.get && r.headers.get('content-type')) || '';
          if (ct.indexOf('application/json') === -1) return r.text().then(function(t){ throw new Error('NOT_JSON'); });
          return r.json();
        })
        .then(function(json) {
          if (json.error) { setStatus('Ошибка: ' + json.error, true); if (gridApi) gridApi.setRowData([]); return; }
          var rows = json.data || [];
          if (gridApi) gridApi.setRowData(rows);
          var view = json.view || {};
          try {
            if (colApi) {
              colApi.setColumnVisible('con_number_date', !!view.view_contractor);
              colApi.setColumnVisible('spc_add_pay_cond', !!view.view_pay_cond);
              colApi.setColumnVisible('spc_delivery_cond', !!view.view_delivery_cond);
              colApi.setColumnVisible('spc_delivery_date_formatted', !!view.view_delivery_cond);
              colApi.setColumnVisible('shp_expiration', !!view.view_expiration);
              colApi.setColumnVisible('pay_expiration_formatted', !!view.view_expiration);
              colApi.setColumnVisible('complaint', !!view.view_complaint);
              colApi.setColumnVisible('comment', !!view.view_comment);
              colApi.setColumnVisible('managersFormatted', !!view.view_manager);
              colApi.setColumnVisible('stuff_categories', !!view.view_stuff_category);
            }
          } catch(e) {}
          var meta = json.meta || {};
          var msg = 'Результаты: ' + rows.length + ' строк';
          if (meta.rowsTotal != null && meta.limited) msg += ' (из ' + meta.rowsTotal + ')';
          setStatus(msg);
        })
        .catch(function(err) {
          setStatus('Ошибка: ' + (err.message || err), true);
          if (gridApi) gridApi.setRowData([]);
        });
    }

    var columnDefs = [
      { field: 'con_number_date', headerName: '№ договора', minWidth: 140 },
      { field: 'spc_number_date', headerName: '№ спецификации', minWidth: 140 },
      { field: 'spc_summ_formatted', headerName: '<bean:message key="CalculationState.spc_summ"/>', minWidth: 100, cellClass: 'ag-right-aligned-cell' },
      { field: 'con_currency', headerName: '<bean:message key="CalculationState.con_currency"/>', minWidth: 70 },
      { field: 'spc_add_pay_cond', headerName: '<bean:message key="CalculationState.spc_add_pay_cond"/>', minWidth: 120 },
      { field: 'spc_delivery_cond', headerName: '<bean:message key="CalculationState.spc_delivery_cond"/>', minWidth: 120 },
      { field: 'spc_delivery_date_formatted', headerName: '<bean:message key="CalculationState.spc_delivery_date"/>', minWidth: 100 },
      { field: 'shp_expiration', headerName: '<bean:message key="CalculationState.shp_expiration"/>', minWidth: 80 },
      { field: 'pay_expiration_formatted', headerName: '<bean:message key="CalculationState.pay_expiration"/>', minWidth: 80 },
      { field: 'complaint', headerName: '<bean:message key="CalculationState.complaint"/>', minWidth: 100 },
      { field: 'comment', headerName: '<bean:message key="CalculationState.comment"/>', minWidth: 120 },
      { field: 'shp_date_formatted', headerName: '<bean:message key="CalculationState.shp_date"/>', minWidth: 90 },
      { field: 'shpNumberFormatted', headerName: '<bean:message key="CalculationState.shp_number"/>', minWidth: 100 },
      { field: 'shp_summ_formatted', headerName: '<bean:message key="CalculationState.shp_summ"/>', minWidth: 100, cellClass: 'ag-right-aligned-cell' },
      { field: 'pay_date_formatted', headerName: '<bean:message key="CalculationState.pay_date"/>', minWidth: 90 },
      { field: 'pay_summ_formatted', headerName: '<bean:message key="CalculationState.pay_summ"/>', minWidth: 100, cellClass: 'ag-right-aligned-cell' },
      { field: 'shp_saldo_formatted', headerName: '<bean:message key="CalculationState.shp_saldo"/>', minWidth: 100, cellClass: 'ag-right-aligned-cell' },
      { field: 'managersFormatted', headerName: '<bean:message key="CalculationState.managers"/>', minWidth: 120 },
      { field: 'stuff_categories', headerName: '<bean:message key="CalculationState.stuff_categories"/>', minWidth: 140 }
    ];

    var gridOptions = {
      columnDefs: columnDefs,
      defaultColDef: { sortable: true, resizable: true, filter: true },
      rowHeight: 24,
      headerHeight: 28,
      pagination: true,
      paginationPageSize: 50,
      getRowClass: function(params) {
        var d = params && params.data;
        if (!d) return null;
        if (d.itogLine) return 'cs-itog';
        if (d.ctr_line) return 'cs-ctr';
        return null;
      },
      onGridReady: function(p) {
        gridApi = p.api;
        colApi = p.columnApi;
        var ps = parseInt(document.getElementById('pageSize').value, 10) || 50;
        if (gridApi && gridApi.paginationSetPageSize) gridApi.paginationSetPageSize(ps);
        loadData();
      }
    };

    document.getElementById('limit').onchange = loadData;
    document.getElementById('pageSize').onchange = function() {
      var ps = parseInt(this.value, 10);
      if (gridApi && gridApi.paginationSetPageSize && ps) gridApi.paginationSetPageSize(ps);
    };
    document.getElementById('quick').oninput = function() {
      if (gridApi && gridApi.setQuickFilter) gridApi.setQuickFilter(this.value || '');
    };
    document.getElementById('refresh').onclick = loadData;
    document.getElementById('exportCsv').onclick = function() {
      if (gridApi && gridApi.exportDataAsCsv) gridApi.exportDataAsCsv({ fileName: 'calculation_state_export.csv' });
      setStatus('Экспорт CSV запущен');
    };

    new agGrid.Grid(gridEl, gridOptions);
  })();
  </script>
</body>
</html>
