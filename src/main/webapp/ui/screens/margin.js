import { apiGetJson, buildQuery, fetchLegacyList } from "../api.js";
import { createGrid } from "../components/grid.js";
import { setStatus, buildOption } from "../components/toolbar.js";
import { setButtonLoading, showGridLoading, hideGridLoading } from "../shared/loading.js";
import { createCombobox } from "../shared/combobox.js";

export function renderMargin(root) {
  const panel = document.createElement("div");
  panel.className = "panel";

  const toolbar = document.createElement("div");
  toolbar.className = "toolbar";

  const dateBegin = inputText("Дата с", "mg_date_begin");
  const dateEnd = inputText("по", "mg_date_end");
  const userSel = selectField("Пользователь?", "mg_user");
  const depSel = selectField("Отдел?", "mg_department");
  const ctrSel = comboboxField("Контрагент?", "mg_contractor", {
    placeholder: "Введите контрагента",
    minChars: 2,
    debounceMs: 250,
    limit: 20,
    recentKey: "contractors",
    fetchOptions: async (query, limit) => {
      const json = await apiGetJson("/api/lookups/contractors/search" + buildQuery({ q: query, limit }));
      return json.items || [];
    },
    onSelect: () => {
      updateButtons();
    }
  });
  const stfSel = selectField("Продукт (Производитель)?", "mg_stuffCategory");
  const routeSel = selectField("Маршрут?", "mg_route");
  attachSelectDebug("user", userSel.select);

  const userAspect = checkboxField("", "mg_user_aspect");
  const depAspect = checkboxField("", "mg_department_aspect");
  const ctrAspect = checkboxField("", "mg_contractor_aspect");
  const stfAspect = checkboxField("", "mg_stuff_category_aspect");
  const routeAspect = checkboxField("", "mg_route_aspect");

  const onlyTotal = checkboxField("Выводить только ИТОГИ?", "mg_onlyTotal");
  const itogSpec = checkboxField("Выводить итоги по Спецификациям договоров?", "mg_itog_by_spec");
  const itogUser = checkboxField("разбивать итог Спецификации на Пользователей?", "mg_itog_by_user");
  const itogProduct = checkboxField("разбивать итог Пользователя на итоги по продуктам?", "mg_itog_by_product");
  const notBlock = checkboxField("Включить в отчёт незаблокированные закрытия договоров?", "mg_get_not_block");

  const viewContractor = checkboxField("Контрагент", "mg_view_contractor");
  const viewCountry = checkboxField("Страна", "mg_view_country");
  const viewContract = checkboxField("Договор", "mg_view_contract");
  const viewStuffCategory = checkboxField("Продукт (Производитель)", "mg_view_stuff_category");
  const viewShipping = checkboxField("Отгрузки", "mg_view_shipping");
  const viewPayment = checkboxField("Оплаты", "mg_view_payment");
  const viewTransport = checkboxField("Транспорт", "mg_view_transport");
  const viewTransportSum = checkboxField("Транспорт Минск-Клиент", "mg_view_transport_sum");
  const viewCustom = checkboxField("Таможенные расходы", "mg_view_custom");
  const viewOtherSum = checkboxField("Логистика", "mg_view_other_sum");
  const viewMontageSum = checkboxField("Монтаж и наладка", "mg_view_montage_sum");
  const viewMontageTime = checkboxField("Время на монтаж, часы", "mg_view_montage_time");
  const viewMontageCost = checkboxField("Ст-ть монтажа по нормативу (справочно)", "mg_view_montage_cost");
  const viewUpdateSum = checkboxField("Корректировка", "mg_view_update_sum");
  const viewSummZak = checkboxField("Сумма закупки", "mg_view_summ_zak");
  const viewKoeff = checkboxField("Средний коэф-т", "mg_view_koeff");
  const viewUser = checkboxField("Пользователь", "mg_view_user");
  const viewDepartment = checkboxField("Отдел", "mg_view_department");
  const viewCheckboxes = [viewContractor, viewCountry, viewContract, viewStuffCategory, viewShipping, viewPayment,
    viewTransport, viewTransportSum, viewCustom, viewOtherSum, viewMontageSum, viewMontageTime, viewMontageCost,
    viewUpdateSum, viewSummZak, viewKoeff, viewUser, viewDepartment];
  viewCheckboxes.forEach((cb) => { cb.input.checked = true; });
  viewMontageTime.input.checked = false;

  const status = document.createElement("span");
  status.className = "status";
  status.textContent = "Укажите даты и хотя бы один фильтр";

  const btnClear = button("Очистить все", false);
  const btnGenerate = button("Сформировать", true);
  const btnExcel = button("Сохранить в Excel", false);

  const formByTitle = document.createElement("div");
  formByTitle.className = "margin-form-by-title";
  formByTitle.textContent = "Формировать отчет по:";
  const aspectTitle = document.createElement("div");
  aspectTitle.className = "margin-aspect-title";
  aspectTitle.textContent = "в разрезе по:";

  const formTable = document.createElement("div");
  formTable.className = "margin-form-table";
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(aspectTitle);
  formTable.appendChild(userSel.wrap);
  formTable.appendChild(userAspect.wrap);
  formTable.appendChild(depSel.wrap);
  formTable.appendChild(depAspect.wrap);
  formTable.appendChild(ctrSel.wrap);
  formTable.appendChild(ctrAspect.wrap);
  formTable.appendChild(stfSel.wrap);
  formTable.appendChild(stfAspect.wrap);
  formTable.appendChild(routeSel.wrap);
  formTable.appendChild(routeAspect.wrap);
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(onlyTotal.wrap);
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(itogSpec.wrap);
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(itogUser.wrap);
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(itogProduct.wrap);
  formTable.appendChild(document.createElement("div"));
  formTable.appendChild(notBlock.wrap);
  formTable.appendChild(document.createElement("div"));

  const leftCol = document.createElement("div");
  leftCol.className = "margin-form-left";
  leftCol.appendChild(formByTitle);
  leftCol.appendChild(formTable);

  const viewTitle = document.createElement("div");
  viewTitle.className = "margin-view-title";
  viewTitle.textContent = "Отображать колонки:";
  const rightCol = document.createElement("div");
  rightCol.className = "margin-form-right";
  rightCol.appendChild(viewTitle);
  viewCheckboxes.forEach((cb) => rightCol.appendChild(cb.wrap));

  const topRow = document.createElement("div");
  topRow.className = "margin-form-top-row";
  topRow.append(dateBegin.wrap, dateEnd.wrap);

  const formBody = document.createElement("div");
  formBody.className = "margin-form-body";
  formBody.appendChild(leftCol);
  formBody.appendChild(rightCol);

  const btnRow = document.createElement("div");
  btnRow.className = "margin-form-buttons";
  btnRow.append(btnClear, btnGenerate, btnExcel, status);

  toolbar.className = "toolbar margin-form-toolbar";
  toolbar.appendChild(topRow);
  toolbar.appendChild(formBody);
  toolbar.appendChild(btnRow);
  panel.appendChild(toolbar);
  root.appendChild(panel);

  const gridWrap = document.createElement("div");
  gridWrap.className = "panel grid-container ag-theme-alpine";
  root.appendChild(gridWrap);

  const columnDefs = [
    { field: "ctr_name", headerName: "Контрагент", minWidth: 160 },
    { field: "cut_name", headerName: "Страна", minWidth: 120 },
    { field: "con_number_formatted", headerName: "Договор", minWidth: 120 },
    { field: "con_date_formatted", headerName: "Дата договора", minWidth: 110 },
    { field: "spc_number_formatted", headerName: "Спецификация", minWidth: 140 },
    { field: "spc_date_formatted", headerName: "Дата спецификации", minWidth: 130 },
    { field: "spc_summ_formatted", headerName: "Сумма", minWidth: 120 },
    { field: "cur_name", headerName: "Валюта", minWidth: 80 },
    { field: "stf_name_show", headerName: "Категория", minWidth: 180 },
    { field: "shp_number_show", headerName: "Отгрузка", minWidth: 130 },
    { field: "shp_date_show", headerName: "Дата отгрузки", minWidth: 120 },
    { field: "pay_date_show", headerName: "Дата оплаты", minWidth: 120 },
    { field: "lps_summ_eur_formatted", headerName: "Сумма EUR", minWidth: 120 },
    { field: "lps_summ_formatted", headerName: "Сумма", minWidth: 120 },
    { field: "lps_sum_transport_formatted", headerName: "Транспорт", minWidth: 140 },
    { field: "lcc_transport_formatted", headerName: "Транспорт (сум)", minWidth: 160 },
    { field: "lps_custom_formatted", headerName: "Таможня", minWidth: 130 },
    { field: "lcc_charges_formatted", headerName: "Прочие", minWidth: 130 },
    { field: "lcc_montage_formatted", headerName: "Монтаж", minWidth: 130 },
    { field: "lps_montage_time_formatted", headerName: "Монтаж время", minWidth: 150 },
    { field: "montage_cost_formatted", headerName: "Стоимость монтажа", minWidth: 170 },
    { field: "lcc_update_sum_formatted", headerName: "Доработки", minWidth: 150 },
    { field: "summ_formatted", headerName: "Итого", minWidth: 120 },
    { field: "summ_zak_formatted", headerName: "Сумма заказ", minWidth: 140 },
    { field: "margin_formatted", headerName: "Маржа", minWidth: 120 },
    { field: "koeff_formatted", headerName: "Коэфф", minWidth: 120 },
    { field: "usr_name_show", headerName: "Пользователь", minWidth: 140 },
    { field: "dep_name_show", headerName: "Отдел", minWidth: 140 }
  ];

  let columnApi = null;
  let gridApi = null;
  let currentFilters = null;

  createGrid(gridWrap, columnDefs, {
    useClientSide: true,
    getRowClass: (params) => {
      const d = params && params.data ? params.data : null;
      if (!d) return null;
      if (d.itogLine) return "row-itog";
      const hasGroup = d.spc_group_delivery && String(d.spc_group_delivery).trim() !== "";
      if (hasGroup) {
        return d.haveUnblockedPrc ? "row-green-pink" : "row-green";
      }
      if (d.haveUnblockedPrc) return "row-pink";
      return null;
    },
    onGridReady: (params) => {
      gridApi = params.api;
      columnApi = params.columnApi;
    }
  });

  function applyView(colApi, view) {
    if (!colApi || !view) return;
    const v = (k) => !!view[k];
    colApi.setColumnVisible("ctr_name", v("view_contractor"));
    colApi.setColumnVisible("cut_name", v("view_country"));
    colApi.setColumnVisible("con_number_formatted", v("view_contract"));
    colApi.setColumnVisible("con_date_formatted", v("view_contract"));
    colApi.setColumnVisible("spc_number_formatted", v("view_contract"));
    colApi.setColumnVisible("spc_date_formatted", v("view_contract"));
    colApi.setColumnVisible("spc_summ_formatted", v("view_contract"));
    colApi.setColumnVisible("cur_name", v("view_contract"));
    colApi.setColumnVisible("stf_name_show", v("view_stuff_category"));
    colApi.setColumnVisible("shp_number_show", v("view_shipping"));
    colApi.setColumnVisible("shp_date_show", v("view_shipping"));
    colApi.setColumnVisible("pay_date_show", v("view_payment"));
    colApi.setColumnVisible("lps_sum_transport_formatted", v("view_transport"));
    colApi.setColumnVisible("lcc_transport_formatted", v("view_transport_sum"));
    colApi.setColumnVisible("lps_custom_formatted", v("view_custom"));
    colApi.setColumnVisible("lcc_charges_formatted", v("view_other_sum"));
    colApi.setColumnVisible("lcc_montage_formatted", v("view_montage_sum"));
    colApi.setColumnVisible("lps_montage_time_formatted", v("view_montage_time"));
    colApi.setColumnVisible("montage_cost_formatted", v("view_montage_cost"));
    colApi.setColumnVisible("lcc_update_sum_formatted", v("view_update_sum"));
    colApi.setColumnVisible("summ_zak_formatted", v("view_summ_zak"));
    colApi.setColumnVisible("koeff_formatted", v("view_koeff"));
    colApi.setColumnVisible("usr_name_show", v("view_user"));
    colApi.setColumnVisible("dep_name_show", v("view_department"));
  }

  async function loadLookups() {
    await Promise.all([
      loadSelectLegacy(userSel.select, "/UsersListAction.do?have_all=true"),
      loadSelectLegacy(depSel.select, "/DepartmentsListAction.do?have_all=true"),
      loadSelect(stfSel.select, "/api/lookups/stuff-categories?includeAll=true"),
      loadSelect(routeSel.select, "/api/lookups/routes?includeAll=true")
    ]);
    // #region agent log
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H2',location:'ui/screens/margin.js:159',message:'margin loadLookups done',data:{userCount:userSel.select.options.length,depCount:depSel.select.options.length,ctrSelected:!!ctrSel.combo.getValue()},timestamp:Date.now()})}).catch(()=>{});
    // #endregion
  }

  function canGenerate() {
    const db = dateBegin.input.value.trim();
    const de = dateEnd.input.value.trim();
    const ctrValue = ctrSel.combo.getValue();
    const ctrId = ctrValue ? ctrValue.id : "";
    const any = userSel.select.value || depSel.select.value || ctrId ||
      stfSel.select.value || routeSel.select.value;
    return db && de && any;
  }

  function selectedOptionText(select) {
    if (!select || !select.options) return "";
    const opt = select.options[select.selectedIndex];
    return opt ? (opt.textContent || opt.text || "").trim() : "";
  }

  function viewVal(cb) { return cb.input.checked ? "1" : ""; }

  function buildFilters() {
    const ctrValue = ctrSel.combo.getValue();
    return {
      dateBegin: dateBegin.input.value.trim(),
      dateEnd: dateEnd.input.value.trim(),
      userId: userSel.select.value,
      userName: selectedOptionText(userSel.select),
      departmentId: depSel.select.value,
      departmentName: selectedOptionText(depSel.select),
      contractorId: (ctrValue && ctrValue.id) ? ctrValue.id : "",
      contractorName: (ctrValue && ctrValue.label) ? ctrValue.label : "",
      stuffCategoryId: stfSel.select.value,
      stuffCategoryName: selectedOptionText(stfSel.select),
      routeId: routeSel.select.value,
      routeName: selectedOptionText(routeSel.select),
      user_aspect: viewVal(userAspect),
      department_aspect: viewVal(depAspect),
      contractor_aspect: viewVal(ctrAspect),
      stuff_category_aspect: viewVal(stfAspect),
      route_aspect: viewVal(routeAspect),
      onlyTotal: onlyTotal.input.checked ? "1" : "",
      itogBySpec: itogSpec.input.checked ? "1" : "",
      itogByUser: itogUser.input.checked ? "1" : "",
      itogByProduct: itogProduct.input.checked ? "1" : "",
      getNotBlock: notBlock.input.checked ? "1" : "",
      view_contractor: viewVal(viewContractor),
      view_country: viewVal(viewCountry),
      view_contract: viewVal(viewContract),
      view_stuff_category: viewVal(viewStuffCategory),
      view_shipping: viewVal(viewShipping),
      view_payment: viewVal(viewPayment),
      view_transport: viewVal(viewTransport),
      view_transport_sum: viewVal(viewTransportSum),
      view_custom: viewVal(viewCustom),
      view_other_sum: viewVal(viewOtherSum),
      view_montage_sum: viewVal(viewMontageSum),
      view_montage_time: viewVal(viewMontageTime),
      view_montage_cost: viewVal(viewMontageCost),
      view_update_sum: viewVal(viewUpdateSum),
      view_summ_zak: viewVal(viewSummZak),
      view_koeff: viewVal(viewKoeff),
      view_user: viewVal(viewUser),
      view_department: viewVal(viewDepartment)
    };
  }

  async function generate() {
    if (!canGenerate()) {
      setStatus(status, "Укажите даты и хотя бы один фильтр", true);
      return;
    }
    setButtonLoading(btnGenerate, true);
    setStatus(status, "Формирование...");
    currentFilters = buildFilters();
    showGridLoading(gridApi);
    let rows = [];
    try {
      const json = await apiGetJson("/api/margin/data" + buildQuery(currentFilters));
      const view = json.view || {};
      applyView(columnApi, view);
      if (view.view_contractor !== undefined) {
        viewContractor.input.checked = !!view.view_contractor;
        viewCountry.input.checked = !!view.view_country;
        viewContract.input.checked = !!view.view_contract;
        viewStuffCategory.input.checked = !!view.view_stuff_category;
        viewShipping.input.checked = !!view.view_shipping;
        viewPayment.input.checked = !!view.view_payment;
        viewTransport.input.checked = !!view.view_transport;
        viewTransportSum.input.checked = !!view.view_transport_sum;
        viewCustom.input.checked = !!view.view_custom;
        viewOtherSum.input.checked = !!view.view_other_sum;
        viewMontageSum.input.checked = !!view.view_montage_sum;
        viewMontageTime.input.checked = !!view.view_montage_time;
        viewMontageCost.input.checked = !!view.view_montage_cost;
        viewUpdateSum.input.checked = !!view.view_update_sum;
        viewSummZak.input.checked = !!view.view_summ_zak;
        viewKoeff.input.checked = !!view.view_koeff;
        viewUser.input.checked = !!view.view_user;
        viewDepartment.input.checked = !!view.view_department;
      }
      rows = json.data || [];
      if (gridApi) gridApi.setRowData(rows);
      setStatus(status, "Результаты готовы");
    } catch (e) {
      setStatus(status, e.message || "Ошибка API", true);
      if (gridApi) gridApi.setRowData([]);
    } finally {
      hideGridLoading(gridApi, rows);
      setButtonLoading(btnGenerate, false);
    }
  }

  btnGenerate.addEventListener("click", generate);
  btnExcel.addEventListener("click", () => {
    setStatus(status, "Экспорт Excel пока недоступен без session", true);
  });
  btnClear.addEventListener("click", () => {
    dateBegin.input.value = "";
    dateEnd.input.value = "";
    [userSel, depSel, stfSel, routeSel].forEach((s) => {
      s.select.innerHTML = "";
    });
    ctrSel.combo.clear();
    [userAspect, depAspect, ctrAspect, stfAspect, routeAspect].forEach((a) => { a.input.checked = false; });
    onlyTotal.input.checked = false;
    itogSpec.input.checked = false;
    itogUser.input.checked = false;
    itogProduct.input.checked = false;
    notBlock.input.checked = false;
    viewCheckboxes.forEach((cb) => { cb.input.checked = true; });
    viewMontageTime.input.checked = false;
    currentFilters = null;
    if (gridApi) gridApi.setRowData([]);
    loadLookups();
    setStatus(status, "Укажите даты и хотя бы один фильтр");
  });

  [dateBegin.input, dateEnd.input, userSel.select, depSel.select, stfSel.select, routeSel.select]
    .forEach((el) => el.addEventListener("change", () => {
      updateButtons();
    }));

  function updateButtons() {
    btnGenerate.disabled = !canGenerate();
    btnExcel.disabled = true;
  }

  updateButtons();
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

function comboboxField(labelText, id, options) {
  const wrap = document.createElement("label");
  wrap.textContent = labelText + " ";
  const container = document.createElement("div");
  container.className = "combobox-field";
  wrap.appendChild(container);
  const combo = createCombobox(container, Object.assign({}, options, { inputId: id }));
  return { wrap, combo };
}

function attachSelectDebug(name, select) {
  if (!select) return;
  const log = (eventName) => {
    // #region agent log
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H5',location:'ui/screens/margin.js:251',message:'select event',data:{name,event:eventName,disabled:select.disabled,options:select.options.length,selectedIndex:select.selectedIndex},timestamp:Date.now()})}).catch(()=>{});
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

async function loadSelectLegacy(select, url) {
  const json = await fetchLegacyList(url);
  select.innerHTML = "";
  const data = json.data || [];
  data.forEach((it) => {
    select.appendChild(buildOption(it.id, it.label));
  });
}
