package com.javahabit.springsecurity.controller;

import com.javahabit.springsecurity.entity.Employee;
import com.javahabit.springsecurity.service.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class EmployeeController {

    private IService employeeService;

    public EmployeeController(@Qualifier("employee-svc") IService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> sayHello(@RequestParam(value = "name", required = false) String name){
        if( name == null){
            name = "World";
        }
        return ResponseEntity.status(200).body("Hello " + name);
    }


    @GetMapping("/employee")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee not found - " + employeeId);
        }
        return employee;
    }

   

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee employee) {
        //employee.setId(0);
        Employee dbEmployee = employeeService.save(employee);
        return dbEmployee;
    }


    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee dbEmployee = employeeService.save(employee);
        return dbEmployee;
    }


    @DeleteMapping("/employee/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        employeeService.deleteById(employeeId);
        return employeeId + ": Deleted employee id";
    }
}
