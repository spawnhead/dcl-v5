package com.dcl.modern.margin.infrastructure;

import com.dcl.modern.margin.domain.MarginLine;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Deterministic test data for Margin grid per TEST_DATA_SPEC.md and CONTRACTS.md.
 * 35 rows: onlyTotal, itog_*, get_not_block, view_* markers; pagination (e.g. pageSize=25), sorting.
 * No random; fixed IDs, dates in Jan 2024, names with (DEV).
 */
public final class MarginFakeDataProvider {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int ROW_COUNT = 35;

    private static String fmt(double v) {
        return String.format("%,.2f", v).replace(',', ' ');
    }

    /** Deterministic dataset: same order every time. */
    public static List<MarginLine> buildDeterministicRows() {
        String[] contractors = { "ООО Контрагент-A (DEV)", "ООО Контрагент-B (DEV)", "ООО ТестКонтрагент-1 (DEV)" };
        String[] users = { "dev_admin", "dev_manager", "dev_economist (DEV)" };
        String[] depts = { "Отдел продаж (DEV)", "Экономический отдел (DEV)" };
        String[] products = { "Категория-A (DEV)", "Категория-B (DEV)" };
        LocalDate base = LocalDate.of(2024, 1, 1);

        return java.util.stream.IntStream.range(0, ROW_COUNT).mapToObj(i -> {
            int n = i + 1;
            boolean itogLine = (n == 10 || n == 20 || n == 30);
            String spcGroup = (n % 5 == 0 && n % 10 != 0) ? "Группа доставки" : (itogLine ? "Итого" : "");
            boolean haveUnblocked = (n % 4 == 0 || n % 7 == 0);
            double summ = 1000 + n * 100;
            double marginVal = summ * 0.12;
            double zak = summ - marginVal;
            double koeff = summ / Math.max(zak, 1);

            String montageTime = (n % 3 == 0) ? "" : fmt(1 + (n % 10) / 2.0);
            String montageCost = (n % 5 == 1) ? "" : fmt(5 + n % 15);
            String updateSum = (n % 4 == 2) ? "0" : fmt(n % 50);

            return new MarginLine(
                contractors[i % contractors.length],
                "BY",
                "CN-" + String.format("%03d", n),
                base.plusDays(n % 28).format(DD_MM_YYYY),
                "SPC-" + String.format("%02d", (n % 20) + 1),
                base.plusDays(5 + n % 20).format(DD_MM_YYYY),
                fmt(summ),
                "EUR",
                products[i % products.length],
                "SHP-" + (100 + n),
                base.plusDays(10 + n % 15).format(DD_MM_YYYY),
                base.plusDays(12 + n % 12).format(DD_MM_YYYY),
                fmt(summ),
                fmt(summ * 1.05),
                fmt(20 + n % 80),
                fmt(10 + n % 40),
                fmt(5 + n % 25),
                fmt(15 + n % 35),
                fmt(10 + n % 20),
                montageTime,
                montageCost,
                updateSum,
                fmt(summ),
                fmt(zak),
                fmt(marginVal),
                fmt(koeff),
                users[i % users.length],
                depts[i % depts.length],
                itogLine,
                spcGroup,
                haveUnblocked
            );
        }).toList();
    }
}
