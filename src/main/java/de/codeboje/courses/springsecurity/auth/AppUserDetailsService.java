package de.codeboje.courses.springsecurity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.codeboje.courses.springsecurity.usermgm.User;
import de.codeboje.courses.springsecurity.usermgm.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userDb = userRepo.findByUsername(username);
		if(userDb == null) {
			throw new UsernameNotFoundException("Unknown user with name "+ username);
		}
		return new SecurityUser(userDb.getUsername(), userDb.getPassword());
	}

}
