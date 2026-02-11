/**
 * N3a1 Contractor create (from Contract). CONTRACTS: docs/screens/contractor_create.
 * Open from Contract create → «Добавить». Return: redirect /contracts/new?newContractorId=…
 */
import { Button, Form, Input, Layout, message, Select, Space, Typography } from 'antd';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

interface OpenResponse {
  defaults: Record<string, unknown>;
  lookups: { countries: { id: string; name: string }[]; reputations: { id: string; name: string }[]; users: unknown[]; currencies: { id: string; name: string }[] };
  returnTo: string;
}

interface SaveResponse {
  ctrId: string;
  redirectTo: string;
  returnTo: string;
}

export default function ContractorCreatePage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [openData, setOpenData] = useState<OpenResponse | null>(null);

  const returnTo = searchParams.get('returnTo') ?? 'contract';

  useEffect(() => {
    fetch(`${API_BASE}/api/contractors/create/open?returnTo=${encodeURIComponent(returnTo)}`)
      .then((r) => r.json())
      .then((data: OpenResponse) => {
        setOpenData(data);
        form.setFieldsValue({
          ctrName: data.defaults?.ctrName ?? '',
          ctrFullName: data.defaults?.ctrFullName ?? '',
          country: (data.defaults?.country as { id?: string })?.id,
          reputation: (data.defaults?.reputation as { id?: string })?.id,
        });
      })
      .catch(() => message.error('Ошибка загрузки'))
      .finally(() => setLoading(false));
  }, [returnTo, form]);

  const handleFinish = useCallback(
    (values: Record<string, unknown>) => {
      setSaving(true);
      const body = {
        ctrName: values.ctrName ?? '',
        ctrFullName: values.ctrFullName ?? '',
        country: values.country ? { id: values.country, name: '' } : null,
        ctrIndex: '',
        ctrRegion: '',
        ctrPlace: '',
        ctrStreet: '',
        ctrBuilding: '',
        ctrAddInfo: '',
        ctrPhone: '',
        ctrFax: '',
        ctrEmail: '',
        ctrUnp: '',
        ctrOkpo: '',
        reputation: values.reputation ? { id: values.reputation, name: '' } : null,
        gridUsers: openData?.defaults?.gridUsers ?? [],
        gridAccounts: openData?.defaults?.gridAccounts ?? [],
        ctrBankProps: '',
        ctrComment: '',
        returnTo,
      };
      fetch(`${API_BASE}/api/contractors/create/save`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      })
        .then(async (r) => {
          const data: SaveResponse = await r.json();
          if (!r.ok) {
            const err = await r.json().catch(() => ({}));
            message.error(err?.error?.message ?? 'Ошибка валидации');
            return;
          }
          message.success('Контрагент сохранён');
          navigate(data.redirectTo || '/contracts/new');
        })
        .catch(() => message.error('Ошибка сохранения'))
        .finally(() => setSaving(false));
    },
    [navigate, openData, returnTo]
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
      <Typography.Title level={4}>Создать контрагента</Typography.Title>
      <Form form={form} layout="horizontal" labelCol={{ span: 6 }} wrapperCol={{ span: 18 }} onFinish={handleFinish} style={{ maxWidth: 560 }}>
        <Form.Item label="Наименование" name="ctrName" rules={[{ required: true, message: 'Введите наименование' }]}>
          <Input style={{ width: 320 }} />
        </Form.Item>
        <Form.Item label="Полное наименование" name="ctrFullName">
          <Input style={{ width: 320 }} />
        </Form.Item>
        <Form.Item label="Страна" name="country">
          <Select
            allowClear
            placeholder="Страна"
            options={openData.lookups.countries.map((c) => ({ value: c.id, label: c.name }))}
            style={{ width: 240 }}
          />
        </Form.Item>
        <Form.Item label="Репутация" name="reputation">
          <Select
            allowClear
            placeholder="Репутация"
            options={openData.lookups.reputations.map((r) => ({ value: r.id, label: r.name }))}
            style={{ width: 240 }}
          />
        </Form.Item>
        <Form.Item wrapperCol={{ offset: 6, span: 18 }}>
          <Space>
            <Button type="primary" htmlType="submit" loading={saving}>Сохранить</Button>
            <Button onClick={() => navigate('/contracts/new')}>Отмена</Button>
          </Space>
        </Form.Item>
      </Form>
    </Layout>
  );
}
