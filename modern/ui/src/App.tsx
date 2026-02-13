import { Layout, Menu, Segmented, Typography } from 'antd';
import { useEffect } from 'react';
import { useLocation, useNavigate, Outlet, Routes, Route } from 'react-router-dom';
import { useTheme } from './shared/context/ThemeContext';
import { consumeFlash } from './shared/lib/feedback';
import MarginPage from './features/margin/MarginPage';
import OrdersPage from './features/orders/OrdersPage';
import OrderEditPage from './features/orders/OrderEditPage';
import ContractsPage from './features/contracts/ContractsPage';
import ContractCreatePage from './features/contracts/ContractCreatePage';
import ContractSpecCreatePage from './features/contracts/ContractSpecCreatePage';
import ContractAttachmentsPage from './features/contracts/ContractAttachmentsPage';
import ContractPlaceholderPage from './features/contracts/ContractPlaceholderPage';
import ContractorsPage from './features/contractors/ContractorsPage';
import ContractorCreatePage from './features/contractors/ContractorCreatePage';
import ContractorEditPage from './features/contractors/ContractorEditPage';
import CountriesPage from './features/countries/CountriesPage';
import CommercialProposalsPage from './features/commercialproposals/CommercialProposalsPage';
import DevDashboardPage from './features/dev/DevDashboardPage';
import './App.css';

const { Header, Content } = Layout;

function AppLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const { themeMode, setThemeMode } = useTheme();

  useEffect(() => {
    consumeFlash();
  }, [location.pathname]);

  const menuItems = [
    {
      key: 'ref',
      label: 'Справочники',
      children: [
        { key: '/', label: 'Страны' },
        { key: '/contractors', label: 'Контрагенты' }
      ]
    },
    {
      key: 'reports',
      label: 'Отчеты',
      children: [
        { key: '/reports/margin', label: 'Маржа' }
      ]
    },
    { key: '/orders', label: 'Заказы' },
    { key: '/commercial-proposals', label: 'Коммерческие предложения' },
    { key: '/contracts', label: 'Договора' },
    { key: '/dev', label: 'Development' }
  ];

  const openKeys = location.pathname.startsWith('/reports') ? ['reports'] : location.pathname === '/dev' ? [] : location.pathname.startsWith('/orders') ? [] : location.pathname.startsWith('/commercial-proposals') ? [] : location.pathname.startsWith('/contracts') ? [] : location.pathname.startsWith('/contractors') ? ['ref'] : ['ref'];

  return (
    <Layout className="app-layout">
      <Header className="app-header" style={{ display: 'flex', alignItems: 'center' }}>
        <Typography.Title level={4} className="app-title" style={{ margin: 0, marginRight: 24, color: '#fff' }}>
          DCL Modern
        </Typography.Title>
        <Menu
          theme="dark"
          mode="horizontal"
          selectedKeys={[location.pathname.startsWith('/contracts') ? '/contracts' : location.pathname.startsWith('/commercial-proposals') ? '/commercial-proposals' : location.pathname.startsWith('/contractors') ? '/contractors' : location.pathname.startsWith('/orders') ? '/orders' : location.pathname]}
          defaultOpenKeys={openKeys}
          items={menuItems}
          onClick={({ key }) => navigate(key)}
          style={{ flex: 1, minWidth: 0 }}
        />
        <Segmented
          value={themeMode}
          onChange={(v) => setThemeMode(v as 'light' | 'dark')}
          options={[
            { label: 'Светлая', value: 'light' },
            { label: 'Тёмная', value: 'dark' }
          ]}
          size="small"
        />
      </Header>
      <Content className="app-content" style={{ padding: 0 }}>
        <Outlet />
      </Content>
    </Layout>
  );
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<AppLayout />}>
        <Route index element={<CountriesPage />} />
        <Route path="reports/margin" element={<MarginPage />} />
        <Route path="orders/new" element={<OrderEditPage />} />
        <Route path="orders/:id/edit" element={<OrderEditPage />} />
        <Route path="orders" element={<OrdersPage />} />
        <Route path="commercial-proposals" element={<CommercialProposalsPage />} />
        <Route path="commercial-proposals/:id/edit" element={<ContractPlaceholderPage title="Редактирование КП" />} />
        <Route path="contracts/new" element={<ContractCreatePage />} />
        <Route path="contracts/draft/specifications/new" element={<ContractSpecCreatePage />} />
        <Route path="contracts/draft/attachments" element={<ContractAttachmentsPage />} />
        <Route path="contracts/import-cp" element={<ContractPlaceholderPage title="Импорт из КП" />} />
        <Route path="contracts" element={<ContractsPage />} />
        <Route path="contractors/new" element={<ContractorCreatePage />} />
        <Route path="contractors/:id/edit" element={<ContractorEditPage />} />
        <Route path="contractors" element={<ContractorsPage />} />
        <Route path="dev" element={<DevDashboardPage />} />
      </Route>
    </Routes>
  );
}
