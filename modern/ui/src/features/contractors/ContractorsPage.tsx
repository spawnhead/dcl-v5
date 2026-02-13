/**
 * Contractors list (References → Контрагенты). CONTRACTS: docs/screens/contractors/.
 * Legacy: ContractorsAction, contractors.jsp.
 */
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Badge, Button, Card, Col, Form, Input, Layout, message, Popconfirm, Row, Select, Space, Typography } from 'antd';
import { ClearOutlined, DeleteOutlined, EditOutlined, FilterOutlined, PlusOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { useCallback, useEffect, useMemo, useState } from 'react';
import type {
  ContractorDataRequest,
  ContractorDataResponse,
  ContractorRowDto,
  ContractorLookupsResponse,
  LookupItemDto,
} from './types';
import { notifySuccess, notifyError } from '../../shared/lib/feedback';

import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import { AgGridShell } from '../../shared/ui/AgGridShell';

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const DEFAULT_PAGE_SIZE = 15;
const PAGE_SIZE_OPTIONS = [10, 15, 25, 50, 100];

function buildDataBody(req: ContractorDataRequest): ContractorDataRequest {
  return {
    ctrName: req.ctrName ?? '',
    ctrFullName: req.ctrFullName ?? '',
    ctrAccount: req.ctrAccount ?? '',
    ctrAddress: req.ctrAddress ?? '',
    ctrEmail: req.ctrEmail ?? '',
    ctrUnp: req.ctrUnp ?? '',
    user: req.user ?? null,
    department: req.department ?? null,
    page: req.page ?? 1,
    pageSize: req.pageSize ?? DEFAULT_PAGE_SIZE,
  };
}

export default function ContractorsPage() {
  const navigate = useNavigate();
  const [ctrName, setCtrName] = useState('');
  const [ctrFullName, setCtrFullName] = useState('');
  const [ctrAccount, setCtrAccount] = useState('');
  const [ctrAddress, setCtrAddress] = useState('');
  const [ctrEmail, setCtrEmail] = useState('');
  const [ctrUnp, setCtrUnp] = useState('');
  const [user, setUser] = useState<LookupItemDto | null>(null);
  const [department, setDepartment] = useState<LookupItemDto | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(DEFAULT_PAGE_SIZE);
  const [gridData, setGridData] = useState<ContractorDataResponse | null>(null);
  const [gridLoading, setGridLoading] = useState(false);
  const [gridError, setGridError] = useState<string | null>(null);
  const [lookups, setLookups] = useState<ContractorLookupsResponse | null>(null);
  const [lookupsLoading, setLookupsLoading] = useState(true);
  const [canCreate, setCanCreate] = useState(true);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    fetch(`${API_BASE}/api/me`)
      .then((r) => (r.ok ? r.json() : null))
      .then((me: { roles?: string[] } | null) => {
        if (me?.roles) {
          setCanCreate(me.roles.some((r) => ['admin', 'economist', 'lawyer'].includes(r)));
          setIsAdmin(me.roles.includes('admin'));
        }
      })
      .catch(() => {});
  }, []);

  const getFilterState = useCallback(
    (): ContractorDataRequest => ({
      ctrName: ctrName || undefined,
      ctrFullName: ctrFullName || undefined,
      ctrAccount: ctrAccount || undefined,
      ctrAddress: ctrAddress || undefined,
      ctrEmail: ctrEmail || undefined,
      ctrUnp: ctrUnp || undefined,
      user: user ?? undefined,
      department: department ?? undefined,
      page: currentPage,
      pageSize,
    }),
    [ctrName, ctrFullName, ctrAccount, ctrAddress, ctrEmail, ctrUnp, user, department, currentPage, pageSize]
  );

  const fetchData = useCallback((body: ContractorDataRequest) => {
    setGridLoading(true);
    setGridError(null);
    fetch(`${API_BASE}/api/contractors/data`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(buildDataBody(body)),
    })
      .then((r) => r.json())
      .then((json: ContractorDataResponse) => {
        if (json.items == null) throw new Error('Invalid response');
        setGridData(json);
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка загрузки'))
      .finally(() => setGridLoading(false));
  }, []);

  const fetchPage = useCallback(
    (direction: 'next' | 'prev') => {
      setGridLoading(true);
      setGridError(null);
      fetch(`${API_BASE}/api/contractors/page`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          direction,
          currentPage,
          filterState: buildDataBody(getFilterState()),
        }),
      })
        .then((r) => r.json())
        .then((json: ContractorDataResponse) => {
          if (json.items == null) throw new Error('Invalid response');
          setGridData(json);
          setCurrentPage(json.page);
        })
        .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка'))
        .finally(() => setGridLoading(false));
    },
    [currentPage, getFilterState]
  );

  useEffect(() => {
    fetch(`${API_BASE}/api/contractors/lookups`)
      .then((r) => r.json())
      .then((json: ContractorLookupsResponse) => {
        setLookups(json);
        const def = json.defaults;
        if (def) {
          setCtrName(def.ctrName ?? '');
          setCtrFullName(def.ctrFullName ?? '');
          setCtrAccount(def.ctrAccount ?? '');
          setCtrAddress(def.ctrAddress ?? '');
          setCtrEmail(def.ctrEmail ?? '');
          setCtrUnp(def.ctrUnp ?? '');
          setUser(def.user ?? null);
          setDepartment(def.department ?? null);
        }
        setCurrentPage(1);
        const initialBody: ContractorDataRequest = {
          ctrName: def?.ctrName ?? '',
          ctrFullName: def?.ctrFullName ?? '',
          ctrAccount: def?.ctrAccount ?? '',
          ctrAddress: def?.ctrAddress ?? '',
          ctrEmail: def?.ctrEmail ?? '',
          ctrUnp: def?.ctrUnp ?? '',
          user: def?.user ?? null,
          department: def?.department ?? null,
          page: 1,
          pageSize: DEFAULT_PAGE_SIZE,
        };
        setGridLoading(true);
        setGridError(null);
        fetch(`${API_BASE}/api/contractors/data`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(buildDataBody(initialBody)),
        })
          .then((res) => res.json())
          .then((gridJson: ContractorDataResponse) => {
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
    fetch(`${API_BASE}/api/contractors/cleanAll`, { method: 'POST' })
      .then((r) => r.json())
      .then((json: ContractorLookupsResponse) => {
        const def = json.defaults;
        if (def) {
          setCtrName(def.ctrName ?? '');
          setCtrFullName(def.ctrFullName ?? '');
          setCtrAccount(def.ctrAccount ?? '');
          setCtrAddress(def.ctrAddress ?? '');
          setCtrEmail(def.ctrEmail ?? '');
          setCtrUnp(def.ctrUnp ?? '');
          setUser(def.user ?? null);
          setDepartment(def.department ?? null);
        }
        setCurrentPage(1);
        fetchData({
          ctrName: '',
          ctrFullName: '',
          ctrAccount: '',
          ctrAddress: '',
          ctrEmail: '',
          ctrUnp: '',
          user: null,
          department: null,
          page: 1,
          pageSize,
        });
        message.info('Фильтр очищен');
      })
      .catch((e) => setGridError(e instanceof Error ? e.message : 'Ошибка'))
      .finally(() => setGridLoading(false));
  }, [fetchData]);

  const handleBlockToggle = useCallback(
    (row: ContractorRowDto) => {
      if (!isAdmin) return;
      const newBlock = row.ctrBlock === '1' ? '0' : '1';
      fetch(`${API_BASE}/api/contractors/block`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ctrId: row.ctrId, block: newBlock }),
      })
        .then((r) => {
          if (r.ok) {
            fetchData(getFilterState());
          }
        })
        .catch(() => {});
    },
    [isAdmin, getFilterState, fetchData]
  );

  const handleDelete = useCallback(
    (row: ContractorRowDto) => {
      fetch(`${API_BASE}/api/contractors/${row.ctrId}`, { method: 'DELETE' })
        .then((r) => {
          if (r.ok) {
            notifySuccess('Контрагент удалён', row.ctrName || undefined);
            fetchData(getFilterState());
          } else if (r.status === 409) {
            notifyError('Ошибка', 'Контрагент занят (есть связанные договора)');
          } else {
            notifyError('Ошибка', 'Не удалось удалить контрагента');
          }
        })
        .catch(() => notifyError('Ошибка', 'Не удалось удалить контрагента'));
    },
    [getFilterState, fetchData]
  );

  const users = lookups?.lookups?.users ?? [];
  const departments = lookups?.lookups?.departments ?? [];

  const columnDefs: ColDef<ContractorRowDto>[] = useMemo(
    () => [
      { field: 'ctrName', headerName: 'Наименование', flex: 1, minWidth: 140 },
      { field: 'ctrFullName', headerName: 'Полное наименование', flex: 1, minWidth: 180 },
      { field: 'ctrAddress', headerName: 'Адрес', flex: 1, minWidth: 140 },
      { field: 'ctrPhone', headerName: 'Телефон', width: 100 },
      { field: 'ctrFax', headerName: 'Факс', width: 100 },
      {
        field: 'ctrEmail',
        headerName: 'Email',
        flex: 1,
        minWidth: 120,
        cellRenderer: (p: { data?: ContractorRowDto }) =>
          p.data?.ctrEmail ? (
            <a href={`mailto:${p.data.ctrEmail}`}>{p.data.ctrEmail}</a>
          ) : null,
      },
      { field: 'ctrBankProps', headerName: 'Банковские реквизиты', flex: 1, minWidth: 120 },
      {
        field: 'ctrBlock',
        headerName: 'Блок',
        width: 70,
        headerComponent: () =>
          isAdmin ? (
            <Badge status="error" text="Блок" />
          ) : (
            <span>Блок</span>
          ),
        cellRenderer: (p: { data?: ContractorRowDto }) =>
          p.data ? (
            <input
              type="checkbox"
              checked={p.data.ctrBlock === '1'}
              disabled={!isAdmin}
              readOnly
              onClick={(e) => {
                e.preventDefault();
                if (isAdmin) handleBlockToggle(p.data!);
              }}
              style={{ cursor: isAdmin ? 'pointer' : 'default' }}
            />
          ) : null,
      },
      {
        headerName: '',
        width: 44,
        cellRenderer: (p: { data?: ContractorRowDto }) =>
          p.data ? (
            <EditOutlined
              role="button"
              tabIndex={0}
              onClick={() => navigate(`/contractors/${p.data!.ctrId}/edit`, { state: { fromList: true } })}
              title="Редактировать"
              style={{ cursor: 'pointer', fontSize: 16 }}
            />
          ) : null,
      },
      {
        headerName: '',
        width: 44,
        cellRenderer: (p: { data?: ContractorRowDto }) =>
          p.data && isAdmin && !p.data.occupied ? (
            <Popconfirm
              title="Удалить контрагента?"
              description="Контрагент будет удалён безвозвратно."
              onConfirm={() => handleDelete(p.data!)}
              okText="Удалить"
              cancelText="Отмена"
            >
              <DeleteOutlined
                role="button"
                tabIndex={0}
                title="Удалить"
                style={{ cursor: 'pointer', fontSize: 16, color: 'var(--ant-color-error)' }}
              />
            </Popconfirm>
          ) : null,
      },
    ],
    [isAdmin, handleBlockToggle, handleDelete, navigate]
  );

  const hasNextPage = gridData?.hasNextPage ?? false;
  const hasPrevPage = currentPage > 1;

  if (lookupsLoading) {
    return (
      <Layout.Content className="app-content" style={{ padding: 24 }}>
        <Typography.Text>Загрузка...</Typography.Text>
      </Layout.Content>
    );
  }

  return (
    <Layout.Content className="app-content" style={{ padding: 24, minHeight: 'calc(100vh - 64px)' }}>
      <div style={{ marginBottom: 24 }}>
        <Typography.Title level={2} style={{ margin: 0 }}>
          Контрагенты
        </Typography.Title>
      </div>

      {/* Блок фильтров — по макету Figma (CounterpartiesPage) */}
      <Card bordered={false} style={{ marginBottom: 24, borderRadius: 8 }}>
        <Form layout="vertical" name="filter_form">
          <Row gutter={[16, 16]}>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="Наименование"
                  value={ctrName}
                  onChange={(e) => setCtrName(e.target.value)}
                  maxLength={200}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="Полное наименование"
                  value={ctrFullName}
                  onChange={(e) => setCtrFullName(e.target.value)}
                  maxLength={300}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="Расчётный счёт"
                  value={ctrAccount}
                  onChange={(e) => setCtrAccount(e.target.value)}
                  maxLength={35}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="Адрес"
                  value={ctrAddress}
                  onChange={(e) => setCtrAddress(e.target.value)}
                  maxLength={200}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="Email"
                  value={ctrEmail}
                  onChange={(e) => setCtrEmail(e.target.value)}
                  maxLength={40}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={8} lg={6} xl={4}>
              <Form.Item style={{ margin: 0 }}>
                <Input
                  placeholder="УНП"
                  value={ctrUnp}
                  onChange={(e) => setCtrUnp(e.target.value)}
                  maxLength={15}
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

      {/* Таблица — Card с гридом по макету Figma */}
      <Card bordered={false} bodyStyle={{ padding: 0 }} style={{ borderRadius: 8, overflow: 'hidden', marginBottom: 24 }}>
        <div style={{ overflowX: 'auto' }}>
          <AgGridShell style={{ width: '100%', height: 520 }}>
            <AgGridReact<ContractorRowDto>
              rowData={gridData?.items ?? []}
              columnDefs={columnDefs}
              loading={gridLoading}
              domLayout="normal"
              defaultColDef={{ sortable: false }}
              suppressCellFocus
            />
          </AgGridShell>
        </div>
        <div style={{ padding: '12px 16px', borderTop: '1px solid var(--ant-color-border)' }}>
          <Space wrap align="center">
            <Button size="small" disabled={!hasPrevPage || gridLoading} onClick={() => fetchPage('prev')}>
              Назад
            </Button>
            <Typography.Text>Стр. {currentPage}</Typography.Text>
            <Button size="small" disabled={!hasNextPage || gridLoading} onClick={() => fetchPage('next')}>
              Вперёд
            </Button>
            <Typography.Text type="secondary">Показывать:</Typography.Text>
            <Select
              value={pageSize}
              onChange={(v) => {
                setPageSize(v);
                setCurrentPage(1);
                fetchData({ ...getFilterState(), page: 1, pageSize: v });
              }}
              options={PAGE_SIZE_OPTIONS.map((n) => ({ value: n, label: String(n) }))}
              style={{ width: 72 }}
            />
          </Space>
        </div>
      </Card>

      <div style={{ marginTop: 0 }}>
        {canCreate && (
          <Button
            type="primary"
            icon={<PlusOutlined />}
            size="large"
            onClick={() => navigate('/contractors/new?returnTo=contractors')}
          >
            Создать
          </Button>
        )}
      </div>
    </Layout.Content>
  );
}
