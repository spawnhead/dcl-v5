# Margin Screen — Implementation Notes

## Status: BLOCKED

**Reason:** `docs/screens/margin/SNAPSHOT.md` is a stub; `payloads/*.json` are missing.  
Contract shapes (request/response DTOs) cannot be defined without them.  
Per task rules: parity 1:1 and no placeholders — implementation waits on Agent-Plan producing the spec.

## Parity acceptance checklist (from SNAPSHOT)

| MUST | Status | Notes |
|------|--------|--------|
| Lookups endpoint (params + response) | NOT DONE | No lookups.request/response.json |
| Grid endpoint (filters, pagination, sort, rows+total) | NOT DONE | No grid-fetch.request/response.json |
| Export endpoint (if in legacy) | NOT DONE | Not specified |
| UI: filters, buttons, table, sort, pagination, totals | NOT DONE | No spec |
| UI: loading/empty/error states | NOT DONE | No spec |
| Menu: Отчеты -> Маржа, route /reports/margin | DONE | Route and menu item added; page shows blocker message |

## Implemented

- **Route:** `/reports/margin` (e.g. in App router).
- **Menu:** "Отчеты" -> "Маржа" (if menu structure exists).
- **Margin page:** Placeholder page that states spec is missing and links to this doc.
- **Docs:** `SNAPSHOT.md` (stub), `payloads/README.md`, this file.

## Next steps to unblock

1. Agent-Plan (or manual) capture legacy "Отчеты -> Маржа" and produce:
   - Filled `SNAPSHOT.md` (UI checklist, endpoint descriptions).
   - `payloads/lookups.request.json`, `lookups.response.json`.
   - `payloads/grid-fetch.request.json`, `grid-fetch.response.json`.
   - Export payloads if applicable.
2. Then: implement backend margin module (fake data) and UI 1:1 per spec.

## Logs

- Backend: logs/dev-margin-backend.log (skipped until margin module exists).
- UI: logs/dev-margin-ui.log (note: route/menu added, parity blocked).
