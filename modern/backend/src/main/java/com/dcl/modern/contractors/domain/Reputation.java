package com.dcl.modern.contractors.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * dcl_reputation. Lookup for contractor create.
 */
@Entity
@Table(name = "dcl_reputation")
public class Reputation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rpt_id")
    private Integer id;

    @Column(name = "rpt_level", nullable = false)
    private Short level;

    @Column(name = "rpt_description", nullable = false, length = 500)
    private String description;

    protected Reputation() {}

    public Integer getId() { return id; }
    public String getDescription() { return description; }
}
