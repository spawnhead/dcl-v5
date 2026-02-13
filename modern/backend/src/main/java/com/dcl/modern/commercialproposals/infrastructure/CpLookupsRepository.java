package com.dcl.modern.commercialproposals.infrastructure;

import com.dcl.modern.commercialproposals.api.LookupItemDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CpLookupsRepository {

    private final JdbcTemplate jdbc;

    public CpLookupsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<LookupItemDto> getDepartments() {
        return jdbc.query(
            "SELECT dep_id, dep_name FROM dcl_department ORDER BY dep_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getContractors() {
        return jdbc.query(
            "SELECT ctr_id, ctr_name FROM dcl_contractor WHERE ctr_block IS NULL OR ctr_block = 0 ORDER BY ctr_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getUsers() {
        return jdbc.query(
            "SELECT usr_id, COALESCE(usr_login, 'usr-' || usr_id) FROM dcl_user ORDER BY usr_login",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }

    public List<LookupItemDto> getStuffCategories() {
        return jdbc.query(
            "SELECT stf_id, stf_name FROM dcl_stuff_category ORDER BY stf_name",
            (rs, i) -> new LookupItemDto(rs.getString(1), rs.getString(2))
        );
    }
}
