# Reports → Margin: Network Contracts

> Source: `docs/screens/margin/SNAPSHOT.md` + payload examples in `docs/screens/margin/payloads/*.json`. No live HAR capture; all request/response details below are **UNCONFIRMED** unless explicitly shown in payload examples.

## Margin Screen Load
- Method: GET
- Path: `/MarginAction.do?dispatch=input`
- Purpose: Render legacy Margin screen (filters + iframe placeholder).
- Auth/session notes: Uses legacy session (JSESSIONID) for downstream grid data. **UNCONFIRMED** if any explicit auth checks occur here.
- Request:
  - query params: none
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: n/a
- Response:
  - rows schema: n/a (HTML page)
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED** exact shape.
- Example payloads:
  - `docs/screens/margin/payloads/initial-load.requests.json`

## Margin Grid Iframe Shell
- Method: GET
- Path: `/test/MarginReportGridStandalone.jsp`
- Purpose: Load AG Grid shell + JS for the Margin grid.
- Auth/session notes: Session cookie required to access grid data endpoint. **UNCONFIRMED**.
- Request:
  - query params: none
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: n/a
- Response:
  - rows schema: n/a (HTML/JS)
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED** exact shape.
- Example payloads:
  - `docs/screens/margin/payloads/initial-load.requests.json`

## Margin Grid Data
- Method: GET
- Path: `/MarginDevData.do`
- Purpose: Fetch JSON data for the grid using server-side Margin session.
- Auth/session notes: Requires active legacy session; uses Margin session built by `generate`. **UNCONFIRMED** if guest access is allowed.
- Request:
  - query params:
    - `limit` (string/integer, optional, default `200`, allowed values: `50|100|200|500|1000`) — max rows to load. **UNCONFIRMED** exact limits; from snapshot.
  - body schema: n/a
  - sorting encoding: none (client-side sort only). **UNCONFIRMED**
  - pagination encoding: none (client-side pagination; server only limits rows). **UNCONFIRMED**
  - filtering encoding: none (server reads Margin session). **UNCONFIRMED**
- Response:
  - rows schema (JSON array `data[]`):
    - `ctr_name` (string)
    - `cut_name` (string)
    - `con_number_formatted` (string)
    - `con_date_formatted` (string, date formatted `dd.MM.yyyy`)
    - `spc_number_formatted` (string)
    - `spc_date_formatted` (string, date formatted `dd.MM.yyyy`)
    - `spc_summ_formatted` (string, formatted number)
    - `cur_name` (string)
    - `stf_name_show` (string)
    - `shp_number_show` (string)
    - `shp_date_show` (string, date formatted `dd.MM.yyyy`)
    - `pay_date_show` (string, date formatted `dd.MM.yyyy`)
    - `lps_summ_eur_formatted` (string, formatted number)
    - `lps_summ_formatted` (string, formatted number)
    - `lps_sum_transport_formatted` (string, formatted number)
    - `lcc_transport_formatted` (string, formatted number)
    - `lps_custom_formatted` (string, formatted number)
    - `lcc_charges_formatted` (string, formatted number)
    - `lcc_montage_formatted` (string, formatted number)
    - `lps_montage_time_formatted` (string, formatted number)
    - `montage_cost_formatted` (string, formatted number)
    - `lcc_update_sum_formatted` (string, formatted number)
    - `summ_formatted` (string, formatted number)
    - `summ_zak_formatted` (string, formatted number)
    - `margin_formatted` (string, formatted number)
    - `koeff_formatted` (string, formatted number)
    - `usr_name_show` (string)
    - `dep_name_show` (string)
    - `itogLine` (boolean)
    - `spc_group_delivery` (string)
    - `haveUnblockedPrc` (boolean)
  - total / summary schema:
    - `meta.rowsTotal` (number)
    - `meta.rowsReturned` (number)
    - `meta.limited` (boolean)
  - view/visibility schema:
    - `view` object with boolean flags:
      - `view_contractor`, `view_country`, `view_contract`, `view_stuff_category`, `view_shipping`, `view_payment`,
        `view_transport`, `view_transport_sum`, `view_custom`, `view_other_sum`, `view_montage_sum`,
        `view_montage_time`, `view_montage_cost`, `view_update_sum`, `view_summ_zak`, `view_koeff`,
        `view_user`, `view_department`
  - errors schema:
    - `{ error: string }` or HTML error page. **UNCONFIRMED** exact shape and status codes.
- Example payloads:
  - `docs/screens/margin/payloads/grid-fetch.request.json`
  - `docs/screens/margin/payloads/grid-fetch.response.json`

## Margin Generate (Build Session)
- Method: POST
- Path: `/MarginAction.do?dispatch=generate`
- Purpose: Build Margin session dataset based on filters/options.
- Auth/session notes: Requires legacy session; writes Margin session state. **UNCONFIRMED**.
- Request:
  - query params: none
  - body schema (URL-encoded form fields; **UNCONFIRMED** full list):
    - `date_begin` (string, required for enablement)
    - `date_end` (string, required for enablement)
    - `user.userFullName` (string)
    - `user.usr_id` (string/id)
    - `department.name` (string)
    - `department.id` (string/id)
    - `contractor.name` (string)
    - `contractor.id` (string/id)
    - `stuffCategory.name` (string)
    - `stuffCategory.id` (string/id)
    - `route.name` (string)
    - `route.id` (string/id)
    - aspect checkboxes: `user_aspect`, `department_aspect`, `contractor_aspect`, `stuff_category_aspect`, `route_aspect` (value `1` when checked)
    - option checkboxes: `onlyTotal`, `itog_by_spec`, `itog_by_user`, `itog_by_product`, `get_not_block` (value `1` when checked)
    - view flags: `view_*` (see Grid Data `view` list; value `1` when checked)
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: all filters in form body. **UNCONFIRMED** exact names/required fields.
- Response:
  - rows schema: HTML page (redirect or re-render). **UNCONFIRMED**.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - None (needs capture). **UNCONFIRMED**: request payload + response.

## Margin Clear All
- Method: POST
- Path: `/MarginAction.do?dispatch=cleanAll`
- Purpose: Clear Margin filters/session data.
- Auth/session notes: Requires legacy session. **UNCONFIRMED**.
- Request:
  - query params: none
  - body schema: empty or form body (unknown). **UNCONFIRMED**.
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: n/a
- Response:
  - rows schema: HTML page (redirect or re-render). **UNCONFIRMED**.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - None (needs capture). **UNCONFIRMED**.

## Margin Excel Export
- Method: GET
- Path: `/MarginAction.do?dispatch=generateExcel`
- Purpose: Download Excel report for current Margin session.
- Auth/session notes: Requires legacy session; uses current Margin session data. **UNCONFIRMED**.
- Request:
  - query params: none
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: uses server session only. **UNCONFIRMED**.
- Response:
  - rows schema: n/a (binary Excel download)
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED** content-type.
- Example payloads:
  - `docs/screens/margin/payloads/export.request.json`

## Users Lookup
- Method: GET
- Path: `/UsersListAction`
- Purpose: Lookup users for filter control.
- Auth/session notes: Uses legacy session; scope may be restricted by role. **UNCONFIRMED**.
- Request:
  - query params:
    - `filter` (string, optional search text)
    - `have_all` (string boolean, default `true`)
    - `dep_id` (string/id, optional; only for chief dep). **UNCONFIRMED**
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: query params only
- Response:
  - rows schema: HTML table list (serverList). **UNCONFIRMED** exact structure.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - `docs/screens/margin/payloads/lookups.request.json`
  - `docs/screens/margin/payloads/lookups.response.json`

## Departments Lookup
- Method: GET
- Path: `/DepartmentsListAction`
- Purpose: Lookup departments for filter control.
- Auth/session notes: Uses legacy session. **UNCONFIRMED**.
- Request:
  - query params:
    - `have_all` (string boolean, default `true`)
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: query params only
- Response:
  - rows schema: HTML table list (serverList). **UNCONFIRMED** exact structure.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - `docs/screens/margin/payloads/lookups.request.json`
  - `docs/screens/margin/payloads/lookups.response.json`

## Contractors Lookup
- Method: GET
- Path: `/ContractorsListAction`
- Purpose: Lookup contractors for filter control.
- Auth/session notes: Uses legacy session. **UNCONFIRMED**.
- Request:
  - query params:
    - `filter` (string, optional search text)
    - `have_all` (string boolean, default `true`)
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: query params only
- Response:
  - rows schema: HTML table list (serverList). **UNCONFIRMED** exact structure.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - `docs/screens/margin/payloads/lookups.request.json`
  - `docs/screens/margin/payloads/lookups.response.json`

## Stuff Categories Lookup
- Method: GET
- Path: `/StuffCategoriesListAction`
- Purpose: Lookup stuff categories for filter control.
- Auth/session notes: Uses legacy session. **UNCONFIRMED**.
- Request:
  - query params:
    - `filter` (string, optional search text)
    - `have_all` (string boolean, default `true`)
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: query params only
- Response:
  - rows schema: HTML table list (serverList). **UNCONFIRMED** exact structure.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - `docs/screens/margin/payloads/lookups.request.json`
  - `docs/screens/margin/payloads/lookups.response.json`

## Routes Lookup
- Method: GET
- Path: `/RoutesListAction`
- Purpose: Lookup routes for filter control.
- Auth/session notes: Uses legacy session. **UNCONFIRMED**.
- Request:
  - query params:
    - `have_all` (string boolean, default `true`)
  - body schema: n/a
  - sorting encoding: n/a
  - pagination encoding: n/a
  - filtering encoding: query params only
- Response:
  - rows schema: HTML table list (serverList). **UNCONFIRMED** exact structure.
  - total / summary schema: n/a
  - errors schema: HTML error page. **UNCONFIRMED**.
- Example payloads:
  - `docs/screens/margin/payloads/lookups.request.json`
  - `docs/screens/margin/payloads/lookups.response.json`
