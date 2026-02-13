package com.dcl.modern.contractors.infrastructure;

import com.dcl.modern.contractors.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
