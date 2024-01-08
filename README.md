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
