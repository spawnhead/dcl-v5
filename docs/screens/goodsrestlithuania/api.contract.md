# goodsrestlithuania — Expected API contracts (legacy-inferred)

- `/GoodsRestLithuaniaAction.do?dispatch=delete`
- `/GoodsRestLithuaniaAction.do?dispatch=download`
- `/GoodsRestLithuaniaUploadFileAction.do?dispatch=input&referencedTable=${GoodsRestLithuania.referencedTable}&referencedID=${GoodsRestLithuania.referencedID}`

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Exact field-to-column mapping: UNCONFIRMED (requires action/DAO SQL trace).

