# unit — Expected API contracts (legacy-inferred)

- UNCONFIRMED endpoints (requires struts-config + runtime trace).

Error semantics: UNCONFIRMED (verify via legacy runtime/HAR).
<<<<<<< HEAD

## SQL constraint alignment (Patch 0.5+)
- Request payload fields must respect SQL types/lengths/NOT NULL from mapped tables.
- Example SQL constraints (from primary candidate table): `UNT_ID` INTEGER NOT NULL; `IS_ACCEPTABLE_FOR_CPR` CHAR(1) default '1' NOT NULL.

=======
>>>>>>> origin/main
