package com.dcl.modern.orders.domain;

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
 * dcl_order. Legacy: OrderAction edit/process. Source: docs/screens/order_edit.
 */
@Entity
@Table(name = "dcl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord_id")
    private Integer id;

    @Column(name = "ord_create_date")
    private LocalDateTime createDate;

    @Column(name = "usr_id_create")
    private Integer createdBy;

    @Column(name = "ord_edit_date")
    private LocalDateTime editDate;

    @Column(name = "usr_id_edit")
    private Integer editedBy;

    @Column(name = "ord_number", nullable = false, length = 15)
    private String number;

    @Column(name = "ord_date", nullable = false)
    private LocalDate ordDate;

    @Column(name = "ctr_id", nullable = false)
    private Integer contractorId;

    @Column(name = "cps_id")
    private Integer contactPersonId;

    @Column(name = "cur_id", nullable = false)
    private Integer currencyId;

    @Column(name = "stf_id")
    private Integer stuffCategoryId;

    @Column(name = "bln_id", nullable = false)
    private Integer blankId;

    @Column(name = "sln_id_for_who", nullable = false)
    private Integer sellerForWhoId;

    @Column(name = "ctr_id_for")
    private Integer contractorForId;

    @Column(name = "spc_id")
    private Integer specificationId;

    @Column(name = "ord_block")
    private Short block;

    @Column(name = "ord_annul")
    private Short annul;

    @Column(name = "ord_summ", precision = 15, scale = 2)
    private BigDecimal summ;

    @Column(name = "ord_comment", length = 3000)
    private String comment;

    @Column(name = "ord_sent_to_prod_date")
    private LocalDate sentToProdDate;

    @Column(name = "ord_received_conf_date")
    private LocalDate receivedConfDate;

    @Column(name = "ord_num_conf", length = 200)
    private String numConf;

    @Column(name = "ord_date_conf")
    private LocalDate dateConf;

    @Column(name = "ord_conf_sent_date")
    private LocalDate confSentDate;

    @Column(name = "ord_executed_date")
    private LocalDate executedDate;

    @Column(name = "ord_ready_for_deliv_date")
    private LocalDate readyForDelivDate;

    @Column(name = "ord_pay_condition", length = 300)
    private String payCondition;

    @Column(name = "ord_addr", length = 256)
    private String addr;

    @Column(name = "ord_delivery_term", length = 300)
    private String deliveryTerm;

    @Column(name = "ord_add_info", length = 5000)
    private String addInfo;

    @Column(name = "usr_id_director")
    private Integer directorId;

    @Column(name = "usr_id_logist")
    private Integer logistId;

    @Column(name = "usr_id_director_rb")
    private Integer directorRbId;

    @Column(name = "usr_id_chief_dep")
    private Integer chiefDepId;

    @Column(name = "usr_id_manager")
    private Integer managerId;

    public Order() {}

    public Integer getId() { return id; }
    public LocalDateTime getCreateDate() { return createDate; }
    public Integer getCreatedBy() { return createdBy; }
    public LocalDateTime getEditDate() { return editDate; }
    public Integer getEditedBy() { return editedBy; }
    public String getNumber() { return number; }
    public LocalDate getOrdDate() { return ordDate; }
    public Integer getContractorId() { return contractorId; }
    public Integer getContactPersonId() { return contactPersonId; }
    public Integer getCurrencyId() { return currencyId; }
    public Integer getStuffCategoryId() { return stuffCategoryId; }
    public Integer getBlankId() { return blankId; }
    public Integer getSellerForWhoId() { return sellerForWhoId; }
    public Integer getContractorForId() { return contractorForId; }
    public Integer getSpecificationId() { return specificationId; }
    public Short getBlock() { return block; }
    public Short getAnnul() { return annul; }
    public BigDecimal getSumm() { return summ; }
    public String getComment() { return comment; }
    public LocalDate getSentToProdDate() { return sentToProdDate; }
    public LocalDate getReceivedConfDate() { return receivedConfDate; }
    public String getNumConf() { return numConf; }
    public LocalDate getDateConf() { return dateConf; }
    public LocalDate getConfSentDate() { return confSentDate; }
    public LocalDate getExecutedDate() { return executedDate; }
    public LocalDate getReadyForDelivDate() { return readyForDelivDate; }
    public String getPayCondition() { return payCondition; }
    public String getAddr() { return addr; }
    public String getDeliveryTerm() { return deliveryTerm; }
    public String getAddInfo() { return addInfo; }
    public Integer getDirectorId() { return directorId; }
    public Integer getLogistId() { return logistId; }
    public Integer getDirectorRbId() { return directorRbId; }
    public Integer getChiefDepId() { return chiefDepId; }
    public Integer getManagerId() { return managerId; }

    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }
    public void setEditDate(LocalDateTime editDate) { this.editDate = editDate; }
    public void setEditedBy(Integer editedBy) { this.editedBy = editedBy; }
    public void setNumber(String number) { this.number = number; }
    public void setOrdDate(LocalDate ordDate) { this.ordDate = ordDate; }
    public void setContractorId(Integer contractorId) { this.contractorId = contractorId; }
    public void setContactPersonId(Integer contactPersonId) { this.contactPersonId = contactPersonId; }
    public void setCurrencyId(Integer currencyId) { this.currencyId = currencyId; }
    public void setStuffCategoryId(Integer stuffCategoryId) { this.stuffCategoryId = stuffCategoryId; }
    public void setBlankId(Integer blankId) { this.blankId = blankId; }
    public void setSellerForWhoId(Integer sellerForWhoId) { this.sellerForWhoId = sellerForWhoId; }
    public void setContractorForId(Integer contractorForId) { this.contractorForId = contractorForId; }
    public void setSpecificationId(Integer specificationId) { this.specificationId = specificationId; }
    public void setBlock(Short block) { this.block = block; }
    public void setAnnul(Short annul) { this.annul = annul; }
    public void setSumm(BigDecimal summ) { this.summ = summ; }
    public void setComment(String comment) { this.comment = comment; }
    public void setSentToProdDate(LocalDate sentToProdDate) { this.sentToProdDate = sentToProdDate; }
    public void setReceivedConfDate(LocalDate receivedConfDate) { this.receivedConfDate = receivedConfDate; }
    public void setNumConf(String numConf) { this.numConf = numConf; }
    public void setDateConf(LocalDate dateConf) { this.dateConf = dateConf; }
    public void setConfSentDate(LocalDate confSentDate) { this.confSentDate = confSentDate; }
    public void setExecutedDate(LocalDate executedDate) { this.executedDate = executedDate; }
    public void setReadyForDelivDate(LocalDate readyForDelivDate) { this.readyForDelivDate = readyForDelivDate; }
    public void setPayCondition(String payCondition) { this.payCondition = payCondition; }
    public void setAddr(String addr) { this.addr = addr; }
    public void setDeliveryTerm(String deliveryTerm) { this.deliveryTerm = deliveryTerm; }
    public void setAddInfo(String addInfo) { this.addInfo = addInfo; }
    public void setDirectorId(Integer directorId) { this.directorId = directorId; }
    public void setLogistId(Integer logistId) { this.logistId = logistId; }
    public void setDirectorRbId(Integer directorRbId) { this.directorRbId = directorRbId; }
    public void setChiefDepId(Integer chiefDepId) { this.chiefDepId = chiefDepId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
}
