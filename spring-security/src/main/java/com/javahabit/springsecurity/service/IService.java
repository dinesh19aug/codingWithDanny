package com.javahabit.springsecurity.service;

import com.javahabit.springsecurity.entity.Employee;

import java.util.List;

public interface IService {
    Employee save(Employee employee);
    void deleteById(int id);
    List<Employee> findAll();

    Employee findById(int id);



}
