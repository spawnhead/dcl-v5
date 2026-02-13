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
import java.time.LocalDate;

/**
 * dcl_ord_list_pay_sum. Legacy: OrderAction ajaxOrderPaySumsGrid. CONTRACTS §5.
 */
@Entity
@Table(name = "dcl_ord_list_pay_sum")
public class OrderPaySum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ops_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", nullable = false)
    private Order order;

    @Column(name = "ops_sum", nullable = false, precision = 15, scale = 2)
    private BigDecimal opsSum;

    @Column(name = "ops_date")
    private LocalDate opsDate;

    public OrderPaySum() {}

    public Integer getId() { return id; }
    public Order getOrder() { return order; }
    public BigDecimal getOpsSum() { return opsSum; }
    public LocalDate getOpsDate() { return opsDate; }

    public void setOrder(Order order) { this.order = order; }
    public void setOpsSum(BigDecimal opsSum) { this.opsSum = opsSum; }
    public void setOpsDate(LocalDate opsDate) { this.opsDate = opsDate; }
}
