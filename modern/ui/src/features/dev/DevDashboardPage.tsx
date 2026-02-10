/**
 * Development dashboard: backend status, Flyway, dataMode, current user.
 * Visible at /dev. Uses /api/dev/status and /api/me (dev profile only).
 */
import { Alert, Card, Descriptions, Spin, Typography } from 'antd';
import { useQuery } from '@tanstack/react-query';
import type { DevStatusResponse, CurrentUserResponse } from './types';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

async function fetchDevStatus(): Promise<DevStatusResponse> {
  const res = await fetch(`${API_BASE}/api/dev/status`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

async function fetchMe(): Promise<CurrentUserResponse> {
  const res = await fetch(`${API_BASE}/api/me`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
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

  const statusError = statusQuery.isError;
  const meError = meQuery.isError;
  const loading = statusQuery.isLoading || meQuery.isLoading;

  return (
    <div style={{ padding: 24 }}>
      <Typography.Title level={3}>Development</Typography.Title>

      {statusError && (
        <Alert
          type="error"
          message="Backend недоступен"
          description="Проверьте, что backend запущен с профилем dev (port 8080) и перезагрузите страницу."
          showIcon
          style={{ marginBottom: 16 }}
        />
      )}

      {loading && <Spin size="large" />}

      {!loading && statusQuery.data && (
        <Card title="Статус окружения" style={{ marginBottom: 16 }}>
          <Descriptions column={1} size="small">
            <Descriptions.Item label="Профиль">{statusQuery.data.activeProfiles?.join(', ') ?? '—'}</Descriptions.Item>
            <Descriptions.Item label="Приложение">{statusQuery.data.appName} {statusQuery.data.version}</Descriptions.Item>
            <Descriptions.Item label="Java">{statusQuery.data.javaVersion}</Descriptions.Item>
            <Descriptions.Item label="БД">
              {statusQuery.data.db?.ok ? 'OK' : 'Ошибка'}
              {statusQuery.data.db?.now != null && ` (now: ${statusQuery.data.db.now})`}
              {statusQuery.data.db?.error != null && ` — ${statusQuery.data.db.error}`}
            </Descriptions.Item>
            <Descriptions.Item label="URL БД">{statusQuery.data.db?.url ?? '—'}</Descriptions.Item>
            <Descriptions.Item label="Flyway">
              {statusQuery.data.flyway?.ok ? 'OK' : 'Ошибка'}
              {statusQuery.data.flyway?.migrationsAppliedCount != null && ` (миграций: ${statusQuery.data.flyway.migrationsAppliedCount})`}
              {statusQuery.data.flyway?.currentVersion != null && `, версия: ${statusQuery.data.flyway.currentVersion}`}
              {statusQuery.data.flyway?.error != null && ` — ${statusQuery.data.flyway.error}`}
            </Descriptions.Item>
            <Descriptions.Item label="Режим данных">{statusQuery.data.dataMode}</Descriptions.Item>
            <Descriptions.Item label="Режим авторизации">{statusQuery.data.authMode}</Descriptions.Item>
          </Descriptions>
        </Card>
      )}

      {!loading && (meError ? (
        <Card title="Текущий пользователь">
          <Alert type="warning" message="Не удалось загрузить /api/me (возможно, backend без профиля dev)." />
        </Card>
      ) : meQuery.data && (
        <Card title="Текущий пользователь">
          <Descriptions column={1} size="small">
            <Descriptions.Item label="ID">{meQuery.data.id}</Descriptions.Item>
            <Descriptions.Item label="Username">{meQuery.data.username}</Descriptions.Item>
            <Descriptions.Item label="Display name">{meQuery.data.displayName}</Descriptions.Item>
            <Descriptions.Item label="Роли">{meQuery.data.roles?.join(', ') ?? '—'}</Descriptions.Item>
          </Descriptions>
        </Card>
      ))}
    </div>
  );
}
