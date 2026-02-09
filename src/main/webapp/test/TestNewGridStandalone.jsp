<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%
  String ctx = request.getContextPath();
  // Use /trusted mapping so standalone page can work without prior login (dev/test).
  String dataUrl = ctx + "/trusted/TestNewGridAction.do?dispatch=data";
%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>test_newGrid — DCL</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid.min.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-theme-alpine.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css"/>
  <style>
    * { box-sizing: border-box; }
    html, body { height: 100%; }
    body {
      font-family: Tahoma, Arial, sans-serif;
      margin: 0;
      padding: 12px;
      background: #f5f5f5;
      height: 100vh;
      display: flex;
      flex-direction: column;
      overflow: hidden;
    }
    .back { margin-bottom: 12px; }
    .back a { color: #0066cc; text-decoration: none; }
    .back a:hover { text-decoration: underline; }
    .test-new-grid-page { display: flex; flex-direction: column; flex: 1 1 auto; min-height: 0; }
    .toolbar { margin-bottom: 8px; display: flex; flex-wrap: wrap; gap: 6px 10px; align-items: center; }
    .toolbar label { margin-left: 12px; }
    .toolbar .date-box { display: inline-flex; align-items: center; gap: 6px; }
    .toolbar .date-label { margin-right: 2px; font-size: 12px; color: #333; }
    .toolbar .ctl-label { font-size: 12px; color: #333; margin-right: 4px; }
    .toolbar select { padding: 3px 6px; }

    /* Buttons */
    .btn {
      display: inline-block;
      border: 1px solid #b5b5b5;
      border-radius: 4px;
      background: #fff;
      color: #222;
      padding: 4px 10px;
      font-size: 12px;
      cursor: pointer;
      user-select: none;
    }
    .btn:hover { filter: brightness(0.97); }
    .btn:active { filter: brightness(0.93); }
    .btn:disabled { opacity: 0.6; cursor: default; }
    .btn-primary { background: #0d6efd; border-color: #0d6efd; color: #fff; }
    .btn-secondary { background: #6c757d; border-color: #6c757d; color: #fff; }
    .btn-danger { background: #dc3545; border-color: #dc3545; color: #fff; }
    .btn-ghost { background: #f8f9fa; }
    .btn-mini { padding: 3px 8px; }
    #ordDateBtn {
      display: inline-block;
      width: 22px;
      height: 22px;
      border: 1px solid #888;
      border-radius: 3px;
      background: #fff;
      cursor: pointer;
      vertical-align: middle;
      user-select: none;
    }
    #ordDateBtn:after {
      content: "📅";
      display: block;
      text-align: center;
      font-size: 14px;
      line-height: 20px;
    }
    #ordDateBtn:hover { background: #f0f0f0; }
    #testNewGridStatus { min-height: 20px; margin-bottom: 4px; font-size: 12px; color: #666; }
    #testNewGridGrid { flex: 1 1 auto; min-height: 260px; width: 100%; }

    /* Fix overlap: force sane line-height inside AG Grid (some global CSS set it to 40px) */
    #testNewGridGrid.ag-theme-alpine { line-height: normal; }
    #testNewGridGrid .ag-cell,
    #testNewGridGrid .ag-cell-value,
    #testNewGridGrid .ag-header-cell-text {
      line-height: 18px !important;
    }
  </style>
</head>
<body>
  <div class="back"><a href="<%= ctx %>/">← Назад в приложение</a></div>
  <div class="test-new-grid-page">
    <p style="margin-bottom:8px;">test_newGrid — тест грида (AG Grid Community). Данные (заказы) из БД по API; фильтр по дате через современный datepicker.</p>
    <div class="toolbar">
      <span class="date-label">Дата заказа:</span>
      <span class="date-box">
        <input type="text" id="ordDate" name="ordDate" value="" style="width:110px; padding:3px 6px;" placeholder="дд.мм.гггг"/>
        <span id="ordDateBtn" tabindex="0" title="Выбрать дату"></span>
        <button type="button" class="btn btn-ghost btn-mini" id="testNewGridToday">Сегодня</button>
        <button type="button" class="btn btn-ghost btn-mini" id="testNewGridYesterday">Вчера</button>
      </span>
      <span style="margin-left:6px;">
        <span class="ctl-label">Грузить:</span>
        <select id="testNewGridLimit" title="Сколько строк грузить с сервера">
          <option value="50">50</option>
          <option value="100">100</option>
          <option value="200" selected="selected">200</option>
          <option value="500">500</option>
        </select>
      </span>
      <span>
        <span class="ctl-label">Показывать:</span>
        <select id="testNewGridPageSize" title="Сколько строк показывать на странице">
          <option value="25">25</option>
          <option value="50" selected="selected">50</option>
          <option value="100">100</option>
          <option value="200">200</option>
        </select>
      </span>
      <button type="button" class="btn btn-primary" id="testNewGridApply">Применить</button>
      <button type="button" class="btn btn-ghost" id="testNewGridClearDate">Сбросить дату</button>
      <button type="button" class="btn btn-secondary" id="testNewGridRefresh">Обновить</button>
      <button type="button" class="btn btn-ghost" id="testNewGridClearFilter">Сбросить фильтр</button>
      <button type="button" class="btn btn-secondary" id="testNewGridExport">Экспорт CSV</button>
      <label><input type="checkbox" id="testNewGridRowSelect" checked="checked"/> Выделение строк</label>
    </div>
    <div id="testNewGridStatus"></div>
    <div id="testNewGridGrid" class="ag-theme-alpine"></div>
  </div>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/ag-grid/26.0.0/ag-grid-community.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
  <script>
  (function() {
    // --- DEBUG MODE instrumentation (do not remove until verified) ---
    // Hypotheses:
    // H1: rowHeight (24) is smaller than actual computed line-height/font-size due to global CSS / OS scaling -> visual overlap
    // H2: some global CSS makes ag-grid cells overflow visible (or changes positioning), so text bleeds into next row
    // H3: theme CSS is missing/overridden -> row/cell heights are not what AG Grid expects
    // H4: rows render but container/cell heights differ (e.g., due to box-sizing/padding) -> mismatch

    var dataUrl = '<%= dataUrl %>';
    var statusEl = document.getElementById('testNewGridStatus');
    var gridEl = document.getElementById('testNewGridGrid');
    var ordDateEl = document.getElementById('ordDate');
    var ordDateBtn = document.getElementById('ordDateBtn');
    var limitEl = document.getElementById('testNewGridLimit');
    var pageSizeEl = document.getElementById('testNewGridPageSize');
    var gridApi = null;
    var fp = null; // flatpickr instance

    // #region agent log (H1/H2/H3) - env + initial UI styles
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
      method:'POST',headers:{'Content-Type':'application/json'},
      body:JSON.stringify({
        sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H1',
        location:'TestNewGridStandalone.jsp:boot',message:'env/ui snapshot',
        data:(function(){
          function bg(id){var el=document.getElementById(id); if(!el) return null; var cs=window.getComputedStyle(el); return {bg:cs.backgroundColor,color:cs.color,border:cs.borderColor};}
          var vv = window.visualViewport ? {scale: window.visualViewport.scale, width: window.visualViewport.width, height: window.visualViewport.height} : null;
          return {
            href: (location && location.href) ? String(location.href) : null,
            dpr: window.devicePixelRatio || null,
            viewport: {w: window.innerWidth || null, h: window.innerHeight || null},
            visualViewport: vv,
            bodyFont: (function(){ var cs=window.getComputedStyle(document.body); return {fontSize: cs.fontSize, lineHeight: cs.lineHeight}; })(),
            stylesheets: (function(){ try { return (document.styleSheets||[]).length; } catch(e){ return null; } })(),
            buttons: {apply:bg('testNewGridApply'), refresh:bg('testNewGridRefresh'), exportCsv:bg('testNewGridExport'), clearDate:bg('testNewGridClearDate')}
          };
        })(),
        timestamp:Date.now()
      })
    }).catch(()=>{});
    // #endregion

    function setStatus(text, isError) {
      if (!statusEl) return;
      statusEl.textContent = text || '';
      statusEl.style.color = isError ? '#721c24' : '#666';
    }

    var columnDefs = [
      { field: 'ord_id', headerName: 'Order ID', filter: true, minWidth: 90 },
      { field: 'ord_number', headerName: 'Number', filter: true, minWidth: 120 },
      { field: 'ord_date', headerName: 'Date', filter: true, minWidth: 110 },
      { field: 'contractor_name', headerName: 'Contractor', filter: true, minWidth: 180 },
      { field: 'contractor_for_name', headerName: 'Consignee', filter: true, minWidth: 180 }
    ];

    var gridOptions = {
      columnDefs: columnDefs,
      defaultColDef: { sortable: true, resizable: true, filter: true },
      pagination: true,
      paginationPageSize: 50,
      headerHeight: 28,
      rowHeight: 24,
      rowSelection: 'multiple',
      suppressRowClickSelection: true,
      onGridReady: function(params) {
        gridApi = params.api;
        // #region agent log (H1/H3/H4) - grid ready config
        fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
          method:'POST',headers:{'Content-Type':'application/json'},
          body:JSON.stringify({
            sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H4',
            location:'TestNewGridStandalone.jsp:onGridReady',message:'grid ready',
            data:{rowHeight:gridOptions.rowHeight,headerHeight:gridOptions.headerHeight,paginationPageSize:gridOptions.paginationPageSize,theme:(gridEl && gridEl.className)||null},
            timestamp:Date.now()
          })
        }).catch(()=>{});
        // #endregion

        try {
          var ps0 = pageSizeEl ? parseInt(pageSizeEl.value, 10) : 50;
          if (gridApi && gridApi.paginationSetPageSize && ps0) {
            gridApi.paginationSetPageSize(ps0);
          }
        } catch (e) {}
        loadData();
      }
    };

    var STUB_DATA = [
      { ord_id: '1', ord_number: 'A-001', ord_date: '2026-02-04', contractor_name: 'Тестовый контрагент', contractor_for_name: 'Тестовый получатель' }
    ];

    function buildUrl() {
      var d = (ordDateEl && ordDateEl.value) ? ordDateEl.value.trim() : '';
      var lim = limitEl ? parseInt(limitEl.value, 10) : 200;
      if (!lim || lim < 1) lim = 200;
      var url = dataUrl + '&limit=' + encodeURIComponent(String(lim));
      if (!d) return url;
      // IMPORTANT: don't use 'ord_date' param напрямую — Struts/BeanUtils попытается
      // заполнить form.ord_date (java.sql.Date) из строки и упадёт ConversionException.
      // Передаём UI-дату отдельным параметром и парсим её вручную в Action.
      return url + '&ord_date_s=' + encodeURIComponent(d);
    }

    function loadData() {
      setStatus('Загрузка…');
      fetch(buildUrl(), { credentials: 'same-origin' })
        .then(function(r) {
          if (!r.ok) throw new Error('HTTP ' + r.status);
          return r.json();
        })
        .then(function(json) {
          if (json.error) {
            setStatus('Ошибка API: ' + json.error + '. Показаны тестовые данные.', true);
            if (gridApi) gridApi.setRowData(STUB_DATA);
            // #region agent log (H1/H3) - API returned error shape
            fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
              method:'POST',headers:{'Content-Type':'application/json'},
              body:JSON.stringify({
                sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H3',
                location:'TestNewGridStandalone.jsp:loadData.then',message:'api json.error -> stub',
                data:{hasGrid:!!gridApi,stubRows:STUB_DATA.length},
                timestamp:Date.now()
              })
            }).catch(()=>{});
            // #endregion
            return;
          }
          var rows = json.data || [];
          setStatus('Загружено строк: ' + rows.length);
          if (gridApi) gridApi.setRowData(rows);
          // #region agent log (H1/H4) - data loaded count
          fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
            method:'POST',headers:{'Content-Type':'application/json'},
            body:JSON.stringify({
              sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H1',
              location:'TestNewGridStandalone.jsp:loadData.then',message:'rows set',
              data:{rows:rows.length,hasGrid:!!gridApi},
              timestamp:Date.now()
            })
          }).catch(()=>{});
          // #endregion
        })
        .catch(function(err) {
          var msg = err.message || String(err);
          setStatus('API недоступен (' + msg + '). Показаны тестовые данные.', true);
          if (gridApi) gridApi.setRowData(STUB_DATA);
          // #region agent log (H3) - fetch/network error
          fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
            method:'POST',headers:{'Content-Type':'application/json'},
            body:JSON.stringify({
              sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H3',
              location:'TestNewGridStandalone.jsp:loadData.catch',message:'fetch failed -> stub',
              data:{err:msg,hasGrid:!!gridApi,stubRows:STUB_DATA.length},
              timestamp:Date.now()
            })
          }).catch(()=>{});
          // #endregion
        });
    }

    // #region agent log (H1/H2/H4) - first render computed sizes/styles
    gridOptions.onFirstDataRendered = function() {
      try {
        var row = document.querySelector('.ag-center-cols-container .ag-row');
        var cell = document.querySelector('.ag-center-cols-container .ag-row .ag-cell');
        var val = document.querySelector('.ag-center-cols-container .ag-row .ag-cell-value') || document.querySelector('.ag-center-cols-container .ag-row .ag-cell');
        var rowRect = row ? row.getBoundingClientRect() : null;
        var cellRect = cell ? cell.getBoundingClientRect() : null;
        var csVal = val ? window.getComputedStyle(val) : null;
        var csCell = cell ? window.getComputedStyle(cell) : null;
        var lh = csVal ? csVal.lineHeight : null;
        var fs = csVal ? csVal.fontSize : null;
        var ov = csVal ? csVal.overflow : null;
        var ovY = csVal ? csVal.overflowY : null;
        var pos = csVal ? csVal.position : null;
        var risk = (function(){
          var rh = rowRect ? rowRect.height : null;
          var lhN = lh && lh.indexOf('px') > 0 ? parseFloat(lh) : null;
          var fsN = fs && fs.indexOf('px') > 0 ? parseFloat(fs) : null;
          if (!rh || (!lhN && !fsN)) return null;
          return {
            rowH: rh,
            lineH: lhN,
            fontSize: fsN,
            lineHgtRow: (lhN != null) ? (lhN > rh + 0.5) : null,
            fontPlusPadGtRow: (fsN != null) ? ((fsN + 8) > rh + 0.5) : null
          };
        })();
        fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
          method:'POST',headers:{'Content-Type':'application/json'},
          body:JSON.stringify({
            sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H2',
            location:'TestNewGridStandalone.jsp:onFirstDataRendered',message:'computed row/cell styles',
            data:{
              rowRect: rowRect ? {h: rowRect.height, top: rowRect.top} : null,
              cellRect: cellRect ? {h: cellRect.height} : null,
              valStyle: csVal ? {fontSize: fs, lineHeight: lh, overflow: ov, overflowY: ovY, position: pos, paddingTop: csVal.paddingTop, paddingBottom: csVal.paddingBottom} : null,
              cellStyle: csCell ? {overflow: csCell.overflow, overflowY: csCell.overflowY, paddingTop: csCell.paddingTop, paddingBottom: csCell.paddingBottom} : null,
              risk: risk
            },
            timestamp:Date.now()
          })
        }).catch(()=>{});
      } catch (e) {}
    };
    // #endregion

    function initDatePicker() {
      try {
        // #region agent log (H3) - datepicker availability
        fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
          method:'POST',headers:{'Content-Type':'application/json'},
          body:JSON.stringify({
            sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H3',
            location:'TestNewGridStandalone.jsp:initDatePicker',message:'flatpickr presence',
            data:{hasInput:!!ordDateEl,hasBtn:!!ordDateBtn,flatpickrType:(typeof flatpickr)},
            timestamp:Date.now()
          })
        }).catch(()=>{});
        // #endregion

        if (!ordDateEl || typeof flatpickr !== 'function') return;
        fp = flatpickr(ordDateEl, {
          dateFormat: 'd.m.Y',
          allowInput: true,
          clickOpens: true,
          onChange: function(selectedDates, dateStr) {
            if (ordDateEl && typeof dateStr === 'string') ordDateEl.value = dateStr;
            loadData();
          }
        });
        // #region agent log (H3) - datepicker initialized
        fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{
          method:'POST',headers:{'Content-Type':'application/json'},
          body:JSON.stringify({
            sessionId:'debug-session',runId:'pre-fix',hypothesisId:'H3',
            location:'TestNewGridStandalone.jsp:initDatePicker',message:'flatpickr initialized',
            data:{fpOk:!!fp,clickOpens:!!(fp && fp.open),setDate:!!(fp && fp.setDate)},
            timestamp:Date.now()
          })
        }).catch(()=>{});
        // #endregion

        if (ordDateBtn) {
          ordDateBtn.onclick = function() { try { if (fp && fp.open) fp.open(); } catch (ignore) {} };
          ordDateBtn.onkeydown = function(e) {
            var k = (e && (e.key || e.keyCode)) || null;
            if (k === 'Enter' || k === ' ' || k === 13 || k === 32) {
              try { if (fp && fp.open) fp.open(); } catch (ignore2) {}
              if (e && e.preventDefault) e.preventDefault();
              return false;
            }
          };
        }
      } catch (e) {}
    }

    function pad2(n) { return (n < 10 ? '0' : '') + n; }
    function fmtDDMMYYYY(dt) {
      return pad2(dt.getDate()) + '.' + pad2(dt.getMonth() + 1) + '.' + dt.getFullYear();
    }
    document.getElementById('testNewGridToday').onclick = function() {
      var d0 = new Date();
      if (fp && fp.setDate) fp.setDate(d0, true);
      else if (ordDateEl) ordDateEl.value = fmtDDMMYYYY(d0);
      loadData();
    };
    document.getElementById('testNewGridYesterday').onclick = function() {
      var d = new Date();
      d.setDate(d.getDate() - 1);
      if (fp && fp.setDate) fp.setDate(d, true);
      else if (ordDateEl) ordDateEl.value = fmtDDMMYYYY(d);
      loadData();
    };

    document.getElementById('testNewGridApply').onclick = loadData;
    document.getElementById('testNewGridClearDate').onclick = function() {
      if (fp && fp.clear) fp.clear();
      else if (ordDateEl) ordDateEl.value = '';
      loadData();
    };
    document.getElementById('testNewGridRefresh').onclick = loadData;
    document.getElementById('testNewGridClearFilter').onclick = function() {
      if (gridApi) { gridApi.setFilterModel(null); setStatus('Фильтр сброшен'); }
    };
    document.getElementById('testNewGridExport').onclick = function() {
      if (gridApi) { gridApi.exportDataAsCsv({ fileName: 'test_newGrid_export.csv' }); setStatus('Экспорт CSV запущен'); }
    };
    document.getElementById('testNewGridRowSelect').onchange = function() {
      if (!gridApi) return;
      gridApi.deselectAll();
      gridApi.setGridOption('rowSelection', this.checked ? 'multiple' : 'single');
    };

    if (limitEl) {
      limitEl.onchange = loadData;
    }
    if (pageSizeEl) {
      pageSizeEl.onchange = function() {
        var ps = parseInt(this.value, 10);
        if (gridApi && gridApi.paginationSetPageSize && ps) {
          gridApi.paginationSetPageSize(ps);
          setStatus('Показывать на странице: ' + ps);
        }
      };
    }

    initDatePicker();
    new agGrid.Grid(gridEl, gridOptions);
  })();
  </script>
</body>
</html>
