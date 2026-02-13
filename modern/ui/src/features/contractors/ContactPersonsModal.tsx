/**
 * Contact person add/edit modal. SNAPSHOT §4.4 gridContactPersons.
 * TASK-0067: Modal editor, required/email/maxlen, notifyError on validation fail.
 */
import { Badge, Checkbox, Form, Input, Modal } from 'antd';
import { useCallback, useEffect } from 'react';
import { notifyError } from '../../shared/lib/feedback';

export interface ContactPersonFormValues {
  cpsName: string;
  cpsPosition: string;
  cpsOnReason: string;
  cpsPhone: string;
  cpsMobPhone: string;
  cpsFax: string;
  cpsEmail: string;
  cpsContractComment: string;
  cpsFire: boolean;
  cpsBlock: boolean;
}

export interface ContactPersonRow {
  cpsName: string;
  cpsPosition: string;
  cpsOnReason: string;
  cpsPhone: string;
  cpsMobPhone: string;
  cpsFax: string;
  cpsEmail: string;
  cpsContractComment: string;
  cpsFire: string;
  cpsBlock: string;
}

function toFormValues(row: ContactPersonRow | null): ContactPersonFormValues {
  if (!row) {
    return {
      cpsName: '',
      cpsPosition: '',
      cpsOnReason: '',
      cpsPhone: '',
      cpsMobPhone: '',
      cpsFax: '',
      cpsEmail: '',
      cpsContractComment: '',
      cpsFire: false,
      cpsBlock: false,
    };
  }
  return {
    cpsName: row.cpsName ?? '',
    cpsPosition: row.cpsPosition ?? '',
    cpsOnReason: row.cpsOnReason ?? '',
    cpsPhone: row.cpsPhone ?? '',
    cpsMobPhone: row.cpsMobPhone ?? '',
    cpsFax: row.cpsFax ?? '',
    cpsEmail: row.cpsEmail ?? '',
    cpsContractComment: row.cpsContractComment ?? '',
    cpsFire: row.cpsFire === '1',
    cpsBlock: row.cpsBlock === '1',
  };
}

function toRow(values: ContactPersonFormValues): ContactPersonRow {
  return {
    cpsName: values.cpsName ?? '',
    cpsPosition: values.cpsPosition ?? '',
    cpsOnReason: values.cpsOnReason ?? '',
    cpsPhone: values.cpsPhone ?? '',
    cpsMobPhone: values.cpsMobPhone ?? '',
    cpsFax: values.cpsFax ?? '',
    cpsEmail: values.cpsEmail ?? '',
    cpsContractComment: values.cpsContractComment ?? '',
    cpsFire: values.cpsFire ? '1' : '0',
    cpsBlock: values.cpsBlock ? '1' : '0',
  };
}

interface ContactPersonsModalProps {
  open: boolean;
  editIndex: number | null;
  initialRow: ContactPersonRow | null;
  onSave: (row: ContactPersonRow, editIndex: number | null) => void;
  onCancel: () => void;
  /** When true, show admin marker (Badge) next to Block label. */
  adminRole?: boolean;
}

export function ContactPersonsModal({
  open,
  editIndex,
  initialRow,
  onSave,
  onCancel,
  adminRole = false,
}: ContactPersonsModalProps) {
  const [form] = Form.useForm<ContactPersonFormValues>();

  useEffect(() => {
    if (open) {
      form.setFieldsValue(toFormValues(initialRow));
    }
  }, [open, initialRow, form]);

  const formInitialValues = open ? toFormValues(initialRow) : undefined;
  const formKey = open ? `contact-form-${editIndex !== null ? editIndex : 'new'}` : 'contact-form-closed';

  const handleOk = useCallback(() => {
    form.validateFields().then((values) => {
      onSave(toRow(values), editIndex);
      form.resetFields();
      onCancel();
    }).catch(() => {
      notifyError('Заполните обязательные поля', 'Проверьте форму контактного лица');
    });
  }, [form, editIndex, onSave, onCancel]);

  const handleCancel = useCallback(() => {
    form.resetFields();
    onCancel();
  }, [form, onCancel]);

  const title = editIndex !== null ? 'Редактировать контактное лицо' : 'Добавить контактное лицо';

  return (
    <Modal
      title={title}
      open={open}
      onOk={handleOk}
      onCancel={handleCancel}
      okText="Сохранить"
      cancelText="Отмена"
      destroyOnHidden
      width={520}
    >
      <Form form={form} layout="vertical" preserve={false} key={formKey} initialValues={formInitialValues}>
        <Form.Item label="ФИО" name="cpsName" rules={[{ required: true, message: 'Введите ФИО' }]}>
          <Input maxLength={200} placeholder="ФИО" />
        </Form.Item>
        <Form.Item label="Должность" name="cpsPosition">
          <Input maxLength={150} placeholder="Должность" />
        </Form.Item>
        <Form.Item label="Основание" name="cpsOnReason">
          <Input maxLength={150} placeholder="Основание" />
        </Form.Item>
        <Form.Item label="Телефон" name="cpsPhone">
          <Input maxLength={150} placeholder="Телефон" />
        </Form.Item>
        <Form.Item label="Моб. телефон" name="cpsMobPhone">
          <Input maxLength={150} placeholder="Моб. телефон" />
        </Form.Item>
        <Form.Item label="Факс" name="cpsFax">
          <Input maxLength={150} placeholder="Факс" />
        </Form.Item>
        <Form.Item
          label="Email"
          name="cpsEmail"
          rules={[
            {
              validator: (_, v) => {
                if (!v || String(v).trim() === '') return Promise.resolve();
                const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return emailRe.test(String(v).trim()) ? Promise.resolve() : Promise.reject(new Error('Введите корректный email'));
              },
            },
          ]}
        >
          <Input maxLength={40} placeholder="Email" />
        </Form.Item>
        <Form.Item label="Комментарий" name="cpsContractComment">
          <Input.TextArea rows={2} maxLength={300} placeholder="Комментарий" />
        </Form.Item>
        <Form.Item name="cpsFire" valuePropName="checked">
          <Checkbox>Уволен</Checkbox>
        </Form.Item>
        <Form.Item
          name="cpsBlock"
          valuePropName="checked"
          label={adminRole ? <Badge status="error" text="Блок" /> : undefined}
        >
          <Checkbox>{adminRole ? '' : 'Блок'}</Checkbox>
        </Form.Item>
      </Form>
    </Modal>
  );
}
