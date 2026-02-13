import React, { useState } from 'react';
import {
  Form,
  Input,
  Select,
  DatePicker,
  Checkbox,
  Button,
  Card,
  Row,
  Col,
  Table,
  Typography,
  Space,
  Tag,
  Tooltip,
  theme
} from 'antd';
import {
  FilterOutlined,
  ClearOutlined,
  ExclamationCircleOutlined,
  LockOutlined,
  MoreOutlined,
  CheckCircleFilled,
  CloseCircleFilled,
  ClockCircleFilled
} from '@ant-design/icons';
import { motion } from 'framer-motion';
import type { ColumnsType } from 'antd/es/table';
import dayjs from 'dayjs';

const { Title, Text } = Typography;
const { Option } = Select;

// --- Mock Data ---
interface OrderRegistryItem {
  key: string;
  number: string;
  date: string;
  contragent: string;
  sum: number;
  clientOrder: string;
  statusIcon: 'ok' | 'error' | 'warning';
  currentStatus: React.ReactNode;
  user: string;
  department: string;
  isLocked: boolean;
  hasAttention: boolean;
}

const initialData: OrderRegistryItem[] = [
  {
    key: '1',
    number: 'BYM-2512/0328-A',
    date: '18.12.2025',
    contragent: 'Baifero',
    sum: 4900.00,
    clientOrder: 'Агрокомбинат «Колос»',
    statusIcon: 'error',
    currentStatus: null,
    user: 'Ласицкий Алексей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
  {
    key: '2',
    number: 'BYM-2512/0335-A',
    date: '15.12.2025',
    contragent: 'Hefei Changyuan Hydraulic',
    sum: 37350.00,
    clientOrder: '',
    statusIcon: 'error',
    currentStatus: null,
    user: 'Вдовухин Андрей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
  {
    key: '3',
    number: 'BYM-2512/0333-A',
    date: '15.12.2025',
    contragent: 'Taifeng',
    sum: 8000.00,
    clientOrder: 'Амкодор-униар',
    statusIcon: 'error',
    currentStatus: null,
    user: 'Вдовухин Андрей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
  {
    key: '4',
    number: 'BYM-2512/0330-A',
    date: '15.12.2025',
    contragent: 'Yulin Pushen Hydraulic Components Manufacturing',
    sum: 4260.00,
    clientOrder: '',
    statusIcon: 'error',
    currentStatus: null,
    user: 'Вдовухин Андрей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
  {
    key: '5',
    number: 'BYM-2512/0326-A',
    date: '15.12.2025',
    contragent: 'Changzhou Weiki Hydraulic Co.,LTD.',
    sum: 24200.00,
    clientOrder: 'БелАЗ',
    statusIcon: 'error',
    currentStatus: null,
    user: 'Вдовухин Андрей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
  {
    key: '8',
    number: 'BYM-2511/0312-A',
    date: '12.12.2025',
    contragent: 'QINGDAO WEITAI HYDRAULIC',
    sum: 2200.00,
    clientOrder: 'Евромаш, ООО',
    statusIcon: 'ok',
    currentStatus: (
      <Space direction="vertical" size={0} style={{ fontSize: 12 }}>
        <Text>Передан на производство 08.12.2025 Не получен ответ от производителя в течение 87 дней.</Text>
        <Text type="secondary">(см. комментарий)</Text>
      </Space>
    ),
    user: 'Вдовухин Андрей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
  {
    key: '10',
    number: 'BYM-2512/0327-A',
    date: '10.12.2025',
    contragent: 'Baifero',
    sum: 5900.00,
    clientOrder: 'Юлайн',
    statusIcon: 'ok',
    currentStatus: (
      <Text style={{ fontSize: 12 }}>
        Получено подтверждение № 11.12.2025 Срок изготовления <Text type="danger" strong>16.01.2026</Text>
      </Text>
    ),
    user: 'Ласицкий Алексей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
   {
    key: '11',
    number: 'BYM-2511/0317-A',
    date: '09.12.2025',
    contragent: 'QINGDAO WEITAI HYDRAULIC',
    sum: 44000.00,
    clientOrder: 'Евромаш, ООО',
    statusIcon: 'ok',
    currentStatus: (
      <Space direction="vertical" size={0} style={{ fontSize: 12 }}>
         <Text>Получено подтверждение №2025352 05.12.2025 Срок изготовления <Text type="danger" strong>05.01.2026</Text></Text>
         <Text type="secondary">(см. комментарий)</Text>
      </Space>
    ),
    user: 'Вдовухин Андрей',
    department: 'Гидравлика',
    isLocked: false,
    hasAttention: true,
  },
];

const OrdersRegistryPage: React.FC = () => {
  const { token } = theme.useToken();
  const [form] = Form.useForm();

  // Columns
  const columns: ColumnsType<OrderRegistryItem> = [
    {
      title: 'Номер',
      dataIndex: 'number',
      key: 'number',
      width: 150,
      render: (text) => <a href="#">{text}</a>,
    },
    {
      title: 'Дата',
      dataIndex: 'date',
      key: 'date',
      width: 100,
    },
    {
      title: 'Контрагент',
      dataIndex: 'contragent',
      key: 'contragent',
      width: 200,
      ellipsis: true,
    },
    {
      title: 'Сумма',
      dataIndex: 'sum',
      key: 'sum',
      align: 'right',
      width: 120,
      render: (val) => val ? val.toLocaleString('ru-RU', { minimumFractionDigits: 2 }) : '',
    },
    {
      title: 'Заказ для клиента',
      dataIndex: 'clientOrder',
      key: 'clientOrder',
      width: 200,
      ellipsis: true,
    },
    {
      title: '',
      dataIndex: 'statusIcon',
      key: 'statusIcon',
      width: 40,
      align: 'center',
      render: (type) => {
        if (type === 'ok') return <CheckCircleFilled style={{ color: token.colorSuccess }} />;
        if (type === 'error') return <CloseCircleFilled style={{ color: token.colorError }} />;
        return <ClockCircleFilled style={{ color: token.colorWarning }} />;
      }
    },
    {
      title: 'Текущее состояние',
      dataIndex: 'currentStatus',
      key: 'currentStatus',
      width: 350,
    },
    {
      title: '',
      dataIndex: 'hasAttention',
      key: 'attention',
      width: 30,
      render: (has) => has ? <ExclamationCircleOutlined style={{ color: token.colorError }} /> : null,
    },
    {
      title: 'Пользователь',
      dataIndex: 'user',
      key: 'user',
      width: 150,
    },
    {
      title: 'Отдел',
      dataIndex: 'department',
      key: 'department',
      width: 120,
    },
    {
      title: <LockOutlined />,
      dataIndex: 'isLocked',
      key: 'isLocked',
      width: 40,
      align: 'center',
      render: (locked) => locked ? <LockOutlined /> : null,
    },
    {
      title: <Checkbox />,
      key: 'selection',
      width: 40,
      align: 'center',
      render: () => <Checkbox />,
    },
    {
      title: '',
      key: 'actions',
      width: 40,
      align: 'center',
      render: () => <Button type="text" size="small" icon={<MoreOutlined />} />,
    },
  ];

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
      style={{ padding: '24px', minHeight: 'calc(100vh - 64px)' }}
    >
      <div style={{ marginBottom: 16 }}>
        <Title level={4} style={{ margin: 0 }}>Заказы</Title>
      </div>

      {/* --- Filter Section --- */}
      <Card variant="borderless" style={{ marginBottom: 24, borderRadius: 8 }}>
        <Form form={form} layout="vertical">
          <Row gutter={24}>
            {/* Column 1 */}
            <Col xs={24} md={12} lg={6}>
              <Form.Item label="Номер" name="number" style={{ marginBottom: 12 }}>
                <Input placeholder="Введите номер" />
              </Form.Item>
              <Form.Item label="Пользователь" name="user" style={{ marginBottom: 12 }}>
                <Select placeholder="Все" allowClear>
                  <Option value="user1">User 1</Option>
                </Select>
              </Form.Item>
              <Form.Item label="Отдел" name="department" style={{ marginBottom: 12 }}>
                <Select placeholder="Все" allowClear>
                  <Option value="dept1">Dept 1</Option>
                </Select>
              </Form.Item>
              <Space direction="vertical" style={{ marginTop: 8 }}>
                <Checkbox defaultChecked>Исполненные заказы</Checkbox>
                <Checkbox defaultChecked>Неисполненные заказы</Checkbox>
              </Space>
            </Col>

            {/* Column 2 */}
            <Col xs={24} md={12} lg={6}>
              <Form.Item label="Контрагент" name="contragent" style={{ marginBottom: 12 }}>
                <Select placeholder="Выберите..." allowClear showSearch />
              </Form.Item>
              <Form.Item label="Производитель (продукт)" name="manufacturer" style={{ marginBottom: 12 }}>
                <Select placeholder="Выберите..." allowClear showSearch />
              </Form.Item>
              <Form.Item label="Продукция заказывается (кем?)" name="orderedBy" style={{ marginBottom: 12 }}>
                <Select placeholder="Выберите..." allowClear />
              </Form.Item>
            </Col>

            {/* Column 3 */}
            <Col xs={24} md={12} lg={6}>
              <Form.Item label="Клиент" name="client" style={{ marginBottom: 12 }}>
                <Select placeholder="Выберите..." allowClear showSearch />
              </Form.Item>
              <Form.Item label="Договор" name="contract" style={{ marginBottom: 12 }}>
                <Select placeholder="Выберите..." allowClear />
              </Form.Item>
              <Form.Item label="Спецификация" name="specification" style={{ marginBottom: 12 }}>
                <Select placeholder="Выберите..." allowClear />
              </Form.Item>
              <Space direction="vertical" style={{ marginTop: 8 }}>
                 <Checkbox>Готовые к отгрузке у производителя</Checkbox>
                 <Checkbox defaultChecked>Не отображать аннулированные</Checkbox>
              </Space>
            </Col>

            {/* Column 4: Ranges */}
            <Col xs={24} md={12} lg={6}>
              <Form.Item label="Дата" style={{ marginBottom: 12 }}>
                 <Space.Compact block>
                   <DatePicker placeholder="с" style={{ width: '50%' }} />
                   <DatePicker placeholder="по" style={{ width: '50%' }} />
                 </Space.Compact>
              </Form.Item>
              <Form.Item label="Сумма" style={{ marginBottom: 12 }}>
                 <Space.Compact block>
                   <Input placeholder="от" style={{ width: '50%' }} />
                   <Input placeholder="до" style={{ width: '50%' }} />
                 </Space.Compact>
              </Form.Item>
            </Col>
          </Row>

          <Row style={{ marginTop: 24 }} gutter={[16, 16]}>
             <Col xs={24}>
               <Text strong style={{ display: 'block', marginBottom: 8 }}>Текущее состояние:</Text>
               <div style={{ display: 'flex', flexWrap: 'wrap', gap: '12px 24px', alignItems: 'center' }}>
                  <Checkbox>Передан на производство</Checkbox>
                  <Checkbox>Нет опыта (3)</Checkbox>
                  <Checkbox>Получено подтверждение</Checkbox>
                  <Space size={8}>
                     <Text>№ подтверждения</Text>
                     <Input style={{ width: 100 }} size="small" />
                  </Space>
                  <Checkbox>Риск нарушить срок поставки (!)</Checkbox>
                  <Checkbox>Выслано покупателю</Checkbox>
                  <Checkbox>Дальнейшее движение</Checkbox>
               </div>
             </Col>
          </Row>

          <Row justify="end" style={{ marginTop: 24 }} gutter={12}>
             <Col>
               <Button icon={<ClearOutlined />} onClick={() => form.resetFields()}>Очистить фильтр</Button>
             </Col>
             <Col>
               <Button type="primary" icon={<FilterOutlined />}>Применить фильтр</Button>
             </Col>
          </Row>
        </Form>
      </Card>

      {/* --- Table Section --- */}
      <Card variant="borderless" styles={{ body: { padding: 0 } }} style={{ borderRadius: 8, overflow: 'hidden' }}>
        <Table
          columns={columns}
          dataSource={initialData}
          pagination={{
             placement: 'bottomLeft',
             defaultPageSize: 15,
             showSizeChanger: true,
             showTotal: (total, range) => `${range[0]}-${range[1]} из ${total}`,
          }}
          size="middle"
          scroll={{ x: 1300 }}
          rowClassName={(record, index) => index % 2 === 0 ? 'table-row-light' : 'table-row-dark'}
        />
      </Card>
    </motion.div>
  );
};

export default OrdersRegistryPage;
