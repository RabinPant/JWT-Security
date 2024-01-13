package com.backend.jwt.securityConfig;

import com.backend.jwt.service.EmployeeUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EMSSecurityConifg {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        UserDetails hr = User.withUsername("rabin")
//                .password(passwordEncoder.encode("pwd1"))
//                .roles("HR").build();
//        UserDetails manager = User.withUsername("sabin")
//                .password(passwordEncoder.encode("pwd2"))
//                .roles("MANAGER").build();
//        UserDetails admin = User.withUsername("abin")
//                .password(passwordEncoder.encode("pwd3"))
//                .roles("HR","MANAGER").build();
//        UserDetails emp = User.withUsername("bin")
//                .password(passwordEncoder.encode("pwd3"))
//                .roles("EMPLOYEE").build();
//
//        return new InMemoryUserDetailsManager(emp,hr,admin,manager);
        return new EmployeeUserDetailsService();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/employees/welcome")
                .permitAll().and()
                .authorizeRequests()
                .antMatchers("/employees/create")
                .permitAll().and()
                .authorizeRequests()
                .antMatchers("/employees/**")
                .authenticated()
                .and()
                .httpBasic().and().build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
