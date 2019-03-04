package de.codeboje.courses.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.codeboje.courses.springsecurity.usermgm.User;
import lombok.Data;

@Entity
@Data
public class Task {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	private String text;
	
	@Enumerated(EnumType.STRING)
	private Lane lane = Lane.todo;
	
	@ManyToOne
	@JsonIgnore
	private User user;
}
