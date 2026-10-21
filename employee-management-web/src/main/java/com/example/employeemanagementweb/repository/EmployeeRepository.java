package com.example.employeemanagementweb.repository;

import com.example.employeemanagementweb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(COALESCE(e.department.name, '')) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Employee> searchAll(String search);

    @Query("SELECT e FROM Employee e JOIN e.department d WHERE LOWER(d.name) = LOWER(:department)")
    List<Employee> findByDepartmentName(String department);

    long countByActiveTrue();

    @Query("SELECT COUNT(e) FROM Employee e WHERE YEAR(e.joiningDate) = YEAR(CURRENT_DATE) AND MONTH(e.joiningDate) >= MONTH(CURRENT_DATE) - 3")
    int countRecentEmployees();
}
