/**
 * Placeholder for contract create/edit or import screens.
 * Legacy: ContractAction.do?dispatch=input, SelectCPContractsAction.do?dispatch=input.
 */
import { Layout, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';

interface Props {
  title: string;
  message?: string;
}

export default function ContractPlaceholderPage({ title, message = 'В разработке' }: Props) {
  const navigate = useNavigate();
  return (
    <Layout style={{ padding: 16 }}>
      <Typography.Title level={4}>{title}</Typography.Title>
      <Typography.Paragraph>{message}</Typography.Paragraph>
      <Typography.Link onClick={() => navigate('/contracts')}>← Назад к списку договоров</Typography.Link>
    </Layout>
  );
}
