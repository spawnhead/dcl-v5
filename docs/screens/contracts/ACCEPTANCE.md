# N3 Contracts — Acceptance criteria (1:1)

## Parity MUST (FAIL если не выполнено)
1. Экран открывается по modern route и отображает фильтр + grid в составе и порядке как legacy Contracts.jsp.
2. Поля фильтра и имена payload совпадают с legacy (`number`, `contractor`, `date_begin/end`, `sum_min/max`, `user`, `seller`, `executed`, `not_executed`, `oridinal_absent`).
3. `executed` и `not_executed` взаимоисключающие в UI; расчет `con_executed` строго по legacy формуле.
4. Clear полностью сбрасывает фильтры к `ContractsAction#input` defaults.
5. Apply всегда выставляет `page=1`.
6. Сетка возвращает колонки в порядке legacy, включая `notes`, `con_reminder_formatted`, attach icon, row actions.
7. Пагинация next/prev серверная, pageSize=15 (если нет системного override).
8. Базовый order: `con_reminder desc, con_date desc, con_number desc`.
9. Row style `crossed-cell` для annulled contract (`con_annul == "1"`).
10. Edit disabled для manager вне своего департамента (`dep_id_list`).
11. Delete доступен только admin и только когда `spc_count == 0`.
12. Export на Contracts list отсутствует (кнопка/endpoint не должны появиться).

## Allowed diffs
- Пусто (не зафиксированы допустимые отклонения).

## Out of scope
- Пусто (всё поведение списка договоров в scope).

## Приёмочные сценарии

### 1) Initial load
- Trigger: open `/journals/contracts`.
- Expected: defaults как в `ContractsAction#input`, grid загружен через contracts data endpoint.

### 2) Apply filter
- Trigger: заполнить минимум 2 поля + Apply.
- Expected: page=1, backend получает mapped параметры SQL-filter, grid обновляется.

### 3) Pagination
- Trigger: Next page.
- Expected: тот же фильтр, page+1, hasNextPage корректен.

### 4) Sort
- Trigger: открытие/перезагрузка данных.
- Expected: порядок по legacy default sort.

### 5) Clear
- Trigger: Clear.
- Expected: reset + reload первой страницы.

### 6) Permission denied
- Trigger: роль без доступа к экрану.
- Expected: отказ в доступе (403/redirect login per security policy), экран не показывается.

### 7) Validation errors
- Trigger: невалидные дата/сумма.
- Expected: отображается ошибка валидации, запрос в data endpoint не должен ломать UI.
