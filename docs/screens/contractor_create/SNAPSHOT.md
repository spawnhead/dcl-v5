# N3a1 Contractor create (from Contract) — Legacy Snapshot

> Дочерний экран: открывается по кнопке «Добавить» у поля Контрагент на форме Contract. Traceability: contractor.jsp, ContractorAction, ContractorForm.

## 1) Идентификация
- Legacy route: ContractorAddActionContract.do?dispatch=create.
- Tile/JSP: `.contractor` → `/jsp/contractor.jsp`.
- Modern route: `/contractors/new?returnTo=contract`.
- Struts Action: `net.sam.dcl.action.ContractAction` newContractor → `ContractorAction` (create, process, back).
- Permissions: xml-permissions.xml:373 — admin, economist; ContractorAddActionContract в списке.

## 2) UI-слепок формы (порядок 1:1)

### 2.1 Скрытые поля
- ctr_id, is_new_doc, usr_date_create, usr_date_edit, createUser, editUser, lastNumber, lastNumberAcc.

### 2.2 Main tab (Contractor.main)
| Поле | Контрол | Обязательность | Валидация | Дефолт |
|------|---------|----------------|-----------|--------|
| ctr_name | text 400px | required | — | "" |
| ctr_full_name | text 400px | — | — | "" |
| country | serverList CountriesListAction + кнопка addAbsentInList | — | — | empty |
| ctr_address | text readonly (computed) | — | — | from index+region+place+street+building+add_info |
| ctr_index | text 100px | — | — | "" |
| ctr_region | text 170px | — | — | "" |
| ctr_place | text 285px | — | — | "" |
| ctr_street | text 170px | — | — | "" |
| ctr_building | text 95px | — | — | "" |
| ctr_add_info | text 217px | — | — | "" |
| ctr_phone | text 400px | — | — | "" |
| ctr_fax | text 400px | — | — | "" |
| ctr_email | text 400px | — | — | "" |
| ctr_unp | text 400px | — | duplicate check | "" |
| ctr_okpo | text 400px | — | — | "" |
| reputation | serverList ReputationsListAction + кнопка editList | required | — | default from ReputationDAO.loadDefaultForCtc |

### 2.3 Users tab (Contractor.users)
- gridUsers: user (UsersListAction), Delete.
- Кнопка «Добавить» → addRowInUserGrid.
- При create: 1 row с текущим user.

### 2.4 Accounts tab (Contractor.accounts)
- ctr_bank_props: textarea 400px.
- gridAccounts: acc_name, acc_account, currency (CurrenciesListAction), Delete.
- Кнопка «Добавить» → addRowInAccountGrid.
- При create: 3 default rows (ctr_account1, ctr_account2, ctr_account_val).
- Validation: если acc_account заполнен — currency обязательна; maxlength 35 для account.

### 2.5 Contact persons tab
- gridContactPersons: cps_name, cps_position, cps_on_reason, cps_phone, cps_mob_phone, cps_fax, cps_email, cps_contract_comment, cps_fire, cps_block, Edit.
- Кнопка «Создать» → addPersonInContractor.
- При create: пустой grid.

### 2.6 Comment tab
- ctr_comment: textarea 400x305.

### 2.7 Кнопки
- «Отмена» → back → ContractAction.retFromContractor (без contractor).
- «Сохранить» → process → contractor-insert → session currentContractorId=ctr_id → forward back → ContractAction.retFromContractor.

## 3) Return flow
- ContractorAction.process() после save: session.setAttribute(Contractor.currentContractorId, form.getCtr_id()).
- forward back → ContractAction.retFromContractor(): читает currentContractorId, ContractorDAO.load, form.setContractor, show Contract.

## 4) Traceability
- contractor.jsp: строки 1–367.
- ContractorAction.create(), process(), ContractorForm.
- validation.xml: /ContractorAction:process (3649).
- sql-resources: contractor-insert.
