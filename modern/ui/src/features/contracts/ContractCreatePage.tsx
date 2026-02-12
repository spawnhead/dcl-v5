/**
 * N3a Contract create. 1:1 per docs/screens/contract_create (SNAPSHOT, CONTRACTS, ACCEPTANCE).
 * Legacy: ContractAction.do?dispatch=input, process.
 * N3a1: Добавить у contractor → /contractors/new?returnTo=contract.
 * N3a2: Спецификации table + «Создать спецификацию» → /contracts/draft/specifications/new.
 * N3a3: Прикреплённые файлы + «Прикрепить» → /contracts/draft/attachments.
 * TASK-0025: Global UX feedback — ScreenLoader, Message.success/error for save.
 * TASK-0060: Figma AntD layout — Cards, Row/Col 2/3+1/3, sticky sidebar actions.
 */
import { Button, Breadcrumb, Card, Checkbox, Col, DatePicker, Flex, Form, Input, Row, Select, Space, Typography } from 'antd';
import { PlusOutlined, SaveOutlined, CloseOutlined } from '@ant-design/icons';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useSearchParams, useLocation } from 'react-router-dom';
import { ScreenLoader } from '../../shared/ui/ScreenLoader';
import { showError, showLoading, hideLoading, notifySuccess, notifyError } from '../../shared/lib/feedback';
import { fetchWithErrorHandling } from '../../shared/lib/api';
import { SpecificationsTable } from './components/SpecificationsTable';
import { FileUploadSection } from './components/FileUploadSection';
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

  // Watch conAnnul to conditionally show annul date
  const conAnnulValue = Form.useWatch('conAnnul', form);

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
    <div style={{ padding: '16px 24px' }}>
      <Breadcrumb
        style={{ marginBottom: 8 }}
        items={[
          { title: 'Главная' },
          { title: 'Договора' },
          { title: 'Создание договора' },
        ]}
      />
      <Typography.Title level={4} style={{ marginBottom: 24 }}>Создание договора</Typography.Title>

      <Form
        form={form}
        layout="vertical"
        colon={false}
        requiredMark="optional"
        onFinish={handleFinish}
        size="large"
      >
        <Row gutter={24}>
          {/* ===== Left column: 2/3 ===== */}
          <Col xs={24} lg={16}>
            <Flex vertical gap="large">
              {/* --- Card: Основные поля --- */}
              <Card title="Основные поля">
                <Row gutter={16}>
                  <Col xs={24} md={12}>
                    <Form.Item
                      label="Номер"
                      name="conNumber"
                      rules={[{ required: true, message: 'Введите номер договора' }]}
                      validateStatus={fieldErrors['conNumber'] ? 'error' : undefined}
                      help={fieldErrors['conNumber']}
                    >
                      <Input maxLength={50} placeholder="Номер договора" />
                    </Form.Item>
                  </Col>
                  <Col xs={24} md={12}>
                    <Form.Item
                      label="Дата"
                      name="conDate"
                      rules={[{ required: true, message: 'Введите дату' }]}
                      validateStatus={fieldErrors['conDate'] ? 'error' : undefined}
                      help={fieldErrors['conDate']}
                    >
                      <DatePicker format={DATE_FORMAT} style={{ width: '100%' }} placeholder="Выберите дату" />
                    </Form.Item>
                  </Col>
                  <Col xs={24} md={12}>
                    <Form.Item
                      label="Срок действия"
                      name="conFinalDate"
                      validateStatus={fieldErrors['conFinalDate'] ? 'error' : undefined}
                      help={fieldErrors['conFinalDate']}
                    >
                      <DatePicker format={DATE_FORMAT} style={{ width: '100%' }} placeholder="Выберите срок действия" />
                    </Form.Item>
                  </Col>
                  <Col xs={24} md={12}>
                    <Form.Item name="conReusable" valuePropName="checked" style={{ marginTop: 30 }}>
                      <Checkbox>Многоразовый</Checkbox>
                    </Form.Item>
                  </Col>
                </Row>

                {/* Контрагент */}
                <Form.Item
                  label="Контрагент"
                  validateStatus={fieldErrors['contractor.id'] ? 'error' : undefined}
                  help={fieldErrors['contractor.id']}
                >
                  <Space.Compact style={{ width: '100%' }}>
                    <Form.Item name="contractor" noStyle rules={[{ required: true, message: 'Выберите контрагента' }]}>
                      <Select
                        allowClear
                        showSearch
                        optionFilterProp="label"
                        placeholder="Выберите контрагента"
                        options={openData.lookups.contractors.map((c) => ({ value: c.id, label: c.name }))}
                        style={{ width: '100%' }}
                      />
                    </Form.Item>
                    <Button icon={<PlusOutlined />} onClick={() => navigate('/contractors/new?returnTo=contract')} />
                  </Space.Compact>
                </Form.Item>

                <Row gutter={16}>
                  <Col xs={24} md={12}>
                    <Form.Item
                      label="Валюта"
                      name="currency"
                      rules={[{ required: true, message: 'Выберите валюту' }]}
                      validateStatus={fieldErrors['currency.id'] ? 'error' : undefined}
                      help={fieldErrors['currency.id']}
                    >
                      <Select
                        allowClear
                        placeholder="Выберите валюту"
                        options={openData.lookups.currencies.map((c) => ({ value: c.id, label: c.name }))}
                      />
                    </Form.Item>
                  </Col>
                  <Col xs={24} md={12}>
                    <Form.Item
                      label="Продавец"
                      name="seller"
                      rules={[{ required: true, message: 'Выберите продавца' }]}
                      validateStatus={fieldErrors['seller.id'] ? 'error' : undefined}
                      help={fieldErrors['seller.id']}
                    >
                      <Select
                        allowClear
                        placeholder="Выберите продавца"
                        options={openData.lookups.sellers.map((s) => ({ value: s.id, label: s.name }))}
                      />
                    </Form.Item>
                  </Col>
                </Row>

                {/* Примечание */}
                <Form.Item
                  label="Примечание"
                  name="conComment"
                  validateStatus={fieldErrors['conComment'] ? 'error' : undefined}
                  help={fieldErrors['conComment']}
                >
                  <Input.TextArea rows={3} maxLength={5000} showCount placeholder="Примечание к договору" />
                </Form.Item>
              </Card>

              {/* --- Card: Спецификации --- */}
              <Card
                title="Спецификации"
                extra={
                  <Button
                    type="primary"
                    icon={<PlusOutlined />}
                    onClick={() =>
                      navigate('/contracts/draft/specifications/new', {
                        state: {
                          currencyName:
                            openData.lookups.currencies.find((c) => c.id === form.getFieldValue('currency'))?.name ?? 'BYN',
                        },
                      })
                    }
                  >
                    Создать спецификацию
                  </Button>
                }
              >
                <SpecificationsTable data={specifications} />
              </Card>

              {/* --- Card: Файлы --- */}
              <Card title="Прикреплённые файлы">
                <FileUploadSection
                  attachments={attachments}
                  onAttachClick={() => navigate('/contracts/draft/attachments')}
                />
              </Card>
            </Flex>
          </Col>

          {/* ===== Right column: 1/3 — sidebar ===== */}
          <Col xs={24} lg={8}>
            <div style={{ position: 'sticky', top: 16 }}>
              <Flex vertical gap="large">
                {/* Статусы */}
                <Card title="Статусы и документы">
                  <Flex vertical gap="middle">
                    <Form.Item name="conFaxCopy" valuePropName="checked" style={{ marginBottom: 0 }}>
                      <Checkbox onChange={conFaxCopyOnChange}>Факсовая копия</Checkbox>
                    </Form.Item>
                    <Form.Item name="conOriginal" valuePropName="checked" style={{ marginBottom: 0 }}>
                      <Checkbox onChange={conOriginalOnChange}>Оригинал</Checkbox>
                    </Form.Item>
                    <Form.Item name="conAnnul" valuePropName="checked" style={{ marginBottom: 0 }}>
                      <Checkbox>Аннулирован</Checkbox>
                    </Form.Item>
                    {conAnnulValue && (
                      <Form.Item
                        label="Дата аннулирования"
                        name="conAnnulDate"
                        validateStatus={fieldErrors['conAnnulDate'] ? 'error' : undefined}
                        help={fieldErrors['conAnnulDate']}
                        style={{ paddingLeft: 24 }}
                      >
                        <DatePicker format={DATE_FORMAT} style={{ width: '100%' }} placeholder="Выберите дату" />
                      </Form.Item>
                    )}
                  </Flex>
                </Card>

                {/* Действия */}
                <Flex vertical gap="middle">
                  <Button type="primary" size="large" block icon={<SaveOutlined />} htmlType="submit" loading={saving}>
                    Сохранить
                  </Button>
                  <Button size="large" block icon={<CloseOutlined />} onClick={handleCancel}>
                    Отмена
                  </Button>
                </Flex>
              </Flex>
            </div>
          </Col>
        </Row>
      </Form>
    </div>
  );
}
