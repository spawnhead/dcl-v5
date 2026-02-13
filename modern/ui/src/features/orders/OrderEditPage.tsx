/**
 * Order create/edit. CONTRACTS: docs/screens/order_edit.
 * Routes: /orders/new (create), /orders/:id/edit (edit).
 */
import { Button, Card, DatePicker, Form, Input, InputNumber, Layout, Select, Space, Table, Typography } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ScreenLoader } from '../../shared/ui/ScreenLoader';
import { showLoading, hideLoading, notifyError, setFlashSuccess } from '../../shared/lib/feedback';
import { fetchWithErrorHandling } from '../../shared/lib/api';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

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

  return (
    <Layout style={{ padding: 24 }}>
      <Typography.Title level={4}>
        {isNew ? 'Новый заказ' : `Заказ ${openData?.order?.ordNumber ?? id}`}
      </Typography.Title>
      <Form form={form} layout="vertical" onFinish={onFinish}>
        <Card title="Основные данные" style={{ marginBottom: 16 }}>
          <Form.Item name="ordNumber" label="Номер" rules={[{ required: true }]}>
            <Input placeholder="Номер заказа" disabled={readOnly} />
          </Form.Item>
          <Form.Item name="ordDate" label="Дата заказа" rules={[{ required: true }]}>
            <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" disabled={readOnly} />
          </Form.Item>
          <Form.Item name="contractorId" label="Контрагент" rules={[{ required: true }]}>
            <Select
              allowClear
              showSearch
              optionFilterProp="label"
              options={lookups.contractors.map((c) => ({ value: c.id, label: c.name }))}
              placeholder="Контрагент"
              disabled={readOnly}
            />
          </Form.Item>
          <Form.Item name="sellerForWhoId" label="Продавец" rules={[{ required: true }]}>
            <Select
              allowClear
              options={lookups.sellers.map((s) => ({ value: s.id, label: s.name }))}
              placeholder="Продавец"
              disabled={readOnly}
            />
          </Form.Item>
          <Form.Item name="currencyId" label="Валюта" rules={[{ required: true }]}>
            <Select
              allowClear
              options={lookups.currencies.map((c) => ({ value: c.id, label: c.name }))}
              placeholder="Валюта"
              disabled={readOnly}
            />
          </Form.Item>
          <Form.Item name="blankId" label="Бланк">
            <Select
              allowClear
              options={lookups.blanks.map((b) => ({ value: b.id, label: b.name }))}
              placeholder="Бланк"
              disabled={readOnly}
            />
          </Form.Item>
          <Form.Item name="stuffCategoryId" label="Категория товара">
            <Select
              allowClear
              options={lookups.stuffCategories.map((s) => ({ value: s.id, label: s.name }))}
              placeholder="Категория"
              disabled={readOnly}
            />
          </Form.Item>
          <Form.Item name="contractorForId" label="Контрагент (для)">
            <Select
              allowClear
              showSearch
              optionFilterProp="label"
              options={lookups.contractors.map((c) => ({ value: c.id, label: c.name }))}
              disabled={readOnly}
            />
          </Form.Item>
          <Form.Item name="contractId" label="Договор">
            <Select allowClear options={lookups.contracts.map((c) => ({ value: c.id, label: c.name }))} disabled={readOnly} />
          </Form.Item>
          <Form.Item name="specificationId" label="Спецификация">
            <Select allowClear options={lookups.specifications.map((s) => ({ value: s.id, label: s.name }))} disabled={readOnly} />
          </Form.Item>
          <Form.Item name="ordComment" label="Комментарий">
            <Input.TextArea rows={2} disabled={readOnly} />
          </Form.Item>
        </Card>
        <Card title="Позиции" style={{ marginBottom: 16 }}>
          <Table
            dataSource={produces}
            pagination={false}
            size="small"
            columns={[
              { title: 'Наименование', dataIndex: 'oprProduceName', key: 'oprProduceName', render: (_, record) => readOnly ? record.oprProduceName : <Input size="small" value={record.oprProduceName} onChange={(e) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprProduceName: e.target.value } : p)))} /> },
              { title: 'Кол-во', dataIndex: 'oprCount', key: 'oprCount', width: 90, render: (_, record) => readOnly ? record.oprCount : <InputNumber size="small" min={0} value={record.oprCount} onChange={(v) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprCount: v ?? null } : p)))} /> },
              { title: 'Цена брутто', dataIndex: 'oprPriceBrutto', key: 'oprPriceBrutto', width: 110, render: (_, record) => readOnly ? record.oprPriceBrutto : <InputNumber size="small" min={0} value={record.oprPriceBrutto} onChange={(v) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprPriceBrutto: v ?? null } : p)))} /> },
              { title: 'Скидка %', dataIndex: 'oprDiscount', key: 'oprDiscount', width: 80, render: (_, record) => readOnly ? record.oprDiscount : <InputNumber size="small" min={0} value={record.oprDiscount} onChange={(v) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprDiscount: v ?? null } : p)))} /> },
              { title: 'Комментарий', dataIndex: 'oprComment', key: 'oprComment', render: (_, record) => readOnly ? record.oprComment : <Input size="small" value={record.oprComment} onChange={(e) => setProduces((prev) => prev.map((p) => (p.key === record.key ? { ...p, oprComment: e.target.value } : p)))} /> },
              { title: '', key: 'actions', width: 60, render: (_, record) => readOnly ? null : <Button type="link" danger size="small" onClick={() => removeProduce(record.key)}>Удалить</Button> },
            ]}
          />
          {!readOnly && <Button type="dashed" icon={<PlusOutlined />} onClick={addProduce} style={{ marginTop: 8 }}>Добавить позицию</Button>}
        </Card>
        <Card title="График оплаты" style={{ marginBottom: 16 }}>
          <Table
            dataSource={payments}
            pagination={false}
            size="small"
            columns={[
              { title: '%', dataIndex: 'orpPercent', key: 'orpPercent', width: 100, render: (_, record) => readOnly ? record.orpPercent : <InputNumber size="small" min={0} max={100} value={record.orpPercent} onChange={(v) => setPayments((prev) => prev.map((p) => (p.key === record.key ? { ...p, orpPercent: v ?? null } : p)))} /> },
              { title: 'Сумма', dataIndex: 'orpSum', key: 'orpSum', width: 120, render: (_, record) => readOnly ? record.orpSum : <InputNumber size="small" min={0} value={record.orpSum} onChange={(v) => setPayments((prev) => prev.map((p) => (p.key === record.key ? { ...p, orpSum: v ?? null } : p)))} /> },
              { title: 'Дата', dataIndex: 'orpDate', key: 'orpDate', width: 130, render: (_, record) => readOnly ? record.orpDate : <DatePicker size="small" value={record.orpDate ? dayjs(record.orpDate) : null} format="DD.MM.YYYY" onChange={(d) => setPayments((prev) => prev.map((p) => (p.key === record.key ? { ...p, orpDate: d ? d.format('YYYY-MM-DD') : null } : p)))} /> },
              { title: '', key: 'actions', width: 60, render: (_, record) => readOnly ? null : <Button type="link" danger size="small" onClick={() => removePayment(record.key)}>Удалить</Button> },
            ]}
          />
          {!readOnly && (
            <Space style={{ marginTop: 8 }}>
              <Button type="dashed" icon={<PlusOutlined />} onClick={addPayment}>Добавить</Button>
              <Button type="default" onClick={recalculatePayments}>Пересчитать</Button>
            </Space>
          )}
        </Card>
        <Card title="Суммы оплаты" style={{ marginBottom: 16 }}>
          <Table
            dataSource={paySums}
            pagination={false}
            size="small"
            columns={[
              { title: 'Сумма', dataIndex: 'opsSum', key: 'opsSum', width: 120, render: (_, record) => readOnly ? record.opsSum : <InputNumber size="small" min={0} value={record.opsSum} onChange={(v) => setPaySums((prev) => prev.map((s) => (s.key === record.key ? { ...s, opsSum: v ?? null } : s)))} /> },
              { title: 'Дата', dataIndex: 'opsDate', key: 'opsDate', width: 130, render: (_, record) => readOnly ? record.opsDate : <DatePicker size="small" value={record.opsDate ? dayjs(record.opsDate) : null} format="DD.MM.YYYY" onChange={(d) => setPaySums((prev) => prev.map((s) => (s.key === record.key ? { ...s, opsDate: d ? d.format('YYYY-MM-DD') : null } : s)))} /> },
              { title: '', key: 'actions', width: 60, render: (_, record) => readOnly ? null : <Button type="link" danger size="small" onClick={() => removePaySum(record.key)}>Удалить</Button> },
            ]}
          />
          {!readOnly && <Button type="dashed" icon={<PlusOutlined />} onClick={addPaySum} style={{ marginTop: 8 }}>Добавить</Button>}
        </Card>
        <Space>
          <Button type="primary" htmlType="submit" loading={saving} disabled={readOnly}>Сохранить</Button>
          <Button onClick={() => navigate('/orders')}>Отмена</Button>
        </Space>
      </Form>
    </Layout>
  );
}
