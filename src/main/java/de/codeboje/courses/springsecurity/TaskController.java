package de.codeboje.courses.springsecurity;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.courses.springsecurity.auth.SecurityUser;
import de.codeboje.courses.springsecurity.model.Task;
import de.codeboje.courses.springsecurity.usermgm.UserRepository;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private UserRepository userRepo;

	
	@GetMapping("/tasks")
	public Iterable<Task> getTasks() {
		
		String username = ( (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		return taskRepo.findAllTasksByUsername(username);
	}
	
	@PostMapping("tasks")
	@ResponseStatus(HttpStatus.CREATED)
	public Task createTask(@RequestBody Task task, Principal principal) {
		String username = ((SecurityUser) ((Authentication) principal).getPrincipal()).getUsername();
		
		task.setUser(userRepo.findByUsername(username));
		return taskRepo.save(task);
	}
	
	
	
	@PostAuthorize("hasAuthority('user') and returnObject.user.username == principal.username")
	@GetMapping("/task/{id}")
	public Task getTask(@PathVariable("id") Long id) {
		return taskRepo.findById(id).orElse(null);
	}
	
	@PreAuthorize("hasPermission(#id, 'write')")
	@PutMapping("/task/{id}")
	public Task putTask(@PathVariable("id") Long id, @RequestBody Task task) {
		if (taskRepo.findById(id).isPresent()) {
			task.setId(id);
			return taskRepo.save(task);
		} else {
			throw new RuntimeException("no task with that id");
		}	
	}
	
	
	// protected via WebSecurityConfiguration
	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") Long id) {
		if (taskRepo.findById(id).isPresent()) {	
			taskRepo.deleteById(id);
		} else {
			throw new RuntimeException("no task with that id");
		}
		
	}
}