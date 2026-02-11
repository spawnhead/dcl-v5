# N3a1 Contractor create — Acceptance criteria (1:1)

## Parity MUST (FAIL если не выполнено)
1. Экран открывается по `/contractors/new?returnTo=contract` при клике «Добавить» у поля Контрагент на Contract form.
2. Main tab: ctr_name, ctr_full_name, country, address (index, region, place, street, building, add_info), ctr_phone, ctr_fax, ctr_email, ctr_unp, ctr_okpo, reputation.
3. ctr_name — обязательное; reputation — default из справочника.
4. При create: gridUsers содержит текущего user; gridAccounts — 3 default rows.
5. При save: contractor-insert; при returnTo=contract — redirect /contracts/new с newContractorId в query или эквивалент; Contract form получает contractor и подставляет в поле.
6. UNP duplicate: при существующем UNP — error.contractorpage.duplicate_unp, форма не сохраняется.
7. «Отмена» → возврат на /contracts/new без создания контрагента.
8. Роли: admin, economist (xml-permissions ContractorAddActionContract).

## Приёмочные сценарии

### 1) Open from Contract
- Trigger: Contract form, клик «Добавить» у contractor.
- Expected: /contractors/new?returnTo=contract, форма пустая, lookups загружены, gridUsers с текущим user, gridAccounts с 3 rows.

### 2) Save valid
- Trigger: заполнить ctr_name, reputation; Save.
- Expected: 200, ctrId; redirect /contracts/new?newContractorId=…; Contract form показывает выбранного contractor.

### 3) UNP duplicate
- Trigger: ctr_unp существующий в БД; Save.
- Expected: 400, error.contractorpage.duplicate_unp.

### 4) Cancel
- Trigger: Отмена.
- Expected: redirect /contracts/new, contractor не создан.
