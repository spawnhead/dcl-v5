# Docs Migration Guide

## New structure
- Active implementation scope: `docs/screens/*` (MVP + Phase 1 only).
- Archived baseline/non-production-ready packs: `docs/archive/screens_baseline/*`.
- Central open questions: `docs/OPEN_QUESTIONS_MASTER.md`.
- Phasing plan: `docs/PHASE_PLAN.md`.

## Why archive
Archived packs were mostly baseline placeholders (`legacy-inferred`, broad UNCONFIRMED blocks) and created noise for implementation planning.

## How to restore archived screen
1. Move folder back from `docs/archive/screens_baseline/<slug>` to `docs/screens/<slug>`.
2. Add the screen to an appropriate phase in `docs/PHASE_PLAN.md`.
3. Populate evidence/contracts before marking production-ready.
