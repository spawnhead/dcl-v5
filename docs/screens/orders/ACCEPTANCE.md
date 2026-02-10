# Orders List: Parity Acceptance Checklist

> Source: `docs/screens/orders/SNAPSHOT.md`, `CONTRACTS.md`. Traceability: OrdersAction, OrdersForm, Orders.jsp, select-orders → DCL_ORDER_FILTER.

## 1) Parity MUST (backend + data)

- **Filter dataset:** List is produced by calling Firebird procedure `DCL_ORDER_FILTER` with the same input parameters as legacy (see CONTRACTS.md). After migration: equivalent Postgres function or application-layer query producing the same columns and semantics. **Trace:** sql-resources.xml `select-orders`, DDL procedure DCL_ORDER_FILTER.
- **Filter parameters:** All 22 procedure inputs are supported: number, contractor (name or id→name), contractor_for, stuffCategory, date_begin, date_end, sum_min, sum_max, user, department, ord_executed, ord_ready_for_deliv, sellerForWho, state_a, state_3, state_b, state_exclamation, state_c, ord_num_conf, ord_annul_not_show, contractNumber, specificationNumber. **Trace:** OrdersForm + JournalForm, sql-resources.xml.
- **Sort:** Default sort `ord_date descending`; optional `order_by` (safe whitelist). **Trace:** OrdersAction.filter sets order_by; SQL `order by {order_by}`.
- **Pagination:** Server-side; page and pageSize (grid next/prev). **Trace:** OrdersAction processBefore NEXT_PAGE/PREV_PAGE, form.getGrid().incPage()/decPage().
- **Row shape:** Response items include all fields returned by DCL_ORDER_FILTER (see CONTRACTS §1.2 and OrdersForm.Order). **Trace:** OrdersForm.Order, DDL RETURNS.
- **Permissions:** Only roles that can open Orders in legacy (admin, economist; manager with department restriction for edit/clone). **Trace:** xml-permissions.xml OrdersAction.do.
- **Edit/Clone visibility:** If user is onlyManager and order.dep_id != currentUser.department.id → edit and clone actions hidden/disabled. **Trace:** OrdersAction editCloneChecker.
- **Block checkbox:** Visible and editable only for admin; for non-admin block column read-only or hidden. **Trace:** OrdersAction blockChecker = !currentUser.isAdmin().
- **Row style:** Rows with ord_annul=1 have CSS class "crossed-cell". **Trace:** OrdersAction style-checker.
- **Warn column:** Show attention icon when is_warn is set. **Trace:** OrdersAction showWarn.

## 2) UI must-have

- **Filters panel:** All filter fields from SNAPSHOT present (number, date_begin, date_end, contractor, contractor_for, user, department, stuffCategory, contract, specification, sellerForWho, sum_min, sum_max, executed, not_executed, ord_ready_for_deliv, ord_annul_not_show, state_a/3/b/exclamation/c, ord_num_conf, ord_show_movement). Buttons: Сбросить фильтр, Применить фильтр.
- **Dependent lookups:** Contract list depends on contractor_for (when set); specification list depends on contract (and contractor_for). When contractor_for cleared, contract/specification behave per legacy (text or empty). **Trace:** Orders.jsp logic:notEmpty contractor_for → ContractsDepFromContractorListAction; specification from contract.
- **Grid columns:** Same order and meaning as SNAPSHOT: number, date, contractor, sum, contractor_for, current state (+ linkToSpec, threeDayMsg), warn, user, department, block, clone, edit.
- **Pagination:** Next/Previous (or page numbers) that trigger server request with page/pageSize.
- **Below grid:** Button/link "Создать" → navigate to new order (OrderAction input).
- **Validation:** Client-side and/or server-side for filter (e.g. date range, sum_min ≤ sum_max) consistent with validation.xml OrdersAction:filter.

## 3) Out of scope for list screen (separate flows)

- Order create/edit form (OrderAction input/edit).
- Order block (dispatch=block) — separate endpoint.
- Order clone (OrderAction clone) — separate endpoint.
- Orders statistics / logistics / unexecuted reports (different actions and SQL).

## 4) UNCONFIRMED (verify on legacy)

- Exact request param names and types sent by legacy for contract/specification (id vs number). **How to verify:** Apply filter by contract and specification in browser, capture HAR or form POST and compare with CONTRACTS.
- Default page size and max page size of grid in legacy. **How to verify:** Check grid taglib config or OrdersAction default grid size.
- ordCurrentStateFormatted and linkToSpec image rules (derived from which fields). **How to verify:** Inspect Order bean getters and JSP logic.
