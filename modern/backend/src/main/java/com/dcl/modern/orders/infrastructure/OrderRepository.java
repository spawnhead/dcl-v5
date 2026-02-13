package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * dcl_order. Legacy: OrderDAO.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
