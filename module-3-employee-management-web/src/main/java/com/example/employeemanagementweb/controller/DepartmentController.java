package com.example.employeemanagementweb.controller;

import com.example.employeemanagementweb.dto.DepartmentFormDto;
import com.example.employeemanagementweb.model.Department;
import com.example.employeemanagementweb.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String list(Model model) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "departments/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("department", new DepartmentFormDto());
        return "departments/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Department dept = departmentService.findById(id);
        DepartmentFormDto dto = new DepartmentFormDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setDescription(dept.getDescription());
        model.addAttribute("department", dto);
        return "departments/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("department") DepartmentFormDto dto,
                       RedirectAttributes redirectAttrs) {
        if (departmentService.existsByName(dto.getName()) && dto.getId() == null) {
            redirectAttrs.addFlashAttribute("error", "Department name already exists: " + dto.getName());
            return "redirect:/departments/new";
        }
        Department dept = new Department();
        dept.setName(dto.getName());
        dept.setDescription(dto.getDescription());
        if (dto.getId() != null) {
            dept = departmentService.findById(dto.getId());
            dept.setName(dto.getName());
            dept.setDescription(dto.getDescription());
        }
        departmentService.save(dept);
        redirectAttrs.addFlashAttribute("success", "Department saved successfully!");
        return "redirect:/departments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            departmentService.deleteById(id);
            redirectAttrs.addFlashAttribute("success", "Department deleted successfully!");
        } catch (IllegalStateException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/departments";
    }
}
