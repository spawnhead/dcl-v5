package com.dcl.modern.contracts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * dcl_seller. Lookup for contract create.
 */
@Entity
@Table(name = "dcl_seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sln_id")
    private Integer id;

    @Column(name = "sln_name", nullable = false, length = 100)
    private String name;

    protected Seller() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
}
