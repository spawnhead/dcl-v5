# paysum (slug: `paysum`) — Legacy Screen Spec

## 1) Вход в экран
- Primary JSP source: `src/main/webapp/jsp/PaySum.jsp`.
- Entry route(s): UNKNOWN (requires Struts mapping verification).

## 2) Что видит пользователь
- Экран основан на JSP и содержит UI-элементы/поля, перечисленные ниже.

### Поля (из JSP `property`)
- `contract.annulStr`
- `contractNumberWithDate`
- `ctr_id`
- `ctr_name`
- `cur_id`
- `lps_occupied`
- `lps_summ`
- `number`
- `pay_summ_nr`
- `specification.annulStr`
- `specification.spc_summ_nr_formatted`
- `specificationNumberWithDate`

### Колонки/гриды (по JSP markup)
- UNKNOWN

## 3) Действия
- См. `api.contract.md` (ожидаемые endpoint based on JSP links/forms).

## 4) Валидации и ошибки
- UNKNOWN: требуется сверка `validation.xml` и runtime HAR.

## 5) DB invariants
- См. `db.invariants.md`.

## 6) Unknowns
- См. `questions.md`.
