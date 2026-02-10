# Orders List: Behavior Matrix

> Source: `docs/screens/orders/SNAPSHOT.md`, `CONTRACTS.md`. Traceability: OrdersAction, OrdersForm, Orders.jsp.

| Scenario | Steps | Expected network | Expected UI | Notes |
|----------|--------|------------------|-------------|--------|
| **Initial load** | Open Orders (menu) | GET `/OrdersAction.do?dispatch=input`; then list may be empty or last state; lookups as needed | Filters visible; grid empty or first page; Сбросить / Применить enabled | Legacy may restore form from session. Modern: GET /api/orders (no or default params) → items + total. |
| **Apply filter** | Set any filter (e.g. date range, contractor) → Применить фильтр | POST (or GET) with filter params; backend runs dcl_order_filter equivalent | Grid shows first page of results; total count; pagination visible if total > pageSize | Trace: OrdersAction.filter → internalFilter, select-orders. |
| **Clear filter** | Сбросить фильтр | Request with no filter (or reset endpoint) | Filter fields cleared; grid empty or default list | Trace: OrdersAction.input. |
| **Pagination** | Click Next / Previous | GET /api/orders?page=2&... (same filter) | Grid shows page 2 (or prev); total unchanged | Trace: processBefore grid NEXT_PAGE/PREV_PAGE → internalFilter. |
| **Sort** | Click column header (if supported) | GET /api/orders?order_by=ord_number&... | Grid re-fetched with new sort | Legacy order_by in SQL; default ord_date descending. |
| **Change page size** | Select 25/50/100 | GET /api/orders?pageSize=50&page=1&... | First page with new size | If legacy supports; else document as client-only. |
| **Dependent lookup** | Select contractor_for → open Contract dropdown | GET /api/contracts?contractor_id={id} | Contract list filtered by contractor_for | Trace: ContractsDepFromContractorListAction scriptUrl ctr_id. |
| **Dependent lookup** | Select contract → open Specification dropdown | GET /api/specifications?contract_id={id}&contractor_id={ctr_id} | Specification list filtered | Trace: SpecificationsDepFromContractListAction. |
| **Edit/Clone visibility** | Login as manager, open Orders | List loaded | Edit/Clone only on rows where dep_id = currentUser.department.id | Trace: editCloneChecker. |
| **Block visibility** | Login as non-admin | List loaded | Block checkbox read-only or hidden | Trace: blockChecker. |
| **Annulled row style** | Apply filter so some rows have ord_annul=1 | List response with ord_annul | Those rows have class "crossed-cell" | Trace: style-checker. |
| **Warn icon** | Rows with is_warn set | List response with is_warn | Attention icon in warn column | Trace: showWarn. |
| **Error: validation** | Submit invalid filter (e.g. date_end < date_begin) | 400 + error body | Message shown; grid unchanged | Trace: validation.xml OrdersAction:filter. |
| **Error: no permission** | User without Orders permission | 403 or redirect to login | No data or "Нет прав" message | Trace: xml-permissions. |
| **Create button** | Click Создать | Navigate to /order/new or OrderAction input | Order create form | Trace: OrderAction.do dispatch=input. |

**Export:** Legacy Orders list does not define Excel/CSV export on this screen; if added later, document in CONTRACTS and here.
