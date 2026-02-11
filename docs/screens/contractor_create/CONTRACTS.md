# N3a1 Contractor create — API Contracts

> Источник: ContractorAction (create, process), contractor.jsp, contractor-insert.

## 1) GET `/api/contractors/create/open`

Назначение: загрузить форму создания контрагента (defaults, lookups). Query: `returnTo=contract` — возврат на Contract form после save.

Request: `GET /api/contractors/create/open?returnTo=contract`.

Response (200):
```json
{
  "defaults": {
    "ctrName": "",
    "ctrFullName": "",
    "country": null,
    "ctrIndex": "",
    "ctrRegion": "",
    "ctrPlace": "",
    "ctrStreet": "",
    "ctrBuilding": "",
    "ctrAddInfo": "",
    "ctrPhone": "",
    "ctrFax": "",
    "ctrEmail": "",
    "ctrUnp": "",
    "ctrOkpo": "",
    "reputation": null,
    "gridUsers": [{ "usrId": "string", "userFullName": "string" }],
    "gridAccounts": [
      { "accName": "Счёт 1", "accAccount": "", "currency": null },
      { "accName": "Счёт 2", "accAccount": "", "currency": null },
      { "accName": "Счёт валютный", "accAccount": "", "currency": null }
    ],
    "gridContactPersons": [],
    "ctrBankProps": "",
    "ctrComment": ""
  },
  "lookups": {
    "countries": [{ "id": "string", "name": "string" }],
    "reputations": [{ "id": "string", "description": "string" }],
    "users": [{ "id": "string", "userFullName": "string" }],
    "currencies": [{ "id": "string", "name": "string" }]
  },
  "returnTo": "contract"
}
```

Traceability: ContractorAction.create() → empty contractor, default accounts, current user in gridUsers.

## 2) POST `/api/contractors/create/save`

Назначение: сохранить контрагента. При returnTo=contract — response ctrId; вызывающая сторона (Contract form) должна refresh contractors list и подставить ctrId.

Request body:
```json
{
  "ctrName": "string",
  "ctrFullName": "string",
  "country": { "id": "string", "name": "string" },
  "ctrIndex": "",
  "ctrRegion": "",
  "ctrPlace": "",
  "ctrStreet": "",
  "ctrBuilding": "",
  "ctrAddInfo": "",
  "ctrPhone": "",
  "ctrFax": "",
  "ctrEmail": "",
  "ctrUnp": "string",
  "ctrOkpo": "",
  "reputation": { "id": "string", "description": "string" },
  "gridUsers": [{ "usrId": "string", "userFullName": "string" }],
  "gridAccounts": [{ "accName": "string", "accAccount": "string", "currency": { "id": "string", "name": "string" } }],
  "ctrBankProps": "",
  "ctrComment": "",
  "returnTo": "contract"
}
```

Validation:
- ctr_name: required.
- ctr_unp: duplicate check (ContractorDAO.loadByUNP) — error.contractorpage.duplicate_unp.
- gridAccounts: если acc_account заполнен и acc_name — один из дефолтных — currency required; maxlength 35 для acc_account.

Response (200):
```json
{
  "ctrId": "string",
  "redirectTo": "/contracts/new",
  "returnTo": "contract"
}
```

При returnTo=contract: SPA navigate на /contracts/new с query ?newContractorId={ctrId} или Contract form вызывает refresh contractors + set contractor by id.

## 3) Cancel
- Back без save: navigate /contracts/new (или returnTo URL).

## 4) UNCONFIRMED / How to verify
- Wire-формат legacy ContractorAddActionContract create/process: HTML form POST.
- **HOW TO VERIFY:** legacy Contract → Добавить → заполнить contractor → Save; Network DevTools — URL, request body, response.
