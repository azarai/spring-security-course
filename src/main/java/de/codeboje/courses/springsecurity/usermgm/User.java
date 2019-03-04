package de.codeboje.courses.springsecurity.usermgm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
