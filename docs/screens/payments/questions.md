# payments — Questions / UNCONFIRMED

1. UNCONFIRMED: точный SQL тела `select-payments` (используется `DAOUtils.fillGrid(..., "select-payments", ...)`).
   - How to verify: открыть SQL map/ресурсы, где определён `select-payments`; сопоставить фильтры с параметрами `PaymentsForm`.
2. UNCONFIRMED: правила прав доступа по ролям на clone/edit/create для экрана платежей в полном объёме.
   - How to verify: разобрать `xml-permissions.xml` + runtime-проверки в action/service layer.
3. UNCONFIRMED: точные тексты/коды ошибок из backend при невалидных данных (кроме declarative validation.xml).
   - How to verify: воспроизвести POST в legacy с невалидными payload и снять HTTP/Struts ошибки через HAR/logs.
