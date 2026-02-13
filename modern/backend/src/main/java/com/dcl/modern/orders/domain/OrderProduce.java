package com.dcl.modern.orders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * dcl_ord_list_produce. Legacy: OrderAction produces grid. Source: docs/screens/order_edit.
 */
@Entity
@Table(name = "dcl_ord_list_produce")
public class OrderProduce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opr_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", nullable = false)
    private Order order;

    @Column(name = "opr_produce_name", length = 1000)
    private String produceName;

    @Column(name = "opr_catalog_num", length = 50)
    private String catalogNum;

    @Column(name = "opr_count", precision = 15, scale = 2)
    private BigDecimal count;

    @Column(name = "opr_price_brutto", precision = 15, scale = 2)
    private BigDecimal priceBrutto;

    @Column(name = "opr_discount", precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(name = "opr_price_netto", precision = 15, scale = 2)
    private BigDecimal priceNetto;

    @Column(name = "opr_summ", precision = 15, scale = 2)
    private BigDecimal summ;

    @Column(name = "prd_id")
    private Integer produceId;

    @Column(name = "opr_comment", length = 5000)
    private String comment;

    @Column(name = "drp_price", precision = 15, scale = 2)
    private BigDecimal drpPrice;

    @Column(name = "ctr_id")
    private Integer contractorId;

    @Column(name = "spc_id")
    private Integer specificationId;

    public OrderProduce() {}

    public Integer getId() { return id; }
    public Order getOrder() { return order; }
    public String getProduceName() { return produceName; }
    public String getCatalogNum() { return catalogNum; }
    public BigDecimal getCount() { return count; }
    public BigDecimal getPriceBrutto() { return priceBrutto; }
    public BigDecimal getDiscount() { return discount; }
    public BigDecimal getPriceNetto() { return priceNetto; }
    public BigDecimal getSumm() { return summ; }
    public Integer getProduceId() { return produceId; }
    public String getComment() { return comment; }
    public BigDecimal getDrpPrice() { return drpPrice; }
    public Integer getContractorId() { return contractorId; }
    public Integer getSpecificationId() { return specificationId; }

    public void setOrder(Order order) { this.order = order; }
    public void setProduceName(String produceName) { this.produceName = produceName; }
    public void setCatalogNum(String catalogNum) { this.catalogNum = catalogNum; }
    public void setCount(BigDecimal count) { this.count = count; }
    public void setPriceBrutto(BigDecimal priceBrutto) { this.priceBrutto = priceBrutto; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public void setPriceNetto(BigDecimal priceNetto) { this.priceNetto = priceNetto; }
    public void setSumm(BigDecimal summ) { this.summ = summ; }
    public void setProduceId(Integer produceId) { this.produceId = produceId; }
    public void setComment(String comment) { this.comment = comment; }
    public void setDrpPrice(BigDecimal drpPrice) { this.drpPrice = drpPrice; }
    public void setContractorId(Integer contractorId) { this.contractorId = contractorId; }
    public void setSpecificationId(Integer specificationId) { this.specificationId = specificationId; }
}
