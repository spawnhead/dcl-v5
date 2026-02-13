/**
 * Development dashboard per DEV_DASHBOARD_SPEC: /dev route, blocks from /api/dev/status and /api/me.
 * Data source: Live DB (Postgres) + Seed dataset indicator. No FAKE_SEEDED display.
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

      {/* Block 5: DB Source + Seed dataset (Postgres-only, no FAKE_SEEDED) */}
      <Card title="Data source" style={{ marginBottom: 16 }}>
        {statusError && !statusQuery.data && (
          <Alert type="error" message="Недоступно (GET /api/dev/status failed)" showIcon />
        )}
        {!statusError && statusQuery.data && (
          <Descriptions column={1} size="small">
            <Descriptions.Item label="DB Source">
              <Typography.Text type="success">
                Live DB (Postgres)
              </Typography.Text>
            </Descriptions.Item>
            <Descriptions.Item label="Seed dataset">
              {statusQuery.data.seedDataset ?? 'unknown'}
            </Descriptions.Item>
          </Descriptions>
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
