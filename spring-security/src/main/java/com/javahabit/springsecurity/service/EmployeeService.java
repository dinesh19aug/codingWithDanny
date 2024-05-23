package com.javahabit.springsecurity.service;

import com.javahabit.springsecurity.repository.EmployeeRepository;
import com.javahabit.springsecurity.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("employee-svc")
public class EmployeeService implements IService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Override
    public Employee findById(int id) {
        Optional<Employee> result = employeeRepository.findById(id);

        Employee employee = null;

        if (result.isPresent()) {
            employee = result.get();
        }
        else {
            throw new RuntimeException("Missing employee id: " + id);
        }

        return employee;
    }
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}
