import { Layout, Menu, Typography } from 'antd';
import { useLocation, useNavigate, Outlet, Routes, Route } from 'react-router-dom';
import MarginPage from './features/margin/MarginPage';
import OrdersPage from './features/orders/OrdersPage';
import CountriesPage from './features/countries/CountriesPage';
import DevDashboardPage from './features/dev/DevDashboardPage';
import './App.css';

const { Header, Content } = Layout;

function AppLayout() {
  const navigate = useNavigate();
  const location = useLocation();

  const menuItems = [
    {
      key: 'ref',
      label: 'Справочники',
      children: [
        { key: '/', label: 'Страны' }
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
    { key: '/dev', label: 'Development' }
  ];

  const openKeys = location.pathname.startsWith('/reports') ? ['reports'] : location.pathname === '/dev' ? [] : location.pathname === '/orders' ? [] : ['ref'];

  return (
    <Layout className="app-layout">
      <Header className="app-header" style={{ display: 'flex', alignItems: 'center' }}>
        <Typography.Title level={4} className="app-title" style={{ margin: 0, marginRight: 24, color: '#fff' }}>
          DCL Modern
        </Typography.Title>
        <Menu
          theme="dark"
          mode="horizontal"
          selectedKeys={[location.pathname]}
          defaultOpenKeys={openKeys}
          items={menuItems}
          onClick={({ key }) => navigate(key)}
          style={{ flex: 1, minWidth: 0 }}
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
        <Route path="orders" element={<OrdersPage />} />
        <Route path="dev" element={<DevDashboardPage />} />
      </Route>
    </Routes>
  );
}
