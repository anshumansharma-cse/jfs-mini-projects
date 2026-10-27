package com.example.employeemanagementweb.dto;

import jakarta.validation.constraints.*;

public class DepartmentFormDto {
    private Long id;

    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    @NotNull(message = "Department name is required")
    private String name;

    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
