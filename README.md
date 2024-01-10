# JWT-Security
### In Memory Authentication POC

```
Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("pradeep")
                .password(passwordEncoder()
                        .encode("test@123"))
                .authorities("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

}
```
```
In above code to have security in the springboot application we have extended from websecurity configrue adapter which is an interface and implemented
or override the methods configure with authManager where we can give password and userName for basic form login
```
### Security Concept
```
Authentication -> who are you -> 401
Authorization -> ROLE -> what all i can do -> 403

why spring secuirty module was needed?

ealrier we have concept of filter:
class CustomFilter extends GenericFilter{

    doFilter(HttpServletRequest request){
        if(uri.contains("/employee"){
            //do something
        }
        if(uri.contains("/customers")){
            //do something
        }
}
}
This was the way earlier we used to validate the end point and direct the user
but this requires lot of hastle and more time so advent of spring sercurity came in the picture.

---spring security module:
username & password
token base
OAuth


FORM BASED AUTH:

After you add spring security to you pom and want to disable the auto configuration that comes up when you add the dependency then :

@SpringBootApplication(exclude=SecurityAutoConfiguration)
this we are adding directly in the anotation

the other way is to add in the properties file:
spring.autoconfigure.exclude = org.springframework.autoconfigure.security.servlet.....
spring.security.user.name = rabin
spring.security.password = pwd1

public class EMSSecurity extends WebSecurityConfigureAdapter{

    @override
    protected void configure(AuthenticationManager auth) throws Exception{
        auth.inMemoryAuthentication().
            .withUser("rabin")
            .password(passwordEncoder().encode("pwd1"))
            .roles("USER")
        auth.inMemoryAuthentication().
            .withUser("sabin")
            .password(passwordEncoder().encode("pwd2"))
            .roles("ADMIN")
        auth.inMemoryAuthentication().
            .withUser("rabin")
            .password("pwd1")
            .roles("ADMIN")
    }

    //I have a situation where I want particular end point to allow all user whereas     //I want some to authenticate

    @Override
    protected void Configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            .antMatchers("/nonsecureapp").permitAll()
            .and()
            .authorizeRequests().antMatchers("welcome","text")
            .authenticated().and().httpBasic();
    }
    
}

@Bean
public PasswordEncoder passwordEncoder(){
return new BCryptPasswordEncoder();
}
=====================================================================================This is the legacy conecpt and after the spring 2.5x they define secuirtyFilterChain:
this is the replacement for the websecurityConfigurerAdapter

- UserDetails and UserDetailsService class we need to remember
-userDetails class holds all the info required for the  
@Configuration
@EnableWebSecurity
public class EMSUpgradeSecuityConfig{
@Bean
public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
    UserDetails admmin =User.withUsername("Basant")
        .password(passwordEncoder.encode("pw2")).roles("ADMIN").build();

    UserDetails user =User.withUsername("Basant")
        .password(passwordEncoder.encode("pw2")).roles("USER").build();

    UserDetails userAdmmin =User.withUsername("Basant")
        .password(passwordEncoder.encode("pw2")).roles("ADMIN","USER").build();

    return new InMemoryUserDetailsManager(user,admin,userAdmin);
}
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http){
    http.authorizeRequests()
        .antMatchers("/nonsecureapp").permitAll()
        .and()
        .authorizeRequests().antMatchers("/welcome","/text")
        .authenticated().and().httpBasic().and().build();
}
@Bean
public PasswordEncoder passwordEncoder(){
    return new BcryptPasswordEncoder();
}

}
```
### Authentication and Role based authorization
```
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EMSSecurityConifg {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails hr = User.withUsername("rabin")
                .password(passwordEncoder.encode("pwd1"))
                .roles("HR").build();
        UserDetails manager = User.withUsername("sabin")
                .password(passwordEncoder.encode("pwd2"))
                .roles("MANAGER").build();
        UserDetails admin = User.withUsername("abin")
                .password(passwordEncoder.encode("pwd3"))
                .roles("HR","MANAGER").build();
        UserDetails emp = User.withUsername("bin")
                .password(passwordEncoder.encode("pwd3"))
                .roles("EMPLOYEE").build();

        return new InMemoryUserDetailsManager(emp,hr,admin,manager);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/employees/welcome")
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
=====================================================================================

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
    @PreAuthorize("hasAuthority('ROLE_HR')")
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
}
```
