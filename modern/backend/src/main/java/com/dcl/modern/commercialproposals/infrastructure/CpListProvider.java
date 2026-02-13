package com.dcl.modern.commercialproposals.infrastructure;

import com.dcl.modern.commercialproposals.api.CpRowDto;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * CP list from Postgres. Filter, sort, pagination. docs/screens/commercial_proposals/.
 */
@Repository
public class CpListProvider {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final JdbcTemplate jdbc;

    public CpListProvider(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static CpRowDto mapRow(ResultSet rs) throws SQLException {
        int cprId = rs.getInt("cpr_id");
        String cprNumber = rs.getString("cpr_number");
        LocalDate cprDate = rs.getObject("cpr_date", LocalDate.class);
        String cprContractor = rs.getString("cpr_contractor");
        BigDecimal summ = rs.getObject("cpr_summ", BigDecimal.class);
        String cprCurrency = rs.getString("cpr_currency");
        String cprStfName = rs.getString("cpr_stf_name");
        String reservedState = "";
        Short blockVal = rs.getObject("cpr_block", Short.class);
        String cprBlock = (blockVal != null && blockVal != 0) ? "1" : "";
        String cprUser = rs.getString("cpr_user");
        String cprDepartment = rs.getString("cpr_department");
        Short checkPriceVal = rs.getObject("cpr_check_price", Short.class);
        String cprCheckPrice = (checkPriceVal != null && checkPriceVal != 0) ? "1" : "";

        String cprDateStr = cprDate != null ? cprDate.format(DD_MM_YYYY) : "";
        String cprSumFormatted = formatSum(summ);

        return new CpRowDto(
            String.valueOf(cprId),
            cprNumber != null ? cprNumber : "",
            cprDateStr,
            cprContractor != null ? cprContractor : "",
            cprSumFormatted,
            cprCurrency != null ? cprCurrency : "",
            cprStfName != null ? cprStfName : "",
            reservedState,
            cprBlock,
            cprUser != null ? cprUser : "",
            cprDepartment != null ? cprDepartment : "",
            cprCheckPrice
        );
    }

    private static String formatSum(BigDecimal sum) {
        if (sum == null) return "";
        return sum.setScale(2, java.math.RoundingMode.HALF_UP).toString().replace(".", " ");
    }

    public record FilterParams(
        String cprNumber,
        String departmentId,
        String contractorId,
        String userId,
        String stuffCategoryId,
        LocalDate cprDateFrom,
        LocalDate cprDateTo,
        Double cprSumFrom,
        Double cprSumTo,
        Boolean cprProposalReceivedFlag,
        Boolean cprProposalDeclined
    ) {}

    public record Result(List<CpRowDto> items, long total, int page, int pageSize) {}

    public Result list(FilterParams params, int page, int pageSize) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();

        if (params.cprNumber() != null && !params.cprNumber().isBlank()) {
            where.append(" AND cpr.cpr_number ILIKE ? ");
            args.add("%" + params.cprNumber().trim() + "%");
        }
        if (params.departmentId() != null && !params.departmentId().isBlank()) {
            where.append(" AND u.dep_id = ? ");
            args.add(Integer.parseInt(params.departmentId()));
        }
        if (params.contractorId() != null && !params.contractorId().isBlank()) {
            where.append(" AND cpr.ctr_id = ? ");
            args.add(Integer.parseInt(params.contractorId()));
        }
        if (params.userId() != null && !params.userId().isBlank()) {
            where.append(" AND cpr.usr_id = ? ");
            args.add(Integer.parseInt(params.userId()));
        }
        if (params.stuffCategoryId() != null && !params.stuffCategoryId().isBlank()) {
            where.append(" AND EXISTS (SELECT 1 FROM dcl_cpr_list_produce lp WHERE lp.cpr_id = cpr.cpr_id AND lp.stf_id = ?) ");
            args.add(Integer.parseInt(params.stuffCategoryId()));
        }
        if (params.cprDateFrom() != null) {
            where.append(" AND cpr.cpr_date >= ? ");
            args.add(params.cprDateFrom());
        }
        if (params.cprDateTo() != null) {
            where.append(" AND cpr.cpr_date <= ? ");
            args.add(params.cprDateTo());
        }
        if (params.cprSumFrom() != null) {
            where.append(" AND cpr.cpr_summ >= ? ");
            args.add(params.cprSumFrom());
        }
        if (params.cprSumTo() != null) {
            where.append(" AND cpr.cpr_summ <= ? ");
            args.add(params.cprSumTo());
        }
        if (Boolean.TRUE.equals(params.cprProposalReceivedFlag())) {
            where.append(" AND (cpr.cpr_proposal_received_flag = 1) ");
        }
        if (Boolean.TRUE.equals(params.cprProposalDeclined())) {
            where.append(" AND (cpr.cpr_proposal_declined = '1') ");
        }

        String baseSql = " FROM dcl_commercial_proposal cpr " +
            " LEFT JOIN dcl_contractor ctr ON cpr.ctr_id = ctr.ctr_id " +
            " LEFT JOIN dcl_currency cur ON cpr.cur_id = cur.cur_id " +
            " LEFT JOIN dcl_user u ON cpr.usr_id = u.usr_id " +
            " LEFT JOIN dcl_department d ON u.dep_id = d.dep_id " +
            where;

        String selectList = " cpr.cpr_id, cpr.cpr_number, cpr.cpr_date, ctr.ctr_name AS cpr_contractor, cpr.cpr_summ, cur.cur_name AS cpr_currency, " +
            " (SELECT s.stf_name FROM dcl_cpr_list_produce lp " +
            "  JOIN dcl_stuff_category s ON s.stf_id = lp.stf_id " +
            "  WHERE lp.cpr_id = cpr.cpr_id AND lp.stf_id IS NOT NULL " +
            "  ORDER BY lp.lpr_id LIMIT 1) AS cpr_stf_name, " +
            " cpr.cpr_block, COALESCE(u.usr_login, 'usr-' || cpr.usr_id) AS cpr_user, d.dep_name AS cpr_department, cpr.cpr_check_price ";

        long total = jdbc.queryForObject("SELECT COUNT(*) " + baseSql, Long.class, args.toArray());

        int p = Math.max(1, page);
        int ps = Math.min(100, Math.max(1, pageSize));
        int offset = (p - 1) * ps;
        args.add(ps);
        args.add(offset);

        List<CpRowDto> items = jdbc.query(
            "SELECT " + selectList + baseSql + " ORDER BY cpr.cpr_date DESC NULLS LAST, cpr.cpr_number DESC NULLS LAST, cpr.cpr_id ASC LIMIT ? OFFSET ? ",
            (RowMapper<CpRowDto>) (rs, i) -> mapRow(rs),
            args.toArray()
        );

        return new Result(items, total, p, ps);
    }

    public static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        String v = value.trim();
        try {
            if (v.length() >= 10) {
                if (v.charAt(2) == '.' && v.charAt(5) == '.') {
                    return LocalDate.parse(v.substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                }
                return LocalDate.parse(v.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        } catch (Exception ignored) {}
        return null;
    }
}
