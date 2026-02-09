# Margin Screen — Snapshot (Spec)

**Status:** Stub. To be filled by Agent-Plan from legacy "Отчеты -> Маржа".

## Purpose
- Report screen "Margin" (Маржа) under Reports (Отчеты).
- Legacy source: trace from reports/margin entry point in legacy UI + network HAR.

## Required from Agent-Plan
1. **lookups**: Request parameters and response structure (e.g. dropdowns: period, contractor, etc.).
   - Place files: `payloads/lookups.request.json`, `payloads/lookups.response.json`
2. **Grid fetch**: Request (filters, pagination, sort) and response (rows, total count, columns).
   - Place files: `payloads/grid-fetch.request.json`, `payloads/grid-fetch.response.json`
3. **Export** (if any): Request/response or HAR snippet.
   - Place files: `payloads/export.request.json`, `payloads/export.response.json` (or describe in SNAPSHOT)
4. **UI parity checklist**: Filters (name, type, default, required), buttons, table columns (order, format, sort), pagination (pageSize options), totals row, empty/error/loading states.
5. **network.har**: Sample of legacy requests for this screen (optional but recommended).

## Parity acceptance checklist (MUST)
- [ ] Lookups endpoint: params + response shape match payloads
- [ ] Grid endpoint: filters, pagination (page/size), sort, response rows + total
- [ ] Export endpoint (if in legacy): request/response match
- [ ] UI: all filters, all buttons, table columns order/format, sort, pagination, totals, loading/empty/error
- [ ] Menu: Отчеты -> Маржа, route e.g. /reports/margin

Until payloads and the above are filled, implementation is **blocked**.
