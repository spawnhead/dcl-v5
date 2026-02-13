import React, { useState } from 'react';
import { ConfigProvider, Layout, Menu, Button, Space, Typography, theme } from 'antd';
import ruRU from 'antd/locale/ru_RU';
import dayjs from 'dayjs';
import 'dayjs/locale/ru';
import { SunOutlined, MoonOutlined } from '@ant-design/icons';
import CounterpartiesPage from './components/CounterpartiesPage';
import OrdersRegistryPage from './components/OrdersRegistryPage';
import OrderPage from './components/OrderPage';
import MarginReportPage from './components/MarginReportPage';

dayjs.locale('ru');

const { Header, Content } = Layout;
const { Text } = Typography;

const App: React.FC = () => {
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [currentKey, setCurrentKey] = useState('orders2'); // Set default to the new page

  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
  };

  const menuItems = [
    { key: 'directories', label: 'Справочники' },
    { key: 'reports', label: 'Отчеты' },
    { key: 'orders', label: 'Заказы' },
    { key: 'orders2', label: 'Заказы 2' },
    { key: 'contracts', label: 'Договора' },
    { key: 'development', label: 'Development' },
  ];

  const renderContent = () => {
    if (currentKey === 'reports') return <MarginReportPage />;
    if (currentKey === 'orders') return <OrderPage />;
    if (currentKey === 'orders2') return <OrdersRegistryPage />;
    return <CounterpartiesPage />;
  };

  return (
    <ConfigProvider
      locale={ruRU}
      theme={{
        algorithm: isDarkMode ? theme.darkAlgorithm : theme.defaultAlgorithm,
        token: {
          colorPrimary: '#1677ff',
          borderRadius: 6,
        },
      }}
    >
      <Layout style={{ minHeight: '100vh' }}>
        <Header style={{ 
          display: 'flex', 
          alignItems: 'center', 
          justifyContent: 'space-between',
          padding: '0 24px',
          background: '#001529', // Always dark header
        }}>
           <div style={{ display: 'flex', alignItems: 'center' }}>
             <Menu
                theme="dark"
                mode="horizontal"
                selectedKeys={[currentKey]}
                onClick={(e) => setCurrentKey(e.key)}
                items={menuItems}
                style={{ 
                  minWidth: 400, 
                  background: 'transparent',
                  borderBottom: 'none'
                }}
             />
           </div>
           
           <Space>
             <Space style={{ color: 'white', marginRight: 16 }}>
               <Text style={{ color: 'rgba(255,255,255,0.65)' }}>Светлая</Text>
               <Button 
                 type="text" 
                 icon={isDarkMode ? <MoonOutlined style={{ color: 'white' }} /> : <SunOutlined style={{ color: 'white' }} />} 
                 onClick={toggleTheme}
               />
               <Text style={{ color: 'rgba(255,255,255,0.65)' }}>Тёмная</Text>
             </Space>
           </Space>
        </Header>
        
        <Content style={{ 
          padding: 0, 
          background: isDarkMode ? '#141414' : '#f0f2f5' 
        }}>
          {renderContent()}
        </Content>
      </Layout>
    </ConfigProvider>
  );
};

export default App;
