/**
 * N3a2 Specification create (draft). CONTRACTS: docs/screens/contract_spec_create.
 * TASK-0013: 2 tabs 1:1 per SNAPSHOT: Главная / Претензии.
 */
import { Button, Checkbox, DatePicker, Form, Input, Layout, message, Select, Space, Table, Tabs, Typography } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

dayjs.extend(customParseFormat);

const API_BASE = (import.meta as { env?: { VITE_API_BASE_URL?: string } }).env?.VITE_API_BASE_URL ?? '';
const DATE_FORMAT = 'DD.MM.YYYY';

interface PaymentRow { percent: number; delayDays: number; currencyName: string }

export default function ContractSpecCreatePage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [openData, setOpenData] = useState<{
    defaults: Record<string, unknown>;
    lookups: { users: { id: string; userFullName: string }[]; deliveryTerms: { id: string; name: string }[] };
    tabs?: { id: string; label: string }[];
    currencyName: string;
  } | null>(null);
  const [payments, setPayments] = useState<PaymentRow[]>([]);

  const currencyName = (location.state as { currencyName?: string })?.currencyName ?? 'BYN';

  useEffect(() => {
    fetch(API_BASE + '/api/contracts/draft/specifications/new/open?currencyName=' + encodeURIComponent(currencyName))
      .then((r) => r.json())
      .then((data) => {
        setOpenData(data);
        const pmt = data.defaults?.specificationPayments ?? [{ percent: 100, delayDays: 0, currencyName }];
        setPayments(Array.isArray(pmt) ? pmt : [{ percent: 100, delayDays: 0, currencyName }]);
        form.setFieldsValue({
          user: data.defaults?.user?.id,
          spcNumber: data.defaults?.spcNumber ?? '',
          spcDate: data.defaults?.spcDate ? dayjs(data.defaults.spcDate, DATE_FORMAT) : null,
          spcSumm: data.defaults?.spcSumm ?? '',
          spcSummNds: data.defaults?.spcSummNds ?? '',
          spcDeliveryCond: data.defaults?.spcDeliveryCond ?? '',
          deliveryTerm: data.defaults?.deliveryTerm?.id,
          spcAdditionalDaysCount: data.defaults?.spcAdditionalDaysCount ?? '',
          spcDeliveryPercent: data.defaults?.spcDeliveryPercent ?? '',
          spcDeliverySum: data.defaults?.spcDeliverySum ?? '',
          spcDeliveryDate: data.defaults?.spcDeliveryDate ? dayjs(data.defaults.spcDeliveryDate, DATE_FORMAT) : null,
          spcAddPayCond: data.defaults?.spcAddPayCond ?? '',
          spcMontage: data.defaults?.spcMontage ?? false,
          spcPayAfterMontage: data.defaults?.spcPayAfterMontage ?? false,
          spcFaxCopy: data.defaults?.spcFaxCopy ?? false,
          spcOriginal: data.defaults?.spcOriginal ?? false,
          spcComment: data.defaults?.spcComment ?? '',
          spcLetter1Date: data.defaults?.spcLetter1Date ? dayjs(data.defaults.spcLetter1Date, DATE_FORMAT) : null,
          spcLetter2Date: data.defaults?.spcLetter2Date ? dayjs(data.defaults.spcLetter2Date, DATE_FORMAT) : null,
          spcLetter3Date: data.defaults?.spcLetter3Date ? dayjs(data.defaults.spcLetter3Date, DATE_FORMAT) : null,
          spcComplaintInCourtDate: data.defaults?.spcComplaintInCourtDate ? dayjs(data.defaults.spcComplaintInCourtDate, DATE_FORMAT) : null,
        });
      })
      .catch(() => message.error('Ошибка загрузки'))
      .finally(() => setLoading(false));
  }, [currencyName, form]);

  const handleFinish = useCallback(
    (values: Record<string, unknown>) => {
      setSaving(true);
      const body = {
        user: values.user ? { id: values.user, userFullName: '' } : null,
        spcNumber: values.spcNumber ?? '',
        spcDate: values.spcDate ? dayjs(values.spcDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
        spcSumm: values.spcSumm ?? '',
        spcSummNds: values.spcSummNds ?? '',
        spcDeliveryCond: values.spcDeliveryCond ?? '',
        deliveryTerm: values.deliveryTerm ? { id: values.deliveryTerm, name: '' } : null,
        spcAdditionalDaysCount: values.spcAdditionalDaysCount ?? '',
        spcDeliveryPercent: values.spcDeliveryPercent ?? '',
        spcDeliverySum: values.spcDeliverySum ?? '',
        spcDeliveryDate: values.spcDeliveryDate ? dayjs(values.spcDeliveryDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
        spcAddPayCond: values.spcAddPayCond ?? '',
        specificationPayments: payments.length ? payments : [{ percent: 100, delayDays: 0, currencyName }],
        spcMontage: values.spcMontage ?? false,
        spcPayAfterMontage: values.spcPayAfterMontage ?? false,
        spcFaxCopy: values.spcFaxCopy ?? false,
        spcOriginal: values.spcOriginal ?? false,
        spcComment: values.spcComment ?? '',
        spcLetter1Date: values.spcLetter1Date ? dayjs(values.spcLetter1Date as dayjs.Dayjs).format(DATE_FORMAT) : '',
        spcLetter2Date: values.spcLetter2Date ? dayjs(values.spcLetter2Date as dayjs.Dayjs).format(DATE_FORMAT) : '',
        spcLetter3Date: values.spcLetter3Date ? dayjs(values.spcLetter3Date as dayjs.Dayjs).format(DATE_FORMAT) : '',
        spcComplaintInCourtDate: values.spcComplaintInCourtDate ? dayjs(values.spcComplaintInCourtDate as dayjs.Dayjs).format(DATE_FORMAT) : '',
      };
      fetch(API_BASE + '/api/contracts/draft/specifications/save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json; charset=UTF-8' },
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
    [currencyName, navigate, payments]
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
      <Form form={form} layout="horizontal" labelCol={{ span: 6 }} wrapperCol={{ span: 18 }} onFinish={handleFinish} style={{ maxWidth: 680 }}>
        <Tabs
          defaultActiveKey="mainPanel"
          items={[
            {
              key: 'mainPanel',
              label: 'Главная',
              children: (
                <>
                  <Form.Item label="Ответственный" name="user">
                    <Select allowClear placeholder="Пользователь" options={openData.lookups.users.map((u) => ({ value: u.id, label: u.userFullName }))} style={{ width: 260 }} />
                  </Form.Item>
                  <Form.Item label="Номер" name="spcNumber" rules={[{ required: true, message: 'Введите номер' }]}>
                    <Input style={{ width: 200 }} maxLength={50} />
                  </Form.Item>
                  <Form.Item label="Дата" name="spcDate" rules={[{ required: true, message: 'Введите дату' }]}>
                    <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
                  </Form.Item>
                  <Form.Item label="Сумма" name="spcSumm" rules={[{ required: true, message: 'Введите сумму' }]}>
                    <Input style={{ width: 200 }} />
                  </Form.Item>
                  <Form.Item label="Сумма с НДС" name="spcSummNds">
                    <Input style={{ width: 200 }} />
                  </Form.Item>
                  <Form.Item label="Условия поставки" name="spcDeliveryCond">
                    <Input.TextArea rows={3} style={{ width: 500 }} maxLength={5000} />
                  </Form.Item>
                  <Form.Item label="Условие поставки" name="deliveryTerm" rules={[{ required: true, message: 'Выберите условие' }]}>
                    <Select allowClear placeholder="Условие поставки" options={openData.lookups.deliveryTerms.map((d) => ({ value: d.id, label: d.name }))} style={{ width: 260 }} />
                  </Form.Item>
                  <Form.Item label="Доп. дни" name="spcAdditionalDaysCount">
                    <Input style={{ width: 80 }} maxLength={3} />
                  </Form.Item>
                  <Form.Item label="Доп. условия оплаты" name="spcAddPayCond">
                    <Input.TextArea rows={3} style={{ width: 500 }} maxLength={5000} />
                  </Form.Item>
                  <Form.Item label="Платежи">
                    <Table
                      dataSource={payments.map((p, i) => ({ ...p, key: i }))}
                      columns={[
                        { title: '%', dataIndex: 'percent', key: 'percent', width: 80 },
                        { title: 'Дней', dataIndex: 'delayDays', key: 'delayDays', width: 80 },
                        { title: 'Валюта', dataIndex: 'currencyName', key: 'currencyName', width: 80 },
                      ]}
                      pagination={false}
                      size="small"
                    />
                  </Form.Item>
                  <Form.Item name="spcMontage" valuePropName="checked">
                    <Checkbox>Монтаж</Checkbox>
                  </Form.Item>
                  <Form.Item name="spcPayAfterMontage" valuePropName="checked">
                    <Checkbox>Оплата после монтажа</Checkbox>
                  </Form.Item>
                  <Form.Item name="spcFaxCopy" valuePropName="checked">
                    <Checkbox>Факсовая копия</Checkbox>
                  </Form.Item>
                  <Form.Item name="spcOriginal" valuePropName="checked">
                    <Checkbox>Оригинал</Checkbox>
                  </Form.Item>
                </>
              ),
            },
            {
              key: 'complaintSpecification',
              label: 'Претензии',
              children: (
                <>
                  <Form.Item label="Письмо 1 дата" name="spcLetter1Date">
                    <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
                  </Form.Item>
                  <Form.Item label="Письмо 2 дата" name="spcLetter2Date">
                    <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
                  </Form.Item>
                  <Form.Item label="Письмо 3 дата" name="spcLetter3Date">
                    <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
                  </Form.Item>
                  <Form.Item label="Дата претензии в суд" name="spcComplaintInCourtDate">
                    <DatePicker format={DATE_FORMAT} style={{ width: 140 }} />
                  </Form.Item>
                  <Form.Item label="Комментарий" name="spcComment">
                    <Input.TextArea rows={5} style={{ width: 500 }} maxLength={5000} />
                  </Form.Item>
                </>
              ),
            },
          ]}
        />
        <Form.Item wrapperCol={{ offset: 6, span: 18 }} style={{ marginTop: 16 }}>
          <Space>
            <Button type="primary" htmlType="submit" loading={saving}>Сохранить</Button>
            <Button onClick={() => navigate('/contracts/new')}>Отмена</Button>
          </Space>
        </Form.Item>
      </Form>
    </Layout>
  );
}
