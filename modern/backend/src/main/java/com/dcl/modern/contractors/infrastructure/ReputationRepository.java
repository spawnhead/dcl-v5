package com.dcl.modern.contractors.infrastructure;

import com.dcl.modern.contractors.domain.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReputationRepository extends JpaRepository<Reputation, Integer> {
}
