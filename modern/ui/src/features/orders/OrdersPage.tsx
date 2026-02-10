/**
 * Orders list (N2). Parity per docs/screens/orders (SNAPSHOT, CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX).
 * Legacy: OrdersAction.do, Orders.jsp.
 */
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Button, Checkbox, DatePicker, Input, InputNumber, Layout, message, Select, Space, Typography } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import { useCallback, useEffect, useMemo, useState } from 'react';
import type { OrderFilterParams, OrderListResponse, OrderRowDto, LookupItemDto } from './types';

dayjs.extend(customParseFormat);
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const DATE_FORMAT = 'DD.MM.YYYY';
const DEFAULT_ORDER_AFTER_FILTER = 'ord_date descending';

/** Format date for grid: API may return ISO (YYYY-MM-DD), display as DD.MM.YYYY (parity with filter). */
function formatDateGrid(value: string | null | undefined): string {
  if (value == null || value === '') return '';
  const d = dayjs(value, ['YYYY-MM-DD', DATE_FORMAT], true);
  return d.isValid() ? d.format(DATE_FORMAT) : value;
}

function buildQueryParams(params: OrderFilterParams): string {
  const q = new URLSearchParams();
  if (params.number != null && params.number !== '') q.set('number', params.number);
  if (params.date_begin != null && params.date_begin !== '') q.set('date_begin', params.date_begin);
  if (params.date_end != null && params.date_end !== '') q.set('date_end', params.date_end);
  if (params.contractor_id != null && params.contractor_id !== '') q.set('contractor_id', params.contractor_id);
  if (params.contractor_for_id != null && params.contractor_for_id !== '') q.set('contractor_for_id', params.contractor_for_id);
  if (params.user_id != null && params.user_id !== '') q.set('user_id', params.user_id);
  if (params.department_id != null && params.department_id !== '') q.set('department_id', params.department_id);
  if (params.stuff_category_id != null && params.stuff_category_id !== '') q.set('stuff_category_id', params.stuff_category_id);
  if (params.contract_number != null && params.contract_number !== '') q.set('contract_number', params.contract_number);
  if (params.specification_number != null && params.specification_number !== '') q.set('specification_number', params.specification_number);
  if (params.seller_for_who_id != null && params.seller_for_who_id !== '') q.set('seller_for_who_id', params.seller_for_who_id);
  if (params.sum_min != null) q.set('sum_min', String(params.sum_min));
  if (params.sum_max != null) q.set('sum_max', String(params.sum_max));
  if (params.executed === true) q.set('executed', 'true');
  if (params.not_executed === true) q.set('not_executed', 'true');
  if (params.ord_ready_for_deliv === true) q.set('ord_ready_for_deliv', 'true');
  if (params.ord_annul_not_show === true) q.set('ord_annul_not_show', 'true');
  if (params.state_a === true) q.set('state_a', 'true');
  if (params.state_3 === true) q.set('state_3', 'true');
  if (params.state_b === true) q.set('state_b', 'true');
  if (params.state_exclamation === true) q.set('state_exclamation', 'true');
  if (params.state_c === true) q.set('state_c', 'true');
  if (params.ord_num_conf != null && params.ord_num_conf !== '') q.set('ord_num_conf', params.ord_num_conf);
  q.set('page', String(params.page));
  q.set('pageSize', String(params.pageSize));
  if (params.order_by != null && params.order_by !== '') q.set('order_by', params.order_by);
  return q.toString();
}

export default function OrdersPage() {
  const [number, setNumber] = useState('');
  const [dateBegin, setDateBegin] = useState<string | null>(null);
  const [dateEnd, setDateEnd] = useState<string | null>(null);
  const [contractorId, setContractorId] = useState<string | null>(null);
  const [contractorForId, setContractorForId] = useState<string | null>(null);
  const [userId, setUserId] = useState<string | null>(null);
  const [departmentId, setDepartmentId] = useState<string | null>(null);
  const [stuffCategoryId, setStuffCategoryId] = useState<string | null>(null);
  const [contractNumber, setContractNumber] = useState<string | null>(null);
  const [specificationNumber, setSpecificationNumber] = useState<string | null>(null);
  const [sellerForWhoId, setSellerForWhoId] = useState<string | null>(null);
  const [sumMin, setSumMin] = useState<number | null>(null);
  const [sumMax, setSumMax] = useState<number | null>(null);
  const [executed, setExecuted] = useState(false);
  const [notExecuted, setNotExecuted] = useState(true);
  const [ordReadyForDeliv, setOrdReadyForDeliv] = useState(false);
  const [ordAnnulNotShow, setOrdAnnulNotShow] = useState(true);
  const [stateA, setStateA] = useState(false);
  const [state3, setState3] = useState(false);
  const [stateB, setStateB] = useState(false);
  const [stateExclamation, setStateExclamation] = useState(false);
  const [stateC, setStateC] = useState(false);
  const [ordNumConf, setOrdNumConf] = useState('');
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(25);
  const [orderBy, setOrderBy] = useState(DEFAULT_ORDER_AFTER_FILTER);
  const [appliedFilter, setAppliedFilter] = useState<OrderFilterParams | null>(null);

  const stateDisabled = executed;

  useEffect(() => {
    if (executed) {
      setStateA(false);
      setState3(false);
      setStateB(false);
      setStateExclamation(false);
      setStateC(false);
    }
  }, [executed]);

  const handleExecutedChange = useCallback((v: boolean) => {
    setExecuted(v);
    if (v) setNotExecuted(false);
  }, []);
  const handleNotExecutedChange = useCallback((v: boolean) => {
    setNotExecuted(v);
    if (v) setExecuted(false);
  }, []);

  const currentParams: OrderFilterParams = useMemo(() => ({
    number: number || undefined,
    date_begin: dateBegin ?? undefined,
    date_end: dateEnd ?? undefined,
    contractor_id: contractorId ?? undefined,
    contractor_for_id: contractorForId ?? undefined,
    user_id: userId ?? undefined,
    department_id: departmentId ?? undefined,
    stuff_category_id: stuffCategoryId ?? undefined,
    contract_number: contractNumber ?? undefined,
    specification_number: specificationNumber ?? undefined,
    seller_for_who_id: sellerForWhoId ?? undefined,
    sum_min: sumMin ?? undefined,
    sum_max: sumMax ?? undefined,
    executed,
    not_executed: notExecuted,
    ord_ready_for_deliv: ordReadyForDeliv,
    ord_annul_not_show: ordAnnulNotShow,
    state_a: stateA,
    state_3: state3,
    state_b: stateB,
    state_exclamation: stateExclamation,
    state_c: stateC,
    ord_num_conf: ordNumConf || undefined,
    page: appliedFilter?.page ?? page,
    pageSize: appliedFilter?.pageSize ?? pageSize,
    order_by: appliedFilter?.order_by ?? orderBy,
  }), [number, dateBegin, dateEnd, contractorId, contractorForId, userId, departmentId, stuffCategoryId, contractNumber, specificationNumber, sellerForWhoId, sumMin, sumMax, executed, notExecuted, ordReadyForDeliv, ordAnnulNotShow, stateA, state3, stateB, stateExclamation, stateC, ordNumConf, appliedFilter, page, pageSize, orderBy]);

  const listParams = appliedFilter ?? null;
  const listQuery = useMemo(() => {
    if (!listParams) return null;
    const params: OrderFilterParams = {
      ...listParams,
      page: listParams.page,
      pageSize: listParams.pageSize,
      order_by: listParams.order_by ?? DEFAULT_ORDER_AFTER_FILTER,
    };
    return params;
  }, [listParams]);

  const listUrl = listQuery ? `${API_BASE}/api/orders?${buildQueryParams(listQuery)}` : null;

  const [listData, setListData] = useState<OrderListResponse | null>(null);
  const [listLoading, setListLoading] = useState(false);
  const [listError, setListError] = useState<string | null>(null);

  useEffect(() => {
    if (!listUrl) {
      setListData(null);
      setListError(null);
      return;
    }
    let cancelled = false;
    setListLoading(true);
    setListError(null);
    fetch(listUrl)
      .then((res) => res.text())
      .then((text) => {
        if (cancelled) return null;
        if (!text) return { items: [], total: 0, page: 1, pageSize: 25 };
        try {
          const json = JSON.parse(text) as OrderListResponse;
          if (json.items == null || typeof json.total !== 'number') throw new Error('Invalid shape');
          return json;
        } catch {
          throw new Error('Неверный формат ответа');
        }
      })
      .then((data) => {
        if (cancelled) return;
        setListData(data ?? { items: [], total: 0, page: 1, pageSize: 25 });
      })
      .catch((err) => {
        if (!cancelled) setListError(err instanceof Error ? err.message : 'Ошибка загрузки');
      })
      .finally(() => {
        if (!cancelled) setListLoading(false);
      });
    return () => { cancelled = true; };
  }, [listUrl]);

  const applyFilter = useCallback(() => {
    setAppliedFilter({
      ...currentParams,
      page: 1,
      pageSize,
      order_by: DEFAULT_ORDER_AFTER_FILTER,
    });
    setPage(1);
    setOrderBy(DEFAULT_ORDER_AFTER_FILTER);
    message.success('Фильтр применён');
  }, [currentParams, pageSize]);

  const clearFilter = useCallback(() => {
    setNumber('');
    setDateBegin(null);
    setDateEnd(null);
    setContractorId(null);
    setContractorForId(null);
    setUserId(null);
    setDepartmentId(null);
    setStuffCategoryId(null);
    setContractNumber(null);
    setSpecificationNumber(null);
    setSellerForWhoId(null);
    setSumMin(null);
    setSumMax(null);
    setExecuted(false);
    setNotExecuted(true);
    setOrdReadyForDeliv(false);
    setOrdAnnulNotShow(true);
    setStateA(false);
    setState3(false);
    setStateB(false);
    setStateExclamation(false);
    setStateC(false);
    setOrdNumConf('');
    setPage(1);
    setPageSize(25);
    setOrderBy(DEFAULT_ORDER_AFTER_FILTER);
    setAppliedFilter(null);
    setListData(null);
    setListError(null);
    message.info('Фильтр сброшен');
  }, []);

  useEffect(() => {
    if (contractorForId == null || contractorForId === '') {
      setContractNumber(null);
      setSpecificationNumber(null);
    }
  }, [contractorForId]);
  useEffect(() => {
    if (contractNumber == null || contractNumber === '') setSpecificationNumber(null);
  }, [contractNumber]);

  const contractorsQuery = useMemo(() => fetch(`${API_BASE}/api/orders/lookups/contractors?have_all=true`).then((r) => r.json()) as Promise<LookupItemDto[]>, []);
  const [contractors, setContractors] = useState<LookupItemDto[]>([]);
  useEffect(() => { contractorsQuery.then(setContractors).catch(() => setContractors([])); }, [contractorsQuery]);
  const usersQuery = useMemo(() => fetch(`${API_BASE}/api/orders/lookups/users?have_all=true`).then((r) => r.json()) as Promise<LookupItemDto[]>, []);
  const [users, setUsers] = useState<LookupItemDto[]>([]);
  useEffect(() => { usersQuery.then(setUsers).catch(() => setUsers([])); }, [usersQuery]);
  const departmentsQuery = useMemo(() => fetch(`${API_BASE}/api/orders/lookups/departments?have_all=true`).then((r) => r.json()) as Promise<LookupItemDto[]>, []);
  const [departments, setDepartments] = useState<LookupItemDto[]>([]);
  useEffect(() => { departmentsQuery.then(setDepartments).catch(() => setDepartments([])); }, [departmentsQuery]);
  const stuffCategoriesQuery = useMemo(() => fetch(`${API_BASE}/api/orders/lookups/stuff-categories?have_all=true`).then((r) => r.json()) as Promise<LookupItemDto[]>, []);
  const [stuffCategories, setStuffCategories] = useState<LookupItemDto[]>([]);
  useEffect(() => { stuffCategoriesQuery.then(setStuffCategories).catch(() => setStuffCategories([])); }, [stuffCategoriesQuery]);
  const sellersQuery = useMemo(() => fetch(`${API_BASE}/api/orders/lookups/sellers?have_all=true`).then((r) => r.json()) as Promise<LookupItemDto[]>, []);
  const [sellers, setSellers] = useState<LookupItemDto[]>([]);
  useEffect(() => { sellersQuery.then(setSellers).catch(() => setSellers([])); }, [sellersQuery]);

  const [contracts, setContracts] = useState<LookupItemDto[]>([]);
  useEffect(() => {
    const url = `${API_BASE}/api/orders/lookups/contracts?have_all=true${contractorForId ? `&contractor_id=${encodeURIComponent(contractorForId)}` : ''}`;
    fetch(url)
      .then((r) => r.json())
      .then(setContracts)
      .catch(() => setContracts([]));
  }, [contractorForId]);
  const [specifications, setSpecifications] = useState<LookupItemDto[]>([]);
  useEffect(() => {
    const url = `${API_BASE}/api/orders/lookups/specifications?have_all=true${contractNumber ? `&contract_id=${encodeURIComponent(contractNumber)}` : ''}${contractorForId ? `&contractor_id=${encodeURIComponent(contractorForId)}` : ''}`;
    fetch(url)
      .then((r) => r.json())
      .then(setSpecifications)
      .catch(() => setSpecifications([]));
  }, [contractNumber, contractorForId]);

  const columnDefs: ColDef<OrderRowDto>[] = useMemo(() => [
    { field: 'ord_number', headerName: '№ заказа', flex: 1, minWidth: 100 },
    { field: 'ord_date', headerName: 'Дата', flex: 1, minWidth: 100, valueFormatter: (p) => formatDateGrid(p.value) },
    { field: 'ord_contractor', headerName: 'Контрагент', flex: 1, minWidth: 140 },
    { field: 'ord_contractor_for', headerName: 'Заказчик', flex: 1, minWidth: 120 },
    { field: 'ord_summ', headerName: 'Сумма', flex: 1, minWidth: 90, valueFormatter: (p) => p.value != null ? String(p.value) : '' },
    { field: 'ord_date_conf', headerName: 'Дата подтв.', flex: 1, minWidth: 100, valueFormatter: (p) => formatDateGrid(p.value) },
    { field: 'ord_user', headerName: 'Пользователь', flex: 1, minWidth: 120 },
    { field: 'ord_department', headerName: 'Отдел', flex: 1, minWidth: 120 },
    {
      field: 'is_warn',
      headerName: '!',
      width: 44,
      cellRenderer: (p: { data?: OrderRowDto }) =>
        p.data?.is_warn && p.data.is_warn !== '0' ? <span title="Предупреждение">⚠</span> : null,
    },
    {
      field: 'ord_block',
      headerName: 'Блок',
      width: 60,
      cellRenderer: (p: { data?: OrderRowDto }) => {
        const row = p.data;
        if (!row) return null;
        const canEdit = row.can_block === true;
        return (
          <input
            type="checkbox"
            checked={row.ord_block === '1'}
            disabled={!canEdit}
            readOnly
            title={canEdit ? 'Заблокировать' : 'Только для администратора'}
          />
        );
      },
    },
    {
      field: 'ord_annul',
      headerName: 'Аннул.',
      width: 70,
      valueFormatter: (p) => (p.value === 1 ? 'Да' : ''),
    },
  ], []);

  const getRowClass = useCallback((params: { data?: OrderRowDto }) => {
    if (params.data?.ord_annul === 1) return 'crossed-cell';
    return '';
  }, []);

  const total = listData?.total ?? 0;
  const totalPages = pageSize > 0 ? Math.max(1, Math.ceil(total / pageSize)) : 1;
  const currentPage = listData?.page ?? 1;

  const goToPage = useCallback((newPage: number) => {
    if (!appliedFilter) return;
    setAppliedFilter({ ...appliedFilter, page: newPage });
    setPage(newPage);
  }, [appliedFilter]);

  return (
    <Layout style={{ padding: 16 }}>
      <Typography.Title level={4}>Заказы</Typography.Title>

      <Space direction="vertical" size="middle" style={{ width: '100%' }}>
        <Space wrap align="start">
          <Input placeholder="№ заказа" value={number} onChange={(e) => setNumber(e.target.value)} style={{ width: 120 }} />
          <DatePicker
            placeholder="Дата с"
            format={DATE_FORMAT}
            value={dateBegin ? dayjs(dateBegin, DATE_FORMAT) : null}
            onChange={(date, _) => setDateBegin(date ? date.format(DATE_FORMAT) : null)}
          />
          <DatePicker
            placeholder="Дата по"
            format={DATE_FORMAT}
            value={dateEnd ? dayjs(dateEnd, DATE_FORMAT) : null}
            onChange={(date, _) => setDateEnd(date ? date.format(DATE_FORMAT) : null)}
          />
          <Select
            placeholder="Контрагент"
            allowClear
            showSearch
            optionFilterProp="label"
            value={contractorId ?? undefined}
            onChange={(v) => setContractorId(v ?? null)}
            options={contractors.map((c) => ({ value: c.id, label: c.name }))}
            style={{ minWidth: 180 }}
          />
          <Select
            placeholder="Заказчик (contractor_for)"
            allowClear
            showSearch
            optionFilterProp="label"
            value={contractorForId ?? undefined}
            onChange={(v) => setContractorForId(v ?? null)}
            options={contractors.map((c) => ({ value: c.id, label: c.name }))}
            style={{ minWidth: 180 }}
          />
          <Select
            placeholder="Договор"
            allowClear
            showSearch
            optionFilterProp="label"
            value={contractNumber ?? undefined}
            onChange={(v) => setContractNumber(v ?? null)}
            options={contracts.map((c) => ({ value: c.id, label: c.name }))}
            style={{ minWidth: 140 }}
          />
          <Select
            placeholder="Спецификация"
            allowClear
            showSearch
            optionFilterProp="label"
            value={specificationNumber ?? undefined}
            onChange={(v) => setSpecificationNumber(v ?? null)}
            options={specifications.map((s) => ({ value: s.id, label: s.name }))}
            style={{ minWidth: 140 }}
          />
          <Select
            placeholder="Пользователь"
            allowClear
            showSearch
            optionFilterProp="label"
            value={userId ?? undefined}
            onChange={(v) => setUserId(v ?? null)}
            options={users.map((u) => ({ value: u.id, label: u.name }))}
            style={{ minWidth: 160 }}
          />
          <Select
            placeholder="Отдел"
            allowClear
            showSearch
            optionFilterProp="label"
            value={departmentId ?? undefined}
            onChange={(v) => setDepartmentId(v ?? null)}
            options={departments.map((d) => ({ value: d.id, label: d.name }))}
            style={{ minWidth: 160 }}
          />
          <Select
            placeholder="Категория"
            allowClear
            showSearch
            optionFilterProp="label"
            value={stuffCategoryId ?? undefined}
            onChange={(v) => setStuffCategoryId(v ?? null)}
            options={stuffCategories.map((s) => ({ value: s.id, label: s.name }))}
            style={{ minWidth: 140 }}
          />
          <Select
            placeholder="Продавец"
            allowClear
            showSearch
            optionFilterProp="label"
            value={sellerForWhoId ?? undefined}
            onChange={(v) => setSellerForWhoId(v ?? null)}
            options={sellers.map((s) => ({ value: s.id, label: s.name }))}
            style={{ minWidth: 140 }}
          />
          <InputNumber placeholder="Сумма мин" value={sumMin ?? undefined} onChange={(v) => setSumMin(v ?? null)} min={0} style={{ width: 100 }} />
          <InputNumber placeholder="Сумма макс" value={sumMax ?? undefined} onChange={(v) => setSumMax(v ?? null)} min={0} style={{ width: 100 }} />
          <Input placeholder="№ подтв." value={ordNumConf} onChange={(e) => setOrdNumConf(e.target.value)} style={{ width: 100 }} />
        </Space>

        <Space wrap align="start">
          <Checkbox checked={executed} onChange={(e) => handleExecutedChange(e.target.checked)}>Выполненные</Checkbox>
          <Checkbox checked={notExecuted} onChange={(e) => handleNotExecutedChange(e.target.checked)}>Не выполненные</Checkbox>
          <Checkbox checked={ordReadyForDeliv} onChange={(e) => setOrdReadyForDeliv(e.target.checked)}>Готов к отгрузке</Checkbox>
          <Checkbox checked={ordAnnulNotShow} onChange={(e) => setOrdAnnulNotShow(e.target.checked)}>Не показ. аннул.</Checkbox>
          <Typography.Text strong style={{ marginRight: 8 }}>Текущее состояние:</Typography.Text>
          <Checkbox checked={stateA} disabled={stateDisabled} onChange={(e) => setStateA(e.target.checked)}>Передан на производство</Checkbox>
          <Checkbox checked={state3} disabled={stateDisabled} onChange={(e) => setState3(e.target.checked)}>Нет ответа (3)</Checkbox>
          <Checkbox checked={stateB} disabled={stateDisabled} onChange={(e) => setStateB(e.target.checked)}>Получено подтверждение</Checkbox>
          <Checkbox checked={stateExclamation} disabled={stateDisabled} onChange={(e) => setStateExclamation(e.target.checked)}>Риск нарушить срок поставки (!)</Checkbox>
          <Checkbox checked={stateC} disabled={stateDisabled} onChange={(e) => setStateC(e.target.checked)}>Выслано покупателю</Checkbox>
        </Space>

        <Space>
          <Button type="primary" onClick={applyFilter}>Применить фильтр</Button>
          <Button onClick={clearFilter}>Сбросить фильтр</Button>
        </Space>

        {listError && <Typography.Text type="danger">{listError}</Typography.Text>}

        {appliedFilter && (
          <>
            {listLoading && <Typography.Text>Загрузка…</Typography.Text>}
            {!listLoading && listData && listData.items.length === 0 && (
              <Typography.Text type="secondary">Нет данных по заданному фильтру.</Typography.Text>
            )}
            {!listLoading && listData && listData.items.length > 0 && (
              <>
                <div className="ag-theme-quartz" style={{ height: 420, width: '100%' }}>
                  <AgGridReact<OrderRowDto>
                    rowData={listData.items}
                    columnDefs={columnDefs}
                    getRowClass={getRowClass}
                    domLayout="normal"
                    suppressCellFocus
                    defaultColDef={{ sortable: true, resizable: true }}
                  />
                </div>
                <Space wrap align="center">
                  <Typography.Text>Сортировка:</Typography.Text>
                  <Select
                    value={appliedFilter?.order_by ?? DEFAULT_ORDER_AFTER_FILTER}
                    onChange={(v) => {
                      const next = v || DEFAULT_ORDER_AFTER_FILTER;
                      setOrderBy(next);
                      if (appliedFilter) setAppliedFilter({ ...appliedFilter, order_by: next });
                    }}
                    options={[
                      { value: 'ord_date descending', label: 'По дате (убыв.)' },
                      { value: 'ord_number asc', label: 'По номеру (возр.)' },
                      { value: 'ord_number desc', label: 'По номеру (убыв.)' },
                    ]}
                    style={{ minWidth: 160 }}
                  />
                  <Button disabled={currentPage <= 1} onClick={() => goToPage(currentPage - 1)}>Назад</Button>
                  <Typography.Text>Стр. {currentPage} из {totalPages} (всего {total})</Typography.Text>
                  <Button disabled={currentPage >= totalPages} onClick={() => goToPage(currentPage + 1)}>Вперёд</Button>
                </Space>
              </>
            )}
          </>
        )}

        {!appliedFilter && (
          <Typography.Text type="secondary">Нажмите «Применить фильтр», чтобы загрузить список заказов.</Typography.Text>
        )}
      </Space>

      <style>{`
        .crossed-cell { text-decoration: line-through; }
      `}</style>
    </Layout>
  );
}
