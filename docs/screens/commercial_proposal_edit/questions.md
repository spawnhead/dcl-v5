# commercial_proposal_edit — Questions / Coverage Status

## VERIFIED (static trace)
1. **Route/action/dispatch catalog verified**:
   - Main endpoint: `/CommercialProposalAction.do`.
   - Core dispatches: `input, edit, process, reload, print, printInvoice, printContract, newProduce, editProduce, importExcel, uploadTemplate`.
   - AJAX dispatches present in action: `ajaxProducesForAssembleMinskGrid`, `ajaxDeleteAllProducesForAssembleMinskGrid`, `ajaxRemoveFromProducesForAssembleMinskGrid`, `ajaxProducesCommercialProposalGrid`, `ajaxDeleteAllProducesCommercialProposalGrid`, `ajaxRemoveFromCommercialProposalGrid`, `ajaxChangeSalePriceForAssembleMinskGrid`, `ajaxChangeNDSByString`, `ajaxChangeFreePrices`, `ajaxChangeReverseCalc`, `ajaxChangeCalculate`, `ajaxChangeCourse`, `ajaxChangeNDS`, `ajaxChangeCurrency`, `ajaxRecalcForAssembleMinskGrid`, `ajaxGetTotal`, `ajaxRecalcCommercialProposalGrid`.
2. **DAO/SQL trace verified**:
   - Save path in `process` goes via `saveCommon(...)` and then `CommercialProposalDAO.insert/save` + related produces/messages operations.
   - Edit loads via `CommercialProposalDAO.load` (which loads produces/transport and dependent refs).
   - Import/attachment flows routed via dedicated forwards (`CommercialProposalImportAction`, deferred attach service).
3. **Business and UI post-effects verified in code**:
   - `process` branches by `printMode` and returns print/printInvoice/printContract/back/show accordingly.
   - `reload` recalculates totals and refreshes form; AJAX toggles mutate session model then return `ajax` forward.

## UNCONFIRMED (needs runtime HAR)
1. Exact request body shape for each AJAX dispatch in real browser session.
2. Exact server error model for validation failures in `process` and attachment/print failures.
3. Exact response payload format for each AJAX endpoint (plain text fragments vs HTML partial variants across environments).

## BLOCKER report (runtime)
- `payloads/network.har.BLOCKED.md` remains active blocker due to missing runnable legacy deployment artifacts in this workspace.
- Static reverse-completeness achieved at code level (Struts + Action + DAO + SQL resource ids), but runtime wire contract cannot be promoted to VERIFIED without legacy HTTP capture.

## How to verify next
1. Bring up legacy servlet runtime; run checklist B dispatches end-to-end.
2. Save HAR + extracted request/response examples per dispatch (including AJAX).
3. Record validation/system error samples (bad date, bad number, forced DB failure) and finalize error model.
