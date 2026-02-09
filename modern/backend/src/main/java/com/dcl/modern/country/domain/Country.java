package com.dcl.modern.country.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "dcl_country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cut_id")
    private Integer id;

    @Column(name = "cut_create_date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "usr_id_create", nullable = false)
    private Integer createdBy;

    @Column(name = "cut_edit_date", nullable = false)
    private LocalDateTime editedAt;

    @Column(name = "usr_id_edit", nullable = false)
    private Integer editedBy;

    @Column(name = "cut_name", nullable = false, length = 50)
    private String name;

    protected Country() {
    }

    public Country(String name, LocalDateTime createdAt, Integer createdBy, LocalDateTime editedAt, Integer editedBy) {
        this.name = name;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.editedAt = editedAt;
        this.editedBy = editedBy;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public Integer getEditedBy() {
        return editedBy;
    }

    public String getName() {
        return name;
    }

    public void rename(String name, LocalDateTime editedAt, Integer editedBy) {
        this.name = name;
        this.editedAt = editedAt;
        this.editedBy = editedBy;
    }
}
