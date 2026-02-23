# customcodehistory — Expected API contracts (legacy-inferred)

- `/CustomCodeFromHistoryAction.do`
- `/CustomCodeFromHistoryAction.do?dispatch=edit`
- `/CustomCodesAction.do?dispatch=execute`

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Exact field-to-column mapping: UNCONFIRMED (requires action/DAO SQL trace).

