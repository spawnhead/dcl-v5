import { apiGetJson, apiPostForm, buildQuery, fetchLegacyList } from "../api.js";
import { createGrid } from "../components/grid.js";
import { setStatus, buildOption } from "../components/toolbar.js";
import { setButtonLoading, showGridLoading, hideGridLoading } from "../shared/loading.js";

export function renderCalcState(root) {
  const panel = document.createElement("div");
  panel.className = "panel";

  const toolbar = document.createElement("div");
  toolbar.className = "toolbar";

  const reportTypeSel = selectField("Вид отчета", "cs_reportType");
  const contractorSel = selectField("Контрагент", "cs_contractor");
  const sellerSel = selectField("Продавец", "cs_seller");
  const contractSel = selectField("Договор", "cs_contract");
  const currencySel = selectField("Валюта", "cs_currency");
  const userSel = selectField("Пользователь", "cs_user");
  const depSel = selectField("Отдел", "cs_department");
  attachSelectDebug("user", userSel.select);
  attachSelectDebug("contractor", contractorSel.select);
  const earliestCheck = checkboxField("Не учитывать если дата раньше", "cs_not_include_earliest");
  const earliestDate = inputText("Дата", "cs_earliest_date");
  const notZero = checkboxField("Исключить нулевые", "cs_not_zero");
  const includeAll = checkboxField("Включить все спецификации", "cs_include_all");
  const notAnnul = checkboxField("Исключить аннулированные", "cs_not_annul");
  const notExpiredZero = checkboxField("Не показывать истекшие с нулевым балансом", "cs_not_expired_zero");
  const viewPay = checkboxField("Показать условия оплаты", "cs_view_pay");
  const viewDelivery = checkboxField("Показать условия поставки", "cs_view_delivery");
  const viewExp = checkboxField("Показать просрочки", "cs_view_exp");
  const viewComplaint = checkboxField("Показать жалобы", "cs_view_complaint");
  const viewComment = checkboxField("Показать комментарии", "cs_view_comment");
  const viewManager = checkboxField("Показать менеджеров", "cs_view_manager");
  const viewStuff = checkboxField("Показать категории", "cs_view_stuff");

  const status = document.createElement("span");
  status.className = "status";
  status.textContent = "Выберите вид отчета и контрагента";

  const btnClear = button("Очистить", false);
  const btnGenerate = button("Сформировать", true);
  const btnExcel = button("Excel", false);

  toolbar.append(
    reportTypeSel.wrap, contractorSel.wrap, sellerSel.wrap, contractSel.wrap,
    currencySel.wrap, userSel.wrap, depSel.wrap,
    earliestCheck.wrap, earliestDate.wrap,
    notZero.wrap, includeAll.wrap, notAnnul.wrap, notExpiredZero.wrap,
    viewPay.wrap, viewDelivery.wrap, viewExp.wrap, viewComplaint.wrap,
    viewComment.wrap, viewManager.wrap, viewStuff.wrap,
    status, btnClear, btnGenerate, btnExcel
  );

  panel.appendChild(toolbar);
  root.appendChild(panel);

  const gridWrap = document.createElement("div");
  gridWrap.className = "panel grid-container ag-theme-alpine";
  root.appendChild(gridWrap);

  const columnDefs = [
    { field: "con_number_date", headerName: "Договор", minWidth: 180 },
    { field: "spc_number_date", headerName: "Спецификация", minWidth: 200 },
    { field: "spc_summ_formatted", headerName: "Сумма", minWidth: 120 },
    { field: "con_currency", headerName: "Валюта", minWidth: 80 },
    { field: "spc_add_pay_cond", headerName: "Условия оплаты", minWidth: 160 },
    { field: "spc_delivery_cond", headerName: "Условия поставки", minWidth: 160 },
    { field: "spc_delivery_date_formatted", headerName: "Срок поставки", minWidth: 140 },
    { field: "shp_expiration", headerName: "Просрочка отгрузки", minWidth: 150 },
    { field: "pay_expiration_formatted", headerName: "Просрочка оплаты", minWidth: 150 },
    { field: "complaint", headerName: "Жалобы", minWidth: 140 },
    { field: "comment", headerName: "Комментарий", minWidth: 200 },
    { field: "shp_date_formatted", headerName: "Дата отгрузки", minWidth: 120 },
    { field: "shpNumberFormatted", headerName: "Отгрузка", minWidth: 140 },
    { field: "shp_summ_formatted", headerName: "Сумма отгр", minWidth: 120 },
    { field: "pay_date_formatted", headerName: "Дата оплаты", minWidth: 120 },
    { field: "pay_summ_formatted", headerName: "Сумма оплаты", minWidth: 120 },
    { field: "shp_saldo_formatted", headerName: "Сальдо", minWidth: 120 },
    { field: "managersFormatted", headerName: "Менеджеры", minWidth: 160 },
    { field: "stuff_categories", headerName: "Категории", minWidth: 160 }
  ];

  let columnApi = null;
  let gridApi = null;
  let gridDataCache = { rows: [], total: 0 };
  let hasGenerated = false;

  createGrid(gridWrap, columnDefs, {
    pageSize: 50,
    getRowClass: (params) => {
      const d = params && params.data ? params.data : null;
      if (!d) return null;
      if (d.itogLine) return "row-itog";
      if (d.ctr_line) return "row-orange";
      return null;
    },
    onGridReady: (params) => {
      gridApi = params.api;
      columnApi = params.columnApi;
    },
    fetchRows: async ({ startRow, endRow }) => {
      if (!hasGenerated) {
        return { rows: [], total: 0 };
      }
      const pageSize = endRow - startRow;
      let result = { rows: [], total: 0 };
      showGridLoading(gridApi);
      try {
        if (gridDataCache.rows.length === 0) {
          const json = await apiGetJson("/CalculationStateDevData.do" + buildQuery({ limit: 1000 }));
          gridDataCache.rows = json.data || [];
          gridDataCache.total = (json.meta && json.meta.rowsTotal) || gridDataCache.rows.length;
          applyView(columnApi, json.view || {});
        }
        const start = Math.min(startRow, gridDataCache.rows.length);
        const end = Math.min(endRow, gridDataCache.rows.length);
        result = {
          rows: gridDataCache.rows.slice(start, end),
          total: gridDataCache.total
        };
        return result;
      } catch (e) {
        setStatus(status, e.message || "Ошибка загрузки данных", true);
        return result;
      } finally {
        hideGridLoading(gridApi, result.rows);
      }
    }
  });

  function applyView(colApi, view) {
    if (!colApi || !view) return;
    const v = (k) => !!view[k];
    colApi.setColumnVisible("spc_add_pay_cond", v("view_pay_cond"));
    colApi.setColumnVisible("spc_delivery_cond", v("view_delivery_cond"));
    colApi.setColumnVisible("spc_delivery_date_formatted", v("view_delivery_cond"));
    colApi.setColumnVisible("shp_expiration", v("view_expiration"));
    colApi.setColumnVisible("pay_expiration_formatted", v("view_expiration"));
    colApi.setColumnVisible("complaint", v("view_complaint"));
    colApi.setColumnVisible("comment", v("view_comment"));
    colApi.setColumnVisible("managersFormatted", v("view_manager"));
    colApi.setColumnVisible("stuff_categories", v("view_stuff_category"));
  }

  async function loadLookups() {
    await Promise.all([
      loadSelectLegacy(reportTypeSel.select, "/CalcStateReportTypesListAction.do"),
      loadSelectLegacy(contractorSel.select, "/ContractorsListAction.do?have_all=true"),
      loadSelectLegacy(sellerSel.select, "/SellersListAction.do"),
      loadSelectLegacy(currencySel.select, "/CurrenciesListAction.do"),
      loadSelectLegacy(userSel.select, "/UsersListAction.do?have_all=true"),
      loadSelectLegacy(depSel.select, "/DepartmentsListAction.do?have_all=true")
    ]);
    // #region agent log
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H2',location:'ui/screens/calc-state.js:151',message:'calc-state loadLookups done',data:{userCount:userSel.select.options.length,ctrCount:contractorSel.select.options.length},timestamp:Date.now()})}).catch(()=>{});
    // #endregion
    await loadContracts();
  }

  async function loadContracts() {
    const ctrId = contractorSel.select.value;
    if (!ctrId) {
      contractSel.select.innerHTML = "";
      return;
    }
    const url = "/ContractsDepFromContractorListAction.do" + buildQuery({ ctr_id: ctrId, have_all: true, allCon: 1 });
    await loadSelectLegacy(contractSel.select, url);
  }

  async function loadSelectLegacy(select, url) {
    const json = await fetchLegacyList(url);
    select.innerHTML = "";
    select.appendChild(buildOption("", "—"));
    (json.data || []).forEach((it) => {
      select.appendChild(buildOption(it.id, it.label));
    });
  }

  function canGenerate() {
    const rt = reportTypeSel.select.value;
    const ctr = contractorSel.select.value;
    return rt && ctr;
  }

  function collectFormData() {
    return {
      dispatch: "generate",
      "reportType.id": reportTypeSel.select.value,
      "reportType.name": reportTypeSel.select.options[reportTypeSel.select.selectedIndex]?.textContent || "",
      "contractorCalcState.id": contractorSel.select.value,
      "contractorCalcState.name": contractorSel.select.options[contractorSel.select.selectedIndex]?.textContent || "",
      "sellerCalcState.id": sellerSel.select.value,
      "sellerCalcState.name": sellerSel.select.options[sellerSel.select.selectedIndex]?.textContent || "",
      "contract.con_id": contractSel.select.value,
      "currencyCalcState.id": currencySel.select.value,
      "currencyCalcState.name": currencySel.select.options[currencySel.select.selectedIndex]?.textContent || "",
      "userCalcState.usr_id": userSel.select.value,
      "userCalcState.userFullName": userSel.select.options[userSel.select.selectedIndex]?.textContent || "",
      "departmentCalcState.id": depSel.select.value,
      "departmentCalcState.name": depSel.select.options[depSel.select.selectedIndex]?.textContent || "",
      "not_include_if_earliest": earliestCheck.input.checked ? "1" : "",
      "earliest_doc_date": earliestDate.input.value.trim(),
      "not_include_zero": notZero.input.checked ? "1" : "",
      "include_all_specs": includeAll.input.checked ? "1" : "",
      "not_show_annul": notAnnul.input.checked ? "1" : "",
      "notShowExpiredContractZeroBalance": notExpiredZero.input.checked ? "1" : "",
      "view_pay_cond": viewPay.input.checked ? "1" : "",
      "view_delivery_cond": viewDelivery.input.checked ? "1" : "",
      "view_expiration": viewExp.input.checked ? "1" : "",
      "view_complaint": viewComplaint.input.checked ? "1" : "",
      "view_comment": viewComment.input.checked ? "1" : "",
      "view_manager": viewManager.input.checked ? "1" : "",
      "view_stuff_category": viewStuff.input.checked ? "1" : ""
    };
  }

  async function generate() {
    if (!canGenerate()) {
      setStatus(status, "Выберите вид отчета и контрагента", true);
      return;
    }
    setButtonLoading(btnGenerate, true);
    setStatus(status, "Формирование...");
    try {
      await apiPostForm("/CalculationStateAction.do", collectFormData());
      hasGenerated = true;
      gridDataCache = { rows: [], total: 0 };
      if (gridApi) gridApi.purgeInfiniteCache();
      setStatus(status, "Результаты готовы");
    } catch (e) {
      setStatus(status, e.message || "Ошибка", true);
    } finally {
      setButtonLoading(btnGenerate, false);
    }
  }

  btnGenerate.addEventListener("click", generate);
  btnExcel.addEventListener("click", () => {
    const url = "/CalculationStateAction.do?dispatch=generateExcel";
    const ifr = document.createElement("iframe");
    ifr.style.display = "none";
    ifr.src = url;
    document.body.appendChild(ifr);
    setTimeout(() => document.body.removeChild(ifr), 5000);
  });

  btnClear.addEventListener("click", () => {
    earliestDate.input.value = "";
    hasGenerated = false;
    gridDataCache = { rows: [], total: 0 };
    [
      reportTypeSel, contractorSel, sellerSel, contractSel, currencySel, userSel, depSel
    ].forEach((s) => {
      s.select.innerHTML = "";
    });
    loadLookups();
    setStatus(status, "Выберите вид отчета и контрагента");
  });

  [reportTypeSel.select, contractorSel.select].forEach((el) => {
    el.addEventListener("change", () => {
      btnGenerate.disabled = !canGenerate();
      btnExcel.disabled = !canGenerate();
      if (el === contractorSel.select) loadContracts();
    });
  });

  btnGenerate.disabled = true;
  btnExcel.disabled = true;
  loadLookups();
}

function inputText(labelText, id) {
  const wrap = document.createElement("label");
  wrap.textContent = labelText + " ";
  const input = document.createElement("input");
  input.type = "text";
  input.id = id;
  wrap.appendChild(input);
  return { wrap, input };
}

function selectField(labelText, id) {
  const wrap = document.createElement("label");
  wrap.textContent = labelText + " ";
  const select = document.createElement("select");
  select.id = id;
  wrap.appendChild(select);
  return { wrap, select };
}

function attachSelectDebug(name, select) {
  if (!select) return;
  const log = (eventName) => {
    // #region agent log
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H5',location:'ui/screens/calc-state.js:268',message:'select event',data:{name,event:eventName,disabled:select.disabled,options:select.options.length,selectedIndex:select.selectedIndex},timestamp:Date.now()})}).catch(()=>{});
    // #endregion
  };
  ["mousedown", "click", "focus"].forEach((evt) => {
    select.addEventListener(evt, () => log(evt));
  });
}

function checkboxField(labelText, id) {
  const wrap = document.createElement("label");
  const input = document.createElement("input");
  input.type = "checkbox";
  input.id = id;
  wrap.appendChild(input);
  wrap.appendChild(document.createTextNode(" " + labelText));
  return { wrap, input };
}

function button(text, primary) {
  const btn = document.createElement("button");
  btn.type = "button";
  btn.className = primary ? "btn btn-primary" : "btn";
  btn.textContent = text;
  return btn;
}

async function loadSelect(select, url) {
  const json = await apiGetJson(url);
  select.innerHTML = "";
  const data = json.data || [];
  data.forEach((it) => {
    select.appendChild(buildOption(it.id, it.label));
  });
}
