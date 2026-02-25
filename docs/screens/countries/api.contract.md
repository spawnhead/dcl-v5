# countries — API contracts (phase hardening)

- `/CountryAction.do?dispatch=create`
- `/CountryAction.do?dispatch=delete`
- `/CountryAction.do?dispatch=edit`

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Exact field-to-column mapping: UNCONFIRMED (requires action/DAO SQL trace).

