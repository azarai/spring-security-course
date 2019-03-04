package de.codeboje.courses.springsecurity;

import org.springframework.data.repository.CrudRepository;

import de.codeboje.courses.springsecurity.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

}
