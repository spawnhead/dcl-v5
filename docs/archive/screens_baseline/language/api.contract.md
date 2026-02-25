# language — Expected API contracts (legacy-inferred)

- UNCONFIRMED endpoints (requires struts-config + runtime trace).

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Example SQL constraints (from primary candidate table): `LNG_ID` INTEGER NOT NULL; `LNG_NAME` VARCHAR(32) NOT NULL; `LNG_LETTER_ID` VARCHAR(2) NOT NULL.

