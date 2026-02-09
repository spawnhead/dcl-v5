import { useQuery } from '@tanstack/react-query';
import { ColDef } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Layout, Typography } from 'antd';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import apiClient from './api/client';
import './App.css';

const { Header, Content } = Layout;

const columnDefs: ColDef[] = [
  { field: 'id', headerName: 'ID', width: 90 },
  { field: 'name', headerName: 'Country', flex: 1 },
  { field: 'createdAt', headerName: 'Created At', flex: 1 },
  { field: 'createdBy', headerName: 'Created By', width: 120 },
  { field: 'editedAt', headerName: 'Edited At', flex: 1 },
  { field: 'editedBy', headerName: 'Edited By', width: 120 }
];

export default function App() {
  const { data, isLoading, error } = useQuery({
    queryKey: ['countries'],
    queryFn: async () => {
      const response = await apiClient.GET('/api/countries');
      if (response.error) {
        throw response.error;
      }
      return response.data ?? [];
    }
  });

  return (
    <Layout className="app-layout">
      <Header className="app-header">
        <Typography.Title level={3} className="app-title">
          Countries (Reference Data)
        </Typography.Title>
      </Header>
      <Content className="app-content">
        {error ? (
          <Typography.Text type="danger">Failed to load countries.</Typography.Text>
        ) : (
          <div className="ag-theme-quartz app-grid">
            <AgGridReact
              rowData={data}
              columnDefs={columnDefs}
              loading={isLoading}
              animateRows
            />
          </div>
        )}
      </Content>
    </Layout>
  );
}
