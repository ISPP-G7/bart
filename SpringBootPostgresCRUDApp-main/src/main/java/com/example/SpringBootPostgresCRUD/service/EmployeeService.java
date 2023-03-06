package com.example.SpringBootPostgresCRUD.service;

import com.example.SpringBootPostgresCRUD.entity.Employee;
import com.example.SpringBootPostgresCRUD.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employeeList.add(employee));

        return employeeList;
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public boolean saveOrUpdateEmployee(Employee employee) {
        Employee emp = employeeRepository.save(employee);
        if (employeeRepository.findById(emp.getId()) != null) {
            return true;
        }
        return false;
    }

    public boolean deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        if (employeeRepository.findById(id) != null) {
            return true;
        }
        return false;
    }

}
