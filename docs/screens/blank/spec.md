# blank (slug: `blank`) — Legacy Screen Spec

## 1) Вход в экран
- Primary JSP source: `src/main/webapp/jsp/Blank.jsp`.
- Entry route(s): UNKNOWN (requires Struts mapping verification).

## 2) Что видит пользователь
- Экран основан на JSP и содержит UI-элементы/поля, перечисленные ниже.

### Поля (из JSP `property`)
- `bim_image`
- `bim_name`
- `blankType.name`
- `bln_charset`
- `bln_id`
- `bln_name`
- `bln_note`
- `bln_preamble`
- `bln_usage`
- `gridImages`
- `language.id`
- `language.name`
- `seller.name`
- `showForBlankType`

### Колонки/гриды (по JSP markup)
- `bim_name`

## 3) Действия
- См. `api.contract.md` (ожидаемые endpoint based on JSP links/forms).

## 4) Валидации и ошибки
- UNKNOWN: требуется сверка `validation.xml` и runtime HAR.

## 5) DB invariants
- См. `db.invariants.md`.

## 6) Unknowns
- См. `questions.md`.
