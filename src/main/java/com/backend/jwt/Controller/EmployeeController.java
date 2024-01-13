package com.backend.jwt.Controller;

import com.backend.jwt.Entity.Employee;
import com.backend.jwt.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome java techie";
    }

    @PostMapping("/create")
    public Employee onboardNewEmployee(@RequestBody Employee employee){
        return service.createNewEmployee(employee);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_HR') ")
    public List<Employee>getAllEmployee(){
        return service.getEmployee();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public Employee getEmployeeById(@PathVariable Integer id){
        return service.getEmployee(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public Employee updateRoleEmployee(@RequestBody Employee employee){
        return service.changeRoleEmployee(employee);
    }
}
