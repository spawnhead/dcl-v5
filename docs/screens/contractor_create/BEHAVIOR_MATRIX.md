# N3a1 Contractor create — Behavior matrix

| Scenario | Trigger | Expected network | Expected UI | Verify / Trace |
|----------|---------|------------------|-------------|----------------|
| Open from Contract | Click «Добавить» у contractor | GET /api/contractors/create/open?returnTo=contract | Форма пустая, main tab, gridUsers 1 row, gridAccounts 3 rows | ContractorAction.create |
| Save valid | Fill ctr_name, reputation, Save | POST /api/contractors/create/save 200 | ctrId, redirect /contracts/new?newContractorId=…; Contract form contractor selected | ContractorAction.process, currentContractorId |
| UNP duplicate | ctr_unp exists, Save | POST ... 400 | error.contractorpage.duplicate_unp | ContractorDAO.loadByUNP |
| Cancel | Click Отмена | Navigate /contracts/new | Return without save | forward back |

## UNCONFIRMED
- Wire format legacy: HTML form POST.
- **HOW TO VERIFY:** HAR при ContractorAddActionContract create/process.
