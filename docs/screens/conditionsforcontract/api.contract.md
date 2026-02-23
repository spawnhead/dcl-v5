# conditionsforcontract — API contract status (legacy reverse)

## VERIFIED (static)
- List endpoint: `POST /ConditionsForContractAction.do` with `input|filter|markExecute|checkPrice` (+ pager handlers).
- Form endpoint: `POST /ConditionForContractAction.do` with `input|edit|clone|process|processForce|reload|importExcel|uploadTemplate|selectCP|returnFromSelectCP` and related produce/attach dispatches.
- AJAX endpoints: `ajaxChangeContract`, `ajaxGetReputation`.
- SQL ids traced through DAO:
  - read/list: `select-conditions_for_contract`, `condition_for_contract-load`, `select-condition_for_contract_produces`;
  - writes: `condition_for_contract-insert`, `condition_for_contract-update`, `condition_for_contract-update-checkPrice`, `execute_condition_for_contract`, produce row insert/delete ids.

## UNCONFIRMED (runtime wire)
- Exact browser payloads and response wire format for all checklist-C operations.
- Exact validation/system error rendering contract.

## SQL constraint alignment
- Request fields must satisfy DB constraints for `DCL_CONDITION_FOR_CONTRACT` and dependent produce/message objects.
- Static trace to SQL ids completed; runtime contract samples pending HAR.
