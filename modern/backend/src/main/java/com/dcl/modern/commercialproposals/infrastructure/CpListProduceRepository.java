package com.dcl.modern.commercialproposals.infrastructure;

import com.dcl.modern.commercialproposals.domain.CpListProduce;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpListProduceRepository extends JpaRepository<CpListProduce, Integer> {

    List<CpListProduce> findByCprIdOrderByIdAsc(Integer cprId);
}
