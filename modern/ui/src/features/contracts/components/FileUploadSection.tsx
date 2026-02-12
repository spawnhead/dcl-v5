import React from 'react';
import { List, Typography, Button } from 'antd';
import { FileOutlined, PaperClipOutlined } from '@ant-design/icons';

const { Text } = Typography;

interface AttachmentItem {
  id: string;
  idx: string;
  originalFileName: string;
  attCreateDate?: string;
}

interface FileUploadSectionProps {
  attachments: AttachmentItem[];
  onAttachClick: () => void;
}

export function FileUploadSection({ attachments, onAttachClick }: FileUploadSectionProps) {
  return (
    <div>
      {attachments.length === 0 ? (
        <div style={{ textAlign: 'center', padding: '40px 20px', border: '1px dashed #d9d9d9', borderRadius: 8 }}>
          <PaperClipOutlined style={{ fontSize: 48, color: '#bfbfbf' }} />
          <div style={{ marginTop: 16 }}>
            <Text>Нет прикреплённых файлов</Text>
          </div>
          <div style={{ fontSize: 12, color: '#8c8c8c', marginTop: 4 }}>
            Нажмите "Прикрепить" чтобы добавить документы
          </div>
        </div>
      ) : (
        <List
          dataSource={attachments}
          renderItem={(file) => (
            <List.Item>
              <List.Item.Meta
                avatar={<FileOutlined style={{ fontSize: 20, color: '#8c8c8c' }} />}
                title={file.originalFileName}
                description={file.attCreateDate ? `Добавлено: ${file.attCreateDate}` : undefined}
              />
            </List.Item>
          )}
          bordered
        />
      )}
      <Button
        type="default"
        icon={<PaperClipOutlined />}
        onClick={onAttachClick}
        style={{ marginTop: attachments.length > 0 ? 16 : 16 }}
      >
        Прикрепить
      </Button>
    </div>
  );
}