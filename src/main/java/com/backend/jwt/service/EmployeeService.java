package com.backend.jwt.service;

import com.backend.jwt.Entity.Employee;
import com.backend.jwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private static final String DEFAULT_ROLE = "ROLE_EMPLOYEE";
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee createNewEmployee(Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRoles(DEFAULT_ROLE);
        return employeeRepository.save(employee);
    }
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }
    public Employee getEmployee(Integer id){
        return employeeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Employee not found"));
    }
    public Employee changeRoleEmployee(Employee employee){
        Employee existingEmployee =  getEmployee(employee.getId());
        existingEmployee.setRoles(employee.getRoles());
        return employeeRepository.save(existingEmployee);
    }
}
