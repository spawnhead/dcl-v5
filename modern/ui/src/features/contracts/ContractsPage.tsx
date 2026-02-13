/**
 * Contracts list (N3). Parity per docs/screens/contracts (SNAPSHOT, CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX).
 * Legacy: ContractsAction.do, Contracts.jsp.
 */
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Button, Card, Checkbox, Col, DatePicker, Form, Input, InputNumber, Layout, message, Row, Select, Space, Typography } from 'antd';
import { ClearOutlined, FilterOutlined, PlusOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import { useCallback, useEffect, useMemo, useState } from 'react';
import type {
  ContractDataRequest,
  ContractDataResponse,
  ContractRowDto,
  ContractsLookupsResponse,
  LookupItemDto,
} from './types';

dayjs.extend(customParseFormat);
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import { AgGridShell } from '../../shared/ui/AgGridShell';

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const DATE_FORMAT = 'DD.MM.YYYY';
const PAGE_SIZE = 15;

function buildDataBody(req: ContractDataRequest): ContractDataRequest {
  return {
    number: req.number ?? '',
    contractor: req.contractor ?? null,
    dateBegin: req.dateBegin ?? '',
    dateEnd: req.dateEnd ?? '',
    sumMin: req.sumMin ?? null,
    sumMax: req.sumMax ?? null,
    user: req.user ?? null,
    seller: req.seller ?? null,
    executed: req.executed ?? false,
    notExecuted: req.notExecuted ?? false,
    oridinalAbsent: req.oridinalAbsent ?? false,
    page: req.page ?? 1,
    pageSize: req.pageSize ?? PAGE_SIZE,
  };
}

export default function ContractsPage() {
  const navigate = useNavigate();
  const [number, setNumber] = useState('');
  const [dateBegin, setDateBegin] = useState<string | null>(null);
  const [dateEnd, setDateEnd] = useState<string | null>(null);
  const [contractor, setContractor] = useState<LookupItemDto | null>(null);
  const [sumMin, setSumMin] = useState<number | null>(null);
  const [sumMax, setSumMax] = useState<number | null>(null);
  const [user, setUser] = useState<LookupItemDto | null>(null);
  const [seller, setSeller] = useState<LookupItemDto | null>(null);
  const [executed, setExecuted] = useState(false);
  const [notExecuted, setNotExecuted] = useState(false);
  const [oridinalAbsent, setOridinalAbsent] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [gridData, setGridData] = useState<ContractDataResponse | null>(null);
  const [gridLoading, setGridLoading] = useState(false);
  const [gridError, setGridError] = useState<string | null>(null);
  const [lookups, setLookups] = useState<ContractsLookupsResponse | null>(null);
  const [lookupsLoading, setLookupsLoading] = useState(true);
  const [canCreate, setCanCreate] = useState(true);

  useEffect(() => {
    fetch(`${API_BASE}/api/me`)
      .then((r) => r.ok ? r.json() : null)
      .then((me: { roles?: string[] } | null) => {
        if (me?.roles) {
          const createRoles = ['admin', 'economist', 'lawyer'];
          setCanCreate(me.roles.some((r) => createRoles.includes(r)));
        }
      })
      .catch(() => {});
  }, []);

  const getFilterState = useCallback((): ContractDataRequest => ({
    number: number || undefined,
    contractor: contractor ?? undefined,
    dateBegin: dateBegin ?? undefined,
    dateEnd: dateEnd ?? undefined,
    sumMin: sumMin ?? undefined,
    sumMax: sumMax ?? undefined,
    user: user ?? undefined,
    seller: seller ?? undefined,
    executed,
    notExecuted,
    oridinalAbsent,
    page: currentPage,
    pageSize: PAGE_SIZE,
  }), [number, contractor, dateBegin, dateEnd, sumMin, sumMax, user, seller, executed, notExecuted, oridinalAbsent, currentPage]);

  const fetchData = useCallback((body: ContractDataRequest) => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/contracts/data`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(buildDataBody(body)),
    })
      .then((r) => r.json())
      .then((json: ContractDataResponse) => {
        if (json.items == null) throw new Error('Invalid response');
        setGridData(json);
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка загрузки'))
      .finally(() => setGridLoading(false));
  }, []);

  const fetchPage = useCallback((direction: 'next' | 'prev', page: number) => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/contracts/page`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        direction,
        currentPage: page,
        filterState: buildDataBody(getFilterState()),
      }),
    })
      .then((r) => r.json())
      .then((json: ContractDataResponse) => {
        if (json.items == null) throw new Error('Invalid response');
        setGridData(json);
        setCurrentPage(json.page);
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка'))
      .finally(() => setGridLoading(false));
  }, [getFilterState]);

  useEffect(() => {
    fetch(`${API_BASE}/api/contracts/lookups`)
      .then((r) => r.json())
      .then((json: ContractsLookupsResponse) => {
        setLookups(json);
        const def = json.defaults;
        if (def) {
          setNumber(def.number ?? '');
          setDateBegin(def.dateBegin ? def.dateBegin : null);
          setDateEnd(def.dateEnd ? def.dateEnd : null);
          setSumMin(def.sumMin ?? null);
          setSumMax(def.sumMax ?? null);
          setExecuted(def.executed ?? false);
          setNotExecuted(def.notExecuted ?? false);
          setOridinalAbsent(def.oridinalAbsent ?? false);
          setContractor(def.contractor ?? null);
          setUser(def.user ?? null);
          setSeller(def.seller ?? null);
        }
        setCurrentPage(1);
        const initialBody: ContractDataRequest = {
          number: def?.number ?? '',
          contractor: def?.contractor ?? null,
          dateBegin: def?.dateBegin ?? '',
          dateEnd: def?.dateEnd ?? '',
          sumMin: def?.sumMin ?? null,
          sumMax: def?.sumMax ?? null,
          user: def?.user ?? null,
          seller: def?.seller ?? null,
          executed: def?.executed ?? false,
          notExecuted: def?.notExecuted ?? false,
          oridinalAbsent: def?.oridinalAbsent ?? false,
          page: 1,
          pageSize: PAGE_SIZE,
        };
        setGridLoading(true);
        setGridError(null);
        fetch(`${API_BASE}/api/contracts/data`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(buildDataBody(initialBody)),
        })
          .then((res) => res.json())
          .then((gridJson: ContractDataResponse) => {
            if (gridJson.items == null) throw new Error('Invalid response');
            setGridData(gridJson);
          })
          .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка загрузки'))
          .finally(() => setGridLoading(false));
      })
      .catch(() => setLookups(null))
      .finally(() => setLookupsLoading(false));
  }, []);

  const applyFilter = useCallback(() => {
    setCurrentPage(1);
    fetchData({ ...getFilterState(), page: 1 });
    message.success('Фильтр применён');
  }, [getFilterState, fetchData]);

  const clearFilter = useCallback(() => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/contracts/cleanAll`, { method: 'POST' })
      .then((r) => r.json())
      .then((json: { defaults?: DefaultsDto; grid?: ContractDataResponse }) => {
        const def = json.defaults;
        if (def) {
          setNumber(def.number ?? '');
          setDateBegin(def.dateBegin ? def.dateBegin : null);
          setDateEnd(def.dateEnd ? def.dateEnd : null);
          setSumMin(def.sumMin ?? null);
          setSumMax(def.sumMax ?? null);
          setExecuted(def.executed ?? false);
          setNotExecuted(def.notExecuted ?? false);
          setOridinalAbsent(def.oridinalAbsent ?? false);
          setContractor(def.contractor ?? null);
          setUser(def.user ?? null);
          setSeller(def.seller ?? null);
        }
        if (json.grid) {
          setGridData(json.grid);
          setCurrentPage(json.grid.page);
        }
        message.info('Фильтр очищен');
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка'))
      .finally(() => setGridLoading(false));
  }, []);

  const handleExecutedChange = useCallback((v: boolean) => {
    setExecuted(v);
    if (v) setNotExecuted(false);
  }, []);
  const handleNotExecutedChange = useCallback((v: boolean) => {
    setNotExecuted(v);
    if (v) setExecuted(false);
  }, []);

  const contractors = lookups?.lookups?.contractors ?? [];
  const users = lookups?.lookups?.users ?? [];
  const sellers = lookups?.lookups?.sellers ?? [];

  const columnDefs: ColDef<ContractRowDto>[] = useMemo(() => [
    { field: 'conNumber', headerName: 'Номер', flex: 1, minWidth: 110 },
    { field: 'conDate', headerName: 'Дата', flex: 1, minWidth: 100 },
    { field: 'conContractor', headerName: 'Контрагент', flex: 1, minWidth: 140 },
    { field: 'conSumm', headerName: 'Сумма', flex: 1, minWidth: 100, cellClass: 'ag-right-aligned-cell' },
    { field: 'conCurrency', headerName: 'Валюта', width: 70 },
    { field: 'notes', headerName: 'Примечания', flex: 1, minWidth: 120 },
    {
      field: 'conExecuted',
      headerName: 'Исполн.',
      width: 70,
      cellRenderer: (p: { data?: ContractRowDto }) =>
        p.data ? <input type="checkbox" checked={p.data.conExecuted === '1'} readOnly disabled /> : null,
    },
    { field: 'conUser', headerName: 'Пользователь', flex: 1, minWidth: 100 },
    {
      field: 'conReminder',
      headerName: 'Напоминания',
      flex: 1,
      minWidth: 120,
      cellRenderer: (p: { data?: ContractRowDto }) =>
        p.data?.conReminder ? <span dangerouslySetInnerHTML={{ __html: p.data.conReminder }} /> : null,
    },
    {
      headerName: '',
      width: 44,
      cellRenderer: () => <span title="Редактировать">✎</span>,
    },
    {
      field: 'attachIdx',
      headerName: '',
      width: 44,
      valueFormatter: (p) => (p.value >= 1 && p.value <= 6 ? `📎${p.value}` : ''),
    },
    {
      headerName: '',
      width: 44,
      cellRenderer: (p: { data?: ContractRowDto }) =>
        p.data && p.data.spcCount === 0 ? <span title="Удалить">🗑</span> : null,
    },
  ], []);

  const getRowClass = useCallback((params: { data?: ContractRowDto }) => {
    if (params.data?.conAnnul === '1') return 'crossed-cell';
    return '';
  }, []);

  const hasNextPage = gridData?.hasNextPage ?? false;
  const hasPrevPage = currentPage > 1;

  return (
    <Layout.Content className="app-content" style={{ padding: 24, minHeight: 'calc(100vh - 64px)' }}>
      <div style={{ marginBottom: 24 }}>
        <Typography.Title level={2} style={{ margin: 0 }}>
          Договора
        </Typography.Title>
      </div>

      {/* Блок фильтров — в одном стиле с Контрагентами (Figma) */}
      <Card bordered={false} style={{ marginBottom: 24, borderRadius: 8 }}>
        <Form layout="vertical" name="filter_form">
          <Row gutter={[16, 16]}>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="Номер"
                  value={number}
                  onChange={(e) => setNumber(e.target.value)}
                  maxLength={50}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Select
                  placeholder="Контрагент"
                  allowClear
                  showSearch
                  optionFilterProp="label"
                  value={contractor?.id ?? undefined}
                  onChange={(v) => setContractor(v ? contractors.find((c) => c.id === v) ?? null : null)}
                  options={contractors.map((c) => ({ value: c.id, label: c.name }))}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <DatePicker
                  placeholder="Дата с"
                  format={DATE_FORMAT}
                  value={dateBegin ? dayjs(dateBegin, DATE_FORMAT) : null}
                  onChange={(_, dateString) => setDateBegin(dateString ?? null)}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <DatePicker
                  placeholder="Дата по"
                  format={DATE_FORMAT}
                  value={dateEnd ? dayjs(dateEnd, DATE_FORMAT) : null}
                  onChange={(_, dateString) => setDateEnd(dateString ?? null)}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <InputNumber
                  placeholder="Сумма от"
                  value={sumMin ?? undefined}
                  onChange={(v) => setSumMin(v ?? null)}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <InputNumber
                  placeholder="Сумма до"
                  value={sumMax ?? undefined}
                  onChange={(v) => setSumMax(v ?? null)}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Select
                  placeholder="Пользователь"
                  allowClear
                  showSearch
                  optionFilterProp="label"
                  value={user?.id ?? undefined}
                  onChange={(v) => setUser(v ? users.find((u) => u.id === v) ?? null : null)}
                  options={users.map((u) => ({ value: u.id, label: u.name }))}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Select
                  placeholder="Продавец"
                  allowClear
                  showSearch
                  optionFilterProp="label"
                  value={seller?.id ?? undefined}
                  onChange={(v) => setSeller(v ? sellers.find((s) => s.id === v) ?? null : null)}
                  options={sellers.map((s) => ({ value: s.id, label: s.name }))}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Space>
                  <Checkbox checked={executed} onChange={(e) => handleExecutedChange(e.target.checked)}>Исполн.</Checkbox>
                  <Checkbox checked={notExecuted} onChange={(e) => handleNotExecutedChange(e.target.checked)}>Не исполн.</Checkbox>
                  <Checkbox checked={oridinalAbsent} onChange={(e) => setOridinalAbsent(e.target.checked)}>Без оригинала</Checkbox>
                </Space>
              </Form.Item>
            </Col>
            <Col xs={24} lg={8} xl={8} style={{ display: 'flex', gap: 8, alignItems: 'flex-start' }}>
              <Button type="primary" icon={<FilterOutlined />} onClick={applyFilter}>
                Применить фильтр
              </Button>
              <Button icon={<ClearOutlined />} onClick={clearFilter}>
                Очистить фильтр
              </Button>
            </Col>
          </Row>
        </Form>
      </Card>

      {gridError && (
        <Typography.Text type="danger" style={{ display: 'block', marginBottom: 16 }}>
          {gridError}
        </Typography.Text>
      )}

      {/* Таблица — Card с гридом */}
      <Card bordered={false} bodyStyle={{ padding: 0 }} style={{ borderRadius: 8, overflow: 'hidden', marginBottom: 24 }}>
        <style>{`.crossed-cell { text-decoration: line-through; }`}</style>
        <div style={{ overflowX: 'auto' }}>
          <AgGridShell style={{ width: '100%', height: 480 }}>
            <AgGridReact<ContractRowDto>
              rowData={gridData?.items ?? []}
              columnDefs={columnDefs}
              getRowClass={getRowClass}
              loading={gridLoading}
              domLayout="normal"
              defaultColDef={{ sortable: false }}
              suppressCellFocus
            />
          </AgGridShell>
        </div>
        <div style={{ padding: '12px 16px', borderTop: '1px solid var(--ant-color-border)' }}>
          <Space wrap align="center">
            <Button size="small" disabled={!hasPrevPage || gridLoading} onClick={() => fetchPage('prev', currentPage - 1)}>
              Назад
            </Button>
            <Typography.Text>Стр. {currentPage}</Typography.Text>
            <Button size="small" disabled={!hasNextPage || gridLoading} onClick={() => fetchPage('next', currentPage + 1)}>
              Вперёд
            </Button>
          </Space>
        </div>
      </Card>

      {/* Кнопки действий — как на Контрагентах */}
      <div style={{ marginTop: 0 }}>
        <Space wrap>
          <Button onClick={() => navigate('/contracts/import-cp')}>Импорт из КП</Button>
          {canCreate && (
            <Button type="primary" icon={<PlusOutlined />} size="large" onClick={() => navigate('/contracts/new')}>
              Создать
            </Button>
          )}
        </Space>
      </div>
    </Layout.Content>
  );
}

interface DefaultsDto {
  number?: string;
  dateBegin?: string;
  dateEnd?: string;
  sumMin?: number | null;
  sumMax?: number | null;
  executed?: boolean;
  notExecuted?: boolean;
  oridinalAbsent?: boolean;
  contractor?: LookupItemDto | null;
  user?: LookupItemDto | null;
  seller?: LookupItemDto | null;
}
