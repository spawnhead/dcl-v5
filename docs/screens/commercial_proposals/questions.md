# commercial_proposals — Questions / Coverage Status

## VERIFIED (static trace)
1. **Route/action chain verified**:
   - Menu/JSP submits to `/CommercialProposalsAction.do` with dispatches `input|filter|edit|cloneLikeNewVersion|cloneLikeOldVersion|block|checkPrice|generateExcel`.
   - Struts mapping forwards edit/clone to `/CommercialProposalAction.do`.
   - Action flow: `CommercialProposalsAction` → `DAOUtils.fillGrid(..., "select-commercial_proposals", ...)` / `CommercialProposalDAO.saveBlock` / `CommercialProposalDAO.saveCheckPrice`.
2. **DB side-effects verified (code + SQL id level)**:
   - List/filter reads from SQL entry `select-commercial_proposals` (`dcl_commercial_proposal_filter(...)`).
   - `block` writes through `commercial_proposal-update-block`.
   - `checkPrice` writes through `commercial_proposal-update-checkPrice`, then removes economist messages via `MessageDAO.deleteConditionForContractMessagesForEconomist`.
3. **Role restrictions verified**:
   - Block allowed for admin/economist/creator when record not blocked.
   - CheckPrice allowed for admin/economist and only when `cpr_check_price != 1`.
   - Manager edit/clone is limited by department check (`dep_id`).

## UNCONFIRMED (needs runtime HAR)
1. Exact wire format per dispatch (full request headers/cookies, hidden fields, pagination params in real browser posts).
2. Exact HTML error rendering contract (status code + DOM location + localized message text).
3. Exact binary response headers for `generateExcel` in production legacy container.

## BLOCKER report (runtime)
- `payloads/network.har.BLOCKED.md` remains valid blocker: in current repo there is no runnable legacy launch pipeline (no legacy Maven/Gradle/Ant script + no packaged servlet container setup for `src/`), so mandatory HAR capture for this screen cannot be produced from this workspace alone.
- All static reverse-engineering bypasses exhausted: Struts mapping, Action, Form, DAO and SQL resources traced.

## How to verify next (once legacy runtime is available)
1. Capture HAR with content for all required dispatches from checklist A.
2. Export one raw request + one raw response sample per dispatch into `payloads/`.
3. Reconcile parameter names with `CommercialProposalsForm` + SQL placeholders, then mark runtime rows VERIFIED.
