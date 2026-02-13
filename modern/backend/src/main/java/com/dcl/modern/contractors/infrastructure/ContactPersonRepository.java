package com.dcl.modern.contractors.infrastructure;

import com.dcl.modern.contractors.domain.ContactPerson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * dcl_contact_person. TASK-0066: full 1:1 persistence.
 */
@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {

    List<ContactPerson> findByContractorIdOrderByIdAsc(Integer contractorId);

    void deleteByContractorId(Integer contractorId);
}
