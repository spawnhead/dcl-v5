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
 * This phase: in-memory session + fake data per CONTRACTS.md.
 */
@Service
public class MarginService {

    private static final int FAKE_ROW_COUNT = 250;
    private static final List<MarginLine> FAKE_LINES;

    static {
        MarginFakeDataProvider provider = new MarginFakeDataProvider();
        FAKE_LINES = provider.generate(FAKE_ROW_COUNT);
    }

    private final AtomicReference<SessionState> session = new AtomicReference<>(null);

    public void generate(MarginGenerateRequest request) {
        ViewFlags view = mapView(request != null && request.view() != null ? request.view() : java.util.Map.of());
        session.set(new SessionState(view));
    }

    public void cleanAll() {
        session.set(null);
    }

    public GridResult getData(int limit) {
        int max = clampLimit(limit);
        SessionState state = session.get();
        ViewFlags view = state != null ? state.view : ViewFlags.allVisible();
        int total = FAKE_LINES.size();
        List<MarginLine> slice = FAKE_LINES.stream().limit(max).toList();
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

    public List<LookupItem> getUsers(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("1", "Иванов И.И."),
            new LookupItem("2", "Петров П.П."),
            new LookupItem("3", "Сидоров С.С.")
        ), filter, haveAll);
    }

    public List<LookupItem> getDepartments(boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("1", "Продажи"),
            new LookupItem("2", "Закупки"),
            new LookupItem("3", "Логистика")
        ), null, haveAll);
    }

    public List<LookupItem> getContractors(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("1", "ООО Пример"),
            new LookupItem("2", "ИП Иванов"),
            new LookupItem("3", "ЗАО Тест")
        ), filter, haveAll);
    }

    public List<LookupItem> getStuffCategories(String filter, boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("1", "Категория 1"),
            new LookupItem("2", "Категория 2")
        ), filter, haveAll);
    }

    public List<LookupItem> getRoutes(boolean haveAll) {
        return filterLookup(List.of(
            new LookupItem("1", "Маршрут 1"),
            new LookupItem("2", "Маршрут 2")
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
