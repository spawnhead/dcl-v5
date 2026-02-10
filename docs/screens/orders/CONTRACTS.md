# Orders List - API Contracts

> Source of truth: legacy `OrdersAction`, `OrdersForm`, sql-resources.xml `select-orders` → `dcl_order_filter`, DDL procedure signature.

## 1. List/Filter endpoint

**Purpose:** Return paginated list of orders matching filter (same as legacy "Применить фильтр" + grid data).

**Legacy trace:** `OrdersAction.filter()` → `internalFilter()` → `DAOUtils.fillGrid(context, form.getGrid(), "select-orders", OrdersForm.Order.class)`; SQL `select * from dcl_order_filter(...) order by {order_by}`. **sql-resources.xml** lines 1947–1973; **db/Lintera_dcl-5_schema.ddl** procedure `DCL_ORDER_FILTER` (lines 3475–3524).

### 1.1 Request

- **Method:** GET (preferred for idempotent list) or POST (if filter payload is large).
- **Path:** `GET /api/orders` (or `POST /api/orders/search` with body — implement one and document).

**Query parameters (filter + paging + sort):**

| Parameter | Type | Required | Description | Legacy form field / SQL param |
|-----------|------|----------|-------------|-------------------------------|
| number | string | no | Order number (partial) | OrdersForm.number → :number |
| date_begin | string (date) | no | Period from | date_begin_date → :date_begin_date |
| date_end | string (date) | no | Period to | date_end_date → :date_end_date |
| contractor_id | number | no | Contractor FK | contractor.id → :contractor.name (resolve name in backend or pass id and resolve) |
| contractor_for_id | number | no | Contractor-for (client) | contractor_for.name |
| stuff_category_id | number | no | Stuff category | stuffCategory.name |
| sum_min | number | no | Sum from | :sum_min |
| sum_max | number | no | Sum to | :sum_max |
| user_id | number | no | User | user.userFullName |
| department_id | number | no | Department | department.id |
| contract_number | string | no | Contract number / id | contractNumber → :contractNumber (contract.con_number) |
| specification_number | string | no | Specification number | specificationNumber → :specificationNumber |
| seller_for_who_id | number | no | Seller (for order) | sellerForWho.name |
| executed | boolean | no | Executed only | ord_executed "1" |
| not_executed | boolean | no | Not executed only | ord_executed "0" (both unchecked → null) |
| ord_ready_for_deliv | boolean | no | Ready for delivery | :ord_ready_for_deliv |
| ord_annul_not_show | boolean | no | Hide annulled | :ord_annul_not_show |
| state_a | boolean | no | State A | :state_a |
| state_3 | boolean | no | State 3 | :state_3 |
| state_b | boolean | no | State B | :state_b |
| state_exclamation | boolean | no | State ! | :state_exclamation |
| state_c | boolean | no | State C | :state_c |
| ord_num_conf | string | no | Confirmation number | :ord_num_conf |
| page | integer | no | Page (1-based) | form.getGrid().getPage() |
| pageSize | integer | no | Page size | form.getGrid().getPageSize() |
| order_by | string | no | Sort clause (safe: ord_date desc, ord_number, etc.) | form.order_by (default " ord_date descending") |

**Note:** Legacy passes names for contractor, contractor_for, stuffCategory, user, sellerForWho; backend can accept id and resolve name for procedure. Document chosen convention (id vs name) in API.

### 1.2 Response 200

```json
{
  "items": [
    {
      "ord_id": 123,
      "ord_number": "12345",
      "ord_date": "2025-01-15",
      "ord_contractor": "Contractor Name",
      "ord_contractor_for": "Client Name",
      "ord_summ": 1000.50,
      "ord_date_conf": "2025-01-20",
      "ord_sent_to_prod_date": null,
      "ord_received_conf_date": null,
      "ord_conf_sent_date": null,
      "ord_ready_for_deliv_date": null,
      "ord_ready_for_deliv": 0,
      "ord_executed_date": null,
      "ord_user": "User Name",
      "ord_department": "Department Name",
      "is_warn": "0",
      "ord_block": "0",
      "ord_annul": "0",
      "ord_num_conf": null,
      "dep_id": 5,
      "ord_link_to_spec": 1,
      "ord_comment_flag": 0,
      "have_empty_date_conf": "0",
      "count_day_curr_minus_sent": 0,
      "ord_ship_from_stock": null,
      "ord_arrive_in_lithuania": null,
      "usr_id_create": "10"
    }
  ],
  "total": 42,
  "page": 1,
  "pageSize": 25
}
```

**Field mapping:** Align with `DCL_ORDER_FILTER` RETURNS and `OrdersForm.Order` (OrdersForm.java inner class Order): ord_id, ord_number, ord_date, ord_contractor, ord_contractor_for, ord_summ, ord_sent_to_prod_date, ord_received_conf_date, ord_conf_sent_date, ord_ready_for_deliv_date, ord_ready_for_deliv, ord_executed_date, ord_user, ord_department, is_warn, usr_id_create, ord_block, ord_date_conf, count_day_curr_minus_sent, ord_annul, ord_num_conf, ord_arrive_in_lithuania, dep_id, have_empty_date_conf, ord_comment_flag, ord_ship_from_stock, ord_link_to_spec. Formatted fields (ordDateFormatted, ordSumFormatted, ordCurrentStateFormatted) can be computed on backend or frontend from these.

### 1.3 Response 4xx/5xx and error shape

- **400** — validation (e.g. date range invalid, sum_min > sum_max). Body: `{ "error": "VALIDATION", "message": "...", "fields": { "date_begin": "..." } }`.
- **403** — no permission to Orders. Body: `{ "error": "FORBIDDEN", "message": "..." }`.
- **500** — server/DB error. Body: `{ "error": "INTERNAL", "message": "..." }`.

**Trace:** Legacy validation.xml OrdersAction:filter; xml-permissions OrdersAction.do.

---

## 2. Clear filter (initial state)

**Purpose:** Same as "Сбросить фильтр" — return empty or default list and reset filter state.

**Legacy trace:** `OrdersAction.input()` — clears/resets form, may restore from session; then typically redirect or re-render with empty grid or last state.

- **Method:** GET.
- **Path:** `GET /api/orders?clear=1` or separate `POST /api/orders/reset` that returns same shape as list with empty/default params. Either: (1) client clears form and calls `GET /api/orders` with no params, or (2) backend provides explicit reset that returns default list. Document chosen behaviour in ACCEPTANCE.

**Response:** Same as list (200) with `items` empty or first page with no filter, `total` 0 or unfiltered total.

---

## 3. Lookups (for filter dropdowns)

All used by Orders.jsp serverList. Return shape: list of `{ id, name }` or `{ value, label }`; backend can follow existing Margin/Countries pattern.

| Lookup | Path (suggestion) | Legacy Action | Notes |
|--------|-------------------|---------------|--------|
| Contractors | GET /api/contractors or /api/lookups/contractors | ContractorsListAction | For contractor, contractor_for |
| Users | GET /api/users or /api/lookups/users | UsersListAction | user.userFullName |
| Departments | GET /api/departments or /api/lookups/departments | DepartmentsListAction | department.id |
| Stuff categories | GET /api/stuff-categories or /api/lookups/stuff-categories | StuffCategoriesListAction | stuffCategory.name |
| Sellers | GET /api/sellers or /api/lookups/sellers | SellersListAction | scriptUrl forOrder=true → sellerForWho |
| Contracts by contractor | GET /api/contracts?contractor_id={id} | ContractsDepFromContractorListAction | scriptUrl ctr_id, allCon=1 |
| Specifications by contract | GET /api/specifications?contract_id={id}&contractor_id={ctr_id} | SpecificationsDepFromContractListAction | scriptUrl ctr_id, id=contract, withExecuted=true |

**Trace:** Orders.jsp ctrl:serverList actions; struts-config for each List action.

---

## 4. Row actions (out of scope for list API)

- **Block:** POST to lock/unlock order — separate endpoint (e.g. `PATCH /api/orders/{id}/block`). **Trace:** OrdersAction dispatch=block, blockChecker.
- **Edit:** Navigate to order edit screen — `GET /order/{id}` or similar. **Trace:** OrderAction.do dispatch=edit.
- **Clone:** Clone order — e.g. `POST /api/orders/{id}/clone`. **Trace:** OrderAction.do dispatch=clone.

Edit/Clone visibility: `editCloneChecker` — if user is onlyManager and record.dep_id != currentUser.getDepartment().getId() then hide. **Trace:** OrdersAction.java editCloneChecker.

---

## 5. Summary table

| Endpoint | Method | Traceability |
|----------|--------|--------------|
| List/filter | GET /api/orders | OrdersAction.internalFilter, select-orders, DCL_ORDER_FILTER |
| Clear/reset | GET /api/orders (no params) or POST /api/orders/reset | OrdersAction.input |
| Lookups | GET /api/... (contractors, users, departments, stuff-categories, sellers, contracts, specifications) | Orders.jsp serverList, struts List actions |

**UNCONFIRMED:** Exact mapping of contract/specification when sent as id vs number (legacy uses contract.con_number, specification.spc_number in SQL).

**How to verify:** In legacy, select contractor_for → choose contract → choose specification → click «Применить фильтр». In DevTools → Network capture the request (form or query). Check whether backend receives `contractNumber`/`specificationNumber` (text) or `contract_id`/`specification_id` (id). Compare with sql-resources.xml `:contractNumber`, `:specificationNumber` and OrdersForm binding. Document result in CONTRACTS and payloads/list-request-contract-spec.json.
