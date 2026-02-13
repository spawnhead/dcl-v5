package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.application.OrderFilterParams;
import com.dcl.modern.orders.domain.OrderRow;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Orders list from Postgres. Reads dcl_order with JOINs to contractor, user, department.
 * CONTRACTS: docs/screens/orders. Replaces OrderFilterFakeProvider.
 */
@Repository
public class OrderListProvider {

    private final JdbcTemplate jdbc;

    public OrderListProvider(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<OrderRow> ROW_MAPPER = (rs, i) -> mapRow(rs);

    private static OrderRow mapRow(ResultSet rs) throws SQLException {
        int ordId = rs.getInt("ord_id");
        String ordNumber = rs.getString("ord_number");
        LocalDate ordDate = rs.getObject("ord_date", LocalDate.class);
        String ordContractor = rs.getString("ord_contractor");
        String ordContractorFor = rs.getString("ord_contractor_for");
        BigDecimal ordSumm = rs.getObject("ord_summ", BigDecimal.class);
        LocalDate ordDateConf = rs.getObject("ord_date_conf", LocalDate.class);
        LocalDate ordSentToProdDate = rs.getObject("ord_sent_to_prod_date", LocalDate.class);
        LocalDate ordReceivedConfDate = rs.getObject("ord_received_conf_date", LocalDate.class);
        LocalDate ordConfSentDate = rs.getObject("ord_conf_sent_date", LocalDate.class);
        LocalDate ordReadyForDelivDate = rs.getObject("ord_ready_for_deliv_date", LocalDate.class);
        int ordReadyForDeliv = ordReadyForDelivDate != null ? 1 : 0;
        LocalDate ordExecutedDate = rs.getObject("ord_executed_date", LocalDate.class);
        String ordUser = rs.getString("ord_user");
        String ordDepartment = rs.getString("ord_department");
        String isWarn = "0";
        Short ordBlockVal = rs.getObject("ord_block", Short.class);
        String ordBlock = (ordBlockVal != null && ordBlockVal != 0) ? "1" : "0";
        int ordAnnul = rs.getObject("ord_annul", Short.class) != null ? rs.getShort("ord_annul") : 0;
        String ordNumConf = rs.getString("ord_num_conf");
        Integer depId = rs.getObject("dep_id", Integer.class);
        int ordLinkToSpec = rs.getObject("spc_id") != null ? 1 : 0;
        String ordComment = rs.getString("ord_comment");
        int ordCommentFlag = (ordComment != null && !ordComment.isBlank()) ? 1 : 0;
        String haveEmptyDateConf = "0";
        Integer countDayCurrMinusSent = null;
        LocalDate ordShipFromStock = null;
        LocalDate ordArriveInLithuania = null;
        Integer usrIdCreate = rs.getObject("usr_id_create", Integer.class);
        String usrIdCreateStr = usrIdCreate != null ? usrIdCreate.toString() : null;

        return new OrderRow(
            ordId, ordNumber, ordDate, ordContractor, ordContractorFor, ordSumm,
            ordDateConf, ordSentToProdDate, ordReceivedConfDate, ordConfSentDate,
            ordReadyForDelivDate, ordReadyForDeliv, ordExecutedDate,
            ordUser, ordDepartment, isWarn, ordBlock, ordAnnul, ordNumConf,
            depId, ordLinkToSpec, ordCommentFlag, haveEmptyDateConf, countDayCurrMinusSent,
            ordShipFromStock, ordArriveInLithuania, usrIdCreateStr
        );
    }

    /** List with filter, sort, pagination. Returns rows and total count. */
    public Result list(OrderFilterParams params) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();

        if (params.number() != null && !params.number().isBlank()) {
            where.append(" AND o.ord_number ILIKE ? ");
            args.add("%" + params.number().trim() + "%");
        }
        if (params.date_begin() != null && !params.date_begin().isBlank()) {
            LocalDate from = parseDate(params.date_begin());
            if (from != null) {
                where.append(" AND o.ord_date >= ? ");
                args.add(from);
            }
        }
        if (params.date_end() != null && !params.date_end().isBlank()) {
            LocalDate to = parseDate(params.date_end());
            if (to != null) {
                where.append(" AND o.ord_date <= ? ");
                args.add(to);
            }
        }
        if (params.contractor_id() != null && !params.contractor_id().isBlank()) {
            where.append(" AND o.ctr_id = ? ");
            args.add(Integer.parseInt(params.contractor_id()));
        }
        if (params.contractor_for_id() != null && !params.contractor_for_id().isBlank()) {
            where.append(" AND o.ctr_id_for = ? ");
            args.add(Integer.parseInt(params.contractor_for_id()));
        }
        if (params.user_id() != null && !params.user_id().isBlank()) {
            where.append(" AND (o.usr_id_manager = ? OR o.usr_id_create = ?) ");
            int uid = Integer.parseInt(params.user_id());
            args.add(uid);
            args.add(uid);
        }
        if (params.department_id() != null && !params.department_id().isBlank()) {
            where.append(" AND u.dep_id = ? ");
            args.add(Integer.parseInt(params.department_id()));
        }
        if (params.stuff_category_id() != null && !params.stuff_category_id().isBlank()) {
            where.append(" AND o.stf_id = ? ");
            args.add(Integer.parseInt(params.stuff_category_id()));
        }
        if (params.seller_for_who_id() != null && !params.seller_for_who_id().isBlank()) {
            where.append(" AND o.sln_id_for_who = ? ");
            args.add(Integer.parseInt(params.seller_for_who_id()));
        }
        if (params.sum_min() != null) {
            where.append(" AND o.ord_summ >= ? ");
            args.add(params.sum_min());
        }
        if (params.sum_max() != null) {
            where.append(" AND o.ord_summ <= ? ");
            args.add(params.sum_max());
        }
        if (params.ord_num_conf() != null && !params.ord_num_conf().isBlank()) {
            where.append(" AND o.ord_num_conf ILIKE ? ");
            args.add("%" + params.ord_num_conf().trim() + "%");
        }
        if (Boolean.TRUE.equals(params.executed())) {
            where.append(" AND o.ord_executed_date IS NOT NULL ");
        }
        if (Boolean.TRUE.equals(params.not_executed())) {
            where.append(" AND o.ord_executed_date IS NULL ");
        }
        if (Boolean.TRUE.equals(params.ord_ready_for_deliv())) {
            where.append(" AND o.ord_ready_for_deliv_date IS NOT NULL ");
        }
        if (Boolean.TRUE.equals(params.ord_annul_not_show())) {
            where.append(" AND (o.ord_annul IS NULL OR o.ord_annul = 0) ");
        }

        String orderByClause = orderByClause(params.order_by());
        String baseSql = " FROM dcl_order o " +
            " LEFT JOIN dcl_contractor ctr ON o.ctr_id = ctr.ctr_id " +
            " LEFT JOIN dcl_contractor ctr_for ON o.ctr_id_for = ctr_for.ctr_id " +
            " LEFT JOIN dcl_user u ON (o.usr_id_manager = u.usr_id OR (o.usr_id_manager IS NULL AND o.usr_id_create = u.usr_id)) " +
            " LEFT JOIN dcl_department d ON u.dep_id = d.dep_id " +
            where;

        String selectList = " o.ord_id, o.ord_number, o.ord_date, ctr.ctr_name AS ord_contractor, ctr_for.ctr_name AS ord_contractor_for, " +
            " o.ord_summ, o.ord_date_conf, o.ord_sent_to_prod_date, o.ord_received_conf_date, o.ord_conf_sent_date, " +
            " o.ord_ready_for_deliv_date, o.ord_executed_date, u.usr_login AS ord_user, d.dep_name AS ord_department, " +
            " o.ord_block, o.ord_annul, o.ord_num_conf, u.dep_id, o.spc_id, o.ord_comment, o.usr_id_create ";

        long total = jdbc.queryForObject("SELECT COUNT(*) " + baseSql, Long.class, args.toArray());

        int page = Math.max(1, params.page());
        int pageSize = Math.min(100, Math.max(1, params.pageSize()));
        int offset = (page - 1) * pageSize;
        args.add(pageSize);
        args.add(offset);

        List<OrderRow> items = jdbc.query(
            "SELECT " + selectList + baseSql + " ORDER BY " + orderByClause + " LIMIT ? OFFSET ? ",
            ROW_MAPPER,
            args.toArray()
        );

        return new Result(items, total, page, pageSize);
    }

    public record Result(List<OrderRow> items, long total, int page, int pageSize) {}

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        String v = value.trim();
        try {
            if (v.length() >= 10) {
                if (v.charAt(2) == '.' && v.charAt(5) == '.') {
                    return LocalDate.parse(v.substring(0, 10), java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                }
                return LocalDate.parse(v.substring(0, 10), java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            }
        } catch (Exception ignored) {}
        return null;
    }

    /** Whitelist order_by to avoid SQL injection. CONTRACTS: ord_date desc, ord_number asc/desc, ord_ready_for_deliv desc. */
    private static String orderByClause(String orderBy) {
        if (orderBy == null || orderBy.isBlank()) {
            return " o.ord_date DESC NULLS LAST, o.ord_number ASC NULLS LAST, o.ord_id ASC ";
        }
        String ob = orderBy.toLowerCase().trim();
        if (ob.contains("ord_number") && ob.contains("asc")) {
            return " o.ord_number ASC NULLS LAST, o.ord_id ASC ";
        }
        if (ob.contains("ord_number") && ob.contains("desc")) {
            return " o.ord_number DESC NULLS LAST, o.ord_id ASC ";
        }
        if (ob.contains("ord_date") && ob.contains("desc")) {
            return " o.ord_date DESC NULLS LAST, o.ord_number ASC NULLS LAST, o.ord_id ASC ";
        }
        if (ob.contains("ord_date") && ob.contains("asc")) {
            return " o.ord_date ASC NULLS FIRST, o.ord_number ASC NULLS LAST, o.ord_id ASC ";
        }
        if (ob.contains("ord_ready_for_deliv")) {
            return " (CASE WHEN o.ord_ready_for_deliv_date IS NOT NULL THEN 1 ELSE 0 END) DESC, o.ord_date DESC NULLS LAST, o.ord_number ASC NULLS LAST, o.ord_id ASC ";
        }
        return " o.ord_date DESC NULLS LAST, o.ord_number ASC NULLS LAST, o.ord_id ASC ";
    }
}
