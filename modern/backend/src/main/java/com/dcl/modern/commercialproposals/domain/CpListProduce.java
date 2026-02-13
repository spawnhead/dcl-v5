package com.dcl.modern.commercialproposals.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * dcl_cpr_list_produce. Produce rows for CP (clone support).
 */
@Entity
@Table(name = "dcl_cpr_list_produce")
public class CpListProduce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lpr_id")
    private Integer id;

    @Column(name = "cpr_id", nullable = false)
    private Integer cprId;

    @Column(name = "lpr_produce_name", length = 1000)
    private String produceName;

    @Column(name = "lpr_catalog_num", length = 50)
    private String catalogNum;

    @Column(name = "lpr_count", precision = 15, scale = 2)
    private BigDecimal count;

    @Column(name = "lpr_price_brutto", precision = 15, scale = 2)
    private BigDecimal priceBrutto;

    @Column(name = "stf_id")
    private Integer stfId;

    @Column(name = "prd_id")
    private Integer prdId;

    protected CpListProduce() {}

    public Integer getId() { return id; }
    public Integer getCprId() { return cprId; }
    public String getProduceName() { return produceName; }
    public String getCatalogNum() { return catalogNum; }
    public BigDecimal getCount() { return count; }
    public BigDecimal getPriceBrutto() { return priceBrutto; }
    public Integer getStfId() { return stfId; }
    public Integer getPrdId() { return prdId; }
}
