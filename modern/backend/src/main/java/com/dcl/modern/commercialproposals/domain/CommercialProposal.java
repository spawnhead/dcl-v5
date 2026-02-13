package com.dcl.modern.commercialproposals.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * dcl_commercial_proposal. Legacy: CommercialProposalsAction.
 * docs/screens/commercial_proposals/.
 */
@Entity
@Table(name = "dcl_commercial_proposal")
public class CommercialProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpr_id")
    private Integer id;

    @Column(name = "cpr_number", nullable = false, length = 20)
    private String number;

    @Column(name = "cpr_date", nullable = false)
    private LocalDate date;

    @Column(name = "ctr_id", nullable = false)
    private Integer contractorId;

    @Column(name = "cur_id", nullable = false)
    private Integer currencyId;

    @Column(name = "usr_id")
    private Integer userId;

    @Column(name = "cpr_summ", precision = 15, scale = 2)
    private BigDecimal summ;

    @Column(name = "cpr_block")
    private Short block;

    @Column(name = "cpr_proposal_received_flag")
    private Short proposalReceivedFlag;

    @Column(name = "cpr_proposal_declined", length = 1)
    private String proposalDeclined;

    @Column(name = "cpr_old_version")
    private Short oldVersion;

    @Column(name = "cpr_check_price")
    private Short checkPrice;

    @Column(name = "cpr_create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "usr_id_create", nullable = false)
    private Integer createdBy;

    @Column(name = "cpr_edit_date", nullable = false)
    private LocalDateTime editDate;

    @Column(name = "usr_id_edit", nullable = false)
    private Integer editedBy;

    @Column(name = "trm_id_price_condition", nullable = false)
    private Integer trmIdPriceCondition;

    @Column(name = "trm_id_delivery_condition", nullable = false)
    private Integer trmIdDeliveryCondition;

    @Column(name = "bln_id", nullable = false)
    private Integer blnId;

    @Column(name = "cur_id_table", nullable = false)
    private Integer curIdTable;

    protected CommercialProposal() {}

    public Integer getId() { return id; }
    public String getNumber() { return number; }
    public LocalDate getDate() { return date; }
    public Integer getContractorId() { return contractorId; }
    public Integer getCurrencyId() { return currencyId; }
    public Integer getUserId() { return userId; }
    public BigDecimal getSumm() { return summ; }
    public Short getBlock() { return block; }
    public Short getProposalReceivedFlag() { return proposalReceivedFlag; }
    public String getProposalDeclined() { return proposalDeclined; }
    public Short getOldVersion() { return oldVersion; }
    public Short getCheckPrice() { return checkPrice; }
    public Integer getTrmIdPriceCondition() { return trmIdPriceCondition; }
    public Integer getTrmIdDeliveryCondition() { return trmIdDeliveryCondition; }
    public Integer getBlnId() { return blnId; }
    public Integer getCurIdTable() { return curIdTable; }

    public void setBlock(Short block) { this.block = block; }
    public void setNumber(String number) { this.number = number; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }
    public void setEditDate(LocalDateTime editDate) { this.editDate = editDate; }
    public void setEditedBy(Integer editedBy) { this.editedBy = editedBy; }
}
