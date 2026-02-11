# N3a2 Contract specification create — Behavior matrix

| Scenario | Trigger | Expected network | Expected UI | Verify / Trace |
|----------|---------|------------------|-------------|----------------|
| Open from Contract | Click «Добавить Спецификацию» | GET /api/contracts/draft/specifications/new/open | Форма main tab, 1 payment row | SpecificationAction.insert |
| Save valid | Fill required, Save | POST /api/contracts/draft/specifications/save 200 | Redirect /contracts/new, grid +1 row | beforeSave, contract.insertSpecification |
| Validation | Empty spc_number, Save | POST ... 400 | Errors | validation.xml |
| Cancel | Click Отмена | Navigate /contracts/new | Return without add | back |

## UNCONFIRMED
- Wire format legacy.
- **HOW TO VERIFY:** HAR SpecificationAction.insert, beforeSave.
