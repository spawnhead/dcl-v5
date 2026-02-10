# Orders (N2) → List: Acceptance Criteria (1:1 Legacy)

## A. Entry and defaults
1. Opening `/OrdersAction.do?dispatch=input` MUST produce list page with prefilled defaults from `OrdersAction.input`.
2. Defaults MUST be exact:
   - `not_executed=1`
   - `ord_annul_not_show=1`
   - `executed=""`
   - `ord_ready_for_deliv=""` unless role branch sets it.
3. Role-sensitive defaults MUST be preserved:
   - manager: `user` filter = current user;
   - declarant/economist: `ord_ready_for_deliv=1`.
4. Initial sorting MUST be `ord_ready_for_deliv desc, ord_date desc, ord_number desc`.

## B. Filtering semantics
1. Apply Filter button MUST submit `dispatch=filter` as form POST.
2. On filter dispatch, backend MUST force:
   - `order_by = ord_date descending`;
   - page reset to first page.
3. SQL for list data MUST call `dcl_order_filter(...)` with argument list equivalent to `select-orders`.
4. `contract.con_number` and `specification.spc_number` filtering MUST use first token before space (`getContractNumber`, `getSpecificationNumber`).

## C. UI coupling rules (do not simplify)
1. `executed` and `not_executed` checkboxes MUST stay mutually exclusive.
2. When `executed=1`, state filters (`state_a`, `state_3`, `state_b`, `state_exclamation`, `state_c`) MUST be disabled and unchecked.
3. State conflict matrix MUST remain:
   - (`state_a` OR `state_3`) disables `state_b`, `state_c`;
   - `state_b` disables `state_a`, `state_3`, `state_c`;
   - `state_c` disables `state_a`, `state_3`, `state_b`.
4. Changing `contractor_for` MUST clear contract+specification and submit `dispatch=reload`.
5. Changing contract MUST conditionally clear specification and submit `dispatch=reload`.

## D. Grid behavior
1. Grid data source MUST be server/session list from `DAOUtils.fillGrid("select-orders")`.
2. Pagination MUST be server-dispatch based (`dispatch=grid`, `grid=NEXT_PAGE|PREV_PAGE`), not purely client-only paging.
3. Row styling MUST strike through annulled orders (`ord_annul=1` → `crossed-cell`).
4. Warning icon MUST display only when `is_warn` is not empty.

## E. Row actions and access gates
1. Block checkbox MUST be read-only for non-admin (`blockChecker`).
2. Clone/Edit MUST be read-only for `onlyManager` when record `dep_id` differs from current user department (`editCloneChecker`).
3. Block action MUST toggle flag and apply additional unexecuted-processing when switching to blocked state.

## F. Anti-drift guard (against “implementation-convenient” reinterpretation)
1. It is NOT acceptable to replace reload with local frontend state only.
2. It is NOT acceptable to convert tri-state execution filter into a single boolean.
3. It is NOT acceptable to keep one universal sort rule for all entry points (input/filter differ by design).
4. It is NOT acceptable to drop role-dependent edit/block restrictions from row controls.
