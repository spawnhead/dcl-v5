/**
 * N3a Contract create. 1:1 per docs/screens/contract_create (SNAPSHOT, CONTRACTS, ACCEPTANCE).
 * Legacy: ContractAction.do?dispatch=input, process.
 * N3a1: Добавить у contractor → /contractors/new?returnTo=contract.
 * N3a2: Спецификации table + «Создать спецификацию» → /contracts/draft/specifications/new.
 * N3a3: Прикреплённые файлы + «Прикрепить» → /contracts/draft/attachments.
 * TASK-0025: Global UX feedback — ScreenLoader, Message.success/error for save.
 */
import { Button, Checkbox, DatePicker, Form, Input, Select, Space, Typography, Card, Row, Col, Flex, Breadcrumb } from 'antd';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useSearchParams, useLocation } from 'react-router-dom';
import { ScreenLoader } from '../../shared/ui/ScreenLoader';
import { showError, showLoading, hideLoading, notifySuccess, notifyError } from '../../shared/lib/feedback';
import { fetchWithErrorHandling } from '../../shared/lib/api';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';

dayjs.extend(customParseFormat);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const DATE_FORMAT = 'DD.MM.YYYY';

interface LookupItem {
  id: string;
  name: string;
}

interface OpenResponse {
  defaults: {
    conNumber: string;
    conDate: string;
    conReusable: boolean;
    conFinalDate: string;
    contractor: LookupItem | null;
    currency: LookupItem | null;
    conFaxCopy: boolean;
    conOriginal: boolean;
    seller: LookupItem | null;
    conAnnul: boolean;
    conAnnulDate: string;
    conComment: string;
  };
  lookups: {
    contractors: LookupItem[];
    currencies: LookupItem[];
    sellers: LookupItem[];
  };
  canCreate: boolean;
}

interface SaveResponse {
  conId: string;
  redirectTo: string;
}

interface ValidationError {
  name: string;
  message: string;
}

interface ValidationErrorResponse {
  error: { code: string; fields: ValidationError[] };
}

/** One row in specifications grid. N3a2 CONTRACTS. */
interface SpecRow {
  key: string;
  spcNumber: string;
  spcDate: string;
  spcSummFormatted: string;
  spcNdsRateFormatted?: string;
  spcSummNdsFormatted?: string;
  spcRemainder?: string;
  spcExecuted?: string;
}

/** One item in attachments list. N3a3 CONTRACTS. */
interface AttachmentItem {
  id: string;
  idx: string;
  originalFileName: string;
  attCreateDate?: string;
}

export default function ContractCreatePage() {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();
  const location = useLocation();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [openData, setOpenData] = useState<OpenResponse | null>(null);
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});
  const [specifications, setSpecifications] = useState<SpecRow[]>([]);
  const [attachments, setAttachments] = useState<AttachmentItem[]>([]);

  const loadOpen = useCallback(() => {
    const newContractorId = searchParams.get('newContractorId');
    const url = newContractorId
      ? `${API_BASE}/api/contracts/create/open?newContractorId=${encodeURIComponent(newContractorId)}`
      : `${API_BASE}/api/contracts/create/open`;
    return fetch(url)
      .then((r) => r.json())
      .then((data: OpenResponse) => {
        setOpenData(data);
        if (!data.canCreate) {
          showError('Нет прав на создание договора');
          navigate('/contracts');
          return;
        }
        const newContractorId = searchParams.get('newContractorId');
        form.setFieldsValue({
          conNumber: data.defaults.conNumber,
          conDate: data.defaults.conDate ? dayjs(data.defaults.conDate, DATE_FORMAT) : null,
          conReusable: data.defaults.conReusable,
          conFinalDate: data.defaults.conFinalDate ? dayjs(data.defaults.conFinalDate, DATE_FORMAT) : null,
          contractor: newContractorId ?? data.defaults.contractor?.id,
          currency: data.defaults.currency?.id,
          conFaxCopy: data.defaults.conFaxCopy,
          conOriginal: data.defaults.conOriginal,
          seller: data.defaults.seller?.id,
          conAnnul: data.defaults.conAnnul,
          conAnnulDate: data.defaults.conAnnulDate ? dayjs(data.defaults.conAnnulDate, DATE_FORMAT) : null,
          conComment: data.defaults.conComment,
        });
        // Do NOT clear newContractorId here: setSearchParams({}) triggers loadOpen refetch
        // with stale params, which overwrites contractor with null.
      });
  }, [form, navigate, searchParams]);

  useEffect(() => {
    const startedAt = performance.now();
    const minLoaderMs = import.meta.env.DEV ? 350 : 0;
    loadOpen()
      .catch(() => showError('Ошибка загрузки формы'))
      .finally(() => {
        const elapsed = performance.now() - startedAt;
        const delay = Math.max(0, minLoaderMs - elapsed);
        setTimeout(() => setLoading(false), delay);
      });
  }, [loadOpen]);

  useEffect(() => {
    const added = (location.state as { addedSpecification?: SpecRow })?.addedSpecification;
    if (added) {
      setSpecifications((prev) => [...prev, { ...added, key: added.spcNumber + String(prev.length) }]);
      window.history.replaceState({}, '', location.pathname);
    }
  }, [location.state, location.pathname]);

  useEffect(() => {
    fetch(`${API_BASE}/api/contracts/draft/attachments`)
      .then((r) => r.json())
      .then((data: { items?: AttachmentItem[] }) => setAttachments(data.items ?? []))
      .catch(() => {});
  }, []);

  const handleCancel = useCallback(() => {
    navigate('/contracts');
  }, [navigate]);

  const conFaxCopyOnChange = useCallback(() => {
    form.setFieldValue('conOriginal', false);
  }, [form]);
  const conOriginalOnChange = useCallback(() => {
    form.setFieldValue('conFaxCopy', false);
  }, [form]);

  const handleFinish = useCallback(
    async (values: Record<string, unknown>) => {
      setFieldErrors({});
      setSaving(true);
      showLoading('Сохранение договора...');
      const contractorId = values.contractor as string;
      const currencyId = values.currency as string;
      const sellerId = values.seller as string;
      const contractors = openData?.lookups.contractors ?? [];
      const currencies = openData?.lookups.currencies ?? [];
      const sellers = openData?.lookups.sellers ?? [];
      const body = {
        conNumber: values.conNumber ?? '',
        conDate: values.conDate ? dayjs(values.conDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
        conReusable: !!values.conReusable,
        conFinalDate: values.conFinalDate ? dayjs(values.conFinalDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
        contractor: contractorId ? { id: contractorId, name: contractors.find((c) => c.id === contractorId)?.name ?? '' } : null,
        currency: currencyId ? { id: currencyId, name: currencies.find((c) => c.id === currencyId)?.name ?? '' } : null,
        conFaxCopy: !!values.conFaxCopy,
        conOriginal: !!values.conOriginal,
        seller: sellerId ? { id: sellerId, name: sellers.find((s) => s.id === sellerId)?.name ?? '' } : null,
        conAnnul: !!values.conAnnul,
        conAnnulDate: values.conAnnulDate ? dayjs(values.conAnnulDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
        conComment: (values.conComment as string) ?? '',
        specifications: specifications.map((s) => ({ spcNumber: s.spcNumber, spcDate: s.spcDate, spcSummFormatted: s.spcSummFormatted })),
      };
      const result = await fetchWithErrorHandling<SaveResponse>(`${API_BASE}/api/contracts/create/save`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });
      hideLoading();
      setSaving(false);
      if (!result.ok) {
        const err = result.error!;
        if (err.fields?.length) {
          const map: Record<string, string> = {};
          err.fields.forEach((f) => {
            map[f.name] = f.message;
          });
          setFieldErrors(map);
        }
        notifyError('Ошибка сохранения', err.message);
        return;
      }
      notifySuccess('Договор сохранён');
      navigate(result.data!.redirectTo || '/contracts');
    },
    [openData, navigate, specifications]
  );

  if (loading || !openData) {
    return (
      <ScreenLoader loading={true} rows={10}>
        <div />
      </ScreenLoader>
    );
  }

  return (
    <div style={{ padding: 16 }}>
      <Typography.Title level={4}>Создание договора</Typography.Title>
      <Form
        form={form}
        layout="vertical"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 18 }}
        colon={false}
        requiredMark="optional"
        onFinish={handleFinish} size="large"
        style={{ maxWidth: 720 }}
      >
        <Divider orientation="left" plain>Основные поля</Divider>
        <Form.Item
          label="Номер"
          name="conNumber"
          rules={[{ required: true, message: 'Введите номер договора' }]}
          validateStatus={fieldErrors['conNumber'] ? 'error' : undefined}
          help={fieldErrors['conNumber']}
        >
          <Input maxLength={50} style={{ width: 230 }} placeholder="Номер договора" />
        </Form.Item>
        <Form.Item
          label="Дата"
          name="conDate"
          rules={[{ required: true, message: 'Введите дату' }]}
          validateStatus={fieldErrors['conDate'] ? 'error' : undefined}
          help={fieldErrors['conDate']}
        >
          <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
        </Form.Item>
        <Form.Item name="conReusable" valuePropName="checked">
          <Checkbox>Многоразовый</Checkbox>
        </Form.Item>
        <Form.Item
          label="Срок действия"
          name="conFinalDate"
          validateStatus={fieldErrors['conFinalDate'] ? 'error' : undefined}
          help={fieldErrors['conFinalDate']}
        >
          <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
        </Form.Item>
        <Form.Item
          label="Контрагент"
          validateStatus={fieldErrors['contractor.id'] ? 'error' : undefined}
          help={fieldErrors['contractor.id']}
        >
          <Space.Compact style={{ width: 320 }}>
            <Form.Item name="contractor" noStyle rules={[{ required: true, message: 'Выберите контрагента' }]}>
              <Select
                allowClear
                showSearch
                optionFilterProp="label"
                placeholder="Контрагент"
                options={openData.lookups.contractors.map((c) => ({ value: c.id, label: c.name }))}
                style={{ width: 240 }}
              />
            </Form.Item>
            <Button type="default" onClick={() => navigate('/contractors/new?returnTo=contract')}>
              Добавить
            </Button>
          </Space.Compact>
        </Form.Item>
        <Form.Item
          label="Валюта"
          name="currency"
          rules={[{ required: true, message: 'Выберите валюту' }]}
          validateStatus={fieldErrors['currency.id'] ? 'error' : undefined}
          help={fieldErrors['currency.id']}
        >
          <Select
            allowClear
            placeholder="Валюта"
            options={openData.lookups.currencies.map((c) => ({ value: c.id, label: c.name }))}
            style={{ width: 120 }}
          />
        </Form.Item>
        <Form.Item name="conFaxCopy" valuePropName="checked">
          <Checkbox onChange={conFaxCopyOnChange}>Факсовая копия</Checkbox>
        </Form.Item>
        <Form.Item name="conOriginal" valuePropName="checked">
          <Checkbox onChange={conOriginalOnChange}>Оригинал</Checkbox>
        </Form.Item>
        <Form.Item
          label="Продавец"
          name="seller"
          rules={[{ required: true, message: 'Выберите продавца' }]}
          validateStatus={fieldErrors['seller.id'] ? 'error' : undefined}
          help={fieldErrors['seller.id']}
        >
          <Select
            allowClear
            placeholder="Продавец"
            options={openData.lookups.sellers.map((s) => ({ value: s.id, label: s.name }))}
            style={{ width: 280 }}
          />
        </Form.Item>
        <Form.Item name="conAnnul" valuePropName="checked">
          <Checkbox>Аннулирован</Checkbox>
        </Form.Item>
        <Form.Item
          label="Дата аннулирования"
          name="conAnnulDate"
          validateStatus={fieldErrors['conAnnulDate'] ? 'error' : undefined}
          help={fieldErrors['conAnnulDate']}
        >
          <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
        </Form.Item>
        <Form.Item
          label="Примечание"
          name="conComment"
          validateStatus={fieldErrors['conComment'] ? 'error' : undefined}
          help={fieldErrors['conComment']}
        >
          <Input.TextArea rows={4} maxLength={5000} style={{ width: 600 }} placeholder="Примечание" />
        </Form.Item>
        <Divider orientation="left" plain>Спецификации</Divider>
        <div style={{ marginBottom: 16 }}>
          <Table
            dataSource={specifications}
            columns={[
              { title: 'Номер', dataIndex: 'spcNumber', key: 'spcNumber', width: 120 },
              { title: 'Дата', dataIndex: 'spcDate', key: 'spcDate', width: 100 },
              { title: 'Сумма', dataIndex: 'spcSummFormatted', key: 'spcSummFormatted', width: 100 },
              { title: 'НДС %', dataIndex: 'spcNdsRateFormatted', key: 'spcNdsRateFormatted', width: 80 },
              { title: 'Сумма с НДС', dataIndex: 'spcSummNdsFormatted', key: 'spcSummNdsFormatted', width: 100 },
              { title: 'Остаток', dataIndex: 'spcRemainder', key: 'spcRemainder', width: 80 },
              { title: 'Исполнено', dataIndex: 'spcExecuted', key: 'spcExecuted', width: 80 },
            ]}
            pagination={false}
            size="small"
            locale={{ emptyText: 'Нет строк' }}
          />
          <Button type="default" style={{ marginTop: 8 }} onClick={() => navigate('/contracts/draft/specifications/new', { state: { currencyName: openData.lookups.currencies.find((c) => c.id === form.getFieldValue('currency'))?.name ?? 'BYN' } })}>
            Создать спецификацию
          </Button>
        </div>
        <Divider orientation="left" plain>Прикреплённые файлы</Divider>
        <div style={{ marginBottom: 16 }}>
          {attachments.length === 0 ? (
            <Typography.Text type="secondary">Нет прикреплённых файлов</Typography.Text>
          ) : (
            <ul style={{ margin: 0, paddingLeft: 20 }}>
              {attachments.map((a) => (
                <li key={a.id}>{a.originalFileName}</li>
              ))}
            </ul>
          )}
          <Button type="default" style={{ marginTop: 8 }} onClick={() => navigate('/contracts/draft/attachments')}>
            Прикрепить
          </Button>
        </div>
        <Form.Item wrapperCol={{ offset: 6, span: 18 }}>
          <Space>
            <Button type="primary" htmlType="submit" loading={saving}>
              Сохранить
            </Button>
            <Button onClick={handleCancel}>Отмена</Button>
          </Space>
        </Form.Item>
      </Form>
    </div>
  );
}

