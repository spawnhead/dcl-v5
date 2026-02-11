# N3a2 Contract specification create — QA role presets

| Role | Access | Verify |
|------|--------|--------|
| admin | Full | Add specification, save, return to contract |
| economist | Full | Same |
| lawyer | Full | Same |
| manager | Denied | No access to contract create; кнопка «Добавить Спецификацию» не видна |

## Source
- SpecificationAction доступна при доступе к Contract (admin, economist, lawyer).
