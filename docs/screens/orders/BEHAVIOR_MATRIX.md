# Orders (N2) → List: Behavior Matrix

| Area | Legacy behavior (required) | Source | How to verify |
|---|---|---|---|
| Initial defaults | `input` sets defaults (`not_executed=1`, `ord_annul_not_show=1`, initial `order_by`) then runs filter | `OrdersAction.input` | Open `src/main/java/net/sam/dcl/action/OrdersAction.java` and inspect `input`; capture first request in legacy Network (`dispatch=input`) |
| Role-specific defaults | Manager auto-filters by self user; declarant/economist auto-check ready-for-delivery | `OrdersAction.input` | Inspect role branches in Action; in legacy, login under each role and capture resulting form state in HTML/request payload |
| Filter sort override | `dispatch=filter` always sets `order_by=' ord_date descending'` and page=1 | `OrdersAction.filter` | Inspect code; submit filter in legacy and capture payload + resulting row order (first page) |
| SQL filter mapping | Backend query is `dcl_order_filter(...) order by {order_by}` with fixed arg order | `sql-resources.xml#select-orders` | Inspect SQL entry; capture POST body fields and compare with function args |
| Tri-state executed filter | `ord_executed` derived from `executed` + `not_executed` (null/1/0) | `OrdersForm.getOrd_executed` + JS | Toggle checkboxes in legacy and inspect submitted form/body for `ord_executed` effects |
| State checkboxes dependency | executed disables+clears states; A/3, B, C are mutually constraining | `Orders.jsp` JS (`setDisableState`, `stateA3OnClick`, `stateBOnClick`, `stateCOnClick`) | In legacy browser, click through combinations and confirm disable/clear behavior before submit |
| Dependent reload | Change `contractor_for` or `contract` triggers `dispatch=reload` and clears dependent fields | `Orders.jsp` JS + `OrdersAction.reload` | Capture requests after changing contractor/contract in legacy and verify `dispatch=reload` with field reset |
| Server paging protocol | Pager posts `dispatch=grid` with `grid=NEXT_PAGE/PREV_PAGE`; Action handlers inc/dec page | `OrdersAction.processBefore`, `PageableDataHolder` | Click pager in legacy and inspect request form fields; confirm values are uppercase constants |
| Row access gates | block toggle read-only for non-admin; edit/clone disabled for onlyManager on foreign department | `OrdersAction.internalFilter` (`blockChecker`, `editCloneChecker`) + `Orders.jsp` | Check row controls under admin vs manager (same/other dep) and compare rendered/active controls |
| Block side effects | toggling block writes `ord_block`; blocking triggers extra DAO processing of unexecuted items | `OrdersAction.block`, `OrderDAO.saveBlock` | Trigger block/unblock in legacy and inspect DB/log/side-effects if available; at minimum confirm network action and resulting state changes |

## Top-5 parity risks and verification focus
1. Executed tri-state derived logic drift.  
   - **How to verify:** inspect `OrdersForm.getOrd_executed`; run UI click sequence (none/both/only executed/only not_executed) and record payload + returned rows.
2. Sort contract drift between `input` and `filter`.  
   - **How to verify:** compare first load vs after Apply Filter in legacy; verify `order_by` values in posted form and SQL trace.
3. Reload chain drift for contractor→contract→specification.  
   - **How to verify:** inspect `Orders.jsp` callbacks + `OrdersAction.reload`; capture `dispatch=reload` requests.
4. Permission drift on row actions.  
   - **How to verify:** inspect `blockChecker`/`editCloneChecker` and `xml-permissions.xml`; run with admin and manager in different departments.
5. Block action side-effect omission.  
   - **How to verify:** inspect `OrderDAO.saveBlock`; verify that blocking invokes both `order-update-block` and `process-order_produces_unexecuted` (SQL log or DB delta).
