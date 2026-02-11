# HAR capture — Contractor create (BLOCKED)

## Goal
Export HAR для flow «Добавить» у контрагента → Contractor create → Save → return to Contract.

## Steps to unblock
1. Start legacy (Tomcat 8082).
2. Login admin/vip2u1ig.
3. Contracts → «Создать» → Contract form.
4. Click «Добавить» у поля Контрагент.
5. DevTools Network → Preserve log.
6. Fill ctr_name, reputation; Save.
7. Export HAR: Right-click → Save all as HAR with content.
8. Save to: `docs/screens/contractor_create/payloads/contractor-create-flow.har`.

## Verify
- Request: ContractorAddActionContract.do?dispatch=create (GET); ContractorAction.do?dispatch=process (POST).
- Response: redirect to ContractAction.do?dispatch=retFromContractor.
