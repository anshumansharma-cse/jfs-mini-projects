package com.example.employeemanagementweb.service;

import com.example.employeemanagementweb.model.Department;
import com.example.employeemanagementweb.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        Department dept = findById(id);
        if (dept.getEmployeeCount() > 0) {
            throw new IllegalStateException("Cannot delete department with existing employees");
        }
        departmentRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}
