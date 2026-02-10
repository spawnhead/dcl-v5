# Orders List – Payloads

Артефакты для паритета и проверки API.

## Рекомендуемые артефакты

| Артефакт | Описание | Источник |
|----------|----------|----------|
| `list-request.json` | Параметры запроса списка (filter + page + pageSize + order_by). **Есть пример** в этой папке. | HAR: POST/GET к OrdersAction после «Применить фильтр» или к GET /api/orders |
| `list-response.json` | Ответ списка: items[], total, page, pageSize. **Есть пример** в этой папке (форма по CONTRACTS §1.2). | HAR: ответ того же запроса или ответ GET /api/orders |
| `list-empty.json` | Ответ при пустом результате (items=[], total=0) | После «Сбросить фильтр» или фильтр без совпадений |
| `lookups-*.json` | Ответы справочников: contractors, users, departments, stuff-categories, sellers, contracts?contractor_id=…, specifications?contract_id=… | HAR: запросы к соответствующим List actions или GET /api/... |

При необходимости:

- `list-request-with-state.json` — запрос с заполненными state_a/state_3/state_b/state_exclamation/state_c и executed/not_executed для проверки маппинга.
- `list-request-contract-spec.json` — запрос с contract и specification (для проверки id vs number). **How to verify:** В legacy выбрать contractor_for → contract → specification, нажать «Применить фильтр», сохранить из HAR параметры contractNumber/specificationNumber или contract.id/specification.id.

## Как захватить (legacy)

1. Открыть экран Заказы в браузере.
2. Заполнить часть фильтров (например номер, даты, контрагент).
3. Нажать «Применить фильтр».
4. В DevTools → Network сохранить:
   - запрос (form data или query string);
   - ответ (тело ответа списка заказов).
5. Сохранить в `list-request.json` / `list-response.json` в этой папке.
6. Для зависимых справочников: выбрать contractor_for → открыть Contract; сохранить запрос/ответ к ContractsDepFromContractorListAction как `lookups-contracts.json`. Аналогично specification → `lookups-specifications.json`.

## Как проверить (modern)

- Вызвать GET /api/orders с параметрами из `list-request.json` (привести имена параметров к контракту из CONTRACTS.md).
- Сравнить структуру ответа с CONTRACTS §1.2 и полями OrdersForm.Order / DCL_ORDER_FILTER RETURNS.
- Проверить пагинацию: page=2, pageSize=25 — те же поля, total не меняется.
- Проверить сброс: запрос без параметров или с clear=1 — items пустой или первая страница без фильтра.

Скриншоты (если нужны) не хранить в репозитории; см. `docs/screens/screenshots/README.md` при наличии.
