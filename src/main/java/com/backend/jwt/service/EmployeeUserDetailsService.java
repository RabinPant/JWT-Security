package com.backend.jwt.service;

import com.backend.jwt.Entity.Employee;
import com.backend.jwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee>emp = employeeRepository.findByUserName(username);
        return emp.map(EmployeeUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException(username+"not found in the system"));
    }
}
