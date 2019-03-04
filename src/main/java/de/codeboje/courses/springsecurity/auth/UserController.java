package de.codeboje.courses.springsecurity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.courses.springsecurity.usermgm.User;
import de.codeboje.courses.springsecurity.usermgm.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	private void registerUser(@RequestBody @Validated User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}
}
