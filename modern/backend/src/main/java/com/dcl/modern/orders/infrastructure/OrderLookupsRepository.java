package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.api.LookupItemDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Read-only lookups for order edit from Postgres. No FAKE; same DB as dcl_order.
 */
@Repository
public class OrderLookupsRepository {

    private final JdbcTemplate jdbc;

    public OrderLookupsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<LookupItemDto> getContractors() {
        return jdbc.query(
            "SELECT ctr_id, ctr_name FROM dcl_contractor WHERE ctr_block IS NULL OR ctr_block = 0 ORDER BY ctr_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getSellers() {
        return jdbc.query(
            "SELECT sln_id, sln_name FROM dcl_seller ORDER BY sln_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getCurrencies() {
        return jdbc.query(
            "SELECT cur_id, cur_name FROM dcl_currency ORDER BY cur_sort_order NULLS LAST, cur_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getStuffCategories() {
        return jdbc.query(
            "SELECT stf_id, stf_name FROM dcl_stuff_category ORDER BY stf_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getBlanks() {
        return jdbc.query(
            "SELECT bln_id, bln_name FROM dcl_blank ORDER BY bln_id",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getContracts(String contractorId) {
        if (contractorId == null || contractorId.isBlank()) {
            return jdbc.query(
                "SELECT con_id, con_number FROM dcl_contract ORDER BY con_date DESC",
                (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
            );
        }
        return jdbc.query(
            "SELECT con_id, con_number FROM dcl_contract WHERE ctr_id = ? ORDER BY con_date DESC",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2)),
            Integer.parseInt(contractorId)
        );
    }

    public List<LookupItemDto> getSpecifications(String contractId, String contractorId) {
        if (contractId != null && !contractId.isBlank()) {
            return jdbc.query(
                "SELECT spc_id, COALESCE(spc_number, 'Спец. ' || spc_id) FROM dcl_con_list_spec WHERE con_id = ? ORDER BY spc_id",
                (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2)),
                Integer.parseInt(contractId)
            );
        }
        return new ArrayList<>();
    }

    public List<LookupItemDto> getDepartments() {
        return jdbc.query(
            "SELECT dep_id, dep_name FROM dcl_department ORDER BY dep_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getUsers(String filter) {
        if (filter != null && !filter.isBlank()) {
            String f = "%" + filter.trim().toLowerCase() + "%";
            return jdbc.query(
                "SELECT usr_id, COALESCE(usr_login, 'usr-' || usr_id) FROM dcl_user WHERE LOWER(COALESCE(usr_login, '')) LIKE LOWER(?) ORDER BY usr_login",
                (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2)),
                f
            );
        }
        return jdbc.query(
            "SELECT usr_id, COALESCE(usr_login, 'usr-' || usr_id) FROM dcl_user ORDER BY usr_login",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }
}
