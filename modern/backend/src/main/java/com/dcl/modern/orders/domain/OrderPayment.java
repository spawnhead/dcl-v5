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
 * dcl_ord_list_payment. Legacy: OrderAction ajaxOrderPaymentsGrid. CONTRACTS §4.
 */
@Entity
@Table(name = "dcl_ord_list_payment")
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orp_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", nullable = false)
    private Order order;

    @Column(name = "orp_percent", nullable = false, precision = 11, scale = 5)
    private BigDecimal orpPercent;

    @Column(name = "orp_sum", nullable = false, precision = 15, scale = 2)
    private BigDecimal orpSum;

    @Column(name = "orp_date")
    private LocalDate orpDate;

    public OrderPayment() {}

    public Integer getId() { return id; }
    public Order getOrder() { return order; }
    public BigDecimal getOrpPercent() { return orpPercent; }
    public BigDecimal getOrpSum() { return orpSum; }
    public LocalDate getOrpDate() { return orpDate; }

    public void setOrder(Order order) { this.order = order; }
    public void setOrpPercent(BigDecimal orpPercent) { this.orpPercent = orpPercent; }
    public void setOrpSum(BigDecimal orpSum) { this.orpSum = orpSum; }
    public void setOrpDate(LocalDate orpDate) { this.orpDate = orpDate; }
}
