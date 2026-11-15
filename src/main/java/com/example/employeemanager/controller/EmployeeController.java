package com.example.employeemanager.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.service.EmployeeService;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }

    // Show the form and employee list
    @GetMapping("/employees")
    public String listEmployees(
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        model.addAttribute("employee", new Employee());
        model.addAttribute("employees", employeeService.searchByName(keyword));
        model.addAttribute("keyword", keyword);

        return "employees";
    }

    // Save a new employee
    @PostMapping("/employees")
    public String createEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        employee.setId(null);

        if (employeeService.emailExists(employee.getEmail())) {
            result.rejectValue(
                    "email", "duplicate",
                    "This email is already registered");
        }

        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.findAll());
            return "employees";
        }

        employeeService.save(employee);
        redirectAttributes.addFlashAttribute(
                "success", "Employee saved successfully!");

        return "redirect:/employees";
    }

    // Show the edit form
    @GetMapping("/employees/{id}/edit")
    public String editEmployee(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "edit-employee";
    }

    // Update an existing employee
    @PostMapping("/employees/{id}/update")
    public String updateEmployee(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        Employee existingEmployee = employeeService.findById(id);
        employee.setId(id);

        if (employeeService.emailExistsForOtherEmployee(
                employee.getEmail(), id)) {
            result.rejectValue(
                    "email", "duplicate",
                    "This email is already registered");
        }

        if (result.hasErrors()) {
            return "edit-employee";
        }

        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());

        employeeService.save(existingEmployee);
        redirectAttributes.addFlashAttribute(
                "success", "Employee updated successfully!");

        return "redirect:/employees";
    }

    // Delete an employee
    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        employeeService.deleteById(id);
        redirectAttributes.addFlashAttribute(
                "success", "Employee deleted successfully!");

        return "redirect:/employees";
    }
}