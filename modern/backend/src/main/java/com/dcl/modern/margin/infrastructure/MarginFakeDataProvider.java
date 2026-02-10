package com.dcl.modern.margin.infrastructure;

import com.dcl.modern.margin.domain.MarginLine;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Deterministic fake data for Margin grid (CONTRACTS.md shape).
 * Legacy: MarginAction.generate + DAO; this phase: in-memory seed for parity UI/API.
 */
public final class MarginFakeDataProvider {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String[] CONTRACTORS = { "ООО Пример", "ИП Иванов", "ЗАО Тест", "ООО Контракт", "ПАО Снаб" };
    private static final String[] COUNTRIES = { "BY", "RU", "PL", "LT", "UA" };
    private static final String[] CURRENCIES = { "EUR", "USD", "BYN" };
    private static final String[] PRODUCTS = { "Товар A", "Товар B", "Товар C", "Продукт 1", "Продукт 2" };
    private static final String[] USERS = { "Иванов И.И.", "Петров П.П.", "Сидоров С.С." };
    private static final String[] DEPTS = { "Продажи", "Закупки", "Логистика" };

    private final AtomicInteger seq = new AtomicInteger(0);

    public List<MarginLine> generate(int count) {
        List<MarginLine> list = new ArrayList<>(count);
        LocalDate base = LocalDate.of(2024, 1, 1);
        for (int i = 0; i < count; i++) {
            int n = seq.incrementAndGet();
            int cIdx = Math.abs(n) % CONTRACTORS.length;
            int coIdx = Math.abs(n * 7) % COUNTRIES.length;
            int curIdx = Math.abs(n * 11) % CURRENCIES.length;
            int pIdx = Math.abs(n * 13) % PRODUCTS.length;
            int uIdx = Math.abs(n * 17) % USERS.length;
            int dIdx = Math.abs(n * 19) % DEPTS.length;
            LocalDate d1 = base.plusDays(n % 200);
            LocalDate d2 = d1.plusDays(5 + (n % 10));
            LocalDate d3 = d2.plusDays(3 + (n % 5));
            LocalDate d4 = d3.plusDays(2 + (n % 7));
            double summ = 500 + (n % 5000);
            double marginVal = summ * (0.05 + (n % 20) / 100.0);
            double zak = summ - marginVal;
            double koeff = summ / Math.max(zak, 1);
            boolean itog = (i + 1) % 50 == 0;
            String spcGroup = itog ? "Итого" : "";
            boolean haveUnblocked = n % 7 == 0;

            list.add(new MarginLine(
                CONTRACTORS[cIdx],
                COUNTRIES[coIdx],
                "CN-" + String.format("%03d", (n % 999) + 1),
                d1.format(DD_MM_YYYY),
                "SPC-" + String.format("%02d", (n % 99) + 1),
                d2.format(DD_MM_YYYY),
                formatNum(summ),
                CURRENCIES[curIdx],
                PRODUCTS[pIdx],
                "SHP-" + (100 + n % 900),
                d3.format(DD_MM_YYYY),
                d4.format(DD_MM_YYYY),
                formatNum(summ),
                formatNum(summ * 1.05),
                formatNum(20 + n % 80),
                formatNum(10 + n % 40),
                formatNum(5 + n % 25),
                formatNum(15 + n % 35),
                formatNum(10 + n % 20),
                formatNum(1 + (n % 10) / 2.0),
                formatNum(5 + n % 15),
                formatNum(n % 50),
                formatNum(summ),
                formatNum(zak),
                formatNum(marginVal),
                formatNum(koeff),
                USERS[uIdx],
                DEPTS[dIdx],
                itog,
                spcGroup,
                haveUnblocked
            ));
        }
        return list;
    }

    private static String formatNum(double v) {
        return String.format("%,.2f", v).replace(',', ' ');
    }
}
