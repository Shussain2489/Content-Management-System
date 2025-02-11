package com.example.cms.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

		private UserRepository userRepo;
		
		
	
	public CustomUserDetailsService(UserRepository userRepo) {
			super();
			this.userRepo = userRepo;
		}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByEmail(username).map(user -> new CustomUserDetails(user))
				.orElseThrow(()-> new UsernameNotFoundException("User not found by given email"));
	}

}
