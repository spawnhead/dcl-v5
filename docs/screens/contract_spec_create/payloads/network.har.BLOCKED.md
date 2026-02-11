# HAR capture — Specification create (BLOCKED)

## Goal
Подтвердить wire-format 1:1 для:
- insert/beforeSave/process flow,
- ajax payment/date/reminder endpoints,
- attachment copy/upload/delete/download.

## Capture steps (legacy)
1. Запустить legacy + войти под admin.
2. Открыть Contract create/edit.
3. Нажать «Добавить спецификацию».
4. В DevTools включить **Preserve log** и **Disable cache**.
5. На Main tab:
   - заполнить required;
   - добавить/удалить строку платежа;
   - поменять дату, delivery term, additional days;
   - попробовать attach copy и attach file.
6. На tab «Претензии» заполнить даты/комментарий.
7. Нажать Save.
8. Экспортировать HAR с content в `docs/screens/contract_spec_create/payloads/spec-create-flow.har`.

## Must-see requests
- `SpecificationAction.do?dispatch=insert`
- `SpecificationAction.do?dispatch=ajaxSpecificationPaymentsGrid`
- `SpecificationAction.do?dispatch=ajaxAddToPaymentGrid`
- `SpecificationAction.do?dispatch=ajaxRemoveFromPaymentGrid`
- `SpecificationAction.do?dispatch=ajaxRecalculatePaymentGrid`
- `SpecificationAction.do?dispatch=ajaxReloadDate`
- `SpecificationAction.do?dispatch=ajaxReloadReminder`
- `SpecificationAction.do?dispatch=ajaxCalculateDeliveryDate`
- `SpecificationAction.do?dispatch=deferredAttachCopy`
- `SpecificationAction.do?dispatch=deferredAttach`
- `SpecificationAction.do?dispatch=deleteAttachment`
- `SpecificationAction.do?dispatch=beforeSave`
