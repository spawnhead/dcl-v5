# BLOCKED: legacy HAR capture for Contracts list

Причина блокировки: в текущем окружении отсутствует доступ к запущенному legacy UI endpoint (нельзя открыть `/ContractsAction.do` и снять браузерный HAR).

Что нужно для разблокировки:
1. URL доступного legacy стенда.
2. Тестовый пользователь с правами на `id.contractDoc`.
3. Возможность открыть DevTools Network и экспортировать HAR.

Что именно снять:
- initial load (`dispatch=input`),
- apply filter (`dispatch=filter`),
- clear (`dispatch=input`),
- pager next/prev,
- lookup calls из `contractor/user/seller` serverList,
- validation error (невалидная дата).
