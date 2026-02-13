package com.dcl.modern.contracts.infrastructure;

import com.dcl.modern.contracts.domain.Contract;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    List<Contract> findAllByOrderByDateDescNumberDesc();

    boolean existsByContractorId(Integer contractorId);
}
