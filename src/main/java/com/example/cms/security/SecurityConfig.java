package com.example.cms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private CustomUserDetailsService customUserDetailservice;

	
public SecurityConfig(CustomUserDetailsService customUserDetailservice) {
		super();
		this.customUserDetailservice = customUserDetailservice;
	}


//	BCryptpasswordEncode is the most secure and widely used algorithm for encrypting passwords there for we are using it.
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder(12);  
	}
	
	
	AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(customUserDetailservice);
		return provider;
	}
	
//	csrf: cross side request forgery.
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/users/register","/users/{userId}")
				.permitAll().anyRequest().authenticated()).formLogin(Customizer.withDefaults()).build();
	}
}
