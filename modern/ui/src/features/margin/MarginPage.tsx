import { Alert, Typography } from 'antd';

/**
 * Margin report screen (Отчеты -> Маржа).
 * Parity implementation blocked: SNAPSHOT.md and payloads are missing.
 * See docs/screens/margin/IMPLEMENTATION_NOTES.md.
 */
export default function MarginPage() {
  return (
    <div style={{ padding: 24 }}>
      <Typography.Title level={3}>Отчеты → Маржа</Typography.Title>
      <Alert
        type="info"
        showIcon
        message="Спецификация экрана отсутствует"
        description={
          <>
            Для реализации паритета 1:1 нужны документы, подготавливаемые Agent-Plan:
            <ul style={{ marginBottom: 0, marginTop: 8 }}>
              <li><code>docs/screens/margin/SNAPSHOT.md</code> — описание экрана и чеклист</li>
              <li><code>docs/screens/margin/payloads/*.json</code> — контракты запросов/ответов (lookups, grid-fetch, export)</li>
            </ul>
            После добавления спецификации будет реализованы backend (margin module) и UI (фильтры, таблица, пагинация, экспорт).
            <br />
            <strong>Статус:</strong> см. <code>docs/screens/margin/IMPLEMENTATION_NOTES.md</code>
          </>
        }
      />
    </div>
  );
}
