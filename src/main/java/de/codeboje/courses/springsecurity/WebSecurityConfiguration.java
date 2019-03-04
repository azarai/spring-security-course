package de.codeboje.courses.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.codeboje.courses.springsecurity.auth.AppUserDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/register").permitAll()
			.anyRequest().authenticated()
		.and()
		.httpBasic();
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth
			.userDetailsService(appUserDetailsService)
			.passwordEncoder(passwordEncode()
		);
		// @formatter:on
	}

	@Bean
	PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}
}