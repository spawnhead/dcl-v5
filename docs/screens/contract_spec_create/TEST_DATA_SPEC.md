# N3a2 Contract specification create — Test data spec

## Domains
- Contract в session (draft или с con_id).
- DCL_CON_LIST_SPEC колонки: spc_number, spc_date, spc_summ, spc_summ_nds, delivery term, etc.

## Required
- users: ≥1 (UsersListAction).
- deliveryTerms: ≥1 (DeliveryTermTypesListAction).
- Contract в session с currency.name (для currencyName).

## Verification
- Open from /contracts/new → form loads, currencyName from Contract.
- Save → spec in Contract.grid; return to Contract.
