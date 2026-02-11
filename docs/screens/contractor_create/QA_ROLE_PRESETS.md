# N3a1 Contractor create — QA role presets

| Role | Access | Verify |
|------|--------|--------|
| admin | Full | Create contractor, save, return to contract |
| economist | Full | Same as admin |
| manager | Denied | ContractorAddActionContract not in permissions; кнопка «Добавить» на Contract скрыта или 403 |

## Source
- xml-permissions.xml:373 — ContractorAddActionContract: admin, economist.
