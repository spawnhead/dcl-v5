/**
 * N3a3 Draft attachments. docs/screens/contract_attachments.
 */
import { Button, Layout, List, message, Typography, Upload } from 'antd';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

export default function ContractAttachmentsPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [items, setItems] = useState<{ id: string; idx: string; originalFileName: string }[]>([]);

  const load = useCallback(() => {
    fetch(`${API_BASE}/api/contracts/draft/attachments`)
      .then((r) => r.json())
      .then((data) => setItems(data.items ?? []))
      .catch(() => setItems([]))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const handleDelete = useCallback(
    (id: string) => {
      fetch(`${API_BASE}/api/contracts/draft/attachments/${id}`, { method: 'DELETE' })
        .then(() => {
          message.success('Удалено');
          load();
        })
        .catch(() => message.error('Ошибка удаления'));
    },
    [load]
  );

  return (
    <Layout style={{ padding: 16 }}>
      <Typography.Title level={4}>Прикреплённые файлы</Typography.Title>
      <List
        loading={loading}
        dataSource={items}
        renderItem={(item) => (
          <List.Item
            actions={[<Button type="link" danger key="del" onClick={() => handleDelete(item.id)}>Удалить</Button>]}
          >
            <Typography.Text>{item.originalFileName}</Typography.Text>
          </List.Item>
        )}
        locale={{ emptyText: 'Нет файлов' }}
      />
      <Upload
        name="file"
        showUploadList={false}
        action={`${API_BASE}/api/contracts/draft/attachments/upload`}
        method="POST"
        onChange={(info) => {
          if (info.file.status === 'done') {
            message.success('Файл загружен');
            load();
          } else if (info.file.status === 'error') {
            message.error('Ошибка загрузки');
          }
        }}
      >
        <Button type="default" style={{ marginRight: 8 }}>Прикрепить файл</Button>
      </Upload>
      <Button type="default" onClick={() => navigate('/contracts/new')} style={{ marginTop: 16 }}>
        Назад
      </Button>
    </Layout>
  );
}
