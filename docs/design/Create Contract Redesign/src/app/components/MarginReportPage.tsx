import React, { useState } from 'react';
import { 
  Button, 
  DatePicker, 
  Select, 
  Checkbox, 
  Card, 
  Form, 
  Row, 
  Col, 
  Typography, 
  Space, 
  Table, 
  Input, 
  Divider,
  theme
} from 'antd';
import { 
  ReloadOutlined, 
  SearchOutlined, 
  FileExcelOutlined,
  FilterOutlined,
  CloseOutlined
} from '@ant-design/icons';
import { motion } from 'framer-motion';
import type { ColumnsType } from 'antd/es/table';
import dayjs from 'dayjs';

const { Title, Text } = Typography;
const { RangePicker } = DatePicker;
const { Option } = Select;

const MarginReportPage: React.FC = () => {
  const { token } = theme.useToken();
  const [form] = Form.useForm();
  
  // State for column visibility
  const [visibleColumns, setVisibleColumns] = useState<string[]>([
    'contragent', 'country', 'contractNo', 'product', 'shipment', 
    'payment', 'transport', 'customs', 'logistics', 'installation', 
    'installTime', 'installCost', 'correction', 'purchaseAmount', 
    'coeff', 'user', 'department'
  ]);

  // Mock columns config
  const allColumnsList = [
    { key: 'contragent', label: 'Контрагент' },
    { key: 'country', label: 'Страна' },
    { key: 'contractNo', label: '№ контракта' },
    { key: 'product', label: 'Продукт' },
    { key: 'shipment', label: 'Отгрузка' },
    { key: 'payment', label: 'Оплата' },
    { key: 'transport', label: 'Транспорт' },
    { key: 'transportMinsk', label: 'Транспорт Минск-Клиент' },
    { key: 'customs', label: 'Таможенные' },
    { key: 'logistics', label: 'Логистика' },
    { key: 'installation', label: 'Монтаж' },
    { key: 'installTime', label: 'Время монтажа' },
    { key: 'installCost', label: 'Ст-ть монтажа' },
    { key: 'correction', label: 'Корректировка' },
    { key: 'purchaseAmount', label: 'Сумма закупки' },
    { key: 'coeff', label: 'Коэфф-т' },
    { key: 'user', label: 'Пользователь' },
    { key: 'department', label: 'Отдел' },
  ];

  // Helper to render filter input in header
  const renderHeader = (title: string, type: 'text' | 'date' | 'number' = 'text') => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
      <Text strong style={{ fontSize: 13 }}>{title}</Text>
      {type === 'date' ? (
        <DatePicker size="small" placeholder="ДД.ММ.ГГГГ" format="DD.MM.YYYY" style={{ width: '100%' }} />
      ) : (
        <Input size="small" prefix={type === 'text' ? null : null} placeholder="" />
      )}
    </div>
  );

  // Table Columns Definition
  const columns: ColumnsType<any> = [
    {
      title: renderHeader('Контрагент'),
      dataIndex: 'contragent',
      key: 'contragent',
      width: 150,
      hidden: !visibleColumns.includes('contragent'),
    },
    {
      title: renderHeader('Страна'),
      dataIndex: 'country',
      key: 'country',
      width: 100,
      hidden: !visibleColumns.includes('country'),
    },
    {
      title: renderHeader('№ контракта'),
      dataIndex: 'contractNo',
      key: 'contractNo',
      width: 120,
      hidden: !visibleColumns.includes('contractNo'),
    },
    {
      title: renderHeader('Дата контракта', 'date'),
      dataIndex: 'contractDate',
      key: 'contractDate',
      width: 130,
    },
    {
      title: renderHeader('№ спецификации'),
      dataIndex: 'specNo',
      key: 'specNo',
      width: 130,
    },
    {
      title: renderHeader('Дата спецификации', 'date'),
      dataIndex: 'specDate',
      key: 'specDate',
      width: 140,
    },
    {
      title: renderHeader('Сумма'),
      dataIndex: 'amount',
      key: 'amount',
      width: 100,
    },
    {
      title: renderHeader('Валюта'),
      dataIndex: 'currency',
      key: 'currency',
      width: 80,
    },
    {
      title: renderHeader('Продукт'),
      dataIndex: 'product',
      key: 'product',
      width: 200,
      hidden: !visibleColumns.includes('product'),
    },
  ].filter(col => !col.hidden);

  const FilterSelectGroup = ({ label, name }: { label: string, name: string }) => (
    <div style={{ display: 'flex', alignItems: 'center', gap: 8, flexWrap: 'nowrap' }}>
      <Text style={{ whiteSpace: 'nowrap' }}>{label}:</Text>
      <Select 
        defaultValue="all" 
        style={{ width: 120 }} 
        size="small"
        options={[
          { value: 'all', label: '- Все -' },
          { value: '1', label: 'Вариант 1' },
        ]}
      />
      <Checkbox style={{ fontSize: 12 }}>Аспект</Checkbox>
    </div>
  );

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
      style={{ padding: '24px', minHeight: 'calc(100vh - 64px)' }}
    >
      <div style={{ marginBottom: 16 }}>
        <Title level={4} style={{ margin: 0, fontWeight: 600, color: token.colorTextHeading }}>
          Отчеты <span style={{ color: token.colorTextSecondary }}>→</span> Маржа
        </Title>
      </div>

      <Card variant="borderless" style={{ marginBottom: 24, borderRadius: 8 }}>
        <Form form={form} layout="vertical">
          {/* Row 1: Date Range */}
          <div style={{ marginBottom: 16 }}>
            <Space align="center">
              <Text>Период с:</Text>
              <DatePicker placeholder="Select date" style={{ width: 140 }} />
              <Text>по:</Text>
              <DatePicker placeholder="Select date" style={{ width: 140 }} />
            </Space>
          </div>

          {/* Row 2: Selects + Aspect Checkboxes */}
          <Row gutter={[24, 12]} style={{ marginBottom: 16 }}>
            <Col><FilterSelectGroup label="Пользователь" name="user" /></Col>
            <Col><FilterSelectGroup label="Отдел" name="department" /></Col>
            <Col><FilterSelectGroup label="Контрагент" name="contragent" /></Col>
            <Col><FilterSelectGroup label="Категория" name="category" /></Col>
            <Col><FilterSelectGroup label="Маршрут" name="route" /></Col>
          </Row>

          {/* Row 3: Boolean Flags */}
          <div style={{ marginBottom: 16, display: 'flex', flexWrap: 'wrap', gap: '16px 24px' }}>
             <Checkbox>Выводить только итоги</Checkbox>
             <Checkbox>Выводить итоги по спецификациям договоров</Checkbox>
             <Checkbox>Разбивать итог спецификации на пользователей</Checkbox>
             <Checkbox>Разбивать итог пользователя на итоги по продуктам</Checkbox>
             <Checkbox>Включить в отчёт незаблокированные закрытия договоров</Checkbox>
          </div>

          {/* Row 4: Column Visibility */}
          <div style={{ 
            marginBottom: 24, 
            padding: '12px', 
            background: token.colorFillAlter, 
            borderRadius: 6,
            display: 'flex',
            alignItems: 'flex-start',
            gap: 12
          }}>
            <Text strong style={{ marginTop: 2 }}>Колонки:</Text>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px 16px' }}>
              {allColumnsList.map(col => (
                <Checkbox 
                  key={col.key}
                  checked={visibleColumns.includes(col.key)}
                  onChange={(e) => {
                    if (e.target.checked) {
                      setVisibleColumns([...visibleColumns, col.key]);
                    } else {
                      setVisibleColumns(visibleColumns.filter(k => k !== col.key));
                    }
                  }}
                  style={{ 
                    marginInlineStart: 0,
                    background: visibleColumns.includes(col.key) ? '#1677ff' : 'transparent',
                    color: visibleColumns.includes(col.key) ? '#fff' : 'inherit',
                    padding: '0 6px',
                    borderRadius: 4,
                    transition: 'all 0.2s'
                  }}
                >
                  {col.label}
                </Checkbox>
              ))}
            </div>
          </div>

          {/* Row 5: Action Buttons */}
          <Space>
            <Button>Сбросить всё</Button>
            <Button disabled>Сформировать</Button>
            <Button icon={<FileExcelOutlined />}>Excel</Button>
          </Space>
        </Form>
      </Card>

      {/* Toolbar */}
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: 16, alignItems: 'center', marginBottom: 16 }}>
        <Space>
          <Text>Грузить:</Text>
          <Select 
            defaultValue="200" 
            style={{ width: 80 }}
            options={[
              { value: '200', label: '200' },
              { value: '500', label: '500' },
            ]}
          />
        </Space>
        <Space>
          <Text>Показывать:</Text>
          <Select 
            defaultValue="50" 
            style={{ width: 80 }}
            options={[
              { value: '50', label: '50' },
              { value: '100', label: '100' },
            ]}
          />
        </Space>
        <Button icon={<ReloadOutlined />}>Обновить</Button>
        <Input placeholder="Поиск..." prefix={<SearchOutlined />} style={{ width: 200 }} />
        <Button>Сбросить фильтр</Button>
        <Button>Экспорт CSV</Button>
      </div>

      {/* Table */}
      <Card variant="borderless" styles={{ body: { padding: 0 } }} style={{ borderRadius: 8, overflow: 'hidden' }}>
        <Table
          columns={columns}
          dataSource={[]} // Empty data as per screenshot "No Rows To Show"
          bordered
          size="small"
          scroll={{ x: 1500 }}
          pagination={false}
          locale={{ emptyText: <div style={{ padding: 40, color: token.colorTextSecondary }}>No Rows To Show</div> }}
        />
      </Card>
    </motion.div>
  );
};

export default MarginReportPage;
