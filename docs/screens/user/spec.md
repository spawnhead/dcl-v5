# user (slug: `user`) — Legacy Screen Spec

## 1) Вход в экран
- Primary JSP source: `src/main/webapp/jsp/user.jsp`.
- Entry route(s): UNKNOWN (requires Struts mapping verification).

## 2) Что видит пользователь
- Экран основан на JSP и содержит UI-элементы/поля, перечисленные ниже.

### Поля (из JSP `property`)
- `department.name`
- `lng_name`
- `usergrid`
- `usr_block`
- `usr_chief_dep`
- `usr_code`
- `usr_email`
- `usr_fax`
- `usr_id`
- `usr_internet_entry`
- `usr_local_entry`
- `usr_login`
- `usr_name`
- `usr_no_login`
- `usr_no_login_saved`
- `usr_passwd`
- `usr_passwd2`
- `usr_phone`
- `usr_position`
- `usr_respons_person`
- `usr_surname`

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
