package com.dcl.modern.orders.infrastructure;

import com.dcl.modern.orders.domain.OrderPaySum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * dcl_ord_list_pay_sum. CONTRACTS §5.
 */
public interface OrderPaySumRepository extends JpaRepository<OrderPaySum, Integer> {

    List<OrderPaySum> findByOrder_IdOrderById(Integer ordId);
}
