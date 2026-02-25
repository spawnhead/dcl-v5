# unit — DB invariants (enforced only)

- Screen-specific enforced DB invariants: UNCONFIRMED in this cycle.
- Verification plan:
  1. Map screen route -> action/DAO/procedure.
  2. Trace touched tables/procedures in `db/Lintera_dcl-5_schema.ddl`.
  3. Record only PK/UK/FK/NOT NULL/triggers/procedures that enforce rules.

## SQL RE-EVALUATION (Patch 0.5+)
- Source: `db/Lintera_dcl-5_schema.ddl` (SQL priority over UI).

### Table `DCL_UNIT`
- Columns (type): `UNT_ID` INTEGER NOT NULL; `IS_ACCEPTABLE_FOR_CPR` CHAR(1) default '1' NOT NULL
- NOT NULL: `UNT_ID`, `IS_ACCEPTABLE_FOR_CPR`
- Foreign Keys: UNCONFIRMED/none detected in direct parse for this table.
- Check Constraints: UNCONFIRMED/none detected.
- Trigger Logic: `DCL_UNIT_BI0`
- Stored Procedures: `DCL_UNIT_INSERT`; `DCL_UNIT_PACK`

### Table `DCL_UNIT_LANGUAGE`
- Columns (type): `UNT_ID` INTEGER NOT NULL; `LNG_ID` INTEGER NOT NULL; `UNT_NAME` VARCHAR(150)
- NOT NULL: `UNT_ID`, `LNG_ID`
- Foreign Keys: UNCONFIRMED/none detected in direct parse for this table.
- Check Constraints: UNCONFIRMED/none detected.
- Trigger Logic: UNCONFIRMED/none detected.
- Stored Procedures: UNCONFIRMED/none matched by name.

