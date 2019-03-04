package de.codeboje.courses.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.courses.springsecurity.model.Task;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepo;
	
	@GetMapping("/tasks")
	public Iterable<Task> getTasks() {
		return taskRepo.findAll();
	}
	
	@PostMapping("tasks")
	@ResponseStatus(HttpStatus.CREATED)
	private Task createTask(@RequestBody Task task) {
		return taskRepo.save(task);
	}
	
	@GetMapping("/task/{id}")
	private Task getTask(@PathVariable("id") Long id) {
		return taskRepo.findById(id).orElse(null);
	}
	
	@PutMapping("/task/{id}")
	private Task putTask(@PathVariable("id") Long id, @RequestBody Task task) {
		if (taskRepo.findById(id).isPresent()) {
			task.setId(id);
			return taskRepo.save(task);
		} else {
			throw new RuntimeException("no task with that id");
		}	
	}
	
	@DeleteMapping("/task/{id}")
	private void deleteTask(@PathVariable("id") Long id) {
		if (taskRepo.findById(id).isPresent()) {	
			taskRepo.deleteById(id);
		} else {
			throw new RuntimeException("no task with that id");
		}
		
	}
}