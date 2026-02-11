# N3a2 Contract specification create — Acceptance criteria (1:1)

## Parity MUST (FAIL если не выполнено)
1. Экран открывается по клику «Добавить Спецификацию» на Contract form (при create или edit).
2. Main tab: user, spc_number, spc_date, spc_summ, spc_summ_nds, spc_delivery_cond, deliveryTerm, spc_additional_days_count, spc_delivery_percent/sum, spc_delivery_date, spc_add_pay_cond, specificationPayments grid, spc_montage, spc_pay_after_montage, spc_fax_copy, spc_original, spc_comment.
3. spc_number, spc_date, spc_summ, deliveryTerm — обязательные.
4. При save: spec добавляется в Contract grid (in-memory); return to Contract form; grid показывает новую строку.
5. «Отмена» → возврат на Contract form без добавления.
6. При create (con_id=null): Contract в session, specs в памяти до saveCommon Contract.

## Приёмочные сценарии

### 1) Open from Contract create
- Trigger: Contract form /contracts/new, клик «Добавить Спецификацию».
- Expected: форма спецификации, main tab, 1 payment row, currencyName из Contract.

### 2) Save valid
- Trigger: заполнить spc_number, spc_date, spc_summ, deliveryTerm; Save.
- Expected: 200, redirect /contracts/new; Contract grid содержит новую строку.

### 3) Validation
- Trigger: пустые spc_number или spc_date; Save.
- Expected: 400, сообщения об ошибках.

### 4) Cancel
- Trigger: Отмена.
- Expected: return /contracts/new, spec не добавлена.
