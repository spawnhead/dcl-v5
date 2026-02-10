# Orders (N2) → List: Network Contracts

> Scope: only legacy Orders **list** (`/OrdersAction.do`), including filter/paging/reload/block actions visible from `Orders.jsp`.

## Contract 1 — Initial screen load
- Method: `GET`
- Path: `/OrdersAction.do?dispatch=input`
- Purpose: initialize defaults in `OrdersForm`, execute initial filter, render list page.
- Response: HTML (`.Orders` view).
- Legacy-critical side effects:
  - resets filter fields to defaults;
  - stores form in session (`StoreUtil.putSession` via `internalFilter`).

### How to verify
1. Read `OrdersAction.input` defaults and role branches.  
2. Confirm request target in menu/navigation to `/OrdersAction.do?dispatch=input`.  
3. Capture legacy HAR and verify first navigation request query exactly includes `dispatch=input`.

## Contract 2 — Apply filter
- Method: `POST`
- Path: `/OrdersAction.do?dispatch=filter`
- Content type: `application/x-www-form-urlencoded`
- Purpose: apply filter criteria and refresh grid dataset.
- Required behavioral effects:
  - sets `order_by = " ord_date descending"`;
  - sets grid page to `1`;
  - calls SQL `select-orders` (`dcl_order_filter(...) order by {order_by}`).

### Request body fields (canonical)
- scalar filters: `number`, `date_begin`, `date_end`, `sum_min_formatted`, `sum_max_formatted`, `ord_num_conf`
- nested filters: `contractor.name/id`, `contractor_for.name/id`, `contract.con_number/con_id`, `specification.spc_number/spc_id`, `user.userFullName/usr_id`, `department.name/id`, `stuffCategory.name/id`, `sellerForWho.name/id`
- toggles: `executed`, `not_executed`, `ord_ready_for_deliv`, `ord_annul_not_show`, `ord_show_movement`, `state_a`, `state_3`, `state_b`, `state_exclamation`, `state_c`
- transport/system: `dispatch=filter`, `order_by`

### How to verify
1. Validate mapping in `Orders.jsp` `<ctrl:form>` fields.  
2. Validate `OrdersAction.filter` sets sort/page before `internalFilter`.  
3. Validate SQL parameter list in `sql-resources.xml` entry `select-orders`.  
4. Capture legacy request payload from browser (Network tab) and compare exact form field names.

## Contract 3 — Dependent reload (contractor/contract changes)
- Method: `POST`
- Path: `/OrdersAction.do?dispatch=reload`
- Content type: `application/x-www-form-urlencoded`
- Purpose: rerender form with dependent server lists after client JS clears fields.
- Trigger:
  - `onChangeContractorFor()` clears `contract` + `specification`, then submit reload.
  - `onChangeContract()` may clear `specification`, then submit reload.
- Response: HTML form view without executing `internalFilter`.

### How to verify
1. Read JS handlers in `Orders.jsp` (`onChangeContractorFor`, `onChangeContract`).  
2. Read `OrdersAction.reload` (copies current form into stored session form and forwards `form`).  
3. Capture legacy reload requests and verify `dispatch=reload` + changed dependent fields.

## Contract 4 — Grid pagination
- Method: `POST`
- Path: `/OrdersAction.do?dispatch=grid`
- Content type: `application/x-www-form-urlencoded`
- Purpose: server-side/session pagination of current filtered list.
- Required body:
  - `grid=NEXT_PAGE` or `grid=PREV_PAGE` (constants from `PageableDataHolder`).
- Behavior:
  - page increment/decrement in session form;
  - then `internalFilter` re-runs data fill.

### How to verify
1. Confirm constants in `PageableDataHolder`.  
2. Confirm handlers in `OrdersAction.processBefore` for `grid` + `NEXT_PAGE`/`PREV_PAGE`.  
3. Capture legacy pager click payload and verify exact `grid` values.

## Contract 5 — Block/unblock from row checkbox
- Method: `POST`
- Path: `/OrdersAction.do?dispatch=block`
- Content type: `application/x-www-form-urlencoded`
- Required params: `ord_id`, `block` (current value sent by `scriptUrl="block=${record.ord_block}"`).
- Behavior:
  - toggles `ord_block` (empty ↔ `1`);
  - persists via `OrderDAO.saveBlock`;
  - if set to blocked (`1`), executes additional DAO update `process-order_produces_unexecuted`.

### How to verify
1. Confirm row control wiring in `Orders.jsp` (`grid:colCheckbox dispatch="block"`).  
2. Confirm toggle logic in `OrdersAction.block`.  
3. Confirm DB side effect in `OrderDAO.saveBlock`.  
4. Capture legacy network call when checking/unchecking lock column.

---

## Top-5 parity risks (most probable)
1. **Executed/not_executed tri-state mismatch**: `ord_executed` derived from two checkboxes, not from single boolean.
2. **Sort drift after filter**: `dispatch=filter` overrides sort to `ord_date desc`; default initial sort differs.
3. **Dependent reload omission**: contractor/contract changes must post `dispatch=reload`; otherwise stale contract/specification options.
4. **Role/department edit gates**: clone/edit availability for managers depends on department match (`dep_id`) and role checks.
5. **Block side-effect loss**: blocking runs extra backend process (`process-order_produces_unexecuted`), not only flag update.
