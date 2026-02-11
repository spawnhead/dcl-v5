# HAR capture — Specification create (BLOCKED)

## Goal
Export HAR для flow «Добавить Спецификацию» → Specification form → Save → return to Contract.

## Steps to unblock
1. Legacy Contract form (/contracts/new).
2. Fill contractor, currency, seller (min).
3. Click «Добавить Спецификацию».
4. DevTools Network → Preserve log.
5. Fill spc_number, spc_date, spc_summ, deliveryTerm; Save.
6. Export HAR.
7. Save to: `docs/screens/contract_spec_create/payloads/spec-create-flow.har`.

## Verify
- SpecificationAction.do?dispatch=insert (от newSpecification).
- SpecificationAction.do?dispatch=beforeSave (от Save).
