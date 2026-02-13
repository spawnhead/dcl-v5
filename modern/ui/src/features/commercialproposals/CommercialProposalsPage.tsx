/**
 * Commercial Proposals list. docs/screens/commercial_proposals/.
 * Legacy: CommercialProposalsAction.do, CommercialProposals.jsp.
 */
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Button, Card, Checkbox, Col, DatePicker, Dropdown, Form, Input, InputNumber, Row, Select, Space, Typography } from 'antd';
import { ClearOutlined, CopyOutlined, FilterOutlined, LockOutlined, UnlockOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import { useCallback, useEffect, useMemo, useState } from 'react';
import type { CpDataRequest, CpDataResponse, CpRowDto, CpLookupsResponse, LookupItemDto } from './types';
import { showLoading, hideLoading } from '../../shared/lib/feedback';
import { notifySuccess, notifyError } from '../../shared/lib/feedback';

dayjs.extend(customParseFormat);
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import { AgGridShell } from '../../shared/ui/AgGridShell';

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const DATE_FORMAT = 'DD.MM.YYYY';
const PAGE_SIZE = 15;

function buildDataBody(req: CpDataRequest): CpDataRequest {
  return {
    cprNumber: req.cprNumber ?? '',
    department: req.department ?? null,
    contractor: req.contractor ?? null,
    user: req.user ?? null,
    stuffCategory: req.stuffCategory ?? null,
    cprDateFrom: req.cprDateFrom ?? '',
    cprDateTo: req.cprDateTo ?? '',
    cprSumFrom: req.cprSumFrom ?? null,
    cprSumTo: req.cprSumTo ?? null,
    cprProposalReceivedFlag: req.cprProposalReceivedFlag ?? false,
    cprProposalDeclined: req.cprProposalDeclined ?? false,
    page: req.page ?? 1,
    pageSize: req.pageSize ?? PAGE_SIZE,
  };
}

export default function CommercialProposalsPage() {
  const navigate = useNavigate();
  const [cprNumber, setCprNumber] = useState('');
  const [department, setDepartment] = useState<LookupItemDto | null>(null);
  const [contractor, setContractor] = useState<LookupItemDto | null>(null);
  const [user, setUser] = useState<LookupItemDto | null>(null);
  const [stuffCategory, setStuffCategory] = useState<LookupItemDto | null>(null);
  const [cprDateFrom, setCprDateFrom] = useState<string | null>(null);
  const [cprDateTo, setCprDateTo] = useState<string | null>(null);
  const [cprSumFrom, setCprSumFrom] = useState<number | null>(null);
  const [cprSumTo, setCprSumTo] = useState<number | null>(null);
  const [cprProposalReceivedFlag, setCprProposalReceivedFlag] = useState(false);
  const [cprProposalDeclined, setCprProposalDeclined] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [gridData, setGridData] = useState<CpDataResponse | null>(null);
  const [gridLoading, setGridLoading] = useState(false);
  const [gridError, setGridError] = useState<string | null>(null);
  const [lookups, setLookups] = useState<CpLookupsResponse | null>(null);
  const [lookupsLoading, setLookupsLoading] = useState(true);
  const [canBlock, setCanBlock] = useState(true);

  useEffect(() => {
    fetch(`${API_BASE}/api/me`)
      .then((r) => r.ok ? r.json() : null)
      .then((me: { roles?: string[] } | null) => {
        if (me?.roles) {
          setCanBlock(me.roles.some((r) => r === 'admin' || r === 'economist'));
        }
      })
      .catch(() => {});
  }, []);

  const getFilterState = useCallback((): CpDataRequest => ({
    cprNumber: cprNumber || undefined,
    department: department ?? undefined,
    contractor: contractor ?? undefined,
    user: user ?? undefined,
    stuffCategory: stuffCategory ?? undefined,
    cprDateFrom: cprDateFrom ?? undefined,
    cprDateTo: cprDateTo ?? undefined,
    cprSumFrom: cprSumFrom ?? undefined,
    cprSumTo: cprSumTo ?? undefined,
    cprProposalReceivedFlag,
    cprProposalDeclined,
    page: currentPage,
    pageSize: PAGE_SIZE,
  }), [cprNumber, department, contractor, user, stuffCategory, cprDateFrom, cprDateTo, cprSumFrom, cprSumTo, cprProposalReceivedFlag, cprProposalDeclined, currentPage]);

  const fetchData = useCallback((body: CpDataRequest) => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/commercial-proposals/data`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(buildDataBody(body)),
    })
      .then((r) => r.json())
      .then((json: CpDataResponse) => {
        if (json.items == null) throw new Error('Invalid response');
        setGridData(json);
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка загрузки'))
      .finally(() => setGridLoading(false));
  }, []);

  const fetchPage = useCallback((direction: 'next' | 'prev', page: number) => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/commercial-proposals/page`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        direction,
        currentPage: page,
        filterState: buildDataBody(getFilterState()),
      }),
    })
      .then((r) => r.json())
      .then((json: CpDataResponse) => {
        if (json.items == null) throw new Error('Invalid response');
        setGridData(json);
        setCurrentPage(json.page);
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка'))
      .finally(() => setGridLoading(false));
  }, [getFilterState]);

  const handleBlock = useCallback((cprId: string) => {
    showLoading('Обновление...');
    fetch(`${API_BASE}/api/commercial-proposals/block`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cprId }),
    })
      .then((r) => {
        hideLoading();
        if (r.ok) {
          notifySuccess('Статус блокировки изменён');
          fetchData({ ...getFilterState(), page: currentPage });
        } else {
          notifyError('Ошибка при изменении блокировки');
        }
      })
      .catch(() => {
        hideLoading();
        notifyError('Ошибка при изменении блокировки');
      });
  }, [getFilterState, currentPage, fetchData]);

  const handleClone = useCallback((cprId: string, mode: 'new' | 'old') => {
    showLoading('Клонирование...');
    fetch(`${API_BASE}/api/commercial-proposals/clone`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cprId, mode }),
    })
      .then((r) => r.json())
      .then((json: { newCprId?: string | null }) => {
        hideLoading();
        if (json.newCprId) {
          notifySuccess('КП склонировано');
          fetchData({ ...getFilterState(), page: 1 });
        } else {
          notifyError('Не удалось склонировать');
        }
      })
      .catch(() => {
        hideLoading();
        notifyError('Ошибка при клонировании');
      });
  }, [getFilterState, fetchData]);

  useEffect(() => {
    fetch(`${API_BASE}/api/commercial-proposals/lookups`)
      .then((r) => r.json())
      .then((json: CpLookupsResponse) => {
        setLookups(json);
        const def = json.defaults;
        if (def) {
          setCprNumber(def.cprNumber ?? '');
          setDepartment(def.department ?? null);
          setContractor(def.contractor ?? null);
          setUser(def.user ?? null);
          setStuffCategory(def.stuffCategory ?? null);
          setCprDateFrom(def.cprDateFrom ? def.cprDateFrom : null);
          setCprDateTo(def.cprDateTo ? def.cprDateTo : null);
          setCprSumFrom(def.cprSumFrom ?? null);
          setCprSumTo(def.cprSumTo ?? null);
          setCprProposalReceivedFlag(def.cprProposalReceivedFlag ?? false);
          setCprProposalDeclined(def.cprProposalDeclined ?? false);
        }
        setCurrentPage(1);
        const initialBody: CpDataRequest = {
          cprNumber: def?.cprNumber ?? '',
          department: def?.department ?? null,
          contractor: def?.contractor ?? null,
          user: def?.user ?? null,
          stuffCategory: def?.stuffCategory ?? null,
          cprDateFrom: def?.cprDateFrom ?? '',
          cprDateTo: def?.cprDateTo ?? '',
          cprSumFrom: def?.cprSumFrom ?? null,
          cprSumTo: def?.cprSumTo ?? null,
          cprProposalReceivedFlag: def?.cprProposalReceivedFlag ?? false,
          cprProposalDeclined: def?.cprProposalDeclined ?? false,
          page: 1,
          pageSize: PAGE_SIZE,
        };
        setGridLoading(true);
        setGridError(null);
        fetch(`${API_BASE}/api/commercial-proposals/data`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(buildDataBody(initialBody)),
        })
          .then((res) => res.json())
          .then((gridJson: CpDataResponse) => {
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
    notifySuccess('Фильтр применён');
  }, [getFilterState, fetchData]);

  const clearFilter = useCallback(() => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/commercial-proposals/cleanAll`, { method: 'POST' })
      .then((r) => r.json())
      .then((json: { defaults?: CpLookupsResponse['defaults']; grid?: CpDataResponse }) => {
        const def = json.defaults;
        if (def) {
          setCprNumber(def.cprNumber ?? '');
          setDepartment(def.department ?? null);
          setContractor(def.contractor ?? null);
          setUser(def.user ?? null);
          setStuffCategory(def.stuffCategory ?? null);
          setCprDateFrom(def.cprDateFrom ? def.cprDateFrom : null);
          setCprDateTo(def.cprDateTo ? def.cprDateTo : null);
          setCprSumFrom(def.cprSumFrom ?? null);
          setCprSumTo(def.cprSumTo ?? null);
          setCprProposalReceivedFlag(def.cprProposalReceivedFlag ?? false);
          setCprProposalDeclined(def.cprProposalDeclined ?? false);
        }
        if (json.grid) {
          setGridData(json.grid);
          setCurrentPage(json.grid.page);
        }
        notifySuccess('Фильтр очищен');
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка'))
      .finally(() => setGridLoading(false));
  }, []);

  const departments = lookups?.lookups?.departments ?? [];
  const contractors = lookups?.lookups?.contractors ?? [];
  const users = lookups?.lookups?.users ?? [];
  const stuffCategories = lookups?.lookups?.stuffCategories ?? [];

  const columnDefs: ColDef<CpRowDto>[] = useMemo(() => [
    { field: 'cprNumber', headerName: '№', flex: 1, minWidth: 130 },
    { field: 'cprDate', headerName: 'Дата', width: 100 },
    { field: 'cprContractor', headerName: 'Контрагент', flex: 1, minWidth: 140 },
    { field: 'cprSumFormatted', headerName: 'Сумма', flex: 1, minWidth: 100, cellClass: 'ag-right-aligned-cell' },
    { field: 'cprCurrency', headerName: 'Валюта', width: 70 },
    { field: 'cprStfName', headerName: 'Категория', flex: 1, minWidth: 100 },
    { field: 'reservedState', headerName: 'Резерв', width: 80 },
    {
      field: 'cprBlock',
      headerName: 'Блок',
      width: 60,
      cellRenderer: (p: { data?: CpRowDto }) =>
        p.data ? <input type="checkbox" checked={p.data.cprBlock === '1'} readOnly disabled /> : null,
    },
    { field: 'cprUser', headerName: 'Пользователь', flex: 1, minWidth: 90 },
    { field: 'cprDepartment', headerName: 'Отдел', flex: 1, minWidth: 90 },
    { field: 'cprCheckPrice', headerName: 'Проверка', width: 80 },
    {
      headerName: '',
      width: 120,
      cellRenderer: (p: { data?: CpRowDto }) => {
        if (!p.data) return null;
        const items = [
          { key: 'edit', label: 'Редактировать', onClick: () => navigate(`/commercial-proposals/${p.data!.cprId}/edit`) },
          { key: 'cloneNew', label: 'Клон (новый)', onClick: () => handleClone(p.data!.cprId, 'new') },
          { key: 'cloneOld', label: 'Клон (старый)', onClick: () => handleClone(p.data!.cprId, 'old') },
          ...(canBlock ? [{ key: 'block', label: p.data.cprBlock === '1' ? 'Разблокировать' : 'Заблокировать', onClick: () => handleBlock(p.data!.cprId) }] : []),
        ];
        return (
          <Dropdown menu={{ items }} trigger={['click']}>
            <Button type="link" size="small">Действия</Button>
          </Dropdown>
        );
      },
    },
  ], [navigate, handleClone, handleBlock, canBlock]);

  const getRowClass = useCallback((params: { data?: CpRowDto }) => {
    if (params.data?.cprBlock === '1') return 'blocked-row';
    return '';
  }, []);

  const hasNextPage = gridData?.hasNextPage ?? false;
  const hasPrevPage = currentPage > 1;

  return (
    <div className="app-content" style={{ padding: 24, minHeight: 'calc(100vh - 64px)' }}>
      <div style={{ marginBottom: 24 }}>
        <Typography.Title level={2} style={{ margin: 0 }}>
          Коммерческие предложения
        </Typography.Title>
      </div>

      <Card bordered={false} style={{ marginBottom: 24, borderRadius: 8 }}>
        <Form layout="vertical" name="cp_filter">
          <Row gutter={[16, 16]}>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input placeholder="Номер КП" value={cprNumber} onChange={(e) => setCprNumber(e.target.value)} maxLength={50} />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Select
                  placeholder="Отдел"
                  allowClear
                  showSearch
                  optionFilterProp="label"
                  value={department?.id ?? undefined}
                  onChange={(v) => setDepartment(v ? departments.find((d) => d.id === v) ?? null : null)}
                  options={departments.map((d) => ({ value: d.id, label: d.name }))}
                  style={{ width: '100%' }}
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
                  placeholder="Категория"
                  allowClear
                  showSearch
                  optionFilterProp="label"
                  value={stuffCategory?.id ?? undefined}
                  onChange={(v) => setStuffCategory(v ? stuffCategories.find((s) => s.id === v) ?? null : null)}
                  options={stuffCategories.map((s) => ({ value: s.id, label: s.name }))}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <DatePicker
                  placeholder="Дата с"
                  format={DATE_FORMAT}
                  value={cprDateFrom ? dayjs(cprDateFrom, DATE_FORMAT) : null}
                  onChange={(_, dateString) => setCprDateFrom(dateString ?? null)}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <DatePicker
                  placeholder="Дата по"
                  format={DATE_FORMAT}
                  value={cprDateTo ? dayjs(cprDateTo, DATE_FORMAT) : null}
                  onChange={(_, dateString) => setCprDateTo(dateString ?? null)}
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <InputNumber placeholder="Сумма от" value={cprSumFrom ?? undefined} onChange={(v) => setCprSumFrom(v ?? null)} style={{ width: '100%' }} />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <InputNumber placeholder="Сумма до" value={cprSumTo ?? undefined} onChange={(v) => setCprSumTo(v ?? null)} style={{ width: '100%' }} />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Space>
                  <Checkbox checked={cprProposalReceivedFlag} onChange={(e) => setCprProposalReceivedFlag(e.target.checked)}>Принято</Checkbox>
                  <Checkbox checked={cprProposalDeclined} onChange={(e) => setCprProposalDeclined(e.target.checked)}>Отклонено</Checkbox>
                </Space>
              </Form.Item>
            </Col>
            <Col xs={24} lg={8} xl={8} style={{ display: 'flex', gap: 8, alignItems: 'flex-start' }}>
              <Button type="primary" icon={<FilterOutlined />} onClick={applyFilter}>Применить</Button>
              <Button icon={<ClearOutlined />} onClick={clearFilter}>Очистить</Button>
            </Col>
          </Row>
        </Form>
      </Card>

      {gridError && (
        <Typography.Text type="danger" style={{ display: 'block', marginBottom: 16 }}>{gridError}</Typography.Text>
      )}

      <Card bordered={false} bodyStyle={{ padding: 0 }} style={{ borderRadius: 8, overflow: 'hidden', marginBottom: 24 }}>
        <style>{`.blocked-row { text-decoration: line-through; }`}</style>
        <div style={{ overflowX: 'auto' }}>
          <AgGridShell style={{ width: '100%', height: 480 }}>
            <AgGridReact<CpRowDto>
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
            <Button size="small" disabled={!hasPrevPage || gridLoading} onClick={() => fetchPage('prev', currentPage - 1)}>Назад</Button>
            <Typography.Text>Стр. {currentPage}</Typography.Text>
            <Button size="small" disabled={!hasNextPage || gridLoading} onClick={() => fetchPage('next', currentPage + 1)}>Вперёд</Button>
          </Space>
        </div>
      </Card>
    </div>
  );
}
