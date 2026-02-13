package com.dcl.modern.contracts.infrastructure;

import com.dcl.modern.contracts.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
}
