# conditionsforcontract (slug: `conditionsforcontract`) — Legacy Screen Spec

## 1) Вход в экран (VERIFIED static)
- Primary JSP: `src/main/webapp/jsp/ConditionsForContract.jsp`.
- Struts mapping endpoint: `/ConditionsForContractAction.do` (list screen).
- Primary dispatches: `input`, `filter`, paging handlers, `markExecute`, `checkPrice`.
- Edit/create transitions go to `/ConditionForContractAction.do` (`input|edit|clone|process...`).

## 2) UI surface (VERIFIED from JSP/action)
- Filter fields: contractor, seller, user, date range, execute/check-price/annul flags.
- Grid columns include contractor/seller/user/execute/check-price/placement date.
- Row-style rule: annul rows rendered with `crossed-cell` class.

## 3) Dispatch catalog
- See `api.contract.md` + `questions.md` for full list and trace chain.

## 4) Validation and errors
- Static source for validators: `validation.xml` + action guards (`process`, `processForce`).
- Runtime-rendered error contract: UNCONFIRMED (requires HAR).

## 5) DB mapping summary
- List: SQL id `select-conditions_for_contract`.
- Entity save/update: ConditionForContractDAO SQL ids in `api.contract.md`.
- Final DB invariant enforcement list: see `db.invariants.md`.
