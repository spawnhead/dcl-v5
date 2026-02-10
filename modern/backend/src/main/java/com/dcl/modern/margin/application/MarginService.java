package com.dcl.modern.margin.application;

import com.dcl.modern.margin.api.MarginGenerateRequest;
import com.dcl.modern.margin.domain.MarginLine;
import com.dcl.modern.margin.domain.ViewFlags;
import com.dcl.modern.margin.infrastructure.MarginExcelExport;
import com.dcl.modern.margin.infrastructure.MarginFakeDataProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

/**
 * Margin report use cases. Legacy: MarginAction (generate/cleanAll), MarginDevDataAction (grid data).
 * This phase: in-memory session + deterministic data per TEST_DATA_SPEC.md and CONTRACTS.md.
 * CONTRACTS: when session is null (no generate yet or after cleanAll), grid data is empty.
 */
@Service
public class MarginService {

    private static final List<MarginLine> DETERMINISTIC_ROWS = MarginFakeDataProvider.buildDeterministicRows();

    private final AtomicReference<SessionState> session = new AtomicReference<>(null);

    public void generate(MarginGenerateRequest request) {
        ViewFlags view = mapView(request != null && request.view() != null ? request.view() : java.util.Map.of());
        session.set(new SessionState(view));
    }

    public void cleanAll() {
        session.set(null);
    }

    /** CONTRACTS: empty data when no session (initial load or after cleanAll). */
    public GridResult getData(int limit) {
        int max = clampLimit(limit);
        SessionState state = session.get();
        if (state == null) {
            return new GridResult(List.of(), ViewFlags.allVisible(), 0, 0, false);
        }
        ViewFlags view = state.view;
        int total = DETERMINISTIC_ROWS.size();
        List<MarginLine> slice = DETERMINISTIC_ROWS.stream().limit(max).toList();
        boolean limited = slice.size() < total;
        return new GridResult(slice, view, total, slice.size(), limited);
    }

    /** Excel export of current session data (CONTRACTS.md Margin Excel Export). */
    public byte[] exportExcel() {
        GridResult result = getData(1000);
        return MarginExcelExport.toBytes(result.data());
    }

    private static int clampLimit(int limit) {
        int max = Math.min(Math.max(limit, 50), 1000);
        if (max != 50 && max != 100 && max != 200 && max != 500 && max != 1000) {
            max = 200;
        }
        return max;
    }

    public record GridResult(List<MarginLine> data, ViewFlags view, long rowsTotal, int rowsReturned, boolean limited) {}

    /** TEST_DATA_SPEC + QA_ROLE_PRESETS: dev_admin, dev_manager, dev_manager_chief, dev_economist. */
    public List<LookupItem> getUsers(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("1001", "dev_admin (DEV)"),
            new LookupItem("1002", "dev_manager (DEV)"),
            new LookupItem("1003", "dev_manager_chief (DEV)"),
            new LookupItem("1004", "dev_economist (DEV)")
        ), filter, haveAll);
    }

    /** TEST_DATA_SPEC: 2–3 departments with (DEV). */
    public List<LookupItem> getDepartments(boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("2001", "Отдел продаж (DEV)"),
            new LookupItem("2002", "Экономический отдел (DEV)")
        ), null, haveAll);
    }

    /** TEST_DATA_SPEC: contractors with (DEV). */
    public List<LookupItem> getContractors(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("5001", "ООО Контрагент-A (DEV)"),
            new LookupItem("5002", "ООО Контрагент-B (DEV)"),
            new LookupItem("5003", "ООО ТестКонтрагент-1 (DEV)")
        ), filter, haveAll);
    }

    /** TEST_DATA_SPEC: stuff categories (DEV). */
    public List<LookupItem> getStuffCategories(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("4001", "Категория-A (DEV)"),
            new LookupItem("4002", "Категория-B (DEV)")
        ), filter, haveAll);
    }

    /** TEST_DATA_SPEC: routes (DEV). */
    public List<LookupItem> getRoutes(boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("3001", "Маршрут Минск–РБ (DEV)"),
            new LookupItem("3002", "Маршрут Минск–EU (DEV)")
        ), null, haveAll);
    }

    public record LookupItem(String id, String name) {}

    private static List<LookupItem> filterLookup(List<LookupItem> items, String filter, boolean haveAll) {
        List<LookupItem> out = new ArrayList<>();
        if (haveAll) {
            out.add(new LookupItem("", "— Все —"));
        }
        String f = filter != null ? filter.trim().toLowerCase(Locale.ROOT) : "";
        for (LookupItem item : items) {
            if (f.isEmpty() || item.name().toLowerCase(Locale.ROOT).contains(f)) {
                out.add(item);
            }
        }
        return out;
    }

    private static ViewFlags mapView(java.util.Map<String, Boolean> view) {
        return new ViewFlags(
            view.getOrDefault("view_contractor", true),
            view.getOrDefault("view_country", true),
            view.getOrDefault("view_contract", true),
            view.getOrDefault("view_stuff_category", true),
            view.getOrDefault("view_shipping", true),
            view.getOrDefault("view_payment", true),
            view.getOrDefault("view_transport", true),
            view.getOrDefault("view_transport_sum", true),
            view.getOrDefault("view_custom", true),
            view.getOrDefault("view_other_sum", true),
            view.getOrDefault("view_montage_sum", true),
            view.getOrDefault("view_montage_time", true),
            view.getOrDefault("view_montage_cost", true),
            view.getOrDefault("view_update_sum", true),
            view.getOrDefault("view_summ_zak", true),
            view.getOrDefault("view_koeff", true),
            view.getOrDefault("view_user", true),
            view.getOrDefault("view_department", true)
        );
    }

    private record SessionState(ViewFlags view) {}
}
