# HAR capture — BLOCKED (manual steps)

## Goal
Export HAR for flow «Импорт из КП» to `import-cp-flow.har`.

## Steps to unblock

1. **Start legacy app** (Tomcat on 8082):
   ```bash
   # из корня проекта, если legacy запускается из IDE — запустить там
   ```

2. **Open Chrome DevTools** → Network tab → Preserve log.

3. **Login:**
   - URL: `http://localhost:8082/trusted/Login.do?dispatch=input`
   - Username: `admin`
   - Password: `vip2u1ig`
   - Submit form.

4. **Navigate to Contracts:**
   - Menu: Contracts (или `http://localhost:8082/Menu.do?current_menu_id=id.contractDoc`).

5. **Click «Импорт из КП»** (или equivalent: selectCP / minsk_store=1).

6. **Wait for grid to load**, optionally apply filter.

7. **Export HAR:**
   - DevTools → Network → Right-click → «Save all as HAR with content».
   - Save to: `docs/screens/contract_import_cp/payloads/import-cp-flow.har`.

8. **Optional:** click one row (select CP) → repeat export to capture select flow.

## Login note
If login page differs (`/Login.do` vs `/trusted/Login.do`), try both and document which works.
