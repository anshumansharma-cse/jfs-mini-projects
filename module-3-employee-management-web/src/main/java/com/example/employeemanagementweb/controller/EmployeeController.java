package com.example.employeemanagementweb.controller;

import com.example.employeemanagementweb.dto.EmployeeFormDto;
import com.example.employeemanagementweb.model.Department;
import com.example.employeemanagementweb.model.Employee;
import com.example.employeemanagementweb.service.DepartmentService;
import com.example.employeemanagementweb.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        List<Employee> employees = employeeService.search(search);
        model.addAttribute("employees", employees);
        model.addAttribute("searchKeyword", search);
        return "employees/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employee", new EmployeeFormDto());
        model.addAttribute("departments", departmentService.findAll());
        return "employees/form";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        EmployeeFormDto dto = new EmployeeFormDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setJobTitle(employee.getJobTitle());
        dto.setSalary(employee.getSalary());
        dto.setJoiningDate(employee.getJoiningDate() != null ? employee.getJoiningDate().toString() : "");
        dto.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        dto.setActive(employee.getActive());
        model.addAttribute("employee", dto);
        model.addAttribute("departments", departmentService.findAll());
        return "employees/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("employee") EmployeeFormDto dto,
                       RedirectAttributes redirectAttrs) {
        if (employeeService.existsByEmail(dto.getEmail()) && dto.getId() == null) {
            redirectAttrs.addFlashAttribute("error", "Email already exists: " + dto.getEmail());
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.employee", null);
            redirectAttrs.addFlashAttribute("departments", departmentService.findAll());
            return "redirect:/employees/new";
        }
        Employee employee = employeeService.save(dto);
        redirectAttrs.addFlashAttribute("success", "Employee saved successfully!");
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        employeeService.deleteById(id);
        redirectAttrs.addFlashAttribute("success", "Employee deleted successfully!");
        return "redirect:/employees";
    }
}
