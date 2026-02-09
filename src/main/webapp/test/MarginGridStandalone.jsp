<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%
  String ctx = request.getContextPath();
  String dataUrl = response.encodeURL(ctx + "/MarginDevData.do");
  String loginUrl = response.encodeURL(ctx + "/trusted/Login.do?dispatch=input");
  String sessionId = (session != null) ? session.getId() : null;
  if (sessionId != null && dataUrl.indexOf(";jsessionid=") == -1)
  {
    int q = dataUrl.indexOf('?');
    if (q >= 0)
    {
      dataUrl = dataUrl.substring(0, q) + ";jsessionid=" + sessionId + dataUrl.substring(q);
    }
    else
    {
      dataUrl = dataUrl + ";jsessionid=" + sessionId;
    }
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Margin (Dev) — Grid</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid.min.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-theme-alpine.min.css"/>
  <style>
    * { box-sizing: border-box; }
    html, body { height: 100%; }
    body {
      margin: 0;
      padding: 10px;
      font-family: Tahoma, Arial, sans-serif;
      background: #f5f5f5;
      height: 100vh;
      display: flex;
      flex-direction: column;
      overflow: hidden;
    }
    .toolbar {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      gap: 8px 10px;
      padding: 8px 10px;
      background: #fff;
      border: 1px solid #e3e6ea;
      border-radius: 10px;
      margin-bottom: 8px;
    }
    .toolbar .spacer { flex: 1 1 auto; }
    .toolbar .label { font-size: 12px; color: #667085; margin-right: 4px; }
    .toolbar input[type="text"] {
      padding: 6px 10px;
      border: 1px solid #d0d5dd;
      border-radius: 8px;
      font-size: 12px;
      min-width: 180px;
    }
    .toolbar select {
      padding: 6px 10px;
      border: 1px solid #d0d5dd;
      border-radius: 8px;
      font-size: 12px;
      background: #fff;
    }
    .btn {
      border: 1px solid #b5b5b5;
      border-radius: 8px;
      background: #fff;
      color: #222;
      padding: 7px 12px;
      font-size: 12px;
      cursor: pointer;
      user-select: none;
    }
    .btn:hover { filter: brightness(0.97); }
    .btn:active { filter: brightness(0.93); }
    .btn-secondary { background: #6c757d; border-color: #6c757d; color: #fff; }
    .btn-ghost { background: #f8f9fa; border-color: #d0d5dd; }
    #status { font-size: 12px; color: #666; margin-left: 2px; }

    #grid { flex: 1 1 auto; min-height: 260px; width: 100%; }

    /* Critical: prevent global CSS from inflating cell line-height (we saw 40px earlier) */
    #grid.ag-theme-alpine { line-height: normal; }
    #grid .ag-cell,
    #grid .ag-cell-value,
    #grid .ag-header-cell-text {
      line-height: 18px !important;
    }

    /* Row styles (legacy equivalents) */
    .mg-itog .ag-cell { font-weight: 700; }
    .mg-green .ag-cell { background: #e9f7ef; }
    .mg-pink .ag-cell { background: #fdecef; }
    .mg-green-pink .ag-cell { background: linear-gradient(90deg, #e9f7ef 0%, #fdecef 100%); }
  </style>
</head>
<body>
  <div class="toolbar">
    <span id="status">Загрузка…</span>
    <span class="spacer"></span>
    <span class="label">Грузить:</span>
    <select id="limit" title="Сколько строк грузить с сервера">
      <option value="50">50</option>
      <option value="100">100</option>
      <option value="200" selected="selected">200</option>
      <option value="500">500</option>
      <option value="1000">1000</option>
    </select>
    <span class="label">Показывать:</span>
    <select id="pageSize" title="Сколько строк на странице">
      <option value="25">25</option>
      <option value="50" selected="selected">50</option>
      <option value="100">100</option>
      <option value="200">200</option>
    </select>
    <input type="text" id="quick" placeholder="Поиск…" title="Быстрый поиск по всем колонкам"/>
    <button type="button" class="btn btn-ghost" id="refresh">Обновить</button>
    <button type="button" class="btn btn-secondary" id="exportCsv">Экспорт CSV</button>
  </div>
  <div id="grid" class="ag-theme-alpine"></div>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid-community.min.js"></script>
  <script>
  (function() {
    var dataUrl = '<%= dataUrl %>';
    var loginUrl = '<%= loginUrl %>';
    var statusEl = document.getElementById('status');
    var gridEl = document.getElementById('grid');
    var limitEl = document.getElementById('limit');
    var pageSizeEl = document.getElementById('pageSize');
    var quickEl = document.getElementById('quick');
    var refreshBtn = document.getElementById('refresh');
    var exportBtn = document.getElementById('exportCsv');

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
        .then(function(r) {
          if (!r.ok) throw new Error('HTTP ' + r.status);
          var ct = r.headers && r.headers.get ? (r.headers.get('content-type') || '') : '';
          if (ct.indexOf('application/json') === -1) {
            return r.text().then(function(html) {
              var msg = 'NOT_JSON';
              if (html) {
                var h = html.toLowerCase();
                if (h.indexOf('login.do') !== -1 || h.indexOf('name="login"') !== -1 ||
                    h.indexOf('usr_login') !== -1 || h.indexOf('usr_passwd') !== -1 ||
                    h.indexOf('/trusted/login') !== -1 || (h.indexOf('<form') !== -1 && h.indexOf('password') !== -1)) {
                  msg = 'AUTH_REQUIRED';
                } else if (html.indexOf('Нет прав') !== -1 || h.indexOf('no_permission') !== -1) {
                  msg = 'NO_PERMISSION';
                }
              }
              var err = new Error(msg);
              err._html = html;
              err._ct = ct;
              err._url = r.url || null;
              throw err;
            });
          }
          return r.json();
        })
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
          var msg = 'Результаты: ' + rows.length + ' строк';
          if (meta.rowsTotal != null && meta.rowsReturned != null && meta.limited) {
            msg += ' (показано ' + meta.rowsReturned + ' из ' + meta.rowsTotal + ')';
          }
          setStatus(msg);
        })
        .catch(function(err) {
          var msg = err && err.message ? err.message : String(err);
          if (msg === 'AUTH_REQUIRED') {
            setStatus('Сессия истекла — обновите страницу или войдите снова.', true);
            if (gridApi) gridApi.setRowData([]);
            return;
          }
          if (msg === 'NO_PERMISSION') {
            setStatus('Нет прав на доступ к данным', true);
            if (gridApi) gridApi.setRowData([]);
            return;
          }
          if (msg === 'NOT_JSON') {
            var ct = err && err._ct ? String(err._ct) : '';
            var url = err && err._url ? String(err._url) : '';
            var html = err && err._html ? String(err._html) : '';
            // short snippet to quickly see: login/no-permission/stacktrace/etc.
            var snip = html.replace(/\s+/g, ' ').trim();
            if (snip.length > 160) snip = snip.substring(0, 160) + '…';
            var extra = '';
            if (ct) extra += ' CT=' + ct;
            if (url) extra += ' URL=' + url;
            if (snip) extra += ' RESP=' + snip;
            setStatus('Сервер вернул страницу вместо JSON.' + extra, true);
            if (gridApi) gridApi.setRowData([]);
            return;
          }
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
      { field: 'shp_number_show', headerName: '<bean:message key="Margin.shp_number"/>', minWidth: 130 },
      { field: 'shp_date_show', headerName: '<bean:message key="Margin.shp_date"/>', minWidth: 120 },
      { field: 'pay_date_show', headerName: '<bean:message key="Margin.pay_date"/>', minWidth: 120 },

      { field: 'lps_summ_eur_formatted', headerName: '<bean:message key="Margin.lps_summ_eur"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
      { field: 'lps_summ_formatted', headerName: '<bean:message key="Margin.lps_summ"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
      { field: 'lps_sum_transport_formatted', headerName: '<bean:message key="Margin.lps_sum_transport"/>', minWidth: 140, cellClass: 'ag-right-aligned-cell' },
      { field: 'lcc_transport_formatted', headerName: '<bean:message key="Margin.lcc_transport"/>', minWidth: 160, cellClass: 'ag-right-aligned-cell' },
      { field: 'lps_custom_formatted', headerName: '<bean:message key="Margin.lps_custom"/>', minWidth: 130, cellClass: 'ag-right-aligned-cell' },
      { field: 'lcc_charges_formatted', headerName: '<bean:message key="Margin.lcc_charges"/>', minWidth: 130, cellClass: 'ag-right-aligned-cell' },
      { field: 'lcc_montage_formatted', headerName: '<bean:message key="Margin.lcc_montage"/>', minWidth: 130, cellClass: 'ag-right-aligned-cell' },
      { field: 'lps_montage_time_formatted', headerName: '<bean:message key="Margin.lps_montage_time"/>', minWidth: 150, cellClass: 'ag-right-aligned-cell' },
      { field: 'montage_cost_formatted', headerName: '<bean:message key="Margin.montage_cost"/>', minWidth: 170, cellClass: 'ag-right-aligned-cell' },
      { field: 'lcc_update_sum_formatted', headerName: '<bean:message key="Margin.lcc_update_sum"/>', minWidth: 150, cellClass: 'ag-right-aligned-cell' },
      { field: 'summ_formatted', headerName: '<bean:message key="Margin.summ"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
      { field: 'summ_zak_formatted', headerName: '<bean:message key="Margin.summ_zak"/>', minWidth: 140, cellClass: 'ag-right-aligned-cell' },
      { field: 'margin_formatted', headerName: '<bean:message key="Margin.margin"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
      { field: 'koeff_formatted', headerName: '<bean:message key="Margin.koeff"/>', minWidth: 120, cellClass: 'ag-right-aligned-cell' },
      { field: 'usr_name_show', headerName: '<bean:message key="Margin.usr_name"/>', minWidth: 140 },
      { field: 'dep_name_show', headerName: '<bean:message key="Margin.dep_name"/>', minWidth: 140 }
    ];

    var gridOptions = {
      columnDefs: columnDefs,
      defaultColDef: { sortable: true, resizable: true, filter: true },
      rowHeight: 24,
      headerHeight: 28,
      pagination: true,
      paginationPageSize: 50,
      getRowClass: function(params) {
        var d = params && params.data ? params.data : null;
        if (!d) return null;
        if (d.itogLine) return 'mg-itog';
        var hasGroup = d.spc_group_delivery && String(d.spc_group_delivery).trim() !== '';
        if (hasGroup) {
          if (d.haveUnblockedPrc) return 'mg-green-pink';
          return 'mg-green';
        }
        if (d.haveUnblockedPrc) return 'mg-pink';
        return null;
      },
      onGridReady: function(params) {
        gridApi = params.api;
        colApi = params.columnApi;
        try {
          var ps0 = pageSizeEl ? parseInt(pageSizeEl.value, 10) : 50;
          if (gridApi && gridApi.paginationSetPageSize && ps0) gridApi.paginationSetPageSize(ps0);
        } catch (e) {}
        loadData();
      }
    };

    if (limitEl) limitEl.onchange = loadData;
    if (pageSizeEl) pageSizeEl.onchange = function() {
      var ps = parseInt(this.value, 10);
      if (gridApi && gridApi.paginationSetPageSize && ps) {
        gridApi.paginationSetPageSize(ps);
        setStatus('Показывать на странице: ' + ps);
      }
    };
    if (quickEl) quickEl.oninput = function() {
      if (gridApi && gridApi.setQuickFilter) gridApi.setQuickFilter(this.value || '');
    };
    if (refreshBtn) refreshBtn.onclick = loadData;
    if (exportBtn) exportBtn.onclick = function() {
      if (!gridApi) return;
      gridApi.exportDataAsCsv({ fileName: 'margin_dev_export.csv' });
      setStatus('Экспорт CSV запущен');
    };

    new agGrid.Grid(gridEl, gridOptions);
  })();
  </script>
</body>
</html>

