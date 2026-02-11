# HAR capture — Contract attachments (BLOCKED)

## Goal
Export HAR для flow «Прикрепить» → attachments grid → upload → download → delete → back.

## Steps to unblock
1. Legacy Contract form (/contracts/new или edit).
2. Click «Прикрепить».
3. DevTools Network → Preserve log.
4. Click «Прикрепить» (upload) → select file.
5. Click download link.
6. Click delete.
7. Export HAR.
8. Save to: `docs/screens/contract_attachments/payloads/attachments-flow.har`.

## Verify
- DeferredAttachmentsAction.do?dispatch=init (или show).
- DefferedUploadFileAction.do?dispatch=input (upload form).
- Upload URL, method, multipart.
- Download URL.
- Delete URL.

## При create (con_id=null)
- Проверить: куда уходит upload (temp storage? session?).
- После save Contract: появился ли файл в dcl_attachment с att_parent_id=con_id.
