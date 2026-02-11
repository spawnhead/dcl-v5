# HAR capture — Contractor create (BLOCKED)

## Goal
Подтвердить legacy wire-format для полного 5-tab сценария:
- create/open,
- grid operations (users/accounts/contacts),
- process save + return to Contract.

## Capture steps
1. Запустить legacy и войти под admin.
2. Contracts -> Создать/Редактировать.
3. Нажать «Добавить» у контрагента.
4. Включить Preserve log + Disable cache.
5. Пройти вкладки:
   - Главная: заполнить required.
   - Курируют: add/delete user.
   - Счета: add/delete account, ошибки currency/account.
   - Контактные лица: create/edit/toggle fire/block.
   - Комментарии: заполнить comment.
6. Нажать Save.
7. Экспортировать HAR в `docs/screens/contractor_create/payloads/contractor-create-flow.har`.

## Must-see requests
- `ContractorAddActionContract.do?dispatch=create`
- `ContractorAddActionContract.do?dispatch=addRowInUserGrid`
- `ContractorAddActionContract.do?dispatch=deleteRowFromUserGrid`
- `ContractorAddActionContract.do?dispatch=addRowInAccountGrid`
- `ContractorAddActionContract.do?dispatch=deleteRowFromAccountGrid`
- `ContractorAddActionContract.do?dispatch=fireContactPerson`
- `ContractorAddActionContract.do?dispatch=blockContactPerson`
- `ContractorAddActionContract.do?dispatch=process`
- return `ContractAction.do?dispatch=retFromContractor`
