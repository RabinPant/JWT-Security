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
