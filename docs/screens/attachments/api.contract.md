# attachments — Expected API contracts (legacy-inferred)

- `/AttachmentsAction.do?dispatch=back`
- `/AttachmentsAction.do?dispatch=delete&referencedTable=${Attachments.referencedTable}`
- `/AttachmentsAction.do?dispatch=download`
- `/UploadFileAction.do?dispatch=input&referencedTable=${Attachments.referencedTable}&referencedID=${Attachments.referencedID}`

Error semantics: UNKNOWN (verify via legacy runtime/HAR).
