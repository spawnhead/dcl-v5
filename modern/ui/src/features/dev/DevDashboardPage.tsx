/**
 * Development dashboard per DEV_DASHBOARD_SPEC: /dev route, blocks from /api/dev/status and /api/me.
 * Error: fixed skeleton, per-block messages, "Повторить" button. Optional: serverTime, dataMode CTA when EMPTY.
 */
import { Alert, Button, Card, Descriptions, Spin, Typography } from 'antd';
import { useQuery } from '@tanstack/react-query';
import type { DevStatusResponse, CurrentUserResponse } from './types';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

async function fetchDevStatus(): Promise<DevStatusResponse> {
  const res = await fetch(`${API_BASE}/api/dev/status`);
  if (!res.ok) throw new Error(`GET /api/dev/status failed: ${res.status}`);
  return res.json();
}

async function fetchMe(): Promise<CurrentUserResponse> {
  const res = await fetch(`${API_BASE}/api/me`);
  if (!res.ok) throw new Error(`GET /api/me failed: ${res.status}`);
  return res.json();
}

export default function DevDashboardPage() {
  const statusQuery = useQuery({
    queryKey: ['dev', 'status'],
    queryFn: fetchDevStatus,
    retry: false,
    refetchOnWindowFocus: false,
  });
  const meQuery = useQuery({
    queryKey: ['dev', 'me'],
    queryFn: fetchMe,
    retry: false,
    refetchOnWindowFocus: false,
  });

  const refetch = () => {
    statusQuery.refetch();
    meQuery.refetch();
  };

  const statusError = statusQuery.isError;
  const meError = meQuery.isError;
  const loading = statusQuery.isLoading || meQuery.isLoading;

  return (
    <div style={{ padding: 24 }}>
      <Typography.Title level={3}>Development</Typography.Title>
      <Button type="primary" onClick={refetch} style={{ marginBottom: 16 }}>
        Повторить
      </Button>

      {/* Block 1: Active profile */}
      <Card title="Active profile" style={{ marginBottom: 16 }}>
        {statusError && !statusQuery.data && (
          <Alert type="error" message="Backend недоступен (GET /api/dev/status failed)" showIcon />
        )}
        {!statusError && statusQuery.data && (
          <Typography.Text>{statusQuery.data.profile ?? '—'}</Typography.Text>
        )}
        {!statusQuery.data && !statusError && loading && <Spin size="small" />}
      </Card>

      {/* Block 2: Java version */}
      <Card title="Java version" style={{ marginBottom: 16 }}>
        {statusError && !statusQuery.data && (
          <Alert type="error" message="Недоступно (GET /api/dev/status failed)" showIcon />
        )}
        {!statusError && statusQuery.data && (
          <Typography.Text>{statusQuery.data.javaVersion ?? '—'}</Typography.Text>
        )}
        {!statusQuery.data && !statusError && loading && <Spin size="small" />}
      </Card>

      {/* Block 3: DB + server time */}
      <Card title="DB + server time" style={{ marginBottom: 16 }}>
        {statusError && !statusQuery.data && (
          <Alert type="error" message="Недоступно (GET /api/dev/status failed)" showIcon />
        )}
        {!statusError && statusQuery.data && (
          <Descriptions column={1} size="small">
            <Descriptions.Item label="DB">
              <Typography.Text type={statusQuery.data.db?.ok ? 'success' : 'danger'}>
                {statusQuery.data.db?.ok ? 'OK' : 'Ошибка'}
                {statusQuery.data.db?.error != null && ` — ${statusQuery.data.db.error}`}
              </Typography.Text>
            </Descriptions.Item>
            {statusQuery.data.db?.product != null && (
              <Descriptions.Item label="Product">{statusQuery.data.db.product}</Descriptions.Item>
            )}
            {statusQuery.data.db?.version != null && (
              <Descriptions.Item label="Version">{statusQuery.data.db.version}</Descriptions.Item>
            )}
            <Descriptions.Item label="Server time">{statusQuery.data.serverTime ?? '—'}</Descriptions.Item>
          </Descriptions>
        )}
        {!statusQuery.data && !statusError && loading && <Spin size="small" />}
      </Card>

      {/* Block 4: Flyway status */}
      <Card title="Flyway status" style={{ marginBottom: 16 }}>
        {statusError && !statusQuery.data && (
          <Alert type="error" message="Недоступно (GET /api/dev/status failed)" showIcon />
        )}
        {!statusError && statusQuery.data && (
          <Descriptions column={1} size="small">
            <Descriptions.Item label="OK">
              <Typography.Text type={statusQuery.data.flyway?.ok ? 'success' : 'danger'}>
                {statusQuery.data.flyway?.ok ? 'OK' : 'Ошибка'}
                {statusQuery.data.flyway?.error != null && ` — ${statusQuery.data.flyway.error}`}
              </Typography.Text>
            </Descriptions.Item>
            <Descriptions.Item label="Applied migrations">
              {statusQuery.data.flyway?.appliedMigrationsCount ?? '—'}
            </Descriptions.Item>
          </Descriptions>
        )}
        {!statusQuery.data && !statusError && loading && <Spin size="small" />}
      </Card>

      {/* Block 5: Data mode */}
      <Card title="Data mode" style={{ marginBottom: 16 }}>
        {statusError && !statusQuery.data && (
          <Alert type="error" message="Недоступно (GET /api/dev/status failed)" showIcon />
        )}
        {!statusError && statusQuery.data && (
          <>
            <Typography.Text strong>{statusQuery.data.dataMode}</Typography.Text>
            {statusQuery.data.dataMode === 'EMPTY' && (
              <div style={{ marginTop: 8 }}>
                <Alert
                  type="info"
                  message="Заполнить dev seed"
                  description="Запустите backend с профилем dev; Flyway применит миграции из db/dev (marker DCL_SETTING.DEV_SEED_VERSION)."
                  showIcon
                />
              </div>
            )}
          </>
        )}
        {!statusQuery.data && !statusError && loading && <Spin size="small" />}
      </Card>

      {/* Block 6: Current user */}
      <Card title="Current user">
        {meError && !meQuery.data && (
          <Alert type="warning" message="Current user недоступен (GET /api/me failed)" showIcon />
        )}
        {!meError && meQuery.data && (
          <Descriptions column={1} size="small">
            <Descriptions.Item label="ID">{meQuery.data.id}</Descriptions.Item>
            <Descriptions.Item label="Name">{meQuery.data.name}</Descriptions.Item>
            <Descriptions.Item label="Roles">{meQuery.data.roles?.join(', ') ?? '—'}</Descriptions.Item>
            <Descriptions.Item label="Department">
              {meQuery.data.department ? `${meQuery.data.department.id} — ${meQuery.data.department.name}` : '—'}
            </Descriptions.Item>
            <Descriptions.Item label="Chief department">{meQuery.data.chiefDepartment ? 'Да' : 'Нет'}</Descriptions.Item>
            {meQuery.data.authMode != null && (
              <Descriptions.Item label="Auth mode">{meQuery.data.authMode}</Descriptions.Item>
            )}
          </Descriptions>
        )}
        {!meQuery.data && !meError && loading && <Spin size="small" />}
      </Card>
    </div>
  );
}
