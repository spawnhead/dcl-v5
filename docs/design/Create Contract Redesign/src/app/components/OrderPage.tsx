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
  Divider,
  Space,
  Upload,
  theme,
  Tag,
  InputNumber,
  Radio
} from 'antd';
import {
  PlusOutlined,
  UploadOutlined,
  SaveOutlined,
  PrinterOutlined,
  ImportOutlined,
  DownloadOutlined,
  DeleteOutlined,
  FileTextOutlined,
  PaperClipOutlined
} from '@ant-design/icons';
import { motion } from 'framer-motion';
import type { ColumnsType } from 'antd/es/table';
import dayjs from 'dayjs';

const { Title, Text } = Typography;
const { TextArea } = Input;
const { Option } = Select;

// --- Mock Data for Table ---
interface OrderItem {
  key: string;
  no: number;
  name: string;
  type: string;
  mainParams: string;
  addParams: string;
  catalogNo: string;
  qty: number;
  qtyExecuted: number;
  price: number;
  sum: number;
  forClient: string;
  contract: string;
}

const initialItems: OrderItem[] = [
  {
    key: '1',
    no: 1,
    name: 'Гидроаккумулятор',
    type: 'FS5E2-401',
    mainParams: '',
    addParams: '',
    catalogNo: '',
    qty: 5.00,
    qtyExecuted: 0.00,
    price: 390.00,
    sum: 1950.00,
    forClient: 'ДОРЭЛЕКТРОМАШ',
    contract: 'BY/12303/0387-EGZ',
  }
];

const OrderPage: React.FC = () => {
  const { token } = theme.useToken();
  const [form] = Form.useForm();
  const [fileList, setFileList] = useState([
    { uid: '-1', name: 'подписание 0067_0969_0072.pdf', status: 'done', url: '#' },
    { uid: '-2', name: 'Гран-нова 0069.docx', status: 'done', url: '#' },
  ]);

  // Table Columns
  const columns: ColumnsType<OrderItem> = [
    { title: '№', dataIndex: 'no', width: 50, fixed: 'left' },
    { title: 'Наименование товара', dataIndex: 'name', width: 200, fixed: 'left' },
    { title: 'Тип', dataIndex: 'type', width: 120 },
    { title: 'Осн. параметры', dataIndex: 'mainParams', width: 150 },
    { title: 'Доп. параметры', dataIndex: 'addParams', width: 150 },
    { title: '№ по каталогу', dataIndex: 'catalogNo', width: 120 },
    { title: 'Кол-во', dataIndex: 'qty', width: 80, align: 'right' },
    { title: 'Кол-во исп.', dataIndex: 'qtyExecuted', width: 100, align: 'right' },
    { title: 'Цена нетто', dataIndex: 'price', width: 100, align: 'right', render: (val) => val.toFixed(2) },
    { title: 'Сумма', dataIndex: 'sum', width: 100, align: 'right', render: (val) => <b>{val.toFixed(2)}</b> },
    { title: 'Для клиента', dataIndex: 'forClient', width: 150 },
    { title: 'По договору', dataIndex: 'contract', width: 150 },
    { title: '', key: 'action', width: 50, fixed: 'right', render: () => <Button type="text" danger icon={<DeleteOutlined />} size="small" /> },
  ];

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
      style={{ padding: '24px', minHeight: 'calc(100vh - 64px)' }}
    >
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
        <Title level={4} style={{ margin: 0 }}>Заказ № 00001 <Tag color="blue">Новый</Tag></Title>
        <Space>
           <Text type="secondary">Текущая дата: {dayjs().format('DD.MM.YYYY')}</Text>
        </Space>
      </div>

      <Form form={form} layout="vertical" initialValues={{ 
        date: dayjs(), 
        currency: 'CNY', 
        netPriceCalc: 'calculated',
        warranty: false,
        oneSpec: false
      }}>
        
        {/* --- Section 1: Main Info & Counterparty --- */}
        <Row gutter={24}>
          <Col xs={24} xl={12}>
            <Card variant="borderless" title="Основные данные" style={{ height: '100%', marginBottom: 24 }}>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item label="Продукция заказывается (кем?)" name="orderedBy">
                    <Select placeholder="Выберите компанию" showSearch>
                      <Option value="lintera">ООО "Линтера ТехСервис"</Option>
                    </Select>
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="Дата" name="date">
                    <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="Язык бланка" name="language">
                     <Select defaultValue="en">
                       <Option value="en">ЛТС (Бланк заказа - EN)</Option>
                       <Option value="ru">ЛТС (Бланк заказа - RU)</Option>
                     </Select>
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item label="Касательно" name="regarding">
                    <Input defaultValue="order" />
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item label="Преамбула" name="preamble1">
                    <TextArea rows={3} defaultValue="Dear Mrs. Amy Qui" />
                  </Form.Item>
                </Col>
              </Row>
            </Card>
          </Col>

          <Col xs={24} xl={12}>
            <Card variant="borderless" title="Контрагент" style={{ height: '100%', marginBottom: 24 }}>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item label="Контрагент (куда)" name="contragent">
                    <Space.Compact style={{ width: '100%' }}>
                       <Select placeholder="Выберите контрагента" showSearch style={{ width: '100%' }} defaultValue="yulin">
                         <Option value="yulin">Yulin Fushan Hydraulic Components...</Option>
                       </Select>
                       <Button icon={<PlusOutlined />}>Добавить</Button>
                    </Space.Compact>
                  </Form.Item>
                </Col>
                <Col span={24}>
                  <Form.Item label="Контактное лицо (Получатель)" name="contactPerson">
                    <Space.Compact style={{ width: '100%' }}>
                       <Select placeholder="Выберите контакт" style={{ width: '100%' }} defaultValue="amy">
                         <Option value="amy">Amy Qui</Option>
                       </Select>
                       <Button icon={<PlusOutlined />}>Добавить</Button>
                    </Space.Compact>
                  </Form.Item>
                </Col>
                <Col span={24}>
                   <div style={{ background: token.colorFillAlter, padding: 12, borderRadius: 6, marginBottom: 16 }}>
                     <Space direction="vertical" size={2} style={{ width: '100%', fontSize: 13 }}>
                       <Text type="secondary">Тел.: (+0086)-18775071305</Text>
                       <Text type="secondary">Факс: (+0086)-775-2697966</Text>
                       <Text type="secondary">E-mail: fnyeya2011@163.com</Text>
                     </Space>
                   </div>
                </Col>
                <Col span={24}>
                   <Form.Item label="Текст подтверждения (Преамбула 2)" name="preamble2">
                     <TextArea readOnly rows={4} style={{ background: token.colorFillQuaternary, color: token.colorTextSecondary }} 
                       defaultValue={`Please accept our order and send us the order confirmation to the e-mail order@lintera.info. Please refer to our order number on all your correspondence.`} 
                     />
                   </Form.Item>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>

        {/* --- Section 2: Settings & Price --- */}
        <Card variant="borderless" style={{ marginBottom: 24 }}>
          <Row gutter={[24, 16]} align="middle">
             <Col xs={24} md={12}>
               <Space direction="vertical">
                 <Checkbox>Весь заказ под одну спецификацию</Checkbox>
                 <Space>
                   <Checkbox>По гарантии</Checkbox>
                   <Text type="secondary" style={{ fontSize: 12 }}>
                     (Галочку следует убрать если товар заказывается для разных клиентов)
                   </Text>
                 </Space>
               </Space>
             </Col>
             <Col xs={24} md={12}>
               <Form.Item label="Производитель (продукт)" name="manufacturer" style={{ marginBottom: 0 }}>
                 <Select placeholder="Fashan" allowClear>
                   <Option value="fashan">Fashan</Option>
                 </Select>
               </Form.Item>
               <Text type="secondary" style={{ fontSize: 11 }}>Чтобы поле стало активным, следует очистить табличную часть</Text>
             </Col>
             
             <Divider style={{ margin: '12px 0' }} />

             <Col xs={24} md={8}>
               <Form.Item label="Расчет цены нетто" name="netPriceCalc" style={{ marginBottom: 0 }}>
                 <Radio.Group>
                   <Radio value="not_calculated">Не рассчитывается</Radio>
                   <Radio value="calculated">Рассчитывается</Radio>
                 </Radio.Group>
               </Form.Item>
             </Col>
             <Col xs={24} md={8}>
                <Space>
                  <Form.Item label="Доп. скидка" name="discount" style={{ marginBottom: 0 }}>
                    <InputNumber formatter={value => `${value}%`} />
                  </Form.Item>
                  <Form.Item label="Включить НДС" name="includeVat" valuePropName="checked" style={{ marginBottom: 0 }}>
                    <Checkbox>по ставке 0%</Checkbox>
                  </Form.Item>
                </Space>
             </Col>
             <Col xs={24} md={8}>
               <Form.Item label="Валюта" name="currency" style={{ marginBottom: 0 }}>
                 <Select style={{ width: 120 }}>
                   <Option value="CNY">CNY</Option>
                   <Option value="USD">USD</Option>
                   <Option value="EUR">EUR</Option>
                 </Select>
               </Form.Item>
             </Col>
          </Row>
        </Card>

        {/* --- Section 3: Items Table --- */}
        <Card variant="borderless" title="Спецификация" style={{ marginBottom: 24 }} styles={{ body: { padding: 0 } }}>
          <div style={{ padding: '16px 24px', borderBottom: `1px solid ${token.colorBorderSecondary}`, display: 'flex', gap: 8, flexWrap: 'wrap' }}>
            <Button icon={<DownloadOutlined />}>Скачать шаблон</Button>
            <Button icon={<ImportOutlined />}>Импорт из КП</Button>
            <Button icon={<FileTextOutlined />}>Импорт из Excel</Button>
            <Button type="primary" icon={<PlusOutlined />}>Добавить позицию</Button>
          </div>
          <Table 
            dataSource={initialItems} 
            columns={columns} 
            pagination={false} 
            scroll={{ x: 1300 }}
            size="small"
            summary={(pageData) => {
              let totalSum = 0;
              pageData.forEach(({ sum }) => { totalSum += sum; });
              return (
                <Table.Summary fixed>
                  <Table.Summary.Row style={{ background: token.colorFillAlter }}>
                    <Table.Summary.Cell index={0} colSpan={9} align="right">
                      <Text strong>ИТОГО:</Text>
                    </Table.Summary.Cell>
                    <Table.Summary.Cell index={1} align="right">
                      <Text strong>{totalSum.toFixed(2)}</Text>
                    </Table.Summary.Cell>
                    <Table.Summary.Cell index={2} colSpan={3} />
                  </Table.Summary.Row>
                </Table.Summary>
              );
            }}
          />
        </Card>

        {/* --- Section 4: Terms --- */}
        <Row gutter={24}>
          <Col xs={24} lg={14}>
             <Card variant="borderless" title="Условия поставки и оплаты" style={{ height: '100%', marginBottom: 24 }}>
                <Row gutter={12}>
                  <Col span={12}>
                    <Form.Item label="Условие поставки" name="deliveryCondition">
                      <Select defaultValue="FCA">
                        <Option value="FCA">FCA (Инкотермс 2010)</Option>
                      </Select>
                    </Form.Item>
                  </Col>
                  <Col span={12}>
                    <Form.Item label="Адрес" name="deliveryAddress">
                       <Input defaultValue="Yulin City, China" />
                    </Form.Item>
                  </Col>
                  <Col span={14}>
                     <Form.Item label="Желаемая стоимость доставки (до)" name="deliveryCostTo">
                       <Input placeholder="например: BEL PAK..." />
                     </Form.Item>
                  </Col>
                  <Col span={10}>
                     <Form.Item label="Стоимость" name="deliveryCostValue">
                        <InputNumber addonAfter="CNY" style={{ width: '100%' }} defaultValue={0} />
                     </Form.Item>
                  </Col>
                  <Col span={24}>
                    <Form.Item label="Условие оплаты" name="paymentTerms">
                      <TextArea rows={2} defaultValue="100% in advance" />
                    </Form.Item>
                  </Col>
                  <Col span={24}>
                    <Form.Item label="Срок поставки" name="deliveryDeadline">
                      <Input defaultValue="30 calendar days after receipt 100% prepayment" />
                    </Form.Item>
                  </Col>
                </Row>
             </Card>
          </Col>
          <Col xs={24} lg={10}>
             <Card variant="borderless" title="Примечания" style={{ height: '100%', marginBottom: 24 }}>
                <Form.Item label="Примечания 1 (внутреннее)" name="notes1">
                   <TextArea rows={4} />
                </Form.Item>
                <Form.Item label="Примечание 2 (в настройках бланка)" name="notes2">
                   <div style={{ background: token.colorWarningBg, padding: 12, borderRadius: 6, border: `1px solid ${token.colorWarningBorder}` }}>
                     <Text type="secondary" style={{ fontSize: 12 }}>
                       In case of any questions concerning delivery, please contact our logistic department: Irina Jelissejenko...
                     </Text>
                   </div>
                </Form.Item>
             </Card>
          </Col>
        </Row>

        {/* --- Section 5: Signatories --- */}
        <Card variant="borderless" title="Подписанты" style={{ marginBottom: 24 }}>
          <Row gutter={24}>
            <Col xs={24} md={12} lg={8}>
              <Form.Item label="Директор (Литва)" name="directorLt">
                 <Select placeholder="Не выбрано" />
              </Form.Item>
            </Col>
            <Col xs={24} md={12} lg={8}>
              <Form.Item label="Директор (РБ)" name="directorRb">
                 <Select defaultValue="pranovich">
                    <Option value="pranovich">Пранович Виталий</Option>
                 </Select>
              </Form.Item>
            </Col>
            <Col xs={24} md={12} lg={8}>
              <Form.Item label="Подпись начальника отдела" name="headDept">
                 <Select defaultValue="zabolotsky">
                    <Option value="zabolotsky">Заболоцкий Егор</Option>
                 </Select>
              </Form.Item>
            </Col>
            <Col xs={24} md={12} lg={8}>
              <Form.Item label="Подпись менеджера" name="manager">
                 <Select defaultValue="zabolotsky">
                    <Option value="zabolotsky">Заболоцкий Егор</Option>
                 </Select>
              </Form.Item>
            </Col>
          </Row>
        </Card>

        {/* --- Section 6: Logistics (Distinct Style) --- */}
        <Card 
          variant="borderless" 
          title={<Space><Text strong>Заполняется отделом логистики</Text><Tag color="orange">В работе</Tag></Space>} 
          style={{ marginBottom: 24, border: `1px solid ${token.colorBorder}` }}
          headStyle={{ background: token.colorFillAlter }}
        >
          <Row gutter={24}>
            <Col xs={24} md={12} lg={8}>
              <Form.Item label="Заказ передан на производство" name="log_toProduction">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" defaultValue={dayjs('2025-03-26')} />
              </Form.Item>
              <Form.Item label="Получено подтверждение заказа" name="log_confirmationReceived">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" defaultValue={dayjs('2025-03-25')} />
              </Form.Item>
              <Form.Item label="№ подтверждения" name="log_confirmNo">
                <Input defaultValue="0069" />
              </Form.Item>
              <Form.Item label="Срок изготовления" name="log_mfgDate">
                <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" defaultValue={dayjs('2025-03-26')} />
              </Form.Item>
            </Col>
            <Col xs={24} md={12} lg={8}>
               <Form.Item label="Тип отгрузочного документа" name="log_shipDocType">
                 <Select defaultValue="email"><Option value="email">E-Mail/Telefonat</Option></Select>
               </Form.Item>
               <Form.Item label="№ документа" name="log_docNo">
                 <Input defaultValue="б/н" />
               </Form.Item>
               <Form.Item label="Товар отгружен со склада" name="log_shippedFrom">
                 <DatePicker style={{ width: '100%' }} />
               </Form.Item>
               <Form.Item label="Планируемая дата прибытия" name="log_arrivalDate">
                 <DatePicker style={{ width: '100%' }} />
               </Form.Item>
            </Col>
            <Col xs={24} md={24} lg={8}>
               <Form.Item label="Комментарий логиста" name="log_comment">
                 <TextArea rows={8} defaultValue={`26.03. - просьба оплатить\n=====оплачено`} />
               </Form.Item>
            </Col>
          </Row>
        </Card>

        {/* --- Section 7: Files & Footer --- */}
        <Card variant="borderless" title="Прикрепленные файлы" style={{ marginBottom: 24 }}>
          <Upload fileList={fileList} onChange={({ fileList }) => setFileList(fileList as any)}>
            <Button icon={<PaperClipOutlined />}>Прикрепить файл</Button>
          </Upload>
          <Divider />
          <Form.Item label="Комментарий к заказу" name="orderComment">
            <TextArea rows={2} placeholder="Оставьте комментарий..." />
          </Form.Item>
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
            borderRadius: 0 
          }}
          styles={{ body: { padding: '16px 24px' } }}
        >
           <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: 16 }}>
             <Space>
               <Checkbox>Указывать единицы измерения</Checkbox>
               <Checkbox>Объединять одинаковые позиции</Checkbox>
             </Space>
             <Space wrap>
               <Button icon={<PrinterOutlined />}>Сопроводительное письмо</Button>
               <Button icon={<PrinterOutlined />}>Печать</Button>
               <Button>Отмена</Button>
               <Button type="primary" icon={<SaveOutlined />}>Сохранить</Button>
             </Space>
           </div>
        </Card>

      </Form>
    </motion.div>
  );
};

export default OrderPage;
