package com.example.employeemanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found: " + id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public boolean emailExistsForOtherEmployee(String email, Long id) {
        return employeeRepository.existsByEmailAndIdNot(email, id);
    }
}