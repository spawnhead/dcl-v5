# languages — Expected API contracts (legacy-inferred)

- `/LanguageAction.do?dispatch=edit`
- `/LanguageAction.do?dispatch=input`

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Exact field-to-column mapping: UNCONFIRMED (requires action/DAO SQL trace).

