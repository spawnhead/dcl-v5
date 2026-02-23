# deliveryrequests — Expected API contracts (legacy-inferred)

- `/DeliveryRequestAction.do?dispatch=delete`
- `/DeliveryRequestAction.do?dispatch=edit&direction=${DeliveryRequests.direction}`
- `/DeliveryRequestAction.do?dispatch=input&direction=${DeliveryRequests.direction}`
- `/UsersListAction`

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Exact field-to-column mapping: UNCONFIRMED (requires action/DAO SQL trace).

