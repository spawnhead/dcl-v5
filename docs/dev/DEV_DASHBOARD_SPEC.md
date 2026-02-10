# DEV_DASHBOARD_SPEC

## 1) Route

- UI route: `/dev`
- Назначение: быстрый health+context экран для разработчика перед тестом фич (первично Margin).

## 2) Data sources (backend contract)

Страница делает 2 запроса:
1. `GET /api/dev/status`
2. `GET /api/me`

## 2.1 `/api/dev/status` response contract

```json
{
  "profile": "dev",
  "javaVersion": "21.0.6",
  "serverTime": "2026-02-10T10:15:30Z",
  "db": {
    "ok": true,
    "product": "PostgreSQL",
    "version": "16"
  },
  "flyway": {
    "ok": true,
    "appliedMigrationsCount": 12
  },
  "dataMode": "FAKE_SEEDED"
}
```

### `dataMode` rule
- `FAKE_SEEDED` — найден marker `DCL_SETTING.STN_NAME='DEV_SEED_VERSION'`.
- `REAL` — marker нет, но есть доменные записи (напр. `DCL_CONTRACT`/`DCL_CONTRACT_CLOSED` > 0).
- `EMPTY` — marker нет и доменных данных нет.

## 2.2 `/api/me` response contract

```json
{
  "id": "dev",
  "name": "dev",
  "roles": ["admin"],
  "department": { "id": "-1", "name": "Все" },
  "chiefDepartment": false,
  "authMode": "DEV_BYPASS"
}
```

## 3) UI blocks (обязательные)

1. **Active profile**
   - значение `profile`.
2. **Java version**
   - значение `javaVersion`.
3. **DB + server time**
   - `db.ok` как статус (green/red),
   - `serverTime` отдельной строкой.
4. **Flyway status**
   - `flyway.ok`,
   - `flyway.appliedMigrationsCount`.
5. **Data mode**
   - badge: `FAKE_SEEDED` / `REAL` / `EMPTY`.
6. **Current user**
   - блок из `/api/me`: `id`, `name`, `roles`, `department`, `chiefDepartment`.

## 4) Error behavior (не оставлять пустой UI)

Если backend недоступен/частично недоступен:
1. Показывать фиксированный каркас блоков (лейблы остаются).
2. В каждом проблемном блоке — понятный текст, например:
   - `Backend недоступен (GET /api/dev/status failed)`
   - `Current user недоступен (GET /api/me failed)`
3. Для успешно полученных блоков данные сохранять (partial success).
4. Добавить кнопку `Повторить` (повтор обоих запросов).

## 5) UX rules для быстрого цикла

1. Авто-refresh каждые 30 секунд (можно отключить toggle).
2. `serverTime` и timestamp последнего обновления обязательно видны.
3. Если `dataMode=EMPTY`, показывать CTA: `Заполнить dev seed` (ссылка на команду/док).

## 6) Non-goals

- Никакого login UI.
- Никакой правки user/roles через dashboard.
- Никаких бизнесовых действий экрана Margin на `/dev`.

## 7) UNCONFIRMED

1. **UNCONFIRMED:** финальный формат `/api/dev/status` может потребовать дополнительные поля окружения (git sha, app version).
   **Проверка:** после первого внедрения сделать dogfooding с Agent-Dev и добавить только реально используемые поля.
