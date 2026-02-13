package com.dcl.modern.contracts.infrastructure;

import com.dcl.modern.contracts.api.LookupItemDto;
import com.dcl.modern.contracts.api.UserLookupDto;
import com.dcl.modern.contracts.domain.ContractRow;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Deterministic fake data for Contracts list. TEST_DATA_SPEC: 60 rows, 2024/2025/2026, beacons, statuses.
 * Legacy: select-contracts → dcl_contract_filter.
 */
public final class ContractsFakeProvider {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String[] CONTRACTORS = {
        "ALFA TRADE", "ALFA TECH", "BETA LLC", "GAMMA CORP", "DELTA EXPORT", "EPSILON LTD",
        "ZETA CO", "ETA TRADE", "ARCHIVE-1", "ARCHIVE-2", "ALFA TRADE B", "ALFA TECH B"
    };
    private static final String[] USERS = { "ivanov", "petrov", "sidorov", "admin", "economist1", "economist2", "manager1", "lawyer1" };
    private static final String[] SELLERS = { "ООО Продавец-1", "ООО Продавец-2", "Seller EU-1", "Seller EU-2" };
    private static final String[] CURRENCIES = { "BYN", "USD", "EUR" };

    /** Lookups for GET /api/contracts/lookups. TEST_DATA_SPEC: 12 contractors, 8 users, 4 sellers. */
    public static List<LookupItemDto> getContractorsLookup() {
        return List.of(
            new LookupItemDto("301", "ALFA TRADE"),
            new LookupItemDto("302", "ALFA TECH"),
            new LookupItemDto("303", "BETA LLC"),
            new LookupItemDto("304", "GAMMA CORP"),
            new LookupItemDto("305", "DELTA EXPORT"),
            new LookupItemDto("306", "EPSILON LTD"),
            new LookupItemDto("307", "ZETA CO"),
            new LookupItemDto("308", "ETA TRADE"),
            new LookupItemDto("309", "ARCHIVE-1"),
            new LookupItemDto("310", "ARCHIVE-2"),
            new LookupItemDto("311", "ALFA TRADE B"),
            new LookupItemDto("312", "ALFA TECH B")
        );
    }

    /** Delivery terms for spec create. N3a2 payload: id 1 = Предоплата 100%. */
    public static List<LookupItemDto> getDeliveryTermsLookup() {
        return List.of(
            new LookupItemDto("1", "Предоплата 100%"),
            new LookupItemDto("2", "Постоплата"),
            new LookupItemDto("3", "Частичная предоплата")
        );
    }

    /** Users as UserLookupDto for spec form. */
    public static List<UserLookupDto> getUsersLookupForSpec() {
        return List.of(
            new UserLookupDto("101", "Admin User"),
            new UserLookupDto("102", "ivanov"),
            new UserLookupDto("103", "petrov"),
            new UserLookupDto("104", "sidorov")
        );
    }

    public static List<LookupItemDto> getUsersLookup() {
        return List.of(
            new LookupItemDto("101", "ivanov"),
            new LookupItemDto("102", "petrov"),
            new LookupItemDto("103", "sidorov"),
            new LookupItemDto("104", "admin"),
            new LookupItemDto("105", "economist1"),
            new LookupItemDto("106", "economist2"),
            new LookupItemDto("107", "manager1"),
            new LookupItemDto("108", "lawyer1")
        );
    }

    public static List<LookupItemDto> getSellersLookup() {
        return List.of(
            new LookupItemDto("201", "ООО Продавец-1"),
            new LookupItemDto("202", "ООО Продавец-2"),
            new LookupItemDto("203", "Seller EU-1"),
            new LookupItemDto("204", "Seller EU-2")
        );
    }

    /** Currencies for contract create. N3a TEST_DATA_SPEC: BYN, USD, EUR. */
    public static List<LookupItemDto> getCurrenciesLookup() {
        return List.of(
            new LookupItemDto("1", "BYN"),
            new LookupItemDto("2", "USD"),
            new LookupItemDto("3", "EUR")
        );
    }

    /** Contractors for create form. N3a payload: id 101=ALFA TRADE. */
    public static List<LookupItemDto> getContractorsLookupForCreate() {
        return List.of(
            new LookupItemDto("101", "ALFA TRADE"),
            new LookupItemDto("102", "BETA SERVICE"),
            new LookupItemDto("103", "GAMMA CORP"),
            new LookupItemDto("104", "DELTA EXPORT"),
            new LookupItemDto("105", "EPSILON LTD")
        );
    }

    /** Sellers for create form. N3a: seller id=1 for con_final_date required. */
    public static List<LookupItemDto> getSellersLookupForCreate() {
        return List.of(
            new LookupItemDto("1", "ЗАО Линтера"),
            new LookupItemDto("2", "UAB SMERKONA"),
            new LookupItemDto("3", "ООО Продавец-1"),
            new LookupItemDto("4", "ООО Продавец-2")
        );
    }

    /** Resolve contractor id to name for filter. */
    public static String contractorIdToName(String id) {
        if (id == null || id.isBlank()) return null;
        return getContractorsLookup().stream()
            .filter(l -> id.equals(l.id()))
            .map(LookupItemDto::name)
            .findFirst()
            .orElse(null);
    }

    public static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return LocalDate.parse(value.trim(), DD_MM_YYYY);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Fixed sort: con_reminder desc (has reminder first), con_date desc, con_number desc. ACCEPTANCE §8. */
    private static final Comparator<ContractRow> DEFAULT_SORT = Comparator
        .<ContractRow>comparingInt(r -> (r.conReminder() != null && !r.conReminder().isBlank()) ? 0 : 1)
        .thenComparing(ContractRow::conDate, Comparator.nullsLast(Comparator.reverseOrder()))
        .thenComparing(ContractRow::conNumber, Comparator.nullsLast(Comparator.reverseOrder()));

    /** 60 rows: 2024 (1-18), 2025 (19-43), 2026 (44-60). Beacons at specific indices. */
    public static List<ContractRow> buildRows() {
        LocalDate base2024 = LocalDate.of(2024, 1, 15);
        LocalDate base2025 = LocalDate.of(2025, 1, 10);
        LocalDate base2026 = LocalDate.of(2026, 1, 5);
        return Stream.iterate(1, i -> i <= 60, i -> i + 1)
            .map(i -> {
                String conId = "100" + (200 + i);
                String conNumber;
                LocalDate conDate;
                if (i == 1) {
                    conNumber = "CN-2025-001";
                    conDate = LocalDate.of(2025, 2, 2);
                } else if (i == 14) {
                    conNumber = "CN-2025-014";
                    conDate = LocalDate.of(2025, 7, 15);
                } else if (i == 9) {
                    conNumber = "CN-2024-009";
                    conDate = LocalDate.of(2024, 11, 21);
                } else if (i == 44) {
                    conNumber = "CN-2026-003";
                    conDate = LocalDate.of(2026, 1, 10);
                } else if (i == 51) {
                    conNumber = "CN-2026-010";
                    conDate = LocalDate.of(2026, 9, 27);
                } else if (i <= 18) {
                    conNumber = "CN-2024-" + String.format("%03d", i);
                    conDate = base2024.plusDays((i - 1) * 17);
                } else if (i <= 43) {
                    conNumber = "CN-2025-" + String.format("%03d", i - 18);
                    conDate = base2025.plusDays((i - 19) * 12);
                } else {
                    conNumber = "CN-2026-" + String.format("%03d", i - 43);
                    conDate = base2026.plusDays((i - 44) * 18);
                }
                String conContractor = CONTRACTORS[(i - 1) % CONTRACTORS.length];
                double sum = 5000 + i * 500;
                String conSumm = String.format("%,.2f", sum).replace(',', ' ');
                String conCurrency = CURRENCIES[i % CURRENCIES.length];
                String notes = i % 7 == 0 ? "Действует до 31.12." + (conDate.getYear() + 1) : "";
                boolean executed = (i % 5) < 2;
                String conExecuted = executed ? "1" : "0";
                String conUser = USERS[(i - 1) % USERS.length];
                boolean hasReminder = (i == 44 || i % 11 == 3);
                String conReminder = hasReminder ? "<span style='color:red'>Укажите срок поставки</span>" : "";
                boolean annul = (i == 14 || i % 10 == 0);
                String conAnnul = annul ? "1" : "";
                int attachIdx = (i % 6) + 1;
                int spcCount = (i == 9 || i % 4 == 0) ? 0 : (i % 5) + 1;
                String usrIdList = "101;203;";
                String depIdList = (i % 2 == 0) ? "20;" : "10;";
                if (i == 51) {
                    notes = "<span style='color:red'>Нет оригинала договора.</span>";
                }
                boolean oridinalAbsent = (i == 51 || i % 8 == 2);
                return new ContractRow(conId, conNumber, conDate, conContractor, conSumm, conCurrency, notes, conExecuted, conUser, conReminder, conAnnul, attachIdx, spcCount, usrIdList, depIdList, oridinalAbsent);
            })
            .sorted(DEFAULT_SORT)
            .toList();
    }

    /** Filter by contractor name when provided (Postgres IDs). Else use contractorId with contractorIdToName (fake IDs). */
    public static List<ContractRow> filterAndPage(
        List<ContractRow> all,
        String number,
        String contractorId,
        String contractorNameOverride,
        LocalDate dateBegin,
        LocalDate dateEnd,
        Double sumMin,
        Double sumMax,
        String userId,
        String sellerId,
        String conExecutedFilter,
        Boolean oridinalAbsent,
        int page,
        int pageSize
    ) {
        Stream<ContractRow> stream = all.stream();
        if (Boolean.TRUE.equals(oridinalAbsent)) {
            stream = stream.filter(ContractRow::oridinalAbsent);
        }
        if (number != null && !number.isBlank()) {
            String n = number.trim().toLowerCase();
            stream = stream.filter(r -> r.conNumber() != null && r.conNumber().toLowerCase().contains(n));
        }
        String contractorName = contractorNameOverride != null ? contractorNameOverride : (contractorId != null ? contractorIdToName(contractorId) : null);
        if (contractorName != null) {
            stream = stream.filter(r -> contractorName.equals(r.conContractor()));
        }
        if (dateBegin != null) {
            LocalDate d = dateBegin;
            stream = stream.filter(r -> r.conDate() != null && !r.conDate().isBefore(d));
        }
        if (dateEnd != null) {
            LocalDate d = dateEnd;
            stream = stream.filter(r -> r.conDate() != null && !r.conDate().isAfter(d));
        }
        if (sumMin != null) {
            stream = stream.filter(r -> parseSum(r.conSumm()) >= sumMin);
        }
        if (sumMax != null) {
            stream = stream.filter(r -> parseSum(r.conSumm()) <= sumMax);
        }
        if (userId != null && !userId.isBlank()) {
            stream = stream.filter(r -> r.usrIdList() != null && r.usrIdList().contains(userId));
        }
        if (sellerId != null && !sellerId.isBlank()) {
            stream = stream.filter(r -> r.conUser() != null);
        }
        if (conExecutedFilter != null) {
            if ("1".equals(conExecutedFilter)) stream = stream.filter(r -> "1".equals(r.conExecuted()));
            else if ("0".equals(conExecutedFilter)) stream = stream.filter(r -> "0".equals(r.conExecuted()));
        }
        if (Boolean.TRUE.equals(oridinalAbsent)) stream = stream.filter(ContractRow::oridinalAbsent);
        List<ContractRow> sorted = stream.sorted(DEFAULT_SORT).toList();
        int total = sorted.size();
        int from = (page - 1) * pageSize;
        if (from >= total) return List.of();
        return sorted.subList(from, Math.min(from + pageSize, total));
    }

    public static long countFiltered(
        List<ContractRow> all,
        String number,
        String contractorId,
        String contractorNameOverride,
        LocalDate dateBegin,
        LocalDate dateEnd,
        Double sumMin,
        Double sumMax,
        String userId,
        String sellerId,
        String conExecutedFilter,
        Boolean oridinalAbsent
    ) {
        Stream<ContractRow> stream = all.stream();
        if (Boolean.TRUE.equals(oridinalAbsent)) stream = stream.filter(ContractRow::oridinalAbsent);
        if (number != null && !number.isBlank()) {
            String n = number.trim().toLowerCase();
            stream = stream.filter(r -> r.conNumber() != null && r.conNumber().toLowerCase().contains(n));
        }
        String contractorName = contractorNameOverride != null ? contractorNameOverride : (contractorId != null ? contractorIdToName(contractorId) : null);
        if (contractorName != null) {
            stream = stream.filter(r -> contractorName.equals(r.conContractor()));
        }
        if (dateBegin != null) {
            LocalDate d = dateBegin;
            stream = stream.filter(r -> r.conDate() != null && !r.conDate().isBefore(d));
        }
        if (dateEnd != null) {
            LocalDate d = dateEnd;
            stream = stream.filter(r -> r.conDate() != null && !r.conDate().isAfter(d));
        }
        if (sumMin != null) stream = stream.filter(r -> parseSum(r.conSumm()) >= sumMin);
        if (sumMax != null) stream = stream.filter(r -> parseSum(r.conSumm()) <= sumMax);
        if (userId != null && !userId.isBlank()) stream = stream.filter(r -> r.usrIdList() != null && r.usrIdList().contains(userId));
        if (conExecutedFilter != null) {
            if ("1".equals(conExecutedFilter)) stream = stream.filter(r -> "1".equals(r.conExecuted()));
            else if ("0".equals(conExecutedFilter)) stream = stream.filter(r -> "0".equals(r.conExecuted()));
        }
        return stream.count();
    }

    private static double parseSum(String s) {
        if (s == null || s.isBlank()) return 0;
        try {
            return Double.parseDouble(s.replace(" ", "").replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
