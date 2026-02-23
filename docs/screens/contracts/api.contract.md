# contracts — API contract status (legacy reverse)

## VERIFIED
- List endpoint: `POST /ContractsAction.do` with dispatch `input|filter|restore|selectCP` + pager handlers.
- Form endpoint: `POST /ContractAction.do` with dispatch `input|importCP|edit|process|back` (+ spec/attachment related).
- List DAO/SQL mapping verified: `select-contracts` (`dcl_contract_filter(...)`).
- Form DAO/SQL mapping verified: `contract-load|contract-insert|contract-update` + specification/payment SQL ids.

## PARTIALLY VERIFIED (runtime)
- Base list captures exist under `payloads/network.har`.
- Import CP path and basic list/filter are evidenced; not every sub-dispatch error path is captured.

## UNCONFIRMED
- Full error wire contract coverage for all subflows (attachments/spec edge cases).
- Complete per-dispatch payload archive for scope-D auxiliary operations.

## SQL constraint alignment
- Request payload fields must respect DB constraints for `DCL_CONTRACT`, `DCL_SPECIFICATION`, and related payment/attachment objects.
- Action/DAO traces already map these objects; remaining work is runtime payload sampling completeness.
