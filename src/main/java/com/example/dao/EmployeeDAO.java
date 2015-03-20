package com.example.dao;

import java.util.List;

import com.example.entity.Employee;

public interface EmployeeDAO
{
    public void addEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public void deleteEmployee(Integer employeeId);
}