import { useQuery } from '@tanstack/react-query';
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
import { Layout, Typography } from 'antd';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import { AgGridShell } from '../../shared/ui/AgGridShell';
import apiClient from '../../api/client';

ModuleRegistry.registerModules([AllCommunityModule]);

const { Content } = Layout;

const columnDefs: ColDef[] = [
  { field: 'id', headerName: 'ID', width: 90 },
  { field: 'name', headerName: 'Country', flex: 1 },
  { field: 'createdAt', headerName: 'Created At', flex: 1 },
  { field: 'createdBy', headerName: 'Created By', width: 120 },
  { field: 'editedAt', headerName: 'Edited At', flex: 1 },
  { field: 'editedBy', headerName: 'Edited By', width: 120 }
];

export default function CountriesPage() {
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
    <Content className="app-content" style={{ padding: 24 }}>
      {error ? (
        <Typography.Text type="danger">Failed to load countries.</Typography.Text>
      ) : (
        <AgGridShell style={{ width: '100%', height: '100%', minHeight: 500 }}>
          <AgGridReact
            rowData={data ?? []}
            columnDefs={columnDefs}
            loading={isLoading}
            animateRows
          />
        </AgGridShell>
      )}
    </Content>
  );
}
