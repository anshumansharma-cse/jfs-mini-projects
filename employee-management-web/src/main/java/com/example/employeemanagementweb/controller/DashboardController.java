package com.example.employeemanagementweb.controller;

import com.example.employeemanagementweb.model.Department;
import com.example.employeemanagementweb.model.Employee;
import com.example.employeemanagementweb.service.DepartmentService;
import com.example.employeemanagementweb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String dashboard(Model model) {
        List<Employee> allEmployees = employeeService.findAll();
        List<Department> departments = departmentService.findAll();

        Map<String, Long> deptStats = new HashMap<>();
        for (Department dept : departments) {
            long count = allEmployees.stream()
                    .filter(e -> e.getDepartment() != null && e.getDepartment().getId().equals(dept.getId()))
                    .count();
            deptStats.put(dept.getName(), count);
        }

        model.addAttribute("totalEmployees", allEmployees.size());
        model.addAttribute("activeEmployees", employeeService.countActive());
        model.addAttribute("totalDepartments", departments.size());
        model.addAttribute("recentEmployees", employeeService.countRecentEmployees());
        model.addAttribute("deptStats", deptStats);
        model.addAttribute("departments", departments);

        return "dashboard";
    }
}
