# attachments — Expected API contracts (legacy-inferred)

- `/AttachmentsAction.do?dispatch=back`
- `/AttachmentsAction.do?dispatch=delete&referencedTable=${Attachments.referencedTable}`
- `/AttachmentsAction.do?dispatch=download`
- `/UploadFileAction.do?dispatch=input&referencedTable=${Attachments.referencedTable}&referencedID=${Attachments.referencedID}`

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Exact field-to-column mapping: UNCONFIRMED (requires action/DAO SQL trace).

