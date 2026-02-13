package com.dcl.modern.contractors.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * dcl_department. Lookup for contractors list filter.
 */
@Entity
@Table(name = "dcl_department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_id")
    private Integer id;

    @Column(name = "dep_name", nullable = false, length = 100)
    private String name;

    protected Department() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
}
