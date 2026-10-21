package com.example.employeemanagementweb.dto;

import jakarta.validation.constraints.*;

public class EmployeeFormDto {
    private Long id;

    @Size(min = 2, max = 50, message = "First name must be 2-50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be 2-50 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\-\\s]{7,15}$", message = "Invalid phone number")
    private String phone;

    @Size(min = 2, max = 100, message = "Job title must be 2-100 characters")
    @NotNull(message = "Job title is required")
    private String jobTitle;

    @Min(value = 0, message = "Salary must be non-negative")
    private Double salary;

    private String joiningDate;
    private Long departmentId;
    private Boolean active = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public String getJoiningDate() { return joiningDate; }
    public void setJoiningDate(String joiningDate) { this.joiningDate = joiningDate; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
