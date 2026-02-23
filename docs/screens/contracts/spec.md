# contracts (slug: `contracts`) — Legacy Screen Spec

## VERIFIED scope summary
- List JSP: `src/main/webapp/jsp/Contracts.jsp`.
- List endpoint: `/ContractsAction.do` with dispatches `input|filter|restore|selectCP` + pager handlers.
- Form endpoint: `/ContractAction.do` with `input|importCP|edit|process|back` (+ spec/attachment related dispatches).
- Import-from-CP route chain is mapped in Struts through `/SelectCPContractsAction.do` return forward to `ContractAction.do?dispatch=importCP`.

## Key behavior
- List filter/read uses SQL id `select-contracts` (`dcl_contract_filter(...)`).
- Edit permissions include manager department restriction (`dep_id_list` check).
- Form read-only/attach visibility depends on role (admin/economist/lawyer vs manager/lithuania/logistic).

## Runtime completeness
- Base HAR exists in `payloads/network.har`.
- Some edge/error subflows still UNCONFIRMED and tracked in `questions.md`.
