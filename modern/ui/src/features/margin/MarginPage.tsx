/**
 * Margin report (Отчеты → Маржа). Parity per ACCEPTANCE.md + BEHAVIOR_MATRIX.md.
 * Legacy: MarginAction.do + MarginReportGridStandalone.jsp.
 */
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Button, Checkbox, DatePicker, Input, Layout, message, Select, Space, Typography } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import { useCallback, useEffect, useMemo, useRef, useState } from 'react';

dayjs.extend(customParseFormat);
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import { AgGridShell } from '../../shared/ui/AgGridShell';
import type { MarginGridResponse, MarginGenerateRequest, LookupItemResponse, ViewFlagsDto } from './types';
import MarginProgressUI from './components/MarginProgress';
import { useMarginProgress } from './useMarginProgress';
import { downloadWithProgress, saveBlobAsFile } from './downloadWithProgress';

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

const LIMIT_OPTIONS = [50, 100, 200, 500, 1000];
const PAGE_SIZE_OPTIONS = [25, 50, 100, 200];

const DATE_FORMAT = 'DD.MM.YYYY';

const DEFAULT_VIEW_FLAGS: ViewFlagsDto = {
  view_contractor: true,
  view_country: true,
  view_contract: true,
  view_stuff_category: true,
  view_shipping: true,
  view_payment: true,
  view_transport: true,
  view_transport_sum: true,
  view_custom: true,
  view_other_sum: true,
  view_montage_sum: true,
  view_montage_time: true,
  view_montage_cost: true,
  view_update_sum: true,
  view_summ_zak: true,
  view_koeff: true,
  view_user: true,
  view_department: true,
};

const VIEW_FLAG_LABELS: { key: keyof ViewFlagsDto; label: string }[] = [
  { key: 'view_contractor', label: 'Контрагент' },
  { key: 'view_country', label: 'Страна' },
  { key: 'view_contract', label: '№ контракта' },
  { key: 'view_stuff_category', label: 'Продукт' },
  { key: 'view_shipping', label: 'Отгрузка' },
  { key: 'view_payment', label: 'Оплата' },
  { key: 'view_transport', label: 'Транспорт' },
  { key: 'view_transport_sum', label: 'Транспорт Минск‑Клиент' },
  { key: 'view_custom', label: 'Таможенные' },
  { key: 'view_other_sum', label: 'Логистика' },
  { key: 'view_montage_sum', label: 'Монтаж' },
  { key: 'view_montage_time', label: 'Время монтажа' },
  { key: 'view_montage_cost', label: 'Ст-ть монтажа' },
  { key: 'view_update_sum', label: 'Корректировка' },
  { key: 'view_summ_zak', label: 'Сумма закупки' },
  { key: 'view_koeff', label: 'Коэфф‑т' },
  { key: 'view_user', label: 'Пользователь' },
  { key: 'view_department', label: 'Отдел' },
];

const COLUMN_VIEW_KEY: Record<string, keyof ViewFlagsDto> = {
  ctr_name: 'view_contractor',
  cut_name: 'view_country',
  con_number_formatted: 'view_contract',
  con_date_formatted: 'view_contract',
  stf_name_show: 'view_stuff_category',
  shp_number_show: 'view_shipping',
  shp_date_show: 'view_shipping',
  pay_date_show: 'view_payment',
  lps_sum_transport_formatted: 'view_transport',
  lcc_transport_formatted: 'view_transport_sum',
  lps_custom_formatted: 'view_custom',
  lcc_charges_formatted: 'view_other_sum',
  lcc_montage_formatted: 'view_montage_sum',
  lps_montage_time_formatted: 'view_montage_time',
  montage_cost_formatted: 'view_montage_cost',
  lcc_update_sum_formatted: 'view_update_sum',
  summ_zak_formatted: 'view_summ_zak',
  koeff_formatted: 'view_koeff',
  usr_name_show: 'view_user',
  dep_name_show: 'view_department',
};

function marginGridResponse(res: unknown): res is MarginGridResponse {
  return (
    res != null &&
    typeof res === 'object' &&
    'data' in res &&
    Array.isArray((res as MarginGridResponse).data) &&
    'view' in res &&
    'meta' in res
  );
}

const LOOKUP_STEP_IDS = ['users', 'departments', 'contractors', 'stuff', 'routes'] as const;

export default function MarginPage() {
  const queryClient = useQueryClient();
  const progress = useMarginProgress();
  const [limit, setLimit] = useState(200);
  const [pageSize, setPageSize] = useState(50);
  const [quickFilterText, setQuickFilterText] = useState('');
  const [dateBegin, setDateBegin] = useState<string | null>(null);
  const [dateEnd, setDateEnd] = useState<string | null>(null);
  const [user, setUser] = useState<LookupItemResponse | null>(null);
  const [department, setDepartment] = useState<LookupItemResponse | null>(null);
  const [contractor, setContractor] = useState<LookupItemResponse | null>(null);
  const [stuffCategory, setStuffCategory] = useState<LookupItemResponse | null>(null);
  const [route, setRoute] = useState<LookupItemResponse | null>(null);
  const [userAspect, setUserAspect] = useState(false);
  const [departmentAspect, setDepartmentAspect] = useState(false);
  const [contractorAspect, setContractorAspect] = useState(false);
  const [stuffCategoryAspect, setStuffCategoryAspect] = useState(false);
  const [routeAspect, setRouteAspect] = useState(false);
  const [onlyTotal, setOnlyTotal] = useState(false);
  const [itogBySpec, setItogBySpec] = useState(false);
  const [itogByUser, setItogByUser] = useState(false);
  const [itogByProduct, setItogByProduct] = useState(false);
  const [getNotBlock, setGetNotBlock] = useState(false);
  const [viewFlags, setViewFlags] = useState<ViewFlagsDto>(() => ({ ...DEFAULT_VIEW_FLAGS }));
  const [isGridActive, setIsGridActive] = useState(false);

  const hasOneSelector = user !== null || department !== null || contractor !== null || stuffCategory !== null || route !== null;
  const canGenerate = dateBegin != null && dateEnd != null && hasOneSelector;

  const gridQuery = useQuery({
    queryKey: ['margin', 'data', limit],
    queryFn: async (): Promise<MarginGridResponse> => {
      const res = await fetch(`${API_BASE}/api/margin/data?limit=${limit}`);
      const text = await res.text();
      if (!res.ok) {
        if (res.headers.get('content-type')?.includes('text/html')) {
          throw new Error('Сервер вернул страницу вместо JSON…');
        }
        if (text.includes('прав') || text.includes('доступ')) {
          throw new Error('Нет прав на доступ к данным');
        }
        throw new Error(text || 'Ошибка загрузки данных');
      }
      const json = JSON.parse(text) as unknown;
      if (!marginGridResponse(json)) throw new Error('Сервер вернул страницу вместо JSON…');
      return json;
    },
    enabled: isGridActive,
    refetchOnWindowFocus: false,
  });

  useEffect(() => {
    if (gridQuery.data?.view) setViewFlags({ ...gridQuery.data.view });
  }, [gridQuery.data?.view]);

  useEffect(() => {
    if (!hasOneSelector) setOnlyTotal(false);
  }, [hasOneSelector]);

  const generateMutation = useMutation({
    mutationFn: async () => {
      const body: MarginGenerateRequest = {
        date_begin: dateBegin ?? undefined,
        date_end: dateEnd ?? undefined,
        user: user ? { id: user.id, name: user.name } : undefined,
        department: department ? { id: department.id, name: department.name } : undefined,
        contractor: contractor ? { id: contractor.id, name: contractor.name } : undefined,
        stuffCategory: stuffCategory ? { id: stuffCategory.id, name: stuffCategory.name } : undefined,
        route: route ? { id: route.id, name: route.name } : undefined,
        user_aspect: userAspect,
        department_aspect: departmentAspect,
        contractor_aspect: contractorAspect,
        stuff_category_aspect: stuffCategoryAspect,
        route_aspect: routeAspect,
        onlyTotal,
        itog_by_spec: itogBySpec,
        itog_by_user: itogByUser,
        itog_by_product: itogByProduct,
        get_not_block: getNotBlock,
        view: viewFlags,
      };
      const res = await fetch(`${API_BASE}/api/margin/generate`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });
      if (!res.ok) throw new Error('Ошибка формирования отчёта');
    },
    onSuccess: () => {
      progress.setStepStatus('request', 'done');
      progress.setStepStatus('response', 'done');
      progress.setStepStatus('render', 'active');
      progress.setDetails('Отрисовка таблицы…');
      setIsGridActive(true);
      queryClient.invalidateQueries({ queryKey: ['margin', 'data'] });
      message.success('Отчёт сформирован');
    },
    onError: (err) => {
      progress.failProgress(err instanceof Error ? err.message : 'Ошибка формирования отчёта');
      message.error(err instanceof Error ? err.message : 'Ошибка формирования отчёта');
    },
  });

  const cleanAllMutation = useMutation({
    mutationFn: async () => {
      const res = await fetch(`${API_BASE}/api/margin/cleanAll`, { method: 'POST' });
      if (!res.ok) throw new Error('Ошибка сброса');
    },
    onError: (err) => message.error(err instanceof Error ? err.message : 'Ошибка сброса'),
    onSuccess: () => {
      setDateBegin(null);
      setDateEnd(null);
      setUser(null);
      setDepartment(null);
      setContractor(null);
      setStuffCategory(null);
      setRoute(null);
      setUserAspect(false);
      setDepartmentAspect(false);
      setContractorAspect(false);
      setStuffCategoryAspect(false);
      setRouteAspect(false);
      setOnlyTotal(false);
      setItogBySpec(false);
      setItogByUser(false);
      setItogByProduct(false);
      setGetNotBlock(false);
      setIsGridActive(false);
      queryClient.invalidateQueries({ queryKey: ['margin', 'data'] });
    },
  });

  const handleExcel = useCallback(() => {
    const url = `${API_BASE}/api/margin/export/excel`;
    progress.startExportProgress();
    progress.setStepStatus('request', 'active');
    downloadWithProgress({
      url,
      onProgress: (_loaded, _total, pct) => {
        progress.setStepStatus('request', 'done');
        progress.setStepStatus('download', 'active');
        if (pct != null) progress.setProgressPct(pct);
        else progress.setDetails('Получение файла…');
      },
      onComplete: (blob) => {
        progress.setStepStatus('request', 'done');
        progress.setStepStatus('download', 'done');
        progress.setStepStatus('save', 'active');
        progress.setDetails('Сохранение…');
        saveBlobAsFile(blob, 'margin_export.xlsx');
        progress.setStepStatus('save', 'done');
        progress.finishProgress();
        message.success('Экспорт завершён');
      },
      onError: (err) => {
        progress.failProgress(err);
        message.error(err);
      },
    });
  }, [progress]);

  const pick = useCallback((arr: LookupItemResponse[] | undefined, id: string) => arr?.find((i) => i.id === id) ?? null, []);
  const handleSelectUser = (v: string) => {
    setDepartment(null); setContractor(null); setStuffCategory(null); setRoute(null);
    setUser(pick(usersQuery.data, v) ?? (v ? { id: v, name: v } : null));
  };
  const handleSelectDepartment = (v: string) => {
    setUser(null); setContractor(null); setStuffCategory(null); setRoute(null);
    setDepartment(pick(deptsQuery.data, v) ?? (v ? { id: v, name: v } : null));
  };
  const handleSelectContractor = (v: string) => {
    setUser(null); setDepartment(null); setStuffCategory(null); setRoute(null);
    setContractor(pick(contractorsQuery.data, v) ?? (v ? { id: v, name: v } : null));
  };
  const handleSelectStuffCategory = (v: string) => {
    setUser(null); setDepartment(null); setContractor(null); setRoute(null);
    setStuffCategory(pick(stuffQuery.data, v) ?? (v ? { id: v, name: v } : null));
  };
  const handleSelectRoute = (v: string) => {
    setUser(null); setDepartment(null); setContractor(null); setStuffCategory(null);
    setRoute(pick(routesQuery.data, v) ?? (v ? { id: v, name: v } : null));
  };

  const aspectOnly = useCallback((set: () => void) => {
    setUserAspect(false);
    setDepartmentAspect(false);
    setContractorAspect(false);
    setStuffCategoryAspect(false);
    setRouteAspect(false);
    set();
  }, []);

  const usersQuery = useQuery({
    queryKey: ['margin', 'lookups', 'users'],
    queryFn: async () => {
      const res = await fetch(`${API_BASE}/api/margin/lookups/users?have_all=true`);
      if (!res.ok) throw new Error('Ошибка загрузки пользователей');
      return (await res.json()) as LookupItemResponse[];
    },
  });
  const deptsQuery = useQuery({
    queryKey: ['margin', 'lookups', 'departments'],
    queryFn: async () => {
      const res = await fetch(`${API_BASE}/api/margin/lookups/departments?have_all=true`);
      if (!res.ok) throw new Error('Ошибка загрузки отделов');
      return (await res.json()) as LookupItemResponse[];
    },
  });
  const contractorsQuery = useQuery({
    queryKey: ['margin', 'lookups', 'contractors'],
    queryFn: async () => {
      const res = await fetch(`${API_BASE}/api/margin/lookups/contractors?have_all=true`);
      if (!res.ok) throw new Error('Ошибка загрузки контрагентов');
      return (await res.json()) as LookupItemResponse[];
    },
  });
  const stuffQuery = useQuery({
    queryKey: ['margin', 'lookups', 'stuff-categories'],
    queryFn: async () => {
      const res = await fetch(`${API_BASE}/api/margin/lookups/stuff-categories?have_all=true`);
      if (!res.ok) throw new Error('Ошибка загрузки категорий');
      return (await res.json()) as LookupItemResponse[];
    },
  });
  const routesQuery = useQuery({
    queryKey: ['margin', 'lookups', 'routes'],
    queryFn: async () => {
      const res = await fetch(`${API_BASE}/api/margin/lookups/routes?have_all=true`);
      if (!res.ok) throw new Error('Ошибка загрузки маршрутов');
      return (await res.json()) as LookupItemResponse[];
    },
  });

  useEffect(() => {
    progress.startInitialLoadProgress();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (progress.loadingPhase !== 'initial' || progress.steps.length === 0) return;
    const lookupQueries = [usersQuery, deptsQuery, contractorsQuery, stuffQuery, routesQuery];
    let doneCount = 0;
    let hasError = false;
    let errorMsg = '';
    lookupQueries.forEach((q, i) => {
      const id = LOOKUP_STEP_IDS[i];
      if (q.isSuccess) {
        progress.setStepStatus(id, 'done');
        doneCount += 1;
      } else if (q.isError) {
        progress.setStepStatus(id, 'error');
        hasError = true;
        errorMsg = q.error instanceof Error ? q.error.message : String(q.error);
      }
    });
    progress.setDetails(`Загружено справочников: ${doneCount}/${LOOKUP_STEP_IDS.length}`);
    if (hasError) progress.failProgress(errorMsg);
    else if (doneCount === LOOKUP_STEP_IDS.length) progress.finishProgress();
  }, [
    progress.loadingPhase,
    progress.steps.length,
    usersQuery.isSuccess,
    usersQuery.isError,
    usersQuery.error,
    deptsQuery.isSuccess,
    deptsQuery.isError,
    deptsQuery.error,
    contractorsQuery.isSuccess,
    contractorsQuery.isError,
    contractorsQuery.error,
    stuffQuery.isSuccess,
    stuffQuery.isError,
    stuffQuery.error,
    routesQuery.isSuccess,
    routesQuery.isError,
    routesQuery.error,
    progress.setStepStatus,
    progress.setDetails,
    progress.finishProgress,
    progress.failProgress,
  ]);

  const userOptions = useMemo(() => (usersQuery.data ?? []).map((i) => ({ label: i.name, value: i.id })), [usersQuery.data]);
  const deptOptions = useMemo(() => (deptsQuery.data ?? []).map((i) => ({ label: i.name, value: i.id })), [deptsQuery.data]);
  const contractorOptions = useMemo(() => (contractorsQuery.data ?? []).map((i) => ({ label: i.name, value: i.id })), [contractorsQuery.data]);
  const stuffOptions = useMemo(() => (stuffQuery.data ?? []).map((i) => ({ label: i.name, value: i.id })), [stuffQuery.data]);
  const routeOptions = useMemo(() => (routesQuery.data ?? []).map((i) => ({ label: i.name, value: i.id })), [routesQuery.data]);

  const columnDefs = useMemo<ColDef[]>(() => {
    const base: { field: string; headerName: string; filter: string | boolean; cellClass?: string; hide?: boolean }[] = [
      { field: 'ctr_name', headerName: 'Контрагент', filter: true },
      { field: 'cut_name', headerName: 'Страна', filter: true },
      { field: 'con_number_formatted', headerName: '№ контракта', filter: true },
      { field: 'con_date_formatted', headerName: 'Дата контракта', filter: 'agDateColumnFilter' },
      { field: 'spc_number_formatted', headerName: '№ спецификации', filter: true },
      { field: 'spc_date_formatted', headerName: 'Дата спецификации', filter: 'agDateColumnFilter' },
      { field: 'spc_summ_formatted', headerName: 'Сумма', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'cur_name', headerName: 'Валюта', filter: true },
      { field: 'stf_name_show', headerName: 'Продукт (производитель)', filter: true },
      { field: 'shp_number_show', headerName: '№ отгрузки', filter: true },
      { field: 'shp_date_show', headerName: 'Дата отгрузки', filter: 'agDateColumnFilter' },
      { field: 'pay_date_show', headerName: 'Дата оплаты', filter: 'agDateColumnFilter' },
      { field: 'lps_summ_eur_formatted', headerName: 'Сумма, EUR', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lps_summ_formatted', headerName: 'Сумма без НДС', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lps_sum_transport_formatted', headerName: 'Транспорт', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lcc_transport_formatted', headerName: 'Транспорт Минск‑Клиент', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lps_custom_formatted', headerName: 'Таможенные', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lcc_charges_formatted', headerName: 'Логистика', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lcc_montage_formatted', headerName: 'Монтаж и наладка', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lps_montage_time_formatted', headerName: 'Время на монтаж, часы', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'montage_cost_formatted', headerName: 'Ст-ть монтажа (норматив)', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'lcc_update_sum_formatted', headerName: 'Корректировка', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'summ_formatted', headerName: 'Сумма товара', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'summ_zak_formatted', headerName: 'Сумма закупки', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'margin_formatted', headerName: 'Маржа', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'koeff_formatted', headerName: 'Средний коэфф‑т', cellClass: 'ag-right-aligned-cell', filter: true },
      { field: 'usr_name_show', headerName: 'Пользователь', filter: true },
      { field: 'dep_name_show', headerName: 'Отдел', filter: true },
    ];
    return base.map((col) => {
      const viewKey = COLUMN_VIEW_KEY[col.field];
      const hide = viewKey ? !viewFlags[viewKey] : false;
      return { ...col, hide };
    });
  }, [viewFlags]);

  const getRowClass = useCallback((params: { data?: { itogLine?: boolean; spc_group_delivery?: string; haveUnblockedPrc?: boolean } }) => {
    const d = params.data;
    if (!d) return '';
    if (d.itogLine) return 'mg-itog';
    if (d.spc_group_delivery && d.haveUnblockedPrc) return 'mg-group-unblocked';
    if (d.spc_group_delivery) return 'mg-group';
    if (d.haveUnblockedPrc) return 'mg-unblocked';
    return '';
  }, []);

  const gridApiRef = useRef<AgGridReact<unknown> | null>(null);
  const onExportCsv = useCallback(() => {
    const api = gridApiRef.current?.api;
    if (api) api.exportDataAsCsv({ fileName: 'margin_export.csv' });
  }, []);
  const onResetGridFilter = useCallback(() => {
    setQuickFilterText('');
    const api = gridApiRef.current?.api;
    if (api) api.setFilterModel(null);
  }, []);

  const data = isGridActive ? (gridQuery.data?.data ?? []) : [];
  const meta = isGridActive ? gridQuery.data?.meta : null;
  const errorMessage = isGridActive && gridQuery.error ? (gridQuery.error instanceof Error ? gridQuery.error.message : String(gridQuery.error)) : null;

  const onGridFirstDataRendered = useCallback(() => {
    if (progress.loadingPhase === 'generate') {
      progress.setStepStatus('render', 'done');
      progress.finishProgress();
    }
  }, [progress.loadingPhase, progress.setStepStatus, progress.finishProgress]);

  return (
    <Layout.Content className="app-content" style={{ padding: 24 }}>
      <Typography.Title level={3} style={{ marginBottom: 16 }}>Отчеты → Маржа</Typography.Title>

      <MarginProgressUI
        {...progress}
        onDismissError={progress.dismissError}
      />

      <Space direction="vertical" size="middle" style={{ width: '100%' }}>
        <Space wrap align="start">
          <Space>
            <span>Период с:</span>
            <DatePicker
              format={DATE_FORMAT}
              value={dateBegin ? dayjs(dateBegin, DATE_FORMAT) : null}
              onChange={(_, dateStr) => setDateBegin(Array.isArray(dateStr) ? dateStr[0] ?? null : dateStr ?? null)}
            />
          </Space>
          <Space>
            <span>по:</span>
            <DatePicker
              format={DATE_FORMAT}
              value={dateEnd ? dayjs(dateEnd, DATE_FORMAT) : null}
              onChange={(_, dateStr) => setDateEnd(Array.isArray(dateStr) ? dateStr[0] ?? null : dateStr ?? null)}
            />
          </Space>
        </Space>

        <Space wrap>
          <Space>
            <span>Пользователь:</span>
            <Select
              allowClear
              placeholder="— Все —"
              style={{ minWidth: 180 }}
              options={userOptions}
              value={user?.id ?? undefined}
              onSelect={(v) => { aspectOnly(() => setUserAspect(false)); handleSelectUser(v); }}
              onClear={() => setUser(null)}
              loading={usersQuery.isLoading}
            />
            <Checkbox checked={userAspect} disabled={!user} onChange={(e) => aspectOnly(() => setUserAspect(e.target.checked))}>Аспект</Checkbox>
          </Space>
          <Space>
            <span>Отдел:</span>
            <Select
              allowClear
              placeholder="— Все —"
              style={{ minWidth: 140 }}
              options={deptOptions}
              value={department?.id ?? undefined}
              onSelect={(v) => { aspectOnly(() => setDepartmentAspect(false)); handleSelectDepartment(v); }}
              onClear={() => setDepartment(null)}
              loading={deptsQuery.isLoading}
            />
            <Checkbox checked={departmentAspect} disabled={!department} onChange={(e) => aspectOnly(() => setDepartmentAspect(e.target.checked))}>Аспект</Checkbox>
          </Space>
          <Space>
            <span>Контрагент:</span>
            <Select
              allowClear
              placeholder="— Все —"
              style={{ minWidth: 160 }}
              options={contractorOptions}
              value={contractor?.id ?? undefined}
              onSelect={(v) => { aspectOnly(() => setContractorAspect(false)); handleSelectContractor(v); }}
              onClear={() => setContractor(null)}
              loading={contractorsQuery.isLoading}
            />
            <Checkbox checked={contractorAspect} disabled={!contractor} onChange={(e) => aspectOnly(() => setContractorAspect(e.target.checked))}>Аспект</Checkbox>
          </Space>
          <Space>
            <span>Категория:</span>
            <Select
              allowClear
              placeholder="— Все —"
              style={{ minWidth: 120 }}
              options={stuffOptions}
              value={stuffCategory?.id ?? undefined}
              onSelect={(v) => { aspectOnly(() => setStuffCategoryAspect(false)); handleSelectStuffCategory(v); }}
              onClear={() => setStuffCategory(null)}
              loading={stuffQuery.isLoading}
            />
            <Checkbox checked={stuffCategoryAspect} disabled={!stuffCategory} onChange={(e) => aspectOnly(() => setStuffCategoryAspect(e.target.checked))}>Аспект</Checkbox>
          </Space>
          <Space>
            <span>Маршрут:</span>
            <Select
              allowClear
              placeholder="— Все —"
              style={{ minWidth: 120 }}
              options={routeOptions}
              value={route?.id ?? undefined}
              onSelect={(v) => { aspectOnly(() => setRouteAspect(false)); handleSelectRoute(v); }}
              onClear={() => setRoute(null)}
              loading={routesQuery.isLoading}
            />
            <Checkbox checked={routeAspect} disabled={!route} onChange={(e) => aspectOnly(() => setRouteAspect(e.target.checked))}>Аспект</Checkbox>
          </Space>
        </Space>

        <Space wrap>
          <Checkbox
            checked={onlyTotal}
            onChange={(e) => {
              const v = e.target.checked;
              if (v) setItogBySpec(false);
              setOnlyTotal(v);
            }}
          >
            Выводить только итоги
          </Checkbox>
          <Checkbox checked={itogBySpec} onChange={(e) => { setItogBySpec(e.target.checked); if (!e.target.checked) { setItogByUser(false); setItogByProduct(false); } }}>Выводить итоги по спецификациям договоров</Checkbox>
          <Checkbox checked={itogByUser} disabled={!itogBySpec} onChange={(e) => { setItogByUser(e.target.checked); if (!e.target.checked) setItogByProduct(false); }}>Разбивать итог спецификации на пользователей</Checkbox>
          <Checkbox checked={itogByProduct} disabled={!itogByUser} onChange={(e) => setItogByProduct(e.target.checked)}>Разбивать итог пользователя на итоги по продуктам</Checkbox>
          <Checkbox checked={getNotBlock} onChange={(e) => setGetNotBlock(e.target.checked)}>Включить в отчёт незаблокированные закрытия договоров</Checkbox>
        </Space>

        <div style={{ marginTop: 8, overflowX: 'auto', maxWidth: '100%' }}>
          <Space wrap align="start" size="small">
            <Typography.Text strong>Колонки:</Typography.Text>
            {VIEW_FLAG_LABELS.map(({ key, label }) => (
              <Checkbox
                key={key}
                checked={viewFlags[key]}
                onChange={(e) => setViewFlags((prev) => ({ ...prev, [key]: e.target.checked }))}
              >
                {label}
              </Checkbox>
            ))}
          </Space>
        </div>

        <Space>
          <Button onClick={() => cleanAllMutation.mutate()} loading={cleanAllMutation.isPending}>Сбросить всё</Button>
          <Button
            type="primary"
            disabled={!canGenerate || progress.loadingPhase === 'generate'}
            onClick={() => {
              progress.startGenerateProgress();
              progress.setStepStatus('request', 'active');
              generateMutation.mutate();
            }}
            loading={generateMutation.isPending}
          >
            Сформировать
          </Button>
          <Button disabled={!canGenerate || progress.loadingPhase === 'export'} onClick={handleExcel}>Excel</Button>
        </Space>
      </Space>

      <div style={{ marginTop: 16 }}>
        <Space style={{ marginBottom: 8 }}>
          <span>Грузить:</span>
          <Select
            value={limit}
            options={LIMIT_OPTIONS.map((n) => ({ label: String(n), value: n }))}
            onChange={(v) => setLimit(v)}
            style={{ width: 80 }}
          />
          <span>Показывать:</span>
          <Select
            value={pageSize}
            options={PAGE_SIZE_OPTIONS.map((n) => ({ label: String(n), value: n }))}
            onChange={(v) => setPageSize(v)}
            style={{ width: 80 }}
          />
          <Button size="small" onClick={() => queryClient.invalidateQueries({ queryKey: ['margin', 'data'] })}>Обновить</Button>
          <Input placeholder="Поиск…" allowClear value={quickFilterText} onChange={(e) => setQuickFilterText(e.target.value)} style={{ width: 160 }} />
          <Button size="small" onClick={onResetGridFilter}>Сбросить фильтр</Button>
          <Button size="small" onClick={onExportCsv}>Экспорт CSV</Button>
        </Space>
        {gridQuery.isLoading && <Typography.Text type="secondary">Загрузка…</Typography.Text>}
        {meta != null && !gridQuery.isLoading && <Typography.Text type="secondary">Записей: {meta.rowsReturned} {meta.limited ? `(не более ${limit})` : ''}</Typography.Text>}
        {errorMessage && <Typography.Text type="danger">{errorMessage}</Typography.Text>}

        <AgGridShell style={{ width: '100%', height: 520, marginTop: 8 }}>
          <AgGridReact
            theme="legacy"
            ref={gridApiRef}
            rowData={errorMessage ? [] : data}
            columnDefs={columnDefs}
            loading={gridQuery.isLoading}
            getRowClass={getRowClass}
            onFirstDataRendered={onGridFirstDataRendered}
            pagination
            paginationPageSize={pageSize}
            paginationPageSizeSelector={PAGE_SIZE_OPTIONS}
            quickFilterText={quickFilterText}
            animateRows
            suppressExcelExport
            defaultColDef={{ sortable: true, floatingFilter: true }}
          />
        </AgGridShell>
      </div>

      <style>{`
        .mg-itog { font-weight: bold; }
        .mg-group { background-color: #d4edda !important; }
        .mg-unblocked { background-color: #f8d7da !important; }
        .mg-group-unblocked { background: linear-gradient(90deg, #d4edda, #f8d7da) !important; }
      `}</style>
    </Layout.Content>
  );
}
