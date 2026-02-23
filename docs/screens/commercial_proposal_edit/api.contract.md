# commercial_proposal_edit — API contract status (legacy reverse)

## VERIFIED (static)
- Endpoint: `POST /CommercialProposalAction.do`.
- Core dispatches: `input`, `edit`, `process`, `reload`, `print`, `printInvoice`, `printContract`, `newProduce`, `editProduce`, `importExcel`, `uploadTemplate`.
- AJAX dispatch family verified in action class (produces grids/toggles/recalc/total/currency/nds/free-prices/reverse-calc).
- Save path: `process` → `saveCommon(...)` → DAO (`CommercialProposalDAO.insert/save`) + related produce/message persistence.

## UNCONFIRMED (runtime wire)
- Exact per-dispatch request/response payload contracts in browser runtime (especially AJAX partials).
- Exact validation/system error response format and status code behavior.
- Exact binary print/download response headers (PDF/XLS/attachment cases).

## SQL constraint alignment
- Main persistence object: `DCL_COMMERCIAL_PROPOSAL` + dependent produce/transport/message tables and procedures from sql-resources mappings.
- Field constraints remain DB-governed; runtime request contracts require HAR confirmation.
