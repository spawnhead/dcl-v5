/**
 * Order create/edit. CONTRACTS: docs/screens/order_edit.
 * Layout per Create Contract Redesign (OrderPage)
 * Routes: /orders/new (create), /orders/:id/edit (edit).
 */
import {
  Button,
  Card,
  Col,
  DatePicker,
  Divider,
  Form,
  Input,
  InputNumber,
  Row,
  Select,
  Space,
  Table,
  Tag,
  Typography,
  theme,
} from 'antd';
import {
  DeleteOutlined,
  DownloadOutlined,
  FileTextOutlined,
  ImportOutlined,
  PaperClipOutlined,
  PlusOutlined,
  PrinterOutlined,
  SaveOutlined,
} from '@ant-design/icons';
import dayjs from 'dayjs';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ScreenLoader } from '../../shared/ui/ScreenLoader';
import { showLoading, hideLoading, notifyError, setFlashSuccess } from '../../shared/lib/feedback';
import { fetchWithErrorHandling } from '../../shared/lib/api';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const { TextArea } = Input;

interface LookupItem { id: string; name: string }
interface OrderHeaderDto {
  ordId: number | null;
  ordNumber: string | null;
  ordDate: string | null;
  contractorId: string | null;
  contactPersonId: string | null;
  currencyId: string | null;
  stuffCategoryId: string | null;
  blankId: string | null;
  sellerForWhoId: string | null;
  contractorForId: string | null;
  contractId: string | null;
  specificationId: string | null;
  ordBlock?: number | null;
  ordAnnul?: number | null;
  ordSumm?: number | null;
  ordComment?: string | null;
  ordSentToProdDate?: string | null;
  ordReceivedConfDate?: string | null;
  ordNumConf?: string | null;
  ordDateConf?: string | null;
  ordConfSentDate?: string | null;
  ordExecutedDate?: string | null;
  ordReadyForDelivDate?: string | null;
  ordPayCondition?: string | null;
  ordAddr?: string | null;
  ordDeliveryTerm?: string | null;
  ordAddInfo?: string | null;
}
interface OrderProduceRowDto {
  oprId: number | null;
  oprProduceName?: string | null;
  oprCatalogNum?: string | null;
  oprCount?: number | null;
  oprPriceBrutto?: number | null;
  oprDiscount?: number | null;
  oprPriceNetto?: number | null;
  oprSumm?: number | null;
  oprComment?: string | null;
  drpPrice?: number | null;
}
interface OrderPaymentRowDto {
  orpId: number | null;
  orpPercent?: number | null;
  orpSum?: number | null;
  orpDate?: string | null;
}
interface OrderPaySumRowDto {
  opsId: number | null;
  opsSum?: number | null;
  opsDate?: string | null;
}
interface OrderEditLookupsDto {
  contractors: LookupItem[];
  sellers: LookupItem[];
  currencies: LookupItem[];
  stuffCategories: LookupItem[];
  blanks: LookupItem[];
  contracts: LookupItem[];
  specifications: LookupItem[];
}
interface OrderEditOpenResponse {
  order: OrderHeaderDto;
  produces: OrderProduceRowDto[];
  orderPayments?: OrderPaymentRowDto[] | null;
  orderPaySums?: OrderPaySumRowDto[] | null;
  lookups: OrderEditLookupsDto;
  roleFlags: { admin: boolean; economist: boolean; logist: boolean; manager: boolean; userInLithuania: boolean };
  formReadOnly: boolean;
}

interface ProduceRow extends OrderProduceRowDto { key: string }
interface PaymentRow extends OrderPaymentRowDto { key: string }
interface PaySumRow extends OrderPaySumRowDto { key: string }

export default function OrderEditPage() {
  const { token } = theme.useToken();
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [openData, setOpenData] = useState<OrderEditOpenResponse | null>(null);
  const [produces, setProduces] = useState<ProduceRow[]>([]);
  const [payments, setPayments] = useState<PaymentRow[]>([]);
  const [paySums, setPaySums] = useState<PaySumRow[]>([]);
  const isNew = id === undefined || id === 'new';

  const loadOpen = useCallback(async () => {
    const url = isNew
      ? `${API_BASE}/api/orders/edit/open`
      : `${API_BASE}/api/orders/edit/open?ordId=${encodeURIComponent(id!)}`;
    const { ok, data, error } = await fetchWithErrorHandling<OrderEditOpenResponse>(url, { method: 'GET' });
    if (!ok || !data) {
      if (error) notifyError(error.message);
      setLoading(false);
      return;
    }
    setOpenData(data);
    const rows: ProduceRow[] = (data.produces ?? []).map((p, i) => ({
      ...p,
      key: p.oprId != null ? String(p.oprId) : `new-${i}`,
    }));
    setProduces(rows);
    const paymentRows: PaymentRow[] = (data.orderPayments ?? [{ orpId: null, orpPercent: 100, orpSum: null, orpDate: null }]).map((p, i) => ({
      ...p,
      key: p.orpId != null ? `p-${p.orpId}` : `p-new-${i}`,
    }));
    setPayments(paymentRows);
    const paySumRows: PaySumRow[] = (data.orderPaySums ?? [{ opsId: null, opsSum: null, opsDate: null }]).map((s, i) => ({
      ...s,
      key: s.opsId != null ? `s-${s.opsId}` : `s-new-${i}`,
    }));
    setPaySums(paySumRows);
    form.setFieldsValue({
      ordNumber: data.order?.ordNumber ?? '',
      ordDate: data.order?.ordDate ? dayjs(data.order.ordDate) : dayjs(),
      contractorId: data.order?.contractorId ?? undefined,
      contactPersonId: data.order?.contactPersonId ?? undefined,
      currencyId: data.order?.currencyId ?? undefined,
      stuffCategoryId: data.order?.stuffCategoryId ?? undefined,
      blankId: data.order?.blankId ?? undefined,
      sellerForWhoId: data.order?.sellerForWhoId ?? undefined,
      contractorForId: data.order?.contractorForId ?? undefined,
      contractId: data.order?.contractId ?? undefined,
      specificationId: data.order?.specificationId ?? undefined,
      ordComment: data.order?.ordComment ?? '',
      ordNumConf: data.order?.ordNumConf ?? '',
      ordPayCondition: data.order?.ordPayCondition ?? '',
      ordAddr: data.order?.ordAddr ?? '',
      ordDeliveryTerm: data.order?.ordDeliveryTerm ?? '',
      ordAddInfo: data.order?.ordAddInfo ?? '',
      ordSentToProdDate: data.order?.ordSentToProdDate ? dayjs(data.order.ordSentToProdDate) : null,
      ordReceivedConfDate: data.order?.ordReceivedConfDate ? dayjs(data.order.ordReceivedConfDate) : null,
      ordDateConf: data.order?.ordDateConf ? dayjs(data.order.ordDateConf) : null,
      ordConfSentDate: data.order?.ordConfSentDate ? dayjs(data.order.ordConfSentDate) : null,
      ordExecutedDate: data.order?.ordExecutedDate ? dayjs(data.order.ordExecutedDate) : null,
      ordReadyForDelivDate: data.order?.ordReadyForDelivDate ? dayjs(data.order.ordReadyForDelivDate) : null,
    });
    setLoading(false);
  }, [isNew, id, form]);

  useEffect(() => {
    const startedAt = performance.now();
    const minLoaderMs = import.meta.env.DEV ? 350 : 0;
    loadOpen().then(() => {
      const elapsed = performance.now() - startedAt;
      setTimeout(() => setLoading(false), Math.max(0, minLoaderMs - elapsed));
    });
  }, [loadOpen]);

  const addProduce = () => {
    setProduces((prev) => [...prev, { oprId: null, key: `new-${Date.now()}` }]);
  };

  const removeProduce = (key: string) => {
    setProduces((prev) => prev.filter((r) => r.key !== key));
  };

  const addPayment = () => {
    setPayments((prev) => [...prev, { orpId: null, orpPercent: 0, orpSum: null, orpDate: null, key: `p-new-${Date.now()}` }]);
  };

  const removePayment = (key: string) => {
    setPayments((prev) => prev.filter((r) => r.key !== key));
  };

  const recalculatePayments = () => {
    const ordSumm = Number(openData?.order?.ordSumm ?? 0) || 0;
    setPayments((prev) =>
      prev.map((p) => ({
        ...p,
        orpSum: ordSumm ? (ordSumm * (Number(p.orpPercent ?? 0) / 100)) : p.orpSum,
      }))
    );
  };

  const addPaySum = () => {
    setPaySums((prev) => [...prev, { opsId: null, opsSum: null, opsDate: null, key: `s-new-${Date.now()}` }]);
  };

  const removePaySum = (key: string) => {
    setPaySums((prev) => prev.filter((r) => r.key !== key));
  };

  const onFinish = async () => {
    const values = form.getFieldsValue();
    const orderDto = {
      ordNumber: values.ordNumber ?? '',
      ordDate: values.ordDate ? dayjs(values.ordDate).format('YYYY-MM-DD') : dayjs().format('YYYY-MM-DD'),
      contractorId: values.contractorId ? Number(values.contractorId) : null,
      contactPersonId: values.contactPersonId ? Number(values.contactPersonId) : null,
      currencyId: values.currencyId ? Number(values.currencyId) : null,
      stuffCategoryId: values.stuffCategoryId ? Number(values.stuffCategoryId) : null,
      blankId: values.blankId ? Number(values.blankId) : null,
      sellerForWhoId: values.sellerForWhoId ? Number(values.sellerForWhoId) : null,
      contractorForId: values.contractorForId ? Number(values.contractorForId) : null,
      contractId: values.contractId ? Number(values.contractId) : null,
      specificationId: values.specificationId ? Number(values.specificationId) : null,
      ordSentToProdDate: values.ordSentToProdDate ? dayjs(values.ordSentToProdDate).format('YYYY-MM-DD') : null,
      ordReceivedConfDate: values.ordReceivedConfDate ? dayjs(values.ordReceivedConfDate).format('YYYY-MM-DD') : null,
      ordNumConf: values.ordNumConf ?? null,
      ordDateConf: values.ordDateConf ? dayjs(values.ordDateConf).format('YYYY-MM-DD') : null,
      ordConfSentDate: values.ordConfSentDate ? dayjs(values.ordConfSentDate).format('YYYY-MM-DD') : null,
      ordReadyForDelivDate: values.ordReadyForDelivDate ? dayjs(values.ordReadyForDelivDate).format('YYYY-MM-DD') : null,
      ordExecutedDate: values.ordExecutedDate ? dayjs(values.ordExecutedDate).format('YYYY-MM-DD') : null,
      ordPayCondition: values.ordPayCondition ?? null,
      ordAddr: values.ordAddr ?? null,
      ordDeliveryTerm: values.ordDeliveryTerm ?? null,
      ordAddInfo: values.ordAddInfo ?? null,
      ordComment: values.ordComment ?? null,
    };
    const producesDto = produces.map((p) => ({
      oprId: p.oprId ?? null,
      oprProduceName: p.oprProduceName ?? '',
      oprCatalogNum: p.oprCatalogNum ?? '',
      oprCount: p.oprCount ?? null,
      oprPriceBrutto: p.oprPriceBrutto ?? null,
      oprDiscount: p.oprDiscount ?? null,
      oprPriceNetto: p.oprPriceNetto ?? null,
      oprComment: p.oprComment ?? null,
      drpPrice: p.drpPrice ?? null,
    }));
    const orderPaymentsDto = payments.map((p) => ({
      orpId: p.orpId ?? null,
      orpPercent: p.orpPercent ?? null,
      orpSum: p.orpSum ?? null,
      orpDate: p.orpDate ? dayjs(p.orpDate).format('YYYY-MM-DD') : null,
    }));
    const orderPaySumsDto = paySums.map((s) => ({
      opsId: s.opsId ?? null,
      opsSum: s.opsSum ?? null,
      opsDate: s.opsDate ? dayjs(s.opsDate).format('YYYY-MM-DD') : null,
    }));
    const body = {
      isNewDoc: isNew,
      ordId: isNew ? null : Number(id),
      order: orderDto,
      produces: producesDto,
      orderPayments: orderPaymentsDto,
      orderPaySums: orderPaySumsDto,
    };
    const key = showLoading('Сохранение заказа...');
    setSaving(true);
    const url = `${API_BASE}/api/orders/edit/save`;
    const method = isNew ? 'POST' : 'PUT';
    const { ok, data, error } = await fetchWithErrorHandling<{ ordId: number; ordNumber: string }>(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });
    hideLoading(key);
    setSaving(false);
    if (!ok) {
      notifyError(error?.message ?? 'Ошибка сохранения');
      return;
    }
    const ordNumber = data?.ordNumber ?? (data?.ordId != null ? String(data.ordId) : '');
    setFlashSuccess('Заказ сохранён', ordNumber ? `№ ${ordNumber}` : undefined);
    navigate('/orders');
  };

  if (loading) return <ScreenLoader />;

  const lookups = openData?.lookups ?? {
    contractors: [], sellers: [], currencies: [], stuffCategories: [], blanks: [], contracts: [], specifications: [],
  };
  const readOnly = openData?.formReadOnly ?? false;

  const produceColumns = [
    { title: 'Наименование', dataIndex: 'oprProduceName', key: 'oprProduceName', render: (_: unknown, record: ProduceRow) => readOnly ? record.oprProduceName : <Input size="small" value={record.oprProduceName} onChange={(e) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprProduceName: e.target.value } : p)))} /> },
    { title: 'Кол-во', dataIndex: 'oprCount', key: 'oprCount', width: 90, render: (_: unknown, record: ProduceRow) => readOnly ? record.oprCount : <InputNumber size="small" min={0} value={record.oprCount} onChange={(v) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprCount: v ?? null } : p)))} /> },
    { title: 'Цена брутто', dataIndex: 'oprPriceBrutto', key: 'oprPriceBrutto', width: 110, render: (_: unknown, record: ProduceRow) => readOnly ? record.oprPriceBrutto : <InputNumber size="small" min={0} value={record.oprPriceBrutto} onChange={(v) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprPriceBrutto: v ?? null } : p)))} /> },
    { title: 'Скидка %', dataIndex: 'oprDiscount', key: 'oprDiscount', width: 80, render: (_: unknown, record: ProduceRow) => readOnly ? record.oprDiscount : <InputNumber size="small" min={0} value={record.oprDiscount} onChange={(v) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprDiscount: v ?? null } : p)))} /> },
    { title: 'Комментарий', dataIndex: 'oprComment', key: 'oprComment', render: (_: unknown, record: ProduceRow) => readOnly ? record.oprComment : <Input size="small" value={record.oprComment} onChange={(e) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprComment: e.target.value } : p)))} /> },
    { title: '', key: 'actions', width: 60, render: (_: unknown, record: ProduceRow) => readOnly ? null : <Button type="text" danger icon={<DeleteOutlined />} size="small" onClick={() => removeProduce(record.key)} /> },
  ];

  return (
    <div style={{ padding: 24, minHeight: 'calc(100vh - 64px)' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
        <Typography.Title level={4} style={{ margin: 0 }}>
          {isNew ? 'Новый заказ' : `Заказ № ${openData?.order?.ordNumber ?? id}`}
          {isNew && <Tag color="blue" style={{ marginLeft: 8 }}>Новый</Tag>}
        </Typography.Title>
        <Space>
          <Typography.Text type="secondary">Текущая дата: {dayjs().format('DD.MM.YYYY')}</Typography.Text>
        </Space>
      </div>

      <Form form={form} layout="vertical" onFinish={onFinish}>
        {/* Section 1: Main Info & Counterparty */}
        <Row gutter={24}>
          <Col xs={24} xl={12}>
            <Card variant="borderless" title="Основные данные" style={{ height: '100%', marginBottom: 24 }}>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item name="sellerForWhoId" label="Продукция заказывается (кем?)" rules={[{ required: true }]}>
                    <Select allowClear options={lookups.sellers.map((s) => ({ value: s.id, label: s.name }))} placeholder="Выберите компанию" showSearch optionFilterProp="label" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="ordNumber" label="Номер" rules={[{ required: true }]}>
                    <Input placeholder="Номер заказа" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="ordDate" label="Дата" rules={[{ required: true }]}>
                    <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="blankId" label="Язык бланка">
                    <Select allowClear options={lookups.blanks.map((b) => ({ value: b.id, label: b.name }))} placeholder="Бланк" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="stuffCategoryId" label="Категория товара">
                    <Select allowClear options={lookups.stuffCategories.map((s) => ({ value: s.id, label: s.name }))} placeholder="Категория" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item name="contractorForId" label="Контрагент (для)">
                    <Select allowClear showSearch optionFilterProp="label" options={lookups.contractors.map((c) => ({ value: c.id, label: c.name }))} placeholder="Выберите контрагента" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="contractId" label="Договор">
                    <Select allowClear options={lookups.contracts.map((c) => ({ value: c.id, label: c.name }))} disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="specificationId" label="Спецификация">
                    <Select allowClear options={lookups.specifications.map((s) => ({ value: s.id, label: s.name }))} disabled={readOnly} />
                  </Form.Item>
                </Col>
              </Row>
            </Card>
          </Col>

          <Col xs={24} xl={12}>
            <Card variant="borderless" title="Контрагент" style={{ height: '100%', marginBottom: 24 }}>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item name="contractorId" label="Контрагент (куда)" rules={[{ required: true }]}>
                    <Select allowClear showSearch optionFilterProp="label" options={lookups.contractors.map((c) => ({ value: c.id, label: c.name }))} placeholder="Выберите контрагента" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item name="contactPersonId" label="Контактное лицо (Получатель)">
                    <Select allowClear options={[]} placeholder="Выберите контакт" disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item name="ordComment" label="Комментарий к заказу">
                    <TextArea rows={3} disabled={readOnly} />
                  </Form.Item>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>

        {/* Section 2: Settings & Price */}
        <Card variant="borderless" style={{ marginBottom: 24 }}>
          <Row gutter={[24, 16]} align="middle">
            <Col xs={24} md={8}>
              <Form.Item name="currencyId" label="Валюта" rules={[{ required: true }]} style={{ marginBottom: 0 }}>
                <Select allowClear options={lookups.currencies.map((c) => ({ value: c.id, label: c.name }))} placeholder="Валюта" style={{ width: 120 }} disabled={readOnly} />
              </Form.Item>
            </Col>
          </Row>
        </Card>

        {/* Section 3: Спецификация */}
        <Card variant="borderless" title="Спецификация" style={{ marginBottom: 24 }} styles={{ body: { padding: 0 } }}>
          <div style={{ padding: '16px 24px', borderBottom: `1px solid ${token.colorBorderSecondary}`, display: 'flex', gap: 8, flexWrap: 'wrap' }}>
            <Button icon={<DownloadOutlined />} disabled>Скачать шаблон</Button>
            <Button icon={<ImportOutlined />} disabled>Импорт из КП</Button>
            <Button icon={<FileTextOutlined />} disabled>Импорт из Excel</Button>
            {!readOnly && <Button type="primary" icon={<PlusOutlined />} onClick={addProduce}>Добавить позицию</Button>}
          </div>
          <Table dataSource={produces} columns={produceColumns} pagination={false} scroll={{ x: 1300 }} size="small" />
        </Card>

        {/* Section 4: График оплаты + Суммы оплаты */}
        <Row gutter={24}>
          <Col xs={24} lg={12}>
            <Card variant="borderless" title="График оплаты" style={{ marginBottom: 24 }}>
              <Table
                dataSource={payments}
                pagination={false}
                size="small"
                columns={[
                  { title: '%', dataIndex: 'orpPercent', key: 'orpPercent', width: 100, render: (_: unknown, record: PaymentRow) => readOnly ? record.orpPercent : <InputNumber size="small" min={0} max={100} value={record.orpPercent} onChange={(v) => setPayments((prev) => prev.map((p) => (p.key === record.key ? { ...p, orpPercent: v ?? null } : p)))} /> },
                  { title: 'Сумма', dataIndex: 'orpSum', key: 'orpSum', width: 120, render: (_: unknown, record: PaymentRow) => readOnly ? record.orpSum : <InputNumber size="small" min={0} value={record.orpSum} onChange={(v) => setPayments((prev) => prev.map((p) => (p.key === record.key ? { ...p, orpSum: v ?? null } : p)))} /> },
                  { title: 'Дата', dataIndex: 'orpDate', key: 'orpDate', width: 130, render: (_: unknown, record: PaymentRow) => readOnly ? record.orpDate : <DatePicker size="small" value={record.orpDate ? dayjs(record.orpDate) : null} format="DD.MM.YYYY" onChange={(d) => setPayments((prev) => prev.map((p) => (p.key === record.key ? { ...p, orpDate: d ? d.format('YYYY-MM-DD') : null } : p)))} /> },
                  { title: '', key: 'actions', width: 60, render: (_: unknown, record: PaymentRow) => readOnly ? null : <Button type="link" danger size="small" onClick={() => removePayment(record.key)}>Удалить</Button> },
                ]}
              />
              {!readOnly && (
                <Space style={{ marginTop: 8 }}>
                  <Button type="dashed" icon={<PlusOutlined />} onClick={addPayment}>Добавить</Button>
                  <Button type="default" onClick={recalculatePayments}>Пересчитать</Button>
                </Space>
              )}
            </Card>
          </Col>
          <Col xs={24} lg={12}>
            <Card variant="borderless" title="Суммы оплаты" style={{ marginBottom: 24 }}>
              <Table
                dataSource={paySums}
                pagination={false}
                size="small"
                columns={[
                  { title: 'Сумма', dataIndex: 'opsSum', key: 'opsSum', width: 120, render: (_: unknown, record: PaySumRow) => readOnly ? record.opsSum : <InputNumber size="small" min={0} value={record.opsSum} onChange={(v) => setPaySums((prev) => prev.map((s) => (s.key === record.key ? { ...s, opsSum: v ?? null } : s)))} /> },
                  { title: 'Дата', dataIndex: 'opsDate', key: 'opsDate', width: 130, render: (_: unknown, record: PaySumRow) => readOnly ? record.opsDate : <DatePicker size="small" value={record.opsDate ? dayjs(record.opsDate) : null} format="DD.MM.YYYY" onChange={(d) => setPaySums((prev) => prev.map((s) => (s.key === record.key ? { ...s, opsDate: d ? d.format('YYYY-MM-DD') : null } : s)))} /> },
                  { title: '', key: 'actions', width: 60, render: (_: unknown, record: PaySumRow) => readOnly ? null : <Button type="link" danger size="small" onClick={() => removePaySum(record.key)}>Удалить</Button> },
                ]}
              />
              {!readOnly && <Button type="dashed" icon={<PlusOutlined />} onClick={addPaySum} style={{ marginTop: 8 }}>Добавить</Button>}
            </Card>
          </Col>
        </Row>

        {/* Section 5: Условия поставки и оплаты + Примечания */}
        <Row gutter={24}>
          <Col xs={24} lg={14}>
            <Card variant="borderless" title="Условия поставки и оплаты" style={{ height: '100%', marginBottom: 24 }}>
              <Row gutter={12}>
                <Col span={24}>
                  <Form.Item name="ordAddr" label="Адрес">
                    <Input disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item name="ordPayCondition" label="Условие оплаты">
                    <TextArea rows={2} disabled={readOnly} />
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item name="ordDeliveryTerm" label="Срок поставки">
                    <Input disabled={readOnly} />
                  </Form.Item>
                </Col>
              </Row>
            </Card>
          </Col>
          <Col xs={24} lg={10}>
            <Card variant="borderless" title="Примечания" style={{ height: '100%', marginBottom: 24 }}>
              <Form.Item name="ordAddInfo" label="Примечания (внутреннее)">
                <TextArea rows={4} disabled={readOnly} />
              </Form.Item>
            </Card>
          </Col>
        </Row>

        {/* Section 6: Логистика */}
        <Card
          variant="borderless"
          title={<Space><Typography.Text strong>Заполняется отделом логистики</Typography.Text><Tag color="orange">В работе</Tag></Space>}
          style={{ marginBottom: 24, border: `1px solid ${token.colorBorder}` }}
          styles={{ header: { background: token.colorFillAlter } }}
        >
          <Row gutter={24}>
            <Col xs={24} md={12} lg={8}>
              <Form.Item name="ordSentToProdDate" label="Заказ передан на производство">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
              </Form.Item>
              <Form.Item name="ordReceivedConfDate" label="Получено подтверждение заказа">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
              </Form.Item>
              <Form.Item name="ordNumConf" label="№ подтверждения">
                <Input disabled={readOnly} />
              </Form.Item>
              <Form.Item name="ordDateConf" label="Срок изготовления">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
              </Form.Item>
            </Col>
            <Col xs={24} md={12} lg={8}>
              <Form.Item name="ordConfSentDate" label="Подтверждение выслано">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
              </Form.Item>
              <Form.Item name="ordReadyForDelivDate" label="Готов к отгрузке">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
              </Form.Item>
              <Form.Item name="ordExecutedDate" label="Исполнен">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
              </Form.Item>
            </Col>
          </Row>
        </Card>

        {/* Section 7: Прикрепленные файлы */}
        <Card variant="borderless" title="Прикрепленные файлы" style={{ marginBottom: 24 }}>
          <Button icon={<PaperClipOutlined />} disabled>Прикрепить файл</Button>
        </Card>

        {/* Fixed Bottom Action Bar */}
        <Card
          variant="borderless"
          style={{
            position: 'sticky',
            bottom: 0,
            zIndex: 10,
            boxShadow: '0 -2px 10px rgba(0,0,0,0.05)',
            margin: '0 -24px -24px',
            borderRadius: 0,
          }}
          styles={{ body: { padding: '16px 24px' } }}
        >
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: 16 }}>
            <Space />
            <Space wrap>
              <Button icon={<PrinterOutlined />} disabled>Печать</Button>
              <Button onClick={() => navigate('/orders')}>Отмена</Button>
              <Button type="primary" icon={<SaveOutlined />} htmlType="submit" loading={saving} disabled={readOnly}>Сохранить</Button>
            </Space>
          </div>
        </Card>
      </Form>
    </div>
  );
}
