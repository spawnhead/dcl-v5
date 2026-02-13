package com.dcl.modern.contractors.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * dcl_user. Lookup for contractor create (grid users).
 */
@Entity
@Table(name = "dcl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;

    @Column(name = "usr_login", length = 8)
    private String login;

    @Column(name = "usr_code", length = 3)
    private String code;

    @Column(name = "dep_id")
    private Integer departmentId;

    protected User() {}

    public Integer getId() { return id; }
    public String getLogin() { return login; }
    public String getCode() { return code; }
    public Integer getDepartmentId() { return departmentId; }

    /** Display name: login or code or "User #id". */
    public String getDisplayName() {
        if (login != null && !login.isBlank()) return login;
        if (code != null && !code.isBlank()) return code;
        return "User #" + id;
    }
}
