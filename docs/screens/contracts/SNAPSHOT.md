# N3 — Contracts list (Договора) — Legacy Snapshot

## 1) Идентификация
- Экран в меню legacy: **«Договора»** (`menus.contract`, `Contracts.title`).
- Legacy route: `GET/POST /ContractsAction.do` (`dispatch=input|filter|restore`, плюс grid pager dispatch).
- Tile/JSP: `.Contracts` → `/jsp/Contracts.jsp`.
- Предлагаемый modern route: **`/journals/contracts`** (в проекте экраны журналов идут под `/journals/*`; если навигация упрощается — допустим `/contracts`).

## 2) Legacy entry points (traceability)
- Struts Action: `net.sam.dcl.action.ContractsAction`.
- Form: `net.sam.dcl.form.ContractsForm` (+ inherited поля из `JournalForm`).
- JSP: `src/main/webapp/jsp/Contracts.jsp`.
- Struts config: `/ContractsAction`, `/SelectCPContractsAction`, form-bean `Contracts`.
- SQL resource: `select-contracts` → `select * from dcl_contract_filter(...) order by con_reminder desc, con_date desc, con_number desc`.
- Validation: `/ContractsAction:filter` (date mask/date, currency, maxlength).
- Permissions: menu `id.contractDoc`, action `/ContractsAction.do,/ContractAction.do,...`.
- Checkers:
  - `style-checker` (annulled row → `crossed-cell`),
  - `show-delete-checker` (delete visible only if no specs),
  - `editChecker` (manager can edit only contracts tied to own department),
  - `alwaysReadonly` for executed checkbox in grid.

## 3) UI-слепок 1:1
### 3.1 Фильтры
1. `number` — text (до 50), дефолт `""`.
2. `contractor.name` + `contractor.id` — serverList (`/ContractorsListAction`), maxlength 200.
3. `date_begin` — date (`DD.MM.YYYY`).
4. `date_end` — date (`DD.MM.YYYY`).
5. `sum_min_formatted` — currency text.
6. `sum_max_formatted` — currency text.
7. `user.usr_name` + `user.usr_id` — serverList (`/UsersListAction`), maxlength 50.
8. `seller.name` + `seller.id` — serverList (`/SellersListAction`).
9. `executed` — checkbox value `1`.
10. `not_executed` — checkbox value `1`.
11. `oridinal_absent` — checkbox value `1`.

Поведение чекбоксов:
- `executed` и `not_executed` взаимоисключающие (JS в JSP).
- SQL-фильтр получает вычисленное `con_executed`:
  - оба включены или оба выключены → `null`;
  - только `executed` → `"1"`;
  - только `not_executed` → `"0"`.

### 3.2 Кнопки
- `Очистить фильтр` → submit `dispatch=input`.
- `Применить фильтр` → submit `dispatch=filter`.
- `Импорт КП` → submit `dispatch=selectCP`.
- `Создать` → link `/ContractAction.do?dispatch=input`.
- В гриде: row `edit`, `attachments` icon, `delete` (только admin + checker).

### 3.3 Колонки грида (порядок)
1. Номер (`con_number`)
2. Дата (`con_date_formatted`)
3. Контрагент (`con_contractor`)
4. Сумма (`con_summ_formatted`, right)
5. Валюта (`con_currency`)
6. Примечания (`notes`, вычисляемое)
7. Статус исполнения (`con_executed`, readonly checkbox)
8. Пользователь (`con_user`)
9. Напоминания (`con_reminder_formatted`, HTML)
10. Edit action
11. Attach icon (`attach_idx` → `images/attach{1..6}.gif`)
12. Delete action (только если admin)

Форматы:
- даты: DB→UI `DD.MM.YYYY`;
- суммы: formatted currency string (локальный формат legacy).

### 3.4 Пагинация/сортировка
- Пагинация серверная через `PageableHolderImplUsingList`, page size по `DAOUtils.DEF_PAGE_SIZE=15` (если не переопределено конфигом).
- NEXT/PREV через processBefore handler (`grid` + `NEXT_PAGE`/`PREV_PAGE`).
- Сортировка в SQL фиксированная (`con_reminder DESC, con_date DESC, con_number DESC`), пользовательская сортировка в Contracts.jsp не зафиксирована.

## 4) Роли/права
Доступ к экрану (menu/action): `admin`, `economist`, `manager`, `lawyer`, `user_in_lithuania`, `logistic`.

Ограничения действий:
- Create (`/ContractAction.do?dispatch=input`): `admin`, `economist`, `lawyer`.
- Edit row: доступен ролям экрана, но для `manager` действует `editChecker` по департаменту.
- Delete row: только если `Contracts.admin == true` и `show-delete-checker == true` (нет спецификаций).

## 5) Связь с backend (legacy)
- Initial load (`dispatch=input`): сброс фильтров + запрос `select-contracts`.
- Apply (`dispatch=filter`): `grid.page=1` + `select-contracts`.
- Clear (`dispatch=input`): полный сброс фильтров + `select-contracts`.
- Pagination next/prev: `grid.page +/- 1` + `select-contracts`.
- Export на списке Contracts: **не найдено** (кнопки/dispatch/export SQL отсутствуют).

## 6) Parity MUST checklist
1. Фильтры и их имена совпадают 1:1 (`number`, `contractor.*`, `date_*`, `sum_*`, `user.*`, `seller.*`, `executed/not_executed/oridinal_absent`).
2. Логика `con_executed` строго как в `ContractsForm#getCon_executed()`.
3. SQL order default строго `con_reminder desc, con_date desc, con_number desc`.
4. Page size = 15 по умолчанию, next/prev серверные.
5. Row style `crossed-cell` для annulled (`con_annul == "1"`).
6. Delete только admin + без спецификаций (`spc_count == 0`).
7. Manager edit lock по `dep_id_list` (department scope).
8. Нет export action на экране.
