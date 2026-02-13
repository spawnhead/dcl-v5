package com.dcl.modern.contracts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * dcl_contract. Legacy: ContractAction process.
 */
@Entity
@Table(name = "dcl_contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_id")
    private Integer id;

    @Column(name = "con_number", nullable = false, length = 50)
    private String number;

    @Column(name = "con_date", nullable = false)
    private LocalDate date;

    @Column(name = "ctr_id")
    private Integer contractorId;

    @Column(name = "cur_id")
    private Integer currencyId;

    @Column(name = "sln_id")
    private Integer sellerId;

    @Column(name = "con_reusable")
    private Short reusable;

    @Column(name = "con_final_date")
    private LocalDate finalDate;

    @Column(name = "con_create_date")
    private LocalDateTime createDate;

    @Column(name = "usr_id_create")
    private Integer createdBy;

    @Column(name = "con_edit_date")
    private LocalDateTime editDate;

    @Column(name = "usr_id_edit")
    private Integer editedBy;

    @Column(name = "con_executed")
    private Short executed;

    @Column(name = "con_summ")
    private java.math.BigDecimal sum;

    @Column(name = "con_original")
    private Short original;

    @Column(name = "con_annul")
    private Short annul;

    @Column(name = "con_annul_date")
    private LocalDate annulDate;

    @Column(name = "con_comment", length = 5000)
    private String comment;

    protected Contract() {}

    public Contract(String number, LocalDate date, Integer contractorId, Integer currencyId, Integer sellerId,
            Short reusable, LocalDate finalDate, String comment, Short original, Short annul,
            LocalDateTime now, Integer userId) {
        this.number = number;
        this.date = date;
        this.contractorId = contractorId;
        this.currencyId = currencyId;
        this.sellerId = sellerId;
        this.reusable = reusable;
        this.finalDate = finalDate;
        this.comment = comment;
        this.original = original;
        this.annul = annul;
        this.createDate = now;
        this.createdBy = userId;
        this.editDate = now;
        this.editedBy = userId;
        this.executed = 0;
    }

    public Integer getId() { return id; }
    public String getNumber() { return number; }
    public LocalDate getDate() { return date; }
    public Integer getContractorId() { return contractorId; }
    public Integer getCurrencyId() { return currencyId; }
    public Integer getSellerId() { return sellerId; }
    public java.math.BigDecimal getSum() { return sum; }
    public Short getExecuted() { return executed; }
    public String getComment() { return comment; }
    public Short getAnnul() { return annul; }
}
