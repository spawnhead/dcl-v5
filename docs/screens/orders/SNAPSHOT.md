# Legacy Screen Snapshot - Orders (List)

> Source: reconstructed from legacy JSP/Action/Form/DAO and DDL. Live HAR/screenshots to be captured for verification.

Related docs:
- Contracts: `docs/screens/orders/CONTRACTS.md`
- Acceptance: `docs/screens/orders/ACCEPTANCE.md`
- Behavior matrix: `docs/screens/orders/BEHAVIOR_MATRIX.md`

## 1. Screen identity

- **Legacy menu path:** Заказы (Orders)
- **Legacy URL/route:** `/OrdersAction.do?dispatch=input` (initial), `dispatch=filter` (apply filter)
- **Required roles:** `admin`, `economist` (from xml-permissions.xml). Manager: access possible; edit/clone restricted by department (see §3).

**Traceability:** `struts-config.xml` path `/OrdersAction`, input `.Orders` → tiles body `/jsp/Orders.jsp`. Form bean `Orders` → `net.sam.dcl.form.OrdersForm`.

## 2. Layout map (all UI elements)

### Filters panel

**Text / lookup filters (from Orders.jsp + OrdersForm / JournalForm):**

- `number` — text, order number. **Trace:** `Orders.jsp` ctrl:text property="number", `OrdersForm` extends JournalForm (number).
- `date_begin`, `date_end` — date pickers (Period from/to). **Trace:** ctrl:date, validation form `OrdersAction:filter` (date_begin, date_end).
- `contractor.name` / `contractor.id` — lookup. **Trace:** ctrl:serverList action="/ContractorsListAction".
- `contractor_for.name` / `contractor_for.id` — lookup (contractor client). **Trace:** ctrl:serverList action="/ContractorsListAction" filter, callback="onChangeContractorFor".
- `user.userFullName` / `user.usr_id` — lookup. **Trace:** ctrl:serverList action="/UsersListAction".
- `department.name` / `department.id` — lookup. **Trace:** ctrl:serverList action="/DepartmentsListAction" filter.
- `stuffCategory.name` / `stuffCategory.id` — lookup. **Trace:** ctrl:serverList action="/StuffCategoriesListAction".
- `contract.con_number` / `contract.con_id` — lookup or text; depends on contractor_for (if set: ContractsDepFromContractorListAction; else text). **Trace:** Orders.jsp logic:notEmpty contractor_for.name → serverList action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractor_for.id)&allCon=1".
- `specification.spc_number` / `specification.spc_id` — lookup or text; depends on contract (if set: SpecificationsDepFromContractListAction; else text). **Trace:** scriptUrl="ctr_id=$(contractor_for.id)&id=$(contract.con_id)&withExecuted=true".
- `sellerForWho.name` / `sellerForWho.id` — lookup. **Trace:** ctrl:serverList action="/SellersListAction" scriptUrl="forOrder=true".
- `sum_min_formatted`, `sum_max_formatted` — sum range. **Trace:** ctrl:text sum_min_formatted/sum_max_formatted; validation OrdersAction:filter sum_min, sum_max.

**Checkboxes (options):**

- `executed` — "Выполненные" (executed orders). **Trace:** OrdersForm.executed, getOrd_executed() maps to 1/0/null for procedure.
- `not_executed` — "Не выполненные". **Trace:** OrdersForm.not_executed.
- `ord_ready_for_deliv` — "Готов к отгрузке". **Trace:** OrdersForm.ord_ready_for_deliv.
- `ord_annul_not_show` — "Не показывать аннулированные". **Trace:** OrdersForm.ord_annul_not_show.
- `state_a`, `state_3`, `state_b`, `state_exclamation`, `state_c` — state filters for "ord_current_state" (JS mutual exclusion with executed). **Trace:** Orders.jsp stateA3OnClick, stateBOnClick, stateCOnClick; OrdersForm state_*.
- `ord_num_conf` — text, confirmation number filter. **Trace:** OrdersForm.ord_num_conf.
- `ord_show_movement` — show movement info in grid. **Trace:** OrdersForm.ord_show_movement.

**Buttons:**

- **Сбросить фильтр** — `dispatch="input"`. Clears form and restores session-stored state. **Trace:** OrdersAction.input/restore.
- **Применить фильтр** — `dispatch="filter"`. Applies filter, sets order_by to `ord_date descending`, grid page 1, runs internalFilter (select-orders). **Trace:** OrdersAction.filter → internalFilter, DAOUtils.fillGrid "select-orders", OrdersForm.Order.class.

### Grid (table)

**Data source:** SQL id `select-orders` → procedure `dcl_order_filter(...)` with form params. **Trace:** sql-resources.xml entry id='select-orders'; OrdersAction.internalFilter fillGrid "select-orders". Order by: `form.order_by` (default `ord_date descending`). **Trace:** OrdersAction.filter sets `form.setOrder_by(" ord_date descending")`.

**Columns (order from Orders.jsp grid:row):**

1. ord_number — Номер (Orders.number)
2. ord_date (formatted) — Дата (Orders.date)
3. ord_contractor — Контрагент (Orders.contractor)
4. ord_summ (formatted) — Сумма (Orders.sum)
5. ord_contractor_for — Контрагент-клиент (Orders.ord_contractor_for)
6. ordCurrentStateFormatted — Текущее состояние (Orders.ord_current_state1) + linkToSpec image, threeDayMsg, showWarn
7. threeDayMsg — (column for 3-day message)
8. showWarn — attention image (tooltip Orders.warn)
9. ord_user — Пользователь (Orders.user)
10. ord_department — Отдел (Orders.department)
11. ord_block — Block checkbox (lock icon); submit dispatch="block", scriptUrl block=${record.ord_block}; readonlyCheckerId="blockChecker". **Trace:** blockChecker = !currentUser.isAdmin().
12. Clone — link /OrderAction.do?dispatch=clone, readonlyCheckerId="editCloneChecker"
13. Edit — link /OrderAction.do?dispatch=edit, readonlyCheckerId="editCloneChecker"

**Row styling:** style-checker: if ord_annul=1 → "crossed-cell". **Trace:** OrdersAction style-checker.

**Pagination:** grid next/prev page. **Trace:** OrdersAction processBefore: handlers "grid" NEXT_PAGE/PREV_PAGE → form.getGrid().incPage()/decPage(), return internalFilter(context).

### Actions below grid

- **Создать** — link `/OrderAction.do?dispatch=input` (create new order). **Trace:** Orders.jsp ctrl:ubutton action="/OrderAction.do?dispatch=input".

## 3. Roles and restrictions

- **xml-permissions:** `/OrdersAction.do` → admin, economist. **Trace:** xml-permissions.xml.
- **editCloneChecker:** If user is onlyManager and record.dep_id != currentUser.getDepartment().getId() → read-only (no edit, no clone). **Trace:** OrdersAction.java editCloneChecker.
- **blockChecker:** If !currentUser.isAdmin() → block checkbox read-only. **Trace:** OrdersAction blockChecker = !currentUser.isAdmin().

## 4. Data model (grid row)

Procedure `DCL_ORDER_FILTER` RETURNS (from DDL): ORD_ID, ORD_NUMBER, ORD_DATE, ORD_CONTRACTOR, ORD_CONTRACTOR_FOR, ORD_SUMM, ORD_SENT_TO_PROD_DATE, ORD_RECEIVED_CONF_DATE, ORD_CONF_SENT_DATE, ORD_READY_FOR_DELIV_DATE, ORD_READY_FOR_DELIV, ORD_EXECUTED_DATE, ORD_USER, ORD_DEPARTMENT, IS_WARN, USR_ID_CREATE, ORD_BLOCK, ORD_DATE_CONF, COUNT_DAY_CURR_MINUS_SENT, ORD_ANNUL, ORD_NUM_CONF, ORD_ARRIVE_IN_LITHUANIA, DEP_ID, HAVE_EMPTY_DATE_CONF, ORD_COMMENT_FLAG, ORD_SHIP_FROM_STOCK, ORD_LINK_TO_SPEC.

OrdersForm.Order maps these + formatted getters (ordDateFormatted, ordSumFormatted, ordCurrentStateFormatted, etc.). **Trace:** OrdersForm.Order class.

## 5. Validation (filter form)

**Trace:** validation.xml form name="/OrdersAction:filter". Fields: date_begin, date_end (mask, date), sum_min, sum_max (mask), contractor.name, contractor_for.name, stuffCategory.name, number, user.userFullName, department.id, sellerForWho.name, ord_num_conf — various validators.

## 6. Parity checklist (summary)

- See ACCEPTANCE.md for MUST list.
- All filter names and grid columns must match legacy; dependent lookups (contract from contractor_for, specification from contract) must behave as in JSP (optional: capture HAR for exact request params).
