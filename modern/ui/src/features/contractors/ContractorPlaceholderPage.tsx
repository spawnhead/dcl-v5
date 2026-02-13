/**
 * Placeholder for contractor edit. Legacy: ContractorAction.do?dispatch=edit.
 */
import { Layout, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';

export default function ContractorPlaceholderPage() {
  const navigate = useNavigate();
  return (
    <Layout style={{ padding: 16 }}>
      <Typography.Title level={4}>Редактирование контрагента</Typography.Title>
      <Typography.Paragraph>В разработке</Typography.Paragraph>
      <Typography.Link onClick={() => navigate('/contractors')}>← Назад к списку контрагентов</Typography.Link>
    </Layout>
  );
}
