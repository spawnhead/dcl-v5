# N3a2 Contract specification create — Legacy Snapshot

> Дочерний экран: открывается по кнопке «Добавить Спецификацию» на форме Contract. Contract в session (con_id может быть null при create).

## 1) Идентификация
- Legacy route: SpecificationAction.do?dispatch=insert (после ContractAction.newSpecification).
- Tile/JSP: `.Specification` → `/jsp/Specification.jsp`.
- Modern route: `/contracts/draft/specifications/new` или `/contracts/:conId/specifications/new` (conId optional).
- Struts Action: SpecificationAction (insert, input, beforeSave, back).

## 2) UI-слепок main tab (порядок 1:1)

### 2.1 Скрытые поля
- spc_id, is_new_doc, old_number, spc_executed, spc_occupied, spc_occupied_in_pay_shp, payed_summ, spc_in_ctc, noRoundSum.

### 2.2 Main tab поля
| Поле | Контрол | Обязательность | Валидация | Дефолт |
|------|---------|----------------|-----------|--------|
| user | serverList UsersListAction | — | — | empty |
| spc_number | text 170px | required | maxlength 50 | "" |
| spc_date | date | required | mask, date | "" |
| spc_summ | text 170px | required | currency | "" |
| spc_summ_nds | text 170px | — | currency | "" |
| spc_delivery_cond | textarea 600x78 | — | maxlength 5000 | "" |
| deliveryTerm | serverList DeliveryTermTypesListAction | required | — | empty |
| spc_additional_days_count | text 63px | — | — | "" |
| spc_delivery_percent | text | — | currency | "" |
| spc_delivery_sum | text | — | currency | "" |
| spc_delivery_date | date | — | mask, date | "" |
| spc_add_pay_cond | textarea 600x78 | — | maxlength 5000 | "" |
| specificationPayments | grid (ajax) | — | — | 1 row (100, 0, currencyName) |
| spc_montage | checkbox | — | — | "" |
| spc_pay_after_montage | checkbox | — | — | "" |
| spc_fax_copy | checkbox | — | — | "" |
| spc_original | checkbox | — | — | "" |
| spc_comment | textarea | — | maxlength 5000 | "" |

currencyName передаётся из Contract (scriptUrl currencyName=$(currency.name) при newSpecification).

### 2.3 Другие вкладки (optional для N3a2-phase1)
- Produce: список продукции (grid).
- Attachments: прикреплённые файлы к спецификации.

### 2.4 Кнопки
- «Отмена» / «Назад» → back → ContractAction.retFromSpecificationOperation → Contract show.
- «Сохранить» → beforeSave → contract.insertSpecification(specification) → return to Contract show.

## 3) Flow
1. Contract form: клик «Добавить Спецификацию» → ContractAction.newSpecification (saveCurrentFormToBean) → forward newSpecification.
2. SpecificationAction.insert(): Contract из session, getEmptySpecification(), specification в session, form defaults (1 payment row), input().
3. User fills form, Save → beforeSave: validation, saveCurrentFormToBean → specification, contract.insertSpecification(specification), return show (Contract).

## 4) Traceability
- Specification.jsp: main tab 1–250+.
- SpecificationAction.insert(), beforeSave(), SpecificationForm.
- validation.xml: /SpecificationAction:beforeSave (1050–1130).
- Contract.insertSpecification(); ContractAction.retFromSpecificationOperation.
