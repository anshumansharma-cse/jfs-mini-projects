package com.example.employeemanagementweb.service;

import com.example.employeemanagementweb.dto.EmployeeFormDto;
import com.example.employeemanagementweb.model.Department;
import com.example.employeemanagementweb.model.Employee;
import com.example.employeemanagementweb.repository.DepartmentRepository;
import com.example.employeemanagementweb.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        return employeeRepository.searchAll(keyword);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    public Employee save(EmployeeFormDto dto) {
        Employee employee;
        if (dto.getId() != null) {
            employee = findById(dto.getId());
        } else {
            employee = new Employee();
        }

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setJobTitle(dto.getJobTitle());
        employee.setSalary(dto.getSalary());
        employee.setActive(dto.getActive());

        if (dto.getJoiningDate() != null && !dto.getJoiningDate().isBlank()) {
            employee.setJoiningDate(LocalDate.parse(dto.getJoiningDate(), DATE_FORMATTER));
        }

        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));
            employee.setDepartment(dept);
        }

        return employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public long countAll() {
        return employeeRepository.count();
    }

    public long countActive() {
        return employeeRepository.countByActiveTrue();
    }

    public int countRecentEmployees() {
        return employeeRepository.countRecentEmployees();
    }
}
