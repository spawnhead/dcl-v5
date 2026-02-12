import React from 'react';
import { Table, Empty } from 'antd';
import { FileTextOutlined } from '@ant-design/icons';
import type { ColumnsType } from 'antd/es/table';

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

const columns: ColumnsType<SpecRow> = [
  {
    title: 'Номер',
    dataIndex: 'spcNumber',
    key: 'spcNumber',
    width: 120,
  },
  {
    title: 'Дата',
    dataIndex: 'spcDate',
    key: 'spcDate',
    width: 120,
  },
  {
    title: 'Сумма',
    dataIndex: 'spcSummFormatted',
    key: 'spcSummFormatted',
    align: 'right',
    width: 130,
    render: (value) => value?.toLocaleString('ru-RU', { minimumFractionDigits: 2 }),
  },
  {
    title: 'НДС %',
    dataIndex: 'spcNdsRateFormatted',
    key: 'spcNdsRateFormatted',
    align: 'right',
    width: 100,
  },
  {
    title: 'Сумма с НДС',
    dataIndex: 'spcSummNdsFormatted',
    key: 'spcSummNdsFormatted',
    align: 'right',
    width: 150,
    render: (value) => value?.toLocaleString('ru-RU', { minimumFractionDigits: 2 }),
  },
  {
    title: 'Остаток',
    dataIndex: 'spcRemainder',
    key: 'spcRemainder',
    align: 'right',
    width: 130,
    render: (value) => value?.toLocaleString('ru-RU', { minimumFractionDigits: 2 }),
  },
  {
    title: 'Исполнено',
    dataIndex: 'spcExecuted',
    key: 'spcExecuted',
    align: 'right',
    width: 100,
  },
];

interface SpecificationsTableProps {
  data: SpecRow[];
}

export function SpecificationsTable({ data }: SpecificationsTableProps) {
  return (
    <Table
      columns={columns}
      dataSource={data}
      pagination={false}
      bordered
      locale={{
        emptyText: (
          <Empty
            image={<FileTextOutlined style={{ fontSize: 48, color: '#bfbfbf' }} />}
            description={
              <div>
                <div>Нет спецификаций</div>
                <div style={{ fontSize: 12, color: '#8c8c8c', marginTop: 4 }}>
                  Нажмите "Создать спецификацию" чтобы добавить
                </div>
              </div>
            }
          />
        ),
      }}
    />
  );
}