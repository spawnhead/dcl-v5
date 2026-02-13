package com.dcl.modern.commercialproposals.infrastructure;

import com.dcl.modern.commercialproposals.domain.CommercialProposal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialProposalRepository extends JpaRepository<CommercialProposal, Integer> {

    List<CommercialProposal> findAllByOrderByDateDescNumberDesc();
}
