# goodsrestlithuania — DB invariants (enforced only)

- Screen-specific enforced DB invariants: UNCONFIRMED in this cycle.
- Verification plan:
  1. Map screen route -> action/DAO/procedure.
  2. Trace touched tables/procedures in `db/Lintera_dcl-5_schema.ddl`.
  3. Record only PK/UK/FK/NOT NULL/triggers/procedures that enforce rules.

## SQL RE-EVALUATION (Patch 0.5+)
- Source: `db/Lintera_dcl-5_schema.ddl` (SQL priority over UI).

- Relevant table mapping: UNCONFIRMED (manual mapping required).
- Foreign Keys: UNCONFIRMED.
- Check Constraints: UNCONFIRMED.
- Trigger Logic: UNCONFIRMED.
- Stored Procedures: UNCONFIRMED.

