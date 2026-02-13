/**
 * Contractor edit. CONTRACTS: docs/screens/contractor_edit.
 * Entry: /contractors/:id/edit, ?returnTo=contractors|contract, ?tab=...
 */
import { Badge, Button, Form, Input, Layout, Popconfirm, Select, Space, Table, Tabs, Typography, Alert } from 'antd';
import { DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { useCallback, useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { ScreenLoader } from '../../shared/ui/ScreenLoader';
import { showError, showLoading, hideLoading, notifySuccess, notifyError, setFlashSuccess } from '../../shared/lib/feedback';
import { fetchWithErrorHandling } from '../../shared/lib/api';
import { ContactPersonsModal, type ContactPersonRow } from './ContactPersonsModal';

const API_BASE = (import.meta as { env?: { VITE_API_BASE_URL?: string } }).env?.VITE_API_BASE_URL ?? '';

const TAB_ORDER = ['mainPanel', 'usersContractor', 'accountsContractor', 'contactPersonsContractor', 'commentContractor'] as const;
const FIELD_TO_TAB: Record<string, string> = {
  ctrName: 'mainPanel', ctrFullName: 'mainPanel', country: 'mainPanel', ctrIndex: 'mainPanel',
  ctrRegion: 'mainPanel', ctrPlace: 'mainPanel', ctrStreet: 'mainPanel', ctrBuilding: 'mainPanel',
  ctrAddInfo: 'mainPanel', ctrPhone: 'mainPanel', ctrFax: 'mainPanel', ctrEmail: 'mainPanel',
  ctrUnp: 'mainPanel', ctrOkpo: 'mainPanel', reputation: 'mainPanel',
  ctrBankProps: 'accountsContractor',
  ctrComment: 'commentContractor',
};

interface UserRow { number?: string; user: { usrId: string; userFullName: string } }
interface AccountRow { number?: string; accName: string; accAccount: string; currency: { id: string; name: string } | null }

interface EditOpenResponse {
  ctrId: string;
  isNewDoc: boolean;
  ctrName: string;
  ctrFullName: string;
  country: { id: string; name: string } | null;
  ctrIndex: string;
  ctrRegion: string;
  ctrPlace: string;
  ctrStreet: string;
  ctrBuilding: string;
  ctrAddInfo: string;
  ctrAddress?: string;
  ctrPhone: string;
  ctrFax: string;
  ctrEmail: string;
  ctrUnp: string;
  ctrOkpo: string;
  ctrBankProps: string;
  ctrComment: string;
  reputation: { id: string; name?: string; description?: string } | null;
  ctrBlock: string;
  formReadOnly: boolean;
  usrDateCreate?: string;
  usrDateEdit?: string;
  createUser?: { usrId: string; userFullName: string };
  editUser?: { usrId: string; userFullName: string };
  gridUsers: UserRow[];
  gridAccounts: AccountRow[];
  gridContactPersons: (ContactPersonRow & { number?: string; cpsId?: string })[];
  activeTab: string;
  returnTo: string;
  lookups: {
    countries: { id: string; name: string }[];
    reputations: { id: string; name?: string; description?: string }[];
    users: { id: string; name?: string; userFullName?: string }[];
    currencies: { id: string; name: string }[];
  };
  roleFlags?: {
    adminRole: boolean;
    readOnlyReputation: boolean;
    readOnlyComment: boolean;
    canDelete: boolean;
    occupied: boolean;
  };
}

interface EditSaveResponse {
  ctrId: string;
  returnTo: string;
  redirectTo: string;
}

export default function ContractorEditPage() {
  const { id: ctrId } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [openData, setOpenData] = useState<EditOpenResponse | null>(null);
  const [notFound, setNotFound] = useState(false);
  const [gridUsers, setGridUsers] = useState<UserRow[]>([]);
  const [gridAccounts, setGridAccounts] = useState<AccountRow[]>([]);
  const [gridContactPersons, setGridContactPersons] = useState<(ContactPersonRow & { cpsId?: string })[]>([]);
  const [contactModalOpen, setContactModalOpen] = useState(false);
  const [contactEditIndex, setContactEditIndex] = useState<number | null>(null);
  const [activeTab, setActiveTab] = useState<string>('mainPanel');
  const [tabErrorKeys, setTabErrorKeys] = useState<Set<string>>(new Set());

  const returnTo = searchParams.get('returnTo') ?? 'contractors';
  const tabParam = searchParams.get('tab');

  useEffect(() => {
    if (!ctrId) {
      setNotFound(true);
      setLoading(false);
      return;
    }
    const startedAt = performance.now();
    const minLoaderMs = import.meta.env.DEV ? 350 : 0;
    const done = () => {
      const elapsed = performance.now() - startedAt;
      setTimeout(() => setLoading(false), Math.max(0, minLoaderMs - elapsed));
    };
    const q = new URLSearchParams();
    if (returnTo) q.set('returnTo', returnTo);
    if (tabParam) q.set('tab', tabParam);
    fetch(`${API_BASE}/api/contractors/${ctrId}/edit/open?${q.toString()}`)
      .then((r) => {
        if (r.status === 404) {
          setNotFound(true);
          return null;
        }
        return r.json();
      })
      .then((data: EditOpenResponse | null) => {
        if (!data) {
          done();
          return;
        }
        setOpenData(data);
        setGridUsers(data.gridUsers ?? []);
        setGridAccounts(data.gridAccounts ?? []);
        setGridContactPersons((data.gridContactPersons ?? []).map((cp) => ({
          cpsName: cp.cpsName ?? '',
          cpsPosition: cp.cpsPosition ?? '',
          cpsOnReason: cp.cpsOnReason ?? '',
          cpsPhone: cp.cpsPhone ?? '',
          cpsMobPhone: cp.cpsMobPhone ?? '',
          cpsFax: cp.cpsFax ?? '',
          cpsEmail: cp.cpsEmail ?? '',
          cpsContractComment: cp.cpsContractComment ?? '',
          cpsFire: cp.cpsFire ?? '0',
          cpsBlock: cp.cpsBlock ?? '0',
          cpsId: cp.cpsId,
        })));
        setActiveTab(data.activeTab ?? 'mainPanel');
        form.setFieldsValue({
          ctrName: data.ctrName ?? '',
          ctrFullName: data.ctrFullName ?? '',
          country: data.country?.id,
          ctrIndex: data.ctrIndex ?? '',
          ctrRegion: data.ctrRegion ?? '',
          ctrPlace: data.ctrPlace ?? '',
          ctrStreet: data.ctrStreet ?? '',
          ctrBuilding: data.ctrBuilding ?? '',
          ctrAddInfo: data.ctrAddInfo ?? '',
          ctrPhone: data.ctrPhone ?? '',
          ctrFax: data.ctrFax ?? '',
          ctrEmail: data.ctrEmail ?? '',
          ctrUnp: data.ctrUnp ?? '',
          ctrOkpo: data.ctrOkpo ?? '',
          reputation: data.reputation?.id ?? (data.reputation as { description?: string })?.description,
          ctrBankProps: data.ctrBankProps ?? '',
          ctrComment: data.ctrComment ?? '',
        });
      })
      .catch(() => {
        showError('Ошибка загрузки');
        setNotFound(true);
      })
      .finally(done);
  }, [ctrId, returnTo, tabParam, form]);

  const validateAccounts = useCallback((): string | null => {
    for (let i = 0; i < gridAccounts.length; i++) {
      const r = gridAccounts[i];
      if ((r.accAccount && r.accAccount.trim()) && !r.currency?.id) return 'accountsContractor';
      if (i >= 3) {
        if (!(r.accAccount && r.accAccount.trim()) || !r.currency?.id) return 'accountsContractor';
      }
      if (r.accAccount && r.accAccount.length > 35) return 'accountsContractor';
    }
    return null;
  }, [gridAccounts]);

  const handleFinish = useCallback(
    async (values: Record<string, unknown>) => {
      if (!ctrId || !openData) return;
      setSaving(true);
      const ctrUnpRaw = values.ctrUnp as string | undefined;
      const ctrUnp = (ctrUnpRaw != null && String(ctrUnpRaw).trim() !== '') ? String(ctrUnpRaw).trim() : null;
      const body = {
        ctrId: openData.ctrId,
        ctrName: values.ctrName ?? '',
        ctrFullName: values.ctrFullName ?? '',
        country: openData.lookups && values.country ? { id: values.country } : null,
        ctrIndex: values.ctrIndex ?? '',
        ctrRegion: values.ctrRegion ?? '',
        ctrPlace: values.ctrPlace ?? '',
        ctrStreet: values.ctrStreet ?? '',
        ctrBuilding: values.ctrBuilding ?? '',
        ctrAddInfo: values.ctrAddInfo ?? '',
        ctrPhone: values.ctrPhone ?? '',
        ctrFax: values.ctrFax ?? '',
        ctrEmail: values.ctrEmail ?? '',
        ctrUnp: ctrUnp,
        ctrOkpo: values.ctrOkpo ?? '',
        ctrBankProps: values.ctrBankProps ?? '',
        ctrComment: values.ctrComment ?? '',
        reputation: openData.lookups && values.reputation ? { id: values.reputation } : null,
        gridUsers,
        gridAccounts: gridAccounts.map((a) => ({
          number: a.number ?? '',
          accName: a.accName ?? '',
          accAccount: a.accAccount ?? '',
          currency: a.currency?.id ? { id: a.currency.id } : null,
        })),
        gridContactPersons: gridContactPersons.map((cp) => ({
          cpsId: cp.cpsId ?? undefined,
          cpsName: cp.cpsName ?? '',
          cpsPosition: cp.cpsPosition ?? '',
          cpsOnReason: cp.cpsOnReason ?? '',
          cpsPhone: cp.cpsPhone ?? '',
          cpsMobPhone: cp.cpsMobPhone ?? '',
          cpsFax: cp.cpsFax ?? '',
          cpsEmail: cp.cpsEmail ?? '',
          cpsContractComment: cp.cpsContractComment ?? '',
          cpsFire: cp.cpsFire ?? '0',
          cpsBlock: cp.cpsBlock ?? '0',
        })),
        returnTo: openData.returnTo ?? returnTo,
      };
      showLoading('Сохранение контрагента...');
      const result = await fetchWithErrorHandling<EditSaveResponse>(`${API_BASE}/api/contractors/${ctrId}/edit/save`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json; charset=UTF-8' },
        body: JSON.stringify(body),
      });
      hideLoading();
      setSaving(false);
      if (!result.ok) {
        if (result.error?.activeTab) setActiveTab(result.error.activeTab);
        notifyError('Ошибка сохранения', result.error?.message ?? 'Не удалось сохранить контрагента');
        return;
      }
      const ctrName = String(values?.ctrName ?? form.getFieldValue?.('ctrName') ?? '').trim() || '';
      setFlashSuccess('Контрагент сохранён', ctrName || undefined);
      navigate(result.data!.redirectTo || (returnTo === 'contract' ? '/contracts/new' : '/contractors'));
    },
    [ctrId, navigate, gridUsers, gridAccounts, gridContactPersons, returnTo, openData, form]
  );

  const handleSubmit = useCallback(
    async (e?: React.FormEvent) => {
      e?.preventDefault();
      if (openData?.formReadOnly) return;
      setTabErrorKeys(new Set());
      try {
        await form.validateFields();
      } catch (err: unknown) {
        const errInfo = err as { errorFields?: Array<{ name: (string | number)[] }> };
        const fields = errInfo?.errorFields ?? [];
        const tabs = new Set<string>();
        for (const f of fields) {
          const name = Array.isArray(f.name) ? String(f.name[0]) : '';
          const tab = FIELD_TO_TAB[name];
          if (tab) tabs.add(tab);
        }
        const firstTab = TAB_ORDER.find((t) => tabs.has(t)) ?? 'mainPanel';
        setTabErrorKeys(tabs);
        setActiveTab(firstTab);
        notifyError('Заполните обязательные поля');
        return;
      }
      const accountErrTab = validateAccounts();
      if (accountErrTab) {
        setTabErrorKeys(new Set([accountErrTab]));
        setActiveTab(accountErrTab);
        notifyError('Заполните обязательные поля');
        return;
      }
      handleFinish(form.getFieldsValue());
    },
    [form, validateAccounts, handleFinish, openData?.formReadOnly]
  );

  const addUserRow = () => {
    const u = openData?.lookups?.users?.[0];
    if (u) setGridUsers((prev) => [...prev, { user: { usrId: u.id, userFullName: (u as { name?: string }).name ?? (u as { userFullName?: string }).userFullName ?? '' } }]);
  };
  const removeUserRow = (idx: number) => setGridUsers((prev) => prev.filter((_, i) => i !== idx));
  const addAccountRow = () => setGridAccounts((prev) => [...prev, { accName: 'Счёт', accAccount: '', currency: null }]);
  const removeAccountRow = (idx: number) => {
    if (gridAccounts.length <= 3) return;
    setGridAccounts((prev) => prev.filter((_, i) => i !== idx));
  };
  const setAccountField = (idx: number, field: keyof AccountRow, value: unknown) => {
    setGridAccounts((prev) => {
      const next = [...prev];
      const row = { ...next[idx] };
      if (field === 'currency') row.currency = value as AccountRow['currency'];
      else if (field === 'accAccount') row.accAccount = String(value ?? '');
      else if (field === 'accName') row.accName = String(value ?? '');
      next[idx] = row;
      return next;
    });
  };
  const openContactModal = (mode: 'create' | 'edit', index?: number) => {
    setContactEditIndex(mode === 'edit' && index !== undefined ? index : null);
    setContactModalOpen(true);
  };
  const handleContactSave = (row: ContactPersonRow, editIndex: number | null) => {
    if (editIndex !== null) {
      setGridContactPersons((prev) => {
        const next = [...prev];
        const existing = next[editIndex];
        next[editIndex] = { ...row, cpsId: existing?.cpsId };
        return next;
      });
      notifySuccess('Контактное лицо изменено');
    } else {
      setGridContactPersons((prev) => [...prev, row]);
      notifySuccess('Контактное лицо добавлено');
    }
    setContactModalOpen(false);
    setContactEditIndex(null);
  };
  const removeContactPersonRow = (idx: number) => setGridContactPersons((prev) => prev.filter((_, i) => i !== idx));

  const formReadOnly = openData?.formReadOnly ?? false;
  const adminRole = openData?.roleFlags?.adminRole ?? false;
  const readOnlyReputation = openData?.roleFlags?.readOnlyReputation ?? false;
  const readOnlyComment = openData?.roleFlags?.readOnlyComment ?? false;

  if (notFound) {
    return (
      <Layout style={{ padding: 16 }}>
        <Alert type="error" message="Контрагент не найден" description="Возможно, запись была удалена." style={{ marginBottom: 16 }} />
        <Button type="primary" onClick={() => navigate('/contractors')}>К списку контрагентов</Button>
      </Layout>
    );
  }

  return (
    <Layout style={{ padding: 16 }}>
      <Typography.Title level={4}>Редактирование контрагента</Typography.Title>
      {openData && !openData.isNewDoc && (openData.usrDateCreate != null || openData.usrDateEdit != null) && (
        <Typography.Paragraph type="secondary" style={{ marginBottom: 16 }}>
          {openData.usrDateCreate != null && openData.createUser != null && `Создано: ${openData.usrDateCreate} ${openData.createUser.userFullName}`}
          {openData.usrDateCreate != null && openData.usrDateEdit != null && ' · '}
          {openData.usrDateEdit != null && openData.editUser != null && `Изменено: ${openData.usrDateEdit} ${openData.editUser.userFullName}`}
        </Typography.Paragraph>
      )}
      <Form form={form} layout="horizontal" labelCol={{ span: 6 }} wrapperCol={{ span: 18 }} style={{ maxWidth: 720 }} disabled={formReadOnly}>
        {(loading || !openData) ? (
          <ScreenLoader loading={true} rows={10} variant="spin">
            <div />
          </ScreenLoader>
        ) : (
          <>
            <Tabs
              activeKey={activeTab}
              onChange={setActiveTab}
              items={[
                {
                  key: 'mainPanel',
                  label: <Badge dot={tabErrorKeys.has('mainPanel')}>Главная</Badge>,
                  children: (
                    <>
                      <Form.Item label="Наименование" name="ctrName" rules={[{ required: true, message: 'Введите наименование' }]}>
                        <Input style={{ width: 400 }} maxLength={200} />
                      </Form.Item>
                      <Form.Item label="Полное наименование" name="ctrFullName" rules={[{ required: true, message: 'Введите полное наименование' }]}>
                        <Input style={{ width: 400 }} maxLength={300} />
                      </Form.Item>
                      <Form.Item label="Страна" name="country" rules={[{ required: true, message: 'Выберите страну' }]}>
                        <Select allowClear placeholder="Страна" options={openData.lookups.countries.map((c) => ({ value: c.id, label: c.name }))} style={{ width: 280 }} />
                      </Form.Item>
                      <Form.Item label="Индекс" name="ctrIndex">
                        <Input style={{ width: 120 }} maxLength={20} />
                      </Form.Item>
                      <Form.Item label="Область" name="ctrRegion">
                        <Input style={{ width: 200 }} maxLength={50} />
                      </Form.Item>
                      <Form.Item label="Населённый пункт" name="ctrPlace">
                        <Input style={{ width: 200 }} maxLength={50} />
                      </Form.Item>
                      <Form.Item label="Улица" name="ctrStreet">
                        <Input style={{ width: 200 }} maxLength={50} />
                      </Form.Item>
                      <Form.Item label="Дом" name="ctrBuilding">
                        <Input style={{ width: 100 }} maxLength={20} />
                      </Form.Item>
                      <Form.Item label="Доп. информация" name="ctrAddInfo">
                        <Input style={{ width: 400 }} maxLength={1000} />
                      </Form.Item>
                      <Form.Item label="Телефон" name="ctrPhone">
                        <Input style={{ width: 300 }} maxLength={100} />
                      </Form.Item>
                      <Form.Item label="Факс" name="ctrFax">
                        <Input style={{ width: 300 }} maxLength={100} />
                      </Form.Item>
                      <Form.Item label="Email" name="ctrEmail">
                        <Input style={{ width: 300 }} maxLength={40} />
                      </Form.Item>
                      <Form.Item label="УНП" name="ctrUnp">
                        <Input style={{ width: 200 }} maxLength={15} />
                      </Form.Item>
                      <Form.Item label="ОКПО" name="ctrOkpo">
                        <Input style={{ width: 200 }} maxLength={15} />
                      </Form.Item>
                      <Form.Item label="Репутация" name="reputation" rules={[{ required: true, message: 'Выберите репутацию' }]}>
                        <Select allowClear placeholder="Репутация" options={openData.lookups.reputations.map((r) => ({ value: r.id, label: (r as { name?: string }).name ?? (r as { description?: string }).description }))} style={{ width: 280 }} disabled={readOnlyReputation} />
                      </Form.Item>
                    </>
                  ),
                },
                {
                  key: 'usersContractor',
                  label: <Badge dot={tabErrorKeys.has('usersContractor')}>Курируют</Badge>,
                  children: (
                    <>
                      <Table
                        dataSource={gridUsers.map((u, i) => ({ ...u, key: i }))}
                        columns={[
                          { title: 'Пользователь', dataIndex: ['user', 'userFullName'], key: 'userFullName', width: 200 },
                          { title: '', key: 'action', width: 80, render: (_, __, idx) => <Button type="link" danger size="small" disabled={formReadOnly} onClick={() => removeUserRow(idx)}>Удалить</Button> },
                        ]}
                        pagination={false}
                        size="small"
                      />
                      <Button type="dashed" onClick={addUserRow} style={{ marginTop: 8 }} disabled={formReadOnly}>Добавить</Button>
                    </>
                  ),
                },
                {
                  key: 'accountsContractor',
                  label: <Badge dot={tabErrorKeys.has('accountsContractor')}>Расчетные счета и банковские реквизиты</Badge>,
                  children: (
                    <>
                      <Form.Item label="Банковские реквизиты" name="ctrBankProps">
                        <Input.TextArea rows={3} style={{ width: 450 }} maxLength={800} />
                      </Form.Item>
                      <Table
                        dataSource={gridAccounts.map((a, i) => ({ ...a, key: i }))}
                        columns={[
                          { title: 'Наименование', dataIndex: 'accName', key: 'accName', width: 150, render: (v, __, i) => <Input value={v} onChange={(e) => setAccountField(i, 'accName', e.target.value)} size="small" maxLength={50} disabled={formReadOnly} /> },
                          { title: 'Счёт', dataIndex: 'accAccount', key: 'accAccount', width: 220, render: (v, __, i) => <Input value={v} onChange={(e) => setAccountField(i, 'accAccount', e.target.value)} size="small" maxLength={35} disabled={formReadOnly} /> },
                          { title: 'Валюта', key: 'currency', width: 130, render: (_, r, i) => <Select allowClear placeholder="Валюта" value={r.currency?.id} onChange={(id) => setAccountField(i, 'currency', id ? { id, name: openData.lookups.currencies.find((c) => c.id === id)?.name ?? '' } : null)} options={openData.lookups.currencies.map((c) => ({ value: c.id, label: c.name }))} style={{ width: 100 }} size="small" disabled={formReadOnly} /> },
                          { title: '', key: 'action', width: 80, render: (_, __, idx) => <Button type="link" danger size="small" disabled={formReadOnly || gridAccounts.length <= 3} onClick={() => removeAccountRow(idx)}>Удалить</Button> },
                        ]}
                        pagination={false}
                        size="small"
                      />
                      <Button type="dashed" onClick={addAccountRow} style={{ marginTop: 8 }} disabled={formReadOnly}>Добавить</Button>
                    </>
                  ),
                },
                {
                  key: 'contactPersonsContractor',
                  label: <Badge dot={tabErrorKeys.has('contactPersonsContractor')}>Контактные лица</Badge>,
                  children: (
                    <>
                      <Table
                        dataSource={gridContactPersons.map((cp, i) => ({ ...cp, key: i }))}
                        rowKey="key"
                        scroll={{ x: 1300 }}
                        columns={[
                          { title: 'ФИО', dataIndex: 'cpsName', key: 'cpsName', width: 180, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Должность', dataIndex: 'cpsPosition', key: 'cpsPosition', width: 100, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Основание', dataIndex: 'cpsOnReason', key: 'cpsOnReason', width: 100, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Телефон', dataIndex: 'cpsPhone', key: 'cpsPhone', width: 120, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Моб.', dataIndex: 'cpsMobPhone', key: 'cpsMobPhone', width: 120, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Факс', dataIndex: 'cpsFax', key: 'cpsFax', width: 100, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Email', dataIndex: 'cpsEmail', key: 'cpsEmail', width: 140, ellipsis: true, render: (v) => v ? <a href={`mailto:${v}`}>{v}</a> : '—' },
                          { title: 'Комментарий', dataIndex: 'cpsContractComment', key: 'cpsContractComment', width: 200, ellipsis: true, render: (v) => v || '—' },
                          { title: 'Уволен', key: 'cpsFire', width: 70, render: (_, r) => (r.cpsFire === '1' ? 'Да' : '—') },
                          {
                            title: adminRole ? (
                              <Badge status="error" text="Блок" />
                            ) : (
                              'Блок'
                            ),
                            key: 'cpsBlock',
                            width: 70,
                            render: (_, r) => (r.cpsBlock === '1' ? 'Да' : '—'),
                          },
                          {
                            title: '',
                            key: 'actions',
                            width: 90,
                            fixed: 'end',
                            render: (_, __, idx) => (
                              <Space size="small">
                                <EditOutlined role="button" title="Редактировать" style={{ cursor: formReadOnly ? 'not-allowed' : 'pointer', fontSize: 16 }} onClick={() => !formReadOnly && openContactModal('edit', idx)} />
                                <Popconfirm title="Удалить контактное лицо?" onConfirm={() => removeContactPersonRow(idx)} okText="Удалить" cancelText="Отмена" disabled={formReadOnly}>
                                  <DeleteOutlined role="button" title="Удалить" style={{ cursor: formReadOnly ? 'not-allowed' : 'pointer', fontSize: 16, color: 'var(--ant-color-error)' }} />
                                </Popconfirm>
                              </Space>
                            ),
                          },
                        ]}
                        pagination={false}
                        size="small"
                      />
                      <Button type="dashed" onClick={() => openContactModal('create')} style={{ marginTop: 8 }} disabled={formReadOnly}>Добавить</Button>
                      <ContactPersonsModal open={contactModalOpen} editIndex={contactEditIndex} initialRow={contactEditIndex !== null ? gridContactPersons[contactEditIndex] ?? null : null} onSave={handleContactSave} onCancel={() => { setContactModalOpen(false); setContactEditIndex(null); }} adminRole={adminRole} />
                    </>
                  ),
                },
                {
                  key: 'commentContractor',
                  label: <Badge dot={tabErrorKeys.has('commentContractor')}>Комментарии</Badge>,
                  children: (
                    <Form.Item label="Комментарий" name="ctrComment">
                      <Input.TextArea rows={8} style={{ width: 500 }} maxLength={5000} disabled={readOnlyComment} />
                    </Form.Item>
                  ),
                },
              ]}
            />
            <Form.Item wrapperCol={{ offset: 6, span: 18 }} style={{ marginTop: 16, marginBottom: 0, position: 'sticky', bottom: 0, background: 'var(--ant-color-bg-container)', paddingTop: 16, borderTop: '1px solid var(--ant-color-border)', zIndex: 1 }}>
              <Space>
                <Button type="primary" htmlType="button" onClick={handleSubmit} loading={saving} disabled={formReadOnly}>Сохранить</Button>
                <Button htmlType="button" onClick={() => navigate(returnTo === 'contract' ? '/contracts/new' : '/contractors')}>Отмена</Button>
              </Space>
            </Form.Item>
          </>
        )}
      </Form>
    </Layout>
  );
}
