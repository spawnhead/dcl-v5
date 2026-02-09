/**
 * Create AG Grid. Supports:
 * - infinite: fetchRows(startRow, endRow) for server-side paging (default).
 * - clientSide: full data in one request; set rowData via gridApi.setRowData() after fetch (parity with legacy full report).
 */
export function createGrid(container, columnDefs, opts) {
  const pageSize = (opts && opts.pageSize) || 50;
  const fetchRows = (opts && opts.fetchRows) || (async () => ({ rows: [], total: 0 }));
  const getRowClass = opts && opts.getRowClass;
  const onGridReady = opts && opts.onGridReady;
  const useClientSide = !!(opts && opts.useClientSide);

  const gridOptions = {
    columnDefs,
    defaultColDef: { sortable: true, resizable: true, filter: true },
    rowHeight: 24,
    headerHeight: 28,
    overlayLoadingTemplate:
      '<span class="ui-loading-overlay-inline"><span class="ui-loading-spinner"></span> Загрузка...</span>',
    overlayNoRowsTemplate:
      '<span class="ui-no-rows">Нет данных</span>',
    getRowClass,
    onGridReady
  };

  if (useClientSide) {
    gridOptions.rowModelType = "clientSide";
    gridOptions.rowData = [];
  } else {
    gridOptions.rowModelType = "infinite";
    gridOptions.cacheBlockSize = pageSize;
    gridOptions.maxBlocksInCache = 2;
    gridOptions.datasource = {
      getRows: async function (params) {
        const startRow = params.startRow || 0;
        const endRow = params.endRow || (startRow + pageSize);
        try {
          const res = await fetchRows({
            startRow,
            endRow,
            sortModel: params.sortModel || [],
            filterModel: params.filterModel || {}
          });
          const rows = res && res.rows ? res.rows : [];
          const total = res && typeof res.total === "number" ? res.total : rows.length;
          params.successCallback(rows, total);
        } catch (e) {
          params.failCallback();
        }
      }
    };
  }

  new agGrid.Grid(container, gridOptions);
  return gridOptions;
}
