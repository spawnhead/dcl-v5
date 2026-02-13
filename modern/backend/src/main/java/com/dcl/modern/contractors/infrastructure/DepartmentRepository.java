package com.dcl.modern.contractors.infrastructure;

import com.dcl.modern.contractors.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
