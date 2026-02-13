package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.domain.OrderProduce;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * dcl_ord_list_produce. Legacy: OrderDAO produces.
 */
public interface OrderProduceRepository extends JpaRepository<OrderProduce, Integer> {

    List<OrderProduce> findByOrder_IdOrderById(Integer orderId);
}
