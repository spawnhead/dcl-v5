package com.dcl.modern.commercialproposals.infrastructure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Clone CP via native SQL. Copies header + produces.
 */
@Component
public class CpCloneHelper {

    private final JdbcTemplate jdbc;

    public CpCloneHelper(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Clone CP. Returns new cpr_id or null on error.
     */
    public Integer clone(int srcCprId, boolean oldVersion, int usrId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        Integer newId = jdbc.queryForObject(
            """
            INSERT INTO dcl_commercial_proposal (
                cpr_create_date, usr_id_create, cpr_edit_date, usr_id_edit,
                cpr_number, cpr_date, ctr_id, cur_id, trm_id_price_condition, trm_id_delivery_condition,
                bln_id, cur_id_table, usr_id, cpr_summ, cpr_block,
                cpr_proposal_received_flag, cpr_proposal_declined, cpr_old_version
            )
            SELECT ?, ?, ?, ?,
                '', ?, ctr_id, cur_id, trm_id_price_condition, trm_id_delivery_condition,
                bln_id, cur_id_table, usr_id, cpr_summ, 0,
                0, '0', ?
            FROM dcl_commercial_proposal WHERE cpr_id = ?
            RETURNING cpr_id
            """,
            Integer.class,
            now, usrId, now, usrId, today, oldVersion ? 1 : 0, srcCprId
        );

        if (newId == null) return null;

        // Copy produces. Old version: clear stf_id, prd_id (free-text only).
        int oldFlag = oldVersion ? 1 : 0;
        jdbc.update(
            """
            INSERT INTO dcl_cpr_list_produce (cpr_id, lpr_produce_name, lpr_catalog_num, lpr_count, lpr_price_brutto, stf_id, prd_id)
            SELECT ?, lpr_produce_name, lpr_catalog_num, lpr_count, lpr_price_brutto,
                CASE WHEN ? = 1 THEN NULL ELSE stf_id END,
                CASE WHEN ? = 1 THEN NULL ELSE prd_id END
            FROM dcl_cpr_list_produce WHERE cpr_id = ?
            """,
            newId, oldFlag, oldFlag, srcCprId
        );

        return newId;
    }
}
