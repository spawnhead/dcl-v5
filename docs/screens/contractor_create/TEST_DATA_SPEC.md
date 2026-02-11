# N3a1 Contractor create — Test data spec

## Domains
- DCL_CONTRACTOR, DCL_COUNTRY, DCL_REPUTATION (или эквивалент), DCL_CURRENCY.

## Required for contractor create
- countries: ≥1 (CountriesListAction).
- reputations: ≥1 default (ReputationDAO.loadDefaultForCtc).
- users: ≥1 (текущий user для gridUsers).
- currencies: ≥1 (для accounts).

## UNP uniqueness
- ctr_unp unique в DCL_CONTRACTOR (или UK/index).
- Для теста duplicate: создать contractor с UNP="123456789" до теста; второй save с тем же UNP → 400.

## Verification
- GET /api/contractors/create/open → defaults, lookups populated.
- POST save с ctr_name + reputation → 200, ctrId.
- POST save с duplicate ctr_unp → 400.
