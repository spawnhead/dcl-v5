/**
 * N3a2 Specification create (draft). docs/screens/contract_spec_create.
 */
import { Button, DatePicker, Form, Input, Layout, message, Select, Space, Typography } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

dayjs.extend(customParseFormat);

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';
const DATE_FORMAT = 'DD.MM.YYYY';

export default function ContractSpecCreatePage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [openData, setOpenData] = useState<{ lookups: { deliveryTerms: { id: string; name: string }[] } } | null>(null);

  const currencyName = (location.state as { currencyName?: string })?.currencyName ?? 'BYN';

  useEffect(() => {
    fetch(API_BASE + '/api/contracts/draft/specifications/new/open?currencyName=' + encodeURIComponent(currencyName))
      .then((r) => r.json())
      .then((data) => {
        setOpenData(data);
        form.setFieldsValue({
          spcNumber: data.defaults?.spcNumber ?? '',
          spcDate: data.defaults?.spcDate ? dayjs(data.defaults.spcDate, DATE_FORMAT) : null,
          spcSumm: data.defaults?.spcSumm ?? '',
          deliveryTerm: data.defaults?.deliveryTerm?.id,
        });
      })
      .catch(() => message.error('Ошибка загрузки'))
      .finally(() => setLoading(false));
  }, [currencyName, form]);

  const handleFinish = useCallback(
    (values: Record<string, unknown>) => {
      setSaving(true);
      const body = {
        user: null,
        spcNumber: values.spcNumber ?? '',
        spcDate: values.spcDate ? dayjs(values.spcDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
        spcSumm: values.spcSumm ?? '',
        spcSummNds: '',
        spcDeliveryCond: '',
        deliveryTerm: values.deliveryTerm ? { id: values.deliveryTerm, name: '' } : null,
        spcAdditionalDaysCount: '',
        spcDeliveryPercent: '',
        spcDeliverySum: '',
        spcDeliveryDate: '',
        spcAddPayCond: '',
        specificationPayments: [{ percent: 100, delayDays: 0, currencyName }],
        spcMontage: false,
        spcPayAfterMontage: false,
        spcFaxCopy: false,
        spcOriginal: false,
        spcComment: '',
      };
      fetch(API_BASE + '/api/contracts/draft/specifications/save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      })
        .then(async (r) => {
          const data = await r.json();
          if (!r.ok) {
            message.error('Ошибка валидации');
            return;
          }
          message.success('Спецификация добавлена');
          navigate('/contracts/new', { state: { addedSpecification: data.specification } });
        })
        .catch(() => message.error('Ошибка сохранения'))
        .finally(() => setSaving(false));
    },
    [currencyName, navigate]
  );

  if (loading || !openData) {
    return (
      <Layout style={{ padding: 16 }}>
        <Typography.Text>Загрузка...</Typography.Text>
      </Layout>
    );
  }

  return (
    <Layout style={{ padding: 16 }}>
      <Typography.Title level={4}>Добавить спецификацию</Typography.Title>
      <Form form={form} layout="horizontal" onFinish={handleFinish} style={{ maxWidth: 520 }}>
        <Form.Item label="Номер" name="spcNumber" rules={[{ required: true, message: 'Введите номер' }]}>
          <Input style={{ width: 200 }} />
        </Form.Item>
        <Form.Item label="Дата" name="spcDate" rules={[{ required: true, message: 'Введите дату' }]}>
          <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
        </Form.Item>
        <Form.Item label="Сумма" name="spcSumm">
          <Input style={{ width: 200 }} />
        </Form.Item>
        <Form.Item label="Условие поставки" name="deliveryTerm" rules={[{ required: true, message: 'Выберите условие' }]}>
          <Select
            allowClear
            placeholder="Условие поставки"
            options={openData.lookups.deliveryTerms.map((d) => ({ value: d.id, label: d.name }))}
            style={{ width: 240 }}
          />
        </Form.Item>
        <Form.Item>
          <Space>
            <Button type="primary" htmlType="submit" loading={saving}>Сохранить</Button>
            <Button onClick={() => navigate('/contracts/new')}>Отмена</Button>
          </Space>
        </Form.Item>
      </Form>
    </Layout>
  );
}
