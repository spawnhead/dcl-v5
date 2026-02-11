# N3a2 Contract specification create — API Contracts

> Источник: SpecificationAction.insert, beforeSave, Contract.insertSpecification.

## 1) GET `/api/contracts/draft/specifications/new/open`

Назначение: открыть форму создания спецификации при create contract (con_id=null). Contract в session. Query: currencyName из Contract.

Request: `GET /api/contracts/draft/specifications/new/open` (или session-based; currencyName из Contract form).

Response (200):
```json
{
  "defaults": {
    "user": null,
    "spcNumber": "",
    "spcDate": "",
    "spcSumm": "",
    "spcSummNds": "",
    "spcDeliveryCond": "",
    "deliveryTerm": null,
    "spcAdditionalDaysCount": "",
    "spcDeliveryPercent": "",
    "spcDeliverySum": "",
    "spcDeliveryDate": "",
    "spcAddPayCond": "",
    "specificationPayments": [{ "percent": 100, "delayDays": 0, "currencyName": "BYN" }],
    "spcMontage": false,
    "spcPayAfterMontage": false,
    "spcFaxCopy": false,
    "spcOriginal": false,
    "spcComment": ""
  },
  "lookups": {
    "users": [{ "id": "string", "userFullName": "string" }],
    "deliveryTerms": [{ "id": "string", "name": "string" }]
  },
  "currencyName": "BYN"
}
```

Traceability: SpecificationAction.insert() → contract.getEmptySpecification(), form payment 1 row.

## 2) POST `/api/contracts/draft/specifications/save`

Назначение: добавить спецификацию в session Contract (in-memory). Contract должен быть в session (из /contracts/new).

Request body:
```json
{
  "user": { "id": "string", "userFullName": "string" },
  "spcNumber": "string",
  "spcDate": "DD.MM.YYYY",
  "spcSumm": "string",
  "spcSummNds": "string",
  "spcDeliveryCond": "",
  "deliveryTerm": { "id": "string", "name": "string" },
  "spcAdditionalDaysCount": "",
  "spcDeliveryPercent": "",
  "spcDeliverySum": "",
  "spcDeliveryDate": "DD.MM.YYYY",
  "spcAddPayCond": "",
  "specificationPayments": [{ "percent": 100, "delayDays": 0, "currencyName": "BYN" }],
  "spcMontage": false,
  "spcPayAfterMontage": false,
  "spcFaxCopy": false,
  "spcOriginal": false,
  "spcComment": ""
}
```

Validation (из validation.xml):
- spc_number: required, maxlength 50.
- spc_date: required, date.
- spc_summ: required, currency.
- deliveryTerm.id: required.
- spc_delivery_cond, spc_add_pay_cond: maxlength 5000.
- spc_comment: maxlength 5000.

Response (200):
```json
{
  "success": true,
  "redirectTo": "/contracts/new"
}
```

Backend: contract.insertSpecification(specification); return to Contract form.

## 3) Cancel
- Back без save: navigate /contracts/new (или SPA state).

## 4) UNCONFIRMED / How to verify
- Wire-формат legacy SpecificationAction.insert → input, beforeSave.
- **HOW TO VERIFY:** HAR при Contract → Добавить Спецификацию → fill → Save.
