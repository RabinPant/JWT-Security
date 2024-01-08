package com.backend.jwt;

import com.backend.jwt.Entities.Authority;
import com.backend.jwt.Entities.User;
import com.backend.jwt.reposirtory.userDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JwtApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private userDetailsRepository userDetailsRepository;
	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@PostConstruct
	protected void init(){
		List<Authority>authorityList = new ArrayList<>();
		authorityList.add(createAuthority("USER","User role"));
		authorityList.add(createAuthority("ADMIN","ADMIN ROLE"));
		User user = new User();
		user.setUserName("rabpan123");
		user.setFirstName("rabin");
		user.setLastName("pant");

		user.setPassword(passwordEncoder.encode("rab123"));
		user.setEnabled(true);
		userDetailsRepository.save(user);
	}
	private Authority createAuthority(String roleCode, String roleDescription){
		Authority authority = new Authority();
		authority.setRoleCode(roleCode);
		authority.setRoleDescription(roleDescription);
		return authority;
	}
}
