# Orders (N2) → List: Legacy Snapshot

> Goal of this package: lock down **1:1 parity** for legacy `/OrdersAction.do` list behavior (filters, grid paging, row actions, role gates), not a “best effort” modernized interpretation.

## 1) Evidence base (validated)
- Action/controller flow: `src/main/java/net/sam/dcl/action/OrdersAction.java` (`input`, `filter`, `internalFilter`, `reload`, `block`, pager handlers).  
- Form contract and computed fields: `src/main/java/net/sam/dcl/form/OrdersForm.java`.  
- View + JS coupling: `src/main/webapp/jsp/Orders.jsp`.  
- DB filter function + ordering: `src/main/webapp/WEB-INF/classes/resources/sql-resources.xml` (`select-orders`).  
- Permissions: `src/main/webapp/WEB-INF/classes/resources/xml-permissions.xml` (`/OrdersAction.do`, `/OrderAction.do?...`).

## 2) Strictness check vs Margin package (etalon)
Result: Orders pack now follows Margin-level structure:
- Explicit endpoint inventory with method/path/body/pagination/sort rules.
- Acceptance written as deterministic legacy behavior, not implementation freedom.
- Behavior matrix includes `source` and `How to verify` per parity risk.
- Payload examples cover: base load, filter form, pager next/prev, sort (`order_by`), dependent reload.

## 3) Legacy UX and behavior (source-of-truth)
- Entry endpoint: `GET /OrdersAction.do?dispatch=input`.
- Default filter state from `input`:
  - `not_executed=1`, `ord_annul_not_show=1`, `ord_ready_for_deliv=""`.
  - manager: auto-sets `user` filter to current user.
  - declarant/economist: auto-sets `ord_ready_for_deliv=1`.
  - default sort: `ord_ready_for_deliv desc, ord_date desc, ord_number desc`.
- Apply filter (`dispatch=filter`) hard-resets sort to `ord_date desc` and page to 1.
- Grid pagination is server/session based through dispatch handler `grid` + `NEXT_PAGE`/`PREV_PAGE`.
- Filtering SQL is function call `dcl_order_filter(...)` with fixed argument order.
- JS constraints:
  - `executed` and `not_executed` are mutually exclusive.
  - if `executed` checked, state checkboxes (`state_a`, `state_3`, `state_b`, `state_exclamation`, `state_c`) are disabled and cleared.
  - `state_a`/`state_3` conflict with `state_b` and `state_c`; `state_b` conflicts with `state_a`/`state_3`/`state_c`; `state_c` conflicts with `state_a`/`state_3`/`state_b`.
  - changing `contractor_for` or `contract` triggers `dispatch=reload`, clears dependent fields.

## 4) Open evidence gaps
- No HAR/network capture attached yet for Orders list in this repo.
- Therefore all request payload examples are **code-derived canonical examples**; capture from legacy browser session is still required for byte-level parity.

Capture checklist for legacy (to close gaps):
1. Open Orders list and save HAR with: initial load, filter submit, contractor change reload, contract change reload, pager next/prev, block/unblock.
2. Save request payloads and response HTML snapshots into `docs/screens/orders/payloads/`.
