package com.dcl.modern.currency.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dcl_currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cur_id")
    private Integer id;

    @Column(name = "cur_name", nullable = false, length = 10)
    private String name;

    @Column(name = "cur_no_round")
    private Integer noRound;

    @Column(name = "cur_sort_order")
    private Integer sortOrder;

    protected Currency() {
    }

    public Currency(String name, Integer noRound, Integer sortOrder) {
        this.name = name;
        this.noRound = noRound;
        this.sortOrder = sortOrder;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNoRound() {
        return noRound;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }
}
