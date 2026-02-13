package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.domain.OrderPayment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * dcl_ord_list_payment. CONTRACTS §4.
 */
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Integer> {

    List<OrderPayment> findByOrder_IdOrderById(Integer ordId);
}
