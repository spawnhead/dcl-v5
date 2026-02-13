package com.dcl.modern.contractors.infrastructure;

import com.dcl.modern.contractors.domain.Contractor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<Contractor, Integer> {

    List<Contractor> findAllByOrderByNameAsc();

    /** For edit save: UNP duplicate check excluding current contractor. */
    Optional<Contractor> findByUnp(String unp);
}
