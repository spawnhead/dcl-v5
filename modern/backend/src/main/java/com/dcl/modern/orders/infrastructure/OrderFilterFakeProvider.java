package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.domain.OrderRow;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Deterministic fake data for Orders list when DCL_ORDER_FILTER is not in Postgres.
 * CONTRACTS §1.2 shape; ord_annul, is_warn for row styles.
 * TEST_DATA_SPEC: rows 1–45 = 2024; 46–55 = 2025-02-01..2025-02-10; 56–70 = 2026 (2 years coverage).
 */
public final class OrderFilterFakeProvider {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

    private static final int ROWS_2024 = 45;
    private static final int ROWS_2025_FEB = 10;
    private static final int ROWS_2026 = 10;
    private static final int TOTAL_ROWS = ROWS_2024 + ROWS_2025_FEB + ROWS_2026;

    private static final String[] CONTRACTORS = { "ООО Контрагент-A (DEV)", "ООО Контрагент-B (DEV)", "ООО ТестКонтрагент (DEV)" };
    private static final String[] CONTRACTORS_FOR = { "Клиент 1", "Клиент 2", "Клиент 3" };
    private static final String[] USERS = { "dev_admin (DEV)", "dev_manager (DEV)", "dev_economist (DEV)" };
    private static final String[] DEPTS = { "Отдел продаж (DEV)", "Экономический отдел (DEV)" };

    /** Parse date string: DD.MM.YYYY (CONTRACTS) or ISO YYYY-MM-DD. Returns null if invalid/empty. */
    public static LocalDate parseDateParam(String value) {
        if (value == null || value.isBlank()) return null;
        String v = value.trim();
        try {
            if (v.length() == 10 && v.charAt(2) == '.' && v.charAt(5) == '.') {
                return LocalDate.parse(v, DD_MM_YYYY);
            }
            return LocalDate.parse(v, ISO);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Deterministic rows: 2024 (1–45), 2025-02-01..10 (46–55), 2026 (56–70). May beacons for QA. */
    public static List<OrderRow> buildRows() {
        LocalDate base2024 = LocalDate.of(2024, 1, 1);
        LocalDate base2025Feb = LocalDate.of(2025, 2, 1);
        LocalDate base2026 = LocalDate.of(2026, 1, 1);

        return Stream.iterate(1, i -> i <= TOTAL_ROWS, i -> i + 1)
            .map(i -> {
                LocalDate ordDate;
                LocalDate ordDateConf;
                if (i <= ROWS_2024) {
                    ordDate = base2024.plusDays((i - 1) % 28);
                    ordDateConf = base2024.plusDays(5 + (i - 1) % 20);
                } else if (i <= ROWS_2024 + ROWS_2025_FEB) {
                    int d = i - ROWS_2024 - 1;
                    ordDate = base2025Feb.plusDays(d);
                    ordDateConf = base2025Feb.plusDays(d + 2);
                } else {
                    int d = (i - ROWS_2024 - ROWS_2025_FEB - 1) % 28;
                    ordDate = base2026.plusDays(d);
                    ordDateConf = base2026.plusDays(d + 5);
                }
                boolean annul = (i % 7 == 0);
                boolean warn = (i % 5 == 2);
                int depId = (i % 2 == 0) ? 2001 : 2002;
                return new OrderRow(
                    i,
                    "ORD-" + String.format("%05d", 10000 + i),
                    ordDate,
                    CONTRACTORS[i % CONTRACTORS.length],
                    CONTRACTORS_FOR[i % CONTRACTORS_FOR.length],
                    BigDecimal.valueOf(1000 + i * 100),
                    ordDateConf,
                    null,
                    null,
                    null,
                    i % 3 == 0 ? ordDate.plusDays(10) : null,
                    i % 3 == 0 ? 1 : 0,
                    i % 4 == 0 ? ordDate.plusDays(15) : null,
                    USERS[i % USERS.length],
                    DEPTS[i % DEPTS.length],
                    warn ? "1" : "0",
                    i % 6 == 0 ? "1" : "0",
                    annul ? 1 : 0,
                    i % 5 == 0 ? "CNF-" + i : null,
                    depId,
                    1,
                    i % 4 == 1 ? 1 : 0,
                    "0",
                    i % 10,
                    null,
                    null,
                    String.valueOf(1000 + (i % 4))
                );
            })
            .toList();
    }

    /** Filter (number, date range, contractor, contractor_for, user, department), sort, then page. CONTRACTS: date_begin/date_end DD.MM.YYYY, inclusive. */
    public static List<OrderRow> filterSortPage(
        List<OrderRow> all,
        String number,
        String dateBegin,
        String dateEnd,
        String contractorId,
        String contractorForId,
        String userId,
        String departmentId,
        String orderBy,
        int page,
        int pageSize
    ) {
        Stream<OrderRow> stream = all.stream();
        LocalDate fromDate = parseDateParam(dateBegin);
        LocalDate toDate = parseDateParam(dateEnd);
        if (fromDate != null) {
            LocalDate f = fromDate;
            stream = stream.filter(r -> r.ord_date() != null && !r.ord_date().isBefore(f));
        }
        if (toDate != null) {
            LocalDate t = toDate;
            stream = stream.filter(r -> r.ord_date() != null && !r.ord_date().isAfter(t));
        }
        if (number != null && !number.isBlank()) {
            String num = number.trim();
            stream = stream.filter(r -> r.ord_number() != null && r.ord_number().contains(num));
        }
        if (contractorId != null && !contractorId.isBlank()) {
            String matchName = "5001".equals(contractorId) ? CONTRACTORS[0]
                : "5002".equals(contractorId) ? CONTRACTORS[1]
                : "5003".equals(contractorId) ? CONTRACTORS[2]
                : null;
            if (matchName != null) {
                stream = stream.filter(r -> matchName.equals(r.ord_contractor()));
            }
        }
        if (contractorForId != null && !contractorForId.isBlank()) {
            String matchFor = "5001".equals(contractorForId) ? CONTRACTORS_FOR[0]
                : "5002".equals(contractorForId) ? CONTRACTORS_FOR[1]
                : "5003".equals(contractorForId) ? CONTRACTORS_FOR[2]
                : null;
            if (matchFor != null) {
                stream = stream.filter(r -> matchFor.equals(r.ord_contractor_for()));
            }
        }
        if (userId != null && !userId.isBlank()) {
            String matchSub = "1001".equals(userId) ? "dev_admin" : "1002".equals(userId) ? "dev_manager" : "1003".equals(userId) ? "dev_manager_chief" : "1004".equals(userId) ? "dev_economist" : null;
            if (matchSub != null) {
                stream = stream.filter(r -> r.ord_user() != null && r.ord_user().contains(matchSub));
            }
        }
        if (departmentId != null && !departmentId.isBlank()) {
            stream = stream.filter(r -> r.dep_id() != null && r.dep_id().toString().equals(departmentId));
        }

        Comparator<OrderRow> cmp = comparatorFromOrderBy(orderBy);
        List<OrderRow> sorted = stream.sorted(cmp).toList();
        int total = sorted.size();
        int from = (page - 1) * pageSize;
        if (from >= total) {
            return List.of();
        }
        int to = Math.min(from + pageSize, total);
        return sorted.subList(from, to);
    }

    public static long countFiltered(
        List<OrderRow> all,
        String number,
        String dateBegin,
        String dateEnd,
        String contractorId,
        String contractorForId,
        String userId,
        String departmentId
    ) {
        Stream<OrderRow> stream = all.stream();
        LocalDate fromDate = parseDateParam(dateBegin);
        LocalDate toDate = parseDateParam(dateEnd);
        if (fromDate != null) {
            LocalDate f = fromDate;
            stream = stream.filter(r -> r.ord_date() != null && !r.ord_date().isBefore(f));
        }
        if (toDate != null) {
            LocalDate t = toDate;
            stream = stream.filter(r -> r.ord_date() != null && !r.ord_date().isAfter(t));
        }
        if (number != null && !number.isBlank()) {
            String num = number.trim();
            stream = stream.filter(r -> r.ord_number() != null && r.ord_number().contains(num));
        }
        if (contractorId != null && !contractorId.isBlank()) {
            String matchName = "5001".equals(contractorId) ? CONTRACTORS[0]
                : "5002".equals(contractorId) ? CONTRACTORS[1]
                : "5003".equals(contractorId) ? CONTRACTORS[2]
                : null;
            if (matchName != null) {
                stream = stream.filter(r -> matchName.equals(r.ord_contractor()));
            }
        }
        if (contractorForId != null && !contractorForId.isBlank()) {
            String matchFor = "5001".equals(contractorForId) ? CONTRACTORS_FOR[0]
                : "5002".equals(contractorForId) ? CONTRACTORS_FOR[1]
                : "5003".equals(contractorForId) ? CONTRACTORS_FOR[2]
                : null;
            if (matchFor != null) {
                stream = stream.filter(r -> matchFor.equals(r.ord_contractor_for()));
            }
        }
        if (userId != null && !userId.isBlank()) {
            String matchSub = "1001".equals(userId) ? "dev_admin" : "1002".equals(userId) ? "dev_manager" : "1003".equals(userId) ? "dev_manager_chief" : "1004".equals(userId) ? "dev_economist" : null;
            if (matchSub != null) {
                stream = stream.filter(r -> r.ord_user() != null && r.ord_user().contains(matchSub));
            }
        }
        if (departmentId != null && !departmentId.isBlank()) {
            stream = stream.filter(r -> r.dep_id() != null && r.dep_id().toString().equals(departmentId));
        }
        return stream.count();
    }

    private static Comparator<OrderRow> comparatorFromOrderBy(String orderBy) {
        if (orderBy == null || orderBy.isBlank()) {
            orderBy = "ord_date descending";
        }
        String ob = orderBy.toLowerCase().trim();
        Comparator<OrderRow> byDateDesc = Comparator.comparing(OrderRow::ord_date, Comparator.nullsLast(Comparator.reverseOrder()));
        Comparator<OrderRow> byDateAsc = Comparator.comparing(OrderRow::ord_date, Comparator.nullsFirst(Comparator.naturalOrder()));
        Comparator<OrderRow> byNumberAsc = Comparator.comparing(OrderRow::ord_number, Comparator.nullsLast(Comparator.naturalOrder()));
        Comparator<OrderRow> byNumberDesc = Comparator.comparing(OrderRow::ord_number, Comparator.nullsLast(Comparator.reverseOrder()));
        Comparator<OrderRow> byReady = Comparator.comparingInt(OrderRow::ord_ready_for_deliv).reversed();
        Comparator<OrderRow> byId = Comparator.comparingInt(OrderRow::ord_id);

        if (ob.contains("ord_number") && ob.contains("asc")) {
            return byNumberAsc.thenComparing(byId);
        }
        if (ob.contains("ord_number") && ob.contains("desc")) {
            return byNumberDesc.thenComparing(byId);
        }
        if (ob.contains("ord_date") && ob.contains("desc")) {
            return byDateDesc.thenComparing(byNumberAsc).thenComparing(byId);
        }
        if (ob.contains("ord_date") && ob.contains("asc")) {
            return byDateAsc.thenComparing(byNumberAsc).thenComparing(byId);
        }
        if (ob.contains("ord_ready_for_deliv")) {
            return byReady.thenComparing(byDateDesc).thenComparing(byNumberAsc).thenComparing(byId);
        }
        return byDateDesc.thenComparing(byNumberAsc).thenComparing(byId);
    }
}
